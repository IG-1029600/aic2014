package aic2014.tuwien.ac.at.dao;

import java.util.ArrayList;

import aic2014.tuwien.ac.at.beans.Document;

public interface IDocumentStoreDAO {
	
	
	public boolean createDocument(Document document);
	public Document findDocument(String name);
	public boolean deleteDocument(String name);
	public boolean deleteDocumentStore();
	public void getImage(String filename, String destination);
	public void deleteImageStore(String name);
	public ArrayList<Document> findAddsForKeywords(ArrayList<String> keywords);
}
