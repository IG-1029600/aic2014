package aic2014.tuwien.ac.at.beans;

import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Transaction;

public class TopicNode {
	public static final String NAME = "name";
	// START SNIPPET: the-node
	private final Node underlyingNode;

	public TopicNode(Node TopicNode) {

		this.underlyingNode = TopicNode;
	}

	protected Node getUnderlyingNode() {
		return underlyingNode;
	}

	// END SNIPPET: the-node
	// START SNIPPET: delegate-to-the-node
	public String getName() {
		String name = null;
		try ( Transaction tx = graphDb().beginTx() )
		{
		    name = (String) underlyingNode.getProperty(NAME);
		    tx.success();	 
		    
		}catch(Exception e){
			e.printStackTrace();
		}
		return name;
	}

	// END SNIPPET: delegate-to-the-node
	// START SNIPPET: override
	@Override
	public int hashCode() {
		return underlyingNode.hashCode();
	}

	@Override
	public boolean equals(Object o) {
		return o instanceof TopicNode && underlyingNode.equals(((TopicNode) o).getUnderlyingNode());
	}

	@Override
	public String toString() {
		return "TopicNode[" + getName() + ", NodeId: "+ underlyingNode.getId() + "]";
	}


	private GraphDatabaseService graphDb() {
		return underlyingNode.getGraphDatabase();
	}

}
