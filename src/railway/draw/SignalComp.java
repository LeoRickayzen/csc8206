package railway.draw;

import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;

public class SignalComp extends Component {

    double start;
    Boolean upper;
    double end;
    double yUpper;
    double yLower;

    public SignalComp(double start, Boolean upper, Boolean reverse, int id){
        
    	super(id);
    	
    	if(upper){
            if(reverse){
                yUpper = 160;
                yLower = 150;
            }else{
                yUpper = 140;
                yLower = 150;
            }
        }else{
            if(reverse){
                yUpper = 210;
                yLower = 200;
            }else{
                yUpper = 190;
                yLower = 200;
            }
        }
        Line leftUp = new Line();
        leftUp.setStartX(start);
        leftUp.setEndX(start);
        leftUp.setStartY(yLower);
        leftUp.setEndY(yUpper);
        leftUp.setStroke(Color.ALICEBLUE);
        leftUp.setStrokeWidth(2);

        end = start+10;

        Line rightUp = new Line();
        rightUp.setStartX(end);
        rightUp.setEndX(end);
        rightUp.setStartY(yUpper);
        rightUp.setEndY(yLower);
        rightUp.setStroke(Color.ALICEBLUE);
        rightUp.setStrokeWidth(2);

        Line top = new Line();
        top.setStartY(yUpper);
        top.setEndY(yUpper);
        top.setStartX(start);
        top.setEndX(end);
        top.setStroke(Color.ALICEBLUE);
        top.setStrokeWidth(2);

        this.getChildren().addAll(leftUp, top, rightUp);
    }

    public double getEnd(){
        return end;
    }
}
