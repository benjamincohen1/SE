import java.util.ArrayList;
import java.util.*;

import junit.framework.TestCase;

/** Vending Machine JUnit tester class.
 *  @author Ian Frasch (ijf1860@rit.edu)
 */
public class VendingMachineTest extends TestCase {

	public VendingMachineTest(String name) {
		super(name);
	}

	// Create an Empty Vending Machine, 3 columns by 3 rows.
	public void testEmptyMachine(){
		VendingMachine testMachine = new VendingMachine(3,3,5, "testMachine");
		assertEquals("TestMachine's height should be 3.", testMachine.height(), 3);
		assertTrue("TestMachine's width should be 3.",testMachine.width() == 3);
		assertTrue("TestMachine's depth should be 5.",testMachine.depth() == 5);
		for (int x = 0; x < testMachine.width(); x++){
			for (int y =0; y< testMachine.height(); y++){
				assertTrue("All slots in Test Machine should be empty.",testMachine.slotAtCoordinates(x,y) == null);
			}
		}
	}
	
	//Test the adding of a new slot to and empty vending machine
	public void testAddSlot(){
		VendingMachine testMachine = new VendingMachine(3, 3, 5, "testMachine");
		ItemMetadata testMetadata = new ItemMetadata("Taco", 525);
		Slot testSlot = new Slot(testMetadata);
		
		testMachine.addSlot(1, 2, testSlot);
		assertTrue("There should be slot of name Taco at row 1 column 2.", testMachine.slotAtCoordinates(1, 2).getName() == "Taco");
		assertTrue("The slot at row 1 column 2 should have quantity 0.", testMachine.slotAtCoordinates(1, 2).quantity() == 0);
		assertTrue("The slot at row 1 column 2 should have price 525.", testMachine.slotAtCoordinates(1, 2).getPrice() == 525);
	}
	
	// Test the adding of one item. A slot must first be initialized 
	public void testAddOneItemToSlot(){
		VendingMachine testMachine = new VendingMachine(3, 3, 5, "testMachine");
		ItemMetadata testMetadata = new ItemMetadata("Taco", 525);
		Slot testSlot = new Slot(testMetadata);
		testMachine.addSlot(1, 2, testSlot);
		ArrayList<Date> testDates = new ArrayList<Date>();
		testDates.add(new Date());
		
		testMachine.addItemsToSlot(1, 2, testDates);
		assertTrue("Testmachine should contain one item at the slot at row 1 column 2.", testMachine.slotAtCoordinates(1, 2).quantity() == 1);
	}
	
	// Test the adding of several items. A slot must first be initialized 
	public void testAddSeveralItemsToSlot(){
		VendingMachine testMachine = new VendingMachine(3, 3, 7, "testMachine");
		ItemMetadata testMetadata = new ItemMetadata("Taco", 525);
		Slot testSlot = new Slot(testMetadata);
		testMachine.addSlot(1, 2, testSlot);
		ArrayList<Date> testDates = new ArrayList<Date>();
		for (int i = 0; i < 5; i++)
			testDates.add(new Date());
		
		testMachine.addItemsToSlot(1, 2, testDates);
		assertTrue("Testmachine should contain 5 items at the slot at row 1 column 2.", testMachine.slotAtCoordinates(1, 2).quantity() == 5);
	}
	
	public void testCanDispense(){
		VendingMachine testMachine = new VendingMachine(8, 8, 7, "testMachine");
		ItemMetadata testMetadata = new ItemMetadata("Taco", 525);
		ItemMetadata testMetadata2 = new ItemMetadata("Yogurt", 125);
		Slot testSlot = new Slot(testMetadata);
		Slot testSlot2 = new Slot(testMetadata2);
		testMachine.addSlot(4, 3, testSlot);
		testMachine.addSlot(2, 1, testSlot2);
		ArrayList<Date> testDates = new ArrayList<Date>();
		for (int i = 0; i < 6; i++) //Add 6 items to slot
			testDates.add(new Date(113, 2, 10)); //Expiration date is in march, which is in the past	
		ArrayList<Date> testDates2 = new ArrayList<Date>();
		for (int i = 0; i < 4; i++) //Add 4 items to slot
			testDates2.add(new Date(500, 2, 10)); //Expiration date is in year 2400, which is well in the future!
		
		testMachine.addItemsToSlot(4, 3, testDates);
		testMachine.addItemsToSlot(2, 1, testDates2);
		
		
		assertFalse("Testmachine's canDispense function should return false due to expiration.", testMachine.canDispenseItem(4, 3, 1));
		assertFalse("Testmachine's canDispense function should return false due to not enough items.", testMachine.canDispenseItem(2, 1, 7));
		assertFalse("Testmachine's canDispense function should return false due to no slot at the coordinates.", testMachine.canDispenseItem(1, 1, 2));
		
		assertTrue("Testmachine's canDispense function should be return true.", testMachine.canDispenseItem(2, 1, 2));
		
		testMetadata2.setRecalled();  // Recall the yogurt item
		assertFalse("Testmachine's canDispense function should return false due to recalled item.", testMachine.canDispenseItem(2, 1, 1));
		
	}
	
	// Test the dispensing of an item. A slot with items must first be 
	public void testDispenseOneItem(){
		VendingMachine testMachine = new VendingMachine(8, 8, 7, "testMachine");
		ItemMetadata testMetadata = new ItemMetadata("Taco", 525);
		Slot testSlot = new Slot(testMetadata);
		testMachine.addSlot(4, 3, testSlot);
		ArrayList<Date> testDates = new ArrayList<Date>();
		for (int i = 0; i < 6; i++) //Add 6 items to slot
			testDates.add(new Date());
		testMachine.addItemsToSlot(4, 3, testDates);
		
		testMachine.dispenseItem(4, 3, 4);
		assertTrue("Testmachine should now only contain 2 items at slot at row 4 column 3", testMachine.slotAtCoordinates(4, 3).quantity() == 2);
	}
}
