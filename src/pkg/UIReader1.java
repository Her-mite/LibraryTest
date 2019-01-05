package pkg;
//读者查询已借阅图书
import java.awt.*;  
import java.awt.event.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.*;
import javax.swing.table.JTableHeader;  
 
public class UIReader1 extends JFrame implements ActionListener  
{  
  
         //定义组件  
        JButton jb1=null;  
        JPanel jp2=null;  
        
        private JScrollPane scpDemo;
        private JTableHeader jth;
        private JTable tabDemo; 
        
      //  static String bookName;
        static String readerID;
        static String bookID;
        static String borrowDate;
        static String deadLine;
        static int returnFlag;
        static String userwordx;
        
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
        
        public  UIReader1(String userword1)    //不能申明为void!!!!!否则弹不出新界面  
        {  
        	userwordx = userword1;
     
            //创建组件  
            jb1=new JButton("查询");  
         
            jb1.addActionListener(this);
           
            jp2=new JPanel();  
            
            this.scpDemo = new JScrollPane();
            this.scpDemo.setBounds(100,300,600,500);            
         
            jp2.add(jb1);                
            this.add(jp2);  
            
         // 将组件加入到窗体中
            add(this.scpDemo);
              
            //设置布局管理器  
            this.setLayout(new GridLayout(2,1,50,20));  
            this.setTitle("图书馆管理系统");  
            this.setSize(400,250);  
            this.setLocation(800,350);       
            this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);  
            this.setVisible(true);  
  
}  
        @Override  
        public void actionPerformed(ActionEvent e) {  
          if(e.getActionCommand()=="查询"){
        	//创建火箭车  
              try {  
                  ct = getConnection();//实例化
                  ps=ct.prepareStatement("select * from borrow ");                   
                  rs=ps.executeQuery();  
                  
                  int count = 0;
                  Object[][] info = new Object[3][4];
                  //循环取出  
                  while(rs.next()){               
                       readerID=rs.getString(3);  
                       readerID = readerID.replaceAll(" ", "");
                       bookID=rs.getString(2);
                       borrowDate=rs.getString(4);
                       deadLine=rs.getString(5);
                       returnFlag=rs.getInt(6);
                      // bookName= bookName.replaceAll(" ", "");
                       if(readerID.equals(userwordx)&&(returnFlag==0)) {
                    	   info[count][0] = readerID;
                           info[count][1] = bookID;
                           info[count][2] = borrowDate;                        
                           info[count][3] = deadLine;
                             count++;
                       }
                    	   
                    // 定义表头
                       String[] title = {"读者号","书号","借阅时间","期限"};
                       // 创建JTable
                       this.tabDemo = new JTable(info,title);
                       // 显示表头
                       this.jth = this.tabDemo.getTableHeader();
                       // 将JTable加入到带滚动条的面板中
                       this.scpDemo.getViewport().add(tabDemo); 
                      //System.out.println("成功获取到密码和用户名from数据库");  
                    //  System.out.println(readerID);   
                  }

                  
              } catch (SQLException e1) {  
                  // TODO Auto-generated catch block  
                  e1.printStackTrace();  
              }
          }
              
        } 
        
}  