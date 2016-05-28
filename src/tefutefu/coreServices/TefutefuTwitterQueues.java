package tefutefu.coreServices;

import tefutefu.commons.Importance;
import tefutefu.message.TefutefuMessage;
import tefutefu.message.TefutefuMessageQueues;
import tefutefu.reactions.TefutefuReactionContainer;
import tefutefu.reactions.TefutefuReactionTypes;
import tefutefu.reactions.TefutefuTwitterStatuses;
import tefutefu.service.TefutefuServiceManager;
import twitter4j.Status;
import twitter4j.Twitter;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by alphakai on 2016/05/28.
 * Queueで受け取ったTwitterに対するリクエストを処理する。
 */
public class TefutefuTwitterQueues extends TefutefuMessageQueues<TefutefuReactionContainer> {
  public  Twitter                t4j;
  private TefutefuServiceManager tsm;

  public TefutefuTwitterQueues(Twitter t4j, TefutefuServiceManager tsm) {
    this.t4j = t4j;
    this.tsm = tsm;
  }

  @Override
  public void recvReaction(TefutefuMessage<TefutefuReactionContainer> message) {
    TefutefuReactionContainer trc     = message.data;
    TefutefuTwitterStatuses ttrv = new TefutefuTwitterStatuses();

    // TODO : switchに書き換える
    if (trc.type == TefutefuReactionTypes.Fav) {
      try {
        ttrv.addNewStatus(t4j.createFavorite(trc.target.targetTweetID));
      } catch (Exception e) {
      }
    }

    if (ttrv.hasStatuses && trc.reaction.needReturnedValue) {
      trc.reaction.processReturnedJson(ttrv);
    }

    if (trc.reaction.hasAfterProcess) {
      trc.reaction.afterProcess();
    }
  }

}
