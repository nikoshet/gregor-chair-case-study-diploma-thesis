
import time

class ScrewPickAndFasten:

    START_MESSAGE = '{"ScrewPickAndFasten_MS": "STARTED"}'
    COMPLETED_MESSAGE = '{"ScrewPickAndFasten_MS": "FINISHED"}'

    def __init__(self):
        print("\n \n Unix Server of ScrewPickAndFasten has just started \n \n")

    def start_working(self):
         try:
                print('doing ScrewPickAndFasten')
                time.sleep(5)
                print('ScrewPickAndFasten finished..')
                print('Replying to WT server...')
                encoded_response = self.COMPLETED_MESSAGE
                return encoded_response
         except (ConnectionResetError, OSError) as e:
                print(e)

