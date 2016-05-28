package tefutefu.bot;

import tefutefu.reactions.*;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.User;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by alphakai on 2016/05/26.
 */

public class Egosa extends TefutefuReaction {
  private String  myScreenName;
  private String  myName;
  private String  patternString;
  private Twitter t4j;

  public Egosa(Twitter t4j) {
    super(TefutefuReactionTypes.Fav);
    this.t4j          = t4j;
    this.reactionName = "Egosa";
    try {
      User user          = t4j.verifyCredentials();
      this.myScreenName  = user.getScreenName();
      this.myName        = user.getName();
      this.patternString = this.myScreenName + "|" + this.myName;
    } catch (Exception e) {}
  }

  public TefutefuReactionContainer process(Status status) {
    TefutefuReactionContainer trc = new TefutefuReactionContainer(
        this.type,
        new TefutefuReactionTarget(status.getId(), status.getUser().getScreenName()),
        this
      );

    return trc;
  }

  public boolean match(Status status) {
    Pattern pattern = Pattern.compile(this.patternString);
    Matcher m = pattern.matcher(status.getText());

    return m.find();
  }

  public void processReturnedJson(TefutefuTwitterStatuses returnedValue) {}

  public void afterProcess() {}
}
