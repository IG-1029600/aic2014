package aic2014.tuwien.ac.at.beans;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Topic {
	
	@Id
	@GeneratedValue
	private Long id;
	private String name;
	private List<String> keywords;
	public Topic(){}
	public Topic(String name, List<String> keywords) {

		this.name = name;
		this.keywords = keywords;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public List<String> getKeywords() {
		return keywords;
	}
	public void setKeywords(List<String> keywords) {
		this.keywords = keywords;
	}
	
	

}
