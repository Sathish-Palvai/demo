package com.palvai.hash;

import java.util.HashMap;

class RandomListNode {
	int label;
	RandomListNode next, random;

	RandomListNode(int x) {
		this.label = x;
	}
};

public class RandomList {

	public RandomListNode copyRandomList(RandomListNode head) {

		HashMap<RandomListNode, RandomListNode> map = new HashMap<RandomListNode, RandomListNode>();

		RandomListNode temp = head;

		/*
		 * Traverse the linked list and store the original node as key with it's
		 * corresponding duplicate copy as it's value in the HashMap
		 */
		while (temp != null) {
			map.put(temp, new RandomListNode(temp.label));
			temp = temp.next;
		}

		temp = head;

		/* Associate 'next' and 'random' pointers for the duplicate nodes */
		while (temp != null) {

			/* Get duplicate node for the original node */
			RandomListNode duplicateNode = map.get(temp);

			/*
			 * Get duplicate nodes pointed to by the 'next' and 'random' pointers of the
			 * original node
			 */
			RandomListNode duplicateNextNode = map.get(temp.next);
			RandomListNode duplicateRandomNode = map.get(temp.random);

			duplicateNode.next = duplicateNextNode;
			duplicateNode.random = duplicateRandomNode;

			temp = temp.next;
		}

		/* Get the head node of duplicate list */
		return map.get(head);
	}
	
	

}
