package railway.validation;

import java.util.ArrayList;

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
 * Last updated: 26/02/2018
 *
 */
public class NetValidation {

	/**
	 * <p>Validates a network and returns an information object with results.<p>
	 * 
	 * @param network The network to validate.
	 * @return ValidationInfo object. Call isValid() for simple boolean check. getIssues() for detailed list if invalid.
	 */
	public ValidationInfo Validate(Network network) {
		ValidationInfo vInfo = new ValidationInfo();
		
		//Validate network parts and save issues if they exist.
		ArrayList<String> issues = ValidatePoints(network);
		issues.addAll(ValidateSections(network));
		issues.addAll(ValidateSignals(network));
		
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
	 * @param points A list of points to validate.
	 * @return A list of issues with any problem points.
	 */
	private ArrayList<String> ValidatePoints(Network network) {
		ArrayList<Point> points = network.getPoints();
		
		ArrayList<String> pointIssues = new ArrayList<String>();
		
		//For each point, check for null (val = 0) neighbours and log if required.
		for(Point p : points) {
			if(p.getMainNeigh() == 0) {
				pointIssues.add(p.getId() + "\t|\t" + "Point" + "\t|\t" + "no main neighbour");
			}
			
			if(p.getmNeigh() == 0) {
				pointIssues.add(p.getId() + "\t|\t" + "Point" + "\t|\t" + "no minus neighbour");
			}
			
			if(p.getpNeigh() == 0) {
				pointIssues.add(p.getId() + "\t|\t" + "Point" + "\t|\t" + "no plus neighbour");
			}
			
			//If the main neighbour is of the same class as this (Point), log issue.
			if(network.getComp(p.getMainNeigh()).getClass().equals(p.getClass())) {
				pointIssues.add(p.getId() + "\t|\t" + "Point" + "\t|\t" + "main neighbour can't be Point");
			}
			
			//If the minus neighbour is of the same class as this (Point), log issue.
			if(network.getComp(p.getmNeigh()).getClass().equals(p.getClass())) {
				pointIssues.add(p.getId() + "\t|\t" + "Point" + "\t|\t" + "minus neighbour can't be Point");
			}
			
			//If the plus neighbour is of the same class as this (Point), log issue.
			if(network.getComp(p.getpNeigh()).getClass().equals(p.getClass())) {
				pointIssues.add(p.getId() + "\t|\t" + "Point" + "\t|\t" + "plus neighbour can't be Point");
			}
			
			//If invalid IDs are used for the neighbours, log issue.
			if(network.getComp(p.getMainNeigh()) == null) {
				pointIssues.add(p.getId() + "\t|\t" + "Point" + "\t|\t" + "ID given for main neighbour is invalid");
			}
			
			if(network.getComp(p.getmNeigh()) == null) {
				pointIssues.add(p.getId() + "\t|\t" + "Point" + "\t|\t" + "ID given for minus neighbour is invalid");
			}
			
			if(network.getComp(p.getpNeigh()) == null) {
				pointIssues.add(p.getId() + "\t|\t" + "Point" + "\t|\t" + "ID given for plus neighbour is invalid");
			}
			
		}
		
		return pointIssues;
	}
	
	/**
	 * <p>Validates a list of sections. Checks that each section has at least one neighbour.</p>
	 * <p>Sections can't neighbour other Sections.</p>
	 * 
	 * @param sections A list of sections to validate.
	 * @return A list of issues with any problem sections.
	 */
	private ArrayList<String> ValidateSections(Network network){
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
				//If the up neighbour is of the same class as this (Section), log issue.
				if(network.getComp(s.getUpNeigh()).getClass().equals(s.getClass())) {
					sectionIssues.add(s.getId() + "\t|\t" + "Section" + "\t|\t" + "up neighbour can't be Section");
				}
				
				//If invalid IDs is used for the neighbour, log issue.
				if(network.getComp(s.getUpNeigh()) == null) {
					sectionIssues.add(s.getId() + "\t|\t" + "Section" + "\t|\t" + "ID given for up neighbour is invalid");
				}
			}
			
			//If the section has a down neighbour
			if(s.getDownNeigh() != 0) {
				//If the down neighbour is of the same class as this (Section), log issue.
				if(network.getComp(s.getDownNeigh()).getClass().equals(s.getClass())) {
					sectionIssues.add(s.getId() + "\t|\t" + "Section" + "\t|\t" + "down neighbour can't be Section");
				}
				
				//If invalid ID is used for the neighbour, log issue.
				if(network.getComp(s.getDownNeigh()) == null) {
					sectionIssues.add(s.getId() + "\t|\t" + "Section" + "\t|\t" + "ID given for down neighbour is invalid");
				}
			}
			
			
		}
		
		return sectionIssues;
	}
	
	/**
	 * <p>Validates a list of signals. Checks that each signal has two neighbours.</p>
	 * <p>Signals can't neighbour other Signals.</p>
	 * 
	 * @param signals The list of signals to validate.
	 * @return A list of issues with any problem signals.
	 */
	private ArrayList<String> ValidateSignals(Network network){
		ArrayList<Signal> signals = network.getSignals();
		
		ArrayList<String> signalIssues = new ArrayList<String>();
		
		//For each signal, check for null neighbours and log if required.
		for(Signal s : signals) {
			if(s.getUpNeigh() == 0) {
				signalIssues.add(s.getId() + "\t|\t" + "Signal" + "\t|\t" + "no up neighbour");
			}
			
			if(s.getDownNeigh() == 0) {
				signalIssues.add(s.getId() + "\t|\t" + "Signal" + "\t|\t" + "no down neighbour");
			}
			
			//If both neighbours are also Signals, log an issue. Can have two Signals in a row, but no more.
			if(network.getComp(s.getUpNeigh()).getClass().equals(s.getClass())) {
				if(network.getComp(s.getDownNeigh()).getClass().equals(s.getClass())) {
					signalIssues.add(s.getId() + "\t|\t" + "Signal" + "\t|\t" + "can only have one Signal as neighbour, not both.");
				}
			}
			
			//If invalid IDs are used for the neighbours, log issue.
			if(network.getComp(s.getUpNeigh()) == null) {
				signalIssues.add(s.getId() + "\t|\t" + "Signal" + "\t|\t" + "ID given for up neighbour is invalid");
			}
			
			if(network.getComp(s.getDownNeigh()) == null) {
				signalIssues.add(s.getId() + "\t|\t" + "Signal" + "\t|\t" + "ID given for down neighbour is invalid");
			}
		}
		
		return signalIssues;
	}
}