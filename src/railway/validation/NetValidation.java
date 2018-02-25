package railway.validation;

import java.util.ArrayList;

import railway.network.Network;
import railway.network.Point;
import railway.network.Section;
import railway.network.Signal;

/**
 * <p>Class to validate a network. Ensures neighbours aren't left null.</p>
 * 
 * @author Jay Kahlil Hussaini
 * Date created: 24/02/2018
 * Last updated: 25/02/2018
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
		ArrayList<String> issues = ValidatePoints(network.getPoints());
		issues.addAll(ValidateSections(network.getSections()));
		issues.addAll(ValidateSignals(network.getSignals()));
		
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
	 * 
	 * @param points A list of points to validate.
	 * @return A list of issues with any problem points.
	 */
	private ArrayList<String> ValidatePoints(ArrayList<Point> points) {
		ArrayList<String> pointIssues = new ArrayList<String>();
		
		//For each point, check for null neighbours and log if required.
		for(Point p : points) {
			if(p.getMainNeigh() == null) {
				pointIssues.add(p.getId() + "\t|\t" + "Point" + "\t|\t" + "no main neighbour");
			}
			
			if(p.getmNeigh() == null) {
				pointIssues.add(p.getId() + "\t|\t" + "Point" + "\t|\t" + "no minus neighbour");
			}
			
			if(p.getpNeigh() == null) {
				pointIssues.add(p.getId() + "\t|\t" + "Point" + "\t|\t" + "no plus neighbour");
			}
		}
		
		return pointIssues;
	}
	
	/**
	 * <p>Validates a list of sections. Checks that each section has at least one neighbour.</p>
	 * 
	 * @param sections A list of sections to validate.
	 * @return A list of issues with any problem sections.
	 */
	private ArrayList<String> ValidateSections(ArrayList<Section> sections){
		ArrayList<String> sectionIssues = new ArrayList<String>();
		
		//For each section, check if both neighbours are null. If so, log an issue.
		for(Section s : sections) {
			if(s.getUpNeigh() == null) {
				if(s.getDownNeigh() == null) {
					sectionIssues.add(s.getId() + "\t|\t" + "Section" + "\t|\t" + "must have at least one neighbour");
				}
			}
		}
		
		return sectionIssues;
	}
	
	/**
	 * <p>Validates a list of signals. Checks that each signal has two neighbours.</p>
	 * 
	 * @param signals The list of signals to validate.
	 * @return A list of issues with any problem signals.
	 */
	private ArrayList<String> ValidateSignals(ArrayList<Signal> signals){
		ArrayList<String> signalIssues = new ArrayList<String>();
		
		//For each signal, check for null neighbours and log if required.
		for(Signal s : signals) {
			if(s.getUpNeigh() == null) {
				signalIssues.add(s.getId() + "\t|\t" + "Signal" + "\t|\t" + "no up neighbour");
			}
			
			if(s.getDownNeigh() == null) {
				signalIssues.add(s.getId() + "\t|\t" + "Signal" + "\t|\t" + "no down neighbour");
			}
		}
		
		return signalIssues;
	}
}