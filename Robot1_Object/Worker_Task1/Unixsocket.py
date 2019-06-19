import socket


class UnixSocket:

    # Create a UDS socket
    def __init__(self, sock=None):
        if sock is None:
            self.sock = socket.socket(
                socket.AF_UNIX, socket.SOCK_STREAM)
        else:
            self.sock = sock

    # --------- Server Methods --------- #

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
    def closeConnection(connection):
        connection.close()

    # --------- Client Methods --------- #

    # Connect to server address
    def connect(self, server_address):
        self.sock.connect(server_address)

    # Send message to server
    def sendToServer(self, encoded_message):
        self.sock.sendall(encoded_message)

    # Receive message from server
    def receiveFromServer(self):
        return self.sock.recv(128).decode()

    # Close connection
    def close(self):
        self.sock.close()
