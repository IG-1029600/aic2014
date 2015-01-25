package aic2014.tuwien.ac.at.web.controller;

import javax.inject.Inject;
import javax.inject.Named;

import org.springframework.context.annotation.Scope;

import aic2014.tuwien.ac.at.web.model.AddModel;

@Named
@Scope("request")
public class AddController {

	@Inject
	private AddModel addModel;

	public void findAdds() {

		System.out.println("username:" + addModel.getUsername() + " interest:" + addModel.getInterest());

		// addModel.setSearchResult(userDao.getAddsforUser(addModel, amount));
	}

	public AddModel getAddModel() {
		return addModel;
	}

	public void setAddModel(AddModel addModel) {
		this.addModel = addModel;
	}

}
