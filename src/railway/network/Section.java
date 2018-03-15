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

    /**
     * Get the id of the up neighbour of the section
     *
     * @return id of up neighbour
     */
	public int getUpNeigh() {
		return upNeigh;
	}

    /**
     * Sets the up neigh id
     *
     * @param upNeigh the id to set the upneigh to
     */
	public void setUpNeigh(int upNeigh) {
		this.upNeigh = upNeigh;
	}

    /**
     * Get the id of the down neighbour of the section
     *
     * @return id of down neighbour
     */
	public int getDownNeigh() {
		return downNeigh;
	}

    /**
     * Sets the down neigh id
     *
     * @param downNeigh the id to set the downNeigh to
     */
	public void setDownNeigh(int downNeigh) {
		this.downNeigh = downNeigh;
	}

    /**
     * Unused but will return whether section is occupied by train.
     *
     * @return boolean of whether section has a train on it
     */
	@JsonIgnore
	public boolean isOccupied() {
		return occupied;
	}

    /**
     * Unused but can set if a train is on the section
     *
     * @param occupied boolean of whether to set to occupied or not
     */
	@JsonIgnore
	public void setOccupied(boolean occupied) {
		this.occupied = occupied;
	}

    /**
     * Get the id of the up neighbour of the section
     *
     * @return boolean of whether the section has a next neighbour
     */
	public Boolean hasNext(Boolean reverse){
		if(reverse){
			return downNeigh != 0;
		}else{
			return upNeigh != 0;
		}
	}

    /**
     * Get the id of the next neighbour of the section
     *
     * @return id of next neighbour
     */
	public int getNext(Boolean reverse){
		if(reverse){
			return downNeigh;
		}else{
			return upNeigh;
		}
	}

}