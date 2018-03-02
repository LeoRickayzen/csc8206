package railway.draw;

//import com.sun.xml.internal.ws.policy.privateutil.PolicyUtils;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import railway.file.RailwayFile;
import railway.network.Network;
import railway.validation.NetValidation;
import railway.validation.ValidationException;
//import sun.nio.ch.Net;

import java.io.File;
import java.io.IOException;
import java.util.Objects;

public class LayoutController
{
    public TextArea entryBox;
    public ComboBox<String> jsonFileOptions;
    public Pane visualRender;
    public VBox left;
    public VBox right;
    public AnchorPane anchorPane;
    public Button clearBtn;
    public Button renderBtn;
    public HBox entryBoxControl;
    public Pane networkEditor;
    private Network network;

    public void initialize()
    {
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
        jsonFileOptions.getItems().addAll(options);

        //Create dynamic GUI adjusting to window dimensions
        entryBox.prefHeightProperty().bind(anchorPane.heightProperty().subtract(renderBtn.heightProperty()).subtract(entryBoxControl.heightProperty()));
        left.prefWidthProperty().bind(anchorPane.widthProperty().subtract(right.prefWidthProperty()));
        visualRender.prefWidthProperty().bind(left.widthProperty());
        visualRender.prefHeightProperty().bind(anchorPane.heightProperty().multiply(0.66));
        jsonFileOptions.prefWidthProperty().bind(entryBoxControl.widthProperty().multiply(0.7));
        clearBtn.prefWidthProperty().bind(entryBoxControl.widthProperty().multiply(0.3));
        renderBtn.prefWidthProperty().bind(right.widthProperty());
    }

    public void clear(ActionEvent actionEvent)
    {
        visualRender.getChildren().clear();
        jsonFileOptions.getSelectionModel().clearSelection();
        entryBox.clear();
    }

    public void loadJson(ActionEvent actionEvent)
    {
        if(!jsonFileOptions.getSelectionModel().isEmpty())
        {
            entryBox.clear();
            RailwayFile file = new RailwayFile("res/" + jsonFileOptions.getValue());

            try
            {
                entryBox.appendText(file.readJson());
                network = file.read();
            }
            catch (ValidationException e)
            {
                NetValidation.showErrorMessage(e, "Invalid Network");
            }
            catch (IOException e)
            {
                NetValidation.showErrorMessage(e, "Something has gone wrong");
            }
        }
    }

    public void render(ActionEvent actionEvent)
    {
        NetworkRenderer renderer = new NetworkRenderer(network);

        NetworkComp networkComp = renderer.draw();

        visualRender.getChildren().clear();
        
        visualRender.getChildren().addAll(networkComp);

    }
}
