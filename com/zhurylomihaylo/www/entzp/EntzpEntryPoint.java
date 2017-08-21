package com.zhurylomihaylo.www.entzp;

import javax.swing.*;
import java.util.UUID;

public class EntzpEntryPoint {

	public static void main(String[] args) {
		MainFrame frame = new MainFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setTitle("Розрахунок оплати приватним підприємцям");
		frame.setVisible(true);
	}
}

class MainFrame extends JFrame{
	static final int WIDTH = 500;
	static final int HEIGHT = 300;
	MainFrame(){
		setSize(WIDTH, HEIGHT);
	}
}

class Bank {
	UUID id;
	String name;
	
	Bank(String name){
		if (name == null || name.equals("")) {
			throw new IllegalArgumentException();
		}
		
		id = UUID.randomUUID();
		this.name = name;
	}
}
