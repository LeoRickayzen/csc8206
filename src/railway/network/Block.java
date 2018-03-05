package railway.network;


import com.fasterxml.jackson.annotation.JsonIgnore;

public class Block {
	private int id;
	private Direction currentDirection;
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
	@JsonIgnore
	public void setDirection(Direction direction) {
		this.currentDirection=direction;
	}
	
	/**
     * get block's direction 
     *
     * @return    up or down
     */
	@JsonIgnore
    public String getDirection() {
		return this.currentDirection.name().toLowerCase();
	}

	@JsonIgnore
	public Boolean hasNext(Boolean reverse){
		return true;
	}

	@JsonIgnore
	public int getNext(Boolean reverse){
		return 0;
	}

	@JsonIgnore
	public int getLevel() {
		return level;
	}

	@JsonIgnore
	public void setLevel(int level) {
		this.level = level;
	}

	@JsonIgnore
	public void setIndex(int index){
		this.index = index;
	}

	@JsonIgnore
	public int getIndex(){
		return index;
	}

}

