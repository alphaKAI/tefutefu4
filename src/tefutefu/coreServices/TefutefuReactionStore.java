package tefutefu.coreServices;

/**
 * Created by alphakai on 2016/05/26.
 */
import tefutefu.message.TefutefuMessage;
import tefutefu.service.ServiceType;
import tefutefu.service.TefutefuService;
import tefutefu.reactions.*;
import tefutefu.util.HashMapUtil;
import twitter4j.Status;
import twitter4j.Twitter;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashMap;


public class TefutefuReactionStore extends TefutefuService {
  public  HashMap<String, TefutefuReaction> reactions = new HashMap<String, TefutefuReaction>();
  public  Twitter t4j;
  private HashMapUtil<String, TefutefuReaction> hashMapUtil;

  public TefutefuReactionStore(Twitter t4j) {
    this.serviceName = "TefutefuReactionStore";
    this.type = ServiceType.Daemon;
    this.t4j = t4j;
    this.hashMapUtil = new HashMapUtil<String, TefutefuReaction>();
  }

  public boolean addNewReaction(TefutefuReaction newReaction) {
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

  public void processTweet() {
    this.checkRecvQueue();
  }

  @Override
  public void recvReaction(TefutefuMessage<Status> message) {
    Status thisStatus = message.data;
    ArrayDeque<TefutefuReactionContainer> reactionList = new ArrayDeque<TefutefuReactionContainer>();
    ArrayList<TefutefuReactionTypes> fallthroughList = new ArrayList<TefutefuReactionTypes>();
    boolean fallthrough = true;
    boolean fallthroughModified = false;

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
            reactionList.add(thisReaction.process(thisStatus));
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

      while (reactionList.iterator().hasNext()) {
        TefutefuReactionContainer trc = reactionList.poll();
        //反応を書く

        if (trc.type == TefutefuReactionTypes.Fav) {
          try {
            t4j.createFavorite(trc.target.targetTweetID);
          } catch (Exception e) {

          }
        }
      }
    }
  }

  public void start() {}
  public void stop() {}
}
