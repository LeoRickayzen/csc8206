package railway.network;

import java.util.ArrayList;

import railway.validation.NetValidation;

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
	 * @throws IllegalArgumentException if no route can be calculated
	 */
	public Route(int routeID, int source, int destination, Network network) throws IllegalArgumentException {
		this.routeID = routeID;
		
		if(NetValidation.isSignal(source, network)) {
			this.source = (Signal)network.getBlock(source);
		}
		else {
			throw new IllegalArgumentException("ID given for source of Route does not refer to a Signal in this Network.");
		}
		
		if(NetValidation.isSignal(destination, network)) {
			this.destination = (Signal)network.getBlock(destination);
		}
		else {
			throw new IllegalArgumentException("ID given for destination of Route does not refer to a Signal in this Network.");
		}
		
		this.network = network;
		calculateRoute();
		if(blocks == null) {
			throw new IllegalArgumentException("No Route can be calculated from " + source + " to " + destination);
		}
	}
	
	
	//This is broken but it doesn't even matter. Saving so we can adapt into the real calc function.
	/*
	public void doInterlocking() {
		//For each Block along the Route
		for(int i = 0; i < blocks.size(); i++) {
			int currentBlockID = blocks.get(i);
			//If the current Block is a Point
			if(network.getBlock(currentBlockID).getClass().equals(Point.class)) {
				Point thisPoint = (Point)network.getBlock(currentBlockID);
				//If the Point has the same direction as this Route
				if(thisPoint.getTravelDirection() == direction){
					//If the next block is the minus neighbour
					if(blocks.get(i+1) == thisPoint.getmNeigh()) {
						//Set the Point to minus
						thisPoint.setPlus(false);
						//GO DOWN PLUS AND FIND FIRST SIGNAL AND SET TO STOP
						Signal pNeigh = (Signal)network.getBlock(thisPoint.getpNeigh());
						pNeigh.setClear(false);
					}
					else {
						//Set the Point to plus
						thisPoint.setPlus(true);
						//GO DOWN MINUS AND FIND FIRST SIGNAL AND SET TO STOP
						Signal mNeigh = (Signal)network.getBlock(thisPoint.getmNeigh());
						mNeigh.setClear(false);
					}
				}
				else {
					//Else if the Point is in the opposite Direction of travel.
					//If the previous block was on the minus neighbour
					if(blocks.get(i-1) == thisPoint.getmNeigh()) {
						//Set the Point to plus.
						thisPoint.setPlus(true);
					}
					else {
						//Set the Point to minus
						thisPoint.setPlus(false);
					}
				}
			}
			
			//If the current Block is a Signal
			if(network.getBlock(currentBlockID).getClass().equals(Signal.class)) {
				Signal thisSignal = (Signal)network.getBlock(currentBlockID);
				//If the Signal has the same direction as this Route
				if(thisSignal.getDirectionEnum().equals(direction)) {
					//Set to clear.
					thisSignal.setClear(true);
				}
				else {
					//Else set to STOP
					thisSignal.setClear(false);
				}
			}
		}
		
		//LOOK AFTER ROUTE(UP) DESTINATION AND GET EITHER FIRST SIGNAL(DOWN) OR FIRST POINT(DOWN)
		//AND SET TO STOP/OTHER NEIGHBOUR.
		boolean found = false;
		Block nextNeighbour = network.getBlock(getDirectionNeighbour(destination, direction));
		int comeFrom = destination.getId();
		while(!found) {
			//If the next neighbour is a signal check if its the correct direction and if so set it to stop
			if(nextNeighbour.getClass().equals(Signal.class)) {
				Signal nextSignal = (Signal)nextNeighbour;
				if(nextSignal.getDirectionEnum().equals(direction.toggle())) {
					nextSignal.setClear(false);
					found = true;
				}
				else {
					comeFrom = nextNeighbour.getId();
					nextNeighbour = network.getBlock(getDirectionNeighbour(nextSignal, direction));
				}
			}
			
			//If the next neighbour is a point
			if(nextNeighbour.getClass().equals(Point.class)) {
				Point nextPoint = (Point)nextNeighbour;
				if(nextPoint.getTravelDirection().equals(direction.toggle())) {
					if(nextPoint.getmNeigh() == comeFrom) {
						nextPoint.setPlus(true);
					}
					else {
						nextPoint.setPlus(false);
					}
					found = true;
				}
				else {
					//If the point is in the same direction then just set the neighbouring signals to stop.
					Signal mNeigh = (Signal)network.getBlock(nextPoint.getmNeigh());
					Signal pNeigh = (Signal)network.getBlock(nextPoint.getpNeigh());
					
					mNeigh.setClear(false);
					pNeigh.setClear(false);
					found = true;
				}
			}
			if(nextNeighbour.getClass().equals(Section.class)) {
				comeFrom = nextNeighbour.getId();
				nextNeighbour = network.getBlock(getDirectionNeighbour((Section)nextNeighbour, direction));
			}
		}
	}*/
	
	/**
	 * <p>Calculate the blocks needed to reach the destination from the source.</p>
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
	 * <p>Searched the whole network for a route to the destination.</p>
	 * 
	 * @param previousNeighbour The block which called this method. Initially this is the source.
	 * @param direction The direction of travel when searching. Should be determined by the sources null neighbour.
	 * @param from The ID of the Block before the previousNeighbour. Should be 0 to start.
	 * @param theOldRoute The list of Blocks which make up the route. Pass it an empty ArrayList<Block> to start.
	 * @return An ArrayList of Blocks which make up the route from source to destination.
	 */
	private ArrayList<Integer> calcNextNeighbour(Block previousNeighbour, Direction direction, int from, ArrayList<Integer> theOldRoute) {
		ArrayList<Integer> theRoute = new ArrayList<>(theOldRoute);
		
		//Stop the route going round in circles if there is a cyclic network.
		if(theRoute.contains(previousNeighbour.getId())) {
			return null;
		}
		
		theRoute.add(previousNeighbour.getId());
		
		//If the prev neighbour is a Section
		if(previousNeighbour.getClass().equals(Section.class)) {
			Section previousSection = (Section)previousNeighbour;
			
			//If the direction neigh is null, meaning previousSection is an endpoint, return null.
			if(getDirectionNeighbour(previousSection, direction) == 0) {
				return null;
			}
			
			Block thisBlock = network.getBlock(getDirectionNeighbour(previousSection, direction));
			
			//If this block is the destination return it.
			if(thisBlock.getId() == destination.getId()) {
				theRoute.add(thisBlock.getId());
				return theRoute;
			}
			
			//Recursively try this all again.
			return calcNextNeighbour(thisBlock, direction, previousSection.getId(), theRoute);
		}
		
		//If the previous neighbour is a Signal
		if(previousNeighbour.getClass().equals(Signal.class)) {
			Signal previousSignal = (Signal)previousNeighbour;
			
			Block thisBlock = network.getBlock(getDirectionNeighbour(previousSignal, direction));
			
			//If this block is the destination return it.
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
				//Try the minus neighbour route.
				Block thisBlock = network.getBlock(previousPoint.getmNeigh());
				
				//If this block is the destination return it.
				if(thisBlock.getId() == destination.getId()) {
					theRoute.add(thisBlock.getId());
					return theRoute;
				}
				
				ArrayList<Integer> nextBlock = calcNextNeighbour(thisBlock, direction, previousPoint.getId(), theRoute);
				//If the minus neighbour returned null, meaning the destination was not found, try the plus route.
				if(nextBlock == null) {
					thisBlock = network.getBlock(previousPoint.getpNeigh());
					
					//If this block is the destination return it.
					if(thisBlock.getId() == destination.getId()) {
						theRoute.add(thisBlock.getId());
						return theRoute;
					}
					return calcNextNeighbour(thisBlock, direction, previousPoint.getId(), theRoute);
				}
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
	 * <p>Get the up or down neighbour of a Section, dependent on provide direction.</p>
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
	 * <p>Get the up or down neighbour of a Signal, dependent on provide direction.</p>
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
	
	public void setRouteID(int routeID) {
		  this.routeID=routeID;
		  
	  }
	  
	public int getRouteID() {
		  return routeID;
	  }
	
	/**
     * set route's source
     *
     * @param  source  new route's source
     */

	public void setSource(Signal source) {
		this.source=source;
	}
	
    /**
     * get route's source
     *
     * @return    route's source
     */
	public Signal getSource() {
		return this.source;
	}
	
	/**
     * set destination
     *
     * @param  destination  new route's destination
     */
	public void setDestination(Signal destination) {
		this.destination=destination;
	}
	/**
     * get destination
     *
     * @return    route's destination
     */
	public Signal getDestination() {
		return this.destination;
	}
	
	/**
     * set a block list
     *
     * @param  blocks  new block list
     */
	public void setBlocks(ArrayList<Integer> blocks) {
		this.blocks=blocks;
	}
	
    /**
     * get a block list
     *
     * @return    a list of block
     */
	public ArrayList<Integer> getBlocks(){
		return this.blocks;
	}
	
	/**
     * add block to a route 
     *
     * @param  block  new block
     * @return true if it is added successfully
     */
	public boolean addBlockToRoute(Block block) {
		return this.blocks.add(block.getId());
	}
	/**
     * remove block from a route 
     *
     * @param  block  existing block
     * @return true if it is removed successfully
     */
	public boolean removeBlockFromRoute(Block block) {
	    //TODO: This doesn't work
		//return this.blocks.remove(block);
        return false;
	}

	public Direction getDirection() {
		return direction;
	}

	public void setDirection(Direction direction) {
		this.direction = direction;
	}	
	
	
}

