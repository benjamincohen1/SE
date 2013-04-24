/* SlotTest.java: JUnit test file covering functionality of the Slot.
 * @author Thomas Farrell
 */

import java.util.ArrayList;
import java.util.Date;
import junit.framework.TestCase;
@SuppressWarnings("deprecation")
public class SlotTest extends TestCase {

	public SlotTest(String name) {
		super(name);
	}

	// Test if an empty Slot can be constructed. Uses the default constructor, with no arguments.
	public void testEmptySlot(){
		Slot emptySlot = new Slot();
		assertEquals("Size of empty slot should be 0.",0, emptySlot.quantity());
		assertEquals("Metadata of empty slot should be NULL.", null, emptySlot.metadata());
	}
	
	// Test to see if a Slot can be constructed, with an empty ArrayList of dates.
	public void testEmptySlotwithMetaData(){
		ItemMetadata testMeta = new ItemMetadata("Broritos", 50);
		Slot newSlot = new Slot(testMeta);
		assertEquals("Size of empty slot should be 0.",0, newSlot.quantity());
		assertEquals("Price of empty slot should be 50 cents", 50, newSlot.metadata().price());
		assertEquals("Name of prouct in empty slot should be Broritos", "Broritos", newSlot.metadata().name());
	}
	
	// Test to see if a slot can be built that is already full of product.
	public void testFullSlot(){
		ItemMetadata testMeta = new ItemMetadata("Broritos", 50);
		ArrayList<Date> testArray = new ArrayList<Date>();
		Date testDate1 = new Date(2011,11,11);
		Date testDate2 = (Date) testDate1.clone();
		Date testDate3 = (Date) testDate1.clone();
		Date testDate4 = (Date) testDate1.clone();
		testArray.add(testDate1);
		testArray.add(testDate2);
		testArray.add(testDate3);
		testArray.add(testDate4);
		Slot testSlot= new Slot(testMeta, testArray);
		assertEquals("Size of empty slot should be 4.",4, testSlot.quantity());
		assertEquals("Price of empty slot should be 50 cents", 50, testSlot.metadata().price());
		assertEquals("Name of prouct in empty slot should be Broritos", "Broritos", testSlot.metadata().name());
	}
	
	// Test to see if you can clone slots, making two slots with the exact specs.
	public void testCloneSlot(){
		ItemMetadata testMeta = new ItemMetadata("Broritos", 50);
		ArrayList<Date> testArray = new ArrayList<Date>();
		Date testDate1 = new Date(2011,11,11);
		Date testDate2 = (Date) testDate1.clone();
		Date testDate3 = (Date) testDate1.clone();
		Date testDate4 = (Date) testDate1.clone();
		testArray.add(testDate1);
		testArray.add(testDate2);
		testArray.add(testDate3);
		testArray.add(testDate4);
		Slot testSlot= new Slot(testMeta, testArray);
		assertEquals("Size of empty slot should be 4.",4, testSlot.quantity());
		assertEquals("Price of empty slot should be 50 cents", 50, testSlot.metadata().price());
		assertEquals("Name of prouct in empty slot should be Broritos", "Broritos", testSlot.metadata().name());
		
		//Create a new slot, based on TestSlot.
		Slot cloneSlot= new Slot(testSlot);
		assertEquals("Size of empty slot should be 4.",4, cloneSlot.quantity());
		assertEquals("Price of empty slot should be 50 cents", 50, cloneSlot.metadata().price());
		assertEquals("Name of prouct in empty slot should be Broritos", "Broritos", cloneSlot.metadata().name());
	}
	
	//Test to see if one more item can be added to the slot.
	public void testAddOne(){
		ItemMetadata testMeta = new ItemMetadata("Broritos", 50);
		Slot newSlot = new Slot(testMeta);
		newSlot.addItem(new Date(2013,4,13));
		assertEquals("Size of slot should be 1.", 1, newSlot.quantity());
	}
	
	//Test to see if multiple items can be added to the slot.
	public void testAddSeveral(){
		ItemMetadata testMeta = new ItemMetadata("Broritos", 50);
		Slot newSlot = new Slot(testMeta);
		newSlot.addItem(new Date(2013,4,13));
		newSlot.addItem(new Date(9999,9,99));
		newSlot.addItem(new Date(2012,12,21));
		newSlot.addItem(new Date(2006,6,6));
		assertEquals("Size of slot should be 4.", 4, newSlot.quantity());
	}
	
	//Test to see if the Metadata can be swapped out with a new one.
	public void testChangeMetaData(){
		ItemMetadata testMeta1 = new ItemMetadata("Broritos", 50);
		ItemMetadata testMeta2 = new ItemMetadata("Broutain Dewd", 150);
		
		Slot newSlot = new Slot(testMeta1);
		newSlot.setItemMetadata(testMeta2);
		assertEquals("Name of product in slot should be Broutain Dewd", "Broutain Dewd", newSlot.getName());
		assertEquals("Price of product in slot should be 150", 150, newSlot.getPrice());
	}
	
	//Test to see if an item can be removed correctly.
	public void testPop(){
		Slot newSlot = new Slot(new ItemMetadata("Broritos", 50));
		Date testDate = new Date(2013,4,13);
		newSlot.addItem(testDate);
		newSlot.addItem(new Date(9999,9,99));
		assertEquals("Quantity of slot should be 2.",2, newSlot.quantity());
		Date poppedDate = newSlot.popItem();
		assertEquals("Quantity of Slot should be 1.", 1, newSlot.quantity());
		assertTrue("PoppedDate should be equal to the first date added.",testDate.equals(poppedDate));
		poppedDate = newSlot.popItem();
		assertEquals("Quantity of Slot should be 0.", 0, newSlot.quantity());
		assertFalse("PoppedDate should NOT be equal to the first date added.", testDate.equals(poppedDate));
	}
	
	//Test to see if canPurchase functionality is correct.
	public void testcanPurchase(){
		Slot allPurchasable = new Slot(new ItemMetadata("Broritos", 50));
		Slot somePurchasable = new Slot(new ItemMetadata("Broritos", 50));
		Slot NonePurchasable = new Slot(new ItemMetadata("Broritos", 50));
		Slot DayofExpiration = new Slot(new ItemMetadata("Broritos", 50));
		
		//This is the date to be tested against, what would be the "Current Date" from the vending machine.
		Date testDate = new Date(1000,10,10);
		
		allPurchasable.addItem(new Date(2012,12,12));
		allPurchasable.addItem(new Date(2012,13,12));
		allPurchasable.addItem(new Date(2012,11,13));
		assertEquals("Quantity of slot should be 3.", 3, allPurchasable.quantity());
		
		NonePurchasable.addItem(new Date(999,10,10));
		NonePurchasable.addItem(new Date(998,7,6));
		assertEquals("Quantity of slot should be 2.", 2, NonePurchasable.quantity());
		
		somePurchasable.addItem(new Date(2012,12,12));
		somePurchasable.addItem(new Date(2013,13,2));
		somePurchasable.addItem(new Date(1,1,1));
		somePurchasable.addItem(new Date(2012,13,4));
		assertEquals("Quantity of slot should be 4.",4, somePurchasable.quantity());
		
		DayofExpiration.addItem(new Date(1000,10,10));
		assertEquals("Quantity of slot should be 1.",1, DayofExpiration.quantity());
		
		assertTrue("All items should be purchasable from the first machine.", allPurchasable.canPurchase(testDate, 0));
		assertTrue("All items should be purchasable from the first machine.", allPurchasable.canPurchase(testDate, 1));
		assertTrue("All items should be purchasable from the first machine.", allPurchasable.canPurchase(testDate, 2));
		assertTrue("All items should be purchasable from the first machine.", allPurchasable.canPurchase(testDate, 3));
		
		assertTrue("No items should be purchasable from the first machine.", NonePurchasable.canPurchase(testDate, 0));
		assertFalse("No items should be purchasable from the first machine.", NonePurchasable.canPurchase(testDate, 1));
		assertFalse("No items should be purchasable from the first machine.", NonePurchasable.canPurchase(testDate, 2));
		
		assertTrue("Zero items from third machine should be valid.", somePurchasable.canPurchase(testDate, 0));
		assertTrue("First item from third machine should be purchasable.", somePurchasable.canPurchase(testDate, 1));
		assertTrue("Second item from third machine should be purchasable.", somePurchasable.canPurchase(testDate, 2));
		assertFalse("Third item from third machine should be NOT purchasable.", somePurchasable.canPurchase(testDate, 3));
		assertFalse("Third item from third machine should be NOT purchasable.", somePurchasable.canPurchase(testDate, 4));
		
		assertTrue("DayofExpiration items should still be sold.", DayofExpiration.canPurchase(testDate, 0));
		assertTrue("DayofExpiration items should still be sold.", DayofExpiration.canPurchase(testDate, 1));
	}
}
