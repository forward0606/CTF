#!/usr/bin/env python3
import os
from base64 import b64encode, b64decode
from Crypto.Cipher import AES

flag = open('flag', 'rb').read()
key = os.urandom(16)

def pad(msg):
    pad_length = 16 - len(msg) % 16
    return msg + bytearray([pad_length] * pad_length)

def unpad(msg):
    byte = msg[-1]
    if msg[-byte:] == bytearray([byte] * byte) :
        return msg[:-byte]
    else :
        raise ValueError

def encrypt(data):
    iv = os.urandom(16)
    aes = AES.new(key, AES.MODE_CBC, iv = iv)
    return iv + aes.encrypt(pad(data))

def decrypt(data):
    iv, data = data[:16], data[16:]
    aes = AES.new(key, AES.MODE_CBC, iv = iv)
    return unpad(aes.decrypt(data))

print(b64encode(encrypt(flag)).decode())

while True :
    cipher = b64decode(input("your cipher : ").strip())
    try :
        m = decrypt(cipher)
        print("OK!")
    except ValueError :
        print("Padding Error!")
    except Exception as e:
        print("Whoops...")
        break