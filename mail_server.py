import zipfile
import os
import smtplib
from email.message import EmailMessage
from email.mime.text import MIMEText
from email.mime.multipart import MIMEMultipart
import zipDelete


un=open("UserNumber.txt","r")
i = int(un.read())
un.close()

print('')
print('Mail client')
print('')


z = zipfile.ZipFile('User'+ str(i) + '.zip', 'r')
z.extract('_mails.txt')

with open('_mails.txt', 'r') as f:
    nums = f.read().splitlines()
    f.close()    
print(nums)

with open('_mails.txt', 'wb'):
    pass


file_path = '_mails.txt'

zipDelete.delete_from_zip_file('User' + str(i) + '.zip', file_names=['_mails.txt']) # 

try:
    os.remove(file_path)
except OSError as e:
    print("Ошибка: %s : %s" % (file_path, e.strerror))

def send_mail():
    login = "AllSafe0@yandex.ru"
    password = "dMG-M74-HPZ-x5H"
    url = "smtp.yandex.ru"
   

    msg = EmailMessage()
    msg['Subject'] = 'You got some files!'
    msg['From'] = 'allsafe0@yandex.ru'
    # body = 'Hello it s for you'
    # msg.set_content(MIMEText(body, 'plain'))

    with open('User'+ str(i) + '.zip', 'rb') as fp:
        file_data = fp.read()
    msg.add_attachment(file_data, maintype='application', subtype='octet-stream', filename='file.zip')    # application/zip

    try:
        server = smtplib.SMTP_SSL(url, 465)
    except TimeoutError:
        print('no connect')
    server.login(login, password)
    server.sendmail(login, nums, msg.as_string())
    server.quit()
    print('connect')





def main():
    send_mail()

if __name__ == "__main__":
    main()
