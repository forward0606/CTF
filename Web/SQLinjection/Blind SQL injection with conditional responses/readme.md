https://portswigger.net/web-security/sql-injection/blind/lab-conditional-responses

cookie:
	session : OIjlLDUsYvW0Ctd82wOSfouDjgzKGy5H
	TrackingId : PMcV9o795ERp7v2S
	
更改 trackingId 可以發現當有成功 query 到資料時，頁面會有 `Welcome back!`，我們可以藉此進行盲注

curl to python: https://curlconverter.com/

實作步驟：
1. 找有幾個 parameter
2. 找有沒有字串型態
3. 用 `LENGTH(password)` 找 password 的長度
4. 用 `SUBSTR(password, 1, 1) >= 'm'` 找具體的字元

實作細節
1. MYSQL 的 STRING 是 1 base
2. MYSQL 的 SUBSTR 用法: SUBSTR(string, pow, length)
3. 註解為 ` -- ` 

