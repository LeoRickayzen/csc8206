package railway.draw;

import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.StrokeLineCap;

public class TrackSection extends Component {

    Line line;

    public TrackSection(double[] start, double[] end, int id){
    	super(id);
        line = new Line();
        line.setStartX(start[0]);
        line.setStartY(start[1]);
        line.setEndX(end[0]);
        line.setEndY(end[1]);
        line.setStroke(Color.YELLOW);
        line.setStrokeWidth(5);
        line.setStrokeLineCap(StrokeLineCap.ROUND);
        this.getChildren().add(line);
    }
}
