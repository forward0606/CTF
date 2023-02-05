from pwn import *

r = remote("120.114.62.215", 2409)
r.readlines(5)

for i in range(100):
    s = r.readline().decode()
    print(s)
    s = r.readline().decode()
    print(s)
    s = s[7:-1]
    print(s)
    s = sorted(s)
    s.reverse()
    ans = ""
    for x in s:
        ans += x
    print(ans)
    r.sendline(ans.encode())
r.interactive()
