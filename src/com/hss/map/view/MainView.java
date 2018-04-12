package com.hss.map.view;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class MainView extends JFrame{
	
	AttractionView view=new AttractionView(); 
	public void mainUi(String name,int adm)
	{
		JFrame jframe=new JFrame("西安邮电大学导航,欢迎您"+name);
		JMenuBar jmenu=new JMenuBar();
		JMenu buttonSearch=new JMenu("		路线查询	");
		JMenu buttonAttractions=new JMenu("	景点操作	");
		JMenu buttonToplogy=new JMenu("	景点拓扑图	");
		JMenu buttonexit=new JMenu("退出");
		JMenuItem jmenuitem1=new JMenuItem("查询路线");
		JMenuItem jmenuitem2=new JMenuItem("景点介绍");
		JMenuItem jmenuitem3=new JMenuItem("添加景点");
		JMenuItem jmenuitem4=new JMenuItem("删除景点");
		JMenuItem jmenuitem5=new JMenuItem("修改景点信息");
		JMenuItem jmenuitem6=new JMenuItem("景点拓扑图");
		JMenuItem jmenuitem7=new JMenuItem("退出");
		buttonSearch.add(jmenuitem1);
		buttonAttractions.add(jmenuitem2);
		buttonAttractions.add(jmenuitem3);
		buttonAttractions.add(jmenuitem4);
		buttonAttractions.add(jmenuitem5);
		buttonToplogy.add(jmenuitem6);
		buttonexit.add(jmenuitem7);
		JPanel jpanel=new JPanel()
		{
				public void paintComponent(Graphics g)
				{
					ImageIcon icon =new ImageIcon("src/image/009.jpg");
					Image img=icon.getImage();
					g.drawImage(img, 0, 0, icon.getIconWidth(),icon.getIconHeight(),icon.getImageObserver());
					jframe.setSize(icon.getIconWidth(), icon.getIconHeight()+150);
				}
		};
		jframe.add(jpanel);
		jframe.setJMenuBar(jmenu);
		jmenu.add(buttonSearch);
		jmenu.add(buttonAttractions);
		jmenu.add(buttonToplogy);
		jmenu.add(buttonexit);
		jframe.setVisible(true);
		jframe.setDefaultCloseOperation(jframe.EXIT_ON_CLOSE);
		jframe.setBounds(300, 00, 1000, 1000);
		
		/**查询路线*/
		jmenuitem1.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				
				view.pathAttUi();
			}
		});
		/**景点介绍*/
		jmenuitem2.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				
				view.ergodicAttUi();
			}
		});
		/**添加景点*/
		jmenuitem3.addActionListener(new ActionListener() {
	
			public void actionPerformed(ActionEvent e) {
				if(adm==1)
				view.addAttUi();
				else
				JOptionPane.showMessageDialog(null,"对不起您没有权限");
			}
		});
		/**删除景点*/
		jmenuitem4.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				if(adm==1)
				view.delAttUi();
				else
					JOptionPane.showMessageDialog(null,"对不起您没有权限");
			}
		});
		/**修改景点信息*/
		jmenuitem5.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				if(adm==1)
				view.modifyAttUi();
				else
					JOptionPane.showMessageDialog(null,"对不起您没有权限");
					
			}
		});
		/**景点拓扑图*/
		jmenuitem6.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				
			}
		});
		//退出系统
		jmenuitem7 .addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				System.out.println("退出系统");
				System.exit(0);
			}
		});
		
	}
	
	
}
