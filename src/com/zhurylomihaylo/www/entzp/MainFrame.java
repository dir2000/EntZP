package com.zhurylomihaylo.www.entzp;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.math.BigDecimal;
import java.text.DecimalFormat;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.InputMap;
import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.KeyStroke;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import javax.swing.text.DefaultFormatter;
import javax.swing.text.DefaultFormatterFactory;
import javax.swing.text.NumberFormatter;

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
		//JOptionPane.showMessageDialog(this, settingsPane.getLayout().getClass());
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
		for (int i = 0; i < columnModel.getColumnCount(); i++) {
			columnModel.getColumn(i).setHeaderValue(Bank.getFieldHeader(i));
		}
		for(int i = 0; i < tableModel.getHiddenColumns().length; i++) {
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
		esvPane.add(makeFormattedField(DataStorage.getMinSalary()));
		
		esvPane.add(new JLabel("Ставка ЄСВ, %"));
		esvPane.add(makeFormattedField(DataStorage.getEsvRate()));
		
		settingsPane.add(esvPane);
		
		//https://stackoverflow.com/questions/6089508/how-to-use-bigdecimal-with-jformattedtextfield
		//Then in the lostFocus method, on the line:
		//jFormattedTextField.commitEdit();
		
		//String number = ftf.getText().replace(",","");
		//BigDecimal bd = new BigDecimal(number);

	}
	
	JFormattedTextField makeFormattedField(BigDecimal value){
		JFormattedTextField field = new JFormattedTextField(value);
		
		DefaultFormatter fmt = new NumberFormatter(new DecimalFormat("################0"));
	    fmt.setValueClass(field.getValue().getClass());
	    DefaultFormatterFactory fmtFactory = new DefaultFormatterFactory(fmt, fmt, fmt);
	    field.setFormatterFactory(fmtFactory);
	    
	    //field.setPreferredSize(new Dimension(50, (int) field.getSize().getHeight()));

	   // BigDecimal bigValue = (BigDecimal) f.getValue();		
		
		return field;
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