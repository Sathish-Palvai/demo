package com.palvai.compression;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LZW {

	/**
	 * Compress a string following LZW algorithm This code assume the ASCII 256
	 * character set.
	 * 
	 * Longer the text we want to compress the faster LZW becomes
	 * Does not need any pre-process phase.
	 * 
	 * Not memory efficient as we have to store several key and
	 * value pairs in the hashtable/trie.
	 * 
	 * TST is the best option to store key value 
	 * 
	 * @param input
	 * @return
	 */
	public List<Integer> compress(String input) {

	  int dictionarySize = 256;
		Map<String, Integer> dictionary = new HashMap<>();
		List<Integer> result = new ArrayList<>();

		// Map ASCII character string with numeric values
		for (int i = 0; i < dictionarySize; i++) {
			dictionary.put("" + (char) i, i);
		}

		String str = "";

		// Loop over the characters and update the dictionary with new key value pairs
		// for new patterns
		for (char c : input.toCharArray()) {
			// System.out.println("str = " + str);
			StringBuilder strBuilder = new StringBuilder(str);
			strBuilder.append(c);
			String key = strBuilder.toString();
			if (dictionary.containsKey(key)) {
				str = key;
				//System.out.println(result.toString());

			} else {
				//System.out.println("str = " + str);
				//System.out.println("Key = " + key);

				result.add(dictionary.get(str));
				dictionary.put(key, dictionarySize++);
				str = "" + c;
			}
		}

		if (!str.equals("")) {

			result.add(dictionary.get(str));

		}

		return result;
	}

	/**
	 * 
	 * @param compressedStrList
	 * @return
	 */
	public String decompress(List<Integer> compressedStrList) {

		int dictionarySize = 256;

		Map<Integer, String> dictionary = new HashMap<>();
	  // Map integers to ASCII characters
		for (int i = 0; i < dictionarySize; i++) {
			dictionary.put(i, "" + (char) i);
		}

		String tempString = "" + (char) (int) compressedStrList.remove(0);
		System.out.println("tempString " + tempString);
		StringBuffer stringBuffer = new StringBuffer(tempString);

		for (int key : compressedStrList) {
			String entry;
			if (dictionary.containsKey(key)) {
				entry = dictionary.get(key);
			} else if (key == dictionarySize) {
				entry = tempString + tempString.charAt(0);
			} else {
				throw new IllegalArgumentException("Bad com"
						+ "pressed key");
			}

			stringBuffer.append(entry);
			dictionary.put(dictionarySize++, tempString + entry.charAt(0));
			tempString = entry;
		}

		return stringBuffer.toString();
	}

	public static void main(String args[]) {

		LZW obj = new LZW();
		//List<Integer> compressedString = obj.compress("CARRARCARCAR");
		List<Integer> compressedString = obj.compress("ABABABA");
		System.out.println(obj.decompress(compressedString));

	}
}
