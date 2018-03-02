package railway.draw;

import javafx.scene.Group;
import javafx.scene.Node;
import railway.network.Block;
import railway.network.Network;
import railway.network.Point;
import railway.network.Section;
import railway.network.Signal;

import java.util.ArrayList;

public class NetworkComp extends Group {
	double gap = 50;
	double xstart = 100;
	double ystart = 200;
    
	public NetworkComp(){
    }
	
	/**
	 * Draws a track from it's down neighbor to it's up neighbor
	 * 
	 * @param section
	 * @param network
	 * @return
	 */
	public TrackSection drawTrack(Section section, Network network){
		Block downNeigh = network.getBlock(section.getDownNeigh());
		Block upNeigh = network.getBlock(section.getUpNeigh());
		
		double[] start = new double[2];
		double[] end = new double[2];
		
		if(downNeigh != null){
			if(downNeigh.getClass() == Point.class){
				Point downPoint = (Point)downNeigh;
				if(downPoint.isReverse()){
					start = getCoords(downPoint.getLevel(), downPoint.getIndex());
				}else{
					if(downPoint.getmNeigh() == section.getId()){
						start = ((PointComp)getCompById(downPoint.getId())).getUpper();
					}else{
						start = ((PointComp)getCompById(downPoint.getId())).getLower();
					}
				}
			}else{
				start = getCoords(downNeigh.getLevel(), downNeigh.getIndex());
			}
		}else{
			start = getCoords(section.getLevel(), section.getIndex());
		}
		
		if(upNeigh!= null){
			if(upNeigh.getClass() == Point.class){
				Point upPoint = (Point)upNeigh;
				if(upPoint.isReverse()){
					if(upPoint.getmNeigh() == section.getId()){
						end = ((PointComp)getCompById(upPoint.getId())).getUpper();
					}else{
						start = ((PointComp)getCompById(upPoint.getId())).getLower();
					}
				}else{
					end = getCoords(upPoint.getLevel(), upPoint.getIndex());
				}
			}else{
				end = getCoords(upNeigh.getLevel(), upNeigh.getIndex());
			}
		}else{
			end = getCoords(section.getLevel(), section.getIndex()+1);
		}
		return new TrackSection(start, end, section.getId());
	}
	
	/**
	 * plot a given network onto this component
	 * 
	 * @param network
	 */
	public void plot(Network network){
		for(Point point: network.getPoints()){
			double[] start;
			double[] upper;
			double[] lower;
			if(!point.isReverse()){
				start = getCoords(point.getLevel(), point.getIndex());
				upper = getCoords(point.getTopHeight(), point.getIndex()+1);
				lower = getCoords(point.getLevel(), point.getIndex()+1);
			}else{
				start = getCoords(point.getLevel(), point.getIndex());
				upper = getCoords(point.getTopHeight(), point.getIndex()-1);
				lower = getCoords(point.getLevel(), point.getIndex()-1);
			}
			PointComp pointComp = new PointComp(start, upper, lower, point.isReverse(), point.getId());
			this.getChildren().add(pointComp);
			System.out.println("id: " + point.getId() + "start: " + start[0] + ',' + start[1] + "upper: " + upper[0] + ',' + upper[1] + "lower: " + lower[0] + ',' + lower[1]);
		}
		for(Signal signal: network.getSignals()){
			//if signal has down neighbor that is a point set the index to be the point index+1
			if(signal.getDownNeigh() != 0 && network.getBlock(signal.getDownNeigh()).getClass() == Point.class){
				Point point = (Point)(network.getBlock(signal.getDownNeigh()));
				signal.setIndex(point.getIndex()+1);
			}
			//if signal has up neighbor that is a point, set the index to be the point index-1
			if(signal.getUpNeigh() != 0 && network.getBlock(signal.getUpNeigh()).getClass() == Point.class){
				Point point = (Point)(network.getBlock(signal.getUpNeigh()));
				signal.setIndex(point.getIndex()-1);
			}
			double[] start = getCoords(signal.getLevel(), signal.getIndex());
			Boolean reverse = signal.getDirection() == "DOWN";
			SignalComp signalComp = new SignalComp(start, reverse, signal.getId());
			this.getChildren().add(signalComp);
		}
		for(Section section: network.getSections()){	
			TrackSection trackSection = drawTrack(section, network);
			this.getChildren().add(trackSection);
		}
	}
	
	/**
	 * given a grid position return the actual coordinates
	 * 
	 * @param level
	 * @param column
	 * @return the coordinates in the form [x, y]
	 */
	public double[] getCoords(int level, int column){
		double[] coords = new double[2];
		coords[0] = column*gap + xstart;
		coords[1] = level*-gap + ystart;
		return coords;
	}
	
	/**
	 * get a component by it's id
	 * 
	 * @param id
	 * @return the component
	 */
	public Component getCompById(int id){
		for(Node node: this.getChildren()){
			Component component = (Component)node;
			if(component.getCompId() == id){
				return component;
			}
		}
		return null;
	}
}
