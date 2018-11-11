package com.palvai.hash;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class Anagram {
	
	public ArrayList<ArrayList<Integer>> anagrams(final List<String> a) {
		
    ArrayList<ArrayList<Integer>> result = new ArrayList<ArrayList<Integer>>(); 
    HashMap<String,ArrayList<Integer>> map = new HashMap<String,ArrayList<Integer>>();
    int n = a.size();
    ArrayList<Integer> list = new ArrayList<Integer>(); 
    for(int i = 0; i < n; i++)
    {
        char[] arr = a.get(i).toCharArray();
        Arrays.sort(arr);
        String str = new String(arr); 
        if(map.get(str) != null)
        {
            map.get(str).add(i+1); 
        }
        else { 
            ArrayList<Integer> g = new ArrayList<Integer>();
              g.add(i+1);
              map.put(str, g);
        }
    }
    
    for(ArrayList<Integer> l: map.values())
    {
        result.add(l); 
    }
    return result; 
}

}
