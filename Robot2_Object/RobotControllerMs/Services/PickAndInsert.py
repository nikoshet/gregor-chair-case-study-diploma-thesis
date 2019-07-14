import time

class PickAndInsert:

    START_MESSAGE = '{"PickAndInsert_MS": "STARTED"}'
    COMPLETED_MESSAGE = '{"PickAndInsert_MS": "FINISHED"}'

    def __init__(self):
        print("\n \n Unix Server of PickAndPlace has just started \n \n")

    def start_working(self):
            try:
                    print('doing PickAndInsert')
                    time.sleep(5)
                    print('PickAndInsert finished..')
                    print('Replying to WT1 server...')
                    encoded_response = self.COMPLETED_MESSAGE
                    return encoded_response
            except (ConnectionResetError, OSError) as e:
                print(e)


