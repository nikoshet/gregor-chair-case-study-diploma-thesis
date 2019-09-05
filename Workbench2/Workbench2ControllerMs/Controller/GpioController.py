import RPi.GPIO as GPIO
import time


class GpioController:

    gpioDict = {'led':14}

    def __init__(self):
        print("\n \n Calling GPIO controller.. \n \n")

    def initPins(self):
        GPIO.cleanup()
        GPIO.setmode(GPIO.BCM)
        for pin in self.gpioDict.values():
            GPIO.setup(pin, GPIO.OUT)
            GPIO.output(pin, 0)

    def gpioControl(self,w_component,state):

        for key, value in self.gpioDict.items():
            if key == w_component:
                if state == 'on':
                    GPIO.output(value,GPIO.HIGH)
                elif state == 'off':
                    GPIO.output(value, GPIO.LOW)

