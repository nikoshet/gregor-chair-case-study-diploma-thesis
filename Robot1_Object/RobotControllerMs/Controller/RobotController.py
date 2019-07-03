"""
this class has all the provided services from this microservice .
PICK AND PLACE , PICK AND INSERT , SCREW PICK AND FASTEN , MOVE , PICK AND PRESS , PICK AND FLIP AND PRESS ,
are some of them.

actually is something like coordinator.

"""


from unix_sockets.unix_server import UnixServer
from unix_sockets.unix_client import UnixClient
from multiprocessing import Queue
from Services.MoveMs import MoveMs
from Services.PickAndPlace import PickAndPlace
from Services.PickAndInsert import PickAndInsert

import threading
import json


class RobotController:

    unix_server: UnixServer
    unix_client: UnixClient
    SERVER_ADDRESS = "/tmp/robot1ctrl.sock"

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
        return "screw pick and fasten service "

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
                sender_address = message_received["sender"]
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
                        print("\n \n \n \n paparas")
                if "PickAndInsert" in message_received:
                    response = self.call_pick_and_insert()
                    if "FINISHED" in response:
                        print("controller free to service another call")
                        self.unix_client = UnixClient(str(sender_address))
                        self.unix_client.connect_client(str(sender_address))
                        self.unix_client.send_data(response)
                        self.unix_client.close_client()
          #          sem.release()
                if "PickAndPress" in message_received:
                    response = self.call_pick_and_press()
                    if "FINISHED" in response:
                        print("controller free to service another call")
                        self.unix_client = UnixClient(str(sender_address))
                        self.unix_client.connect_client(str(sender_address))
                        self.unix_client.send_data(response)
                        self.unix_client.close_client()
                        print("\n \n \n \n paparas")
                if "PickAndFlipAndPress" in message_received:
                    response = self.call_pick_and_flip_press()
                    if "FINISHED" in response:
                        print("controller free to service another call")
                        self.unix_client = UnixClient(str(sender_address))
                        self.unix_client.connect_client(str(sender_address))
                        self.unix_client.send_data(response)
                        self.unix_client.close_client()
            #          sem.release()

            except (ConnectionResetError, OSError) as e:
                print(e)
