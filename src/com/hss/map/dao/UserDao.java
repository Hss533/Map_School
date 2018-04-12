package com.hss.map.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.hss.map.model.UserModel;
import com.hss.map.util.Util;

public class UserDao {
	
	Util util=new Util();
	Connection con=null;
	/**
	 * 注册用户
	 * 返回1表示注册成功
	 * 返回0表示注册失败
	 */
	public int addUser(UserModel user){
		int judge=0;
		con=util.getConnection();
		String sql="insert into attuser (name,password,adm) values (?,?,?)";
		try {
			PreparedStatement ptmt=con.prepareStatement(sql);
			
			ptmt.setString(1, user.getName());
			ptmt.setString(2, user.getPassword());
			ptmt.setInt(3, user.getAdm());
			ptmt.executeUpdate();
			judge=1;
			System.out.println("添加成功");
		} catch (SQLException e) {
			judge=0;
			System.out.println("添加失败");
			e.printStackTrace();
		}
		return judge;
	}
	/**
	 * 用户登录
	 * 返回的值为登录是否成功该用户是否为管理员
	 * */
	public int[] Login(String name,String password)
	{
		int[] num={0,0};
		String p=null;
		con=util.getConnection();
		String sql="select id,password,adm from attuser where name=?";
		try {
			PreparedStatement ptmt=con.prepareStatement(sql);
			ptmt.setString(1,name);
			ResultSet res=ptmt.executeQuery();
			while(res.next())
			{
				p=res.getString("password");
				num[1]=res.getInt("adm");
			}
			if(p==null)
			{
				System.out.println(p);
				num[0]=2;//表示用户名不存在
			}
			if(password.equals(p))
			{
				num[0]=1;//表示登录成功
			}
			if(p!=null&&!password.equals(p))
			{
				num[0]=-1;//表示密码不正确
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return num;
	}
	
	/**
	 * 查看用户名是否重复
	 * 如果返回值为1表示没有重复
	 * 如果返回值为0表示重复了
	 * */
	public int judges(String name)
	{
		int judge=0;
		int i=0;
		con=util.getConnection();
		String sql="select * from attuser where name=?";
		try {
			PreparedStatement ptmt=con.prepareStatement(sql);
			ptmt.setString(1, name);
			ResultSet res=ptmt.executeQuery();
			while(res.next())
			{
				i++;
			}
			if(i==0)
				judge=1;
			else judge=0;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return judge;
	}
	
}
