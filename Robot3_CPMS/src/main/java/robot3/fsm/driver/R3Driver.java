package robot3.fsm.driver;

import java.util.concurrent.ArrayBlockingQueue;

import robot3.fsm.Robot3Ctrl;

public class R3Driver {

    protected ArrayBlockingQueue<Robot3Ctrl> itsCtrlEq;

	public void wait4pos3() {
	/*	RobotInstance.event2sim = new JSONObject()
				.put("event2sim", "R3_WAIT_4_W1")
				.put("value", "").toString();
		RobotInstance.fireResourcesChange(20);
		R3_Main.simulator.R3.setBackground(Color.BLUE);
		MessageRequest mes = new MessageRequest(Source.R3,Target.SIMULATOR, Command.R3_WAIT_4_W1);
		Gson gson = new Gson();
		String json = gson.toJson(mes);
		new Client(Config.PORT_SIMULATOR,Config.LOCALHOST,json);*/

	}

	public void doSubAss3(){
//		RobotInstance.event2sim = new JSONObject()
//				.put("event2sim", "R3_WORK_ON_W1")
//				.put("value", "").toString();
//		RobotInstance.fireResourcesChange(20);
		/*R3_Main.simulator.R3.setBackground(Color.GREEN);
		MessageRequest mes = new MessageRequest(Source.R3,Target.SIMULATOR, Command.R3_WORK_ON_W1);
		Gson gson = new Gson();
		String json = gson.toJson(mes);
		new Client(Config.PORT_SIMULATOR,Config.LOCALHOST,json);*/

	}

}