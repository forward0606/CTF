## 
用 redare2
```
r2 pwntool
aa
s main
V
V
```
![](https://i.imgur.com/yosL8jP.png)

發現 `cmp eax, 0x79487ff` 會產生一個 branch，並且 false 下程式會結束，所以我們要讓 input 等於 `0x79487ff`

輸入成功後會顯示 `Hacker can complete 1000 math problems in 60s, prove yourself.`，然後就寫的 python 算一下答案，算完後就拿到 shell 了

![](https://i.imgur.com/e1W561C.png)
