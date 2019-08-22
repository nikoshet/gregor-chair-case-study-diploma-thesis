import time
#from main import device


class ScrewPickAndFasten:
    START_MESSAGE = '{"ScrewPickAndFasten_MS": "STARTED"}'
    COMPLETED_MESSAGE = '{"ScrewPickAndFasten_MS": "FINISHED"}'

    def __init__(self):
        print("\n \n Unix Server of ScrewPickAndFasten has just started \n \n")

    def start_working(self, device):
        try:
            print('doing ScrewPickAndFasten')
            if device == "RPI":
                from Controller.GpioController import GpioController
                gpio = GpioController()
                gpio.initPins()
                gpio.gpioControl('wrist_f', 1)
                time.sleep(1)
                gpio.gpioControl('grip_f', 0.3)
                gpio.gpioControl('led', 1)
                time.sleep(0.5)
                gpio.gpioControl('led', 1)
                time.sleep(0.5)
                gpio.gpioControl('led', 1)
                time.sleep(0.5)
                gpio.gpioControl('led', 1)
                time.sleep(0.5)
                gpio.gpioControl('grip_b', 0.3)
                time.sleep(1)
                gpio.gpioControl('wrist_b', 1.2)
            elif device == "LAPTOP":
                time.sleep(1)

            print('ScrewPickAndFasten finished..')
            print('Replying to WT server...')
            encoded_response = self.COMPLETED_MESSAGE
            return encoded_response

        except (ConnectionResetError, OSError) as e:
            print(e)
