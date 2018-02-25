
public class Section extends Block{
	            
	boolean occupied;          
	Block upNeigh;          
	Block downNeigh;

	// the constructor	
  public Section (int blockID, boolean occupied,Block upNeigh, Block downNeigh) {
	super(blockID);
	this.occupied=occupied;
	this.upNeigh=upNeigh;
	this.downNeigh=downNeigh;
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
  
  public Block getUpNeigh() {
	  return upNeigh;
  }
  
  public void setDownNeigh(Section downNeigh) {
	  this.downNeigh=downNeigh;
  }
  
  public Block getDownNeigh() {
	  return downNeigh;
  }
}
