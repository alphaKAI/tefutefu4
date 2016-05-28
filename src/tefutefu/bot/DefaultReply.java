package tefutefu.bot;

import tefutefu.commons.Importance;
import tefutefu.reactions.*;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.User;

import java.util.Date;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by alphakai on 2016/05/28.
 */
//リプライ用のクラスを作ったほうがいいかも...?
public class DefaultReply extends TefutefuReaction {
  private String patternString;

  public DefaultReply(
      Twitter t4j
  ) {
    super(TefutefuReactionTypes.Reply);
    this.reactionName = "DefaultReply";

    try {
      User user = t4j.verifyCredentials();
      this.patternString = "^@" + user.getScreenName();
    } catch (Exception e) {}
  }

  public TefutefuReactionContainer process(
      Status status
  ) {
    String generatedString;

    generatedString = "@" + status.getUser().getScreenName() + " " + new Date().toString();

    TefutefuReactionContainer trc = new TefutefuReactionContainer(
        this.type,
        new TefutefuReactionTarget(status.getId(), status.getUser().getScreenName()),
        generatedString,
        this
    );

    System.out.println("Process" + generatedString);

    return trc;
  }

  public boolean match(
      Status status
  ) {
    Pattern pattern = Pattern.compile(this.patternString);
    Matcher m = pattern.matcher(status.getText());

    //return m.find();
    return false;
  }

  public void processReturnedJson(
      TefutefuTwitterStatuses returnedValue
  ) {}

  public void afterProcess() {}
}