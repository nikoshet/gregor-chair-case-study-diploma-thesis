from unix_sockets.unix_server import UnixServer
from unix_sockets.unix_client import UnixClient
import json
from Controller.GpioController import GpioController
from enum import Enum
import time

class PickAndInsert:

    START_MESSAGE = '{"PickAndInsert_MS": "STARTED"}'
    COMPLETED_MESSAGE = '{"PickAndInsert_MS": "FINISHED"}'

    def __init__(self):
        print("\n \n Unix Server of PickAndInsert has just started \n \n")

    def start_working(self):
            try:
                    print('doing PickAndInsert')

                    gpio = GpioController()
                    gpio.initPins()
                    gpio.gpioControl('wrist_f', 1)
                    time.sleep(0.5)
                    gpio.gpioControl('grip_f', 0.3)
                    gpio.gpioControl('led', 3)
                    gpio.gpioControl('grip_b', 0.3)
                    time.sleep(0.5)
                    gpio.gpioControl('wrist_b', 1.2)

                    print('PickAndInsert finished..')
                    print('Replying to WT server...')
                    encoded_response = self.COMPLETED_MESSAGE
                    return encoded_response
                    #break
                #else:
                    #print("den katalabainw")
            except (ConnectionResetError, OSError) as e:
                print(e)

