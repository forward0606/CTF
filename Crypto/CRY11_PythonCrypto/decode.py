

ciphertext = 'cvqBeqacRtqazEigwiAobxrKobxrAobxrLwgk8Lwgk8CrtuiTzahfFreqc{bnjrZwgk8Ikgd4Pj85ePgb_e_rwqr7fvbmHjklo3tews_hmkogooyf0vbnk0ii87Drfgh_n kiwutfb0ghk9ro987k5tfb_hjiouo087ptfcv}'
ans=""
for i in range(3, len(ciphertext), 5):
    ans += ciphertext[i]
print(ans)
