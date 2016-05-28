package tefutefu.bot;

import tefutefu.commons.Importance;
import tefutefu.reactions.TefutefuReaction;
import tefutefu.reactions.TefutefuReactionContainer;
import tefutefu.reactions.TefutefuReactionTypes;
import tefutefu.reactions.TefutefuTwitterStatuses;
import twitter4j.Status;

/**
 * Created by alphakai on 2016/05/26.
 */
public class OutPut extends TefutefuReaction {
  public OutPut() {
    super(TefutefuReactionTypes.Display);

    this.reactionName = "OutPut";
    this.importance   = Importance.HIGH;
  }

  public TefutefuReactionContainer process(Status status) {
    TefutefuReactionContainer trc = new TefutefuReactionContainer(
        this.type,
        this
      );

    System.out.println("------------------------------------");
    System.out.printf("%s(@%s) : %s\n",
        status.getUser().getScreenName(),
        status.getUser().getId(),
        status.getText());
    System.out.println("------------------------------------");

    return trc;
  }

  public boolean match(Status status) {
    return true;
  }

  public void processReturnedJson(TefutefuTwitterStatuses returnedValue) {}

  public void afterProcess() {}
}