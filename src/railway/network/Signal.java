package railway.network;

public class Signal extends Block{

	private boolean clear;               
	private String direction;      
	private int upNeigh;       
	private int downNeigh;
	
	// the constructor	
	public Signal(int id, boolean clear, String direction, int upNeigh, int downNeigh) {
		super(id);
		this.clear=clear;
		this.direction=direction;
		this.upNeigh=upNeigh;
		this.downNeigh=downNeigh;
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
		return direction;
	}

	public void setDirection(String direction) {
		this.direction = direction;
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
