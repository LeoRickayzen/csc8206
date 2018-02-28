package railway.draw;

import javafx.scene.Group;

import java.util.ArrayList;

public class NetworkComp extends Group {
    public NetworkComp(ArrayList<railway.draw.Component>  components){
        this.getChildren().addAll(components);
    }
}
