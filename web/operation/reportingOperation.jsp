<%-- 
    Document   : reportingOperation
    Created on : 9 Aug, 2018, 3:28:23 PM
    Author     : basava
--%>

<%@page language="java"  contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@page import ="org.json.JSONObject"%>
<%@page import ="java.io.BufferedReader"%>
<%@page import ="com.example.operations.ReportingOperation"%>
<%! 
    String Data;
%>
<%
try{
    StringBuilder builder = new StringBuilder();
    BufferedReader reader= request.getReader();
    String line ;
    while ((line = reader.readLine()) != null) {
        builder.append(line);
    }
    Data=builder.toString();
    JSONObject object=new JSONObject(Data);
    String opName=object.getString("OperationName");
    JSONObject reportingData =object.getJSONObject("reportingData");
    ReportingOperation operation= new ReportingOperation(reportingData);
    //{"OperationName":"insertStaffDailyReport","reportingData":{"StaffId":"5","ClassId":"5","SetBy":"2","ReportDate":"26-02-2018","Period":"1","Report":"this is the report","SubjectId":"8","ReportSentDate":"28-02-2018 11:00:30"}}
     if(opName.equals("insertStaffDailyReport")){
        out.println(operation.insertStaffDailyReport());
       }
     //{"OperationName":"getClasswiseStaffReport","reportingData":{"ClassId":"5","ReportDate":"26-02-2018"}}
     else if(opName.equals("getClasswiseStaffReport")){
        out.println(operation.getClasswiseStaffReport());
       }
     //{"OperationName":"getStaffwiseStaffReport","reportingData":{"StaffId":"5","ReportDate":"26-02-2018"}}
     else if(opName.equals("getStaffwiseStaffReport")){
        out.println(operation.getStaffwiseStaffReport());
       }
     //{"OperationName":"getPeriodForReport","reportingData":{"ClassId":"5","DayId":"2"}}
     else if(opName.equals("getPeriodForReport")){
        out.println(operation.getPeriodForReport());
       }
      //{"OperationName":"insertStaffDailyReport","reportingData":{"StaffId":"5","ClassId":"5","SetBy":"2","ReportDate":"26-02-2018","Period":"1","Report":"this is the report","SubjectId":"8","ReportSentDate":"28-02-2018 11:00:30"}}
     if(opName.equals("editStaffDailyReport")){
        out.println(operation.editStaffDailyReport());
       }
      //{"OperationName":"insertStaffDailyReport","reportingData":{"StaffId":"5","ClassId":"5","SetBy":"2","ReportDate":"26-02-2018","Period":"1","Report":"this is the report","SubjectId":"8","ReportSentDate":"28-02-2018 11:00:30"}}
     if(opName.equals("deleteStaffReport")){
        out.println(operation.deleteStaffReport());
       }
}catch(Exception e){
    e.printStackTrace();
}    
%>

