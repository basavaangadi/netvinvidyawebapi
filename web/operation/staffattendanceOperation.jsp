<%-- 
    Document   : staffattendanceOperation
    Created on : 20 Jul, 2017, 4:42:15 PM
    Author     : basava
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="com.example.operations.StaffAttendanceOperation"%>
<%@page import="org.json.JSONObject"%>
<%@page import="java.io.BufferedReader"%>
<%!
String data;
%>
<%
    try {
            StringBuilder builder = new StringBuilder();
   BufferedReader reader = request.getReader();
    String line;
    while ((line = reader.readLine()) != null) {
        builder.append(line);
    }
    data = builder.toString();
    JSONObject object = new JSONObject(data); 
    String opName=object.getString("OperationName");
    JSONObject staffAttendanceData= object.getJSONObject("staffAttendanceData");
    // {"OperationName":"ExamSyllabusSet","ExamData":{"SchoolId":"1", "AcademicYearId":"1", "SetBy":"1", "ClassId":"1", "SyllabusSetDate":"2017-06-12 10:00:00", "ExamId":"1","SubjectId":"1","StudentId":"1","MarksObtained":"50","Grade":"A","Date":"2017-06-12 10:00:00","Syllabus":"bvsdhj"}}
    StaffAttendanceOperation operation= new StaffAttendanceOperation(staffAttendanceData);
    if(opName.equals("StaffAttendance_Insert")){
        out.println(operation.StaffAttendance_Insert());
       }// {"OperationName":"ExamResultDisplay_Studentwise","ExamData":{"SchoolId":"", "AcademicYearId":"", "SetBy":"", "ClassId":"", "SyllabusSetDate":"", "ExamId":"","SubjectId":"","StudentId":"1","MarksObtained":"","Grade":"","Date":"","Syllabus":""}}
    else if(opName.equals("AttendenceDisplay_Staffwise")){
        out.println(operation.AttendenceDisplay_Staffwise());
    }
     
    } catch (Exception e) {
            out.println(e.toString());
        }
    %>
    