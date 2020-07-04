import os
from tkinter import * 
import tkinter.ttk as ttk
from tkinter import filedialog
from tkinter import messagebox
from PIL import ImageTk,Image
import socket
import base64
import time
import RSA12
import shutil
import plyer
root = Tk()
root.geometry("800x450")
root.title("Allsafe")
root.resizable(False, False)
root.iconbitmap('img/allsafe.ico')
root.eval('tk::PlaceWindow %s center' % root.winfo_pathname(root.winfo_id()))
with open('_mails.txt', 'w', encoding='utf-8') as g:
	g.write("")
SetPaths= set()
SetMails= set()
class Colors:
	dark="#191919"
	halfdark="#202020"
	grey="#B3B3B3"
class Phrase:
	help1="The program (desktop / mobile) sends a request to the server.\n Before sending a request It receives a response from the \n server with the public key, encrypts the files using the\nlibrary, and the key sends them to the server along\n with the recipients (lists by e-mail)"
	help2="The file server generates responses to sender requests, generates a\n key pair, sends the public key to the user, receives \nfiles, decrypts and generates download links"
	help3="The mail server takes all address and address files, sends\n files. The recipient opens\n the mail, clicks the files, downloads the file"
	expression = "Allsafe is a free program for the safe transfer of mail letters. "
style = ttk.Style()
style.theme_use("clam")
style.configure("Treeview", 
                fieldbackground="#1b2c45", foreground="black", borderwidth=0)
style.configure("Treeview.Heading", 
                fieldbackground="red", foreground="black", backgroud="red", borderwidth=0)

currIp="45.137.190.159"
currPort=9009
def main_def_send_mail():
	global SetPaths
	global currIp
	global currPort
	sentmsg = Label(sec0nd_frame, text='Please Wait', bg=Colors.halfdark, fg="lightgrey")
	sentmsg.place(x=200,y=170)

	#############################
	sock = socket.socket()

	try: 
		sock.connect((currIp, currPort))
	except ConnectionRefusedError:
		messagebox.showerror("Error", "Server is not avaliable.")
		return
	show_sec0nd_frame()
	
	sock.send(str(len(SetPaths)).encode())
	stopmsg=b'stop'
	progress = ttk.Progressbar(sec0nd_frame, orient = HORIZONTAL, 
              length =200, mode = 'determinate') 
	progress.place(x=200,y=195)
	for path in SetPaths:
		root.update()
		# путь к файлу
		fileKeys = sock.recv(512)
		e, n =int(str(fileKeys.decode()).split("_")[0]), int(str(fileKeys.decode()).split("_")[1])
		if os.path.basename(path)!="_mails.txt":
			shutil.copy(path,os.getcwd())
		RSA12.encrypt_file(os.path.basename(path), e, n)
		root.update()
		os.remove(os.path.basename(path))
		with open("enc_"+os.path.basename(path), 'rb') as file:

			#print ("Sending "+path)
			if os.path.basename(path)=="_mails.txt":
				print("[+]Sending info about mails...")
				sock.send(os.path.basename(path).encode())
				print("[+]Successfuly.Preparing next file... ")
			else:	
				print("[+]Sending "+os.path.basename(path)+"...")
				sock.send(os.path.basename(path).encode())
				print("[+]File successfuly sent. Preparing next file...")
			if os.path.basename(path)=="_mails.txt":	
				sentmsg.config(text="Sending "+"info about mails"+"...")
			else:
				sentmsg.config(text="Sending "+str(os.path.basename(path))+"...")
			time.sleep(1)
			sock.send(file.read())
			root.update()
			time.sleep(10)
			sock.send(stopmsg)
			root.update()
			time.sleep(10)
			progress['value'] += 100/len(SetPaths)
		os.remove("enc_"+os.path.basename(path))
	sentmsg.config(text=str(len(SetPaths)-1)+ " file(s) have been sent!")

	root.update()
	print("[+] "+str(len(SetPaths)-1)+ " file(s) have been sent!")
	progress.grid_forget()
	sock.close()
	plyer.notification.notify(message='Your files were sent successfully',app_name='Allsafe',title='Allsafe', )
	root.after(100, root.quit())
###CHOOSE FILE###

def change_ip():
	global iplace
	global portplace
	changeIp = Toplevel()
	changeIp.grab_set()
	changeIp.focus_set()
	changeIp['bg']="#191919"
	changeIp.geometry("200x100")
	changeIp.title("Change Ip")
	changeIp.resizable(False, False)
	iplace= Entry(changeIp,width=15,bg="lightgrey", fg="grey", borderwidth=0)
	iplace.place(x=20, y=40)
	ipLabel=Label(changeIp, bg=Colors.dark, borderwidth=0, fg="#333333",
	text="enter Ip here.")		
	ipLabel.place(x=20,y=20)	
	portplace= Entry(changeIp,width=5,bg="lightgrey", fg="grey", borderwidth=0)
	portplace.place(x=150, y=40)
	hostLabel=Label(changeIp, bg=Colors.dark, borderwidth=0, fg="#333333",
	text="port.")		
	hostLabel.place(x=150,y=20)
	changeIpBtn=Button(changeIp,text="Change",font=("Arial Bold", 13),bg=Colors.dark, borderwidth=0, fg="grey", command=check_ip)
	changeIpBtn.place(x=65,y=60)
	changeIp.mainloop()

def check_ip():
	global iplace
	global portplace
	global currIp
	global currPort
	ipvalue="1234567890."
	ip = iplace.get()
	port = portplace.get()
	for char in ip:
		if char not in ipvalue:
			messagebox.showerror("Error", "Invalid parameters.")
			return
	for char in port:
		if char not in ipvalue:
			messagebox.showerror("Error", "Invalid parameters.")
			return
	if  ip.count(".")==3 and len(port)==4 and len(ip):
		currIp=ip
		currPort=port
		messagebox.showwarning("Success", "Ip has been changed. This may cause server unavailability. Reload the program to return to the original values")
	else:
		messagebox.showerror("Error", "Invalid parameters.")



	return
def choose_file():
	global tree1
	global file_path
	global SetPaths
	global Button_send
	global SetMails
	alphabet= 'abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890-_.@~@#$%^-_(){}`'
	file_path =  filedialog.askopenfilename(initialdir = "/",
											title = "Select file", filetypes = (("png files","*.png"),("jpeg files","*.jpg"),("txt files","*.txt")))
	if len(file_path)>3 and file_path not in SetPaths  :
		for char in os.path.split(file_path)[1]:
			if char not in alphabet:
				messagebox.showerror("Error", "Bad filename. Support only latin letters.")
				return
		tree1.insert("",0, text=os.path.split(file_path)[1], values=(str(os.path.getsize(file_path)/1024//1)+"KB"))
		SetPaths.add(file_path)
		if SetMails:
			Button_send.config(state=NORMAL, bg="lightgrey")





###HOW THAT WORKS###
def place_help_text(number):

	
	if number == 1:
		myHelp1=Label(helps, bg=Colors.grey, borderwidth=0, fg="#333333",
		text=Phrase.help1)		
		myHelp1.place(x=10,y=25)	

	elif number == 2:
		myHelp2=Label(helps,bg=Colors.grey, borderwidth=0, fg="#333333", 
		text=Phrase.help2)
		myHelp2.place(x=10,y=200)

	else:
		myHelp3=Label(helps,bg=Colors.grey, borderwidth=0, fg="#333333", 
		text=Phrase.help3)
		myHelp3.place(x=10,y=350)
	


###HOW THAT WORKS2###
def show_help():
    global helps
    helps = Toplevel()
    helps['bg'] = Colors.grey
    helps.geometry("660x450")
    helps.resizable(False, False)
    #Label(a, text="About this").pack(expand=1)
    helps.grab_set()
    helps.focus_set()
    imghelp = ImageTk.PhotoImage(Image.open("img/hiw.png"))

    LabelHelp = Label(helps,image=imghelp, borderwidth=0)
    LabelHelp.place(x=350,y=0)

    helpbutton1=Button(helps,text="What\n is it?",font=("Arial Bold", 13),
    				 bg=Colors.grey, borderwidth=0, fg="grey", command=lambda: place_help_text(1))
    helpbutton1.place(x=600,y=50)

    helpbutton2=Button(helps,text="What\n is it?",font=("Arial Bold", 13),
    				 bg=Colors.grey, borderwidth=0, fg="grey", command=lambda: place_help_text(2))
    helpbutton2.place(x=600,y=200)

    helpbutton3=Button(helps,text="What\n is it?",font=("Arial Bold", 13),
    				 bg=Colors.grey, borderwidth=0, fg="grey", command=lambda: place_help_text(3))
    helpbutton3.place(x=600,y=350)
	
    helps.mainloop()
    

def show_f1rst_frame():
	global f1rst_frame
	global sec0nd_frame
	global my_frame1
	my_frame1.grid_forget()
	sec0nd_frame.grid_forget()
	f1rst_frame.grid(row=0, column=1)

def show_sec0nd_frame():
	global f1rst_frame
	global sec0nd_frame
	global my_frame1
	my_frame1.grid_forget()
	f1rst_frame.grid_forget()

	sec0nd_frame.grid(row=0, column=1)

def button_home():
	global f1rst_frame
	global sec0nd_frame
	global my_frame1
	f1rst_frame.grid_forget()
	sec0nd_frame.grid_forget()
	my_frame1.grid(row=0, column=1)
def show_send_letter():
	return
#Set for mails

def valid_check():
	global emailplace
	global tree
	global SetMails
	alphabet= 'abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890-_.@'
	temp=emailplace.get()
	for char in temp:
		if char not in alphabet:
			messagebox.showerror("Error", "Wrong email.")
			return False
	if  temp.find("@")==-1 or  temp.find(".")==-1:
		messagebox.showerror("Error", "Wrong mail type.")
		return False
	else:	
		if temp.find("@") and temp.find(".") and temp.index("@")+1!=temp.index(".") and temp.index(".")!=len(temp)-1  and temp.count("@")==1:
			if temp not in SetMails:
				tree.insert("",0, text=temp)
				SetMails.add(temp)
				filemail = open('_mails.txt', "a")
				filemail.write(temp+"\n")
				filemail.close()
				SetPaths.add(os.getcwd()+"\\" "_mails.txt")
				emailplace.delete(0, END)
			else:
				messagebox.showwarning("Warning", "You already add this email")
				emailplace.delete(0, END)
		else:
			messagebox.showerror("Error", "Wrong email.")		
	if SetPaths:
		Button_send.config(state=NORMAL, bg="lightgrey")

helps = Label()
#Frames 
my_frame = Frame(root, bg=Colors.dark, width=150, height=450)
my_frame.grid(row=0, column=0, rowspan=10)
my_frame1 = Frame(root, bg=Colors.halfdark, width=650, height=450)
my_frame1.grid(row=0, column=1)


#Allsafe Title
status = Button(my_frame, text="Allsafe Build 0.1.9", bg=Colors.dark, borderwidth=0,
				 fg="#104981", font=("Arial Bold", 10), command=button_home)
status.place(x=15,y=10)
#How it works

helps = Button(my_frame, text="How it works?", bg="#181818", 
				fg="#959595", borderwidth=0, command=show_help)
helps.place(x=33, y=400)

#News Label
newslabel = Label(my_frame1, text="Allsafe", bg=Colors.halfdark,
				 fg="#171717", font=("Arial Bold", 90))
newslabel.place(x=270, y=150)
newslabel1 = Label(my_frame1, text="cyberletters", bg=Colors.halfdark, 
					fg="#171717", font=("Arial Bold", 30))
newslabel1.place(x=370, y=260)

#Allsafe logo
logo = ImageTk.PhotoImage(Image.open("img/logo.png"))
logoLabel = Label(my_frame1,image=logo, borderwidth=0)
logoLabel.place(x=100,y=150)

#Buttons


#Switch Buttons
button_switch1 = Button(my_frame, text="Let's send something!",bg="#181818",
						 fg="lightgrey", borderwidth=0, command=show_f1rst_frame)
button_switch1.place(x=20, y=100)

button_switch2 = Button(my_frame, text="change ip", bg="#181818",
						 fg="lightgrey", borderwidth=0, command=change_ip)
button_switch2.place(x=20, y=130)


#switching frames
f1rst_frame = Frame(root, bg=Colors.halfdark, width=650, height=450)
subframe = Frame(f1rst_frame, width=610, height=310,
				 bg="#1b2c45",borderwidth=2, relief="ridge")
subframe.place(x=20, y=60)

logosend = ImageTk.PhotoImage(Image.open("img/logosend.png"))
logoSendL = Label(subframe,image=logosend, borderwidth=0)
logoSendL.place(x=220,y=80)

button_addfile = Button(subframe, padx=50, pady=10, text="Choose File",
						 bg="#282828", fg="lightgrey", command=choose_file, borderwidth=0)
button_addfile.place(x=10,y=80)

Sending1= Label(subframe, text="Enter email here", borderwidth=0, bg="#1b2c45", fg="#666464")
Sending1.place(x=30, y=30)
#Mail pole
emailplace= Entry(subframe,width=45,bg="lightgrey", fg="grey", borderwidth=0)
emailplace.place(x=10, y=10)

#addmail Button
addmail=Button(subframe, text="add mail", bg="#1b2c45", 
				fg="lightgrey", pady=1, command=valid_check, borderwidth=0)
addmail.place(x=295, y=9)

#MAILS TREE
tree=ttk.Treeview(subframe, height=6)
tree.column("#0",stretch="NO")
tree.heading("#0",text="e-mails",anchor=W)
#tree.insert("",1, text="your mail here") #for inserting
tree.place(x=400, y=5)

tree1=ttk.Treeview(subframe, height=6)
tree1["columns"]=("one")
tree1.column("#0",stretch="NO", width=145)
tree1.heading("#0",text="chosen files",anchor=W)
tree1.column("one",stretch="NO", width=55)
tree1.heading("one",text=" ", anchor=W)
#tree.insert("",1, text="your mail here") #for inserting
tree1.place(x=400, y=155)

Sending2= Label(subframe, text="Choose files yow want to send",
				 borderwidth=0, bg="#1b2c45", fg="#666464")
Sending2.place(x=10, y=130)
Button_send = Button(subframe,text="Send", padx=30, pady=5,font=("Impact", 11),
					 bg="grey",state=DISABLED, command=main_def_send_mail, borderwidth=0)
Button_send.place(x=8, y=255)

sec0nd_frame = Frame(root, bg=Colors.halfdark, width=650, height=450)
WarningLabel = Label(sec0nd_frame, bg=Colors.halfdark,
					 text=Phrase.expression, fg="lightgrey") 
WarningLabel.place(x=300, y=400)
#Some things

root.mainloop()