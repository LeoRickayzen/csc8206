package routeCalculation;

import java.util.ArrayList;
import java.util.HashMap;

import railway.network.Direction;
import railway.network.Network;
import railway.network.Point;
import railway.network.Route;
import railway.network.Section;
import railway.network.Signal;

public class RouteConflict {
	private ArrayList<Route> routes;
	private Network network;
	//key is a route ID and the value is a list of conflict routes ID
	private HashMap<Integer,ArrayList<Integer>> routesConflict;
	//key is a route ID and the value is a list of blocks ID (sections and points)
	private HashMap<Integer,ArrayList<Integer>> routePath;
	//key is a route ID and the value is a list of pointID+status (plus or minus)
	private HashMap<Integer,ArrayList<String>> routePointsSetting;
	//key is a route ID and the value is a list of blocks ID (Siganl that must set to STOP)
	private HashMap<Integer,ArrayList<Integer>> routeSignal;
	
	//constructor
	public RouteConflict(ArrayList<Route> routes,Network network) {
		this.routes=routes;
		this.network=network;
		this.routesConflict=new HashMap<Integer,ArrayList<Integer>>(); 
		this.routePath=new HashMap<Integer,ArrayList<Integer>>();
		this.routePointsSetting=new HashMap<Integer,ArrayList<String>>();
		this.routeSignal=new HashMap<Integer,ArrayList<Integer>>();
	}
	
	/**
     * calculate the conflicts between all routes
     *
     * @return    all routes along with a list of its conflicts
     */
	public HashMap<Integer,ArrayList<Integer>> calculateConflictRoute() {
		for(int i = 0; i<routes.size(); i++) {
		//take a route to compare its blocks with blocks of other routes
		Route route = routes.get(i);
		int currentRouteID=route.getRouteID();
		ArrayList<Integer> conflicts=new ArrayList<Integer>();
		
		for(Route secondRoute:this.routes) {
			if(currentRouteID != secondRoute.getRouteID()) {
				for(int blockID:routes.get(i).getBlocks()) {
					//if currentRoute shares a part (Section or Point) of the path with secondRoute
					if(secondRoute.getBlocks().contains(blockID)) {
						//checks if this block is of type section or point
						if(network.getBlock(blockID).getClass().equals(Section.class) || (network.getBlock(blockID).getClass().equals(Point.class)) ) { 
						if(!conflicts.contains(secondRoute.getRouteID()))
						conflicts.add(secondRoute.getRouteID());
						break;
						}
					}
				}
				
			}
		}
		routesConflict.put(currentRouteID, conflicts);
		}
		return routesConflict;
	}
	/**
     * calculate the path of all routes
     *
     * @return    all routes with all blocks on their path
     */
	public HashMap<Integer,ArrayList<Integer>> calculatePath() {
		for(int i = 0; i<routes.size(); i++) {
		Route route = routes.get(i);
		int currentRouteID=route.getRouteID();
		ArrayList<Integer> path=new ArrayList<Integer>();
				for(int blockID:routes.get(i).getBlocks()) {
					//the path of the route is all the sections and points from source to destination
						//checks if this block is of type section or point
						if(network.getBlock(blockID).getClass().equals(Section.class) || (network.getBlock(blockID).getClass().equals(Point.class)) ) { 
						if(!path.contains(blockID))
						path.add(blockID);
						}
					}
				
		routePath.put(currentRouteID, path);
		}
		return routePath;
	}
	
	     // for calculating point setting for each routes
		public HashMap<Integer,ArrayList<String>> calculatePointsSetting() {
			for(int i = 0; i<routes.size(); i++) {
			Route route = routes.get(i);
			//get the id of first route to calculate 'the point setting'
			int currentRouteID=route.getRouteID();
			ArrayList<String> pointSetting=new ArrayList<String>();
			        //to find all points within the route's path
					for(int blockID:routes.get(i).getBlocks()) {
							//checks if this block is of type 'point'
							if(network.getBlock(blockID).getClass().equals(Point.class)) { 
								Point point=(Point) network.getBlock(blockID);
							if((routes.get(i).getBlocks().contains(point.getmNeigh())) && (routes.get(i).getBlocks().contains(point.getMainNeigh()))) {
							pointSetting.add(blockID+":m");
							}else if((routes.get(i).getBlocks().contains(point.getpNeigh())) && (routes.get(i).getBlocks().contains(point.getMainNeigh()))) {
								pointSetting.add(blockID+":p");
							}
							}
					}
							//for up direction
							// check if up neighbour of the destination signal is point
						if((route.getDirection() == Direction.UP) && network.getBlock(route.getDestination().getUpNeigh()).getClass().equals(Point.class)) {
								Point point=(Point) network.getBlock(route.getDestination().getUpNeigh());
			
								//if the destination is a plus neighbour of the point, the setting has to be minus for protection
								if(route.getDestination().getId()== point.getpNeigh()) {
								if(!pointSetting.contains(route.getDestination().getUpNeigh()+":m"))
									pointSetting.add(route.getDestination().getUpNeigh()+":m");		
								}
								//if the destination is a minus neighbour of the point, the setting has to be plus for protection
								if(route.getDestination().getId()== point.getmNeigh()) {
									if(!pointSetting.contains(route.getDestination().getUpNeigh()+":p"))
										pointSetting.add(route.getDestination().getUpNeigh()+":p");
											
									}
									}
						
							//for down direction
							if((route.getDirection() == Direction.DOWN) && network.getBlock(route.getDestination().getDownNeigh()).getClass().equals(Point.class)) {
								Point point=(Point) network.getBlock(route.getDestination().getDownNeigh());
								//if the destination is a plus neighbour of the point, the setting has to be minus for protection
								if(route.getDestination().getId()== point.getpNeigh()) {
								if(!pointSetting.contains(route.getDestination().getDownNeigh()+":m"))
									pointSetting.add(route.getDestination().getDownNeigh()+":m");
								}
								//if the destination is a minus neighbour of the point, the setting has to be plus for protection
								if(route.getDestination().getId()== point.getmNeigh()) {
									if(!pointSetting.contains(route.getDestination().getDownNeigh()+":p"))
										pointSetting.add(route.getDestination().getDownNeigh()+":p");
									}
										
									} 
							
							//to find a point if the route's direction UP
							if((route.getDirection() == Direction.UP)) {
							int j=route.getDestination().getUpNeigh();
							while(network.getBlock(j)!= null && !network.getBlock(j).getClass().equals(Point.class)) {
								//------
								if(network.getBlock(j).getClass().equals(Section.class)) {
									Section section=(Section) network.getBlock(j);
									//----------------u
									if(network.getBlock(section.getUpNeigh())!= null && network.getBlock(section.getUpNeigh()).getClass().equals(Point.class) ) {
										Point point=(Point) network.getBlock(section.getUpNeigh());
										//if the section is a plus neighbour of the point, the setting has to be minus for protection
										if(network.getBlock(j).getId()== point.getpNeigh()) {
										if(!pointSetting.contains(point.getId()+":m")) {
											pointSetting.add(point.getId()+":m");
											j=section.getUpNeigh();
										}
										
									}
										if(network.getBlock(j).getId()== point.getmNeigh()) {
											if(!pointSetting.contains(point.getId()+":p")) {
												pointSetting.add(point.getId()+":p");
												j=section.getUpNeigh();
											}
								}
								}else j=section.getUpNeigh();
									}else if(network.getBlock(j).getClass().equals(Signal.class)) {
										Signal signal=(Signal) network.getBlock(j);
										if(network.getBlock(signal.getUpNeigh())!= null && network.getBlock(signal.getUpNeigh()).getClass().equals(Point.class)) {
											Point point=(Point) network.getBlock(signal.getUpNeigh());
											//if the section is a plus neighbour of the point, the setting has to be minus for protection
											if(network.getBlock(j).getId()== point.getpNeigh()) {
											if(!pointSetting.contains(point.getId()+":m")) {
												pointSetting.add(point.getId()+":m");
												j=signal.getUpNeigh();
											}
											
										}
											if(network.getBlock(j).getId()== point.getmNeigh()) {
												if(!pointSetting.contains(point.getId()+":p")) {
													pointSetting.add(point.getId()+":p");
													j=signal.getUpNeigh();
												}
									}
									}else j=signal.getUpNeigh();
									}
				
							}
					
							}//--
							
							// from here for DOWN direction
							//to find a point
							if((route.getDirection() == Direction.DOWN)) {
							int j=route.getDestination().getDownNeigh();
							while(network.getBlock(j)!= null && !network.getBlock(j).getClass().equals(Point.class)) {
								//------
								if(network.getBlock(j).getClass().equals(Section.class)) {
									Section section=(Section) network.getBlock(j);
									//----------------
									if(network.getBlock(section.getDownNeigh())!= null && network.getBlock(section.getDownNeigh()).getClass().equals(Point.class) ) {
										Point point=(Point) network.getBlock(section.getDownNeigh());
										//if the section is a plus neighbour of the point, the setting has to be minus for protection
										if(network.getBlock(j).getId()== point.getpNeigh()) {
										if(!pointSetting.contains(point.getId()+":m")) {
											pointSetting.add(point.getId()+":m");
											j=section.getDownNeigh();
										}
										
									}
										if(network.getBlock(j).getId()== point.getmNeigh()) {
											if(!pointSetting.contains(point.getId()+":p")) {
												pointSetting.add(point.getId()+":p");
												j=section.getDownNeigh();
											}
								}
								}else j=section.getDownNeigh();
									}else if(network.getBlock(j).getClass().equals(Signal.class)) {
										Signal signal=(Signal) network.getBlock(j);
										if(network.getBlock(signal.getDownNeigh())!= null && network.getBlock(signal.getDownNeigh()).getClass().equals(Point.class)) {
											Point point=(Point) network.getBlock(signal.getDownNeigh());
											//if the section is a plus neighbour of the point, the setting has to be minus for protection
											if(network.getBlock(j).getId()== point.getpNeigh()) {
											if(!pointSetting.contains(point.getId()+":m")) {
												pointSetting.add(point.getId()+":m");
												j=signal.getDownNeigh();
											}
											
										}
											if(network.getBlock(j).getId()== point.getmNeigh()) {
												if(!pointSetting.contains(point.getId()+":p")) {
													pointSetting.add(point.getId()+":p");
													j=signal.getDownNeigh();
												}
									}
									}else j=signal.getDownNeigh();
									}
				
							}
					
							}//----------------------------------------
			this.routePointsSetting.put(currentRouteID, pointSetting);
		}
			return this.routePointsSetting;
		}
	//----------------	
		/**
	     * calculate the Signal setting of all routes
	     *
	     * @return    all Signal that must set to STOP for all routes
	    */
		public HashMap<Integer,ArrayList<Integer>> calculateSignal() {
			for(int i = 0; i<routes.size(); i++) {
				Route route = routes.get(i);
				//get the id of first route to calculate 'the Signal setting'
				int currentRouteID=route.getRouteID();
				ArrayList<Integer> signalSetting=new ArrayList<Integer>();
				        //to find all Signal, that has an opposite direction, within the route's path
						for(int blockID:routes.get(i).getBlocks()) {
								//checks if this block is of type 'Signal'
								if(network.getBlock(blockID).getClass().equals(Signal.class)) { 
									Signal signal=(Signal) network.getBlock(blockID);
									// if the signal has an opposite direction to the route direction, add this signal to the 'STOP' list
								if(route.getDirection()== Direction.UP && signal.getDirection().equals("down")) {
									signalSetting.add(blockID);
								}else if(route.getDirection()== Direction.DOWN && signal.getDirection().equals("up")) {
									signalSetting.add(blockID);
								}
						}
								//if this block is point
								//checks if this block is of type 'point'
								if(network.getBlock(blockID).getClass().equals(Point.class)) { 
									Point point=(Point) network.getBlock(blockID);
									//if minus neighbour is not part of the route
								if(!(routes.get(i).getBlocks().contains(point.getmNeigh()))) {
								    if(network.getBlock(point.getmNeigh()).getClass().equals(Signal.class)) {
								    	signalSetting.add(point.getmNeigh());
								    }
								}else if(!(routes.get(i).getBlocks().contains(point.getpNeigh()))) {
									if(network.getBlock(point.getpNeigh()).getClass().equals(Signal.class)) {
								    	signalSetting.add(point.getpNeigh());
								    }
								}else if(!(routes.get(i).getBlocks().contains(point.getMainNeigh()))) {
									if(network.getBlock(point.getMainNeigh()).getClass().equals(Signal.class)) {
								    	signalSetting.add(point.getMainNeigh());
								    }
									
								}
								}
						}
						//---------------------- search after the destination from here
						//to find a point if the route's direction UP
						if((route.getDirection() == Direction.UP)) {
						int previous=route.getDestination().getId();	
						int j=route.getDestination().getUpNeigh();
						while(network.getBlock(j)!= null && !network.getBlock(j).getClass().equals(Signal.class)) {
							//------
							if(network.getBlock(j).getClass().equals(Section.class)) {
								Section section=(Section) network.getBlock(j);
								//----------------u
								if(network.getBlock(section.getUpNeigh())!= null && network.getBlock(section.getUpNeigh()).getClass().equals(Signal.class) ) {
									signalSetting.add(section.getUpNeigh());
									j=section.getUpNeigh();
								}else j=section.getUpNeigh();
							
								}else if(network.getBlock(j).getClass().equals(Point.class)) {
									Point point=(Point) network.getBlock(j);
									if(network.getBlock(point.getmNeigh())!= null && (previous!=point.getmNeigh()) && network.getBlock(point.getmNeigh()).getClass().equals(Signal.class)) {
										signalSetting.add(point.getmNeigh());
										j=point.getmNeigh();
									}else if(network.getBlock(point.getpNeigh())!= null && (previous!=point.getpNeigh()) && network.getBlock(point.getpNeigh()).getClass().equals(Signal.class)) {
										signalSetting.add(point.getpNeigh());
										j=point.getpNeigh();
									}else if(network.getBlock(point.getMainNeigh())!= null && (previous!=point.getMainNeigh()) && network.getBlock(point.getMainNeigh()).getClass().equals(Signal.class)) {
										signalSetting.add(point.getMainNeigh());
										j=point.getMainNeigh();
									} else {
										previous=point.getId();
									}
								}
			
						}
				
						}//--
						
			routeSignal.put(currentRouteID,signalSetting);
			}
			return routeSignal;
			
		}//--------  
		
	//	public Signal findSignal(int BlockID) {
			
	//		return  ;
	//	} 
		
}
		
		
		
		
		

	


