import rsa

n = 66473473500165594946611690873482355823120606837537154371392262259669981906291
e = 65537
PUBKEY = rsa.PublicKey(n, e)

p = 800644567978575682363895000391634967
q = 83024947846700869393771322159348359271173
r = (p - 1) * (q - 1)

d = inverse(e, r)

def encrypt(s, pubkey):
	return rsa.encrypt( s, pubkey )

def decrypt(s, prikey):
    return rsa.

if __name__ == '__main__':
	with open('flag.txt', 'r') as fp:
		flag = fp.read()
	
	with open('flag.enc', 'w') as fp:
		fp.write( encrypt(flag, PUBKEY) )
