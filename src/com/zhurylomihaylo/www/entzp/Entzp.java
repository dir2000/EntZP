package com.zhurylomihaylo.www.entzp;

import javax.swing.*;

import java.awt.EventQueue;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;

public class Entzp {
	static private DataStorage dataStorage;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				MainFrame frame = new MainFrame();
				frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
				frame.setTitle("���������� ������ ��������� ���������");
				frame.setVisible(true);

				frame.addWindowListener(new WindowAdapter() {
					public void windowClosing(WindowEvent event) {
						try {
							DataStorage.saveDataStorage();
						} catch (IOException ex) {
							ex.printStackTrace();
							int reply = JOptionPane.showOptionDialog(
									frame,
									"��� ����� �������� ��� ��������� ������� \"" + ex + "\". �� ������ ������ �����?",
									"�����",
									JOptionPane.YES_NO_OPTION,
									JOptionPane.QUESTION_MESSAGE,
									null,
									new String [] {"���", "ͳ"}, "ͳ");
							if (reply != JOptionPane.YES_OPTION)
								return; 
						}
						event.getWindow().setVisible(false);
						System.exit(0);
					}
				});
			}
		});
	}
}
