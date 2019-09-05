import os
from at8_MS.AT8Controller import AT8Controller


if __name__ == "__main__":
    # execute only if run as a script
    try:
        print("start program")

        wt8 = AT8Controller()
        wt8.start_working()
    except Exception as e:
        print("Error: unable to start thread")
        print(e)


