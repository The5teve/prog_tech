import os
from tkinter import * 
import tkinter.ttk as ttk
from tkinter import filedialog
from tkinter import messagebox
from PIL import ImageTk,Image
import socket
import base64


root = Tk()
root.geometry("800x450")
root.title("Allsafe")
root.resizable(False, False)
root.iconbitmap('img/allsafe.ico')



style = ttk.Style()
style.theme_use("clam")
style.configure("Treeview", 
                fieldbackground="#1b2c45", foreground="black", borderwidth=0)
style.configure("Treeview.Heading", 
                fieldbackground="red", foreground="black", backgroud="red", borderwidth=0)

def main_def_send_mail():
	global SetPaths
	sends = Toplevel()
	sends['bg']='#202020'
	sends.geometry("450x200")
	sends.resizable(False, False)
	RANDOM = Label(sends, text='Please wait...', bg="#202020", fg="white")
	RANDOM.place(x=190,y=30)
	pb = ttk.Progressbar(sends, length=250)
	pb.place(x=100, y=50)
	pb.start(200)
	#############################
	sock = socket.socket()
	sock.connect(('25.84.180.124', 8080))                             #####  ПОМЕНЯТЬ IP, IP ТУТ ОТ ХАМАЧИ, РАЗНЫЕ СЕТИ ДЛЯ ПЕРЕДАЧИ
	##################################
	#handle = open("txt.txt","r") 
	#data = handle.read()
	#handle.close()
	for path in SetPaths:
		with open(path, 'rb') as file:
			print (path)
			sock.send(base64.encodebytes(file.read()).decode('utf-8').encode())

	#sock.send(file.read())
	#################################
	#sock.send(data.encode())
	s2 = sock.recv(4096)
	print(s2.decode("UTF-8"))
	sock.close()
	#############################
	sends.mainloop()


###CHOOSE FILE###
SetPaths= set()
def choose_file():
	global tree1
	global file_path
	global SetPaths
	global Button_send
	global SetMails
	file_path =  filedialog.askopenfilename(initialdir = "/",
											title = "Select file", filetypes = (("all files","*.*"),("jpeg files","*.jpg")))
	if len(file_path)>3:
		tree1.insert("",0, text=os.path.split(file_path)[1], values=(str(os.path.getsize(file_path)/1024//1)+"KB"))
		SetPaths.add(file_path)
		if SetMails:
			Button_send.config(state=NORMAL, bg="lightgrey")




###HOW THAT WORKS###
def place_help_text(number):
	print(os.urandom(3))
	
	if number == 1:
		myHelp1=Label(helps, bg="#B3B3B3", borderwidth=0, fg="#333333",
		text="The program (desktop / mobile) sends a request to the server.\n Before sending a request It receives a response from the \n server with the public key, encrypts the files using the\nlibrary, and the key sends them to the server along\n with the recipients (lists by e-mail)")		
		myHelp1.place(x=10,y=25)	

	elif number == 2:
		myHelp2=Label(helps,bg="#B3B3B3", borderwidth=0, fg="#333333", 
		text="The file server generates responses to sender requests, generates a\n key pair, sends the public key to the user, receives \nfiles, decrypts and generates download links")
		myHelp2.place(x=10,y=200)

	else:
		myHelp3=Label(helps,bg="#B3B3B3", borderwidth=0, fg="#333333", 
		text="The mail server takes all address and address files, sends\n links  to download files to all addresses. The recipient opens\n the mail, clicks the link, downloads the file")
		myHelp3.place(x=10,y=350)
	


###HOW THAT WORKS2###
def show_help():
    global helps
    helps = Toplevel()
    helps['bg'] = '#B3B3B3'
    helps.geometry("660x450")
    helps.resizable(False, False)
    #Label(a, text="About this").pack(expand=1)
    helps.grab_set()
    helps.focus_set()
    imghelp = ImageTk.PhotoImage(Image.open("img/hiw.png"))

    LabelHelp = Label(helps,image=imghelp, borderwidth=0)
    LabelHelp.place(x=350,y=0)

    helpbutton1=Button(helps,text="What\n is it?",font=("Arial Bold", 13),
    				 bg="#B3B3B3", borderwidth=0, fg="grey", command=lambda: place_help_text(1))
    helpbutton1.place(x=600,y=50)

    helpbutton2=Button(helps,text="What\n is it?",font=("Arial Bold", 13),
    				 bg="#B3B3B3", borderwidth=0, fg="grey", command=lambda: place_help_text(2))
    helpbutton2.place(x=600,y=200)

    helpbutton3=Button(helps,text="What\n is it?",font=("Arial Bold", 13),
    				 bg="#B3B3B3", borderwidth=0, fg="grey", command=lambda: place_help_text(3))
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
SetMails= set()
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
	if temp.find("@")==-1 or temp.find(".")==-1:
		messagebox.showerror("Error", "Wrong mail type.")
	else:	
		if temp.find("@") and temp.find(".") and temp.index("@")+1!=temp.index(".") and temp.index(".")!=len(temp)-1  and temp.count("@")==1:
			if temp not in SetMails:
				tree.insert("",0, text=temp)
				SetMails.add(temp)
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
my_frame = Frame(root, bg="#191919", width=150, height=450)
my_frame.grid(row=0, column=0, rowspan=10)
my_frame1 = Frame(root, bg="#202020", width=650, height=450)
my_frame1.grid(row=0, column=1)


#Allsafe Title
status = Button(my_frame, text="Allsafe Build 0.1.9", bg="#191919", borderwidth=0,
				 fg="#104981", font=("Arial Bold", 10), command=button_home)
status.place(x=15,y=10)
#How it works

helps = Button(my_frame, text="How it works?", bg="#181818", 
				fg="#959595", borderwidth=0, command=show_help)
helps.place(x=33, y=400)

#News Label
newslabel = Label(my_frame1, text="Allsafe", bg="#202020",
				 fg="#171717", font=("Arial Bold", 90))
newslabel.place(x=270, y=150)
newslabel1 = Label(my_frame1, text="cyberletters", bg="#202020", 
					fg="#171717", font=("Arial Bold", 30))
newslabel1.place(x=370, y=260)

#Allsafe logo
logo = ImageTk.PhotoImage(Image.open("img/logo.png"))
logoLabel = Label(my_frame1,image=logo, borderwidth=0)
logoLabel.place(x=100,y=150)

#Buttons


#Switch Buttons
button_switch1 = Button(my_frame, text="Let's send something!",bg="#181818", fg="lightgrey", borderwidth=0, command=show_f1rst_frame)
button_switch1.place(x=20, y=100)

button_switch2 = Button(my_frame, text="Show reference", bg="#181818", fg="lightgrey", borderwidth=0, command=show_sec0nd_frame)
button_switch2.place(x=20, y=130)


#switching frames
f1rst_frame = Frame(root, bg="#202020", width=650, height=450)
subframe = Frame(f1rst_frame, width=610, height=310, bg="#1b2c45",borderwidth=2, relief="ridge")
subframe.place(x=20, y=60)

logosend = ImageTk.PhotoImage(Image.open("img/logosend.png"))
logoSendL = Label(subframe,image=logosend, borderwidth=0)
logoSendL.place(x=220,y=80)

button_addfile = Button(subframe, padx=50, pady=10, text="Choose File", bg="#282828", fg="lightgrey", command=choose_file, borderwidth=0)
button_addfile.place(x=10,y=80)

Sending1= Label(subframe, text="Enter email here", borderwidth=0, bg="#1b2c45", fg="#666464")
Sending1.place(x=30, y=30)
#Mail pole
emailplace= Entry(subframe,width=45,bg="lightgrey", fg="grey", borderwidth=0)
emailplace.place(x=10, y=10)

#addmail Button
addmail=Button(subframe, text="add mail", bg="#1b2c45", fg="lightgrey", pady=1, command=valid_check, borderwidth=0)
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



Sending2= Label(subframe, text="Choose files yow want to send", borderwidth=0, bg="#1b2c45", fg="#666464")
Sending2.place(x=10, y=130)
Button_send = Button(subframe,text="Send", padx=30, pady=5,font=("Impact", 11),
					 bg="grey",state=DISABLED, command=main_def_send_mail, borderwidth=0)
Button_send.place(x=8, y=255)
#SendMessage.place(x=430,y=330)
#263e4d cиний
#2f434f серый






sec0nd_frame = Frame(root, bg="#202020", width=650, height=450)
WarningLabel = Label(sec0nd_frame, bg="#202020",
					 text="AllSafe is a free program for the safe \n \ntransfer of mail letters. ", fg="white") 
WarningLabel.place(x=200, y=180)
#Some things


##Progress bar
 #pb = ttk.Progressbar(my_frame1, length=450,)
#pb.place(x=10, y=10)
#pb.start(200)


##Open Folder
#root.filename =  filedialog.askopenfilename(initialdir = "/",title = "Select file",filetypes = (("jpeg files","*.jpg"),("all files","*.*")))
#root.directory = filedialog.askdirectory()

root.mainloop()