package railway.validation;

import java.util.ArrayList;

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
	
}
