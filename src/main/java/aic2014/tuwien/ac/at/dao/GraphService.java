package aic2014.tuwien.ac.at.dao;

import java.net.UnknownHostException;

import aic2014.tuwien.ac.at.beans.UserNode;

import com.mongodb.BasicDBObject;

public class GraphService {
	
	private GraphDAOImpl graphDAO; //TODO Interface
	
	public GraphService(){
		try {
			
			this.graphDAO = new GraphDAOImpl();
			
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
	}
	/**
	 * main access point from outside the DAO
	 * @param dbObject
	 */
	public void processDbObject(BasicDBObject dbObject) {

		UserNode newUser = graphDAO.createUserNode((String) dbObject.get("name"));
		System.out.println("create new userNode with name: "  +(String) dbObject.get("name"));
		String[] mentions = ((String) dbObject.get("friends")).split(";");

		for (String currFriendName : mentions) {
			
			UserNode mentionedUser = graphDAO.createUserNode(currFriendName);	
			newUser.addFriend(mentionedUser);
			System.out.println("created Friend " + currFriendName + " and added Relationship");

		}
	}
	
	public void processJSONStrings(String username, String userMentions){
		UserNode newUser = graphDAO.createUserNode(username);
		System.out.println("create new userNode with name: "  +username);
		
		
		String[] mentions = userMentions.split(";");

		for (String currFriendName : mentions) {
			
			UserNode mentionedUser = graphDAO.createUserNode(currFriendName);	
			newUser.addFriend(mentionedUser);
			System.out.println("created Friend " + currFriendName + " and added Relationship");

		}
	}
}
