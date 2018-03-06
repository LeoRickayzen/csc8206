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
 * <p>Class to validate a network. Ensures neighbours aren't left null. Ensures no component neighbours a component of the same type.</p>
 * <p>Checks that IDs given for neighbours are all valid in the network.</p>
 *
 * @author Jay Kahlil Hussaini
 * Date created: 24/02/2018
 * Last updated: 28/02/2018
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
	
	//TODO stack overflow occuring because route finding keeps going on the minus neigh when its trying to find a destination on a plus.
	//This is when there is a cycle so it can't find the destination that would say it is a cycle.
	//Maybe some note that you've already got to a point? If a block is already in the route go another way? Return null?
	private static ArrayList<String> ValidateNonCyclic(Network network){
		ArrayList<String> issues = new ArrayList<String>();
		for(Signal signal : network.getSignals()) {
			try {
				Route route = new Route(-1, signal.getId(), signal.getId(), network);
				issues.add("Network cannot be cyclical. Can create cyclic Route with Signal " + signal.getId());
			}
			catch(IllegalArgumentException e) {
				//If there is an exception it means that the network isn't cyclical. This is good.
			}
		}
		return issues;
	}
	
	/**
	 * <p>Checks that no ID is duplicated in the network.<p>
	 * @param network The {@link Network} to check.
	 * @return A list detailing any ID duplications
	 */
	private static ArrayList<String> ValidateUniqueIds(Network network){
		ArrayList<String> issues = new ArrayList<String>();
		HashSet<Integer> allIds = new HashSet<Integer>();
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
	
	//WORK IN PROGRESS
	//TODO figure out how to ensure neighbours are duplicated between blocks.
	//This is difficult because two signals could both have the same down neighbour if it is a Point.
	//Was gonna do some sort of mapping thing but thats hard when points have different neighbours than just up and down.
	private static ArrayList<String> ValidateUniqueNeighbours(Network network){
		ArrayList<String> issues = new ArrayList<String>();
		HashMap<Integer, ArrayList<Integer>> upNeighbours = new HashMap<Integer, ArrayList<Integer>>();
		HashMap<Integer, ArrayList<Integer>> downNeighbours = new HashMap<Integer, ArrayList<Integer>>();
		
		//For each section, add its ID to a list in a map where the key is the ID of the up/down neighbour
		for(Section section : network.getSections()) {
			ArrayList<Integer> blocksWithSameUp;
			blocksWithSameUp = upNeighbours.get(section.getUpNeigh());
			if(blocksWithSameUp == null) {
				blocksWithSameUp = new ArrayList<Integer>();
			}
			blocksWithSameUp.add(section.getId());
			upNeighbours.put(section.getUpNeigh(), blocksWithSameUp);
			
			ArrayList<Integer> blocksWithSameDown;
			blocksWithSameDown = downNeighbours.get(section.getDownNeigh());
			if(blocksWithSameDown == null) {
				blocksWithSameDown = new ArrayList<Integer>();
			}
			blocksWithSameDown.add(section.getId());
			downNeighbours.put(section.getDownNeigh(), blocksWithSameDown);
		}
		
		//For each Signal, add its ID to a list in a map where the key is the ID of the up/down neighbour
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
		
		for(Point point : network.getPoints()) {
			ArrayList<Integer> blocksWithSameUp;
			ArrayList<Integer> pointsWithSameUp2;
			ArrayList<Integer> blocksWithSameDown;
			ArrayList<Integer> pointsWithSameDown2;
			if(point.getTravelDirection() == Direction.UP) {
				//If point travel is up, the up neighbours are plus and minus.
				blocksWithSameUp = upNeighbours.get(point.getmNeigh());
				pointsWithSameUp2 = upNeighbours.get(point.getpNeigh());
				
				if(blocksWithSameUp == null) {
					blocksWithSameUp = new ArrayList<Integer>();
				}
				
				if(pointsWithSameUp2 == null) {
					pointsWithSameUp2 = new ArrayList<Integer>();
				}
				
				blocksWithSameUp.add(point.getId());
				pointsWithSameUp2.add(point.getId());
				
				upNeighbours.put(point.getmNeigh(), blocksWithSameUp);
				upNeighbours.put(point.getpNeigh(), pointsWithSameUp2);
				
				//If point travel is up, the down neighbour is main.
				blocksWithSameDown = downNeighbours.get(point.getMainNeigh());
				
				if(blocksWithSameDown == null) {
					blocksWithSameDown = new ArrayList<Integer>();
				}
				
				blocksWithSameDown.add(point.getId());
				
				downNeighbours.put(point.getMainNeigh(), blocksWithSameDown);
			}
			else {
				//Else if point travel is down, the up neigh is just the main.
				blocksWithSameUp = upNeighbours.get(point.getMainNeigh());
				
				if(blocksWithSameUp == null) {
					blocksWithSameUp = new ArrayList<Integer>();
				}
				
				blocksWithSameUp.add(point.getId());
				upNeighbours.put(point.getMainNeigh(), blocksWithSameUp);
				
				//Else if point travel is down, the down neighbours are plus and minus.
				blocksWithSameDown = downNeighbours.get(point.getmNeigh());
				pointsWithSameDown2 = downNeighbours.get(point.getpNeigh());
				
				if(blocksWithSameDown == null) {
					blocksWithSameDown = new ArrayList<Integer>();
				}
				
				if(pointsWithSameDown2 == null) {
					pointsWithSameDown2 = new ArrayList<Integer>();
				}
				
				blocksWithSameDown.add(point.getId());
				pointsWithSameDown2.add(point.getId());
				
				downNeighbours.put(point.getmNeigh(), blocksWithSameDown);
				downNeighbours.put(point.getpNeigh(), pointsWithSameDown2);
			}
		}
		
		//Validity check up up neighbours
		for(Entry<Integer, ArrayList<Integer>> entry : upNeighbours.entrySet()) {
			if(entry.getValue().size() >= 3 && entry.getKey() != 0) {
				issues.add("Too many Blocks declare " + network.getBlock(entry.getKey()).getClass().getSimpleName() + " " + entry.getKey() + " as their up neighbour.\t" + entry.getValue());
			}
			if(entry.getValue().size() == 2 && entry.getKey() != 0) {
				if(network.getBlock(entry.getKey()).getClass().equals(Point.class)) {
					Point thisPoint = (Point)network.getBlock(entry.getKey());
					if(thisPoint.getTravelDirection() != Direction.DOWN) {
						//If its a point but direction isn't down
						issues.add("Too many Blocks declare " + network.getBlock(entry.getKey()).getClass().getSimpleName() + " " + entry.getKey() + " as their up neighbour.\t" + entry.getValue());
					}
				}
				else {
					//not point
					issues.add("Too many Blocks declare " + network.getBlock(entry.getKey()).getClass().getSimpleName() + " " + entry.getKey() + " as their up neighbour.\t" + entry.getValue());
				}
			}
		}
		
		//Validity check on down neighbours
		for(Entry<Integer, ArrayList<Integer>> entry : downNeighbours.entrySet()) {
			if(entry.getValue().size() >= 3) {
				issues.add("Too many Blocks declare " + network.getBlock(entry.getKey()).getClass().getSimpleName() + " " + entry.getKey() + " as their down neighbour.\t" + entry.getValue());
			}
			if(entry.getValue().size() == 2) {
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
	 * <p>Validates a list of points. Ensures each point has three neighbours.</p>
	 * <p>Points cannot neighbour Points.</p>
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
				pointIssues.add(p.getId() + "\t|\t" + "Point" + "\t|\t" + "no main neighbour");
			}
			else {
				//If invalid IDs are used for the neighbours, log issue.
				if(network.getBlock(p.getMainNeigh()) == null) {
					pointIssues.add(p.getId() + "\t|\t" + "Point" + "\t|\t" + "ID given for main neighbour is invalid");
				}
				else {
					//If the main neighbour is of the same class as this (Point), log issue.
					if(network.getBlock(p.getMainNeigh()).getClass().equals(p.getClass())) {
						pointIssues.add(p.getId() + "\t|\t" + "Point" + "\t|\t" + "main neighbour can't be Point");
					}
					else {
						//Check that Section is between Points on main neighbour.
						Block nextBlock = network.getBlock(p.getMainNeigh());
						while(!nextBlock.getClass().equals(Section.class)) {
							if(nextBlock.getClass().equals(Point.class)) {
								pointIssues.add(p.getId() + "\t|\t" + "Point" + "\t|\t" + "there must be a Section between Points. Main neighbour.");
								break;
							}
							nextBlock = network.getBlock(getDirectionNeighbour((Signal)nextBlock, p.getTravelDirection()));
						}
					}
				}
			}
			
			//-----------MINUS NEIGHBOUR----------
			if(p.getmNeigh() == 0) {
				pointIssues.add(p.getId() + "\t|\t" + "Point" + "\t|\t" + "no minus neighbour");
			}
			else {
				if(network.getBlock(p.getmNeigh()) == null) {
					pointIssues.add(p.getId() + "\t|\t" + "Point" + "\t|\t" + "ID given for minus neighbour is invalid");
				}
				else {
					//If the minus neighbour is not a Signal, log issue.
					if(!network.getBlock(p.getmNeigh()).getClass().equals(Signal.class)) {
						pointIssues.add(p.getId() + "\t|\t" + "Point" + "\t|\t" + "minus neighbour must be a Signal");
					}
					else {
						//If the Signal neighbour is in the wrong direction, log issue.
						if(((Signal)network.getBlock(p.getmNeigh())).getDirectionEnum().equals(p.getTravelDirection())){
							pointIssues.add(p.getId() + "\t|\t" + "Point" + "\t|\t" + "minus neighbour must be a Signal set to direction " + p.getTravelDirection().toggle());
						}
						else { //Check for Section between Points.
							Signal signal = (Signal)network.getBlock(p.getmNeigh());
							Block afterSignal = network.getBlock(getDirectionNeighbour(signal, p.getTravelDirection()));
							switch(afterSignal.getClass().getSimpleName()) {
							case "Signal": //(point, signal, signal)
								//If the third neighbour (point, signal1, signal2, n3) is not a Section, log issue.
								if(!network.getBlock(getDirectionNeighbour((Signal)afterSignal, p.getTravelDirection())).getClass().equals(Section.class)) {
									pointIssues.add(p.getId() + "\t|\t" + "Point" + "\t|\t" + "there must be a Section between Points. Minus neighbour.");
								}
								break;
							case "Point": //(point, signal, point)
								pointIssues.add(p.getId() + "\t|\t" + "Point" + "\t|\t" + "there must be a Section between Points. Minus neighbour.");
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
				pointIssues.add(p.getId() + "\t|\t" + "Point" + "\t|\t" + "no plus neighbour");
			}
			else {
				if(network.getBlock(p.getpNeigh()) == null) {
					pointIssues.add(p.getId() + "\t|\t" + "Point" + "\t|\t" + "ID given for plus neighbour is invalid");
				}
				else {
					//If the plus neighbour not a Signal, log issue.
					if(!network.getBlock(p.getpNeigh()).getClass().equals(Signal.class)) {
						pointIssues.add(p.getId() + "\t|\t" + "Point" + "\t|\t" + "plus neighbour must be a Signal");
					}
					else {
						if(((Signal)network.getBlock(p.getpNeigh())).getDirectionEnum().equals(p.getTravelDirection())){
							pointIssues.add(p.getId() + "\t|\t" + "Point" + "\t|\t" + "plus neighbour must be a Signal set to direction " + p.getTravelDirection().toggle());
						}
						else {
							Signal signal = (Signal)network.getBlock(p.getpNeigh());
							Block afterSignal = network.getBlock(getDirectionNeighbour(signal, p.getTravelDirection()));
							switch(afterSignal.getClass().getSimpleName()) {
							case "Signal": //(point, signal, signal)
								//If the third neighbour (point, signal1, signal2, n3) is not a Section, log issue.
								if(!network.getBlock(getDirectionNeighbour((Signal)afterSignal, p.getTravelDirection())).getClass().equals(Section.class)) {
									pointIssues.add(p.getId() + "\t|\t" + "Point" + "\t|\t" + "there must be a Section between Points. Plus neighbour.");
								}
								break;
							case "Point": //(point, signal, point)
								pointIssues.add(p.getId() + "\t|\t" + "Point" + "\t|\t" + "there must be a Section between Points. Plus neighbour.");
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
	 * <p>Validates a list of sections. Checks that each section has at least one neighbour.</p>
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
					sectionIssues.add(s.getId() + "\t|\t" + "Section" + "\t|\t" + "must have at least one neighbour");
				}
			}
			
			//If the section has an up neighbour
			if(s.getUpNeigh() != 0) {
				//If invalid IDs is used for the neighbour, log issue.
				if(network.getBlock(s.getUpNeigh()) == null) {
					sectionIssues.add(s.getId() + "\t|\t" + "Section" + "\t|\t" + "ID given for up neighbour is invalid");
				}
				else {
					//If the up neighbour is of the same class as this (Section), log issue.
					if(network.getBlock(s.getUpNeigh()).getClass().equals(s.getClass())) {
						sectionIssues.add(s.getId() + "\t|\t" + "Section" + "\t|\t" + "up neighbour can't be Section");
					}
				}
			}
			
			//If the section has a down neighbour
			if(s.getDownNeigh() != 0) {
				//If invalid ID is used for the neighbour, log issue.
				if(network.getBlock(s.getDownNeigh()) == null) {
					sectionIssues.add(s.getId() + "\t|\t" + "Section" + "\t|\t" + "ID given for down neighbour is invalid");
				}
				else {
					//If the down neighbour is of the same class as this (Section), log issue.
					if(network.getBlock(s.getDownNeigh()).getClass().equals(s.getClass())) {
						sectionIssues.add(s.getId() + "\t|\t" + "Section" + "\t|\t" + "down neighbour can't be Section");
					}
				}
			}
		}
		return sectionIssues;
	}
	
	/**
	 * <p>Validates a list of signals. Checks that each signal has two neighbours.</p>
	 * <p>Signals can't neighbour other Signals.</p>
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
				signalIssues.add(s.getId() + "\t|\t" + "Signal" + "\t|\t" + "no up neighbour");
			}
			else {
				//If invalid IDs are used for the neighbours, log issue.
				if(network.getBlock(s.getUpNeigh()) == null) {
					signalIssues.add(s.getId() + "\t|\t" + "Signal" + "\t|\t" + "ID given for up neighbour is invalid");
				}
			}
			
			if(s.getDownNeigh() == 0) {
				signalIssues.add(s.getId() + "\t|\t" + "Signal" + "\t|\t" + "no down neighbour");
			}
			else {
				if(network.getBlock(s.getDownNeigh()) == null) {
					signalIssues.add(s.getId() + "\t|\t" + "Signal" + "\t|\t" + "ID given for down neighbour is invalid");
				}
			}
			
			//If both neighbour are valid IDs check for too many Signals
			if(s.getUpNeigh() != 0 && s.getDownNeigh() != 0) {
				if(network.getBlock(s.getUpNeigh()) != null && network.getBlock(s.getDownNeigh()) != null) {
					//If both neighbours are also Signals, log an issue. Can have two Signals in a row, but no more.
					if(network.getBlock(s.getUpNeigh()).getClass().equals(s.getClass())) {
						if(network.getBlock(s.getDownNeigh()).getClass().equals(s.getClass())) {
							signalIssues.add(s.getId() + "\t|\t" + "Signal" + "\t|\t" + "can only have one Signal as neighbour, not both.");
						}
					}
				}
			}
		}
		return signalIssues;
	}
	
	private static Map<Integer[], Boolean> validateConnectedNetwork(Network network) {
		HashMap<Integer[], Boolean> connections = new HashMap<>();
		
		//For each endpoint, check if they are connected to each other, if not set invalid.
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
	 * <p>Calculate the blocks needed to reach the destination from the source.</p>
	 */
	private static boolean calculateRoute(Network network, Block source, Block destination)
    {
        //Try looking down the network, if no route found, look up the network. If no routes are found either way, route will be null.
        boolean tempRoute = calcNextNeighbour(source, Direction.DOWN, 0, network, destination, new ArrayList<>());
        //System.out.println("Looking up the network");
        return tempRoute || calcNextNeighbour(source, Direction.UP, 0, network, destination, new ArrayList<>());
    }
	
	/**
	 * <p>Searched the whole network for a route to the destination.</p>
	 * 
	 * @param previousNeighbour The block which called this method. Initially this is the source.
	 * @param direction The direction of travel when searching. Should be determined by the sources null neighbour.
	 * @param from The ID of the Block before the previousNeighbour. Should be 0 to start.
	 * @param network The network with a list of Blocks which make up the route. Pass it an empty ArrayList<Block> to start.
	 * @param destination The destination Block.
	 * @param visited The list of IDs which have been visited.
	 * @return boolean
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
	 * <p>Get the up or down neighbour of a Section, dependent on provide direction.</p>
	 * 
	 * @param section The Section to provide a neighbour of.
	 * @param direction The direction of the desired neighbour.
	 * @return an int ID of a neighbour.
	 */
	private static int getDirectionNeighbour(Section section, Direction direction) {
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
	private static int getDirectionNeighbour(Signal signal, Direction direction) {
		if(direction == Direction.UP) {
			return signal.getUpNeigh();
		}
		return signal.getDownNeigh();
	}
	
	/**
	 * <p>Return true if the given ID refers to a {@link Signal} in the given {@link Network}.</p>
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