package aic2014.tuwien.ac.at.app;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import aic2014.tuwien.ac.at.beans.Document;
import aic2014.tuwien.ac.at.dao.DocumentStoreDAOImpl;
import aic2014.tuwien.ac.at.dao.PublicDAOImpl;

public class CreateAdApp {

	public static void main(String[] args) {
		System.out.println("Startet creating ads.");
		
		DocumentStoreDAOImpl docDao = new DocumentStoreDAOImpl();
		

		Document doc1 = new Document("Picture auction", "dorotheum", "pic1.jpg", "art", "http://www.dorotheum.com");
		Document doc2 = new Document("new jobs", "monster", "pic2.jpg", "work", "http://www.monster.at");
		Document doc3 = new Document("new iPhone", "Apple", "pic3.jpg", "apple", "http://www.apple.com");
		Document doc4 = new Document("Naturhistorisches Museum", "Naturhistorisches Museum", "pic4.jpg", "history", "http://www.nhm-wien.ac.at");
		Document doc5 = new Document("deposit", "Bank Austria", "pic5.jpg", "finance", "http://www.bankaustria.at");
		Document doc6 = new Document("new processor", "intel", "pic6.jpg", "hardware", "http://www.intel.com");
		Document doc7 = new Document("Windows 10", "microsoft", "pic7.jpg", "software", "http://www.microsoft.com");
		Document doc8 = new Document("Get sober", "Alcoholic Anonymous", "pic8.jpg", "alcohol", "http://www.aa.org");
		Document doc9 = new Document("Wie findest du das", "Thalia", "pic9.jpg", "books", "http://www.thalia.at");
		

		docDao.createDocument(doc1);
		docDao.createDocument(doc2);
		docDao.createDocument(doc3);
		docDao.createDocument(doc4);
		docDao.createDocument(doc5);
		docDao.createDocument(doc6);
		docDao.createDocument(doc7);
		docDao.createDocument(doc8);
		docDao.createDocument(doc9);
		
		System.out.println("Finished creating ads.");

	}

}
