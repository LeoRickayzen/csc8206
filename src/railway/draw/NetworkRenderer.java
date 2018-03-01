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
    
    int level = -1;
    
    public NetworkRenderer(Network network){
    	this.network = network;
    }
    
    public NetworkComp Render(Network network){
    	components = new ArrayList<railway.draw.Component>();
        Block root = network.getFirst();
        return draw(root, network, true, false);
        
    }
    
    public Component getCompById(int id){
    	for(Component comp : components){
    		if(comp.getCompId() == id){
    			return comp;
    		}
    	}
    	return null;
    }
    
    public void leveler(int y, Point point, Boolean backwards, int index){
    	level = level+1;
    	if(point != null){
    		point.setTopLevel(level);
    	}
    	ArrayList<Point> points = new ArrayList<Point>();
    	Block block = network.getBlock(y);
    	while(block.hasNext(backwards)){
    		if(backwards){
        		index = index - 1;
    		}else{
    			index = index + 1;
    		}
        	Block nextBlock = network.getBlock(block.getNext(backwards));
        	nextBlock.setLevel(level);
        	nextBlock.setIndex(index);
        	if(isPoint(block)){
        		points.add((Point)block);
        	}
    	}
    	for(int i = points.size(); i > -1; i--){
        	leveler(points.get(i).getmNeigh(), points.get(i), points.get(i).isReverse(), points.get(i).getIndex());
    	}
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
    
    public NetworkComp draw(Block block, Network network, Boolean first, Boolean upper){
        /*double start = 10;
        if(block.getClass() == Point.class){
        	Point point = (Point)block;
        	if(!first){
            	start = getCompById(point.getMainNeigh()).getEnd();
        	}
            PointComp p = new PointComp(start, point.isReverse(), block.getId());
            components.add(p);
            if(point.getmNeigh() != 0){
                draw(network.getBlock(point.getmNeigh()), network, false, true);
            }
            if(point.getpNeigh() != 0){
                draw(network.getBlock(point.getpNeigh()), network, false, false);
            }
        }
        if(block.getClass() == Section.class){
            System.out.println(block.getClass().toString());
            Section s = (Section)block;
        	if(!first){
            	start = getCompById(s.getDownNeigh()).getEnd();
        	}
            TrackSection tc = new TrackSection(start, upper, block.getId());
            components.add(tc);
            System.out.println(s.getUpNeigh());
            if(s.getUpNeigh() != 0){
                draw(network.getBlock(s.getUpNeigh()), network, false, null);
            }
        }
        if(block.getClass() == Signal.class){
            Signal s = (Signal)block;
        	if(!first){
            	start = getCompById(s.getDownNeigh()).getEnd();
        	}
            SignalComp sc = new SignalComp(start, false, false, block.getId());
            components.add(sc);
            if(s.getUpNeigh() != 0){
                draw(network.getBlock(s.getUpNeigh()), network, false, null);
            }
        }
        return new NetworkComp(components);*/
    	return null;
    }
}
