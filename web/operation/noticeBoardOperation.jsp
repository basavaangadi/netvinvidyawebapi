<%-- 
    Document   : noticeBoardOperation
    Created on : 21 Jul, 2017, 7:26:26 PM
    Author     : basava
--%>

<%--<%@page contentType="text/html" pageEncoding="UTF-8"%>--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="org.json.JSONObject"%>
<%@page import="java.io.BufferedReader"%>
<%@page import="com.example.operations.NoticeBoardOperation"%>
<%! 
    String Data;
%>
<%
    try{
        StringBuilder builder = new StringBuilder();
    BufferedReader reader = request.getReader();
    String line;
    while ((line = reader.readLine()) != null) {
        builder.append(line);
    }
    Data = builder.toString();
    JSONObject object = new JSONObject(Data); 
    String opName=object.getString("OperationName");
    JSONObject noticeBoardData= object.getJSONObject("noticeBoardData");
    // {"OperationName":"ExamSyllabusSet","ExamData":{"SchoolId":"1", "AcademicYearId":"1", "SetBy":"1", "ClassId":"1", "SyllabusSetDate":"2017-06-12 10:00:00", "ExamId":"1","SubjectId":"1","StudentId":"1","MarksObtained":"50","Grade":"A","Date":"2017-06-12 10:00:00","Syllabus":"bvsdhj"}}
    NoticeBoardOperation operation= new NoticeBoardOperation(noticeBoardData);
   
//{"OperationName":"noticeDisplay","noticeBoardData":{"StudentId":"5","AcademicYearId"  :"2"}}
   // verified working correct 
   //in server verified 
    if(opName.equals("noticeDisplay")){
        out.println(operation.noticeDisplay());
       }
    // {"OperationName":"noticeDisplay_ByDate","noticeBoardData":{"StudentId":"5","fromDate":"12-08-2017","toDate":"24-11-2017"}}
  //verified working correct
    //in server verified 
    else if(opName.equals("noticeDisplay_ByDate"))
    {
        //out.println(operation.noticeDisplay_ByDate());
        out.println(operation.noticeDisplay_ByDate());
    }
    //{"OperationName":"getStaffNotice","noticeBoardData":{"StaffId":"2","AcademicYearId":"2"}}
    else if(opName.equals("getStaffNotice")){
        out.println(operation.getStaffNotice());
    }
    
    //{"OperationName":"getStaffNoticeByDate","noticeBoardData":{"StaffId":"2","fromDate":"12-05-2017","toDate":"24-11-2017"}}
    else if(opName.equals("getStaffNoticeByDate")){
         out.println(operation.getStaffNoticeByDate());
    }
    
    //{"OperationName":"getStaffStudentNotice","noticeBoardData":{"SchoolId":"3","AcademicYearId":"2"}}
    else if(opName.equals("getStaffStudentNotice")){
        out.println(operation.getStaffStudentNotice());
    }
    
    //{"OperationName":"getStaffStudentNoticeByDate","noticeBoardData":{"SchoolId":"3","fromDate":"2017-05-12","toDate":"2017-11-24"}}
    else if(opName.equals("getStaffStudentNoticeByDate")){
         out.println(operation.getStaffStudentNoticeByDate());
    }
    
 //   {"OperationName":"insertNotice","noticeBoardData":{"SchoolId":"3","AcademicYearId":"2","SetBy":"2","NoticeSetDate":"26-02-2018 12:44:02","ToWhom":"All","NoticeTitle":"1","OtherTitle":"bsahd hasjhs","Notice":"vdjh dhgdh hgdd dwgjwd wdgjd dd d","NoticeDate":"28-02-2018","NoticeTime":"11:00 AM"}}
     else if(opName.equals("insertNotice")){
         out.println(operation.insertNotice());
    }
     //   {"OperationName":"insertNotice","noticeBoardData":{"SchoolId":"3","AcademicYearId":"2","SetBy":"2","NoticeSetDate":"26-02-2018 12:44:02","ToWhom":"All","NoticeTitle":"1","OtherTitle":"bsahd hasjhs","Notice":"vdjh dhgdh hgdd dwgjwd wdgjd dd d","NoticeDate":"28-02-2018","NoticeTime":"11:00 AM"}}
     else if(opName.equals("sendParentMessage")){
         out.println(operation.sendParentMessage());
    }
     //{"OperationName":"insertNotice","noticeBoardData":{"SchoolId":"3","AcademicYearId":"2","SetBy":"2","NoticeSetDate":"26-02-2018 12:44:02","ToWhom":"All","NoticeTitle":"1","OtherTitle":"bsahd hasjhs","Notice":"vdjh dhgdh hgdd dwgjwd wdgjd dd d","NoticeDate":"28-02-2018","NoticeTime":"11:00 AM"}}
     else if(opName.equals("sendStaffReply")){
         out.println(operation.sendStaffReply());
    }
     
    // {"OperationName":"insertClassNotice","noticeBoardData":{"SchoolId":"4","AcademicYearId":"3","SetBy":"4","ClassId":"6","NoticeTitle":"1","OtherTitle":"bsahd hasjhs","Notice":"vdjh dhgdh hgdd dwgjwd wdgjd dd d","NoticeDate":"28-03-2018","NoticeTime":"11:00 AM"}}
     else if(opName.equals("insertClassNotice")){
         out.println(operation.insertClassNotice());
    }
     
      // {"OperationName":"getClassNoticeForStaff","noticeBoardData":{"ClassId":"6","fromDate":"01-01-0001","toDate":"01-01-0001"}}
     else if(opName.equals("getClassNoticeForStaff")){
         out.println(operation.getClassNoticeForStaff());
    }
     // {"OperationName":"getClassNoticeForStudent","noticeBoardData":{"StudentId":"11","fromDate":"01-01-0001","toDate":"01-01-0001"}}
     else if(opName.equals("getClassNoticeForStudent")){
         out.println(operation.getClassNoticeForStudent());
    }
     // {"OperationName":"editClassNotice","noticeBoardData":{"NoticeId":"1","SchoolId":"4","AcademicYearId":"3","SetBy":"4","ClassId":"6","NoticeTitle":"1","OtherTitle":"bsahd hasjhs","Notice":"vdjh dhgdh hgdd dwgjwd wdgjd dd d","NoticeDate":"28-03-2018","NoticeTime":"11:00 AM"}}
     else if(opName.equals("editClassNotice")){
         out.println(operation.editClassNotice());
    }
     
     
     
 //   {"OperationName":"getNoticeDetailsById","noticeBoardData":{"Id":"1"}}
     else if(opName.equals("getNoticeDetailsById")){
         out.println(operation.getNoticeDetailsById());
    }
     //   {"OperationName":"deleteNoticeById","noticeBoardData":{"Id":"1"}}
     else if(opName.equals("deleteNoticeById")){
         out.println(operation.deleteNoticeById());
    }
     //   {"OperationName":"editNoticeById","noticeBoardData":{"Id":"1"}}
     else if(opName.equals("editNoticeById")){
   
         out.println(operation.editNoticeById());
    
     }
     
    //{"OperationName":"getTeacherNote","noticeBoardData":{"StaffId":"2","AcademicYearId":"2"}}
    else if(opName.equals("getTeacherNote")){
         out.println(operation.getTeacherNote());
    }
    //
    else if(opName.equals("getSchoolwiseParentsNote")){
        out.println(operation.getSchoolwiseParentMessage());
    }
    
    
    
     //{"OperationName":"getTeacherNoteByDate","noticeBoardData":{"StaffId":"2","fromDate":"12-05-2017","toDate":"24-11-2017"}}
    else if(opName.equals("getTeacherNoteByDate")){
         out.println(operation.getTeacherNoteByDate());
    }
    //{"OperationName":"getStaffListBySchoolId","noticeBoardData":{"SchoolId":"3"}}
    else if(opName.equals("getStaffListBySchoolId")){
         out.println(operation.getStaffListBySchoolId());
    }
    
        //{"OperationName":"getTeachersNoteIdBySchoolId","noticeBoardData":{"SchoolId":"3"}}
    else if(opName.equals("getTeachersNoteIdBySchoolId")){
         out.println(operation.getTeachersNoteIdBySchoolId());
    }
    
    //{"OperationName":"getNoticeIdBySchoolId","noticeBoardData":{"SchoolId":"3"}}
    else if(opName.equals("getNoticeIdBySchoolId")){
         out.println(operation.getNoticeIdBySchoolId());
    }
    //{"OperationName":"getClassNoticeIdBySchoolId","noticeBoardData":{"SchoolId":"3"}}
    else if(opName.equals("getClassNoticeIdBySchoolId")){
         out.println(operation.getClassNoticeIdBySchoolId());
    }
    //{"OperationName":"getStaffReminderIdBySchoolId","noticeBoardData":{"SchoolId":"3"}}
    else if(opName.equals("getStaffReminderIdBySchoolId")){
         out.println(operation.getStaffReminderIdBySchoolId());
    }
     //{"OperationName":"getStudentReminderIdBySchoolId","noticeBoardData":{"SchoolId":"3"}}
    else if(opName.equals("getStudentReminderIdBySchoolId")){
         out.println(operation.getStudentReminderIdBySchoolId());
    }
    /*
        {"OperationName":"insertTeacherNote", "noticeBoardData":{"TeacherNoteArray":[
     {"SchoolId":"3","AcademicYearId":"2","SentTo":"2","EnteredBy":"2","NoteSentDate":"14-02-2018 12:45:44","NoteTitleId":"1","OtherNoteTitle":" long absent","Note":"je bje jhda","NoteOnDate":"24-02-2018","NoticeTime": "01:23 PM"},
    {"SchoolId":"3","AcademicYearId":"2","SentTo":"6","EnteredBy":"2","NoteSentDate":"14-02-2018 12:45:44","NoteTitleId":"1","OtherNoteTitle":" long absent","Note":"je bje jhda","NoteOnDate":"24-02-2018","NoticeTime": "01:23 PM"},    
    {"SchoolId":"3","AcademicYearId":"2","SentTo":"8","EnteredBy":"2","NoteSentDate":"14-02-2018 12:45:44","NoteTitleId":"1","OtherNoteTitle":" long absent","Note":"je bje jhda","NoteOnDate":"24-02-2018","NoticeTime": "01:23 PM"}]
}}
    */
    else if(opName.equals("insertTeacherNote")){
         out.println(operation.insertTeacherNote());
    }
//   {"OperationName":"getTeacherNoteById","noticeBoardData":{"Id":"1"}}
     else if(opName.equals("getTeacherNoteById")){
         out.println(operation.getTeacherNoteById());
    }
    //   {"OperationName":"getParentNoteById","noticeBoardData":{"Id":"1"}}
     else if(opName.equals("deleteTeacherNoteById")){
         out.println(operation.deleteTeacherNoteById());
    }
    // {"OperationName":"editTeachersNoteById","noticeBoardData":{"NoticeId":"1"}}
     else if(opName.equals("editTeachersNoteById")){
         out.println(operation.editTeachersNoteById());
    }

    
//{"OperationName":"studentReminderDisplay_studentwise","noticeBoardData":{"StudentId":"5","AcademicYearId":"2"}}
    //verified working correct
    //in server verified 
    else if(opName.equals("studentReminderDisplay_studentwise"))
    {
        out.println(operation.studentReminderDisplay_studentwise());
    }
    
    /*
   {"OperationName":"insertStudentReminder", "noticeBoardData":{"StudentReminderArray":[
     {"SchoolId":"3","AcademicYearId":"2","StudentId":"5","EnteredBy":"2","ClassId":"5","ReminderSetDate":"16-03-2018 01:25:07","ReminderTitle":"1","OtherTitle":" long absent","Reminder":"je bje jhda","ReminderDate":"01-04-2018" ,"ReminderTime":"01:25 PM"},
    {"SchoolId":"3","AcademicYearId":"2","StudentId":"6","EnteredBy":"2","ClassId":"5","ReminderSetDate":"16-03-2018 01:25:07","ReminderTitle":"1","OtherTitle":" long absent","Reminder":"je bje jhda","ReminderDate":"01-04-2018" ,"ReminderTime":"01:25 PM"},
    {"SchoolId":"3","AcademicYearId":"2","StudentId":"8","EnteredBy":"2","ClassId":"5","ReminderSetDate":"16-03-2018 01:25:07","ReminderTitle":"1","OtherTitle":" long absent","Reminder":"je bje jhda","ReminderDate":"01-04-2018" ,"ReminderTime":"01:25 PM"}
    ]}}

    */
    
    else if(opName.equals("insertStudentReminder"))
    {
            out.println(operation.insertStudentReminder());
    }
    
    else if(opName.equals("editStudentReminder"))
    {
            out.println(operation.editStudentReminder());
    }
    
    else if(opName.equals("getStudentReminderDetailsById"))
    {
            out.println(operation.getStudentReminderDetailsById());
    
   }
     else if(opName.equals("deleteStudentReminder"))
    {
            out.println(operation.deleteStudentReminder());
    }
     
     else if(opName.equals("deleteStaffReminder"))
    {
            out.println(operation.deleteStaffReminder());
    }
    
        //{"OperationName":"staffReminderDisplayByDate","noticeBoardData":{"StaffId":"2","fromDate":"12-08-2017","toDate":"24-11-2017"}}
    //verified working correct  
    //in  server verified 
    else if(opName.equals("staffReminderDisplayByDate"))
    {
        out.println(operation.staffReminderDisplayByDate());
    }
    
    
        //{"OperationName":"staffReminderDisplay","noticeBoardData":{"StaffId":"2","AcademicYearId":"2"}}
    //verified working correct
    //in server verified 
    else if(opName.equals("staffReminderDisplay"))
    {
        out.println(operation.staffReminderDisplay());
    }
   /*
        {"OperationName":"insertstaffReminder", "noticeBoardData":{"StaffReminderArray":[ 
        {"SchoolId":"3","AcademicYearId":"2","SentTo":"2","EnteredBy":"2","ReminderSetDate":"15-03-2018 01:25:07","ReminderTitle":"1","OtherTitle":" long absent","Reminder":"je bje jhda","ReminderDate":"01-04-2018" ,"ReminderTime":"01:25 PM"}, 
        {"SchoolId":"3","AcademicYearId":"2","SentTo":"6","EnteredBy":"2","ReminderSetDate":"15-03-2018 01:25:07","ReminderTitle":"1","OtherTitle":" long absent","Reminder":"je bje jhda","ReminderDate":"01-04-2018" ,"ReminderTime":"01:25 PM"},
        {"SchoolId":"3","AcademicYearId":"2","SentTo":"8","EnteredBy":"2","ReminderSetDate":"15-03-2018 01:25:07","ReminderTitle":"1","OtherTitle":" long absent","Reminder":"je bje jhda","ReminderDate":"01-04-2018" ,"ReminderTime":"01:25 PM"} 
        ]}}
    
    */ 
    
    else if(opName.equals("insertstaffReminder"))
    {
        out.println(operation.insertstaffReminder());
    }
        
     else if(opName.equals("editStaffReminder"))
    {
        out.println(operation.editStaffReminder());
    }
        //{"OperationName":"studentReminderDisplayByDate","noticeBoardData":{"StudentId":"5","fromDate":"12-08-2017","toDate":"24-11-2017"}}
    //verified working correct  
    //in  server verified 
    else if(opName.equals("studentReminderDisplayByDate"))
    {
        out.println(operation.studentReminderDisplayByDate());
    }
    
    
    // {"OperationName":"noticeDisplay_AllStudent","noticeBoardData":{"StudentId":"1", "NoticeDate":"", "NoticeCreatedDate":"", "ClassId":"", "SyllabusSetDate":"", "ExamId":"","SubjectId":"","StudentId":"1","MarksObtained":"","Grade":"","Date":"","Syllabus":""}}
    else if(opName.equals("noticeDisplay_AllStudent")){
        out.println(operation.noticeDisplay_AllStudent());
    }//{"OperationName":"ExamMarksDisplay_Staffwise","ExamData":{"SchoolId":"", "AcademicYearId":"", "SetBy":"", "ClassId":"", "SyllabusSetDate":"", "ExamId":"1","SubjectId":"","StaffId":"1","MarksObtained":"","Grade":"","Date":"","Syllabus":""}}
    else if(opName.equals("NoticeInsert_AllStaff"))
    {
        out.println(operation.NoticeInsert_AllStaff());
    }
  /*{"OperationName":"insertParentNote", "noticeBoardData":{"ParentNoteArray":[
 {"SchoolId":"3","AcademicYearId":"2","StudentId":"5","EnteredBy":"2","NoteSentDate":"25-03-2018 01:25:07","ClassId":"5","NoteTitleId":"1","OtherNoteTitle":" long absent","Note":"je bje jhda","NoteOnDate":"29-03-2018","NoticeTime": "01:23 PM"},
{"SchoolId":"3","AcademicYearId":"2","StudentId":"6","EnteredBy":"2","NoteSentDate":"25-03-2018 01:25:07","ClassId":"5","NoteTitleId":"1","OtherNoteTitle":" long absent","Note":"je bje jhda","NoteOnDate":"29-03-2018","NoticeTime": "01:23 PM"},
{"SchoolId":"3","AcademicYearId":"2","StudentId":"7","EnteredBy":"2","NoteSentDate":"25-03-2018 01:25:07","ClassId":"5","NoteTitleId":"1","OtherNoteTitle":" long absent","Note":"je bje jhda","NoteOnDate":"29-03-2018","NoticeTime": "01:23 PM"},
{"SchoolId":"3","AcademicYearId":"2","StudentId":"8","EnteredBy":"2","NoteSentDate":"25-03-2018 01:25:07","ClassId":"5","NoteTitleId":"1","OtherNoteTitle":" long absent","Note":"je bje jhda","NoteOnDate":"29-03-2018","NoticeTime": "01:23 PM"}]}}*/
    else if(opName.equals("insertParentNote")){
        out.println(operation.insertParentNote());
    }
     else if(opName.equals("editParentNote")){
        out.println(operation.editParentNote());
    }
     //{"OperationName":"getClasswiseParentMessage","noticeBoardData":{"StudentId":"9460", "ClassId":"127","Date":"28-05-2019"}}
     else if(opName.equals("getClasswiseParentMessage")){
        out.println(operation.getClasswiseParentMessage());
    }
      //{"OperationName":"getSchoolwiseParentMessage","noticeBoardData":{"SchoolId":"4","Date":"28-05-2019"}}
     else if(opName.equals("getSchoolwiseParentMessage")){
        out.println(operation.getSchoolwiseParentMessage());
    }
     
     
    //   {"OperationName":"getParentNoteById","noticeBoardData":{"Id":"1"}}
     else if(opName.equals("getParentNoteById")){
         out.println(operation.getParentNoteById());
    }
    //   {"OperationName":"getParentNoteById","noticeBoardData":{"Id":"1"}}
     else if(opName.equals("deleteParentNoteById")){
         out.println(operation.deleteParentNoteById());
    }
     
      //   {"OperationName":"deleteClassNoteById","noticeBoardData":{"Id":"1"}}
     else if(opName.equals("deleteClassNoteById")){
         out.println(operation.deleteClassNoteById());
    }
    
      // {"OperationName":"parentnoteDisplayByDate","noticeBoardData":{"StudentId":"5","fromDate":"12-08-2017","toDate":"24-11-2017"}}
    //verified working correct  
    //in  server verified 
    
    else if(opName.equals("parentnoteDisplayByDate")){
        out.println(operation.parentnoteDisplayByDate());
    }
   //{"OperationName":"parentnoteDisplay","noticeBoardData":{"StudentId":"5","AcademicYearId":"2"}}
    //verified working correct  
    //in  server verified 
    else if(opName.equals("parentnoteDisplay"))
    {
        out.println(operation.parentnoteDisplay());
    }
    //{"OperationName":"setStaffParentsNote","noticeBoardData":{"SchoolId":"3", "AcademicYearId":"2", "SentBy":"2", "ClassId":"5","StudentId":"5","NoteSentDate":"12-01-2018","NoticeTitle":"1","NoteOtherTitle":"abc notice","Note":"jkashdj","NoteOnDate":"22-01-2018"}}
    else if(opName.equals("setStaffParentsNote")){
    out.println(operation.setStaffParentsNote());
    }
    
    //{"OperationName":"getParentNoteTitle","noticeBoardData":{"SchoolId":"3"}}
    else if(opName.equals("getParentNoteTitle")){
    out.println(operation.getParentNoteTitle());
    }
    
    
    }catch(Exception e)
    {
        e.printStackTrace();
    }
    
   
    
    



    %>