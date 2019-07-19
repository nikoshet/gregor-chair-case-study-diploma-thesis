import time

class Rotate:

    COMPLETED_MESSAGE = 'ROTATE_FINISHED'

    def __init__(self):
        print("\n \n Rotate has just started \n \n")

    def start_working(self):
        try:
            print('doing Rotate')
            time.sleep(5);
            print('Rotate finished..')
            print('Replying to server...')
            encoded_response = self.COMPLETED_MESSAGE
            return encoded_response
        except (ConnectionResetError, OSError) as e:
            print(e)

