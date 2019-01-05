package pkg;

import java.awt.*;  
import java.awt.event.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.table.JTableHeader;
import javax.swing.*;  
  
public class UI2 extends JFrame implements ActionListener{

	public JButton jb1,jb2,jb3,jb4 = null;
	public JPanel  jp1,jp2,jp3,jp4 = null;
	
	
	public UI2(){
		jb1 = new JButton("修改书籍信息");
		jb2 = new JButton("修改读者信息");
		jb3 = new JButton("修改管理员信息");
		jb4 = new JButton("查询借书记录");
				
		jp1 = new JPanel();
		jp2 = new JPanel();
		jp3 = new JPanel();
		jp4 = new JPanel();
		
		jb1.addActionListener(this);
		jb2.addActionListener(this);
		jb3.addActionListener(this);
		jb4.addActionListener(this);
		
		jp1.add(jb1);
		jp2.add(jb2);
		jp3.add(jb3);
		jp4.add(jb4);
		
		this.add(jp1);
		this.add(jp2);
		this.add(jp3);
		this.add(jp4);
		
		this.setLayout(new GridLayout(4,1,25,8));  
		this.setTitle("管理员界面");
		this.setSize(300,500);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO 自动生成的方法存根
		
	}
	
}
