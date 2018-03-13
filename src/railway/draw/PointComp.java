package railway.draw;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.StrokeLineCap;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import railway.network.Network;
import railway.network.Point;

import java.io.File;
import java.io.IOException;

public class PointComp extends Component {
	
	private double[] start; 
	private double[] upper;
	private double[] lower;
    private Point point;

    /**
     * 
     * @param start the coordinates of the point where the upper and lower line meet/start
     * @param upper the coordinates of the end of the upper line
     * @param lower the coordinates of the end of the lower line
     * @param reverse whether or not the point is in reverse, if set to false the point is drawing with the single track on the left and the 2 on the right (<), if false the point is inverted
     * @param point the point object to draw
     * @param layoutController passed for the super constructor
     */
    public PointComp(double[] start, double[] upper, double[] lower, Boolean reverse, Point point, LayoutController layoutController){
    	 	
    	super(point.getId(), point, layoutController);
    	
    	Color upperColor = null;
    	Color lowerColor = null;
    	
    	//set the color based on which way the point is open
    	if(point.isPlus()){
        	upperColor = Color.RED;
        	lowerColor = Color.GREEN;
    	}else{
        	upperColor = Color.GREEN;
        	lowerColor = Color.RED;
    	}
    	
    	this.point = point;
    	
    	//Plot the lines
    	this.setStart(start);
    	this.setUpper(upper);
    	this.setLower(lower);
        Line upperLine = new Line();
        Line lowerLine = new Line();
        
        upperLine.setStroke(upperColor);
        upperLine.setStrokeWidth(5);
        upperLine.setStrokeLineCap(StrokeLineCap.ROUND);
        upperLine.setStartX(start[0]);
        upperLine.setStartY(start[1]);
        upperLine.setEndX(upper[0]);
        upperLine.setEndY(upper[1]);
        
        lowerLine.setStroke(lowerColor);
        lowerLine.setStrokeWidth(5);
        lowerLine.setStrokeLineCap(StrokeLineCap.ROUND);
        lowerLine.setStartX(start[0]);
        lowerLine.setStartY(start[1]);
        lowerLine.setEndX(lower[0]);
        lowerLine.setEndY(lower[1]);
        
        double labelx = start[0];
        double labely = start[1] + 20;
        
        //add line label
        Text label = new Text();
        label.setText("p" + point.getId());
        label.setX(labelx);
        label.setY(labely);
        label.setStroke(Color.WHITE);
        
        //add all lines to the group
        this.getChildren().addAll(upperLine, lowerLine, label);
        }

    /**
     * get the start coordinates, the start being the tip of the point,
     * or the point at which both the upper and lower line join
     * 
     * @return start coords
     */
	public double[] getStart() {
		return start;
	}

	/**
	 * set the start coordinates
	 * 
	 * @param start
	 */
	public void setStart(double[] start) {
		this.start = start;
	}

	/**
	 * get the coordinates for the end of the lower line
	 * 
	 * @return end coords
	 */
	public double[] getLower() {
		return lower;
	}

	/**
	 * set the coordinates for the end of the lower line
	 * 
	 * @param end coords
	 */
	public void setLower(double[] lower) {
		this.lower = lower;
	}

	/**
	 * get the end coordinates of the upper line
	 * 
	 * @return the coordinates
	 */
	public double[] getUpper() {
		return upper;
	}
	
	/**
	 * set the end coordinates of the upper line
	 * 
	 * @param upper
	 */
	public void setUpper(double[] upper) {
		this.upper = upper;
	}

}
