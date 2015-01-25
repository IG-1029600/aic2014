package aic2014.tuwien.ac.at.services;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import aic2014.tuwien.ac.at.beans.Topic;
import aic2014.tuwien.ac.at.beans.User;
import aic2014.tuwien.ac.at.dao.UserDao;

public class InterestedUsersCalculationService {

	private UserDao userDao;

	public InterestedUsersCalculationService() {
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
		userDao = (UserDao) context.getBean("userDao");
	}

	public List<User> calculateFocusedScore(int count, double alphaParam) {

		if (alphaParam <= 0) {
			System.out.println("invalid value for alphaParam: " + alphaParam + " ; abort calculating scores");
			return new ArrayList<User>();
		}

		List<User> users = userDao.getAll();

		for (User currUser : users) {

			List<Double> scores = new ArrayList<>();

			for (Topic currTopic : currUser.getTopics()) {
				double score = currTopic.getCount() / (currUser.getTotalTweetCount() * alphaParam);
				scores.add(score);
			}

			currUser.setFocussedInterestScore(Collections.max(scores));
		}

		Collections.sort(users, new User.UserFocussedInterestScoreComparator());

		return users.subList(0, count);
	}

	public User calculateFocusedScoreForUser(User user) {

		List<Double> scores = new ArrayList<>();

		for (Topic currTopic : user.getTopics()) {
			double score = currTopic.getCount() / user.getTotalTweetCount();
			scores.add(score);
		}

		user.setFocussedInterestScore(Collections.max(scores));

		// userDao.updateUser(user);
		return user;
	}

	public User calculateFocusedScoreForUser(User user, double alphaParam) {
		if (alphaParam <= 0) {
			System.out.println("invalid value for alphaParam: " + alphaParam + " ; abort calculating scores");
			return user;
		}

		List<Double> scores = new ArrayList<>();

		for (Topic currTopic : user.getTopics()) {
			double score = currTopic.getCount() / (user.getTotalTweetCount() * alphaParam);
			scores.add(score);
		}

		user.setFocussedInterestScore(Collections.max(scores));

		// userDao.updateUser(user);
		return user;
	}

	public User calculateBroadScoreForUser(User user) {

		int userTopics = user.getTopics().size();

		double score = userTopics / user.getTotalTweetCount();

		user.setBroadInterestScore(score);
		return user;
	}

	public User calculateBroadScoreForUser(User user, double alphaParam) {
		if (alphaParam <= 0) {
			System.out.println("invalid value for alphaParam: " + alphaParam + " ; abort calculating scores");
			return user;
		}

		int userTopics = user.getTopics().size();

		double score = userTopics / (user.getTotalTweetCount() * alphaParam);

		user.setBroadInterestScore(score);
		return user;
	}

	public List<User> findUsersInterestedInBroadRangeOfTopics(int count) {

		return userDao.findUsersInterestedInBroadRangeOfTopics(count);
	}

	public List<User> findUsersInterestedFocused(int count) {

		// max( t1Count/TotTweetCount ... tnCount/TotTweetCount )
		return userDao.findUsersInterestedFocused(count);
	}

}
