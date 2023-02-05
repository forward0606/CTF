
def decode(c):
    x = ord(c) - ord('a')
    if c == '_':
        x = 26
    x -= 15
    x = x * 7
    x = x % 27
    if x < 0:
        x += 27
    ret = chr(x + ord('a'))
    if(x == 26):
        ret = '_'
    return ret

ciphertext = 'ifpmluglesecdlqp_rclfrseljpkq'
plaintext = ""
for x in ciphertext:
    plaintext += decode(x)
print(plaintext)

