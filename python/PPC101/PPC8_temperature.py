"""
===== Welcome =====
I need you to transform from Fahrenheit to Celsius
----- wave : example -----
Fahrenheit : 10 (guarantee to be integer)
Celsius : -110/9
----- wave : 1/100 -----
Fahrenheit : 29
"""

from pwn import *

r = remote('120.114.62.215', 5127)
