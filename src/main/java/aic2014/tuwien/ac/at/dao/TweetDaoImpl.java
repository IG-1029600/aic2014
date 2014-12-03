package aic2014.tuwien.ac.at.dao;

import java.util.ArrayList;
import java.util.List;

import aic2014.tuwien.ac.at.beans.Tweet;
import aic2014.tuwien.ac.at.beans.User;

public class TweetDaoImpl {
	public TweetDaoImpl(){
		
	}
	
	public Tweet addTweet(User user, int fav, int retweets, long id){
		Tweet tweet=new Tweet();
		tweet.setUser(user);
		tweet.setFavorites(fav);
		tweet.setRetweets(retweets);
		tweet.setId(id);
		return tweet;
	}
	
	public List<Tweet> createTweets(User user,int fav, int retweets, long id){
		List<Tweet> tweets=new ArrayList<>();
		tweets.add(addTweet(user, fav, retweets, id));
		return tweets;
	}
}
