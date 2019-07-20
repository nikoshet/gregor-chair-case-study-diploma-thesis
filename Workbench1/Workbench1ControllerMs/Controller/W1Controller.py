from Communication_Wrapper.W1Coordinator_Wrapper import W1Coordinator_Wrapper
from unix_sockets.unix_server import UnixServer
from unix_sockets.unix_client import UnixClient
from Services.Rotate import Rotate

class W1Controller:

    SERVER_ADDRESS = "/tmp/w1ctrl.sock"
    w1_wrapper = W1Coordinator_Wrapper()

    def __init__(self):
        self.unix_server = UnixServer(self.SERVER_ADDRESS)
        self.unix_server.start()
        print("\n \n Unix Server of W1 Controller has just started \n \n")

#########################################################
#                            CALL MICROSERVICES
#########################################################

    def call_rotate(self):
        rotate = Rotate()
        return rotate.start_working()



    def start_controller(self):
        while True:
            try:
                message_received = self.unix_server.read_data()
                print(message_received)

                if "ROTATE" in message_received:
                    response = self.call_rotate()
                    if "FINISHED" in response:
                        print("controller free to service another call")
                        self.w1_wrapper.create_client()
                        self.w1_wrapper.connect_2_unix_server(W1Coordinator_Wrapper.SERVER_ADDRESS)
                        self.w1_wrapper.send_2_coordinator(response)
                        self.w1_wrapper.unix_client.close_client()

            except (ConnectionResetError, OSError) as e:
                print(e)
