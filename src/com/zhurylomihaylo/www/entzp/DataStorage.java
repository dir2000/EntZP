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
import java.util.HashMap;
import java.util.List;
import java.util.Vector;

import javax.swing.JOptionPane;

class DataStorage implements Serializable  {
	static private final long serialVersionUID = 1L;
	
	static private DataStorage thisInstance;
	static private final String FILE_NAME;
	
	private Vector<EdiTableObject> bankList;
	private Vector<EdiTableObject> entList;
	private HashMap<Class, Vector<EdiTableObject>> allLists;
	
	private BigDecimal minSalary = BigDecimal.ZERO;	
	private BigDecimal esvPercent = BigDecimal.ZERO;
	private BigDecimal taxPercent = BigDecimal.ZERO;	
	private BigDecimal additionalPercent = BigDecimal.ZERO;

	static {
		FILE_NAME = "data.ser";
		File file = new File(FILE_NAME);
		if  (file.exists())
			readDataStorage(file);
		else
			thisInstance = new DataStorage();
	}
	
	/******************** CONSTRUCTORS *********************/
	
	private DataStorage() {
		esvPercent = BigDecimal.valueOf(22.0);
		minSalary = BigDecimal.valueOf(3200.0);
		additionalPercent = BigDecimal.valueOf(5.0);
		taxPercent = BigDecimal.valueOf(5.0);

		allLists = new HashMap<>();
		
		bankList = new Vector<>();
		bankList.add(new Bank("Аваль", 50.0, 0.85, 5.0));
		bankList.add(new Bank("Приватбанк", 30.0, 0.85, 5.0));
		allLists.put(Bank.class, bankList);
		
		entList = new Vector<>();
		entList.add(new Ent("Іванов Петро Сидорович"));
		allLists.put(Ent.class, entList);
	}
	
	/******************** STATIC METHODS ********************/
		
	static void saveDataStorage() throws IOException{
		try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_NAME))){
			oos.writeObject(thisInstance);
			oos.flush();
		}
		catch (Exception ex) {
			JOptionPane.showMessageDialog(null, ex.getMessage());
		}
	}

	private static void readDataStorage(File file) {
		try (ObjectInputStream oin = new ObjectInputStream(new FileInputStream(file)))
		{
			thisInstance = (DataStorage) oin.readObject();			
		} catch (java.io.InvalidClassException ex){
			JOptionPane.showMessageDialog(null, "Помилка читання файлу " + file + ": змінився формат файлу.");
			thisInstance = new DataStorage();
		} 
		catch (Exception ex){
			JOptionPane.showMessageDialog(null, "Помилка читання файлу " + file + ": \"" + ex + "\"");
			JOptionPane.showMessageDialog(null, ex.getClass());
			thisInstance = new DataStorage();
		}
	}
	
	/******************** GETTERS AND SETTERS ********************/
	
//	static ArrayList<EdiTableObject> getBankList(){
//		return thisInstance.bankList;
//	}	
//
//	static ArrayList<EdiTableObject> getEntList(){
//		return thisInstance.entList;
//	}	

	static Vector<EdiTableObject> getVector(Class cl){
		return thisInstance.allLists.get(cl);
	}	
	
	static BigDecimal getEsvPercent() {
		return thisInstance.esvPercent;
	}

	static void setEsvPercent(BigDecimal esvPercent) {
		thisInstance.esvPercent = esvPercent;
	}

	static BigDecimal getMinSalary() {
		return thisInstance.minSalary;
	}

	static void setMinSalary(BigDecimal minSalary) {
		thisInstance.minSalary = minSalary;
	}

	static BigDecimal getAdditionalPercent() {
		return thisInstance.additionalPercent;
	}

	static void setAdditionalPercent(BigDecimal additionalPercent) {
		thisInstance.additionalPercent = additionalPercent;
	}

	static BigDecimal getTaxPercent() {
		return thisInstance.taxPercent;
	}

	static void setTaxPercent(BigDecimal taxPercent) {
		thisInstance.taxPercent = taxPercent;
	}
}