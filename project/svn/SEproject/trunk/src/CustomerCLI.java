/**
    @author Jack Lawrence
*/
import java.io.*;
import java.util.*;
import javax.xml.*;
import org.w3c.dom.*;
@SuppressWarnings("deprecation")
/** The customer command line interface. */
public class CustomerCLI extends CommandLineInterface {

    /** Encapsulates a row and a column. */
    private static class CoordinatePair {
        public int row;
        public int column;
        
        public CoordinatePair(int aRow, int aColumn) {
            row = aRow;
            column = aColumn;
        }
        
        @Override
        public boolean equals(Object other) {
            if (other.getClass().isInstance(this)) {
                CoordinatePair pairObject = (CoordinatePair)other;
                return (pairObject.row == row && pairObject.column == column);
            }
            return false;
        }
        
        @Override
         public int hashCode() {
            return row*1000+column; // shift row over a bunch to separate it from column.
         }
    }

    /** @name Instance Variables */
    
    /** The vending machine */
    private static VendingMachine _vendingMachine;
    /** 
        The transaction list. Keys are CoordinatePair objects and values are Integer objects. 
        The CoordinatePair corresponds to the coordinates of the Slot and the Integer
        corresponds to the quantity of items to be dispensed from the Slot.
        
        Using a LinkedHashMap makes it easy to combine multiple requests for
        the same item into one datapoint. LinkedHashMap objects preserve insertion order. 
    */
    private static LinkedHashMap<CoordinatePair, Integer> _currTransaction;
    
    /** @name Accessors & Mutators */
    
    private static VendingMachine vendingMachine() {
        return _vendingMachine;
    }
    
    /**
        Print a formatted grid of the items in the vending machine.
    */
    private static void display() {
        if (_currTransaction == null) {
            beginTransaction();
        }
        for (int row = 0; row < vendingMachine().height(); row++) {
            for (int column = 0; column < vendingMachine().width(); column++) {   	
                Slot currSlot = vendingMachine().slotAtCoordinates(row, column);
                if (currSlot == null) //check to see if this slot is null (meaning no slot object has been initialized)
                	System.out.print(String.format("[%s: %.2f (%d)]\t", "Empty", 0.00, 0));
                else
                	System.out.print(String.format("[%s: %.2f (%d)]\t", currSlot.metadata().name(), currSlot.metadata().price()/100.0, currSlot.quantity()));
            }
            System.out.println();
            for (int column = 0; column < _vendingMachine.width(); column++){   //display item number
            	System.out.print("\t"+(row+1)+""+(column+1) + "\t\t");
            }
            System.out.println();
        }
    }
    
    /**
        Begin a transaction.
    */
    private static void beginTransaction() {
        _currTransaction = new LinkedHashMap<CoordinatePair, Integer>();
    }
    
    /**
        Cancel a transaction. Begins a new transaction afterwards.
    */
    private static void cancelTransaction() throws IOException{
        beginTransaction();
    }
    
    /**
        Finish paying for a transaction.
        
        Creates an array of LineItem objects using the data in _currTransaction
        and then adds the LineItem objects to a new Sale object with the current 
        date. Archives the sale to disk.
        
        Clears the transaction after it's finished and begins a new one.
    */
    private static void finishPayment(String name, VendingMachine vendingMachine) throws IOException{
        try{
            if (_currTransaction != null) {
                File f = new File(name+"_log.txt");
                PrintWriter w=new PrintWriter(new BufferedWriter(new FileWriter(f, true))); 
                ArrayList<LineItem> lineItems = new ArrayList<LineItem>();
                for (Map.Entry<CoordinatePair, Integer> entry : _currTransaction.entrySet()) {
                    CoordinatePair pair = entry.getKey();
                    Integer quantity = entry.getValue();
                    if (quantity > 0) {
                        //vendingMachine().dispenseItem(pair.row, pair.column, quantity);
                        //Commented out the above because things were being removed twice
                        Slot slot = vendingMachine().slotAtCoordinates(pair.row, pair.column);
                        LineItem lineItem = new LineItem(slot.metadata(), quantity);
                        lineItems.add(lineItem);
                    }
                }
                Date currentDate = new Date();
                Sale sale = new Sale(currentDate, lineItems);
                w.println(sale.getLogStringRepresentation());
                w.close();
                _currTransaction = null;
            }
        }
        catch(IOException ex){
            throw new IOException("Blah Blah Blah");
        }
        vendingMachine.updateFlatfile();

        beginTransaction();
    }
    
    /**
        List all of the items currently in the user's cart (the current transaction).
    */
    private static void listItemsInCart() {
        if (_currTransaction != null && _currTransaction.size() != 0) {
            double total = 0.0;
            int index = 0;
            for (Map.Entry<CoordinatePair, Integer> entry : _currTransaction.entrySet()) {
                CoordinatePair pair = entry.getKey();
                Integer quantity = entry.getValue();
                if (quantity > 0) {
                    index++;
                    Slot slot = vendingMachine().slotAtCoordinates(pair.row, pair.column);
                    total += (slot.metadata().price()*quantity)/100.0;
                    System.out.println(String.format("%d. %s %.2f (%d): %.2f", index, slot.metadata().name(), slot.metadata().price()/100.0, quantity, (slot.metadata().price()*quantity)/100.0));
                }
            }
            System.out.println("-------------------------------------------------------------------");
            System.out.println(String.format("Total: %.2f", total));
        }
        else {
            System.out.println("Cart is empty.");
        }
    }
    
    /**
        Add an item to the user's cart (the current transaction).
        
        @param number The number of the slot in the vending machine.
        @param quantity The quantity of the item to add to the user's cart.
        
        @throw IllegalArgumentException if the number for the slot is out of 
        bounds or the quantity is unavailable.
    */
    private static void addItemToCart(int number, int quantity) throws IllegalArgumentException {
        if (_currTransaction == null) {
            beginTransaction();
        }
        if (number > _vendingMachine.height()*10 + 10 + _vendingMachine.width() + 1) {
             // out of bounds
             throw new IllegalArgumentException(String.format("Item slot %d is out of bounds.", number));
        }
        int row = number / 10 - 1;
        int column = number%10 - 1;
        CoordinatePair pair = new CoordinatePair(row, column);
        if (vendingMachine().canDispenseItem(pair.row, pair.column, quantity)) {
            vendingMachine().dispenseItem(pair.row, pair.column, quantity);
            Integer oldQuantity = _currTransaction.get(pair); // Get existing quantity from the transaction, if any.
            Integer finalQuantity = quantity + ((oldQuantity != null)?oldQuantity:0);
            _currTransaction.put(pair, finalQuantity);
        }
        else {
            throw new IllegalArgumentException(String.format("Slot at %d does not have %d unexpired item%s to vend.", number, quantity, (quantity != 1)?"s":""));
        }
    }
    
    /**
        Remove an item from the user's cart (the current transaction) and put it back in the machine.
        
        @param pair The Coordinate pair of the Slot in the vending machine.
        
        @throw IllegalArgumentException if the pair does not exist in the cart.
    */
    private static void removeItemFromCartWithCoordinates(CoordinatePair pair) throws IllegalArgumentException {
        _currTransaction.put(pair, 0);
    }
    
    private static void quit() throws IOException{
        try{
            cancelTransaction();
        }
        catch(IOException ex){
            throw new IOException("Not sure why I have to do this");
        }
    }
    
    private static String menuDescription() {
        return "-------------------------------------------------------------------\n" + 
               "Customer Vending Machine CLI\n\n" + 
               "Type help at any time to print the following menu options:\n" + 
               "display                   display the items in the vending machine\n" +  
               "cancel                    cancel a transaction\n" + 
               "pay                       purchase the items in your cart\n" + 
               "cart                      list the items in your cart\n" + 
               "add [number] [quantity]   add a quantity of an item in a slot to your cart (Number should be [rowcol] format)\n" + 
               "remove [number]           remove the numbered line item from your cart\n" + 
               "help                      print this menu again\n" + 
               "quit                      close the application\n" + 
               "-------------------------------------------------------------------";
    }
    
    public static void main(String args[]) throws IOException, FileNotFoundException{
    	//Populate vending machine from file
        VendingMachine vendingMachine = new VendingMachine(new File(args[0]+"Metadata.txt"));
        
        _vendingMachine = vendingMachine;
    
        String userCommand = "";
        while (!userCommand.equals("quit")) {
            System.out.println(menuDescription());
            System.out.print("CustomerCLI> ");
            userCommand = inputScanner().nextLine().toLowerCase(); // Lowercase to normalize input.
        
            if (userCommand.equals("display")) {
                display();
            }
            else if (userCommand.equals("begin")) {
                beginTransaction();
            }
            else if (userCommand.equals("cancel")) {
                cancelTransaction();
            }
            else if (userCommand.equals("pay")) {
                try{
                    finishPayment(vendingMachine.name(),vendingMachine);
                }
                catch(IOException ex){
                    throw new IOException("You dun messed up");
                }
            }
            else if (userCommand.equals("cart")) {
                listItemsInCart();
            }
            else if (userCommand.startsWith("add")) {
                try {
                    ArrayList<Integer> parsedArgs = positiveIntegerArgumentsOnCommand(userCommand, 2);
                    if (parsedArgs != null) {
                        addItemToCart(parsedArgs.get(0), parsedArgs.get(1));
                        listItemsInCart();
                    }
                }
                catch (IllegalArgumentException argumentException) {
                    System.err.println(argumentException.getMessage());
                }
            }
            else if (userCommand.startsWith("remove")) {
                ArrayList<Integer> parsedArgs = positiveIntegerArgumentsOnCommand(userCommand, 1);
                if (parsedArgs != null) {
                    if (_currTransaction == null) {
                        beginTransaction();
                    }
                    int zeroBasedIndex = parsedArgs.get(0)-1; // FIXME probably should move index shift to removeItemFromCartWithCoordinates().
                    if ((zeroBasedIndex+1) > _currTransaction.size()) {
                        System.err.println("Error: index out of bounds.");
                    }
                    else {
                        CoordinatePair chosenCoordinatePair = (CoordinatePair)_currTransaction.keySet().toArray()[zeroBasedIndex];
                        removeItemFromCartWithCoordinates(chosenCoordinatePair);
                    }
                }
            }
            else if (!userCommand.equalsIgnoreCase("help") && !userCommand.equalsIgnoreCase("quit")) {
                // If the command isn't any of the ones in the menu, print an error and continue.
                // We don't need to specifically handle help and quit since help gets printed every
                // time through the loop and quit is handled in the while statement.
                System.err.println("Command not recognized.");
            }
        }  
        quit();
    }
}
