package com.hss.map.test;

import java.util.ArrayList;
import java.util.List;

public class Test {
	static Test1 a=new Test1();
	static Test1 a2=null;
	public static void main(String[] args) {
		List<List<Integer>> list=new ArrayList<List<Integer>>();
		List<Integer> list0=new ArrayList<>();
		list0.add(1);
		list0.add(2);
		list.add(list0);
		List<Integer> list1=new ArrayList<>();
		list1.add(2);
		list1.add(3);
		list.add(list1);
		for(List<Integer> l:list)
		{
			for(Integer num:l)
			{
				System.out.print(num+" ");
				
			}
			System.out.println();
		}
		System.out.println(a);
		System.out.println(a2);
	}
}
class Test1{
	
}
