
from unix_server import UnixServer
from unix_client import UnixClient
from multiprocessing import Queue
from MoveMs import MoveMs
from PickAndPlace import PickAndPlace
from PickAndInsert import PickAndInsert
from ScrewPickAndFasten import ScrewPickAndFasten
import json


class RobotController:

    SERVER_ADDRESS = "/tmp/robot2ctrl.sock"

    def __init__(self):
        self.unix_server = UnixServer(self.SERVER_ADDRESS)
        self.unix_server.start()
       #self.unix_client = UnixClient(self.AT1_ADDRESS)
        print("\n \n Unix Server of Robot Controller has just started \n \n")

#########################################################
#                            CALL MICROSERVICES
#########################################################

    def call_pick_and_place(self):
        pickandplace = PickAndPlace()
        return pickandplace.start_working() #"pick and place service "

    def call_pick_and_insert(self):
        pickandinsert = PickAndInsert()
        return pickandinsert.start_working() #"pick and insert service "

    def call_screw_pick_and_fasten(self):
        screwpickandfasten = ScrewPickAndFasten()
        return screwpickandfasten.start_working() #"pick and insert service "

    def call_pick_and_press(self):
        return "screw pick and press service "

    def call_move(self):
        return MoveMs.MoveMs.run_moveMs()

    def call_pick_and_flip_press(self):
        return "pick and flip and press service "

    def stop_controller(self):
        return " ad"

    def wrong_message_response(self):
        return "wrong messsage received"

    @staticmethod
    def call_microservice(self,service):
        switcher = {
            "pick_and_place": self.call_pick_and_place(),
            "pick_and_insert": self.call_pick_and_insert(),
            "screw_pick_And_fasten": self.call_screw_pick_and_fasten(),
            "pick_and_press": self.call_pick_and_press(),
            "move": self.call_move(),
            "pick_and_flip_press": self.call_pick_and_flip_press(),
        }
        return switcher.get(service , self.wrong_message_response())


    def start_controller(self):
       # q = Queue()
        #sem = threading.Semaphore()
        while True:
            try:
                message_received = self.unix_server.read_data()
                print(message_received)
                message = json.loads(message_received)
                sender_address = message['sender']
                print(sender_address)
           #     q.put(message_received)
         #       sem.acquire()
                if "PickAndPlace" in message_received:
                    response = self.call_pick_and_place()
                    if "FINISHED" in response:
                        print("controller free to service another call")
                        self.unix_client = UnixClient(str(sender_address))
                        self.unix_client.connect_client(str(sender_address))
                        self.unix_client.send_data(response)
                        self.unix_client.close_client()
                        print("\n \n \n \n ok")
                if "PickAndInsert" in message_received:
                    response = self.call_pick_and_insert()
                    if "FINISHED" in response:
                        print("controller free to service another call")
                        self.unix_client = UnixClient(str(sender_address))
                        self.unix_client.connect_client(str(sender_address))
                        self.unix_client.send_data(response)
                        self.unix_client.close_client()
                if "ScrewPickAndFasten" in message_received:
                    response = self.call_screw_pick_and_fasten()
                    if "FINISHED" in response:
                        print("controller free to service another call")
                        self.unix_client = UnixClient(str(sender_address))
                        self.unix_client.connect_client(str(sender_address))
                        self.unix_client.send_data(response)
                        self.unix_client.close_client()
                if "Move" in message_received:
                    response = self.call_move()
                    if "FINISHED" in response:
                        print("controller free to service another call")
                        self.unix_client = UnixClient(str(sender_address))
                        self.unix_client.connect_client(str(sender_address))
                        self.unix_client.send_data(response)
                        self.unix_client.close_client()
          #          sem.release()

            except (ConnectionResetError, OSError) as e:
                print(e)
