import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.SwingConstants;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JSpinner;
import javax.swing.JList;
import javax.swing.AbstractListModel;
import javax.swing.border.MatteBorder;
import java.awt.Color;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;
import javax.swing.JRadioButton;
import java.awt.FlowLayout;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

import javax.swing.ButtonGroup;

public class RestockerGUI {

	private JFrame MainPanel;
	private JTextField DateTextField;
	private JTextField ExpirationDateTxtField;
	private final ButtonGroup buttonGroup = new ButtonGroup();
	private ActionListener listener;
	private JComboBox comboBox;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					RestockerGUI window = new RestockerGUI();
					window.MainPanel.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public RestockerGUI() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		listener = new ButtonListener();

		ManagerActionBridge ManagerBridge = new ManagerActionBridge();
		MainPanel = new JFrame();
		//MainPanel.setResizable(false);
		MainPanel.setTitle("Smart Vending Restocker");
		MainPanel.setBounds(100, 100, 530, 557);
		MainPanel.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JPanel MainNorthGrid = new JPanel();
		MainPanel.getContentPane().add(MainNorthGrid, BorderLayout.NORTH);
		MainNorthGrid.setLayout(new GridLayout(2, 1, 0, 0));
		
		JLabel HeaderTXT = new JLabel("M.C. Vending Enterprises");
		HeaderTXT.setHorizontalAlignment(SwingConstants.CENTER);
		HeaderTXT.setFont(new Font("Tahoma", Font.PLAIN, 20));
		MainNorthGrid.add(HeaderTXT);
		
		JPanel NorthGridSouth = new JPanel();
		MainNorthGrid.add(NorthGridSouth);
		NorthGridSouth.setLayout(new GridLayout(0, 2, 15, 0));
		
		JPanel NorthWest = new JPanel();
		NorthGridSouth.add(NorthWest);
		
		JLabel lblDate = new JLabel("Date:");
		lblDate.setFont(new Font("Tahoma", Font.PLAIN, 15));
		NorthWest.add(lblDate);
		
		DateTextField = new JTextField();
		DateTextField.setFont(new Font("Tahoma", Font.PLAIN, 15));
		DateTextField.setEditable(false);
		DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
		Calendar cal = Calendar.getInstance();
		DateTextField.setText(dateFormat.format(cal.getTime()));
		NorthWest.add(DateTextField);
		DateTextField.setColumns(10);
		
		JPanel NorthEast = new JPanel();
		NorthGridSouth.add(NorthEast);
		
		JLabel lblMachines = new JLabel("Machine ID:");
		lblMachines.setFont(new Font("Tahoma", Font.PLAIN, 15));
		NorthEast.add(lblMachines);

		ArrayList<VendingMachine> machinesAL = ManagerBridge.vendingMachines();
		ArrayList<String> names = new ArrayList<String>();
		int count = 0;
		for(VendingMachine v : machinesAL){
			names.add(v.name());
		}
		comboBox = new JComboBox(names.toArray());
		comboBox.setFont(new Font("Tahoma", Font.PLAIN, 15));
		comboBox.addActionListener(listener);
		NorthEast.add(comboBox);
		
		JPanel MainSouthGrid = new JPanel();
		MainPanel.getContentPane().add(MainSouthGrid, BorderLayout.SOUTH);
		MainSouthGrid.setLayout(new GridLayout(2, 0, 0, 0));
		
		JButton btnConfirmRestock = new JButton("Confirm Restock");
		btnConfirmRestock.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		
		JPanel SouthNorthGrid = new JPanel();
		MainSouthGrid.add(SouthNorthGrid);
		SouthNorthGrid.setLayout(new GridLayout(0, 2, 0, 0));
		
		JPanel SouthNorthPanel1 = new JPanel();
		SouthNorthGrid.add(SouthNorthPanel1);
		SouthNorthPanel1.setLayout(new BorderLayout(0, 0));
		
		JPanel RadioPanel = new JPanel();
		SouthNorthPanel1.add(RadioPanel, BorderLayout.WEST);
		RadioPanel.setLayout(new GridLayout(0, 2, 0, 0));
		
		JRadioButton AddButton = new JRadioButton("Add");
		AddButton.setSelected(true);
		buttonGroup.add(AddButton);
		RadioPanel.add(AddButton);
		
		JRadioButton RemoveButton = new JRadioButton("Remove");
		buttonGroup.add(RemoveButton);
		RadioPanel.add(RemoveButton);
		
		JPanel RadioButton = new JPanel();
		SouthNorthPanel1.add(RadioButton, BorderLayout.SOUTH);
		RadioButton.setLayout(new GridLayout(0, 2, 0, 0));
		
		JLabel lblNewLabel = new JLabel("Total Restocked:");
		RadioButton.add(lblNewLabel);
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 15));
		
		JSpinner RestockedSpinner = new JSpinner();
		RadioButton.add(RestockedSpinner);
		RestockedSpinner.setFont(new Font("Tahoma", Font.PLAIN, 15));
		
		JPanel SouthNorthPanel2 = new JPanel();
		SouthNorthGrid.add(SouthNorthPanel2);
		SouthNorthPanel2.setLayout(new BorderLayout(0, 0));
		
		JLabel lblNewLabel_1 = new JLabel("Expiration Date: ");
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 15));
		SouthNorthPanel2.add(lblNewLabel_1, BorderLayout.WEST);
		
		ExpirationDateTxtField = new JTextField();
		ExpirationDateTxtField.setFont(new Font("Tahoma", Font.PLAIN, 15));
		SouthNorthPanel2.add(ExpirationDateTxtField, BorderLayout.SOUTH);
		ExpirationDateTxtField.setColumns(10);
		btnConfirmRestock.setFont(new Font("Tahoma", Font.PLAIN, 15));
		MainSouthGrid.add(btnConfirmRestock);
		
		JPanel MainCenter = new JPanel();
		MainPanel.getContentPane().add(MainCenter, BorderLayout.CENTER);
		MainCenter.setLayout(new GridLayout(3, 1, 0, 15));
		
		JPanel NotesPanel = new JPanel();
		MainCenter.add(NotesPanel);
		NotesPanel.setLayout(new BorderLayout(0, 0));
		
		JLabel lblNotesFromManager = new JLabel("Notes From Manager:");
		NotesPanel.add(lblNotesFromManager, BorderLayout.NORTH);
		
		JTextArea ManagerNotesTXTField = new JTextArea();
		
		JScrollPane scrollPaneManagerNotes = new JScrollPane(ManagerNotesTXTField);
		NotesPanel.add(scrollPaneManagerNotes, BorderLayout.CENTER);
		
		
		ManagerNotesTXTField.setWrapStyleWord(true);
		ManagerNotesTXTField.setLineWrap(true);
		ManagerNotesTXTField.setText("Messages, yo");
		ManagerNotesTXTField.setFont(new Font("Tahoma", Font.PLAIN, 11));
		ManagerNotesTXTField.setEditable(false);
		
		JPanel ResponsePanel = new JPanel();
		MainCenter.add(ResponsePanel);
		ResponsePanel.setLayout(new BorderLayout(0, 0));
		
		JLabel lblNotesToManager = new JLabel("Notes To Manager: Please Record And Submit");
		ResponsePanel.add(lblNotesToManager, BorderLayout.NORTH);
		
		JScrollPane NotesToManagerScrollPane = new JScrollPane();
		ResponsePanel.add(NotesToManagerScrollPane, BorderLayout.CENTER);
		
		JTextArea NotesFromRestockerField = new JTextArea();
		NotesFromRestockerField.setFont(new Font("Arial", Font.PLAIN, 11));
		NotesToManagerScrollPane.setViewportView(NotesFromRestockerField);
		
		JButton btnSubmitResponse = new JButton("Submit Restocker Response");
		ResponsePanel.add(btnSubmitResponse, BorderLayout.SOUTH);
		//NotesPanel.add(ManagerNotesTXTField, BorderLayout.CENTER);
		
		JPanel InstructionsPanel = new JPanel();
		MainCenter.add(InstructionsPanel);
		InstructionsPanel.setLayout(new BorderLayout(0, 0));
		
		JLabel RestockInstructionsLabel = new JLabel("Restock Instructions:");
		InstructionsPanel.add(RestockInstructionsLabel, BorderLayout.NORTH);
		
		
		JList PrimaryList = new JList();
		
		JScrollPane scrollPane = new JScrollPane(PrimaryList);
		InstructionsPanel.add(scrollPane, BorderLayout.CENTER);
		
		
		PrimaryList.setModel(new AbstractListModel() {
			String[] values = new String[] {};
			public int getSize() {
				return values.length;
			}
			public Object getElementAt(int index) {
				return values[index];
			}
		});
		PrimaryList.setBorder(new MatteBorder(2, 2, 2, 2, (Color) Color.GRAY));
	}
	private class ButtonListener implements ActionListener{
			public void actionPerformed(ActionEvent e) {
				if(e.getSource()==comboBox){
					System.out.println(comboBox.getSelectedIndex());
				}
			}
		}
}
