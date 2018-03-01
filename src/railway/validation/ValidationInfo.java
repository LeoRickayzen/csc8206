package railway.validation;

import java.util.ArrayList;

/**
 * <p>Simple information class with boolean for whether a network is valid or not and a list of issues with the network.</p>
 * 
 * @author Jay Kahlil Hussaini
 * Date created: 24/02/2018
 * Last updated: 28/02/2018
 */
public class ValidationInfo {
	private ArrayList<String> issues = new ArrayList<String>();
	private boolean valid = false;
	
	
	public boolean isValid() {
		return valid;
	}
	public void setValid(boolean valid) {
		this.valid = valid;
	}
	public ArrayList<String> getIssues() {
		return issues;
	}
	
	/**
	 * 
	 * @param newIssues A list of issues to be added to the master list for this info object.
	 */
	public void addIssues(ArrayList<String> newIssues) {
		issues.addAll(newIssues);
	}
	
	public String toString() {
		String info = "Valid: " + valid;
		for(String i : issues) {
			info = info.concat("\n" + i);
		}
		return info;
	}
	
}
