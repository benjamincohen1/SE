import java.util.*;
public class Sale{
	ArrayList<Item> items;
	String purchacer,paymentMethod,time;
	public Sale(){
		this.items=new ArrayList<Item>();
		this.purchacer="";
		this.paymentMethod="";
		this.time="8PM";
	}
	public void addItem(Item i){
		items.add(new Item(i));
	}
	public void setPurchacer(String buyer){
		this.purchacer=buyer;
	}
	public void setDate(String date){
		this.time=date;
	}
	public void setPaymentMethod(String pm){
		paymentMethod=pm;
	}
	public double getTotal(){
		double total=0.00;
		for(Item i : items){
			total+=(i.getPrice()*i.getQuantity());
		}
		return total;
	}
	public void print(){
		System.out.println("Sale at "+time+": ");
		for (Item i : items){
			System.out.print("\t");
			System.out.println(i.print());
		}
		System.out.println("Total: $"+getTotal());
	}
	public ArrayList<Item> getItems(){
		return this.items;
	}
	public void removeItemFromSale(Item i){
		items.remove(i);
	}
}