import os
from at6_MS.AT6Controller import AT6Controller
#import eureka_client as eureka_client


if __name__ == "__main__":
    # execute only if run as a script
    try:
        print("start program")

       # eureka_client.start()
        wt6 = AT6Controller()
        wt6.start_working()
    except Exception as e:
        print("Error: unable to start thread")
        print(e)
    #    os.unlink("/tmp/at1.sock")
