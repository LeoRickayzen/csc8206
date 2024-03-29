package railway.draw;

import javafx.beans.property.ObjectProperty;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.StrokeLineCap;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import railway.network.Network;
import railway.network.Section;

import java.io.File;
import java.io.IOException;

public class TrackSection extends Component {

    private Line line;
    private Section section;

    /**
     * @param start where to start the track section from
     * @param end where to end the track section
     * @param section the track section to draw
     * @param layoutController passed for the super constructor
     */
    public TrackSection(double[] start, double[] end, Section section, LayoutController layoutController){
    	super(section.getId(), section, layoutController);

    	this.section = section;

    	//draw the line
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
        
        //add label
        Text label = new Text();
        label.setText("t" + section.getId());
        label.setX(labelx);
        label.setY(labely);
        label.setStroke(Color.WHITE);
        
        //add to the group
        this.getChildren().addAll(line, label);
    }
}
