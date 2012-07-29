package schedule;

import java.util.ArrayList;

public class Place {

	String name;
	int no_of_pax_in;
	ArrayList passengers;
	int no_of_pax_out;
	int status;
	
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getNo_of_pax_in() {
		return no_of_pax_in;
	}
	public void setNo_of_pax_in(int noOfPaxIn) {
		no_of_pax_in = noOfPaxIn;
	}
	public int getNo_of_pax_out() {
		return no_of_pax_out;
	}
	public void setNo_of_pax_out(int noOfPaxOut) {
		no_of_pax_out = noOfPaxOut;
	}
	public ArrayList getPassengers() {
		return passengers;
	}
	public void setPassengers(ArrayList passengers) {
		this.passengers = passengers;
	}
}
