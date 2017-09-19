package com.zhurylomihaylo.www.entzp;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.InputMap;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.KeyStroke;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;

class MainFrame extends JFrame{
	static final int DEFAULT_WIDTH = 700;
	static final int DEFAULT_HEIGHT = 300;
	
	
	private JButton addBankButton;
	private JButton deleteBankButton;
	private JTable bankTable;
	private BankTableModel tableModel;

	
	MainFrame(){
		buildGUI();
		defineListeners();
	}
	
	void buildGUI(){
		setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);
		
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
		
		tableModel = new BankTableModel(Entzp.getDataStorage().getBankList());
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
		
		banksPane.add(new JScrollPane(bankTable));
		
		add(banksPane, BorderLayout.NORTH);
	}
	
	void defineListeners(){
		addBankButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Entzp.getDataStorage().getBankList().add(new Bank("Новий банк"));
				tableModel.fireTableDataChanged();
			}
		});
		
		deleteBankButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int selectedRow = bankTable.getSelectedRow();
				if (selectedRow < 0)
					return;
				
				Bank bank = Entzp.getDataStorage().getBankList().get(selectedRow);
				int reply = JOptionPane.showOptionDialog(
						MainFrame.this,
						"Ви дійсно бажаєте вилучити \"" + bank.getName() + "\"?",
						"Увага",
						JOptionPane.YES_NO_OPTION,
						JOptionPane.QUESTION_MESSAGE,
						null,
						new String [] {"Так", "Ні"}, "Так");
		        if (reply == JOptionPane.YES_OPTION) {
		        	Entzp.getDataStorage().getBankList().remove(bank);
		        	tableModel.fireTableDataChanged();
		        }
			}
		});
		
	}
}