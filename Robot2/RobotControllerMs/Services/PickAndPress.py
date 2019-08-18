import time
#from main import device


class PickAndPress:
    START_MESSAGE = '{"PickAndPress_MS": "STARTED"}'
    COMPLETED_MESSAGE = '{"PickAndPress_MS": "FINISHED"}'

    def __init__(self):
        print("\n \n Unix Server of PickAndPress has just started \n \n")

    def start_working(self, device):

        try:
            print('doing PickAndPress')
            if device == "RPI":
                from Controller.GpioController import GpioController
                gpio = GpioController()
                gpio.initPins()
                gpio.gpioControl('base_f', 0.3)
                time.sleep(1)
                gpio.gpioControl('grip_f', 0.3)
                gpio.gpioControl('grip_b', 0.3)
                gpio.gpioControl('grip_f', 0.3)
                gpio.gpioControl('grip_b', 0.3)
                time.sleep(1)
                gpio.gpioControl('base_b', 0.5)
                gpio.gpioControl('base_b', 0.5)
            elif device == "LAPTOP":
                time.sleep(1)

            print('PickAndPress finished..')
            print('Replying to WT server...')
            response = self.COMPLETED_MESSAGE
            return response

        except (ConnectionResetError, OSError) as e:
            print(e)
