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

	public void findAdds() {

		System.out.println("username:" + addModel.getUsername() + " interest:" + addModel.getInterest());

		if (addModel.getInterest().equals("existing")) {
			addModel.setSearchResult(userDao.getAddsforUser(addModel.getUsername(), 0));
		}

		if (addModel.getInterest().equals("potential")) {
			addModel.setSearchResult(userDao.getAddsforUserbyFriends(addModel.getUsername(), 3, 0));
		}

	}

	public AddModel getAddModel() {
		return addModel;
	}

	public void setAddModel(AddModel addModel) {
		this.addModel = addModel;
	}

}
