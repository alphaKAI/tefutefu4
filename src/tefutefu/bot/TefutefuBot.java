package tefutefu.bot;

import twitter4j.conf.Configuration;
import twitter4j.conf.ConfigurationBuilder;

import tefutefu.service.*;
import tefutefu.coreServices.*;
/*
 * 	Twitter Bot のConsumerKeyやAccessToeknをもつ。
 * このクラスがTefutefuを制御する実態(TefutefuMainクラスはこのクラスをインスタンスとして呼び出す)
 * サービスを呼び出し、イベントを受け取ったりする。
 * */

public class TefutefuBot {
	private String  consumerKey,
					consumerSecret,
					accessToken,
					accessTokenSecret;
	Configuration conf;
	
	public TefutefuBot() {
		consumerKey 	  = "";
		consumerSecret    = "";
		accessToken		  = "";
		accessTokenSecret = "";
		ConfigurationBuilder cb = new ConfigurationBuilder();

		cb.setOAuthConsumerKey(consumerKey);
		cb.setOAuthConsumerSecret(consumerSecret);
		cb.setOAuthAccessToken(accessToken);
		cb.setOAuthAccessTokenSecret(accessTokenSecret);
		
		conf = cb.build(); 
	}
	
	public void boot() {
		TefutefuServiceManager tsm = new TefutefuServiceManager();
		TefutefuUserStream userStreamService = new TefutefuUserStream(tsm);
		userStreamService.initialize(conf);
		tsm.addNewService(userStreamService);
		
		tsm.startEventLoop();
	}
}