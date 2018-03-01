package railway.network;


import com.fasterxml.jackson.annotation.JsonIgnore;

public class Block {
	private int id;
    private String currentDirection;

	Block(){}

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
	@JsonIgnore
    public String getDirection() {
		return this.currentDirection;
	}
}

