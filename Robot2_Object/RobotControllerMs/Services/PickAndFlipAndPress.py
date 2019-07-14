import json
import time


class PickAndFlipAndPress:

    START_MESSAGE = '{"PickAndFlipAndPress_MS": "STARTED"}'
    COMPLETED_MESSAGE = '{"PickAndFlipAndPress_MS": "FINISHED"}'

    def __init__(self):
        print("\n \n PickAndFlipAndPress has just started \n \n")

    def start_working(self):
        try:
            print('doing PickAndFlipAndPress')
            time.sleep(5)
            print('PickAndFlipAndPress finished..')
            print('Replying to WT server...')
            response = self.COMPLETED_MESSAGE
            return response
        except (ConnectionResetError, OSError) as e:
            print(e)

