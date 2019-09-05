

class Release:
    START_MESSAGE = '{"Release_MS": "STARTED"}'
    COMPLETED_MESSAGE = '{"Release_MS": "FINISHED"}'

    def __init__(self):
        print("\n \n Unix Server of Release has just started \n \n")

    def start_working(self, device):
        try:
            print('doing Release')
            if device == "RPI":
                from Controller.GpioController import GpioController
                #gpio = GpioController()
                #gpio.initPins()
                #gpio.gpioControl('led', 'off')

            elif device == "LAPTOP":
                pass

            print('Release finished..')
            print('Replying to WCoordinator server...')
            encoded_response = self.COMPLETED_MESSAGE
            return encoded_response
        except (ConnectionResetError, OSError) as e:
            print(e)
