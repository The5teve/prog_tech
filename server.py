import socket
import base64
import time
import zipfile
import os
import RSA_1
from subprocess import Popen


un=open("UserNumber.txt","r")   #открываем .txt, в котором ведём учёт  User и кладём в переменную i число
i = int(un.read())
un.close()

counter = 0

while True:

    sock = socket.socket()  #создаём сокет
    sock.setsockopt(socket.SOL_SOCKET, socket.SO_REUSEADDR, 1) #потому что на Ubuntu 3 минуты "отдышка" после передачи по сокетам
    sock.bind(('', 8008)) #хост и порт, хост оставляем пустым, чтобы каждый мог подключиться
    sock.listen(1) #режим прослушивания, принимает один аргумент
    conn, addr = sock.accept() #принимает подключение с помощью метода accept, которой возвращает новый сокет и адрес клиента

    print ('connected:', addr) #ip подключенного клиента

    numfile = conn.recv(10) #читаем порциями по 10 байт, узнаём сколько будем файлов
    numfile = numfile.decode("UTF-8") #переводим из байтов в кодировку UTF-8
    res = b'' #переменная, в которую будут склдываться порции байтов, из которых будут декодироваться файлы
    i+=1 #счётчик User
    new_zip = zipfile.ZipFile('User' + str(i) + '.zip', 'a') #создаём архив с номеро User
    for j in range(int(numfile)): #делаем столько циклов, сколько файлов получили
        e, d, n = RSA_1.generateKeys(keysize=32)
        conn.send(str(e).encode()+b'_'+str(n).encode())
        file_name = conn.recv(1024) #читаем порциями по 1024 байт имя файла
        print('')
        print('File ' + str(j+1) + ' is decoding...') 
        res = b'' #обнуляем переменную, где будет лежать файл в байтовом представлении
        while True:
            #print(file_name)
            data = conn.recv(4096) #читаем байты файлов, из которых позже получим файл
            if data == b'stop': #если прочитал stop, работа с файлом закончена, переходи к следующему
                break 
            if not data: #если не пришло ничего, no data
                print('no data')
                break
            # with open(file_name.decode("UTF-8"), 'ab') as file:
            #     file+=data
            res+=data #суммируем все байты в переменную res

        with open(file_name, 'wb') as f1:
            f1.write(res) #записываем в файл полученные байты
        file_name = file_name.decode("UTF-8") # раскодируем имя файла и запишем в кодировке UTF-8
        if (file_name == '_mails.txt'): #если нашло файл с именем _mails.txt:
            #print(file_name + ' первый принт')
            RSA_1.decrypt_file(file_name, d, n) #декодируем по библиотеке RSA_1 файл
            if os.path.exists('_mails.txt'):
                new_zip.write('_mails.txt') 
            print("File received and recreated")
            os.remove('_mails.txt') #удаляем файл из директории, оставляем только в архиве

        else:
            #print(file_name + ' элсе')
            RSA_1.decrypt_file(file_name, d, n) #декодируем по библиотеке RSA_1 файл
            ext = file_name[file_name.find('.'): len(file_name)]
            counter = counter + 1
            os.rename(file_name, 'File' + str(counter) + ext)
            if os.path.exists('File' + str(counter) + ext):
                new_zip.write('File' + str(counter) + ext)
            print("File received and recreated")
            #new_zip.write(file_name) 
            os.remove('File' + str(counter) + ext) 

        file_name = b''
        res = b''

    print('')
    print("All files were successfully received!")
    
    un = open("UserNumber.txt", "w")
    un.write(str(i))
    un.close()

    new_zip.close()
    conn.close()
    
    counter = 0
    
    os.system('python mail_server.py') #os.system('python3.8 mail_server.py') 
