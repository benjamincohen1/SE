import java.util.*;
public class main{
	public static void main(String args[]){
		System.out.println("I'm in main!");
		Item i = new Item("Chips","-1",1.00,1);
		Item i2 = new Item("Popcorn","-1",5.00,2);
		VendingMachine v = new VendingMachine();
		Sale s = new Sale();
		v.addItem(i,2,2);
		v.addItem(i2,0,0);
		if(args[0].equals("Customer")){
			int choice=-1;
			Scanner sc = new Scanner(System.in);
			while(choice!=1000){
				v.printSelf();
				System.out.println("Choices: 1) New Item 2) Remove Item 3) Finish 4) Cancel");
				choice=sc.nextInt();
				switch (choice){
					case 1: 
						System.out.println("New Item: ");
						int x=-1;
						int y=-1;
						while(x<0||x>9||y<0||y>9){
							System.out.print("Enter an x:");
							x=sc.nextInt();
							System.out.print("Enter an y:");
							y=sc.nextInt();
						}
						System.out.println("How many would you like?  There are: "+v.getQuantity(x,y)+".  Enter -1 to cancel.");
						int quantity = -4;
						while(quantity<0||quantity>v.getQuantity(x,y)){
							System.out.print("Quantity: ");
							quantity=sc.nextInt();
						}
						Item it = v.getItemAt(x,y,quantity);
						Item itemcopy = new Item(it.getName(),it.getDate(),it.getPrice(),it.getQuantity());
						//The above line is making a copy because we need to keep the current value and not let it be modified.
						s.addItem(itemcopy);
						for(int z = 0;z<quantity;z++){
							v.removeItem(x,y);
						}
						break;
					case 2:
						 ArrayList<Item> items = s.getItems();
						 int tmp=0;
						 for(Item item2 : items){
						 	tmp++;
						 	System.out.println(tmp+": "+items.get(items.indexOf(item2)).print());
						 }
					 	 System.out.print("Enter the number of the item you want to remove (-1 to cancel): ");

						 int itemToRemove=-2;
						 while(itemToRemove<0||itemToRemove>items.size()){
						 	itemToRemove = sc.nextInt();
						 	System.out.print("Enter the number of the item you want to remove (-1 to cancel): ");

						 }
						 s.removeItemFromSale(items.get(itemToRemove-1));
						 System.out.println("Item Removed Sucessfully");
						 s.print();
						break;
					case 3: 
						s.print();
						s=new Sale();
						break;
					case 4:
						break;
					default:
						System.out.println("Invalid choice");

				}
			}
		}
	}
}