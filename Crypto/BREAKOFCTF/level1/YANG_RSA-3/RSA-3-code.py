from Crypto.Util import number

def getTwin(n):
	while True:
		p = number.getPrime(n)
		if number.isPrime(p+2):
			return p

p = getTwin(1650)
q = getTwin(2000)
n1 = p * q
n2 = (p+2) * (q+2)
e = 10000001
m = libnum.s2n(open('flag').read().strip())
c = pow(pow(m,e,n1),e,n2)

with open('output.txt','w') as f:
	f.write('c = %d \n\n' % c)
	f.write('e = %d \n\n' % e)
	f.write('n1 = %d \n\n' % n1)
	f.write('n2 = %d \n\n' % n2)

