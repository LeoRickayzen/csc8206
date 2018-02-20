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

        TrackSection a1 = new TrackSection(150.0, 450.0, false);
        TrackSection a2 = new TrackSection(460.0, 760.0, true);
        TrackSection a3 = new TrackSection(770.0, 1070.0, false);

        //Creating a Group
        Group root = new Group();

        root.getChildren().add(a1);
        root.getChildren().add(a2);
        root.getChildren().add(a3);

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
