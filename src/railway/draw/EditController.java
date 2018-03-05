package railway.draw;

import javafx.application.Platform;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import railway.file.RailwayFile;
import railway.network.*;
import railway.validation.ValidationException;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class EditController implements Initializable
{
    private LayoutController layoutController;
    private Block block;
    public ComboBox componentSelection;
    public Button deleteBtn;
    public Button addBtn;
    public AnchorPane anchorPane;
    public VBox controls;
    public ComboBox pointSelection;

    EditController(LayoutController layoutController, Block block)
    {
        this.layoutController = layoutController;
        this.block = block;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle)
    {
        controls.prefHeightProperty().bind(anchorPane.heightProperty());
        controls.prefWidthProperty().bind(anchorPane.widthProperty());

        if (block instanceof Point)
        {
            Point p = (Point) block;
            if (p.getmNeigh() != 0 || p.getpNeigh() != 0)
            {
                deleteBtn.setDisable(true);
            }
            pointSelection.setDisable(false);
        }
        else
        {
            addBtn.setDisable(false);
        }
    }

    public void pointTypeChange(ActionEvent actionEvent)
    {
        if(!componentSelection.getSelectionModel().isEmpty() && !pointSelection.getSelectionModel().isEmpty())
        {
            addBtn.setDisable(false);
        }
        else
        {
            addBtn.setDisable(true);
        }
    }

    public void delete(ActionEvent actionEvent)
    {
        //Block = to be deleted
        if(block instanceof Section)
        {
            Section s = (Section)block;
            doDelete(s.getDownNeigh());
            layoutController.getNetwork().removeSectionFromNetwork((Section)block);
        }
        else if(block instanceof Point)
        {
            Point p = (Point)block;
            doDelete(p.getMainNeigh());
            layoutController.getNetwork().removePointFromNetwork((Point)block);
        }
        else if(block instanceof Signal)
        {
            Signal s = (Signal)block;
            doDelete(s.getDownNeigh());
            layoutController.getNetwork().removeSignalFromNetwork((Signal) block);
        }
        layoutController.render(actionEvent);
        Stage stage = (Stage) deleteBtn.getScene().getWindow();
        stage.close();
    }

    private void doDelete(int parentId)
    {
        Block parentBlock = layoutController.getNetwork().getBlockById(parentId);
        //parentBlock = up neighbour
        if(parentBlock instanceof Section)
        {
            Section prevS = (Section)parentBlock;
            prevS.setUpNeigh(0);
        }
        else if(parentBlock instanceof Point)
        {
            Point prevP = (Point)parentBlock;
            if(prevP.getmNeigh() == block.getId())
            {
                prevP.setmNeigh(0);
            }
            else
            {
                prevP.setpNeigh(0);
            }
        }
        else if(parentBlock instanceof Signal)
        {
            Signal prevS = (Signal)parentBlock;
            prevS.setUpNeigh(0);
        }
    }

    public void add(ActionEvent actionEvent)
    {
        String toAdd = componentSelection.getSelectionModel().getSelectedItem().toString();
        int newId = layoutController.getNetwork().getNextId();

        switch (toAdd)
        {
            //TODO: Check constructor arguments
            case "Point":
                //TODO: reverse points
                Point point = new Point(newId, false, block.getId(), 0, 0, false);
                layoutController.getNetwork().addPointToNetwork(point);
                break;
            case "Section":
                Section section = new Section(newId, false, 0, block.getId());
                layoutController.getNetwork().addSectionToNetwork(section);
                break;
            case "Signal":
                Signal signal = new Signal(newId, true, "down", 0, block.getId());
                layoutController.getNetwork().addSignalToNetwork(signal);
                break;
        }
        createNewEndpoint(block, newId);
        layoutController.render(actionEvent);
        Stage stage = (Stage) addBtn.getScene().getWindow();
        stage.close();
    }

    private void createNewEndpoint(Block b, int newEndpointId)
    {
        if(b instanceof Signal)
        {
            Signal signal = (Signal)b;
            signal.setUpNeigh(newEndpointId);
        }
        else if(b instanceof Section)
        {
            Section section = (Section)b;
            section.setUpNeigh(newEndpointId);
        }
        else if(b instanceof Point)
        {
            Point point = (Point)b;
            if(pointSelection.getSelectionModel().getSelectedItem().equals("Plus"))
            {
                point.setpNeigh(newEndpointId);
            }
            else if(pointSelection.getSelectionModel().getSelectedItem().equals("Minus"))
            {
                point.setmNeigh(newEndpointId);
            }
        }
    }
}
