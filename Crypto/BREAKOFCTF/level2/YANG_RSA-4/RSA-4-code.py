#!/usr/bin/env python
from Crypto.Util import number
import libnum

p = number.getPrime(1600)
q = number.getPrime(1600)
n = p * q

e1 = number.getPrime(512)
e2 = number.getPrime(512)

m = libnum.s2n(open('flag').read().strip())

c1 = pow(m,e1,n)
c2 = pow(m,e2,n)

with open('output.txt','w') as f:
	f.write('n = %d \n\n' % n)
	f.write('e1 = %d \n\n' % e1)
	f.write('c1 = %d \n\n' % c1)
	f.write('e2 = %d \n\n' % e2)
	f.write('c2 = %d \n\n' % c2)
