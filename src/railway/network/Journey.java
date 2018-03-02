package railway.network;

import java.util.ArrayList;
import java.util.Arrays;

public class Journey {
	private ArrayList<Route> routes;
	
	//Constructor
	public Journey() {
		this.routes=new ArrayList<>();	
	}
		
    /**
     * 
     * @param routes A list of {@link Route Routes} in which the destination of each {@link Route} should be the same as the source of the next {@link Route}.
     * @throws IllegalArgumentException if the list of {@link Route Routes} provided is not connected.
     */
	public Journey(ArrayList<Route> routes) throws IllegalArgumentException {
		setRoutes(routes);
	}
	
	public Journey(Route ... routes) {
		ArrayList<Route> routesList = new ArrayList<Route>(Arrays.asList(routes));
		setRoutes(routesList);
	}
	
	/**
     * <p>Set the {@link Route Routes} from a given list, ensuring each {@link Route} is connected to the next.</p>
     *
     * @param  routes  List of {@link Route Routes} to set.
     * @throws IllegalArgumentException if the destination of a {@link Route} does not match the source of the next.
     */
	public void setRoutes(ArrayList<Route> routes) throws IllegalArgumentException {
		//For each route other than the last
		for(int i = 0; i < routes.size()-1; i++) {
			//Check that the destination of each route is the same as the source of the next route.
			if(routes.get(i).getDestination().getId() != routes.get(i+1).getSource().getId()) {
				throw new IllegalArgumentException("Destination of Route " + routes.get(i).getRouteID() + " is not the same as source of Route " + routes.get(i+1).getRouteID());
			}
		}
		
		//All routes connected in order so set the routes.
		this.routes=routes;
	}
	
	/**
     * get routes list
     *
     * @return    a list of routes
     */
	public ArrayList<Route> getRoutes() {
		return this.routes;
	}
	
	/**
     * <p>Add a new {@link Route} to the end of the Journey.</p>
     *
     * @param  {@link Route} to add.
     * @return true if it is added successfully
     * @throws IllegalArgumentException if the new {@link Route}'s source does not match the destination of the last {@link Route} in the list.
     */
	public boolean addRouteToJourneyEnd(Route route) throws IllegalArgumentException {
		if(route.getSource().getId() != routes.get(routes.size()-1).getDestination().getId()) {
			throw new IllegalArgumentException("Source of new Route does not match the destination of the last Route in Journey");
		}
		return this.routes.add(route);
	}
	
	/**
	 * <p>Add a new {@link Route} to the start of the Journey.</p>
	 * 
	 * @param route {@link Route} to add.
	 * @throws IllegalArgumentException if the new {@link Route}'s destination does not match the source of the first {@link Route} in the list.
	 */
	public void addRouteToJourneyStart(Route route) throws IllegalArgumentException {
		if(route.getDestination().getId() != routes.get(0).getSource().getId()) {
			throw new IllegalArgumentException("Destination of new Route does not match the source of the first Route in Journey");
		}
		this.routes.add(0, route);
	}
	
	/**
     * <p>Remove {@link Route} from end of Journey.</p>
     */
	public void removeRouteFromJourneyEnd() {
		this.routes.remove(routes.size()-1);
	}
	
	/**
	 * <p>Remove {@link Route} from the start of the Journey.</p>
	 */
	public void removeRouteFromJourneyStart() {
		this.routes.remove(0);
	}
}
