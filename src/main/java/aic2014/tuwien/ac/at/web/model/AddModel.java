package aic2014.tuwien.ac.at.web.model;

import java.util.List;

import javax.inject.Named;

import aic2014.tuwien.ac.at.beans.Document;

@Named
public class AddModel {

	private List<Document> searchResult;

	private String username;
	private String interest;

	public List<Document> getSearchResult() {
		return searchResult;
	}

	public void setSearchResult(List<Document> searchResult) {
		this.searchResult = searchResult;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getInterest() {
		return interest;
	}

	public void setInterest(String interest) {
		this.interest = interest;
	}

}
