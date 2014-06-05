package game.actor.pathfind;

import com.badlogic.gdx.math.Vector2;

public class Node {

	private int x;
	private int y;
	private Vector2 position;
	
	private Node parent;		// the node we moved from to get here
	
	private int g;				// cost to get from the start to this node
	private int h;				// estimated cost to get from this node to the goal
	
	
	public Node(int x, int y) {
		this.x = x;
		this.y = y;
		this.position = new Vector2(x,y);
	}
	
	public Node(Vector2 position) {
		this.x = (int) position.x;
		this.y = (int) position.y;
		this.position = position;
	}

	public Node getParent() {
		return parent;
	}
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
	
	public Vector2 getPosition() {
		return position;
	}
	
	public int getG() {
		return g;
	}
	
	public void setG(int g) {
		this.g = g;
	}
	
	public int getH() {
		return h;
	}
	
	public void setH(int h) {
		this.h = h;
	}
	
	public int getF() {
		return g + h;
	}
	
	public void setParent(Node parent) {
		this.parent = parent;
	}
	
	@Override
    public boolean equals(Object object) {

        if (object != null && object instanceof Node) {
            if(this.x == ((Node) object).x && this.y == ((Node) object).y) {
            	return true;
            }
        }
        
        return false;
    }
	
}
