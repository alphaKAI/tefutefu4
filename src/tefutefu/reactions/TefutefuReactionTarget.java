package tefutefu.reactions;

/**
 * Created by alphakai on 2016/05/26.
 */
public class TefutefuReactionTarget {
  public String targetUserScreenName;
  public long   targetTweetID;

  public TefutefuReactionTarget(
      long targetTweetID,
      String targetUserScreenName
  ) {
    this(targetUserScreenName, targetTweetID);
  }

  public TefutefuReactionTarget(
      String targetUserScreenName,
      long targetTweetID
  ) {
    this.targetUserScreenName  = targetUserScreenName;
    this.targetTweetID         = targetTweetID;
  }
}
