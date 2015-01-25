package aic2014.tuwien.ac.at.services;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import aic2014.tuwien.ac.at.beans.Topic;
import aic2014.tuwien.ac.at.beans.User;
import aic2014.tuwien.ac.at.dao.UserDao;

public class InterestedUsersCalculationService {

	private UserDao userDao;

	public InterestedUsersCalculationService() {
	}

	public void calculateFocusedScore(double alphaParam, int scaleFactor) {

		if (alphaParam <= 0) {
			System.out.println("invalid value for alphaParam: " + alphaParam + " ; abort calculating scores");
			return;
		}

		List<User> users = userDao.getAll();

		for (User currUser : users) {

			calculateFocusedScoreForUser(currUser, scaleFactor);
		}
	}

	private User calculateFocusedScoreForUser(User user, int scaleFactor) {

		List<Double> scores = new ArrayList<>();

		for (Topic currTopic : user.getTopics()) {
			double score = (double) currTopic.getCount() / user.getTotalTweetCount();
			scores.add(score);

			// System.out.println("Debug> score:" + score);
		}
		if (scores.isEmpty()) {
			return user;
		}
		double endScore = Collections.max(scores);
		endScore *= scaleFactor;
		System.out.println("Debug> endScore: " + endScore);
		user.setFocussedInterestScore(endScore);

		userDao.updateUser(user);
		return user;
	}

	public void calculateBroadScore(double alphaParam) {
		if (alphaParam <= 0) {
			System.out.println("invalid value for alphaParam: " + alphaParam + " ; abort calculating scores");
			return;
		}

		for (User currUser : userDao.getAll()) {
			calculateBroadScoreForUser(currUser, alphaParam);
		}
	}

	private User calculateBroadScoreForUser(User user, double alphaParam) {
		if (alphaParam <= 0) {
			System.out.println("invalid value for alphaParam: " + alphaParam + " ; abort calculating scores");
			return user;
		}

		int userTopics = user.getTopics().size();

		double score = userTopics / (user.getTotalTweetCount() * alphaParam);

		user.setBroadInterestScore(score);
		userDao.updateUser(user);
		return user;
	}

	@Deprecated
	public List<User> calculateFocusedScoreAndGetUsers(int count, double alphaParam) {

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

	public List<User> findUsersInterestedInBroadRangeOfTopics(int count) {

		return userDao.findUsersInterestedInBroadRangeOfTopics(count);
	}

	public List<User> findUsersInterestedFocused(int count) {

		// max( t1Count/TotTweetCount ... tnCount/TotTweetCount )
		return userDao.findUsersInterestedFocused(count);
	}

	public UserDao getUserDao() {
		return userDao;
	}

	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}

}
