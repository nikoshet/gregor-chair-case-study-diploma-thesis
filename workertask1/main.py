import os
from at1_MS.AT1Controller import AT1Controller


if __name__ == "__main__":
    # execute only if run as a script
    try:
        print("start program")

        wt1 = AT1Controller()
        wt1.start_working()
    except Exception as e:
        print("Error: unable to start thread")
        print(e)


