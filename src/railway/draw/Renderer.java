package railway.draw;

import javafx.scene.Group;
import railway.network.Component;
import railway.network.Network;
import railway.network.Section;

import java.util.ArrayList;

public class Renderer {

    ArrayList<railway.draw.Component> comps = new ArrayList<railway.draw.Component>();

    public void Render(Network network){
        Component root = network.getFirst();
    }

    public void Draw(Component comp){
        if(comp.getClass() == Point.class){
            Point p = new Point(comps.get(comps.size()-1).getEnd(), false);
            comps.add(p);
        }
        if(comp.getClass() == Section.class){
            TrackSection s = new TrackSection(comps.get(comps.size()-1).getEnd(), false);
            comps.add(s);
        }
        if(comp.getClass() == Signal.class){
            Signal s = new Signal(comps.get(comps.size()-1).getEnd(), false, false);
            comps.add(s);
        }
    }
}
