package railway.draw;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import railway.file.RailwayFile;
import railway.network.Network;
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
            
            RailwayFile testFile = new RailwayFile("res/testNetwork.json");
            Network n2 = testFile.read();
            
            Route r1=new Route(1,2,15,n2);
            Route r2=new Route(2,2,10,n2);
            Route r3=new Route(3,2,8,n2);
            Route r4=new Route(4,5,8,n2);
            Route r5=new Route(5,6,15,n2);
            
            for(Signal s:n2.getSignals()) {
            	if(s.getId()==12) {
            	System.out.println("newtwork " + s.getId()+" "+s.getDirection());
            	s.setDirection("down");
            	System.out.println("newtwork " + s.getId()+" "+s.getDirection());
            	}else {
            		System.out.println("newtwork " + s.getId()+" "+s.getDirection());
                	s.setDirection("up");
                	System.out.println("newtwork " + s.getId()+" "+s.getDirection());
            		
            	}
            }
            r1.calculateRoute();
            System.out.println("r1 " + r1.getBlocks());
            r2.calculateRoute();
            System.out.println("r2 " + r2.getBlocks());
            r3.calculateRoute();
            System.out.println("r3 " + r3.getBlocks());
            r4.calculateRoute();
            System.out.println("r4 " + r4.getBlocks());
            r5.calculateRoute();
            System.out.println("r5 " + r5.getBlocks());
            
            ArrayList<Route> routeList= new ArrayList<>();
            routeList.add(r1);
            routeList.add(r2);
            routeList.add(r3);
            routeList.add(r4);
            routeList.add(r5);
            System.out.println("conflict stuff...");
            RouteConflict rConflict=new RouteConflict(routeList,n2);
            HashMap<Integer,ArrayList<Integer>> conflictList = rConflict.calculateConflictRoute();
            System.out.println("Conflict list: "+conflictList);
            

            HashMap<Integer,ArrayList<String>> pointSetting=new HashMap<Integer,ArrayList<String>>(); 
            pointSetting=rConflict.calculatePointsSetting();
            System.out.println("point setting list: "+pointSetting);
            HashMap<Integer,ArrayList<Integer>> paths=new HashMap<Integer,ArrayList<Integer>>(); 
            paths=rConflict.calculatePath();
            
           HashMap<Integer,ArrayList<Integer>> signals=new HashMap<Integer,ArrayList<Integer>>();
           signals=rConflict.calculateSignal();
            System.out.println("Signal setting list: "+signals);

          //HashMap<Integer,ArrayList<Integer>> paths = rConflict.calculatePath();

            System.out.println("path list: "+paths);
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
