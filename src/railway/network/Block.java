package railway.network;


import com.fasterxml.jackson.annotation.JsonIgnore;
/**
 * <p>Stores information about a Block.</p>
 * 
 */

public class Block {
	private int id;
	private int level;
	private int index;

	Block(){}

	//constructor
	/**
	 * 
	 * @param id int ID of the Block
	 */
	Block(int id) {
		this.id=id;
	}
	
	/**
	 * to set block ID
	 * @param id int ID of the Block
	 */
	public void setId(int id) {
		  this.id=id;
		  
	  }
	 
	/**
	 * to get block ID
	 * @return id int ID of the Block
	 */
	public int getId() {
		  return id;
	  }

	/**
	 * <p>to check if it has next block ID</p>
	 * 
	 * <p>NOTE! Get's overwritten in {@link Section}, {@link Signal}, and {@link Point} so this isn't as insane as it looks.</p>
	 *
	 * @param reverse Boolean 
	 * @return ture
	 */
	@JsonIgnore
	public Boolean hasNext(Boolean reverse){
		//What an unhelpful function
		return true;
	}
	
	/**
	 * <p>to check if it has next block ID</p>
	 *
	 * <p>NOTE! Get's overwritten in {@link Section}, {@link Signal}, and {@link Point} so this isn't as insane as it looks.</p>
	 *
	 * @param reverse Boolean 
	 * @return 0
	 */
	@JsonIgnore
	public int getNext(Boolean reverse){
		return 0;
	}
	/**
	 * to get block level
	 * 
	 * @return the block level
	 */
	@JsonIgnore
	public int getLevel() {
		return level;
	}
	/**
	 * to set block level
	 * 
	 * @param the block level
	 */
	@JsonIgnore
	public void setLevel(int level) {
		this.level = level;
	}
	/**
	 * to set block index
	 * 
	 * @param the block index
	 */
	@JsonIgnore
	public void setIndex(int index){
		this.index = index;
	}
	/**
	 * to get block index
	 * 
	 * @return the block index
	 */
	@JsonIgnore
	public int getIndex(){
		return index;
	}

}

