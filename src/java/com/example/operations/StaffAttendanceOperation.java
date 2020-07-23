/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.operations;
import com.example.db.DBConnect;
import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 *
 * @author basava
 */
public class StaffAttendanceOperation {
    
     JSONObject outObject = new JSONObject();
    JSONObject staffAttendanceData;
    JSONArray array = new JSONArray();
     DBConnect con = new DBConnect();
     CallableStatement callable=null;
     ResultSet rs=null;
    public StaffAttendanceOperation(JSONObject staffAttendanceData) {
        this.staffAttendanceData=staffAttendanceData;
    }
    public void closeDBConnection()
    {
        try{
            callable.close();
            }catch(Exception e){
                e.printStackTrace();
            }
    }
    public String StaffAttendance_Insert()
    {
       
           try
           {
               
           // JSONObject staffAttendanceData = new JSONObject(staffAttendanceData);
            String schoolId = staffAttendanceData.getString("SchoolId");
            int sclId = Integer.parseInt(schoolId);
            String academicYearId = staffAttendanceData.getString("AcademicYearId");
             int acmdid = Integer.parseInt(academicYearId);
             String date = staffAttendanceData.getString("Date");
             SimpleDateFormat formatterentrdt = new SimpleDateFormat("yyyy-MM-dd HH:mm");
             java.util.Date atndDate= formatterentrdt.parse(date);
             String staffId = staffAttendanceData.getString("StaffId");
             int stfid = Integer.parseInt(staffId);
             String status = staffAttendanceData.getString("Status");
             int sts = Integer.parseInt(status);
             String reason = staffAttendanceData.getString("Reason");
                   
            String sql = "CALL `StaffAttendance_Insert`(?, ?, ?, ?, ?, ?)";
           
            callable = con.getConnection().prepareCall(sql);
            callable.setInt(1,sclId);
            callable.setInt(2,acmdid);
             callable.setDate(3, new java.sql.Date(atndDate.getTime()));
            callable.setInt(4, stfid);
            callable.setInt(5, sts); 
            callable.setString(6, reason);
            
                        
            int effRow=callable.executeUpdate();
            if(effRow>0)
            {
                 outObject.put("Status", "Success");
                 outObject.put("Message", "inserted sucessfully");
            }else{
                 outObject.put("Status", "fail");
                 outObject.put("Message", " not inserted ");
            }
            
          return outObject.toString();
        }catch(Exception ee) {
            String exce = ee.toString();
            String exc = "{\"Status\":\"Exception\" ,  \"Message\":\""+exce+"\"}";
            return exc;    
        }finally{
            closeDBConnection();
        }
    }
    public String AttendenceDisplay_Staffwise()
    {
        
           try
           {
               
  
            String staffid = staffAttendanceData.getString("StaffId");
            int stfId = Integer.parseInt(staffid);
            String attendanceDate = staffAttendanceData.getString("Date"); 
           SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
           java.util.Date date= formatter.parse(attendanceDate);
            String sql = " CALL `AttendenceDisplay_Staffwise`(?, ?)";
            
            callable = con.getConnection().prepareCall(sql);
            callable.setInt(1,stfId);
            callable.setDate(2, new java.sql.Date(date.getTime()));
           
            rs = callable.executeQuery();
           if(rs.first())
           {
               do
               {
                    JSONObject object = new JSONObject();
                            String strStaffId = rs.getString("StaffId");
                            String strStaffName = rs.getString("StaffName");
                            String strDate = rs.getString("Date");
                            String strStatus = rs.getString("Status");
                            object.put("StaffId", strStaffId);
                            object.put("Name", strStaffName);
                            object.put("Date", strDate);
                            object.put("Status", strStatus);
                            array.put(object);
               }while(rs.next());
                outObject.put("Status", "Success");
                outObject.put("Message", "Data found..");
                outObject.put("Result", array);
           }
           
          else {
                        outObject.put("Status", "Failure");
                        outObject.put("Message", "Data not found..");
                        outObject.put("Result", array);
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
