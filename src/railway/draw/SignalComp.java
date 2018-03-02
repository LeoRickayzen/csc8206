package railway.draw;

import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;

public class SignalComp extends Component {

	int reverseConstant = 1;

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
        
        /*
        Line rightUp = new Line();
        rightUp.setStartX(start[0]+2);
        rightUp.setEndX(start[0]+2);
        rightUp.setStartY(start[1]);
        rightUp.setEndY(start[1]-15);
        rightUp.setStroke(Color.ALICEBLUE);
        rightUp.setStrokeWidth(2);

        Line top = new Line();
        top.setStartY(start[1]-15);
        top.setEndY(start[1]-15);
        top.setStartX(start[0]);
        top.setEndX(start[0]+2);
        top.setStroke(Color.ALICEBLUE);
        top.setStrokeWidth(2);

        this.getChildren().addAll(leftUp, top, rightUp);
        */
        this.getChildren().addAll(leftUp);
    }
}
