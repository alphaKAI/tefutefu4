package tefutefu.service;
import tefutefu.message.*;
import twitter4j.Status;

abstract public class TefutefuService extends TefutefuMessageQueues<Status> {
  public ServiceType type;
  public String      serviceName;
  public boolean     running;

  public void initialize(ServiceType type, String name) {
    this.type        = type;
    this.serviceName = name;
  }

  public abstract void start();

  public abstract void stop();
}