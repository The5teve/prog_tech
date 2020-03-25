from tkinter import *
import tkinter.ttk as ttk
from tkinter import filedialog
from tkinter import messagebox
root = Tk()
root.geometry("800x450")
root.title("AllSafe")
root.resizable(False, False)



def main_def_send_mail():
	return
def choose_file():
	global tree1
	global file_path
	file_path =  filedialog.askopenfilename(initialdir = "/",title = "Select file", filetypes = (("jpeg files","*.jpg"),("all files","*.*")))
	tree1.insert("",0, text=file_path)
def show_help():
    helps = Toplevel()
    helps.geometry('200x150')
    helps['bg'] = '#191919'
    helps.geometry("400x300")
    #Label(a, text="About this").pack(expand=1)
    helps.grab_set()
    helps.focus_set()
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

#Set for mails
SetMails= set()
def validcheck():
	global emailplace
	global tree
	global SetMails
	temp=emailplace.get()
	if temp.find("@")==-1 or temp.find(".")==-1:
		messagebox.showerror("Error", "Wrong mail type.")
	else:	
		if temp.find("@") and temp.find(".") and temp.index("@")+1!=temp.index(".") and temp.index(".")!=len(temp)-1 and temp.index("@")<temp.index(".") and temp.count("@")==1:
			if temp not in SetMails:
				tree.insert("",0, text=temp)
				SetMails.add(temp)
				emailplace.delete(0, END)
			else:
				messagebox.showwarning("Warning", "You already add this email")
				emailplace.delete(0, END)
		else:
			messagebox.showerror("Error", "Wrong email.")		
#Frames 
my_frame = Frame(root, bg="#191919", width=150, height=450)
my_frame.grid(row=0, column=0, rowspan=10)
my_frame1 = Frame(root, bg="#202020", width=650, height=450)
my_frame1.grid(row=0, column=1)


#Allsafe Title
status = Button(my_frame, text="AllSafe Build 0.0.71", bg="#242424", borderwidth=0, fg="white", font=("Arial Bold", 10), command=button_home)
status.place(x=15,y=10)


#News Label
newslabel = Label(my_frame1, text="Allsafe", bg="#202020", fg="#171717", font=("Arial Bold", 90))
newslabel.place(x=270, y=150)

#How it works
helps = Button(my_frame, text="How it works?", bg="#181818", fg="#959595", borderwidth=0, command=show_help)
helps.place(x=33, y=400)


#Buttons


#Switch Buttons
button_switch1 = Button(my_frame, text="Let's send something!",bg="#181818", fg="white", borderwidth=0, command=show_f1rst_frame)
button_switch1.place(x=20, y=100)

button_switch2 = Button(my_frame, text="Show reference.", bg="#181818", fg="white", borderwidth=0, command=show_sec0nd_frame)
button_switch2.place(x=20, y=130)


#switching frames
f1rst_frame = Frame(root, bg="#202020", width=650, height=450)
subframe = Frame(f1rst_frame, width=610, height=310, bg="#2f2f4f")
subframe.place(x=20, y=20)
button_addfile = Button(subframe, padx=50, pady=10, text="Choose File", bg="#282828", fg="White", command=choose_file)
button_addfile.place(x=10,y=80)
Sending1= Label(subframe, text="Enter email here", borderwidth=0, bg="#2f2f4f", fg="#666464")
Sending1.place(x=30, y=30)
#Mail pole
emailplace= Entry(subframe,width=45,bg="lightgrey", fg="black", borderwidth=0)
emailplace.place(x=10, y=10)
#addmail Button
addmail=Button(subframe, text="add mail", bg="#2f2f4f", fg="white", pady=1, command=validcheck, borderwidth=0)
addmail.place(x=295, y=9)
#MAILS TREE
tree=ttk.Treeview(subframe, height=6)
tree.column("#0",stretch="NO")
tree.heading("#0",text="e-mails",anchor=W)
#tree.insert("",1, text="your mail here") #for inserting
tree.place(x=400, y=5)

tree1=ttk.Treeview(subframe, height=6)
tree1.column("#0",stretch="NO")
tree1.heading("#0",text="chosen files",anchor=W)
#tree.insert("",1, text="your mail here") #for inserting
tree1.place(x=400, y=155)

Sending2= Label(subframe, text="Choose files yow want to send", borderwidth=0, bg="#2f2f4f", fg="#666464")
Sending2.place(x=10, y=130)
Button_send = Button(subframe,text="Send", padx=30, pady=5,font=("Impact", 11), bg="#262652",state=DISABLED, command=main_def_send_mail)
Button_send.place(x=8, y=255)
#SendMessage.place(x=430,y=330)
#263e4d cиний
#2f434f серый












sec0nd_frame = Frame(root, bg="#202020", width=650, height=450)
WarningLabel = Label(sec0nd_frame, bg="#202020", text="idk", fg="white")
WarningLabel.place(x=270, y=150)
#Some things


##Progress bar
 #pb = ttk.Progressbar(my_frame1, length=450,)
#pb.place(x=10, y=10)
#pb.start(200)


##Open Folder
#root.filename =  filedialog.askopenfilename(initialdir = "/",title = "Select file",filetypes = (("jpeg files","*.jpg"),("all files","*.*")))
#root.directory = filedialog.askdirectory()

root.mainloop()