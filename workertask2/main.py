import os
from at2_MS.AT2Controller import AT2Controller


if __name__ == "__main__":
    # execute only if run as a script
    try:
        print("start program")

        wt2 = AT2Controller()
        wt2.start_working()
    except Exception as e:
        print("Error: unable to start thread")
        print(e)

