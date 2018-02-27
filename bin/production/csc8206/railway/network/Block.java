package railway.network;


public class Block {
	int id;
	String currentDirection;
	
	//constructor
	public Block(int id) {
		this.id=id;
	}
	
	public void setId(int id) {
		  this.id=id;
		  
	  }
	  
	public int getId() {
		  return id;
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

