import time
#from main import device


class PickAndPlace:
    START_MESSAGE = '{"PickAndPlace_MS": "STARTED"}'
    COMPLETED_MESSAGE = '{"PickAndPlace_MS": "FINISHED"}'

    def __init__(self):
        print("\n \n Unix Server of PickAndPlace has just started \n \n")

    def start_working(self, device):

        try:
            print('doing PickAndPlace')
            if device == "RPI":
                from Controller.GpioController import GpioController
                gpio = GpioController()
                gpio.initPins()
                gpio.gpioControl('elbow_f', 1)
                time.sleep(0.5)
                gpio.gpioControl('grip_f', 0.3)
                gpio.gpioControl('grip_b', 0.3)
                time.sleep(0.5)
                gpio.gpioControl('elbow_b', 1.35)
            elif device == "LAPTOP":
                time.sleep(1)

            print('PickAndPlace finished..')
            print('Replying to WT server...')
            response = self.COMPLETED_MESSAGE
            return response

        except (ConnectionResetError, OSError) as e:
            print(e)
