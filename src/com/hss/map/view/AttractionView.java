package com.hss.map.view;
import image.*;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.LayoutManager;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import com.hss.map.dao.AttractionDao;
import com.hss.map.dao.AttractionPath;
import com.hss.map.model.AttractionModel;
import com.hss.map.util.ToArray;

public class AttractionView extends JFrame implements ItemListener{

	private int judge=0; 
	private int[] number=new int[100];
	private String[] string=null;
	int[] path1=null;
	int[] path2=null;
	List<List<Integer>> list=null;
	
	
	ToArray to=new ToArray();
	AttractionModel att=new AttractionModel();
	AttractionDao dao=new AttractionDao();
	AttractionPath path=new AttractionPath();
	
	JButton bu1=null;
	JButton bu3=null;
	JButton bu4=null;
	JComboBox jc=null;
	JComboBox jc1=null;
	JComboBox jc2=null;
	JTextField jt1=null;
	JTextArea jt2=null;
	JTextArea jt3=null;
	JTextArea jt4=null;
	JButton bupath1=null;
	JButton bupath2=null;
	JFrame jfdel=null;
	JFrame jfmod=null;
	JFrame jfadd=null;
	/**删除景点*/
	public void delAttUi()
	{
		this.judge=0;
		jfdel=new JFrame("删除景点");
		JPanel jp=new JPanel();
		jp.setLayout(null);
		JLabel jl=new JLabel("请选择要删除的景点");
		jl.setFont(new Font("黑体",Font.BOLD, 24));
		bu1=new JButton("确定");
		JButton bu2=new JButton("取消");
		//添加
		String name=null;
		int num=dao.returnnum();
		int j=0;
		List<AttractionModel> list=new ArrayList<>();
		JRadioButton[] br=new JRadioButton[num];
		ButtonGroup bg=new ButtonGroup();
		for(int i=0;i<num;i++)
		{
			list=dao.find(i+1);
			for(AttractionModel attest:list)
			{
				name=attest.getName();
			}
			if(!("已删除".equals(name)))
			{
				System.out.println(name);
				System.out.println(i);
			    br[i]=new JRadioButton(name);
			    if(j%2==0)
			    br[i].setBounds(150,80+j*20,300,20);
				if(j%2!=0)
				br[i].setBounds(500,60+j*20,300,20);
				jp.add(br[i]);
				bg.add(br[i]);
				br[i].addItemListener(this);
				j++;
			}
			
		}
		jl.setBounds(300, 20, 1000, 20);
		bu1.setBounds(200, 700, 100, 30);
		bu2.setBounds(500, 700, 100, 30);
		jp.add(jl);
		jp.add(bu1);
		jp.add(bu2);
		jfdel.add(jp);
		jfdel.setBounds(500, 150, 800, 800);
		jfdel.setVisible(true);
		
		bu2.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				jfdel.dispose();
			}
		});
		
		
	}
	
	public void itemStateChanged(ItemEvent e) {
		
		if(e.getStateChange()==ItemEvent.SELECTED&&judge==0)
		{
			//删除景点
			String name=((JRadioButton)e.getSource()).getText();
			System.out.println(name);
			int[] num=new int[2];
			bu1.addActionListener(new ActionListener() {
				
				public void actionPerformed(ActionEvent arg0) {
				 int[] num=dao.returnid(name);
				 System.out.println(num[0]);
				 dao.del(num[0]);
				 JOptionPane.showMessageDialog(null,"删除成功");
				 jfdel.dispose();
				}
			});
			
		}
		if(e.getStateChange()==ItemEvent.SELECTED&&judge==1)
		{
			//修改景点
			String str=(String)jc.getSelectedItem();
			string=new String[2];
			string=dao.returnmessage(str);
			jt1.setText(string[0]);
			jt2.setText(string[1]);
			jt3.setText(string[2]);
			bu4.addActionListener(new ActionListener() {
				
				public void actionPerformed(ActionEvent e) {
					
					int[] id=new int[1];
					id=dao.returnid(string[0]);
					AttractionModel attractionModel=new AttractionModel();
					attractionModel.setName(jt1.getText());
					attractionModel.setIntroduction(jt2.getText());
					attractionModel.setId(id[0]);
					int[] num=new int[100];
					
					num=to.stringtoarray(jt3.getText());
					dao.modify(attractionModel,num);
					JOptionPane.showMessageDialog(null,"修改成功");
					jfmod.dispose();
				}
			});
		}
		
		if(e.getStateChange()==ItemEvent.SELECTED&&judge==2)
		{
			System.out.println("最短路径");
			String pathstart=(String) jc1.getSelectedItem();
			String pathend=(String)jc2.getSelectedItem();
			System.out.println(pathstart+" "+pathend);
			path1=new int[2];
			path2=new int[2];
			path1=dao.returnid(pathstart);
			path2=dao.returnid(pathend);
			bupath1.addActionListener(new ActionListener() {
				
				public void actionPerformed(ActionEvent e) {
					System.out.println(path1[0]+" "+path2[0]);
					list=path.screatArray();
					jt4.setText(path.shortPath(list,path2[0]-1,path1[0]-1));
				}
			});
		}
	}
	
	/**修改景点信息*/
	public void modifyAttUi()
	{
		this.judge=1;
		jfmod=new JFrame("修改景点");
		JPanel jp=new JPanel();
		int num=dao.returnnum();
		int i=0;
		String[] str=new String[num];
		List<AttractionModel> list=new ArrayList<>();
		list=dao.ergodic();
		for(AttractionModel att:list)
		{
			if(!att.getName().equals("已删除"))
			{
				str[i]=att.getName();
				System.out.println(str[i]);
				i++;
			}
		}
		jc=new JComboBox(str);
		JLabel jl1=new JLabel("请选择景点");
		JLabel jl2=new JLabel("修改景点名称");
		JLabel jl3=new JLabel("修改景点介绍");
		JLabel jl4=new JLabel("修改该景点与其他景点之间的距离");
		jl1.setFont(new Font("黑体",Font.BOLD, 16));
		jl2.setFont(new Font("黑体",Font.BOLD, 16));
		jl3.setFont(new Font("黑体",Font.BOLD, 16));
		jl4.setFont(new Font("黑体",Font.BOLD, 16));
		jt1=new JTextField();
		jt2=new JTextArea();
		jt3=new JTextArea();
		jt2.setLineWrap(true);
		jt3.setLineWrap(true);
		JButton bu1=new JButton("重置");
		JButton bu2=new JButton("取消");
		bu4=new JButton("确定");
		jp.setLayout(null);	
		jl1.setBounds(50, 10, 500, 100);
		jl2.setBounds(50, 50, 200, 150);
		jl3.setBounds(50, 130, 200, 100);
		jl4.setBounds(50, 230, 300, 100);
		jt1.setBounds(170, 105, 250, 30);
		jt2.setBounds(170, 170, 250, 80);
		jt3.setBounds(50, 300, 400, 80);
		bu1.setBounds(50,400,80,30);
		bu2.setBounds(200,400,80,30);
		bu4.setBounds(340,400,80,30);
		jfmod.setBounds(700, 200, 500, 530);
		jc.setBounds(170, 40, 200, 30);
		jp.add(jl1);
		jp.add(jl2);
		jp.add(jl3);
		jp.add(jl4);
		jp.add(jt1);
		jp.add(jt2);
		jp.add(jt3);
		jp.add(bu1);
		jp.add(bu2);
		jp.add(bu4);
		jp.add(jc);
		jfmod.add(jp);
		jfmod.setVisible(true);
		bu1.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				jt1.setText("");
				jt2.setText("");
				jt3.setText("");
			}
		});
		bu2.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				jfmod.dispose();
			}
		});
		jc.addItemListener(this);
	}
	
	/**查找所有景点的信息*/
	public void ergodicAttUi()
	{
		JFrame jf=new JFrame("浏览所有景点");
		JPanel jp=new JPanel();
		JLabel jl1=new JLabel("所有的景点有");
		JLabel jl4=new JLabel("景点名称");
		JLabel jl5=new JLabel("景点介绍"); 
		jl4.setFont(new Font("黑体",Font.BOLD, 22));
		jl5.setFont(new Font("黑体",Font.BOLD, 22));
		jl1.setFont(new Font("黑体",Font.BOLD, 24));
		List<AttractionModel> list=new ArrayList<>();
		list=dao.ergodic();
		int i=0;
		for(AttractionModel a:list)
		{
			String str1=a.getName();
			String str2=a.getIntroduction();
			if(!str1.equals("已删除"))
			{
			JLabel jl2=new JLabel(str1);
			jl2.setFont(new Font("楷体",Font.BOLD, 18));
			jl2.setBounds(30,140+i*30,250,20);
			JLabel jl3=new JLabel(str2);
			jl3.setFont(new Font("楷体",Font.BOLD, 18));
			jl3.setBounds(300,137+i*30,500,30);
			jp.add(jl2);
			jp.add(jl3);
			i++;
			}
			
		}
		jp.setLayout(null);
		jl1.setBounds(20, 10, 500, 100);
		jl4.setBounds(20, 50, 500, 100);
		jl5.setBounds(300, 50, 500, 100);
		jp.add(jl1);
		jp.add(jl5);
		jp.add(jl4);
		jf.add(jp);
		jf.setVisible(true);
		jf.setBounds(400, 0, 800, 1200);
	}

	/**增加景点*/
	public void addAttUi()
	{
		this.judge=0;
		jfadd=new JFrame("添加景点");
		JPanel jp=new JPanel();
		JLabel jl1=new JLabel("添加景点");
		JLabel jl2=new JLabel("景点名称");
		JLabel jl3=new JLabel("景点介绍");
		JLabel jl4=new JLabel("该景点与其他景点之间的距离");
		jl1.setFont(new Font("黑体",Font.BOLD, 24));
		jl2.setFont(new Font("黑体",Font.BOLD, 18));
		jl3.setFont(new Font("黑体",Font.BOLD, 18));
		jl4.setFont(new Font("黑体",Font.BOLD, 18));
		JTextField jt1=new JTextField();
		JTextArea jt2=new JTextArea();
		JTextArea jt3=new JTextArea();
		jt2.setLineWrap(true);
		jt3.setLineWrap(true);
		JButton bu1=new JButton("重置");
		JButton bu2=new JButton("取消");
		JButton bu3=new JButton("确定");
		jp.setLayout(null);	
		jl1.setBounds(175, 10, 100, 100);
		jl2.setBounds(50, 70, 100, 100);
		jl3.setBounds(50, 130, 100, 100);
		jl4.setBounds(50, 230, 300, 100);
		jt1.setBounds(150, 105, 150, 30);
		jt2.setBounds(150, 170, 250, 80);
		jt3.setBounds(50, 305, 350, 60);
		bu1.setBounds(50,400,80,30);
		bu2.setBounds(200,400,80,30);
		bu3.setBounds(340,400,80,30);
		jfadd.setBounds(700, 200, 500, 530);
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
		jfadd.add(jp);
		jfadd.setVisible(true);
		bu1.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				jt1.setText("");
				jt2.setText("");
				jt3.setText("");
			}
		});
		bu2.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				jfadd.dispose();
			}
		});
		bu3.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				
				if(!(jt1.getText().replaceAll(" +", "").equals(""))&&!(jt3.getText().replaceAll(" +", "").equals("")))
				{
					if(to.stringtoarray(jt3.getText())[99]!=1)
					{
						att.setName(jt1.getText());
						att.setIntroduction(jt2.getText());
						number=to.stringtoarray(jt3.getText());
						System.out.println(jt3.getText());
						dao.add(att, number);
						JOptionPane.showMessageDialog(null,"景点添加成功");
						jfadd.dispose();
					}
					else
					{
						JOptionPane.showMessageDialog(null,"景点路径格式错误");
					}
				}
				else
					{
						JOptionPane.showMessageDialog(null,"景点名称和路径不能为空");
					}
				
			}
		});
	}
	
	/**查询最短路径*/
	public void pathAttUi()
	{
		this.judge=2;
		JFrame jf=new JFrame("查询最短路径");
		JPanel jp=new JPanel();
		JLabel jl1=new JLabel("请选择初始点");
		JLabel jl2=new JLabel("请选择目的地");
		JLabel jl3=new JLabel("最短路径为");
		bupath1=new JButton("确定");
		bupath2=new JButton("取消");
		jt4=new JTextArea();
		int num=dao.returnnum();
		int i=0;
		String[] str=new String[num];
		List<AttractionModel> list=new ArrayList<>();
		list=dao.ergodic();
		for(AttractionModel att:list)
		{
			if(!att.getName().equals("已删除"))
			{
				str[i]=att.getName();
				System.out.println(str[i]);
				i++;
			}
		}
		jc1=new JComboBox(str);
		jc2=new JComboBox(str);
		jp.add(jl1);
		jp.add(jl2);
		jp.add(jl3);
		jp.add(jc1);
		jp.add(jc2);
		jp.add(jt4);
		jp.add(bupath1);
		jp.add(bupath2);
		jp.setLayout(null);
		bupath1.setBounds(350,40,100,30);
		bupath2.setBounds(350,85,100,30);
		jl1.setFont(new Font("黑体", Font.BOLD, 17));
		jl2.setFont(new Font("黑体", Font.BOLD, 17));
		jl3.setFont(new Font("黑体", Font.BOLD, 17));
		jl1.setBounds(50,10,300,100);
		jl2.setBounds(50,50,300,100);
		jl3.setBounds(50, 100, 300, 100);
		jc1.setBounds(170,40,140,30);
		jc2.setBounds(170,85,140,30);
		jt4.setBounds(50,170,400,250);
		jt4.setLineWrap(true);
		jf.add(jp);
		jf.setBounds(600, 200, 500, 500);
		jf.setVisible(true);
		
		//关闭窗口
		bupath2.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				jf.dispose();
			}
		});
		jc1.addItemListener(this);
		jc2.addItemListener(this);
	}
	
	public static void main(String[] args) {
		AttractionView test=new AttractionView();
		test.modifyAttUi();
	}
	
}
