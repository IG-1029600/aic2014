package aic2014.tuwien.ac.at.web.controller;

import javax.inject.Inject;
import javax.inject.Named;

import org.springframework.context.annotation.Scope;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import aic2014.tuwien.ac.at.dao.UserDao;
import aic2014.tuwien.ac.at.web.model.AddModel;

@Named
@Scope("request")
public class AddController {

	@Inject
	private AddModel addModel;

	public void findAdds() {
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
		UserDao userDao = (UserDao) context.getBean("userDao");

		System.out.println("username:" + addModel.getUsername() + " interest:" + addModel.getInterest());

		if (addModel.getInterest().equals("existing")) {
			addModel.setSearchResult(userDao.getAddsforUser(addModel.getUsername(), 0));
			// addModel.setSearchResult(null);
		}

		if (addModel.getInterest().equals("potential")) {
			addModel.setSearchResult(userDao.getAddsforUserbyFriends(addModel.getUsername(), 3, 0));
		}

		// for (Document doc : addModel.getSearchResult()) {
		// System.out.println("Result: " + doc.getName());
		// }

		context.close();
	}

	public AddModel getAddModel() {
		return addModel;
	}

	public void setAddModel(AddModel addModel) {
		this.addModel = addModel;
	}

}
