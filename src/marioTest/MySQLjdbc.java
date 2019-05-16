package marioTest;

import java.beans.Statement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;

public class MySQLjdbc {
	public static void main(String args[]) {
	    try {
	      Class.forName("com.mysql.cj.jdbc.Driver");     //加载MYSQL JDBC驱动程序   
	      //Class.forName("org.gjt.mm.mysql.Driver");
	     System.out.println("Success loading Mysql Driver!");
	    }catch (Exception e) {
	      System.out.print("Error loading Mysql Driver!");
	      e.printStackTrace();
	    }
	    try {
	      Connection connect = DriverManager.getConnection("jdbc:mysql://localhost:3306/Mario?serverTimezone=UTC","root","979988");
	           //连接URL为   jdbc:mysql//服务器地址/数据库名  ，后面的2个参数分别是登陆用户名和密码

	      java.sql.Statement stmt = connect.createStatement();
	      System.out.println("Success connect Mysql server!");

	      ResultSet rs = ((java.sql.Statement) stmt).executeQuery("select * from user");
	                                                              //user 为你表的名称
	while (rs.next()) {
	        System.out.println(rs.getString("name"));
	      }
	    }
	    catch (Exception e) {
	      System.out.print("get data error!");
	      e.printStackTrace();
	    }
	  }

}
