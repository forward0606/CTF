from Crypto.Util.number import inverse
from Crypto.PublicKey import RSA
import libnum

def rsa_enc(m, e, n):
    return pow(m, e, n)

def rsa_dec(c, d, n):
    return pow(c, d, n)
def find_d(e, r):
    return inverse(e, r)


f = open("pub.pem", "r")
key = RSA.importKey(f.read())

print(key.n)
print(key.e)

n = 86044608266042558038553786299703811809507347936888618532703612396944160396661
e = 65537

p = 270613060120468613971049355250995010949
q = 317961772531370599800029965079161987889

r = (p-1) * (q-1)

d = find_d(e, r)

key = RSA.construct((n, e, d))
contents = ""
with open("flag.enc", 'rb') as f:
    contents = f.read()


m = key.decrypt(contents)
print(m)

