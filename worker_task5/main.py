import os
from at5_MS.AT5Controller import AT5Controller
#import eureka.eureka_client as eureka_client


if __name__ == "__main__":
    # execute only if run as a script
    try:
        print("start program")

       # eureka_client.start()
        wt5 = AT5Controller()
        wt5.start_working()
    except Exception as e:
        print("Error: unable to start thread")
        print(e)
    #    os.unlink("/tmp/at1.sock")


