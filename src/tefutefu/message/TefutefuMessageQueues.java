package tefutefu.message;
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
			HashMap<Integer, ArrayList<TefutefuMessage<T>>> tasks = new HashMap<Integer, ArrayList<TefutefuMessage<T>>>();
			tasks.put(0, new ArrayList<TefutefuMessage<T>>());
			tasks.put(1, new ArrayList<TefutefuMessage<T>>());
			tasks.put(2, new ArrayList<TefutefuMessage<T>>());
			
			for (TefutefuMessage<T> message : this.recvQueue) {
				tasks.get(message.getImportance()).add(message);
			}
		
			this.recvQueue.clear();
			
			int[] importances = {2, 1, 0};
			
			for (int importance : importances) {
				for (TefutefuMessage<T> message : tasks.get(importance)) {
					this.recvReaction(message);
				}
			}
		}
	}

	public void recvReaction(TefutefuMessage<T> message) {}
}
