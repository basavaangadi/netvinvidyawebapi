/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.operations;

import com.example.db.DBConnect;
import com.example.db.StringUtil;
import java.sql.ResultSet;
import java.sql.CallableStatement;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 *
 * @author basava
 */
public class ReportingOperation {
    JSONObject reportingData;
    CallableStatement callable=null;
    DBConnect con=new DBConnect();
    JSONArray array = new JSONArray();
    int resId=0,effRows=0;
    JSONObject outObject = new JSONObject();
    ResultSet rs=null;
    public ReportingOperation(JSONObject reportingData){
       this.reportingData=reportingData;
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
    
    public void printFaliureStatus(JSONArray array){
        try{
            outObject.put("Status", "Failure");
            outObject.put("Message", "Data not found..");
            outObject.put("Result", array);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public void fetchReportDetailsById(JSONObject reportingData,String sql){
    try{
    int  ntcId = Integer.parseInt(reportingData.getString("Id"));
   
       
    callable=con.getConnection().prepareCall(sql);
    callable.setInt(1, ntcId);
    
    rs=callable.executeQuery();
}catch(Exception e){
    e.printStackTrace();
}
}
    
    public void  fetchDailyReport(){
        try{
        do{
                  JSONObject object= new JSONObject();
                  String strStaffReportId= rs.getString("StaffReportId");
                  String strReportOnDate=rs.getString("ReportDate");
                  String strClass=rs.getString("Cls_Section");
                  String strPeriod=rs.getString("Period");
                  String strSubject=rs.getString("Subject");
                  String strReport=rs.getString("Report");
                  String strClassId= rs.getString("ClassId");
                  String strSubjectId=rs.getString("SubjectId");
                  String strStaffId=rs.getString("StaffId");
                  
                  object.put("StaffReportId", strStaffReportId);
                   object.put("ReportOnDate", strReportOnDate);
                   object.put("Class", strClass);
                   object.put("Period", strPeriod);
                   object.put("Subject", strSubject);
                   object.put("Report", strReport);
                   object.put("ClassId", strClassId);
                   object.put("SubjectId", strSubjectId);
                   object.put("ClassId", strClassId);
                  
                   array.put(object);
              }while(rs.next());
        }catch(Exception e){
            e.printStackTrace();
        }
    } 
    public String insertStaffDailyReport(){
        try
           {
               
            Timestamp tmpReportSentDate=null,tmpReportOnDate=null;
                 String sql = "CALL sp_insert_tblstaffdailyreport(?,?,?,?,?,?,?,?,?,?)";
                 
                 int staffId = Integer.parseInt(reportingData.getString("StaffId"));
                 int clsId=Integer.parseInt(reportingData.getString("ClassId"));
                 String strReportDate= reportingData.getString("Date");
                 int period = Integer.parseInt(reportingData.getString("Period"));
                  String strReport= reportingData.getString("Report");
                 int subId=Integer.parseInt(reportingData.getString("SubjectId"));
                 String strReportSentDate= reportingData.getString("ReportSentDate");
                 String base64Report=StringUtil.base64ToString(strReport);
                 if(!(strReportDate.equals("")))
             {
                 Date repDate = new SimpleDateFormat("dd-MM-yyyy").parse(strReportDate);
               tmpReportOnDate= new Timestamp(repDate.getTime());
                
             }
                 if(!(strReportSentDate.equals("")))
             {
                 Date repSentDate = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss").parse(strReportSentDate);
               tmpReportSentDate= new Timestamp(repSentDate.getTime());
                
             }
                 
                 callable=con.getConnection().prepareCall(sql);
                callable.setInt(1,0);
                 callable.setInt(2,clsId);
                 callable.setTimestamp(3, tmpReportOnDate);
                 callable.setInt(4,period);
                 callable.setInt(5,subId);
                 callable.setString(6, base64Report);
                 callable.setInt(7,staffId);
                 callable.setTimestamp(8, tmpReportSentDate);
                 callable.setInt(9,staffId);
                 
                 int effrows =callable.executeUpdate();
                 resId=callable.getInt(10);
               
          
           if(resId>0)
            {
                 outObject.put("Status", "Success");
                 outObject.put("Message", "Report sent sucessfully");
                 outObject.put("resId",resId);
                 
            }else if(resId==-1){
                 outObject.put("Status", "Fail");
                 outObject.put("Message", "Report has been already sent");
                  outObject.put("resId",resId);
            }else {
            outObject.put("Status", "Fail");
                 outObject.put("Message", "Something went wrong");
                  outObject.put("resId",resId);
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
    public String getClasswiseStaffReport(){
        try{
    String sql="CALL `Sp_Get_dailyreport_By_clsId`(?,?);";
    int clsId=Integer.parseInt(reportingData.getString("ClassId"));
    String strReportDate= reportingData.getString("Date");
    Timestamp tmpReportOnDate=null;
    if(!(strReportDate.equals("")))
             {
                 Date repDate = new SimpleDateFormat("dd-MM-yyyy").parse(strReportDate);
               tmpReportOnDate= new Timestamp(repDate.getTime());
                
             }
    callable= con.getConnection().prepareCall(sql);
    callable.setInt(1,clsId);
    callable.setTimestamp(2,tmpReportOnDate);
    rs= callable.executeQuery();
    if(rs.first())
           {
           fetchDailyReport();
   
              
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
    public String getPeriodForReport(){
       try{
    String sql="CALL `Sp_Dropdown_timetable_period`(?,?);";
    int clsId=Integer.parseInt(reportingData.getString("ClassId"));
    int dayId= Integer.parseInt(reportingData.getString("DayId"));
   
    callable= con.getConnection().prepareCall(sql);
    callable.setInt(1,clsId);
    callable.setInt(2,dayId);
    rs= callable.executeQuery();
    if(rs.first())
           {
                  JSONObject object1= new JSONObject();
                  object1.put("DayId", "0");
                  object1.put("Period", "Select Period");
                  object1.put("Subject", "Select Period");
                   array.put(object1);
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
        
            if(array.length()>1){
               printSuccessStatus(array);
               }else{
                
                   printFaliureStatus(array);
               }               
           }
           
          else {
                  JSONObject object1= new JSONObject();
                  object1.put("DayId", "0");
                  object1.put("Period", "Select Period");
                  object1.put("Subject", "Select Period");
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
    public String getStaffwiseStaffReport(){
        try{
    String sql="CALL `Sp_Get_dailyreport_By_StaffId`(?,?);";
    int stfId=Integer.parseInt(reportingData.getString("StaffId"));
    String strReportDate= reportingData.getString("Date");
    Timestamp tmpReportOnDate=null;
    if(!(strReportDate.equals("")))
             {
                 Date repDate = new SimpleDateFormat("dd-MM-yyyy").parse(strReportDate);
               tmpReportOnDate= new Timestamp(repDate.getTime());
                
             }
    callable= con.getConnection().prepareCall(sql);
    callable.setInt(1,stfId);
    callable.setTimestamp(2,tmpReportOnDate);
    rs= callable.executeQuery();
    if(rs.first())
           {
           fetchDailyReport();
   
              
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
    public  String editStaffDailyReport(){
     try
           {
               
            Timestamp tmpReportSentDate=null,tmpReportOnDate=null;
                 String sql = "CALL sp_insert_tblstaffdailyreport(?,?,?,?,?,?,?,?,?,?)";
                 int staffReportId = Integer.parseInt(reportingData.getString("StaffReportId"));
                 int staffId = Integer.parseInt(reportingData.getString("StaffId"));
                 int clsId=Integer.parseInt(reportingData.getString("ClassId"));
                 String strReportDate= reportingData.getString("Date");
                 int period = Integer.parseInt(reportingData.getString("Period"));
                  String strReport= reportingData.getString("Report");
                  String reportBase64=StringUtil.base64ToString(strReport);
                 int subId=Integer.parseInt(reportingData.getString("SubjectId"));
                Date repSentDateSQL = null;
                 java.sql.Date sqlRepSentDate = null;
                 if(!(strReportDate.equals("")))
             {
                 Date repDate = new SimpleDateFormat("dd-MM-yyyy").parse(strReportDate);
               tmpReportOnDate= new Timestamp(repDate.getTime());
                SimpleDateFormat dateFormatToServer = new SimpleDateFormat("dd-MM-yyyy");
                String strNoteOnDateInFormated = dateFormatToServer.format(repDate);
                
                repSentDateSQL = dateFormatToServer.parse(strNoteOnDateInFormated);
//              SQL Date For Stored Procuder Init
                sqlRepSentDate = new java.sql.Date(repSentDateSQL.getTime());
             }
                
                 
                 callable=con.getConnection().prepareCall(sql);
                callable.setInt(1,staffReportId);
                 callable.setInt(2,clsId);
                 callable.setDate(3, sqlRepSentDate);
                 callable.setInt(4,period);
                 callable.setInt(5,subId);
                 callable.setString(6, reportBase64);
                 callable.setInt(7,staffId);
                 callable.setTimestamp(8, null);
                 callable.setInt(9,staffId);
                 
                 int effrows =callable.executeUpdate();
                 resId=callable.getInt(10);
               
          
           if(resId==-2)
            {
                 outObject.put("Status", "Success");
                 outObject.put("Message", "Report updated sucessfully");
                 outObject.put("resId",resId);
                 
            }else if(resId==-1)
            {
                 outObject.put("Status", "Fail");
                 outObject.put("Message", "No changes in Report to update");
                 outObject.put("resId",resId);
                 
            }
           else {
                    outObject.put("Status", "Fail");
                    outObject.put("Message", "Report was not updated");
                    outObject.put("resId",resId);
            }
            return outObject.toString();
        }catch(Exception ee) {
            String exce = ee.toString();
            String exc = "{\"Status\":\"Exception\" ,  \"Message\":\""+exce+"\" ,"
                    + " \"Result\":[]}";
         try {
             outObject.put("Status", "Error");
             outObject.put("Message", exc);
             outObject.put("resId",resId);
         } catch (Exception  ex) {
           ex.fillInStackTrace();
         }
                    
            return exc;    
        }finally{
            closeDBConnection();
        }
    }
    public String deleteStaffReport(){
        try{
    /*String sql="CALL `Sp_Delete_DailyReport_by_StaffReportId`(?);";
    fetchReportDetailsById(reportingData,sql);
   effRows=callable.executeUpdate();
   // String strStatus="", strMessage="";
    //effRows=Integer.parseInt(rs.toString());
        

           if(effRows==0){
             String strStatus="Success";
             String strMessage="Report deleted successfully..";
           outObject.put("Status",strStatus);
           outObject.put("Message",strMessage);
           }else {
             
             String strStatus="Fail";
            String strMessage="Report couldn't be deleted..";
           outObject.put("Status",strStatus);
           outObject.put("Message",strMessage);  
           }
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
}
