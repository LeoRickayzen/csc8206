package railway.network;

import java.util.UUID;

public class Point extends Block{

<<<<<<< HEAD
	boolean plus;                
	int mainNeigh;     
	int mNeigh; //minus Neighbour         
	int pNeigh; //plus Neighbour
	
=======
	private boolean plus;
	private int mainNeigh;
	private int mNeigh; //minus Neighbour
	private int pNeigh; //plus Neighbour
	private boolean reverse;

	public Point(){}

>>>>>>> 6323fa204a80710a924b4713cc3f0b3c90d7f4c4
	// the constructor	
	public Point(int id, boolean plus, int mainNeigh, int mNeigh, int pNeigh, boolean reverse) {
		super(id);
		this.plus=plus;
		this.mainNeigh=mainNeigh;
		this.mNeigh=mNeigh;
		this.pNeigh=pNeigh;
		this.reverse=reverse;		
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

	public boolean isReverse() {
		return reverse;
	}

	public void setReverse(boolean reverse) {
		this.reverse = reverse;
	}

	


}
