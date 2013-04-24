import java.awt.EventQueue;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;
import javax.swing.border.LineBorder;
import javax.swing.border.EtchedBorder;


/**
 * 
 * @author Ian Frasch (ijf1860@rit.edu)
 */
public class ManagerGUI {

	private JFrame frmSmartVendingManager;
	private JButton btnNewMachine,submitNewMachine,cancelNewMachine,btnSubmitNotesTo, discardButton;
	private ButtonListener listener;
	private JTextField itemNameField,nameBox,locBox;
	private final ButtonGroup buttonGroup = new ButtonGroup();
	private ManagerActionBridge bridge;
	private JFrame newMachineFrame; //pop up window that is displayed when a new machine is created
	private JFrame viewAllRequestsFrame;
	private JComboBox MachineSelectorFreeform, MachineChoiceRequest;
	private JTextArea NotesFromRestockTextArea,NotesToRestockerTextArea;
	private JComboBox rowBox,colBox,deBox;
	private JList<String> requestList, statusList, unsubmittedRequestList;
	private ArrayList<Request> requests;
	private ArrayList<Request> unsubmittedRequests;
	private JSpinner rowSpinner, colSpinner, quantitySpinner;
	private JRadioButton restockRadioBtn, removeRadioBtn;
	private DefaultListModel<String> unsubmittedRequestListModel;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ManagerGUI window = new ManagerGUI();
					window.frmSmartVendingManager.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public ManagerGUI() {
		bridge = new ManagerActionBridge();
		requests = new ArrayList<Request>();
		unsubmittedRequestListModel = new DefaultListModel<String>();
		initializeGUI();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initializeGUI() {
		listener = new ButtonListener();

		frmSmartVendingManager = new JFrame();
		frmSmartVendingManager.setTitle("Smart Vending Manager");
		frmSmartVendingManager.setBounds(100, 100, 535, 349);
		frmSmartVendingManager.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		frmSmartVendingManager.getContentPane().add(tabbedPane, BorderLayout.CENTER);
		
		JPanel machinesTab = new JPanel();
		tabbedPane.addTab("Machines", null, machinesTab, "Manage Vending Machines");
		machinesTab.setLayout(new BorderLayout(0, 0));
		String[] test = {"PoopMachine", "ButtsMachine", "Chalupa Machine"};
		
		JPanel machinesList = new JPanel();
		machinesTab.add(machinesList, BorderLayout.WEST);
		machinesList.setLayout(new BorderLayout(0, 0));
		
		
		JList list = new JList();
		list.setBorder(new LineBorder(new Color(0, 0, 0)));
		list.setListData(test);
		JScrollPane scrollPane = new JScrollPane(list);
		machinesList.add(scrollPane, BorderLayout.WEST);
		
		JLabel lblMachinesList = new JLabel("Machines List:");
		scrollPane.setColumnHeaderView(lblMachinesList);
		
		JPanel panel = new JPanel();
		machinesList.add(panel, BorderLayout.EAST);
		GridBagLayout gbl_panel = new GridBagLayout();
		gbl_panel.columnWidths = new int[]{139, 0};
		gbl_panel.rowHeights = new int[]{32, 0, 32, 0};
		gbl_panel.columnWeights = new double[]{0.0, Double.MIN_VALUE};
		gbl_panel.rowWeights = new double[]{0.0, 0.0, 0.0, Double.MIN_VALUE};
		panel.setLayout(gbl_panel);
		
		btnNewMachine = new JButton("+ New Machine");
		btnNewMachine.addActionListener(listener);
		GridBagConstraints gbc_btnNewMachine = new GridBagConstraints();
		gbc_btnNewMachine.insets = new Insets(0, 0, 5, 0);
		gbc_btnNewMachine.gridx = 0;
		gbc_btnNewMachine.gridy = 0;
		panel.add(btnNewMachine, gbc_btnNewMachine);
		
		JButton btnRemoveMachine = new JButton("Remove Selected");
		GridBagConstraints gbc_btnRemoveMachine = new GridBagConstraints();
		gbc_btnRemoveMachine.insets = new Insets(0, 0, 5, 0);
		gbc_btnRemoveMachine.gridx = 0;
		gbc_btnRemoveMachine.gridy = 1;
		panel.add(btnRemoveMachine, gbc_btnRemoveMachine);
		
		JPanel salesTab = new JPanel();
		tabbedPane.addTab("Sales", null, salesTab, "View sales and analytics");
		
		JPanel itemsTab = new JPanel();
		tabbedPane.addTab("Items", null, itemsTab, "Manage Items");
		
		JPanel restockTab = new JPanel();
		tabbedPane.addTab("Restock", null, restockTab, "Send a request to a restocker");
		restockTab.setLayout(new GridLayout(0, 3, 5, 0));
		
		JPanel CurrentRequestList = new JPanel();
		restockTab.add(CurrentRequestList);
		CurrentRequestList.setLayout(new BorderLayout(10, 5));
		
		JLabel lblCurrentRequestList = new JLabel("Current Request List:");
		lblCurrentRequestList.setHorizontalAlignment(SwingConstants.CENTER);
		CurrentRequestList.add(lblCurrentRequestList, BorderLayout.NORTH);
		
		
		
		unsubmittedRequestList = new JList<String>();
		unsubmittedRequestList.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		//RequestListScrollPane.setViewportView(unsubmittedRequestList);
		
		JScrollPane RequestListScrollPane = new JScrollPane(unsubmittedRequestList);
		CurrentRequestList.add(RequestListScrollPane, BorderLayout.CENTER);
		
		JButton submitRequestButton = new JButton("Submit Request");
		submitRequestButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				bridge.submitRequest(unsubmittedRequests);
			}
		});
		CurrentRequestList.add(submitRequestButton, BorderLayout.SOUTH);
		
		JPanel middlePane = new JPanel();
		restockTab.add(middlePane);
		middlePane.setLayout(new BorderLayout(0, 0));
		
		JPanel AddRemovePane = new JPanel();
		middlePane.add(AddRemovePane);
		AddRemovePane.setLayout(new GridLayout(2, 1, 0, 30));
		
		JButton AddToListButton = new JButton("<<< Add To List");
		AddToListButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				addToRequestList();
			}
		});
		AddRemovePane.add(AddToListButton);
		
		JButton RemoveFromListButton = new JButton("Remove From List >>>");
		AddRemovePane.add(RemoveFromListButton);
		
		JPanel statusPane = new JPanel();
		FlowLayout flowLayout = (FlowLayout) statusPane.getLayout();
		middlePane.add(statusPane, BorderLayout.NORTH);
		
		JButton viewRequestsButton = new JButton("View/Edit All Requests");
		viewRequestsButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				viewAllRequests();
			}
		});
		statusPane.add(viewRequestsButton);
		
		JPanel NewRequestPane = new JPanel();
		restockTab.add(NewRequestPane);
		NewRequestPane.setLayout(new BorderLayout(0, 5));
		
		JButton btnNewButton = new JButton("Clear All Fields");
		NewRequestPane.add(btnNewButton, BorderLayout.SOUTH);
		
		JPanel MachineNumberGrid = new JPanel();
		NewRequestPane.add(MachineNumberGrid, BorderLayout.NORTH);
		MachineNumberGrid.setLayout(new GridLayout(0, 2, 0, 5));
		
		JLabel lblMachineRequest = new JLabel("Machine:");
		MachineNumberGrid.add(lblMachineRequest);
		
		
		
		JPanel FillInPane = new JPanel();
		NewRequestPane.add(FillInPane, BorderLayout.CENTER);
		FillInPane.setLayout(new GridLayout(5, 1, 0, 15));
		
		JPanel RowFillInPane = new JPanel();
		FillInPane.add(RowFillInPane);
		RowFillInPane.setLayout(new GridLayout(1, 2, 0, 0));
		
		JLabel lblrow = new JLabel("Row:");
		RowFillInPane.add(lblrow);
		
		rowSpinner = new JSpinner();
		rowSpinner.setModel(new SpinnerNumberModel(new Integer(0), new Integer(0), null, new Integer(1)));
		RowFillInPane.add(rowSpinner);
		
		JPanel ColFillInPane = new JPanel();
		FillInPane.add(ColFillInPane);
		ColFillInPane.setLayout(new GridLayout(1, 2, 0, 0));
		
		JLabel lblcol = new JLabel("Column:");
		ColFillInPane.add(lblcol);
		
		colSpinner = new JSpinner();
		colSpinner.setModel(new SpinnerNumberModel(new Integer(0), new Integer(0), null, new Integer(1)));
		ColFillInPane.add(colSpinner);
		
		JPanel NameFillInPane = new JPanel();
		FillInPane.add(NameFillInPane);
		NameFillInPane.setLayout(new GridLayout(0, 2, 0, 0));
		
		JLabel lblName = new JLabel("Item Name:");
		NameFillInPane.add(lblName);
		
		itemNameField = new JTextField();
		NameFillInPane.add(itemNameField);
		itemNameField.setColumns(16);
		
		JPanel QuantityFillInPane = new JPanel();
		FillInPane.add(QuantityFillInPane);
		QuantityFillInPane.setLayout(new GridLayout(0, 2, 0, 0));
		
		JLabel lblQuantity = new JLabel("Quantity:");
		QuantityFillInPane.add(lblQuantity);
		
		quantitySpinner = new JSpinner();
		quantitySpinner.setModel(new SpinnerNumberModel(new Integer(0), new Integer(0), null, new Integer(1)));
		QuantityFillInPane.add(quantitySpinner);
		
		JPanel ActionFillInPane = new JPanel();
		FillInPane.add(ActionFillInPane);
		ActionFillInPane.setLayout(new GridLayout(0, 2, 0, 0));
		
		restockRadioBtn = new JRadioButton("Restock");
		buttonGroup.add(restockRadioBtn);
		ActionFillInPane.add(restockRadioBtn);
		
		removeRadioBtn = new JRadioButton("Remove");
		buttonGroup.add(removeRadioBtn);
		ActionFillInPane.add(removeRadioBtn);
		
		restockRadioBtn.setSelected(true);
		
		JPanel notesPane = new JPanel();
		tabbedPane.addTab("Freeform Notes", null, notesPane, null);
		notesPane.setLayout(new BorderLayout(0, 0));
		
		JPanel NotePaneNorth = new JPanel();
		notesPane.add(NotePaneNorth, BorderLayout.NORTH);
		NotePaneNorth.setLayout(new GridLayout(0, 2, 0, 0));
		
		JLabel lblSelectAMachine = new JLabel("Select a machine to view/create notes for:");
		NotePaneNorth.add(lblSelectAMachine);
		
		ArrayList<VendingMachine> machinesAL = bridge.vendingMachines();
		ArrayList<String> names = new ArrayList<String>();
		int count = 0;
		for(VendingMachine v : machinesAL){
			names.add(v.name());
		}
		MachineSelectorFreeform = new JComboBox(names.toArray());
		MachineSelectorFreeform.addActionListener(listener);
		NotePaneNorth.add(MachineSelectorFreeform);
		
		JComboBox MachineChoiceRequest = new JComboBox(MachineSelectorFreeform.getModel());
		MachineNumberGrid.add(MachineChoiceRequest);
		
		JPanel MainPanelNotes = new JPanel();
		notesPane.add(MainPanelNotes, BorderLayout.CENTER);
		MainPanelNotes.setLayout(new GridLayout(2, 0, 0, 10));
		
		JPanel NotesToMangerFreeformPane = new JPanel();
		MainPanelNotes.add(NotesToMangerFreeformPane);
		NotesToMangerFreeformPane.setLayout(new BorderLayout(0, 0));
		
		JLabel lblNotesFromRestocker = new JLabel("Notes From Restocker:");
		NotesToMangerFreeformPane.add(lblNotesFromRestocker, BorderLayout.NORTH);
		
		JScrollPane NotesFromRestockScrollpane = new JScrollPane();
		NotesToMangerFreeformPane.add(NotesFromRestockScrollpane, BorderLayout.CENTER);
		


		NotesFromRestockTextArea = new JTextArea();
		NotesFromRestockTextArea.setWrapStyleWord(true);
		NotesFromRestockTextArea.setLineWrap(true);
		NotesFromRestockTextArea.setEditable(false);
		//Sort of Dirty tricks below.  Please don't kill me// - Ben
		//This makes no sense and I'm going to kill you. - Ian
		if(bridge.vendingMachines().size()>0){
			NotesFromRestockTextArea.setText(bridge.getManagerNotesForMachine(bridge.vendingMachines().get(0)));
		}
		//End of dirtyness
		NotesFromRestockScrollpane.setViewportView(NotesFromRestockTextArea);
		
		JPanel NotesFromManagerFreeformPane = new JPanel();
		MainPanelNotes.add(NotesFromManagerFreeformPane);
		NotesFromManagerFreeformPane.setLayout(new BorderLayout(0, 0));
		
		JLabel lblNotesToRestocker = new JLabel("Notes To Restocker:");
		NotesFromManagerFreeformPane.add(lblNotesToRestocker, BorderLayout.NORTH);
		
		JScrollPane NotesToRestockerScrollpane = new JScrollPane();
		NotesFromManagerFreeformPane.add(NotesToRestockerScrollpane, BorderLayout.CENTER);
		
		NotesToRestockerTextArea = new JTextArea();
		NotesToRestockerTextArea.setWrapStyleWord(true);
		NotesToRestockerTextArea.setLineWrap(true);
		NotesToRestockerScrollpane.setViewportView(NotesToRestockerTextArea);
		btnSubmitNotesTo = new JButton("Submit Notes To Restocker");
		btnSubmitNotesTo.addActionListener(listener);
		NotesFromManagerFreeformPane.add(btnSubmitNotesTo, BorderLayout.SOUTH);
	}
	
	private class ButtonListener implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			if(e.getSource() == btnNewMachine){
				newMachine();
			}
			else if(e.getSource() == submitNewMachine){
				//Ben has to go but when he gets back he'll add code here to make new machine work :D
				//locBox,nameBox,rowBox,colBox,deBox;
				VendingMachine vm = new VendingMachine(rowBox.getSelectedIndex()+2,colBox.getSelectedIndex()+2,deBox.getSelectedIndex(),nameBox.getText());
				newMachineFrame.dispose();
				bridge.addVendingMachine(vm);
				MachineSelectorFreeform.addItem(vm.name());	
				MachineChoiceRequest = new JComboBox(MachineSelectorFreeform.getModel());
			}
			else if(e.getSource() == cancelNewMachine){
				newMachineFrame.dispose();
			}
			else if(e.getSource()==MachineSelectorFreeform){
				VendingMachine curMachine = bridge.vendingMachines().get(MachineSelectorFreeform.getSelectedIndex());
				NotesFromRestockTextArea.setText(bridge.getManagerNotesForMachine(curMachine));
				if (NotesFromRestockTextArea.getText().equals("")){
					NotesFromRestockTextArea.setText("No notes for this machine at this time");
				}
			}
			else if(e.getSource()==btnSubmitNotesTo){
				VendingMachine curMachine = bridge.vendingMachines().get(MachineSelectorFreeform.getSelectedIndex());

				String boxText = NotesToRestockerTextArea.getText();
				try{
					curMachine.writeRestockerNotes(boxText);
					JOptionPane.showMessageDialog(null,"Notes Sumbitted Sucessfully!");
				}
				catch(Exception ex){
					System.err.println("Couldn't write notes");
				}
			}		
			else if (e.getSource() == discardButton){
				int[] indices = requestList.getSelectedIndices();
				// discard requests by removing the specific request object; 
				// start from highest index and decrement so the lower array indices don't change
				for (int i = indices.length-1; i != -1; i--){ 
					requestList.remove(i);
					statusList.remove(i);
					requests.remove(i);
				}
				// rewrite requests file
				bridge.updateRequests(requests);
			}
		}
	}
	
	private void newMachine(){

		newMachineFrame = new JFrame();

		newMachineFrame.setTitle("New Machine");
		newMachineFrame.setBounds(400, 200, 1500, 600);
		JPanel p = new JPanel();
		p.setLayout(new GridLayout(6,2));

		JLabel name,rows,slots,cols,location;
		name = new JLabel("Name: ");
		rows = new JLabel("Rows: ");
		slots = new JLabel("Slots: ");
		cols = new JLabel("Columns: ");
		location = new JLabel("Location: ");
		String [] Depths = {"1","2","3","4","5","6","7","8","9"};
		String [] Rows = {"3","4","5","6","7","8","9"};
		String [] Cols = {"3","4","5","6","7","8","9"};
		rowBox=new JComboBox(Rows);
		colBox=new JComboBox(Cols);
		deBox=new JComboBox(Depths);
		nameBox = new JTextField();
		locBox= new JTextField();
		p.add(name);
		p.add(nameBox);
		p.add(rows);
		p.add(rowBox);
		p.add(cols);
		p.add(colBox);
		p.add(slots);
		p.add(deBox);
		p.add(location);
		p.add(locBox);
		submitNewMachine = new JButton("Submit");
		submitNewMachine.addActionListener(listener);

		cancelNewMachine = new JButton("Cancel");
		cancelNewMachine.addActionListener(listener);
		p.add(cancelNewMachine);

		p.add(submitNewMachine);
		newMachineFrame.getContentPane().add(p);

		newMachineFrame.pack();

		newMachineFrame.setVisible(true);
	}
	
	/**
	 * Add a request to the request list in the restock tab.
	 */
	private void addToRequestList(){
		String machineName = bridge.vendingMachines().get(MachineChoiceRequest.getSelectedIndex()).name();
		int row = (int)rowSpinner.getValue();
		int col = (int)colSpinner.getValue();
		String itemName = itemNameField.getText();
		int quantity = (int)quantitySpinner.getValue();
		String action;
		if (restockRadioBtn.isSelected())
			action = "restock";
		else
			action = "remove";
		Request request = new Request(machineName, row, col, itemName, quantity, action, 0);
		unsubmittedRequests.add(request);
		
		unsubmittedRequestListModel.addElement(request.toString());
		unsubmittedRequestList.setModel(unsubmittedRequestListModel);
	}
	
	/**
	 * Remove a request from the request list in the restock tab.
	 */
	private void removeFromRequestList(){
		//unsubmittedRequests.remove(request);
		// Ian will write this
	}
	
	/**
	 * Pop up window that shows status of all requests, and allows removal.
	 */
	private void viewAllRequests(){
		
		viewAllRequestsFrame = new JFrame("Sent Requests");
		viewAllRequestsFrame.setBounds(300, 200, 500, 500);
		viewAllRequestsFrame.getContentPane().setLayout(new BorderLayout(0, 0));
		
		try {
			requests = bridge.getAllRequests();
			//Create list models so they can be added to a JList
			DefaultListModel<String> requestListModel = new DefaultListModel<String>();
			DefaultListModel<String> statusListModel = new DefaultListModel<String>();
			// Add request elements to list models
			for (Request request : requests){
				requestListModel.addElement(request.toString());
				int completed = request.getQuantityCompleted();
				if (completed >= request.getQuantity())
					statusListModel.addElement("Complete");
				else if (completed > 0 && completed < request.getQuantity())
					statusListModel.addElement("Completed " + completed);
				else 
					statusListModel.addElement("Incomplete");
			}
			
			requestList = new JList<String>(requestListModel);
			statusList = new JList<String>(statusListModel);
			//set statusList elements unselectable to avoid confusion
			statusList.setCellRenderer(new DefaultListCellRenderer() { 
			    public Component getListCellRendererComponent(JList list, Object value, int index,
			            boolean isSelected, boolean cellHasFocus) {
			        super.getListCellRendererComponent(list, value, index, false, false);
			        return this;
			    }
			});
			
			//Set up scroll panes to move in sync
			JScrollPane requestScrollPane = new JScrollPane(requestList);
			requestScrollPane.setEnabled(false);
			requestScrollPane.setColumnHeaderView(new JLabel("Request"));
			requestScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
			JScrollBar requestScrollBar = requestScrollPane.getVerticalScrollBar();
			
			JScrollPane statusScrollPane = new JScrollPane(statusList);
			statusScrollPane.setColumnHeaderView(new JLabel("Status"));
			JScrollBar statusScrollBar = statusScrollPane.getVerticalScrollBar();
			requestScrollBar.setModel(statusScrollBar.getModel());
			
			//Set up panels
			JPanel listPanel = new JPanel();
			listPanel.setLayout(new BorderLayout());
			listPanel.add(requestScrollPane, BorderLayout.CENTER);
			listPanel.add(statusScrollPane, BorderLayout.EAST);
			viewAllRequestsFrame.getContentPane().add(listPanel, BorderLayout.CENTER);	
			
			JPanel buttonPanel = new JPanel();
			
			// Discard Request button
			discardButton = new JButton("Discard Request");
			discardButton.addActionListener(listener);
			buttonPanel.add(discardButton);
			viewAllRequestsFrame.getContentPane().add(buttonPanel, BorderLayout.EAST);
			
			
		} catch (IOException e) {
			System.err.println("Error reading request files.");
		}
		
		
		viewAllRequestsFrame.setVisible(true);
	}
	
	private static void addPopup(Component component, final JPopupMenu popup) {
		component.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				if (e.isPopupTrigger()) {
					showMenu(e);
				}
			}
			public void mouseReleased(MouseEvent e) {
				if (e.isPopupTrigger()) {
					showMenu(e);
				}
			}
			private void showMenu(MouseEvent e) {
				popup.show(e.getComponent(), e.getX(), e.getY());
			}
		});
	}
}
