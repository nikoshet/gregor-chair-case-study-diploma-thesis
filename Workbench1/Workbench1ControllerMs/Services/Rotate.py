import time
#import RPi.GPIO as GPIO

out1 = 13
out2 = 11
out3 = 15
out4 = 12

class Rotate:

    COMPLETED_MESSAGE = 'ROTATE_FINISHED'

    def __init__(self):
        print("\n \n Rotate has just started \n \n")

    def start_working(self):
        try:
            print('doing Rotate')
            #initPins()
            #rotate()
            print('Rotate finished..')
            print('Replying to server...')
            encoded_response = self.COMPLETED_MESSAGE
            return encoded_response
        except (ConnectionResetError, OSError) as e:
            print(e)


    def initPins():
        GPIO.setwarnings(False)
        GPIO.setmode(GPIO.BOARD)
        GPIO.setup(out1, GPIO.OUT)
        GPIO.setup(out2, GPIO.OUT)
        GPIO.setup(out3, GPIO.OUT)
        GPIO.setup(out4, GPIO.OUT)


    def rotate():
        Direction=0
        Step=0
        NoTurns=0
        NoItems = 1
        NoTurns = 0
        for ItemNo in range(NoItems+1):
            for Step in range(133,0,-1):
                if Step == 133: # edw einai ItemNo katastash tou Pos 1 prin kounhthei ItemNo trapeza gia prwth fora
                    if NoTurns == 0:
                        pass                        
                    elif NoTurns == 1:
                        pass
                elif Step == 1: # stamataei na kounietai
                    NoTurns+=1
                    if NoTurns == 1:
                        pass
                    elif NoTurns > 1:
                        pass
                if Direction==0:
                    GPIO.output(out1,GPIO.HIGH)
                    GPIO.output(out2,GPIO.LOW)
                    GPIO.output(out3,GPIO.LOW)
                    GPIO.output(out4,GPIO.LOW)
                    time.sleep(0.01)
                    #time.sleep(1)
                elif Direction==1:
                    GPIO.output(out1,GPIO.HIGH)
                    GPIO.output(out2,GPIO.HIGH)
                    GPIO.output(out3,GPIO.LOW)
                    GPIO.output(out4,GPIO.LOW)
                    time.sleep(0.01)
                    #time.sleep(1)
                elif Direction==2:
                    GPIO.output(out1,GPIO.LOW)
                    GPIO.output(out2,GPIO.HIGH)
                    GPIO.output(out3,GPIO.LOW)
                    GPIO.output(out4,GPIO.LOW)
                    time.sleep(0.01)
                    #time.sleep(1)
                elif Direction==3:
                    GPIO.output(out1,GPIO.LOW)
                    GPIO.output(out2,GPIO.HIGH)
                    GPIO.output(out3,GPIO.HIGH)
                    GPIO.output(out4,GPIO.LOW)
                    time.sleep(0.01)
                    #time.sleep(1)
                elif Direction==4:
                    GPIO.output(out1,GPIO.LOW)
                    GPIO.output(out2,GPIO.LOW)
                    GPIO.output(out3,GPIO.HIGH)
                    GPIO.output(out4,GPIO.LOW)
                    time.sleep(0.01)
                    #time.sleep(1)
                elif Direction==5:
                    GPIO.output(out1,GPIO.LOW)
                    GPIO.output(out2,GPIO.LOW)
                    GPIO.output(out3,GPIO.HIGH)
                    GPIO.output(out4,GPIO.HIGH)
                    time.sleep(0.01)
                    #time.sleep(1)
                elif Direction==6:
                    GPIO.output(out1,GPIO.LOW)
                    GPIO.output(out2,GPIO.LOW)
                    GPIO.output(out3,GPIO.LOW)
                    GPIO.output(out4,GPIO.HIGH)
                    time.sleep(0.01)
                  #time.sleep(1)
                elif Direction==7:
                    GPIO.output(out1,GPIO.HIGH)
                    GPIO.output(out2,GPIO.LOW)
                    GPIO.output(out3,GPIO.LOW)
                    GPIO.output(out4,GPIO.HIGH)
                if Direction==0:
                    Direction=7
                    continue
                Direction=Direction-1

    #GPIO.cleanup()
