/**
    @author Jack Lawrence
*/
import java.util.*;
import java.io.*;
/** 
    Performs item queueing and dequeueing operations.

    Takes into consideration the expiration dates of the items of a specific item type that it holds.
*/
public class Slot {

    /** @name Instance Variables */
    
    /** The metadata describing the item type. */
    private ItemMetadata _metadata;
    /** A queue of expiration dates where the front-most item is the next item to be dispensed by the vending machine. */
    private ArrayList<Date> _expirationDates;
    
    /** @name Accessors & Mutators */
    
    public ItemMetadata metadata() {
        return _metadata;
    }
    
    public int getPrice(){
    	return _metadata.price();
    }
    
    public String getName(){
    	return _metadata.name();
    }
    public ArrayList<Date> getExpDates(){
        return _expirationDates;
    }
    /** Setter function setItemMetadata: Changes the ItemMetadata with a given ItemMetaData **/
    public void setItemMetadata(ItemMetadata newData){
    	this._metadata = newData;
    }
    
    //This will need to be removed 
    
    /** Setter function setExpirationDates: Changes the current list of Expiration Dates with a given set. **/
    public void setExpirationDates(ArrayList<Date> newExpirationDates){
    	this._expirationDates = newExpirationDates;
    }
    
    /** Removes an item from the front of the slot.
    
        @return The expiration date of the item.
     */
    public Date popItem(){
    	if (!_expirationDates.isEmpty()) { //Checks to see if the there is nothing in the slot
    		return _expirationDates.remove(0);
    	}
    	else{
    		System.out.println("The slot is empty, there are not items be dispensed.");
    	}
    	
    	return null;
    }
    
    /** Adds an item to the back of the slot.
     * 
     * @param 	expirationDate	expirationDate of item to be added.
     */
    public void addItem(Date expirationDate){
    	_expirationDates.add(expirationDate);
    }
    
    /**
     * Checks the slot to see if the quantity of the items asked for is not expired.
     * @param currentDate: The current date, given by the Vending Machine.
     * @param quantity: Number of items to check from front of the vending machine.
     * @return boolean: If number of items can be purchased from the row.
     * 
     * Precondition: Quantity given is LESS THAN OR EQUAL to the quantity in the machine.
     */
    public boolean canPurchase(Date currentDate, int demand){
    	if (_metadata.isRecalled())		// check for recalled item
    		return false;
    	for(int index = 0; index < demand; index++){	//check expiration dates
    		if(_expirationDates.get(index).before(currentDate)){
    			return false;
    		}
    	}
    	//Read recalled items file
    	try{
    	File recalledItemsFile = new File("RecalledItems.txt");
    	FileReader r = new FileReader(recalledItemsFile);
		BufferedReader br = new BufferedReader(r);
		String line;
		String recalledItem;
		
		while((line = br.readLine()) != null){
			String [] textOnLine = line.split(" ");
			recalledItem = textOnLine[0];
			if (_metadata.name().equalsIgnoreCase(recalledItem)){
				System.err.println("Item has been recalled.");
				return false;
			}
		}	
    	} catch (FileNotFoundException e){
    		System.err.println("File RecalledItems.txt not found");
    	} catch (IOException e) {
			e.printStackTrace();
		}
    	return true;
    }
    
    /** @name Derived Properties */
    
    /** The number of items available in the slot. */
    public int quantity() {
        return _expirationDates.size();
    }
    
    /** @name Constructors */
    
    public Slot(ItemMetadata metadata, ArrayList<Date> expirationDates) {
        _metadata = metadata;
        _expirationDates = expirationDates;
    }
    
    // Empty Slot constructor
    public Slot(){
    	_metadata = null;
    	_expirationDates = new ArrayList<Date>();
    }
    
    // Slot constructor. Uses a metadata to create a slot, but the array itself is empty.
    public Slot(ItemMetadata metadata){
    	_metadata = metadata;
    	_expirationDates = new ArrayList<Date>();
    }
    
    // copy constructor
    
    public Slot(Slot otherSlot) {
        _expirationDates = new ArrayList<Date>(otherSlot._expirationDates);
        _metadata = new ItemMetadata(otherSlot._metadata);
    }
	
}
