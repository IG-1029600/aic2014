package aic2014.tuwien.ac.at.web.model;

import java.util.List;

import javax.inject.Named;

import aic2014.tuwien.ac.at.beans.Document;

@Named
public class AddModel {

	// private final List<Adds> searchAdds = newArrayList<>();

	private List<Document> searchResult;

	public List<Document> getSearchResult() {
		return searchResult;
	}

	public void setSearchResult(List<Document> searchResult) {
		this.searchResult = searchResult;
	}

}
