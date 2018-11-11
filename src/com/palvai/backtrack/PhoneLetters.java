package com.palvai.backtrack;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PhoneLetters {

	String[] map = new String[] { "0", "1", "abc", "def", "ghi", "jkl", "mno", "pqrs", "tuv", "wxyz" };

	public ArrayList<String> letterCombinations(String A) {
		ArrayList<String> ans = new ArrayList<String>();

		back(A, ans, "", 0);
		return ans;
	}

	public void back(String A, ArrayList<String> ans, String str, int index) {
		if (index >= A.length()) {
			ans.add(str);
			return;
		}
		int idx = (int) (A.charAt(index) - '0');
		String temp = map[idx];
		for (int j = 0; j < temp.length(); j++) {
			back(A, ans, str + temp.charAt(j), index + 1);
		}
	}

	public static void main(String args[]) {
		PhoneLetters obj = new PhoneLetters();
		System.out.println(obj.letterCombinations2("23"));
	}

	public ArrayList<String> letterCombinations2(String a) {

		ArrayList<String> resultList = new ArrayList<String>();
		if (a == null || a.equals(""))
			return resultList;
		Map<String, List<String>> keypad = new HashMap<String, List<String>>();

		keypad.put("0", Arrays.asList("0"));
		keypad.put("1", Arrays.asList("1"));
		keypad.put("2", Arrays.asList("a", "b", "c"));
		keypad.put("3", Arrays.asList("d", "e", "f"));
		keypad.put("4", Arrays.asList("g", "h", "i"));
		keypad.put("5", Arrays.asList("j", "k", "l"));
		keypad.put("6", Arrays.asList("m", "n", "o"));
		keypad.put("7", Arrays.asList("p", "q", "r", "s"));
		keypad.put("8", Arrays.asList("t", "u", "v"));
		keypad.put("9", Arrays.asList("w", "x", "y", "z"));

		getAllPossible(a, keypad, "", resultList);
		return resultList;
	}

	public ArrayList<String> getAllPossible(String a, Map<String, List<String>> keypad, String result,
	    ArrayList<String> resultList) {
		
		if (a.length() == 0) {
			resultList.add(result);
		} else {
			for (String k : keypad.get("" + a.charAt(0))) {
				String newStr = result + k;
				String st = a.substring(1);
				getAllPossible(st, keypad, newStr, resultList);
			}
		}

		return resultList;
	}

}
