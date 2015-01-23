package aic2014.tuwien.ac.at.web.controller;

import javax.inject.Inject;
import javax.inject.Named;

import org.springframework.context.annotation.Scope;

import aic2014.tuwien.ac.at.dao.UserDao;
import aic2014.tuwien.ac.at.web.model.AddModel;

@Named
@Scope("request")
public class AddController {

	@Inject
	private AddModel addModel;

	@Inject
	private UserDao userDao;

	private String username;
	private int amount;

	public void findAdds(String username, int amount) {

		addModel.setSearchResult(userDao.getAddsforUser(username, amount));
	}

	public AddModel getAddModel() {
		return addModel;
	}

	public void setAddModel(AddModel addModel) {
		this.addModel = addModel;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}

}
