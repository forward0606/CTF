使用 binwalk

## 安裝
```
sudo apt install binwalk
```
## usage
```
forward@forward-VirtualBox:~/Downloads$ binwalk carter.jpg 

DECIMAL       HEXADECIMAL     DESCRIPTION
--------------------------------------------------------------------------------
0             0x0             JPEG image data, JFIF standard 1.01
382           0x17E           Copyright string: "Copyright (c) 1998 Hewlett-Packard Company"
3192          0xC78           TIFF image data, big-endian, offset of first image directory: 8
140147        0x22373         JPEG image data, JFIF standard 1.01
140177        0x22391         TIFF image data, big-endian, offset of first image directory: 8
```
發現有兩張圖片
用 dd 把另一張圖片拉出來

```
dd if=carter.jpg of=flag.jpg skip=140147 bs=1
```

flag 就藏在圖片中
