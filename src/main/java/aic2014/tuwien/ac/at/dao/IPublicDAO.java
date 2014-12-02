package aic2014.tuwien.ac.at.dao;

import twitter4j.Status;




interface IPublicDAO {
	
	void insertTweet(Status status);
	void analyze();
	void deleteContent();

}
