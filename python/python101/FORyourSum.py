from pwn import *


sum = 0;
for i in range(1, 7):
    for j in range(1, 7):
        #if i == j:
            #continue
        for k in range(1, 7):
            #if i == k or j == k:
            #continue
            print(i, j, k)
            sum += i * 100 + j * 10 + k

print(sum)
