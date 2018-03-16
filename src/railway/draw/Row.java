package railway.draw;

import javafx.beans.property.SimpleStringProperty;
/**
 * Represents a row in the conflict table
 *
 * @author Leo
 *
 */
public class Row {
	private SimpleStringProperty id;
	private SimpleStringProperty source;
	private SimpleStringProperty dest;
	private SimpleStringProperty points;
	private SimpleStringProperty signals;
	private SimpleStringProperty paths;
	private SimpleStringProperty conflicts;
	
	/**
	 * @param id the row id
	 * @param source source of the journey
	 * @param dest destination of the journey
	 * @param points the point setting string
	 * @param signals the signal setting string
	 * @param paths the string representation of the path through the network
	 * @param conflicts string list of the conflicts
	 */
	public Row(int id, int source, int dest, String points, String signals, String paths, String conflicts) {
		super();
		this.id = new SimpleStringProperty(String.valueOf(id));
		this.source = new SimpleStringProperty(String.valueOf(source));
		this.dest = new SimpleStringProperty(String.valueOf(dest));
		this.points = new SimpleStringProperty(points);
		this.signals = new SimpleStringProperty(signals);
		this.paths = new SimpleStringProperty(paths);
		this.conflicts = new SimpleStringProperty(conflicts);
	}
	
	/**
	 * get the id
	 * 
	 * @return id
	 */
	public int getId() {
		return Integer.parseInt(id.getValue());
	}
	
	/**
	 * set the id
	 * 
	 * @param id
	 */
	public void setId(int id) {
		this.id.set(String.valueOf(id));
	}
	
	/**
	 * get the source
	 * 
	 * @return source
	 */
	public int getSource() {
		return Integer.parseInt(source.getValue());
	}
	
	/**
	 * set the source
	 * 
	 * @param source
	 */
	public void setSource(int source) {
		this.source.set(String.valueOf(source));
	}
	
	/**
	 * get the destination
	 * 
	 * @return the destination
	 */
	public int getDest() {
		return Integer.parseInt(dest.getValue());
	}
	
	/**
	 * set the destination
	 * 
	 * @param dest
	 */
	public void setDest(int dest) {
		this.dest.set(String.valueOf(dest));
	}
	
	/**
	 * get the points settings
	 * 
	 * @return points
	 */
	public String getPoints() {
		return points.getValue();
	}
	
	/**
	 * set the points settings
	 * 
	 * @param points
	 */
	public void setPoints(String points) {
		this.points.set(points);
	}
	
	/**
	 * get the signal settings
	 * 
	 * @return signal settings
	 */
	public String getSignals() {
		return signals.getValue();
	}
	
	/**
	 * set the signal settings
	 * 
	 * @param signals
	 */
	public void setSignals(String signals) {
		this.signals.set(signals);
	}
	
	/**
	 * get the path string
	 * 
	 * @return
	 */
	public String getPaths() {
		return paths.getValue();
	}
	
	/**
	 * set the path
	 * 
	 * @param paths
	 */
	public void setPaths(String paths) {
		this.paths.set(paths);
	}
	
	/**
	 * get the conflicts
	 * 
	 * @return the string of conflicts
	 */
	public String getConflicts() {
		return conflicts.getValue();
	}
	
	/**
	 * set the conflicts
	 * 
	 * @param conflicts
	 */
	public void setConflicts(String conflicts) {
		this.conflicts.set(conflicts);
	}
	
}
