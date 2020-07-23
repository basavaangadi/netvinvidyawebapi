<%-- 
    Document   : serviceWithJsonArray
    Created on : 8 Feb, 2018, 8:33:17 PM
    Author     : basava
--%>

<%@page import="org.json.JSONArray"%>
<%@page import ="com.example.operations.ServiceWithJsonArray"%>
<%@page import="org.json.JSONObject"%>
<%@page import="java.io.BufferedReader"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%!
 String data;
%>

<!DOCTYPE html>
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
    JSONArray otherData= object.getJSONArray("JSONData");
    // {"OperationName":"ExamSyllabusSet","ExamData":{"SchoolId":"1", "AcademicYearId":"1", "SetBy":"1", "ClassId":"1", "SyllabusSetDate":"2017-06-12 10:00:00", "ExamId":"1","SubjectId":"1","StudentId":"1","MarksObtained":"50","Grade":"A","Date":"2017-06-12 10:00:00","Syllabus":"bvsdhj"}}
//    ServiceWithJsonArray operation= new ServiceWithJsonArray(otherData);
    
    if(opName.equals("homeworkFeedback_Insert_withJsonArray"))
    {
  //      out.println(operation.getSubjectlist());
    }
    }catch (Exception e) {
            out.println(e.toString());
        }
    %>
