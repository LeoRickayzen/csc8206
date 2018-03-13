package railway.network;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * <p>Stores information about a Signal.</p>
 * 
 * @author Jay Kahlil Hussaini
 * @author Ohud Almutairi
 */
public class Signal extends Block{

	private boolean clear;               
	private Direction direction;      
	private int upNeigh;       
	private int downNeigh;
	
	public Signal(){}
	
	// the constructor
	/**
	 * @param id int ID for this Signal
	 * @param clear true by default. true = CLEAR, false = STOP
	 * @param direction String representation of a {@link Direction}
	 * @param upNeigh int ID of the up neighbour {@link Block}
	 * @param downNeigh in ID of the down neighbour {@link Block}
	 */
	public Signal(@JsonProperty("id") int id, @JsonProperty("clear") Boolean clear, @JsonProperty("direction") String direction, @JsonProperty("upNeigh") int upNeigh, @JsonProperty("downNeigh") int downNeigh) {
		super(id);
		this.clear = clear == null ? true : clear; //Ignore IDE warnings, clear is used!
        this.direction = Direction.valueOf(direction.toUpperCase());
		this.upNeigh=upNeigh;
		this.downNeigh=downNeigh;
	}

	/**
	 * <p>Getter for direction to be used for everything but JSON.</p>
	 * 
	 * @return Enum of Direction
	 */
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

	@JsonIgnore
	public boolean isClear() {
		return clear;
	}

	@JsonIgnore
	public void setClear(boolean clear) {
		this.clear = clear;
	}

	/**
	 * <p>ONLY FOR JSON USE. Please use {@link Signal.getDirectionEnum()}</p>
	 * 
	 * <p>Getter for direction for JSON.</p>
	 */
	public String getDirection() {
		return direction.name().toLowerCase();
	}

	/**
	 * <p>Setter for direction for JSON.</p>
	 * 
	 * @param direction String of direction ("up"/"down") to be set as enum.
	 */
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
