package railway.draw;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
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

	public Component(int id, Block block, LayoutController layoutController){
		this.compid = id;
		this.block = block;
		this.layoutController = layoutController;

        this.setOnMouseClicked(event -> onActionProperty().get().handle(event));
    }
	
	public int getLevel(){
		return level;
	}
	
	public void setLevel(int level){
		this.level = level;
	}
	
	public int getCompId(){
		return compid;
	}
	
	public void setId(int compid){
		this.compid = compid;
	}

	//Making component clickable
	private ObjectProperty<EventHandler<MouseEvent>> propertyOnAction = new SimpleObjectProperty<>();

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
                    Driver.showErrorMessage(e, "Could not load editor.");
                }
            }
            else
            {
                Driver.showErrorMessage(new UnsupportedOperationException("Can only alter end points."), "Not Supported");
            }
        }

		return propertyOnAction;
	}

	public final void setOnAction(EventHandler<MouseEvent> handler) {
		propertyOnAction.set(handler);
	}

	public final EventHandler<MouseEvent> getOnAction() {
		return propertyOnAction.get();

	}
}
