package railway.draw;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Stage;
import railway.file.RailwayFile;
import railway.network.Network;

import java.io.IOException;

public class main extends Application{
    /**
     * Driver class.
     *
     * code based on https://www.tutorialspoint.com/javafx/javafx_application.htm
     * @param stage
     */
    public void start(Stage stage) {
        TrackSection a1 = new TrackSection(150.0, false);
        Point p1 = new Point(a1.getEnd(), false);
        TrackSection a2 = new TrackSection(p1.getEnd(), true);
        TrackSection a4 = new TrackSection(p1.getEnd(), false);
        Point p2 = new Point(a2.getEnd(), true);
        TrackSection a3 = new TrackSection(p2.getEnd(), false);
        Signal s1 = new Signal(p2.getEnd(), false, false);

        //Creating a Group
        Group root = new Group();

        root.getChildren().add(a1);
        root.getChildren().add(p1);
        root.getChildren().add(a2);
        root.getChildren().add(p2);
        root.getChildren().add(a3);
        root.getChildren().add(a4);
        root.getChildren().add(s1);

        //Creating a Scene
        Scene scene = new Scene(root, 600, 300);

        //Setting title to the scene
        stage.setTitle("Sample application");

        //Adding the scene to the stage
        stage.setScene(scene);

        //Displaying the contents of a scene
        stage.show();
    }

    public static void main(String args[]){
        //TODO: Remove CLI testing
        try
        {
            RailwayFile file = new RailwayFile("res/railway.json");
            Network n = file.read();
            System.out.println(n);
            n.addPointToNetwork(new railway.network.Point(1, true, 1, 1, 1));
            System.out.println(n);
            file.write(n);
        }
        catch (IOException e)
        {
            System.out.println(e.getMessage());
        }

        launch(args);
    }
}
