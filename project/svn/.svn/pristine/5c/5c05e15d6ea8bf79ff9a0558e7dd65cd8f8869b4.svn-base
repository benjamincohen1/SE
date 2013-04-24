/**
    @author Jack Lawrence
*/

import java.util.*;
import java.io.*;

/** The manager command line interface. */
public class ManagerCLI extends CommandLineInterface {

    private static final int kMenuLevelMain = 0;
    private static final int kMenuLevelMachine = 1;
    private static final int kMenuLevelItems = 2;
    private static final int kMenuLevelRestock = 3;

    /** All of the vending machines in the system. */
    private static ArrayList<VendingMachine> _vendingMachines = new ArrayList<VendingMachine>();
    /** The currently selected vending machine. */
    private static VendingMachine _currentVendingMachine;
    
    private static ArrayList<VendingMachine> vendingMachines() {
        _vendingMachines = new ArrayList<VendingMachine>();
        //Get machines from directory
        String path = ".";
        File folder = new File(path);
        File[] listOfFiles=folder.listFiles();
        ArrayList<File> MetadataFiles= new ArrayList<File>();
        for (int i = 0;i<listOfFiles.length;i++){
            if(listOfFiles[i].isFile()){
                if(listOfFiles[i].getName().matches(".*Meta.*txt")){
                   MetadataFiles.add(listOfFiles[i]);
                }
            }
        }
        for (File x : MetadataFiles){
            try {
                VendingMachine vm = new VendingMachine(x);
                _vendingMachines.add(vm);
            }
            catch (Exception ex) {
                System.err.println("Couldn't create vending machine: " + ex.getMessage());
            }
        }
        return _vendingMachines;
    }
    
    private static VendingMachine currentVendingMachine() {
        return _currentVendingMachine;
    }
    
    private static void setCurrentVendingMachine(VendingMachine currentVendingMachine) {
        _currentVendingMachine = currentVendingMachine;
    }
    
    /**
        Lists every vending machine in the system.
    */
    private static void listVendingMachines() {
        int index = 0;
        for (VendingMachine vendingMachine : vendingMachines()) {
            index++;
            System.out.println(String.format("%d: %s", index, vendingMachine.name()));
        }        
    }
    
    /**
        Selects the vending machine to view data on and restock.
    */
    private static boolean selectVendingMachine(int machineIdx){
        if (machineIdx < vendingMachines().size()) {
            setCurrentVendingMachine(vendingMachines().get(machineIdx));
            return true;
        }
        else {
            System.err.println("Machine number out of bounds.");
            return false;
        }
    }
    
    /**
        Remove a vending machine from the system.
        
        @param machineIdx The index of the vending machine to remove.
    */
    private static void removeVendingMachine(int machineIdx) {
        vendingMachines().remove(machineIdx);
    }
    
    /**
        Adds a new vending machine to the system. Walks the user
        through creation.
    */
    private static void addNewVendingMachine() throws IOException{
        // name
        System.out.print("Name: ");
        String name = inputScanner().nextLine();
        
        // rows
        int rows = scanPositiveInteger("Rows: ");
        
        // columns
        int columns = scanPositiveInteger("Columns: ");
        
        // slot depth
        int depth = scanPositiveInteger("Slot Depth: ");
	    try{
        VendingMachine newMachine = new VendingMachine(rows, columns, depth, name);
        BufferedWriter bf = new BufferedWriter(new FileWriter(new File(newMachine.name()+"Metadata.txt")));
        bf.write(newMachine.name()+" "+rows+" "+columns+" "+depth+" "+newMachine.name()+"Data.txt");
        bf.flush();
        bf.close();
        BufferedWriter bf1= new BufferedWriter(new FileWriter(new File(newMachine.name()+"Data.txt")));
        //bf1.write();
        bf1.flush();
        bf1.close();
        vendingMachines().add(newMachine);   
        }
        catch (IOException ex){

        }    

    }
    
    /**
        Lists all sales across vending machines.
    */
    private static void listSales() {
        VendingMachine backup = _currentVendingMachine;
        String path = ".";
        File folder = new File(path);
        File[] listOfFiles=folder.listFiles();
        for (int i = 0;i<listOfFiles.length;i++){
            if(listOfFiles[i].isFile()){
                if(listOfFiles[i].getName().matches(".*Meta.*txt")){
                    try {
                        _currentVendingMachine = new VendingMachine(listOfFiles[i]);
                        System.out.println(_currentVendingMachine.name());
                        System.out.println("------------------------------");
                        listSalesForCurrentMachine();
                    }
                    catch (Exception ex) {
                        System.err.println("Couldn't list sales: " + ex.getMessage());
                    }
                }
            }
        }    
        _currentVendingMachine=backup;
    }
    
    /**
        Lists sales for the current vending machine.
    */
    private static void listSalesForCurrentMachine() {

        String newName = new String(_currentVendingMachine.name()+"_log.txt");
        //File f = new File(newName);
        try{
            Reader r = new FileReader(new File(newName));
            BufferedReader br = new BufferedReader(r);
            String line;
            while((line = br.readLine()) != null){
                if(line.matches(".*:.*")){
                    System.out.println(line);
                }
                else{
                    System.out.println("\t"+line);
                }
            }

        }
        catch(IOException ex){
            System.err.println("Log file not found: " + ex.getMessage());
        }
    }
    
    /**
        Lists all unique items available for restocking.
    */
    private static void listItems() {
    	try{
	    	File itemFile = new File("Items.txt");
	    	File recalledItemsFile = new File("RecalledItems.txt");
	    	FileReader r = new FileReader(itemFile);
	    	BufferedReader br = new BufferedReader(r);
	    	String line;
	    	int i = 1;
	    	while((line = br.readLine()) != null){
	    		String [] lineSplit = line.split(" ");
	    		int price = Integer.parseInt(lineSplit[lineSplit.length-1]);
	    		System.out.println(i + ". " + lineSplit[0] + " $" + price/100.0);
				i++;
			}	
	    	r = new FileReader(recalledItemsFile);
	    	br = new BufferedReader(r);
	    	i = 1;
	    	System.out.println("\nRecalled Items: ");
	    	while((line = br.readLine()) != null){
	    		System.out.println(i + ". " + line);
				i++;
			}	
	    	
    	}catch(FileNotFoundException f){
    		System.err.println("File Items.txt not found, cannot list Items.");
    	}catch (IOException e){
    		System.err.println("Cannot list items.");
    	}
    }
    
    /**
        Adds an item type to the list of unique items, stored in Items.txt
    */
    private static void addItem(String itemName, int itemPrice) {
        File itemFile = new File("Items.txt");
        PrintWriter w;
		try {
			w = new PrintWriter(new BufferedWriter(new FileWriter(itemFile, true)));
	        w.println(itemName + " " + Integer.toString(itemPrice));
	        w.close();
		} catch (IOException e) {
			System.err.println("Error: cannot add item.");
		} 
    }
    
    /**
	    Remove an item at the specified index from the
	    list of unique items.
	    
	    @param itemIdx The index of the item to remove.
	*/
	private static void removeItem(String itemName) {
		System.err.println("Unimplemented command");
	}
    
    /**
        Recall item name across all vending machines. This updates the recalledItems.txt file.
    */
    private static void recallItem(String itemName) {
    	try{
	    	File itemsFile = new File("Items.txt");
	    	FileReader r = new FileReader(itemsFile);
			BufferedReader br = new BufferedReader(r);
			boolean foundItem = false;
			String line;
			String [] lineSplit;
			
			while((line = br.readLine()) != null){
				lineSplit = line.split(" ");
				if (lineSplit[0].equalsIgnoreCase(itemName))
					foundItem = true;
			}	
			if (foundItem){
		    	File recalledItemsFile = new File("RecalledItems.txt");
		    	PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(recalledItemsFile, true)));
		    	pw.println(itemName);
		        pw.close();
	        	System.out.println(itemName + " has been recalled across all vending machines.");
			}
			else{
				System.err.println("Item name not found. Refer to Items.txt for list of items");
			}
    	}catch (FileNotFoundException f){
    		System.err.println("File Items.txt not found, cannot recall item.");
    	}
    	catch (IOException e){
    		System.err.println("Error: cannot recall item.");
    	}
    }
    
    /**
        Begins a batch of restocking commands for the
        currently selected vending machine.
    */
    private static void beginRestock() {
        try{

            File f = new File(_currentVendingMachine.name()+"ManagerNotes.txt");
            FileWriter w = new FileWriter(f);
            BufferedWriter bw = new BufferedWriter(w);
            bw.newLine();
            bw.flush();
            bw.close();
        }
        catch (IOException ex){

        }

    }
    
    /**
        Lists the current restocking instructions for the
        currently selected vending machine.
    */
    private static void listRestockCommands() {
    
    }
    
    /**
        Confirms a batch of restocking commands.
    */
    private static void confirmRestock() {
        System.out.println("Beginning Restock.");
        try{

            File f = new File(_currentVendingMachine.name()+"ManagerNotes.txt");
            FileWriter w = new FileWriter(f);
            BufferedWriter bw = new BufferedWriter(w);
            System.out.print("Enter Message for restocker: ");
            String i = inputScanner().nextLine();
            bw.write(i);
            bw.flush();
            bw.close();
        }
        catch (IOException ex){

        }    }
    
    /**
        Cancels the current batch of restocking commands
        for the currently selected vending machine.
    */
    private static void cancelRestock() {
    
    }
    
    /**
        Walks through restocking an item in the current
        vending machine.
    */
    private static void restockItem() {
    
    }
    
    /**
        Walks through removing an item in the current
        vending machine.
    */
    private static void removeItemFromMachine() {
    
    }
    
    private static String menuDescriptionForLevel(int level) {
        switch (level) {
            case kMenuLevelMachine:
                return machineMenuDescription();
            case kMenuLevelItems:
                return itemMenuDescription();
            case kMenuLevelRestock:
                return restockMenuDescription();
            case kMenuLevelMain:
            default:
                return mainMenuDescription();

            
        }
    }
    
    private static String mainMenuDescription() {
        return "-------------------------------------------------------------------\n" + 
               "Manager CLI - Main Menu\n\n" + 
               "Type help at any time to print the following menu options:\n" + 
               "machines                  display a list of vending machines in the system\n" +
               "add                       add a vending machine to the system\n" +   
               "remove [index]            remove the specified machine from the system \n" + 
               "select [index]            select the machine to command\n" + 
               "sales                     display a list of sales across every vending machine\n" + 
               "items                     enter the item management interface.\n" +
               "help                      print this menu again\n" + 
               "quit                      close the application\n" + 
               "-------------------------------------------------------------------";
    }
    
    private static String machineMenuDescription() {
        return "-------------------------------------------------------------------\n" + 
               "Manager CLI - Vending Machine " + currentVendingMachine().name() + " Menu\n\n" + 
               "Type help at any time to print the following menu options:\n" + 
               "sales                     display a list of sales for the currently selected vending machine.\n" + 
               "restock                   enter the restocking interface for the currently selected vending machine.\n" + 
               "help                      print this menu again\n" + 
               "back                      return to the main menu\n" + 
               "-------------------------------------------------------------------";

    }
    
    private static String itemMenuDescription() {
        return "-------------------------------------------------------------------\n" + 
               "Manager CLI - Item Menu\n\n" + 
               "Type help at any time to print the following menu options:\n" + 
               "display                   display a list of unique items in the system\n" +
               "add                       add an item to the system\n" +   
               "remove                    remove an item from the system\n" + 
               "recall                    recall an item\n" + 
               "help                      print this menu again\n" + 
               "back                      return to the main menu\n" + 
               "-------------------------------------------------------------------";
    }
    
    private static String restockMenuDescription() {
        return "-------------------------------------------------------------------\n" + 
               "Manager CLI - Restock " + currentVendingMachine().name() + " Menu\n\n" + 
               "Type help at any time to print the following menu options:\n" + 
               "list                      list the current batch of restocking instructions\n" +
               "add                       add an item to the machine\n" +
               "remove                    remove an item from the machine\n" +   
               "confirm                   send the batch of restocking instructions to the restocker\n" +
               "cancel                    cancel and return to the machine menu\n" + 
               "help                      print this menu again\n" + 
               "-------------------------------------------------------------------";
    }
    
    private static int handleCommandAtLevel(String command, int level) {
        switch (level) {
            case kMenuLevelMain:
                return handleCommandAtMainLevel(command);

            case kMenuLevelMachine:
                return handleCommandAtMachineLevel(command);
            case kMenuLevelItems:
                return handleCommandAtItemLevel(command);
            case kMenuLevelRestock:
                return handleCommandAtRestockLevel(command);
            default:
                return kMenuLevelMain;
        }
    }
    
    private static int handleCommandAtMainLevel(String command) {
        int level = kMenuLevelMain;
        
        if (command.equals("machines")) {
            listVendingMachines();
        }
        else if (command.equals("add")) {
            try{
             addNewVendingMachine();
            }
            catch (IOException ex){

            }
        }
        else if (command.startsWith("remove")) {
            ArrayList<Integer> parsedArgs = positiveIntegerArgumentsOnCommand(command, 1);
            if (parsedArgs != null) {
                removeVendingMachine(parsedArgs.get(0)-1); // FIXME probably shouldn't do the index shift here.
            }
        }
        else if (command.startsWith("select")) {
            ArrayList<Integer> parsedArgs = positiveIntegerArgumentsOnCommand(command, 1);
            if (parsedArgs != null) {
                if (selectVendingMachine(parsedArgs.get(0)-1)) // FIXME probably shouldn't do the index shift here.
                {
                    level = kMenuLevelMachine;
                }
            }
        }
        else if (command.equals("sales")) {
            listSales();
        }
        else if (command.equals("items")) {
            level = kMenuLevelItems;
        }
        
        return level;
    }
    
    private static int handleCommandAtMachineLevel(String command) {
        int level = kMenuLevelMachine;
        
        if (command.equals("sales")) {
            listSalesForCurrentMachine();
        }
        else if (command.equals("restock")) {
            beginRestock();
            level = kMenuLevelRestock;   
        }
        else if (command.equals("back")) {
            level = kMenuLevelMain;
        }
        
        return level;
    }
    
    private static int handleCommandAtItemLevel(String command) {
        int level = kMenuLevelItems;
        
        if (command.equals("display")) {
            listItems();
        }
        else if (command.equals("add")) {
        	System.out.print("Enter name of the item to add (no spaces): ");
        	String name = inputScanner().next();
        	System.out.print("Enter price of the item in cents: ");
        	int price = inputScanner().nextInt();
            addItem(name, price);
        }
        else if (command.startsWith("remove")) {
        		String blank = " ";
                removeItem(blank);
        }
        else if (command.startsWith("recall")) {
        	System.out.print("Enter name of the item to recall: ");
        	String name = inputScanner().next();
        	recallItem(name);
        }
        else if (command.equals("back")) {
            level = kMenuLevelMain;
        }
        
        return level;
    }
    
    private static int handleCommandAtRestockLevel(String command) {
        int level = kMenuLevelRestock;
        
        if (command.equals("list")) {
            listRestockCommands();
        }
        else if (command.equals("add")) {
            restockItem();
        }
        else if (command.equals("remove")) {
            removeItemFromMachine();
        }
        else if (command.equals("confirm")) {
            confirmRestock();
            level = kMenuLevelMachine;
        }
        else if (command.equals("cancel")) {
            cancelRestock();
            level = kMenuLevelMachine;
        }
        
        return level;
    }
    
    public static void main(String args[]) {        
        String userCommand = "";
        
      //Initialize recalled items file if it is not already there.
        boolean foundFile = false;
        String path = ".";
        File folder = new File(path);
        File[] listOfFiles=folder.listFiles();
        for (int i = 0;i<listOfFiles.length;i++){
            if(listOfFiles[i].isFile()){
                if(listOfFiles[i].getName().matches(".*RecalledItems.*txt")){
                	foundFile = true;
                }
            }
        }
        if (foundFile == false){
        	try{
	        	File recalledItems = new File("RecalledItems.txt");
	            FileWriter w = new FileWriter(recalledItems);
	    		BufferedWriter bw = new BufferedWriter(w);
	    		bw.write("");
	    		bw.flush();
	    		bw.close();
        	}
        	catch (IOException e){
        		System.out.println("Something got messed up.");
        	}
        }
        
        int menuLevel = kMenuLevelMain;
        while (!(menuLevel == kMenuLevelMain && userCommand.equals("quit"))) {
            System.out.println(menuDescriptionForLevel(menuLevel));
            System.out.print("ManagerCLI> ");
            userCommand = inputScanner().nextLine().toLowerCase(); // Lowercase to normalize input.
            menuLevel = handleCommandAtLevel(userCommand, menuLevel);
        }
    }
	
}
