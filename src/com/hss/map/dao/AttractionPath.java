package com.hss.map.dao;
/**
 * 查询景点的最短路径
 * @author hu
 *
 */

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.hss.map.util.Util;

public class AttractionPath {
	
	Util util=new Util();
	Connection con=null;
	AttractionDao dao=new AttractionDao();
	int allnum=0;
	private static final int MAX=1000000;
	public List<List<Integer>> screatArray()
	{
		List<List<Integer>> list=new ArrayList<>();
		List<Integer> list1=null;
		con=util.getConnection();
		allnum=dao.returnnum();
		String str="path";
		try{
			for(int i=1;i<=allnum;i++)
			{
				list1=new ArrayList<>();
				for(int j=1;j<=allnum;j++)
				{
					String sql="select path"+j+" from attractions where id="+i;
					PreparedStatement ptmt=con.prepareStatement(sql);
					ResultSet re=ptmt.executeQuery();
					while(re.next())
					{
						int num=re.getInt("path"+j);
						list1.add(num);
					}
				}
				list.add(list1);
			}
		}catch(SQLException e)
		{
			e.printStackTrace();
		}
		/*for(List<Integer> li:list)
		{
			for(Integer l:li)
			{
				System.out.print(l+"\t");
			}
			System.out.println();
		}*/
		return list;
		
	}
	public String shortPath(List<List<Integer>> table1,int startnum,int endnum)
	{
		//使用邻接矩阵进行存储路径
		/*List<Integer> D=new ArrayList<>();//保存最短路径长度
		List<List<Integer>> p=new ArrayList<>();//路径
		List<Integer> finall=new ArrayList<>();//若finall==1说明顶点v1已经在集合S中
		*/
		int n=dao.returnnum();//顶点的个数
		int[][] table=new int[n][n];
		int path[]=new int[n];//用来存放最短路径的
		int[] D=new int[n];//保存最短路径长度
		int[][] p=new int[n][n];//路径
		int[] finall=new int[n];//若final=1则说明定点vi已在集合s中
		int v0=startnum;
		int v,w;
		int listi=0;
		System.out.println(n);
		for(List<Integer> li:table1)
		{
			int listj=0;
			for(Integer l:li)
			{
				table[listi][listj]=l;
				listj++;
				
			}
			listi++;
		}
		for(int i=0;i<n;i++)
		{
			for(int j=0;j<n;j++)
			{
				System.out.print(table[i][j]+",");
			}
			System.out.println();
		}
		for(v=0;v<n;v++)//循环 初始化
	    {
	          finall[v] = 0;
	          D[v] = table[v0][v];
	          for (w = 0; w < n; w++)
	        	  p[v][w] = 0;//设空路径
	          if (D[v] < MAX) 
	          {
	        	  p[v][v0] = 1; 
	        }
	    }
	    D[v0] = 0;
	    finall[v0]=0; //初始化 v0顶点属于集合S
	    //开始主循环 每次求得v0到某个顶点v的最短路径 并加v到集合S中
	    for (int i = 0; i < n; i++) 
	    {
	          int min = MAX;
	          for (w = 0; w < n; w++)
	          {
	               //核心过程--选点
	               if (finall[w]==0) //如果w顶点在V-S中
	               {
	                    //这个过程最终选出的点 应该是选出当前V-S中与S有关联边
	                    //且权值最小的顶点 书上描述为 当前离V0最近的点
	            	   	if (D[w] < min)
	                    {
	                    	v = w;
	                    	min = D[w];
	                    }
	               }
	          }
	          finall[v] = 1; //选出该点后加入到合集S中
	          for (w = 0; w < n; w++)//更新当前最短路径和距离
	          {
	               /*在此循环中 v为当前刚选入集合S中的点
	               	则以点V为中间点 考察 d0v+dvw 是否小于 D[w] 如果小于 则更新
	               */
	               if (finall[w]==0&&(min+table[v][w]<D[w]))
	               {
	                    D[w] = min + table[v][w];	                   
	                    for(int l=0;l<n;l++)
	                    {
	                    	p[l][w]=0;
	                    }
	                    p[v][w]=1;
	               } 
	              }
	        }
	    	path[0]=D[endnum];
			int number=endnum;//结束位置
			int k=2;
			path[1]=endnum+1;
			for(int i=0;i<n;i++)
			{
				for(int j=0;j<n;j++)
				{
					System.out.print(p[i][j]+" ");
				}
				System.out.println();
			}
			for(int i=0;i<n;i++)
			{
				if(p[i][number]==1)
				{
					number=i;
					path[k]=(number+1);
					k++;
				}
				if(number==startnum)//初始位置
				{
					break;	
				}
			}
			path[k]=startnum+1;
			String pathname="最短路径为:";
			for(int i=1;i<path.length;i++)
			{
				if(path[i]!=0)
				{
					if(i==1)
					pathname=pathname+dao.returnname(path[i]);
					else
					pathname=pathname+"一一一一>"+dao.returnname(path[i]);
				}
				System.out.print(path[i]+" ");
				
			}
			System.out.println();
		return pathname+"\n最短路径的长度为:"+path[0]+"米";
	}
	
}
