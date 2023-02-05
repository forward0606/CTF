

month = [31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31]
y = int(input())
m = int(input())
d = int(input())
ans = d
for i in range(0, m-1):
    ans += month[i]
if (y % 4 == 0 and y % 100 != 0) or y % 400 == 0:
    ans += 1
print(ans)
