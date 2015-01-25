package aic2014.tuwien.ac.at.dao;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;

import twitter4j.Status;
import twitter4j.UserMentionEntity;
import aic2014.tuwien.ac.at.beans.Tweet;
import aic2014.tuwien.ac.at.beans.User;
import aic2014.tuwien.ac.at.services.InterestedUsersCalculationService;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.MongoClient;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
@Component
public class PublicDAOImpl implements IPublicDAO {

	@Autowired
	UserDao userDao;
	
	@Autowired
	InterestedUsersCalculationService calc;
	MongoClient mongoClient;
	ServerAddress serverAddress;
	String[] filter;
	IGraphDAO graphDao;

	public PublicDAOImpl(String[] filter) throws UnknownHostException {
		serverAddress = new ServerAddress("localhost", 27017);
		this.filter = filter;

		MongoCredential creds = MongoCredential.createPlainCredential("", "aic2014", "".toCharArray());
		List<MongoCredential> auths = new ArrayList<MongoCredential>();
		auths.add(creds);

		// graphDao = new GraphDAOImpl();

	}

	public void displayMongoDB() {
		mongoClient = new MongoClient(serverAddress);
		DB dbs = mongoClient.getDB("aic2014");
		DBCollection dbCollection = dbs.getCollection("history-test");

		DBCursor cursor = dbCollection.find();
		while (cursor.hasNext()) {
			BasicDBObject db = (BasicDBObject) cursor.next();
			System.out.println(db.get("name"));
			System.out.println(db.get("favorites"));
			System.out.println(db.get("retweet"));
			System.out.println("Retweets" + db.get("retweets-number"));
		}
		mongoClient.close();
	}

	public void insertTweet(Status status) {
		mongoClient = new MongoClient(serverAddress);
		DB dbs = mongoClient.getDB("aic2014");
		DBCollection dbCollection = dbs.getCollection("history-test");

		String tmp = status.getText();
		String tps = topics(status.getText());
		if (tps.isEmpty() == false) {
			BasicDBObject dbo = new BasicDBObject("tweet", tmp);
			dbo.append("id", status.getId()); 
			dbo.append("name", status.getUser().getName());
			dbo.append("tweetCount" , status.getUser().getStatusesCount());
			dbo.append("numOfFollowers", status.getUser().getFollowersCount());
			dbo.append("favorites", status.getFavoriteCount());
			dbo.append("retweets", status.getRetweetCount());

			if (status.isRetweet() == true) {
				Status tst = status.getRetweetedStatus();
				dbo.append("retweet", true);
				dbo.append("friends", usersMentioned(tst.getUserMentionEntities()));
				dbo.append("topics", topics(tst.getText()));
				dbo.append("retweetid", tst.getId());
				dbo.append("retweetUsername", tst.getUser().getName());
				dbo.append("retweets-number", tst.getRetweetCount());
				dbo.append("retweets-favorites", tst.getFavoriteCount());
			} else {
				dbo.append("retweet", false);
				dbo.append("retweetid", "");
				dbo.append("friends", usersMentioned(status.getUserMentionEntities()));
				dbo.append("topics", tps);
				dbo.append("retweetUsername", "");
				dbo.append("retweets-number", "");
				dbo.append("retweets-favorites", "");

			}

			dbCollection.insert(dbo);
			mongoClient.close();

		}
	}

	private Tweet tweetExists(List<Tweet> tweets, long number) {

		for (Tweet tweet : tweets) {
			if (tweet.getId() == number) {
				return tweet;
			}
		}
		return null;
	}

	public void deleteContent() {
		mongoClient = new MongoClient(serverAddress);
		DB dbs = mongoClient.getDB("aic2014");
		DBCollection dbCollection = dbs.getCollection("history");
		dbCollection.drop();
		mongoClient.close();
	}

	public void analyze() {
		GraphService graphService = new GraphService();
		mongoClient = new MongoClient(serverAddress);
		DB dbs = mongoClient.getDB("aic2014");
		DBCollection dbCollection = dbs.getCollection("history-test");
		DBCursor cursor = dbCollection.find();
		while (cursor.hasNext()) {
			BasicDBObject dbObject = (BasicDBObject) cursor.next();
			// TODO (Cannot cast dbObject to boolean

			boolean retweet = (boolean) dbObject.get("retweet");
			 if (retweet == true) {
			 insertTweet(dbObject);
			 insertRetweet(dbObject);
			 } else {
			 insertTweet(dbObject);
			 }

			graphService.processDbObject(dbObject);
		}
		
		
		
		System.out.println("finished analyze");
	}
	
	public void calculateScores(){	
		System.out.println("starting to calculate scores..");
		
		calc.calculateFocusedScore(0.5, 1000);
		calc.calculateBroadScore(0.5);
		
	}

	private void insertTweet(BasicDBObject dbObject) {
		
		List<User> users = userDao.getOne(dbObject.getString("name"));
		TweetDaoImpl tweetDaoImpl = new TweetDaoImpl();
		TopicDaoImpl topicDaoImpl = new TopicDaoImpl();
		if (users.size() == 0) {
			User nUser = createUser(dbObject.getString("name"), dbObject.getInt("favorites"), dbObject.getInt("retweets"));
			nUser.setTotalTweetCount(dbObject.getInt("tweetCount"));
			nUser.setNumOfFollowers(dbObject.getInt("numOfFollowers"));
			userDao.save(nUser);
			nUser.setTweets(tweetDaoImpl.createTweets(nUser, dbObject.getInt("favorites"), dbObject.getInt("retweets"), dbObject.getLong("id")));
			nUser.setTopics(topicDaoImpl.createTopic(dbObject.getString("topics"), nUser));

			userDao.updateUser(nUser);

		} else {

			User user = users.get(0);
			user.setFavorites(user.getFavorites() + dbObject.getInt("favorites"));
			user.setRetweets(user.getRetweets() + dbObject.getInt("retweets"));
			List<Tweet> tweets = user.getTweets();
			tweets.add(tweetDaoImpl.addTweet(user, dbObject.getInt("favorites"), dbObject.getInt("retweets"), dbObject.getLong("id")));
			user.setTweets(tweets);
			user.setTopics(topicDaoImpl.updateTopic(dbObject.getString("topics"), user.getTopics(), user));
			userDao.updateUser(user);

		}
	}

	private void insertRetweet(BasicDBObject dbObject) {
		
		List<User> users = userDao.getOne(dbObject.getString("retweetUsername"));
		TweetDaoImpl tweetDaoImpl = new TweetDaoImpl();
		TopicDaoImpl topicDaoImpl = new TopicDaoImpl();
		if (users.size() == 0) {
			User nUser = createUser(dbObject.getString("retweetUsername"), dbObject.getInt("retweets-favorites"), dbObject.getInt("retweets-number"));
			nUser.setTotalTweetCount(dbObject.getInt("tweetCount"));
			nUser.setNumOfFollowers(dbObject.getInt("numOfFollowers"));
			userDao.save(nUser);
			nUser.setTweets(tweetDaoImpl.createTweets(nUser, dbObject.getInt("retweets-favorites"), dbObject.getInt("retweets-number"), dbObject.getLong("retweetid")));
			nUser.setTopics(topicDaoImpl.createTopic(dbObject.getString("topics"), nUser));
			userDao.updateUser(nUser);

		} else {
			User user = users.get(0);
			List<Tweet> tweets = user.getTweets();
			Tweet ttweet = tweetExists(tweets, dbObject.getLong("retweetid"));
			if (ttweet == null) {
				user.setFavorites(user.getFavorites() + dbObject.getInt("retweets-favorites"));
				user.setRetweets(user.getRetweets() + dbObject.getInt("retweets-number"));
				user.setTweets(tweetDaoImpl.createTweets(user, dbObject.getInt("retweets-favorites"), dbObject.getInt("retweets-number"), dbObject.getLong("retweetid")));

			} else {
				int retweets = user.getRetweets() + (dbObject.getInt("retweets-number") - ttweet.getRetweets());
				int favorites = user.getFavorites() + (dbObject.getInt("retweets-favorites") - ttweet.getFavorites());
				ttweet.setFavorites(dbObject.getInt("retweets-favorites"));
				ttweet.setRetweets(dbObject.getInt("retweets-number"));
				tweets.remove(ttweet);
				user.setFavorites(favorites);
				user.setRetweets(retweets);
				tweets.add(ttweet);
				user.setTweets(tweets);

			}
			user.setTopics(topicDaoImpl.updateTopic(dbObject.getString("topics"), user.getTopics(), user));
			userDao.updateUser(user);
		}
	}

	private User createUser(String name, int fav, int retweets) {
		User nUser = new User();
		nUser.setUsername(name);
		nUser.setFavorites(fav);
		nUser.setRetweets(retweets);
		return nUser;
	}

	private String topics(String text) {
		String result = "";
		for (String tmp : filter) {
			String tmpL = tmp.substring(0, 1).toUpperCase() + tmp.substring(1);
			String upper = tmp.toUpperCase();
			if (text.contains(tmp) == true || text.contains(tmpL) == true || text.contains(upper) == true) {
				result = result + tmp + ";";
			}
		}
		return result;
	}

	private String usersMentioned(UserMentionEntity[] userm) {

		String user = "";
		for (UserMentionEntity um : userm) {
			user = user + um.getName() + ";";
		}
		return user;
	}

	public UserDao getUserDao() {
		return userDao;
	}

	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}

	public InterestedUsersCalculationService getCalc() {
		return calc;
	}

	public void setCalc(InterestedUsersCalculationService calc) {
		this.calc = calc;
	}
}