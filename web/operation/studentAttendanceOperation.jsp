<%-- 
    Document   : studentAttendanceOperation
    Created on : 20 Jul, 2017, 3:49:20 PM
    Author     : basava
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="com.example.operations.StudentAttendanceOperations"%>
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
    JSONObject studAttendanceData= object.getJSONObject("studAttendanceData");
    // {"OperationName":"AttendenceDisplay_Studentwise","studAttendanceData":{"SchoolId":"1", "AcademicYearId":"1", "SetBy":"1", "ClassId":"1", "SyllabusSetDate":"2017-06-12 10:00:00", "ExamId":"1","SubjectId":"1","StudentId":"1","MarksObtained":"50","Grade":"A","Date":"2017-06-12 10:00:00","Syllabus":"bvsdhj"}}
    StudentAttendanceOperations operation= new StudentAttendanceOperations(studAttendanceData);
    if(opName.equals("AttendenceDisplay_Admin_Studentwise")){
        out.println(operation.AttendenceDisplay_Admin_Studentwise());
       }// {"OperationName":"AttendenceDisplay_Studentwise","studAttendanceData":{"SchoolId":"", "AcademicYearId":"", "SetBy":"", "ClassId":"", "SyllabusSetDate":"", "ExamId":"","SubjectId":"","StudentId":"1","MarksObtained":"","Grade":"","Date":"","Syllabus":""}}
    else if(opName.equals("AttendenceDisplay_Classwise_In")){
        out.println(operation.AttendenceDisplay_Classwise_In());
    }
   // {"OperationName":"studentAttendanceDefault","studAttendanceData":{"StudentId":"5"}}
    //verified
      else if(opName.equals("studentAttendanceDefault")){
            out.println(operation.studentAttendanceDefault());
    }
      else if(opName.equals("getAttendanceMonths")){
        out.println(operation.getAttendanceMonths());
    }
       else if(opName.equals("StudentAttendence_Insert")){
        out.println(operation.StudentAttendence_Insert());
    }
      //{"OperationName":"studentAttendanceByMonth","studAttendanceData":{"StudentId":"5","Date":"17-05-2017"}}
      //verified  
      
      else if(opName.equals("studentAttendanceByMonth")){
        out.println(operation.studentAttendanceByMonth());
    }
      
      
      
      
      //{"OperationName":"checkStudentAttendance","studAttendanceData":{"ClassId":"5"}}
      //verified  
      
      else if(opName.equals("checkStudentAttendance")){
        out.println(operation.checkStudentAttendance());
    }
      
      //{"OperationName":"insertStudentLeaveRequest","studAttendanceData":{"SchoolId":"3","AcademicYearId":"2","StudentId":"5","EnteredBy":"2","FromDate":"30-04-2019","ToDate":"30-04-2019","ClassId":"5","ReasonTitleId":"1","OtherReasonTitle":" long absent","Description":"my son is not well"}}
      //verified  
      
      else if(opName.equals("insertStudentLeaveRequest")){
        out.println(operation.insertStudentLeaveRequest());
    }
     
      //{"OperationName":"editStudentLeaveRequest","studAttendanceData":{"SchoolId":"3","AcademicYearId":"2","StudentId":"5","EnteredBy":"2","FromDate":"30-04-2019","ToDate":"30-04-2019","ClassId":"5","ReasonTitleId":"1","OtherReasonTitle":" long absent","Description":"my son is not well"}}
      //verified  
      
      else if(opName.equals("editStudentLeaveRequest")){
        out.println(operation.editStudentLeaveRequest());
    }
      
     //{"OperationName":"deleteStudentLeave","studAttendanceData":{"SchoolId":"3","AcademicYearId":"2","StudentId":"5","EnteredBy":"2","FromDate":"30-04-2019","ToDate":"30-04-2019","ClassId":"5","ReasonTitleId":"1","OtherReasonTitle":" long absent","Description":"my son is not well"}}
      //verified  
      
      else if(opName.equals("deleteStudentLeave")){
        out.println(operation.deleteStudentLeave());
    } 
      
      //{"OperationName":  "getClasswiseStudentLeavelist", "studAttendanceData":{"ClassId":"6","Date":"26-02-2019"}}
     else if(opName.equals("getClasswiseStudentLeaveList")){
        out.println(operation.getClasswiseStudentLeavelist());
    }
      
     //{"OperationName":"getStudentwiseStudentRequestList", "studAttendanceData":{"StudentId":"4746"}}
     else if(opName.equals("getStudentwiseStudentRequestList")){
        out.println(operation.getStudentwiseStudentRequestList());
    } 
     
     
      
      //{"OperationName":"checkStudentAttendancePeriodWise","studAttendanceData":{"ClassId":"5"}}
      //verified  
      
      else if(opName.equals("checkStudentAttendancePeriodWise")){
        out.println(operation.checkStudentAttendancePeriodWise());
    }
      
    //{"OperationName":"insertStudentAttendance", "studAttendanceData":{"AttendanceArray":[{"SchoolId":"3","AcademicYearId":"2","StudentId":"5","EnteredBy":"2","Status":"0","Date":"02-05-2018","ClassId":"5","ReasonId":"1","OtherReasonTitle":" long absent"},{"SchoolId":"3","AcademicYearId":"2","StudentId":"6","EnteredBy":"2","Status":"1","Date":"02-05-2018","ClassId":"5","ReasonId":"0","OtherReasonTitle":""}]}}
    else if(opName.equals("insertStudentAttendance")){
        out.println(operation.insertStudentAttendance());
    }
    
    //{"OperationName":"insertStudentAttendancrequeePeriodWise", "studAttendanceData":{"AttendanceArray":[{"SchoolId":"3","AcademicYearId":"2","StudentId":"5","EnteredBy":"2","Status":"0","Date":"02-05-2018","ClassId":"5","ReasonId":"1","OtherReasonTitle":" long absent"},{"SchoolId":"3","AcademicYearId":"2","StudentId":"6","EnteredBy":"2","Status":"1","Date":"02-05-2018","ClassId":"5","ReasonId":"0","OtherReasonTitle":""}]}}
    else if(opName.equals("insertStudentAttendancePeriodWise")){
        out.println(operation.insertStudentAttendancePeriodWise());
    }
    
    //{"OperationName":"displayReason", "studAttendanceData":{"SchoolId":"3"}}
    else if(opName.equals("displayReason")){
        out.println(operation.displayReason());
    }
    //{"OperationName":"getClasswiseAttendance", "studAttendanceData":{"ClassId":"5"}}
    
    else if(opName.equals("getClasswiseAttendance")){
        out.println(operation.getClasswiseAttendance());
    }
    //{"OperationName":"getClasswiseAttendanceByDate", "studAttendanceData":{"ClassId":"5","Date":"14-02-2018"}}
    
    else if(opName.equals("getClasswiseAttendanceByDate")){
        out.println(operation.getClasswiseAttendanceByDate());
    }
    
    //{"OperationName":"getStudentwiseLeavelist", "studAttendanceData":{"StudentId":"9460"}}
    
    else if(opName.equals("getStudentwiseLeaveList")){
            out.println(operation.getStudentwiseLeaveList());
    }
    //{"OperationName":"getClasswiseStudentLeavelist", "studAttendanceData":{"ClassId":"127"}}
    
    else if(opName.equals("getClasswiseStudentLeavelist")){
        out.println(operation.getClasswiseAttendanceByDate());
    }
    
    //{"OperationName":"getperiodwiseClassAttendance", "studAttendanceData":{"ClassId":"5","Date":"14-02-2018"}}
    
    else if(opName.equals("getperiodwiseClassAttendance")){
        out.println(operation.getperiodwiseClassAttendance());
    }
    
     //{"OperationName":"getperiodwiseStudentAttendance", "studAttendanceData":{"StudentId":"5","Date":"14-02-2018"}}
    
    else if(opName.equals("getperiodwiseStudentAttendance")){
        out.println(operation.getperiodwiseStudentAttendance());
    }
    
     //{"OperationName":"getperiodwiseStudentAttendanceByDate", "studAttendanceData":{"StudentId":"5","Date":"14-02-2018"}}
    
    else if(opName.equals("getperiodwiseStudentAttendanceByMonth")){
        out.println(operation.getperiodwiseStudentAttendanceByMonth());
    }
    
    
    
    } catch (Exception e) {
            out.println(e.toString());
        }
%>

