/**
    @author Jack Lawrence
*/
import java.util.*;
/** Holds the quantity of a specific item type in a sale. */
public class LineItem {
    
    /** @name Instance Variables */
    
    /** The metadata describing the item type. */
    private ItemMetadata _metadata;
    /** The quantity of an item type purchased */
    private int _quantity;
    
    /** @name Accessors & Mutators */
    
    public ItemMetadata metadata() {
        return _metadata;
    }
    
    public int quantity() {
        return _quantity;
    }
    
    /** @name Constructors */
    
    public LineItem(ItemMetadata metadata, int quantity) {
        _metadata = metadata;
        _quantity = quantity;
    }
	public String getString(){
        return _metadata.name()+" "+_metadata.price()+" "+_quantity;
    }
}
