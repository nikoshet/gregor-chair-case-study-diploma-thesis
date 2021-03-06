from unix_sockets.unix_client import UnixClient
import json


class Robot3Coordinator_Wrapper:

    COMPLETED_MESSAGE = "AT8_FINISHED"
    SERVER_ADDRESS = '/tmp/robot3coordinator.sock'

    def create_client(self):
        self.unix_client = UnixClient(self.SERVER_ADDRESS)

    def send_2_robot_coordinator(self, message):
        self.unix_client.send_data(str(message))

    def read_from_robot_coordinator(self):
        self.unix_client.read_data()

    def connect_2_unix_server(self, server_address):
        self.unix_client.connect_client(server_address)


