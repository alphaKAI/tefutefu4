package tefutefu.service;
import tefutefu.message.*;
import twitter4j.Status;

abstract public class TefutefuService<T> extends TefutefuMessageQueues<T> {
  public ServiceType type;
  public String      serviceName;
  public boolean     running;

  public TefutefuService(ServiceType type, String name) {
    this.type        = type;
    this.serviceName = name;
  }

  public abstract void start();
  public abstract void stop();
}