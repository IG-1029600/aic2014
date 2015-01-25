package aic2014.tuwien.ac.at.dao;

import java.util.ArrayList;

import aic2014.tuwien.ac.at.beans.TopicNode;
import aic2014.tuwien.ac.at.beans.UserNode;

public interface IGraphDAO {

	public UserNode createUserNode(String username);

	public ArrayList<String> friendsList(String name, int depth);

	public TopicNode createTopicNode(String topicName);
}
