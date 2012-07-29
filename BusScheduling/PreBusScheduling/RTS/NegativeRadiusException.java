
public class NegativeRadiusException extends RuntimeException{
	
	NegativeRadiusException(){
		super("Negative Radius entered for range Query");
		System.out.println("Negative Radius entered for range Query");
		
	}
	NegativeRadiusException(int radius){
		super("Negative Radius entered for range Query");
		
		System.out.println("Negative Radius entered for range Query:Radius:"+radius);
	}
}