import time

class MoveMs:


    START_MESSAGE = '{"Move_MS": "STARTED"}'
    COMPLETED_MESSAGE = '{"Move_MS": "FINISHED"}'

    def __init__(self):
        print("\n \n Move_MS has just started \n \n")

    def run_moveMs(self):
        print(" running move microservice ")
        time.sleep(5)
        print(" move microservice finished")
        return self.COMPLETED_MESSAGE
