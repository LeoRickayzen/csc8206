package railway.network;

public class Signal implements Component{

	int id;
	boolean clear;               
	String direction;      
	Section upNeigh;       
	Section downNeigh;
	
	// the constructor	
	public Signal(int id, boolean clear, String direction, Section upNeigh, Section downNeigh) {
		this.id=id;
		this.clear=clear;
		this.direction=direction;
		this.upNeigh=upNeigh;
		this.downNeigh=downNeigh;
	}

	public Section getUpNeigh() {
		return upNeigh;
	}

	public void setUpNeigh(Section upNeigh) {
		this.upNeigh = upNeigh;
	}

	public Section getDownNeigh() {
		return downNeigh;
	}

	public void setDownNeigh(Section downNeigh) {
		this.downNeigh = downNeigh;
	}

	public int getId() {
		return id;
	}
	
	


}
