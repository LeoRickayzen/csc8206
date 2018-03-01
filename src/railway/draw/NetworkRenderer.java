package railway.draw;

import railway.network.Block;
import railway.network.Network;
import railway.network.Point;
import railway.network.Section;
import railway.network.Signal;

import java.util.ArrayList;

public class NetworkRenderer {

    private ArrayList<railway.draw.Component> components;

    public NetworkComp Render(Network network){
    	components = new ArrayList<>();
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

    public NetworkComp draw(Block block, Network network, Boolean first, Boolean upper){
        double start = 10;
        if(block.getClass() == Point.class){
        	Point point = (Point)block;
        	if(!first){
            	start = getCompById(point.getMainNeigh()).getEnd();
        	}
            PointComp p = new PointComp(start, false, block.getId());
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
        return new NetworkComp(components);
    }
}
