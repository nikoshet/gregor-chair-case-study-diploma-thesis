import json


class PickAndInsertWrapper(object):

    def __init__(self, socket, messageToSend):
        self.socket = socket
        self.messageToSend = messageToSend

    def startPickAndInsert(self, socket, messageToSend):
        while True:
            # Wait for a connection
            print("waiting for a connection from PickAndInsert")
            connection, client_address = socket.accept()
            try:
                print('connection from {}'.format(client_address))
                print("Starting client PickAndInsert_MS")
                encoded_message = json.dumps(messageToSend).encode()
                socket.send(connection, encoded_message)
                #connection.sendall(encoded_message)
                print("Waiting for Client's PickAndInsert_MS response..")
                response = socket.receive(connection)
                #response = connection.recv(128).decode()
                while response == "":
                    pass
                if response:
                    print("Client's PickAndInsert_MS response:  '{}'".format(response))
                    if json.loads(response) == "{'PickAndInsert_MS','FINISHED'}":
                        print("PickAndInsert finished..")
                        break
                        # print ('sending data back to the client')
                else:
                    pass
                    # print ('no more data from {}'.format(client_address))
                    # break
            finally:
                # Clean up the connection
                socket.closeConnection(connection)
                #connection.close()