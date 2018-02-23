package railway.network;

public class Section {
	
	int blockID;             
	boolean occupied;          
	Section upNeigh;          
	Section downNeigh;

	// the constructor	
  public Section (int blockID, boolean occupied,Section upNeigh, Section downNeigh) {
	this.blockID=blockID;
	this.occupied=occupied;
	this.upNeigh=upNeigh;
	this.downNeigh=downNeigh;
}

}
