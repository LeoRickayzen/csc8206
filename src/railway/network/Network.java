package railway.network;

import java.util.ArrayList;
import java.util.UUID;

//import org.codehaus.jackson.annotate.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * <p>Stores information about a railway network such as lists of all the {@link Block Blocks} and associated {@link Route Routes}.</p>
 * 
 */
public class Network {
	private ArrayList<Signal> signals;
	private ArrayList<Section> sections;
	private ArrayList<Point> points;
	private ArrayList<Route> routes;

	//Constructor
	public Network() {
		signals = new ArrayList<>();
		sections = new ArrayList<>();
		points = new ArrayList<>();
		routes = new ArrayList<>();
		
	}
		
    //Constructor
	public Network(ArrayList<Signal> signals,ArrayList<Section> sections, ArrayList<Point> points) {
		this.signals=signals;
		this.sections=sections;
		this.points=points;
		routes = new ArrayList<>();
	}
	
	/**
	 * <p>Get the first block in the list.</p>
	 *
	 * @return The first {@link Section} in the Network which is an end point.
	 */
	@JsonIgnore
	public Block getFirst(){
		for (Section section : sections){
			if (section.getDownNeigh() == 0){
				return section;
			}
		}
		return null;
	}

    /**
     * <p>Finds a component (or block) with a given ID. If it doesn't exist, returns null.</p>
     *
     * @param id ID of the block to find.
     * @return Returns the found block or null.
     */
	public Block getBlock(int id){
		//Check for the ID in the list of Sections.
		for (Section section : sections){
			if (section.getId() == id){
				return section;
			}
		}
		
		//Check for the ID in the list of Signals.
		for (Signal signal : signals){
			if (signal.getId() == id){
				return signal;
			}
		}
		
		//Check for the ID in the list of Points.
		for (Point point : points){
			if (point.getId() == id){
				return point;
			}
		}
		return null;
	}
	
	/**
     * <p>Set the list of {@link Signal Signals}.</p>
     *
     * @param  signals  new signal list
     */
	public void setSignals(ArrayList<Signal> signals) {
		this.signals=signals;
	}
	
    /**
     * <p>Get the list of {@link Signal Signals}.</p>
     *
     * @return    a list of signal
     */
	public ArrayList<Signal> getSignals(){
		return this.signals;
	}
	
	/**
     * <p>Add a {@link Signal} to the Network.</p>
     *
     * @param  signal  Signal to be added
     * @return true if it is added successfully
     */
	public boolean addSignalToNetwork(Signal signal) {
		return this.signals.add(signal);
	}
	
	/**
     * <p>Remove a {@link Signal} from the Network.</p>
     *
     * @param  signal  Existing Signal to be removed from the Network
     * @return true if it is removed successfully
     */
	public boolean removeSignalFromNetwork(Signal signal) {
		return this.signals.remove(signal);
	}
	
	/**
     * <p>Set the list of {@link Section Sections}.</p>
     *
     * @param  sections  new section list
     */
	public void setSections(ArrayList<Section> sections) {
		this.sections=sections;	
	}
	
	/**
     * <p>Get the list of {@link Section Sections}.</p>
     *
     * @return    a list of Sections
     */
	public ArrayList<Section> getSections(){
		return this.sections;
	}
	
	/**
     * <p>Add a {@link Section} to the Network.</p>
     *
     * @param  section  Section to be added
     * @return true if it is added successfully
     */
	public boolean addSectionToNetwork(Section section) {
		return this.sections.add(section);
	}
	
	/**
     * <p>Remove a {@link Section} from the Network.</p>
     *
     * @param  section  Existing Section to be removed from the Network
     * @return true if it is removed successfully
     */
	public boolean removeSectionFromNetwork(Section section) {
		return this.sections.remove(section);
	}
	
	/**
     * <p>Set the list of {@link Point Points}.</p>
     *
     * @param  points  new Point list
     */
	public void setPoints(ArrayList<Point> points) {
		this.points=points;
	}
	
	/**
     * <p>Get the list of {@link Point Points}.</p>
     *
     * @return    a list of Points
     */
	public ArrayList<Point> getPoints(){
		return this.points;
	}
	
	/**
     * <p>Add a {@link Point} to the Network.</p>
     *
     * @param  point  Point to be added
     * @return true if it is added successfully
     */
	public boolean addPointToNetwork(Point point) {
		return this.points.add(point);
	}
	
	/**
     * <p>Remove a {@link Point} from the Network.</p>
     *
     * @param  point  Existing Point to be removed from the Network
     * @return true if it is removed successfully
     */
	public boolean removePointFromNetwork(Point point) {
		return this.points.remove(point);
	}

	/**
	 * <p>Returns a list of IDs of endpoints of the network.</p>
	 *
	 * @return ArrayList of endpoint IDs.
	 */
	@JsonIgnore
	public ArrayList<Integer> getEndpoints(){
		ArrayList<Integer> endpoints = new ArrayList<Integer>();

		//For each section
		for(Section s : sections) {
			//If either neighbour is null
			if(s.getUpNeigh() == 0 || s.getDownNeigh() == 0) {
				endpoints.add(s.getId());
			}
		}

		return endpoints;
	}
	
	/**
	 * <p>Returns a list of all the {@link Route Routes} created on this Network.</p>
	 * 
	 * @return List of Routes
	 */
	@JsonIgnore
	public ArrayList<Route> getRoutes() {
		return routes;
	}
	
	/**
	 * <p>Adds a new {@link Route} to the list of Routes in this Network, so long as its ID is unique.</p>
	 * 
	 * @param newRoute The Route to be added.
	 * @return True if the Route has been added. False if a Route with that ID already exists in this Network.
	 */
	@JsonIgnore
	public boolean addRoute(Route newRoute) {
		//Check the Route ID doesn't already exist.
		for(Route route : routes) {
			if(route.getRouteID() == newRoute.getRouteID()) {
				return false;
			}
		}
		return routes.add(newRoute);
	}

	/**
	 * <p>Used by editor to find any upward end point regardless of type.</p>
	 * 
	 * @return array list of endpoint ids
	 */
    @JsonIgnore
    public ArrayList<Integer> getUpEndpoints(){
		ArrayList<Integer> endpoints = new ArrayList<Integer>();

		//For each Section
		for(Section s : sections) {
			//If up neighbour is null
			if(s.getUpNeigh() == 0) {
				endpoints.add(s.getId());
			}
		}
		
		//For each Point, if the up neighbour is null, add it to the endpoints list.
		for (Point p: points)
		{
			if(p.getmNeigh() == 0 || p.getpNeigh() == 0)
			{
				endpoints.add(p.getId());
			}
			else if(p.isReverse() && p.getMainNeigh() == 0)
			{
				endpoints.add(p.getId());
			}
		}
		
		//For each Signal, if the up neighbour is null, add it to the endpoints list.
		for (Signal s: signals)
		{
			if(s.getUpNeigh() == 0)
			{
				endpoints.add(s.getId());
			}
		}

		return endpoints;
	}

	/**
	 * <p>Returns the next available ID through auto incrementing.</p>
	 *
	 * @return int of last ID + 1.
	 */
	@JsonIgnore
	public Integer getNextId(){
	    int max = 0;
		for(Block b : new ArrayList<Block>(){{addAll(points); addAll(sections); addAll(signals);}}) {
			if(b.getId() > max)
            {
                max = b.getId();
            }
		}

		return max + 1;
	}

	/**
	 * <p>Returns the next available ID through auto incrementing.</p>
	 *
	 * @return int of last ID + 1 or null if not found.
	 */
	@JsonIgnore
	public Block getBlockById(int id){
		for(Block b : new ArrayList<Block>(){{addAll(points); addAll(sections); addAll(signals);}}) {
			if(b.getId() == id){
			    return b;
            }
		}
		return null;
	}

	/**
	 * @return String containing information on how many of each {@link Block} type there is
	 */
	public String toString()
    {
        return "Points: " + this.getPoints().size() + ", Sections: " + this.getSections().size() + ", Signals: " + this.getSignals().size();
    }
}
