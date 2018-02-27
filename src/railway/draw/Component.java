package railway.draw;

import javafx.scene.Group;

public abstract class Component extends Group {
	
	private int compid;
	
	public Component(int id){
		this.compid = id;
	}
	
	public int getCompId(){
		return compid;
	}
	
	public void setId(int compid){
		this.compid = compid;
	}
	
    public abstract double getEnd();
}
