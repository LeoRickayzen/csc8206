package railway.network;


public class Block {
	private int id;
	private String currentDirection;
	private int level;
	private int index;
	
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
	public String getDirection() {
		return this.currentDirection;
	}
	
	public Boolean hasNext(Boolean reverse){
		return true;
	};
	
	public int getNext(Boolean reverse){
		return 0;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}
	
	public void setIndex(int index){
		this.index = index;
	}
	
	public int getIndex(){
		return index;
	}

}

