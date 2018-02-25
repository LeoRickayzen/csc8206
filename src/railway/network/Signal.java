package railway.network;

import java.util.UUID;

public class Signal implements Component{

	private UUID id;
	boolean clear;               
	String direction;      
	UUID upNeigh;       
	UUID downNeigh;
	
	// the constructor	
	public Signal(boolean clear, String direction, UUID upNeigh, UUID downNeigh) {
		this.id = UUID.randomUUID();
		this.clear=clear;
		this.direction=direction;
		this.upNeigh=upNeigh;
		this.downNeigh=downNeigh;
	}

	public UUID getUpNeigh() {
		return upNeigh;
	}

	public void setUpNeigh(UUID upNeigh) {
		this.upNeigh = upNeigh;
	}

	public UUID getDownNeigh() {
		return downNeigh;
	}

	public void setDownNeigh(UUID downNeigh) {
		this.downNeigh = downNeigh;
	}

	public UUID getId() {
		return id;
	}
	
	


}
