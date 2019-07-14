from unix_sockets.unix_server import UnixServer
from unix_sockets.unix_client import UnixClient
from enum import Enum
import json
import time



class PickAndFlipAndPress:

    START_MESSAGE = '{"PickAndFlipAndPress_MS": "STARTED"}'
    COMPLETED_MESSAGE = '{"PickAndFlipAndPress_MS": "FINISHED"}'
    def __init__(self):
        print("\n \n Unix Server of PickAndFlipAndPress has just started \n \n")

    def start_working(self):
            try:
                    print('doing PickAndFlipAndPress')
                    time.sleep(5)
                    print('PickAndFlipAndPress finished..')
                    print('Replying to WT server...')
                    encoded_response = self.COMPLETED_MESSAGE
                    return encoded_response
            except (ConnectionResetError, OSError) as e:
                print(e)

