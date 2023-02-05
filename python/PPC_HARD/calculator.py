from pwn import *

r = remote('120.114.62.215', 5119)

r.readlines(3)

for i in range(100):
    s = r.readline().decode()
    print(s)
    s = r.readline().decode()
    s = s.split()
    x = int(s[0])
    y = int(s[2])
    z = int(s[4])
    ans = ''
    if x + y == z:
        ans = '+'
    if x - y == z:
        ans = '-'
    if x * y == z:
        ans = '*'
    r.sendline(ans.encode())
r.interactive()
