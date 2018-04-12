/***
 * 1000000 1000000 1000000 1000000 1000000 1000000 1000000 1000000 1000000 1000000 1000000 1000000 1000000 1000000 1000000 1000000 1000000 1000000 1000000 1000000 1000000 1000000 30 20 20 1000000 1000000 1000000 1000000 1000000
 */
package com.hss.map.view;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import com.hss.map.dao.UserDao;
import com.hss.map.model.UserModel;

public class UserView  extends JFrame{
	
	UserDao dao=new UserDao();
	MainView main=new MainView();
	UserView userView=null;
	UserModel user=null;
	public void loginUi()
	{
		JFrame jf=new JFrame("用户登录");
		JPanel jp=new JPanel();
		JLabel jl1=new JLabel("登录        ");
		jl1.setFont(new Font("黑体", Font.BOLD, 24));
		JLabel jl2=new JLabel("用户名");
		jl2.setFont(new Font("黑体", Font.BOLD, 18));
		JLabel jl3=new JLabel("密  码");
		jl3.setFont(new Font("黑体", Font.BOLD, 18));
		JTextField jt1=new JTextField();//用户名
		JPasswordField jt2=new JPasswordField();//密码
		JButton bu1=new JButton("注册");
		JButton bu2=new JButton("重置");
		JButton bu3=new JButton("确定");
		JButton bu4=new JButton("退出");
		JButton bu5=new JButton("游客试用");
		jp.setLayout(null);
		jl1.setBounds(200,1, 200, 50);
		jl2.setBounds(50,80,200,30);
		jl3.setBounds(50,180,200,30);
		jt1.setBounds(150,80,200,30);
		jt2.setBounds(150,180,200,30);
		bu1.setBounds(50,300,80,30);
		bu2.setBounds(180,300,80,30);
		bu3.setBounds(300,300,80,30);
		bu4.setBounds(350,400,80,30);
		bu5.setBounds(50,400,100,30);
		jp.add(jl1);
		jp.add(jl2);
		jp.add(jl3);
		jp.add(jt1);
		jp.add(jt2);
		jp.add(bu1);
		jp.add(bu2);
		jp.add(bu3);
		jp.add(bu4);
		jp.add(bu5);
		jf.add(jp);
		jf.setVisible(true);
		jf.setBounds(600,200,500,500);
		bu1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				userView=new UserView();
				userView.addUser();
			}
		});
		/**重置*/
		bu2.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				jt1.setText("");
				jt2.setText("");
			}
		});
		/**确定*/
		bu3.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				if(!("".equals(jt1.getText().replaceAll(" +","")))&&!("".equals(jt2.getText().replaceAll(" +",""))))
				{
					int[] num=new int[2];
					String name=jt1.getText();
					String password=jt2.getText();
					num=dao.Login(name, password);
					if(num[0]==-1)
					{
						JOptionPane.showMessageDialog(null,"密码不正确");
					}
					if(num[0]==0)
					{
						JOptionPane.showMessageDialog(null,"登录失败");
					}
					if(num[0]==2)
					{
						JOptionPane.showMessageDialog(null,"用户名不存在");
					}
					if(num[0]==1)
					{
						System.out.println("登录成功");
						JOptionPane.showMessageDialog(null,"登录成功");
						jf.dispose();
						main.mainUi(name,num[1]);
					}
					
				}
				else{
					//用户名和密码不能为空
					JOptionPane.showMessageDialog(null,"用户名和密码不能为空");
				}
			}
		});
		bu4.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		bu5.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				main.mainUi("未登录",0);
			}
		});
	}
	public void addUser()
	{
		JFrame jf=new JFrame("用户注册");
		JPanel jp=new JPanel();
		JLabel jl1=new JLabel("用户注册");
		jl1.setFont(new Font("黑体", Font.BOLD, 24));
		JLabel jl2=new JLabel("用 户 名");
		jl2.setFont(new Font("黑体", Font.BOLD, 18));
		JLabel jl3=new JLabel("密    码");
		jl3.setFont(new Font("黑体", Font.BOLD, 18));
		JLabel jl4=new JLabel("确认密码");
		jl4.setFont(new Font("黑体", Font.BOLD, 18));
		JTextField jt1=new JTextField();//name
		JPasswordField jt2=new JPasswordField();//pass
		JPasswordField jt3=new JPasswordField();//repass
		JButton bu1=new JButton("重置");
		JButton bu2=new JButton("取消");
		JButton bu3=new JButton("确定");
		jp.setLayout(null);
		jl1.setBounds(200,1, 200, 50);
		jl2.setBounds(50,80,200,30);
		jl3.setBounds(50,150,200,30);
		jl4.setBounds(50,220,200,30);
		jt1.setBounds(150,80,200,30);
		jt2.setBounds(150,150,200,30);
		jt3.setBounds(150, 220, 200,30);
		bu1.setBounds(50,300,80,30);
		bu2.setBounds(180,300,80,30);
		bu3.setBounds(300,300,80,30);
		jp.add(jl1);
		jp.add(jl2);
		jp.add(jl3);
		jp.add(jl4);
		jp.add(jt1);
		jp.add(jt2);
		jp.add(jt3);
		jp.add(bu1);
		jp.add(bu2);
		jp.add(bu3);
		jf.add(jp);
		jf.setBounds(600, 200, 500, 500);
		jf.setVisible(true);
		bu1.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				jt1.setText("");
				jt2.setText("");
				jt3.setText("");
			}
		});
		bu2.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				jf.dispose();
			}
		});
		bu3.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				
				String name=jt1.getText().replaceAll(" +","");
				String pass=jt2.getText().replaceAll(" +","");
				String repa=jt3.getText().replaceAll(" +","");
				System.out.println("name="+name+"pass="+pass+"repa="+repa);
				if((!("".equals(name))&&(!("".equals(pass)))&&(!("".equals(repa)))))
				{
					System.out.println(dao.judges(name));
					if(dao.judges(name)==1)
					{
						System.out.println("2");
						if(pass.equals(repa))
						{
							System.out.println("3");
							user=new UserModel();
							user.setName(name);
							user.setPassword(pass);
							user.setAdm(0);
							dao.addUser(user);
							JOptionPane.showMessageDialog(null,"注册成功");
							jf.dispose();
						}
						else
						{
							//密码不相符
							JOptionPane.showMessageDialog(null,"两次输入的密码不相同");
						}
					
					}
					else{
						//用户名已存在
						JOptionPane.showMessageDialog(null,"该用户名已存在");
					}
				}
				else
				{
					//全为空格
					JOptionPane.showMessageDialog(null,"用户名或密码全为空格");
				}
			}
		});
	}
	public static void main(String[] args) {
		UserView tets=new UserView();
		tets.loginUi();;
	}
}
