import java.util.*;

/*
 * @Mydocuments
 * @Class
 */
public class RangeQuery {
	Point queryPoint;
	int fixedRadius;
	Node rootNode;
	ArrayList<Node> locationList;
	
	static final boolean DEBUG = false;
	RangeQuery(PointQuadTree tree,int x,int y, int radius)   {
		if(radius <= 0){
			throw (new NegativeRadiusException(radius));
		}
		queryPoint = new Point(x,y);
		
		fixedRadius = radius;
		rootNode = tree.root;
		locationList = new ArrayList<Node>();
	}
	
	ArrayList<Node> search() {
		locationList.clear();
		searchRange(rootNode);
		
		if(locationList.size() > 0) {
			for(ListIterator<Node> list=locationList.listIterator();list.hasNext();) {
				Node p = list.next();
				//System.out.println(p.place+" : "+p.x+","+p.y+"CT:"+p.childType+"NT:"+p.nodeType+"Dist:"+getDistance(p.x,p.y,queryPoint));
			}
			
			return locationList;
		}
		return null;
	}
	int getRadius(){
		return fixedRadius;
	}
	void setRadius(int value) {
		if(value <= 0)
			throw (new NegativeRadiusException());
		fixedRadius = value;
	}
	Point getQueryPoint(){
		
		return queryPoint;
	}
	void setQueryPoint(Point p){
		queryPoint = p;
	}
	protected void searchRange(Node root) {
		if(root == null)
			return;
		int direction[] = getDirection(root,queryPoint);
		//System.out.println("came here:root:"+root.place+"Direction Len:"+direction.length);
		//System.out.println("Root x:"+root.x+" y:"+root.y+"Dist:"+getDistance(root.x,root.y,queryPoint));
		if(getDistance(root.x,root.y,queryPoint) <= fixedRadius) {
			//System.out.println("Found node " + root.place + "at " + "(" + root.x + "," + root.y + ")Distance:"+getDistance(root.x,root.y,queryPoint)+"fixedRad:"+fixedRadius);
			locationList.add(root);
		} 
		for(int i=0;i < direction.length;i++) {
			switch(direction[i]) {
				case 1:	{ // North-east direction
					if(root.ne != null) {
						//System.out.println("NE DIRECTION:"+direction[i]);
						searchRange(root.ne);
					} else {
						continue;
					}
				}
				break;
				case 2: { //North-west direction
					if(root.nw != null) {
						//System.out.println("NW DIRECTION:"+direction[i]);
						searchRange(root.nw);
					} else {
						continue;
					}
				}	
				break;
				case 3: {
				//South-west direction
					if( root.sw != null) {
						//System.out.println("SW DIRECTION:"+direction[i]);
						searchRange(root.sw);
					} else {
						continue;
					}
					
					
				}	
				break;
				case 4: {
				//South-east direction
					if(root.se != null) {
						//System.out.println("SE DIRECTION:"+direction[i]);
						searchRange(root.se);
					} else {
						continue;
					}
				}
				break;
				default:
					//System.out.println("Direction does not exist");
				break;
			}
		}
	}
	int getDistance(double a,double c,Point b)
	{
		/* Computes euclidean distance between a and b */
		int distanceBetweenPoints = ((int) Math.sqrt(Math.pow((a - b.x),2)+Math.pow((c-b.y),2)));
		
		//if(DEBUG)
		//	System.out.println("Distance from ("+a.x+","+a.y+") to ("+b.x+","+b.y+") is "+ distanceBetweenPoints);
		return distanceBetweenPoints;
	}
	int[] getDirection(Node root,Point givenPoint) {
		//int directions[] = new int[4];
		int i=0,location=0;
		if(givenPoint.x < root.x) {
            if(givenPoint.y < root.y) {
            	location = 3;
            }else{
            	location = 2;
            }
		}else{
            if(givenPoint.y < root.y) {
            	location = 4;
            }else{
            	location = 1;
            }
		}

		if(Math.abs(givenPoint.x - root.x ) >= fixedRadius) {
			if(givenPoint.x < root.x){
				/* Left of y-axis */
				if(Math.abs(root.y - givenPoint.y) >= fixedRadius) {
					if(givenPoint.y >= root.y) {
						/* NW only */
						int directions[] = {2};
						return directions;
					}else{
						/* SW only */
						int directions[] = {3};
						return directions;
					}
				}else{
					/* Both SW and NW to be added */
					int directions[] = {2,3};
					return directions;
				}
			}else{
				/* Right of y-axis */
				if(Math.abs(root.y - givenPoint.y) >=fixedRadius) {
					if(givenPoint.y >= root.y) {
						/* NE only */
						int directions[] = {1};
						return directions;
					}else{
						/* SE only */
						int directions[] = {4};
						return directions;
					}
				}else{
					/* Both SW and NE to be added */
					int directions[] = {4,1};
					return directions;
				}
				
			}
		}else{
			if(Math.abs(givenPoint.y - root.y ) >= fixedRadius) {
				/* Either NE, NW or SE,SW */
				if(givenPoint.y < root.y){
					/* Below x-axis */
						int directions[] = {3,4};
						return directions;
				}else{
					/* Above x-axis */
						int directions[] = {1,2};
						return directions;
				}
			}else{
				if(getDistance(root.x,root.y,givenPoint) >= fixedRadius){
					int directions[] = new int[3];
					directions[0]=location;
					switch(location){
					case 1: directions[1] = 2;
							directions[2] = 4;
							break;
					case 2: directions[1] = 3;
							directions[2] = 1;
							break;
					case 3: directions[1] = 2;
							directions[2] = 4;
								break;
					case 4: directions[1] = 1;
							directions[2] = 3;
							break;
					default:if(DEBUG)
								System.out.println("Warning:could not resolve location quadrant:"+ location+" for the range query search");
							break;
					}
					return directions;
				}else{
					int directions[] = {1,2,3,4};
					return directions;
				}
			}
		}
		/* Directions spec. for the following code are: 
		  0 - ne
		  1 - nw
		  2 - sw
		  3 - se
		for(int i=0;i<3;i++) {
			if((i == 2-location) || (i == location - 2)) {
				if(location - 2 == -1)
					directions[i] = 1;
				else
					directions[i] = 3;
			}
			else
				directions[i] = i;
		}*/
		/*System.out.println("Location was "+ location);
		for(int i=0;i<3;i++) {
			System.out.println("Direction:"+directions[i]+" i:"+i);
		}*/
	}
}


		
	
		
		
