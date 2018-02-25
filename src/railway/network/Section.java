package railway.network;

import java.util.UUID;

public class Section implements Component{
	
	private UUID id;
	boolean occupied;          
	UUID upNeigh;          
	UUID downNeigh;

	// the constructor	
	public Section (boolean occupied, UUID upNeigh, UUID downNeigh) {
		this.id = UUID.randomUUID();
		this.occupied=occupied;
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
