package railway.draw;

import javafx.beans.property.SimpleStringProperty;

public class Row {
	private SimpleStringProperty id;
	private SimpleStringProperty source;
	private SimpleStringProperty dest;
	private SimpleStringProperty points;
	private SimpleStringProperty signals;
	private SimpleStringProperty paths;
	private SimpleStringProperty conflicts;
	
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
	
	public int getId() {
		return Integer.parseInt(id.getValue());
	}
	
	public void setId(int id) {
		this.id.set(String.valueOf(id));
	}
	
	public int getSource() {
		return Integer.parseInt(source.getValue());
	}
	
	public void setSource(int source) {
		this.source.set(String.valueOf(source));
	}
	
	public int getDest() {
		return Integer.parseInt(dest.getValue());
	}
	
	public void setDest(int dest) {
		this.dest.set(String.valueOf(dest));
	}
	
	public String getPoints() {
		return points.getValue();
	}
	
	public void setPoints(String points) {
		this.points.set(points);
	}
	
	public String getSignals() {
		return signals.getValue();
	}
	
	public void setSignals(String signals) {
		this.signals.set(signals);
	}
	
	public String getPaths() {
		return paths.getValue();
	}
	
	public void setPaths(String paths) {
		this.paths.set(paths);
	}
	
	public String getConflicts() {
		return conflicts.getValue();
	}
	
	public void setConflicts(String conflicts) {
		this.conflicts.set(conflicts);
	}
	
}
