package schedule;

import java.util.Date;

public class Passenger {
	String name;
	String source;
	String destination;
	Date starttime;
	Date endtime;
	Date maxresponsetime;
	int sourceConfirmedFlag;
	int confirmedFlag;
	int busId = -1;
	
	public int getBusId() {
		return busId;
	}
	public void setBusId(int busId) {
		this.busId = busId;
	}
	public int getConfirmedFlag() {
		return confirmedFlag;
	}
	public void setConfirmedFlag(int confirmedFlag) {
		this.confirmedFlag = confirmedFlag;
	}
	public int getSourceConfirmedFlag() {
		return sourceConfirmedFlag;
	}
	public void setSourceConfirmedFlag(int sourceConfirmedFlag) {
		this.sourceConfirmedFlag = sourceConfirmedFlag;
	}
	public Date getStarttime() {
		return starttime;
	}
	public void setStarttime(Date starttime) {
		this.starttime = starttime;
	}
	public Date getEndtime() {
		return endtime;
	}
	public void setEndtime(Date endtime) {
		this.endtime = endtime;
	}
	public Date getMaxresponsetime() {
		return maxresponsetime;
	}
	public void setMaxresponsetime(Date maxresponsetime) {
		this.maxresponsetime = maxresponsetime;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSource() {
		return source;
	}
	public void setSource(String source) {
		this.source = source;
	}
	public String getDestination() {
		return destination;
	}
	public void setDestination(String destination) {
		this.destination = destination;
	}
	
	
	
}
