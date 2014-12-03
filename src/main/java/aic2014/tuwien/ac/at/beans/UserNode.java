package aic2014.tuwien.ac.at.beans;

import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Relationship;
import org.neo4j.graphdb.Transaction;

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
		String name = null;
		try (Transaction tx = graphDb().beginTx()) {
			name = (String) underlyingNode.getProperty(NAME);
			tx.success();

		} catch (Exception e) {
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
		return o instanceof UserNode
				&& underlyingNode.equals(((UserNode) o).getUnderlyingNode());
	}

	@Override
	public String toString() {
		return "UserNode[" + getName() + ", NodeId: "+ underlyingNode.getId() + "]";
	}

	// END SNIPPET: override
	public void addFriend(UserNode otherUserNode) {
		try (Transaction tx = graphDb().beginTx()) {

			if (!this.equals(otherUserNode)) {
				Relationship friendRel = getFriendRelationshipTo(otherUserNode);
				if (friendRel == null) {
					underlyingNode.createRelationshipTo(
							otherUserNode.getUnderlyingNode(),
							GraphDAOImpl.RelTypes.FRIEND);

					tx.success();	
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println(this.toString() + " FRIEND_REL TO --> " +otherUserNode);
	}

	public void removeFriend(UserNode otherUserNode) {
		if (!this.equals(otherUserNode)) {
			Relationship friendRel = getFriendRelationshipTo(otherUserNode);
			if (friendRel != null) {
				friendRel.delete();
			}
		}
	}

	public void addInterestedIn(TopicNode topicNode) {
		try (Transaction tx = graphDb().beginTx()) {

			Relationship topicRel = getInterestedInRelationshipTo(topicNode);
			if (topicRel == null) {
				underlyingNode.createRelationshipTo(
						topicNode.getUnderlyingNode(),
						GraphDAOImpl.RelTypes.INTERESTED_IN);

				tx.success();
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		
		System.out.println(this.toString() + " INTERESTED_IN_REL --> " +topicNode);
	}

	public void removeInterestedIn(TopicNode topicNode) {

			Relationship topicRel = getInterestedInRelationshipTo(topicNode);
			if (topicRel != null) {
				topicRel.delete();
			}
		
	}

	private GraphDatabaseService graphDb() {
		return underlyingNode.getGraphDatabase();
	}

	private Relationship getFriendRelationshipTo(UserNode otherUserNode) {
		Node otherNode = otherUserNode.getUnderlyingNode();
		for (Relationship rel : underlyingNode
				.getRelationships(GraphDAOImpl.RelTypes.FRIEND)) {
			if (rel.getOtherNode(underlyingNode).equals(otherNode)) {

				return rel;
			}
		}

		return null;
	}

	private Relationship getInterestedInRelationshipTo(TopicNode topicNode) {
		Node otherNode = topicNode.getUnderlyingNode();
		for (Relationship rel : underlyingNode
				.getRelationships(GraphDAOImpl.RelTypes.INTERESTED_IN)) {

			if (rel.getOtherNode(underlyingNode).equals(otherNode)) {

				return rel;
			}
		}

		return null;
	}

}