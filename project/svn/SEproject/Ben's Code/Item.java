import java.util.*;
	/*
	*Account Constructor
	*/
public class Item{
	 String name,expDate;
	 double price;
	 int quantity;
	
	public Item(String name,String expDate,double price,int quantity){
		this.name=name;
		this.expDate=expDate;
		this.price=price;
		this.quantity=quantity;
	}
	public Item(Item i){
		this.name = i.getName();
		this.expDate = i.getDate();
		this.price = i.getPrice();
		this.quantity=i.getQuantity();
	}
	public String getDate(){
		return this.expDate;  //Implement this
	}
	public double getPrice(){
		return this.price; 
	}
	public String getName(){
		return this.name;
	}
	public int getQuantity(){
		return this.quantity;
	}
	public void addOne(){
		this.quantity++;
	}
	public void removeOne(){
		this.quantity--;
	}
	public String print(){
		if(this.name.equals("Empty (0)")){
			return "Empty";
		}
		return name+" ("+quantity+")";
	}
	public boolean isEmpty(){
		return this.name.equals("Empty");
	}
	public void setQuantity(int newQuantity){
		quantity=newQuantity;
	}
}