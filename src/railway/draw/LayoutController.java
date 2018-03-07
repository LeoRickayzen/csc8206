package railway.draw;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import railway.file.RailwayFile;
import railway.network.Block;
import railway.network.Network;
import railway.network.Route;
import railway.network.Signal;
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
    public HBox tableControls;
    public HBox entryBoxControl;
    public Button editorToggle;
    public TableView conflictsTable;
    public TableColumn idColumn;
    public TableColumn sourceColumn;
    public TableColumn destColumn;
    public TableColumn pointsColumn;
    public TableColumn signalsColumn;
    public TableColumn pathColumn;
    public TableColumn conflictColumn;
    public TextField idBox;
    public TextField sourceBox;
    public TextField destBox;
    private Network network;
    private boolean editorEnabled;
    private ValidationInfo networkValidation;
    private RailwayFile file;
    private ArrayList<Route> routes = new ArrayList<Route>();

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
        
        idColumn.setCellValueFactory(
        		new PropertyValueFactory<Row,String>("id")
        );      
        sourceColumn.setCellValueFactory(
        		new PropertyValueFactory<Row,String>("source")
        );    
        destColumn.setCellValueFactory(
        		new PropertyValueFactory<Row,String>("dest")
        );     
        pointsColumn.setCellValueFactory(
        		new PropertyValueFactory<Row,String>("points")
        );  
        signalsColumn.setCellValueFactory(
        		new PropertyValueFactory<Row,String>("signals")
        );  
        pathColumn.setCellValueFactory(
        		new PropertyValueFactory<Row,String>("paths")
        );  
        conflictColumn.setCellValueFactory(
        		new PropertyValueFactory<Row,String>("conflicts")
        );  
    }
    
    public void addRow(ActionEvent actionEvent)
    {
    	if(!idBox.getText().isEmpty() && !sourceBox.getText().isEmpty() && !destBox.getText().isEmpty()){
    		int id = Integer.parseInt(idBox.getText());
        	int start = Integer.parseInt(sourceBox.getText());
        	int dest = Integer.parseInt(destBox.getText());
		    try{
		    	routes.add(new Route(id, start, dest, network));
		        RouteConflict conflicts = new RouteConflict(routes, network);
		        conflictsTable.getItems().clear();
		        HashMap<Integer, ArrayList<Integer>> conflictsList = conflicts.calculateConflictRoute();
		        for(int i = 0; i < routes.size(); i++){
		        //for(Route route: routes){
		        	Route route = routes.get(i);
		        	ArrayList<Integer> signals = conflicts.calculateSignal().get(route.getRouteID());
		        	ArrayList<String> points = conflicts.calculatePointsSetting().get(route.getRouteID());
		        	String conflictsString = "";
		        	String stopSignals = "";
		        	String pointSettings = "";
		        	String journey = "";
		        	for(int signal: signals){
		        		stopSignals = String.valueOf(signal) + ". ";
		        	}
		        	for(String point: points){
		        		pointSettings = point + ". ";
		        	}
		        	for(int block: route.getBlocks()){
		        		journey = journey + String.valueOf(block) + "-";
		        	}
		        	for(int routeId : conflictsList.get(route.getRouteID())){
		           	conflictsString = conflictsString + String.valueOf(routeId);
		           }
		        	conflictsTable.getItems().add(new Row(route.getRouteID(), route.getSource().getId(), route.getDestination().getId(), pointSettings, stopSignals, journey, conflictsString));
		        }
		    }catch(IllegalArgumentException e){
		    	Driver.showErrorMessage(e);
		    }
    	}else{
	    	Driver.showErrorMessage(new Exception("All feilds must be filled"));
    	}
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
                Driver.showErrorMessage(e);
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
                Driver.showErrorMessage(new ValidationException(networkValidation.toString()));
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
                    Driver.showErrorMessage(new ValidationException(networkValidation.toString()));
                }
            }
            catch (IOException e)
            {
                Driver.showErrorMessage(e);
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
