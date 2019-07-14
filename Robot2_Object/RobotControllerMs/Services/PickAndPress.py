import json
import time

class PickAndPress:

    START_MESSAGE = '{"PickAndPress_MS": "STARTED"}'
    COMPLETED_MESSAGE = '{"PickAndPress_MS": "FINISHED"}'

    def __init__(self):
        print("\n \n Unix Server of PickAndPress has just started \n \n")

    def start_working(self):
            try:
                print('doing PickAndPress')
                time.sleep(5)
                print('PickAndPress finished..')
                print('Replying to WT server...')
                encoded_response = self.COMPLETED_MESSAGE
                return encoded_response
            except (ConnectionResetError, OSError) as e:
                print(e)

