
import time

class PickAndPlace:


    START_MESSAGE = '{"PickAndPlace_MS": "STARTED"}'
    COMPLETED_MESSAGE = '{"PickAndPlace_MS": "FINISHED"}'

    def __init__(self):
        print("\n \n Unix Server of PickAndPlace has just started \n \n")

    def start_working(self):
        try:
                print('doing PickAndPlace')
                time.sleep(5)
                print('PickAndPlace finished..')
                print('Replying to WT server...')
                response = self.COMPLETED_MESSAGE
                return response
        except (ConnectionResetError, OSError) as e:
            print(e)

