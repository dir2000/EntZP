package com.zhurylomihaylo.www.entzp;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.math.*;
import java.text.*;
import javax.swing.*;
import javax.swing.table.*;
import javax.swing.text.*;

class MainFrame extends JFrame{
	static final int DEFAULT_WIDTH = 700;
	//static final int DEFAULT_HEIGHT = 150;
	
	private JPanel settingsPane;
	JButton addBankButton;
	JButton deleteBankButton;
	private JTable bankTable;
	private BankTableModel tableModel;
	
	MainFrame(){
		buildGUI();
		defineListeners();
	}
	
	void buildGUI(){
		//setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);
		
		settingsPane = new JPanel();
		settingsPane.setLayout(new BoxLayout(settingsPane, BoxLayout.Y_AXIS));
		//JOptionPane.showMessageDialog(this, arr == null);
		add(settingsPane, BorderLayout.NORTH);
		
		buildGUIBanks();
		buildGUIEsv();
		
		pack();
	}
	
	void buildGUIBanks(){
		JPanel banksPane = new JPanel();
		banksPane.setBorder(BorderFactory.createTitledBorder("Тарифи банків"));
		banksPane.setLayout(new BoxLayout(banksPane, BoxLayout.Y_AXIS));
	
		
		JPanel banksButtonsPane = new JPanel();
		banksButtonsPane.setLayout(new FlowLayout(FlowLayout.LEFT));
		addBankButton = new JButton("Додати");
		banksButtonsPane.add(addBankButton);
		deleteBankButton = new JButton("Вилучити");
		banksButtonsPane.add(deleteBankButton);
		banksPane.add(banksButtonsPane);
		
		
		tableModel = new BankTableModel(DataStorage.getBankList());
		bankTable = new JTable(tableModel);
		TableColumnModel columnModel = bankTable.getColumnModel();
		
		DefaultTableCellRenderer rightRenderer = new DefaultTableCellRenderer();
		rightRenderer.setHorizontalAlignment(DefaultTableCellRenderer.RIGHT);
		for (int i = 0; i < columnModel.getColumnCount(); i++) {
			TableColumn column = columnModel.getColumn(i);
			column.setHeaderValue(Bank.getFieldHeader(i));
			if(Bank.isFieldNumeric(i))
				column.setCellRenderer(rightRenderer);
		}
		
		for(int i : tableModel.getHiddenColumns()) {
			bankTable.getColumnModel().getColumn(i).setMinWidth(0);	
			bankTable.getColumnModel().getColumn(i).setMaxWidth(0);
		}
		
		InputMap im = bankTable.getInputMap(JTable.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
		KeyStroke enter = KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0);
		KeyStroke f2 = KeyStroke.getKeyStroke(KeyEvent.VK_F2, 0);
		im.put(enter, im.get(f2));		
		
		JScrollPane scrollPane = new JScrollPane(bankTable);
		scrollPane.setBorder(BorderFactory.createEtchedBorder());
		scrollPane.setPreferredSize(new Dimension(DEFAULT_WIDTH, 100));		
		banksPane.add(scrollPane);
		
		settingsPane.add(banksPane);		
	}
	
	void buildGUIEsv(){
		JPanel esvPane = new JPanel();
		esvPane.setLayout(new FlowLayout(FlowLayout.LEFT));
		
		esvPane.add(new JLabel("Мінімальна зарплата, грн., %"));
		makeFormattedField(esvPane, DataStorage.getMinSalary());
		
		esvPane.add(new JLabel("Ставка ЄСВ, %"));
		makeFormattedField(esvPane, DataStorage.getEsvRate());
		
		settingsPane.add(esvPane);
		
		//https://stackoverflow.com/questions/6089508/how-to-use-bigdecimal-with-jformattedtextfield
		//Then in the lostFocus method, on the line:
		//jFormattedTextField.commitEdit();
		
		//String number = ftf.getText().replace(",","");
		//BigDecimal bd = new BigDecimal(number);

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
	
	void defineListeners(){
		addBankButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				DataStorage.getBankList().add(new Bank("Новий банк"));
				tableModel.fireTableDataChanged();
			}
		});
		
		deleteBankButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int selectedRow = bankTable.getSelectedRow();
				if (selectedRow < 0)
					return;
				
				Bank bank = DataStorage.getBankList().get(selectedRow);
				int reply = JOptionPane.showOptionDialog(
						MainFrame.this,
						"Ви дійсно бажаєте вилучити \"" + bank.getName() + "\"?",
						"Увага",
						JOptionPane.YES_NO_OPTION,
						JOptionPane.QUESTION_MESSAGE,
						null,
						new String [] {"Так", "Ні"}, "Так");
		        if (reply == JOptionPane.YES_OPTION) {
		        	DataStorage.getBankList().remove(bank);
		        	tableModel.fireTableDataChanged();
		        }
			}
		});
		
	}
	
}