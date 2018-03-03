package railway.network;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Signal extends Block{

	private boolean clear;               
	private Direction direction;      
	private int upNeigh;       
	private int downNeigh;
	
	public Signal(){
		
	}
	
	// the constructor
	
	public Signal(@JsonProperty("id") int id, @JsonProperty("clear") boolean clear, @JsonProperty("direction") String direction, @JsonProperty("upNeigh") int upNeigh, @JsonProperty("downNeigh") int downNeigh) {
		super(id);
		this.clear=clear;
		this.direction=Direction.valueOf(direction.toUpperCase());
		this.upNeigh=upNeigh;
		this.downNeigh=downNeigh;
	}

	@JsonIgnore
	public Direction getDirectionEnum() {
		return direction;
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

	public boolean isClear() {
		return clear;
	}

	public void setClear(boolean clear) {
		this.clear = clear;
	}

	
	public String getDirection() {
		return direction.name().toLowerCase();
	}

	
	public void setDirection(String direction) {
		this.direction = Direction.valueOf(direction.toUpperCase());
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
