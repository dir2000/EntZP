package com.zhurylomihaylo.www.entzp;

import java.awt.*;
import java.awt.event.*;
import java.math.*;
import java.text.*;
import java.util.Vector;

import javax.swing.*;
import javax.swing.table.*;
import javax.swing.text.*;

class MainFrame extends JFrame {
	static final int DEFAULT_WIDTH = 700;
	static MainFrame mainFrame;

	EditableCellRendererComponent rendComp;

	private JPanel settingsPane;

	private JPanel banksPane = new JPanel();
	private JButton addBankButton = new JButton("Додати банк");
	private JButton deleteBankButton = new JButton("Вилучити банк");
	private EdiTableModel bankTableModel = new EdiTableModel(Bank.class, DataStorage.getVector(Bank.class));
	private JTable bankTable = new JTable(bankTableModel);

	private JPanel entPane = new JPanel();
	private JButton addEntButton = new JButton("Додати особу");
	private JButton deleteEntButton = new JButton("Вилучити особу");
	private EdiTableModel entTableModel = new EdiTableModel(Ent.class, DataStorage.getVector(Ent.class));
	private JTable entTable = new JTable(entTableModel);
	
	JComboBox bankBox = new JComboBox<>(DataStorage.getVector(Bank.class));

	JButton testButton = new JButton("Test");

	/******************** CONSTRUCTORS ********************/

	MainFrame() {
		mainFrame = this;

		buildGUI();
		setListeners();
	}

	/******************** NON-STATIC METHODS ********************/

	void buildGUI() {
		rendComp = new EditableCellRendererComponent();

		settingsPane = new JPanel();
		settingsPane.setLayout(new BoxLayout(settingsPane, BoxLayout.Y_AXIS));
		add(settingsPane, BorderLayout.NORTH);

		buildGUITablePane(Bank.class, banksPane, "Тарифи банків", bankTableModel, bankTable, addBankButton,
				deleteBankButton);
		settingsPane.add(banksPane);

//		testButton.addActionListener(new ActionListener() {
//			@Override
//			public void actionPerformed(ActionEvent e) {
//				JOptionPane.showMessageDialog(MainFrame.this, bankTable.getSelectedRow());
//			}
//		});
//		settingsPane.add(testButton);

		buildGUIConsts();

		buildGUITablePane(Ent.class, entPane, "Особи", entTableModel, entTable, addEntButton, deleteEntButton);
		add(entPane, BorderLayout.CENTER);

		TableColumn column = entTable.getColumnModel().getColumn(Ent.BANK_FIELS_INDEX);
		column.setCellEditor(new DefaultCellEditor(bankBox));
		
		pack();
	}

	void buildGUITablePane(Class cl, JPanel pane, String title, TableModel tableModel, JTable table, JButton addButton,
			JButton deleteButton) {
		pane.setBorder(BorderFactory.createTitledBorder(title));
		pane.setLayout(new BoxLayout(pane, BoxLayout.Y_AXIS));

		JPanel buttonsPane = new JPanel();
		buttonsPane.setLayout(new FlowLayout(FlowLayout.LEFT));
		buttonsPane.add(addButton);
		buttonsPane.add(deleteButton);
		pane.add(buttonsPane);

		TableColumnModel columnModel = table.getColumnModel();

		for (int i = 0; i < columnModel.getColumnCount(); i++) {
			TableColumn column = columnModel.getColumn(i);
			column.setHeaderValue(EdiTableObject.getFieldHeader(cl, i));
		}
		table.getTableHeader().setReorderingAllowed(false);
		table.setDefaultRenderer(Object.class, rendComp);

		// for(int i : bankTableModel.getHiddenColumns()) {
		// bankTable.getColumnModel().getColumn(i).setMinWidth(0);
		// bankTable.getColumnModel().getColumn(i).setMaxWidth(0);
		// }

		InputMap im = table.getInputMap(JTable.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
		KeyStroke enter = KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0);
		KeyStroke f2 = KeyStroke.getKeyStroke(KeyEvent.VK_F2, 0);
		im.put(enter, im.get(f2));

		JScrollPane scrollPane = new JScrollPane(table);
		scrollPane.setBorder(BorderFactory.createEtchedBorder());
		scrollPane.setPreferredSize(new Dimension(DEFAULT_WIDTH, 100));
		pane.add(scrollPane);
	}

	void buildGUIConsts() {
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

		// https://stackoverflow.com/questions/6089508/how-to-use-bigdecimal-with-jformattedtextfield
		// Then in the lostFocus method, on the line:
		// jFormattedTextField.commitEdit();

		// String number = ftf.getText().replace(",","");
		// BigDecimal bd = new BigDecimal(number);

	}

	void makeFormattedField(JPanel pane, BigDecimal value) {
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

	void setListeners() {
		setAddListener(addBankButton, Bank.class, bankTableModel);
		setDeleteListener(deleteBankButton, Bank.class, bankTableModel, bankTable);

		setAddListener(addEntButton, Ent.class, entTableModel);		
		setDeleteListener(deleteEntButton, Ent.class, entTableModel, entTable);
		// addBankButton.addActionListener(new ActionListener() {
		// @Override
		// public void actionPerformed(ActionEvent e) {
		// DataStorage.getList(Bank.class).add(new Bank("Новий банк"));
		// bankTableModel.fireTableDataChanged();
		// }
		// });

//		deleteBankButton.addActionListener(new ActionListener() {
//			@Override
//			public void actionPerformed(ActionEvent e) {
//				int selectedRow = bankTable.getSelectedRow();
//				if (selectedRow < 0)
//					return;
//
//				EdiTableObject bank = DataStorage.getList(Bank.class).get(selectedRow);
//				int reply = JOptionPane.showOptionDialog(MainFrame.this, "Ви дійсно бажаєте вилучити \"" + bank + "\"?",
//						"Увага", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null,
//						new String[] { "Так", "Ні" }, "Так");
//				if (reply == JOptionPane.YES_OPTION) {
//					DataStorage.getList(Bank.class).remove(bank);
//					bankTableModel.fireTableDataChanged();
//				}
//			}
//		});
	}
	void setAddListener(JButton button, Class cl, EdiTableModel tableModel) {
		ActionListener al = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					DataStorage.getVector(cl).add((EdiTableObject) cl.newInstance());
					tableModel.fireTableDataChanged();
				} catch (Exception ex) {
					JOptionPane.showMessageDialog(MainFrame.this, ex);
				}
			}
		};
		button.addActionListener(al);
	}
	
	void setDeleteListener(JButton button, Class cl, EdiTableModel tableModel, JTable table) {
		button.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int selectedRow = table.getSelectedRow();
				if (selectedRow < 0)
					return;
				
				
				EdiTableObject etoToDelete = DataStorage.getVector(cl).get(selectedRow);
				
				if (integrityCheckFailed(cl, etoToDelete))
					return;
					
				int reply = JOptionPane.showOptionDialog(MainFrame.this, "Ви дійсно бажаєте вилучити \"" + etoToDelete + "\"?",
						"Увага", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null,
						new String[] { "Так", "Ні" }, "Так");
				if (reply == JOptionPane.YES_OPTION) {
					DataStorage.getVector(cl).remove(etoToDelete);
					tableModel.fireTableDataChanged();
				}				
			}
		});
	}
	
	boolean integrityCheckFailed(Class cl, EdiTableObject etoToDelete){
		if (!cl.equals(Bank.class))
			return false;
		
		Vector<EdiTableObject> list = DataStorage.getVector(Ent.class);
		for(EdiTableObject listEto : list) {
			if (etoToDelete.equals(listEto.getFieldValue(Ent.class, Ent.BANK_FIELS_INDEX))) {
				JOptionPane.showMessageDialog(MainFrame.this, "Вибачте, але цей банк вже вибраний в списку осіб.");
				return true;
			}
		}
		
		return false;
	}



}