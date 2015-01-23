package aic2014.tuwien.ac.at.web.model;

import java.util.List;

import javax.inject.Named;

import aic2014.tuwien.ac.at.beans.User;

@Named
public class UserModel {

	private List<User> searchResult;

	public void setSearchResult(List<User> searchResult) {
		this.searchResult = searchResult;
	}

	public List<User> getSearchResult() {
		return searchResult;
	}

}
