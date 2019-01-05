package pkg;
//借书
import java.awt.*;
import javax.swing.*;
import javax.swing.table.JTableHeader;
import java.sql.*;
import java.awt.event.*;
import java.lang.*;

public class UIReader3 extends JFrame implements ActionListener{
    JButton jb1  = null;
    JLabel  jlb1,jlb2,jlb3 = null;
    JPanel  jp1,jp2,jp3,jp4  = null;
    JTextField jtf1,jtf2 =null;
    JPasswordField jpf1 = null;
    
  //  static String bookName;
    static String readerID;
    static String bookID;
    static String pwd;
    static String borrowDate;
    static String deadLine;
    static String userword3;
    
    
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
    
    	public  void clear()  {  
    		//jtf1.setText(""); 
    		jtf2.setText("");
    		jpf1.setText("");  
    	} 
        
    	public UIReader3(String userword1){
    		userword3 = userword1;
    		jb1 = new JButton("确认借书");
    		jb1.addActionListener(this);
	
    		//jlb1 = new JLabel("读者号");
    		jlb2 = new JLabel("借阅书号");
    		jlb3 = new JLabel("密码");
		
    		jp1 = new JPanel();
    		jp2 = new JPanel();
    		jp3 = new JPanel();
    		jp4 = new JPanel();
	
    		//jtf1 = new JTextField(10);
    		jtf2 = new JTextField(10);
    		jpf1 = new JPasswordField(10);
	
    		//jp1.add(jlb1);
    		///jp1.add(jtf1);
	
    		jp2.add(jlb2);
    		jp2.add(jtf2);
	
    		jp3.add(jlb3);
    		jp3.add(jpf1);
	
    		jp4.add(jb1);
    		
    		//this.add(jp1);
    		this.add(jp2);
    		this.add(jp3);
    		this.add(jp4); 
    		
    		this.setLayout(new GridLayout (4,1,50,50));
    		this.setTitle("图书借阅");
    		this.setSize(400,500);
    		this.setLocation(800,200);
    		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    		this.setVisible(true);
    	}

    	public void actionPerformed(ActionEvent e){
    		if(e.getActionCommand()=="确认借书"){
    			  try{
    				 // String s1 = jtf1.getText();
    				  String s2 = jtf2.getText();
    				  String s3 = jpf1.getText();
    				  Date d=new Date(System.currentTimeMillis());
       			    ct = getConnection();//实例化
       			    ps=ct.prepareStatement("select * from reader");                                    
                    rs=ps.executeQuery();  
                    int flag = 0;
                      //循环取出  
                      while(rs.next()){  
                          //将学生的用户名和密码取出  
                           readerID=userword3;  
                           pwd=rs.getString(4);  
                          //System.out.println("成功获取到密码和用户名from数据库");  
                          readerID = readerID.replaceAll(" ", "");
                          pwd= pwd.replaceAll(" ", "");
                          if(readerID.equals(userword3)&&pwd.equals(jpf1.getText())) {flag = 1;break;}
                          //System.out.println(userword+"\t"+pwd+"\t");   
                      } 
                      		if(flag == 0) {JOptionPane.showMessageDialog(null, "用户名或密码错误 ", "借阅失败 ", JOptionPane.ERROR_MESSAGE);clear();}
                     
                        ct = getConnection();
                        ps = ct.prepareStatement("select bookID from book where 是否可借阅 = '可借阅'");
                        rs = ps.executeQuery();
                          int flag2 = 0;
                      while (rs.next()){
                    	  bookID = rs.getString(1);
                    	  bookID = bookID.replaceAll(" ","");
                    	  if(bookID.equals(jtf2.getText())){flag2 = 1;break;}
                      }
                      		if(flag2 == 0) {JOptionPane.showMessageDialog(null, "此书已借出，不可借阅 ", "借阅失败 ", JOptionPane.ERROR_MESSAGE);clear();}
                      
                      	if ((flag == 1)&&(flag2 == 1)){
                      		 String sql = "update book set 是否可借阅 = '不可借阅' where bookID = ?";
      					   PreparedStatement pStmt = conn.prepareStatement(sql);            					
      					   //pStmt.setString(1,"不可借阅");//向名字字段填入数据
      					   pStmt.setString(1, s2);
      					   pStmt.executeUpdate();
      					   
      					  int num = (int)(Math.random()*100);
      					    
      					   String sql2 = "insert into dbo.borrow values(?,?,?,?,?,?) ";  
      					   PreparedStatement pst2 = conn.prepareStatement(sql2,Statement.RETURN_GENERATED_KEYS);  
      					   pst2.setInt(1,num );  
      					   pst2.setString(2, bookID); 
      					   pst2.setString(3, readerID);
      					   pst2.setDate(4,d);
      					   pst2.setInt(5,30);
      					   pst2.setInt(6, 0);
      					   pst2.executeUpdate();  
      					   pst2.close();  
			
      					   
                      		JOptionPane.showMessageDialog(null,"借阅成功");
                      		
                      		dispose(); 
                      	}
       			  
       		   }catch(SQLException e1) {  
                      // TODO Auto-generated catch block  
                      e1.printStackTrace();  
    		}
    	}
    	}

}