package railway;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class main extends Application{
    /**
     * code based on https://www.tutorialspoint.com/javafx/javafx_application.htm
     * @param stage
     */
    public void start(Stage stage) {

        TrackSection a1 = new TrackSection(150.0, 450.0, true);
        Point p1 = new Point(450, 460, false);
        TrackSection a2 = new TrackSection(460.0, 760.0, false);
        TrackSection a4 = new TrackSection(460.0, 760.0, true);
        Point p2 = new Point(760, 770, true);
        TrackSection a3 = new TrackSection(770.0, 1070.0, true);

        //Creating a Group
        Group root = new Group();

        root.getChildren().add(a1);
        root.getChildren().add(p1);
        root.getChildren().add(a2);
        root.getChildren().add(p2);
        root.getChildren().add(a3);
        root.getChildren().add(a4);

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
        launch(args);
    }
}
