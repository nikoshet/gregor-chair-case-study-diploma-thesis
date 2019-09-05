import socket
import sys
import os
import threading
import json

class UnixServer (threading.Thread):

    server_address = ""
    sock = ""
    connection = ""
    client_address = ""

    def __init__(self, server_address):
        threading.Thread.__init__(self)
        self.server_address = server_address
        try:
            os.unlink(self.server_address)
        except OSError:
            if os.path.exists(self.server_address):
                print("path already exists", file=sys.stderr)
                raise
        self.sock = socket.socket(socket.AF_UNIX, socket.SOCK_STREAM)
        print("starting up on %s" % self.server_address, file=sys.stderr)
        self.sock.bind(self.server_address)

        # Listen for incoming connections
        self.sock.listen(1)

   # def run(self):
        #self.start_server()

    # Bind the socket to the port
    def bind(self, server_address):
        print('starting up on {}'.format(server_address))
        self.sock.bind(server_address)

    # Listen for incoming connections
    def listen(self, noOfConnections):
        self.sock.listen(noOfConnections)

    # Accept incoming connections
    def accept(self):
        connection, client_address = self.sock.accept()
        return connection, client_address

    # Send message to client
    @staticmethod
    def send(connection, encoded_message):
        connection.sendall(encoded_message)

    # Receive message from client
    @staticmethod
    def receive(connection):
        return connection.recv(128).decode()

    # Close Server connection
    @staticmethod
    def close_connection(connection):
        connection.close()

    def read_data(self):
        connection = self.sock.accept()[0]
        print(connection)
        data = connection.recv(128).decode()
        if not data:
            print('not message from unix client socket')
        print('server received "%s"' % data, file=sys.stderr)
        return json.loads(data)

