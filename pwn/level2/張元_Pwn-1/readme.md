## Stack overflow
```
Stack:

char *		buf	rbp-0x20	0x4
int		var_1ch	rbp-0x1c	0x4
time_t		seed	rbp-0x18	0x4
int		var_14h	rbp-0x14	0x4	--> var_14h = 0xfaceb00c
int		var_10h	rbp-0x10	0x4	--> var_10h = deadbeef
int 		var_ch	rbp-0xc		0x4	--> random value --> password = random_value
long long	var_8h	rbp-0x8 	0x8
			rbp
		return address
```
