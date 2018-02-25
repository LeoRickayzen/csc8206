package railway.network;

import java.util.UUID;

public class Point implements Component{

	private UUID id;
	boolean plus;                
	UUID mainNeigh;     
	UUID mNeigh; //minus Neighbour         
	UUID pNeigh; //plus Neighbour
	
	// the constructor	
	public Point(boolean plus, UUID mainNeigh, UUID mNeigh, UUID pNeigh) {
		this.id = UUID.randomUUID();
		this.plus=plus;
		this.mainNeigh=mainNeigh;
		this.mNeigh=mNeigh;
		this.pNeigh=pNeigh;
		
	}

	public UUID getId() {
		return id;
	}

	public UUID getMainNeigh() {
		return mainNeigh;
	}

	public void setMainNeigh(UUID mainNeigh) {
		this.mainNeigh = mainNeigh;
	}

	public UUID getmNeigh() {
		return mNeigh;
	}

	public void setmNeigh(UUID mNeigh) {
		this.mNeigh = mNeigh;
	}

	public UUID getpNeigh() {
		return pNeigh;
	}

	public void setpNeigh(UUID pNeigh) {
		this.pNeigh = pNeigh;
	}


}
