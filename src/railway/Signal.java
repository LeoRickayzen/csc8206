
public class Signal {
	Section blockID;
	boolean clear;               
	String direction;      
	Section upNeigh;       
	Section downNeigh;
	
	// the constructor	
	public Signal(Section blockID,boolean clear, String direction, Section upNeigh,Section downNeigh) {
		this.blockID=blockID;
		this.clear=clear;
		this.direction=direction;
		this.upNeigh=upNeigh;
		this.downNeigh=downNeigh;
	}


}
