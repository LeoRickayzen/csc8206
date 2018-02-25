package railway.network;

public class Section implements Component{
	
	int id;
	boolean occupied;          
	Section upNeigh;          
	Section downNeigh;

	// the constructor	
	public Section (int blockID, boolean occupied,Section upNeigh, Section downNeigh) {
		this.id=blockID;
		this.occupied=occupied;
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
