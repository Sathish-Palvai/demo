package com.palvai.tries;

import java.util.ArrayList;
import java.util.List;

public class Trie {

	private Node root;

	public Trie() {
		this.root = new Node("");
	}

	public void insert(String key, int value) {
		Node tempNode = root;
		for (int i = 0; i < key.length(); i++) {
			char c = key.charAt(i);
			int asciiIndex = c - 'A';
			if (tempNode.getChild(asciiIndex) == null) {
				Node newNode = new Node(String.valueOf(c));
				tempNode.setChild(asciiIndex, newNode, value);
				tempNode = newNode;
			} else {
				tempNode = tempNode.getChild(asciiIndex);
			}
		}

		tempNode.setLeaf(true);
	}

	public boolean search(String key) {
		Node trieNode = root;
		for (int i = 0; i < key.length(); i++) {
			char c = key.charAt(i);
			int asciiIndex = c - 'A';
			if (trieNode.getChild(asciiIndex) == null) {
				return false;
			} else {
				trieNode = trieNode.getChild(asciiIndex);
			}
		}
		return true;
	}

	public Integer searchAsMap(String key) {
		Node trieNode = root;
		for (int i = 0; i < key.length(); i++) {
			char c = key.charAt(i);
			int asciiIndex = c - 'A';
			if (trieNode.getChild(asciiIndex) != null) {
				trieNode = trieNode.getChild(asciiIndex);
			} else {
				return null;
			}
		}
		return trieNode.getValue();
	}

	public List<String> allWordsWithPrefix(String prefix) {

		Node trieNode = root;
		List<String> allWords = new ArrayList<>();

		for (int i = 0; i < prefix.length(); ++i) {

			char c = prefix.charAt(i);
			int asciiIndex = c - 'A';
			trieNode = trieNode.getChild(asciiIndex);
		}

		collect(trieNode, prefix, allWords);

		return allWords;
	}

	private void collect(Node node, String prefix, List<String> allWords) {

		if (node == null)
			return;

		if (node.isLeaf()) {
			allWords.add(prefix);
		}

		for (Node childNode : node.getChildren()) {
			if (childNode == null)
				continue;
			String childCharacter = childNode.getCharacter();
			collect(childNode, prefix + childCharacter, allWords);
		}
	}

	public static void main(String args[]) {
		Trie trie = new Trie();
		trie.insert("JOSE", 1);
		trie.insert("DON", 2);
		trie.insert("SANJOSE", 3);
		trie.insert("DOG", 4);

		System.out.println(trie.search("DO"));
		System.out.println(trie.searchAsMap("DO"));

	}
}
