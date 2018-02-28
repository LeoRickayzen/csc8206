package railway.draw;

import javafx.scene.Group;
import railway.network.Network;

import java.util.ArrayList;

public class NetworkComp extends Group {
	double gap = 15;
    
	public NetworkComp(ArrayList<railway.draw.Component>  components){
        this.getChildren().addAll(components);
    }
	
	public void plot(Network network){
	
	}
}
