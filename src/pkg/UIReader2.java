  package pkg;
//修改个人信息
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.sql.*;

    public  class UIReader2 extends JFrame implements ActionListener{
         
	          JLabel jlb1,jlb2,jlb3,jlb4,jlb5,jlb6,jlb7 = null;
	          JPanel jp1,jp2,jp3,jp4,jp5,jp6,jp7,jp8= null;
	          JButton jb1 = null;
	          JTextField jtf1,jtf2,jtf3 = null;
	          JPasswordField jpf1,jpf2,jpf3 = null;
	          JComboBox jcb;
	          
	          static String userword;
	      	  static String pwd;
	      	  static String userword2;
	          
	          static Connection ct = null;
	          PreparedStatement ps =null;
	          ResultSet rs = null;
	          
	          private final static String URL = "jdbc:sqlserver://localhost:1433;DatabaseName=Library";
	          private static final String USER = "sa";
	          private static final String PASSWORD="123";
	          
	          private static Connection conn=null;
	          //静态代码块（将加载驱动、连接数据库放入静态块中）
	          static{
	              try {
	                  //1.加载驱动程序
	                  Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
	                  //2.获得数据库的连接
	                  conn=(Connection)DriverManager.getConnection(URL,USER,PASSWORD);
	              } catch (ClassNotFoundException e) {
	                  e.printStackTrace();
	              } catch (SQLException e) {
	                  e.printStackTrace();
	              }
	          }
	          
	          //对外提供一个方法来获取数据库连接
	          public static Connection getConnection(){
	              return conn;
	          }

                  
    
 public UIReader2(String userword1){
	 
	               userword2 = userword1;
            	   String str[] = {"男","女"};
            	   
            	   jcb = new JComboBox(str);
            	   
            	   jlb1 = new JLabel ("请输入修改后的信息");           	   
            	   jlb3 = new JLabel ("姓名");
            	   jlb4 = new JLabel ("性别");
            	   jlb5 = new JLabel ("新密码");
            	   jlb6 = new JLabel ("原始密码");
            	   jlb7 = new JLabel("再次输入");
            	   
            	   jp1 = new JPanel();
            	   jp8 = new JPanel();
            	   jp3 = new JPanel();
            	   jp4 = new JPanel();
            	   jp5 = new JPanel();
            	   jp6 = new JPanel();
            	   jp7 = new JPanel();
            	   
            	   jtf1 = new JTextField(10); 
            	   jtf2 = new JTextField(10);
            	   jtf3 = new JTextField(10);
            	   
            	   jpf1 = new JPasswordField(10);  
            	   jpf2 = new JPasswordField(10);
            	   jpf3 = new JPasswordField(10);
            	   
            	   jb1 = new JButton("确认");
            	   
            	   jp1.add(jlb1);
            	   
            	   jp8.add(jlb6);
            	   jp8.add(jpf3);
            	   
            	   jp3.add(jlb3);
            	   jp3.add(jtf2);
            	   
            	   jp4.add(jlb4);
            	   jp4.add(jcb);
            	   
            	   jp5.add(jlb5);
            	   jp5.add(jpf1);
            	   
            	   jp6.add(jlb7);
            	   jp6.add(jpf2);
            	   
            	   jp7.add(jb1);
            	   
            	   jb1.addActionListener(this);
            	   jcb.addActionListener(this);
            	   jcb.setEditable(true);
            	   
            	   this.add(jp1);
            	   this.add(jp8);
            	   this.add(jp3);
            	   this.add(jp4);
            	   this.add(jp5);
            	   this.add(jp6);
            	   this.add(jp7);
            	  
            	   
            	   this.setLayout(new GridLayout(7,1,50,20));  
                   this.setTitle("图书馆管理系统");  
                   this.setSize(400,650);  
                   this.setLocation(800, 180);       
                   this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);  
                   this.setVisible(true);  
               }
               
               public void actionPerformed(ActionEvent e){
            	   if(e.getActionCommand()=="确认"){
            		   try{
            			   //System.out.println(jcb.getSelectedItem());
            			   String s1 = userword2;
            			   String s2 = jtf2.getText();
            			   String s3 = (String) jcb.getSelectedItem();
            			   String s4 = jpf1.getText();
            			   String s5 = jpf2.getText();
            			   int flag = 0;
            			   
            			    ct = getConnection();//实例化
            			    ps=ct.prepareStatement("select * from reader");                                    
                            rs=ps.executeQuery();  
                           //循环取出  
                           while(rs.next()){  
                               //将学生的用户名和密码取出  
                                userword=rs.getString(1);  
                                pwd=rs.getString(4);   
                               userword = userword.replaceAll(" ", "");
                               pwd= pwd.replaceAll(" ", "");
                               if(userword.equals(userword2)&&pwd.equals(jpf3.getText())) {flag = 1;break;}  
                           }  
            			   if(flag == 1){
            			   String sql = "update reader set readerName =?,性别 =? ,passWord =? where readerID =?";
            					   PreparedStatement pStmt = conn.prepareStatement(sql);            					
            					   pStmt.setString(1,s2);//向名字字段填入数据
            					   pStmt.setString(2,s3);
            					   pStmt.setString(4,s1);
            					   if(s5.equals(s4))
            						   pStmt.setString(3,s4);
            					   else {JOptionPane.showMessageDialog(null, "两次密码不一致 ", "修改失败 ", JOptionPane.ERROR_MESSAGE);
            					   pStmt.setString(3,pwd);
            					   }
            					   pStmt.executeUpdate();
            					   
            					   JOptionPane.showMessageDialog(null,"修改成功");
            					   dispose();  
            			   }
            			   else JOptionPane.showMessageDialog(null, "用户名或密码错误 ", "修改失败 ", JOptionPane.ERROR_MESSAGE);
            		   }catch(SQLException e1) {  
                           // TODO Auto-generated catch block  
                           e1.printStackTrace();  
                       }  
            		
            	   }
            	   
               }
        	 
         
}        
