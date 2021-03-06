import java.util.*;
import java.io.*;
/**
 * 
 * @author Ian Frasch (ijf1860@rit.edu)
 */
 @SuppressWarnings("deprecation") // no fucks given.  Not even the one
public class VendingMachine {

	/** Keeps track of time using a Date object*/
	private Date time;
	/** Represents the vending machine layout of slots. (row, col) format. */
	private ArrayList<ArrayList<Slot>> slots;
	/** The number of rows in the vending machine. */
	private int _rows;
	/** The number of columns in the vending machine. */
	private int _columns;
	/** The depth of a slot in the vending machine. */
	private int _depth;
	/** The name/identifier of the machine. */
	private String _name;
	
	//Getter Functions
	public int width() {
		return _columns;
	}
	
	public int height() {
	   return _rows;
	}
	
	public int depth() {
	   return _depth;
	}
	
	public String name() {
	   return _name;
	}
	
	public Date date(){
		return time;
	}
	
	/**Constructor
	 * @param	rows	number of rows
	 * 			cols	number of columns
	 * 			depth	maximum number of items
	 * 					that can fit in a slot
	 *          name    The name/identifier of 
	 *                  the machine.
	 * */
	public VendingMachine(int rows, int cols, int depth, String name) { 
		slots = new ArrayList<ArrayList<Slot>>();
		_rows = rows;
		_columns = cols;
		_depth = depth;
		_name = name;
		//Initialize each slot with null until an actual slot with item metadata gets
		//instantiated and put there. This way the size of the vending machine gets fixed.
		for (int row = 0; row < rows; row++){
			ArrayList<Slot> rowOfSlots = new ArrayList<Slot>(); //create a row
			for (int col = 0; col < cols; col++){				
				rowOfSlots.add(null);							//fill with null
			}
			slots.add(rowOfSlots);								//add row to the 2D array
		}
		time = new Date(); //Initialize the simulated time with the actual current time 
						  //(measured to the nearest millisecond)
        try {
            BufferedWriter bf = new BufferedWriter(new FileWriter(new File(name+"Metadata.txt")));
            bf.write(name+" "+rows+" "+cols+" "+depth+" "+name+"Data.txt");
            bf.flush();
            bf.close();
            BufferedWriter bf1= new BufferedWriter(new FileWriter(new File(name+"Data.txt")));
            bf1.flush();
            bf1.close();
        }
        catch (IOException ex) {
            System.err.println(String.format("Failed to persist the VendingMachine to disk: %s", ex.getMessage()));
        }
	}
	public void updateFlatfile() throws IOException, FileNotFoundException{
		File f = new File(_name+"Data.txt");
		FileWriter w = new FileWriter(f);
		BufferedWriter bw = new BufferedWriter(w);

		for (int y = 0;y<_columns;y++){
			for (int x = 0;x<_rows;x++){
				if (slots.get(x).get(y)!=null){
					Slot item = slots.get(x).get(y);
					int count = 0;
					ArrayList<Date> expDates = item.getExpDates();
					Date curDate=new Date();
					boolean hasDate=false;
					try{
						curDate = expDates.get(0);
						hasDate=true;
					}
					catch(IndexOutOfBoundsException ex){
						//throw new IndexOutOfBoundsException("We should never see this.");
					}
					if(hasDate){
						for (Date d : expDates){
						if(d.compareTo(curDate)==0){
							count++;
						}
						else{
							bw.write(x+" "+y+" "+item.getName()+" "+item.getPrice()+" "+curDate.getDay()+"/"+curDate.getMonth()+"/"+curDate.getYear()+" "+count);
							bw.newLine();
							curDate=d;
							count = 0;
						}

					}
					bw.write(x+" "+y+" "+item.getName()+" "+item.getPrice()+" "+curDate.getDay()+"/"+curDate.getMonth()+"/"+curDate.getYear()+" "+count);
					bw.newLine();					
				}
					
				}
			}
		}
		bw.flush();
		bw.close();
	}
	public VendingMachine(File f) throws IOException, FileNotFoundException{ 
		File popFile=new File("");

		try{
			FileReader w = new FileReader(f);
			BufferedReader br = new BufferedReader(w);
			String line;
			while((line = br.readLine()) != null){
				String [] ns=line.split(" ");
				_rows=Integer.parseInt(ns[1]);
				_columns=Integer.parseInt(ns[2]);
				_depth=Integer.parseInt(ns[3]);
				_name=ns[0].replaceAll("_"," ");
				popFile = new File(ns[4].replaceAll("_"," "));
			}
		}
		catch(IOException ex){
			throw new IOException("You needz a file");
		}
		
		slots = new ArrayList<ArrayList<Slot>>();

		//Initialize each slot with null until an actual slot with item metadata gets
		//instantiated and put there. This way the size of the vending machine gets fixed.
		for (int row = 0; row < _rows; row++){
			ArrayList<Slot> rowOfSlots = new ArrayList<Slot>(); //create a row
			for (int col = 0; col < _columns; col++){				
				rowOfSlots.add(null);							//fill with null
			}
			slots.add(rowOfSlots);								//add row to the 2D array
		}
		time = new Date(); //Initialize the simulated time with the actual current time 
						  //(measured to the nearest millisecond)
		populateFromFile(popFile);
	}
	public void writeRestockerNotes(String s) throws IOException,FileNotFoundException{
		try{
			BufferedWriter bf = new BufferedWriter(new FileWriter(new File(_name+"RestockerNotes.txt")));
			bf.write(s);
			bf.flush();
			bf.close();
		}
		catch(Exception ex){
			//throw something
		}


	}
	public void populateFromFile(File f) throws IOException, FileNotFoundException{

		try{
			FileReader w = new FileReader(f);
			BufferedReader br = new BufferedReader(w);
			String line;
			while((line = br.readLine()) != null){
				String [] ns=line.split(" ");

				ItemMetadata m = new ItemMetadata(ns[2],Integer.parseInt(ns[3]));
				updateMetadataForSlot(Integer.parseInt(ns[0]),Integer.parseInt(ns[1]),m);
				for(int x = 0;x<Integer.parseInt(ns[5]);x++){
					String [] d = ns[4].split("/");
					Date expDate = new Date(Integer.parseInt(d[2]),Integer.parseInt(d[1]),Integer.parseInt(d[0]));
					slots.get(Integer.parseInt(ns[0])).get(Integer.parseInt(ns[1])).addItem(expDate);
				}
			}	
		}
		catch(FileNotFoundException fnf){
			throw new FileNotFoundException("File not found");
		}
		catch(IOException ex){
			throw new IOException("File not found");
		}

	}
	/**
	   Retrieve (but do not remove or mutate) a copy of the slot at the provided coordinates.
	   
	   Returns a copy of the slot to ensure that the vending machine's state is not unknowingly 
	   modified by the caller.
	   
	   @param row The row of the slot to retrieve.
	   @param column The column of the slot to retrieve.
	   
	   @return A copy of the slot at the provided coordinates.
	*/
	public Slot slotAtCoordinates(int row, int column) {
	   try {
	       if (slots.get(row).get(column) == null){
			   return null;	//return null if no slot has been initialized at the specified coordinates
		   }
		   return new Slot(slots.get(row).get(column));
       }
       catch (IndexOutOfBoundsException e) {
           return null;
       }
	}
	
	/** Determine whether or not the specified quantity of an item at the specified coordinates
	 *  can be dispensed.
	 * @param   row 		row of slot to dispense from
	 * 			column		column of slot to dispense from
	 * 			quantity	number of items to dispense from slot
	 * @return Whether or not the quantity of items can be dispensed.
	 */
	public boolean canDispenseItem(int row, int column, int quantity) {
		Slot checkSlot = slotAtCoordinates(row, column);
		if (checkSlot == null){
			return false;
		}
		if (checkSlot.quantity() < quantity){
			return false;
		}
		if (!checkSlot.canPurchase(time, quantity)){
			return false;
		}
	   return true;
	}
	
	/** Dispense(remove) an item from the machine, update flat file.
	 * @param   row 		row of slot to dispense from
	 * 			column		column of slot to dispense from
	 * 			quantity	number of items to dispense from slot
	 * */
	public void dispenseItem(int row, int column, int quantity) {
		for (int i = 0; i < quantity; i++){
			slots.get(row).get(column).popItem(); // pop an Item from the specified slot
		}
		//TODO: modify contents the flat file to reflect change (how will this file be set up?)
	}
	
	/** Update the metadata for a slot in the machine.
	 * @param   row 		row of slot to update
	 * 			column 		column of slot to update
	 * 			metadata	metadata to use
	 * */
	public void updateMetadataForSlot(int row, int column, ItemMetadata metadata){
		if (slots.get(row).get(column)==null){
			Slot s = new Slot();
			addSlot(row,column,s);
			slots.get(row).get(column).setItemMetadata(metadata);
		}

				

	}
	
	/** Add Items to the back of a slot in the machine.
	 * @param   row 		row of slot to add Items to
	 * 			column 		column of slot to add Items to
	 * 			expirationDates 	array of Date objects
	 * */
	public void addItemsToSlot(int row, int column, ArrayList<Date> expirationDates){
		Slot slot = slots.get(row).get(column);
		
		if ((slot.quantity() + expirationDates.size()) >= _depth){	//Check to see if slot does not have enough space
			System.err.println("There is not enough room in the slot to add these items.");
		}
		else{
			for (Date expirationDate : expirationDates){
				slot.addItem(expirationDate);
			}
		}
	}
	
	/** Remove all items from a slot
	 * @param   row 		row of slot to be removed
	 * 			column 		column of slot to be removed
	 * */
	public void removeAllItemsFromSlot(int row, int column){
		//set expiration dates to an empty arraylist of dates
		slots.get(row).get(column).setExpirationDates(new ArrayList<Date>());
	}
	
	/** Swap two slots
	 * @param   row1 		row of first slot
	 * 			column1 	column of first slot
	 * 			row2 		row of second slot
	 * 			column2 	column of second slot
	 * */
	public void swapSlots(int row1, int column1, int row2, int column2){
		Slot firstSlot = slots.get(row1).get(column1);
		Slot secondSlot = slots.get(row2).get(column2);
		
		slots.get(row1).set(column1, secondSlot);
		slots.get(row2).set(column2, firstSlot);
	}
	
	public void addSlot(int row, int column, Slot slotToAdd){
		if (slots.get(row).get(column) == null)
			slots.get(row).set(column, slotToAdd);
		else
			System.err.println("There is already a slot at row " + row + ", column " + column + 
								". Try updating metaData/removing items instead of adding a new slot.");
	}
	
	// These need to be implemented, we don't know what they are yet
	public void GetAnalytics() {
	}
	
	public void GenerateSalesReport() {
		
	}
}