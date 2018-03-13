package railway.network;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * <p>A Journey is a collection of {@link Route Routes} which are connected to each other in sequence.</p>
 * 
 * <p>This class ensures any Routes added to a Journey are adjoining.</p>
 */
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
	
	/**
	 * 
	 * @param routes An array of {@link Route Routes} in which the destination of each Route should be the same as the source of the next Route.
	 * @throws IllegalArgumentException if the array of Routes provided is not conencted.
	 */
	public Journey(Route ... routes) throws IllegalArgumentException {
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
			//Check that the destination of each route is the same as the source of the next route. Exception if not.
			if(routes.get(i).getDestination().getId() != routes.get(i+1).getSource().getId()) {
				throw new IllegalArgumentException("Destination of Route " + routes.get(i).getRouteID() + " is not the same as source of Route " + routes.get(i+1).getRouteID());
			}
		}
		
		//All routes connected in order so set the routes.
		this.routes=routes;
	}
	
	/**
     * <p>Get the list of {@link Route Routes} that make up this Journey.</p>
     *
     * @return a list of Routes
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
		//If the source of the new Route does not match the destination of the last in the Journey, throw an exception.
		if(route.getSource().getId() != routes.get(routes.size()-1).getDestination().getId()) {
			throw new IllegalArgumentException("Source of new Route does not match the destination of the last Route in Journey");
		}
		
		//All good so add the new Route to the end of the Journey.
		return this.routes.add(route);
	}
	
	/**
	 * <p>Add a new {@link Route} to the start of the Journey.</p>
	 * 
	 * @param route {@link Route} to add.
	 * @throws IllegalArgumentException if the new {@link Route}'s destination does not match the source of the first {@link Route} in the list.
	 */
	public void addRouteToJourneyStart(Route route) throws IllegalArgumentException {
		//If the destination of the new route does not match the source of the first in the Journey, throw an exception.
		if(route.getDestination().getId() != routes.get(0).getSource().getId()) {
			throw new IllegalArgumentException("Destination of new Route does not match the source of the first Route in Journey");
		}
		
		//All good so add the new Route to the start of the Journey.
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
