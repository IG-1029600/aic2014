package aic2014.tuwien.ac.at.dao;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;



import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.MongoClient;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;

import twitter4j.Status;

import twitter4j.UserMentionEntity;

public class PublicDAOImpl implements IPublicDAO{
	
	MongoClient mongoClient;
	ServerAddress serverAddress;
	String[] filter;
	
	public PublicDAOImpl(String[] filter) throws UnknownHostException{
		serverAddress=new ServerAddress("localhost" , 27017);
		this.filter=filter;
		
		
		MongoCredential creds = MongoCredential.createPlainCredential("", "aic2014", "".toCharArray());
		List<MongoCredential> auths = new ArrayList<MongoCredential>();
		auths.add(creds);

	
	}
	
	
	public void insertTweet(Status status){
		mongoClient = new MongoClient(serverAddress);
		DB dbs=mongoClient.getDB("aic2014");
		DBCollection dbCollection=dbs.getCollection("history-test");
		String tmp=status.getText();
		String tps=topics(status.getText());
		if(tps.isEmpty()==false){
			BasicDBObject dbo=new BasicDBObject("tweet",tmp);
				dbo.append("name", status.getUser().getName());
		
				dbo.append("favorites", status.getFavoriteCount());
				dbo.append("retweets", status.getRetweetCount());
				
				if(status.isRetweet()==true){
					Status tst=status.getRetweetedStatus();
					dbo.append("retweet", true);
					dbo.append("friends",usersMentioned(tst.getUserMentionEntities()));
					dbo.append("topics", topics(tst.getText()));
					dbo.append("retweetid", tst.getId());
					dbo.append("retweetUsername", tst.getUser().getName());
					dbo.append("retweets-number", tst.getRetweetCount());
					dbo.append("retweets-favorites", tst.getFavoriteCount());
				}else{
					dbo.append("retweet", false);
					dbo.append("retweetid", "");
					dbo.append("friends",usersMentioned(status.getUserMentionEntities()));
					dbo.append("topics", tps);
					dbo.append("retweetUsername", "");
					dbo.append("retweets-number", "");
					dbo.append("retweets-favorites", "");
				}
			
			
		
				
		
			dbCollection.insert(dbo);
			mongoClient.close();
			
		}
	}
	


	private String topics(String text){
		String result="";
		for(String tmp:filter){
			String tmpL=tmp.substring(0, 1).toUpperCase()+tmp.substring(1);
			String upper=tmp.toUpperCase();
			if(text.contains(tmp)==true ||text.contains(tmpL)==true||text.contains(upper)==true){
				result=result+tmp+";";
			}
		}
		return result;
	}
	
	
	private String usersMentioned(UserMentionEntity[] userm){
		
		String user="";
		for(UserMentionEntity um:userm){
			user=user+um.getName()+";";
		}
		return user;
	}
	
	
	public void deleteContent(){
		mongoClient = new MongoClient(serverAddress);
		DB dbs=mongoClient.getDB("aic2014");
		DBCollection dbCollection=dbs.getCollection("history");
		dbCollection.drop();
		mongoClient.close();
	}




	public void analyze(){
		mongoClient = new MongoClient(serverAddress);
		DB dbs=mongoClient.getDB("aic2014");
		DBCollection dbCollection=dbs.getCollection("history-test");
		DBCursor cursor=dbCollection.find();
		while(cursor.hasNext()){
			BasicDBObject dbObject=(BasicDBObject) cursor.next();
			boolean retweet=(boolean) dbObject.get("retweet");
			if(retweet==true){
				insertTweet(dbObject);
				insertRetweet(dbObject);
			}else{
				insertTweet(dbObject);
			}
		}
	}
	
	private void insertTweet(BasicDBObject dbObject){
		
	}

	private void insertRetweet(BasicDBObject dbObject){
		
	}
	
	
	
}
