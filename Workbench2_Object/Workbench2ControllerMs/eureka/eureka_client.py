import py_eureka_client.eureka_client as eureka_client


def start():
    try:
        your_rest_server_port = 9099
        # The flowing code will register your server to eureka server and also start to send heartbeat every 30 seconds
        eureka_client.init(eureka_server="http://localhost:9000/eureka",
                           app_name="ROBOT_CONTROLLER",
                           instance_port=your_rest_server_port)
    except Exception:
        print("Error: connect to eureka server")


