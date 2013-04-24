/**
    @author Jack Lawrence
*/
import java.io.*;
import java.util.*;

/** */
public class RestockerActionBridge {

	private VendingMachine _currentVendingMachine;
	
    public RestockerActionBridge() {
			
    }
    
    /**
     * Parses request objects from the Requests.txt file
     * @return	Arraylist of requests. If empty, that means there are no requests
     * @throws IOException 
     */
    public ArrayList<Request> parseRequestsFromFile() throws IOException{
    	ArrayList<Request> requests = new ArrayList<Request>();
    	String path = ".";
    	File folder = new File(path);
    	File[] listOfFiles = folder.listFiles();
    	for (File file : listOfFiles){
    		if (file.isFile()){
    			if(file.getName().matches(".*Requests.*txt")){
    				try{
    					FileReader r = new FileReader(file);
    					BufferedReader br = new BufferedReader(r);
    					String line;
    					while((line = br.readLine()) != null){
    						String [] ls = line.split(" ");
    						Request request = new Request(ls[0], Integer.parseInt(ls[6]), Integer.parseInt(ls[8]), ls[3], Integer.parseInt(ls[2]), ls[1], Integer.parseInt(ls[10]));
    						requests.add(request);
    					}
    					r.close();
    				}
    				catch(FileNotFoundException fnf){
    					throw new FileNotFoundException("File not found");
    				}
    		    	catch(IOException ex){
    					throw new IOException("Parsing error; could not read request data from file");
    				}
    			}
    		}
    	}

    	return requests;
    }
    
    /**
     * Confirm a restock by writing what was done to the Requests.txt file
     * so the information can be relayed back to the manager
     * @param requests		ArrayList of requests that were processed/are being processed
     */
    public void confirmRestock(String machineName, ArrayList<Request> requests){
    	File requestFile = new File(machineName + "Requests.txt");
    	BufferedWriter bw;
		try {
			bw = new BufferedWriter(new FileWriter(requestFile));
			for (Request request : requests){
				bw.write(request.toFileString());
				bw.newLine();	
			}
			bw.flush();
	        bw.close();
	    	System.out.println("A restocker has sent a restock response to manager.");
		} catch (IOException e) {
			System.err.println("Error: cannot submit restock response.");
		}
    }
	
}
