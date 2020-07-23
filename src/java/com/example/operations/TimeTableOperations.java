/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.operations;
import com.example.db.DBConnect;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 *
 * @author basava
 */
public class TimeTableOperations {
    JSONObject timeTableData;
    JSONObject outObject = new JSONObject();
    JSONArray array = new JSONArray();
    DBConnect con=new  DBConnect();
    CallableStatement callable=null;
    ResultSet rs=null;


    public TimeTableOperations(JSONObject timeTableData) {
        this.timeTableData = timeTableData;
    }

    
    public void closeDBConnection()
    {
        try{
            callable.close();
            }catch(Exception e){
                e.printStackTrace();
            }
    }
    public void printSuccessStatus(JSONArray array){
      try{
          outObject.put("Status", "Success");
          outObject.put("Message", "Data found..");
          outObject.put("Result", array);
      }catch(Exception e){
          e.printStackTrace();
      }
  }
  
  
  public  void printFaliureStatus(JSONArray array){
      try{
          outObject.put("Status", "Failure");
          outObject.put("Message", "Data not found..");
          outObject.put("Result", array);
      }catch(Exception e ){
          e.printStackTrace();
      }
  }
   public void getTimeTabledataLater(){
         try{
             do
               {
                    JSONObject object = new JSONObject();
                           // String strDay = rs.getString("Day");
                            String strPeriod = rs.getString("Period");
                            String strSubject = rs.getString("Subject");
                            String strStartTime = rs.getString("Time");
                            String strClass = rs.getString("Class");
                            
                           // object.put("Day", strDay);
                            object.put("Period", strPeriod);
                            object.put("Subject", strSubject);
                            object.put("StartTime", strStartTime);
                            object.put("Class", strClass);
                            
                            array.put(object);
               }while(rs.next());
    }catch(Exception e){
        e.printStackTrace();
      }
    }
    public void getTimeTabledata(){
         try{
             do
               {
                    JSONObject object = new JSONObject();
                           // String strDay = rs.getString("Day");
                            String strPeriod = rs.getString("Period");
                            String strSubject = rs.getString("Subject");
                            String strStartTime = rs.getString("Time");
                            String strClass = rs.getString("Class");
                            
                           // object.put("Day", strDay);
                            object.put("Period", strPeriod);
                            object.put("Subject", strSubject);
                            object.put("StartTime", strStartTime);
                            object.put("Class", strClass);
                            
                            array.put(object);
               }while(rs.next());
    }catch(Exception e){
        e.printStackTrace();
      }
    }
    
    public String timetableDisplay_ClassWise()
    {
        
           try
           {
               

            String strStudentId = timeTableData.getString("StudentId");
            int studId = Integer.parseInt(strStudentId);
            String strDayId= timeTableData.getString("dayId");
            int dayId=Integer.parseInt(strDayId);

        String sql= " CALL `Sp_and_Get_timetable_Classwise`(?,?);";
        
            callable= con.getConnection().prepareCall(sql);
           callable.setInt(1, studId);
           callable.setInt(2, dayId);
           
           rs = callable.executeQuery();
          
           if(rs.first())
           {
                 getTimeTabledataLater();
                if(array.length()!=0)
                 printSuccessStatus(array);
                else{
                    printFaliureStatus(array);
                }
           }
           
          else {
                        printFaliureStatus(array);
                }
            
            return outObject.toString();
        }catch(Exception ee) {
            String exce = ee.toString();
            String exc = "{\"Status\":\"Exception\" ,  \"Message\":\""+exce+"\" ,"
                    + " \"Result\":[]}";
            return exc;    
        }finally{
            closeDBConnection();
        }
    }


    public String TimetableDisplay_Staffwise()
    {
        
           try
           {
               
           // JSONObject timeTableData = new JSONObject(timeTableData);
            String staffid = timeTableData.getString("StaffId");
            int stfId = Integer.parseInt(staffid);
            String strDayId= timeTableData.getString("dayId");
            int dayId=Integer.parseInt(strDayId);
            String sql = " CALL `sp_and_get_staff_timetable`(?,?);";
            
            callable = con.getConnection().prepareCall(sql);
            callable.setInt(1,stfId);
            callable.setInt(2,dayId);
            rs = callable.executeQuery();
           if(rs.first())
           {
                 getTimeTabledata();
                if(array.length()!=0)
                 printSuccessStatus(array);
                else{
                    printFaliureStatus(array);
                }
                 
           }
           
          else {
                        printFaliureStatus(array);
                }
            
            return outObject.toString();
        }catch(Exception ee) {
            String exce = ee.toString();
            String exc = "{\"Status\":\"Exception\" ,  \"Message\":\""+exce+"\" ,"
                    + " \"Result\":[]}";
            return exc;    
        }finally{
            closeDBConnection();
        }
    }
    public String getStaffClassWiseTimetable(){
        try{
         
            int cls = Integer.parseInt(timeTableData.getString("ClassId"));
            
           int dayId= Integer.parseInt(timeTableData.getString("dayId"));
           
            
            String sql="CALL `sp_and_get_staff_classwise_timetable`(?,?);";
            callable= con.getConnection().prepareCall(sql);
           callable.setInt(1, cls);
           callable.setInt(2, dayId);
          
            rs = callable.executeQuery();
          
           if(rs.first())
           {
                 getTimeTabledata();
                printSuccessStatus(array);
           }
           
          else {
                        printFaliureStatus(array);
                }
            
            return outObject.toString();
        }catch(Exception ee) {
            String exce = ee.toString();
            String exc = "{\"Status\":\"Exception\" ,  \"Message\":\""+exce+"\" ,"
                    + " \"Result\":[]}";
            return exc;    
        }finally{
            closeDBConnection();
        }
} 
    public  String getStaffTimetable(){
        try{
         
            int stfId = Integer.parseInt(timeTableData.getString("StaffId"));
            
           int dayId= Integer.parseInt(timeTableData.getString("dayId"));
           
            
            String sql="CALL `sp_and_get_staff_timetable`(?,?);";
            callable= con.getConnection().prepareCall(sql);
           callable.setInt(1, stfId);
           callable.setInt(2, dayId);
          
            rs = callable.executeQuery();
          
           if(rs.first())
           {
                 getTimeTabledata();
                printSuccessStatus(array);
           }
           
          else {
                        printFaliureStatus(array);
                }
            
            return outObject.toString();
        }catch(Exception ee) {
            String exce = ee.toString();
            String exc = "{\"Status\":\"Exception\" ,  \"Message\":\""+exce+"\" ,"
                    + " \"Result\":[]}";
            return exc;    
        }finally{
            closeDBConnection();
        }
    }
}
