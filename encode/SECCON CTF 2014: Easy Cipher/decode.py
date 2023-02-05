"""
經過觀察後發現 ascill code 由不同進位的數字呈現
且有以下規律
2 進位 : 數字的長度較長，通常大於等於 6
8 進位 : 0 開頭
16進位 : 有英文字母在裡面
10進位 : 其他的
"""

s = input().split()
ans = ""
for x in s:
    flag = True
    for i in x:
        if(i >= 'a' and i <= 'f'):
            flag = False
    if(not flag):
        ans += chr(int(x, 16))
    elif(len(x) >= 6):
        ans += chr(int(x, 2))
    elif x[0] == '0':
        ans += chr(int(x, 8))
    else:
        ans += chr(int(x))
print(ans)
