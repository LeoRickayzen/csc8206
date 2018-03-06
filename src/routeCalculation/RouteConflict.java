package routeCalculation;

import java.util.ArrayList;
import java.util.HashMap;

import railway.network.Direction;
import railway.network.Network;
import railway.network.Point;
import railway.network.Route;
import railway.network.Section;
import railway.network.Signal;

public class RouteConflict {
	private ArrayList<Route> routes;
	private Network network;
	//key is a route ID and the value is a list of conflict routes ID
	private HashMap<Integer,ArrayList<Integer>> routesConflict;
	//key is a route ID and the value is a list of blocks ID (sections and points)
	private HashMap<Integer,ArrayList<Integer>> routePath;
	//key is a route ID and the value is a list of pointID+status (plus or minus)
	private HashMap<Integer,ArrayList<String>> routePointsSetting;
	
	//constructor
	public RouteConflict(ArrayList<Route> routes,Network network) {
		this.routes=routes;
		this.network=network;
		this.routesConflict=new HashMap<Integer,ArrayList<Integer>>(); 
		this.routePath=new HashMap<Integer,ArrayList<Integer>>();
		this.routePointsSetting=new HashMap<Integer,ArrayList<String>>();
	}
	
	/**
     * calculate the conflicts between all routes
     *
     * @return    all routes along with a list of its conflicts
     */
	public HashMap<Integer,ArrayList<Integer>> calculateConflictRoute() {
		for(int i = 0; i<routes.size(); i++) {
		//take a route to compare its blocks with blocks of other routes
		Route route = routes.get(i);
		int currentRouteID=route.getRouteID();
		ArrayList<Integer> conflicts=new ArrayList<Integer>();
		
		for(Route secondRoute:this.routes) {
			if(currentRouteID != secondRoute.getRouteID()) {
				for(int blockID:routes.get(i).getBlocks()) {
					//if currentRoute shares a part (Section or Point) of the path with secondRoute
					if(secondRoute.getBlocks().contains(blockID)) {
						//checks if this block is of type section or point
						if(network.getBlock(blockID).getClass().equals(Section.class) || (network.getBlock(blockID).getClass().equals(Point.class)) ) { 
						if(!conflicts.contains(secondRoute.getRouteID()))
						conflicts.add(secondRoute.getRouteID());
						break;
						}
					}
				}
				
			}
		}
		routesConflict.put(currentRouteID, conflicts);
		}
		return routesConflict;
	}
	/**
     * calculate the path of all routes
     *
     * @return    all routes with all blocks on their path
     */
	public HashMap<Integer,ArrayList<Integer>> calculatePath() {
		for (Route route : routes)
		{
			int currentRouteID = route.getRouteID();
			ArrayList<Integer> path = new ArrayList<Integer>();
			for (int blockID : route.getBlocks())
			{
				//the path of the route is all the sections and points from source to destination
				//checks if this block is of type section or point
				if (network.getBlock(blockID).getClass().equals(Section.class) || (network.getBlock(blockID).getClass().equals(Point.class)))
				{
					if (!path.contains(blockID))
						path.add(blockID);
				}
			}

			routePath.put(currentRouteID, path);
		}
		return routePath;
	}

}
