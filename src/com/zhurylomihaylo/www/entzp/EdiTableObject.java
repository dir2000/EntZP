package com.zhurylomihaylo.www.entzp;

import java.lang.reflect.Field;
import java.math.BigDecimal;

interface EdiTableObject {
	static final int FIELD_NAME = 0;
	static final int FIELD_HEADER = 1;
	static final int FIELD_IS_NUMERIC = 2;
	static final int FIELD_IS_EDITABLE = 3;
}