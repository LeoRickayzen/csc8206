package railway.draw;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import railway.file.RailwayFile;
import railway.network.Network;
import railway.network.Route;
import railway.validation.NetValidation;
import railway.validation.ValidationException;
import railway.validation.ValidationInfo;
import routeCalculation.RouteConflict;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;
import java.util.ResourceBundle;

/**
 * Controller for main GUI
 *
 * @author josh
 *
 */
public class LayoutController implements Initializable
{
    public TextArea entryBox;
    public ComboBox<String> jsonFileOptions;
    public ScrollPane visualRenderParent;
    public Pane visualRender;
    public VBox journeyPane;
    public VBox left;
    public VBox right;
    public AnchorPane anchorPane;
    public Button clearBtn;
    public Button renderBtn;
    public HBox tableControls;
    public HBox entryBoxControl;
    public Button editorToggle;
    public TableView<Row> conflictsTable;
    public TableColumn<Row, String> idColumn;
    public TableColumn<Row, String> sourceColumn;
    public TableColumn<Row, String> destColumn;
    public TableColumn<Row, String> pointsColumn;
    public TableColumn<Row, String> signalsColumn;
    public TableColumn<Row, String> pathColumn;
    public TableColumn<Row, String> conflictColumn;
    public TableColumn<Route, Route> setInterlockingColumn;
    public TextField idBox;
    public TextField sourceBox;
    public TextField destBox;
    private Network network;
    private boolean editorEnabled;
    private ValidationInfo networkValidation;
    private RailwayFile file;
    private ArrayList<Route> routes = new ArrayList<>();

    /**
     * Initializes several aspect of the GUI:
     * -Relative width of components
     * -Column properties
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle)
    {
        ObservableList<String> options = FXCollections.observableArrayList();

        //List .json files from res folder
        for (File f :
                Objects.requireNonNull(
                        new File("res").listFiles((dir, name) -> name.endsWith(".json"))))
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
        visualRender.minWidthProperty().bind(left.widthProperty());
        visualRender.minHeightProperty().bind(anchorPane.heightProperty().multiply(0.66));
        visualRenderParent.prefWidthProperty().bind(left.widthProperty());
        visualRenderParent.prefHeightProperty().bind(anchorPane.heightProperty().multiply(0.66));
        jsonFileOptions.prefWidthProperty().bind(entryBoxControl.widthProperty().multiply(0.7));
        clearBtn.prefWidthProperty().bind(entryBoxControl.widthProperty().multiply(0.3));
        renderBtn.prefWidthProperty().bind(right.widthProperty());
        editorToggle.prefWidthProperty().bind(right.widthProperty());
        journeyPane.prefWidthProperty().bind(left.widthProperty());
        journeyPane.prefHeightProperty().bind(anchorPane.heightProperty().multiply(0.33));
        
        idColumn.setCellValueFactory(
        		new PropertyValueFactory<>("id")
        );      
        sourceColumn.setCellValueFactory(
        		new PropertyValueFactory<>("source")
        );    
        destColumn.setCellValueFactory(
        		new PropertyValueFactory<>("dest")
        );     
        pointsColumn.setCellValueFactory(
        		new PropertyValueFactory<>("points")
        );  
        signalsColumn.setCellValueFactory(
        		new PropertyValueFactory<>("signals")
        );  
        pathColumn.setCellValueFactory(
                new PropertyValueFactory<>("paths")
        );  
        conflictColumn.setCellValueFactory(
        		new PropertyValueFactory<>("conflicts")
        );
        setInterlockingColumn.setCellValueFactory(new PropertyValueFactory<>("setInterlockingBtn"));
    }
    
    /**
     * Event to be triggered when a row is added to the conflicts list
     * 
     * @param actionEvent
     */
    public void addRow(ActionEvent actionEvent)
    {
    	if(!idBox.getText().isEmpty() && !sourceBox.getText().isEmpty() && !destBox.getText().isEmpty())
    	{
    		int id = Integer.parseInt(idBox.getText());
        	int start = Integer.parseInt(sourceBox.getText());
        	int dest = Integer.parseInt(destBox.getText());
		    try
		    {
		    	routes.add(new Route(id, start, dest, network));
		        RouteConflict conflicts = new RouteConflict(routes, network);
		        conflictsTable.getItems().clear();
		        HashMap<Integer, ArrayList<Integer>> conflictsList = conflicts.calculateConflictRoute();
                for (Route route : routes)
                {
                    ArrayList<Integer> signals = conflicts.calculateSignal().get(route.getRouteID());
                    ArrayList<String> points = conflicts.calculatePointsSetting().get(route.getRouteID());
                    StringBuilder conflictsString = new StringBuilder();
                    String stopSignals = "";
                    StringBuilder pointSettings = new StringBuilder();
                    StringBuilder journey = new StringBuilder();
                    for (int signal : signals)
                    {
                        stopSignals = stopSignals + String.valueOf(signal) + ". ";
                    }
                    for (String point : points)
                    {
                        pointSettings.append(point).append(". ");
                    }
                    for (int block : route.getBlocks())
                    {
                        journey.append(String.valueOf(block)).append("-");
                    }
                    for (int routeId : conflictsList.get(route.getRouteID()))
                    {
                        conflictsString.append(String.valueOf(routeId) + ",");
                    }

                    
                    setInterlockingColumn.setCellFactory(col -> {
                        Button editButton = new Button("Set Interlocking");
                        TableCell<Route, Route> cell = new TableCell<Route, Route>() {
                            @Override
                            public void updateItem(Route r, boolean empty) {
                                super.updateItem(r, empty);
                                if (empty) {
                                    setGraphic(null);
                                } else {
                                    setGraphic(editButton);
                                }
                            }
                        };

                        editButton.setAlignment(Pos.BASELINE_CENTER);

                        editButton.setOnAction(e -> setInterlocking(routes.get(cell.getTableRow().getIndex())));

                        return cell ;
                    });
                    

                    Row row = new Row(route.getRouteID(), route.getSource().getId(), route.getDestination().getId(),
                            pointSettings.toString(), stopSignals, journey.toString(), conflictsString.toString());

                    conflictsTable.getItems().add(row);
                }
		    }catch(IllegalArgumentException e){
		    	Driver.showErrorMessage(e);
		    }
    	}else{
	    	Driver.showErrorMessage(new Exception("All fields must be filled"));
    	}
    }
    
    /**
     * Event to be triggered when interlocking button is presseed,
     * causes interlocking point settings to change according to the route selected
     * for interlocking
     * 
     * @param route
     */
    public void setInterlocking(Route route)
    {
        route.setInterlocking();
        render(null);
    }

    /**
     * clear all aspects of the GUI, Triggered when the clear button
     * is pressed
     * 
     * @param actionEvent
     */
    public void clear(ActionEvent actionEvent)
    {
        network = null;
        routes = new ArrayList<Route>();
        visualRender.getChildren().clear();
        jsonFileOptions.getSelectionModel().clearSelection();
        entryBox.clear();
        conflictsTable.getItems().clear();
        idBox.clear();
        destBox.clear();
        sourceBox.clear();
    }

    /**
     * load JSON from a file into the textbox,
     * triggered when a JSON file is selected
     * 
     * @param actionEvent
     */
    public void loadJson(ActionEvent actionEvent)
    {
        if(!jsonFileOptions.getSelectionModel().isEmpty())
        {
        	routes = new ArrayList<Route>();
            conflictsTable.getItems().clear();
            destBox.clear();
            sourceBox.clear();
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
                Driver.showErrorMessage(e);
            }
        }
    }

    /**
     * renders a visualization of the network
     * called when the render button is pressed
     * 
     * @param actionEvent
     */
    public void render(ActionEvent actionEvent)
    {
        if (network != null)
        {
            if(!isEditorEnabled())
            {
                networkValidation = NetValidation.Validate(network);
            }

            if(isEditorEnabled() || networkValidation.isValid())
            {
                NetworkRenderer renderer = new NetworkRenderer(network);

                NetworkComp networkComp = renderer.draw(this);

                visualRender.getChildren().clear();

                visualRender.getChildren().addAll(networkComp);
            }
            else
            {
                Driver.showErrorMessage(new ValidationException(networkValidation.toString()));
            }
        }
    }

    /**
     * returns the current network object that is loaded into the GUI
     * @return current loaded network
     */
    public Network getNetwork()
    {
        return this.network;
    }

    /**
     * sets the current network to be loaded into the GUI
     * 
     * @param network the network object to load
     */
    public void setNetwork(Network network)
    {
        this.network = network;
    }

    /**
     * Toggle editing mode within the GUI
     * Triggered when the edit button is pressed
     * 
     * @param actionEvent
     */
    public void toggleEditor(ActionEvent actionEvent)
    {
        conflictsTable.getItems().clear();
        destBox.clear();
        sourceBox.clear();
    	routes = new ArrayList<Route>();
        if(isEditorEnabled())
        {
            try
            {
                render(actionEvent);

                //if (networkValidation == null)
                //{
                    networkValidation = NetValidation.Validate(network);
                //}

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
                    Driver.showErrorMessage(new ValidationException(networkValidation.toString()));
                }
            }
            catch (IOException e)
            {
                Driver.showErrorMessage(e);
            }
        }
        else if(network != null)
        {
            entryBox.setEditable(true);
            editorToggle.setText("Save Changes");
            setEditorEnabled(true);
        }
    }
    
    /**
     * Find out if editor is enabled
     * 
     * @return Boolean on whether editor is enabled
     */
    public boolean isEditorEnabled()
    {
        return editorEnabled;
    }
    
    /**
     * Set whether editor is enabled
     * 
     * @param editorEnabled whether or not to enable the editor
     */
    public void setEditorEnabled(boolean editorEnabled)
    {
        this.editorEnabled = editorEnabled;
    }
}
