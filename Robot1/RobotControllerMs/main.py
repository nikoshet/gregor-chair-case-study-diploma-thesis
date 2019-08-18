import os
from Controller.RobotController import RobotController
#import eureka.eureka_client as eureka_client
import sys

if __name__ == "__main__":
    # execute only if run as a script
    try:
        print("start program")
        device = "{}".format(sys.argv[1])
        print("Device: " + device)
        # eureka_client.start()
        robot = RobotController()
        robot.start_controller(device)
    except Exception as e:
        print("Error: unable to start thread")
        print(e)
    


