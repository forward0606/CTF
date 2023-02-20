
https://crypto.stackexchange.com/questions/16283/how-to-use-common-modulus-attack

根據貝祖定理，當 $gcd(e_1, e_2) = 1$ 時必可以找到一組 $s_1, s_2$ 使的 $e_1s_1 + e_2s_2 = 1$

根據題目我們有

$c_1 \equiv m^{e_1} (\mod n)$
$c_2 \equiv m^{e_2} (\mod n)$

$c_1^{s_1}c_2^{s_2} = m^{e_1s_1}m^{e_2s_2} = m^{e_1s_1 + e_2s_2} = m$
