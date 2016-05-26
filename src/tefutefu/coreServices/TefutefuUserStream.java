package tefutefu.coreServices;
import tefutefu.service.*;
import tefutefu.message.*;
import twitter4j.Status;
import twitter4j.TwitterStream;
import twitter4j.TwitterStreamFactory;
import twitter4j.UserStreamAdapter;
import twitter4j.conf.Configuration;

class TefutefuUserStreamAdapter extends UserStreamAdapter {
  private TefutefuUserStream tus;
  
  public TefutefuUserStreamAdapter(TefutefuUserStream tus) {
    this.tus = tus;
  }
  
  @Override public void onStatus(Status status) {
    System.out.println("Get a new status");
    this.tus.processStatus(status);
  }  
}

public class TefutefuUserStream extends TefutefuService {
  private TefutefuServiceManager tsm;
  private TwitterStreamFactory   twitterStreamFactory;
  private TwitterStream          twitterStream;

  public TefutefuUserStream(TefutefuServiceManager tsm) {
    this.tsm         = tsm;
    this.type        = ServiceType.Streamer;
    this.serviceName = "TefutefuUserStream";
  }

  public void initialize(Configuration conf) {
    twitterStreamFactory = new TwitterStreamFactory(conf);
    twitterStream        = twitterStreamFactory.getInstance();
  }

  @Override
  public void start() {
    this.running = true;

    twitterStream.addListener(new TefutefuUserStreamAdapter(this));
    twitterStream.user();
  }
  
  @Override
  public void stop() {
    this.running = false;
  }
  
  protected void processStatus(Status status) {
    this.tsm.streamStatusQueues.pushToRecvQueue(new TefutefuMessage<Status>(status));
    this.tsm.processTweetEvent();
  }
}