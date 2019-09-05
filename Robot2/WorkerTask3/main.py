import os
from at3_MS.AT3Controller import AT3Controller


if __name__ == "__main__":
    # execute only if run as a script
    try:
        print("start program")

        wt3 = AT3Controller()
        wt3.start_working()
    except Exception as e:
        print("Error: unable to start thread")
        print(e)

