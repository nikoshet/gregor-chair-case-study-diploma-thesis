from unix_sockets.unix_client import UnixClient
import json


class Robot3Ctrl_Wrapper:

    START_MESSAGE_PickAndPlace = '{"PickAndPlace": "STARTED","sender": "/tmp/at8.sock"}'
    COMPLETED_MESSAGE_PickAndPlace = '{"PickAndPlace": "FINISHED","sender": "/tmp/at8.sock"}'
    START_MESSAGE_PickAndInsert = '{"PickAndInsert": "STARTED","sender": "/tmp/at8.sock"}'
    COMPLETED_MESSAGE_PickAndInsert = '{"PickAndInsert": "FINISHED","sender": "/tmp/at8.sock"}'
    START_MESSAGE_ScrewPickAndFasten = '{"ScrewPickAndFasten": "STARTED","sender": "/tmp/at8.sock"}'
    COMPLETED_MESSAGE_ScrewPickAndFasten = '{"ScrewPickAndFasten": "FINISHED","sender": "/tmp/at8.sock"}'
    START_MESSAGE_PickAndFlipAndPress = '{"PickAndFlipAndPress": "STARTED","sender": "/tmp/at8.sock"}'
    COMPLETED_MESSAGE_PickAndFlipAndPress = '{"PickAndFlipAndPress": "FINISHED","sender": "/tmp/at8.sock"}'
    SERVER_ADDRESS = '/tmp/robot3ctrl.sock'

    def create_client(self):
        self.unix_client = UnixClient(self.SERVER_ADDRESS)

    def send_2_robotctrl(self, message):
        self.unix_client.send_data(message)

    def read_from_robot1(self):
        self.unix_client.read_data()

    def connect_2_unix_server(self, server_address):
        self.unix_client.connect_client(server_address)


