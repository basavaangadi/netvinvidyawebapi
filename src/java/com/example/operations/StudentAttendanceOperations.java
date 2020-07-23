/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.operations;
import com.example.db.DBConnect;
import com.example.db.SendSms;
import com.example.db.FCM;
import com.example.db.StringUtil;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 *
 * @author basava
 */
public class StudentAttendanceOperations {
    JSONObject studAttendanceData; 
    JSONObject outObject = new JSONObject();
    JSONArray array = new JSONArray();
    DBConnect con = new DBConnect();
    CallableStatement callable=null;
     ResultSet rs=null;
    int effRows,resId;
    FCM fcm= new FCM();
    String strUserToken="";
  Timestamp tmpAttendDate=null;
    public void closeDBConnection()
    {
        try{
            callable.close();
            }catch(Exception e){
                e.printStackTrace();
            }
    }
    
    
    public StudentAttendanceOperations(JSONObject studAttendanceData) {
    this.studAttendanceData=studAttendanceData;
    }
    public void fetchStudentData(JSONObject studAttendanceData,String sql)
    {
    try{ String studentId = studAttendanceData.getString("StudentId");
       int studId = Integer.parseInt(studentId);
       
       callable=con.getConnection().prepareCall(sql);
       callable.setInt(1, studId);
       rs=callable.executeQuery();
    }catch(Exception e){
        e.printStackTrace();
    }
    
    }
    
    public void fetchClassIdWithDate(JSONObject studAttendanceData,String sql)
    {
    try{ 
        int clsId = Integer.parseInt(studAttendanceData.getString("ClassId"));
       String strDate = studAttendanceData.getString("Date");
            SimpleDateFormat ymd=new SimpleDateFormat("yyyy-mm-dd");
            Date atndDateToSQL = null;
            java.sql.Date sqlAtndDate = null;
            if(!(strDate.equals("")))
             {
                 Date attendDateIn = new SimpleDateFormat("dd-MM-yyyy").parse(strDate);
               
                SimpleDateFormat dateFormatToServer = new SimpleDateFormat("yyyy-MM-dd");
                String strAttendDateInFormated = dateFormatToServer.format(attendDateIn);
                
                atndDateToSQL = dateFormatToServer.parse(strAttendDateInFormated);
               
                //SQL Date For Stored Procuder Init
                sqlAtndDate = new java.sql.Date(atndDateToSQL.getTime());
             }
       
       callable=con.getConnection().prepareCall(sql);
       callable.setInt(1, clsId);
       callable.setDate(2, sqlAtndDate);
       rs=callable.executeQuery();
    }catch(Exception e){
        e.printStackTrace();
    }
    
    }
    
    
    public void fetchRequestDetails() {
        try {
            do {
                JSONObject object = new JSONObject();
                String strRollNo = rs.getString("RollNo");
                String strName = rs.getString("StudentName");
                String strFromDate = rs.getString("FromDate");
                String strToDate = rs.getString("ToDate");
                String strClass = rs.getString("Class");
                String strDescription = rs.getString("Description");
                String strClassId = rs.getString("ClassID");
                String strStudentId = rs.getString("StudentID");
                String strStudentRequestId = rs.getString("StudentLeaveRequestID");

                object.put("RollNo", strRollNo);
                object.put("StudentName", strName);
                object.put("Class", strClass);
                object.put("FromDate", strFromDate);
                object.put("ToDate", strToDate);
                object.put("Description", strDescription);
                object.put("ClassId", strClassId);
                object.put("StudentId", strStudentId);
                object.put("StudentRequestId", strStudentRequestId);
                array.put(object);

            } while (rs.next());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    
    
    public void fillStudentAttendance(){
        try{
            do
                {
                   JSONObject object = new JSONObject();
                   String strRollNo = rs.getString("RollNo");
                   String strName = rs.getString("StudentName");
                   String strDate = rs.getString("Date");
                   String strClass = rs.getString("class");
                   String strDay = rs.getString("Day");
                   String strStatus = rs.getString("Status");
                            
                   object.put("RollNo", strRollNo);
                   object.put("StudentName", strName);
                   object.put("Class", strClass);
                   object.put("Date", strDate);
                   object.put("Day", strDay);
                   object.put("Status", strStatus);
               
                   array.put(object);
               
                }while(rs.next());
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    public void fillStudentAttendanceLater(){
        try{
            do
                {
                   JSONObject object = new JSONObject();
                   String strRollNo = rs.getString("RollNo");
                   String strName = rs.getString("StudentName");
                   String strDate = rs.getString("Date");
                   String strClass = rs.getString("class");
                   String strDay = rs.getString("Day");
                   String strStatus = rs.getString("Status");
                            
                   object.put("RollNo", strRollNo);
                   object.put("StudentName", strName);
                   object.put("Class", strClass);
                   object.put("Date", strDate);
                   object.put("Day", strDay);
                   object.put("Status", strStatus);
               
                   array.put(object);
               
                }while(rs.next());
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
    
    public void printFaliureStatus(JSONArray array){
        try{
            outObject.put("Status", "Failure");
            outObject.put("Message", "Data not found..");
            outObject.put("Result", array);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    public void fetchPeriodwiseAttendance(){
        try{
        if(rs.first()){
               
             do
               {
                    JSONObject object = new JSONObject();
                            String strClass = rs.getString("Class");
                            
                            String strRollNo = rs.getString("RollNo");
                            String strStudentName = rs.getString("StudentName");
                            String strDate = rs.getString("Date");
                            String strDay=rs.getString("Day");
                            String strStatus = rs.getString("Status");
                            String strPeriod = rs.getString("Period");
                             object.put("Class", strClass);
                            
                            object.put("StudentName", strStudentName);
                            object.put("RollNo", strRollNo);
                            object.put("Date", strDate);
                            object.put("Status", strStatus);
                            object.put("Day",strDay);
                            object.put("Period", strPeriod);
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
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    
    public void fetchId(JSONObject noticeBoardData,String sql){
    try{
    int  ntcId = Integer.parseInt(noticeBoardData.getString("Id"));
   
       
    callable=con.getConnection().prepareCall(sql);
    callable.setInt(1, ntcId);
    
    rs=callable.executeQuery();
}catch(Exception e){
    e.printStackTrace();
}
}
    
    
    
    public void fetchLeaveDetails(){
        try{
        if(rs.first()){
               
             do
               {
                    JSONObject object = new JSONObject();
                    
                            String strStudentLeaveRequestID = rs.getString("StudentLeaveRequestID");
                            String strClassID = rs.getString("ClassID");
                            String strStudentID = rs.getString("StudentID");
                            String strClass = rs.getString("Class");
                            String strRollNo = rs.getString("RollNo");
                            String strStudentName = rs.getString("StudentName");
                            String strFromDate = rs.getString("FromDate");
                            String strToDate = rs.getString("ToDate");
                            String strReasonTitleID = rs.getString("ReasonTitleID");
                            String strReasonTitle = rs.getString("ReasonTitle");
                            String strDescription = rs.getString("Description");
                             
                            object.put("StudentLeaveRequestId", strStudentLeaveRequestID);
                            object.put("ClassId", strClassID);
                            object.put("StudentId", strStudentID);
                            object.put("Class", strClass);
                            object.put("RollNo", strRollNo);
                            object.put("StudentName", strStudentName);
                            object.put("FromDate", strFromDate);
                            object.put("ToDate", strToDate);
                            object.put("ReasonTitleId", strReasonTitleID);
                            object.put("ReasonTitle", strReasonTitle);
                            object.put("Description", strDescription);
                            
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
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public void fetchLeaveDetailsWithEditable(){
        try{
        if(rs.first()){
               
             do
               {
                    JSONObject object = new JSONObject();
                    
                            String strStudentLeaveRequestID = rs.getString("StudentLeaveRequestID");
                            String strClassID = rs.getString("ClassID");
                            String strStudentID = rs.getString("StudentID");
                             String strSchoolID = rs.getString("SchoolID");
                            String strAcademicYearID = rs.getString("AcademicYearID");
                            String strClass = rs.getString("Class");
                            String strRollNo = rs.getString("RollNo");
                            String strStudentName = rs.getString("StudentName");
                            String strFromDate = rs.getString("FromDate");
                            String strToDate = rs.getString("ToDate");
                            String strReasonTitleID = rs.getString("ReasonTitleID");
                            String strReasonTitle = rs.getString("ReasonTitle");
                            String strDescription = rs.getString("Description");
                             String strIsEditable = rs.getString("is_editable");
                             
                            object.put("StudentLeaveRequestId", strStudentLeaveRequestID);
                            object.put("ClassId", strClassID);
                            object.put("StudentId", strStudentID);
                            object.put("AcademicYearId", strAcademicYearID);
                            object.put("SchoolId", strSchoolID);
                            object.put("Class", strClass);
                            object.put("RollNo", strRollNo);
                            object.put("StudentName", strStudentName);
                            object.put("FromDate", strFromDate);
                            object.put("ToDate", strToDate);
                            object.put("ReasonTitleId", strReasonTitleID);
                            object.put("ReasonTitle", strReasonTitle);
                            object.put("Description", strDescription);
                            object.put("IsEditable", strIsEditable);
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
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    
    
    public void fetchAttendence(){
        try{
        if(rs.first()){
               
             do
               {
                    JSONObject object = new JSONObject();
                            String strClass = rs.getString("Class");
                            
                            String strRollNo = rs.getString("RollNo");
                            String strStudentName = rs.getString("StudentName");
                            String strDate = rs.getString("Date");
                            String strStatus = rs.getString("Status");
                             object.put("Class", strClass);
                            
                            object.put("StudentName", strStudentName);
                            object.put("RollNo", strRollNo);
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
        }catch(Exception e){
            e.printStackTrace();
        }
    }
     public  String studentAttendanceDefault()
    {    
          try
           {
            String sql= "CALL sp_and_get_currenet_attendance(?)";
               fetchStudentData(studAttendanceData, sql);
               if(rs.first())
                {
                    fillStudentAttendanceLater();
                if(array.length()!=0){
               printSuccessStatus(array);
               }else{
                   printFaliureStatus(array);
               }  
                
           
           }   else {
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
     public  String studentAttendanceByMonth()
    {
         
           
           try
           {
            String student_Id = studAttendanceData.getString("StudentId");
            int studId= Integer.parseInt(student_Id);
            String date = studAttendanceData.getString("Date");
            SimpleDateFormat ymd=new SimpleDateFormat("yyyy-mm-dd");
            Date atndDateToSQL = null;
            java.sql.Date sqlAtndDate = null;
            if(!(date.equals("")))
             {
                 Date attendDateIn = new SimpleDateFormat("dd-MM-yyyy").parse(date);
               
                SimpleDateFormat dateFormatToServer = new SimpleDateFormat("yyyy-MM-dd");
                String strAttendDateInFormated = dateFormatToServer.format(attendDateIn);
                
                atndDateToSQL = dateFormatToServer.parse(strAttendDateInFormated);
               
                //SQL Date For Stored Procuder Init
                sqlAtndDate = new java.sql.Date(atndDateToSQL.getTime());
             }
            String sql="CALL sp_and_get_attendance_by_date(?,?);";
            
             callable = con.getConnection().prepareCall(sql);
            
            callable.setDate(2,sqlAtndDate);
            callable.setInt(1,studId);
            
           rs = callable.executeQuery();
           
          
          if(rs.first())
           {
               

                    fillStudentAttendance();
                
                    if(array.length()!=0){
               
                    printSuccessStatus(array);
               }else{
                   
                    printFaliureStatus(array);
               }  
                
           
           }   else {
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

     
      public String insertStudentLeaveRequest(){
            try{
                
        
                 String sql = "call sp_insert_tbl_student_leave_request(?,?,?,?,?,?,?,?,?,?,?);";
                 //JSONObject object = array.getJSONObject(i);
                 int schoolId = Integer.parseInt(studAttendanceData.getString("SchoolId"));
                 int academicYearId = Integer.parseInt(studAttendanceData.getString("AcademicYearId"));
                 int studentId = Integer.parseInt(studAttendanceData.getString("StudentId"));
                 //int enteredBy = Integer.parseInt(studAttendanceData.getString("EnteredBy"));
                 String strLeaveFromDate= studAttendanceData.getString("FromDate");
                 String strLeaveToDate= studAttendanceData.getString("ToDate");
                  int reasonTitleId = Integer.parseInt(studAttendanceData.getString("ReasonTitleId"));
                  String strOtherReason=studAttendanceData.getString("OtherReasonTitle");
                  String strDescription=studAttendanceData.getString("Description");
                  //int isApproved = Integer.parseInt(object.getString("IsApproved"));
                  int  clsId = Integer.parseInt(studAttendanceData.getString("ClassId"));
                 /*int rsnId = Integer.parseInt(object.getString("ReasonId"));
                 String otherReason=object.getString("OtherReasonTitle");*/
               String strPhoneNumber = studAttendanceData.getString("PhoneNumber");
              String strPassword=studAttendanceData.getString("Password");   
                 int studAttendId=0,passwordMatch=0;
                 
                 Date fromDateSQL = null;
             java.sql.Date sqlfromDateDate = null;
              Date toDateSQL = null;
             java.sql.Date sqlTofromDateDate = null;
             
             
             if(!(strLeaveFromDate.equals("")))
             {
                 Date leaveFromDate = new SimpleDateFormat("dd-MM-yyyy").parse(strLeaveFromDate);
               
                SimpleDateFormat dateFormatToServer = new SimpleDateFormat("yyyy-MM-dd");
                String strAttendDateInFormated = dateFormatToServer.format(leaveFromDate);
                
                fromDateSQL = dateFormatToServer.parse(strAttendDateInFormated);
               
                //SQL Date For Stored Procuder Init
                sqlfromDateDate = new java.sql.Date(fromDateSQL.getTime());
             }
             
             if(!(strLeaveToDate.equals("")))
             {
                 Date leaveFromDate = new SimpleDateFormat("dd-MM-yyyy").parse(strLeaveToDate);
               
                SimpleDateFormat dateFormatToServer = new SimpleDateFormat("yyyy-MM-dd");
                String strAttendDateInFormated = dateFormatToServer.format(leaveFromDate);
                
                toDateSQL = dateFormatToServer.parse(strAttendDateInFormated);
               
                //SQL Date For Stored Procuder Init
                sqlTofromDateDate = new java.sql.Date(toDateSQL.getTime());
             }
             
            
             
             
            String sql2="CALL sp_and_get_password(?);";
            
             CallableStatement callable2=con.getConnection().prepareCall(sql2);
            callable2.setString(1, strPhoneNumber);
           
            ResultSet rs2=callable2.executeQuery();
           
            if(rs2.first())
            {
               
                    JSONObject object = new JSONObject();
                            String strDbPassword = rs2.getString("password");
                            if(strDbPassword.equals(strPassword)){
                                passwordMatch=1;
                            }
              
                
            }
           
          else {
                        outObject.put("Status", "Failure");
                        outObject.put("Message", "Password did not match ");
                        //outObject.put("Result", array);
                }
             if(passwordMatch==1){
             callable=con.getConnection().prepareCall(sql);
           
            callable.setInt(1,0);
            callable.setInt(2, schoolId);
             callable.setInt(3, academicYearId);
             callable.setInt(4,clsId);
            callable.setInt(5, studentId);
             callable.setDate(6, sqlfromDateDate);
             callable.setDate(7, sqlTofromDateDate);
             
            callable.setInt(8, reasonTitleId);
            callable.setString(9, strOtherReason);
            callable.setString(10, strDescription);
           
//            callable.setInt(9,0);
            
            effRows=callable.executeUpdate();
               resId =callable.getInt(11);
               /*
                if(resId==-1){
                    break;
                }
                else if (resId>0){
                String strAtnStatus="Present";
                    rowCount++;
                    if(status==0){
                        strAtnStatus="Absent";
                    }
                    String strMessage=" Your Child is "+strAtnStatus; 
                   // fcm.send_FCM_Notification(strUserToken, strMessage, "VinVidya Attendance");
                }
                else {
                      break;
                }
            */
             
    
     if(resId>0){                 
                 outObject.put("Status", "Success");
                 outObject.put("Message", "leave request sent Sucessfully");
                 outObject.put("resultId",resId);
                 
            }
     else if(resId==-1){
                 outObject.put("Status", "Alert");
                 outObject.put("Message", "leave request has been already added");
                 outObject.put("resultId",resId);
            }else {
            outObject.put("Status", "Fail");
                 outObject.put("Message", "Something went wrong");
                 outObject.put("resultId",resId);
            }    
             }else{
                  outObject.put("Status", "Unauthorised");
                        outObject.put("Message", "Password did not match ");
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
     
      public String editStudentLeaveRequest(){
            try{
                
        
                 String sql = "call sp_insert_tbl_student_leave_request(?,?,?,?,?,?,?,?,?,?,?);";
                 //JSONObject object = array.getJSONObject(i);
                 int studentLeaveRequestId = Integer.parseInt(studAttendanceData.getString("StudentLeaveRequestId"));
                 int schoolId = Integer.parseInt(studAttendanceData.getString("SchoolId"));
                 int academicYearId = Integer.parseInt(studAttendanceData.getString("AcademicYearId"));
                 int studentId = Integer.parseInt(studAttendanceData.getString("StudentId"));
                 //int enteredBy = Integer.parseInt(studAttendanceData.getString("EnteredBy"));
                 String strLeaveFromDate= studAttendanceData.getString("FromDate");
                 String strLeaveToDate= studAttendanceData.getString("ToDate");
                  int reasonTitleId = Integer.parseInt(studAttendanceData.getString("ReasonTitleId"));
                  String strOtherReason=studAttendanceData.getString("OtherReasonTitle");
                  
                  String strDescription=studAttendanceData.getString("Description");
                    String base64Description=StringUtil.base64ToString(strDescription);
//int isApproved = Integer.parseInt(object.getString("IsApproved"));
                  int  clsId = Integer.parseInt(studAttendanceData.getString("ClassId"));
                 /*int rsnId = Integer.parseInt(object.getString("ReasonId"));
                 String otherReason=object.getString("OtherReasonTitle");*/
                 
                 int studAttendId=0;
                 
                 Date fromDateSQL = null;
             java.sql.Date sqlfromDateDate = null;
              Date toDateSQL = null;
             java.sql.Date sqlTofromDateDate = null;
             
             
             if(!(strLeaveFromDate.equals("")))
             {
                 Date leaveFromDate = new SimpleDateFormat("dd-MM-yyyy").parse(strLeaveFromDate);
               
                SimpleDateFormat dateFormatToServer = new SimpleDateFormat("yyyy-MM-dd");
                String strAttendDateInFormated = dateFormatToServer.format(leaveFromDate);
                
                fromDateSQL = dateFormatToServer.parse(strAttendDateInFormated);
               
                //SQL Date For Stored Procuder Init
                sqlfromDateDate = new java.sql.Date(fromDateSQL.getTime());
             }
             
             if(!(strLeaveToDate.equals("")))
             {
                 Date leaveFromDate = new SimpleDateFormat("dd-MM-yyyy").parse(strLeaveToDate);
               
                SimpleDateFormat dateFormatToServer = new SimpleDateFormat("yyyy-MM-dd");
                String strAttendDateInFormated = dateFormatToServer.format(leaveFromDate);
                
                toDateSQL = dateFormatToServer.parse(strAttendDateInFormated);
               
                //SQL Date For Stored Procuder Init
                sqlTofromDateDate = new java.sql.Date(toDateSQL.getTime());
             }
             
             
             
            callable=con.getConnection().prepareCall(sql);
           
            callable.setInt(1,studentLeaveRequestId);
            callable.setInt(2, schoolId);
             callable.setInt(3, academicYearId);
             callable.setInt(4,clsId);
            callable.setInt(5, studentId);
             callable.setDate(6, sqlfromDateDate);
             callable.setDate(7, sqlTofromDateDate);
             
            callable.setInt(8, reasonTitleId);
            if(strOtherReason.equalsIgnoreCase("9999")){
            callable.setString(9, null);     
            }else{
            callable.setString(9, strOtherReason);     
            }
           
            callable.setString(10, base64Description);
           
//            callable.setInt(9,0);
            
            effRows=callable.executeUpdate();
               resId =callable.getInt(11);
               /*
                if(resId==-1){
                    break;
                }
                else if (resId>0){
                String strAtnStatus="Present";
                    rowCount++;
                    if(status==0){
                        strAtnStatus="Absent";
                    }
                    String strMessage=" Your Child is "+strAtnStatus; 
                   // fcm.send_FCM_Notification(strUserToken, strMessage, "VinVidya Attendance");
                }
                else {
                      break;
                }
            */
             
    
     if(resId==-2){                 
                 outObject.put("Status", "Success");
                 outObject.put("Message", "leave request edited Sucessfully");
                 outObject.put("resultId",resId);
                 
            }
     else if(resId==-1){
                 outObject.put("Status", "Alert");
                 outObject.put("Message", "Nothing To edit");
                 outObject.put("resultId",resId);
            }else {
            outObject.put("Status", "Fail");
                 outObject.put("Message", "Something went wrong");
                 outObject.put("resultId",resId);
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
      
      
      
      
public String deleteStudentLeave(){
    try{
    String sql="CALL sp_delete_student_leave_request_by_id(?);";
    fetchId(studAttendanceData,sql);
   effRows=callable.executeUpdate();
   // String strStatus="", strMessage="";
    //effRows=Integer.parseInt(rs.toString());
        

           if(effRows==0){
             String strStatus="Success";
             String strMessage="Leave deleted successfully..";
           outObject.put("Status",strStatus);
           outObject.put("Message",strMessage);
           }else {
             
             String strStatus="Fail";
            String strMessage="Leave couldn't be deleted..";
           outObject.put("Status",strStatus);
           outObject.put("Message",strMessage);  
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

      
      
     
     
     public String getClasswiseStudentLeaveList() {
        try {
            Date atndDate = null;
            java.sql.Date sqlRequestDate = null, sqlToDate = null;
            Date requestDateSql = null, toDateSql = null;
            int clsId = Integer.parseInt(studAttendanceData.getString("ClassId"));
            String strRequestDate = studAttendanceData.getString("Date");
            String sql = "CALL `sp_get_tbl_student_leave_request`(?,?)";
            if (!(strRequestDate.equals(""))) {
                Date RequestDate = new SimpleDateFormat("dd-MM-yyyy").parse(strRequestDate);
                //tmpNoteOn=new Timestamp(fromDate.getTime());
                SimpleDateFormat dateFormatToServer = new SimpleDateFormat("dd-MM-yyyy");
                String strToDateInFormated = dateFormatToServer.format(RequestDate);

                requestDateSql = dateFormatToServer.parse(strToDateInFormated);
//              SQL Date For Stored Procuder Init
                sqlRequestDate = new java.sql.Date(requestDateSql.getTime());
            }
            callable = con.getConnection().prepareCall(sql);
            callable.setInt(1, clsId);
            callable.setDate(2, sqlRequestDate);
            rs = callable.executeQuery();
            if (rs.first()) {
                fetchRequestDetails();
                if (array.length() != 0) {
                    printSuccessStatus(array);
                } else {
                    printFaliureStatus(array);
                }

            } else {
                printFaliureStatus(array);
            }

            return outObject.toString();
        } catch (Exception ee) {
            String exce = ee.toString();
            String exc = "{\"Status\":\"Exception\" ,  \"Message\":\"" + exce + "\" ,"
                    + " \"Result\":[]}";
            return exc;
        } finally {
            closeDBConnection();
        }

    }
     
     public String getStudentwiseStudentRequestList() {
        try {
            Date atndDate = null;
            java.sql.Date sqlRequestDate = null, sqlToDate = null;
            Date requestDateSql = null, toDateSql = null;
            int studId = Integer.parseInt(studAttendanceData.getString("StudentId"));
            
            String sql = "CALL `sp_get_tbl_student_leave_request_by_student`(?)";
            
            callable = con.getConnection().prepareCall(sql);
            callable.setInt(1, studId);
            
            rs = callable.executeQuery();
            if (rs.first()) {
                fetchRequestDetails();
                if (array.length() != 0) {
                    printSuccessStatus(array);
                } else {
                    printFaliureStatus(array);
                }

            } else {
                printFaliureStatus(array);
            }

            return outObject.toString();
        } catch (Exception ee) {
            String exce = ee.toString();
            String exc = "{\"Status\":\"Exception\" ,  \"Message\":\"" + exce + "\" ,"
                    + " \"Result\":[]}";
            return exc;
        } finally {
            closeDBConnection();
        }

    }
     
      
     
     
     
     
     public String checkStudentAttendance(){
         try
           {
            int clsId= Integer.parseInt(studAttendanceData.getString("ClassId"));
            
            
            String sql="CALL Sp_Check_Student_Attendance_by_ClassId(?,?);";
            
             callable = con.getConnection().prepareCall(sql);
            
            
            callable.setInt(1,clsId);
            
           rs = callable.executeQuery();
          int resultId=callable.getInt(2);
          
          if(resultId>0)
           {
               
              outObject.put("Status", resultId);
             outObject.put("Message", "Data found..");
             outObject.put("Result", array);
           
           }
           else if(resultId==-1)
           {
               
              outObject.put("Status", resultId);
             outObject.put("Message", "Data Not found..");
             outObject.put("Result", array);
           
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
     
     public String checkStudentAttendancePeriodWise(){
         try
           {
            int clsId= Integer.parseInt(studAttendanceData.getString("ClassId"));
            String strPeriod= studAttendanceData.getString("Period");
            
            String sql="CALL Sp_Check_Periodwise_Student_Attendance_by_ClassId(?,?,?);";
            
             callable = con.getConnection().prepareCall(sql);
            
            
            callable.setInt(1,clsId);
            callable.setString(2,strPeriod);
            
           rs = callable.executeQuery();
          int resultId=callable.getInt(3);
          
          if(resultId>0)
           {
               
              outObject.put("Status", resultId);
             outObject.put("Message", "Data found..");
             outObject.put("Result", array);
           
           }
           else if(resultId==-1)
           {
               
              outObject.put("Status", resultId);
             outObject.put("Message", "Data found..");
             outObject.put("Result", array);
           
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
     
     
    public  String AttendenceDisplay_Admin_Studentwise()
    {
        JSONObject outObject = new JSONObject();
           try
           {
               JSONArray array = new JSONArray();
           // JSONObject studAttendanceData = new JSONObject(studAttendanceData);
            String schoolid = studAttendanceData.getString("SchoolId");
            int sclId = Integer.parseInt(schoolid);
            String strRollNo = studAttendanceData.getString("RollNo");
            int RollNo = Integer.parseInt(strRollNo);
            String clss = studAttendanceData.getString("Class");
            String section = studAttendanceData.getString("Section");
            String attendanceDate = studAttendanceData.getString("Date"); 
           SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
           java.util.Date date= formatter.parse(attendanceDate);
            String sql = " CALL `AttendenceDisplay_Admin_Studentwise`(?, ?, ?, ?, ?)";
           
            callable = con.getConnection().prepareCall(sql);
            callable.setString(1, clss);
            callable.setString(2, section);
            callable.setInt(3,RollNo);
            callable.setDate(4,new java.sql.Date(date.getTime()));
            callable.setInt(5,sclId);
           
            rs = callable.executeQuery();
           if(rs.first())
           {
               do
               {
                    JSONObject object = new JSONObject();
                            String strrollNo = rs.getString("RollNo");
                            String strStudentName = rs.getString("StudentName");
                            String strDate = rs.getString("Date");
                            String strStatus = rs.getString("Status");
                            object.put("StudentName", strStudentName);
                            object.put("RollNo", strrollNo);
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
    
     public String insertStudentAttendance(){
            try{
                int clsId=0;
                Timestamp tmpAttendAdded= null;
         JSONArray array= studAttendanceData.getJSONArray("AttendanceArray");
         int rowCount=0;
        
             for (int i=0; i < array.length(); i++) {
                 String sql = "call Sp_Insert_tblStudentAttendance(?,?,?,?,?,?,?,?,?,?,?,?,?,?);";
                 JSONObject object = array.getJSONObject(i);
                 int schoolId = Integer.parseInt(object.getString("SchoolId"));
                 int academicYearId = Integer.parseInt(object.getString("AcademicYearId"));
                 int studentId = Integer.parseInt(object.getString("StudentId"));
                 int enteredBy = Integer.parseInt(object.getString("EnteredBy"));
                 int status = Integer.parseInt(object.getString("Status"));
                 String strAttendanceDate= object.getString("Date");
                 clsId = Integer.parseInt(object.getString("ClassId"));
                 /*int rsnId = Integer.parseInt(object.getString("ReasonId"));
                 String otherReason=object.getString("OtherReasonTitle");*/
                 
                 int studAttendId=0;
                 
                 Date attendDateSQL = null;
             java.sql.Date sqlAttendDate = null;
             
             if(!(strAttendanceDate.equals("")))
             {
                 Date homewrokDate = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss").parse(strAttendanceDate);
                 tmpAttendAdded=new Timestamp(homewrokDate.getTime());
                SimpleDateFormat dateFormatToServer = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                String strAttendDateInFormated = dateFormatToServer.format(homewrokDate);
                
                attendDateSQL = dateFormatToServer.parse(strAttendDateInFormated);
               
                //SQL Date For Stored Procuder Init
                sqlAttendDate = new java.sql.Date(attendDateSQL.getTime());
             }
            callable=con.getConnection().prepareCall(sql);
           
             callable.setInt(1,0);
             callable.setInt(2, schoolId);
             callable.setInt(3, academicYearId);
             callable.setInt(4,enteredBy);
             callable.setTimestamp(5, tmpAttendAdded);
             callable.setInt(6, clsId);
             callable.setInt(7, studentId);
             callable.setInt(8,status);
             callable.setInt(9,0);
             callable.setString(10,null);
             callable.setInt(11,enteredBy);
             callable.setTimestamp(12, tmpAttendAdded);
             //callable.setDate(12, sqlAttendDate);
//            callable.setInt(9,0);
            
            effRows=callable.executeUpdate();
                resId=callable.getInt(13);
                 strUserToken=callable.getString(14);
               
                if(resId==-1){
                    break;
                }
                else if (resId>0){
                String strAtnStatus="Present";
                    rowCount++;
                    if(status==0){
                        strAtnStatus="Absent";
                    }
                    String strMessage=" Your Child is "+strAtnStatus;
                   // fcm.send_FCM_Notification(strUserToken, strMessage, "VinVidya Attendance");
                }
                else {
                      break;
                }
             }
    
     if(resId>0){                 
                 outObject.put("Status", "Success");
                 outObject.put("Message", "Student attendance added sucessfully");
                 outObject.put("effected rows",rowCount);
            }
     else if(resId==-1){
                 outObject.put("Status", "Fail");
                 outObject.put("Message", "Student attendance has been already added");
            }else {
            outObject.put("Status", "Fail");
                 outObject.put("Message", "Something went wrong");
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

public String insertStudentAttendancePeriodWise(){
    try{
                int clsId=0;
         JSONArray array= studAttendanceData.getJSONArray("AttendanceArray");
         int rowCount=0;
        
             for (int i=0; i < array.length(); i++) {
                 String sql = "call sp_insert_tblstudentattendance_periodwise(?,?,?,?,?,?,?,?,?,?,?,?,?,?);";
                 JSONObject object = array.getJSONObject(i);
                 int schoolId = Integer.parseInt(object.getString("SchoolId"));
                 int academicYearId = Integer.parseInt(object.getString("AcademicYearId"));
                 int studentId = Integer.parseInt(object.getString("StudentId"));
                 int enteredBy = Integer.parseInt(object.getString("EnteredBy"));
                 int status = Integer.parseInt(object.getString("Status"));
                 String strAttendanceDate= object.getString("Date");
                 clsId = Integer.parseInt(object.getString("ClassId"));
                 String strPeriod = object.getString("Period");
                 
                 /*int rsnId = Integer.parseInt(object.getString("ReasonId"));
                 String otherReason=object.getString("OtherReasonTitle");*/
                 
                 int studAttendId=0;
                 
                 Date attendDateSQL = null;
             java.sql.Date sqlAttendDate = null;
             
             if(!(strAttendanceDate.equals("")))
             {
                 Date homewrokDate = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss").parse(strAttendanceDate);
               
                SimpleDateFormat dateFormatToServer = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                String strAttendDateInFormated = dateFormatToServer.format(homewrokDate);
                
                attendDateSQL = dateFormatToServer.parse(strAttendDateInFormated);
               
                //SQL Date For Stored Procuder Init
                sqlAttendDate = new java.sql.Date(attendDateSQL.getTime());
             }
            callable=con.getConnection().prepareCall(sql);
           
            callable.setInt(1,0);
            callable.setInt(2, schoolId);
             callable.setInt(3, academicYearId);
             callable.setInt(4,enteredBy);
             callable.setDate(5, sqlAttendDate);
             callable.setString(6,strPeriod);
            callable.setInt(7, clsId);
            callable.setInt(8, studentId);
            callable.setInt(9,status);
            callable.setInt(10,0);
            callable.setString(11,null);
            callable.setInt(12,enteredBy);
           
//            callable.setInt(9,0);
            
            effRows=callable.executeUpdate();
                resId=callable.getInt(13);
                 strUserToken=callable.getString(14);
               
                if(resId==-1){
                    break;
                }
                else if (resId>0){
                    
                    String strAtnStatus="Present";
                    
                    rowCount++;
                    
                    if(status==0){
                        strAtnStatus="Absent";
                    }
                    String strMessage=" Your Child is "+strAtnStatus;
                    fcm.send_FCM_Notification(strUserToken, strMessage, "VinVidya Attendance");
                }
                else {
                      break;
                }
             }
    
     if(resId>0){                 
                 outObject.put("Status", "Success");
                 outObject.put("Message", "Student attendance added sucessfully");
                 outObject.put("effected rows",rowCount);
            }
     else if(resId==-1){
                 outObject.put("Status", "Fail");
                 outObject.put("Message", "Student attendance has been already added");
            }else {
            outObject.put("Status", "Fail");
                 outObject.put("Message", "Something went wrong");
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
     
     
     
     
     
     public void sendMessage(String phonenumber,String textMessage)
       
{
    
//Your authentication key
        String authkey = "222059ACFHhJkC5b2dfd12";
//Multiple mobiles numbers separated by comma
        String mobiles = phonenumber;
//Sender ID,While using route4 sender id should be 6 characters long.
        String senderId = "VinVid";
//Your message to send, Add URL encoding here.
//String message = "hi this is the message to check the characters \n of the message for msg 91 this is the test message please ignore if it had come for now, Actually we are doing sms test with character length more than 169 ";

        String message = textMessage;
//define route
        String route="4";
        
        String unicode="1";
//Prepare Url
        URLConnection myURLConnection=null;
        URL myURL=null;
        BufferedReader reader=null;

//encoding message
       String encoded_message=URLEncoder.encode(message);

//Send SMS API
       String mainUrl="http://api.msg91.com/api/sendhttp.php?";

//Prepare parameter string
       StringBuilder sbPostData= new StringBuilder(mainUrl);
       
       sbPostData.append("authkey="+authkey);
       sbPostData.append("&mobiles="+mobiles);
       sbPostData.append("&message="+encoded_message);
       sbPostData.append("&route="+route);
       sbPostData.append("&sender="+senderId);
       sbPostData.append("&unicode="+unicode);

//final string
       mainUrl = sbPostData.toString();
        try
            {
//prepare connection
                myURL = new URL(mainUrl);
                myURLConnection = myURL.openConnection();
                myURLConnection.connect();
                reader= new BufferedReader(new InputStreamReader(myURLConnection.getInputStream()));
//reading response
                String response;
                while ((response = reader.readLine()) != null)
//print response
                System.out.println(response);

//finally close connection
                reader.close();
            }
              catch (IOException e)
                {
                    e.printStackTrace();
                }
    }
    
     
     public String getPeriodForAttendance(){
       try{
    String sql="CALL `Sp_Dropdown_timetable_period`(?,?);";
    int clsId=Integer.parseInt(studAttendanceData.getString("ClassId"));
    int dayId= Integer.parseInt(studAttendanceData.getString("DayId"));
   
    callable= con.getConnection().prepareCall(sql);
    callable.setInt(1,clsId);
    callable.setInt(2,dayId);
    rs= callable.executeQuery();
    if(rs.first())
           {
           
        do{
                  JSONObject object= new JSONObject();
                  
                  String strDayId= rs.getString("DayId");
                  String strPeriod=rs.getString("Period");
                  String strSubject=rs.getString("Subject");
                
                  object.put("DayId", strDayId);
                  object.put("Period", strPeriod);
                  object.put("Subject", strSubject);
                   
                  
                   array.put(object);
              }while(rs.next());
        
            if(array.length()!=0){
               printSuccessStatus(array);
               }else{
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
     
     
     
     
     public  String AttendenceDisplay_Classwise_In()
    {
            JSONObject outObject = new JSONObject();
           try
           {
               JSONArray array = new JSONArray();
          //  JSONObject studAttendanceData = new JSONObject(studAttendanceData);
            String schoolid = studAttendanceData.getString("SchoolId");
            int sclId = Integer.parseInt(schoolid);
            String clss = studAttendanceData.getString("Class");
            String section = studAttendanceData.getString("Section");
            String attendanceDate = studAttendanceData.getString("Date"); 
           SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
           java.util.Date date= formatter.parse(attendanceDate);
            String sql = " CALL `AttendenceDisplay_Classwise_In`(?, ?, ?, ?)";
            
            callable = con.getConnection().prepareCall(sql);
            callable.setInt(1,sclId);
            callable.setString(2, clss);
            callable.setString(3, section);
            callable.setDate(4,new java.sql.Date(date.getTime()));
           
            rs = callable.executeQuery();
           if(rs.first())
           {
               do
               {
                    JSONObject object = new JSONObject();
                            String strClass = rs.getString("Class");
                            String strSection = rs.getString("Section");
                            String strRollNo = rs.getString("RollNo");
                            String strStudentName = rs.getString("StudentName");
                            String strDate = rs.getString("Date");
                            String strStatus = rs.getString("Status");
                             object.put("Class", strClass);
                            object.put("Section", strSection);
                            object.put("StudentName", strStudentName);
                            object.put("RollNo", strRollNo);
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
   
    public  String StudentAttendence_Insert()
    {
        
           try
           {
            
           // JSONObject studAttendanceData = new JSONObject(studAttendanceData);
            String schoolId = studAttendanceData.getString("SchoolId");
            int sclId = Integer.parseInt(schoolId);
            String academicYearId = studAttendanceData.getString("AcademicYearId");
             int acmdid = Integer.parseInt(academicYearId);
             String createdBy = studAttendanceData.getString("CreatedBy");
             int crtby = Integer.parseInt(createdBy);
             String date = studAttendanceData.getString("Date");
             SimpleDateFormat formatterentrdt = new SimpleDateFormat("yyyy-MM-dd HH:mm");
             java.util.Date atndDate= formatterentrdt.parse(date);
             String classId = studAttendanceData.getString("ClassId");
             int clsid = Integer.parseInt(classId);
             String studentId = studAttendanceData.getString("StudentId");
             int studid = Integer.parseInt(studentId);
             String status = studAttendanceData.getString("Status");
             int sts = Integer.parseInt(status);
             String reason = studAttendanceData.getString("Reason");
                  
            String sql = " CALL `StudentAttendence_Insert`(?, ?, ?, ?, ?, ?, ?, ?)";
            
            callable = con.getConnection().prepareCall(sql);
            callable.setInt(1,sclId);
            callable.setInt(2,acmdid);
            callable.setInt(3,crtby);
             callable.setDate(4, new java.sql.Date(atndDate.getTime()));
            callable.setInt(5, clsid);
            callable.setInt(6, studid);
            callable.setInt(7, sts); 
            callable.setString(8, reason);
            
                        
            int effRow=callable.executeUpdate();
            if(effRow>0)
            {
                 outObject.put("Status", "Success");
                 outObject.put("Message", "inserted sucessfully");
            }else{
                 outObject.put("Status", "Fail");
                 outObject.put("Message", "Attendnance was not added");
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
    public String getAttendanceMonths()
    {
        
           try
           {
            String studentid = studAttendanceData.getString("StudentId");
            int studId = Integer.parseInt(studentid);
            String sql ="CALL `getAttendanceMonths`(?)";
            
            callable = con.getConnection().prepareCall(sql);
            callable.setInt(1,studId);
           
            rs = callable.executeQuery();
             if(rs.first())
           {
               do
               {
                    JSONObject object = new JSONObject();
                            String strMonth = rs.getString("Month");
                            object.put("Month", strMonth);
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
    
       public String displayReason(){
            JSONObject outObject = new JSONObject();
           try
           {
               JSONArray array = new JSONArray();
          //  JSONObject studAttendanceData = new JSONObject(studAttendanceData);
            int schoolid = Integer.parseInt(studAttendanceData.getString("SchoolId"));
            String sql = " CALL `Sp_Dropdown_StudentReasonIdMaster`(?)";
            
            callable = con.getConnection().prepareCall(sql);
            callable.setInt(1,schoolid);
            rs=callable.executeQuery();
            if(rs.first()){
                do{
                    JSONObject object = new JSONObject();
                    String strReasonId = rs.getString("ReasonId");
                    String strReason = rs.getString("Reason");
                    object.put("ReasonId", strReasonId);
                    object.put("Reason", strReason);
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
     public String getClasswiseAttendance(){
         try
           {
              
            int clsId = Integer.parseInt(studAttendanceData.getString("ClassId"));
            String sql = " CALL `sp_and_get_classwise_attendence`(?)";
            
            callable = con.getConnection().prepareCall(sql);
            callable.setInt(1,clsId);
            rs=callable.executeQuery();
            if(rs.first()){
                fetchAttendence();
                if(array.length()!=0){
               printSuccessStatus(array);
               }else{
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

     public  String getClasswiseAttendanceByDate(){
         try
           {
              Date atndDate=null;
            int clsId = Integer.parseInt(studAttendanceData.getString("ClassId"));
             String strAttendanceDate = studAttendanceData.getString("Date");
            String sql = " CALL `sp_and_get_classwise_attendence_by_date`(?,?)";
            if(!(strAttendanceDate.equals("")))
                {
                    atndDate= new SimpleDateFormat("dd-MM-yyyy").parse(strAttendanceDate);
   //                    SimpleDateFormat sdf = new SimpleDateFormat("M/d/yy h:mm:ss a",Locale.US);
                   SimpleDateFormat dateFormatToServer = new SimpleDateFormat("yyyy-MM-dd");
                   String strRmdSetDateInFormated = dateFormatToServer.format(atndDate);
                    tmpAttendDate=new Timestamp(atndDate.getTime());
                }
                    callable = con.getConnection().prepareCall(sql);
            callable.setInt(1,clsId);
            callable.setTimestamp(2, tmpAttendDate);
            rs=callable.executeQuery();
            if(rs.first()){
                fetchAttendence();
                if(array.length()!=0){
               printSuccessStatus(array);
               }else{
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
     
     public  String getStudentwiseLeaveList(){
         try
           {
              
            String sql = " CALL sp_get_student_leave_request_by_studentid(?)";
           fetchStudentData(studAttendanceData, sql);
            if(rs.first()){
                fetchLeaveDetailsWithEditable();
                if(array.length()!=0){
               printSuccessStatus(array);
               }else{
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
     public  String getClasswiseStudentLeavelist(){
         try
           {
              
            String sql = " CALL sp_get_student_leave_request_by_classid(?,?)";
           fetchClassIdWithDate(studAttendanceData, sql);
            if(rs.first()){
                fetchLeaveDetails();
                if(array.length()!=0){
               printSuccessStatus(array);
               }else{
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
     
     
     
     
     public String getperiodwiseClassAttendance(){
try
           {
              Date atndDate=null;
            int clsId = Integer.parseInt(studAttendanceData.getString("ClassId"));
             String strAttendanceDate = studAttendanceData.getString("Date");
            String sql = " CALL sp_get_periodwise_attendancelist_of_class(?,?)";
            if(!(strAttendanceDate.equals("")))
                {
                    atndDate= new SimpleDateFormat("dd-MM-yyyy").parse(strAttendanceDate);
   //                    SimpleDateFormat sdf = new SimpleDateFormat("M/d/yy h:mm:ss a",Locale.US);
                   SimpleDateFormat dateFormatToServer = new SimpleDateFormat("yyyy-MM-dd");
                   String strRmdSetDateInFormated = dateFormatToServer.format(atndDate);
                    tmpAttendDate=new Timestamp(atndDate.getTime());
                }
                    callable = con.getConnection().prepareCall(sql);
            callable.setInt(1,clsId);
            callable.setTimestamp(2, tmpAttendDate);
            rs=callable.executeQuery();
            if(rs.first()){
                fetchPeriodwiseAttendance();
                if(array.length()!=0){
               printSuccessStatus(array);
               }else{
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
     public String getperiodwiseStudentAttendance(){
try
           {
              Date atndDate=null;
            int studId = Integer.parseInt(studAttendanceData.getString("StudentId"));
             
            String sql = " CALL sp_and_get_currenet_attendance(?)";
            
                    callable = con.getConnection().prepareCall(sql);
            callable.setInt(1,studId);
           
            rs=callable.executeQuery();
            if(rs.first()){
                fetchPeriodwiseAttendance();
                if(array.length()!=0){
               printSuccessStatus(array);
               }else{
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
     public String getperiodwiseStudentAttendanceByMonth(){
try
           {
              Date atndDate=null;
            int clsId = Integer.parseInt(studAttendanceData.getString("StudentId"));
             String strAttendanceDate = studAttendanceData.getString("Date");
            String sql = "CALL sp_and_get_attendance_by_date(?,?)";
            if(!(strAttendanceDate.equals("")))
                {
                    atndDate= new SimpleDateFormat("dd-MM-yyyy").parse(strAttendanceDate);
   //                    SimpleDateFormat sdf = new SimpleDateFormat("M/d/yy h:mm:ss a",Locale.US);
                   SimpleDateFormat dateFormatToServer = new SimpleDateFormat("yyyy-MM-dd");
                   String strRmdSetDateInFormated = dateFormatToServer.format(atndDate);
                    tmpAttendDate=new Timestamp(atndDate.getTime());
                }
                    callable = con.getConnection().prepareCall(sql);
            callable.setInt(1,clsId);
            callable.setTimestamp(2, tmpAttendDate);
            rs=callable.executeQuery();
            if(rs.first()){
                fetchPeriodwiseAttendance();
                if(array.length()!=0){
               printSuccessStatus(array);
               }else{
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
     
     
     
}



