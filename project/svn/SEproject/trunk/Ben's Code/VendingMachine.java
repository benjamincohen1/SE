import java.util.*;
import java.io.*;
public class VendingMachine{
	String time, id;
	Item items[][];
	public VendingMachine(String id){  //Makes an empty Machine
		/* Make this read from a file to populate data*/
		this.time = "0";
		this.id=id;
		this.items= new Item[8][8];//Check that this is 8x8
		for (int x=0;x<8;x++){
			for(int y=0;y<8;y++){
				items[x][y]=new Item("Empty","0",0,0);
			}
		}
	}
	public VendingMachine(String id, File f) throws IOException, FileNotFoundException{ //Populates a vending machine from a file.
		/* Make this read from a file to populate data*/
		this.time = "0";
		this.id=id;
		this.items= new Item[8][8];//Check that this is 8x8
		for (int x=0;x<8;x++){
			for(int y=0;y<8;y++){
				items[x][y]=new Item("Empty","0",0,0);
			}
		}
		try{
			FileReader w = new FileReader(f);
			BufferedReader br = new BufferedReader(w);
			String line;
			while((line = br.readLine()) != null){
				String [] ns=line.split(" ");
				items[Integer.parseInt(ns[0])][Integer.parseInt(ns[1])]=new Item(ns[2],ns[4],Double.parseDouble(ns[5]),Integer.parseInt(ns[3]));

			}	
		}
		catch(FileNotFoundException fnf){
			throw new FileNotFoundException("File not found");
		}
		catch(IOException ex){
			throw new IOException("File not found");
		}


		}
	public void logSale(Sale s) throws IOException{
		File f = new File(id.toString()+".txt");
		PrintWriter w=new PrintWriter(new BufferedWriter(new FileWriter(f, true)));	
		String sr=s.stringRep();
		w.println(sr);
		w.close();
	}
	public boolean addItem(Item i,int xPos, int yPos){
		Item curItemInSlot = this.items[xPos][yPos];
		if (curItemInSlot.isEmpty()) {
			items[xPos][yPos]=i;
			return true;
		}
		else if(curItemInSlot.getName().equals(i.getName())){
			items[xPos][yPos].addOne();
		}
		if(curItemInSlot.getQuantity()==8){
			return false;
		}
		return true;
	}
	public boolean removeItem(int xPos, int yPos){
		Item curItemInSlot = this.items[xPos][yPos];
		if (curItemInSlot.isEmpty()) {
			return false;
		}
		else{
			items[xPos][yPos].removeOne();
			if (items[xPos][yPos].getQuantity()==0){
				//Go back to empty
				items[xPos][yPos]=new Item("Empty","0",0,0);
			}
			return true;
		}
	}
	public void printSelf(){
		//Should probably find a better way to remind x and y
		System.out.println("x->\t\t0\t    1\t \t  2 \t\t   3\t\t    4\t\t   5\t\t   6\t\t   7\t");
		for(int x = 0;x<8;x++){
			for(int y = 0;y<8;y++){
				if(y==0){
					System.out.print("[y("+x+"): "+items[x][y].print()+"\t");
				}
				else if(y==7){
					System.out.println(items[x][y].print()+"]");
				}
				else{
					System.out.print(items[x][y].print()+"\t");
				}
			}//end y loop
		}//end x loop
	}
	public boolean isExpired(int xPos,int yPos){
		//Right now this function assumes that every item in a slot has the same expiry date
		return Integer.parseInt(time)>Integer.parseInt(items[xPos][yPos].getDate());
		/* Implement non-integer date model*/
	}
	public void switchItems(int x1,int y1,int x2,int y2){
		Item tmp=items[x1][y1];
		items[x1][y1]=items[x2][y2];
		items[x2][y2]=tmp;
	}
	public void removeAllFromSlot(int x,int y){
		items[x][y]=new Item("Empty","0",0,0);
	}
	public Item getItemAt(int x,int y,int quantity){
		Item tmp = new Item(items[x][y]);
		tmp.setQuantity(quantity);
		return tmp;
	}
	public int getQuantity(int x,int y){
		return items[x][y].getQuantity();
	}
}