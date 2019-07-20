import py_eureka_client.eureka_client as eureka_client


def start():
    try:
        your_rest_server_port = 9090
        # The flowing code will register your server to eureka server and also start to send heartbeat every 30 seconds
        eureka_client.init(eureka_server="http://localhost:9000/eureka",
                           app_name="WORKER_TASK_1",
                           instance_port=your_rest_server_port)

    except Exception:
        print("Error: connect to eureka server")

def getApp(Appname):
     print(Appname)
     try:
         app=eureka_client.get_application(eureka_server="http://localhost:9000/eureka",
                               app_name="PICK_AND_INSERT").name
         print("eureka returns: " + app)
         return app
     except Exception:
         print("Error: unable to find registered microservice with this name --> "+ Appname)
