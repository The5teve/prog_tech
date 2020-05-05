import socket
import base64
sock = socket.socket()
sock.connect(('192.168.0.101', 9090))
##################################
#handle = open("txt.txt","r") 
#data = handle.read()
#handle.close()
with open("D:/AllSafe/txt.txt", 'rb') as file:
	sock.send(base64.encodebytes(file.read()).decode('utf-8').encode())
	#sock.send(file.read())
#################################
#sock.send(data.encode())

s2 = sock.recv(2048)
print(s2)
sock.close()
