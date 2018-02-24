
public class Point {
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
	
	public void setPlus(boolean plus) {
		this.plus=plus;
	}
	
	public boolean getPlus() {
		return plus;
	}

	public void setMainNeigh(Section mainNeigh) {
		this.mainNeigh=mainNeigh;
		
	}

	public Section getmainNeigh() {
		return mainNeigh;
	}
	
	public void setMNeigh(Section mNeigh) {
		this.mNeigh=mNeigh;
	}
	
	public Section getPMeigh() {
		return mNeigh;
		
	}
	
	public void setPNeigh(Section pNeigh) {
		this.pNeigh=pNeigh;
		
	}
	
	public Section getPNeigh() {
		return pNeigh;
	}
	
	
}
