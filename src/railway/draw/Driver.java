package railway.draw;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import railway.file.RailwayFile;
import railway.network.Network;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicReference;

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
            stage.show();
        }
        catch (IOException e)
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Something has gone wrong");
            alert.setContentText(e.getMessage());

            alert.showAndWait();
        }
    }

    public static void main(String args[])
    {
        launch(args);
    }
}
