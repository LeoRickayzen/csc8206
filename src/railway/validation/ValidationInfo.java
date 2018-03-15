package railway.validation;

import railway.network.Network;

import java.util.ArrayList;

/**
 * <p>Simple information class with boolean for whether a {@link Network} is valid or not and a list of any issues with the network.</p>
 * 
 * @author Jay Kahlil Hussaini
 * Date created: 24/02/2018
 * Last updated: 28/02/2018
 */
public class ValidationInfo {
	private ArrayList<String> issues = new ArrayList<String>();
	private boolean valid = false;
	
	/**
	 * 
	 * @return Boolean indicating if the {@link Network} is valid.
	 */
	public boolean isValid() {
		return valid;
	}
	
	/**
	 * 
	 * @param valid Boolean indicating if the {@link Network} is to be set to valid.
	 */
	public void setValid(boolean valid) {
		this.valid = valid;
	}
	
	/**
	 * 
	 * @return The master list of issues for this ValidationInfo object.
	 */
	public ArrayList<String> getIssues() {
		return issues;
	}
	
	/**
	 * <p>Adds a given ArrayList of issue Strings to this ValidationInfo's master issue list.</p>
	 * 
	 * @param newIssues A list of issues to be added to the master list for this info object.
	 */
	public void addIssues(ArrayList<String> newIssues) {
		issues.addAll(newIssues);
	}
	
	/**
	 * <p>Valid: true/false</p>
	 * <p>Issue 1</p>
	 * <p>Issue 2</p>
	 * <p>...</p>
	 * <p>Issue n</p>
	 * 
	 * @return A String which indicates whether or not the {@link Network} is valid and if it is not a list of issues.</p>
	 */
	public String toString() {
		String info = "Valid: " + valid;
		for(String i : issues) {
			info = info.concat("\n" + i);
		}
		return info;
	}
	
}
