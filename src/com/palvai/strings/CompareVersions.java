package com.palvai.strings;

import java.math.BigInteger;
import java.util.StringTokenizer;

public class CompareVersions {

	public int compareVersion(String A, String B) {
		String[] arr1 = A.split("\\.");
		String[] arr2 = B.split("\\.");

		int i = 0;

		while (i < arr1.length) {

			if (Long.parseLong(arr2[i]) > Long.parseLong(arr1[i]))
				return -1;

			if (Long.parseLong(arr1[i]) > Long.parseLong(arr2[i]))
				return 1;

			i += 1;
			if (arr1.length == i && arr2.length != i)
				return -1;
			else if (arr2.length == i && arr1.length != i)
				return 1;
		}

		return 0;
	}

	public int compareVersion2(String S1, String S2) {
		StringTokenizer v1St = new StringTokenizer(S1, ".");
		StringTokenizer v2St = new StringTokenizer(S2, ".");
		int v1Count = v1St.countTokens();
		int v2Count = v2St.countTokens();
		if (v1Count < v2Count) {
			for (int i = 0; i < v1Count; i++) {
				BigInteger v1 = new BigInteger(v1St.nextToken());
				BigInteger v2 = new BigInteger(v2St.nextToken());
				if (!v1.equals(v2)) {
					return v1.compareTo(v2);
				}
			}
			while (v2St.hasMoreTokens()) {
				if (!new BigInteger(v2St.nextToken()).equals(BigInteger.ZERO))
					return -1;
			}
		} else if (v2Count < v1Count) {
			for (int i = 0; i < v2Count; i++) {
				BigInteger v1 = new BigInteger(v1St.nextToken());
				BigInteger v2 = new BigInteger(v2St.nextToken());
				if (!v1.equals(v2)) {
					return v1.compareTo(v2);
				}
			}
			while (v1St.hasMoreTokens()) {
				if (!new BigInteger(v1St.nextToken()).equals(BigInteger.ZERO))
					return 1;
			}
		} else {
			for (int i = 0; i < v1Count; i++) {
				BigInteger v1 = new BigInteger(v1St.nextToken());
				BigInteger v2 = new BigInteger(v2St.nextToken());
				if (!v1.equals(v2))
					return v1.compareTo(v2);
			}
		}
		return 0;
	}

	public static void main(String args[]) {
		CompareVersions obj = new CompareVersions();
		System.out.println(obj.compareVersion("13.0", "13.0.8"));
	}

}
