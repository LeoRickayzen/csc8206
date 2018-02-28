package railway.draw;

import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.StrokeLineCap;

public class PointComp extends Component {


    public PointComp(double[] start, double[] upper, double[] lower, Boolean reverse, int id){
    	
    	super(id);

        Line upperLine = new Line();
        upperLine.setStroke(Color.RED);
        upperLine.setStrokeWidth(5);
        upperLine.setStrokeLineCap(StrokeLineCap.ROUND);
        upperLine.setStartX(start[0]);
        upperLine.setStartY(start[1]);
        upperLine.setEndX(upper[0]);
        upperLine.setEndY(upper[1]);
        
        Line lowerLine = new Line();
        lowerLine.setStroke(Color.RED);
        lowerLine.setStrokeWidth(5);
        lowerLine.setStrokeLineCap(StrokeLineCap.ROUND);
        upperLine.setStartX(start[0]);
        upperLine.setStartY(start[1]);
        upperLine.setEndX(lower[0]);
        upperLine.setEndY(lower[1]);

        this.getChildren().addAll(upperLine, lowerLine);
    }

}
