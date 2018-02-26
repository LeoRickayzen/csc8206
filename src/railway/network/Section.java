package railway.network;

public class Section extends Block{
	
	private boolean occupied;          
	private int upNeigh;          
	private int downNeigh;

	// the constructor	
	public Section (int id, boolean occupied, int upNeigh, int downNeigh) {
		super(id);
		this.occupied=occupied;
		this.upNeigh=upNeigh;
		this.downNeigh=downNeigh;
	}

	public int getUpNeigh() {
		return upNeigh;
	}
	
	public void setUpNeigh(int upNeigh) {
		this.upNeigh = upNeigh;
	}
	
	public int getDownNeigh() {
		return downNeigh;
	}
	
	public void setDownNeigh(int downNeigh) {
		this.downNeigh = downNeigh;
	}
	
	public int getId() {
		return id;
	}

	public boolean isOccupied() {
		return occupied;
	}

	public void setOccupied(boolean occupied) {
		this.occupied = occupied;
	}
  
	

}
