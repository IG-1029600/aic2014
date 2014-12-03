package aic2014.tuwien.ac.at.dao;

import java.util.ArrayList;
import java.util.List;

import aic2014.tuwien.ac.at.beans.Topic;
import aic2014.tuwien.ac.at.beans.User;

public class TopicDaoImpl {

	public TopicDaoImpl(){
		
	}
	
	
	

	
	public List<Topic> createTopic(String text,User user){
		
		List<Topic>topics=new ArrayList<>();
		String[] split=text.split(";");
		
		for(String t:split){
			Topic topic = new Topic();
			topic.setCount(1);
			topic.setName(t);
			topic.setUser(user);
			topics.add(topic);
		}
		
		return topics;
	}
	
	public List<Topic> updateTopic(String text, List<Topic> topics, User user){
		
		String[] split=text.split(";");
		
		for(String t:split){
			boolean cont=false;
			
			for(Topic topic:topics){
				if(topic.getName().equals(t)){
					int count=topic.getCount()+1;
					topic.setCount(count);
					cont=true;
				}
			}
			
			if(cont==false){
				Topic topic = new Topic();
				topic.setCount(1);
				topic.setName(t);
				topic.setUser(user);
				topics.add(topic);
			}
		}
		return topics;
	}
	
	

}
