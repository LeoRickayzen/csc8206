package railway.draw;

import javafx.scene.Group;
import javafx.scene.shape.Line;

public class Signal extends Group {

    double start;
    Boolean upper;
    double end;
    double yUpper;
    double yLower;

    public Signal(double start, Boolean upper, Boolean reverse){
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

        end = start+10;

        Line rightUp = new Line();
        rightUp.setStartX(end);
        rightUp.setEndX(end);
        rightUp.setStartY(yUpper);
        rightUp.setEndY(yLower);

        Line top = new Line();
        top.setStartY(yUpper);
        top.setEndY(yUpper);
        top.setStartX(start);
        top.setEndX(end);

        this.getChildren().addAll(leftUp, top, rightUp);
    }
}
