package railway.draw;

import javafx.scene.Group;
import javafx.scene.shape.Line;

public class TrackSection extends Component {

    double upperY = 150;
    double lowerY = 200;
    private double start;
    private double end;
    Line line;

    public TrackSection(double start, Boolean upper){
        line = new Line();
        this.end = start + 50;
        line.setStartX(start);
        line.setEndX(end);
        if(upper){
            line.setStartY(upperY);
            line.setEndY(upperY);
        }else{
            line.setStartY(lowerY);
            line.setEndY(lowerY);
        }
        this.getChildren().add(line);
    }

    public double getEnd() {
        return end;
    }

    public void setEnd(double end) {
        this.end = end;
    }
}