import os
from at6_MS.AT6Controller import AT6Controller


if __name__ == "__main__":
    # execute only if run as a script
    try:
        print("start program")

        wt6 = AT6Controller()
        wt6.start_working()
    except Exception as e:
        print("Error: unable to start thread")
        print(e)

