import socket
import sys
import os


class UnixClient:

    server_address = ""
    sock = ""
    connection = ""
    client_address = ""

    def __init__(self, server_address):
        self.server_address = server_address
        self.sock = socket.socket(socket.AF_UNIX, socket.SOCK_STREAM)
        #UnixClient.connect_client(self, str(self.server_address))

    def send_data(self, message):
        self.sock.sendall(message.encode())
        print('client sending "%s"' % message, file=sys.stderr)

    def read_data(self):
        data = self.sock.recv(128)
        if not data:
            print('not message from unix server socket')
        print('client received "%s"' % data.decode(), file=sys.stderr)
        return data.decode()

    def connect_client(self, server_address):
        print("client started")
        if os.path.exists(server_address):
            print("path already exists", file=sys.stderr)
            print("connecting to %s" % server_address, file=sys.stderr)
            try:
                self.sock.connect(server_address)
            except socket.error as msg:
                print(msg, file=sys.stderr)
                sys.exit(1)
        else:
            print(" socket server is not on")

    def close_client(self):
        print('closing socket', file=sys.stderr)
        self.sock.close()





