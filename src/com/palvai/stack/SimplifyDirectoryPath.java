package com.palvai.stack;

import java.util.LinkedList;
import java.util.Stack;

public class SimplifyDirectoryPath {

	public String simplifyPath(String A) {
		LinkedList<String> linkedList = new LinkedList<String>();
		String[] split = A.split("/");
		// System.out.println("Split length " + split.length);
		for (int i = 0; i < split.length; i++) {
			String token = split[i];
			if (token.equals(".")) {
				continue;
			} else if (token.equals("..")) {
				if (linkedList.size() != 0) {
					linkedList.removeLast();
					System.out.println("Linked List " + linkedList.toString());
				}
			} else {
				if (!token.trim().equals(""))
					linkedList.add(token);
			}
		}
		StringBuilder finalPath = new StringBuilder();

		// System.out.println("finalPath " + finalPath);
		while (linkedList.size() > 0) {
			String val = linkedList.remove();
			if (!val.trim().equals("")) {
				finalPath.append("/");
				finalPath.append(val);
			}

		}
		String finalString = finalPath.toString();
		// System.out.println("finalString " + finalString);
		return (finalString.length() == 0) ? "/" : finalString;
	}

	public static void main(String args[]) {
		SimplifyDirectoryPath sdp = new SimplifyDirectoryPath();
		System.out.println(sdp.simplifyPath(
		    "/./.././ykt/xhp/nka/eyo/blr/emm/xxm/fuv/bjg/./qbd/./../pir/dhu/./../../wrm/grm/ach/jsy/dic/ggz/smq/mhl/./../yte/hou/ucd/vnn/fpf/cnb/ouf/hqq/upz/akr/./pzo/../llb/./tud/olc/zns/fiv/./eeu/fex/rhi/pnm/../../kke/./eng/bow/uvz/jmz/hwb/./././ids/dwj/aqu/erf/./koz/.."));
	}

	public String simplifyPathUsingStack(String a) {
		if (a.isEmpty() || a.equals("/"))
			return "/";

		String[] segments = a.split("/");

		Stack<String> nameStack = new Stack<>();

		for (int i = 0; i < segments.length; i++) {
			String curr = segments[i];
			if (curr.isEmpty() || curr.equals("."))
				continue;

			if (curr.equals("..")) {
				if (!nameStack.isEmpty())
					nameStack.pop();
				continue;
			}

			nameStack.push(curr);
		}

		StringBuilder path = new StringBuilder();
		for (String name : nameStack) {
			path.append("/");
			path.append(name);
		}
		if (path.length() == 0)
			path.append("/");

		return path.toString();
	}

}
