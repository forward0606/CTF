from pwn import *

r = remote('120.114.62.215', 2408)

r.readlines(8)

s = r.readline().decode()
#s = input()
t = r.readline().decode()
s += t
s = s.split()
s = [int(x) for x in s]
s = set(s)
s = sorted(s)
ans = ""
for x in s:
    ans += str(x)
    ans += " "
ans = ans[:-1]
r.sendline(ans.encode())
print(ans.encode())
r.interactive()
