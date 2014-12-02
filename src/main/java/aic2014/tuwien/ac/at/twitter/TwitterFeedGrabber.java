package aic2014.tuwien.ac.at.twitter;

import java.net.UnknownHostException;

import twitter4j.FilterQuery;
import twitter4j.StallWarning;
import twitter4j.Status;
import twitter4j.StatusDeletionNotice;
import twitter4j.StatusListener;
import twitter4j.TwitterException;
import twitter4j.TwitterStream;
import twitter4j.TwitterStreamFactory;
import twitter4j.conf.ConfigurationBuilder;
import aic2014.tuwien.ac.at.dao.PublicDAOImpl;

public class TwitterFeedGrabber {

	public ConfigurationBuilder configurationBuilder;
	TwitterStream twitterStream;

	String[] filter;
	StatusListener listen = new StatusListener() {

		public void onStatus(Status status) {

			try {
				PublicDAOImpl publicDAOImpl = new PublicDAOImpl(filter);
				publicDAOImpl.insertTweet(status);
			} catch (UnknownHostException e) {
				e.printStackTrace();
			}

		}

		public void onDeletionNotice(StatusDeletionNotice statusDeletionNotice) {
		}

		public void onTrackLimitationNotice(int numberOfLimitedStatuses) {
		}

		public void onScrubGeo(long userId, long upToStatusId) {
		}

		public void onStallWarning(StallWarning warning) {
		}

		public void onException(Exception ex) {
			ex.printStackTrace();
		}

	};

	public TwitterFeedGrabber(String[] filter) {
		this.filter = filter;
		configurationBuilder = new ConfigurationBuilder();
		configurationBuilder.setDebugEnabled(true).setOAuthConsumerKey("Se4SNB7ygFkSQM2e2KRJR0lqI").setOAuthConsumerSecret("ckxBBDm5foe1fNzT21Q4GYc3I5Z8WgHzYKpjJaj9XLluvNyza1").setOAuthAccessToken("118771229-PEz4rbdxdgQ4RRoYmEKZrTH9UQotsqW3TEk0IYTY")
				.setOAuthAccessTokenSecret("UjYZND9Lxiylp1PyA4FB7WnCg0AxQxBZYDexGGxHpffKB");
		configurationBuilder.setJSONStoreEnabled(true);

	};

	public void listenToStream() throws TwitterException {
		twitterStream = new TwitterStreamFactory(configurationBuilder.build()).getInstance();
		twitterStream.addListener(listen);
		twitterStream.filter(new FilterQuery().track(filter));
	}

	public void killListener() throws TwitterException {
		twitterStream.clearListeners();
		twitterStream.shutdown();
	}

}
