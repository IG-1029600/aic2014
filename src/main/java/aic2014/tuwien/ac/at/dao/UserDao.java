package aic2014.tuwien.ac.at.dao;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.transaction.annotation.Transactional;

import aic2014.tuwien.ac.at.beans.Document;
import aic2014.tuwien.ac.at.beans.Topic;
import aic2014.tuwien.ac.at.beans.User;

@Transactional
public class UserDao {

	@PersistenceContext
	private EntityManager em;

	public Long save(User user) {
		em.persist(user);
		return user.getId();
	}

	public long updateUser(User user) {
		em.merge(user);
		return user.getId();
	}

	public List<User> getAll() {
		return em.createQuery("SELECT u FROM User u", User.class).getResultList();
	}

	public List<User> getOne(String username) {
		if (username.contains("'") == true) {
			username = username.replaceAll("'", "''");
		}
		return em.createQuery("SELECT u FROM User u WHERE u.username= '" + username + "'", User.class).getResultList();
	}

	public ArrayList<Document> getAddsforUser(String username, int numberOfMentioned) {

		List<User> userList = getOne(username);

		User user = userList.get(0);

		ArrayList<String> topicList = new ArrayList<String>();

		for (Topic topic : user.getTopics()) {

			if (topic.getCount() >= numberOfMentioned) {

				topicList.add(topic.getName());

			}

		}

		DocumentStoreDAOImpl docStore = new DocumentStoreDAOImpl();

		ArrayList<Document> docList = docStore.findAddsForKeywords(topicList);

		return docList;
	}

	
	
	

	public ArrayList<Document> getAddsforUserbyFriends(String username, int depth, int numberOfMentioned){
		
		ArrayList<String> topicList = new ArrayList<String>();
		
	
		ArrayList<String> friendList;
		try {
			
		
			
			GraphDAOImpl graph = new GraphDAOImpl();
			
			
		
			
			
			friendList = graph.friendsList(username, depth);
		
		
		
			
			
		
			for(String friend: friendList){
				System.out.println(friend);
				List<User> userList = getOne(friend);
				if(userList.size()!=0){
					User user = userList.get(0);
				
					for(Topic topic : user.getTopics() ){
					
						if(topic.getCount()>=numberOfMentioned){
					
							
							if(topicList.contains(topic.getName())==false){
								topicList.add(topic.getName());
							}
					
					}
				
				
					}
				}
			}	
			
			
			
		
		
		
		DocumentStoreDAOImpl docStore = new DocumentStoreDAOImpl();
		
		ArrayList<Document> docList = docStore.findAddsForKeywords(topicList);
		
		return docList;
		
			
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		
	}
	
	
	
	public List<User> getInfluentUsers(int limit){
		
		return em.createQuery("SELECT u FROM User u  WHERE (u.favorites+u.retweets+u.numOfFollowers)>100 order by (u.favorites+u.retweets+u.numOfFollowers) desc",User.class).setMaxResults(limit).getResultList();
	}
	public List<User> findUsersInterestedFocused(int count) {

		return em.createQuery("SELECT u FROM User u ORDER BY u.focussedInterestScore desc", User.class).setMaxResults(count)
				.getResultList();
	}

	public List<User> findUsersInterestedInBroadRangeOfTopics(int count) {

		//SELECT u FROM User u ORDER BY u.broadInterestScore DESC LIMIT
		
		return em.createQuery("SELECT u FROM User u order by u.broadInterestScore desc", User.class).setMaxResults(count)
				.getResultList();
	}

}
