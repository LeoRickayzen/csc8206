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


}
