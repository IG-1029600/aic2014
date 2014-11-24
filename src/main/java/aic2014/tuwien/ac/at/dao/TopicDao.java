package aic2014.tuwien.ac.at.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.transaction.annotation.Transactional;

import aic2014.tuwien.ac.at.beans.Topic;

@Transactional
public class TopicDao {
	
	@PersistenceContext
	private EntityManager em;

	public Long save(Topic topic) {
		em.persist(topic);
		return topic.getId();
	}

	public List<Topic> getAll() {
		return em.createQuery("SELECT t FROM Topic t", Topic.class)
				.getResultList();
	}

}
