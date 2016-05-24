package tefutefu.service;
import tefutefu.message.*;
import twitter4j.Status;
import java.util.ArrayList;
import java.util.HashMap;

public class TefutefuServiceManager {
	private HashMap<String, TefutefuService> services = new HashMap<String, TefutefuService>();
	public TefutefuMessageQueues<Status> streamStatusQueues = new TefutefuMessageQueues<Status>() {
		@Override
		public void recvReaction(TefutefuMessage<Status> message) {
			//他のサービスにたらい回す
			Status status = message.data;
			System.out.println("recvReaction");
			System.out.println("------------------------------------");
			System.out.printf("%s(@%s) : %s",
					status.getUser().getScreenName(),
					status.getUser().getId(),
					status.getText());
			System.out.println("------------------------------------");
		}
	};  
	
	public ArrayList<String> getServiceNames() {
		ArrayList<String> keys = new ArrayList<String>();
		
		if (this.services != null) {
			for (HashMap.Entry<String, TefutefuService> entry : this.services.entrySet()) {
		    	keys.add(entry.getKey());
			}
		}
		
		return keys;
	} 
	
	public boolean addNewService(TefutefuService newService) {
		if (this.existService(newService.serviceName)) {
			return false;
		} else {
			services.put(newService.serviceName, newService);
			return true;
		}
	}
	
	public boolean startService(String serviceName) {
		if (!this.existService(serviceName)) {
			System.out.println("[Error] - There is no service as" + serviceName);
			return false;
		} else {
			if (this.services.get(serviceName).running) {
				return false;
			} else {
				this.services.get(serviceName).start();
				return true;
			}
		}
	}
	
	//イベント監視 
	public void startEventLoop() {
		boolean running = true;
		/*
		 * 現状だとwhile loopとかいう最悪(CPUリソースを食い潰す)な手法なのでEven-drivenな実装を参考にすること
		 * */
		do {
			this.checkServices();
			//イベントループを実装
			this.streamStatusQueues.checkRecvQueue();
		} while (running);
	}
	
	public void checkServices() {
		if (this.services != null) {
			for (HashMap.Entry<String, TefutefuService> entry : this.services.entrySet()) {
				if (entry.getValue().running == false) {
					entry.getValue().start();
					System.out.println("The service of " + entry.getKey() + " was started");
				}
			}	
		}
	}
	
	public boolean existService(String name) {
		return this.getServiceNames().contains(name);
	}
}
