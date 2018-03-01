package railway.draw;

import javafx.scene.Group;
import railway.network.Block;
import railway.network.Network;
import railway.network.Point;
import railway.network.Section;
import railway.network.Signal;

import java.util.ArrayList;

public class NetworkRenderer {

    ArrayList<railway.draw.Component> components;
    Network network;
	ArrayList<Point> points = new ArrayList<Point>();
    
    int level = -1;
    
    public NetworkRenderer(Network network){
    	this.network = network;
    }
    
    public Component getCompById(int id){
    	for(Component comp : components){
    		if(comp.getCompId() == id){
    			return comp;
    		}
    	}
    	return null;
    }
    
    public void leveler(int y, Point point, Boolean backwards, int index, Network network){
    	level = level+1;
    	if(point != null){
    		point.setTopLevel(level);
    	}
    	Block block = network.getBlock(y);
    	setLevels(block, backwards, index, level);
    	System.out.println(points.size());
    	if(points.size() > 0){
        	Point nextPoint = points.get(points.size()-1);
        	points.remove(points.size()-1);
            leveler(nextPoint.getmNeigh(), nextPoint, nextPoint.isReverse(), nextPoint.getIndex()+1, network);
    	}	
    }
    
    public void setLevels(Block block, Boolean backwards, int index, int level){
    	block.setLevel(level);
    	block.setIndex(index);
    	if(backwards){
    		index = index - 1;
		}else{
			index = index + 1;
		}
    	if(isPoint(block)){
    		points.add((Point)block);
    	}
    	if(block.hasNext(backwards)){
        	Block nextBlock = network.getBlock(block.getNext(backwards));
        	setLevels(nextBlock, backwards, index, level);
    	}
    }
    
    public NetworkComp draw(){
        leveler(network.getFirst().getId(), null, false, 0, network);
        NetworkComp netComp = new NetworkComp();
        netComp.plot(network);
        return netComp;
    }
    
    private Boolean isPoint(Block block){
		return block.getClass() == Point.class;
    }
    
    private Boolean isSection(Block block){
		return block.getClass() == Section.class;
    }
    
    private Boolean isSignal(Block block){
		return block.getClass() == Signal.class;
    }
    
}
