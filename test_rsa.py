import socket
import base64
import time
import zipfile
import os
import RSA_1

un=open("UserNumber.txt","r")
i = int(un.read())
un.close()

while True:

    sock = socket.socket()
    #sock.setsockopt(socket.SOL_SOCKET, socket.SO_REUSEADDR, 1) #потому что на Ubuntu 3 минуты "отдышка" после передачи по сокетам
    sock.bind(('', 8008))
    sock.listen(1)
    conn, addr = sock.accept()

    print ('connected:', addr)

    numfile = conn.recv(10)
    numfile = numfile.decode("UTF-8")
    res = b''
    i+=1
    new_zip = zipfile.ZipFile('User' + str(i) + '.zip', 'a')
    for j in range(int(numfile)):
        file_name = conn.recv(1024)
        print('')
        print('File ' + str(j+1) + ' is decoding...')
        res = b''
        while True:
            #print(file_name)
            data = conn.recv(4096)
            if data == b'stop':
                break
            if not data:
                print('no data')
                break
            # with open(file_name.decode("UTF-8"), 'ab') as file:
            #     file+=data
            res+=data

        with open(file_name, 'wb') as f1:
            f1.write(res)
        file_name = file_name.decode("UTF-8")
        if file_name.find('_mails.txt')>1:
            #print(file_name + ' первый принт')
            RSA_1.decrypt_file(file_name, int(file_name[0: file_name.find('_')]), int(file_name[file_name.find('_') + 1: file_name.find('_mails.txt')]))
            os.rename(file_name, '_mails.txt')
            if os.path.exists('_mails.txt'):
                new_zip.write('_mails.txt')
            print("File received and recreated")
            os.remove('_mails.txt')

        else:
            #print(file_name + ' элсе')
            RSA_1.decrypt_file(file_name, int(file_name[0: file_name.find('_')]), int(file_name[file_name.find('_') + 1: file_name.find('.')]))
            print("File received and recreated")
            new_zip.write(file_name)
            os.remove(file_name)

        file_name = b''
        res = b''

    print('')
    print("All files were successfully received!")
    un = open("UserNumber.txt", "w")
    un.write(str(i))
    un.close()

    new_zip.close()
    conn.close()

