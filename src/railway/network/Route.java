package railway.network;

import java.util.ArrayList;
import java.util.HashMap;

import railway.validation.NetValidation;
import routeCalculation.RouteConflict;

/**
 * <p>Class to store and calculate Route information on a given {@link Network}.</p>
 * 
 *
 */
public class Route {
	private int routeID;
	private Signal source;
	private Signal destination;
	private ArrayList<Integer> blocks;
	private Network network;
	private Direction direction;

	/**
	 * Constructor
     *
	 * @param routeID The route ID
	 * @param source The source
	 * @param destination The destination
	 * @param network The whole network
	 *
	 * @throws IllegalArgumentException if no route can be calculated or if Route ID already exists or {@link Network} is null or source/destination IDs don't refer to real Signal.
	 */
	public Route(int routeID, int source, int destination, Network network) throws IllegalArgumentException {
		this.routeID = routeID;
		
		//Ensure the Network is not null before doing anything else.
		if(network == null){
			throw new IllegalArgumentException("must be a network for there to be a route");
		}else{
			//Check that the given source ID refers to a Signal in this Network. Throw exception otherwise.
			if(network.getBlock(source) != null && NetValidation.isSignal(source, network)) {
				this.source = (Signal)network.getBlock(source);
			}
			else {
				throw new IllegalArgumentException("ID given for source of Route does not refer to a Signal in this Network.");
			}
			
			//Check that the given destination ID refers to a Signal in this Network. Exception otherwise.
			if(network.getBlock(destination) != null && NetValidation.isSignal(destination, network)) {
				this.destination = (Signal)network.getBlock(destination);
			}
			else {
				throw new IllegalArgumentException("ID given for destination of Route does not refer to a Signal in this Network.");
			}
			
			this.network = network;
			
			//Calculate the Route.
			calculateRoute();
			
			//If no route could be calculated throw an exception.
			if(blocks == null) {
				throw new IllegalArgumentException("No Route can be calculated from " + source + " to " + destination);
			}
			
			//If the given ID already exists in the master list of Routes in the Network, throw an exception.
			if(!network.addRoute(this)) {
				throw new IllegalArgumentException("A Route with this ID (" + routeID + ") already exists on this Network.");
			}
		}
	}
	
	/**
	 * <p>Calculate the blocks needed to reach the destination from the source.</p>
	 * 
	 * <p>Controller method for the recursive calcNextNeighbour() method.</p>
	 */
	public void calculateRoute() {
		//Try looking down the network, if no route found, look up the network. If no routes are found either way, route will be null.	
		ArrayList<Integer> tempRoute = calcNextNeighbour(source, Direction.DOWN, 0, new ArrayList<>());
		
		if(tempRoute == null) {
			setBlocks(calcNextNeighbour(source, Direction.UP, 0, new ArrayList<>()));
			direction = Direction.UP;
		}
		else {
			setBlocks(tempRoute);
			direction = Direction.DOWN;
		}
	}
	
	/**
	 * <p>Search the whole network for a route to the destination from the source.</p>
	 * 
	 * <p>Recursive function to find a Route. Returns null if no route can be found or a list of blocks if a Route can be found.</p>
	 * 
	 * <p>Finds the first Route from source to destination, with preference to plus neighbours on {@link Point Points}.</p>
	 * 
	 * @param previousNeighbour The block which called this method. Initially this is the source.
	 * @param direction The direction of travel when searching. Should be determined by the sources null neighbour.
	 * @param from The ID of the Block before the previousNeighbour. Should be 0 to start.
	 * @param theOldRoute The list of Blocks which make up the route. Pass it an empty ArrayList<Block> to start.
	 * @return An ArrayList of Blocks which make up the route from source to destination.
	 */
	private ArrayList<Integer> calcNextNeighbour(Block previousNeighbour, Direction direction, int from, ArrayList<Integer> theOldRoute) {
		//Copy the route so far into a new list, so if we have to fall back to a point the blocks in the wrong direction are not included.
		ArrayList<Integer> theRoute = new ArrayList<>(theOldRoute);
		
		//Stop the route going round in circles if there is a cyclic network. This is here for one of the validation calls.
		if(theRoute.contains(previousNeighbour.getId())) {
			return null;
		}
		
		//Add the current block to the list of blocks for the route.
		theRoute.add(previousNeighbour.getId());
		
		//If the previous neighbour is a Section
		if(previousNeighbour.getClass().equals(Section.class)) {
			Section previousSection = (Section)previousNeighbour;
			
			//If the direction neighbour is null, meaning previousSection is an endpoint, return null.
			if(getDirectionNeighbour(previousSection, direction) == 0) {
				return null;
			}
			
			//Get the neighbouring block from the direction we are searching in.
			Block thisBlock = network.getBlock(getDirectionNeighbour(previousSection, direction));
			
			//If this block is the destination, add it to the list and return it.
			if(thisBlock.getId() == destination.getId()) {
				theRoute.add(thisBlock.getId());
				return theRoute;
			}
			
			//Recursively try this all again. We haven't found the destination yet so keep looking.
			return calcNextNeighbour(thisBlock, direction, previousSection.getId(), theRoute);
		}
		
		//If the previous neighbour is a Signal
		if(previousNeighbour.getClass().equals(Signal.class)) {
			Signal previousSignal = (Signal)previousNeighbour;
			
			Block thisBlock = network.getBlock(getDirectionNeighbour(previousSignal, direction));
			
			//If this block is the destination, add it to the list and return it.
			if(thisBlock.getId() == destination.getId()) {
				theRoute.add(thisBlock.getId());
				return theRoute;
			}
			
			//Recursively try this all again.
			return calcNextNeighbour(thisBlock, direction, previousSignal.getId(), theRoute);
		}
		
		//If the previous neighbour is a Point
		if(previousNeighbour.getClass().equals(Point.class)) {
			//LOGIC FOR NAVIGATING POINTS
			Point previousPoint = (Point)previousNeighbour;
			
			//if we have come from the main neighbour, try the plus and minus paths.
			if(from == previousPoint.getMainNeigh()) {
				//Try the plus neighbour route.
				Block thisBlock = network.getBlock(previousPoint.getpNeigh());
				
				//If this block is the destination, add it to the list and return it.
				if(thisBlock.getId() == destination.getId()) {
					theRoute.add(thisBlock.getId());
					return theRoute;
				}
				
				//Look on the plus route first.
				ArrayList<Integer> nextBlock = calcNextNeighbour(thisBlock, direction, previousPoint.getId(), theRoute);
				
				//If the plus neighbour returned null, meaning the destination was not found, try the minus route.
				if(nextBlock == null) {
					thisBlock = network.getBlock(previousPoint.getmNeigh());
					
					//If this block is the destination, add it to the list return it.
					if(thisBlock.getId() == destination.getId()) {
						theRoute.add(thisBlock.getId());
						return theRoute;
					}
					return calcNextNeighbour(thisBlock, direction, previousPoint.getId(), theRoute);
				}
				//Return the route we found from the plus neighbour.
				return nextBlock;
			}
			
			//If we have come from either the plus or the minus, only try the main neigh.
			if(from == previousPoint.getmNeigh() || from == previousPoint.getpNeigh()) {
				Block thisBlock = network.getBlock(previousPoint.getMainNeigh());
				
				//Recursively try this all again.
				return calcNextNeighbour(thisBlock, direction, previousPoint.getId(), theRoute);
			}
		}
		
		return theRoute;
	}
	
	/**
	 * <p>Set the {@link Point} and {@link Signal} interlocking settings.</p>
	 * 
	 * <p>Uses the interlocking tables generated by {@link RouteConflict} to set Points and Signals.</p>
	 * 
	 * 
	 */
	public void setInterlocking() {
		//RouteConflict takes in a list of Routes, so create a list with just this Route and create an instance of RouteConflict.
		ArrayList<Route> routes = new ArrayList<Route>();
		routes.add(this);
		RouteConflict routeConflict = new RouteConflict(routes, network);
		
		//Get the interlocking settings for all the Points and Signals in this Route.
		HashMap<Integer, ArrayList<String>> pointSettings = routeConflict.calculatePointsSetting();
		HashMap<Integer, ArrayList<Integer>> signalSettings = routeConflict.calculateSignal();
		
		//For each point, get it's interlocking setting and set it to that.
		for(String pointConfig : pointSettings.get(routeID)) {
			//Get the :m or :p from the point info
			String setting = pointConfig.substring(pointConfig.length()-1, pointConfig.length());
			
			//Get the Point ID from the point info.
			int pointID = Integer.parseInt(pointConfig.substring(0, pointConfig.length()-2));
			
			//Get the Point from the above ID.
			Point point = (Point)network.getBlock(pointID);
			
			//Based on the above information, set the Point accordingly.
			if(setting.equals("p")) {
				point.setPlus(true);
			}
			else{
				point.setPlus(false);
			}
		}
		
		//For each Signal in the Route controlling the same direction as this Route, set it to clear.
		//Because the Signals listed from RouteConflict are only the ones which should be set to STOP, we must set any on our Route to CLEAR
		//if they control the Routes direction of travel.
		for(Integer blockID : getBlocks()) {
			Block block = network.getBlock(blockID);
			
			//If the current Block is a Signal, save it into a Signal variable.
			if(block.getClass().equals(Signal.class)) {
				Signal signal = (Signal)block;
				
				//If the Signal controls the Routes direction of travel, set it to CLEAR.
				if(signal.getDirectionEnum().equals(direction)) {
					signal.setClear(true);
				}
			}
		}
		
		//For each signal in the list for interlocking, set it STOP.
		for(Integer signalConfig : signalSettings.get(routeID)) {
			Signal signal = (Signal)network.getBlock(signalConfig);
			signal.setClear(false);
		}
	}
	
	/**
	 * <p>Get the up or down neighbour of a {@link Section}, dependent on provided {@link Direction}.</p>
	 * 
	 * @param section The Section to provide a neighbour of.
	 * @param direction The direction of the desired neighbour.
	 * @return an int ID of a neighbour.
	 */
	private int getDirectionNeighbour(Section section, Direction direction) {
		if(direction == Direction.UP) {
			return section.getUpNeigh();
		}
		return section.getDownNeigh();
	}
	
	/**
	 * <p>Get the up or down neighbour of a {@link Signal}, dependent on provide {@link Direction}.</p>
	 * 
	 * @param signal The Signal to provide a neighbour of.
	 * @param direction The direction of the desired neighbour.
	 * @return an int ID of a neighbour.
	 */
	private int getDirectionNeighbour(Signal signal, Direction direction) {
		if(direction == Direction.UP) {
			return signal.getUpNeigh();
		}
		return signal.getDownNeigh();
	}
	
	/**
	 * <p>Set the Route's ID.</p>
	 * @param routeID int to set as the ID of this Route.
	 */
	public void setRouteID(int routeID) {
		  this.routeID=routeID;
		  
	  }
	  
	/**
	 * <p>Get the Route's ID.</p>
	 * @return int ID of this Route.
	 */
	public int getRouteID() {
		  return routeID;
	 }
	
    /**
     * <p>Get the Route's source.</p>
     *
     * @return Signal which is the Route's source
     */
	public Signal getSource() {
		return this.source;
	}
	
	/**
     * <p>Get the Route's destination.</p>
     *
     * @return Signal which is the Route's destination
     */
	public Signal getDestination() {
		return this.destination;
	}
	
	/**
     * set a block list
     *
     * @param  blocks  new block list
     */
	private void setBlocks(ArrayList<Integer> blocks) {
		this.blocks=blocks;
	}
	
    /**
     * <p>Get the list of Blocks in the Route.</p>
     *
     * @return    a list of blocks
     */
	public ArrayList<Integer> getBlocks(){
		return this.blocks;
	}

	/**
	 * <p>Get the direction that this Route goes in to get to the destination.</p>
	 * 
	 * @return {@link Direction} of travel for this Route.
	 */
	public Direction getDirection() {
		return direction;
	}
}

