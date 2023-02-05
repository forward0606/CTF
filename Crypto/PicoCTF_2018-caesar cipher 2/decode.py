
def shift(c, k):
    c = ord(c)
    c += k
    if c < 0:
        c += 128
    c %= 128
    return chr(c)


ciphertext = 'e^Xd8I;pX6ZhVGT8^E]:gHT_jHITVG:cITh:XJg:r'
for k in range(0, 26):
    plaintext = ""
    for x in ciphertext:
        plaintext += shift(x, k)
    print(plaintext)
