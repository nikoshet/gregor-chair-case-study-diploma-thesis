

class Hold:
    START_MESSAGE = '{"Hold_MS": "STARTED"}'
    COMPLETED_MESSAGE = '{"Hold_MS": "FINISHED"}'

    def __init__(self):
        print("\n \n Unix Server of Hold has just started \n \n")

    def start_working(self, device):
        try:
            print('doing Hold')
            if device == "RPI":
                from Controller.GpioController import GpioController
                #gpio = GpioController()
                #gpio.initPins()
                #gpio.gpioControl('led', 'on')

            elif device == "LAPTOP":
                pass

            print('Hold finished..')
            print('Replying to WCoordinator server...')
            encoded_response = self.COMPLETED_MESSAGE
            return encoded_response
        except (ConnectionResetError, OSError) as e:
            print(e)
