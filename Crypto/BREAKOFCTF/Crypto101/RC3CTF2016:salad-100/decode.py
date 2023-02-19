def myord(s, c):
    for i in range(0, len(s)):
        if s[i] == c:
            return i
    return -1
def shift(s, c, k):
    x = myord(s, c)
    if x == -1:
        return c
    x += k
    if x < 0:
        x += len(s)
    x %= len(s)
    return s[x]


ciphertext = '7sj-ighm-742q3w4t'
            # RC3-2016-R
s =  "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
s += "abcdefghijklmnopqrstuvwxyz"
s += "0123456789"
for k in range(0, 128):
    plaintext = ""
    for x in ciphertext:
        plaintext += shift(s, x, k)
    if plaintext[0] == 'R':
        print(plaintext)
