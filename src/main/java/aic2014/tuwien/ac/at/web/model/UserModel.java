package aic2014.tuwien.ac.at.web.model;

import java.util.List;

import javax.inject.Named;

import aic2014.tuwien.ac.at.beans.User;

@Named
public class UserModel {

	private List<User> searchResult;

	private String amount;
	private String type;

	public void setSearchResult(List<User> searchResult) {
		this.searchResult = searchResult;
	}

	public List<User> getSearchResult() {
		return searchResult;
	}

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

}
