package railway.draw;

import javafx.scene.Group;
import railway.network.Block;
import railway.network.Network;
import railway.network.Section;

import java.util.ArrayList;

public class NetworkRenderer {

    ArrayList<railway.draw.Component> blocks = new ArrayList<railway.draw.Component>();

    public void Render(Network network){
        Block root = network.getFirst();
    }

    public railway.draw.Network Draw(Block block){
        double start;
        if(blocks.isEmpty()){
            start = 0;
        }else{
            start = blocks.get(blocks.size()-1).getEnd();
        }
        if(block.getClass() == railway.network.Point.class){
            Point p = new Point(start, false);
            blocks.add(p);
        }
        if(block.getClass() == Section.class){
            TrackSection s = new TrackSection(start, false);
            blocks.add(s);
        }
        if(block.getClass() == railway.network.Signal.class){
            Signal s = new Signal(start, false, false);
            blocks.add(s);
        }
        return new railway.draw.Network(blocks);
    }
}
