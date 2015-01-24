package aic2014.tuwien.ac.at.web.controller;

import javax.inject.Inject;
import javax.inject.Named;

import org.springframework.context.annotation.Scope;

import aic2014.tuwien.ac.at.web.model.UserModel;

@Named
@Scope("request")
public class UserController {

	@Inject
	private UserModel userModel;

	// @Inject
	// private IUserService userService;

	public void findUsers() {

		System.out.println("QueryType:" + userModel.getType() + " amount:" + userModel.getAmount());

		// userModel.setSearchResult(userService.findUsers());
	}

	public UserModel getUserModel() {
		return userModel;
	}

	public void setUserModel(UserModel userModel) {
		this.userModel = userModel;
	}
}
