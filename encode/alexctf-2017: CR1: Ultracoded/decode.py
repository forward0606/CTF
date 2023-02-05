"""
ciphertext 中有一堆 ZERO 與 ONE 先處理成 0 和 1 
每 7 個為一組，轉成 ascii
"""
import base64
import morse_talk as mtalk
s = input().split()

ans = ''
for x in s:
    if x == "ONE":
        ans += '1'
    elif x == "ZERO":
        ans += '0'
    else:
        print("another thing : '", x, "'.")

s = "" 
for i in range(0, len(ans), 8):
    s += chr(int(ans[i: i+8], 2))

print(s)                #Li0gLi0uLiAuIC0uLi0gLS4tLiAtIC4uLS4gLSAuLi4uIC4tLS0tIC4uLi4uIC0tLSAuLS0tLSAuLi4gLS0tIC4uLi4uIC4uLSAuLS0uIC4uLi0tIC4tLiAtLS0gLi4uLi4gLiAtLi0uIC4tLiAuLi4tLSAtIC0tLSAtIC0uLi0gLQ==
print(len(s))

s = base64.b64decode(s)
s = s.decode()
print(s)                #.- .-.. . -..- -.-. - ..-. - .... .---- ..... --- .---- ... --- ..... ..- .--. ...-- .-. --- ..... . -.-. .-. ...-- - --- - -..- -
s = mtalk.decode(s)
print(s)                #ALEXCTFTH15O1SO5UP3RO5ECR3TOTXT


# convert O to _, and add {}
ans = "ALEXCTF{TH15_1S_5UP3R_5ECR3T_TXT}"
print(ans)
