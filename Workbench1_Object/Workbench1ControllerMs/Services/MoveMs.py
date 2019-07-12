import json


class MoveMs:

    def run_moveMs(self):
        print(" running move microservice ")
        sleep(1000)
        print(" move microservice finished")
        return self.Messages.COMPLETED
