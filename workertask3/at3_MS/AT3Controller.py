from unix_sockets.unix_server import  UnixServer
from unix_sockets.unix_client import  UnixClient
from Communication_Wrapper.robotctrl_wrapper import RobotCtrl_Wrapper
from Communication_Wrapper.robot2Coordinator_Wrapper import Robot2Coordinator_Wrapper
import json
#import eureka_client as eureka_client

class AT3Controller:

    robot2ctrl_wrapper= RobotCtrl_Wrapper()
    robot2coordinator_wrapper= Robot2Coordinator_Wrapper()
    SERVER_ADDRESS = "/tmp/at3.sock"
    def __init__(self):
        self.unix_server = UnixServer(self.SERVER_ADDRESS)
        self.unix_server.start()
        print("\n \n Unix Server of Assembly Task3 has just started \n \n")


    def start_working(self):
        while True:
            try:
                message_received= self.unix_server.read_data()
                if message_received!='' :
                    message = json.loads(message_received)
                    sender_address = message['sender']
                    print(message)

                    if sender_address == "coordinator":  # mono start mporei na steilei o coordinator
                        self.robot2ctrl_wrapper.create_client()
                        self.robot2ctrl_wrapper.connect_2_unix_server(RobotCtrl_Wrapper.SERVER_ADDRESS)
                        self.robot2ctrl_wrapper.send_2_robotctrl(self.robot2ctrl_wrapper.START_MESSAGE_PickAndInsert)
                        while True:
                            message = self.unix_server.read_data()
                            print(str(message))
                            if message ==  '{"PickAndInsert_MS": "FINISHED"}':
                                print("\n \n \n \n pick and insert has completed \n \n \n \n")
                                self.robot2ctrl_wrapper.unix_client.close_client()
                                break
                        print(" PICK AND INSERT FINISHED . LETS START SCREW PICK AND FASTEN NOW")

                        self.robot2ctrl_wrapper.create_client()
                        self.robot2ctrl_wrapper.connect_2_unix_server(RobotCtrl_Wrapper.SERVER_ADDRESS)
                        self.robot2ctrl_wrapper.send_2_robotctrl(self.robot2ctrl_wrapper.START_MESSAGE_ScrewPickAndFasten)
                        while True:
                            message = self.unix_server.read_data()
                            print(str(message))
                            if message ==  '{"ScrewPickAndFasten_MS": "FINISHED"}':
                                print("\n \n \n \n screw pick and fasten has completed \n \n \n \n")
                                self.robot2ctrl_wrapper.unix_client.close_client()
                                break
                        print("SCREW PICK AND FASTEN FINISHED")
                        print("controller free to service another call")

                        self.robot2coordinator_wrapper.create_client()
                        self.robot2coordinator_wrapper.connect_2_unix_server(Robot2Coordinator_Wrapper.SERVER_ADDRESS)
                        self.robot2coordinator_wrapper.send_2_robot_coordinator(Robot2Coordinator_Wrapper.COMPLETED_MESSAGE)
                        self.robot2coordinator_wrapper.unix_client.close_client()

            except (ConnectionResetError, OSError) as e:
                print(e)

