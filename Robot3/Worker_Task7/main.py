import os
from at7_MS.AT7Controller import AT7Controller



if __name__ == "__main__":
    # execute only if run as a script
    try:
        print("start program")

        wt7 = AT7Controller()
        wt7.start_working()
    except Exception as e:
        print("Error: unable to start thread")
        print(e)
