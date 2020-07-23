<%-- 
    Document   : otherOperation
    Created on : 27 Oct, 2017, 3:26:45 PM
    Author     : basava
--%>
<%@page import ="com.example.operations.OtherOperation"%>
<%@page import="org.json.JSONObject"%>
<%@page import="java.io.BufferedReader"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
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
    JSONObject otherData= object.getJSONObject("otherData");
    // {"OperationName":"ExamSyllabusSet","ExamData":{"SchoolId":"1", "AcademicYearId":"1", "SetBy":"1", "ClassId":"1", "SyllabusSetDate":"2017-06-12 10:00:00", "ExamId":"1","SubjectId":"1","StudentId":"1","MarksObtained":"50","Grade":"A","Date":"2017-06-12 10:00:00","Syllabus":"bvsdhj"}}
    OtherOperation operation= new OtherOperation(otherData);
    
  //  {"OperationName":"getSubjectlist","otherData":{"studentId":"5"}}
    // verified workinhg fine
    
     if(opName.equals("getSubjectlist"))
    {
        out.println(operation.getSubjectlist());
    }
    // {"OperationName":"getSyllabus","otherData":{"studentId":"5","subject":"kannada"}}
    // {"OperationName":"getSyllabus","otherData":{"studentId":"5","subject":"Kannada"}}

    // verified working fine
     else if(opName.equals("getSyllabus")){
        out.println(operation.getSyllabus());
       }
        //{"OperationName":"getGAlleryImagesByEventID","otherData":{"studentId":"5","eventId":"5"}}
   
    else if(opName.equals("getGAlleryImagesByEventID")){
        
        out.println(operation.getGAlleryImagesByEventID());
    }
    
    //{"OperationName":"getStaffGAlleryImagesByEventId","otherData":{"eventId":"5"}}
     else if(opName.equals("getStaffGAlleryImagesByEventId")){
        
        out.println(operation.getStaffGAlleryImagesByEventId());
    }
     
     //{"OperationName":"getStaffGAlleryImagesByEventId","otherData":{"eventId":"5"}}
     else if(opName.equals("getStudentGAlleryImagesByEventId")){
        
        out.println(operation.getStuentGAlleryImagesByEventId());
    }
    //{"OperationName":"getGAlleryEventList","otherData":{"studentId":"5","AcademicYearId":"2"}}
    
    else if(opName.equals("getGAlleryEventList")){
        out.println(operation.getGAlleryEventList());   
    }
    //{"OperationName":"getSatffGAlleryEventList","otherData":{"staffId":"2","AcademicYearId":"2"}}
    else if(opName.equals("getSatffGAlleryEventList")){
        out.println(operation.getSatffGAlleryEventList());
    }
        //{"OperationName":"getStaffEventList","otherData":{"staffId":"2","AcademicYearId":"2"}}
    //     verified working fine

    else if(opName.equals("getStaffEventList"))
    {
        out.println(operation.getStaffEventList());
    }
     
      //{"OperationName":"getClasswiseAssignmentList","otherData":{"ClassId":"127"}}
    //     verified working fine

    else if(opName.equals("getClasswiseAssignmentList"))
    {
        out.println(operation.getClasswiseAssignmentList());
    }
    
     //{"OperationName":"getStudentwiseAssignmentList","otherData":{"StudentId":"9460"}}
    //     verified working fine

    else if(opName.equals("getStudentwiseAssignmentList"))
    {
        out.println(operation.getStudentwiseAssignmentList());
    }
    
    
    //{"OperationName":"editAssignmentDetails","otherData":{"StudentId":"9460"}}
    //     verified working fine

    else if(opName.equals("editAssignmentDetails"))
    {
        out.println(operation.editAssignmentDetails());
    }
    
    
    
    else if(opName.equals("getEventList"))
    {
        out.println(operation.getEventList());
    }
    
//{"OperationName":"getEventDetails","otherData":{"studentId":"5","eventId":"7"}}
   //verified working fine
    else if(opName.equals("getEventDetails"))
    {
        out.println(operation.getEventDetails());
    }
    //{"OperationName":"getStaffEventDetails","otherData":{"eventId":"7"}}
    else if(opName.equals("getStaffEventDetails"))
    {
        out.println(operation.getStaffEventDetails());
    }
    
    //{"OperationName":"getStaffclasswiseSubject","otherData":{"ClassId":"5"}}
      else if(opName.equals("getStaffclasswiseSubject"))
    {
        out.println(operation.getStaffclasswiseSubject());
    }
   //{"OperationName":"getStaffclasswiseSubjectSyllabus","otherData":{"ClassId":"5","SubjectId":"8"}}
      else if(opName.equals("getStaffclasswiseSubjectSyllabus"))
    {
        out.println(operation.getStaffclasswiseSubjectSyllabus());
    }
    } catch (Exception e) {
            out.println(e.toString());
        }
%>


