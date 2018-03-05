package railway.draw;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import railway.file.RailwayFile;
import railway.network.Block;
import railway.network.Network;
import railway.network.Point;
import railway.network.Route;
import railway.network.Signal;
import routeCalculation.RouteConflict;
import railway.validation.NetValidation;
import railway.validation.ValidationException;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicReference;
import java.io.File;
import java.io.IOException;

/**
 * Driver class.
 */

public class Driver extends Application
{
    public void start(Stage stage) throws ValidationException
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
            showErrorMessage(e, "Cannot load.");
        }
    }

    public static void showErrorMessage(Exception e, String header)
    {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(header);
        alert.setContentText(e.getMessage());

        alert.showAndWait();
    }

    public static void main(String args[])
    {
        launch(args);
    }
}
