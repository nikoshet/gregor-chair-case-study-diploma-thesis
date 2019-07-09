import os
from unix_sockets.unix_client import UnixClient
from at3_MS.AT3Controller import AT1Controller
import eureka.eureka_client as eureka_client


if __name__ == "__main__":
    # execute only if run as a script
    try:
        print("start program")

       # eureka_client.start()
        wt1 = AT1Controller()
        wt1.start_working()
    except Exception as e:
        print("Error: unable to start thread")
        print(e)
    #    os.unlink("/tmp/at1.sock")


