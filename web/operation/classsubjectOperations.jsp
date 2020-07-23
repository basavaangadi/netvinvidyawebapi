<%-- 
    Document   : classsubjectOperations
    Created on : 20 Jul, 2017, 7:29:33 PM
    Author     : basava
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="org.json.JSONObject"%>
<%@page import="java.io.BufferedReader"%>
<%@page import="com.example.operations.ClassSubjectOperation"%>

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
    // schoolid,
    data = builder.toString();
    JSONObject object = new JSONObject(data); 
    String opName=object.getString("OperationName");
    JSONObject classSubjectData= object.getJSONObject("classSubjectData");
    // 
    ClassSubjectOperation operation= new ClassSubjectOperation(classSubjectData);
   // {"OperationName":"StudentNameWithRollNo","classSubjectData":{"School":"Holy cross convent","Class":"1","Section":"A","RollNumber":"1"}}
    if(opName.equals("StudentNameWithRollNo")){
        out.println(operation.StudentNameWithRollNo());
       }// {"OperationName":"SectionDetails","classSubjectData":{"SchoolId":"1","Class":"5th"}
    else if(opName.equals("SectionDetails")){
        out.println(operation.SectionDetails());
    }//{"OperationName":"Staff_ClassNames","classSubjectData":{"SchoolId":"3","AcademicYearId":"2"}}
    else if(opName.equals("Staff_ClassNames"))
    {
        out.println(operation.Staff_ClassNames());
    }
    //{"OperationName":"Staff_Class_With_Section","classSubjectData":{"SchoolId":"3","AcademicYearId":"2","Class":"1"}}
    else if(opName.equals("Staff_Class_With_Section"))
    {
        out.println(operation.Staff_Class_With_Section());
    }
    //{"OperationName":"getSubjectsWithClassAndSetion","classSubjectData":{"Class":"1","Section":"A","SchoolId":"4"}}
     else if(opName.equals("getSubjectsWithClassAndSetion"))
    {
        out.println(operation.getSubjectsWithClassAndSetion());
    }
     //{"OperationName":"sp_get_marks_entered_subjects","classSubjectData":{"Class":"1","Section":"A","SchoolId":"4"}}
     else if(opName.equals("getMarksEnteredSubjects"))
    {
        out.println(operation.getMarksEnteredSubjects());
    }
     
     
     
    //{"OperationName":"getClasswiseSubjects","classSubjectData":{"ClassId":"5"}}
     else if(opName.equals("getClasswiseSubjects"))
    {
        out.println(operation.getClasswiseSubjects());
    }
    //{"OperationName":"getClasswiseStudentsList","classSubjectData":{"ClassId":"5"}}
     else if(opName.equals("getClasswiseStudentsList"))
    {
        out.println(operation.getClasswiseStudentsList());
    }
     
     ////{"OperationName":"getStaffwiseClassList","classSubjectData":{"SchoolId":"3","AcademicYearId":"2","StaffId":"2"}}
     else if(opName.equals("getStaffwiseClassList"))
    {
        out.println(operation.getStaffwiseClassList());
    }
    //{"OperationName":"getStaffwiseSectionList","classSubjectData":{"SchoolId":"3","AcademicYearId":"2","StaffId":"2","Class":"1"}}
     else if(opName.equals("getStaffwiseSectionList"))
    {
        out.println(operation.getStaffwiseSectionList());
    }
     //{"OperationName":"getClasslist", "getClasslist""classSubjectData":{"SchoolId":"4","AcademicYearId":"4"}}
     else if(opName.equals("getClasslist"))
    {
        out.println(operation.getClasslist());
    }
     
     
    //{"OperationName":"getStaffwiseClassSubjects","classSubjectData":{"SchoolId":"3","AcademicYearId":"2","StaffId":"2","ClassId":"5"}}
     else if(opName.equals("getStaffwiseClassSubjects"))
    {
        out.println(operation.getStaffwiseClassSubjects());
    }
        } catch(Exception e){
        out.println(e.toString());
    }
    
    
%>