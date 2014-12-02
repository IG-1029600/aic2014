package aic2014.tuwien.ac.at.beans;

import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Relationship;

import aic2014.tuwien.ac.at.dao.GraphDAOImpl;

public class UserNode {
	public static final String NAME = "name";
	// START SNIPPET: the-node
	private final Node underlyingNode;

	public UserNode(Node UserNodeNode) {

		this.underlyingNode = UserNodeNode;
	}

	protected Node getUnderlyingNode() {
		return underlyingNode;
	}

	// END SNIPPET: the-node
	// START SNIPPET: delegate-to-the-node
	public String getName() {
		return (String) underlyingNode.getProperty(NAME);
	}

	// END SNIPPET: delegate-to-the-node
	// START SNIPPET: override
	@Override
	public int hashCode() {
		return underlyingNode.hashCode();
	}

	@Override
	public boolean equals(Object o) {
		return o instanceof UserNode && underlyingNode.equals(((UserNode) o).getUnderlyingNode());
	}

	@Override
	public String toString() {
		return "UserNode[" + getName() + "]";
	}

	// END SNIPPET: override
	public void addFriend(UserNode otherUserNode) {
		if (!this.equals(otherUserNode)) {
			Relationship friendRel = getFriendRelationshipTo(otherUserNode);
			if (friendRel == null) {
				underlyingNode.createRelationshipTo(otherUserNode.getUnderlyingNode(), GraphDAOImpl.RelTypes.FRIEND);
			}
		}
	}

	public void removeFriend(UserNode otherUserNode) {
		if (!this.equals(otherUserNode)) {
			Relationship friendRel = getFriendRelationshipTo(otherUserNode);
			if (friendRel != null) {
				friendRel.delete();
			}
		}
	}

	private GraphDatabaseService graphDb() {
		return underlyingNode.getGraphDatabase();
	}

	private Relationship getFriendRelationshipTo(UserNode otherUserNode) {
		Node otherNode = otherUserNode.getUnderlyingNode();
		for (Relationship rel : underlyingNode.getRelationships(GraphDAOImpl.RelTypes.FRIEND)) {
			if (rel.getOtherNode(underlyingNode).equals(otherNode)) {
				return rel;
			}
		}
		return null;
	}

}