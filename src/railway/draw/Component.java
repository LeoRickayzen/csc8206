package railway.draw;

import javafx.scene.Group;

public abstract class Component extends Group {
	
	private int compid;
	private int level;
	
	public Component(int id){
		this.compid = id;
	}
	
	public int getLevel(){
		return level;
	}
	
	public void setLevel(int level){
		this.level = level;
	}
	
	public int getCompId(){
		return compid;
	}
	
	public void setId(int compid){
		this.compid = compid;
	}
	
    public abstract double getEnd();
}
