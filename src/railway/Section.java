
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
  
  public void setBlockID(int blockID) {
	  this.blockID=blockID;
	  
  }
  
  public int getBlockID() {
	  return blockID;
  }

  public void setOccupied(boolean occupied) {
	  this.occupied=occupied;
  }
  
  public boolean getOccupied() {
	  return occupied;
  }
  
  public void setUpNeigh(Section upNeigh) {
	  this.upNeigh=upNeigh;
  }
  
  public Section getUpNeigh() {
	  return upNeigh;
  }
  
  public void setDownNeigh(Section downNeigh) {
	  this.downNeigh=downNeigh;
  }
  
  public Section getDownNeigh() {
	  return downNeigh;
  }
}
