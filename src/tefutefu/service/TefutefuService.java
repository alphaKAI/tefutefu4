package tefutefu.service;

abstract public class TefutefuService {
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