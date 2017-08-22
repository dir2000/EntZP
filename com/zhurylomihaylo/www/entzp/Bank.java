package com.zhurylomihaylo.www.entzp;

import java.math.BigDecimal;
import java.util.UUID;

class Bank {
	private UUID id = UUID.randomUUID();
	private String name;
	private BigDecimal monthlyFee = BigDecimal.ZERO;
	private BigDecimal transactionComission = BigDecimal.ZERO;
	private BigDecimal transactionFee = BigDecimal.ZERO;
	
	static int getFieldsCount() {
		return 5;
	}
	
	Bank(String name){
		if (name == null || name.equals("")) {
			throw new IllegalArgumentException();
		}
		
		this.name = name;
	}
	
}