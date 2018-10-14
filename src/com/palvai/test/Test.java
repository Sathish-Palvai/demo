package com.palvai.test;

public class Test {
	
	public static void main(String args[]) {
		java.math.BigDecimal param = new java.math.BigDecimal("-999.99");
		System.out.println("Param " + param.scale());
		java.math.BigDecimal param2 = new java.math.BigDecimal("-999");
		System.out.println("param2 " + param2.scale());
		java.math.BigDecimal param3 = new java.math.BigDecimal("999.9");
		System.out.println("param3 " + param3.scale());
		java.math.BigDecimal param4 = new java.math.BigDecimal("999.0");
		System.out.println("param4 " + param4.scale());
		java.math.BigDecimal param5 = new java.math.BigDecimal("999.");
		System.out.println("param4 " + param5.scale());
		
	}

}
