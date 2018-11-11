package com.palvai.generic;

public class GenericTest {

	int add(int a, int b) {
		return a + b;
	}

	public static void main(String args[]) {
		GenericTest obj = new GenericTest();
		System.out.println(obj.add(3, 4));
	}

}
