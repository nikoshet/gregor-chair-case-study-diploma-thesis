import time


out1 = 13
out2 = 11
out3 = 15
out4 = 12


class Rotate:

    COMPLETED_MESSAGE = 'ROTATE_FINISHED'

    def __init__(self):
        print("\n \n Rotate has just started \n \n")

    def start_working(self,device):
        try:
            print('doing Rotate')
            if device == "RPI":
                import RPi.GPIO as GPIO
                self.initPins(GPIO)
                self.rotate(GPIO)
            elif device == "LAPTOP":
                time.sleep(1)

            print('Rotate finished..')
            print('Replying to server...')
            encoded_response = self.COMPLETED_MESSAGE
            return encoded_response
        except (ConnectionResetError, OSError) as e:
            print(e)


    def initPins(self,GPIO):
        GPIO.setwarnings(False)
        GPIO.setmode(GPIO.BOARD)
        GPIO.setup(out1, GPIO.OUT)
        GPIO.setup(out2, GPIO.OUT)
        GPIO.setup(out3, GPIO.OUT)
        GPIO.setup(out4, GPIO.OUT)


    def rotate(self,GPIO):
        Direction=0
        Step=0
        for Step in range(130,0,-1): 
            if Direction==0:
                GPIO.output(out1,GPIO.HIGH)
                GPIO.output(out2,GPIO.LOW)
                GPIO.output(out3,GPIO.LOW)
                GPIO.output(out4,GPIO.LOW)
                time.sleep(0.08)
                #time.sleep(1)
            elif Direction==1:
                GPIO.output(out1,GPIO.HIGH)
                GPIO.output(out2,GPIO.HIGH)
                GPIO.output(out3,GPIO.LOW)
                GPIO.output(out4,GPIO.LOW)
                time.sleep(0.08)
                #time.sleep(1)
            elif Direction==2:
                GPIO.output(out1,GPIO.LOW)
                GPIO.output(out2,GPIO.HIGH)
                GPIO.output(out3,GPIO.LOW)
                GPIO.output(out4,GPIO.LOW)
                time.sleep(0.08)
                #time.sleep(1)
            elif Direction==3:
                GPIO.output(out1,GPIO.LOW)
                GPIO.output(out2,GPIO.HIGH)
                GPIO.output(out3,GPIO.HIGH)
                GPIO.output(out4,GPIO.LOW)
                time.sleep(0.08)
                #time.sleep(1)
            elif Direction==4:
                GPIO.output(out1,GPIO.LOW)
                GPIO.output(out2,GPIO.LOW)
                GPIO.output(out3,GPIO.HIGH)
                GPIO.output(out4,GPIO.LOW)
                time.sleep(0.08)
                #time.sleep(1)
            elif Direction==5:
                GPIO.output(out1,GPIO.LOW)
                GPIO.output(out2,GPIO.LOW)
                GPIO.output(out3,GPIO.HIGH)
                GPIO.output(out4,GPIO.HIGH)
                time.sleep(0.08)
                #time.sleep(1)
            elif Direction==6:
                GPIO.output(out1,GPIO.LOW)
                GPIO.output(out2,GPIO.LOW)
                GPIO.output(out3,GPIO.LOW)
                GPIO.output(out4,GPIO.HIGH)
                time.sleep(0.08)
                #time.sleep(1)
            elif Direction==7:
                GPIO.output(out1,GPIO.HIGH)
                GPIO.output(out2,GPIO.LOW)
                GPIO.output(out3,GPIO.LOW)
                GPIO.output(out4,GPIO.HIGH)
                time.sleep(0.08)
            if Direction==0:
                Direction=7
                continue
            Direction=Direction-1
        GPIO.cleanup()
