
public class Point extends Block{
	boolean plus;                
	Block mainNeigh;     
	Block mNeigh; //minus Neighbour         
	Block pNeigh; //plus Neighbour
	
	// the constructor	
	public Point(int blockID,boolean plus,Block mainNeigh, Block mNeigh, Block pNeigh) {
		super(blockID);
		this.plus=plus;
		this.mainNeigh=mainNeigh;
		this.mNeigh=mNeigh;
		this.pNeigh=pNeigh;
		
	}
	
	public void setPlus(boolean plus) {
		this.plus=plus;
	}
	
	public boolean getPlus(){
		return plus;
	}
	//set main neighbour
	public void setMainNeigh(Section mainNeigh) {
		this.mainNeigh=mainNeigh;
		
	}
	//get main neighbour
	public Block getmainNeigh() {
		return mainNeigh;
	}
	//set minus neighbour
	public void setMNeigh(Section mNeigh) {
		this.mNeigh=mNeigh;
	}
	//get minus neighbour
	public Block getMNeigh() {
		return mNeigh;
		
	}
	//set plus neighbour
	public void setPNeigh(Section pNeigh) {
		this.pNeigh=pNeigh;
		
	}
	//get plus neighbour
	public Block getPNeigh() {
		return pNeigh;
	}
	
	
}
