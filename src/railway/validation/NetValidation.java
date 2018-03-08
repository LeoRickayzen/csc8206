package railway.validation;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;

import railway.network.Block;
import railway.network.Direction;
import railway.network.Network;
import railway.network.Point;
import railway.network.Route;
import railway.network.Section;
import railway.network.Signal;

/**
 * <p>Class to validate a {@link Network} against various criteria.</p>
 *
 * @author Jay Kahlil Hussaini
 * <p>Date created: 24/02/2018</p>
 * <p>Last updated: 08/03/2018</p>
 *
 */
public class NetValidation {

	/**
	 * <p>Validates a network and returns an information object with results.<p>
	 * 
	 * @param network The network to validate.
	 * @return ValidationInfo object. Call isValid() for simple boolean check. getIssues() for detailed list if invalid.
	 */
	public static ValidationInfo Validate(Network network) {
		ValidationInfo vInfo = new ValidationInfo();
		ArrayList<String> issues = new ArrayList<>();
		
		//Validate network parts and save issues if they exist.
		issues.addAll(ValidatePoints(network));
		issues.addAll(ValidateSections(network));
		issues.addAll(ValidateSignals(network));
		issues.addAll(ValidateUniqueIds(network));
		issues.addAll(ValidateUniqueNeighbours(network));
		issues.addAll(ValidateNonCyclic(network));
		
		//If there are no issues with the above checks, check that the whole network is connected.
		if(issues.isEmpty()) {
			for(Map.Entry<Integer[], Boolean> entry : validateConnectedNetwork(network).entrySet()) {
				if(!entry.getValue()) {
					issues.add("Connection between endpoints " + Arrays.toString(entry.getKey()) + " has not been made.");
				}
			}
		}
		
		//If there are no issues, the network is valid.
		if(issues.isEmpty()) {
			vInfo.setValid(true);
		}
		else { //Else if there are issues, network not valid. Save issues to info object.
			vInfo.setValid(false);
			vInfo.addIssues(issues);
		}
		
		return vInfo;
	}
	
	/**
	 * <p>Validation to ensure that there are no cycles in the {@link Network}.</p>
	 * 
	 * <p>A cyclic Network is one in which you can get from a {@link Signal} back to that same Signal whilst only travelling
	 * in one direction in the network. A Network in which you have to turn around and go down the minus neighbour of a {@link Point}
	 * after coming up the plus neighbour, for instance, is not cyclic, as that is not permitted in the {@link Route} finding.</p>
	 * 
	 * @param network The {@link Network} to validate.
	 * @return An ArrayList of issues in String format.
	 */
	private static ArrayList<String> ValidateNonCyclic(Network network){
		ArrayList<String> issues = new ArrayList<String>();
		
		//For every Signal in the Network, try and create a Route to itself.
		//If this fails for every Signal, the Network is not cyclic.
		for(Signal signal : network.getSignals()) {
			try {
				Route route = new Route(-1, signal.getId(), signal.getId(), network);
				issues.add("Network cannot be cyclic. Can create cyclic Route with Signal " + signal.getId() + ".");
			}
			catch(IllegalArgumentException e) {
				//If there is an exception it means that the network isn't cyclic. This is good.
				//If a Route cannot be created from the given source and destination IDs, an IllegalArgumentException is throw.
			}
		}
		return issues;
	}
	
	/**
	 * <p>Checks that no ID is duplicated in the network.<p>
	 * 
	 * @param network The {@link Network} to check.
	 * @return A list detailing any ID duplications
	 */
	private static ArrayList<String> ValidateUniqueIds(Network network){
		ArrayList<String> issues = new ArrayList<String>();
		HashSet<Integer> allIds = new HashSet<Integer>();
		
		//Simply chuck every ID into a Set and if any ID already exists, add an issue to the issue list.
		for(Section section : network.getSections()) {
			if(!allIds.add(section.getId())) {
				issues.add("A Block with the ID " + section.getId() + " already exists. Please change one of them.");
			}
		}
		
		for(Signal signal : network.getSignals()) {
			if(!allIds.add(signal.getId())) {
				issues.add("A Block with the ID " + signal.getId() + " already exists. Please change one of them.");
			}
		}
		
		for(Point point : network.getPoints()) {
			if(!allIds.add(point.getId())) {
				issues.add("A Block with the ID " + point.getId() + " already exists. Please change one of them.");
			}
		}
		
		return issues;
	}
	

	/**
	 * <p>Validation to ensure no two {@link Block Blocks} have the same up/down neighbour, unless that neighbour is a
	 * {@link Point} which has its plus and minus neighbours facing the two Blocks.</p>
	 * 
	 * 
	 * @param network {@link Network} to validate.
	 * @return ArrayList of issues in String format.
	 */
	private static ArrayList<String> ValidateUniqueNeighbours(Network network){
		ArrayList<String> issues = new ArrayList<String>();
		
		//Key = a Block ID. Value = a list of Blocks which have declared Key as their up/down neighbour.
		//If a Point is reversed, the up neighbour is Main and down are plus and minus. If the Point is not reversed it is the other way around.
		HashMap<Integer, ArrayList<Integer>> upNeighbours = new HashMap<Integer, ArrayList<Integer>>();
		HashMap<Integer, ArrayList<Integer>> downNeighbours = new HashMap<Integer, ArrayList<Integer>>();
		
		//For each section, add its ID to a list in a map where the key is the ID of the up/down neighbour
		for(Section section : network.getSections()) {
			//If there are already values for the up neighbour, use that list, otherwise make a new one.
			ArrayList<Integer> blocksWithSameUp;
			blocksWithSameUp = upNeighbours.get(section.getUpNeigh());
			if(blocksWithSameUp == null) {
				blocksWithSameUp = new ArrayList<Integer>();
			}
			//Add this Section's ID to the list of Blocks which have declared the up neighbour as their up neighbour.
			blocksWithSameUp.add(section.getId());
			//Put that list into the map.
			upNeighbours.put(section.getUpNeigh(), blocksWithSameUp);
			
			//As above but for the down neighbour.
			ArrayList<Integer> blocksWithSameDown;
			blocksWithSameDown = downNeighbours.get(section.getDownNeigh());
			if(blocksWithSameDown == null) {
				blocksWithSameDown = new ArrayList<Integer>();
			}
			blocksWithSameDown.add(section.getId());
			downNeighbours.put(section.getDownNeigh(), blocksWithSameDown);
		}
		
		//For each Signal, add its ID to a list in a map where the key is the ID of the up/down neighbour
		//This all works the same as the Section stuff, above.
		for(Signal signal : network.getSignals()) {
			ArrayList<Integer> blocksWithSameUp;
			blocksWithSameUp = upNeighbours.get(signal.getUpNeigh());
			if(blocksWithSameUp == null) {
				blocksWithSameUp = new ArrayList<Integer>();
			}
			blocksWithSameUp.add(signal.getId());
			upNeighbours.put(signal.getUpNeigh(), blocksWithSameUp);
			
			ArrayList<Integer> blocksWithSameDown;
			blocksWithSameDown = downNeighbours.get(signal.getDownNeigh());
			if(blocksWithSameDown == null) {
				blocksWithSameDown = new ArrayList<Integer>();
			}
			blocksWithSameDown.add(signal.getId());
			downNeighbours.put(signal.getDownNeigh(), blocksWithSameDown);
		}
		
		//For each Point in the Network
		for(Point point : network.getPoints()) {
			ArrayList<Integer> pointsWithSameUp1;
			ArrayList<Integer> pointsWithSameUp2;
			ArrayList<Integer> pointsWithSameDown1;
			ArrayList<Integer> pointsWithSameDown2;
			if(point.getTravelDirection() == Direction.UP) {
				//If point travel is up, the up neighbours are plus and minus.
				pointsWithSameUp1 = upNeighbours.get(point.getmNeigh());
				pointsWithSameUp2 = upNeighbours.get(point.getpNeigh());
				
				//If there are already values for the up neighbours, use that list, otherwise make a new one.
				if(pointsWithSameUp1 == null) {
					pointsWithSameUp1 = new ArrayList<Integer>();
				}
				
				if(pointsWithSameUp2 == null) {
					pointsWithSameUp2 = new ArrayList<Integer>();
				}
				
				//Add this Point's ID to each up neighbour list.
				pointsWithSameUp1.add(point.getId());
				pointsWithSameUp2.add(point.getId());
				
				//Put those lists in the up neighbour map for the respective up neighbour as a key.
				upNeighbours.put(point.getmNeigh(), pointsWithSameUp1);
				upNeighbours.put(point.getpNeigh(), pointsWithSameUp2);
				
				//If point travel is up, the down neighbour is main.
				//Same as above but for the down neighbour.
				pointsWithSameDown1 = downNeighbours.get(point.getMainNeigh());
				
				if(pointsWithSameDown1 == null) {
					pointsWithSameDown1 = new ArrayList<Integer>();
				}
				
				pointsWithSameDown1.add(point.getId());
				
				downNeighbours.put(point.getMainNeigh(), pointsWithSameDown1);
			}
			else {
				//Else if point travel is down, the up neigh is just the main.
				//The down neighbours are plus and minus.
				//Do the same as above but with the appropriate neighbours.
				pointsWithSameUp1 = upNeighbours.get(point.getMainNeigh());
				
				if(pointsWithSameUp1 == null) {
					pointsWithSameUp1 = new ArrayList<Integer>();
				}
				
				pointsWithSameUp1.add(point.getId());
				upNeighbours.put(point.getMainNeigh(), pointsWithSameUp1);
				
				//Else if point travel is down, the down neighbours are plus and minus.
				pointsWithSameDown1 = downNeighbours.get(point.getmNeigh());
				pointsWithSameDown2 = downNeighbours.get(point.getpNeigh());
				
				if(pointsWithSameDown1 == null) {
					pointsWithSameDown1 = new ArrayList<Integer>();
				}
				
				if(pointsWithSameDown2 == null) {
					pointsWithSameDown2 = new ArrayList<Integer>();
				}
				
				pointsWithSameDown1.add(point.getId());
				pointsWithSameDown2.add(point.getId());
				
				downNeighbours.put(point.getmNeigh(), pointsWithSameDown1);
				downNeighbours.put(point.getpNeigh(), pointsWithSameDown2);
			}
		}
		
		//Validity check up up neighbours
		//For each entry in the up neighbour map.
		for(Entry<Integer, ArrayList<Integer>> entry : upNeighbours.entrySet()) {
			//If the current list of blocks which declare the current ID as their up neighbour is 3 or larger, there is an issue.
			//Unless the ID is 0, as that means the block has no up neighbour.
			if(entry.getValue().size() >= 3 && entry.getKey() != 0) {
				issues.add("Too many Blocks declare " + network.getBlock(entry.getKey()).getClass().getSimpleName() + " " + entry.getKey() + " as their up neighbour.\t" + entry.getValue());
			}
			
			//If 2 declare the ID as an up neighbour, this is okay only if the ID refers to a Point with a Direction setting of DOWN.
			//If the Direction is DOWN it means two blocks will declare it as an up neighbour, to fill the plus and minus slots.
			if(entry.getValue().size() == 2 && entry.getKey() != 0) {
				//If the ID refers to a Point.
				if(network.getBlock(entry.getKey()).getClass().equals(Point.class)) {
					Point thisPoint = (Point)network.getBlock(entry.getKey());
					//If that Point is not DOWN.
					if(thisPoint.getTravelDirection() != Direction.DOWN) {
						//If its a point but direction isn't down
						issues.add("Too many Blocks declare " + network.getBlock(entry.getKey()).getClass().getSimpleName() + " " + entry.getKey() + " as their up neighbour.\t" + entry.getValue());
					}
				}
				else {
					//Not Point.
					issues.add("Too many Blocks declare " + network.getBlock(entry.getKey()).getClass().getSimpleName() + " " + entry.getKey() + " as their up neighbour.\t" + entry.getValue());
				}
			}
		}
		
		//Validity check on down neighbours
		//Same as above but the other way around. Points are UP, not DOWN, etc..
		for(Entry<Integer, ArrayList<Integer>> entry : downNeighbours.entrySet()) {
			if(entry.getValue().size() >= 3 && entry.getKey() != 0) {
				issues.add("Too many Blocks declare " + network.getBlock(entry.getKey()).getClass().getSimpleName() + " " + entry.getKey() + " as their down neighbour.\t" + entry.getValue());
			}
			if(entry.getValue().size() == 2 && entry.getKey() != 0) {
				if(network.getBlock(entry.getKey()).getClass().equals(Point.class)) {
					Point thisPoint = (Point)network.getBlock(entry.getKey());
					if(thisPoint.getTravelDirection() != Direction.UP) {
						//If its a point but direction isn't up
						issues.add("Too many Blocks declare " + network.getBlock(entry.getKey()).getClass().getSimpleName() + " " + entry.getKey() + " as their down neighbour.\t" + entry.getValue());
					}
				}
				else {
					//not point
					issues.add("Too many Blocks declare " + network.getBlock(entry.getKey()).getClass().getSimpleName() + " " + entry.getKey() + " as their down neighbour.\t" + entry.getValue());
				}
			}
		}
		
		return issues;
	}
	
	/**
	 * <p>Validates a list of points. Ensures each {@link Point} has three neighbours.</p>
	 * <p>Points cannot neighbour Points. Two Points must have at least one {@link Section} between them.</p>
	 * 
	 * @param network A network with a list of points to validate.
	 * @return A list of issues with any problem points.
	 */
	private static ArrayList<String> ValidatePoints(Network network) {
		ArrayList<Point> points = network.getPoints();
		
		ArrayList<String> pointIssues = new ArrayList<>();
		
		//For each point, check for null (val = 0) neighbours and log if required.
		for(Point p : points) {
			//------------MAIN NEIGHBOUR----------
			if(p.getMainNeigh() == 0) {
				pointIssues.add("Point " + p.getId() + " has no main neighbour");
			}
			else {
				//If invalid IDs are used for the neighbours, log issue.
				if(network.getBlock(p.getMainNeigh()) == null) {
					pointIssues.add("ID given for main neighbour of Point " + p.getId() + " is invalid");
				}
				else {
					//If the main neighbour is of the same class as this (Point), log issue.
					if(network.getBlock(p.getMainNeigh()).getClass().equals(p.getClass())) {
						pointIssues.add("Main neighbour of Point " + p.getId() + " can't be a Point");
					}
					else {
						//Check that Section is between Points on main neighbour.
						Block nextBlock = network.getBlock(p.getMainNeigh());
						while(!nextBlock.getClass().equals(Section.class)) {
							if(nextBlock.getClass().equals(Point.class)) {
								pointIssues.add("Main neighbour route of Point " + p.getId() + " has no Section before another Point.");
								break;
							}
							nextBlock = network.getBlock(getDirectionNeighbour((Signal)nextBlock, p.getTravelDirection()));
						}
					}
				}
			}
			
			//-----------MINUS NEIGHBOUR----------
			if(p.getmNeigh() == 0) {
				pointIssues.add("Point " + p.getId() + " has no minus neighbour");
			}
			else {
				if(network.getBlock(p.getmNeigh()) == null) {
					pointIssues.add("ID given for minus neighbour of Point " + p.getId() + " is invalid");
				}
				else {
					//If the minus neighbour is not a Signal, log issue.
					if(!network.getBlock(p.getmNeigh()).getClass().equals(Signal.class)) {
						pointIssues.add("Minus neighbour of Point " + p.getId() + " must be a Signal");
					}
					else {
						//If the Signal neighbour is in the wrong direction, log issue.
						if(((Signal)network.getBlock(p.getmNeigh())).getDirectionEnum().equals(p.getTravelDirection())){
							pointIssues.add("Minus neighbour of Point " + p.getId() + " must be a Signal set to direction " + p.getTravelDirection().toggle());
						}
						else { //Check for Section between Points.
							Signal signal = (Signal)network.getBlock(p.getmNeigh());
							Block afterSignal = network.getBlock(getDirectionNeighbour(signal, p.getTravelDirection()));
							switch(afterSignal.getClass().getSimpleName()) {
							case "Signal": //(point, signal, signal)
								//If the third neighbour (point, signal1, signal2, n3) is not a Section, log issue.
								if(!network.getBlock(getDirectionNeighbour((Signal)afterSignal, p.getTravelDirection())).getClass().equals(Section.class)) {
									pointIssues.add("Minus neighbour route of Point " + p.getId() + " has no Section before another Point.");
								}
								break;
							case "Point": //(point, signal, point)
								pointIssues.add("Minus neighbour route of Point " + p.getId() + " has no Section before another Point.");
								break;
							default:
								break;
							}
						}
					}
				}
			}
			
			//-----------PLUS NEIGHBOUR----------
			if(p.getpNeigh() == 0) {
				pointIssues.add("Point " + p.getId() + " has no plus neighbour");
			}
			else {
				if(network.getBlock(p.getpNeigh()) == null) {
					pointIssues.add("ID given for plus neighbour of Point " + p.getId() + " is invalid");
				}
				else {
					//If the plus neighbour not a Signal, log issue.
					if(!network.getBlock(p.getpNeigh()).getClass().equals(Signal.class)) {
						pointIssues.add("Plus neighbour of Point " + p.getId() + " must be a Signal");
					}
					else {
						if(((Signal)network.getBlock(p.getpNeigh())).getDirectionEnum().equals(p.getTravelDirection())){
							pointIssues.add("Plus neighbour of Point " + p.getId() + " must be a Signal set to direction " + p.getTravelDirection().toggle());
						}
						else {
							Signal signal = (Signal)network.getBlock(p.getpNeigh());
							Block afterSignal = network.getBlock(getDirectionNeighbour(signal, p.getTravelDirection()));
							switch(afterSignal.getClass().getSimpleName()) {
							case "Signal": //(point, signal, signal)
								//If the third neighbour (point, signal1, signal2, n3) is not a Section, log issue.
								if(!network.getBlock(getDirectionNeighbour((Signal)afterSignal, p.getTravelDirection())).getClass().equals(Section.class)) {
									pointIssues.add("Plus neighbour route of Point " + p.getId() + " has no Section before another Point.");
								}
								break;
							case "Point": //(point, signal, point)
								pointIssues.add("Plus neighbour route of Point " + p.getId() + " has no Section before another Point.");
								break;
							default:
								break;
							}
						}
					}
				}
			}
		}
		return pointIssues;
	}
	
	/**
	 * <p>Validates a list of {@link Section Sections}. Checks that each Section has at least one neighbour.</p>
	 * <p>Sections can't neighbour other Sections.</p>
	 * 
	 * @param network The network with sections to validate.
	 * @return A list of issues with any problem sections.
	 */
	private static ArrayList<String> ValidateSections(Network network) {
		ArrayList<Section> sections = network.getSections();
		
		ArrayList<String> sectionIssues = new ArrayList<>();
		
		//For each section, check if both neighbours are null. If so, log an issue.
		for(Section s : sections) {
			if(s.getUpNeigh() == 0) {
				if(s.getDownNeigh() == 0) {
					sectionIssues.add("Section " + s.getId() + " must have at least one neighbour");
				}
			}
			
			//If the section has an up neighbour
			if(s.getUpNeigh() != 0) {
				//If invalid IDs is used for the neighbour, log issue.
				if(network.getBlock(s.getUpNeigh()) == null) {
					sectionIssues.add("ID given for up neighbour of Section " + s.getId() + " is invalid");
				}
				else {
					//If the up neighbour is of the same class as this (Section), log issue.
					if(network.getBlock(s.getUpNeigh()).getClass().equals(s.getClass())) {
						sectionIssues.add("Up neighbour of Section " + s.getId() + " can't be a Section");
					}
				}
			}
			
			//If the section has a down neighbour
			if(s.getDownNeigh() != 0) {
				//If invalid ID is used for the neighbour, log issue.
				if(network.getBlock(s.getDownNeigh()) == null) {
					sectionIssues.add("ID given for down neighbour of Section " + s.getId() + " is invalid");
				}
				else {
					//If the down neighbour is of the same class as this (Section), log issue.
					if(network.getBlock(s.getDownNeigh()).getClass().equals(s.getClass())) {
						sectionIssues.add("Down neighbour of Section " + s.getId() + " can't be a Section");
					}
				}
			}
		}
		return sectionIssues;
	}
	
	/**
	 * <p>Validates a list of {@link Signal Signals}. Checks that each signal has two neighbours.</p>
	 * <p>Can't have more than two Signals in a row.</p>
	 * 
	 * @param network The network with signals to validate.
	 * @return A list of issues with any problem signals.
	 */
	private static ArrayList<String> ValidateSignals(Network network) {
		ArrayList<Signal> signals = network.getSignals();
		
		ArrayList<String> signalIssues = new ArrayList<>();
		
		//For each signal, check for null neighbours and log if required.
		for(Signal s : signals) {
			if(s.getUpNeigh() == 0) {
				signalIssues.add("Signal " + s.getId() + " has no up neighbour.");
			}
			else {
				//If invalid IDs are used for the neighbours, log issue.
				if(network.getBlock(s.getUpNeigh()) == null) {
					signalIssues.add("ID given for up neighbour of Signal " + s.getId() + " is invalid");
				}
			}
			
			if(s.getDownNeigh() == 0) {
				signalIssues.add("Signal " + s.getId() + " has no down neighbour");
			}
			else {
				if(network.getBlock(s.getDownNeigh()) == null) {
					signalIssues.add("ID given for down neighbour of Signal " + s.getId() + " is invalid");
				}
			}
			
			//If both neighbours are valid IDs check for too many Signals
			if(s.getUpNeigh() != 0 && s.getDownNeigh() != 0) {
				if(network.getBlock(s.getUpNeigh()) != null && network.getBlock(s.getDownNeigh()) != null) {
					//If both neighbours are also Signals, log an issue. Can have two Signals in a row, but no more.
					if(network.getBlock(s.getUpNeigh()).getClass().equals(s.getClass())) {
						if(network.getBlock(s.getDownNeigh()).getClass().equals(s.getClass())) {
							signalIssues.add("Signal " + s.getId() + " has a Signal as both neighbours. Only two Signals in a row allowed.");
						}
					}
				}
			}
		}
		return signalIssues;
	}
	
	/**
	 * <p>Validates a {@link Network} to ensure each end point is connected to every other end point.</p>
	 * 
	 * <p>This uses an adapted version of the Route finding method, but allows for turning around at {@link Point Points}.
	 * E.g. if you have come from the plus neighbour on a Point, you can look down the minus neighbour for the destination.
	 * In the real Route finding you cannot change direction on a Point.</p>
	 * 
	 * <p>Also, unlike a real {@link Route}, these Routes start and finish at {@link Section Sections}, not {@link Signal Signals}.</p>
	 * 
	 * @param network Network to validate.
	 * @return A Map containing an array of each {source, destination} connection and a boolean to signify if a connection was made.
	 */
	private static Map<Integer[], Boolean> validateConnectedNetwork(Network network) {
		HashMap<Integer[], Boolean> connections = new HashMap<>();
		
		//For each end point, check if they are connected to each other, if not set invalid.
		for(int source : network.getEndpoints()) {
			for(int destination : network.getEndpoints()) {
				//Array for current connection
				Integer[] connection = new Integer[] {source, destination};
				
				//If there is no route, connection invalid. Else valid.
				if(!calculateRoute(network, network.getBlock(source), network.getBlock(destination))) {
					connections.put(connection, false);
				}
				else {
					connections.put(connection, true);
				}
				
				//If the route is to itself, above will say invalid, so this says it's valid.
				if(source == destination) {
					connections.put(connection, true);
				}
			}
		}
		
		return connections;
	}
	

	/**
	 * <p>Calculates if a {@link Route} can be created between two {@link Block Blocks}.</p>
	 * 
	 * @param network {@link Network} to calculate Route on.
	 * @param source Source Block of the Route.
	 * @param destination Destination Block of the Route.
	 * @return Boolean indicating if a Route can be created.
	 */
	private static boolean calculateRoute(Network network, Block source, Block destination)
    {
        //Try looking down the network, if no route found, look up the network. If no routes are found either way, route will be false.
        boolean tempRoute = calcNextNeighbour(source, Direction.DOWN, 0, network, destination, new ArrayList<>());
        return tempRoute || calcNextNeighbour(source, Direction.UP, 0, network, destination, new ArrayList<>());
    }
	
	/**
	 * <p>Search the whole {@link Network} for a route to the destination.</p>
	 * 
	 * @param previousNeighbour The {@link Block} which called this method. Initially this is the source.
	 * @param direction The {@link Direction} of travel when searching. Should be determined by the sources null neighbour.
	 * @param from The ID of the Block before the previousNeighbour. Should be 0 to start.
	 * @param network The network with a list of Blocks which make up the route. Pass it an empty ArrayList of Blocks to start.
	 * @param destination The destination Block.
	 * @param visited The list of IDs which have been visited.
	 * @return boolean True if a Route can be found.
	 */
	private static boolean calcNextNeighbour(Block previousNeighbour, Direction direction, int from, Network network, Block destination, ArrayList<Integer> visited) {
		//If previousNeighbour already been checked, go back.
		if(visited.contains(previousNeighbour.getId())){
			return false;
		}
		visited.add(previousNeighbour.getId());
		
		//If the prev neighbour is a Section
		if(previousNeighbour.getClass().equals(Section.class)) {
			Section previousSection = (Section)previousNeighbour;
			
			//If the direction neigh is null, meaning previousSection is an endpoint, return null.
			if(getDirectionNeighbour(previousSection, direction) == 0) {
				return false;
			}
			
			Block thisBlock = network.getBlock(getDirectionNeighbour(previousSection, direction));
			
			//If this block is the destination return true.
			if(thisBlock.getId() == destination.getId()) {
				return true;
			}
			
			//Recursively try this all again.
			return calcNextNeighbour(thisBlock, direction, previousSection.getId(), network, destination, visited);
		}
		
		//If the previous neighbour is a Signal
		if(previousNeighbour.getClass().equals(Signal.class)) {
			Signal previousSignal = (Signal)previousNeighbour;
			
			Block thisBlock = network.getBlock(getDirectionNeighbour(previousSignal, direction));
			
			//If this block is the destination return true.
			if(thisBlock.getId() == destination.getId()) {
				return true;
			}
			
			//Recursively try this all again.
			return calcNextNeighbour(thisBlock, direction, previousSignal.getId(), network, destination, visited);
		}
		
		//If the previous neighbour is a Point
		if(previousNeighbour.getClass().equals(Point.class)) {
			//LOGIC FOR NAVIGATING POINTS
			Point previousPoint = (Point)previousNeighbour;
			
			//if we have come from the main neighbour, try the plus and minus paths.
			if(from == previousPoint.getMainNeigh()) {
				//Try the minus neighbour route.
				Block thisBlock = network.getBlock(previousPoint.getmNeigh());
				
				//If this block is the destination return true.
				if(thisBlock.getId() == destination.getId()) {
					return true;
				}
				
				boolean nextBlock = calcNextNeighbour(thisBlock, direction, previousPoint.getId(), network, destination, visited);
				//If the minus neighbour returned null, meaning the destination was not found, try the plus route.
				if(!nextBlock)
                {
                    thisBlock = network.getBlock(previousPoint.getpNeigh());

                    //If this block is the destination return true.
                    return thisBlock.getId() == destination.getId() || calcNextNeighbour(thisBlock, direction, previousPoint.getId(), network, destination, visited);
                }
				return true;
			}
			
			//if we have come from the minus neighbour, try the plus and main paths.
			if(from == previousPoint.getmNeigh()) {
				//Try the main neighbour route.
				Block thisBlock = network.getBlock(previousPoint.getMainNeigh());
				
				//If this block is the destination return true.
				if(thisBlock.getId() == destination.getId()) {
					return true;
				}
				
				boolean nextBlock = calcNextNeighbour(thisBlock, direction, previousPoint.getId(), network, destination, visited);
				//If the main neighbour returned null, meaning the destination was not found, try the plus route.
				if(!nextBlock)
                {
                    thisBlock = network.getBlock(previousPoint.getpNeigh());

                    //If this block is the destination return true.
                    return thisBlock.getId() == destination.getId() || calcNextNeighbour(thisBlock, direction.toggle(), previousPoint.getId(), network, destination, visited);
                }
				return true;
			}
			
			//if we have come from the plus neighbour, try the main and minus paths.
			if(from == previousPoint.getpNeigh()) {
				//Try the minus neighbour route.
				Block thisBlock = network.getBlock(previousPoint.getmNeigh());
				
				//If this block is the destination return true.
				if(thisBlock.getId() == destination.getId()) {
					return true;
				}
				
				boolean nextBlock = calcNextNeighbour(thisBlock, direction.toggle(), previousPoint.getId(), network, destination, visited);
				//If the minus neighbour returned null, meaning the destination was not found, try the main route.
				if(!nextBlock)
                {
                    thisBlock = network.getBlock(previousPoint.getMainNeigh());

                    //If this block is the destination return true.
                    return thisBlock.getId() == destination.getId() || calcNextNeighbour(thisBlock, direction, previousPoint.getId(), network, destination, visited);
                }
				return true;
			}
		}
		
		return false;
	}
	
	/**
	 * <p>Get the up or down neighbour of a {@link Section}, dependent on provided direction.</p>
	 * 
	 * @param section The {@link Section} to provide a neighbour of.
	 * @param direction The {@link Direction} of the desired neighbour.
	 * @return An int ID of a neighbour.
	 */
	private static int getDirectionNeighbour(Section section, Direction direction) {
		if(direction == Direction.UP) {
			return section.getUpNeigh();
		}
		return section.getDownNeigh();
	}
	
	/**
	 * <p>Get the up or down neighbour of a {@link Signal}, dependent on provide direction.</p>
	 * 
	 * @param signal The {@link Signal} to provide a neighbour of.
	 * @param direction The {@link Direction} of the desired neighbour.
	 * @return An int ID of a neighbour.
	 */
	private static int getDirectionNeighbour(Signal signal, Direction direction) {
		if(direction == Direction.UP) {
			return signal.getUpNeigh();
		}
		return signal.getDownNeigh();
	}
	
	/**
	 * <p>Return true if the given ID refers to a {@link Signal} in the given {@link Network}.</p>
	 * 
	 * @param id ID of block to check.
	 * @param network Network to check in.
	 * @return True if ID refers to a Signal. False if its not a Signal or if the ID is invalid.
	 */
	public static boolean isSignal(int id, Network network) {
		if(network.getBlock(id) != null) {
			return network.getBlock(id).getClass().equals(Signal.class);
		}
		return false;
	}
}