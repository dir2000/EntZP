package com.zhurylomihaylo.www.entzp;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;

import javax.swing.JOptionPane;

class DataStorage implements Serializable  {
	static private DataStorage thisInstance;
	static private final String FILE_NAME;
	
	private ArrayList<Bank> bankList;

	static {
		FILE_NAME = "data.ser";
		File file = new File(FILE_NAME);
		if  (file.exists())
			readDataStorage(file);
		else
			thisInstance = new DataStorage();
	}
	
	private DataStorage() {
		bankList = new ArrayList<>();

		Bank bank = new Bank("Аваль");
		bank.setMonthlyFee(BigDecimal.valueOf(50D));
		bank.setTransactionComission(BigDecimal.valueOf(0.0085));
		bank.setTransactionFee(BigDecimal.valueOf(5D));
		bankList.add(bank);

		Bank bank2 = new Bank("Приватбанк");
		bank2.setMonthlyFee(BigDecimal.valueOf(30D));
		bank2.setTransactionComission(BigDecimal.valueOf(0.0085));
		bank2.setTransactionFee(BigDecimal.valueOf(5D));
		bankList.add(bank2);
	}
	
	static ArrayList<Bank> getBankList(){
		return thisInstance.bankList;
	}	
	
	static void saveDataStorage() throws IOException{
		try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_NAME))){
			oos.writeObject(thisInstance);
			oos.flush();
		}
//		catch (Exception ex) {
//			ex.printStackTrace();
//		}
	}

	private static void readDataStorage(File file) {
		try (ObjectInputStream oin = new ObjectInputStream(new FileInputStream(file)))
		{
			thisInstance = (DataStorage) oin.readObject();			
		} catch (Exception ex){
			JOptionPane.showMessageDialog(null, "Помилка читання файлу " + file + ": \"" + ex + "\"");
			thisInstance = new DataStorage();
		}
	}

}