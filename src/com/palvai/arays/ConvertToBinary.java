package com.palvai.arays;

import java.util.Scanner;

public class ConvertToBinary {

	public static void main(String[] args) {
		int n = 4, a;
		String x = "";
		Scanner s = new Scanner(System.in);
		System.out.print("Enter any decimal number:");
		n = s.nextInt();
		while (n > 0) {
			a = n % 2;
			x = a + "" + x;
			n = n / 2;
		}
		System.out.println("Binary number:" + x);
		Integer i = new Integer(4);
    String binary = Integer.toBinaryString(i);
    System.out.println(binary);
    s.close();

	}

}
