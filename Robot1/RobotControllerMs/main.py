import os
from Controller.RobotController import RobotController
#import eureka.eureka_client as eureka_client


if __name__ == "__main__":
    # execute only if run as a script
    try:
        print("start program")

        #eureka_client.start()
        robot = RobotController()
        robot.start_controller()
    except Exception as e:
        print("Error: unable to start thread")
        print(e)
    


