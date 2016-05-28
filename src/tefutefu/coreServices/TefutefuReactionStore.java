package tefutefu.coreServices;

/**
 * Created by alphakai on 2016/05/26.
 */
import tefutefu.message.TefutefuMessage;
import tefutefu.service.ServiceType;
import tefutefu.service.TefutefuService;
import tefutefu.reactions.*;
import tefutefu.service.TefutefuServiceManager;
import tefutefu.util.HashMapUtil;
import twitter4j.Status;
import twitter4j.Twitter;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashMap;


public class TefutefuReactionStore extends TefutefuService<Status> {
  public  HashMap<String, TefutefuReaction>     reactions = new HashMap<>();
  public  Twitter                               t4j;
  private HashMapUtil<String, TefutefuReaction> hashMapUtil;
  private TefutefuServiceManager                tsm;

  public TefutefuReactionStore(
      Twitter t4j,
      TefutefuServiceManager tsm
  ) {
    super(ServiceType.Daemon, "TefutefuReactionStore");

    this.t4j         = t4j;
    this.hashMapUtil = new HashMapUtil<>();
    this.tsm         = tsm;
  }

  public boolean addNewReaction(
      TefutefuReaction newReaction
  ) {
    if (this.existReaction(newReaction.reactionName)) {
      return false;
    } else {
      this.reactions.put(newReaction.reactionName, newReaction);
      return true;
    }
  }

  public boolean existReaction(String name) {
    return this.hashMapUtil.existValue(this.reactions, name);
  }

  /*
  public void processTweet() {
    this.checkRecvQueue();
  }
  */

  @Override
  public void recvReaction(
      TefutefuMessage<Status> message
  ) {
    Status                           thisStatus          = message.data;
    ArrayDeque<TefutefuReaction>     reactionList        = new ArrayDeque<>();
    ArrayList<TefutefuReactionTypes> fallthroughList     = new ArrayList<TefutefuReactionTypes>();
    boolean                          fallthrough         = true;
    boolean                          fallthroughModified = false;

    /*
    * TODO: 優先度(Imprtance)を元に優先度の高いものから評価するようにする
    * */
    if (this.reactions != null) {
      for (HashMap.Entry<String, TefutefuReaction> entry : this.reactions.entrySet()) {
        System.out.println("REACTION => " + entry.getKey());
        TefutefuReaction thisReaction = entry.getValue();

        if (thisReaction.match(thisStatus)) {
          boolean granted = false;
          if (fallthroughModified) {
            for (TefutefuReactionTypes type : fallthroughList) {
              if (thisReaction.type == type) {
                granted = true;
                break;
              }
            }
          } else {
            granted = true;
          }

          if (granted) {
            System.out.println("granted: " + thisReaction.reactionName);
            reactionList.add(thisReaction);
          }

          if (thisReaction.fallthrough && thisReaction.limited && granted) {
            if (fallthrough == false) {
              fallthrough = true;
            }

            for (TefutefuReactionTypes type : thisReaction.fallthroughList) {
              if (!fallthroughList.contains(type)) {
                fallthroughList.add(type);
              }
            }

            fallthroughModified = true;
          }
        }
      }


      for (TefutefuReaction reaction : reactionList) {
        System.out.println("=> " + reaction.reactionName);

        TefutefuReactionContainer trc = reaction.process(thisStatus);

        if (reaction.type != TefutefuReactionTypes.Display) {
          //Queuesに投げる
          System.out.println("Push new reaction " + reaction.reactionName);
          this.tsm.twq.pushToRecvQueue(new TefutefuMessage<>(trc));
        }
      }

      reactionList.clear();

      this.tsm.twq.checkRecvQueue();
    }
  }

  public void start() {}
  public void stop() {}
}
