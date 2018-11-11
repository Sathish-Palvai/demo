package com.palvai.backtrack;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class SubSetsDuplicates {
	private ArrayList<ArrayList<Integer>> ans = new ArrayList<ArrayList<Integer>>();
	private ArrayList<Integer> temp = new ArrayList<Integer>();
	private List<Integer> A = new ArrayList<Integer>();

	public static void main(String args[]) {
		SubSetsDuplicates obj = new SubSetsDuplicates();
		System.out.println(obj.subsetsWithDup(Arrays.asList(1, 2, 2)));
		// System.out.println(ret);

	}

	public static ArrayList<ArrayList<Integer>> subsets(ArrayList<Integer> a) {
		ArrayList<ArrayList<Integer>> output = new ArrayList<ArrayList<Integer>>();
		output.add(new ArrayList<Integer>());
		if (a.size() == 0)
			return output;
		Collections.sort(a);
		generate(a, output, new ArrayList<Integer>(), 0);
		return output;
	}

	public static void generate(ArrayList<Integer> a, ArrayList<ArrayList<Integer>> output, ArrayList<Integer> temp,
	    int index) {
		for (int i = index; i < a.size(); i++) {
			temp.add(a.get(i));
			output.add(new ArrayList<Integer>(temp));
			generate(a, output, temp, i + 1);
			temp.remove(temp.size() - 1);
		}
	}

	public static void findPowerSet(List<Integer> S) {
		// N stores total number of subsets
		int N = (int) Math.pow(2, S.size());
		Set<ArrayList<Integer>> set = new HashSet<ArrayList<Integer>>();

		// generate each subset one by one
		for (int i = 0; i < N; i++) {
			ArrayList<Integer> subset = new ArrayList<Integer>();

			// check every bit of i
			for (int j = 0; j < S.size(); j++) {
				// if j'th bit of i is set, append S[j] to subset
				if ((i & (1 << j)) != 0) {
					subset.add(S.get(j));

				}
			}

			// insert the subset into the set
			set.add(subset);
		}

		// print all subsets present in the set
		for (ArrayList<Integer> subset : set) {
			System.out.println(subset);
		}
	}

	public ArrayList<ArrayList<Integer>> subsetsWithDup(List<Integer> A) {
		this.A = A;
		Collections.sort(this.A);
		backtrack(0);
		return ans;
	}

	public void backtrack(int index) {

		// if(!ans.contains(list)){
		ans.add(new ArrayList<Integer>(temp));
		for (int i = index; i < this.A.size(); i++) {
			//if (i > index && this.A.get(i) == A.get(i - 1)) {
				//continue;
			//}
			temp.add(this.A.get(i));
			backtrack(i + 1);
			temp.remove(temp.size() - 1);
		}
	}

}
