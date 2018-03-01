package railway.network;

import java.util.ArrayList;

public class Journey {
	private ArrayList<Route> routes;
	
	//Constructor
	public Journey() {
		this.routes=new ArrayList<>();
		
		}
		
    //Constructor
	public Journey(ArrayList<Route> routes) {
		this.routes=routes;
		
		}
	/**
     * set  routes list
     *
     * @param  routes  new routes list
     */
	public void setRoutes(ArrayList<Route> routes) {
		this.routes=routes;
	}
	/**
     * get routes list
     *
     * @return    a list of routes
     */
	public ArrayList<Route> getRoutes(){
		return this.routes;
	}
	/**
     * add route to a journey 
     *
     * @param  route  new route
     * @return true if it is added successfully
     */
	public boolean addRoutesToJourney(Route route) {
		return this.routes.add(route);
	}
	
	/**
     * remove route from journey 
     *
     * @param  route  existing route
     * @return true if it is removed successfully
     */
	public boolean removeRouteFromJourney(Route route) {
		return this.routes.remove(route);
	}
	

}
