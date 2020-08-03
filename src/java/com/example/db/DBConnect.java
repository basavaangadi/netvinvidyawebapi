/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.db;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author LENOVO
 */
public class DBConnect {
//       public static final String DB_NAME = "studentmanagement";
  

/*public static final String DB_NAME = "studentmanagement_production_july";
public static final String DB_USER_NAME = "root";
public static final String DB_PASSWORD = "root123";
public static final String HOST_NAME = "jdbc:mysql://localhost:3306/"+DB_NAME+"?useUnicode=yes&characterEncoding=UTF-8"; */

public static final String DB_NAME = "studentmanagement_production";
  public static final String DB_USER_NAME = "Pmmsskmd";
  public static final String DB_PASSWORD = "P!m@m#s$s%k^m7d*";
public static final String HOST_NAME = "jdbc:mysql://103.120.179.71:4443/"+DB_NAME+"?useUnicode=yes&characterEncoding=UTF-8";


 
  
//public static final String DB_NAME = "studentmanagement_production";
  

  //  public static final String HOST_NAME = "jdbc:mysql:// 192.168.1.4:3306/"+DB_NAME+"?useUnicode=yes&characterEncoding=UTF-8";  
//public static final String HOST_NAME = "jdbc:mysql://127.0.0.1:3306/"+DB_NAME+"?useUnicode=yes&characterEncoding=UTF-8";     

 

//

    Connection connection;
    
    /**
    * Create connection with the data base
    * //
    */
    public DBConnect() {
        
        try {

            Class.forName("com.mysql.jdbc.Driver");
            connection = (Connection) DriverManager.getConnection(HOST_NAME,DB_USER_NAME, DB_PASSWORD);
            //connection = (Connection) DriverManager.getConnection(HOST_NAME+"?useUnicode=yes&characterEncoding=UTF-8",DB_USER_NAME, DB_PASSWORD);
     //       connection = (Connection) DriverManager.getConnection("jdbc:mysql://103.92.235.246:3306/studentmanagement_production?useUnicode=yes&characterEncoding=UTF-8",DB_USER_NAME, DB_PASSWORD);

        }catch(ClassNotFoundException ee){
            
            System.out.println("Where is your MySQL JDBC Driver?");
   ee.printStackTrace();
            Logger.getLogger(DBConnect.class.getName()).log(Level.SEVERE, null, "Exception "+ee);
            return;
        }catch(SQLException  ee){
                System.out.println("Connection Failed! Check output console");
ee.printStackTrace();
                 Logger.getLogger(DBConnect.class.getName()).log(Level.SEVERE, null, "Exception "+ee);
return;
        }
        
        //return connection;
    }
    
    /**
    * insetData 
    * This function insert records into the database based on the input SQL Query 
    * @param sql String
    * @return Integer no of rows affected  
    */
    public int insertData(String sql){
           int ret = 0;
           try {
               if(connection != null){
                   Statement st = (Statement) connection.createStatement();
                   //st.executeQuery("SET NAMES 'UTF8'");
                   ret = st.executeUpdate(sql);
               }
        
           } catch (SQLException ex) {
               Logger.getLogger(DBConnect.class.getName()).log(Level.SEVERE, null, "Exception "+ex);
           }
          return ret;
    }
    
    /**
     * getData
     * This function get the records form database based on the input SQL Query
     * @param sqlQuery String 
     * @return ResultSet
     */
     public ResultSet getData(String sql){
            ResultSet rs = null;
           try {
                Statement st = (Statement) connection.createStatement();
                rs =  (ResultSet) st.executeQuery(sql);
            } catch (SQLException ex) {
                Logger.getLogger(DBConnect.class.getName()).log(Level.SEVERE, null, "Exception "+ex);
            }
            return rs;
     }
   
    /**
    * updateRecord
    * This function Update the records form database based on the input SQL Query
    * @param sql String 
    * @return Integer no of rows affected 
    */
    public int updateRecord(String sql){
           int returnVal = 0;
           try {
               Statement st = (Statement) connection.createStatement();
               returnVal = st.executeUpdate(sql);
           } catch (SQLException ex) {
               Logger.getLogger(DBConnect.class.getName()).log(Level.SEVERE, null, "Exception "+ex);
           }
           return returnVal;
   }
    
    /**
    * deleteRecord
    * This function get the records form database based on the input SQL Query
    * @param sql String 
    * @return Integer no of rows affected 
    */
    public int deleteRecord(String sql){

         int returnVal =0;
            try {
                Statement st = (Statement) connection.createStatement();
                returnVal = st.executeUpdate(sql);
            } catch (SQLException ex) {
               Logger.getLogger(DBConnect.class.getName()).log(Level.SEVERE, null, "Exception "+ex);
            }
         return returnVal;
    } 
    
    /**
    * deleteAllRecordFromTable
    * This function Update the records form database based on the input SQL Query
    * @param tableName String
    * @return boolean success or failure
    */
    public boolean deleteAllRecordFromTable(String tableName){
            boolean value = false;
            String sql = "TRUNCATE "+tableName;
            try {
                Statement st = (Statement) connection.createStatement();
                int acno = st.executeUpdate(sql);
            } catch (SQLException ex) {
               Logger.getLogger(DBConnect.class.getName()).log(Level.SEVERE, null, "Exception "+ex);
            }
            return value;
    }
    
    public Connection getConnection(){
            return this.connection;
    }

    
}
