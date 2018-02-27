package railway.draw;

import javafx.scene.Group;
import railway.network.Block;
import railway.network.Network;
import railway.network.Point;
import railway.network.Section;
import railway.network.Signal;

import java.util.ArrayList;

public class NetworkRenderer {

    ArrayList<railway.draw.Component> components = new ArrayList<railway.draw.Component>();

    public NetworkComp Render(Network network){
        Block root = network.getFirst();
        return draw(root, network);
        
    }
    
    public Component getCompById(int id){
    	for(Component comp : components){
    		if(comp.getCompId() == id){
    			return comp;
    		}
    	}
    	return null;
    }

    public NetworkComp draw(Block block, Network network){
        if(block.getClass() == Point.class){
        	Point point = (Point)block;
        	double start = getCompById(point.getMainNeigh()).getEnd();
            PointComp p = new PointComp(start, false, block.getId());
            components.add(p);
            draw(network.getBlock(point.getmNeigh()), network);
            draw(network.getBlock(point.getpNeigh()), network);
        }
        if(block.getClass() == Section.class){
            Section s = (Section)block;
        	double start = getCompById(s.getDownNeigh()).getEnd();
            TrackSection tc = new TrackSection(start, false, block.getId());
            components.add(tc);
            draw(network.getBlock(s.getUpNeigh()), network);
        }
        if(block.getClass() == Signal.class){
            Signal s = (Signal)block;
        	double start = getCompById(s.getDownNeigh()).getEnd();
            SignalComp sc = new SignalComp(start, false, false, block.getId());
            components.add(sc);
            draw(network.getBlock(s.getUpNeigh()), network);
        }
        return new NetworkComp(components);
    }
}
