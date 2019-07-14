from unix_sockets.unix_server import UnixServer
from unix_sockets.unix_client import UnixClient
import json
from enum import Enum
import time

class PickAndInsert:

    START_MESSAGE = '{"PickAndInsert_MS": "STARTED"}'
    COMPLETED_MESSAGE = '{"PickAndInsert_MS": "FINISHED"}'

    def __init__(self):
        print("\n \n Unix Server of PickAndInsert has just started \n \n")

    def start_working(self):
     #while True:
            try:
                #message_received = self.unix_server.read_data()
                #print(message_received)
                #if json.loads(message_received) == "{\"PickAndPlace_MS\": \"STARTED\"}":
                    print('doing PickAndInsert')
                    time.sleep(5)
                    print('PickAndInsert finished..')
                    print('Replying to WT server...')
                    encoded_response = self.COMPLETED_MESSAGE
                    #self.unix_client.connect_client(self.WT1_ADDRESS)
                    #self.unix_client.send_data(encoded_response)
                    return encoded_response
                    #break
                #else:
                    #print("den katalabainw")
            except (ConnectionResetError, OSError) as e:
                print(e)


