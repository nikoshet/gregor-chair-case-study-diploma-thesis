import os
from unix_sockets.unix_client import UnixClient
from at2_MS.AT2Controller import AT2Controller
#import eureka.eureka_client as eureka_client


if __name__ == "__main__":
    # execute only if run as a script
    try:
        print("start program")

       # eureka_client.start()
        wt2 = AT2Controller()
        wt2.start_working()
    except Exception as e:
        print("Error: unable to start thread")
        print(e)
    #    os.unlink("/tmp/at1.sock")


