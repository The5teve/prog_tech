#!/usr/bin/env python
# -*- coding: utf-8 -*-
import codecs
import socket
import base64
import time
import zipfile
import os

un=open("UserNumber.txt","r")
i = int(un.read())
un.close()

while True:

    sock = socket.socket()
    sock.setsockopt(socket.SOL_SOCKET, socket.SO_REUSEADDR, 1) #потому что на Ubuntu 3 минуты "отдышка" после передачи по сокетам
    sock.bind(('', 8008)) #8080
    sock.listen(1)
    conn, addr = sock.accept()

    print ('connected:', addr)




    numfile = conn.recv(10)
    numfile = numfile.decode("UTF-8")
    res=b''
    i+=1
    new_zip = zipfile.ZipFile('User' + str(i), 'a')
    for j in range(int(numfile)):
        file_name = conn.recv(1024)
        print(file_name)
        res = b''
        while True:
            #print(file_name)
            data = conn.recv(4096)
            if data==b'stop':
                break
            if not data:
                print('no data')
                break
            res += data


        res = base64.b64decode(res)

        g = open(file_name, "wb")
        g.write(res)
        g.close()
        print("File received and recreated")

        new_zip.write(file_name.decode("UTF-8"))

        #path = os.path.join(os.path.abspath(os.path.dirname(__file__)), file_name.decode("UTF-8"))
        os.remove(file_name)

        file_name=b''
        res = b''

    un = open("UserNumber.txt", "w")
    un.write(str(i))
    un.close()

    new_zip.close()
    conn.close()