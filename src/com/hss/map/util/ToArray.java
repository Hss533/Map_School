package com.hss.map.util;

import javax.swing.JOptionPane;

//工具类:将字符串转换为数组
public class ToArray {
	public int[] stringtoarray(String string)
	{
		string=string.trim();//去掉首尾空格
		int[] number=new int[100];
		String[] strings=string.split("\\s+");
		try{
				for(int i=0;i<strings.length;i++)
				{
					number[i]=Integer.parseInt(strings[i]);
				}
		}catch(NumberFormatException e)
		{
			number[99]=1;
		}
		return number;
	}
	
}
