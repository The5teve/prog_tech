#!/usr/bin/env python
# -*- coding: utf-8 -*-
import codecs
import socket
import base64
import time

sock = socket.socket()
sock.bind(('', 8080))
sock.listen(1)
conn, addr = sock.accept()

print ('connected:', addr)
res=''
while True:
    data = conn.recv(4096)
    if not data:
        break
    print(data)
    b1=data
    d=base64.b64decode(b1)
    s2=d.decode("UTF-8")
    print(s2)
    #conn.send(s2.encode())
    print('---------------------------------')
    conn.send('Данные были успешно отправлены'.encode("UTF-8"))

conn.close()