package aic2014.tuwien.ac.at.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.transaction.annotation.Transactional;

import aic2014.tuwien.ac.at.beans.User;
@Transactional
public class UserDao {

	@PersistenceContext
	private EntityManager em;

	public Long save(User user) {
		em.persist(user);
		return user.getId();
	}

	public long updateUser(User user){
		em.merge(user);
		return user.getId();
	}
	
	
	public List<User> getAll() {
		return em.createQuery("SELECT u FROM User u", User.class)
				.getResultList();
	}
	
	public List<User> getOne(String username){
		if(username.contains("'")==true){
			username=username.replaceAll("'", "''");
		}
		return em.createQuery("SELECT u FROM User u WHERE u.username= '"+username+"'",User.class)
				.getResultList();
	}
	
	
}
