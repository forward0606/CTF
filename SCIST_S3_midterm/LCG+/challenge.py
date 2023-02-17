from pwn import *
import sys

from Crypto.Util.number import *


r = remote('lab.scist.org', 10302)

r.recvuntil(":")
r.sendline("challenge".encode())
arr = []
for i in range(16):
    number = (1<<64) + i
    r.sendline(str(number).encode())
    s = r.readline()
    print("nonce = ", number)
    s = s.decode()
    t = ""
    for x in s:
        if x.isdigit():
            t += x
    arr.append(int(t))
print(arr)
a3 = (arr[1] - arr[0])
c2 = arr[0] - (a3 * (1<<64))
ans = c2 * inverse(a3, m)
print("a = ", a)
print("a is prime number:", isPrime(round(a)))
r.interactive()

