package com.zhurylomihaylo.www.entzp;

import javax.swing.*;

import java.awt.EventQueue;
import java.util.ArrayList;

public class Entzp {
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				MainFrame frame = new MainFrame();
				frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				frame.setTitle("���������� ������ ��������� ���������");
				frame.setVisible(true);
			}
		});
	}
}
