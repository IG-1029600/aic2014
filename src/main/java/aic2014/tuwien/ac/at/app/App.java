package aic2014.tuwien.ac.at.app;

import java.util.List;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import aic2014.tuwien.ac.at.beans.User;
import aic2014.tuwien.ac.at.dao.UserDao;

public class App {

	public static void main(String[] args) {
		ClassPathXmlApplicationContext context = new 
				ClassPathXmlApplicationContext("applicationContext.xml");
		UserDao userDao = (UserDao) context.getBean("userDao");
		User ivan = new User();
		User peter = new User();
		ivan.setUsername("ivan");
		peter.setUsername("peter");
		userDao.save(ivan);
		userDao.save(peter);
		List<User> users = userDao.getAll();
		for (User user : users) {
            System.out.println(user.getUsername());
        }
        context.close();
		
	}

}
