package aic2014.tuwien.ac.at.app;

import java.util.List;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import aic2014.tuwien.ac.at.beans.Document;
import aic2014.tuwien.ac.at.beans.User;
import aic2014.tuwien.ac.at.dao.DocumentStoreDAOImpl;
import aic2014.tuwien.ac.at.dao.PublicDAOImpl;
import aic2014.tuwien.ac.at.dao.UserDao;
import aic2014.tuwien.ac.at.services.PublicStreamService;

public class App {

	public static void main(String[] args) {
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
		UserDao userDao = (UserDao) context.getBean("userDao");

		// User ivan = new User();
		// User peter = new User();
		// ivan.setUsername("ivan");
		// peter.setUsername("peter");
		// userDao.save(ivan);
		// userDao.save(peter);

//		List<User> users = userDao.getInfluentUsers(10);
//		for (User user : users) {
//			System.out.println(user.getUsername());
//		}

		PublicStreamService streamService = new PublicStreamService();

		streamService.listenToStream();

		try {
			PublicDAOImpl publicDao = (PublicDAOImpl) context.getBean("publicDAOImpl");

			// publicDao.analyze();
			// publicDao.calculateScores();

			//DocumentStoreDAOImpl docDao = new DocumentStoreDAOImpl();

			//Document document = new Document("name", "company", "filepath", "art", "homepage");

			//docDao.createDocument(document);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// Graph testing
		// GraphService graphService = new GraphService();
		//
		// String testUser = "testUser1";
		// String mentions = "mentionedUser1;mentionedUser2";
		//
		// String topic = "TestTopic1;TestTopic2";
		//
		// graphService.processJSONStrings(testUser, mentions, topic);
		// graphService.processJSONStrings(testUser, mentions, topic);
		//
		// testUser = "testUser2";
		// mentions = "testUser1;mentionedUser3";
		//
		// graphService.processJSONStrings(testUser, mentions, topic);
		context.close();

	}

}
