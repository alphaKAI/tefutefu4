package tefutefu.reactions;

import twitter4j.Status;

import java.util.ArrayList;

/**
 * Created by alphakai on 2016/05/28.
 */
public class TefutefuTwitterStatuses {
  public ArrayList<Status> statuses;
  public boolean hasStatuses;

  public TefutefuTwitterStatuses() {
    this.statuses    = new ArrayList<>();
    this.hasStatuses = false;
  }

  public void addNewStatus(Status status) {
    if (this.hasStatuses == false) {
      this.hasStatuses = true;
    }

    statuses.add(status);
  }
}
