
s = "ERTKSOOTCMCHYRAFYLIPL"
ans = ""
for i in range(0, len(s), 3):
    ans += s[i]

for i in range(1, len(s), 3):
    ans += s[i]
for i in range(2, len(s), 3):
    ans += s[i]

print(ans)
