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
	
	/**
	 * @param start where to start drawing the signal from
	 * @param reverse if set to true draw the signal beneath the track section
	 * @param signal the signal object to draw
	 * @param layoutController passed for the super constructor
	 */
    public SignalComp(double[] start, Boolean reverse, Signal signal, LayoutController layoutController){
        
    	super(signal.getId(), signal, layoutController);

    	this.signal = signal;
    	
    	//constant causes line to be drawn downwards
    	if(reverse){
    		reverseConstant = -1;
    	}
    	
    	//draw the line
        Line leftUp = new Line();
        leftUp.setStartX(start[0]);
        leftUp.setEndX(start[0]);
        leftUp.setStartY(start[1]);
        leftUp.setEndY(start[1]-(reverseConstant*15));
        
        if(signal.isClear()) {
        	leftUp.setStroke(Color.GREEN);
        }
        else {
        	leftUp.setStroke(Color.RED);
        }
        
        leftUp.setStrokeWidth(2);
        
        //add label
        double labelx = start[0];
        double labely = start[1] + (reverseConstant*30);
        
        Text label = new Text();
        label.setText("s" + signal.getId());
        label.setX(labelx);
        label.setY(labely);
        label.setStroke(Color.WHITE);
        
        //add to the group
        this.getChildren().addAll(leftUp, label);
    }
}
