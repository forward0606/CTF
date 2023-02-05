from pwn import *

r = remote('120.114.62.215', 2400)

r.readlines(7)
r.readuntil(': ')
s = r.readline().decode()
s = s.split()
print("s = ", s)

print(s)
s = [int(x) for x in s]
s = sorted(s)
print(str(s[-3]).encode())
r.sendline(str(s[-3]).encode())
r.interactive()
