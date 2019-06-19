import socket
import sys
import os

from Worker_Task1.pickAndInsertWrapper import PickAndInsertWrapper
from Worker_Task1.pickAndPlaceWrapper import PickAndPlaceWrapper
from Worker_Task1.Unixsocket import UnixSocket


def main():

    server_address1 = '/tmp/workertask1ToPickAndPlace_unix.sock'
    server_address2 = '/tmp/workertask1ToPickAndInsert_unix.sock'

    message1 = "{'PickAndPlace_MS','START'}"
    message2 = "{'PickAndInsert_MS','START'}"

    # Make sure the socket does not already exist
    try:
        os.unlink(server_address1)
        os.unlink(server_address2)
    except OSError:
        if os.path.exists(server_address1):
            raise
        if os.path.exists(server_address2):
            raise

    # Create a UDS socket
    sock1 = UnixSocket()
    sock2 = UnixSocket()
    # Bind the socket to the port
    sock1.bind(server_address1)
    sock2.bind(server_address2)
    # Listen for incoming connections
    sock1.listen(1)
    sock2.listen(1)

    wrapper1 = PickAndPlaceWrapper(sock1, message1)
    wrapper2 = PickAndInsertWrapper(sock2, message2)

    try:
        print("Starting Microservices..")

        wrapper1.startPickAndPlace(sock1, message1)
        wrapper2.startPickAndInsert(sock2, message2)

        print("Microservices finished..")
        os.unlink(server_address1)
        os.unlink(server_address2)

    except socket.error as msg:
        print(sys.stderr, msg)


if __name__ == "__main__":
    main()
