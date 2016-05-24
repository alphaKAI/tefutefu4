package tefutefu.coreServices;
import java.util.regex.Pattern;
import java.util.regex.Matcher;
import java.util.ArrayList;
import tefutefu.message.*;
import twitter4j.Status;

public class TefutefuReaction extends TefutefuMessageQueues<Status> {
  public  TefutefuReactionTypes            type;
  // フォールスローするかどうか。 すなわち、このReaction後に他のReactionが動作することを許すかどうか
  public  boolean                          fallthrough     = true;
  public  ArrayList<TefutefuReactionTypes> fallthroughList = new ArrayList<TefutefuReactionTypes>();
  private Pattern                          pattern;

  public TefutefuReaction(TefutefuReactionTypes type, String regexString) {
    this.type    = type;
    this.pattern = Pattern.compile(regexString);
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
  
  public boolean match(Status status) {
    return this.pattern.matcher(status.getText()).find();
  }
}
