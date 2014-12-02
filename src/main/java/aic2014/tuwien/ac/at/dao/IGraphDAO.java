package aic2014.tuwien.ac.at.dao;

import com.mongodb.BasicDBObject;

public interface IGraphDAO {

	void processDbObject(BasicDBObject dbObject);

	void insertRelationShip();

	void deleteNode();

}
