package aic2014.tuwien.ac.at.dao;

import java.net.UnknownHostException;

import aic2014.tuwien.ac.at.beans.TopicNode;
import aic2014.tuwien.ac.at.beans.UserNode;

import com.mongodb.BasicDBObject;

public class GraphService {

	private GraphDAOImpl graphDAO; // TODO Interface

	public GraphService() {
		try {

			this.graphDAO = new GraphDAOImpl();

		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
	}

	public void processDbObject(BasicDBObject dbObject) {
		processJSONStrings((String) dbObject.get("name"), (String) dbObject.get("friends"), (String) dbObject.get("topics"));

	}

	public void processJSONStrings(String username, String userMentions, String topics) {

		UserNode newUser = graphDAO.createUserNode(username);

		String[] interestedTopics = topics.replaceAll(" ", "").split(";");

		for (String currTopic : interestedTopics) {
			// als ersteller
			TopicNode newTopic = graphDAO.createTopicNode(currTopic);
			newUser.addInterestedIn(newTopic);
		}

		String[] mentions = userMentions.replaceAll(" ", "").split(";");

		for (String currFriendName : mentions) {

			UserNode mentionedUser = graphDAO.createUserNode(currFriendName);

			for (String currTopic : interestedTopics) {
				// als getter
				TopicNode newTopic = graphDAO.createTopicNode(currTopic);
				mentionedUser.addInterestedIn(newTopic);
			}

			newUser.addFriend(mentionedUser);

		}
	}
}
