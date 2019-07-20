from unix_sockets.unix_server import UnixServer
from unix_sockets.unix_client import UnixClient
from enum import Enum
from Controller.GpioController import GpioController

import time



class PickAndFlipAndPress:

    START_MESSAGE = '{"PickAndFlipAndPress_MS": "STARTED"}'
    COMPLETED_MESSAGE = '{"PickAndFlipAndPress_MS": "FINISHED"}'
    def __init__(self):
        print("\n \n Unix Server of PickAndFlipAndPress has just started \n \n")

    def start_working(self):
            try:
                    print('doing PickAndFlipAndPress')

                    gpio = GpioController()
                    gpio.initPins()
                    gpio.gpioControl('wrist_f', 1)
                    time.sleep(0.5)
                    gpio.gpioControl('grip_f', 0.3)
                    gpio.gpioControl('grip_b', 0.3)
                    time.sleep(0.5)
                    gpio.gpioControl('grip_f', 0.3)
                    gpio.gpioControl('grip_b', 0.3)
                    time.sleep(0.5)
                    gpio.gpioControl('wrist_b', 1.2)

                    print('PickAndFlipAndPress finished..')
                    print('Replying to WT server...')
                    encoded_response = self.COMPLETED_MESSAGE
                    return encoded_response
            except (ConnectionResetError, OSError) as e:
                print(e)

