package railway.network;

import java.util.UUID;

public class Point extends Block{

	private boolean plus;                
	private int mainNeigh;     
	private int mNeigh; //minus Neighbour         
	private int pNeigh; //plus Neighbour
	
	// the constructor	
	public Point(int blockID, boolean plus, int mainNeigh, int mNeigh, int pNeigh) {
		super(blockID);
		this.plus=plus;
		this.mainNeigh=mainNeigh;
		this.mNeigh=mNeigh;
		this.pNeigh=pNeigh;
		
	}

	public boolean isPlus() {
		return plus;
	}

	public void setPlus(boolean plus) {
		this.plus = plus;
	}

	public int getMainNeigh() {
		return mainNeigh;
	}

	public void setMainNeigh(int mainNeigh) {
		this.mainNeigh = mainNeigh;
	}

	public int getmNeigh() {
		return mNeigh;
	}

	public void setmNeigh(int mNeigh) {
		this.mNeigh = mNeigh;
	}

	public int getpNeigh() {
		return pNeigh;
	}

	public void setpNeigh(int pNeigh) {
		this.pNeigh = pNeigh;
	}

	


}
