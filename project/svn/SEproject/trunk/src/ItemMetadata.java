/**
    @author Jack Lawrence
*/
import java.util.*;
/** Holds information about an item type. */
public class ItemMetadata {
    
    /** @name Instance Variables */
    
    /** The name of the item */
    private String _name;
    /** The item's price in cents */
    private int _price;
    /** Whether or not the item has been recalled */
    private boolean _recalled = false;
    
    /** @name Accessors & Mutators */
    
    public String name() {
        return _name;
    }
    
    public int price() {
        return _price;
    }
    
    public boolean isRecalled(){
    	return _recalled;
    }
    
    public void setRecalled(){
    	_recalled = true;
    }
    
    /** @name Constructors */
    //Main constructor. Price is in cents.
    public ItemMetadata(String name, int price) {
        _name = name;
        _price = price;
    }
	
	// Copy constructor.
	
	public ItemMetadata(ItemMetadata otherMetadata) {
	   _name = otherMetadata.name();
	   _price = otherMetadata.price();
	   _recalled = otherMetadata.isRecalled();
	}
}
