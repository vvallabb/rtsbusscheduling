//package newcode;

import java.util.ArrayList;

public class PointQuadTree {

	Node root;

	ArrayList deletedNodesChildren = new ArrayList();

	long nodeCount = 0;

	long idCount = 0;

	long traverseCount = 0;

	PointQuadTree pointQuadTree;

	PointQuadTree(int id, int x, int y, int nodeType, String place) {

		root = new Node(id, x, y, nodeType, place);
		System.out.println("ROOT:" +root.place+" "+root.x+" "+root.y);
	}

	public ArrayList getTreeNodes(int nodeType, Node currentNode,
			ArrayList returnNodes) {

		if (currentNode != null) {
			//System.out.println(node.place + " " + node.x + " " + node.y);
			returnNodes.add(currentNode);
			getTreeNodes(nodeType, currentNode.ne, returnNodes);
			getTreeNodes(nodeType, currentNode.nw, returnNodes);
			getTreeNodes(nodeType, currentNode.se, returnNodes);
			getTreeNodes(nodeType, currentNode.sw, returnNodes);
		}

		return returnNodes;
	}

	public Node findNode(Node currentNode, Node keyNode) {

		while (currentNode != null) {

			traverseCount++;
			//System.out.println(currentNode.x + " " + currentNode.y + " " + currentNode.nodeType);
			if (currentNode.place.equals(keyNode.place)) {
				//System.out.println("returning " + currentNode.place);
				return currentNode;
			}

			currentNode = getChildTypeObj(currentNode, keyNode);
		}

		return null;

	}

	public void traverseTree(Node node) {

		/*if (node != null) {
			//System.out.println(traverseCount+" "+node.id + " "+node.nodeType+" "+node.place + " " + node.x + " " + node.y);
			traverseCount++;
			traverseTree(node.ne);
			traverseTree(node.nw);
			traverseTree(node.se);
			traverseTree(node.sw);
		*/
		if(node != null){
			//System.out.println(node.place + " " + node.point.x + " " + node.point.y);
			if(node.ne!=null) {
				//System.out.println(node.place+" "+node.x+" "+node.y+"             NE:"+node.ne.place+" "+node.ne.x+"  "+node.ne.y);
				traverseTree(node.ne);
			}
			if(node.nw!=null) {
				//System.out.println(node.place+" "+node.x+" "+node.y+"             NW:"+node.nw.place+" "+node.nw.x+"  "+node.nw.y);
				traverseTree(node.nw);
			}
			if(node.se!=null) {
				//System.out.println(node.place+" "+node.x+" "+node.y+"             SE:"+node.se.place+" "+node.se.x+"  "+node.se.y);		
				traverseTree(node.se);
			}
			if(node.sw!=null) {
				//System.out.println(node.place+" "+node.x+" "+node.y+"             SW:"+node.sw.place+" "+node.sw.x+"  "+node.sw.y);		
				traverseTree(node.sw);
			}

		}

	}

	private ArrayList findNodesToReinsert(Node node, ArrayList reinsertNodeList) {

		if (node != null) {

			Node currentNode = new Node(node.id, node.x, node.y, node.nodeType,
					node.place);
			//this.deletedNodesChildren.add(currentNode);
			reinsertNodeList.add(currentNode);
			//System.out.println("to be reinserted :" + node.place);
			findNodesToReinsert(node.ne, reinsertNodeList);
			findNodesToReinsert(node.nw, reinsertNodeList);
			findNodesToReinsert(node.se, reinsertNodeList);
			findNodesToReinsert(node.sw, reinsertNodeList);
			node = null;
		}

		return reinsertNodeList;
	}

	public void addChild(Node parent, Node child) {

		if (child == null) {
			return;
		}

		String childType = getChildType(parent, child);
		//System.out.println("adding child");

		if (childType.equals("ne")) {
			if (parent.ne == null) {
				parent.ne = child;
				//System.out.println(parent.place + " " + child.place + " adding ne");
				return;
			} else {
				addChild(parent.ne, child);
			}
		} else if (childType.equals("nw")) {
			if (parent.nw == null) {
				parent.nw = child;
				//System.out.println(parent.place + " " + child.place + " adding nw");
				return;
			} else {
				addChild(parent.nw, child);
			}
		} else if (childType.equals("sw")) {
			if (parent.sw == null) {
				parent.sw = child;
				//System.out.println(parent.place + " " + child.place + " adding sw");
				return;
			} else {
				addChild(parent.sw, child);
			}
		} else {
			if (parent.se == null) {
				parent.se = child;
				//System.out.println(parent.place + " " + child.place + " adding se");
				return;
			} else {
				addChild(parent.se, child);
			}
		}

		nodeCount++;

	}

	private Node getChildTypeObj(Node parent, Node child) {


		if (child.x <= parent.x) {

			if (child.y <= parent.y) {

				return parent.sw;
			} else {
				return parent.nw;
			}
		} else {
			if (child.y > parent.y) {

				return parent.ne;
			} else {
				return parent.se;
			}
		}
	}

	private String getChildType(Node parent, Node child) {


		if (child.x <= parent.x) {

			if (child.y <= parent.y) {

				return "sw";
			} else {
				return "nw";
			}
		} else {
			if (child.y > parent.y) {

				return "ne";
			} else {
				return "se";
			}
		}
	}

	private boolean nonRootDelete(Node node, Node keyNode, boolean returnValue) {

		if (node == null) {
			return returnValue;
		}
		//System.out.println(node.place + " " + keyNode.place + " compared"); 

		//System.out.println("above if");

		if (node.ne != null) {
			//System.out.println(node.ne.place + " " + keyNode.place + " compared");
			if (node.ne.place.equals(keyNode.place)) {
				//System.out.println(node.ne.place + " " + node.ne.place + " found");
				ArrayList reinserNodeList = new ArrayList();
				reinserNodeList = findNodesToReinsert(node.ne, reinserNodeList);
				node.ne = null;
				reInsert(reinserNodeList);
				return true;
			} else {
				//nonRootDelete(node.ne.ne, keyNode);
			}
		}
		if (node.nw != null) {
			//System.out.println(node.nw.place + " " + keyNode.place  + " compared");
			if (node.nw.place.equals(keyNode.place)) {
				//System.out.println(node.nw.place + " " + node.nw.place + " found");
				ArrayList reinserNodeList = new ArrayList();
				reinserNodeList = findNodesToReinsert(node.nw, reinserNodeList);
				node.nw = null;
				reInsert(reinserNodeList);
				return true;
			} else {
				//nonRootDelete(node.nw.nw, keyNode);

			}
		}
		if (node.se != null) {
			//System.out.println(node.se.place+ " " + keyNode.place  + " compared");
			if (node.se.place.equals(keyNode.place)) {
				//System.out.println(node.se.place + " " + node.se.place + " found");
				ArrayList reinserNodeList = new ArrayList();
				reinserNodeList = findNodesToReinsert(node.se, reinserNodeList);
				node.se = null;
				reInsert(reinserNodeList);
				return true;
			} else {
				//nonRootDelete(node.se.se, keyNode);

			}
		}
		if (node.sw != null) {
			//System.out.println(node.sw.place+ " " + keyNode.place  + " compared");
			if (node.sw.place.equals(keyNode.place)) {
				//System.out.println(node.sw.place + " " + node.sw.place + " found");
				ArrayList reinserNodeList = new ArrayList();
				reinserNodeList = findNodesToReinsert(node.sw, reinserNodeList);
				node.sw = null;
				reInsert(reinserNodeList);
				return true;
			} else {
				//nonRootDelete(node.sw.sw, keyNode);
			}
		}

		returnValue = nonRootDelete(node.ne, keyNode, returnValue);
		returnValue = nonRootDelete(node.nw, keyNode, returnValue);
		returnValue = nonRootDelete(node.se, keyNode, returnValue);
		returnValue = nonRootDelete(node.sw, keyNode, returnValue);
		return returnValue;
	}

	private void reInsert(ArrayList reinserNodeList) {

		//System.out.println("size " + reinserNodeList.size());

		for (int i = 1; i < reinserNodeList.size(); i++) {

			Node currentNode = (Node) reinserNodeList.get(i);

			//System.out.println("adding" + currentNode.place );

			this.addChild(root, currentNode);
		}

		return;

	}

	public boolean delete(Node node, Node keyNode) {

		if (node != null) {
			if (node.place.equals(keyNode.place)) {
				ArrayList reinserNodeList = new ArrayList();
				reinserNodeList = findNodesToReinsert(node.ne, reinserNodeList);
				this.root = null;
				return true;
			} else {
				return nonRootDelete(node, keyNode, false);
			}
		}

		return false;
	}

	public Node generateNode() {

		int id = (int) Math.round(Math.random() * 3);

		Node node = new Node(idCount, (int) Math.round(Math.random() * 10000),
				(int) Math.round(Math.random() * 10000), (int) Math.round(Math
						.random() * 4), "New City" + idCount);

		idCount++;

		//System.out.println(node.x + " " + node.y);

		return node;
	}

	public long getSearchStats(Node node) {
		//Node node = new Node(91109,39,82,3,"New City91109");

		traverseCount = 0;

		long startTime = System.nanoTime();
		Node resultNode = findNode(root, node);
		long endTime = System.nanoTime();

		if (resultNode == null) {
			System.out.println("Search unsuccessful");
			System.out.println("Time to search " + (endTime - startTime));
		} else {
			System.out.println("Search sucessfull " + resultNode.x + " "
					+ resultNode.y + " " + resultNode.nodeType);
			System.out.println("Time to search " + (endTime - startTime) / 1000
					+ " micro s");
		}

		System.out.println("nodes traversed " + traverseCount);
		return (endTime - startTime);
	}

	//private long populateTree() {
	public long populateTree() {

		//this.pointQuadTree = new PointQuadTree(0, 50, 50, 1, "Bangalore");	

		long startTime = System.nanoTime();

		ArrayList nodeList = NodeGenerator.readNodes();

		int size = nodeList.size();

		//System.out.println(size);
		for (int i = 0; i < size; i++) {
			//Node node = pointQuadTree.generateNode();
			Node node = (Node) nodeList.get(0);
			nodeList.remove(0);
			//System.out.println("Id:"+node.id +"X:"+node.x+"Y:"+node.y+"nodeType:"+node.nodeType+"place:"+node.place);
			this.addChild(this.root, node);
		}

		long endTime = System.nanoTime();

		return endTime - startTime;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		/*
		 PointQuadTree pqt = new PointQuadTree(0, 5000, 5000, 1, "Bangalore");
		 
		 Node node = new Node(93610,10000,1000,5,"New City93610");
		 
		 long st = System.nanoTime();
		 pqt.getChildType(pqt.root, node);
		 long et = System.nanoTime();
		 System.out.println((et-st)/1000);
		 */


		PointQuadTree pointQuadTree = new PointQuadTree(0, 50, 50, 1,
				"Bangalore");

		int num = 11;
		Node node[] = new Node[11];
		node[0] = new Node(15, 21.837941794288305, 53.24273948152414, 4,
				"New City99700");
		node[1] = new Node(34, 48.99180754644353, 86.30046921644005, 6,
				"New City34900");
		node[2] = new Node(209090, 97.7286595996209, 95.67661145214956, 3,
				"New City5090");
		node[3] = new Node(105789, 19.53587051317074, 66.53797568567292, 9,
				"New City75789");
		node[4] = new Node(63645, 37.19986118242675, 82.36826911762203, 2,
				"New City3645");
		node[5] = new Node(391727, 1.361604179551179, 53.56059046258657, 9,
				"New City1727");
		node[6] = new Node(304952, 8.443119290907951, 73.15388873569346, 7,
				"New City4");
		node[7] = new Node(203298, 12.860788638623466, 92.37844081953219, 7,
				"New City83298");
		node[8] = new Node(399999, 31.944581904062318, 93.88937306972083, 0,
				"New City9999");
		node[9] = new Node(399977, 91.3961738659124, 44.59315159690591, 4,
				"New City44");
		node[10] = new Node(211570, 85.75674608612145, 46.4924991038117, 6,
				"New City11570");

		pointQuadTree.populateTree();
		//pointQuadTree.delete(pointQuadTree.root, new Node());

		pointQuadTree.traverseTree(pointQuadTree.root);



		//PointQuadTree pointQuadTree;
		RangeQuery query;
        query = new RangeQuery(pointQuadTree,60,15,30);
		ArrayList<Node> rangeQueryList = query.search();

		System.out.println("\n\n");
		//System.out.println(pointQuadTree.traverseCount + "+ : tc ");
		long time[] = new long[num];

		//

		long startTime;
		long endTime;
		//for(int j=0;j <5; j++){
/*		for (int i = 0; i < node.length; i++) {

			startTime = System.nanoTime();
			pointQuadTree.delete(pointQuadTree.root, node[i]);
			endTime = System.nanoTime();
			time[i] = endTime - startTime;
			//pointQuadTree = new PointQuadTree(0, 50, 50, 1, "Bangalore");
			//time[i] = pointQuadTree.populateTree();
			//time[i] = pointQuadTree.getSearchStats(node[j]);
			System.out.println(time[i]);

		}
*/
		/*
		 long sum = 0;
		 for (int i = 0; i < node.length; i++) {
		 
		 sum += time[i];

		 }

		 System.out.println("avg time us :" + sum / (num * 1000));
		 */
		//}
		/*
		 System.out.println("Time to insert "+ nodeArray.length +" nodes :" + (endTime - startTime)/1000 + "micro s");
		 
		 startTime = System.nanoTime();
		 pointQuadTree.traverseTree(pointQuadTree.root);
		 endTime = System.nanoTime();
		 System.out.println(pointQuadTree.traverseCount);
		 System.out.println("Time to traverse "+ nodeArray.length + " nodes :" + (endTime - startTime)/1000 + "micro s");
		 */

	}

	public long getTraverseCount() {
		return traverseCount;
	}

}
