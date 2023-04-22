
## LAB: 張元_pwn-7
![](https://i.imgur.com/bBKv48a.png)

從 main 得知只要經過三個 stage 就可以拿到 shell
### stage1
![](https://i.imgur.com/1hSqDSy.png)
```
test eax, eax --> 檢查 eax 是否為 0
```

條件為輸入的數字 `and 1` 要是 0，並且 `and 0x100000` 要是 0，`0x111111` 可符合條件，轉為 10 進位為 `1118481`，輸入後即可通過 stage1

### stage2
![](https://i.imgur.com/0qARV7l.png)

![](https://i.imgur.com/i08Hxet.png)
分別是 0x64, 0x100, 0xfaceb00c


### stage3
![](https://i.imgur.com/qhI5nU0.png)

與 0x60107c 配對

### Note
1. radare2, gdb-peda 超好用
2. pwntool 的 process 可以串 gdb

