import RPi.GPIO as GPIO
import time


class GpioController:

    gpioDict = {'elbow_f':20,'elbow_b':21,'wrist_f':12,'wrist_b':16,
                'grip_f':8,'grip_b':7,'rotate_l':26,'rotate_r':19,
                'base_f':6,'base_b':13,'led':14}

    def __init__(self):
        print("\n \n Calling GPIO controller.. \n \n")

    def initPins(self):
        GPIO.cleanup()
        GPIO.setmode(GPIO.BCM)
        for pin in self.gpioDict.values():
            GPIO.setup(pin, GPIO.OUT)
            GPIO.output(pin, 0)

    def gpioControl(self,robot_component,delay):

        for key, value in self.gpioDict.items():
            if key == robot_component:
                GPIO.output(value,GPIO.HIGH)
                time.sleep(delay)
                GPIO.output(value, GPIO.LOW)
