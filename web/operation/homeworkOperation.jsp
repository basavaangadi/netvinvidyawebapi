<%-- 
    Document   : homeworkOperation
    Created on : 19 Jul, 2017, 8:28:42 PM
    Author     : basava
--%>
<%@page import="org.json.JSONObject"%>
<%@page import="java.io.BufferedReader"%>
<%@page import="com.example.operations.HomeWorkOperation"%>
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
    JSONObject homeworkData= object.getJSONObject("homeworkData");
    HomeWorkOperation operation= new HomeWorkOperation(homeworkData);
    //{"OperationName":"HomeWork_Insert","homeworkData":{"SchoolId":"1", "AcademicYearId":"1", "SetBy":"1", "ClassId":"1", "SyllabusSetDate":"2017-06-12 10:00:00", "ExamId":"1","SubjectId":"1","StudentId":"1","MarksObtained":"50","Grade":"A","Date":"2017-06-12 10:00:00","Syllabus":"bvsdhj"}}
    if(opName.equals("HomeWorkSubTypeName")){
        out.println(operation.HomeWorkSubTypeName());
    }
    //{"OperationName":"HomeWork_Insert","homeworkData":{"Date":"18-01-2018","StaffId":"2","SubjectId":"8","ClassId": "5","Chapter":"bbbbb","Description":"quesion answer"}}
    else if(opName.equals("HomeWork_Insert")){
       out.println(operation.HomeWork_Insert());
    }
        //{"OperationName":"HomeWork_Insert","homeworkData":{"Date":"18-01-2018","StaffId":"2","SubjectId":"8","ClassId": "5","Chapter":"bbbbb","Description":"quesion answer"}}
    else if(opName.equals("editHomework")){
       out.println(operation.editHomework());
    }
    
    //{"OperationName":"homeworkDisplayBydate","homeworkData":{"StudentId":"5","Date":"22-06-2017"}}
    // verified workingFine
    //in server verified 
    else if(opName.equals("homeworkDisplayBydate")){
        out.println(operation.homeworkDisplayBydate());
    }
    //{"OperationName":"homeworkDisplayDefault","homeworkData":{"StudentId":"5"}}
    // verified workingFine
    //in server verified 
    else if(opName.equals("homeworkDisplayDefault")){
        out.println(operation.homeworkDisplayDefault());
    }
//{"OperationName":"homeworkDisplayClasswise","homeworkData":{"ClassId":"5","SubjectId":"8"}}
    else if(opName.equals("homeworkDisplayClasswise")){
        out.println(operation.homeworkDisplayClasswise());
    }
    //{"OperationName":"homeworkDisplayClasswiseByDate","homeworkData":{"ClassId":"5","SubjectId":"8","Date":"08-01-2018"}}
    else if(opName.equals("homeworkDisplayClasswiseByDate")){
        out.println(operation.homeworkDisplayClasswiseByDate());
    }
     //{"OperationName":"getHomeworkDetailsForEdit","homeworkData":{"HomeworkId":"148"}}
    else if(opName.equals("getHomeworkDetailsForEdit")){
        out.println(operation.getHomeworkDetailsForEdit());
    }
    //{"OperationName":"homeworkDisplayClasswiseByDate","homeworkData":{"ClassId":"5","SubjectId":"8","Date":"08-01-2018"}}
    else if(opName.equals("editHomework")){
        out.println(operation.editHomework());
    }
    //{"OperationName":"deleteHomework","homeworkData":{"HomeworkId":"146"}}
    else if(opName.equals("deleteHomework")){
        out.println(operation.deleteHomework());
    }
    //{"OperationName":"checkHomeworkFeedbackById","homeworkData":{"HomeworkId":"146"}}
    else if(opName.equals("checkHomeworkFeedbackById")){
        out.println(operation.checkHomeworkFeedbackById());
    }
    //{"OperationName":"homeworkFeedback_Insert","homeworkData":{"HomeworkId":"34","EnteredBy":"2","StudentId": "7", "Status":"0","Date":"18-01-2018"}}
    else if(opName.equals("homeworkFeedback_Insert")){
        out.println(operation.homeworkFeedback_Insert());
    }
    else if(opName.equals("homeworkFeedback_Insert_withJsonArray")){
        out.println(operation.homeworkFeedback_Insert_withJsonArray());
    }
/*{"OperationName":"homeworkFeedback_Insert",“homeworkData”: {"HomeworkArray":[{"HomeworkId":"39","EnteredBy":"2","StudentId":
"5","Status":"0","Date":"18-01-2018"},{"HomeworkId":"39","EnteredBy":"2","StudentId":"6","Status":"1","Date":"18-01-2018"},{"HomeworkId":"39","EnteredBy":"2","StudentId":
"7", "Status":"1","Date":"18-01-2018"}]}}
    */
    
   //{"OperationName":"insertHomworkFeedBackWithJSONArray", "homeworkData":{"HomeworkArray":[{"HomeworkId":"39","EnteredBy":"2","StudentId":"5","Status":"0","Date":"18-01-2018"},{"HomeworkId":"39","EnteredBy":"2","StudentId":"5","Status":"0","Date":"18-01-2018"},{"HomeworkId":"39","EnteredBy":"2","StudentId":"5","Status":"0","Date":"18-01-2018"}]}}
    else if(opName.equals("insertHomworkFeedBackWithJSONArray")){
        out.println(operation.insertHomworkFeedBackWithJSONArray());
    }
    
   //{"OperationName":"getHomeworkDetailsForFeedback","homeworkData":{"HomeworkId":"148"}}
    else if(opName.equals("getHomeworkDetailsForFeedback")){
        out.println(operation.getHomeworkDetailsForFeedback());
    }
    
    
    //{"OperationName":"homeworkFeedbackstaff","homeworkData":{"HomeworkId":"33"}}
    else if(opName.equals("homeworkFeedbackstaff")){
        out.println(operation.homeworkFeedbackstaff());
    }
    //{"OperationName":"homeworkFeedbackDateWise","homeworkData":{"StudentId":"5","Date":"09-11-2017"}}
   //verified workingFine
    //in server verified 
    else if(opName.equals("homeworkFeedbackDateWise")){
        out.println(operation.homeworkFeedbackDateWise());
    }
    //{"OperationName":"homeworkFeedback_StudentWise","homeworkData":{"StudentId":"5"}}
    //verified workingFine
    //in server verified 
    else if(opName.equals("homeworkFeedback_StudentWise")){
        out.println(operation.homeworkFeedback_StudentWise());
    }
    //{"OperationName":"getStaffhomeworkFeedback","homeworkData":{"ClassId":"5","SubjectId":"7"}}
    else if(opName.equals("getStaffhomeworkFeedback")){
        out.println(operation.getStaffhomeworkFeedback());
    }
    //{"OperationName":"getStaffhomeworkFeedbackDateWise","homeworkData":{"ClassId":"5","SubjectId":"7","Date":"09-11-2017"}}
    else if(opName.equals("getStaffhomeworkFeedbackDateWise")){
        out.println(operation.getStaffhomeworkFeedbackDateWise());
    }
    } catch(Exception e)
    {
         out.println(e.toString());
    }
    %>
