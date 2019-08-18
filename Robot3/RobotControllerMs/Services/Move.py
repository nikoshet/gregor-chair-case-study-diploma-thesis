import time


class Move:
    START_MESSAGE = '{"Move_Ms": "STARTED"}'
    COMPLETED_MESSAGE_POS1 = 'POS1_REACHED'
    COMPLETED_MESSAGE_POS2 = 'POS2_REACHED'

    def __init__(self):
        print("\n \n Unix Server of MoveMs has just started \n \n")

    def start_working(self, message):
        try:
            print('doing MoveMs to '+ str(message))
            if "left" in message:
                print(" \n \n \n move to left \n \n \n ")
                time.sleep(2)
                print('MoveMs finished..')
                print('Replying to server...')
                encoded_response = self.COMPLETED_MESSAGE_POS2
                return encoded_response
            elif "right" in message:
                print(" \n \n \n move to right \n \n \n ")
                time.sleep(2)
                print('MoveMs finished..')
                print('Replying to server...')
                encoded_response = self.COMPLETED_MESSAGE_POS1
                return encoded_response


        except (ConnectionResetError, OSError) as e:
            print(e)
