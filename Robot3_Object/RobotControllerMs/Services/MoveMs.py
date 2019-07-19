import time

class MoveMs:

    START_MESSAGE = '{"Move_Ms": "STARTED"}'
    COMPLETED_MESSAGE = '{"Move_Ms": "FINISHED"}'

    def __init__(self):
        print("\n \n Unix Server of MoveMs has just started \n \n")

    def start_working(self, message):
        try:
            print('doing MoveMs to '+ str(message))
            if "left" in message:
                print(" \n \n \n move to left \n \n \n ")
                time.sleep(5)
            elif "right" in message:
                print(" \n \n \n move to right \n \n \n ")
                time.sleep(5)

            print('MoveMs finished..')
            print('Replying to server...')
            encoded_response = self.COMPLETED_MESSAGE
            return encoded_response
        except (ConnectionResetError, OSError) as e:
            print(e)

