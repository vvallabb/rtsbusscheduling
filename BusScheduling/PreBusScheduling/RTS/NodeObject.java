//package newcode;

public class NodeObject {

	public int xval, yval, // cordinates of object
			type;

	public long identifier; // used to identify the type of the object, i.e place/person/building etc

	public String objectName;

	public int countOfRecords = 0;//To keep track of number of objects inserted

	/**
	 * pointer from each object to the quadtree.
	 */

	/**
	 * pointer from each object to the rtree.
	 */
	NodeObject() {

	}

	/**
	 * Initialises the values for each object.
	 * @param x
	 * @param y
	 * @param entityType
	 * @param id
	 */

	public NodeObject(int x, int y, int entityType, long id) // srinidhi.. what are entity type n id
	{

		xval = x;
		yval = y;
		identifier = id;
		type = entityType;
		objectName = " ";

	}

	public NodeObject(int x, int y, int entityType, long id, String objName) // srinidhi.. what are entity type n id
	{

		xval = x;
		yval = y;
		identifier = id;
		type = entityType;
		objectName = objName;
	}

	/**
	 * Updates the values of the object with the new one.
	 * @param newObj - The object with the updated values.
	 * @throws Exception
	 */
	boolean update(NodeObject newObj) throws Exception {
		xval = newObj.xval;
		yval = newObj.yval;
		return true;
	}

	int getNumberOfRecords() {
		return countOfRecords;
	}

}
