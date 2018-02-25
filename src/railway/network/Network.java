package railway.network;

import java.util.ArrayList;
import java.util.UUID;

public class Network {
	ArrayList<Signal> signals;
	ArrayList<Section> sections;
	ArrayList<Point> points;

	public Component getFirst(){
		for(int i = 0; i < sections.size(); i++){
			if(sections.get(i).downNeigh == null){
				return sections.get(i);
			}
		}
		return null;
	}

	public Component getComp(UUID id){
		for(int i = 0; i < sections.size(); i++){
			if(sections.get(i).getId().equals(id)){
				return sections.get(i);
			}
		}
		for(int i = 0; i < signals.size(); i++){
			if(signals.get(i).getId().equals(id)){
				return signals.get(i);
			}
		}
		for(int i = 0; i < points.size(); i++){
			if(points.get(i).getId().equals(id)){
				return points.get(i);
			}
		}
		return null;
	}

	public ArrayList<Signal> getSignals() {
		return signals;
	}

	public ArrayList<Section> getSections() {
		return sections;
	}

	public ArrayList<Point> getPoints() {
		return points;
	}
}
