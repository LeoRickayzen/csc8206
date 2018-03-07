package railway.draw;

import railway.network.Block;
import railway.network.Network;
import railway.network.Point;
import railway.network.Signal;

import java.util.ArrayList;

/**
 * Class that contains methods to set the layout of the network
 * 
 * @author Leo
 *
 */
public class NetworkRenderer {

    private ArrayList<railway.draw.Component> components;
    private Network network;
	private ArrayList<Point> points = new ArrayList<Point>();
    
	/**
	 * 
	 * @param network	the network to render
	 */
    public NetworkRenderer(Network network){
    	this.network = network;
    }
    
    /**
     * get a component by it's id
     * 
     * @param id	the id of the component to get
     * @return	the component
     */
    public Component getCompById(int id){
    	for(Component comp : components){
    		if(comp.getCompId() == id){
    			return comp;
    		}
    	}
    	return null;
    }
    
    /**
     * set the correct level and index for every component so it can snap to a grid,
     * Algorithm works by levelling the very end branches first and then working backwards in order to create
     * a nested branch structure where none overlap
     * 
     * @param block		the starting block
     * @param point		the point that y is joined to, parsed in order to set the level
     * @param backwards	set if the algorithm is now travelling backwards through the network
     * @param index		the current index
     * @param network	the network object
     * @param level		the level to start from
     */
    public void leveler(Block block, Point point, Boolean backwards, int index, Network network, int level){
    	//increment the level
    	level = level+1;
    	//set the top level of the previous point to be inline with this level
    	if(point != null){
    		point.setTopLevel(level);
        	System.out.println("point: " + point.getId() + " level:" + level);
    	}
    	//get the block that corresponds to the y Id
    	Boolean dontlevel = false;
    	//set levels of all components 'down' from this block
    	setLevels(block, backwards, index, level, dontlevel);
    	if(points.size() > 0){
    		//go to the last point in the list, and level from the upneighbor
        	Point nextPoint = points.get(points.size()-1);
        	points.remove(points.size()-1);
        	if(nextPoint.getmNeigh() != 0)
			{
				leveler(network.getBlock(nextPoint.getmNeigh()), nextPoint, nextPoint.isReverse(), nextPoint.getIndex() + 1, network, level);
			}
    	}	
    }
    
    /**
     * recursively iterate through a 'flat route of points' and set the level of them, for instance for network:
     *     ______
     * ___/______\_____
     * abc defghi jklmn
     * 
     * the path will be through the bottom level where the point splits and so will be 'abcdefhijklmn',
     * and all components on this path will be set to level 0
     * 
     * @param block		the current block
     * @param backwards	whether or not to travel backwards through the network
     * @param index		current index
     * @param level		current level
     * @param dontlevel	if set, no blocks are levelled, but they are still indexed
     */
    public void setLevels(Block block, Boolean backwards, int index, int level, Boolean dontlevel){
		if(!dontlevel){
	    	block.setLevel(level);
	    	System.out.println(block.getId() + ": " + level);
		}else{
	    	System.out.println(block.getId() + ": na");
		}
    	block.setIndex(index);
    	if(backwards){
    		index = index - 1;
		}else{
			index = index + 1;
		}
    	if(isPoint(block) && !((Point)block).isReverse()){
    		points.add((Point)block);
    	}
    	if(block.hasNext(backwards)){
    		//if the next block is a reverse block where the route will travel down the minus branch of the point, dont set the level, but set the top level to be this level
    		if(!(isPoint(network.getBlock(block.getNext(backwards))) && ((Point)network.getBlock(block.getNext(backwards))).getmNeigh() == block.getId())){
		     	Block nextBlock = network.getBlock(block.getNext(backwards));
		     	if(nextBlock.getClass() == Signal.class && block.getClass() == Signal.class){
		     		if(backwards){
		        		index = index + 1;
		    		}else{
		    			index = index - 1;
		    		}
		     	}
		     	setLevels(nextBlock, backwards, index, level, dontlevel);
		    }else{
		    	((Point)(network.getBlock(block.getNext(backwards)))).setTopLevel(level);
		    	dontlevel = true;
		    	Block nextBlock = network.getBlock(block.getNext(backwards));
		    	if(nextBlock.getClass() == Signal.class && block.getClass() == Signal.class){
		     		if(backwards){
		        		index = index + 1;
		    		}else{
		    			index = index - 1;
		    		}
		     	}
		     	setLevels(nextBlock, backwards, index, level, dontlevel);
		    }
    	}
    }
    
    /**
     * get a network component from the network object
     * 
     * @return	drawing of the network
     */
    public NetworkComp draw(LayoutController layoutController){
        leveler(network.getFirst(), null, false, 0, network, 0);
        NetworkComp netComp = new NetworkComp(
                100,
                layoutController.anchorPane.heightProperty().divide(2).doubleValue()
        );
        netComp.plot(network, layoutController);
        return netComp;
    }
    
    /**
     * check if block is a point
     * 
     * @param block
     * @return
     */
    private Boolean isPoint(Block block){
		return block.getClass() == Point.class;
    }    
}
