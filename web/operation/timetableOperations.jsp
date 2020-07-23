<%-- 
    Document   : timetableOperations
    Created on : 20 Jul, 2017, 7:46:54 PM
    Author     : basava
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="com.example.operations.TimeTableOperations"%>
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
    JSONObject timeTableData= object.getJSONObject("timeTableData");
    // {"OperationName":"ExamSyllabusSet","ExamData":{"SchoolId":"1", "AcademicYearId":"1", "SetBy":"1", "ClassId":"1", "SyllabusSetDate":"2017-06-12 10:00:00", "ExamId":"1","SubjectId":"1","StudentId":"1","MarksObtained":"50","Grade":"A","Date":"2017-06-12 10:00:00","Syllabus":"bvsdhj"}}
    TimeTableOperations operation= new TimeTableOperations(timeTableData);
    
        // {"OperationName":"timetableDisplay_ClassWise","timeTableData":{"StudentId":"5","dayId":"1"}}
    //working and verifed 
    
    if(opName.equals("timetableDisplay_ClassWise")){ 
        out.println(operation.timetableDisplay_ClassWise());
       }

// {"OperationName":"TimetableDisplay_Staffwise","timeTableData":{"StaffId":"2", "dayId":"1"}}
    else if(opName.equals("TimetableDisplay_Staffwise")){
        out.println(operation.TimetableDisplay_Staffwise());
    }

//{"OperationName":"getStaffClassWiseTimetable","timeTableData":{"ClassId":"5","dayId":"1"}}
   else if(opName.equals("getStaffClassWiseTimetable")){
        out.println(operation.getStaffClassWiseTimetable());
    }
    
   // {"OperationName":"getStaffTimetable","timeTableData":{"StaffId":"3","dayId":"1"}}
   else if(opName.equals("getStaffTimetable")){
        out.println(operation.getStaffTimetable());
    }
    } catch (Exception e) {
            out.println(e.toString());
        }    
    
    %>
        

