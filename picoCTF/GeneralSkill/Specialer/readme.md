觀察了一下發現 `ls`, `cat` 不能用並且 `cd`, `pwd`, `echo` 可以用

google 不用 ls 的方法： https://ostechnix.com/different-ways-to-list-directory-contents-without-using-ls-command/
發現可以用 echo 取代

並且也可以用 echo 來當 `cat`: https://stackoverflow.com/questions/22377792/how-to-use-echo-command-to-print-out-content-of-a-text-file

payload
```
echo * .*
echo "$(<a.txt )"
```
