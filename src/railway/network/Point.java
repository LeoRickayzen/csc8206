package railway.network;

public class Point extends Block{

	boolean plus;                
	int mainNeigh;     
	int mNeigh; //minus Neighbour         
	int pNeigh; //plus Neighbour

    public Point(){}

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
