package com.zhurylomihaylo.www.entzp;

import javax.swing.*;

import java.util.ArrayList;

public class Entzp {
	static ArrayList<Bank> bankList = new ArrayList<>();
	
	public static void main(String[] args) {
		MainFrame frame = new MainFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setTitle("Розрахунок оплати приватним підприємцям");
		frame.setVisible(true);
	}
}
