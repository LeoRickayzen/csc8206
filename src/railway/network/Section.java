package railway.network;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * <p>Stores information about a Section.</p>
 * 
 */
public class Section extends Block{
	
	private boolean occupied;
	private int upNeigh;
	private int downNeigh;

	public Section(){}
	
	// the constructor	
	/**
	 * 
	 * @param id int ID of the Section
	 * @param occupied FUTURE USE. If the Section is occupied by a train
	 * @param upNeigh int ID of the up neighbour of this Section
	 * @param downNeigh int ID of the down neighbour of this Section
	 */
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

	@JsonIgnore
	public boolean isOccupied() {
		return occupied;
	}

	@JsonIgnore
	public void setOccupied(boolean occupied) {
		this.occupied = occupied;
	}
  
	public Boolean hasNext(Boolean reverse){
		if(reverse){
			return downNeigh != 0;
		}else{
			return upNeigh != 0;
		}
	}
	
	public int getNext(Boolean reverse){
		if(reverse){
			return downNeigh;
		}else{
			return upNeigh;
		}
	}

}