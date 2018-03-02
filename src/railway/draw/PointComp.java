package railway.draw;

import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.StrokeLineCap;
import javafx.scene.text.Text;

public class PointComp extends Component {
	
	private double[] start; 
	private double[] upper;
	private double[] lower;

    public PointComp(double[] start, double[] upper, double[] lower, Boolean reverse, int id){
    	
    	super(id);
    	
    	this.setStart(start);
    	this.setUpper(upper);
    	this.setLower(lower);
        Line upperLine = new Line();
        Line lowerLine = new Line();
        
        upperLine.setStroke(Color.RED);
        upperLine.setStrokeWidth(5);
        upperLine.setStrokeLineCap(StrokeLineCap.ROUND);
        upperLine.setStartX(start[0]);
        upperLine.setStartY(start[1]);
        upperLine.setEndX(upper[0]);
        upperLine.setEndY(upper[1]);
        
        lowerLine.setStroke(Color.RED);
        lowerLine.setStrokeWidth(5);
        lowerLine.setStrokeLineCap(StrokeLineCap.ROUND);
        lowerLine.setStartX(start[0]);
        lowerLine.setStartY(start[1]);
        lowerLine.setEndX(lower[0]);
        lowerLine.setEndY(lower[1]);
        
        double labelx = start[0];
        double labely = start[1] + 20;
        
        Text label = new Text();
        label.setText("p" + id);
        label.setX(labelx);
        label.setY(labely);
        label.setStroke(Color.WHITE);

        this.getChildren().addAll(upperLine, lowerLine, label);
    }

	public double[] getStart() {
		return start;
	}

	public void setStart(double[] start) {
		this.start = start;
	}

	public double[] getLower() {
		return lower;
	}

	public void setLower(double[] lower) {
		this.lower = lower;
	}

	public double[] getUpper() {
		return upper;
	}

	public void setUpper(double[] upper) {
		this.upper = upper;
	}

}
