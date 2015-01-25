package aic2014.tuwien.ac.at.app;

import java.util.List;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import aic2014.tuwien.ac.at.beans.User;
import aic2014.tuwien.ac.at.dao.PublicDAOImpl;
import aic2014.tuwien.ac.at.dao.UserDao;

public class CalculatorApp {

	public static void main(String[] args) {
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
		System.out.println("Starting Calculator App...");

		PublicDAOImpl publicDao = (PublicDAOImpl) context.getBean("publicDAOImpl");
		publicDao.calculateScores();

		UserDao userDao = (UserDao) context.getBean("userDao");

		System.out.println("Testing Queries...");

		System.out.println("Finding Broad Range Interest Users: ");
		List<User> users = userDao.findUsersInterestedInBroadRangeOfTopics(20);

		for (User user : users) {
			System.out.println(user.getUsername() + " / score: " + user.getBroadInterestScore() + " / topics: "
					+ user.getTopics().size() + " / totTweet: " + user.getTotalTweetCount());
		}

		System.out.println("Finding Broad Range Interest Users: ");
		users = userDao.findUsersInterestedFocused(20);

		for (User user : users) {
			System.out.println(user.getUsername() + " / score: " + user.getFocussedInterestScore() + " / totTweet: "
					+ user.getTotalTweetCount());
		}

		System.out.println("Finished Calculator App.");
		
	}

}
