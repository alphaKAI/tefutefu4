package tefutefu.message;
import tefutefu.commons.Importance;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashMap;

public class TefutefuMessageQueues<T> {
  public ArrayDeque<TefutefuMessage<T>> recvQueue = new ArrayDeque<TefutefuMessage<T>>();
  //sendQueue = new ArrayDeque<TefutefuMessage<T>>(),

  public void pushToRecvQueue(TefutefuMessage<T> newMessage) {
    this.recvQueue.push(newMessage);
  }

  public void checkRecvQueue() {
    if (!this.recvQueue.isEmpty()) {
      HashMap<Importance, ArrayList<TefutefuMessage<T>>> tasks = new HashMap<>();

      tasks.put(Importance.LOW,  new ArrayList<>());
      tasks.put(Importance.MID,  new ArrayList<>());
      tasks.put(Importance.HIGH, new ArrayList<>());
      
      for (TefutefuMessage<T> message : this.recvQueue) {
        tasks.get(message.getImportance()).add(message);
      }
    
      this.recvQueue.clear();
      
      Importance[] importances = {Importance.HIGH, Importance.MID, Importance.LOW};
      
      for (Importance importance : importances) {
        for (TefutefuMessage<T> message : tasks.get(importance)) {
          this.recvReaction(message);
        }
      }
    }
  }

  public void recvReaction(TefutefuMessage<T> message) {}
}
