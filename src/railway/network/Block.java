package railway.network;


public class Block {
<<<<<<< HEAD
	int id;
	String currentDirection;
	
=======
	private int id;
	private String currentDirection;

	Block(){}

>>>>>>> 6323fa204a80710a924b4713cc3f0b3c90d7f4c4
	//constructor
	Block(int id) {
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

