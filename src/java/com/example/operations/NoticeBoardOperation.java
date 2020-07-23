/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.operations;
import com.example.db.DBConnect;
import com.example.db.SendSms;
import com.example.db.StringUtil;
import com.example.db.FCM;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import org.json.JSONArray;
import java.util.ArrayList;
import org.json.JSONObject; 
import java.util.Date;  
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.JSONException;
import java.sql.Timestamp;
import static java.nio.charset.StandardCharsets.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;


import java.net.URL;
/**
 *
 * @author basava
 */
public class NoticeBoardOperation {
JSONObject noticeBoardData;

JSONArray array = new JSONArray();
JSONObject outObject = new JSONObject();
DBConnect con=new DBConnect();
CallableStatement callable=null;
ResultSet rs=null;
int resId=0,effRows;
String strUSerToken="",strFcmResponse="";
 FCM fcm= new FCM();
 
public NoticeBoardOperation(JSONObject noticeBoardData) {
        this.noticeBoardData = noticeBoardData;
    }
    
    public void fetchStudentNoticeData(JSONObject noticeBoardData,String sql){
       try{
       
           String studentId = noticeBoardData.getString("StudentId");
       int studId = Integer.parseInt(studentId);
       
       callable=con.getConnection().prepareCall(sql);
       callable.setInt(1, studId);
       rs=callable.executeQuery();
           
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public void fetchStudentNoticeDataWithDate(JSONObject noticeBoardData,String sql){
      try{
          String studentId = noticeBoardData.getString("StudentId");
            String ntcFromDate = noticeBoardData.getString("fromDate");
             String ntcToDate = noticeBoardData.getString("toDate");
            int studId = Integer.parseInt(studentId);
           
            SimpleDateFormat ymd=new SimpleDateFormat("yyyy-mm-dd");
            Date noticeFromDateSQL = null;
            java.sql.Date sqlNtcDate = null;
            if(!(ntcFromDate.equals("")))
             {
                 Date noticeDateFrom = new SimpleDateFormat("dd-MM-yyyy").parse(ntcFromDate);
               
                SimpleDateFormat dateFormatToServer = new SimpleDateFormat("yyyy-MM-dd");
                String strNtcFrmDateInFormated = dateFormatToServer.format(noticeDateFrom);
                
                noticeFromDateSQL = dateFormatToServer.parse(strNtcFrmDateInFormated);
               
                //SQL Date For Stored Procuder Init
                sqlNtcDate = new java.sql.Date(noticeFromDateSQL.getTime());
             }
           
             Date noticeToDateSQL = null;
            java.sql.Date sqlNtcToDate = null;
            if(!(ntcToDate.equals("")))
             {
                 Date noticeDateTo = new SimpleDateFormat("dd-MM-yyyy").parse(ntcToDate);
               
                SimpleDateFormat dateFormatToServer = new SimpleDateFormat("yyyy-MM-dd");
                String strNtcToDateInFormated = dateFormatToServer.format(noticeDateTo);
                
                noticeToDateSQL = dateFormatToServer.parse(strNtcToDateInFormated);
               
                //SQL Date For Stored Procuder Init
                sqlNtcToDate = new java.sql.Date(noticeToDateSQL.getTime());
             }
      
       callable=con.getConnection().prepareCall(sql);
       callable.setInt(1, studId);
       callable.setDate(2, sqlNtcDate);
       callable.setDate(3,sqlNtcToDate);
       rs=callable.executeQuery();
      
      }catch(Exception e){
      
          e.printStackTrace();
      }
  }
    public void fetchSchoolStudentnoticeDataWithDate(JSONObject noticeBoardData,String sql){
        try{
            int schoolId = Integer.parseInt(noticeBoardData.getString("SchoolId"));
            String ntcFromDate = noticeBoardData.getString("fromDate");
            String ntcToDate = noticeBoardData.getString("toDate");
            Timestamp tmpNoticeFromDate=null,tmpNoticeToDate=null;
            if(!(ntcFromDate.equals("")))
             {
                 Date noticeDateFrom = new SimpleDateFormat("dd-MM-yyyy").parse(ntcFromDate);
                 tmpNoticeFromDate= new Timestamp(noticeDateFrom.getTime());
             }
            if(!(ntcToDate.equals("")))
             {
                 Date noticeDateTo = new SimpleDateFormat("dd-MM-yyyy").parse(ntcToDate);
                 tmpNoticeToDate= new Timestamp(noticeDateTo.getTime());
             }
            
       callable=con.getConnection().prepareCall(sql);
       callable.setInt(1, schoolId);
       callable.setTimestamp(2, tmpNoticeFromDate);
       callable.setTimestamp(3,tmpNoticeToDate);
       rs=callable.executeQuery();
            
        }catch(Exception e){
            e.printStackTrace();
        }
        
    }
    public void fetchSchoolStudentNoticeData(JSONObject noticeBoardData,String sql){
         try{
       
           int schoolIdId =  Integer.parseInt(noticeBoardData.getString("SchoolId"));
      
       int acdmcId =Integer.parseInt(noticeBoardData.getString("AcademicYearId"));
      
     
       callable=con.getConnection().prepareCall(sql);
       callable.setInt(1, schoolIdId);
       callable.setInt(2, acdmcId);
       rs=callable.executeQuery();
           
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    public void fetchStaffNoticeData(JSONObject noticeBoardData,String sql){
       try{
       
         int stfId = Integer.parseInt(noticeBoardData.getString("StaffId"));
      
       String acdemicYearId = noticeBoardData.getString("AcademicYearId");
       int acdmcId = Integer.parseInt(acdemicYearId);
      
       callable=con.getConnection().prepareCall(sql);
       callable.setInt(1, stfId);
       callable.setInt(2, acdmcId);
       rs=callable.executeQuery();
           
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
     public void fetchClasswiseParentMessageData(JSONObject noticeBoardData,String sql){
       try{
       
         int clsId = Integer.parseInt(noticeBoardData.getString("ClassId"));
      
       
       
       String strDate = noticeBoardData.getString("Date");
      
        SimpleDateFormat ymd=new SimpleDateFormat("yyyy-mm-dd");
            Date prntMsgDateSQL = null;
            java.sql.Date sqlPrntMsgDate = null;
            if(!(strDate.equals("")))
             {
                 Date noticeDateFrom = new SimpleDateFormat("dd-MM-yyyy").parse(strDate);
               
                SimpleDateFormat dateFormatToServer = new SimpleDateFormat("yyyy-MM-dd");
                String strNtcFrmDateInFormated = dateFormatToServer.format(noticeDateFrom);
                
                prntMsgDateSQL = dateFormatToServer.parse(strNtcFrmDateInFormated);
               
                //SQL Date For Stored Procuder Init
                sqlPrntMsgDate = new java.sql.Date(prntMsgDateSQL.getTime());
             }
       
       
       callable=con.getConnection().prepareCall(sql);
       callable.setInt(1, clsId);
       
       callable.setDate(2,sqlPrntMsgDate);
       rs=callable.executeQuery();
           
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
     
     
      public void fetchSchoolwiseParentMessageData(JSONObject noticeBoardData,String sql){
       try{
       
         int schId = Integer.parseInt(noticeBoardData.getString("SchoolId"));
      
       
       
       String strDate = noticeBoardData.getString("Date");
      
        SimpleDateFormat ymd=new SimpleDateFormat("yyyy-mm-dd");
            Date prntMsgDateSQL = null;
            java.sql.Date sqlPrntMsgDate = null;
            if(!(strDate.equals("")))
             {
                 Date noticeDateFrom = new SimpleDateFormat("dd-MM-yyyy").parse(strDate);
               
                SimpleDateFormat dateFormatToServer = new SimpleDateFormat("yyyy-MM-dd");
                String strNtcFrmDateInFormated = dateFormatToServer.format(noticeDateFrom);
                
                prntMsgDateSQL = dateFormatToServer.parse(strNtcFrmDateInFormated);
               
                //SQL Date For Stored Procuder Init
                sqlPrntMsgDate = new java.sql.Date(prntMsgDateSQL.getTime());
             }
       
       
       callable=con.getConnection().prepareCall(sql);
       callable.setInt(1, schId);
       
       callable.setDate(2,sqlPrntMsgDate);
       rs=callable.executeQuery();
           
        }catch(Exception e){
            e.printStackTrace();
        }
    }
     
     
    
    
    
    
    
   public void fetchStaffNoticeDataWithDate(JSONObject noticeBoardData,String sql){
      try{
            String stafftId = noticeBoardData.getString("StaffId");
            String ntcFromDate = noticeBoardData.getString("fromDate");
            String ntcToDate = noticeBoardData.getString("toDate");
            
            int stfId = Integer.parseInt(stafftId);
            SimpleDateFormat ymd=new SimpleDateFormat("yyyy-mm-dd");
            Date noticeFromDateSQL = null;
            java.sql.Date sqlNtcDate = null;
            
            if(!(ntcFromDate.equals("")))
             {
                 Date noticeDateFrom = new SimpleDateFormat("dd-MM-yyyy").parse(ntcFromDate);
               
                SimpleDateFormat dateFormatToServer = new SimpleDateFormat("yyyy-MM-dd");
                String strNtcFrmDateInFormated = dateFormatToServer.format(noticeDateFrom);
                
                noticeFromDateSQL = dateFormatToServer.parse(strNtcFrmDateInFormated);
               
                //SQL Date For Stored Procuder Init
                sqlNtcDate = new java.sql.Date(noticeFromDateSQL.getTime());
             }
           
             Date noticeToDateSQL = null;
            java.sql.Date sqlNtcToDate = null;
            if(!(ntcToDate.equals("")))
             {
                 Date noticeDateTo = new SimpleDateFormat("dd-MM-yyyy").parse(ntcToDate);
               
                SimpleDateFormat dateFormatToServer = new SimpleDateFormat("yyyy-MM-dd");
                String strNtcToDateInFormated = dateFormatToServer.format(noticeDateTo);
                
                noticeToDateSQL = dateFormatToServer.parse(strNtcToDateInFormated);
               
                //SQL Date For Stored Procuder Init
                sqlNtcToDate = new java.sql.Date(noticeToDateSQL.getTime());
             }
           
       callable=con.getConnection().prepareCall(sql);
       callable.setInt(1, stfId);
       callable.setDate(2, sqlNtcDate);
       callable.setDate(3,sqlNtcToDate);
       rs=callable.executeQuery();
      
      }catch(Exception e){
      
          e.printStackTrace();
      }
  }
   
   
   public void fetchCLassIdWithDate(JSONObject noticeBoardData,String sql){
      try{
            int classId = Integer.parseInt(noticeBoardData.getString("ClassId"));
            String ntcFromDate = noticeBoardData.getString("fromDate");
            String ntcToDate = noticeBoardData.getString("toDate");
            
            
            SimpleDateFormat ymd=new SimpleDateFormat("yyyy-mm-dd");
            Date noticeFromDateSQL = null;
            java.sql.Date sqlNtcDate = null;
            
            if(!(ntcFromDate.equals("")))
             {
                 Date noticeDateFrom = new SimpleDateFormat("dd-MM-yyyy").parse(ntcFromDate);
               
                SimpleDateFormat dateFormatToServer = new SimpleDateFormat("yyyy-MM-dd");
                String strNtcFrmDateInFormated = dateFormatToServer.format(noticeDateFrom);
                
                noticeFromDateSQL = dateFormatToServer.parse(strNtcFrmDateInFormated);
               
                //SQL Date For Stored Procuder Init
                sqlNtcDate = new java.sql.Date(noticeFromDateSQL.getTime());
             }
           
             Date noticeToDateSQL = null;
            java.sql.Date sqlNtcToDate = null;
            if(!(ntcToDate.equals("")))
             {
                 Date noticeDateTo = new SimpleDateFormat("dd-MM-yyyy").parse(ntcToDate);
               
                SimpleDateFormat dateFormatToServer = new SimpleDateFormat("yyyy-MM-dd");
                String strNtcToDateInFormated = dateFormatToServer.format(noticeDateTo);
                
                noticeToDateSQL = dateFormatToServer.parse(strNtcToDateInFormated);
               
                //SQL Date For Stored Procuder Init
                sqlNtcToDate = new java.sql.Date(noticeToDateSQL.getTime());
             }
           
       callable=con.getConnection().prepareCall(sql);
       callable.setInt(1, classId);
       callable.setDate(2, sqlNtcDate);
       callable.setDate(3,sqlNtcToDate);
       rs=callable.executeQuery();
      
      }catch(Exception e){
      
          e.printStackTrace();
      } 
   }
   
   public void fetchStudentIdWithDate(JSONObject noticeBoardData,String sql){
      try{
            int studId = Integer.parseInt(noticeBoardData.getString("StudentId"));
            String ntcFromDate = noticeBoardData.getString("fromDate");
            String ntcToDate = noticeBoardData.getString("toDate");
            
            
            SimpleDateFormat ymd=new SimpleDateFormat("yyyy-mm-dd");
            Date noticeFromDateSQL = null;
            java.sql.Date sqlNtcDate = null;
            
            if(!(ntcFromDate.equals("")))
             {
                 Date noticeDateFrom = new SimpleDateFormat("dd-MM-yyyy").parse(ntcFromDate);
               
                SimpleDateFormat dateFormatToServer = new SimpleDateFormat("yyyy-MM-dd");
                String strNtcFrmDateInFormated = dateFormatToServer.format(noticeDateFrom);
                
                noticeFromDateSQL = dateFormatToServer.parse(strNtcFrmDateInFormated);
               
                //SQL Date For Stored Procuder Init
                sqlNtcDate = new java.sql.Date(noticeFromDateSQL.getTime());
             }
           
             Date noticeToDateSQL = null;
            java.sql.Date sqlNtcToDate = null;
            if(!(ntcToDate.equals("")))
             {
                 Date noticeDateTo = new SimpleDateFormat("dd-MM-yyyy").parse(ntcToDate);
               
                SimpleDateFormat dateFormatToServer = new SimpleDateFormat("yyyy-MM-dd");
                String strNtcToDateInFormated = dateFormatToServer.format(noticeDateTo);
                
                noticeToDateSQL = dateFormatToServer.parse(strNtcToDateInFormated);
               
                //SQL Date For Stored Procuder Init
                sqlNtcToDate = new java.sql.Date(noticeToDateSQL.getTime());
             }
           
       callable=con.getConnection().prepareCall(sql);
       callable.setInt(1, studId);
       callable.setDate(2, sqlNtcDate);
       callable.setDate(3,sqlNtcToDate);
       rs=callable.executeQuery();
      
      }catch(Exception e){
      
          e.printStackTrace();
      } 
       
       
   }
   
    public void fetchSchoolNoticeData(JSONObject noticeBoardData,String sql){
        try {
            int schoolId=Integer.parseInt(noticeBoardData.getString("SchoolId"));
               
       callable=con.getConnection().prepareCall(sql);
       callable.setInt(1, schoolId);
       rs=callable.executeQuery();
        } catch (Exception e) {
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
    
    public void closeDBConnection()
    {
        try{
            callable.close();
            }catch(Exception e){
                e.printStackTrace();
            }
    }
    public void fetchData(){
        try{
         ResultSetMetaData rsmd = rs.getMetaData();
           int columnCount = rsmd.getColumnCount();
           do{
                   JSONObject object= new JSONObject();
                   for(int i=1;i<=columnCount;i++){
                       
                       String data=rsmd.getColumnName(i);
                       
                       String value=rs.getString(i);
                       
                       object.put(data,value);
                   }
                   array.put(object);
               }while(rs.next());
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    public void fetchNoticeDetailsForEdit(){
        try{
             do
               {
                    JSONObject object = new JSONObject();
                            //String strAcademicYearId = rs.getString("AcademicYearId");
                            String strNoticeId = rs.getString("NoticeId");
                            String strSchoolId = rs.getString("SchoolId");
                            String strAcademicYearId = rs.getString("AcademicYearId");
                            String strSentBy = rs.getString("SentBy");
                            String strNoticeCreatedDate = rs.getString("NoticeCreatedDate");
                            String strToWhom = rs.getString("ToWhom");
                            String strNoticeTitleId = rs.getString("NoticeTitleId");
                            String strNoticeTitle = rs.getString("NoticeTitle");
                            String strName = rs.getString("Name");
                            String strNotice = rs.getString("Notice");
                            
//                            String strClass = rs.getString("class")
;
/*                        if(strNoteOnTime==null||strNoteOnTime.isEmpty()){
//                               strNoticeDate=strNoticeDate;
//                            }else {
//                                 strNoticeDate=strNoticeDate+" "+strNoteOnTime;
                            }*/
                            //object.put("AcademicYearId", strAcademicYearId);
                            object.put("NoticeId", strNoticeId);
                            object.put("SchoolId", strSchoolId);
                            object.put("AcademicYearId", strAcademicYearId);
                            object.put("SentBy", strSentBy);
                            object.put("NoticeCreatedDate", strNoticeCreatedDate);
                            object.put("ToWhom", strToWhom);
                            object.put("NoticeTitleId", strNoticeTitleId);
                            object.put("NoticeTitle", strNoticeTitle);
                            object.put("Name", strName);
                            object.put("Notice", strNotice);
                            
                            
                            array.put(object);
               }while(rs.next());
        }catch(Exception e)
        {
            e.printStackTrace();
        }
    }
    public void fetchNoticeData()
    {
        try{
             do
               {
                    JSONObject object = new JSONObject();
                            //String strAcademicYearId = rs.getString("AcademicYearId");
                            String strNoticeCreatedDate = rs.getString("NoticeCreatedDate");
                            String strNoticeTitle = rs.getString("NoticeTitle");
                            String strNotice = rs.getString("Notice");
                            String strNoticeDate = rs.getString("NoticeDate");
                            String strNoteOnTime = rs.getString("NoticeTime");
                            String strNoticeId = rs.getString("NoticeId");
                            
                            if(strNoteOnTime==null||strNoteOnTime.isEmpty()||strNoteOnTime.equalsIgnoreCase("00:00:00")){
                               strNoticeDate=strNoticeDate;
                            }else {
                                 strNoticeDate=strNoticeDate+" "+strNoteOnTime;
                            }
                            
                            object.put("NoticeCreatedDate", strNoticeCreatedDate);
                            object.put("NoticeTitle", strNoticeTitle);
                            object.put("Notice", strNotice);
                            object.put("NoticeDate", strNoticeDate);
                            object.put("NoticeId", strNoticeId);
//                            object.put("Class", strClass);
                            array.put(object);
               }while(rs.next());
        }catch(Exception e)
        {
            e.printStackTrace();
        }
    }
    public void fetchNoticeDataForNoticeBoard()
    {
        try{
             do
               {
                    JSONObject object = new JSONObject();
                            //String strAcademicYearId = rs.getString("AcademicYearId");
                            String strNoticeCreatedDate = rs.getString("NoticeCreatedDate");
                            String strNoticeTitle = rs.getString("NoticeTitle");
                            String strNotice = rs.getString("Notice");
                            String strNoticeDate = rs.getString("NoticeDate");
                            String strNoticeDateTime="";
                            String strNoteOnTime = rs.getString("NoticeTime");
                            String strNoticeId = rs.getString("NoticeId");
                            String strToWhom=rs.getString("ToWhom");
                            String strNoticeTitleId=rs.getString("NoticeTitleId");
                            
                            if(strNoteOnTime==null||strNoteOnTime.isEmpty()||strNoteOnTime.equalsIgnoreCase("00:00:00")||strNoteOnTime.equalsIgnoreCase("")){
                               //strNoticeDate=strNoticeDate;
                               strNoticeDateTime=strNoticeDate;
                               strNoteOnTime="9999";
                            }else {
                                 strNoticeDateTime=strNoticeDate+" "+strNoteOnTime;
                                 
                            }
                            
                            object.put("NoticeCreatedDate", strNoticeCreatedDate);
                            object.put("NoticeTitle", strNoticeTitle);
                            object.put("Notice", strNotice);
                            object.put("NoticeDate", strNoticeDateTime);
                            object.put("NoticeId", strNoticeId);
                            object.put("ToWhom",strToWhom);
                            object.put("NoticeEditDate",strNoticeDate);
                            object.put("NoticeEditTime",strNoteOnTime);
                            object.put("NoticeTitleId",strNoticeTitleId);
//                            object.put("Class", strClass);
                            array.put(object);
               }while(rs.next());
        }catch(Exception e)
        {
            e.printStackTrace();
        }
    }
    
    public void fetchParentMessage()
    {
        try{
             do
               {
                    JSONObject object = new JSONObject();
                            String strParentMessageId = rs.getString("parent_message_id");
                            String strStudentName = rs.getString("student_name");
                            String strclass_id = rs.getString("class_id");
                            String strClass = rs.getString("class");
                            String strschool_id = rs.getString("school_id");
                            String strNoticeDateTime="";
                            String strAcademic_year_id = rs.getString("academic_year_id");
                            String strStudent_id = rs.getString("student_id");
                            String strAddedDate=rs.getString("added_date");
                           
                             String strQuery = rs.getString("query");
                            String strReply=rs.getString("reply");
                            String strReplyDate=rs.getString("reply_date");
                            
                            
                            
                            object.put("StudentName", strStudentName);
                            object.put("ClassId", strclass_id);
                            object.put("Class", strClass);
                            
                            object.put("StudentId", strStudent_id);
                            object.put("AddedDate",strAddedDate);
                            object.put("SchoolId",strschool_id);
                            object.put("AcademicYearId",strAcademic_year_id);
                            object.put("ParentMessageId",strParentMessageId);
                            object.put("Query",strQuery);
                            object.put("Reply",strReply);
                            object.put("ReplyDate",strReplyDate);
                            
//                            object.put("Class", strClass);
                            array.put(object);
               }while(rs.next());
        }catch(Exception e)
        {
            e.printStackTrace();
        }
    }
    
    public void fetchClassNoticeDataForNoticeBoard()
    {
        try{
             do
               {
                    JSONObject object = new JSONObject();
                            //String strAcademicYearId = rs.getString("AcademicYearId");
                            String strNoticeId = rs.getString("NoticeId");
                            String strSchoolId = rs.getString("SchoolId");
                            String strAcademicYearId = rs.getString("AcademicYearId");
                            String strClassId = rs.getString("ClassId");
                            String strNoticeDateTime=rs.getString("NoticeDateTime");
                            
                            String strNoticeCreatedDate = rs.getString("NoticeCreatedDate");
                            String strClass=rs.getString("Class");
                            String strNotice=rs.getString("Notice");
                            String strNoticeDate=rs.getString("NoticeDate");
                            String strNoticeTime=rs.getString("NoticeTime");
                            
                            String strNoticeTitle=rs.getString("NoticeTitle");
                            String strNoticeTitleId=rs.getString("NoticeTitleId");
                            String strSentBy=rs.getString("SentBy");
                           
                            if(strNoticeTime==null||strNoticeTime.isEmpty()||strNoticeTime.equalsIgnoreCase("00:00:00")||strNoticeTime.equalsIgnoreCase("")){
                               //strNoticeDate=strNoticeDate;
                               //strNoticeDateTime=strNoticeDate;
                               strNoticeTime="9999";
                            }
                            
                            object.put("NoticeId", strNoticeId);
                            object.put("SchoolId", strSchoolId);
                            object.put("AcademicYearId", strAcademicYearId);
                            object.put("ClassId", strClassId);
                            object.put("NoticeCreatedDate", strNoticeCreatedDate);
                            object.put("Class",strClass);
                            object.put("Notice",strNotice);
                            object.put("NoticeDate",strNoticeDate);
                            object.put("NoticeTime",strNoticeTime);
                            object.put("NoticeDateTime",strNoticeDateTime);
                            object.put("NoticeTitle",strNoticeTitle);
                            object.put("NoticeTitleId",strNoticeTitleId);
                            object.put("SentBy", strSentBy);
                            array.put(object);
               }while(rs.next());
        }catch(Exception e)
        {
            e.printStackTrace();
        }
    } 
public String noticeDisplay()
{
     
           try
           {
             
            String sql = "CALL sp_and_get_notice(?,?)";
               fetchStudentWithAcademicYEar(noticeBoardData, sql);
          
           if(rs.first())
           {
              fetchNoticeData();
              
              
            if(array.length()!=0){
               printSuccessStatus(array);
               }else{
                   printFaliureStatus(array);
               }               
           }
           
          else {
                        
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

public  String noticeDisplay_ByDate()
{
   
           try
           {
            
            String sql = "   CALL sp_and_get_notice_by_date(?, ?, ?)";
               fetchStudentNoticeDataWithDate(noticeBoardData, sql);
           
           if(rs.first())
           {
               fetchNoticeData();
               
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

public String getTeacherNote(){
   try
           {
            
            String sql = "   CALL sp_and_get_teachersnote(?,?)";
               fetchStaffNoticeData(noticeBoardData, sql);
           
           if(rs.first())
           {
               //fetchNoticeData();
               fetchNoticeDataForNoticeBoard();
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
public String getStaffListBySchoolId(){
   try
           {
            
            String sql = "CALL `Sp_Get_StaffList`(?)";
           fetchSchoolNoticeData(noticeBoardData, sql );
            
           if(rs.first())
           {
               JSONObject object1= new JSONObject();
               object1.put("StaffDetailsId", "0");              
               object1.put("StaffId", "StaffId");
               object1.put("Name", "Select Staff");
               array.put(object1);
                do
               {
                    JSONObject object = new JSONObject();
                            String strStaffDetailsId = rs.getString("StaffDetailsId");
                            String strStaffId = rs.getString("StaffId");
                            String strName = rs.getString("Name");
                   
                            object.put("StaffDetailsId", strStaffDetailsId);              
                            object.put("StaffId", strStaffId);
                            object.put("Name", strName);
                            array.put(object);
               }while(rs.next());
                         outObject.put("Status", "Success");
                         outObject.put("Message", "Data found..");
                         outObject.put("Result", array);
                }  else {
               JSONObject object1= new JSONObject();
               object1.put("StaffDetailsId", "0");              
               object1.put("StaffId", "StaffId");
               object1.put("Name", "Select Staff");
               array.put(object1);
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
public String getTeachersNoteIdBySchoolId(){
    try{ 
    String sql = "CALL Sp_Dropdown_teachersnoteIdMaster(?)";
           fetchSchoolNoticeData(noticeBoardData, sql );
            
           if(rs.first())
           {
               
                do
               {
                    JSONObject object = new JSONObject();
                            String strTeacherNoteTitleId = rs.getString("TeacherNoteTitleId");
                            String strSchoolId = rs.getString("SchoolId");
                            String strTeacherNoteTitle = rs.getString("TeacherNoteTitle");
                   
                            object.put("TeacherNoteTitleId", strTeacherNoteTitleId);              
                            object.put("SchoolId", strSchoolId);
                            object.put("TeacherNoteTitle", strTeacherNoteTitle);
                            array.put(object);
               }while(rs.next());
                         printSuccessStatus(array);
                }  else {
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

public String getNoticeIdBySchoolId(){
    try{ 
    String sql = "CALL Sp_Dropdown_tblNoticeTitleIdMaster(?)";
           fetchSchoolNoticeData(noticeBoardData, sql );
            
           if(rs.first())
           {
               
                do
               {
                    JSONObject object = new JSONObject();
                            String strNoticeTitleId = rs.getString("NoticeTitleId");
                            String strSchoolId = rs.getString("SchoolId");
                            String strNoticeTitle= rs.getString("NoticeTitle");
                   
                            object.put("NoticeTitleId", strNoticeTitleId);              
                            object.put("SchoolId", strSchoolId);
                            object.put("NoticeTitle", strNoticeTitle);
                            array.put(object);
               }while(rs.next());
                         printSuccessStatus(array);
                }  else {
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

public String getClassNoticeIdBySchoolId(){
    try{ 
    String sql = "CALL sp_get_class_notice_title_by_schoolid(?)";
           fetchSchoolNoticeData(noticeBoardData, sql );
            
           if(rs.first())
           {
               
                do
               {
                    JSONObject object = new JSONObject();
                            String strNoticeTitleId = rs.getString("ClassNoteTitleId");
                            
                            String strNoticeTitle= rs.getString("ClassNoteTitle");
                   
                            object.put("ClassNoticeTitleId", strNoticeTitleId);              
                            
                            object.put("ClassNoticeTitle", strNoticeTitle);
                            array.put(object);
               }while(rs.next());
                         printSuccessStatus(array);
                }  else {
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
public String getStaffReminderIdBySchoolId(){
    try{ 
    String sql = "CALL Sp_Get_tblStaffReminderTitleList(?)";
           fetchSchoolNoticeData(noticeBoardData, sql );
            
           if(rs.first())
           {
               
                do
               {
                    JSONObject object = new JSONObject();
                            String strReminderTitleId = rs.getString("ReminderTitleId");
                            String strSchoolId = rs.getString("SchoolId");
                            String strReminderTitle= rs.getString("ReminderTitle");
                   
                            object.put("ReminderTitleId", strReminderTitleId);              
                            object.put("SchoolId", strSchoolId);
                            object.put("ReminderTitle", strReminderTitle);
                            array.put(object);
               }while(rs.next());
                         printSuccessStatus(array);
                }  else {
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
public String getStudentReminderIdBySchoolId(){
    try{ 
    String sql = "CALL Sp_Get_tblStudentRemainderTiles(?)";
           fetchSchoolNoticeData(noticeBoardData, sql );
            
           if(rs.first())
           {
               
                do
               {
                    JSONObject object = new JSONObject();
                            String strReminderTitleId = rs.getString("ReminderTitleId");
                            String strSchoolId = rs.getString("SchoolId");
                            String strReminderTitle= rs.getString("ReminderTitle");
                   
                            object.put("ReminderTitleId", strReminderTitleId);              
                            object.put("SchoolId", strSchoolId);
                            object.put("ReminderTitle", strReminderTitle);
                            array.put(object);
               }while(rs.next());
                         printSuccessStatus(array);
                }  else {
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
public String getTeacherNoteByDate(){
    try
           {
            
            String sql = "   CALL sp_and_get_teacher_note_datewise(?,?,?)";
               fetchStaffNoticeDataWithDate(noticeBoardData, sql);
           
           if(rs.first())
           {
               //fetchNoticeData();
               fetchNoticeDataForNoticeBoard();
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

public  String insertTeacherNote(){
    try{
         JSONArray array= noticeBoardData.getJSONArray("TeacherNoteArray");
         int rowCount=0,objLength=0;
             for (int i=0; i < array.length(); i++) {
               Timestamp tmpNoteSent=null,tmpNoteOn=null;
                 String sql = "CALL `Sp_Insert_tblteachersNote`(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
                 JSONObject object = array.getJSONObject(i);
                 int schoolId = Integer.parseInt(object.getString("SchoolId"));
                 int academicYearId = Integer.parseInt(object.getString("AcademicYearId"));
                 int enteredBy = Integer.parseInt(object.getString("EnteredBy"));
                 
                 String strNoteSentDate= object.getString("NoteSentDate");
                 int stfId = Integer.parseInt(object.getString("SentTo"));
                 int ttlId = Integer.parseInt(object.getString("NoteTitleId"));
                 String strOtherTitle=object.getString("OtherNoteTitle");
                 String base64_OtherTitle=StringUtil.base64ToString(strOtherTitle);
                 String strNote=object.getString("Note");
                 String base64Note=StringUtil.base64ToString(strNote);
                 String strNoteOnDate= object.getString("NoteOnDate");
                  String strNoteOnTime= object.getString("NoticeTime");
                objLength=object.length();
                 
                 Date noteSentDateSQL = null,noteOnDateSql=null;
             java.sql.Date sqlNoteSentDate = null,sqlNoteOnDate=null;
           
             
             if(!(strNoteSentDate.equals("")))
             {
                 Date noteSentDate = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss").parse(strNoteSentDate);
               tmpNoteSent= new Timestamp(noteSentDate.getTime());
                 
//                SimpleDateFormat dateFormatToServer = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
//                String strNoteSentDateInFormated = dateFormatToServer.format(noteSentDate);
//                
//                noteSentDateSQL = dateFormatToServer.parse(strNoteSentDateInFormated);
//               
//                //SQL Date For Stored Procuder Init
//                sqlNoteSentDate = new java.sql.Date(noteSentDateSQL.getTime());
             }
              if(!(strNoteOnDate.equals("")))
             {
                 Date noteOnDate = new SimpleDateFormat("dd-MM-yyyy").parse(strNoteOnDate);
               tmpNoteOn=new Timestamp(noteOnDate.getTime());
                 
                SimpleDateFormat dateFormatToServer = new SimpleDateFormat("dd-MM-yyyy");
                String strNoteOnDateInFormated = dateFormatToServer.format(noteOnDate);
                
                noteOnDateSql = dateFormatToServer.parse(strNoteOnDateInFormated);
//              SQL Date For Stored Procuder Init
                sqlNoteOnDate = new java.sql.Date(noteOnDateSql.getTime());
             }
             callable=con.getConnection().prepareCall(sql);
           
            callable.setInt(1,0);
            callable.setInt(2, schoolId);
             callable.setInt(3, academicYearId);
             callable.setInt(4,enteredBy);
            callable.setTimestamp(5, tmpNoteSent);
            callable.setInt(6, stfId);
            
            callable.setInt(7,ttlId);
            if(strOtherTitle.equalsIgnoreCase("")){
                callable.setString(8,null);
            }else{
            callable.setString(8,base64_OtherTitle);
            }
            callable.setString(9,base64Note);
            callable.setDate(10,sqlNoteOnDate);
            if(strNoteOnTime.equalsIgnoreCase("-9999")){
                callable.setString(11,null);
            }else{
            callable.setString(11,strNoteOnTime);     
            }
           
             callable.setInt(12,enteredBy);
             callable.setTimestamp(13, tmpNoteSent);
//            callable.setInt(9,0);
            
            effRows=callable.executeUpdate();
                resId=callable.getInt(14);
               strUSerToken=callable.getString(15);
                if(resId==-1){
                 
                    break;
                }
                else if (resId>0){
                    
                    rowCount++;
                    FCM.FCMResponse fcmresp= fcm.send_FCM_Notification(strUSerToken, base64Note, "Teacher's note");
                    System.out.println("response from server "+fcmresp.getResponse());
                }
                else {
                
                         break;
                }
             }
     if(resId>0)
            {
                 outObject.put("Status", "Success");
                 outObject.put("object Length",objLength);
                 outObject.put("Message", "Teacher's note inserted sucessfully");
                 outObject.put("effected rows",rowCount);
            }else if(resId==-1){
                 outObject.put("Status", "Fail");
                 outObject.put("Message", "Teacher's note has been already added");
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
public String getTeacherNoteById(){
    try{
    String sql="CALL `Sp_Get_tblteachersNoteById`(?);";
    fetchNoticeDetailsById(noticeBoardData,sql);
    
    
    if(rs.first())
           {
              try{
             do
               {
                    JSONObject object = new JSONObject();
                            //String strAcademicYearId = rs.getString("AcademicYearId");
                            String strTeachersNoteId = rs.getString("TeachersNoteId");
                            String strSchoolId = rs.getString("SchoolId");
                            String strSentBy = rs.getString("SentBy");
                            String strAcademicYearId = rs.getString("AcademicYearId");
                            String strNoteSentDate = rs.getString("NoteSentDate");
                            String strNoteTitle = rs.getString("NoteTitle");
                            
                            String strSentTo = rs.getString("SentTo");
                            String strNoteOtherTitle=rs.getString("NoteOtherTitle");
                            String strNotice = rs.getString("Note");
                            String strNoteOnDate = rs.getString("NoteOnDate");
                            String strNoteOnTime=rs.getString("NoteOnTime");
                            String strNoteDateTime=rs.getString("NoteDateTime");
                            String strSentToName= rs.getString("SentToName");
                            
                            
//                            String strClass = rs.getString("class")
;
/*                        if(strNoteOnTime==null||strNoteOnTime.isEmpty()){
//                               strNoticeDate=strNoticeDate;
//                            }else {
//                                 strNoticeDate=strNoticeDate+" "+strNoteOnTime;
                            }*/
                            //object.put("AcademicYearId", strAcademicYearId);
                            object.put("TeacherNoteId", strTeachersNoteId);
                            object.put("SchoolId", strSchoolId);
                            object.put("AcademicYearId", strAcademicYearId);
                            object.put("SentBy", strSentBy);
                            object.put("NoticeCreatedDate", strNoteSentDate);
                            object.put("ToWhom", strSentTo);
                            object.put("NoticeOtherTitle", strNoteOtherTitle);
                            object.put("NoteTitle", strNoteTitle);
                            object.put("Name", strSentTo);
                            object.put("Notice", strNotice);
                            
                            
                            array.put(object);
               }while(rs.next());
        }catch(Exception e)
        {
            e.printStackTrace();
        }
              
              
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
public String deleteTeacherNoteById(){
    try{
    /*String sql="CALL `Sp_Delete_tblTeachersNote`(?);";
    fetchNoticeDetailsById(noticeBoardData,sql);
    effRows=callable.executeUpdate();
    String strStatus="", strMessage="";
    //if(effRows>0)
           if(effRows==0){
             strStatus="Success";
             strMessage="Teacher's Note deleted successfully..";
             }else {
             
             strStatus="Fail";
             strMessage="Teacher's Note couldn't be deleted..";
             
           }
           outObject.put("Status",strStatus);
           outObject.put("Message",strMessage);*/
            
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

public String editTeachersNoteById(){
    try{
        int rowCount=0,objLength=0;
        Timestamp tmpNoteSent=null,tmpNoteOn=null;
   String sql = "CALL `Sp_Insert_tblteachersNote`(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
                 
                 int noticeId = Integer.parseInt(noticeBoardData.getString("NoticeId"));   
                 int schoolId = Integer.parseInt(noticeBoardData.getString("SchoolId"));
                 int academicYearId = Integer.parseInt(noticeBoardData.getString("AcademicYearId"));
                 int enteredBy = Integer.parseInt(noticeBoardData.getString("SetBy"));
                 
                 String strNoteSentDate= noticeBoardData.getString("NoteSentDate");
                 int stfId = Integer.parseInt(noticeBoardData.getString("SentTo"));
                 int ttlId = Integer.parseInt(noticeBoardData.getString("NoteTitleId"));
                 String base64_OtherTitle="";
                 if(ttlId==1){
                 String strOtherTitle=noticeBoardData.getString("OtherNoteTitle");
                 base64_OtherTitle=StringUtil.base64ToString(strOtherTitle);    
                 }
                 
                 String strNote=noticeBoardData.getString("Notice");
                 String base64Note=StringUtil.base64ToString(strNote);
                 String strNoteOnDate= noticeBoardData.getString("NoteOnDate");
                 objLength=noticeBoardData.length();
                 
                String strNoteOnTime= noticeBoardData.getString("NoticeTime");
                                 
                 Date noteSentDateSQL = null,noteOnDateSql=null;
             java.sql.Date sqlNoteSentDate = null,sqlNoteOnDate=null;
           
             
             if(!(strNoteSentDate.equals("")))
             {
                 Date noteSentDate = new SimpleDateFormat("dd/MM/yyyy").parse(strNoteSentDate);
               tmpNoteSent= new Timestamp(noteSentDate.getTime());
                 
//                SimpleDateFormat dateFormatToServer = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
//                String strNoteSentDateInFormated = dateFormatToServer.format(noteSentDate);
//                
//                noteSentDateSQL = dateFormatToServer.parse(strNoteSentDateInFormated);
//               
//                //SQL Date For Stored Procuder Init
//                sqlNoteSentDate = new java.sql.Date(noteSentDateSQL.getTime());
             }
              if(!(strNoteOnDate.equals("")))
             {
                 Date noteOnDate = new SimpleDateFormat("dd/MM/yyyy").parse(strNoteOnDate);
               tmpNoteOn=new Timestamp(noteOnDate.getTime());
                 
                SimpleDateFormat dateFormatToServer = new SimpleDateFormat("dd-MM-yyyy");
                String strNoteOnDateInFormated = dateFormatToServer.format(noteOnDate);
                
                noteSentDateSQL = dateFormatToServer.parse(strNoteOnDateInFormated);
//              SQL Date For Stored Procuder Init
                sqlNoteOnDate = new java.sql.Date(noteSentDateSQL.getTime());
             }
             callable=con.getConnection().prepareCall(sql);
           
            callable.setInt(1,noticeId);
            callable.setInt(2, schoolId);
             callable.setInt(3, academicYearId);
             callable.setInt(4,enteredBy);
            callable.setTimestamp(5, tmpNoteSent);
            callable.setInt(6, stfId);
            
            callable.setInt(7,ttlId);
            if(ttlId!=1){
             callable.setString(8,null);
            }else{
            callable.setString(8,base64_OtherTitle);
            }
            callable.setString(9,base64Note);
            callable.setDate(10,sqlNoteOnDate);
            
            if(strNoteOnTime.equalsIgnoreCase("9999")){
            callable.setString(11,null);
            }else{
                callable.setString(11,strNoteOnDate);
            }
            callable.setInt(12,enteredBy);
            callable.setTimestamp(13, tmpNoteSent);
//            callable.setInt(9,0);
            
            effRows=callable.executeUpdate();
                resId=callable.getInt(14);
               strUSerToken=callable.getString(15);
                if(resId==-2){
                 outObject.put("Status", "Success");
                 outObject.put("Message", "Teacher's note edited sucessfully");
                 outObject.put("effected rows",rowCount);
                 fcm.send_FCM_Notification(strUSerToken, base64Note, "Teacher's note update");
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
public void fetchNoticeDetailsById(JSONObject noticeBoardData,String sql){
    try{
    int  ntcId = Integer.parseInt(noticeBoardData.getString("Id"));
   
       
    callable=con.getConnection().prepareCall(sql);
    callable.setInt(1, ntcId);
    
    rs=callable.executeQuery();
}catch(Exception e){
    e.printStackTrace();
}
}

public void fetchStudentWithAcademicYEar(JSONObject noticeBoardData,String sql){
    try{
         String studentId = noticeBoardData.getString("StudentId");
         int studId = Integer.parseInt(studentId);
         String academicId = noticeBoardData.getString("AcademicYearId");
         int acdId = Integer.parseInt(academicId);
         
         callable=con.getConnection().prepareCall(sql);
         callable.setInt(1, studId);
         callable.setInt(2, acdId);
         rs=callable.executeQuery();
         
    }catch(Exception e){
        e.printStackTrace();
    }
}


public void fillReminderData()
 {
 try{   
      do
               {
                    JSONObject object = new JSONObject();
                            String strAcademicYearId = rs.getString("AcademicYearId");
                            String strReminderSetDate = rs.getString("remindersetdate");
                            String strReminderDate= rs.getString("ReminderDate");
                            String strReminderTitle= rs.getString("remindertitle");
                            String strReminderDateTime="";
                            String strReminder= rs.getString("Reminder");
                            String strReminderId=rs.getString("ReminderId");
                            String strReminderTime= rs.getString("ReminderTime");
                            String strSentTo=rs.getString("SentTo");
                            String strReminderTitleId=rs.getString("ReminderTitleId");
                            if(strReminderTime==null||strReminderTime.isEmpty()||strReminderTime.equalsIgnoreCase("00:00:00")){
                            strReminderDateTime=strReminderDate;
                            strReminderTime="";
                            }else {
                                strReminderDateTime=strReminderDate+" "+strReminderTime;
                            }
//                            String strStudentname= rs.getString("studentname");
//                            String strClass= rs.getString("class");
                            object.put("AcademicYearId", strAcademicYearId);
                            object.put("ReminderSetDate", strReminderSetDate);
                            object.put("ReminderDate", strReminderDateTime);
                            object.put("ReminderTitle", strReminderTitle);
                            object.put("Reminder", strReminder);
                            object.put("ReminderTitleId",strReminderTitleId);
                            object.put("ReminderId",strReminderId);
                            object.put("SentTo",strSentTo);
                            object.put("ReminderEditDate",strReminderDate);
                            object.put("ReminderEditTime",strReminderTime);
//                            object.put("Studentname", strStudentname);
//                            object.put("Class", strClass);
                            
                            array.put(object);
               }while(rs.next());
 }catch(Exception e ){
     e.printStackTrace();
     }
 
 }

public String studentReminderDisplayByDate()
    {
        
//      
    try {
              String sql= "CALL sp_and_get_student_reminder_by_date(?, ?, ?)";
              fetchStudentNoticeDataWithDate(noticeBoardData, sql);
//         
                 if(rs.first())
                 {
                       fillReminderData();
                      
                     if(array.length()!=0){
                         printSuccessStatus(array);
                     }else {
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

public String staffReminderDisplay(){
    try
                {
                   
                 String sql = "CALL sp_and_get_staff_reminder(?,?)";
                 // String sql= "CALL sp_and_get_staffreminder(?,?)";  
                    fetchStaffNoticeData(noticeBoardData, sql);
                if(rs.first())
                {
                       fillReminderData();
                      
                     if(array.length()!=0){
                         
                         printSuccessStatus(array);
                     
                     }else {
                      
                         printFaliureStatus(array);
                     
                     }
           }else {
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
public String staffReminderDisplayByDate(){
    try {
              String sql= "CALL sp_and_get_staffreminder_by_date(?, ?, ?)";
              fetchStaffNoticeDataWithDate(noticeBoardData, sql);
//         
                 if(rs.first())
                 {
                       fillReminderData();
                      
                     if(array.length()!=0){
                         printSuccessStatus(array);
                     }else {
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
        
        
   

public String getStaffNotice(){
    try{
    String sql="CALL `sp_and_get_staff_notice`(?, ?);";
    fetchStaffNoticeData(noticeBoardData, sql);
    if(rs.first())
           {
              fetchNoticeDataForNoticeBoard();
              
              
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
public String getStaffStudentNotice(){
    try{
    String sql="CALL `sp_and_staff_get_student_notice`(?, ?);";
    fetchSchoolStudentNoticeData(noticeBoardData, sql);
    if(rs.first())
           {
              //fetchNoticeData();
               //fetchData();
              fetchNoticeDataForNoticeBoard();
              
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
public String getStaffStudentNoticeByDate(){
    try{
    String sql="CALL `sp_and_staff_get_student_notice_by_date`(?,?,?);";
    fetchSchoolStudentnoticeDataWithDate(noticeBoardData, sql);
    if(rs.first())
           {
             // fetchNoticeData();
              //fetchData();
              fetchNoticeDataForNoticeBoard();
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

public String getNoticeDetailsById(){
    try{
    String sql="CALL `Sp_Get_tblnoticeBoardDetailsById`(?);";
    fetchNoticeDetailsById(noticeBoardData,sql);
    
    
    if(rs.first())
           {
              fetchNoticeDetailsForEdit();
              
              
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
public String deleteNoticeById(){
    try{
  /*  String sql="CALL `Sp_Delete_tblNoticeBoardById`(?);";
    fetchNoticeDetailsById(noticeBoardData,sql);
   effRows=callable.executeUpdate();
   // String strStatus="", strMessage="";
    //effRows=Integer.parseInt(rs.toString());
        

           if(effRows==0){
             String strStatus="Success";
             String strMessage="Notice deleted successfully..";
           outObject.put("Status",strStatus);
           outObject.put("Message",strMessage);
           }else {
             
             String strStatus="Fail";
            String strMessage="Notice couldn't be deleted..";
           outObject.put("Status",strStatus);
           outObject.put("Message",strMessage);  
           } */
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

public String editNoticeById(){
    try{
        Timestamp tmpNoticeSetDate=null,tmpNoticeOnDate=null;
    //String sql="call Sp_Insert_tblNoticeBoard`(?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
    String sql="CALL `Sp_Insert_tblNoticeBoard`(?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
//    fetchNoticeDetailsById(noticeBoardData,sql);
            int noticeId=Integer.parseInt(noticeBoardData.getString("NoticeId"));
            
            int sclId = Integer.parseInt(noticeBoardData.getString("SchoolId"));
            int acmdid =Integer.parseInt(noticeBoardData.getString("AcademicYearId"));
            int stby = Integer.parseInt(noticeBoardData.getString("SetBy"));
            String strNoteSentDate = noticeBoardData.getString("NoticeSetDate");
            String toWhom = noticeBoardData.getString("ToWhom");
            int ntcTtlId = Integer.parseInt(noticeBoardData.getString("NoticeTitle"));
            String ntcOtherTitle=noticeBoardData.getString("OtherTitle");
             

                String notice= noticeBoardData.getString("Notice");
           String base64OtherTitle=StringUtil.base64ToString(ntcOtherTitle);
             String base64Notice= StringUtil.base64ToString(notice);
            // System.out.println("com.example.operations.NoticeBoardOperation: Notice value: "+textNotice);
            
             String strNoteOnDate = noticeBoardData.getString("NoticeDate");
             String strNoteOnTime = noticeBoardData.getString("NoticeTime");
              Date noteSentDateSQL = null,noteOnDateSql=null;
             java.sql.Date sqlNoteSentDate = null,sqlNoteOnDate=null;
            
                if(!(strNoteSentDate.equals("")))
             {
                 Date noteSentDate = new SimpleDateFormat("dd/MM/yyyy").parse(strNoteSentDate);
               
                tmpNoticeSetDate=new Timestamp(noteSentDate.getTime());
                
               
             }
               
               //sqlNoteOnDate = new java.sql.Date(noteOnDateSql.getTime());
              if(!(strNoteOnDate.equals("")))
             {
                 Date noteOnDate = new SimpleDateFormat("dd/MM/yyyy").parse(strNoteOnDate);
               tmpNoticeOnDate=new Timestamp(noteOnDate.getTime());
              
             }
          //  
            callable=con.getConnection().prepareCall(sql);
           // callable=con.getConnection().prepareCall(sql1);
            callable.setInt(1,noticeId);
            callable.setInt(2,sclId);
            callable.setInt(3,acmdid);
            
            callable.setInt(4,stby);
            //callable.setDate(5, sqlNoteSentDate);
            callable.setTimestamp(5, tmpNoticeSetDate);
            callable.setString(6, toWhom);
            callable.setInt(7,ntcTtlId);
            if(ntcTtlId!=1){
                callable.setString(8,null);
            }else{
            callable.setString(8,base64OtherTitle);
            }
            callable.setString(9,base64Notice);
            callable.setTimestamp(10, tmpNoticeOnDate);
            if(strNoteOnTime.equalsIgnoreCase("9999")){
                callable.setString(11,null);
            }else{
            callable.setString(11,strNoteOnTime);
            }
            callable.setInt(12,stby);
             callable.setTimestamp(13, tmpNoticeSetDate);
                //rs.executeQuery("SET NAMES 'UTF8'");
             effRows=callable.executeUpdate();
            
            resId=callable.getInt(14);
    //effRows=callable.executeUpdate();
   // String strStatus="", strMessage="";
    //effRows=Integer.parseInt(rs.toString());
        String strStatus="",strMessage="";

           if(resId==-2){
            strStatus ="Success";
            strMessage="Notice edited successfully..";
           strFcmResponse=fcm.sendStudentPushNotificationSchoolWise(sclId,base64Notice,"Notice Update");    
           
           } else if(resId==-1){
             strStatus="Fail";
            strMessage="Notice couldnot be edited as there is no any change..";
           
            
           
           }
               else {
             
                    strStatus="Fail";
                    strMessage="Notice couldn't be edited..";
           }
           outObject.put("Status",strStatus);
             outObject.put("Message",strMessage);
             outObject.put("resId",resId);
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


public String insertNotice(){
    try{ 
     Timestamp tmpNoticeSetDate=null,tmpNoticeOnDate=null;
     String base64OtherTitle="";
    String sql=" CALL `Sp_Insert_tblNoticeBoard`(?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
           
            int sclId = Integer.parseInt(noticeBoardData.getString("SchoolId"));
             
            int acmdid =Integer.parseInt(noticeBoardData.getString("AcademicYearId"));
            
           
             int stby = Integer.parseInt(noticeBoardData.getString("SetBy"));
            
             String strNoteSentDate = noticeBoardData.getString("NoticeSetDate");
            
             String toWhom = noticeBoardData.getString("ToWhom");
           
             int ntcTtlId = Integer.parseInt(noticeBoardData.getString("NoticeTitle"));
             if(ntcTtlId==1){
             String ntcOtherTitle=noticeBoardData.getString("OtherTitle");
             base64OtherTitle=StringUtil.base64ToString(ntcOtherTitle);
             }
             
// String notice ="ಇಂದು ರಜೆ";
                String notice= noticeBoardData.getString("Notice");
           
             String base64Notice= StringUtil.base64ToString(notice);
            // System.out.println("com.example.operations.NoticeBoardOperation: Notice value: "+textNotice);
//             
             String strNoteOnDate = noticeBoardData.getString("NoticeDate");
             
             String strNoteOnTime = noticeBoardData.getString("NoticeTime");
            
             Date noteSentDateSQL = null,noteOnDateSql=null;
             java.sql.Date sqlNoteSentDate = null,sqlNoteOnDate=null;
           
                if(!(strNoteSentDate.equals("")))
             {
                 Date noteSentDate = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").parse(strNoteSentDate);
               
                tmpNoticeSetDate=new Timestamp(noteSentDate.getTime());
                
               
             }
               
               //sqlNoteOnDate = new java.sql.Date(noteOnDateSql.getTime());
              if(!(strNoteOnDate.equals("")))
             {
                 Date noteOnDate = new SimpleDateFormat("dd-MM-yyyy").parse(strNoteOnDate);
               tmpNoticeOnDate=new Timestamp(noteOnDate.getTime());
              
             }
           
            callable=con.getConnection().prepareCall(sql);
            
            callable.setInt(1,0);
            callable.setInt(2,sclId);
            callable.setInt(3,acmdid);
            
            callable.setInt(4,stby);
            //callable.setDate(5, sqlNoteSentDate);
            callable.setTimestamp(5, tmpNoticeSetDate);
            callable.setString(6, toWhom);
            callable.setInt(7,ntcTtlId);
            if(ntcTtlId==1){
                callable.setString(8,base64OtherTitle); 
            }else{
                 callable.setString(8,null);
            }
           
            callable.setString(9,base64Notice);
            callable.setTimestamp(10, tmpNoticeOnDate);
             if(strNoteOnTime.equalsIgnoreCase("9999")){
                  callable.setString(11,null);
             }else{
                  callable.setString(11,strNoteOnTime);
             }
           
            callable.setInt(12,stby);
             callable.setTimestamp(13, tmpNoticeSetDate);
                //rs.executeQuery("SET NAMES 'UTF8'");
             effRows=callable.executeUpdate();
            
            resId=callable.getInt(14);
             
             
             if(resId==-1){
                outObject.put("Status", "Fail");
                outObject.put("Message", "Notice has been already added");  
                outObject.put("resId",resId);
//outObject.put("Result", array);
             }
             else if(resId>0)
            {
                String strMessage="";
                ArrayList<String>listPhoneNumber=new ArrayList<String>();
                String sql2="call sp_send_notice_message_to_parentphonenumber(?)";
                        CallableStatement callable2=null;
                        callable2=con.getConnection().prepareCall(sql2);
                        callable2.setInt(1, resId);
                        ResultSet rs1=callable2.executeQuery();
                 strFcmResponse=fcm.sendStudentPushNotificationSchoolWise(sclId,base64Notice," Notice ");
                        
             if(rs1.first())
             {
                 JSONObject outObject= new JSONObject();
                 outObject.put("sender", "SOCKET");
                 outObject.put("sender", "SOCKET");
                 outObject.put("sender", "SOCKET");
                 
                 JSONArray attendArray= new JSONArray();
                         do
               {
                      JSONObject object = new JSONObject();
                      strMessage=rs1.getString("message");
                      String strmobile_number= rs1.getString("mobile_number");
                      listPhoneNumber.add(strmobile_number);
                         
               }while(rs1.next());
                  outObject.put("sms", attendArray);   
            }
             
                     SendSms sendSms= new SendSms();
                     
                     String strPhoneNumberList=listPhoneNumber.toString();
                     String strSubString=strPhoneNumberList.substring(1, strPhoneNumberList.length()-1);
                     
                   /* HttpResponse<String> response = Unirest.post("http://api.msg91.com/api/v2/sendsms?campaign=&response=&afterminutes=&schtime=&unicode=&flash=&message=&encrypt=&authkey=&mobiles=&route=&sender=&country=91")
  .header("authkey", "222059ACFHhJkC5b2dfd12")
  .header("content-type", "application/json")
  .body("{ \"sender\": \"SOCKET\", \"route\": \"4\", \"country\": \"91\", \"sms\": [ { \"message\": \"Message1\", \"to\": [ \"98260XXXXX\", \"98261XXXXX\" ] }, { \"message\": \"Message2\", \"to\": [ \"98260XXXXX\", \"98261XXXXX\" ] } ] }")
  .asString();*/
                     sendSms.sendMessage(strSubString,strMessage);
                outObject.put("Message", "Notice has been added sucessfuly");
                 outObject.put("Status", "Success");
                 outObject.put("resId",resId);
                outObject.put("number of rows affected",effRows);
                 
            }else{
                outObject.put("Status", "Fail");
                outObject.put("Message", "Something went wrong");
                //outObject.put("Result", array);
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

public String sendParentMessage(){
    try{ 
     Timestamp tmpNoticeSetDate=null,tmpNoticeOnDate=null;
     String base64OtherTitle="";
    String sql=" CALL `sp_insert_tbl_parents_message_by_parents`(?,?,?,?,?,?,?)";
           
            int sclId = Integer.parseInt(noticeBoardData.getString("SchoolId"));
             
            int acmdid =Integer.parseInt(noticeBoardData.getString("AcademicYearId"));
            
           int clsId=Integer.parseInt(noticeBoardData.getString("ClassId"));
             int studId = Integer.parseInt(noticeBoardData.getString("StudentId"));
            String strMessage=noticeBoardData.getString("Message");
            String base64Message= StringUtil.base64ToString(strMessage);
            // System.out.println("com.example.operations.NoticeBoardOperation: Notice value: "+textNotice);
         callable=con.getConnection().prepareCall(sql);
            
            callable.setInt(1,0);
            callable.setInt(2,sclId);
            callable.setInt(3,acmdid);
            
            callable.setInt(4,clsId);
            callable.setInt(5, studId);
            
            callable.setString(6, base64Message);
            
                //rs.executeQuery("SET NAMES 'UTF8'");
             effRows=callable.executeUpdate();
            
            resId=callable.getInt(7);
             
             
             if(resId==-1){
                outObject.put("Status", "Fail");
                outObject.put("Message", "Notice has been already added");  
                outObject.put("resId",resId);
//outObject.put("Result", array);
             }
             else if(resId>0)
            {
             
                outObject.put("Message", "Notice has been added sucessfuly");
                 outObject.put("Status", "Success");
                 outObject.put("resId",resId);
                outObject.put("number of rows affected",effRows);
                 
            }else{
                outObject.put("Status", "Fail");
                outObject.put("Message", "Something went wrong");
                //outObject.put("Result", array);
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

public String sendStaffReply(){
    
    
    try{ 
     Timestamp tmpNoticeSetDate=null,tmpNoticeOnDate=null;
     String base64OtherTitle="";
    String sql=" CALL `sp_insert_tbl_staff_replies_by_staffs`(?,?,?,?,?,?,?)";
           
            int prntMsgId = Integer.parseInt(noticeBoardData.getString("ParentMessageId"));
             
            String strStaffReply=noticeBoardData.getString("StaffReply");
            
           int repliedBy=Integer.parseInt(noticeBoardData.getString("RepliedBy"));
             
            String base64Reply= StringUtil.base64ToString(strStaffReply);
            // System.out.println("com.example.operations.NoticeBoardOperation: Notice value: "+textNotice);
         callable=con.getConnection().prepareCall(sql);
            
            callable.setInt(1,0);
            callable.setInt(2,prntMsgId);
            callable.setString(3,base64Reply);
            
            callable.setInt(4,repliedBy);
            callable.setInt(5, repliedBy);
            
            
            
                //rs.executeQuery("SET NAMES 'UTF8'");
             effRows=callable.executeUpdate();
            
            resId=callable.getInt(6);
            String usrToken=callable.getString(7);
             
             
             if(resId==-1){
                outObject.put("Status", "Fail");
                outObject.put("Message", "reply was already sent");  
                outObject.put("resId",resId);
//outObject.put("Result", array);
             }
             else if(resId>0)
            {
             
                outObject.put("Message", "reply Sent sucessfuly");
                 outObject.put("Status", "Success");
                 outObject.put("resId",resId);
                outObject.put("number of rows affected",effRows);
                 
            }else{
                outObject.put("Status", "Fail");
                outObject.put("Message", "Something went wrong");
                //outObject.put("Result", array);
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


public String editClassNotice(){
    
    
    try{ 
     Timestamp tmpNoticeSetDate=null,tmpNoticeOnDate=null;
     String base64OtherTitle="";
     String ntcOtherTitle="";
    String sql=" CALL `sp_insert_tbl_class_notice`(?,?,?,?,?,?,?,?,?,?,?,?)";
           int noticeId=Integer.parseInt(noticeBoardData.getString("NoticeId"));
            int sclId = Integer.parseInt(noticeBoardData.getString("SchoolId"));
             
            int acmdid =Integer.parseInt(noticeBoardData.getString("AcademicYearId"));
            
           
             int stby = Integer.parseInt(noticeBoardData.getString("SetBy"));
            
             String strNoteSentDate = noticeBoardData.getString("NoticeSetDate");
            
             int clsId = Integer.parseInt(noticeBoardData.getString("ClassId"));
           
             int ntcTtlId = Integer.parseInt(noticeBoardData.getString("NoticeTitle"));
             if(ntcTtlId==1){
             ntcOtherTitle=noticeBoardData.getString("OtherTitle");
             base64OtherTitle=StringUtil.base64ToString(ntcOtherTitle);
             }
             
// String notice ="ಇಂದು ರಜೆ";
                String notice= noticeBoardData.getString("Notice");
           
             String base64Notice= StringUtil.base64ToString(notice);
            // System.out.println("com.example.operations.NoticeBoardOperation: Notice value: "+textNotice);
//             
             String strNoteOnDate = noticeBoardData.getString("NoticeDate");
             
             String strNoteOnTime = noticeBoardData.getString("NoticeTime");
            
             Date noteSentDateSQL = null,noteOnDateSql=null;
             java.sql.Date sqlNoteSentDate = null,sqlNoteOnDate=null;
           
             /*   if(!(strNoteSentDate.equals("")))
             {
                 Date noteSentDate = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").parse(strNoteSentDate);
               
                tmpNoticeSetDate=new Timestamp(noteSentDate.getTime());
                
               
             }*/
               
               //sqlNoteOnDate = new java.sql.Date(noteOnDateSql.getTime());
              if(!(strNoteOnDate.equals("")))
             {
                 Date noteOnDate = new SimpleDateFormat("dd-MM-yyyy").parse(strNoteOnDate);
               tmpNoticeOnDate=new Timestamp(noteOnDate.getTime());
              
             }
           
            callable=con.getConnection().prepareCall(sql);
            
            callable.setInt(1,noticeId);
            callable.setInt(2,sclId);
            callable.setInt(3,acmdid);
            
            callable.setInt(4,stby);
            callable.setInt(5,clsId);
            callable.setTimestamp(6, tmpNoticeSetDate);
            
            callable.setInt(6,ntcTtlId);
            if(ntcTtlId==1){
                callable.setString(7,base64OtherTitle); 
            }else{
                 callable.setString(7,null);
            }
           
            callable.setString(8,base64Notice);
            callable.setTimestamp(9, tmpNoticeOnDate);
             if(strNoteOnTime.equalsIgnoreCase("9999")){
                  callable.setString(10,null);
             }else{
                  callable.setString(10,strNoteOnTime);
             }
           
            callable.setInt(11,stby);
           
                //rs.executeQuery("SET NAMES 'UTF8'");
             effRows=callable.executeUpdate();
            
            resId=callable.getInt(12);
             
             
             if(resId==-1){
                outObject.put("Status", "Fail");
                outObject.put("Message", "Notice has been already added");  
                outObject.put("resId",resId);
//outObject.put("Result", array);
             }
             else if(resId==-2)
            {
                /*String strMessage="";
                ArrayList<String>listPhoneNumber=new ArrayList<String>();
                String sql2="call sp_send_notice_message_to_parentphonenumber(?)";
                        CallableStatement callable2=null;
                        callable2=con.getConnection().prepareCall(sql2);
                        callable2.setInt(1, resId);
                        ResultSet rs1=callable2.executeQuery();
                 strFcmResponse=fcm.sendStudentPushNotificationSchoolWise(sclId,base64Notice," Notice ");
                        
             if(rs1.first())
             {
                 JSONObject outObject= new JSONObject();
                 outObject.put("sender", "SOCKET");
                 outObject.put("sender", "SOCKET");
                 outObject.put("sender", "SOCKET");
                 
                 JSONArray attendArray= new JSONArray();
                         do
               {
                      JSONObject object = new JSONObject();
                      strMessage=rs1.getString("message");
                      String strmobile_number= rs1.getString("mobile_number");
                      listPhoneNumber.add(strmobile_number);
                         
               }while(rs1.next());
                  outObject.put("sms", attendArray);   
            }
             
                     SendSms sendSms= new SendSms();
                     
                     String strPhoneNumberList=listPhoneNumber.toString();
                     String strSubString=strPhoneNumberList.substring(1, strPhoneNumberList.length()-1);
                     */
                     
                   /* HttpResponse<String> response = Unirest.post("http://api.msg91.com/api/v2/sendsms?campaign=&response=&afterminutes=&schtime=&unicode=&flash=&message=&encrypt=&authkey=&mobiles=&route=&sender=&country=91")
  .header("authkey", "222059ACFHhJkC5b2dfd12")
  .header("content-type", "application/json")
  .body("{ \"sender\": \"SOCKET\", \"route\": \"4\", \"country\": \"91\", \"sms\": [ { \"message\": \"Message1\", \"to\": [ \"98260XXXXX\", \"98261XXXXX\" ] }, { \"message\": \"Message2\", \"to\": [ \"98260XXXXX\", \"98261XXXXX\" ] } ] }")
  .asString();*/
                    // sendSms.sendMessage(strSubString,strMessage);
                outObject.put("Message", "Notice has been updated sucessfuly");
                 outObject.put("Status", "Success");
                 outObject.put("resId",resId);
                outObject.put("number of rows affected",effRows);
                 
            }
             else{
                outObject.put("Status", "Fail");
                outObject.put("Message", "Something went wrong");
                //outObject.put("Result", array);
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
public String insertClassNotice(){
    try{ 
     Timestamp tmpNoticeSetDate=null,tmpNoticeOnDate=null;
     String base64OtherTitle="";
     String ntcOtherTitle="";
    String sql=" CALL `sp_insert_tbl_class_notice`(?,?,?,?,?,?,?,?,?,?,?,?)";
           
            int sclId = Integer.parseInt(noticeBoardData.getString("SchoolId"));
             
            int acmdid =Integer.parseInt(noticeBoardData.getString("AcademicYearId"));
            
           
             int stby = Integer.parseInt(noticeBoardData.getString("SetBy"));
            
           //  String strNoteSentDate = noticeBoardData.getString("NoticeSetDate");
            
             int clsId = Integer.parseInt(noticeBoardData.getString("ClassId"));
           
             int ntcTtlId = Integer.parseInt(noticeBoardData.getString("NoticeTitle"));
             if(ntcTtlId==1){
             ntcOtherTitle=noticeBoardData.getString("OtherTitle");
             base64OtherTitle=StringUtil.base64ToString(ntcOtherTitle);
             }
             
// String notice ="ಇಂದು ರಜೆ";
                String notice= noticeBoardData.getString("Notice");
           
             String base64Notice= StringUtil.base64ToString(notice);
            // System.out.println("com.example.operations.NoticeBoardOperation: Notice value: "+textNotice);
//             
             String strNoteOnDate = noticeBoardData.getString("NoticeDate");
             
             String strNoteOnTime = noticeBoardData.getString("NoticeTime");
            
             Date noteSentDateSQL = null,noteOnDateSql=null;
             java.sql.Date sqlNoteSentDate = null,sqlNoteOnDate=null;
           
             /*   if(!(strNoteSentDate.equals("")))
             {
                 Date noteSentDate = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").parse(strNoteSentDate);
               
                tmpNoticeSetDate=new Timestamp(noteSentDate.getTime());
                
               
             }*/
               
               //sqlNoteOnDate = new java.sql.Date(noteOnDateSql.getTime());
              if(!(strNoteOnDate.equals("")))
             {
                 Date noteOnDate = new SimpleDateFormat("dd-MM-yyyy").parse(strNoteOnDate);
               tmpNoticeOnDate=new Timestamp(noteOnDate.getTime());
              
             }
           
            callable=con.getConnection().prepareCall(sql);
            
            callable.setInt(1,0);
            callable.setInt(2,sclId);
            callable.setInt(3,acmdid);
            
            callable.setInt(4,stby);
            callable.setInt(5,clsId);
           // callable.setTimestamp(6, tmpNoticeSetDate);
            
            callable.setInt(6,ntcTtlId);
            if(ntcTtlId==1){
                callable.setString(7,base64OtherTitle); 
            }else{
                 callable.setString(7,null);
            }
           
            callable.setString(8,base64Notice);
            callable.setTimestamp(9, tmpNoticeOnDate);
             if(strNoteOnTime.equalsIgnoreCase("9999")){
                  callable.setString(10,null);
             }else{
                  callable.setString(10,strNoteOnTime);
             }
           
            callable.setInt(11,stby);
           
                //rs.executeQuery("SET NAMES 'UTF8'");
             effRows=callable.executeUpdate();
            
            resId=callable.getInt(12);
             
             
             if(resId==-1){
                outObject.put("Status", "Fail");
                outObject.put("Message", "Notice has been already added");  
                outObject.put("resId",resId);
//outObject.put("Result", array);
             }
             else if(resId>0)
            {
                /*String strMessage="";
                ArrayList<String>listPhoneNumber=new ArrayList<String>();
                String sql2="call sp_send_notice_message_to_parentphonenumber(?)";
                        CallableStatement callable2=null;
                        callable2=con.getConnection().prepareCall(sql2);
                        callable2.setInt(1, resId);
                        ResultSet rs1=callable2.executeQuery();
                 strFcmResponse=fcm.sendStudentPushNotificationSchoolWise(sclId,base64Notice," Notice ");
                        
             if(rs1.first())
             {
                 JSONObject outObject= new JSONObject();
                 outObject.put("sender", "SOCKET");
                 outObject.put("sender", "SOCKET");
                 outObject.put("sender", "SOCKET");
                 
                 JSONArray attendArray= new JSONArray();
                         do
               {
                      JSONObject object = new JSONObject();
                      strMessage=rs1.getString("message");
                      String strmobile_number= rs1.getString("mobile_number");
                      listPhoneNumber.add(strmobile_number);
                         
               }while(rs1.next());
                  outObject.put("sms", attendArray);   
            }
             
                     SendSms sendSms= new SendSms();
                     
                     String strPhoneNumberList=listPhoneNumber.toString();
                     String strSubString=strPhoneNumberList.substring(1, strPhoneNumberList.length()-1);
                     */
                     
                   /* HttpResponse<String> response = Unirest.post("http://api.msg91.com/api/v2/sendsms?campaign=&response=&afterminutes=&schtime=&unicode=&flash=&message=&encrypt=&authkey=&mobiles=&route=&sender=&country=91")
  .header("authkey", "222059ACFHhJkC5b2dfd12")
  .header("content-type", "application/json")
  .body("{ \"sender\": \"SOCKET\", \"route\": \"4\", \"country\": \"91\", \"sms\": [ { \"message\": \"Message1\", \"to\": [ \"98260XXXXX\", \"98261XXXXX\" ] }, { \"message\": \"Message2\", \"to\": [ \"98260XXXXX\", \"98261XXXXX\" ] } ] }")
  .asString();*/
                    // sendSms.sendMessage(strSubString,strMessage);
                outObject.put("Message", "Notice has been added sucessfuly");
                 outObject.put("Status", "Success");
                 outObject.put("resId",resId);
                outObject.put("number of rows affected",effRows);
                 
            }else{
                outObject.put("Status", "Fail");
                outObject.put("Message", "Something went wrong");
                //outObject.put("Result", array);
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
 public String getClassNoticeForStaff(){
    try{
            String sql="CALL `sp_get_class_notice_details_by_classId`(?, ?,?);";
            fetchCLassIdWithDate(noticeBoardData, sql);
            
            if(rs.first())
               {
                    fetchClassNoticeDataForNoticeBoard();
              
              
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
 
 public String getClassNoticeForStudent(){
    try{
            String sql="CALL `sp_get_class_notice_details_by_studentId`(?,?,?);";
            fetchStudentIdWithDate(noticeBoardData, sql);
            
            if(rs.first())
               {
                    fetchClassNoticeDataForNoticeBoard();
              
              
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
 
 
public String getStaffNoticeByDate(){
    try{
    String sql="CALL `sp_and_get_staff_notice_by_date`(?, ?,?);";
    fetchStaffNoticeDataWithDate(noticeBoardData, sql);
    if(rs.first())
           {
              fetchNoticeDataForNoticeBoard();
              
              
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




public String noticeDisplay_AllStudent()
{
    JSONObject outObject = new JSONObject();
           try
           {
               JSONArray array = new JSONArray();
           
            String schoolId = noticeBoardData.getString("SchoolId");
            int schId = Integer.parseInt(schoolId);
            String sql = "  CALL `NoticeDisplay_AllStudent`(?)";
         
            ResultSet rs = callable.executeQuery();
           if(rs.first())
           {
               do
               {
                    JSONObject object = new JSONObject();
                            String strDate = rs.getString("Date");
                            String strNoticeTitle = rs.getString("NoticeTitle");
                            String strOtherTitle = rs.getString("OtherTitle");
                            String strNotice = rs.getString("Notice");
                            String strNoticeDate = rs.getString("NoticeDate");
                            
                            object.put("Date", strDate);
                            object.put("NoticeTitle", strNoticeTitle);
                            object.put("OtherTitle", strOtherTitle);
                            object.put("Notice", strNotice);
                            object.put("NoticeDate", strNoticeDate);
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
public String NoticeInsert_AllStaff()
{
    JSONObject outObject = new JSONObject();
           try
           {
               JSONArray array = new JSONArray();
            //JSONObject noticeBoardData = new JSONObject(noticeBoardData);
            String schoolId = noticeBoardData.getString("SchoolId");
            int sclId = Integer.parseInt(schoolId);
            String academicYearId = noticeBoardData.getString("AcademicYearId");
             int acmdid = Integer.parseInt(academicYearId);
             String sentBy = noticeBoardData.getString("SentBy");
             int sntby = Integer.parseInt(sentBy);
             String noticeCreatedDate = noticeBoardData.getString("NoticeCreatedDate"); 
            SimpleDateFormat formatterentrdt = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            java.util.Date ntcCrtdDate= formatterentrdt.parse(noticeCreatedDate);
            String noticeType = noticeBoardData.getString("NoticeType");
             String noticeTitle = noticeBoardData.getString("NoticeTitle");
             String noticeOtherTitle = noticeBoardData.getString("NoticeOtherTitle");
             String notice = noticeBoardData.getString("Notice");
             String noticeDate = noticeBoardData.getString("NoticeDate"); 
             java.util.Date ntcDate= formatterentrdt.parse(noticeDate);
             
            String sql = "CALL `NoticeInsert_AllStaff`(?, ?, ?, ?, ?, ?, ?, ?, ?)";
//           
//            callable.setInt(1,sclId);
//            callable.setInt(2,acmdid);
//             callable.setInt(3,sntby);
//            callable.setDate(4, new java.sql.Date(ntcCrtdDate.getTime()));
//            callable.setString(5, noticeType);
//            callable.setString(6, noticeTitle); 
//            callable.setString(7, noticeOtherTitle);
//            callable.setString(8, notice);
//             callable.setDate(9, new java.sql.Date(ntcDate.getTime()));
                        
            int effRow=callable.executeUpdate();
            if(effRow>0)
            {
                 outObject.put("Status", "Success");
                 outObject.put("Message", "inserted sucessfully");
            }else{
                 outObject.put("Status", "Fail");
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


public String NoticeInsert_AllStudent()
{
    JSONObject outObject = new JSONObject();
           try
           {
               JSONArray array = new JSONArray();
          //  JSONObject noticeBoardData = new JSONObject(noticeBoardData);
            String schoolId = noticeBoardData.getString("SchoolId");
            int sclId = Integer.parseInt(schoolId);
            String academicYearId = noticeBoardData.getString("AcademicYearId");
             int acmdid = Integer.parseInt(academicYearId);
             String sentBy = noticeBoardData.getString("SentBy");
             int sntby = Integer.parseInt(sentBy);
             String noticeCreatedDate = noticeBoardData.getString("NoticeCreatedDate"); 
            SimpleDateFormat formatterentrdt = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            java.util.Date ntcCrtdDate= formatterentrdt.parse(noticeCreatedDate);
            String noticeType = noticeBoardData.getString("NoticeType");
             String noticeTitle = noticeBoardData.getString("NoticeTitle");
             String noticeOtherTitle = noticeBoardData.getString("NoticeOtherTitle");
             String notice = noticeBoardData.getString("Notice");
             String noticeDate = noticeBoardData.getString("NoticeDate"); 
             java.util.Date ntcDate= formatterentrdt.parse(noticeDate);
                    
            String sql = "CALL `NoticeInsert_AllStudent`(?, ?, ?, ?, ?, ?, ?, ?, ?)";
//          
//            callable.setInt(1,sclId);
//            callable.setInt(2,acmdid);
//             callable.setInt(3,sntby);
//            callable.setDate(4, new java.sql.Date(ntcCrtdDate.getTime()));
//            callable.setString(5, noticeType);
//            callable.setString(6, noticeTitle); 
//            callable.setString(7, noticeOtherTitle);
//            callable.setString(8, notice);
//             callable.setDate(9, new java.sql.Date(ntcDate.getTime()));
                        
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

public String NoticeInsert_ToAll()
{
    JSONObject outObject = new JSONObject();
           try
           {
               JSONArray array = new JSONArray();
         //   JSONObject noticeBoardData = new JSONObject(noticeBoardData);
            String schoolId = noticeBoardData.getString("SchoolId");
            int sclId = Integer.parseInt(schoolId);
            String academicYearId = noticeBoardData.getString("AcademicYearId");
             int acmdid = Integer.parseInt(academicYearId);
             String sentBy = noticeBoardData.getString("SentBy");
             int sntby = Integer.parseInt(sentBy);
             String noticeCreatedDate = noticeBoardData.getString("NoticeCreatedDate"); 
            SimpleDateFormat formatterentrdt = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            java.util.Date ntcCrtdDate= formatterentrdt.parse(noticeCreatedDate);
            String noticeType = noticeBoardData.getString("NoticeType");
             String noticeTitle = noticeBoardData.getString("NoticeTitle");
             String noticeOtherTitle = noticeBoardData.getString("NoticeOtherTitle");
             String notice = noticeBoardData.getString("Notice");
             String noticeDate = noticeBoardData.getString("NoticeDate"); 
             java.util.Date ntcDate= formatterentrdt.parse(noticeDate);
                    
            String sql = "CALL `NoticeInsert_ToAll`(?, ?, ?, ?, ?, ?, ?, ?, ?)";
//           
//            callable.setInt(1,sclId);
//            callable.setInt(2,acmdid);
//             callable.setInt(3,sntby);
//            callable.setDate(4, new java.sql.Date(ntcCrtdDate.getTime()));
//            callable.setString(5, noticeType);
//            callable.setString(6, noticeTitle); 
//            callable.setString(7, noticeOtherTitle);
//            callable.setString(8, notice);
//             callable.setDate(9, new java.sql.Date(ntcDate.getTime()));
                        
            int effRow=callable.executeUpdate();
            if(effRow>0)
            {
                 outObject.put("Status", "Success");
                 outObject.put("Message", "Notice has been added sucessfully");
            }else{
                 outObject.put("Status", "Fail");
                 outObject.put("Message", "Notice was not added");
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

public String insertParentNote(){
    
     try{
         JSONArray array= noticeBoardData.getJSONArray("ParentNoteArray");
         int rowCount=0;
         int clsId=0;
         Timestamp tmpNoteSent=null,tmpNoteOn=null;
             for (int i=0; i < array.length(); i++) {
                 
                 String sql = "   CALL `Sp_Insert_tblParentNote`(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
                 JSONObject object = array.getJSONObject(i);
                 int schoolId = Integer.parseInt(object.getString("SchoolId"));
                 int academicYearId = Integer.parseInt(object.getString("AcademicYearId"));
                 int studentId = Integer.parseInt(object.getString("StudentId"));
                 int enteredBy = Integer.parseInt(object.getString("EnteredBy"));
                 
                 String strNoteSentDate= object.getString("NoteSentDate");
                  clsId= Integer.parseInt(object.getString("ClassId"));
                 int ttlId = Integer.parseInt(object.getString("NoteTitleId"));
                 String strOtherTitle=object.getString("OtherNoteTitle");
                 String strNote=object.getString("Note");
                 String base64OtherTitle=StringUtil.base64ToString(strOtherTitle);
                 String base64Note=StringUtil.base64ToString(strNote);
                         
                 String strNoteOnDate= object.getString("NoteOnDate");
                  String strNoteOnTime= object.getString("NoticeTime");
                 
                 
                 Date noteSentDateSQL = null,noteOnDateSql=null;
             java.sql.Date sqlNoteSentDate = null,sqlNoteOnDate=null;
           
             
             if(!(strNoteSentDate.equals("")))
             {
                 Date noteSentDate = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss").parse(strNoteSentDate);
               tmpNoteSent=new Timestamp(noteSentDate.getTime());
//              
             }
              if(!(strNoteOnDate.equals("")))
             {
                 Date noteOnDate = new SimpleDateFormat("dd-MM-yyyy").parse(strNoteOnDate);
               tmpNoteOn=new Timestamp(noteOnDate.getTime());
//              
             }
             callable=con.getConnection().prepareCall(sql);
           
            callable.setInt(1,0);
            callable.setInt(2, schoolId);
             callable.setInt(3, academicYearId);
             callable.setInt(4,enteredBy);
            callable.setInt(5, clsId);
            callable.setInt(6, studentId);
            callable.setTimestamp(7, tmpNoteSent);
            callable.setInt(8,ttlId);
            
            callable.setString(9,base64OtherTitle);
            callable.setString(10,base64Note);
            callable.setTimestamp(11, tmpNoteOn);
            if(strNoteOnTime.equalsIgnoreCase("9999")){
               callable.setString(12,null); 
            }else{
            callable.setString(12,strNoteOnTime);
            }
             callable.setInt(13,enteredBy);
             callable.setTimestamp(14, tmpNoteSent);
//            callable.setInt(9,0);
            
            effRows=callable.executeUpdate();
                resId=callable.getInt(15);
               strUSerToken=callable.getString(16);
                if(resId==-1){
                 
                    break;
                }
                else if (resId>0){
                    
                    rowCount++;
                    fcm.send_FCM_Notification(strUSerToken, base64Note, "Parent's note");
                
                }
                else {
                
                         break;
                }
             }
     if(resId>0)
            {
                String strMessage="";
                ArrayList<String>listPhoneNumber=new ArrayList<String>();
                String sql2="call sp_send_parentsnote_message_to_parentphonenumber(?,?)";

                CallableStatement callable2=null;
                callable2=con.getConnection().prepareCall(sql2);
                callable2.setInt(1,clsId);
                callable2.setTimestamp(2, tmpNoteSent);
                ResultSet rs1=callable2.executeQuery();
                    
             if(rs1.first())
             {
                     JSONArray attendArray= new JSONArray();
                         do
               {
                      JSONObject object = new JSONObject();
                      strMessage=rs1.getString("message");
                      String strmobile_number= rs1.getString("mobile_number");
                      sendMessage(strmobile_number, strMessage);
                         
               }while(rs1.next());
                     
            }
                     
                 outObject.put("Status", "Success");
                 outObject.put("Message", "Parent's note inserted sucessfully");
                 outObject.put("effected rows",rowCount);
            }else if(resId==-1){
                 outObject.put("Status", "Fail");
                 outObject.put("Message", "Parent's note has been already added");
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

public String getClasswiseParentMessage(){
    try
           {
            
            String sql = "CALL sp_get_parent_message_by_classwise(?,?)";
               fetchClasswiseParentMessageData(noticeBoardData, sql);
           
           if(rs.first())
           {
               //fetchNoticeData();
               fetchParentMessage();
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

public String getSchoolwiseParentMessage(){
    try
           {
            
            String sql = "CALL sp_get_parent_message_by_schoolwise(?,?)";
               fetchSchoolwiseParentMessageData(noticeBoardData, sql);
           
           if(rs.first())
           {
               //fetchNoticeData();
               fetchParentMessage();
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




public String editParentNote(){
    try{
    String sql = "   CALL `Sp_Insert_tblParentNote`(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
                 Timestamp tmpNoteSent=null,tmpNoteOn=null;
                 String strNoteOnTime=null;
                 int ntcId = Integer.parseInt(noticeBoardData.getString("NoticeId"));
                 int schoolId = Integer.parseInt(noticeBoardData.getString("SchoolId"));
                 int academicYearId = Integer.parseInt(noticeBoardData.getString("AcademicYearId"));
                 int studentId = Integer.parseInt(noticeBoardData.getString("SentTo"));
                 int enteredBy = Integer.parseInt(noticeBoardData.getString("SetBy"));
                 
                 String strNoteSentDate= noticeBoardData.getString("NoteSentDate");
                  int clsId= Integer.parseInt(noticeBoardData.getString("ClassId"));
                 int ttlId = Integer.parseInt(noticeBoardData.getString("NoteTitleId"));
                 String strOtherTitle=noticeBoardData.getString("OtherNoteTitle");
                 String strNote=noticeBoardData.getString("Notice");
             String base64OtherTitle=StringUtil.base64ToString(strOtherTitle);
             String base64Note=StringUtil.base64ToString(strNote);
                 String strNoteOnDate= noticeBoardData.getString("NoteOnDate");
                 int objLength=noticeBoardData.length();
                
                  strNoteOnTime= noticeBoardData.getString("NoticeTime");
                 
                 
                 Date noteSentDateSQL = null,noteOnDateSql=null;
             java.sql.Date sqlNoteSentDate = null,sqlNoteOnDate=null;
           
             
             if(!(strNoteSentDate.equals("")))
             {
                 Date noteSentDate = new SimpleDateFormat("dd/MM/yyyy").parse(strNoteSentDate);
               tmpNoteSent=new Timestamp(noteSentDate.getTime());
               
//              
             }
              if(!(strNoteOnDate.equals("")))
             {
                 Date noteOnDate = new SimpleDateFormat("dd/MM/yyyy").parse(strNoteOnDate);
               tmpNoteOn=new Timestamp(noteOnDate.getTime());
               SimpleDateFormat dateFormatToServer = new SimpleDateFormat("dd-MM-yyyy");
                String strNoteOnDateInFormated = dateFormatToServer.format(noteOnDate);
                
                noteOnDateSql = dateFormatToServer.parse(strNoteOnDateInFormated);
//              SQL Date For Stored Procuder Init
                sqlNoteOnDate = new java.sql.Date(noteOnDateSql.getTime());
//              
             }
             callable=con.getConnection().prepareCall(sql);
           
            callable.setInt(1,ntcId);
            callable.setInt(2, schoolId);
             callable.setInt(3, academicYearId);
             callable.setInt(4,enteredBy);
            callable.setInt(5, clsId);
            callable.setInt(6, studentId);
            callable.setTimestamp(7, tmpNoteSent);
            callable.setInt(8,ttlId);
            if(strOtherTitle.equalsIgnoreCase("")){
                 callable.setString(9,null);
            }else{
            callable.setString(9,base64OtherTitle);
            }
            callable.setString(10,base64Note);
            callable.setDate(11, sqlNoteOnDate);
            if(strNoteOnTime.equalsIgnoreCase("9999")){
           callable.setString(12,null);
            }else{
                callable.setString(12,strNoteOnTime);
            }
             callable.setInt(13,enteredBy);
             callable.setTimestamp(14, tmpNoteSent);
//            callable.setInt(9,0);
            
            effRows=callable.executeUpdate();
                resId=callable.getInt(15);
                strUSerToken=callable.getString(16);
               if(resId==-2){
                 outObject.put("Status", "Success");
                 outObject.put("Message", "Parent's note edited sucessfully");
                 fcm.send_FCM_Notification(strUSerToken, base64Note," Parent's Note Update ");
                 
            }else if(resId==-1){
                outObject.put("Status", "Alert");
                 outObject.put("Message", "no changes to edit ");
                
            } 
                else    {
            outObject.put("Status", "Fail");
                 outObject.put("Message", "Something went wrong");
            }
            
          return outObject.toString();
    }catch(Exception ee){
         String exce = ee.toString();
            String exc = "{\"Status\":\"Exception\" ,  \"Message\":\""+exce+"\"}";
            return exc;    
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


public String NoticeTitle()
{
    JSONObject outObject = new JSONObject();
           try
           {
               JSONArray array = new JSONArray();
         //   JSONObject noticeBoardData = new JSONObject(noticeBoardData);
            String strSchoolId = noticeBoardData.getString("SchoolId");
            int SchoolId = Integer.parseInt(strSchoolId);
            String sql= " CALL `NoticeTitle`(?)";
//           
//            callable.setInt(1,SchoolId);
//            ResultSet rs = callable.executeQuery();
           if(rs.first())
           {
               do
               {
                    JSONObject object = new JSONObject();
                    String strnotice = rs.getString("NoticeTitle");
                    object.put("NoticeTitle", strnotice);
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

public void fillParentNoteData()
{
    try{
         do
               {
                    JSONObject object = new JSONObject();
                    String strAcademicYearId = rs.getString("AcademicYearId");
                    String strNoticeCreatedDate = rs.getString("NoticeCreatedDate");
                    String strNoteTitle = rs.getString("NoticeTitle");
                    String strNotice = rs.getString("Notice");
                    String strNoticeDate = rs.getString("NoticeDate");
                    String strNoticeTime = rs.getString("NoteOnTime");
                    String strStudentName = rs.getString("studentName");
                    String strNoteTitleId=rs.getString("NoteTitleId");
                    String strClass = rs.getString("class");
                    String strNoticeDateTime="";
                    String strNoticeId=rs.getString("NoticeId");
                    String strStudentDetailsId=rs.getString("StudentDetailsId");
                    
                    if(strNoticeTime==null||strNoticeTime.isEmpty()||strNoticeTime.equalsIgnoreCase("00:00:00")){
                    strNoticeDateTime=strNoticeDate;
                    strNoticeTime="9999";
                            }else {
                                strNoticeDateTime=strNoticeDate+" "+strNoticeTime;
                            }
                            
                    object.put("AcademicYearId", strAcademicYearId);
                    object.put("NoticeCreatedDate", strNoticeCreatedDate);
                    object.put("NoteTitle", strNoteTitle);
                    object.put("Notice", strNotice);
                    object.put("NoticeDate", strNoticeDateTime);
                    object.put("StudentName", strStudentName);
                    object.put("Class", strClass);
                    object.put("NoteTitleId",strNoteTitleId);
                    object.put("NoticeId",strNoticeId);
                    object.put("StudentDetailsId",strStudentDetailsId);
                    object.put("NoticeEditDate",strNoticeDate);
                    object.put("NoticeEditTime",strNoticeTime);
                    array.put(object);
               }while(rs.next());
    }catch(Exception e){
        e.printStackTrace();
    }
}

public String ParentNoteTitle()
{
    JSONObject outObject = new JSONObject();
           try
           {
               JSONArray array = new JSONArray();
           // JSONObject noticeBoardData = new JSONObject(noticeBoardData);
            String strSchoolId = noticeBoardData.getString("SchoolId");
            int SchoolId = Integer.parseInt(strSchoolId);
            String sql= " CALL `ParentNoteTitle`(?)";
//            
//            callable.setInt(1,SchoolId);
//            ResultSet rs = callable.executeQuery();
           if(rs.first())
           {
               do
               {
                    JSONObject object = new JSONObject();
                    String strparentnote = rs.getString("ParentNoteTitle");
                    object.put("ParentNoteTitle", strparentnote);
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
public String ParentNoteDisplay_Admin()
{
    JSONObject outObject = new JSONObject();
           try
           {
               JSONArray array = new JSONArray();
     //       JSONObject noticeBoardData = new JSONObject(noticeBoardData);
            String strClass = noticeBoardData.getString("Class");
            String strSection = noticeBoardData.getString("Section");
            String RollNo = noticeBoardData.getString("RollNo");
            int studId = Integer.parseInt(RollNo);
//            String sql = "  CALL `ParentNoteDisplay_Admin`(?, ?, ?)";
//            
//            callable.setString(1,strClass);
//            callable.setString(2, strSection);
//            callable.setInt(3,studId);
//            ResultSet rs = callable.executeQuery();
           if(rs.first())
           {
               do
               {
                    JSONObject object = new JSONObject();
                            String strDate = rs.getString("Date");
                            String strSendBy = rs.getString("SendBy");
                            String strNoteTitle = rs.getString("NoteTitle");
                            String strNote = rs.getString("Note");
                            String strNoticeDate = rs.getString("NoteOnDate");
                            String strTime = rs.getString("Time");
                            
                            object.put("Date", strDate);
                            object.put("SendBy", strSendBy);
                            object.put("NoteTitle", strNoteTitle);
                            object.put("Note", strNote);
                            object.put("NoticeDate", strNoticeDate);
                            object.put("Time", strTime);
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
    public String setStaffParentsNote()
{
    JSONObject outObject = new JSONObject();
           try
           {
               JSONArray array = new JSONArray();
           // JSONObject noticeBoardData = new JSONObject(noticeBoardData);
            String schoolId = noticeBoardData.getString("SchoolId");
            int sclId = Integer.parseInt(schoolId);
            String academicYearId = noticeBoardData.getString("AcademicYearId");
             int acmdid = Integer.parseInt(academicYearId);
             String sentBy = noticeBoardData.getString("SentBy");
             int sntby = Integer.parseInt(sentBy);
              String classId = noticeBoardData.getString("ClassId");
             int clsid = Integer.parseInt(classId);
             String noteSentDate = noticeBoardData.getString("NoteSentDate");
             //SimpleDateFormat ymd=new SimpleDateFormat("yyyy-mm-dd HH:mm");
            Date noticeSentDateSQL = null;
            java.sql.Date sqlNtcDate = null;
            int prntId=0;
            
            
            //SimpleDateFormat formatterentrdt = new SimpleDateFormat("yyyy-MM-dd HH:mm");
           // java.util.Date ntsntDate= formatterentrdt.parse(noteSentDate);
            String studentId = noticeBoardData.getString("StudentId");
             int studid = Integer.parseInt(studentId);
             String noticeTitle = noticeBoardData.getString("NoticeTitle");
             String noticeOtherTitle = noticeBoardData.getString("NoteOtherTitle");
             String Note = noticeBoardData.getString("Note");
             String noteOnDate = noticeBoardData.getString("NoteOnDate"); 
           //  java.util.Date ntcDate= formatterentrdt.parse(noteOnDate);
                  
            String sql = "CALL `Sp_Insert_tblParentNote`(?,?, ?,?, ?,?, ?,?, ?,?, ?,?, ?,?);";
            if(!(noteSentDate.equals("")))
             {
                 Date noticeDateFrom = new SimpleDateFormat("dd-MM-yyyy HH:mm").parse(noteSentDate);
               
                SimpleDateFormat dateFormatToServer = new SimpleDateFormat("yyyy-MM-dd HH:mm");
                String strnoteSentDateInFormated = dateFormatToServer.format(noticeDateFrom);
                
                noticeSentDateSQL = dateFormatToServer.parse(strnoteSentDateInFormated);
               
                //SQL Date For Stored Procuder Init
                sqlNtcDate = new java.sql.Date(noticeSentDateSQL.getTime());
             }
             Date noticeOnDateSQL = null;
            java.sql.Date sqlNtcOnDate = null;
            if(!(noteOnDate.equals("")))
             {
                 Date noticeDateTo = new SimpleDateFormat("dd-MM-yyyy HH:mm").parse(noteOnDate);
               
                SimpleDateFormat dateFormatToServer = new SimpleDateFormat("yyyy-MM-dd HH:mm");
                String strNtcToDateInFormated = dateFormatToServer.format(noticeDateTo);
                
                noticeOnDateSQL = dateFormatToServer.parse(strNtcToDateInFormated);
               
                //SQL Date For Stored Procuder Init
                sqlNtcOnDate = new java.sql.Date(noticeOnDateSQL.getTime());
             }
            callable= con.getConnection().prepareCall(sql);
            callable.setInt(1,0);
            callable.setInt(2,sclId);
            callable.setInt(3,acmdid);
             callable.setInt(4,sntby);
              callable.setInt(5,clsid);
               callable.setInt(6, studid);
            callable.setDate(7, sqlNtcDate);
           
            callable.setString(8, noticeTitle); 
            callable.setString(9, noticeOtherTitle);
            callable.setString(10, Note);
             callable.setDate(11, sqlNtcOnDate);
             callable.setInt(12, sntby);
               callable.setDate(13, sqlNtcDate);         
            int effRow=callable.executeUpdate();
            resId=callable.getInt(14);
            if(resId>0)
            {
                 outObject.put("Status", "Success");
                 outObject.put("Message", "Parents note added sucessfully");
            }else if(resId==-1){
                 outObject.put("Status", "Fail");
                 outObject.put("Message", "Parents note has been already inserted");
            } else {
                outObject.put("Status", "Fail");
                 outObject.put("Message", "Parents note was not inserted");
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
public String getParentNoteTitle(){
    try{
        String sql = "  CALL `sp_and_staff_get_parentNoteTitle`(?)";
         String schoolId = noticeBoardData.getString("SchoolId");
            int sclId = Integer.parseInt(schoolId);
            callable=con.getConnection().prepareCall(sql);
            callable.setInt(1,sclId);
            rs=callable.executeQuery();
            if(rs.first()){
                do
               {
                    JSONObject object = new JSONObject();
                    String strParentNoteTitle = rs.getString("ParentNoteTitle");
                    String strParentNoteTitleId = rs.getString("ParentNoteTitleId");
                    
                            
                    object.put("ParentNoteTitle", strParentNoteTitle);
                    object.put("ParentNoteTitleId", strParentNoteTitleId);
                    
                    
                    array.put(object);
               }while(rs.next());
                if(array.length()!=0){
                         printSuccessStatus(array);
                     }else {
                         printFaliureStatus(array);
                     }
                }else{
                 printFaliureStatus(array);
            }
            return outObject.toString();
            
    }catch(Exception ee){
          String exce = ee.toString();
            String exc = "{\"Status\":\"Exception\" ,  \"Message\":\""+exce+"\" ,"
                    + " \"Result\":[]}";
            return exc;    
        }finally{
            closeDBConnection();
        }
}

public String parentnoteDisplay()

{
     try
           {
               
            String sql = "  CALL `sp_and_get_parent_note`(?,?)";
               fetchStudentWithAcademicYEar(noticeBoardData, sql);
            
           if(rs.first())
           {
               fillParentNoteData();
              
                if(array.length()!=0){
                         printSuccessStatus(array);
                     }else {
                         printFaliureStatus(array);
                     }
           }else {
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

public String getParentNoteById(){
    try{
    String sql="CALL `Sp_Get_tblparentnoteById`(?);";
    fetchNoticeDetailsById(noticeBoardData,sql);
    
    
    if(rs.first())
           {
              fetchNoticeDetailsForEdit();
              
              
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
public String deleteParentNoteById(){
    try{
    /*String sql="CALL `Sp_Delete_ParentNoteById`(?);";
    fetchNoticeDetailsById(noticeBoardData,sql);
    effRows=callable.executeUpdate();
    String strStatus="", strMessage="";
   
           if(effRows==0){
             strStatus="Success";
             strMessage="parents note deleted successfully..";
             }else {
             
             strStatus="Fail";
             strMessage="parents note couldn't be deleted..";
             
           }
           outObject.put("Status",strStatus);
           outObject.put("Message",strMessage);
       */
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

public String deleteClassNoteById(){
    try{
    /*String sql="CALL `Sp_Delete_tblclassnoticebyId`(?);";
    fetchNoticeDetailsById(noticeBoardData,sql);
    effRows=callable.executeUpdate();
    String strStatus="", strMessage="";
   
           if(effRows==0){
             strStatus="Success";
             strMessage="Class notice deleted successfully..";
             }else {
             
             strStatus="Fail";
             strMessage="Class notice couldn't be deleted..";
             
           }
           outObject.put("Status",strStatus);
           outObject.put("Message",strMessage);*/
            outObject.put("Status", "Fail");
            outObject.put("Message", "This feature is no more, please update the app");
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




public String parentnoteDisplayByDate(){
    try{
            String sql=" CALL `sp_and_get_parent_note_by_date`(?,?,?);";
        fetchStudentNoticeDataWithDate(noticeBoardData, sql);
    if(rs.first())
                 
                 {
                       fillParentNoteData();
                       
                     if(array.length()!=0){
                         printSuccessStatus(array);
                     }else {
                         printFaliureStatus(array);
                     }
           }else {
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
public String ReminderTitle()
{
    JSONObject outObject = new JSONObject();
           try
           {
               JSONArray array = new JSONArray();
         //   JSONObject noticeBoardData = new JSONObject(noticeBoardData);
            String strSchoolId = noticeBoardData.getString("SchoolId");
            int SchoolId = Integer.parseInt(strSchoolId);
            String sql= " CALL `ReminderTitle`(?)";
//            
//            callable.setInt(1,SchoolId);
//            ResultSet rs = callable.executeQuery();
           if(rs.first())
           {
               do
               {
                    JSONObject object = new JSONObject();
                    String strReminderTitle = rs.getString("ReminderTitle");
                    object.put("ReminderTitle", strReminderTitle);
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


public String StaffReminderDisplay()
{
    JSONObject outObject = new JSONObject();
           try
           {
               JSONArray array = new JSONArray();
         //   JSONObject noticeBoardData = new JSONObject(noticeBoardData);
            String staffid = noticeBoardData.getString("StaffId");
            int stfId = Integer.parseInt(staffid);
            String sql = " CALL `StaffReminderDisplay`(?)";
//            
//            callable.setInt(1,stfId);
//            ResultSet rs = callable.executeQuery();
           if(rs.first())
           {
               do
               {
                    JSONObject object = new JSONObject();
                            String strDate = rs.getString("Date");
                            String strStaff_Name = rs.getString("Staff_Name");
                            String strReminderTitle= rs.getString("ReminderTitle");
                            String strReminder= rs.getString("Reminder");
                            String strReminder_Date= rs.getString("Reminder_Date");
                            String strReminder_Time= rs.getString("Reminder_Time");
                            object.put("Date", strDate);
                            object.put("Staff_Name", strStaff_Name);
                            object.put("ReminderTitle", strReminderTitle);
                            object.put("Reminder", strReminder);
                            object.put("Reminder_Time", strReminder_Time);
                            
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
public String StaffReminder_Insert()
{
    JSONObject outObject = new JSONObject();
           try
           {
               JSONArray array = new JSONArray();
            //JSONObject noticeBoardData = new JSONObject(noticeBoardData);
            int sclId = Integer.parseInt(noticeBoardData.getString("SchholId"));
            
            int acmdid = Integer.parseInt(noticeBoardData.getString("AcademicYearId"));
             int stby = Integer.parseInt(noticeBoardData.getString("SetBy"));
             String reminderSetDate = noticeBoardData.getString("ReminderSetDate");
             SimpleDateFormat formatterentrdt = new SimpleDateFormat("yyyy-MM-dd HH:mm");
             java.util.Date rmdrstDate= formatterentrdt.parse(reminderSetDate);
             int stfid = Integer.parseInt(noticeBoardData.getString("StaffId"));
             String reminderTitle = noticeBoardData.getString("ReminderTitle");
             String reminder = noticeBoardData.getString("Reminder");
             String reminderDate = noticeBoardData.getString("ReminderDate");
             java.util.Date rmdrDate= formatterentrdt.parse(reminderDate);
                 
            String sql = " CALL `StaffReminder_Insert`(?, ?, ?, ?, ?, ?, ?,?)";
//           
//            callable.setInt(1,sclId);
//            callable.setInt(2,acmdid);
//            
//            callable.setInt(3,stby);
//             callable.setDate(4, new java.sql.Date(rmdrstDate.getTime()));
//            callable.setInt(5, studid);
//            callable.setString(6, reminderTitle); 
//            callable.setString(7, reminder);
//            callable.setDate(8, new java.sql.Date(rmdrDate.getTime())); 
            
                        
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
public String studentReminderDisplay_studentwise()
{
        try
                {
                   
                 String sql = "  CALL `sp_and_get_student_reminder`(?, ?);";
                    fetchStudentWithAcademicYEar(noticeBoardData, sql);
                if(rs.first())
                {
                       fillReminderData();
                      
                     if(array.length()!=0){
                         
                         printSuccessStatus(array);
                     
                     }else {
                      
                         printFaliureStatus(array);
                     
                     }
           }else {
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

public String insertStudentReminder()
{
    JSONObject outObject = new JSONObject();
           
    try
           {
           JSONArray array= noticeBoardData.getJSONArray("StudentReminderArray");
         int rowCount=0;
         int effRow=0;
         int clsid=0;
         Date reminderSetDate=null,reminderOnDate=null;
         Timestamp tmpreminderSetDate=null,tmpreminderOnDate=null;
             for (int i=0; i < array.length(); i++) {
                 String sql=" CALL `Sp_Insert_tblstudentreminder`(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
           JSONObject reminderObject = array.getJSONObject(i);
            int sclId = Integer.parseInt(reminderObject.getString("SchoolId"));
             
            int acmdid =Integer.parseInt(reminderObject.getString("AcademicYearId"));
            clsid= Integer.parseInt(reminderObject.getString("ClassId"));
           
             int stby = Integer.parseInt(reminderObject.getString("EnteredBy"));
            
             String strReminderSetDate = reminderObject.getString("ReminderSetDate");
           
             int studid = Integer.parseInt(reminderObject.getString("StudentId"));
           
             int rmdId = Integer.parseInt(reminderObject.getString("ReminderTitle"));
             String rmndrOtherTitle=reminderObject.getString("OtherTitle");
             String reminder = reminderObject.getString("Reminder");
             String base64_OtherTitle=StringUtil.base64ToString(rmndrOtherTitle);
             String base64Reminder=StringUtil.base64ToString(reminder);
             String strReminderDate = reminderObject.getString("ReminderDate");
            String strReminderTime = reminderObject.getString("ReminderTime");
            //java.util.Date rmdrDate= formatterentrdt.parse(reminderDate);
             
                  Date rmdrSetDateSQL = null;
             java.sql.Date sqlRmdrSetDate = null;
             if(!(strReminderSetDate.equals("")))
             {
                  reminderSetDate= new SimpleDateFormat("dd-MM-yyyy hh:mm:ss").parse(strReminderSetDate);
//                    
                tmpreminderSetDate=new Timestamp(reminderSetDate.getTime());
//               
             }
              Date rmdrOnDateSQL = null;
             java.sql.Date sqlRmdrOnDate = null;
             if(!(strReminderDate.equals("")))
             {
                 reminderOnDate = new SimpleDateFormat("dd-MM-yyyy").parse(strReminderDate);
                 tmpreminderOnDate=new Timestamp(reminderOnDate.getTime());
               
             }
             
            
            callable=con.getConnection().prepareCall(sql);
            
            callable.setInt(1,0);
            callable.setInt(2,sclId);
            callable.setInt(3,acmdid);
            callable.setInt(4,clsid);
            callable.setInt(5,stby);
            callable.setTimestamp(6,tmpreminderSetDate);
            callable.setInt(7, studid);
            callable.setInt(8, rmdId);
            callable.setString(9, base64_OtherTitle);
            callable.setString(10, base64Reminder);
            callable.setTimestamp(11,tmpreminderOnDate);
            if(strReminderTime.equalsIgnoreCase("-9999")){
                callable.setString(12, null);
            }else{
            callable.setString(12, strReminderTime);    
            }
            
            callable.setInt(13,stby);
            callable.setTimestamp(14,tmpreminderSetDate);
          
            effRow=callable.executeUpdate();
            
            resId=callable.getInt(15);
            strUSerToken=callable.getString(16);
             if(resId==-1){
                 break;
             }
             else if(resId>0)
            {
                 rowCount++;
                 fcm.send_FCM_Notification(strUSerToken, base64Reminder,"Student Reminder");
            }else{
                 break;
            }
             }
             if(resId==-1){
                outObject.put("Status", "Fail");
                outObject.put("Message", "Reminder has been already added");
//                outObject.put("Result", array);
             }
             else if(resId>0)
            {
                String strMessage="";
                ArrayList<String>listPhoneNumber=new ArrayList<String>();
                String sql2="call sp_send_student_reminder_to_parentsphonenumber(?,?)";
                        
                       
                        CallableStatement callable2=null;
                callable2=con.getConnection().prepareCall(sql2);
                callable2.setInt(1,clsid);
                callable2.setTimestamp(2, tmpreminderSetDate);
                ResultSet rs1=callable2.executeQuery();
                    
             if(rs1.first())
             {
                     JSONArray attendArray= new JSONArray();
                         do
               {
                      JSONObject object = new JSONObject();
                      strMessage=rs1.getString("message");
                      String strmobile_number= rs1.getString("mobile_number");
                      sendMessage(strmobile_number, strMessage);
                         
               }while(rs1.next());
                     
            }
                outObject.put("Status", "Success");
                outObject.put("Message", "Reminder has been added sucessfuly");
                outObject.put("number of rows affected",rowCount);
                 
            }else{
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
public String editStudentReminder(){
    try{
            Date reminderSetDate=null,reminderOnDate=null;
            Timestamp tmpreminderSetDate=null,tmpreminderOnDate=null;
            String rmndrOtherTitle="";
            String sql="CALL `Sp_Insert_tblstudentreminder`(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
           
            int reminderId = Integer.parseInt(noticeBoardData.getString("ReminderId"));
             int sclId = Integer.parseInt(noticeBoardData.getString("SchoolId"));
            int acmdid =Integer.parseInt(noticeBoardData.getString("AcademicYearId"));
            int clsid= Integer.parseInt(noticeBoardData.getString("ClassId"));
           
             int stby = Integer.parseInt(noticeBoardData.getString("EnteredBy"));
            
             String strReminderSetDate = noticeBoardData.getString("ReminderSetDate");
           
             int studid = Integer.parseInt(noticeBoardData.getString("StudentId"));
           
             int rmdId = Integer.parseInt(noticeBoardData.getString("ReminderTitle"));
             if(rmdId==1){
            rmndrOtherTitle =noticeBoardData.getString("OtherTitle");}
             String reminder = noticeBoardData.getString("Reminder");
            String base64_OtherTitle=StringUtil.base64ToString(rmndrOtherTitle);
             String base64Reminder=StringUtil.base64ToString(reminder);
             String strReminderDate = noticeBoardData.getString("ReminderDate");
            String strReminderTime = noticeBoardData.getString("ReminderTime");
            //java.util.Date rmdrDate= formatterentrdt.parse(reminderDate);
             
                  Date rmdrSetDateSQL = null;
             java.sql.Date sqlRmdrSetDate = null;
             if(!(strReminderSetDate.equals("")))
             {
                  reminderSetDate= new SimpleDateFormat("dd/MM/yyyy").parse(strReminderSetDate);
//                    
                tmpreminderSetDate=new Timestamp(reminderSetDate.getTime());
//               
             }
              Date rmdrOnDateSQL = null;
             java.sql.Date sqlRmdrOnDate = null;
             if(!(strReminderDate.equals("")))
             {
                 reminderOnDate = new SimpleDateFormat("dd/MM/yyyy").parse(strReminderDate);
                 tmpreminderOnDate=new Timestamp(reminderOnDate.getTime());
               
             }
             
             
            callable=con.getConnection().prepareCall(sql);
            
            callable.setInt(1,reminderId);
            callable.setInt(2,sclId);
            callable.setInt(3,acmdid);
            callable.setInt(4,clsid);
            callable.setInt(5,stby);
            callable.setTimestamp(6,tmpreminderSetDate);
            callable.setInt(7, studid);
            callable.setInt(8, rmdId);
            if(rmdId==1){
            callable.setString(9, base64_OtherTitle);}
            else{
            callable.setString(9, null);
            }
            callable.setString(10, base64Reminder);
            callable.setTimestamp(11,tmpreminderOnDate);
            if(strReminderTime.equalsIgnoreCase("-9999")){
                 callable.setString(12, null);
            }else{
            callable.setString(12, strReminderTime);
            }
            callable.setInt(13,stby);
            callable.setTimestamp(14,tmpreminderSetDate);
          
            int effRow=callable.executeUpdate();
            
            resId=callable.getInt(15);
             strUSerToken=callable.getString(16);
            if(resId==-2){
                
                outObject.put("Status", "Success");
                outObject.put("Message", "Student reminder has been edited sucessfuly");
                fcm.send_FCM_Notification(strUSerToken, base64Reminder,"Student Reminder Update");
                 
            }else if(resId==-1){
                outObject.put("Status", "Fail");
                outObject.put("Message", "No changes to edit");
                
            }else{
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
public String getStudentReminderDetailsById(){
    try{
    String sql="CALL `Sp_Get_tblStudentRemainderDetailsById`(?);";
    fetchNoticeDetailsById(noticeBoardData,sql);
    
    
    if(rs.first())
           {
              fetchNoticeDetailsForEdit();
              
              
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
public  String deleteStudentReminder(){
    try{
    /*String sql="CALL `Sp_Delete_tblstudentreminder`(?);";
    fetchNoticeDetailsById(noticeBoardData,sql);
    effRows=callable.executeUpdate();
    String strStatus="", strMessage="";

           if(effRows==0){
             strStatus="Success";
             strMessage="Student reminder deleted successfully..";
             }else {
             
             strStatus="Fail";
             strMessage="Student reminder couldn't be deleted..";
             
           }
           outObject.put("Status",strStatus);
           outObject.put("Message",strMessage);*/
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
public String deleteStaffReminder(){
    try{
    /*String sql="CALL Sp_Delete_tblstaffreminderDetailsById(?);";
    fetchNoticeDetailsById(noticeBoardData,sql);
    effRows=callable.executeUpdate();
    String strStatus="", strMessage="";

           if(effRows==0){
             strStatus="Success";
             strMessage="Staff Reminder deleted successfully..";
             }else {
             
             strStatus="Fail";
             strMessage="Staff Reminder couldn't be deleted..";
             
           }
           outObject.put("Status",strStatus);
           outObject.put("Message",strMessage);*/
                
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
public String insertstaffReminder(){
    
    JSONObject outObject = new JSONObject();
           try
           {
           JSONArray array= noticeBoardData.getJSONArray("StaffReminderArray");
         int rowCount=0;
         int effRow=0;
          Date reminderSetDate,reminderOnDate;
         Timestamp tmpReminderSetDate,tmpReminderOndate;
             for (int i=0; i < array.length(); i++) {
                 String sql="CALL `Sp_Insert_tblStaffReminder`(?,?,?,?,?,?, ?,?,?,?,?,?,?,?,?)";
           JSONObject reminderObject = array.getJSONObject(i);
            int sclId = Integer.parseInt(reminderObject.getString("SchoolId"));
             
            int acmdid =Integer.parseInt(reminderObject.getString("AcademicYearId"));
            
            int stby = Integer.parseInt(reminderObject.getString("EnteredBy"));
            
             String strReminderSetDate = reminderObject.getString("ReminderSetDate");
          //  
             int stfid = Integer.parseInt(reminderObject.getString("SentTo"));
           
             int rmdId = Integer.parseInt(reminderObject.getString("ReminderTitle"));
             String rmndrOtherTitle=reminderObject.getString("OtherTitle");
             String reminder = reminderObject.getString("Reminder");
             String base64_OtherTitle=StringUtil.base64ToString(rmndrOtherTitle);
             String base64Reminder=StringUtil.base64ToString(reminder);
             String strReminderDate = reminderObject.getString("ReminderDate");
              String strReminderTime = reminderObject.getString("ReminderTime");
   //          java.util.Date rmdrDate= formatterentrdt.parse(strReminderDate);
             
                Date rmdrSetDateSQL = null;
                java.sql.Date sqlRmdrSetDate = null;
                tmpReminderSetDate=null;
                reminderSetDate=null;
                if(!(strReminderSetDate.equals("")))
                {
                    reminderSetDate= new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").parse(strReminderSetDate);
   //                    SimpleDateFormat sdf = new SimpleDateFormat("M/d/yy h:mm:ss a",Locale.US);
                   SimpleDateFormat dateFormatToServer = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                   String strRmdSetDateInFormated = dateFormatToServer.format(reminderSetDate);
                    tmpReminderSetDate=new Timestamp(reminderSetDate.getTime());
//                   rmdrSetDateSQL = dateFormatToServer.parse(strRmdSetDateInFormated);
//
//                   //SQL Date For Stored Procuder Init
//                   sqlRmdrSetDate = new java.sql.Date(rmdrSetDateSQL.getTime());
                }
                 Date rmdrOnDateSQL = null;
                java.sql.Date sqlRmdrOnDate = null;
                tmpReminderOndate=null;
                if(!(strReminderDate.equals("")))
                {
                     reminderOnDate = new SimpleDateFormat("dd-MM-yyyy").parse(strReminderDate);

                   SimpleDateFormat dateFormatToServer = new SimpleDateFormat("yyyy-MM-dd");
                   String strRmdOnDateInFormated = dateFormatToServer.format(reminderOnDate);
                   tmpReminderOndate=new Timestamp(reminderOnDate.getTime());
                   rmdrOnDateSQL = dateFormatToServer.parse(strRmdOnDateInFormated);

                   //SQL Date For Stored Procuder Init
                   //sqlRmdrOnDate = new java.sql.Date(rmdrOnDateSQL.getTime());
                sqlRmdrOnDate = new java.sql.Date(rmdrOnDateSQL.getTime());
               
                }
             
             
             
             
            callable=con.getConnection().prepareCall(sql);
            
            callable.setInt(1,0);
            callable.setInt(2,sclId);
            callable.setInt(3,acmdid);
            
            callable.setInt(4,stby);
            callable.setTimestamp(5,tmpReminderSetDate);
            //callable.setDate(5,sqlRmdrSetDate);
            callable.setInt(6, stfid);
            callable.setInt(7, rmdId);
            callable.setString(8,base64_OtherTitle);
            callable.setString(9, base64Reminder);
            //callable.setDate(10, sqlRmdrOnDate); 
            callable.setTimestamp(10, tmpReminderOndate);
            if(strReminderTime.equalsIgnoreCase("-9999")){
                 callable.setString(11,null);
            }else{
            callable.setString(11,strReminderTime);
            }
            callable.setInt(12,stby);
            callable.setTimestamp(13,tmpReminderSetDate);
            // new java.sql.Date(rmdrDate.getTime())
          
            effRow=callable.executeUpdate();
            
  //          resId=Integer.parseInt(callable.getString("ResultId"));
            resId=callable.getInt(14); 
            strUSerToken=callable.getString(15);
            if(resId==-1){
                 break;
             }
             else if(resId>0)
            {
                 rowCount++;
                 fcm.send_FCM_Notification(strUSerToken, base64Reminder, "Staff Reminder");
            }else{
                 break;
            }
             }
             if(resId==-1){
                outObject.put("Status", "Fail");
                outObject.put("Message", "Reminder has been already added");
                
             }
             else if(resId>0)
            {
                outObject.put("Status", "Success");
                outObject.put("Message", "Reminder has been added sucessfuly");
                outObject.put("number of rows affected",rowCount);
                 
            }else{
                outObject.put("Status", "Fail");
                outObject.put("Message", "Couldn't add the reminder because of some error");
                
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
public String editStaffReminder(){
    try
    {
        Date reminderSetDate,reminderOnDate;
        String strReminderTime="";
        String sql="CALL `Sp_Insert_tblStaffReminder`(?,?,?,?,?,?, ?,?,?,?,?,?,?,?,?)";
           int rmdrId=Integer.parseInt(noticeBoardData.getString("ReminderId"));
            int sclId = Integer.parseInt(noticeBoardData.getString("SchoolId"));
             
            int acmdid =Integer.parseInt(noticeBoardData.getString("AcademicYearId"));
            
            int stby = Integer.parseInt(noticeBoardData.getString("EnteredBy"));
            
             String strReminderSetDate = noticeBoardData.getString("ReminderSetDate");
          //  
             int stfid = Integer.parseInt(noticeBoardData.getString("SentTo"));
           
             int rmdtitleId = Integer.parseInt(noticeBoardData.getString("ReminderTitle"));
             
             String reminder = noticeBoardData.getString("Reminder");
             String base64_OtherTitle="";
             if(rmdtitleId==1){
             String rmndrOtherTitle=noticeBoardData.getString("OtherTitle");
            base64_OtherTitle=StringUtil.base64ToString(rmndrOtherTitle);
             }
            String base64Reminder=StringUtil.base64ToString(reminder);
             String strReminderDate = noticeBoardData.getString("ReminderDate");
             
               strReminderTime= noticeBoardData.getString("ReminderTime");
   //          java.util.Date rmdrDate= formatterentrdt.parse(strReminderDate);
             
                Date rmdrSetDateSQL = null;
                java.sql.Date sqlRmdrSetDate = null;
                 Timestamp tmpReminderSetDate=null;
                reminderSetDate=null;
                if(!(strReminderSetDate.equals("")))
                {
                    reminderSetDate= new SimpleDateFormat("dd/MM/yyyy").parse(strReminderSetDate);
   //                    SimpleDateFormat sdf = new SimpleDateFormat("M/d/yy h:mm:ss a",Locale.US);
                   SimpleDateFormat dateFormatToServer = new SimpleDateFormat("yyyy-MM-dd");
                   String strRmdSetDateInFormated = dateFormatToServer.format(reminderSetDate);
                    tmpReminderSetDate=new Timestamp(reminderSetDate.getTime());
//                   rmdrSetDateSQL = dateFormatToServer.parse(strRmdSetDateInFormated);
//
//                   //SQL Date For Stored Procuder Init
//                   sqlRmdrSetDate = new java.sql.Date(rmdrSetDateSQL.getTime());
                }
                 Date rmdrOnDateSQL = null;
                java.sql.Date sqlRmdrOnDate = null;
             Timestamp   tmpReminderOndate=null;
                if(!(strReminderDate.equals("")))
                {
                     reminderOnDate = new SimpleDateFormat("dd/MM/yyyy").parse(strReminderDate);

                   SimpleDateFormat dateFormatToServer = new SimpleDateFormat("yyyy-MM-dd");
                   String strRmdOnDateInFormated = dateFormatToServer.format(reminderOnDate);
                   tmpReminderOndate=new Timestamp(reminderOnDate.getTime());
                   rmdrOnDateSQL = dateFormatToServer.parse(strRmdOnDateInFormated);

                   //SQL Date For Stored Procuder Init
                   //sqlRmdrOnDate = new java.sql.Date(rmdrOnDateSQL.getTime());
                sqlRmdrOnDate = new java.sql.Date(rmdrOnDateSQL.getTime());
               
                }
             
             
             
             
            callable=con.getConnection().prepareCall(sql);
            
            callable.setInt(1,rmdrId);
            callable.setInt(2,sclId);
            callable.setInt(3,acmdid);
            
            callable.setInt(4,stby);
            callable.setTimestamp(5,tmpReminderSetDate);
            //callable.setDate(5,sqlRmdrSetDate);
            callable.setInt(6, stfid);
            callable.setInt(7, rmdtitleId);
            if(rmdtitleId!=1){
            callable.setString(8,null);
            }else{
                callable.setString(8,base64_OtherTitle);
            }
            
            callable.setString(9, base64Reminder);
            //callable.setDate(10, sqlRmdrOnDate); 
            callable.setTimestamp(10, tmpReminderOndate); 
            if(strReminderTime.equalsIgnoreCase("-9999")){
            callable.setString(11,null);
            }else {
                
                callable.setString(11,strReminderTime);
            }
            callable.setInt(12,stby);
            callable.setTimestamp(13,tmpReminderSetDate);
            // new java.sql.Date(rmdrDate.getTime())
          
            int effRow=callable.executeUpdate();
            
  //          resId=Integer.parseInt(callable.getString("ResultId"));
            resId=callable.getInt(14); 
            strUSerToken=callable.getString(15);
             if(resId==-2){
               
                outObject.put("Status", "Success");
                outObject.put("Message", "Reminder has been Edited sucessfuly");
                fcm.send_FCM_Notification(strUSerToken, base64Reminder, " Staff Reminder Update ");
                 
            }else if(resId==-1){
                outObject.put("Status", "Fail");
                outObject.put("Message", "No changes to edit"); 
                
            }else{
                outObject.put("Status", "Fail");
                outObject.put("Message", "Couldn't Edit the reminder because of some error");
                
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
