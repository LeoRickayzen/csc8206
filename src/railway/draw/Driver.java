package railway.draw;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.scene.layout.Region;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;


/**
 * Driver class.
 */

public class Driver extends Application
{
    public void start(Stage stage)
    {
        try
        {
            FXMLLoader loader = new FXMLLoader(new File("layout/layout.fxml").toURI().toURL());
            Parent root = loader.load();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.setTitle("Railway");
            stage.getIcons().add(new Image("file:layout/images/latest.png"));
            stage.show();
        }
        catch (IOException e)
        {
            showErrorMessage(e);
        }
    }

    public static void showErrorMessage(Exception e)
    {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(e.getClass().getSimpleName());
        alert.setContentText(e.getMessage());
        alert.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
        alert.showAndWait();
    }

    public static void main(String args[])
    {
        launch(args);
    }
}
