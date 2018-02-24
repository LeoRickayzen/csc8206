
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
	/**
     * set signal's block
     *
     * @param  blockID  new signal's block
     */
	//the section that the signal on
	public void setBlockID(Section blockID) {
		this.blockID=blockID;
		
	}
	//the section that the signal on
	public Section getBlockID() {
		return blockID;
	}
	
	/**
     * set signal's status
     *
     * @param  clear  new clear value 
     */
	public void setClear(boolean clear) {
		this.clear=clear;
	}
	
	/**
     * get signal's status 
     *
     * @return    true if it is clear
     */
	public boolean getClear() {
		return clear;
	}
	
	/**
     * set signal's direction
     *
     * @param  direction  new direction value (up or down)
     */
	public void setDirection(String direction) {
		this.direction=direction;
	}
	
	/**
     * get signal's direction 
     *
     * @return    up or down
     */
	public String getDirection() {
		return direction;
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
		return this.downNeigh;
	}


}
