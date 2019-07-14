from unix_sockets.unix_server import UnixServer
from unix_sockets.unix_client import UnixClient
import json


class PickAndInsertController:

    START_MESSAGE = '{"PickAndInsert_MS": "STARTED"}'
    COMPLETED_MESSAGE = '{"PickAndInsert_MS": "FINISHED"}'
    unix_server: UnixServer
    unix_client: UnixClient
    SERVER_ADDRESS = "/tmp/pickandinsert.sock"
    WT1_ADDRESS = "/tmp/wt1.sock"

    def __init__(self):
        self.unix_server = UnixServer(self.SERVER_ADDRESS)
        self.unix_server.start()
        self.unix_client = UnixClient(self.WT1_ADDRESS)
        print("\n \n Unix Server of PickAndPlace has just started \n \n")

    def start_working(self):
        while True:
            try:
                message_received = self.unix_server.read_data()
                print(message_received)
                if json.loads(message_received) == "{\"PickAndInsert_MS\": \"STARTED\"}":
                    print('doing PickAndInsert')
                    print('PickAndInsert finished..')
                    print('Replying to WT1 server...')
                    encoded_response = json.dumps(self.COMPLETED_MESSAGE).encode()
                    self.unix_client.connect_client(self.WT1_ADDRESS)
                    self.unix_client.send_data(encoded_response)
                    #break
                else:
                    print("den katalabainw")
            except (ConnectionResetError, OSError) as e:
                print(e)
        #print("\n \n \n \n worker task 1 has just completed \n \n \n \n")

