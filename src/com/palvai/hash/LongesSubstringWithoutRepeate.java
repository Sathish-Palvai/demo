package com.palvai.hash;

import java.util.HashSet;

public class LongesSubstringWithoutRepeate {

	// private static String input = "4WiFFJjKJd";
	// private static String input = "dabdc";
	// private static String input = "abcabcbb";
	private static String input = "Wnb9z9dMc7E8v1RTUaZPoDNIAXRlzkqLaa97KMWLzbitaCkRpiE4J4hJWhRcGnC8H6mwasgDfZ76VKdXhvEYmYrZY4Cfmf4HoSlchYWFEb1xllGKyEEmZOLPh1V6RuM7Mxd7xK72aNrWS4MEaUmgEn7L4rW3o14Nq9l2EN4HH6uJWljI8a5irvuODHY7A7ku4PJY2anSWnfJJE1w8p12Ks3oZRxAF3atqGBlzVQ0gltOwYmeynttUmQ4QBDLDrS4zn4VRZLosOITo4JlIqPD6t4NjhHThOjJxpMp9fICkrgJeGiDAwsb8a3I7Txz5BBKV9bEfMsKNhCuY3W0ZHqY0MhBfz1CbYCzwZZdM4p65ppP9s5QJcfjadmMMi26JKz0TVVwvNA8LP5Vi1QsxId4SI19jfcUH97wmZu0pbw1zFtyJ8GAp5yjjQTzFIboC1iRzklnOJzJld9TMaxqvBNBJKIyDjWrdfLOY8FGMOcPhfJ97Dph35zfxYyUf4DIqFi94lm9J0skYqGz9JT0kiAABQZDazZcNi80dSSdveSl6h3dJjHmlK8qHIlDsqFd5FMhlEirax8WA0v3NDPT8vPhwKpxcnVeu14Gcxr3h1wAXXV0y7Xy9qqB2NQ5HQLJ7cyXAckEYHsLCPSy28xcdNJatx1KLWohOQado4WywJbGvsFR17rKmvOPABweXnFD3odrbSMD4Na4nuBBswvMmFRTUOcf7jZi4z5JnJqXz6hitaPnaEtjoSEBq82a52nvqYy7hhldBoxen2et2OMadVEHeTYLL7GLsIhTP6UizHIuzcJMljo4lFgW5AyrfUlIBPAlhwaSiJtTvcbVZynDSM6RO1PqFKWKg2MHIgNhjuzENg2oFCfW7z5KJvEL9qWqKzZNc0o3BMRjS04NCHFvhtsteQoQRgz84XZBHBJRdekCdcVVXu9c01gYRAz7oIAxN3zKZb64EFKssfQ4HW971jv3H7x5E9dAszA0HrKTONyZDGYtHWt4QLhNsIs8mo4AIN7ecFKewyvGECAnaJpDn1MTTS4yTgZnm6N6qnmfjVt6ZU51F9BxH0jVG0kovTGSjTUkmb1mRTLQE5mTlVHcEz3yBOh4WiFFJjKJdi1HBIBaDL4r45HzaBvmYJPlWIomkqKEmQ4rLAbYG7C5rFfpMu8rHvjU7hP0JVvteGtaGn7mqeKsn7CgrJX1tb8t0ldaS3iUy8SEKAo5IZHNKOfEaij3nI4oRVzeVOZsH91pMsA4jRYgEohubPW8ciXwVrFi1qEWjvB8gfalyP60n1fHyjsiLW0T5uY1JzQWHKCbLVh7QFoJFAEV0L516XmzIo556yRH1vhPnceOCjebqgsmO78AQ8Ir2d4pHFFHAGB9lESn3OtJye1Lcyq9D6X93UakA3JKVKEt6JZDLVBMp4msOefkPKSw59Uix9d9kOQm8WCepJTangdNSOKaxblZDNJ5eHvEroYacBhd9UdafEitdF3nfStF7AhkSfQVC61YWWkKTNdx96OoJGTnxuqt4oFZNFtO7aMuN3IJAkw3m3kgZFRGyd3D3wweagNL9XlYtvZwejbjpkDOZz33C0jbEWaMEaUPw6BG49XqyQoUwtriguO0yvWyaJqD4ye3o0E46huKYAsdKAq6MLWMxF6tfyPVaoqOGd0eOBHbAF89XXmDd4AIkoFPXkAOW8hln5nXnIWP6RBbfEkPPbxoToMbV";

	/**
	 * @param A
	 * @return
	 */
	public int lengthOfLongestSubstring(String A) {
		String repeatedString = "";
		int pointer = 0;
		for (int i = 1; i < input.length() + 1; i++) {
			// System.out.println("i = " + i);
			String subStr = input.substring(pointer, i);
			// System.out.println(subStr + " , " + (i - pointer - 1));
			int index = subStr.indexOf(subStr.charAt(i - pointer - 1));
			// System.out.println("Index = " + index);
			if (index >= 0 && index < i - pointer - 1) {
				// System.out.println("Repeated Character found " + i);
				i = i - (i - pointer - 1);
				pointer = i;
			} else {
				if (subStr.length() > repeatedString.length())
					repeatedString = subStr;
				// pointer = 0;
			}
			// System.out.println(repeatedString);

		}

		// System.out.println(sortedMap);
		return repeatedString.length();
	}

	public int lengthOfLongestSubstring2(String A) {
		HashSet<Character> hashSet = new HashSet<Character>();
		int maxCount = 0;
		int start = 0;
		for (char c : A.toCharArray()) {
			while (hashSet.contains(c)) {
				hashSet.remove(A.charAt(start));
				start++;
			}
			hashSet.add(c);
			maxCount = Math.max(maxCount, hashSet.size());
		}

		return maxCount;
	}

	public static void main(String args[]) {
		LongesSubstringWithoutRepeate obj = new LongesSubstringWithoutRepeate();

		System.out.println(obj.lengthOfLongestSubstring(input));
	}

}
