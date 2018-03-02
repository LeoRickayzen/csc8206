package railway.validation;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import javafx.scene.control.Alert;
import railway.network.Block;
import railway.network.Direction;
import railway.network.Network;
import railway.network.Point;
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
		ArrayList<String> issues = new ArrayList<String>();
		
		//Validate network parts and save issues if they exist.
		issues.addAll(ValidatePoints(network));
		issues.addAll(ValidateSections(network));
		issues.addAll(ValidateSignals(network));
		
		//If there are no issues with the above checks, check that the whole network is connected.
		if(issues.isEmpty()) {
			for(Map.Entry<Integer[], Boolean> entry : validateConnectedNetwork(network).entrySet()) {
				if(entry.getValue() == false) {
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
	 * <p>Validates a list of points. Ensures each point has three neighbours.</p>
	 * <p>Points cannot neighbour Points.</p>
	 * 
	 * @param network A network with a list of points to validate.
	 * @return A list of issues with any problem points.
	 */
	private static ArrayList<String> ValidatePoints(Network network) {
		ArrayList<Point> points = network.getPoints();
		
		ArrayList<String> pointIssues = new ArrayList<String>();
		
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
					//If the minus neighbour is of the same class as this (Point), log issue.
					if(network.getBlock(p.getmNeigh()).getClass().equals(p.getClass())) {
						pointIssues.add(p.getId() + "\t|\t" + "Point" + "\t|\t" + "minus neighbour can't be Point");
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
					//If the plus neighbour is of the same class as this (Point), log issue.
					if(network.getBlock(p.getpNeigh()).getClass().equals(p.getClass())) {
						pointIssues.add(p.getId() + "\t|\t" + "Point" + "\t|\t" + "plus neighbour can't be Point");
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
		
		ArrayList<String> sectionIssues = new ArrayList<String>();
		
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
		
		ArrayList<String> signalIssues = new ArrayList<String>();
		
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
		HashMap<Integer[], Boolean> connections = new HashMap<Integer[], Boolean>();
		
		//For each endpoint, check if they are connected to each other, if not set invalid.
		for(int source : network.getEndpoints()) {
			for(int destination : network.getEndpoints()) {
				//Array for current connection
				Integer[] connection = new Integer[] {source, destination};
				
				//If there is no route, connection invalid. Else valid.
				if(calculateRoute(network, network.getBlock(source), network.getBlock(destination)) == false) {
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
	private static boolean calculateRoute(Network network, Block source, Block destination) {
		//Try looking down the network, if no route found, look up the network. If no routes are found either way, route will be null.
		boolean tempRoute = calcNextNeighbour(source, Direction.DOWN, 0, network, destination, new ArrayList<Integer>());
		if(tempRoute == false) {
			//System.out.println("Looking up the network");
			return tempRoute = calcNextNeighbour(source, Direction.UP, 0, network, destination, new ArrayList<Integer>());
		}
		else {
			return tempRoute;
		}
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
				if(nextBlock == false) {
					thisBlock = network.getBlock(previousPoint.getpNeigh());
					
					//If this block is the destination return true.
					if(thisBlock.getId() == destination.getId()) {
						return true;
					}
					return calcNextNeighbour(thisBlock, direction, previousPoint.getId(), network, destination, visited);
				}
				return nextBlock;
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
				if(nextBlock == false) {
					thisBlock = network.getBlock(previousPoint.getpNeigh());
					
					//If this block is the destination return true.
					if(thisBlock.getId() == destination.getId()) {
						return true;
					}
					return calcNextNeighbour(thisBlock, direction.toggle(), previousPoint.getId(), network, destination, visited);
				}
				return nextBlock;
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
				if(nextBlock == false) {
					thisBlock = network.getBlock(previousPoint.getMainNeigh());
					
					//If this block is the destination return true.
					if(thisBlock.getId() == destination.getId()) {
						return true;
					}
					return calcNextNeighbour(thisBlock, direction, previousPoint.getId(), network, destination, visited);
				}
				return nextBlock;
			}
		}
		
		//System.out.println("Final null return");
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

	public static void showErrorMessage(Exception e, String header)
    {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(header);
        alert.setContentText(e.getMessage());

        alert.showAndWait();
    }
}