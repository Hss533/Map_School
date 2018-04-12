package com.hss.map.dao;
import com.hss.map.model.*;
import com.hss.map.util.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AttractionDao {
	
	Util util=new Util();
	Connection con=null;
	private String[] string=null;
	/**增加景点*/
	public boolean add(AttractionModel att,int[] pathnum)
	{
		boolean judge=true;
		//先添加列后添加行
		con=util.getConnection();
		/*获取现在表中的行数*/
		int attnum=this.returnnum();
		int newattnum=attnum+1;
		String sqlcol="alter table attractions add column path"+newattnum+" int default 1000000";//添加列
		System.out.println(sqlcol);
		try {
			PreparedStatement ptmt=con.prepareStatement(sqlcol);
			ptmt.executeUpdate();
			
		} catch (SQLException e) {
			judge=false;
			System.out.println("添加列操作失败");
			e.printStackTrace();
		}
		String addcol="update attractions set path"+newattnum+"=? where id=?";
		System.out.println(addcol);
			try {	
				for(int i=1;i<=attnum;i++)
				{
					PreparedStatement ptmt1=con.prepareStatement(addcol);
					ptmt1.setInt(1, pathnum[i-1]);
					ptmt1.setInt(2, i);
					ptmt1.executeUpdate();
				}
				
			} catch (SQLException e) {
				judge=false;
				e.printStackTrace();
				System.out.println("添加列数据失败");
			}
		//添加行
			String pathname="";
			for(int i=1;i<=newattnum;i++)
			{
				pathname=pathname+",path"+i;
			}
			String  val="";
			for(int i=1;i<=newattnum;i++)
			{
				val=val+",?";
			}
			String sqlrow="insert into attractions (name,introduction"+pathname+") values (?,?"+val+")";
			System.out.println(sqlrow);
			try{
				PreparedStatement ptmt=con.prepareStatement(sqlrow);
				ptmt.setString(1, att.getName());
				ptmt.setString(2, att.getIntroduction());
				for(int i=1;i<=newattnum;i++)
				{
					ptmt.setInt(i+2,pathnum[i-1]);
				}
				ptmt.executeUpdate();
				
			}catch(Exception  e)
			{
				judge=false;
				e.printStackTrace();
				System.out.println("添加行失败");
			}
		return judge;
	}
	/**删除景点*/
	public boolean del(int num)
	{
		boolean judge=true;
		con=util.getConnection();
		int attnum=this.returnnum();
		String sql2="update attractions set "+"path"+num+"=1000000";
		try{	
			Statement ptmt2=con.createStatement();
			ptmt2.executeUpdate(sql2);
		}catch(Exception e)
		{	
			judge=false;
			System.out.println("删除失败");
			e.printStackTrace();
		}
		//修改行
		String sql="";
		for(int i=1;i<=attnum;i++)
		{
			sql=sql+",path"+i+"=1000000";
		}
		String sql1="update attractions set name='已删除'"+sql+" where id=?";
		try {
				PreparedStatement ptmt=con.prepareStatement(sql1);
				ptmt.setInt(1, num);
				ptmt.executeLargeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return judge;
	}
	/**修改景点信息*/
	public void modify(AttractionModel att,int[] pathnum)
	{
		con=util.getConnection();
		int attnum=this.returnnum();	
		String sql="";
		for(int i=1;i<=attnum;i++)
		{
			sql=sql+",path"+i+"=?";
		}
		String sqlrow="update attractions set name=?,introduction=?"+sql+" where id="+att.getId();
		System.out.println(sqlrow);
		try {
			PreparedStatement ptmt=con.prepareStatement(sqlrow);
			ptmt.setString(1,att.getName());
			ptmt.setString(2,att.getIntroduction());
			for(int i=1;i<=attnum;i++)
			{
				ptmt.setInt(i+2, pathnum[i-1]);	
			}
			ptmt.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		String sqlcol="update attractions set path"+att.getId()+"=? where id=?";
		System.out.println(sqlcol);
		try{
			for(int i=1;i<=attnum;i++)
			{
				PreparedStatement ptmt=con.prepareStatement(sqlcol);
				ptmt.setInt(1,pathnum[i-1]);
				ptmt.setInt(2, i);
				ptmt.executeUpdate();
			}
			
		}catch(Exception e )
		{
			System.out.println("修改失败");
			e.printStackTrace();
		}
	}
	/**查找所有景点的信息*/
	public List<AttractionModel> ergodic()
	{
		con=util.getConnection();
		String sql="select * from attractions";
		List<AttractionModel> list=null;
		AttractionModel  att=null;
		try{
			list=new ArrayList<>();
			
			PreparedStatement ptmt=con.prepareStatement(sql);
			ResultSet  res=ptmt.executeQuery();
			while(res.next())
			{
				att=new AttractionModel();
				att.setId(res.getInt("id"));
				att.setName(res.getString("name"));
				att.setIntroduction(res.getString("introduction"));
				list.add(att);
			}
		}catch(Exception  e)
		{
			System.out.println("查询失败");
			e.printStackTrace();
		}
		
		return list;
	}
	/**查找个别景点的信息*/
	public List<AttractionModel> find(int id)
	{
		con=util.getConnection();
		String sql="select * from attractions where id=?";
		List<AttractionModel> list=null;
		AttractionModel att=null;
		try{
			list=new ArrayList<>();
			PreparedStatement ptmt=con.prepareStatement(sql);
			ptmt.setInt(1,id);
			ResultSet res=ptmt.executeQuery();
			while(res.next())
			{
				att=new AttractionModel();
				att.setId(res.getInt("id"));
				att.setName(res.getString("name"));
				att.setIntroduction(res.getString("introduction"));
				list.add(att);
			}
			
		}catch(Exception e)
		{
			System.out.println("查询失败");
		}
		return list;
	}
	/**返回记录条数*/
	public int returnnum()
	{
		int num=0;
		con=util.getConnection();
		String sql="select * from attractions";
		try{
			
			PreparedStatement ptmt=con.prepareStatement(sql);
			ResultSet res=ptmt.executeQuery();
			while(res.next())
			{
				num++;
			}
		}catch(Exception  e)
		{
			e.printStackTrace();
			System.out.println("查询失败");
		}
		return num;
		
	}
	/**根据名字查询*/
	public int[] returnid(String name)
	{
		int num[]=new int[100];
		int i=0;
		con=util.getConnection();
		String sql="select * from attractions where name like '%"+name+"%'";
		try{
			PreparedStatement ptmt=con.prepareStatement(sql);
			ResultSet res=ptmt.executeQuery();
			while(res.next())
			{
				num[i]=res.getInt("id");
				i++;
			}
		}catch(SQLException e)
		{
			System.out.println("查询失败");
			e.printStackTrace();
		}
		return num;
	}
	public String[] returnmessage(String name)
	{
		string=new String[5];
		con=util.getConnection();
		int num=this.returnnum();
		String str="";
		for(int i=1;i<=num;i++)
		{
			str=str+",path"+i;
		}
		String sql="select name,introduction"+str+" from attractions where name like '%"+name+"%'";
		System.out.println(sql);
		try{
			
			PreparedStatement ptmt=con.prepareStatement(sql);
			ResultSet res=ptmt.executeQuery();
			while(res.next())
			{
				string[0]=res.getString("name");
				string[1]=res.getString("introduction");
				String pre="";
				for(int i=1;i<=num;i++)
				{
					String a="path"+i;
					pre=pre+""+res.getInt(a)+" ";
				}
				System.out.println(pre);
				string[2]=pre;
			}
		}catch(SQLException e)
		{
			e.printStackTrace();
		}
		return string;
	}
	public String returnname(int id)
	{
		String name=null;
		con=util.getConnection();
		String sql="select name from attractions where id=?";
		try {
			PreparedStatement ptmt=con.prepareStatement(sql);
			ptmt.setInt(1, id);
			ResultSet re=ptmt.executeQuery();
			while(re.next())
			{
				
				name=re.getString("name");
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} 
		return name;
	}
	
}
