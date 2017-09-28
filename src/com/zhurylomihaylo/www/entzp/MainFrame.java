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
	JButton addBankButton;
	JButton deleteBankButton;
	private JTable bankTable;
	private BankTableModel bankTableModel;
	
	private JPanel entPane;
	JButton addEntButton;
	JButton deleteEntButton;
	private JTable entTable;
	private EntTableModel entTableModel;
	
	/******************** CONSTRUCTORS ********************/
	
	MainFrame(){
		buildGUI();
		defineListeners();
	}
	
	/******************** NON-STATIC METHODS ********************/
	
	void buildGUI(){
		rendComp = new EditableCellRendererComponent();
		
		settingsPane = new JPanel();
		settingsPane.setLayout(new BoxLayout(settingsPane, BoxLayout.Y_AXIS));
		add(settingsPane, BorderLayout.NORTH);

		entPane = new JPanel();
		entPane.setBorder(BorderFactory.createTitledBorder("Особи"));
		entPane.setLayout(new BoxLayout(entPane, BoxLayout.Y_AXIS));
		add(entPane, BorderLayout.CENTER);
		
		buildGUIBanks();
		buildGUIConsts();
		buildGUIEnts();
		
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
		
		
		bankTableModel = new BankTableModel(DataStorage.getBankList());
		bankTable = new JTable(bankTableModel);
		TableColumnModel columnModel = bankTable.getColumnModel();
		
		for (int i = 0; i < columnModel.getColumnCount(); i++) {
			TableColumn column = columnModel.getColumn(i);
			column.setHeaderValue(Bank.getFieldHeader(i));
		}
		bankTable.getTableHeader().setReorderingAllowed(false);
		bankTable.setDefaultRenderer(Object.class, rendComp);
		
		for(int i : bankTableModel.getHiddenColumns()) {
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
		buttonsPane.setLayout(new FlowLayout(FlowLayout.LEFT));
		addEntButton = new JButton("Додати");
		buttonsPane.add(addEntButton);
		deleteEntButton = new JButton("Вилучити");
		buttonsPane.add(deleteEntButton);
		entPane.add(buttonsPane);
		
		entTableModel = new EntTableModel(DataStorage.getEntList());
		entTable = new JTable(entTableModel);
		TableColumnModel columnModel = entTable.getColumnModel();
		
		for (int i = 0; i < columnModel.getColumnCount(); i++) {
			TableColumn column = columnModel.getColumn(i);
			column.setHeaderValue(Ent.getFieldHeader(i));
		}
		entTable.getTableHeader().setReorderingAllowed(false);
		entTable.setDefaultRenderer(Object.class, rendComp);
		
		InputMap im = entTable.getInputMap(JTable.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
		KeyStroke enter = KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0);
		KeyStroke f2 = KeyStroke.getKeyStroke(KeyEvent.VK_F2, 0);
		im.put(enter, im.get(f2));		
		
		JScrollPane scrollPane = new JScrollPane(entTable);
		scrollPane.setBorder(BorderFactory.createEtchedBorder());
		scrollPane.setPreferredSize(new Dimension(DEFAULT_WIDTH, 100));		
		entPane.add(scrollPane);
		
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
				bankTableModel.fireTableDataChanged();
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
						"Ви дійсно бажаєте вилучити \"" + bank + "\"?",
						"Увага",
						JOptionPane.YES_NO_OPTION,
						JOptionPane.QUESTION_MESSAGE,
						null,
						new String [] {"Так", "Ні"}, "Так");
		        if (reply == JOptionPane.YES_OPTION) {
		        	DataStorage.getBankList().remove(bank);
		        	bankTableModel.fireTableDataChanged();
		        }
			}
		});
		
	}
	
}