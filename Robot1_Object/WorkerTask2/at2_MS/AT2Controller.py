from unix_sockets.unix_server import  UnixServer
from unix_sockets.unix_client import  UnixClient
from Communication_Wrapper.robot1ctrl_wrapper import Robot1Ctrl_Wrapper
from Communication_Wrapper.robot1Coordinator_Wrapper import Robot1Coordinator_Wrapper
import json
#import eureka.eureka_client as eureka_client

class AT2Controller:

    robot1ctrl_wrapper= Robot1Ctrl_Wrapper()
    robot1coordinator_wrapper= Robot1Coordinator_Wrapper()
    SERVER_ADDRESS = "/tmp/at2.sock"
    def __init__(self):
        self.unix_server = UnixServer(self.SERVER_ADDRESS)
        self.unix_server.start()
        print("\n \n Unix Server of Assembly task2 has just started \n \n")


    def start_working(self):
        while True:
            try:
                message_received= self.unix_server.read_data()
                if message_received!='' :
                    message = json.loads(message_received)
                    sender_address = message['sender']
                    print(message)

                    if sender_address == "coordinator":  # mono start mporei na steilei o coordinator
                        self.robot1ctrl_wrapper.create_client()
                        self.robot1ctrl_wrapper.connect_2_unix_server(Robot1Ctrl_Wrapper.SERVER_ADDRESS)
                        self.robot1ctrl_wrapper.send_2_robotctrl(self.robot1ctrl_wrapper.START_MESSAGE_PickAndPlace)
                        while True:
                            message = self.unix_server.read_data()
                            print(str(message))
                            if message ==  '{"PickAndPlace_MS": "FINISHED"}':
                                print("\n pick and place has completed \n")
                                self.robot1ctrl_wrapper.unix_client.close_client()
                                break
                        print(" PICK AND PLACE FINISHED . LETS START PICK AND INSERT NOW")

                        self.robot1ctrl_wrapper.create_client()
                        self.robot1ctrl_wrapper.connect_2_unix_server(Robot1Ctrl_Wrapper.SERVER_ADDRESS)
                        self.robot1ctrl_wrapper.send_2_robotctrl(self.robot1ctrl_wrapper.START_MESSAGE_PickAndFlipAndPress)
                        while True:
                            message = self.unix_server.read_data()
                            print(str(message))
                            if message ==  '{"PickAndFlipAndPress_MS": "FINISHED"}':
                                print("\n \n \n \n pick and flip and press has completed \n \n \n \n")
                                self.robot1ctrl_wrapper.unix_client.close_client()
                                break
                        print("PICK AND FLIP AND PRESS FINISHED")
                        print("controller free to service another call")

                        self.robot1coordinator_wrapper.create_client()
                        self.robot1coordinator_wrapper.connect_2_unix_server(Robot1Coordinator_Wrapper.SERVER_ADDRESS)
                        self.robot1coordinator_wrapper.send_2_robot_coordinator(Robot1Coordinator_Wrapper.COMPLETED_MESSAGE)
                        self.robot1coordinator_wrapper.unix_client.close_client()

            except (ConnectionResetError, OSError) as e:
                print(e)

