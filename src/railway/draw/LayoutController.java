package railway.draw;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import railway.file.RailwayFile;
import railway.network.Network;
import railway.validation.NetValidation;
import railway.validation.ValidationException;
import railway.validation.ValidationInfo;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class LayoutController implements Initializable
{
    public TextArea entryBox;
    public ComboBox<String> jsonFileOptions;
    public Pane visualRender;
    public VBox journeyPane;
    public VBox left;
    public VBox right;
    public AnchorPane anchorPane;
    public Button clearBtn;
    public Button renderBtn;
    public HBox entryBoxControl;
    public Button editorToggle;
    public TableView conflictsTable;
    private Network network;
    private boolean editorEnabled;
    private ValidationInfo networkValidation;
    private RailwayFile file;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle)
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
        entryBox.prefHeightProperty().bind(anchorPane.heightProperty()
                .subtract(renderBtn.heightProperty())
                .subtract(entryBoxControl.heightProperty())
                .subtract(editorToggle.heightProperty())
        );
        left.prefWidthProperty().bind(anchorPane.widthProperty().subtract(right.prefWidthProperty()));
        visualRender.prefWidthProperty().bind(left.widthProperty());
        visualRender.prefHeightProperty().bind(anchorPane.heightProperty().multiply(0.66));
        jsonFileOptions.prefWidthProperty().bind(entryBoxControl.widthProperty().multiply(0.7));
        clearBtn.prefWidthProperty().bind(entryBoxControl.widthProperty().multiply(0.3));
        renderBtn.prefWidthProperty().bind(right.widthProperty());
        editorToggle.prefWidthProperty().bind(right.widthProperty());
        journeyPane.prefWidthProperty().bind(left.widthProperty());
        journeyPane.prefHeightProperty().bind(anchorPane.heightProperty().multiply(0.33));
    }

    public void clear(ActionEvent actionEvent)
    {
        network = null;
        visualRender.getChildren().clear();
        jsonFileOptions.getSelectionModel().clearSelection();
        entryBox.clear();
    }

    public void loadJson(ActionEvent actionEvent)
    {
        if(!jsonFileOptions.getSelectionModel().isEmpty())
        {
            visualRender.getChildren().clear();
            entryBox.clear();
            file = new RailwayFile("res/" + jsonFileOptions.getValue());

            try
            {
                entryBox.appendText(file.readJson());
                network = file.read();
            }
            catch (IOException e)
            {
                Driver.showErrorMessage(e, "Something has gone wrong");
            }
        }
    }

    public void render(ActionEvent actionEvent)
    {
        if (network != null)
        {
            networkValidation = NetValidation.Validate(network);

            if((networkValidation.isValid() && !isEditorEnabled()) || isEditorEnabled())
            {
                NetworkRenderer renderer = new NetworkRenderer(network);

                NetworkComp networkComp = renderer.draw(this);

                visualRender.getChildren().clear();

                visualRender.getChildren().addAll(networkComp);
            }
            else
            {
                Driver.showErrorMessage(new ValidationException(networkValidation.toString()), "Validation Exception");
            }
        }
    }

    public Network getNetwork()
    {
        return this.network;
    }

    public void setNetwork(Network network)
    {
        this.network = network;
    }

    public void toggleEditor(ActionEvent actionEvent)
    {
        if(isEditorEnabled())
        {
            try
            {
                render(actionEvent);

                if(networkValidation.isValid())
                {
                    entryBox.setEditable(false);
                    setEditorEnabled(false);
                    editorToggle.setText("Turn On Editor");
                    file.write(getNetwork());
                    entryBox.setText(file.readJson());
                }
                else
                {
                    Driver.showErrorMessage(new ValidationException(networkValidation.toString()), "Invalid Network");
                }
            }
            catch (IOException e)
            {
                Driver.showErrorMessage(e, "Could not save network file");
            }
        }
        else
        {
            entryBox.setEditable(true);
            editorToggle.setText("Save Changes");
            setEditorEnabled(true);
        }
    }

    public boolean isEditorEnabled()
    {
        return editorEnabled;
    }

    public void setEditorEnabled(boolean editorEnabled)
    {
        this.editorEnabled = editorEnabled;
    }
}
