/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.operations;

import com.example.db.DBConnect;
import com.example.db.FCM;
import com.example.db.StringUtil;
import com.example.db.SendSms;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Timestamp;
import java.sql.Types;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.ExecutionException;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 *
 * @author basava
 */
public class HomeWorkOperation {
  JSONObject homeworkData; 
  JSONArray array = new JSONArray();
  JSONArray fedBackInsertArray= new JSONArray();
  JSONObject outObject = new JSONObject();
  DBConnect con=new DBConnect();
  CallableStatement callable=null;
  ResultSet rs=null;
  int effRows=0,clsId=0;
  int resId=0;
  
    FCM fcm=new FCM();
  String base64Description,base64Chapter,strUserToken="";
  public HomeWorkOperation(JSONObject homeworkData ) {
        this.homeworkData = homeworkData;
       
    }
 
  
  public void fetchStudentWithData(JSONObject homeworkData,String sql){
      try{
       String studentId = homeworkData.getString("StudentId");
       int studId = Integer.parseInt(studentId);
      
       callable=con.getConnection().prepareCall(sql);
       callable.setInt(1, studId);
       rs=callable.executeQuery();
       }catch(Exception e){
         e.printStackTrace();
       }
  }
  
  
  public void fetchClasswiseDatawithSubject(JSONObject homeworkData, String sql){
      
      try{
          
            String strClassId = homeworkData.getString("ClassId");
            int clsId = Integer.parseInt(strClassId);
            String strSubjectId = homeworkData.getString("SubjectId");
            int subId = Integer.parseInt(strSubjectId);
//            String cls = homeworkData.getString("Class");
//            String section = homeworkData.getString("Section");
//            String subject = homeworkData.getString("Subject");
      
       callable=con.getConnection().prepareCall(sql);
       callable.setInt(1, clsId);
       callable.setInt(2, subId);
//       callable.setString(3, cls);
//       callable.setString(4, section);
//       callable.setString(5, subject);
       rs=callable.executeQuery();
      }catch(Exception e){
          
      }
  } 
  
  public void fetchClasswiseDatawithDate(JSONObject homeworkData, String sql){
      
      try{
          
            String strClassId = homeworkData.getString("ClassId");
            int clsId = Integer.parseInt(strClassId);
            String strSubjectId = homeworkData.getString("SubjectId");
            int subId = Integer.parseInt(strSubjectId);
            String hmwrkFromDate=homeworkData.getString("FromDate");
            String hmwrkToDate=homeworkData.getString("ToDate");
           //  SimpleDateFormat ymd=new SimpleDateFormat("yyyy-mm-dd");
            Date hmwrkFromDateSQL = null,hmwrkToDateSQL=null;
            java.sql.Date sqlhmwrkFromDate = null,sqlhmwrkToDate=null;
            
            if(!(hmwrkFromDate.equals("")))
             {
                 Date homewrokDate = new SimpleDateFormat("dd-MM-yyyy").parse(hmwrkFromDate);
               
                SimpleDateFormat dateFormatToServer = new SimpleDateFormat("yyyy-MM-dd");
                String strhmwrkDateInFormated = dateFormatToServer.format(homewrokDate);
                
                hmwrkFromDateSQL = dateFormatToServer.parse(strhmwrkDateInFormated);
               
                //SQL Date For Stored Procuder Init
                sqlhmwrkFromDate = new java.sql.Date(hmwrkFromDateSQL.getTime());
             }
            
            if(!(hmwrkToDate.equals("")))
             {
                 Date homewrokToDate = new SimpleDateFormat("dd-MM-yyyy").parse(hmwrkToDate);
               
                SimpleDateFormat dateFormatToServer = new SimpleDateFormat("yyyy-MM-dd");
                String strhmwrkDateInFormated = dateFormatToServer.format(homewrokToDate);
                
                hmwrkToDateSQL = dateFormatToServer.parse(strhmwrkDateInFormated);
               
                //SQL Date For Stored Procuder Init
                sqlhmwrkToDate = new java.sql.Date(hmwrkToDateSQL.getTime());
             }
            
            
            
       
       callable=con.getConnection().prepareCall(sql);
       callable.setInt(1, clsId);
       callable.setInt(2, subId);
       callable.setDate(3, sqlhmwrkFromDate);
       callable.setDate(4, sqlhmwrkToDate);
       rs=callable.executeQuery();
      }catch(Exception e){
          
      }
  } 
  
  
  public void fetchStudentDataWithDate(JSONObject homeworkData,String sql){
      try{
          String studentId = homeworkData.getString("StudentId");
            String date = homeworkData.getString("Date");
             String todate = homeworkData.getString("toDate");
            int studId = Integer.parseInt(studentId);
           
            SimpleDateFormat ymd=new SimpleDateFormat("yyyy-mm-dd");
            Date homeworkDateToSQL = null,homeworkDateFromSQL=null;
            java.sql.Date sqlHmwrkFromDate = null,sqlHmwrkToDate=null;
            if(!(date.equals("")))
             {
                 Date attendDateIn = new SimpleDateFormat("dd-MM-yyyy").parse(date);
               
                SimpleDateFormat dateFormatToServer = new SimpleDateFormat("yyyy-MM-dd");
                String strHmwrkDateInFormated = dateFormatToServer.format(attendDateIn);
                
                homeworkDateFromSQL = dateFormatToServer.parse(strHmwrkDateInFormated);
               
                //SQL Date For Stored Procuder Init
                sqlHmwrkFromDate = new java.sql.Date(homeworkDateFromSQL.getTime());
             }
            
            if(!(todate.equals("")))
             {
                 Date attendDateTo = new SimpleDateFormat("dd-MM-yyyy").parse(todate);
               
                SimpleDateFormat dateFormatToServer = new SimpleDateFormat("yyyy-MM-dd");
                String strHmwrkDateInFormatedTo = dateFormatToServer.format(attendDateTo);
                
                homeworkDateToSQL = dateFormatToServer.parse(strHmwrkDateInFormatedTo);
               
                //SQL Date For Stored Procuder Init
                sqlHmwrkToDate = new java.sql.Date(homeworkDateToSQL.getTime());
             }
            
            
       callable=con.getConnection().prepareCall(sql);
       callable.setInt(1, studId);
       callable.setDate(2, sqlHmwrkFromDate);
        callable.setDate(3, sqlHmwrkToDate);
       rs=callable.executeQuery();
      
      }catch(Exception e){
      
          e.printStackTrace();
      }
  }
  
  public void fetchHmwrkFdBckinsrtDta(JSONObject homeworkData,String sql){
      try{
            String homeworkId = homeworkData.getString("HomeworkId");
            int hmwrkId = Integer.parseInt(homeworkId);
            String enteredBy = homeworkData.getString("EnteredBy");
        
            int entrBy = Integer.parseInt(enteredBy);
            String studentId = homeworkData.getString("StudentId");
            int studId = Integer.parseInt(studentId);
            String status = homeworkData.getString("Status");
            int sts = Integer.parseInt(status);
            String hmwrkDate=homeworkData.getString("Date");
            Date hmwrkDateSQL = null;
             java.sql.Date sqlhmwrkDate = null;
             if(!(hmwrkDate.equals("")))
             {
                 Date homewrokDate = new SimpleDateFormat("dd-MM-yyyy").parse(hmwrkDate);
               
                SimpleDateFormat dateFormatToServer = new SimpleDateFormat("yyyy-MM-dd");
                String strhmwrkDateInFormated = dateFormatToServer.format(homewrokDate);
                
                hmwrkDateSQL = dateFormatToServer.parse(strhmwrkDateInFormated);
               
                //SQL Date For Stored Procuder Init
                sqlhmwrkDate = new java.sql.Date(hmwrkDateSQL.getTime());
             }
            
            
            
            callable=con.getConnection().prepareCall(sql);
            callable.setInt(1, 0);
            callable.setInt(2, hmwrkId);
            callable.setDate(3, sqlhmwrkDate);
            callable.setInt(4, entrBy);
            callable.setInt(5, studId);
            callable.setInt(6, sts);
            callable.setInt(7, entrBy);
            callable.setDate(8, sqlhmwrkDate);
     
       effRows=callable.executeUpdate();
  }catch(Exception e){
      e.printStackTrace();
  }
  
}
  
  public void fetchHmwrkFdbckStf(JSONObject homeworkData,String sql){
      try{
          
          String homeworkId = homeworkData.getString("HomeworkId");
          int hmwrkId = Integer.parseInt(homeworkId);  
          
          callable=con.getConnection().prepareCall(sql);
          callable.setInt(1, hmwrkId);
          rs=callable.executeQuery();
      
      }catch(Exception e){
          e.printStackTrace();
      }
      
  }
  
  public void fetchHmwrkInsertData(JSONObject homework,String sql){
      try{
                //String strDate = homeworkData.getString("Date");
               
                String staffId = homeworkData.getString("StaffId");
                int stfId = Integer.parseInt(staffId); 
                String cls = homeworkData.getString("ClassId");
                clsId=Integer.parseInt(cls);
                String subject = homeworkData.getString("SubjectId");
                int subId=Integer.parseInt(subject);
                
                String chapter = homeworkData.getString("Chapter");
                base64Chapter=StringUtil.base64ToString(chapter);
                String description = homeworkData.getString("Description");
                base64Description=StringUtil.base64ToString(description);
                SimpleDateFormat ymd=new SimpleDateFormat("yyyy-mm-dd");
            
                Date homeworkDateToSQL = null;
                java.sql.Date sqlHmwrkDate = null;
            
                /*if(!(strDate.equals("")))
             {
                 Date attendDateIn = new SimpleDateFormat("dd-MM-yyyy").parse(strDate);
               
                SimpleDateFormat dateFormatToServer = new SimpleDateFormat("yyyy-MM-dd");
                String strHmwrkDateInFormated = dateFormatToServer.format(attendDateIn);
                
                homeworkDateToSQL = dateFormatToServer.parse(strHmwrkDateInFormated);
               
                //SQL Date For Stored Procuder Init
                sqlHmwrkDate = new java.sql.Date(homeworkDateToSQL.getTime());
             }*/
                
                
                callable=con.getConnection().prepareCall(sql);
                callable.setInt(1, 0);
                //callable.setDate(2, sqlHmwrkDate);
                callable.setInt(2, stfId);
                callable.setInt(3, clsId);
                callable.setInt(4, subId);
                callable.setString(5,base64Chapter);
                callable.setString(6,base64Description);
                callable.setInt(7, stfId);
                callable.setDate(8, sqlHmwrkDate);
                
                effRows=callable.executeUpdate();
                resId=callable.getInt(9);
      }catch(Exception e){
          e.printStackTrace();
      }
  }
  
  public void fetchClswseHmwFdbckData(JSONObject homeworkData,String sql){
     try{
      int clsId=Integer.parseInt(homeworkData.getString("ClassId"));
       int subId=Integer.parseInt(homeworkData.getString("SubjectId"));        
                callable=con.getConnection().prepareCall(sql);
                callable.setInt(1, clsId);
                callable.setInt(2, subId);
                rs = callable.executeQuery();
  }catch(Exception e){
      e.printStackTrace();
  }
     
  }
  public void fetchHomeworkDetailsById(JSONObject homeworkData,String sql){
     try{
      int hmwrkId = Integer.parseInt(homeworkData.getString("HomeworkId"));
            
             
             callable=con.getConnection().prepareCall(sql);
             callable.setInt(1, hmwrkId);
       
             rs=callable.executeQuery();
             
  }catch(Exception e){
      e.printStackTrace();
  }
  }
  
   public void fetchClswseHmwFdbckDataWithDate(JSONObject homeworkData,String sql){
     try{
      String cls = homeworkData.getString("ClassId");
      int subId=Integer.parseInt(homeworkData.getString("SubjectId"));  
      String hmwrkDate=homeworkData.getString("Date");
            Date hmwrkDateSQL = null;
             java.sql.Date sqlhmwrkDate = null;
             if(!(hmwrkDate.equals("")))
             {
                 Date homewrokDate = new SimpleDateFormat("dd-MM-yyyy").parse(hmwrkDate);
               
                SimpleDateFormat dateFormatToServer = new SimpleDateFormat("yyyy-MM-dd");
                String strhmwrkDateInFormated = dateFormatToServer.format(homewrokDate);
                
                hmwrkDateSQL = dateFormatToServer.parse(strhmwrkDateInFormated);
               
                //SQL Date For Stored Procuder Init
                sqlhmwrkDate = new java.sql.Date(hmwrkDateSQL.getTime());
             }
                int clsId=Integer.parseInt(cls);
                callable=con.getConnection().prepareCall(sql);
                callable.setInt(1, clsId);
                callable.setDate(2, sqlhmwrkDate);
                callable.setInt(3, subId);
                rs = callable.executeQuery();
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
  public String  prntSuccessStatus(JSONArray array){
      try{
          outObject.put("Status", "Success");
          outObject.put("Message", "Data found..");
          outObject.put("Result", array);
          return array.toString();
      }catch(Exception e){
         String exce = e.toString();
            String exc = "{\"Status\":\"Exception\" ,  \"Message\":\""+exce+"\" ,"
                    + " \"Result\":[]}";
            return exc;    
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
   public  String prntFaliureStatus(JSONArray array){
      try{
          outObject.put("Status", "Failure");
          outObject.put("Message", "Data not found..");
          outObject.put("Result", array);
          return array.toString();
      }catch(Exception e ){
          String exce = e.toString();
            String exc = "{\"Status\":\"Exception\" ,  \"Message\":\""+exce+"\" ,"
                    + " \"Result\":[]}";
            return exc;   
      }
  }
   public void closeDBConnection()
    {
        try{
            callable.close();
            }catch(Exception e){
                e.printStackTrace();
            }
    }
  public void fetchHomeworkData(){
      try{
          do
               {
                  JSONObject object = new JSONObject();
                  //String strStudentName = rs.getString("StudentName");
                  String strClass = rs.getString("class");
                  String strchapterName = rs.getString("chapterName");
                  String strSubject = rs.getString("Subject");
                  String strHomeWork = rs.getString("homework");
                  String strDate = rs.getString("Date");
                  String strClassId=rs.getString("ClassId");
                  String strHomeworkId=rs.getString("HomeworkId");
                  String strSubjectId = rs.getString("SubjectId");
                  
                            
                 // object.put("StudentName", strStudentName);
                  object.put("Class", strClass);
                  object.put("ChapterName", strchapterName);
                  object.put("Subject", strSubject);
                  object.put("Date", strDate);
                  object.put("HomeWork", strHomeWork);
                  object.put("ClassId", strClassId);
                  object.put("HomeworkId", strHomeworkId);
                  object.put("SubjectId", strSubjectId);
                  
                  array.put(object);
               }while(rs.next());
      }catch(Exception e)
      {
          e.printStackTrace();
      }
  }
  public void fetchHomeworkDetailsData(){
      try{
          do
               {
                  JSONObject object = new JSONObject();
                  //String strStudentName = rs.getString("StudentName");
                  
                  String strHomeworkId=rs.getString("HomeWorkId");
                  String strDate = rs.getString("Date");
                  String strClassId=rs.getString("ClassId");
                  String strSubjectId = rs.getString("SubjectId");
                  String strStaffId = rs.getString("StaffId");
                  String strSchoolId = rs.getString("SchoolId");
                  String strAcademicYearId = rs.getString("AcademicYearId");
                  String strClass = rs.getString("Class");
                  String strSection=rs.getString("Section");
                  String strDescription = rs.getString("Description");
                  String strSubject = rs.getString("subject");
                  String strchapterName = rs.getString("ChapterName");
                  String strClassSection=rs.getString("ClassSection");
                  
                  object.put("HomeworkId", strHomeworkId);
                  object.put("Date", strDate);
                  object.put("ClassId", strClassId);
                  object.put("SubjectId", strSubjectId);
                  object.put("StaffId", strStaffId);
                  object.put("SchoolId", strSchoolId);
                  object.put("AcademicYearId", strAcademicYearId);
                  object.put("Class", strClass);
                  object.put("Section", strSection);
                  object.put("Description", strDescription);
                  object.put("Subject", strSubject);
                  object.put("ChapterName", strchapterName);
                  object.put("ClassSection", strClassSection);
                  
                  
                  array.put(object);
               }while(rs.next());
      }catch(Exception e)
      {
          e.printStackTrace();
      }
  } 
   public void fetchHomeworkDataLater(){
      try{
          do
               {
                  JSONObject object = new JSONObject();
                  //String strStudentName = rs.getString("StudentName");
                  String strClass = rs.getString("class");
                  String strchapterName = rs.getString("chapterName");
                  String strSubject = rs.getString("Subject");
                  String strHomeWork = rs.getString("homework");
                  String strDate = rs.getString("Date");
                  String strSubjectId = rs.getString("SubjectId");
                            
                 // object.put("StudentName", strStudentName);
                  object.put("Class", strClass);
                  object.put("chapterName", strchapterName);
                  object.put("Subject", strSubject);
                  object.put("Date", strDate);
                  object.put("HomeWork", strHomeWork);
                  object.put("SubjectId", strSubjectId);          
                  array.put(object);
               }while(rs.next());
      }catch(Exception e)
      {
          e.printStackTrace();
      }
  }
  public void fetchHomeworkfeedBack(){
       try{
           do 
               {
                    JSONObject object = new JSONObject();
                    String strHomeworkDate = rs.getString("homeworkDate");
                    String strSubject = rs.getString("Subject");
                    String strClass= rs.getString("class");
                    String strChapterName = rs.getString("ChapterName");
                    //String strDescription= rs.getString("Description");
                    String strFeedbackDate = rs.getString("feedbackDate");
                    String strStudentName= rs.getString("studentname");
                    String strHomework= rs.getString("Homework");
                    String strStatus= rs.getString("status");
                    String strRollNo=rs.getString("RollNo");
                           
                    object.put("homeworkDate", strHomeworkDate);
                    object.put("Subject", strSubject);
                      object.put("FeedbackDate", strFeedbackDate);
                    object.put("StudentName", strStudentName);
                    object.put("Homework", strHomework);
                    object.put("Class", strClass);
                    object.put("RollNo", strRollNo);
                    object.put("ChapterName", strChapterName);
                    object.put("Status", strStatus);
                            
                            
                    array.put(object);
               }while(rs.next());
       }catch(Exception e){
           e.printStackTrace();
       }
   }
    public String HomeWorkSubTypeName()
            {
                
           try
           {
               JSONArray array = new JSONArray();
           // JSONObject homeworkData = new JSONObject(homeworkData);
            String strSchoolId = homeworkData.getString("SchoolId");
            int SchoolId = Integer.parseInt(strSchoolId);
            String sql= "CALL `HomeWorkSubTypeName`(?)";
            
           if(rs.first())
           {
               do
               {
                    JSONObject object = new JSONObject();
                    String strSubType = rs.getString("SubType");
                    object.put("SubType", strSubType);
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
    public String HomeWork_Insert()
            {
                
            try
            {
                String sql =  "CALL `Sp_insert_tblhomework`(?,?,?,?,?,?,?,?,?);";
                fetchHmwrkInsertData(homeworkData, sql);
             
            //String res= rs.getString("ResultId");
           
            if(resId>0)
            {
                //SendSms sendmessage=new SendSms();
               
                //sendmessage.sendMessage(base64Description);
              /*  HttpResponse<String> response = Unirest.post("http://api.msg91.com/api/v2/sendsms?campaign=&response=&afterminutes=&schtime=&unicode=&flash=&message=&encrypt=&authkey=&mobiles=&route=&sender=&country=91")
  .header("authkey", "222059ACFHhJkC5b2dfd12")
  .header("content-type", "application/json")
  .body("{ \"sender\": \"SOCKET\", \"route\": \"4\", \"country\": \"91\", \"sms\": [ { \"message\": \"Message1\", \"to\": [ \"98260XXXXX\", \"98261XXXXX\" ] }, { \"message\": \"Message2\", \"to\": [ \"98260XXXXX\", \"98261XXXXX\" ] } ] }")
  .asString();*/
                 outObject.put("Status", "Success");
                 outObject.put("Message", "Homework added sucessfully");
                 outObject.put("rows effected", effRows);
                 String strMessage=base64Chapter+"\n"+base64Description;
                fcm.sendClasswisePushNotification(clsId,strMessage , " HomeWork ");
                 
                 
            }else if(resId==-1){
            outObject.put("Status","Fail");
            outObject.put("Message","Homework has been already added");
            outObject.put("rows effected",effRows);
            }else{
                 outObject.put("Status", "Fail");
                 outObject.put("Message", " Homework not inserted ");
                 outObject.put("rows effected", effRows);
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
    
    public String editHomework(){
        try
            {
                String sql =  "CALL `Sp_insert_tblhomework`(?,?,?,?,?,?,?,?,?);";
                //fetchHmwrkInsertData(homeworkData, sql);
             
                String strDate = homeworkData.getString("Date");
               int homeworkId=Integer.parseInt(homeworkData.getString("HomeworkId"));
                String staffId = homeworkData.getString("StaffId");
                int stfId = Integer.parseInt(staffId); 
                String cls = homeworkData.getString("ClassId");
                int clsId=Integer.parseInt(cls);
                String subject = homeworkData.getString("SubjectId");
                int subId=Integer.parseInt(subject);
                
                String chapter = homeworkData.getString("Chapter");
               base64Chapter=StringUtil.base64ToString(chapter);
                String description = homeworkData.getString("Description");
                base64Description=StringUtil.base64ToString(description);
                SimpleDateFormat ymd=new SimpleDateFormat("yyyy-mm-dd");
            
                Date homeworkDateToSQL = null;
                java.sql.Date sqlHmwrkDate = null;
            
                if(!(strDate.equals("")))
             {
                 Date attendDateIn = new SimpleDateFormat("dd/MM/yyyy").parse(strDate);
               
                SimpleDateFormat dateFormatToServer = new SimpleDateFormat("yyyy-MM-dd");
                String strHmwrkDateInFormated = dateFormatToServer.format(attendDateIn);
                
                homeworkDateToSQL = dateFormatToServer.parse(strHmwrkDateInFormated);
               
                //SQL Date For Stored Procuder Init
                sqlHmwrkDate = new java.sql.Date(homeworkDateToSQL.getTime());
             }
               
                
                callable=con.getConnection().prepareCall(sql);
                callable.setInt(1,homeworkId);
                
                callable.setInt(2, stfId);
                callable.setInt(3, clsId);
                callable.setInt(4, subId);
                callable.setString(5,base64Chapter);
                callable.setString(6,base64Description);
                callable.setInt(7, stfId);
                callable.setDate(8, sqlHmwrkDate);
                
                effRows=callable.executeUpdate();
                resId=callable.getInt(9);
            //String res= rs.getString("ResultId");
           
            if(resId==-2)
            {
                outObject.put("Status", "Success");
                outObject.put("Message", "Homework edited sucessfully");
                outObject.put("rows effected", effRows);
                 String strMessage=base64Chapter+"\n"+base64Description;
                fcm.sendClasswisePushNotification(clsId,strMessage , " HomeWork update");
                
                
            }else{
                 outObject.put("Status", "Fail");
                 outObject.put("Message", " Homework was not edited");
                 outObject.put("rows effected", effRows);
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
    public String HomeworkDisplay_Staffwise()
    {
          
           try
           {
          
           // JSONObject homeworkData = new JSONObject(homeworkData);
            String staffid = homeworkData.getString("StaffId");
            int stfId = Integer.parseInt(staffid);
            String sql = " CALL `HomeworkDisplay_Staffwise`(?)";
           
           if(rs.first())
           {
               do
               {
                    JSONObject object = new JSONObject();
                            String strClass = rs.getString("Class");
                            String strSection = rs.getString("Section");
                            String strDate= rs.getString("Date");
                            String strSubject= rs.getString("Subject");
                            String strHomework= rs.getString("Homework");
                            object.put("Class", strClass);
                            object.put("Section", strSection);
                            object.put("Date", strDate);
                            object.put("Subject", strSubject);
                            object.put("Homework", strHomework);
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
   public String homeworkDisplayDefault()
   {
       
           try
           {
       
                     
            String sql="CALL sp_and_get_homework(?);";
         
            fetchStudentWithData(homeworkData, sql);
         
            if(rs.first())
             {
               fetchHomeworkDataLater();
              
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
        }
           finally{
            closeDBConnection();
        }
   }
    public String homeworkDisplayBydate()
   {
      
                  try
           {
             String sql=" CALL `sp_and_get_homework_bydate`(?,?,?);";
       
            fetchStudentDataWithDate(homeworkData, sql);
       
           if(rs.first())
           {
               fetchHomeworkDataLater();
               
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
   public String homeworkDisplayClasswise()
   {
       
           try
           {
            String sql = " CALL sp_and_get_classwise_homework(?,?)";
            
            fetchClasswiseDatawithSubject(homeworkData,  sql);
           
            if(rs.first())
           {
               fetchHomeworkData();
               
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
   
   public  String homeworkDisplayClasswiseByDate(){
       try
           {
            String sql = " CALL `sp_and_get_classwise_homework_by_date`(?, ?, ?,?)";
            
            fetchClasswiseDatawithDate(homeworkData,  sql);
           
            if(rs.first())
           {
               fetchHomeworkData();
               
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
   public String getHomeworkDetailsForEdit(){
       try
           {
            String sql = " CALL `Sp_Get_HomeworkDetailsById`(?)";
            
            fetchHomeworkDetailsById(homeworkData,sql);
           
            if(rs.first())
           {
               fetchHomeworkDetailsData();
               
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
   public String getHomeworkDetailsForFeedback(){
       try
           {
            String sql = " CALL `Sp_Get_HomeworkFeedbackDetailsById`(?)";
            
            fetchHomeworkDetailsById(homeworkData,sql);
           
            if(rs.first())
           {
               fetchHomeworkDetailsData();
               
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
   
   /*public String editHomework(){
       try
           {
            String sql = " CALL `Sp_insert_tblhomework`(?, ?, ?)";
            
            fetchClasswiseDatawithDate(homeworkData,  sql);
           
            if(rs.first())
           {
               fetchHomeworkData();
               
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
   }*/
   public String deleteHomework(){
       try
           {
            /*String sql = " CALL `Sp_Delete_HomeworkDetails`(?)";
            String strStatus,strMessage;
             fetchHomeworkDetailsById(homeworkData,sql);
              effRows=callable.executeUpdate();
              //resId=callable.getInt(2);
              int intStatus=effRows;
             if(effRows==0){
             strStatus="Success";
             strMessage="Homework deleted sucessfully..";
             }else {
                  strStatus="Fail";
             strMessage="Homework couldn't be deleted..";
             }
             
              outObject.put("Status", intStatus);
             outObject.put("Message", strMessage);*/
             
            // outObject.put("Result", array);
                outObject.put("Status", "Fail");
                outObject.put("Message", " This feature is no more, please update the app");
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
   public String checkHomeworkFeedbackById(){
       try
           {
            String sql = " CALL `sp_check_homework_feedback_by_id`(?,?)";
            String strStatus,strMessage;
               fetchHomeworkDetailsById(homeworkData, sql);
              int intStatus=callable.getInt(2);
             if(intStatus>0){
             strStatus="Fail";
             strMessage="Homework feedback already added..";
             }else {
                  strStatus="Success";
             strMessage="Homework feedback not added.";
             }
             
              outObject.put("Status", intStatus);
             outObject.put("Message", strMessage);
            // outObject.put("Result", array);
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
   public void fetchHomeworkfeedBackLater(){
       try{
           do 
               {
                    JSONObject object = new JSONObject();
                    String strHomeworkDate = rs.getString("homeworkDate");
                    String strSubject = rs.getString("Subject");
                    String strClass= rs.getString("class");
                    String strChapterName = rs.getString("ChapterName");
                    //String strDescription= rs.getString("Description");
                    String strFeedbackDate = rs.getString("feedbackDate");
                    String strStudentName= rs.getString("studentname");
                    String strHomework= rs.getString("Homework");
                    String strStatus= rs.getString("status");
                   String strRollNo=rs.getString("RollNo");
                           
                    object.put("homeworkDate", strHomeworkDate);
                    object.put("Subject", strSubject);
                      object.put("FeedbackDate", strFeedbackDate);
                    object.put("StudentName", strStudentName);
                    object.put("Homework", strHomework);
                    object.put("Class", strClass);
                    object.put("RollNo", strRollNo);
                    object.put("ChapterName", strChapterName);
                    object.put("Status", strStatus);
                            
                            
                    array.put(object);
               }while(rs.next());
       }catch(Exception e){
           e.printStackTrace();
       }
   }
   
   public String homeworkFeedback_Classwise()
   {
       JSONObject outObject = new JSONObject();
           try
           {
               JSONArray array = new JSONArray();
            //JSONObject homeworkData = new JSONObject(homeworkData);
            String schoolid = homeworkData.getString("SchoolId");
            int sclId = Integer.parseInt(schoolid);
            String clss = homeworkData.getString("Class");
            String section = homeworkData.getString("Section");
            String sql = " CALL `homeworkFeedback_Classwise`(?, ?, ?)";
           
           if(rs.first())
           {
               do
               {
                    JSONObject object = new JSONObject();
                            String strDate = rs.getString("Date");
                            String strClass = rs.getString("Class");
                            String strSection = rs.getString("Section");
                            String strSubject = rs.getString("Subject");
                            String strRollNo = rs.getString("RollNo");
                            String strStudentName = rs.getString("StudentName");
                            String strHomework = rs.getString("Homework");
                            String strStatus = rs.getString("Status");
                            
                            
                            object.put("Date", strDate);
                            object.put("Class", strClass);
                            object.put("Section", strSection);
                            object.put("Subject", strSubject);
                            object.put("RollNo", strRollNo);
                            object.put("StudentName", strStudentName);
                            object.put("Homework", strHomework);
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
   
   public  String homeworkFeedbackstaff(){
       try{
       String sql="CALL `sp_and_get_staff_homework_feedback`(?);";
       fetchHmwrkFdbckStf(homeworkData, sql);
            
           if(rs.first())
           {
               fetchHomeworkfeedBack();
               
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
   
   public String homeworkFeedback_Insert()
   {
       
           try
           {
              
             String sql =  " CALL `Sp_Insert_HomeWorkFeedback`(?,?,?,?,?,?,?,?,?);";
              String homeworkId = homeworkData.getString("HomeworkId");
            int hmwrkId = Integer.parseInt(homeworkId);
            String enteredBy = homeworkData.getString("EnteredBy");
        
            int entrBy = Integer.parseInt(enteredBy);
            String studentId = homeworkData.getString("StudentId");
            int studId = Integer.parseInt(studentId);
            String status = homeworkData.getString("Status");
            int sts = Integer.parseInt(status);
            String hmwrkDate=homeworkData.getString("Date");
            int hmwrkfdbckId=0;
            Date hmwrkDateSQL = null;
             java.sql.Date sqlhmwrkDate = null;
             if(!(hmwrkDate.equals("")))
             {
                 Date homewrokDate = new SimpleDateFormat("dd-MM-yyyy").parse(hmwrkDate);
               
                SimpleDateFormat dateFormatToServer = new SimpleDateFormat("yyyy-MM-dd");
                String strhmwrkDateInFormated = dateFormatToServer.format(homewrokDate);
                
                hmwrkDateSQL = dateFormatToServer.parse(strhmwrkDateInFormated);
               
                //SQL Date For Stored Procuder Init
                sqlhmwrkDate = new java.sql.Date(hmwrkDateSQL.getTime());
             }
            
            
            
            callable=con.getConnection().prepareCall(sql);
           
            callable.setInt(1,0);
            callable.setInt(2, hmwrkId);
            callable.setDate(3, sqlhmwrkDate);
            callable.setInt(4, entrBy);
            callable.setInt(5, studId);
            callable.setInt(6, sts);
            callable.setInt(7, entrBy);
            callable.setDate(8, sqlhmwrkDate);
//            callable.setInt(9,0);
            
            effRows=callable.executeUpdate();
                resId=callable.getInt("ResultId");
             //fetchHmwrkFdBckinsrtDta(homeworkData, sql);
                        
           
            if(resId>0)
            {
                 outObject.put("Status", "Success");
                 outObject.put("Message", "Inserted sucessfully");
                 outObject.put("effected rows",effRows);
            }else if(resId==-1){
                 outObject.put("Status", "Fail");
                 outObject.put("Message", "Homework Has been already added");
            }else {
            outObject.put("Status", "Fail");
                 outObject.put("Message", " Something went wrong");
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
   public String homeworkFeedback_Insert_withJsonArray()
   {
       
           try
           {
              
             
             JSONArray hwfebckArray=homeworkData.getJSONArray("HomeworkDataArray");
             for(int i=0;i<hwfebckArray.length();i++){
              String sql =  " CALL `Sp_Insert_HomeWorkFeedback`(?,?,?,?,?,?,?,?,?)";
                 JSONObject hmfebckObject=hwfebckArray.getJSONObject(i);
             String homeworkId=  hmfebckObject.getString("HomeworkId");
              int hmwrkId = Integer.parseInt(homeworkId);
            String enteredBy = homeworkData.getString("EnteredBy");
        
            int entrBy = Integer.parseInt(enteredBy);
            String studentId = homeworkData.getString("StudentId");
            int studId = Integer.parseInt(studentId);
            String status = homeworkData.getString("Status");
            int sts = Integer.parseInt(status);
            String hmwrkDate=homeworkData.getString("Date");
            int hmwrkfdbckId=0;
            Date hmwrkDateSQL = null;
             java.sql.Date sqlhmwrkDate = null;
             if(!(hmwrkDate.equals("")))
             {
                 Date homewrokDate = new SimpleDateFormat("dd-MM-yyyy").parse(hmwrkDate);
               
                SimpleDateFormat dateFormatToServer = new SimpleDateFormat("yyyy-MM-dd");
                String strhmwrkDateInFormated = dateFormatToServer.format(homewrokDate);
                
                hmwrkDateSQL = dateFormatToServer.parse(strhmwrkDateInFormated);
               
                //SQL Date For Stored Procuder Init
                sqlhmwrkDate = new java.sql.Date(hmwrkDateSQL.getTime());
             }
            
            
            
            callable=con.getConnection().prepareCall(sql);
           
            callable.setInt(1,0);
            callable.setInt(2, hmwrkId);
            callable.setDate(3, sqlhmwrkDate);
            callable.setInt(4, entrBy);
            callable.setInt(5, studId);
            callable.setInt(6, sts);
            callable.setInt(7, entrBy);
            callable.setDate(8, sqlhmwrkDate);
//            callable.setInt(9,0);
            
            effRows=callable.executeUpdate();
                resId=callable.getInt("ResultId");
               
                if(resId==-1){
                 outObject.put("Status", "Fail");
                 outObject.put("Message", "HomeWork has been already added");
          
                 break;
                }
             }
//              String homeworkId = homeworkData.getString("HomeworkId");
//            int hmwrkId = Integer.parseInt(homeworkId);
//            String enteredBy = homeworkData.getString("EnteredBy");
//        
//            int entrBy = Integer.parseInt(enteredBy);
//            String studentId = homeworkData.getString("StudentId");
//            int studId = Integer.parseInt(studentId);
//            String status = homeworkData.getString("Status");
//            int sts = Integer.parseInt(status);
//            String hmwrkDate=homeworkData.getString("Date");
//            int hmwrkfdbckId=0;
//            Date hmwrkDateSQL = null;
//             java.sql.Date sqlhmwrkDate = null;
//             if(!(hmwrkDate.equals("")))
//             {
//                 Date homewrokDate = new SimpleDateFormat("dd-MM-yyyy").parse(hmwrkDate);
//               
//                SimpleDateFormat dateFormatToServer = new SimpleDateFormat("yyyy-MM-dd");
//                String strhmwrkDateInFormated = dateFormatToServer.format(homewrokDate);
//                
//                hmwrkDateSQL = dateFormatToServer.parse(strhmwrkDateInFormated);
//               
//                //SQL Date For Stored Procuder Init
//                sqlhmwrkDate = new java.sql.Date(hmwrkDateSQL.getTime());
//             }
//            
//            
//            
//            callable=con.getConnection().prepareCall(sql);
//           
//            callable.setInt(1,0);
//            callable.setInt(2, hmwrkId);
//            callable.setDate(3, sqlhmwrkDate);
//            callable.setInt(4, entrBy);
//            callable.setInt(5, studId);
//            callable.setInt(6, sts);
//            callable.setInt(7, entrBy);
//            callable.setDate(8, sqlhmwrkDate);
////            callable.setInt(9,0);
//            
//            effRows=callable.executeUpdate();
//                resId=callable.getInt("ResultId");
//             //fetchHmwrkFdBckinsrtDta(homeworkData, sql);
//                        
//           
            if(resId>0)
            {
                 outObject.put("Status", "Success");
                 outObject.put("Message", "Inserted sucessfully");
                 outObject.put("effected rows",effRows);
            }else if(resId==-1){
                 outObject.put("Status", "Fail");
                 outObject.put("Message", "Homework feedback has been already added");
            }else {
            outObject.put("Status", "fail");
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
   
   public String homeworkFeedback_Staffwise()
   {
        JSONObject outObject = new JSONObject();
           try
           {
               JSONArray array = new JSONArray();
          //  JSONObject homeworkData = new JSONObject(homeworkData);
            String staffid = homeworkData.getString("StaffId");
            int stfId = Integer.parseInt(staffid);
            String sql = " CALL `homeworkFeedback_Staffwise`(?)";
//           
//            callable.setInt(1,stfId);
//            ResultSet rs = callable.executeQuery();
           if(rs.first())
           {
               do
               {
                    JSONObject object = new JSONObject();
                            String strDate = rs.getString("Date");
                            String strClass = rs.getString("Class");
                           
                            String strSection= rs.getString("Section");
                            String strSubject= rs.getString("Subject");
                            String strRollNo= rs.getString("RollNo");
                            String strStudentName= rs.getString("StudentName");
                            String strHomework= rs.getString("Homework");
                            String strStatus= rs.getString("Status");
                            object.put("Date", strDate);
                            object.put("Class", strClass);
                            
                            object.put("Section", strSection);
                            object.put("Subject", strSubject);
                            object.put("RollNo", strRollNo);
                            object.put("StudentName", strStudentName);
                            object.put("Homework", strHomework);
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
   
   public String homeworkFeedbackDateWise()
   {
       
           try
           {
             String sql = " CALL `sp_and_get_homework_feedback_bydate`(?,?,?)";
           
            fetchStudentDataWithDate(homeworkData, sql);
            
           if(rs.first())
           {
               fetchHomeworkfeedBackLater();
               
               
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
   public String homeworkFeedback_StudentWise(){
         try
           {
             String sql = " CALL `sp_and_get_homework_feedback`(?)";
           
            fetchStudentWithData(homeworkData, sql);
            
           if(rs.first())
           {
               fetchHomeworkfeedBackLater();
               
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
   public String getStaffhomeworkFeedback(){
        try{ 
       String sql="CALL `sp_and_staff_get_classwise_homework_feedback`(?,?);";
            fetchClswseHmwFdbckData(homeworkData, sql);
            if(rs.first()){
                fetchHomeworkfeedBack();
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
   }catch(Exception e){
  String exce = e.toString();
            String exc = "{\"Status\":\"Exception\" ,  \"Message\":\""+exce+"\" ,"
                    + " \"Result\":[]}";
            return exc;     
   }finally{
            closeDBConnection();
        }
   }
    public String getStaffhomeworkFeedbackDateWise(){
       try{ 
       String sql="CALL `sp_and_staff_get_classwise_homework_feedback_datewise`(?,?,?);";
           fetchClswseHmwFdbckDataWithDate(homeworkData, sql);
         if(rs.first()){
                fetchHomeworkfeedBack();
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
   }catch(Exception e){
  String exce = e.toString();
            String exc = "{\"Status\":\"Exception\" ,  \"Message\":\""+exce+"\" ,"
                    + " \"Result\":[]}";
            return exc;     
   }finally{
            closeDBConnection();
        }
  }
    public String insertHomworkFeedBackWithJSONArray() {
        try {
             int rowEffected=0;
             Timestamp tmpHomeworkSetDate=null;
             JSONArray array= homeworkData.getJSONArray("HomeworkArray");
             for (int i=0; i < array.length(); i++) {
                 String sql = "CALL `Sp_Insert_HomeWorkFeedback`(?,?,?,?,?,?,?,?,?,?)";
                 JSONObject object = array.getJSONObject(i);
                 int homworkId = Integer.parseInt(object.getString("HomeworkId"));
                 int entBy = Integer.parseInt(object.getString("EnteredBy"));
                 int studId = Integer.parseInt(object.getString("StudentId"));
                 int status = Integer.parseInt(object.getString("Status"));
                 int hmwrkfdbckId=0;
                 String hmwrkDate=object.getString("Date");
                 Date hmwrkDateSQL = null;
             java.sql.Date sqlhmwrkDate = null;
             if(!(hmwrkDate.equals("")))
             {
                 Date homewrokDate = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").parse(hmwrkDate);
               
                SimpleDateFormat dateFormatToServer = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String strhmwrkDateInFormated = dateFormatToServer.format(homewrokDate);
                
                hmwrkDateSQL = dateFormatToServer.parse(strhmwrkDateInFormated);
               
                //SQL Date For Stored Procuder Init
                sqlhmwrkDate = new java.sql.Date(hmwrkDateSQL.getTime());
                
                tmpHomeworkSetDate=new Timestamp(homewrokDate.getTime());
             }
             callable=con.getConnection().prepareCall(sql);
           
            callable.setInt(1,0);
            callable.setInt(2, homworkId);
            callable.setTimestamp(3, tmpHomeworkSetDate);
            callable.setInt(4, entBy);
            callable.setInt(5, studId);
            callable.setInt(6, status);
            callable.setInt(7, entBy);
            callable.setTimestamp(8, tmpHomeworkSetDate);            
            
            effRows=callable.executeUpdate();
                resId=callable.getInt(9);
               strUserToken=callable.getString(10);
                if(resId==-1){
                 
                    break;
                }
                 else if (resId>0){
                   
                     rowEffected++;
                     String strStatus;
                     if(status==1){
                         strStatus="done";
                     }
                     else{
                         strStatus="not done";
                     }
                     String strMessage="Your child has "+strStatus+"today's omework";
                     fcm.send_FCM_Notification(strUserToken,strMessage,"Homework feedack");
                }
                else {
                       
                       break;
                }
             }
     if(resId>0)
            {
                 outObject.put("Status", "Success");
                 outObject.put("Message", "Homework feedback added sucessfully");
                 outObject.put("effected rows",rowEffected);
           }else if(resId==-1){
                 outObject.put("Status", "Fail");
                 outObject.put("Message", "Homework feedback has been already added");
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
       
   
}
