package railway.draw;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Cursor;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import railway.network.*;

import java.io.File;
import java.io.IOException;

public class Component extends Group {
	
	private int compid;
	private int level;
	private Block block;
	private LayoutController layoutController;
	//Making component clickable
	private ObjectProperty<EventHandler<MouseEvent>> propertyOnAction = new SimpleObjectProperty<>();

	/**
	 * @param id the id of the component
	 * @param block the datastructure representation of the component
	 * @param layoutController passed for the super constructor
	 */
	public Component(int id, Block block, LayoutController layoutController){
		this.compid = id;
		this.block = block;
		this.layoutController = layoutController;

        this.setOnMouseEntered(event -> this.getScene().setCursor(Cursor.HAND));
        this.setOnMouseExited(event -> this.getScene().setCursor(Cursor.DEFAULT));
        this.setOnMouseClicked(event -> onActionProperty().get().handle(event));
    }
	
	/**
	 * get the level the component is on on the grid
	 * 
	 * @return level
	 */
	public int getLevel(){
		return level;
	}
	
	/**
	 * set the level of the component
	 * 
	 * @param level
	 */
	public void setLevel(int level){
		this.level = level;
	}
	
	/**
	 * get the Id of the component
	 * 
	 * @return component Id
	 */
	public int getCompId(){
		return compid;
	}
	
	/**
	 * set the Id of the component
	 * 
	 * @param compid
	 */
	public void setId(int compid){
		this.compid = compid;
	}

	/**
	 * Handles clickable aspect of the network component
	 * 
	 * @return
	 */
	public final ObjectProperty<EventHandler<MouseEvent>> onActionProperty()
    {
        if(layoutController.isEditorEnabled())
        {
            if (layoutController.getNetwork().getUpEndpoints().contains(block.getId()))
            {
                try
                {
                    Stage dialogStage = new Stage();
                    FXMLLoader loader = new FXMLLoader(new File("layout/edit_component.fxml").toURI().toURL());
                    EditController controller = new EditController(layoutController, block);
                    loader.setController(controller);
                    AnchorPane root = loader.load();
                    Scene scene = new Scene(root);
                    dialogStage.setScene(scene);
                    dialogStage.setTitle("Editor");
                    dialogStage.showAndWait();
                }
                catch (IOException e)
                {
                    Driver.showErrorMessage(e);
                }
            }
            else
            {
                Driver.showErrorMessage(new UnsupportedOperationException("Can only alter end points."));
            }
        }

		return propertyOnAction;
	}

	/**
	 * set the action property
	 * 
	 * @param handler
	 */
	public final void setOnAction(EventHandler<MouseEvent> handler) {
		propertyOnAction.set(handler);
	}

	/**
	 * get the action property
	 * 
	 * @return
	 */
	public final EventHandler<MouseEvent> getOnAction() {
		return propertyOnAction.get();

	}
}