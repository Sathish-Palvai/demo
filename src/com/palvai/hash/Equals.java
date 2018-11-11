package com.palvai.hash;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.List;

public class Equals {
	
	public ArrayList<Integer> equal(List<Integer> A) 
  {
      int sum=0;
      ArrayList<Integer>ans=null;
      Hashtable<Integer,ArrayList<Integer>>ht=new Hashtable<Integer,ArrayList<Integer>>();
      for(int i=0;i<A.size();i++)
      {
          for(int j=i+1;j<A.size();j++)
          {
              sum=A.get(i)+A.get(j);
              System.out.println("Sum = " + sum);
              if(ht.containsKey(sum))
              {
                  ArrayList<Integer>a=ht.get(sum);
                  if(a.get(0)!=i && a.get(1)!=j && a.get(0)!=j && a.get(1)!=i)
                  {
                      ArrayList<Integer>tmp=new ArrayList<Integer>();
                      tmp.add(a.get(0));
                      tmp.add(a.get(1));
                      tmp.add(i);
                      tmp.add(j);
                      if(ans==null)
                          ans=tmp;
                      else
                      {
                          for(int k=0;k<4;k++)
                          {
                              if(ans.get(k)<tmp.get(k))
                                  break;
                              else if(ans.get(k)>tmp.get(k))
                              {
                                  ans=tmp;
                                  break;
                              }
                          }
                      }
                  }
              }
              else
              {
                  ArrayList<Integer>l=new ArrayList<Integer>();
                  l.add(i);
                  l.add(j);
                  ht.put(sum,l);
              }
          }
      }
      return ans;
  }
	
	public static void main(String args[]) {
		Equals obj = new Equals();
		System.out.println(obj.equal(Arrays.asList(3, 4, 7, 1, 2, 9, 8)));
	}

}
