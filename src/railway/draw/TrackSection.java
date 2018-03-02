package railway.draw;

import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.StrokeLineCap;
import javafx.scene.text.Text;

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
        
        double labelx = (end[0] - start[0])/2 + start[0];
        double labely = start[1] + 20;
        
        Text label = new Text();
        label.setText("t" + id);
        label.setX(labelx);
        label.setY(labely);
        label.setStroke(Color.WHITE);
        
        System.out.println(start[0]);
        System.out.println((end[0] - start[0]) + start[0]);
        
        this.getChildren().addAll(line, label);
    }
}
