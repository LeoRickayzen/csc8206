package railway.draw;

import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;

public class Point extends Component {

    private double end;

    public Point(double start, Boolean reverse){

        double upperY;
        double lowerY;
        this.end = start + 25;

        if(reverse){
            upperY = 200.0;
            lowerY = 150.0;
        }else{
            upperY = 150.0;
            lowerY = 200.0;
        }

        Line upper = new Line();
        upper.setStroke(Color.BLUE);
        Line lower = new Line();
        lower.setStroke(Color.BLUE);

        upper.setStartX(start);
        lower.setStartX(start);
        upper.setEndX(end);
        lower.setEndX(end);

        upper.setStartY(lowerY);
        upper.setEndY(upperY);
        lower.setStartY(200.0);
        lower.setEndY(200.0);
        this.getChildren().addAll(upper, lower);
    }

    public double getEnd(){
        return end;
    }
}
