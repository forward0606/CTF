用了 strings, exiftool, binwalk 和 stegslove 都沒有找到東西
重看一次題目後發現出題者把 flag 用 base64 加密
於是把 BITSCTF 用 base64 加密後再用 grep 去找

```
forward@forward-VirtualBox:~/CTF/CTF/STEG/隱寫術102/BITSCTF 2017 : black-hole-10$ strings black_hole.jpg | grep Qkl
UQklUQ1RGe1M1IDAwMTQrODF9
```
因為開頭必為 QKl，故把 U 去掉去解密
```
>>> base64.b64decode(b'QklUQ1RGe1M1IDAwMTQrODF9')
b'BITCTF{S5 0014+81}'
```
