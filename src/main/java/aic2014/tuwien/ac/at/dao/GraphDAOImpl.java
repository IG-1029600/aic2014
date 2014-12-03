package aic2014.tuwien.ac.at.dao;

import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Map;


import org.neo4j.cypher.javacompat.ExecutionEngine;
//import org.neo4j.cypher.ExecutionEngine;
import org.neo4j.graphdb.DynamicLabel;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.RelationshipType;
import org.neo4j.graphdb.ResourceIterator;
import org.neo4j.graphdb.Transaction;
import org.neo4j.graphdb.factory.GraphDatabaseFactory;

import scala.collection.Iterator;
import aic2014.tuwien.ac.at.beans.UserNode;

import com.mongodb.BasicDBObject;

//TODO update interface in the end
public class GraphDAOImpl {//implements IGraphDAO {

	public GraphDatabaseService graphDb = null;
	public static String DB_PATH = "database/neo4j/";
	private final ExecutionEngine engine;

	public GraphDAOImpl() throws UnknownHostException {

		graphDb = new GraphDatabaseFactory().newEmbeddedDatabase(DB_PATH);
		
		this.engine = new ExecutionEngine( graphDb);
		
		// registerShutdownHook(graphDb);

		try (Transaction tx = graphDb.beginTx()) {
			if(!graphDb.schema().getConstraints(DynamicLabel.label("User")).iterator().hasNext()){
				graphDb.schema().constraintFor(DynamicLabel.label("User")).assertPropertyIsUnique("name").create();
				//graphDb.schema().indexFor(DynamicLabel.label("User")).on("name").create();
				
				
			}else{
				System.out.println("Uniqueness constraint on User already defined");
			}
			tx.success();
		}

	}

	public UserNode createUserNode(String username) {
		
		UserNode userNode = null;
		
		Node result = null;
		ResourceIterator<Object> resultIterator = null;
		try ( Transaction tx = graphDb.beginTx() )
		{
		    String queryString = "MERGE (n:User {name: {name}}) RETURN n";
		    Map<String, Object> parameters = new HashMap<>();
		    parameters.put( "name", username );
		    engine.execute( queryString, parameters );
		    resultIterator = engine.execute( queryString, parameters ).columnAs( "n" );
		    result = (Node) resultIterator.next();
		    
		    userNode = new UserNode(result);
		    tx.success();	   	   
		}catch(Exception e){
			e.printStackTrace();
		}
		
		System.out.println(userNode);
		
		return userNode;
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
