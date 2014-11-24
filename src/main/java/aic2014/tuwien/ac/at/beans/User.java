package aic2014.tuwien.ac.at.beans;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class User {

	@Id
	@GeneratedValue
	private Long id;
	private String username;
	private int nrOfFollowers;
	private int nrOfTweets;
	public User() {}
	public User(String username, int nrOfFollowers, int nrOfTweets) {

		this.username = username;
		this.nrOfFollowers = nrOfFollowers;
		this.nrOfTweets = nrOfTweets;
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
	public int getNrOfFollowers() {
		return nrOfFollowers;
	}
	public void setNrOfFollowers(int nrOfFollowers) {
		this.nrOfFollowers = nrOfFollowers;
	}
	public int getNrOfTweets() {
		return nrOfTweets;
	}
	public void setNrOfTweets(int nrOfTweets) {
		this.nrOfTweets = nrOfTweets;
	}
}
