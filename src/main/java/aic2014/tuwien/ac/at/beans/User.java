package aic2014.tuwien.ac.at.beans;

import java.util.Comparator;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class User {

	@Id
	@GeneratedValue
	private Long id;
	private String username;
	private int favorites;
	private int retweets;
	private int totalTweetCount;
	private int numOfFollowers;
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "user", fetch = FetchType.EAGER)
	private List<Tweet> tweets;
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "user", fetch = FetchType.EAGER)
	private List<Topic> topics;

	private double focussedInterestScore;
	private double broadInterestScore;

	public User(String username, int favorites, int retweets, List<Tweet> tweets) {

		this.username = username;
		this.favorites = favorites;
		this.retweets = retweets;
		this.tweets = tweets;
	}

	public List<Tweet> getTweets() {
		return tweets;
	}

	public void setTweets(List<Tweet> tweets) {
		this.tweets = tweets;
	}

	public User() {
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public int getFavorites() {
		return favorites;
	}

	public void setFavorites(int favorites) {
		this.favorites = favorites;
	}

	public int getRetweets() {
		return retweets;
	}

	public void setRetweets(int retweets) {
		this.retweets = retweets;
	}

	public List<Topic> getTopics() {
		return topics;
	}

	public void setTopics(List<Topic> topics) {
		this.topics = topics;
	}

	public int getTotalTweetCount() {
		return totalTweetCount;
	}

	public void setTotalTweetCount(int totalTweetCount) {
		this.totalTweetCount = totalTweetCount;
	}

	public double getFocussedInterestScore() {
		return focussedInterestScore;
	}

	public void setFocussedInterestScore(double focussedInterestScore) {
		this.focussedInterestScore = focussedInterestScore;
	}

	public double getBroadInterestScore() {
		return broadInterestScore;
	}

	public void setBroadInterestScore(double broadInterestScore) {
		this.broadInterestScore = broadInterestScore;
	}

	public static class UserFocussedInterestScoreComparator implements
			Comparator<User> {

		public int compare(User lhs, User rhs) {

			int res = 0;
			if (lhs.getFocussedInterestScore() < rhs.getFocussedInterestScore()) {
				res = -1;
			} else if (lhs.getFocussedInterestScore() > rhs
					.getFocussedInterestScore()) {
				res = 1;
			}
			return res;
		}

	}

	public static class UserBroadInterestScoreComparator implements
			Comparator<User> {

		public int compare(User lhs, User rhs) {

			int res = 0;
			if (lhs.getBroadInterestScore() < rhs.getBroadInterestScore()) {
				res = -1;
			} else if (lhs.getBroadInterestScore() > rhs
					.getBroadInterestScore()) {
				res = 1;
			}
			return res;
		}

	}

	public int getNumOfFollowers() {
		return numOfFollowers;
	}

	public void setNumOfFollowers(int numOfFollowers) {
		this.numOfFollowers = numOfFollowers;
	}

}
