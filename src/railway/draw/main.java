package railway.draw;

import com.sun.prism.paint.Color;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import railway.draw.Point;
import railway.draw.Signal;
import railway.draw.TrackSection;


public class main extends Application{
    /**
     * code based on https://www.tutorialspoint.com/javafx/javafx_application.htm
     * @param stage
     */
    public void start(Stage stage) {

        GridPane gridPane = new GridPane();
        gridPane.setMinSize(400, 200);

        TrackSection a1 = new TrackSection(10.0, false);
        Point p1 = new Point(a1.getEnd(), false);
        TrackSection a2 = new TrackSection(p1.getEnd(), true);
        TrackSection a4 = new TrackSection(p1.getEnd(), false);
        Point p2 = new Point(a2.getEnd(), true);
        TrackSection a3 = new TrackSection(p2.getEnd(), false);
        Signal s1 = new Signal(p2.getEnd(), false, false);

        //Creating a Group
        Pane root = new Pane();
        
        root.getChildren().add(a1);
        root.getChildren().add(p1);
        root.getChildren().add(a2);
        root.getChildren().add(p2);
        root.getChildren().add(a3);
        root.getChildren().add(a4);
        root.getChildren().add(s1);
        
        root.setStyle("-fx-background-color: black;");
        
        TextArea entryBox = new TextArea();
        
        FlowPane textControls = new FlowPane();
        
        Button clearButton = new Button("clear");
        Button renderButton = new Button("render");
        Button loadButton = new Button("load");
        
        textControls.getChildren().addAll(clearButton, renderButton, loadButton);
        
        gridPane.add(root, 1, 0, 1, 2);
        gridPane.add(entryBox, 0, 0, 1, 1);
        gridPane.add(textControls, 0, 1);
        
        ColumnConstraints col1Constraints = new ColumnConstraints();
        col1Constraints.setPercentWidth(35);
        ColumnConstraints col2Constraints = new ColumnConstraints();
        col2Constraints.setPercentWidth(65);
        
        gridPane.getColumnConstraints().addAll(col1Constraints, col2Constraints);
        
        //Creating a Scene
        Scene scene = new Scene(gridPane, 600, 300);

        //Setting title to the scene
        stage.setTitle("Railway");

        //Adding the scene to the stage
        stage.setScene(scene);

        //Displaying the contents of a scene
        stage.show();
    }

    public static void main(String args[]){
        launch(args);
    }
}
