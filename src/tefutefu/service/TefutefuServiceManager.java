package tefutefu.service;

import tefutefu.message.*;
import tefutefu.util.HashMapUtil;
import twitter4j.Twitter;
import twitter4j.Status;
import java.util.ArrayList;
import java.util.HashMap;

public class TefutefuServiceManager {
  private HashMap<String, TefutefuService>     services           = new HashMap<String, TefutefuService>();
  private HashMapUtil<String, TefutefuService> hashMapUtil      = new HashMapUtil<String, TefutefuService>();
  public  Twitter                              t4j;
  public  TefutefuMessageQueues<Status>        streamStatusQueues = new TefutefuMessageQueues<Status>() {
    @Override
    public void recvReaction(TefutefuMessage<Status> message) {
      //TODO : 他のサービス(と言うかリアクション処理)にたらい回す
      //Status status = message.data;
      System.out.println("recvReaction");

      services.get("TefutefuReactionStore").pushToRecvQueue(message);
      services.get("TefutefuReactionStore").checkRecvQueue();
    }
  };

  public TefutefuServiceManager(Twitter t4j) {
    this.t4j = t4j;
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
        System.out.println("Start service : " + serviceName);
        this.services.get(serviceName).start();
        return true;
      }
    }
  }

  //ユーザーストリームでイベントを読み込んだらこのメソッドが呼ばれる
  //checkRecvQueueで呼ばれるrecvReactionに他のサービスによる反応を記述。
  public void processTweetEvent() {
    this.streamStatusQueues.checkRecvQueue();//Experimental
  }

  public void sendMesseageToService(String targetService, TefutefuMessage<Status> message) {
    if (this.existService(targetService)) {
      this.services.get(targetService).pushToRecvQueue(message);
    }
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
  
  public boolean existService(String name) { return this.hashMapUtil.existValue(this.services, name); }
}
