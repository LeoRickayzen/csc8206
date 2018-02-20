package railway;

import javafx.scene.shape.Line;

public class TrackSection extends Line {

    double upperY = 200.0;
    double lowerY = 150.0;

    public TrackSection(double start, double end, Boolean upper){
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
}
