
package aic2014.tuwien.ac.at.beans;
public class Document {
	private String name;
	private String company;
	private String filepath;
	private String category;
	private String homepage;
	
	
	public Document(String name, String company, String filepath, String category, String homepage){
		this.name=name;
		this.company=company;
		this.filepath=filepath;
		this.category=category;
		this.homepage=homepage;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public String getCompany() {
		return company;
	}


	public void setCompany(String company) {
		this.company = company;
	}


	public String getFilepath() {
		return filepath;
	}


	public void setFilepath(String filepath) {
		this.filepath = filepath;
	}


	public String getCategory() {
		return category;
	}


	public void setCategory(String category) {
		this.category = category;
	}


	public String getHomepage() {
		return homepage;
	}


	public void setHomepage(String homepage) {
		this.homepage = homepage;
	}
	
	
	
	
}
