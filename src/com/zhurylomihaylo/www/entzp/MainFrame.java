package com.zhurylomihaylo.www.entzp;

import java.awt.*;
import java.awt.event.*;
import java.math.*;
import java.text.*;
import java.util.ArrayList;

import javax.swing.*;
import javax.swing.table.*;
import javax.swing.text.*;

class MainFrame extends JFrame{
	static final int DEFAULT_WIDTH = 700;

	EditableCellRendererComponent rendComp;
	
	private JPanel settingsPane;
	
	JPanel banksPane;
	JButton addBankButton;
	JButton deleteBankButton;
	private JTable bankTable;
	private EdiTableModel bankTableModel;
	
	private JPanel entPane;
	JButton addEntButton;
	JButton deleteEntButton;
	private JTable entTable;
	private EdiTableModel entTableModel;
	
	/******************** CONSTRUCTORS ********************/
	
	MainFrame(){
		buildGUI();
		setListeners();
	}
	
	/******************** NON-STATIC METHODS ********************/
	
	void buildGUI(){
		rendComp = new EditableCellRendererComponent();
		
		settingsPane = new JPanel();
		settingsPane.setLayout(new BoxLayout(settingsPane, BoxLayout.Y_AXIS));
		add(settingsPane, BorderLayout.NORTH);

		entPane = new JPanel();
		add(entPane, BorderLayout.CENTER);
		
		buildGUIBanks();
		buildGUIConsts();
		buildGUIEnts();
		
		pack();
	}
	void buildGUITablePane(Class cl, JPanel pane, String title, TableModel tableModel, JTable table, JButton addButton, JButton deleteButton){
		pane.setBorder(BorderFactory.createTitledBorder(title));
		pane.setLayout(new BoxLayout(pane, BoxLayout.Y_AXIS));
	
		JPanel buttonsPane = new JPanel();
		buttonsPane.setLayout(new FlowLayout(FlowLayout.LEFT));
		//addButton = new JButton("Додати");
		buttonsPane.add(addButton);
		//deleteButton = new JButton("Вилучити");
		buttonsPane.add(deleteButton);
		pane.add(buttonsPane);
		
		tableModel = new EdiTableModel(cl, DataStorage.getList(cl));
		table = new JTable(tableModel);
		TableColumnModel columnModel = table.getColumnModel();
		
		for (int i = 0; i < columnModel.getColumnCount(); i++) {
			TableColumn column = columnModel.getColumn(i);
			column.setHeaderValue(EdiTableObject.getFieldHeader(cl, i));
		}
		table.getTableHeader().setReorderingAllowed(false);
		table.setDefaultRenderer(Object.class, rendComp);
		
//		for(int i : bankTableModel.getHiddenColumns()) {
//			bankTable.getColumnModel().getColumn(i).setMinWidth(0);	
//			bankTable.getColumnModel().getColumn(i).setMaxWidth(0);
//		}
		
		InputMap im = table.getInputMap(JTable.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
		KeyStroke enter = KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0);
		KeyStroke f2 = KeyStroke.getKeyStroke(KeyEvent.VK_F2, 0);
		im.put(enter, im.get(f2));		
		
		JScrollPane scrollPane = new JScrollPane(table);
		scrollPane.setBorder(BorderFactory.createEtchedBorder());
		scrollPane.setPreferredSize(new Dimension(DEFAULT_WIDTH, 100));		
		pane.add(scrollPane);
	}
	
	void buildGUIBanks(){
		JPanel banksPane = new JPanel();
		JPanel banksButtonsPane = new JPanel();		
		addBankButton = new JButton("Додати банк");		
		deleteBankButton = new JButton("Вилучити");
		
//		Class bankClass = Bank.class;
//		DataStorage.getList(bankClass);
//		ArrayList<EdiTableObject> bankList = DataStorage.getList(bankClass);
//		bankTableModel = new EdiTableModel(bankClass, bankList);
		bankTableModel = new EdiTableModel(Bank.class, DataStorage.getList(Bank.class));
		bankTable = new JTable(bankTableModel);

		buildGUITablePane(Bank.class, banksPane, "Тарифи банків", bankTableModel, bankTable, addBankButton, deleteBankButton);		
		
		settingsPane.add(banksPane);		
	}
	
	void buildGUIConsts(){
		JPanel constPane = new JPanel();
		constPane.setLayout(new FlowLayout(FlowLayout.LEFT));
		
		constPane.add(new JLabel("Мінімальна зарплата, грн., %"));
		makeFormattedField(constPane, DataStorage.getMinSalary());
		
		constPane.add(new JLabel("Ставка ЄСВ, %"));
		makeFormattedField(constPane, DataStorage.getEsvPercent());

		constPane.add(new JLabel("Ставка єдиного податку, %"));
		makeFormattedField(constPane, DataStorage.getTaxPercent());

		constPane.add(new JLabel("Додатковий відсоток, %"));
		makeFormattedField(constPane, DataStorage.getAdditionalPercent());
		
		settingsPane.add(constPane);
		
		//https://stackoverflow.com/questions/6089508/how-to-use-bigdecimal-with-jformattedtextfield
		//Then in the lostFocus method, on the line:
		//jFormattedTextField.commitEdit();
		
		//String number = ftf.getText().replace(",","");
		//BigDecimal bd = new BigDecimal(number);

	}
	
	void buildGUIEnts(){
		JPanel buttonsPane = new JPanel();
		addEntButton = new JButton("Додати особу");
		deleteEntButton = new JButton("Вилучити");
		entTableModel = new EdiTableModel(Ent.class, DataStorage.getList(Ent.class));
		entTable = new JTable(entTableModel);

		buildGUITablePane(Ent.class, entPane, "Особи", entTableModel, entTable, addEntButton, deleteEntButton);
	}
	
	void makeFormattedField(JPanel pane, BigDecimal value){
		JFormattedTextField field = new JFormattedTextField(value);
		field.setPreferredSize(new Dimension(100, (int) field.getPreferredSize().getHeight()));
		field.setHorizontalAlignment(SwingConstants.RIGHT);
		
		DefaultFormatter fmt = new NumberFormatter(new DecimalFormat("################0"));
	    fmt.setValueClass(field.getValue().getClass());
	    DefaultFormatterFactory fmtFactory = new DefaultFormatterFactory(fmt, fmt, fmt);
	    field.setFormatterFactory(fmtFactory);
	    pane.add(field);

	   // BigDecimal bigValue = (BigDecimal) f.getValue();		
	}
	
	void setListeners(){
		setAddListener(addBankButton, Bank.class, bankTableModel);
//		
//		addBankButton.addActionListener(new ActionListener() {
//			@Override
//			public void actionPerformed(ActionEvent e) {
//				DataStorage.getList(Bank.class).add(new Bank("Новий банк"));
//				bankTableModel.fireTableDataChanged();
//			}
//		});
		
		deleteBankButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int selectedRow = bankTable.getSelectedRow();
				if (selectedRow < 0)
					return;
				
				EdiTableObject bank = DataStorage.getList(Bank.class).get(selectedRow);
				int reply = JOptionPane.showOptionDialog(
						MainFrame.this,
						"Ви дійсно бажаєте вилучити \"" + bank + "\"?",
						"Увага",
						JOptionPane.YES_NO_OPTION,
						JOptionPane.QUESTION_MESSAGE,
						null,
						new String [] {"Так", "Ні"}, "Так");
		        if (reply == JOptionPane.YES_OPTION) {
		        	DataStorage.getList(Bank.class).remove(bank);
		        	bankTableModel.fireTableDataChanged();
		        }
			}
		});
	}
	
	void setAddListener(JButton button, Class cl, EdiTableModel tableModel) {
		ActionListener al = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					DataStorage.getList(cl).add((EdiTableObject) cl.newInstance());
					tableModel.fireTableDataChanged();
				} catch(Exception ex) {
					JOptionPane.showMessageDialog(MainFrame.this, ex);
				}
			}
		};
		button.addActionListener(al);		
	}
	
}