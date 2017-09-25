package com.zhurylomihaylo.www.entzp;

class Entrepreneur{
	private static Object[][] columnFieldsInfo;
	private String name;
	
	static {
		//1. Field
		//2. Column header
		//3. Is numeric
		columnFieldsInfo = new Object[1][];
		//columnFieldsInfo[0] = new Object[]{"id", "ID", false};
		columnFieldsInfo[1] = new Object[]{"name", "������������", false};
//		columnFieldsInfo[2] = new Object[]{"monthlyFee", "�������� �����", true};
//		columnFieldsInfo[3] = new Object[]{"transactionComission", "³������ �� ������ ������, %", true};
//		columnFieldsInfo[4] = new Object[]{"transactionFee", "����� �� ������ ������, ���.", true};
	}
	
	Object getFieldValue(int fieldIndex) {
		try {
			return getClass().getDeclaredField((String) columnFieldsInfo[fieldIndex][0]).get(this);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	
}