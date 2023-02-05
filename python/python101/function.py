def XXX(m, n):
    while m != 0:
        if n == 0:
            n = 1
        else:
            n = XXX(m, n-1)
        m = m - 1
    return n+1

print(XXX(2, 2))
