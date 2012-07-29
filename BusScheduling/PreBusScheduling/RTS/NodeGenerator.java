//package newcode;

import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class NodeGenerator {

	long idCount;

	public static ArrayList readNodes() {

		ArrayList nodeList = new ArrayList();
		FileInputStream fileStream;

		DataInputStream in;

		int i = 0;
		try {
			fileStream = new FileInputStream("nodes.txt");

			in = new DataInputStream(fileStream);

			while (in.available() != 0) {

				String inputLine = in.readLine();

				String[] field = inputLine.split(",");

				Node node = new Node(Integer.parseInt(field[0]), Double
						.parseDouble(field[1]), Double.parseDouble(field[2]),
						Integer.parseInt(field[3]), field[4]);

				nodeList.add(node);
				i++;
			}

			in.close();

		} catch (IOException e) {

			System.out.println(e);
		}

		return nodeList;
	}

	public Node generateNode() {

		double x = Math.random() * 100;

		double y = Math.random() * 100;

		//System.out.println(x + " " + y);

		Node node = new Node(idCount, x, y, (int) Math
				.round(Math.random() * 10), "New City" + idCount);

		idCount++;

		return node;
	}

	public void writeNodes(int num) {

		try {

			BufferedWriter out = new BufferedWriter(new FileWriter("nodes.txt"));

			String writeString = null;

			for (int i = 0; i < num; i++) {
				Node node = generateNode();
				writeString = node.id + "," + node.x + "," + node.y + ","
						+ node.nodeType + "," + node.place;

				out.write(writeString);
				if (i < num - 1) {
					out.newLine();
				}
			}
			out.close();
			//System.out.println("GRAPHVIZ file written: "+ edge.getStartNode().getName() + ".dot");
		} catch (IOException e) {
			System.out.println(e);
		}

	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		NodeGenerator nodeGenerator = new NodeGenerator();
		nodeGenerator.writeNodes(100000);
	}
}
