package com.zhurylomihaylo.www.entzp;

import java.awt.Color;
import java.awt.Component;
import java.math.BigDecimal;

import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableModel;

class EditableCellRendererComponent extends DefaultTableCellRenderer {

	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
			int row, int column) {
		// Cells are by default rendered as a JLabel.
		JLabel l = (JLabel) super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

		TableModel tableModel = table.getModel();
		if (tableModel.isCellEditable(row, column)) {
			l.setBackground(null);
		} else l.setBackground(Color.LIGHT_GRAY);
		
		if (value instanceof BigDecimal){
			l.setHorizontalAlignment(JLabel.RIGHT);
		} else l.setHorizontalAlignment(JLabel.LEFT);

//		if (hasFocus)
//			setBorder(UIManager.getBorder("Table.focusCellHighlightBorder"));
//		else
//			setBorder(null);
		
		// Return the JLabel which renders the cell.
		return l;
	}
}