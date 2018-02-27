package railway.draw;

import com.sun.prism.paint.Color;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import railway.file.RailwayFile;
import railway.network.Network;
import railway.network.Point;
import railway.network.Section;

import java.io.IOException;


public class main extends Application{
    /**
     * Driver class.
     *
     * code based on https://www.tutorialspoint.com/javafx/javafx_application.htm
     * @param stage
     */
    public void start(Stage stage) {

        GridPane gridPane = new GridPane();
        gridPane.setMinSize(400, 200);

        /*
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
        */
        
        Pane root = new Pane();
        
        NetworkRenderer renderer = new NetworkRenderer();
        Network network = new Network();
        
        Section s1 = new Section();
        s1.setId(1);
        
        Point p2 = new Point();
        p2.setId(2);
        
        Section s3 = new Section();
        s3.setId(3);
        Section s4 = new Section();
        s4.setId(4);
        
        s1.setDownNeigh(0);
        s1.setUpNeigh(p2.getId());
        
        p2.setMainNeigh(s1.getId());
        p2.setpNeigh(s3.getId());
        p2.setmNeigh(s4.getId());
        
        s3.setDownNeigh(p2.getId());
        s3.setUpNeigh(0);
        
        s4.setDownNeigh(p2.getId());
        s4.setUpNeigh(0);
        
        network.addSectionToNetwork(s1);
        network.addSectionToNetwork(s3);
        network.addSectionToNetwork(s4);
        network.addPointToNetwork(p2);
        
        NetworkComp networkComp = renderer.Render(network);
        
        root.getChildren().addAll(networkComp);
        
        root.setStyle("-fx-background-color: black;");
        
        TextArea entryBox = new TextArea();
        
        FlowPane textControls = new FlowPane();
        
        Button clearButton = new Button("clear");
        Button renderButton = new Button("render");
        Button loadButton = new Button("load");

        clearButton.setOnMouseClicked(mouseEvent -> entryBox.clear());
        

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
        //TODO: Remove CLI testing
        try
        {
            RailwayFile file = new RailwayFile("res/testNetwork.json");
            Network n = file.read();
            System.out.println(n);
            System.out.println(n);
            file.write(n);
            System.out.println(file.readJson());
        }
        catch (IOException e)
        {
            System.out.println(e.getMessage());
        }

        launch(args);
    }
}
