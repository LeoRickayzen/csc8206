package railway.draw;

import javafx.beans.property.ObjectProperty;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import railway.network.Network;
import railway.network.Signal;

import java.io.File;
import java.io.IOException;

public class SignalComp extends Component {

	private int reverseConstant = 1;
	private Signal signal;

    public SignalComp(double[] start, Boolean reverse, Signal signal, LayoutController layoutController){
        
    	super(signal.getId(), signal, layoutController);

    	this.signal = signal;

    	if(reverse){
    		reverseConstant = -1;
    	}
    	
        Line leftUp = new Line();
        leftUp.setStartX(start[0]);
        leftUp.setEndX(start[0]);
        leftUp.setStartY(start[1]);
        leftUp.setEndY(start[1]-(reverseConstant*15));
        leftUp.setStroke(Color.ALICEBLUE);
        leftUp.setStrokeWidth(2);
        
        double labelx = start[0];
        double labely = start[1] + 20;
        
        Text label = new Text();
        label.setText("s" + signal.getId());
        label.setX(labelx);
        label.setY(labely);
        label.setStroke(Color.WHITE);
        
        this.getChildren().addAll(leftUp, label);
    }
}
