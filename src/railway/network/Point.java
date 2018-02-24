package railway.network;

public class Point implements Component{

	int id;
	boolean plus;                
	Section mainNeigh;     
	Section mNeigh; //minus Neighbour         
	Section pNeigh; //plus Neighbour
	
	// the constructor	
	public Point(boolean plus,Section mainNeigh, Section mNeigh, Section pNeigh) {
		this.plus=plus;
		this.mainNeigh=mainNeigh;
		this.mNeigh=mNeigh;
		this.pNeigh=pNeigh;
		
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Section getMainNeigh() {
		return mainNeigh;
	}

	public void setMainNeigh(Section mainNeigh) {
		this.mainNeigh = mainNeigh;
	}

	public Section getmNeigh() {
		return mNeigh;
	}

	public void setmNeigh(Section mNeigh) {
		this.mNeigh = mNeigh;
	}

	public Section getpNeigh() {
		return pNeigh;
	}

	public void setpNeigh(Section pNeigh) {
		this.pNeigh = pNeigh;
	}


}
