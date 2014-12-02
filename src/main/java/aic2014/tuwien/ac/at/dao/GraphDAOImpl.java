package aic2014.tuwien.ac.at.dao;

import java.net.UnknownHostException;

import org.neo4j.graphdb.DynamicLabel;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.RelationshipType;
import org.neo4j.graphdb.Transaction;
import org.neo4j.graphdb.factory.GraphDatabaseFactory;

import aic2014.tuwien.ac.at.beans.UserNode;

import com.mongodb.BasicDBObject;

public class GraphDAOImpl implements IGraphDAO {

	public GraphDatabaseService graphDb = null;
	public static String DB_PATH = "database/neo4j/";

	public GraphDAOImpl() throws UnknownHostException {

		graphDb = new GraphDatabaseFactory().newEmbeddedDatabase(DB_PATH);
		// registerShutdownHook(graphDb);

		try (Transaction tx = graphDb.beginTx()) {
			graphDb.schema().constraintFor(DynamicLabel.label("User")).assertPropertyIsUnique("name").create();
			tx.success();
		}

	}

	public void insertNode(UserNode user) {

	}

	public void processDbObject(BasicDBObject dbObject) {
		// TODO Auto-generated method stub

		Node newPersonNode = graphDb.createNode();
		newPersonNode.setProperty(UserNode.NAME, dbObject.get("name"));

		// lock now taken, we can check if already exist in index
		// Node alreadyExist = index.get(UserNode.NAME,
		// dbObject.get("name")).getSingle();
		// if (alreadyExist != null) {
		// throw new Exception("Person with this name already exists ");
		// }

		UserNode newUser = new UserNode(newPersonNode);

		String[] mentions = ((String) dbObject.get("friends")).split(";");

		for (String user : mentions) {
			Node currNode = graphDb.createNode();
			currNode.setProperty(UserNode.NAME, user);

			UserNode currUser = new UserNode(currNode);

			newUser.addFriend(currUser);

		}

		// index.add(newPersonNode, Person.NAME, name);

	}

	public void insertRelationShip() {
		// TODO Auto-generated method stub

	}

	public void deleteNode() {
		// TODO Auto-generated method stub

	}

	private static void registerShutdownHook(final GraphDatabaseService graphDb) {
		// Registers a shutdown hook for the Neo4j instance so that it
		// shuts down nicely when the VM exits (even if you "Ctrl-C" the
		// running application).
		Runtime.getRuntime().addShutdownHook(new Thread() {
			@Override
			public void run() {
				graphDb.shutdown();
			}
		});
	}

	public static enum RelTypes implements RelationshipType {
		FRIEND, INTERESTED_IN
	}

}
