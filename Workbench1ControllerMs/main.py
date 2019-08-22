import os
from unix_sockets.unix_client import UnixClient
from Controller.W1Controller import W1Controller
#import eureka.eureka_client as eureka_client
import sys

if __name__ == "__main__":
    # execute only if run as a script
    try:
        print("start program")
        device = "{}".format(sys.argv[1])
        print("Device: " + device)

        #eureka_client.start()
        w1 = W1Controller()
        w1.start_controller(device)
    except Exception as e:
        print("Error: unable to start thread")
        print(e)
    #    os.unlink("/tmp/at1.sock")



