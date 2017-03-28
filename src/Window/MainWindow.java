package Window;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.JOptionPane;
import javax.swing.JSplitPane;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JScrollPane;
import javax.swing.JList;
import javax.swing.AbstractListModel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.ListSelectionModel;
import javax.swing.JTabbedPane;

import org.json.JSONException;
import org.json.JSONObject;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import Data.ItemList;
import Data.VariablesList;

public class MainWindow extends JFrame {

	private JPanel contentPane;
	private JTable SelectionTable;
	private JTable FileTable;
	private JTable StatTable;
	private JTable EffectTable;
	private String curSel;
	private JSONObject tempVal;
	/**
	 * Launch the applictation.
	 */
	public static void main(String[] args) {
		VariablesList.readFromFile();
		ItemList.init();
		ItemList.addItem("new 1");
		ItemList.addItem("new 2");
		ItemList.addItem("new 3");
		ItemList.addItem("new 4");
		
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainWindow frame = new MainWindow();
					frame.setLocationRelativeTo(null);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public MainWindow() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 800, 600);
		
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		JMenu mnFile = new JMenu("File");
		menuBar.add(mnFile);
		
		JMenuItem mntmSaveVariablenames = new JMenuItem("Save VariableNames");
		mntmSaveVariablenames.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				VariablesList.writeToFile();
			}
		});
		mnFile.add(mntmSaveVariablenames);
		
		JMenuItem mntmLoadVariablenames = new JMenuItem("Load VariableNames");
		mntmLoadVariablenames.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				VariablesList.readFromFile();
			}
		});
		mnFile.add(mntmLoadVariablenames);
		
		JMenu mnItemsMenu = new JMenu("Item");
		menuBar.add(mnItemsMenu);
		JMenuItem mntmNewMenuItem = new JMenuItem("Add Item");
		mntmNewMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String newItemName = JOptionPane.showInputDialog("Input Item Name");
				ItemList.addItem(newItemName);
				
				SelectionTable.setModel(setSelectionModel(ItemList.getItemList(), new String[]{"#", "Name"}));
				SelectionTable.getColumnModel().getColumn(0).setResizable(false);
				SelectionTable.getColumnModel().getColumn(0).setPreferredWidth(25);
				SelectionTable.getColumnModel().getColumn(1).setResizable(false);
				SelectionTable.revalidate();
			}
		});
		mnItemsMenu.add(mntmNewMenuItem);
		
		JMenuItem mntmNewMenuItem_1 = new JMenuItem("Delete Item");
		mnItemsMenu.add(mntmNewMenuItem_1);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		JSplitPane splitPane = new JSplitPane();
		splitPane.setDividerLocation(200);
		contentPane.add(splitPane, BorderLayout.CENTER);
		
		JScrollPane scrollPane = new JScrollPane();
		splitPane.setLeftComponent(scrollPane);
		
		SelectionTable = new JTable();
		
		SelectionTable.getSelectionModel().addListSelectionListener(new ListSelectionListener(){
	        public void valueChanged(ListSelectionEvent event) {
	        	UpdateList(StatTable, "Effects", curSel);
	        	UpdateList(StatTable, "File", curSel);
	        	UpdateList(StatTable, "Stats", curSel);
	        	curSel = (String)SelectionTable.getValueAt(SelectionTable.getSelectedRow(), 1);
	        	FileTable.setModel(setTableModel(ItemList.getVarList(curSel  ,"File", VariablesList.File), new String[] {"Variable", "Value"}));
	        	StatTable.setModel(setTableModel(ItemList.getVarList(curSel  ,"Stats", VariablesList.Stats), new String[] {"Variable", "Value"}));
	        	EffectTable.setModel(setTableModel(ItemList.getVarList(curSel  ,"Effects", VariablesList.Effects), new String[] {"Variable", "Value"}));
	        }
	    });
		SelectionTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		SelectionTable.setModel(setSelectionModel(ItemList.getItemList(), new String[]{"#", "Name"}));
		SelectionTable.getColumnModel().getColumn(0).setResizable(false);
		SelectionTable.getColumnModel().getColumn(0).setPreferredWidth(25);
		SelectionTable.getColumnModel().getColumn(1).setResizable(false);
		scrollPane.setViewportView(SelectionTable);
		
		JScrollPane scrollPane_1 = new JScrollPane();
		splitPane.setRightComponent(scrollPane_1);
		
		JTabbedPane TableContainer = new JTabbedPane(JTabbedPane.TOP);
		scrollPane_1.setViewportView(TableContainer);
		
		JScrollPane FileContainer = new JScrollPane();
		TableContainer.addTab("File", null, FileContainer, null);
		
		FileTable = new JTable();
		FileTable.addFocusListener(new FocusListener(){
			public void focusGained(FocusEvent e) {
				UpdateList(FileTable, "File", curSel);
			}

			public void focusLost(FocusEvent e) {
				UpdateList(FileTable, "File", curSel);
			}
			
		});
		FileTable.setModel(new DefaultTableModel(
			null,
			new String[] {
				"Variable", "Value"
			}
		) {
			boolean[] columnEditables = new boolean[] {
				false, true
			};
			public boolean isCellEditable(int row, int column) {
				return columnEditables[column];
			}
		});
		FileContainer.setViewportView(FileTable);
		
		JScrollPane StatContainer = new JScrollPane();
		TableContainer.addTab("Stats", null, StatContainer, null);
		
		StatTable = new JTable();
		StatTable.addFocusListener(new FocusListener(){
			public void focusGained(FocusEvent e) {
				UpdateList(StatTable, "Stats", curSel);
			}

			public void focusLost(FocusEvent e) {
				UpdateList(StatTable, "Stats", curSel);
			}
			
		});
		StatTable.setModel(new DefaultTableModel(
			null,
			new String[] {
				"Variable", "Value"
			}
		) {
			boolean[] columnEditables = new boolean[] {
				false, true
			};
			public boolean isCellEditable(int row, int column) {
				return columnEditables[column];
			}
		});
		StatContainer.setViewportView(StatTable);
		
		JScrollPane EffectContainer = new JScrollPane();
		TableContainer.addTab("Effects", null, EffectContainer, null);
		
		EffectTable = new JTable();
		EffectTable.addFocusListener(new FocusListener(){
			public void focusGained(FocusEvent e) {
				UpdateList(EffectTable, "Effects", curSel);
			}

			public void focusLost(FocusEvent e) {
				UpdateList(EffectTable, "Effects", curSel);
			}
			
		});
		EffectTable.setModel(new DefaultTableModel(
			null,
			new String[] {
				"Variable", "Value"
			}
		) {
			boolean[] columnEditables = new boolean[] {
				false, true
			};
			public boolean isCellEditable(int row, int column) {
				return columnEditables[column];
			}
		});
		EffectContainer.setViewportView(EffectTable);
	}
	public static DefaultTableModel setTableModel(Object[][] newModel, String[] Header){
		DefaultTableModel retval = new DefaultTableModel(newModel, Header){
        	boolean[] columnEditables = new boolean[] {
    				false, true
    			};
    			public boolean isCellEditable(int row, int column) {
    				return columnEditables[column];
    		}
        };
		return retval;
	}
	public static DefaultTableModel setSelectionModel(Object[][] newModel, String[] Header){
		DefaultTableModel retval = new DefaultTableModel(newModel, Header){
				boolean[] columnEditables = new boolean[] {
					false, false
				};
				public boolean isCellEditable(int row, int column) {
					return columnEditables[column];
				}
			};
		return retval;
	}
	public static void UpdateList(JTable tableInput, String type, String Key){
		try {
			JSONObject tempVal = (JSONObject) ItemList.ItemList.get(Key);
			JSONObject tempVal2 = (JSONObject) tempVal.get(type);
			for(int a=0;a<tableInput.getRowCount();a++){
				tempVal2.put((String) tableInput.getValueAt(a, 0), (String)tableInput.getValueAt(a, 1));
			}
			tempVal.put(type, tempVal2);
			ItemList.ItemList.put(Key, tempVal);
		} catch (JSONException e1) {
			e1.printStackTrace();
		}
	}
}
