package railway.draw;

import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;

public class SignalComp extends Component {

	double reverseConstant = 1;

    public SignalComp(double[] start, Boolean reverse, int id){
        
    	super(id);
    	
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
        double labely = start[1] + (reverseConstant*30);
        
        Text label = new Text();
        label.setText("s" + id);
        label.setX(labelx);
        label.setY(labely);
        label.setStroke(Color.WHITE);
        
        this.getChildren().addAll(leftUp, label);
    }
}
