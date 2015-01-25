package aic2014.tuwien.ac.at.web.controller;

import javax.inject.Inject;
import javax.inject.Named;

import org.springframework.context.annotation.Scope;

import aic2014.tuwien.ac.at.beans.User;
import aic2014.tuwien.ac.at.dao.UserDao;
import aic2014.tuwien.ac.at.web.model.UserModel;

@Named
@Scope("request")
public class UserController {

	@Inject
	private UserModel userModel;

	@Inject
	private UserDao userDao;

	public void findUsers() {

		// ClassPathXmlApplicationContext context = new
		// ClassPathXmlApplicationContext("applicationContext.xml");
		// UserDao userDao = (UserDao) context.getBean("userDao");

		if (userModel.getType().equals("most")) {
			System.out.println("amount: " + userModel.getAmount());
			// userModel.setSearchResult(userDao.getInfluentUsers(Integer.parseInt(userModel.getAmount())));

			for (User u : userDao.getInfluentUsers(Integer.parseInt(userModel.getAmount()))) {
				System.out.println("username: " + u.getUsername());
			}
		}

		if (userModel.getType().equals("broad")) {

		}

		if (userModel.getType().equals("focused")) {

		}

		// context.close();

	}

	/*
	 * @Inject private UserDao userDao;
	 * 
	 * // @Inject // private IUserService userService;
	 * 
	 * public void findUsers() {
	 * 
	 * System.out.println("QueryType:" + userModel.getType() + " amount:" +
	 * userModel.getAmount()); //
	 * userModel.setSearchResult(userService.findUsers()); }
	 * 
	 * public void findInterestedFocussedUseres(int amount) {
	 * 
	 * userDao.findUsersInterestedFocused(amount); //
	 * userModel.setSearchResult(userService.findUsers()); }
	 * 
	 * public void findInterestedBroadUsers(int amount) {
	 * 
	 * userDao.findUsersInterestedInBroadRangeOfTopics(amount);
	 * 
	 * // userModel.setSearchResult(userService.findUsers()); }
	 * 
	 * public void findInfluentUsers(int amount) {
	 * 
	 * userDao.getInfluentUsers(amount);
	 * 
	 * // userModel.setSearchResult(userService.findUsers()); }
	 */
	public UserModel getUserModel() {
		return userModel;
	}

	public void setUserModel(UserModel userModel) {
		this.userModel = userModel;
	}

}
