package game.actor.pathfind;

import game.actor.pathfind.Node;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Stack;

import tools.Globals;

import com.badlogic.gdx.math.Vector2;

public class AStar {
	private ArrayList<Node> openNodes;
	private ArrayList<Node> closedNodes;
	
	private Node start;
	private Node goal;
	
	// unimplemented
	//private int shadowCost;		// the cost to move into a tile in shadow
	//private int lightCost;		// the cost to move into a tile in light
	
	
	public AStar() {
		
	}
	
	/**
	 * return a path from the startPos to the goalPos if one exists, use lastStep to exclude the goal from the path (useful when pathing to an actor or object) 
	 * @param startPos
	 * @param goalPos
	 * @param lastStep
	 * @return
	 */
	public Stack<Vector2> path(Vector2 startPos, Vector2 goalPos, boolean lastStep) {
		openNodes = new ArrayList<Node>();
		closedNodes = new ArrayList<Node>();
		
		start = new Node(startPos);
		goal = new Node(goalPos);
		System.out.println("building path from x:" + startPos.x + ", y:" + startPos.y + " to x:" + goalPos.x + ", y:" + goalPos.y);
		
		// add the starting node to the open node list
		openNodes.add(start);
		
		while(openNodes.size() != 0) {
			
			// get the node with the lowest f value
			Node currentNode = getCheapestNode();
			
			if(currentNode.equals(goal)) {
				// we've found the path
				System.out.println("path has been found");
				return buildPath(currentNode, lastStep);
			} else {
				// move current node to the closed list and get its neighbours
				moveToClosed(currentNode);
				ArrayList<Node> neighbours = getNeighbours(currentNode);
				
				// check each neighbour
				for (Iterator<Node> iterator = neighbours.iterator(); iterator.hasNext();) {
					Node node = (Node) iterator.next();
					
					if(closedNodes.contains(node) && currentNode.getG() < node.getG()) {
						// the node is already in the closed nodes list but we've found a shorter path to it
						node.setG(currentNode.getG() + getMovementCost(node, currentNode));
						node.setParent(currentNode);
					} else if (openNodes.contains(node) && currentNode.getG() < node.getG()) {
						// the node is in the open nodes list but we've found a short path to it
						node.setG(currentNode.getG() + getMovementCost(node, currentNode));
						node.setParent(currentNode);
					} else if (!openNodes.contains(node) && !closedNodes.contains(node)) {
						// add the node to the open list (we've already worked the g and parent out in processNeighbour)
						openNodes.add(node);
					}
				}
			}
		}
		
		// no path could be found
		System.out.println("path has NOT been found");
		return null;
	}
	
	/**
	 * return the cost of moving from one node to the other
	 * @param node
	 * @param currentNode
	 * @return
	 */
	private int getMovementCost(Node node, Node currentNode) {
		return 1;
	}

	/**
	 * add all of the nodes neighbours to the open list providing they aren't blocked
	 * @param currentNode
	 */
	private ArrayList<Node> getNeighbours(Node currentNode) {
		// not allowing diagonal movement
		ArrayList<Node> neighbours = new ArrayList<Node>();
		
		// check each node
		
		Node northNode = new Node(currentNode.getX() + 1, currentNode.getY());
		if(processNeighbour(northNode, currentNode)) {
			neighbours.add(northNode);
		}
		
		Node eastNode = new Node(currentNode.getX(), currentNode.getY() + 1);
		if(processNeighbour(eastNode, currentNode)) {
			neighbours.add(eastNode);
		}
		
		Node southNode = new Node(currentNode.getX() - 1, currentNode.getY());
		if(processNeighbour(southNode, currentNode)) {
			neighbours.add(southNode);
		}
		
		Node westNode = new Node(currentNode.getX(), currentNode.getY() - 1);
		if(processNeighbour(westNode, currentNode)) {
			neighbours.add(westNode);
		}
		
		return neighbours;
	}
	
	/**
	 * work out and set the parent, g and h value of the node.
	 * @param node
	 * @return whether the node be added to the neighbour list
	 */
	private boolean processNeighbour(Node node, Node parent) {
		if(closedNodes.contains(node) || openNodes.contains(node)) {
			// the node already exists so do nothing with it
			return false;
		} else {
			// newly discovered node
			if(isBlocked(node)) {
				node.setParent(parent);
				node.setG(parent.getG() + getMovementCost(node, parent));
				node.setH(this.calculateH(node, goal));
				return true;
			} else {
				return false;
			}
		}
	}

	/**
	 * remove the node from the openNodes list and add it to the closedNodes list
	 * @param currentNode
	 */
	private void moveToClosed(Node currentNode) {
		openNodes.remove(currentNode);
		closedNodes.add(currentNode);
	}

	/**
	 * working backwards from the given node, build a stack of the tile locations back to the start
	 * if lastStep is set to false the goal will be excluded from the path (useful for pathing to an actor)
	 * @return the path
	 */
	private Stack<Vector2> buildPath(Node node, boolean lastStep) {
		Stack<Vector2> path = new Stack<Vector2>();
		Node current = node;
		Node previous = null;
		
		while (!current.equals(start)) {
			System.out.println("current  x:" + current.getX() + ", y:" + current.getY() + " does not equal goal,  x:" + goal.getX() + ", y:" + goal.getY());
			
			if(previous == null && lastStep) {
				path.add(current.getPosition());
			}
			
			previous = current;
			current = current.getParent();
			path.add(current.getPosition());
		}
		
		return path;
	}

	/**
	 * return the node in the openNodes list with the lowest f value
	 * @return
	 */
	private Node getCheapestNode() {
		Node cheapestNode = null;
		
		for (Iterator<Node> iterator = openNodes.iterator(); iterator.hasNext();) {
			Node node = (Node) iterator.next();
			if(cheapestNode == null || node.getF() < cheapestNode.getF()) {
				cheapestNode = node;
			}
		}
		
		return cheapestNode;
	}

	/**
	 * calculate the heuristic for the given node
	 * @param current
	 * @param goal
	 * @return
	 */
	private int calculateH(Node current, Node goal) {
		// Manhattan method
		return Math.abs(current.getX() - goal.getX()) + Math.abs(current.getY() - goal.getY());
	}
	
	/**
	 * check if the location given is blocked
	 * @return
	 */
	private boolean isBlocked(Node loc) {
		if(!Globals.map.isTileBlocked(loc.getX(), loc.getY()) && Globals.actors.isOccupied(loc.getX(), loc.getY())) {
			return false;
		} else {
			return true;
		}
	}
	
}
