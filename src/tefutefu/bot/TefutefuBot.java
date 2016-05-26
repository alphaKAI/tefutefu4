package tefutefu.bot;

import twitter4j.Twitter;
import twitter4j.TwitterFactory;
import twitter4j.conf.Configuration;
import twitter4j.conf.ConfigurationBuilder;

import tefutefu.service.*;
import tefutefu.coreServices.*;

/*
 *   Twitter Bot のConsumerKeyやAccessToeknをもつ。
 * このクラスがTefutefuを制御する実態(TefutefuMainクラスはこのクラスをインスタンスとして呼び出す)
 * サービスを呼び出し、イベントを受け取ったりする。
 * */

public class TefutefuBot {
  private String        consumerKey,
                        consumerSecret,
                        accessToken,
                        accessTokenSecret;
  private Configuration conf;
  
  public TefutefuBot() {
    consumerKey       = "";
    consumerSecret    = "";
    accessToken       = "";
    accessTokenSecret = "";
    
    ConfigurationBuilder cb = new ConfigurationBuilder();

    cb.setOAuthConsumerKey(consumerKey);
    cb.setOAuthConsumerSecret(consumerSecret);
    cb.setOAuthAccessToken(accessToken);
    cb.setOAuthAccessTokenSecret(accessTokenSecret);
    
    conf = cb.build(); 
  }
  
  public void boot() {
    TwitterFactory tf = new TwitterFactory(conf);
    Twitter twitter = tf.getInstance();
    TefutefuServiceManager tsm           = new TefutefuServiceManager(twitter);
    TefutefuReactionStore reactionStore = new TefutefuReactionStore(twitter);

    reactionStore.addNewReaction(new Egosa(twitter));
    reactionStore.addNewReaction(new OutPut());

    tsm.addNewService(reactionStore);
    tsm.startService("TefutefuReactionStore");

    TefutefuUserStream userStreamService = new TefutefuUserStream(tsm);
    userStreamService.initialize(conf);
    tsm.addNewService(userStreamService);
    tsm.startService("TefutefuUserStream");

  }
}