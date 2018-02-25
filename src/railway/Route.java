
public class Route {
	int routeID;
	Signal source;
	Signal destination;
	
	//Constructor
	public Route(Signal source, Signal destination) {
		this.source=source;
		this.destination=destination;	
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
	
	
	
	

}
