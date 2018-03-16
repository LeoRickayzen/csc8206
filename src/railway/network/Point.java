package railway.network;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * <p>Stores information about a Point.</p>
 * 
 */

public class Point extends Block{

	private boolean plus;
	private int mainNeigh;
	private int mNeigh; //minus Neighbour
	private int pNeigh; //plus Neighbour
	private boolean reverse;
	private int topHeight;
	private Direction travelDirection;

	public Point(){}

	// the constructor
	/**
	 * 
	 * @param id int ID of the Point.
	 * @param plus Boolean. If the Point is in Plus or Minus setting.
	 * @param mainNeigh int ID of the main neighbour of this Point.
	 * @param mNeigh int ID of the plus neighbour of this Point.
	 * @param pNeigh int ID of the minus neighbour of this Point.
	 * @param reverse boolean value, change of direction.
	 */
	public Point(int id, boolean plus, int mainNeigh, int mNeigh, int pNeigh, boolean reverse) {
		super(id);
		this.plus=plus;
		this.mainNeigh=mainNeigh;
		this.mNeigh=mNeigh;
		this.pNeigh=pNeigh;
		this.reverse=reverse;
	}
	/**
	 * to check the status of this Point (Plus or Minus)
	 * @return plus boolean, if it is plus return True.
	 */
	public boolean isPlus() {
		return plus;
	}
	/**
	 * to set this Point status (plus or minus) ID
	 * @param plus boolean.
	 */
	public void setPlus(boolean plus) {
		this.plus = plus;
	}
	/**
	 * to get the main neighbour of this Point.
	 * @return mainNeigh int, the ID of the main neighbour.
	 */
	public int getMainNeigh() {
		return mainNeigh;
	}
	/**
	 * to set the main neighbour of this Point. 
	 * @param mainNeigh int, the ID of the main neighbour.
	 */
	public void setMainNeigh(int mainNeigh) {
		this.mainNeigh = mainNeigh;
	}
	/**
	 * to get the minus neighbour of this Point.
	 * @return mNeigh int, the ID of the minus neighbour.
	 */
	public int getmNeigh() {
		return mNeigh;
	}
	/**
	 * to set the minus neighbour of this Point. 
	 * @param mNeigh int, the ID of the minus neighbour.
	 */
	public void setmNeigh(int mNeigh) {
		this.mNeigh = mNeigh;
	}
	/**
	 * to get the plus neighbour of this Point.
	 * @return pNeigh int, the ID of the plus neighbour.
	 */
	public int getpNeigh() {
		return pNeigh;
	}
	/**
	 * to set the plus neighbour of this Point. 
	 * @param pNeigh int, the ID of the plus neighbour.
	 */
	public void setpNeigh(int pNeigh) {
		this.pNeigh = pNeigh;
		
	}
	/**
	 * to check the reverse direction status of this Point.
	 * @return reverse boolean, if it is reversed return True.
	 */
	public boolean isReverse() {
		return reverse;
	}
	/**
	 * to set the reverse status and travel direction of this point. 
	 * @param reverse int, the ID of the plus neighbour.
	 */
	public void setReverse(boolean reverse) {
		this.reverse = reverse;
		this.travelDirection = (reverse ? Direction.DOWN : Direction.UP);
	}
	/**
	 * to get the top height of this Point.
	 * @return topHeight int.
	 */
	public int getTopHeight() {
		return topHeight;
	}
	/**
	 * to set the top level of this point. 
	 * @param topHeight int.
	 */
	public void setTopLevel(int topHeight) {
		this.topHeight = topHeight;
	}
	/**
	 * to check if this Point has neighbour.
	 * @return true if this point has a neighbour (has a mainNeigh if reverse is ture, or has pNeigh if if reverse is false), otherwise false.
	 */
	public Boolean hasNext(Boolean reverse){
		if(this.reverse){
			return mainNeigh != 0;
		}else{
			return pNeigh != 0;
		}
	}
	/**
	 * to get the next neighbour.
	 * @return  a mainNeigh ID (int) if reverse is ture, or pNeigh ID (int) if if reverse is false.
	 */
	public int getNext(Boolean reverse){
		if(this.reverse){
			return mainNeigh;
		}else{
			return pNeigh;
		}
	}
	/**
	 * to get the travel direction of this point.
	 * @return travelDirection Direction of this point.
	 */
	@JsonIgnore
	public Direction getTravelDirection() {
		return travelDirection;
	}
	/**
	 * to set the travel direction of this point. 
	 * @param direction Direction.
	 */

	@JsonIgnore
	public void setTravelDirection(Direction direction) {
		this.travelDirection = direction;
	}
	
	
}
