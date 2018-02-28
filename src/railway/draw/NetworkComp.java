package railway.draw;

import javafx.scene.Group;
import railway.network.Block;
import railway.network.Network;
import railway.network.Point;
import railway.network.Section;
import railway.network.Signal;

import java.util.ArrayList;

public class NetworkComp extends Group {
	double gap = 15;
    
	public NetworkComp(ArrayList<railway.draw.Component>  components){
        this.getChildren().addAll(components);
    }
	
	public void plot(Network network){
		for(Point point: network.getPoints()){
			double[] start = getCoords(point.getLevel(), point.getIndex());
			double[] upper = getCoords(point.getTopHeight(), point.getIndex()+1);
			double[] lower = getCoords(point.getLevel(), point.getIndex()+1);
			PointComp pointComp = new PointComp(start, upper, lower, point.isReverse(), point.getId());
			this.getChildren().add(pointComp);
		}
		for(Signal signal: network.getSignals()){
			double[] start = getCoords(signal.getLevel(), signal.getIndex());
			SignalComp signalComp = new SignalComp(start, true, signal.getId());
			this.getChildren().add(signalComp);
		}
		for(Section section: network.getSections()){
			Block downNeigh = network.getBlock(section.getDownNeigh());
			Block upNeigh = network.getBlock(section.getUpNeigh());
			
			double[] start;
			double[] end;
			
			if(downNeigh.getClass() == Point.class){
				Point downPoint = (Point)downNeigh;
				if(downPoint.isReverse()){
					start = getCoords(downPoint.getLevel(), downPoint.getIndex());
				}else{
					if(downPoint.getmNeigh() == section.getId()){
						start = getCoords(downPoint.getTopHeight(), downPoint.getIndex()+1);
					}else{
						start = getCoords(downPoint.getLevel(), downPoint.getIndex());
					}
				}
			}else{
				start = getCoords(downNeigh.getLevel(), downNeigh.getIndex());
			}
			
			if(upNeigh.getClass() == Point.class){
				Point upPoint = (Point)upNeigh;
				if(upPoint.isReverse()){
					if(upPoint.getmNeigh() == section.getId()){
						end = getCoords(upPoint.getTopHeight(), upPoint.getIndex()-1);
					}else{
						end = getCoords(upPoint.getLevel(), upPoint.getIndex());
					}
				}else{
					end = getCoords(upPoint.getLevel(), upPoint.getIndex());
				}
			}else{
				end = getCoords(downNeigh.getLevel(), downNeigh.getIndex());
			}
		}
	}
	
	public double[] getCoords(int level, int column){
		double[] coords = new double[2];
		coords[0] = column*gap;
		coords[1] = level*gap;
		return coords;
	}
}
