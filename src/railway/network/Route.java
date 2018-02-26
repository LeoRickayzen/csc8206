package railway.network;

import java.util.ArrayList;

public class Route {
	private int routeID;
	private Signal source;
	private Signal destination;
	private ArrayList<Block> blocks;
	private Network network;
	
	//Constructor
	public Route(int routeID, Signal source, Signal destination, ArrayList<Block> blocks, Network network) {
		this.routeID=routeID;
		this.source=source;
		this.destination=destination;
		this.blocks=blocks;
		this.network = network;
	}
	
	/**
	 * <p>Calculate the blocks needed to reach the destination from the source.</p>
	 */
	private void calculateRoute() {
		//If the direction is up, set direction up, else set down.
		if(source.getDownNeigh() == 0) {
			setBlocks(calcNextNeighbour(source, Direction.UP, 0, getBlocks()));
		}
		else {
			setBlocks(calcNextNeighbour(source, Direction.DOWN, 0, getBlocks()));
		}
	}
	
	/**
	 * <p>Searched the whole network for a route to the destination.</p>
	 * 
	 * @param previousNeighbour The block which called this method. Initially this is the source.
	 * @param direction The direction of travel when searching. Should be determined by the sources null neighbour.
	 * @param from The ID of the Block before the previousNeighbour. Should be 0 to start.
	 * @param theRoute The list of Blocks which make up the route. Pass it an empty ArrayList<Block> to start.
	 * @return An ArrayList of Blocks which make up the route from source to destination.
	 */
	private ArrayList<Block> calcNextNeighbour(Block previousNeighbour, Direction direction, int from, ArrayList<Block> theRoute) {		
		theRoute.add(previousNeighbour);
		
		//If the prev neighbour is a Section
		if(previousNeighbour.getClass().equals(Section.class)) {
			Section previousSection = (Section)previousNeighbour;
			
			//If the direction neigh is null, meaning previousSection is an endpoint, return null.
			if(getDirectionNeighbour(previousSection, direction) == 0) {
				return null;
			}
			
			Block thisBlock = network.getComp(getDirectionNeighbour(previousSection, direction));
			
			//If this block is the destination return it.
			if(thisBlock.equals(destination)) {
				theRoute.add(thisBlock);
				return theRoute;
			}
			
			//Recursively try this all again.
			return calcNextNeighbour(thisBlock, direction, previousSection.getId(), theRoute);
		}
		
		//If the previous neighbour is a Signal
		if(previousNeighbour.getClass().equals(Signal.class)) {
			Signal previousSignal = (Signal)previousNeighbour;
			
			Block thisBlock = network.getComp(getDirectionNeighbour(previousSignal, direction));
			
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
				Block thisBlock = network.getComp(previousPoint.getmNeigh());
				
				ArrayList<Block> nextBlock = calcNextNeighbour(thisBlock, direction, previousPoint.getId(), theRoute);
				
				//If the minus neighbour returned null, meaning the destination was not found, try the plus route.
				if(nextBlock == null) {
					thisBlock = network.getComp(previousPoint.getpNeigh());
				
					return calcNextNeighbour(thisBlock, direction, previousPoint.getId(), theRoute);
				}
			}
			
			//If we have come from either the plus or the minus, only try the main neigh.
			if(from == previousPoint.getmNeigh() || from == previousPoint.getpNeigh()) {
				Block thisBlock = network.getComp(previousPoint.getMainNeigh());
				
				//Recursively try this all again.
				return calcNextNeighbour(thisBlock, direction, previousPoint.getId(), theRoute);
			}
		}
		
		return null;
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
	public void setBlocks(ArrayList<Block> blocks) {
		this.blocks=blocks;
	}
	
    /**
     * get a block list
     *
     * @return    a list of block
     */
	public ArrayList<Block> getBlocks(){
		return this.blocks;
	}
	
	/**
     * add block to a route 
     *
     * @param  block  new block
     * @return true if it is added successfully
     */
	public boolean addBlockToRoute(Block block) {
		return this.blocks.add(block);
	}
	/**
     * remove block from a route 
     *
     * @param  block  existing block
     * @return true if it is removed successfully
     */
	public boolean removeBlockFromRoute(Block block) {
		return this.blocks.remove(block);
	}	

}

