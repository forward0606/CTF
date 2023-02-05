
用 binwalk 發現有兩張圖片，再用 dd 去把圖片拉出來
```
forward@forward-VirtualBox:~/CTF/CTF/STEG/隱寫術102/steg_3:Embedded$ binwalk PurpleThing.png 

DECIMAL       HEXADECIMAL     DESCRIPTION
--------------------------------------------------------------------------------
0             0x0             PNG image, 3200 x 2953, 8-bit/color RGBA, non-interlaced
85            0x55            Zlib compressed data, best compression
2757          0xAC5           Zlib compressed data, best compression
765455        0xBAE0F         JPEG image data, JFIF standard 1.01
765485        0xBAE2D         TIFF image data, big-endian, offset of first image directory: 8
1809691       0x1B9D1B        StuffIt Deluxe Segment (data): f

forward@forward-VirtualBox:~/CTF/CTF/STEG/隱寫術102/steg_3:Embedded$ dd if=PurpleThing.png of=flag.jpg skip=765455 bs=1
1588801+0 records in
1588801+0 records out
1588801 bytes (1.6 MB, 1.5 MiB) copied, 1.53279 s, 1.0 MB/s

```

ans:`ABCTF{PNG_S0_C00l}`
