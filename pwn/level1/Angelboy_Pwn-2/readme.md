## LAB Angelboy_Pwn-2
### return to shell attack
use gdb with `vmmap` to find the segment with all rwx permission(紅色的應該都可讀可寫可執行)

![](https://i.imgur.com/PwKTnRh.png)

用 r2 看，我們發現我們可以寫 50 個 byte 在 0x601080 的地方

![](https://i.imgur.com/x8LzuIo.png)

後面有呼叫 get，所以有 bufferoverflow 產生

![](https://i.imgur.com/C3uwAeG.png)

```
stack:
var_20h    0x20
rbp        0x8
return_add 0x8
```
把 return address 設到 0x601080

### 問題排解
在本地端測試， read 一定會吃滿 50 個 bytes 超過 50 的才會變成 get 的輸入，但 server 端則不是，換行後就變成 get 吃了
