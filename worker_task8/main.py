import os
from at8_MS.AT8Controller import AT8Controller
#import eureka_client as eureka_client


if __name__ == "__main__":
    # execute only if run as a script
    try:
        print("start program")

       # eureka_client.start()
        wt8 = AT8Controller()
        wt8.start_working()
    except Exception as e:
        print("Error: unable to start thread")
        print(e)
    #    os.unlink("/tmp/at1.sock")


