package aic2014.tuwien.ac.at.app;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import aic2014.tuwien.ac.at.dao.PublicDAOImpl;

public class AnalyzerApp {

	public static void main(String[] args) {
		System.out.println("Starting Analyzer...");
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
		
		PublicDAOImpl publicDao = (PublicDAOImpl) context.getBean("publicDAOImpl");

		publicDao.analyze();
		
		System.out.println("Finished Analyzer.");
	}

}
