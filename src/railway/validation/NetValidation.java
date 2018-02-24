package railway.validation;

import java.util.ArrayList;

import railway.network.Network;
import railway.network.Point;

/**
 * <p>Class to validate a network. Ensures neighbours aren't left null.</p>
 * 
 * @author Jay Kahlil Hussaini
 * Date created: 24/02/2018
 * Last updated: 24/02/2018
 *
 */
public class NetValidation {

	public ValidationInfo Validate(Network network) {
		ValidationInfo vInfo = new ValidationInfo();
		
		ArrayList<String> pointIssues = ValidatePoints(network.getPoints());
		
		//If there are no issues, the network is valid.
		if(pointIssues.isEmpty()) {
			vInfo.setValid(true);
		}
		
		return vInfo;
	}
	
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
}