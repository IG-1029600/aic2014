package aic2014.tuwien.ac.at.services;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import aic2014.tuwien.ac.at.beans.Topic;
import aic2014.tuwien.ac.at.beans.User;
import aic2014.tuwien.ac.at.dao.UserDao;
@Component
public class InterestedUsersCalculationService {

	@Autowired
	private UserDao userDao;

	public InterestedUsersCalculationService() {
	}

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
			
			//System.out.println("Debug> score:" + score);
		}
		double endScore = Collections.max(scores);
		endScore *= scaleFactor;
		System.out.println("Debug> endScore: " +endScore);
		user.setFocussedInterestScore(endScore);

		userDao.updateUser(user);
		return user;
	}

	private User calculateFocusedScoreForUser(User user, double alphaParam) {
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

	public void calculateBroadScore(double alphaParam) {
		if (alphaParam <= 0) {
			System.out.println("invalid value for alphaParam: " + alphaParam + " ; abort calculating scores");
			return;
		}

		for (User currUser : userDao.getAll()) {
			calculateBroadScoreForUser(currUser, alphaParam);
		}
	}

	private User calculateBroadScoreForUser(User user) {

		int userTopics = user.getTopics().size();

		double score = userTopics / user.getTotalTweetCount();

		user.setBroadInterestScore(score);
		return user;
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