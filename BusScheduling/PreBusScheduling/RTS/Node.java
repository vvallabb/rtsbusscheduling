//package newcode;


public class Node {

	public long id;

	public Node nw;

	public Node ne;

	public Node sw;

	public Node se;

	public double x;

	public double y;

	public String place;

	public String childType;

	public int nodeType;

	public Node() {

	}

	public Node(long id, double x, double y, int nodeType, String place) {

		this.id = id;

		this.x = x;

		this.y = y;

		this.nodeType = nodeType;

		this.place = place;

	}

}
