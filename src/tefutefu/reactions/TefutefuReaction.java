package tefutefu.reactions;
import java.util.ArrayList;

import tefutefu.commons.Importance;
import tefutefu.message.*;
import twitter4j.Status;

public abstract class TefutefuReaction extends TefutefuMessageQueues<Status> {
  public String                           reactionName;
  public TefutefuReactionTypes            type;
  // フォールスローするかどうか。 すなわち、このReaction後に他のReactionが動作することを許すかどうか
  public boolean                          fallthrough      = true;
  public boolean                          limited          = false;//limitedが有効な場合、以下のフォロースローリストにないtypeは弾かれる
  public ArrayList<TefutefuReactionTypes> fallthroughList  = new ArrayList<TefutefuReactionTypes>();
  public Importance                       importance       = Importance.MID;
  public boolean                          hasAfterProcess  = false;

  public TefutefuReaction(TefutefuReactionTypes type) {
    this.type    = type;
  }
  
  public void fallthroughEnable() {
    this.fallthrough = true;
  }
  
  public void fallthroughDisable() {
    this.fallthrough = false;
  }
  
  public void addFallthroughList(TefutefuReactionTypes type) {
    this.fallthroughList.add(type);
  }
  
  public void setFallthroughList(ArrayList<TefutefuReactionTypes> types) {
    for (TefutefuReactionTypes type : types) {
      this.addFallthroughList(type);
    }
  }
  
  public abstract boolean match(Status status);

  public abstract TefutefuReactionContainer process(Status status);

  public abstract void  processReturnJson(Status status);
}
