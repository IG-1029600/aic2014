package aic2014.tuwien.ac.at.app;

import java.net.UnknownHostException;
import java.util.List;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import aic2014.tuwien.ac.at.beans.User;
import aic2014.tuwien.ac.at.dao.GraphDAOImpl;
import aic2014.tuwien.ac.at.dao.GraphService;
import aic2014.tuwien.ac.at.dao.IGraphDAO;
import aic2014.tuwien.ac.at.dao.UserDao;

public class App {

	public static void main(String[] args) {
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
		UserDao userDao = (UserDao) context.getBean("userDao");
		User ivan = new User();
		User peter = new User();
		ivan.setUsername("ivan");
		peter.setUsername("peter");
		userDao.save(ivan);
		userDao.save(peter);
		List<User> users = userDao.getAll();
		for (User user : users) {
			System.out.println(user.getUsername());
		}
		context.close();

		
		//Graph testing
		GraphService graphService = new GraphService();
		
		String testUser = "testUser1";
		String mentions = "mentionedUser1;mentionedUser2";
		
		String topic = "TestTopic";
		
		graphService.processJSONStrings(testUser, mentions, topic);
		graphService.processJSONStrings(testUser, mentions, topic);
		
		testUser = "testUser2";
		mentions = "testUser1;mentionedUser3";
		
		graphService.processJSONStrings(testUser, mentions, topic);
		
	}

}
