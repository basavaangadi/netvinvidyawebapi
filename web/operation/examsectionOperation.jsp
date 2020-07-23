<%-- 
    Document   : examsectionOperation
    Created on : 15 Jul, 2017, 11:01:07 PM
    Author     : basava
--%>


<%@page import="com.example.operations.ClassSubjectOperation"%>
<%@page import="com.example.operations.ExamOperation" %>
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
    JSONObject examData= object.getJSONObject("examData");
    // {"OperationName":"ExamSyllabusSet","ExamData":{"SchoolId":"1", "AcademicYearId":"1", "SetBy":"1", "ClassId":"1", "SyllabusSetDate":"2017-06-12 10:00:00", "ExamId":"1","SubjectId":"1","StudentId":"1","MarksObtained":"50","Grade":"A","Date":"2017-06-12 10:00:00","Syllabus":"bvsdhj"}}
    ExamOperation operation= new ExamOperation(examData);
    if(opName.equals("ExamSyllabusSet")){
        out.println(operation.ExamSyllabusSet());
       }
        //{"OperationName":"examScheduleDisplay_Studentwise","examData":{"StudentId":"5"}}
        //verifed working correct
    //in server verified 
    else if(opName.equals("examScheduleDisplay_Studentwise")){
        
        out.println(operation.examScheduleDisplay_Studentwise());
    }
    //{"OperationName":"examScheduleDisplayByExamid","examData":{"StudentId":"5","ExamId":"1"}}
    //verified working correct
    //in server verified 
    else if(opName.equals("examScheduleDisplayByExamid"))
    {
        out.println(operation.examScheduleDisplayByExamid());
    }
    
    //examScheduleDisplayByExamid_jv
    
   // {"OperationName":"examScheduleDisplayByExamid_jv","examData":{"StudentId":"5","ExamId":"1"}}
    //verified working correct
    //in server verified 
    else if(opName.equals("examScheduleDisplayByExamid_jv"))
    {
        out.println(operation.examScheduleDisplayByExamid_jv());
    }
    // {"OperationName":"getGradesBySchoolId","examData":{"SchoolId":"4","AcademicYearId":"4"}}
    //verified working correct
    //in server verified 
    else if(opName.equals("getGradesBySchoolId"))
    {
        out.println(operation.getGradesBySchoolId());
    }
    
    
    //{"OperationName":"checkIsExamScheduleEntered","examData":{"StudentId":"5","ExamId":"1"}}
    //verified working correct
    //in server verified 
    else if(opName.equals("checkIsExamScheduleEntered"))
    {
        out.println(operation.checkIsExamScheduleEntered());
    }
    
    //{"OperationName":"checkIsExamSyllabusEntered","examData":{"StudentId":"5","ExamId":"1"}}
    //verified working correct
    //in server verified 
    else if(opName.equals("checkIsExamSyllabusEntered"))
    {
        out.println(operation.checkIsExamSyllabusEntered());
    }
     //{"OperationName":"checkIsExamSyllabusEntered","examData":{"StudentId":"5","ExamId":"1"}}
    //verified working correct
    //in server verified 
    
    
    else if(opName.equals("checkIsExamMarksEntered"))
    {
        out.println(operation.checkIsExamMarksEntered());
    }
    //{"OperationName":"getLeftOutStudentsForMarksEntry","examData":{"ClassId":"5","ExamResultId":"1"}}
    else if(opName.equals("getLeftOutStudentsForMarksEntry"))
    {
        out.println(operation.getLeftOutStudentsForMarksEntry());
    }
    
    //{"OperationName":"getStaffexamSchdule","examData":{"StaffId":"74","ExamId":"15","AcademicYearId":"3"}}
    //verified 
    else if(opName.equals("getStaffexamSchdule"))
    {
        out.println(operation.getStaffexamSchdule());
    }
    //{"OperationName":"getStaffexamSchduleClasswise","examData":{"ClassId":"5","ExamId":"1"}}
    //verified working correct
    
    else if(opName.equals("getStaffexamSchduleClasswise"))
    {
        out.println(operation.getStaffexamSchduleClasswise());
    }
    //{"OperationName":"getStaffexamSchduleDetailsById","examData":{"ExamSceduleId":"2"}}
    
     else if(opName.equals("getStaffexamSchduleDetailsById"))
    {
        out.println(operation.getStaffexamSchduleDetailsById());
    }
 // {"OperationName":"setStaffexamSchdule","examData":{"SubjectId":"8","SetBy":"2","SetDate":"21-01-2018 10:30","ExamOnDate":"22-02-2018 12:30","ClassId":"5","ExamId":"1","ExamStartTime":"9:30 AM","ExamEndTime":"12:30 PM"}}
     else if(opName.equals("setStaffexamSchdule"))
    {
        out.println(operation.setStaffexamSchdule());
    }
//{"OperationName":"setStaffexamSchdule","examData":{"SubjectId":"8","SetBy":"2","SetDate":"21-01-2018 10:30","ExamOnDate":"22-02-2018 12:30","ClassId":"5","ExamId":"1","ExamStartTime":"9:30 AM","ExamEndTime":"12:30 PM"}}
     else if(opName.equals("editExamSchdule"))
    {
        out.println(operation.editExamSchdule());
    }
      else if(opName.equals("deleteExamSchedule"))
    {
        out.println(operation.deleteExamSchedule());
    }
     
     
     
     
     
    // {"OperationName":"insertStaffexamSchdule","examData":{"SubjectId":"8","SetBy":"2","SetDate":"21-01-2018 10:30","ExamOnDate":"22-02-2018 12:30","ClassId":"5","ExamId":"1"}}
//     else if(opName.equals("insertStaffexamSchdule"))
//    {
//        out.println(operation.insertStaffexamSchdule());
//    } 
     
     
   //  {"OperationName":"examResultDisplay_Studentwise","examData":{"StudentId":"5"}}
    // verified workinhg fine
    //in server verified 
    
    else if(opName.equals("examResultDisplay_Studentwise"))
    {
        out.println(operation.examResultDisplay_Studentwise());
    }
    //{"OperationName":"examResultDisplayExamwise","examData":{"StudentId":"3","ExamId":"FA1"}}
    //verified workinhg fine
    //in server verified
    else if(opName.equals("examResultDisplayExamwise"))
    {
        out.println(operation.examResultDisplayExamwise());
    }
    //{"OperationName":"examResultDisplay_Studentwise_BKBI","examData":{"StudentId":"9781"}}
    else if(opName.equals("examResultDisplay_Studentwise_BKBI"))
    {
        out.println(operation.examResultDisplay_Studentwise_BKBI());
    }
    //{"OperationName":"examResultDisplay_Studentwise_GDGB","examData":{"StudentId":"9781"}}
    else if(opName.equals("examResultDisplay_Studentwise_GDGB"))
    {
        out.println(operation.examResultDisplay_Studentwise_GDGB());
    }
    //{"OperationName":"examResultDisplay_Studentwise_GDGB","examData":{"StudentId":"9781"}}
    else if(opName.equals("examResultDisplay_Studentwise_HBBN"))
    {
        out.println(operation.examResultDisplay_Studentwise_HBBN());
    }
    
    
    //{"OperationName":"examResultDisplayExamwise_GDGB","examData":{"StudentId":"5665","ExamId":"2"}}
    //verified workinhg fine
    //14969
    //in server verified
    else if(opName.equals("examResultDisplayExamwise_GDGB"))
    {
        out.println(operation.examResultDisplayExamwise_GDGB());
    }
    //{"OperationName":"examResultDisplayExamwise_GDGB","examData":{"StudentId":"5665","ExamId":"2"}}
    //verified workinhg fine
    //in server verified
    else if(opName.equals("examResultDisplayExamwise_HBBN"))
    {
        out.println(operation.examResultDisplayExamwise_HBBN());
    }
    
    //{"OperationName":"examResultDisplayExamwise_BKBI","examData":{"StudentId":"5665","ExamId":"2"}}
    //verified workinhg fine
    //in server verified
    else if(opName.equals("examResultDisplayExamwise_BKBI"))
    {
        out.println(operation.examResultDisplayExamwise_BKBI());
    }
    
    
    
    //getExamMarksByExam_Jv
    
    //{"OperationName":"getExamMarksByExam_Jv","examData":{"StudentId":"5665","ExamId":"2"}}
    //verified workinhg fine
    //in server verified
    else if(opName.equals("getExamMarksByExam_Jv"))
    {
        out.println(operation.getExamMarksByExam_Jv());
    }
    
    
    
    
    //{"OperationName":"examResultDisplayTermExam_GDGB","examData":{"StudentId":"5665","ExamId":"31"}}
    //verified workinhg fine
    //in server verified
    else if(opName.equals("examResultDisplayTermExam_GDGB"))
    {
        out.println(operation.examResultDisplayTermExam_GDGB());
    }
    /*{"OperationName":"insertExamResult","examData":{"MarksArray":[{"ExamResultId":"346","AddedBy":"5","StudentId":"3","ObtainedMarks":"5","SetDate":"30-01-2019 10:30:31"},
    {"ExamResultId":"346","AddedBy":"5","StudentId":"11","ObtainedMarks":"5","SetDate":"30-01-2019 10:30:31"},
    {"ExamResultId":"346","AddedBy":"5","StudentId":"13","ObtainedMarks":"5","SetDate":"30-01-2019 10:30:31"}
    ]}} */ 
    else if(opName.equals("insertExamResult"))
    {
        out.println(operation.insertExamResult());
    }
    // //{"OperationName":"insertExamResultMaxMin","examData":{"ExamScheduleId":"653","SetBy":"5","MaxMarks":"10","MinMarks":"2","SetDate":"30-01-2019 10:30:31",}} 
    else if(opName.equals("insertExamResultMaxMin"))
    {
        out.println(operation.insertExamResultMaxMin());
    }
    
    
    //{"OperationName":"examResultDisplayExamwiseWithoutColumn","examData":{"ClassId":"5","ExamId":"5"}}
    else if(opName.equals("examResultDisplayExamwiseWithoutColumn"))
    {
        out.println(operation.examResultDisplayExamwiseWithoutColumn());
    }
    
    //{"OperationName":"getStaffResultDisplay","examData":{"ClassId":"5"}}
    //verified 
    else if(opName.equals("getStaffResultDisplay"))
    {
        out.println(operation.getStaffResultDisplay());
    }
    //{"OperationName":"getStaffResultDisplayExamwise","examData":{"ClassId":"5","ExamId":"5"}}
    //verified working correct
    
    else if(opName.equals("getStaffResultDisplayExamwise"))
    {
        out.println(operation.getStaffResultDisplayExamwise());
    }
    
    
    //{"OperationName":"getStaffExamwiseSubjectList","examData":{"ClassId":"5","ExamId":"5"}}
    //verified working correct
    
    else if(opName.equals("getStaffExamwiseSubjectList"))
    {
        out.println(operation.getStaffExamwiseSubjectList());
    }
    //{"OperationName":"getStaffExamResultTabularGDGB","examData":{"ClassId":"29","ExamId":"5"}}
    else if(opName.equals("getStaffExamResultTabularGDGB"))
    {
        out.println(operation.getStaffExamResultTabularGDGB());
    }
      //{"OperationName":"getStaffExamResultTabularMNJT","examData":{"ClassId":"29","ExamId":"5"}}
    else if(opName.equals("getStaffExamResultTabularMNJT"))
    {
        out.println(operation.getStaffExamResultTabularMNJT());
    }
    
    
    
    else if(opName.equals("getStaffExamResultTabularHBBN"))
    {
        out.println(operation.getStaffExamResultTabularHBBN());
    }
    
    //{"OperationName":"getStaffExamResultTabularGDGB","examData":{"ClassId":"29","ExamId":"5"}}
    else if(opName.equals("getStaffMidTermResultGDGB"))
    {
        out.println(operation.getStaffExamResultTabularGDGB());
    }
    //{"OperationName":"getStaffExamResultTabularGDGB","examData":{"ClassId":"29","ExamId":"5"}}
    else if(opName.equals("getStaffAnunualResultGDGB"))
    {
        out.println(operation.getStaffExamResultTabularGDGB());
    }
    
    //{"OperationName":"getStaffExamResultGDGB","examData":{"ClassId":"29","ExamId":"5"}}
     else if(opName.equals("getStaffExamResultGDGB"))
    {
        out.println(operation.getStaffExamResultGDGB());
    }
     
     // {"OperationName":"checkIsExamDetailsEntered","examData":{"ExamId":"4","SchoolId":"2","AcdemicYearId":"3","ClassId":"1"}}
        else if(opName.equals("checkIsExamDetailsEntered"))
    {
        out.println(operation.checkIsExamDetailsEntered());
    }
     
     
     
    //{"OperationName":"getStaffExamResultRVK","examData":{"ClassId":"29","ExamId":"5"}}
     else if(opName.equals("getStaffExamResultRVK"))
    {
        out.println(operation.getStaffExamResultRVK());
    }
    
    //{"OperationName":"getStaffExamResultGDGB","examData":{"ClassId":"29","ExamId":"5"}}
     else if(opName.equals("getStaffExamResultGDGBDemo"))
    {
        out.println(operation.getStaffExamResultGDGBDemo());
    }
    
    //{"OperationName":"getSubjectwiseResult","examData":{"ClassId":"29","ExamId":"5","SubjectId":"2","ExamSchduleSetDate":"2018-10-01 10:00:00"}}2017-10-03 10:00:00.0 2017-10-03 10:10
    else if (opName.equals("getSubjectwiseResultRVK")){
          out.println(operation.getSubjectwiseResultRVK());
     }
   // //{"OperationName":"getSubjectwiseResultGDGB","examData":{"ClassId":"89","ExamId":"2","SubjectId":"2","ExamSchduleSetDate":"29-07-2019"}}
    else if (opName.equals("getSubjectwiseResultGDGB")){
          out.println(operation.getSubjectwiseResultGDGB());
     }
    
  //  {"OperationName":"examSyllabus","examData":{"StudentId":"5"}}
    //verified workinhg fine
    //in server veified fine 
    else if(opName.equals("examSyllabus"))
    {
        out.println(operation.examSyllabus());
    }
  //  {"OperationName":"examSyllabusDisplayByExam","examData":{"StudentId":"5","ExamId":"1"}}
    //verified workinhg fine
    //in server verified fine 
    else if(opName.equals("examSyllabusDisplayByExam"))
    {
        out.println(operation.examSyllabusDisplayByExam());
    }
    
    //examSyllabusDisplayByExam_jv
    //  {"OperationName":"examSyllabusDisplayByExam_jv","examData":{"StudentId":"5","ExamId":"1"}}
    //verified workinhg fine
    //in server verified fine 
    else if(opName.equals("examSyllabusDisplayByExam_jv"))
    {
        out.println(operation.examSyllabusDisplayByExam_jv());
    }
    
    
    //{"OperationName":"getStaffExamSyllabus","examData":{"StaffId":"2","ExamId":"1"}}
    //verified 
    else if(opName.equals("getStaffExamSyllabus"))
    {
        out.println(operation.getStaffExamSyllabus());
    }
    //{"OperationName":"getStaffExamSyllabusClasswise","examData":{"ClassId":"5","ExamId":"1"}}
    //verified working correct
    
    else if(opName.equals("getStaffExamSyllabusClasswise"))
    {
        out.println(operation.getStaffExamSyllabusClasswise());
    }
    //{"OperationName":"getSubjectByExamId","examData":{"ClassId":"5","ExamId":"1"}}
     else if(opName.equals("getSubjectByExamId"))
    {
        out.println(operation.getSubjectByExamId());
    }
        //{"OperationName":"setStaffExamSyllabus","examData":{"SetBy":"2","SetDate":"21-01-2018 10:30","ExamSceduleId":"22","Syllabus":"ahskasddsa savaasjd saKHgskajs"}}
     else if(opName.equals("setStaffExamSyllabus"))
    {
        out.println(operation.setStaffExamSyllabus());
    }
    //{"OperationName":"editExamSyllabus","examData":{"SetBy":"2","SetDate":"21-01-2018 10:30","ExamSceduleId":"22","Syllabus":"ahskasddsa savaasjd saKHgskajs"}}
     else if(opName.equals("editExamSyllabus"))
    {
        out.println(operation.editExamSyllabus());
    }
    //{"OperationName":"deleteExamSyllabus","examData":{"SetBy":"2","SetDate":"21-01-2018 10:30","ExamSceduleId":"22","Syllabus":"ahskasddsa savaasjd saKHgskajs"}}
     else if(opName.equals("deleteExamSyllabus"))
    {
        out.println(operation.deleteExamSyllabus());
    }
    //{"OperationName":"getExamName",examData:{"StudentId":"5"}}
    //verified workinhg fine
    //in server verified fine 
    else if (opName.equals("getExamName")){
        out.println(operation.getExamName());
    }
    //{"OperationName":"getStaffExamName","examData":{"SchoolId":"3"}}
    else if (opName.equals("getStaffExamName")){
        out.println(operation.getStaffExamName());
    }
    //{"OperationName":"getExamNameForSchedule","examData":{"SchoolId":"3"}}
    else if (opName.equals("getExamNameForSchedule")){
        out.println(operation.getExamNameForSchedule());
    }
    
    //{"OperationName":"getEnteredExamNameForScheduleOfClass","examData":{"ClassId":"3"}}
    else if (opName.equals("getEnteredExamNameForScheduleOfClass")){
        out.println(operation.getEnteredExamNameForScheduleOfClass());
    }
    
    //{"OperationName":"getEnteredExamNameForMarksOfClass","examData":{"ClassId":"3"}}
    else if (opName.equals("getEnteredExamNameForMarksOfClass")){
        out.println(operation.getEnteredExamNameForMarksOfClass());
    }
    
    
    
    
    //{"OperationName":"getEnteredExamNameForSyllabusOfClass","examData":{"ClassId":"3"}}
    else if (opName.equals("getEnteredExamNameForSyllabusOfClass")){
        out.println(operation.getEnteredExamNameForSyllabusOfClass());
    }
    
    //{"OperationName":"getEnteredExamNameForMarksOfSubject","examData":{"ClassId":"3"}}
    else if (opName.equals("getEnteredExamNameForMarksOfSubject")){
        out.println(operation.getEnteredExamNameForMarksOfSubject());
    }
    
    
    
    
    
    
    //{"OperationName":"getEnteredExamNameForSyllabusOfStaff","examData":{"StaffId":"3","AcademicYearId":"4"}}
    else if (opName.equals("getEnteredExamNameForSyllabusOfStaff")){
        out.println(operation.getEnteredExamNameForSyllabusOfStaff());
    }
    //{"OperationName":"getEnteredExamNameForScheduleOfStaff","examData":{"StaffId":"3","AcademicYearId":"4"}}
    else if (opName.equals("getEnteredExamNameForScheduleOfStaff")){
        out.println(operation.getEnteredExamNameForScheduleOfStaff());
    }
    //{"OperationName":"getEnteredExamNameForMarksOfStaff","examData":{"StaffId":"3","AcademicYearId":"4"}}
    else if (opName.equals("getEnteredExamNameForMarksOfStaff")){
        out.println(operation.getEnteredExamNameForMarksOfStaff());
    }
    
    
     // //{"OperationName":"getSyllabusExamName","examData":{"SchoolId":"3"}}   
     else if (opName.equals("getSyllabusExamName")){
        out.println(operation.getSyllabusExamName());
    }
    // //{"OperationName":"getScheduleExamName","examData":{"ClassId":"5"}}
     else if (opName.equals("getScheduleExamName")){
        out.println(operation.getScheduleExamName());
    }
     
     //{"OperationName":"getresultExamName","examData":{"ClassId":"5"}}
     else if (opName.equals("getResultExamName")){
        out.println(operation.getResultExamName());
    }
     
     //{"OperationName":"getStudentResutltExamName","examData":{"StudentId":"5114"}}
     else if (opName.equals("getStudentResultExamName")){
        out.println(operation.getStudentResultExamName());
    }
     
     
     //{"OperationName":"getresultExamSchedule","examData":{"ClassId":"5","ExamId":"5","SubjectId":"8"}}
     else if (opName.equals("getresultExamSchedule")){
        out.println(operation.getresultExamSchedule());
    }
    
    } catch (Exception e) {
            out.println(e.toString());
        }
%>
