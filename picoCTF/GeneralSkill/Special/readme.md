https://play.picoctf.org/events/72/challenges/challenge/377?category=5&page=1

連進去後發現在 Special 中 ls 不能用，所以我們要想辦法去用其他的 shell，像是 bash

一般而言只要輸入 `bash` 就能夠使用 bash shell
但實際輸入後會發現它會偵測 `sh` 並把我們的輸入擋掉
並且會把所有的輸入的第一個字元改成大寫
所以要想辦法跳脫，嘗試了許久後，發現可以用 "" 來跳脫

payload:
```
"ba"s"h
```
