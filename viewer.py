from tkinter import *
from PIL import ImageTk, Image 
root  = Tk( )
root.title("Allsafe Desk v. 0.0.0.1")
#root.iconbitmap('c:/1/dielit.ico')



#button_quit = Button(root, text="Exit Program", command=root.quit)
#button_quit.pack()

my_img1 = ImageTk.PhotoImage(Image.open("c:/1/img1.jpg"))
my_img2 = ImageTk.PhotoImage(Image.open("c:/1/img2.jpg"))
my_img3 = ImageTk.PhotoImage(Image.open("c:/1/img3.jpg"))
my_img4 = ImageTk.PhotoImage(Image.open("c:/1/img4.jpg"))
my_img5 = ImageTk.PhotoImage(Image.open("c:/1/img5.jpg"))
image_list = [my_img1,my_img2,my_img3,my_img4,my_img5]

my_label = Label(image=my_img1)

my_label.grid(row=0, column=0, columnspan=3)

def forward(image_number):
	global my_label
	global forward_button
	global back_button
	global exit_button
	my_label.grid_forget()
	my_label = Label(image=image_list[image_number-1])
	forward_button= Button(root, text=">>", command=lambda: forward(image_number+1))
	button_back= Button(root, text="<<", command=lambda: back(image_number-1))

   # back_button.grid(row=1,column=0)
	#forward_button.grid(row=1,column=2)  
	#exit_button.grid(row=1,column=1)
  #  my_label.grid(row=0, column=0, columnspan=3)
    
	

def back():
	global my_label
	global forward_button
	global back_button




back_button = Button (root, text="<<", command=back)
exit_button = Button(root, text="exit", command=root.quit)
forward_button = Button(root, text=">>", command=lambda: forward(2))

back_button.grid(row=1,column=0)

exit_button.grid(row=1,column=1)
forward_button.grid(row=1,column=2)
root.mainloop()