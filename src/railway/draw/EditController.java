package railway.draw;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import railway.network.*;

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
    public ComboBox<String> secondarySelection;

    EditController(LayoutController layoutController, Block block)
    {
        this.layoutController = layoutController;
        this.block = block;
    }

    /**
     * Initializes several aspect of the GUI:
     * -Heights and widths of elements
     * -Enables/disables dropdowns depending on block type
     *
     * @param resourceBundle
     * @param url
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle)
    {
        controls.prefHeightProperty().bind(anchorPane.heightProperty());
        controls.prefWidthProperty().bind(anchorPane.widthProperty());

        if (block instanceof Point)
        {
            Point p = (Point) block;
            if(!p.isReverse())
            {
                if (p.getmNeigh() != 0 || p.getpNeigh() != 0)
                {
                    deleteBtn.setDisable(true);
                }
                pointSelection.setDisable(false);
            }
        }
    }

    /**
     * Event to be triggered when a type of block to add is selected
     *
     * @param actionEvent The event
     */
    public void typeSelected(ActionEvent actionEvent)
    {
        pointSelection.getSelectionModel().clearSelection();
        secondarySelection.getSelectionModel().clearSelection();
        pointSelection.setDisable(true);
        secondarySelection.setDisable(true);
        addBtn.setDisable(true);

        if(componentSelection.getSelectionModel().getSelectedItem().equals("Reverse Point"))
        {
            pointSelection.setDisable(false);
            secondarySelection.setDisable(false);

            ObservableList<String> options = FXCollections.observableArrayList();

            //List all possible
            for (Signal s: layoutController.getNetwork().getSignals())
            {
                if(s.getUpNeigh() == 0 && s.getId()!=block.getId())
                {
                    options.add(String.valueOf(s.getId()));
                }
            }
            secondarySelection.getItems().addAll(options);

            pointSelection.setDisable(false);
            secondarySelection.setDisable(false);
        }
        else if(componentSelection.getSelectionModel().getSelectedItem().equals("Point"))
        {
            pointSelection.setDisable(false);
        }
        else
        {
            addBtn.setDisable(false);
        }
    }

    /**
     * Event to be triggered when a type of point to add is selected
     *
     * @param actionEvent The event
     */
    public void pointTypeChange(ActionEvent actionEvent)
    {
        addBtn.setDisable(true);

        if(componentSelection.getSelectionModel().getSelectedItem().equals("Point") && !pointSelection.getSelectionModel().isEmpty())
        {
            addBtn.setDisable(false);
        }
        else if(!pointSelection.getSelectionModel().isEmpty() && !secondarySelection.getSelectionModel().isEmpty())
        {
            addBtn.setDisable(false);
        }
    }

    /**
     * Event to be triggered when endpoint to add to is selected
     *
     * @param actionEvent The event
     */
    public void secondaryChange(ActionEvent actionEvent)
    {
        addBtn.setDisable(true);

        if(!componentSelection.getSelectionModel().isEmpty() && !pointSelection.getSelectionModel().isEmpty() && !secondarySelection.getSelectionModel().isEmpty())
        {
            addBtn.setDisable(false);
        }
    }

    /**
     * Event to be triggered when a block is to be deleted
     *
     * @param actionEvent The event
     */
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
            if(p.isReverse())
            {
                doDelete(p.getmNeigh());
                doDelete(p.getpNeigh());
            }
            else
            {
                doDelete(p.getMainNeigh());
            }
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

    /**
     * Sets the new endpoint to 0 of the parent id
     *
     * @param parentId The block id of the down neighbour which will be set as new endpoint
     */
    private void doDelete(int parentId)
    {
        Block parentBlock = layoutController.getNetwork().getBlock(parentId);
        //parentBlock = down neighbour
        if(parentBlock instanceof Section)
        {
            Section prevS = (Section)parentBlock;
            prevS.setUpNeigh(0);
        }
        else if(parentBlock instanceof Point)
        {
            Point prevP = (Point)parentBlock;
            if(prevP.isReverse())
            {
                prevP.setMainNeigh(0);
            }
            else
            {
                if (prevP.getmNeigh() == block.getId())
                {
                    prevP.setmNeigh(0);
                } else
                {
                    prevP.setpNeigh(0);
                }
            }
        }
        else if(parentBlock instanceof Signal)
        {
            Signal prevS = (Signal)parentBlock;
            prevS.setUpNeigh(0);
        }
    }

    /**
     * Event to be triggered when a block is to be added
     *
     * @param actionEvent The event
     */
    public void add(ActionEvent actionEvent)
    {
        String toAdd = componentSelection.getSelectionModel().getSelectedItem().toString();
        int newId = layoutController.getNetwork().getNextId();

        switch (toAdd)
        {
            case "Point":
                Point point = new Point(newId, false, block.getId(), 0, 0, false);
                layoutController.getNetwork().addPointToNetwork(point);
                break;
            case "Reverse Point":
                Point reversePoint;
                if(pointSelection.getSelectionModel().getSelectedItem().equals("Minus"))
                {
                    reversePoint = new Point(newId, false, 0, block.getId(), Integer.parseInt(secondarySelection.getSelectionModel().getSelectedItem()), true);
                }
                else
                {
                    reversePoint = new Point(newId, false, 0, Integer.parseInt(secondarySelection.getSelectionModel().getSelectedItem()), block.getId(), true);
                }
                createNewEndpoint(layoutController.getNetwork().getBlock(Integer.parseInt(secondarySelection.getSelectionModel().getSelectedItem())), newId);
                layoutController.getNetwork().addPointToNetwork(reversePoint);
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

    /**
     * Creates a the new endpoint on the block for the given id
     *
     * @param b The block
     * @param newEndpointId The new endpoint id
     */
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
            if(point.isReverse())
            {
                point.setMainNeigh(newEndpointId);
            }
            else
            {
                if (pointSelection.getSelectionModel().getSelectedItem().equals("Plus"))
                {
                    point.setpNeigh(newEndpointId);
                }
                else if (pointSelection.getSelectionModel().getSelectedItem().equals("Minus"))
                {
                    point.setmNeigh(newEndpointId);
                }
            }
        }
    }
}
