package marioTest;

import java.beans.Statement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;

public class MySQLjdbc {
	public static void main(String args[]) {
	    try {
	      Class.forName("com.mysql.cj.jdbc.Driver");     //����MYSQL JDBC��������   
	      //Class.forName("org.gjt.mm.mysql.Driver");
	     System.out.println("Success loading Mysql Driver!");
	    }catch (Exception e) {
	      System.out.print("Error loading Mysql Driver!");
	      e.printStackTrace();
	    }
	    try {
	      Connection connect = DriverManager.getConnection("jdbc:mysql://localhost:3306/Mario?serverTimezone=UTC","root","979988");
	           //����URLΪ   jdbc:mysql//��������ַ/���ݿ���  �������2�������ֱ��ǵ�½�û���������

	      java.sql.Statement stmt = connect.createStatement();
	      System.out.println("Success connect Mysql server!");

	      ResultSet rs = ((java.sql.Statement) stmt).executeQuery("select * from user");
	                                                              //user Ϊ��������
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
