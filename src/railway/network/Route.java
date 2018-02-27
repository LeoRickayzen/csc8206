package railway.network;

import java.util.ArrayList;

public class Route {
	private int routeID;
	private Signal source;
	private Signal destination;
	private ArrayList<Block> blocks;
	
	//Constructor
	public Route(int routeID, Signal source, Signal destination,ArrayList<Block> blocks) {
		this.routeID=routeID;
		this.source=source;
		this.destination=destination;
		this.blocks=blocks;
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

