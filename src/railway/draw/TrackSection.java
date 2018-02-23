package railway.draw;

import javafx.scene.shape.Line;

public class TrackSection extends Line {

    double upperY = 150;
    double lowerY = 200;
    private double start;
    private double end;

    public TrackSection(double start, Boolean upper){
        this.end = start + 50;
        this.setStartX(start);
        this.setEndX(end);
        if(upper){
            this.setStartY(upperY);
            this.setEndY(upperY);
        }else{
            this.setStartY(lowerY);
            this.setEndY(lowerY);
        }
    }

    public double getEnd() {
        return end;
    }

    public void setEnd(double end) {
        this.end = end;
    }
}
