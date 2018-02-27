package railway.draw;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import railway.file.RailwayFile;
import railway.network.Network;

import java.io.File;
import java.io.IOException;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicReference;

/**
 * Driver class.
 */

public class main extends Application{
    /**
     * code based on https://www.tutorialspoint.com/javafx/javafx_application.htm
     * @param stage
     */
    public void start(Stage stage)
    {

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
        /*Network network = new Network();

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
        network.addPointToNetwork(p2);*/

        root.setStyle("-fx-background-color: black;");

        TextArea entryBox = new TextArea();

        FlowPane textControls = new FlowPane();
        
        Button clearButton = new Button("clear");
        Button renderButton = new Button("render");
        Button loadButton = new Button("load");

        clearButton.setOnMouseClicked(mouseEvent -> entryBox.clear());
        ObservableList<String> options = FXCollections.observableArrayList();

        //List .json files from res folder
        for (File f :
                Objects.requireNonNull(
                        new File("res").listFiles(file ->
                                file.getName().substring(file.getName().lastIndexOf("."),
                                        file.getName().length()).toLowerCase().equals(".json"))
                ))
        {
            options.add(f.getName());
        }

        ComboBox<String> comboBox = new ComboBox<>(options);

        AtomicReference<Network> network = new AtomicReference<>();

        loadButton.setOnMouseClicked(mouseEvent ->
        {
            entryBox.clear();
            RailwayFile file = new RailwayFile("res/" + comboBox.getValue());

            try
            {
                entryBox.appendText(file.readJson());
                network.set(file.read());
            }
            catch (IOException e)
            {
                entryBox.appendText(e.getMessage());
            }
        });

        renderButton.setOnMouseClicked(mouseEvent ->
        {
            //TODO: Not working
            NetworkComp networkComp = renderer.Render(network.get());

            root.getChildren().addAll(networkComp);
        });

        textControls.getChildren().addAll(clearButton, renderButton, comboBox, loadButton);
        
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
