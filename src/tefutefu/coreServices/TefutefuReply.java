package tefutefu.coreServices;

public abstract class TefutefuReply extends TefutefuReaction {
	public TefutefuReply(String regexString) {
		super(TefutefuReactionTypes.Reply, regexString);
		
	}
	
}
