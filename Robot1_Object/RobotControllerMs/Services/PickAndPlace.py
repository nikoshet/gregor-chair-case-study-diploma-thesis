from unix_sockets.unix_server import UnixServer
from unix_sockets.unix_client import UnixClient
import json
from enum import Enum
import time

class PickAndPlace:


    START_MESSAGE = '{"PickAndPlace_MS": "STARTED"}'
    COMPLETED_MESSAGE = '{"PickAndPlace_MS": "FINISHED"}'

    def __init__(self):
        print("\n \n Unix Server of PickAndPlace has just started \n \n")

    def start_working(self):
        #while True:
            try:
                #message_received = self.unix_server.read_data()
                #print(message_received)
                #if json.loads(message_received) == self.START_MESSAGE: # "{\"PickAndPlace_MS\": \"STARTED\"}":
                    print('doing PickAndPlace')
                    time.sleep(5)
                    print('PickAndPlace finished..')
                    print('Replying to WT1 server...')
                    response = self.COMPLETED_MESSAGE
                    #self.unix_client.connect_client(self.WT1_ADDRESS)
                    #self.unix_client.send_data(encoded_response)
                    return response
                    #break
                #else:
                    #print("den katalabainw")
            except (ConnectionResetError, OSError) as e:
                print(e)

