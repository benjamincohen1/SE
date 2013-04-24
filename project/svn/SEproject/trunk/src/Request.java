/**
 * Data holder for a restocker request.
 * 
 * @author Ian Frasch (ijf1860@rit.edu)
 */
public class Request {
	
	//Vending machine Name for request
	private String _machineName;
	// Row of request
	private int _row;
	// Column of request
	private int _col;
	// Name of item;
	private String _itemName;
	// Quantity of items to restock or remove
	private int _quantity;
	// Action for restocker to do; "restock" or "remove"
	private String _action;
	
	// Amount of items that were restocked or removed (used to relay information back to the manager)
	private int _quantityCompleted;
	
	public Request(String machineName, int row, int col, String itemName, int quantity, String action, int quantityCompleted){
		_machineName = machineName;
		_row = row;
		_col = col;
		_itemName = itemName;
		_quantity = quantity;
		_action = action;
		_quantityCompleted = quantityCompleted;
	}
	
	public String getMachineName(){
		return _machineName;
	}
	
	public int getRow(){
		return _row;
	}
	
	public int getCol(){
		return _col;
	}
	
	public String getItemName(){
		return _itemName;
	}
	
	public int getQuantity(){
		return _quantity;
	}
	
	public String getAction(){
		return _action;
	}
	
	public int getQuantityCompleted(){
		return _quantityCompleted;
	}
	
	public void setQuantityCompleted(int quantity){
		_quantityCompleted = quantity;
	}
	
	public String toString(){
		return (_machineName + " " + _action + " " + _quantity + " " + _itemName + 
				" at row " + _row + " column " + _col);
	}
	
	public String toFileString(){
		return toString() + " completed " + _quantityCompleted;
	}

}
