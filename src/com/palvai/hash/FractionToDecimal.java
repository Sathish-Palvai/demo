package com.palvai.hash;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class FractionToDecimal {

	public String fractionToDecimal(int A, int B) {

		String val = new String("" + (double) (Math.abs((double) A / (double) B)));
		int startIndex = val.indexOf(".");
		int endIndex = val.length();
		HashMap<Character, Integer> hashMap = new HashMap<Character, Integer>();
		if (startIndex != -1) {
			for (int i = startIndex + 1; i < endIndex; i++) {
				if (hashMap.containsKey(val.charAt(i))) {
					hashMap.put(val.charAt(i), hashMap.get(val.charAt(i)) + 1);
				} else {
					hashMap.put(val.charAt(i), 1);
				}
			}

			// System.out.println("Size " + hashMap.size());
			// System.out.println(hashMap);

			if (hashMap.size() == 1) {
				Character repeatedChar = val.charAt(startIndex + 1);
				if (hashMap.size() == 1 && hashMap.get(repeatedChar) > 1) {
					val = val.substring(0, startIndex + 1) + "(" + repeatedChar + ")";
				} else {
					Set<Character> set = hashMap.keySet();
					for (Character c : set) {
						if (c == '0') {
							val = val.substring(0, startIndex);
						}
					}
				}
			} else {
				if (val.indexOf("E") != -1) {
					val = val.substring(0, val.indexOf("E"));
					System.out.println("Val = " + val);
					String[] res = val.split("\\.");

					val = res[0] + res[1];
				}
				if (A < 0 && B > 0) {
					return "-" + val;
				}
				return val;
			}
		}

		if (A < 0 && B > 0) {
			return "-" + val;
		}
		return val;
	}

	public static void main(String args[]) {
		FractionToDecimal obj = new FractionToDecimal();
		System.out.println(obj.fractionToDecimal(-2147483648, -1));
	}

	public String fractionToDecimal2(int numerator, int denominator) {
		long a = numerator, b = denominator;
		if (a % b == 0)
			return String.valueOf(a / b);
		Map<Long, Integer> map = new HashMap<>();
		StringBuilder res = new StringBuilder();
		if ((a > 0 && b < 0) || (a < 0 && b > 0))
			res.append("-");
		a = Math.abs(a);
		b = Math.abs(b);
		res.append(a / b + ".");
		a = (a % b) * 10;
		while (!map.containsKey(a)) {
			map.put(a, res.length());
			res.append(String.valueOf(a / b));
			a = (a % b) * 10;
			if (a == 0)
				return res.toString();
		}
		return res.insert(map.get(a), "(").append(")").toString();
	}

	public String fractionToDecimal3(int numerator, int denominator) {

		if (numerator == Integer.MIN_VALUE && denominator == -1)
			return "2147483648";
		int dummy = numerator / denominator, lef = numerator % denominator;
		String str = dummy + "";
		if (dummy == 0) {
			if ((numerator > 0 && denominator < 0) || (numerator < 0 && denominator > 0))
				str = "-" + str; // 0 does not have a negative mark in front of it, so we have to add that.
		}
		if (lef == 0)
			return str; // this indicates the result is an integer.

		StringBuilder sb = new StringBuilder();
		sb.append(str);
		sb.append(".");
		long left = lef < 0 ? -lef : lef;
		long denomin = denominator < 0 ? -denominator : denominator;
		if (denominator == Integer.MIN_VALUE) {
			denomin = Integer.MAX_VALUE;
			denomin++; // without this, when the denominator is -214748648, it will cause a overflow.
		}
		long slow = mod(left, denomin), fast = mod(mod(left, denomin), denomin);
		if (slow == 0) { // no need to start the loop.
			left = extend(left, denomin, sb);
			return sb.toString();
		}

		while (slow != fast) {
			if (fast == 0) { // this indicates there are no cycles.
				while (left != 0)
					left = extend(left, denomin, sb);
				return sb.toString();
			}
			slow = mod(slow, denomin);
			fast = mod(mod(fast, denomin), denomin);
		}

		slow = left;
		while (slow != fast) {
			slow = mod(slow, denomin);
			fast = mod(fast, denomin);
		}
		while (left != slow)
			left = extend(left, denomin, sb);
		sb.append("("); // the start of the cycle.
		left = extend(left, denomin, sb);
		while (left != slow)
			left = extend(left, denomin, sb);
		sb.append(")");
		return sb.toString();
	}

	private long mod(long left, long denominator) {
		left = left * 10;
		return left % denominator;
	}

	private long extend(long left, long denominator, StringBuilder sb) {
		left = left * 10;
		sb.append(left / denominator);
		return left % denominator;
	}

}
