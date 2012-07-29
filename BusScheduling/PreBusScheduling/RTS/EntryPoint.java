//package newcode;




import java.util.ArrayList;

public class EntryPoint {

	long idCount = 0;

	PointQuadTree[] tree = new PointQuadTree[11];

	public void addChild(Node node) {
		PointQuadTree pointQuadTree = tree[node.nodeType];
		pointQuadTree.addChild(pointQuadTree.root, node);
	}

	public Node findNode(Node node) {
		PointQuadTree pointQuadTree = tree[node.nodeType];
		Node foundNode = pointQuadTree.findNode(pointQuadTree.root, node);
		return foundNode;
	}

	public void DeleteNode(Node node) {
		PointQuadTree pointQuadTree = tree[node.nodeType];
		pointQuadTree.delete(pointQuadTree.root, node);
	}

	public Node generateNode() {

		int id = (int) Math.round(Math.random() * 3);

		Node node = new Node(idCount, (int) Math.round(Math.random() * 10000),
				(int) Math.round(Math.random() * 10000), (int) Math.round(Math
						.random() * 10), "New City" + idCount);

		idCount++;

		System.out.println(node.x + " " + node.y);

		return node;
	}

	public ArrayList getTreeNodes(int nodeType, ArrayList returnNodes) {

		return tree[nodeType].getTreeNodes(nodeType, tree[nodeType].root,
				returnNodes);

	}

	public void createTree() {

		tree[0] = new PointQuadTree(0, 50, 50, 1, "Bangalore0");
		tree[1] = new PointQuadTree(0, 50, 50, 2, "Bangalore1");
		tree[2] = new PointQuadTree(0, 50, 50, 3, "Bangalore2");
		tree[3] = new PointQuadTree(0, 50, 50, 4, "Bangalore3");
		tree[4] = new PointQuadTree(0, 50, 50, 5, "Bangalore4");
		tree[5] = new PointQuadTree(0, 50, 50, 6, "Bangalore5");
		tree[6] = new PointQuadTree(0, 50, 50, 7, "Bangalore6");
		tree[7] = new PointQuadTree(0, 50, 50, 8, "Bangalore7");
		tree[8] = new PointQuadTree(0, 50, 50, 9, "Bangalore8");
		tree[9] = new PointQuadTree(0, 50, 50, 10, "Bangalore9");
		tree[10] = new PointQuadTree(0, 50, 50, 11, "Bangalore10");

	}

	public long getSearchStats(Node searchNode) {

		tree[searchNode.nodeType].traverseCount = 0;

		long startTime = System.nanoTime();
		Node resultNode = tree[searchNode.nodeType].findNode(
				tree[searchNode.nodeType].root, searchNode);
		long endTime = System.nanoTime();

		if (resultNode == null) {
			System.out.println("Search unsuccessful");
			// System.out.println("Time to search " + (endTime - startTime));
		} else {
			System.out.println("Search sucessfull" + resultNode.place);
			System.out.println("Time to search " + (endTime - startTime) / 1000
					+ " micro s");
		}
		// System.out.println("nodes traversed "
		// + tree[searchNode.nodeType].traverseCount);
		return (endTime - startTime);

	}

	public long populateTree() {

		long startTime = System.nanoTime();

		ArrayList nodeList = NodeGenerator.readNodes();

		if (nodeList == null) {
			System.out.println("Error reading input file");
		}
		
		System.out.println(nodeList.size());

		int size = nodeList.size();

		PointQuadTree pointQuadTree = null;

		for (int i = 0; i < size; i++) {
			// Node node = pointQuadTree.generateNode();
			Node newNode = (Node) nodeList.get(0);
			nodeList.remove(0);
			// System.out.println(i + " " + newNode.nodeType);
			pointQuadTree = tree[newNode.nodeType];
			// System.out.println(node.nodeType);
			pointQuadTree.addChild(pointQuadTree.root, newNode);
		}

		long endTime = System.nanoTime();

		return endTime - startTime;

	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

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

		long time[] = new long[num];

		EntryPoint entryPoint = new EntryPoint();

		entryPoint.createTree();

		entryPoint.populateTree();

		// for (int j = 0; j < 11; j++) {
		long startTime;
		long endTime;

		boolean found = false;

		for (int j = 0; j < 11; j++) {
			long sum = 0;
			for (int i = 0; i < 11; i++) {
				// entryPoint.createTree();
				startTime = System.nanoTime();

				found = entryPoint.tree[i].delete(entryPoint.tree[i].root,
						node[j]);
				endTime = System.nanoTime();
				sum += endTime - startTime;
				// System.out.println(sum + " " + found);
				if (found) {
					System.out.println(sum);
					break;
				}
				// [i] = entryPoint.populateTree();

				// time[i] = entryPoint.getSearchStats(node[j]);
			}
		}

		/*
		 * long sum = 0; for (int i = 0; i < num; i++) { //if (time[i] >
		 * 1000000) { // sum += time[i - 1]; //} else { sum += time[i]; //} }
		 * 
		 * System.out.println("avg time us :" + sum / (num * 1000));
		 */
		// }
	}

}
