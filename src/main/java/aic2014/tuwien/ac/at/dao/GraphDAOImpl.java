package aic2014.tuwien.ac.at.dao;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
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
import org.neo4j.graphdb.traversal.Evaluators;
import org.neo4j.cypher.javacompat.ExecutionResult;
import aic2014.tuwien.ac.at.beans.TopicNode;
import aic2014.tuwien.ac.at.beans.UserNode;
import org.neo4j.helpers.collection.IteratorUtil;


//TODO update interface in the end
public class GraphDAOImpl {// implements IGraphDAO {

	public GraphDatabaseService graphDb = null;
	public static String DB_PATH = "database/neo4j/";
	private final ExecutionEngine engine;

	public GraphDAOImpl() throws UnknownHostException {

		graphDb = new GraphDatabaseFactory().newEmbeddedDatabase(DB_PATH);

		this.engine = new ExecutionEngine(graphDb);

		// registerShutdownHook(graphDb);

		try (Transaction tx = graphDb.beginTx()) {
			if (!graphDb.schema().getConstraints(DynamicLabel.label("User")).iterator().hasNext()) {
				graphDb.schema().constraintFor(DynamicLabel.label("User")).assertPropertyIsUnique("name").create();
				// graphDb.schema().indexFor(DynamicLabel.label("User")).on("name").create();

			} else {
				System.out.println("Uniqueness constraint on User already defined");
			}

			if (!graphDb.schema().getConstraints(DynamicLabel.label("Topic")).iterator().hasNext()) {
				graphDb.schema().constraintFor(DynamicLabel.label("Topic")).assertPropertyIsUnique("name").create();
				// graphDb.schema().indexFor(DynamicLabel.label("User")).on("name").create();

			} else {
				System.out.println("Uniqueness constraint on Topic already defined");
			}
			tx.success();
		}

	}

	public UserNode createUserNode(String username) {

		UserNode userNode = null;

		Node result = null;
		ResourceIterator<Object> resultIterator = null;
		try (Transaction tx = graphDb.beginTx()) {
			// CREATE/MATCH
			String queryString = "MERGE (n:User {name: {name}}) RETURN n";
			Map<String, Object> parameters = new HashMap<>();
			parameters.put("name", username);
			engine.execute(queryString, parameters);
			resultIterator = engine.execute(queryString, parameters).columnAs("n");
			result = (Node) resultIterator.next();

			userNode = new UserNode(result);
			tx.success();
		} catch (Exception e) {
			e.printStackTrace();
		}

		System.out.println("merged " + userNode);

		return userNode;
	}

	
	public ArrayList<String> friendsList(String name, int depth){
		
		
		

		ExecutionResult result;
		
		
		
		try(Transaction tx = graphDb.beginTx()){
			
			ArrayList<String> friendList = new ArrayList<String>();
			
			result = engine.execute( "match (n {name: '"+name+"'}) return n" );	
			
			 Iterator<Node> n_column = result.columnAs( "n" );
			 
		
			 
			 for ( Node start : IteratorUtil.asIterable( n_column ) ){
		
				 for ( Node node : graphDb.traversalDescription()
							.depthFirst()
							.relationships( RelTypes.FRIEND)
							.evaluator( Evaluators.toDepth( depth ) )
							.traverse(start)
							.nodes())
						{
					 		
							friendList.add((String) node.getProperty("name"));
						}
				 
				 
			}
			
			
		
			 tx.success();
			
			 return friendList;
		
		}catch(Exception e){
		
			
			e.printStackTrace();
		}
		
		return null;
	}


	
	
	
	public TopicNode createTopicNode(String topicName) {

		TopicNode topicNode = null;

		Node result = null;
		ResourceIterator<Object> resultIterator = null;
		try (Transaction tx = graphDb.beginTx()) {
			String queryString = "MERGE (n:Topic {name: {name}}) RETURN n";
			Map<String, Object> parameters = new HashMap<>();
			parameters.put("name", topicName);
			engine.execute(queryString, parameters);
			resultIterator = engine.execute(queryString, parameters).columnAs("n");
			result = (Node) resultIterator.next();

			topicNode = new TopicNode(result);
			tx.success();
		} catch (Exception e) {
			e.printStackTrace();
		}

		System.out.println("created " + topicNode);

		return topicNode;
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
