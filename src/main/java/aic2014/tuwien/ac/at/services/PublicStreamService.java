package aic2014.tuwien.ac.at.services;

import java.net.UnknownHostException;

import twitter4j.TwitterException;
import aic2014.tuwien.ac.at.dao.PublicDAOImpl;
import aic2014.tuwien.ac.at.twitter.TwitterFeedGrabber;

public class PublicStreamService {

	// TODO filter through keywords and not topics
	String[] filter = { "software", "hardware", "finance", "history", "work", "art", "books", "apple", "alcohol" };
	private TwitterFeedGrabber twitterFeedGrabber;

	public PublicStreamService() {
		twitterFeedGrabber = new TwitterFeedGrabber(filter);
	}

	public void listenToStream() {

		try {
			twitterFeedGrabber.listenToStream();

		} catch (TwitterException e) {
			e.printStackTrace();

		}
	}

	public void analyzeData() {
		try {
			PublicDAOImpl publicDAOImpl = new PublicDAOImpl(filter);
			publicDAOImpl.analyze();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
	}

	public void killPublic() {

		try {
			twitterFeedGrabber.killListener();
		} catch (TwitterException e) {
			e.printStackTrace();
		}
	}
}
