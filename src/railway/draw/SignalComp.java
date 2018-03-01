package railway.draw;

import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;

public class SignalComp extends Component {


    public SignalComp(double[] start, Boolean reverse, int id){
        
    	super(id);
    	
        Line leftUp = new Line();
        leftUp.setStartX(start[0]);
        leftUp.setEndX(start[0]);
        leftUp.setStartY(start[1]);
        leftUp.setEndY(start[1]+5);
        leftUp.setStroke(Color.ALICEBLUE);
        leftUp.setStrokeWidth(2);

        Line rightUp = new Line();
        rightUp.setStartX(start[0]+5);
        rightUp.setEndX(start[0]+5);
        rightUp.setStartY(start[1]);
        rightUp.setEndY(start[1]+5);
        rightUp.setStroke(Color.ALICEBLUE);
        rightUp.setStrokeWidth(2);

        Line top = new Line();
        top.setStartY(start[1]+5);
        top.setEndY(start[1]+5);
        top.setStartX(start[0]);
        top.setEndX(start[0]+5);
        top.setStroke(Color.ALICEBLUE);
        top.setStrokeWidth(2);

        this.getChildren().addAll(leftUp, top, rightUp);
    }
}
