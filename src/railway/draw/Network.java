package railway.draw;

import javafx.scene.Group;

import java.util.ArrayList;

public class Network extends Group {
    public Network(ArrayList<railway.draw.Component>  components){
        this.getChildren().addAll(components);
    }
}
