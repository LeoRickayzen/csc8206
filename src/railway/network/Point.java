package railway.network;

import java.util.UUID;

public class Point extends Block{

	private boolean plus;
	private int mainNeigh;
	private int mNeigh; //minus Neighbour
	private int pNeigh; //plus Neighbour
	private boolean reverse;
	private int topHeight;

	public Point(){}

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
	
	public int getTopHeight() {
		return topHeight;
	}

	public void setTopLevel(int topHeight) {
		this.topHeight = topHeight;
	}
	
	public Boolean hasNext(Boolean reverse){
		return pNeigh != 0;
	}
	
	public int getNext(Boolean reverse){
		return mNeigh;
	}
}