package tefutefu.bot;

import twitter4j.Twitter;
import twitter4j.TwitterFactory;
import twitter4j.conf.Configuration;
import twitter4j.conf.ConfigurationBuilder;

import tefutefu.service.*;
import tefutefu.coreServices.*;

import java.io.File;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;


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
    ObjectMapper mapper = new ObjectMapper();
    JsonNode     root;

    try {
      root = mapper.readTree(new File("configure.json"));

      consumerKey       = root.get("consumerKey").asText();
      consumerSecret    = root.get("consumerSecret").asText();
      accessToken       = root.get("accessToken").asText();
      accessTokenSecret = root.get("accessTokenSecret").asText();
    } catch (Exception e) {}

    ConfigurationBuilder cb = new ConfigurationBuilder();

    cb.setOAuthConsumerKey(consumerKey);
    cb.setOAuthConsumerSecret(consumerSecret);
    cb.setOAuthAccessToken(accessToken);
    cb.setOAuthAccessTokenSecret(accessTokenSecret);
    
    conf = cb.build(); 
  }
  
  public void boot() {
    TwitterFactory          twitterFactory = new TwitterFactory(conf);
    Twitter                 twitter        = twitterFactory.getInstance();
    TefutefuServiceManager  tsm            = new TefutefuServiceManager(twitter);

    TefutefuReactionStore   reactionStore  = new TefutefuReactionStore(twitter, tsm);
    // TODO: フォールスローのテストをする。
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