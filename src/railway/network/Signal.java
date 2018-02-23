package railway.network;

public class Signal implements Component{

	int id;
	boolean clear;               
	String direction;      
	Section upNeigh;       
	Section downNeigh;
	
	// the constructor	
	public Signal(int id,boolean clear, String direction, Section upNeigh, Section downNeigh) {
		this.blockID=blockID;
		this.clear=clear;
		this.direction=direction;
		this.upNeigh=upNeigh;
		this.downNeigh=downNeigh;
	}


}
