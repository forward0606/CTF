from pwn import *

r = remote('120.114.62.215', 2406)

r.readlines(14)
for i in range(0, 100):
    s = r.readline()
    print(s)
    s = r.readline()
    s = s.decode()
    s = s[5:]
    print("s = ", s)
    if(len(s) != 11):
        r.sendline("invalid".encode())
        print("1invalid")
        continue
    flag = True;
    for i in range(1, 10):
        if(s[i] < '0' or s[i] > '9'):
            flag = False
            r.sendline("invalid".encode())
            print("2invalid")
            break
    
    if(not flag):
        continue
    x = int(s[1:10])
    if(not(s[0] >= 'A' and s[0] <= 'Z')):
        flag = False
    if(x % 3 != 0):
        flag = False
    if(flag):
        r.sendline("valid".encode())
        print("3valid")
    else:
        r.sendline("invalid".encode())
        print("4invalid")
    #s = r.readline()
    #print(s.decode())

r.interactive()
