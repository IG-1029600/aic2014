package aic2014.tuwien.ac.at.twitter;
import java.net.UnknownHostException;

import aic2014.tuwien.ac.at.dao.PublicDAOImpl;

import twitter4j.*;
import twitter4j.conf.ConfigurationBuilder;


public class TwitterFeedGrabber {
	
	public ConfigurationBuilder configurationBuilder;
	TwitterStream twitterStream;
	
	

		
		String[] filter;
		StatusListener listen = new StatusListener(){
		
		@Override
		public void onStatus(Status status) {

			try {
				PublicDAOImpl publicDAOImpl=new PublicDAOImpl(filter);
				publicDAOImpl.insertTweet(status);
			} catch (UnknownHostException e) {
				e.printStackTrace();
			}
			
		}
		
		@Override
		public void onDeletionNotice(StatusDeletionNotice statusDeletionNotice) {
		}
		
		@Override
		public void onTrackLimitationNotice(int numberOfLimitedStatuses) {
		}
		
	
		@Override
		public void onScrubGeo(long userId, long upToStatusId) {
		}
		
		@Override
		public void onStallWarning(StallWarning warning) {
		}

		@Override
		public void onException(Exception ex) {
		ex.printStackTrace();
		}

	};
	
	
	
	
	
	
	public TwitterFeedGrabber(String[] filter){
		this.filter=filter;
		configurationBuilder=new ConfigurationBuilder();
		configurationBuilder.setDebugEnabled(true)
		  .setOAuthConsumerKey("Se4SNB7ygFkSQM2e2KRJR0lqI")
		  .setOAuthConsumerSecret("ckxBBDm5foe1fNzT21Q4GYc3I5Z8WgHzYKpjJaj9XLluvNyza1")
		  .setOAuthAccessToken("118771229-PEz4rbdxdgQ4RRoYmEKZrTH9UQotsqW3TEk0IYTY")
		  .setOAuthAccessTokenSecret("UjYZND9Lxiylp1PyA4FB7WnCg0AxQxBZYDexGGxHpffKB");
		configurationBuilder.setJSONStoreEnabled(true);
		
	};
	
	
	public void listenToStream()throws TwitterException{
		twitterStream = new TwitterStreamFactory(configurationBuilder.build()).getInstance();
		twitterStream.addListener(listen);
		twitterStream.filter(new FilterQuery().track(filter));
	}
	
	public void killListener() throws TwitterException{
		twitterStream.clearListeners();
		twitterStream.shutdown();
	}
	
}
