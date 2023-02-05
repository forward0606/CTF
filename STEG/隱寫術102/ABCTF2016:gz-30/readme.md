
use `file` to check the file type

```
forward@forward-VirtualBox:~/CTF/CTF/STEG/隱寫術102/ABCTF2016:gz-30$ file flag 
flag: gzip compressed data, was "flag", last modified: Sun Jun 26 17:22:38 2016, from Unix, original size modulo 2^32 21
```
發現是 gzip 檔，把它改檔名並解壓縮
```
forward@forward-VirtualBox:~/CTF/CTF/STEG/隱寫術102/ABCTF2016:gz-30$ mv flag flag.gz
forward@forward-VirtualBox:~/CTF/CTF/STEG/隱寫術102/ABCTF2016:gz-30$ gunzip flag.gz 
forward@forward-VirtualBox:~/CTF/CTF/STEG/隱寫術102/ABCTF2016:gz-30$ ls
flag
forward@forward-VirtualBox:~/CTF/CTF/STEG/隱寫術102/ABCTF2016:gz-30$ file flag 
flag: ASCII text
```
變成一個 ASCII 的文字檔了，flag 在文字檔中
