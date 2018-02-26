
public class Block {
	int blockID;
	String currentDirection;
	
	//constructor
	public Block(int blockID) {
		this.blockID=blockID;
	}
	
	public void setBlockID(int blockID) {
		  this.blockID=blockID;
		  
	  }
	  
	public int getBlockID() {
		  return blockID;
	  }
	
	/**
     * set block's current direction
     *
     * @param  direction  new direction value
     */
	public void setDirection(String direction) {
		this.currentDirection=direction;
	}
	
	/**
     * get block's direction 
     *
     * @return    up or down
     */
	public String getDirection() {
		return this.currentDirection;
	}

}
