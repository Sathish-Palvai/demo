package com.palvai.hash;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class SubStringConcatenation {

	public ArrayList<Integer> findSubstring(String A, final List<String> B) {
		int wordSize = B.get(0).length();
		int wordCount = B.size();
		int totalCharacters = wordSize * wordCount;
		ArrayList<Integer> resultIndices = new ArrayList<Integer>();

		if (totalCharacters > A.length())
			return resultIndices;

		// Store the words and frequencies in a hash table for L array
		HashMap<String, Integer> words = new HashMap<String, Integer>();
		for (String s : B) {
			if (words.containsKey(s))
				words.put(s, words.get(s) + 1);
			else
				words.put(s, 1);
		}
		
		System.out.println("Words " + words);
		int finalItrVal = A.length() - wordCount * wordSize;
    System.out.println("finalItrVal " + finalItrVal);
		// find the concatenations
		for (int i = 0; i <= A.length() - wordCount * wordSize; i++) {
			// check if it is a concatenation
			HashMap<String, Integer> target = new HashMap<String, Integer>(words);
			for (int j = i; j <= A.length() - wordSize && !target.isEmpty(); j += wordSize) {
				String sub = A.substring(j, j + wordSize);
				System.out.println("SUB " + sub);
				System.out.println("j " + j);
				if (!target.containsKey(sub)) {
					System.out.println("Breaking Loop");
					break;
				}
				else if (target.get(sub) > 1) {
					// reduce the frequency
					target.put(sub, target.get(sub) - 1);
				} else {
					// remove the word if only one left
					target.remove(sub);
				}
			}
			System.out.println("target " + target);
			
			if (target.isEmpty()) {
				System.out.println("Adding " + i);
				resultIndices.add(i);
			}
		}
		return resultIndices;

	}

	public static void main(String args[]) {
		String A = "barfoothefoobarman";
		List<String> B = new ArrayList<String>(Arrays.asList("foo", "bar"));
		SubStringConcatenation obj = new SubStringConcatenation();
		obj.findSubstring(A, B);
	}

}
