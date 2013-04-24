
public class Request {
	
	// Row of request
	private int _Row;
	// Column of request
	private int _Col;
	// Depth of request
	private int _Depth;
	
	// Name of product;
	private String _Name;
	// Restock or Remove, true for Restock, false for Remove
	private boolean _restock_remove;
	
	public Request(int row, int col, int depth, String name, boolean r_r){
		_Row = row;
		_Col = col;
		_Depth = depth;
		_Name = name;
		_restock_remove = r_r;
	}
	
	public int getRow(){
		return _Row;
	}
	
	public int getCol(){
		return _Col;
	}
	
	public int getDepth(){
		return _Depth;
	}
	
	public String getName(){
		return _Name;
	}
	
	public boolean getReason(){
		return _restock_remove;
	}

}
