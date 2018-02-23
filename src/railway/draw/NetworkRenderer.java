package railway.draw;

import javafx.scene.Group;
import railway.network.Component;
import railway.network.Network;
import railway.network.Section;

import java.util.ArrayList;

public class NetworkRenderer {

    ArrayList<railway.draw.Component> comps = new ArrayList<railway.draw.Component>();

    public void Render(Network network){
        Component root = network.getFirst();
    }

    public railway.draw.Network Draw(Component comp){
        double start;
        if(comps.isEmpty()){
            start = 0;
        }else{
            start = comps.get(comps.size()-1).getEnd();
        }
        if(comp.getClass() == railway.network.Point.class){
            Point p = new Point(start, false);
            comps.add(p);
        }
        if(comp.getClass() == Section.class){
            TrackSection s = new TrackSection(start, false);
            comps.add(s);
        }
        if(comp.getClass() == railway.network.Signal.class){
            Signal s = new Signal(start, false, false);
            comps.add(s);
        }
        return new railway.draw.Network(comps);
    }
}
