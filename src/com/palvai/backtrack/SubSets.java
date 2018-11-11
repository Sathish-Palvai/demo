package com.palvai.backtrack;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class SubSets {

	public static ArrayList<ArrayList<Integer>> subsets(List<Integer> A) {

		ArrayList<ArrayList<Integer>> retVal = new ArrayList<ArrayList<Integer>>();
		int n = A.size();
		long pow_set_size = (long) Math.pow(2, n);
		int counter, j;

		for (counter = 0; counter < pow_set_size; counter++) {
			ArrayList<Integer> temp = new ArrayList<Integer>();
			for (j = 0; j < n; j++) {

				if ((counter & (1 << j)) > 0)
					temp.add(A.get(j));
			}
			Collections.sort(temp);
			

			retVal.add(temp);
		}

		return retVal;

	}

	public static void main(String args[]) {
		ArrayList<ArrayList<Integer>> ret = SubSets.subsets(Arrays.asList(15, 20, 12, 19, 4 ));
		System.out.println(ret);

		Collections.sort(ret, new Comparator<List<Integer>>() {
			@Override
			public int compare(List<Integer> a, List<Integer> b) {
				if(a.size() == 0 || b.size() == 0) {
					return 1;
				}
				return a.get(0).compareTo(b.get(0));
			}
		});

		System.out.println("Sorted " + ret);

	}
	
	public ArrayList<ArrayList<Integer>> subsets(ArrayList<Integer> a) {
		ArrayList<ArrayList<Integer>> output = new ArrayList<ArrayList<Integer>>();
		output.add(new ArrayList<Integer>());
		if (a.size() == 0)
			return output;
		Collections.sort(a);
		generate(a, output, new ArrayList<Integer>(), 0);
		return output;
	}
	
	public void generate(ArrayList<Integer> a, ArrayList<ArrayList<Integer>> output, ArrayList<Integer> temp, int index)
	{
		for (int i = index; i < a.size(); i++)
		{
			temp.add(a.get(i));
			output.add(new ArrayList<Integer>(temp));
			generate(a, output, temp, i+1);
			temp.remove(temp.size() - 1);
		}
	}

}
