import java.awt.Color;
import java.awt.EventQueue;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
/**
 * 
 * @author Ian Frasch (ijf1860@rit.edu)
 */
public class CustomerGUI {
	private static String MACHINENAME="pres";
	private JFrame frame;
	private int total = 0;
	private JTextPane numDisplay;			//Displays the item number that is being typed
	private String numberStringEntered = "";	//Item number entered in String form
	private int itemNumberInput;	//Actual item number input the user has entered
	private String[] testCart = {}; 
	private JLabel lblTotal;
	private JList list;
	private JButton btnX;
	private JTextPane textPaneTotal;
	private JScrollPane scrollPane;
	private ArrayList<ArrayList<JLabel>> itemNameLabels; //2D array of JLabels for each item in the machine
	private CustomerActionBridge bridge;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		if (args.length < 1){
			System.err.println("You need to specify the vending machine name in the command line argument.");
			return;
		}
		MACHINENAME = args[0];
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					CustomerGUI window = new CustomerGUI();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public CustomerGUI() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		try{
			bridge=new CustomerActionBridge(MACHINENAME);

		}
		catch(Exception ex){
			System.err.println(ex.getMessage());
		}
		frame = new JFrame();
		frame.setTitle("Smart Vending");
		frame.setBounds(100, 100, 1500, 600);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		ButtonListener listener = new ButtonListener();
		
		JPanel panel_1 = new JPanel();
		frame.getContentPane().add(panel_1, BorderLayout.CENTER);
		panel_1.setLayout(new GridLayout(1, 2, 0, 0));
		
		JPanel panel_3 = new JPanel();
		panel_1.add(panel_3);
		panel_3.setLayout(new BorderLayout(0, 0));
		
		JPanel numpadPanel = new JPanel();
		panel_3.add(numpadPanel, BorderLayout.CENTER);
		numpadPanel.setLayout(new GridLayout(0, 3, 0, 0));
		
		JButton button1 = new JButton("1");
		numpadPanel.add(button1);
		button1.addActionListener(listener);
		
		JButton button2 = new JButton("2");
		numpadPanel.add(button2);
		button2.addActionListener(listener);
		
		JButton button3 = new JButton("3");
		numpadPanel.add(button3);
		button3.addActionListener(listener);
		
		JButton button4 = new JButton("4");
		numpadPanel.add(button4);
		button4.addActionListener(listener);
		
		JButton button5 = new JButton("5");
		numpadPanel.add(button5);
		button5.addActionListener(listener);
		
		JButton button6 = new JButton("6");
		numpadPanel.add(button6);
		button6.addActionListener(listener);
		
		JButton button7 = new JButton("7");
		numpadPanel.add(button7);
		button7.addActionListener(listener);
		
		JButton button8 = new JButton("8");
		numpadPanel.add(button8);
		button8.addActionListener(listener);
		
		JButton button9 = new JButton("9");
		numpadPanel.add(button9);
		button9.addActionListener(listener);
		
		
		JPanel panel_10 = new JPanel();
		panel_3.add(panel_10, BorderLayout.SOUTH);
		panel_10.setLayout(new BorderLayout(0, 0));
		
		JButton btnEnter = new JButton("ENTER");
		btnEnter.addActionListener(listener);
		panel_10.add(btnEnter, BorderLayout.CENTER);
		btnEnter.addActionListener(listener);	
		
		JPanel panel_4 = new JPanel();
		FlowLayout flowLayout_1 = (FlowLayout) panel_4.getLayout();
		flowLayout_1.setHgap(20);
		flowLayout_1.setVgap(0);
		panel_4.setBackground(Color.WHITE);
		panel_10.add(panel_4, BorderLayout.NORTH);
		
		numDisplay = new JTextPane();
		numDisplay.setText(numberStringEntered);
		panel_4.add(numDisplay);
		
		JPanel panel = new JPanel();
		panel_1.add(panel);
		panel.setLayout(new BorderLayout(0, 0));
		
		JPanel panel_5 = new JPanel();
		panel.add(panel_5, BorderLayout.NORTH);
		
		JLabel lblCart = new JLabel("Cart:");
		panel_5.add(lblCart);
		
		JPanel panel_6 = new JPanel();
		panel.add(panel_6, BorderLayout.SOUTH);
		panel_6.setLayout(new GridLayout(2, 1, 0, 0));
		
		JButton btnCancel = new JButton("Cancel");
		btnCancel.addActionListener(listener);
		panel_6.add(btnCancel);
		
		JButton btnPay = new JButton("Pay");
		btnPay.addActionListener(listener);
		panel_6.add(btnPay);
		
		JPanel panel_8 = new JPanel();
		panel.add(panel_8, BorderLayout.WEST);
		
		JPanel panel_9 = new JPanel();
		FlowLayout flowLayout = (FlowLayout) panel_9.getLayout();
		flowLayout.setVgap(0);
		panel.add(panel_9, BorderLayout.EAST);
		
		btnX = new JButton("X");
		btnX.addActionListener(listener);
		panel_9.add(btnX);
		
		JPanel panel_7 = new JPanel();
		panel.add(panel_7, BorderLayout.CENTER);
		panel_7.setLayout(new BorderLayout(0, 0));
		
		scrollPane = new JScrollPane();
		panel_7.add(scrollPane);
		
		list = new JList();
		list.setListData(testCart);
		scrollPane.setViewportView(list);
		list.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
	
		JPanel panel_12 = new JPanel();
		panel_12.setBackground(Color.WHITE);
		panel_7.add(panel_12, BorderLayout.SOUTH);
		panel_12.setLayout(new BorderLayout(0, 0));
		
		lblTotal = new JLabel("Total:");
		lblTotal.setBackground(Color.WHITE);
		panel_12.add(lblTotal, BorderLayout.WEST);
		
		textPaneTotal = new JTextPane();
		textPaneTotal.setEditable(false);
		textPaneTotal.setText("$"+ total/100.0);
		panel_12.add(textPaneTotal, BorderLayout.CENTER);
		
		JPanel panel_2 = new JPanel();
		frame.getContentPane().add(panel_2, BorderLayout.NORTH);
		
		JLabel lblChalupaMachine = new JLabel("Chalupa Machine");
		panel_2.add(lblChalupaMachine);
		
		JPanel panel_11 = new JPanel();
		frame.getContentPane().add(panel_11, BorderLayout.WEST);
		panel_11.setLayout(new BorderLayout(0, 0));
		
		JPanel panel_13 = new JPanel();
		panel_11.add(panel_13, BorderLayout.EAST);
		
		JPanel panel_14 = new JPanel();
		panel_11.add(panel_14, BorderLayout.WEST);
		
		//Set up the panel which will display what is in the vending machine
		JPanel machineDisplayPanel = new JPanel();
		panel_11.add(machineDisplayPanel, BorderLayout.CENTER);
		int rows = bridge.vendingMachine().height();
		int cols = bridge.vendingMachine().width();
		machineDisplayPanel.setLayout(new GridLayout(rows, cols, 0, 0));
		
		// loop through the vending machine 2D array of slots, add their info to the grid layout. 
		// Create the display
		itemNameLabels = new ArrayList<ArrayList<JLabel>>(); // create a new 2D array to hold the item JLabels
		
		for (int row = 0; row < rows; row++){
			ArrayList<JLabel> rowOfJLabels = new ArrayList<JLabel>(); //row of JLabels to add to the 2D array
			
			for (int col = 0; col < cols; col++){
				
				Slot currentSlot = bridge.vendingMachine().slotAtCoordinates(row, col);
				
				JPanel cell = new JPanel();
				cell.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
				machineDisplayPanel.add(cell);
				cell.setLayout(new GridLayout(0, 1, 0, 0));
				
				JPanel innerCell = new JPanel();
				cell.add(innerCell);
				String cellNumber = (row+1) + "" + (col+1);  
				innerCell.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), cellNumber, TitledBorder.CENTER, TitledBorder.BOTTOM, null, null));
				innerCell.setLayout(new GridLayout(2, 1, 0, 0));
				
				JPanel itemNameQuantity = new JPanel();
				innerCell.add(itemNameQuantity);
				
				String itemName;
				if (currentSlot == null){
					itemName = "Empty (0)";
				}else
					itemName = currentSlot.getName() + " (" + currentSlot.quantity() + ")"; 
				JLabel itemNameLabel = new JLabel(itemName);
				rowOfJLabels.add(itemNameLabel);
				itemNameQuantity.add(itemNameLabel);
				
				JPanel itemPrice = new JPanel();
				FlowLayout fl_itemPrice = (FlowLayout) itemPrice.getLayout();
				fl_itemPrice.setVgap(0);
				innerCell.add(itemPrice);
				
				Double priceDouble;
				if (currentSlot == null){
					priceDouble = 0.0;
				}else
					priceDouble = currentSlot.getPrice()/100.00;
				JLabel itemPriceLabel = new JLabel("$" + priceDouble);
				itemPrice.add(itemPriceLabel);
			}
			
			itemNameLabels.add(rowOfJLabels);
		}
		
		
		updateTotal();	
	}

	/**
	 *  Private button listener class.
	 * 
	 */
	private class ButtonListener implements ActionListener{
		public void actionPerformed(ActionEvent e) {

			String buttonString = e.getActionCommand();
			
			for (int i = 1; i < 10; i++){
				if (buttonString.equals(String.valueOf(i))){
					if (numberStringEntered.length() <= 1){
						numberStringEntered += i;
						numDisplay.setForeground(Color.BLACK);
						numDisplay.setText(numberStringEntered);
					}
				}
			}
			
			if (buttonString.equals("ENTER")){
				try{
					itemNumberInput = Integer.parseInt(numberStringEntered);
				}
				catch(Exception f){
					return;
				}
				numberStringEntered = ""; //Clear the input
				numDisplay.setText("");
				try{
					bridge.addItemToCart(itemNumberInput,1);
				}
				catch(IllegalArgumentException err){
					System.err.println(err.getMessage());
					numDisplay.setText("Item is either depleted, recalled, or expired");
					numDisplay.setForeground(Color.RED);
				}
			}
			else if (buttonString.equals("Pay")){
				try{
					bridge.finishPayment(MACHINENAME);
				}
				catch(Exception ex){
					System.err.println(ex.getMessage());
				}
				//Update all item JLabels
				for (int row = 0; row < bridge.vendingMachine().height(); row ++){
					for (int col = 0; col < bridge.vendingMachine().width(); col ++){
						Slot currentSlot = bridge.vendingMachine().slotAtCoordinates(row, col);
						if (currentSlot != null){ //only possibly need to update item label if there is a slot there
							JLabel itemNameLabel = itemNameLabels.get(row).get(col);
							itemNameLabel.setText(currentSlot.getName() + " (" + currentSlot.quantity() + ")");
						}
					}
				}
			}
			else if (buttonString.equals("X")){
				if (list.getSelectedIndex()==-1){
					return;
				}
				bridge.removeItemFromCartAtIndex(list.getSelectedIndex());
			}
			else if (buttonString.equals("Cancel")){
				bridge.cancelTransaction();
			}

				updateTotal();

		}
	}
	private void updateTotal(){
		textPaneTotal.setText(String.format("$%.2f",bridge.totalPrice()));
		ArrayList<String> items = bridge.itemsInCart();

		testCart=items.toArray(new String[items.size()]);
		list.setListData(testCart);


	}

}
