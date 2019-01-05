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
  
public class UI extends JFrame implements ActionListener  
{  
  
	
         //定义组件  
        public JButton jb1,jb2,jb3,jb4,jb5,jb6,jb7=null;  
        public JPanel jp1,jp2,jp3,jp4,jp5,jp6,jp7,jp8=null;  
        public JLabel jlb1,jlb2,jlb3,jlb4=null; 
        public JTextField jtf1 = null;
        public JTextField jtf2 = null; 
        
     // 定义组件
        private JScrollPane scpDemo;//滚动轴
        private JTableHeader jth;
        private JTable tabDemo;                                                                                                           
  
       /* public static void main(String[] args){
        	UI ui = new UI ();
        }*/
        
      //图书库各项数据
    	static String bookID;
    	static String bookName;
    	static String publishHouse;
    	static String author;
    	static String Price;
    	static String Borrowed;
    	static String userword1;
    	
    	
    	static Connection ct = null;
    	PreparedStatement ps = null;
    	ResultSet rs = null;
    	
        //这里可以设置数据库名称
        private final static String URL = "jdbc:sqlserver://localhost:1433;DatabaseName=Library";
        private static final String USER="sa";
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
        
        //构造函数  
        public  UI(String userword)    //不能申明为void!!!!!否则弹不出新界面  
        {  
        	
            userword1 = userword;
        	//创建组件  
            jb1=new JButton("查询全部图书");  
            jb2=new JButton("按书号查询图书"); 
            jb3=new JButton("按书名查询图书");
            jb4=new JButton("查询借阅图书");
            jb5=new JButton("修改个人信息");
            jb6=new JButton("借书");
            jb7=new JButton("还书");
            
            this.scpDemo = new JScrollPane();
            this.scpDemo.setBounds(100,300,600,500);
            
            jb1.addActionListener(this);
            jb2.addActionListener(this);
            jb3.addActionListener(this);
            jb4.addActionListener(this);
            jb5.addActionListener(this);
            jb6.addActionListener(this);
            jb7.addActionListener(this);
            
            
            jtf1=new JTextField(10);
            jtf2=new JTextField(10);
            
            jp1=new JPanel();           
            jp2=new JPanel();  
            jp3=new JPanel();  
            jp4=new JPanel();
            jp5=new JPanel();
            jp6=new JPanel();
            jp7=new JPanel();
            jp8=new JPanel();
            
            scpDemo.setOpaque(false);

            jp1.setOpaque(false) ;
            jp2.setOpaque(false) ;
            jp3.setOpaque(false) ;
            jp4.setOpaque(false) ;
            jp5.setOpaque(false) ;
            jp6.setOpaque(false) ;
            jp7.setOpaque(false) ;
            jp8.setOpaque(false) ;
              
            jlb1=new JLabel("欢迎使用图书馆管理系统");  
//            jlb2=new JLabel("读者号");  

            jp1.add(jlb1);  
            //jp1.add(jlb2);  
            jp2.add(jb1);  
             
            jp3.add(jtf1);              
            jp3.add(jb2);  
          
            jp4.add(jtf2);            
            jp4.add(jb3);

            jp5.add(jb4);
            
            jp6.add(jb5);
            
            jp7.add(jb6);
            
            jp8.add(jb7);
              
            ImageIcon img = new ImageIcon("D:\\照片\\高清壁纸\\2.jpg"); 
            JLabel jl_bg = new JLabel(img); //背景
            jl_bg.setBounds(0, 0, 600, 800); //设置位置和大小，先setLayout(null)一下。  
            this.getLayeredPane().add(jl_bg, new Integer(Integer.MIN_VALUE));  
            ((JPanel)this.getContentPane()).setOpaque(false); //设置透明  

            
            this.add(jp1);  
            this.add(jp2);  
            this.add(jp3);  
            this.add(jp4);
            this.add(jp5);
            this.add(jp6);
            this.add(jp7);
            this.add(jp8);
            
         // 将组件加入到窗体中
            add(this.scpDemo);

          
            //设置布局管理器  
            this.setLayout(new GridLayout(9,1,25,8));  
            this.setTitle("读者界面");  
            this.setSize(500,800);  
            this.setLocation(800, 150);       
            this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  
            this.setVisible(true);  
  
}  

        public void actionPerformed(ActionEvent e){
        	if(e.getActionCommand()=="查询全部图书")  
            {    
        		try {  
                    ct = getConnection();//实例化
                    ps=ct.prepareStatement("select * from book ");  
                    rs=ps.executeQuery();  
                    //循环取出  
                    int count = 0;
                    while(rs.next()){count++;}
                    
                    rs=ps.executeQuery(); 
                    Object[][] info = new Object[count][6];
                       count = 0;
                    while(rs.next()){  
                    	  //System.out.println(count);
                         bookID=rs.getString(1);  
                         bookName=rs.getString(2); 
                         publishHouse=rs.getString(3);
                         author=rs.getString(4);
                         Price=rs.getString(5);
                         Borrowed=rs.getString(6);
                      
                         info[count][0] = bookID;
                         info[count][1] = bookName;
                         info[count][2] = publishHouse;                        
                         info[count][3] = author;
                         info[count][4] = Price;
                         info[count][5] = Borrowed;
                         
                         count++;
                         // 定义表头
                         String[] title = {"书号","书名","出版社","作者","单价","借阅状态"};
                         // 创建JTable
                         this.tabDemo = new JTable(info,title);
                         // 显示表头
                         this.jth = this.tabDemo.getTableHeader();
                         // 将JTable加入到带滚动条的面板中
                         this.scpDemo.getViewport().add(tabDemo); 
  
                    }  
                } catch (SQLException e1) {  
                    // TODO Auto-generated catch block  
                    e1.printStackTrace();  
                }  
            }else if(e.getActionCommand()=="按书号查询图书"){      
            	try {  
                    ct = getConnection();//实例化
                    ps=ct.prepareStatement("select * from book");  
                    rs=ps.executeQuery();  
                    //循环取出  
                    int count = 0;
                    while(rs.next()){count++;}
                    
                    rs=ps.executeQuery(); 
                    Object[][] info = new Object[count][6];
                       count = 0;
                    while(rs.next()){ 
 
                         bookID=rs.getString(1);  
                         bookName=rs.getString(2); 
                         publishHouse=rs.getString(3);
                         author=rs.getString(4);
                         Price=rs.getString(5);
                         Borrowed=rs.getString(6);
                         bookID= bookID.replaceAll(" ", "");
                           if(bookID.equals(jtf1.getText())){
                         info[count][0] = bookID;
                         info[count][1] = bookName;
                         info[count][2] = publishHouse;                        
                         info[count][3] = author;
                         info[count][4] = Price;
                         info[count][5] = Borrowed;
                           count++;}
                           
                         // 定义表头
                         String[] title = {"书号","书名","出版社","作者","单价","借阅状态"};
                         // 创建JTable
                         this.tabDemo = new JTable(info,title);
                         // 显示表头
                         this.jth = this.tabDemo.getTableHeader();
                         // 将JTable加入到带滚动条的面板中
                         this.scpDemo.getViewport().add(tabDemo); 
                        //System.out.println("成功获取到密码和用户名from数据库");  
                        //System.out.println(bookID+bookName+publishHouse+author+Price+Borrowed);   
                    }  

                } catch (SQLException e1) {  
                    // TODO Auto-generated catch block  
                    e1.printStackTrace();  
                }  
            
            }else if(e.getActionCommand()=="按书名查询图书"){      
            	try {  
                    ct = getConnection();//实例化
                    ps=ct.prepareStatement("select * from book");  
                    rs=ps.executeQuery();  
                    //循环取出  
                    int count = 0;
                    while(rs.next()){count++;}
                    
                    rs=ps.executeQuery(); 
                    Object[][] info = new Object[count][6];
                       count = 0;
                    while(rs.next()){ 
 
                    	  
                         bookID=rs.getString(1);  
                         bookName=rs.getString(2); 
                         publishHouse=rs.getString(3);
                         author=rs.getString(4);
                         Price=rs.getString(5);
                         Borrowed=rs.getString(6);
                         bookName= bookName.replaceAll(" ", "");
                           if(bookName.equals(jtf2.getText())){
                         info[count][0] = bookID;
                         info[count][1] = bookName;
                         info[count][2] = publishHouse;                        
                         info[count][3] = author;
                         info[count][4] = Price;
                         info[count][5] = Borrowed;
                           count++;}
                           
                         // 定义表头
                         String[] title = {"书号","书名","出版社","作者","单价","借阅状态"};
                         // 创建JTable
                         this.tabDemo = new JTable(info,title);
                         // 显示表头
                         this.jth = this.tabDemo.getTableHeader();
                         // 将JTable加入到带滚动条的面板中
                         this.scpDemo.getViewport().add(tabDemo); 
                        //System.out.println("成功获取到密码和用户名from数据库");  
                        //System.out.println(bookID+bookName+publishHouse+author+Price+Borrowed);   
                    }  

                } catch (SQLException e1) {                   
                    e1.printStackTrace();  
                  }  
                }else if(e.getActionCommand()=="查询借阅图书"){
            	     UIReader1 ui1 = new UIReader1(userword1);
            	     
                 }else if(e.getActionCommand()=="修改个人信息"){
                	 UIReader2 ui2 = new UIReader2(userword1);
                	
                 }else if(e.getActionCommand()=="借书"){
                	 
                	 UIReader3 ui3 = new UIReader3(userword1);
                 }else if(e.getActionCommand()=="还书"){
                	 UIReader4 ui4 = new UIReader4(userword1);
                 }
        	
        }
}  
