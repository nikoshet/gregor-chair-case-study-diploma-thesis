import json
import time
import os

from PickAndPlace.Unixsocket import UnixSocket


def main():

    # sock = UnixSocket()

    # Connect the socket to the port where the server is listening
    server_address = '/tmp/robot.sock'
    print('connecting to {}'.format(server_address))

    while True:
        while not os.path.exists(server_address):
            # if sock.connect_ex(server_address) == 0:
            print('Server not available to connect')
            time.sleep(2)
        print('Server available to connect')
        # while True:
        try:
            sock = UnixSocket()
            sock.connect(server_address)
            messageFromWT1 = sock.receiveFromServer()
            # messageFromWT1 = sock.recv(128).decode()
            print("WT1 Server says: {}".format(messageFromWT1))
            # while len(messageFromWT1) <= 0:
            # pass
            if len(messageFromWT1) <= 0:
                # break
                pass
            if json.loads(messageFromWT1) == "{'PickAndPlace_MS','START'}":
                print('doing PickAndPlace')
                print('PickAndPlace finished..')
                print('Replying to WT1 server...')
                response = "{'PickAndPlace_MS','FINISHED'}"
                encoded_response = json.dumps(response).encode()
                sock.sendToServer(encoded_response)
                # sock.sendall(encoded_response)
                print('closing socket')
                sock.close()
                # break
            else:
                print('Wrong message')
                print('closing socket')
                sock.close()
                time.sleep(2)
        except (ConnectionResetError, OSError) as e:
            print(e)
            time.sleep(2)
            # break


if __name__ == "__main__":
    main()
