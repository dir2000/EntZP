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
	private static final long serialVersionUID = 1L;
	
	static private DataStorage thisInstance;
	static private final String FILE_NAME;
	
	private ArrayList<Bank> bankList;
	private BigDecimal minSalary = BigDecimal.ZERO;	
	private BigDecimal esvRate = BigDecimal.ZERO;
	private BigDecimal additionalRate = BigDecimal.ZERO;
	private BigDecimal taxRate = BigDecimal.ZERO;

	static {
		FILE_NAME = "data.ser";
		File file = new File(FILE_NAME);
		if  (file.exists())
			readDataStorage(file);
		else
			thisInstance = new DataStorage();
	}
	
	
	//CONSTRUCTORS
	
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
		
		esvRate = BigDecimal.valueOf(22.0);
		minSalary = BigDecimal.valueOf(3200.0);
		additionalRate = BigDecimal.valueOf(5.0);
		taxRate = BigDecimal.valueOf(5.0);
	}
	
	
	//APPLIED METHODS
	
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
		} catch (java.io.InvalidClassException ex){
			JOptionPane.showMessageDialog(null, "Помилка читання файлу " + file + ": застірылий формат файлу.");
		} 
		catch (Exception ex){
			JOptionPane.showMessageDialog(null, "Помилка читання файлу " + file + ": \"" + ex + "\"");
			JOptionPane.showMessageDialog(null, ex.getClass());
			thisInstance = new DataStorage();
		}
	}

	
	//GETTERS AND SETTERS
	
	static BigDecimal getEsvRate() {
		return thisInstance.esvRate;
	}

	static void setEsvRate(BigDecimal esvRate) {
		thisInstance.esvRate = esvRate;
	}

	static BigDecimal getMinSalary() {
		return thisInstance.minSalary;
	}

	static void setMinSalary(BigDecimal minSalary) {
		thisInstance.minSalary = minSalary;
	}

	static BigDecimal getAdditionalRate() {
		return thisInstance.additionalRate;
	}

	static void setAdditionalRate(BigDecimal additionalRate) {
		thisInstance.additionalRate = additionalRate;
	}

	static BigDecimal getTaxRate() {
		return thisInstance.taxRate;
	}

	static void setTaxRate(BigDecimal taxRate) {
		thisInstance.taxRate = taxRate;
	}
}