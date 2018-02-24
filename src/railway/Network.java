import java.util.ArrayList;

public class Network {
	ArrayList<Signal> signals;
	ArrayList<Section> sections;
	ArrayList<Point> points;
	
	//Constructor
	public Network() {
		signals=new ArrayList<Signal>();
		sections=new ArrayList<Section>();
		points=	new ArrayList<Point>();	
		
	}
	
	    //Constructor
		public Network(ArrayList<Signal> signals,ArrayList<Section> sections, ArrayList<Point> points) {
			this.signals=signals;
			this.sections=sections;
			this.points=points;	
		}
		
	    /**
	     * set a signal list
	     *
	     * @param  signals  new signal list
	     */
		public void setSignals(ArrayList<Signal> signals) {
			this.signals=signals;
		}
		
	    /**
	     * get a signal list
	     *
	     * @return    a list of signal
	     */
		public ArrayList<Signal> getSignals(){
			return this.signals;
		}
		
		/**
	     * add signal to a network 
	     *
	     * @param  signal  new signal
	     * @return true if it is added successfully
	     */
		public boolean addSignalToNetwork(Signal signal) {
			return this.signals.add(signal);
		}
		/**
	     * remove signal from a network 
	     *
	     * @param  signal  existing signal
	     * @return true if it is removed successfully
	     */
		public boolean removeSignalFromNetwork(Signal signal) {
			return this.signals.remove(signal);
		}
		
		/**
	     * set a section list
	     *
	     * @param  sections  new section list
	     */
		public void setSections(ArrayList<Section> sections) {
			this.sections=sections;	
		}
		/**
	     * get a section list
	     *
	     * @return    a list of section
	     */
		public ArrayList<Section> getSections(){
			return this.sections;
		}
		
		/**
	     * add section to a network 
	     *
	     * @param  section  new section
	     * @return true if it is added successfully
	     */
		public boolean addSectionToNetwork(Section section) {
			return this.sections.add(section);
		}
		/**
	     * remove section from a network 
	     *
	     * @param  section  existing section
	     * @return true if it is removed successfully
	     */
		public boolean removeSectionFromNetwork(Section section) {
			return this.sections.remove(section);
		}
		/**
	     * set a point list
	     *
	     * @param  points  new point list
	     */
		public void setPoints(ArrayList<Point> points) {
			this.points=points;
		}
		/**
	     * get a point list
	     *
	     * @return    a list of point
	     */
		public ArrayList<Point> getPoints(){
			return this.points;
		}
		/**
	     * add point to a network 
	     *
	     * @param  point  new point
	     * @return true if it is added successfully
	     */
		public boolean addPointToNetwork(Point point) {
			return this.points.add(point);
		}
		
		/**
	     * remove point from a network 
	     *
	     * @param  point  existing point
	     * @return true if it is removed successfully
	     */
		public boolean removePointFromNetwork(Point point) {
			return this.points.remove(point);
		}
		
		
		
		
		
		
		
		

}
