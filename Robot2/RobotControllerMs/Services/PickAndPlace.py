
import time

from Controller.GpioController import GpioController


class PickAndPlace:

    START_MESSAGE = '{"PickAndPlace_MS": "STARTED"}'
    COMPLETED_MESSAGE = '{"PickAndPlace_MS": "FINISHED"}'

    def __init__(self):
        print("\n \n Unix Server of PickAndPlace has just started \n \n")

    def start_working(self):

            try:
                    print('doing PickAndPlace')

                    gpio = GpioController()
                    gpio.initPins()
                    gpio.gpioControl('elbow_f', 1)
                    time.sleep(0.5)
                    #gpio.gpioControl('grip_f', 0.3)
                    #gpio.gpioControl('grip_b', 0.3)
                    #time.sleep(0.5)
                    gpio.gpioControl('elbow_b', 1.25)

                    print('PickAndPlace finished..')
                    print('Replying to WT server...')
                    response = self.COMPLETED_MESSAGE
                    return response

            except (ConnectionResetError, OSError) as e:
                print(e)
