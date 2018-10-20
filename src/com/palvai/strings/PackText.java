package com.palvai.strings;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PackText {

	public static ArrayList<String> fullJustify(List<String> A, int B) {

		int maxLen = B;
		int arraySize = A.size();
		ArrayList<String> res = new ArrayList<String>();
		StringBuilder temp = new StringBuilder();
		int currLen = 0;

		for (int i = 0; i < arraySize; i++) {

			currLen = temp.length() + A.get(i).length();
			if (currLen <= maxLen) {
				temp.append(A.get(i));
				temp.append(" ");
			} else {
				int toAddLen = temp.toString().trim().length();
				int spacesToInsert = 0;
				if (toAddLen < B) {
					spacesToInsert = B - toAddLen;
					int index = temp.toString().indexOf(" ");
					for (int j = 0; j < spacesToInsert; j++) {
						temp = temp.insert(index, ' ');
					}

				}

				res.add(temp.toString().trim());
				temp = new StringBuilder(A.get(i));
				temp.append(" ");
			}

		}

		if (temp.length() != 0) {
			res.add(temp.toString().trim());
		}

		return res;
	}

	public static void main(String args[]) {
		ArrayList<String> res = PackText
		    .fullJustify((List<String>) Arrays.asList("This", "is", "an", "example", "of", "text", "justification."), 16);
		System.out.println(res.get(2).length());
	}

}
