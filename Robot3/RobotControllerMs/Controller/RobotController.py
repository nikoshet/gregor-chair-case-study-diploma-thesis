"""
this class has all the provided services from this microservice .
PICK AND PLACE , PICK AND INSERT , SCREW PICK AND FASTEN , MOVE , PICK AND PRESS , PICK AND FLIP AND PRESS ,
are some of them.

"""

from unix_sockets.unix_server import UnixServer
from unix_sockets.unix_client import UnixClient
from Services.Move import Move
from Services.PickAndPlace import PickAndPlace
from Services.PickAndInsert import PickAndInsert
from Services.ScrewPickAndFasten import ScrewPickAndFasten
from Services.PickAndPress import PickAndPress


class RobotController:
    SERVER_ADDRESS = "/tmp/robot3ctrl.sock"

    def __init__(self):
        self.unix_server = UnixServer(self.SERVER_ADDRESS)
        self.unix_server.start()
        print("\n \n Unix Server of Robot Controller has just started \n \n")

    #########################################################
    #                            CALL MICROSERVICES
    #########################################################

    def call_pick_and_place(self, device):
        pickandplace = PickAndPlace()
        return pickandplace.start_working(device)  # pick and place service

    def call_pick_and_insert(self, device):
        pickandinsert = PickAndInsert()
        return pickandinsert.start_working(device)  # pick and insert service

    def call_screw_pick_and_fasten(self, device):
        screwpickandfasten = ScrewPickAndFasten()
        return screwpickandfasten.start_working(device)  # screw pick and fasten service

    def call_pick_and_press(self, device):
        pickandpress = PickAndPress()
        return pickandpress.start_working(device)  # pick and press service

    def call_move(self, message):
        move = Move()
        return move.start_working(message)  # move service

    def call_pick_and_flip_press(self):
        return "pick and flip and press service "  # pick and flip and press service

    def stop_controller(self):
        return " ad"

    def wrong_message_response(self):
        return "wrong messsage received"

    @staticmethod
    def call_microservice(self, service):
        switcher = {
            "pick_and_place": self.call_pick_and_place(),
            "pick_and_insert": self.call_pick_and_insert(),
            "screw_pick_And_fasten": self.call_screw_pick_and_fasten(),
            "pick_and_press": self.call_pick_and_press(),
            "move": self.call_move(),
            "pick_and_flip_press": self.call_pick_and_flip_press(),
        }
        return switcher.get(service, self.wrong_message_response())

    def start_controller(self, device):
        while True:
            try:
                message_received = self.unix_server.read_data()
                print(message_received)
                message = message_received
                sender_address = message['sender']
                print(sender_address)
                if "PickAndInsert" in message_received:
                    response = self.call_pick_and_insert(device)
                    if "FINISHED" in response:
                        print("controller free to service another call")
                        self.unix_client = UnixClient(str(sender_address))
                        self.unix_client.connect_client(str(sender_address))
                        self.unix_client.send_data(response)
                        self.unix_client.close_client()
                if "PickAndPress" in message_received:
                    response = self.call_pick_and_press(device)
                    if "FINISHED" in response:
                        print("controller free to service another call")
                        self.unix_client = UnixClient(str(sender_address))
                        self.unix_client.connect_client(str(sender_address))
                        self.unix_client.send_data(response)
                        self.unix_client.close_client()
                if "ScrewPickAndFasten" in message_received:
                    response = self.call_screw_pick_and_fasten(device)
                    if "FINISHED" in response:
                        print("controller free to service another call")
                        self.unix_client = UnixClient(str(sender_address))
                        self.unix_client.connect_client(str(sender_address))
                        self.unix_client.send_data(response)
                        self.unix_client.close_client()
                if "Move" in message_received:
                    response = self.call_move(message_received)
                    if "FINISHED" in response:
                        print("controller free to service another call")
                        self.unix_client = UnixClient(str(sender_address))
                        self.unix_client.connect_client(str(sender_address))
                        self.unix_client.send_data(response)
                        self.unix_client.close_client()

            except (ConnectionResetError, OSError) as e:
                print(e)
