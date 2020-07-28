/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.operations;

import com.example.db.DBConnect;
import com.example.db.StringUtil;
import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author basava
 */
public class OtherOperation {
    JSONObject otherData;
     String studentId,schoolId,staffId,academicYearIdId;
     int studId,stfId,acdmicId;
     DBConnect con = new DBConnect();
     JSONObject outObject=new JSONObject();
     JSONArray array= new JSONArray();
      ResultSet rs=null;      
      CallableStatement callable=null;
   public OtherOperation(JSONObject otherData){
    this.otherData=otherData;
}  
   public void fetchSudentIdWithSql(JSONObject otherData,String sql){
       try{
      studId = Integer.parseInt(otherData.getString("studentId"));
        acdmicId=Integer.parseInt(otherData.getString("AcademicYearId"));
       
        callable= con.getConnection().prepareCall(sql);
        callable.setInt(1,studId);
        callable.setInt(2,acdmicId);
        rs= callable.executeQuery();
       }catch(Exception e){
           e.printStackTrace();
       }
   }
   
   public void fetchStaffIdWithSql(JSONObject otherData,String sql){
       try{
      staffId = otherData.getString("StaffId");
        stfId = Integer.parseInt(staffId);
        academicYearIdId = otherData.getString("AcademicYearId");
        acdmicId = Integer.parseInt(academicYearIdId);
       
        callable= con.getConnection().prepareCall(sql);
        callable.setInt(1,stfId);
        callable.setInt(2,acdmicId);
        rs= callable.executeQuery();
       }catch(Exception e){
           e.printStackTrace();
       }
   }
   public void fetchStaffSubjectData(JSONObject otherData,String sql){
       try{
           String strClassId = otherData.getString("ClassId");
        int clsId = Integer.parseInt(strClassId);
      
        callable= con.getConnection().prepareCall(sql);
        callable.setInt(1,clsId);
         rs= callable.executeQuery();
       }catch(Exception e){
            e.printStackTrace();
       }
       
   }
   
    public void fetchSudentHistoryIdWithSql(JSONObject otherData,String sql){
       try{
      studId = Integer.parseInt(otherData.getString("StudentId"));
        callable= con.getConnection().prepareCall(sql);
        callable.setInt(1,studId);
        
        rs= callable.executeQuery();
       }catch(Exception e){
           e.printStackTrace();
       }
   }
   
   public void fetchStaffSubjectSyllabusData(JSONObject otherData,String sql){
       try{
           String strClassId = otherData.getString("ClassId");
        int clsId = Integer.parseInt(strClassId);
        String strSubjectId=otherData.getString("SubjectId");
        int subId = Integer.parseInt(strSubjectId);
       
        callable= con.getConnection().prepareCall(sql);
        callable.setInt(1,clsId);
        callable.setInt(2,subId);
         rs= callable.executeQuery();
       }catch(Exception e){
            e.printStackTrace();
       }
   }
   
    public void getSudentId(JSONObject otherData){
       try{
      studentId = otherData.getString("studentId");
        studId = Integer.parseInt(studentId);
        
       }catch(Exception e){
           e.printStackTrace();
       }
   }
   
   public void printSuccessStatus(JSONArray array){
        try{
             outObject.put("Status", "Success");
             outObject.put("Message", "Data found..");
             outObject.put("Result", array);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public void printFaliureStatus(JSONArray array){
        try{
            outObject.put("Status", "Failure");
            outObject.put("Message", "Data not found..");
            outObject.put("Result", array);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    public void closeDBConnection()
    {
        try{
            callable.close();
            }catch(Exception e){
                e.printStackTrace();
            }
    }
        
   
   public String getSubjectlist() throws JSONException, SQLException{
      
       try{
       
           
            String sql = " CALL sp_and_get_subject_studentswise(?);";
            // fetchSudentIdWithSql(otherData,sql);
             studId = Integer.parseInt(otherData.getString("studentId"));
       
       
        callable= con.getConnection().prepareCall(sql);
        callable.setInt(1,studId);
        
        rs= callable.executeQuery();
            
            if(rs.first())
           
            {
               do
               {
                    JSONObject object = new JSONObject();
                            
                            String strSubject= rs.getString("Subject");
                            String strstudentname = rs.getString("studentname");
                            String strclass = rs.getString("class");
                            String strSubjectId= rs.getString("SubjectId");
                            object.put("studentname", strstudentname);
                            object.put("Subject", strSubject);
                            object.put("class", strclass);
                           object.put("SubjectId", strSubjectId);
                            array.put(object);
               }while(rs.next());
               
                printSuccessStatus(array);
           }
           
          else {
                        printFaliureStatus(array);
                }
            
            return outObject.toString();
        }catch(Exception ee) {
            String exce = ee.toString();
            String exc = "{\"Status\":\"Exception\" ,  \"Message\":\""+exce+"\" ,"
                    + " \"Result\":[]}";
            return exc;    
        }finally{
            closeDBConnection();
        }   
       
      
   }
    public String getSyllabus(){
        try{
               
               int subjectId = Integer.parseInt(otherData.getString("SubjectId"));
               int studentId= Integer.parseInt(otherData.getString("StudentId")); 
               String sql = " CALL `sp_and_get_syllabus`(?, ?);";
               getSudentId(otherData);
               
               callable = con.getConnection().prepareCall(sql);
               callable.setInt(1,studentId);
               callable.setInt(2, subjectId);
               rs = callable.executeQuery(); 
               if(rs.first())
           
               {
                 do
                 {
                          JSONObject object = new JSONObject();
                            
                            String strSyllabus= rs.getString("Syllabus");
                            String strstudentname = rs.getString("StudentName");
                            String strclass = rs.getString("Class");
                            String strSubject=rs.getString("Subject");
                            String strChapterName=rs.getString("ChapterName");
                            object.put("Syllabus", strSyllabus);
                            object.put("Subject", strSubject);
                            object.put("studentname", strstudentname);
                            object.put("class", strclass);
                           object.put("ChapterName", strChapterName);
                            array.put(object);
                }while(rs.next());
               
                      outObject.put("Status", "Success");
                      outObject.put("Message", "Data found..");
                      outObject.put("Result", array);
           }
           
          else {
                        outObject.put("Status", "Failure");
                        outObject.put("Message", "Data not found..");
                        outObject.put("Result", array);
                }
            
            return outObject.toString();
        }catch(Exception ee){
             String exce = ee.toString();
            String exc = "{\"Status\":\"Exception\" ,  \"Message\":\""+exce+"\" ,"
                    + " \"Result\":[]}";
            return exc; 
        }finally{
            closeDBConnection();
        }
        
    }
    public String getGAlleryEventList(){
       try{
       
           
            String sql = " CALL `sp_and_get_gallery_event_list`(?,?);";
             fetchSudentIdWithSql(otherData,sql);
            
            if(rs.first())
           
            {
               do
               {
                    JSONObject object = new JSONObject();
                            
                           
                            String streventTitle = rs.getString("eventTitle");
                            String streventId = rs.getString("eventId");
                            
                            
                            object.put("eventTitle", streventTitle);
                             object.put("eventId", streventId);
                            array.put(object);
               }while(rs.next());
               
                outObject.put("Status", "Success");
                outObject.put("Message", "Data found..");
                outObject.put("Result", array);
           }
           
          else {
                        outObject.put("Status", "Failure");
                        outObject.put("Message", "Data not found..");
                        outObject.put("Result", array);
                }
            
            return outObject.toString();
        }catch(Exception ee) {
            String exce = ee.toString();
            String exc = "{\"Status\":\"Exception\" ,  \"Message\":\""+exce+"\" ,"
                    + " \"Result\":[]}";
            return exc;    
        }finally{
            closeDBConnection();
        }
    }
    
    public String getSatffGAlleryEventList(){
        try{
       
           
            String sql = " CALL `sp_and_get_staff_gallery`(?,?);";
             fetchStaffIdWithSql(otherData,sql);
            
            if(rs.first())
           
            {
               do
               {
                    JSONObject object = new JSONObject();
                            
                           
                            String streventTitle = rs.getString("eventTitle");
                            String streventId = rs.getString("eventId");
                            
                            
                            object.put("eventTitle", streventTitle);
                             object.put("eventId", streventId);
                            array.put(object);
               }while(rs.next());
               
                outObject.put("Status", "Success");
                outObject.put("Message", "Data found..");
                outObject.put("Result", array);
           }
           
          else {
                        outObject.put("Status", "Failure");
                        outObject.put("Message", "Data not found..");
                        outObject.put("Result", array);
                }
            
            return outObject.toString();
        }catch(Exception ee) {
            String exce = ee.toString();
            String exc = "{\"Status\":\"Exception\" ,  \"Message\":\""+exce+"\" ,"
                    + " \"Result\":[]}";
            return exc;    
        }finally{
            closeDBConnection();
        }
    }
    public  String getGAlleryImagesByEventID(){
       try{
       
            getSudentId(otherData);
            String eventId = otherData.getString("eventId");
            int evntId=Integer.parseInt(eventId);
            String sql = " CALL `sp_and_get_images_by_eventId`(?,?);";
            callable = con.getConnection().prepareCall(sql);
            callable.setInt(1,studId);
            callable.setInt(2,evntId);
            rs = callable.executeQuery();
            
            if(rs.first())
           
            {
               do
               {
                    JSONObject object = new JSONObject();
                            
                           
                            String streventTitle = rs.getString("eventTitle");
                            String streventId = rs.getString("eventId");
                            String strDescription = rs.getString("Description");
                            String strUploadedDate = rs.getString("UploadedDate");
                            String strImage = rs.getString("Image");
                            
                            object.put("eventTitle", streventTitle);
                             object.put("eventId", streventId);
                             object.put("Description", strDescription);
                             object.put("UploadedDate", strUploadedDate);
                             object.put("Image", strImage);
                            
                            array.put(object);
               }while(rs.next());
               
                outObject.put("Status", "Success");
                outObject.put("Message", "Data found..");
                outObject.put("Result", array);
           }
           
          else {
                        outObject.put("Status", "Failure");
                        outObject.put("Message", "Data not found..");
                        outObject.put("Result", array);
                }
            
            return outObject.toString();
        }catch(Exception ee) {
            String exce = ee.toString();
            String exc = "{\"Status\":\"Exception\" ,  \"Message\":\""+exce+"\" ,"
                    + " \"Result\":[]}";
            return exc;    
        }finally{
            closeDBConnection();
        } 
    }
    
    public String getStaffGAlleryImagesByEventId(){
        try{
       
           
            int eventId = Integer.parseInt(otherData.getString("eventId"));
            int acdId = Integer.parseInt(otherData.getString("AcademicYearId"));
           
            String sql = " CALL `sp_and_staff_get_gallery_details_by_eventId`(?,?);";
            callable = con.getConnection().prepareCall(sql);
           
            callable.setInt(1,eventId);
            callable.setInt(2,acdId);
            rs = callable.executeQuery();
            
            if(rs.first())
           
            {
               do
               {
                    JSONObject object = new JSONObject();
                            
                           
                            String streventTitle = rs.getString("eventTitle");
                            String streventId = rs.getString("eventId");
                            String strDescription = rs.getString("Description");
                            String strUploadedDate = rs.getString("UploadedDate");
                            String strImage = rs.getString("Image");
                            
                            object.put("eventTitle", streventTitle);
                             object.put("eventId", streventId);
                             object.put("Description", strDescription);
                             object.put("UploadedDate", strUploadedDate);
                             object.put("Image", strImage);
                            
                            array.put(object);
               }while(rs.next());
               
                outObject.put("Status", "Success");
                outObject.put("Message", "Data found..");
                outObject.put("Result", array);
           }
           
          else {
                        outObject.put("Status", "Failure");
                        outObject.put("Message", "Data not found..");
                        outObject.put("Result", array);
                }
            
            return outObject.toString();
        }catch(Exception ee) {
            String exce = ee.toString();
            String exc = "{\"Status\":\"Exception\" ,  \"Message\":\""+exce+"\" ,"
                    + " \"Result\":[]}";
            return exc;    
        }finally{
            closeDBConnection();
        } 
    }
    
    
     public String getStuentGAlleryImagesByEventId(){
        try{
       
           
            int studId = Integer.parseInt(otherData.getString("StudentId"));
            int eventId = Integer.parseInt(otherData.getString("eventId"));
            String sql = " CALL `sp_and_student_get_gallery_details_by_eventId`(?,?);";
            callable = con.getConnection().prepareCall(sql);
            
            callable.setInt(1,eventId);
            callable.setInt(2,studId);
            rs = callable.executeQuery();
            
            if(rs.first())
           
            {
               do
               {
                    JSONObject object = new JSONObject();
                            
                           
                            String streventTitle = rs.getString("eventTitle");
                            String streventId = rs.getString("eventId");
                            String strDescription = rs.getString("Description");
                            String strUploadedDate = rs.getString("UploadedDate");
                            String strImage = rs.getString("Image");
                            
                            object.put("eventTitle", streventTitle);
                             object.put("eventId", streventId);
                             object.put("Description", strDescription);
                             object.put("UploadedDate", strUploadedDate);
                             object.put("Image", strImage);
                            
                            array.put(object);
               }while(rs.next());
               
                outObject.put("Status", "Success");
                outObject.put("Message", "Data found..");
                outObject.put("Result", array);
           }
           
          else {
                        outObject.put("Status", "Failure");
                        outObject.put("Message", "Data not found..");
                        outObject.put("Result", array);
                }
            
            return outObject.toString();
        }catch(Exception ee) {
            String exce = ee.toString();
            String exc = "{\"Status\":\"Exception\" ,  \"Message\":\""+exce+"\" ,"
                    + " \"Result\":[]}";
            return exc;    
        }finally{
            closeDBConnection();
        } 
    }
    
    
    
    
    public String getEventList(){
        try{
       
            
            String sql = " CALL `sp_and_get_events_list`(?,?);";
            fetchSudentIdWithSql(otherData,sql);
            
            if(rs.first())
           
            {
               do
               {
                    JSONObject object = new JSONObject();
                            
                           
                            String strstudentname = rs.getString("studentname");
                            String strclass = rs.getString("class");
                            String strDay= rs.getString("Day"); 
                            String strMonth = rs.getString("Month");
                            String strEventtitle= rs.getString("eventtitle");
                            String streventId= rs.getString("eventId");
                            
                            object.put("studentname", strstudentname);
                            object.put("class", strclass);
                            object.put("Day", strDay);
                            object.put("Month", strMonth);
                            object.put("eventtitle", strEventtitle);
                            object.put("eventId", streventId);
                            array.put(object);
               }while(rs.next());
               
                outObject.put("Status", "Success");
                outObject.put("Message", "Data found..");
                outObject.put("Result", array);
           }
           
          else {
                        outObject.put("Status", "Failure");
                        outObject.put("Message", "Data not found..");
                        outObject.put("Result", array);
                }
            
            return outObject.toString();
        }catch(Exception ee) {
            String exce = ee.toString();
            String exc = "{\"Status\":\"Exception\" ,  \"Message\":\""+exce+"\" ,"
                    + " \"Result\":[]}";
            return exc;    
        }finally{
            closeDBConnection();
        }
    }
    
    public String getClasswiseAssignmentList(){
        
        try{
       
            
            String sql = " CALL `sp_get_tblassignmentlist_byclassid`(?);";
            fetchStaffSubjectData(otherData,sql);
            
            if(rs.first())
           
            {
               do
               {
                    JSONObject object = new JSONObject();
                            
                           
                            String strClass=rs.getString("class");
                            String strSubject = rs.getString("Subject");
                            String strTitle = rs.getString("title");
                            String strDescription= rs.getString("description"); 
                            String strFile_path = rs.getString("fileurl");
                            String strFilename= rs.getString("filename");
                            String strSubjectId=rs.getString("SubjectId");
                            String strAssignment_id = rs.getString("assignment_id");
                            String strSchoolId = rs.getString("school_id");
                            String strAcademicYearId= rs.getString("academic_year_id"); 
                            String strClassId = rs.getString("class_id");
                            String strAddedBy= rs.getString("added_by");
                            String strAddedDate = rs.getString("added_date");
                            
                            
                            
                            
                            object.put("Class", strClass);
                            object.put("Subject", strSubject);
                            object.put("Title", strTitle);
                            object.put("Description", strDescription);
                            object.put("File_path", strFile_path);
                            object.put("Filename", strFilename);
                            object.put("SubjectId", strSubjectId);
                            object.put("Assignment_id", strAssignment_id);
                            object.put("SchoolId", strSchoolId);
                            object.put("AcademicYearId", strAcademicYearId);
                            object.put("ClassId", strClassId);
                            object.put("AddedBy", strAddedBy);
                            object.put("AddedDate", strAddedDate);
                            
                            array.put(object);
               }while(rs.next());
               
                outObject.put("Status", "Success");
                outObject.put("Message", "Data found..");
                outObject.put("Result", array);
           }
           
          else {
                        outObject.put("Status", "Failure");
                        outObject.put("Message", "Data not found..");
                        outObject.put("Result", array);
                }
            
            return outObject.toString();
        }catch(Exception ee) {
            String exce = ee.toString();
            String exc = "{\"Status\":\"Exception\" ,  \"Message\":\""+exce+"\" ,"
                    + " \"Result\":[]}";
            return exc;    
        }finally{
            closeDBConnection();
        }
        
        
        
    }
    
    public String editAssignmentDetails(){
        int effRow,resId;
        try{
            String sql=" CALL `sp_insert_tblassignment`(?,?,?,?,?,?,?,?,?,?,?)";
             Timestamp tmpStmpExmSetDate=null,tmpStmpExmOnDate=null;
             
            int asmntId = Integer.parseInt(otherData.getString("AssignmentId"));
            int schId = Integer.parseInt(otherData.getString("SchoolId"));
            int acdmId = Integer.parseInt(otherData.getString("AcademicYearId"));
            int clsId=Integer.parseInt(otherData.getString("ClassId"));
            int subId = Integer.parseInt(otherData.getString("SubjectId"));
            int setBy = Integer.parseInt(otherData.getString("SetBy"));
            
            String strTitle=otherData.getString("Title");
            String base64Title= StringUtil.base64ToString(strTitle);
            String strDescription=otherData.getString("Description");
            String base64Description= StringUtil.base64ToString(strDescription);
            String strEditDate = otherData.getString("EditDate");
            String strFilePath=otherData.getString("FilePath");
           
           if(!(strEditDate.equals("")))
             {
                 Date asmntDate = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").parse(strEditDate);
               tmpStmpExmSetDate=new Timestamp(asmntDate.getTime());;
//                SimpleDateFormat dateFormatToServer = new SimpleDateFormat("yyyy-MM-dd HH:mm");
//                String strnoteSentDateInFormated = dateFormatToServer.format(noticeDateFrom);
//                
//                scdulSetDateSQL = dateFormatToServer.parse(strnoteSentDateInFormated);
//               
//                //SQL Date For Stored Procuder Init
//                sqlSdulSetDate = new java.sql.Date(scdulSetDateSQL.getTime());
             }
            
          
             
            callable= con.getConnection().prepareCall(sql);
            callable.setInt(1,asmntId);
            callable.setInt(2,schId);
            callable.setInt(3,acdmId);
            callable.setInt(4,clsId);
            callable.setInt(5,subId);
            callable.setString(6, strTitle);
            callable.setString(7, strDescription);
            
            
            callable.setString(8,strFilePath);
            
            
            callable.setInt(9,setBy); 
            
             callable.setInt(10,setBy);
            
                      
            effRow=callable.executeUpdate();
            resId=callable.getInt(11);
            
             if(resId==-2)
            {
                 
                 outObject.put("Status", "Success");
                 outObject.put("resultId", resId);
                 outObject.put("Message", "assignment edited sucessfully");
                
             }else if(resId==-1){
                 outObject.put("Status", "Fail");
                 outObject.put("resultId", resId);
                 outObject.put("Message", "This Record already exists");
            }else {
                outObject.put("Status", "Fail");
                outObject.put("resultId", resId);
                 outObject.put("Message", "Coludn't edit assigment because of some error ");
            }
            
            
          return outObject.toString();
        }catch(Exception ee) {
            String exce = ee.toString();
            String exc = "{\"Status\":\"Exception\" ,  \"Message\":\""+exce+"\" ,"
                    + " \"Result\":[]}";
            return exc;    
        }
      finally{
            closeDBConnection();
        }
        
        
    } 
    
    public String getStudentwiseAssignmentList(){
        try{
       
            
            String sql = " CALL `sp_get_assignment_by_student_history_id`(?);";
            fetchSudentHistoryIdWithSql(otherData,sql);
            
            if(rs.first())
           
            {
               do
               {
                    JSONObject object = new JSONObject();
                            
                           
                            String strRollNo=rs.getString("RollNo");
                            String strClass = rs.getString("Class");
                            String strStudentName = rs.getString("StudentName");
                            String strSubject= rs.getString("Subject"); 
                            String strTitle = rs.getString("title");
                            String strDescription= rs.getString("description");
                            String strFilePath=rs.getString("fileurl");
                            //String strFileUrl=rs.getString("fileurl");
                            String strFileName = rs.getString("filename");
                            String strAddedDate = rs.getString("added_date");
                            
                            
                            
                            
                            
                            object.put("RollNo", strRollNo);
                            object.put("Class", strClass);
                            object.put("StudentName", strStudentName);
                            object.put("Subject", strSubject);
                            object.put("Title", strTitle);
                            object.put("Description", strDescription);
                            object.put("FilePath", strFilePath);
                           // object.put("FileUrl", strFileUrl);
                            object.put("FileName", strFileName);
                            object.put("AddedDate", strAddedDate);
                            
                            
                            
                            
                            array.put(object);
               }while(rs.next());
               
                outObject.put("Status", "Success");
                outObject.put("Message", "Data found..");
                outObject.put("Result", array);
           }
           
          else {
                        outObject.put("Status", "Failure");
                        outObject.put("Message", "Data not found..");
                        outObject.put("Result", array);
                }
            
            return outObject.toString();
        }catch(Exception ee) {
            String exce = ee.toString();
            String exc = "{\"Status\":\"Exception\" ,  \"Message\":\""+exce+"\" ,"
                    + " \"Result\":[]}";
            return exc;    
        }finally{
            closeDBConnection();
        }
    }
    public String getStaffEventList(){
         try{
       
            
            String sql = " CALL `sp_and_get_staff_eventlist`(?,?);";
            fetchStaffIdWithSql(otherData,sql);
            
            if(rs.first())
           
            {
               do
               {
                    JSONObject object = new JSONObject();
                            
                           
//                            String strstudentname = rs.getString("studentname");
//                            String strclass = rs.getString("class");
                            String strDay= rs.getString("Day"); 
                            String strMonth = rs.getString("Month");
                            String strEventtitle= rs.getString("eventtitle");
                            String streventId= rs.getString("eventId");
                            
//                            object.put("studentname", strstudentname);
//                            object.put("class", strclass);
                            object.put("Day", strDay);
                            object.put("Month", strMonth);
                            object.put("eventtitle", strEventtitle);
                            object.put("eventId", streventId);
                            array.put(object);
               }while(rs.next());
               
                outObject.put("Status", "Success");
                outObject.put("Message", "Data found..");
                outObject.put("Result", array);
           }
           
          else {
                        outObject.put("Status", "Failure");
                        outObject.put("Message", "Data not found..");
                        outObject.put("Result", array);
                }
            
            return outObject.toString();
        }catch(Exception ee) {
            String exce = ee.toString();
            String exc = "{\"Status\":\"Exception\" ,  \"Message\":\""+exce+"\" ,"
                    + " \"Result\":[]}";
            return exc;    
        }finally{
            closeDBConnection();
        }
    }
    
    
    
     public String getEventDetails(){
        try{
               getSudentId(otherData);
               String eventId = otherData.getString("eventId");
               int evntId=Integer.parseInt(eventId);
               String sql = " CALL `sp_and_get_event_details`(?,?);";
               callable = con.getConnection().prepareCall(sql);
               callable.setInt(1,studId);
               callable.setInt(2, evntId);
               rs = callable.executeQuery(); 
               if(rs.first())
           
               {
                 do
                 {
                          JSONObject object = new JSONObject();
                            
                            String strEventDescription= rs.getString("EventDescription");
                            String strstudentname = rs.getString("studentname");
                            String strclass = rs.getString("class");
                            
                            String strEventVenue= rs.getString("EventVenue");
                            String streventDate = rs.getString("eventDate");
                            String streventCreated = rs.getString("eventCreated");
                            String streventtitle=rs.getString("eventtitle");
                            
                            object.put("EventDescription", strEventDescription);
                            object.put("studentname", strstudentname);
                            object.put("class", strclass);
                            object.put("EventVenue", strEventVenue);
                            object.put("eventDate", streventDate);
                            object.put("eventCreated", streventCreated);
                            object.put("eventtitle", streventtitle);
                            array.put(object);
                }while(rs.next());
               
                      outObject.put("Status", "Success");
                      outObject.put("Message", "Data found..");
                      outObject.put("Result", array);
           }
           
          else {
                        outObject.put("Status", "Failure");
                        outObject.put("Message", "Data not found..");
                        outObject.put("Result", array);
                }
            
            return outObject.toString();
        }catch(Exception ee){
             String exce = ee.toString();
            String exc = "{\"Status\":\"Exception\" ,  \"Message\":\""+exce+"\" ,"
                    + " \"Result\":[]}";
            return exc; 
        }finally{
            closeDBConnection();
        }
    }
     
  public String getStaffEventDetails(){
      try{
               
               String eventId = otherData.getString("eventId");
               int evntId=Integer.parseInt(eventId);
               String sql = " CALL `sp_and_staff_get_event_details`(?);";
               callable = con.getConnection().prepareCall(sql);
               
               callable.setInt(1, evntId);
               rs = callable.executeQuery(); 
               if(rs.first())
           
               {
                 do
                 {
                          JSONObject object = new JSONObject();
                            
                            String strEventDescription= rs.getString("EventDescription");
                           
                            
                            String strEventVenue= rs.getString("EventVenue");
                            String streventDate = rs.getString("eventDate");
                            String streventCreated = rs.getString("eventCreated");
                            String streventtitle=rs.getString("eventtitle");
                            
                            object.put("EventDescription", strEventDescription);
                          
                            object.put("EventVenue", strEventVenue);
                            object.put("eventDate", streventDate);
                            object.put("eventCreated", streventCreated);
                            object.put("eventtitle", streventtitle);
                            array.put(object);
                }while(rs.next());
               
                      outObject.put("Status", "Success");
                      outObject.put("Message", "Data found..");
                      outObject.put("Result", array);
           }
           
          else {
                        outObject.put("Status", "Failure");
                        outObject.put("Message", "Data not found..");
                        outObject.put("Result", array);
                }
            
            return outObject.toString();
        }catch(Exception ee){
             String exce = ee.toString();
            String exc = "{\"Status\":\"Exception\" ,  \"Message\":\""+exce+"\" ,"
                    + " \"Result\":[]}";
            return exc; 
        }finally{
            closeDBConnection();
        }
  }   
   
  public String getStaffclasswiseSubject(){
      try{
       String sql = " CALL sp_and_staff_get_classwise_syllabus (?);";
            
             fetchStaffSubjectData(otherData,sql);
            
            if(rs.first())
           
            {
               do
               {
                    JSONObject object = new JSONObject();
                            
                            String strSubject= rs.getString("subject");
                            String strSubjectId = rs.getString("SubjectId");
                            String strclass = rs.getString("class");
                            String strClassId = rs.getString("ClassId");
                            
                            object.put("SubjectId", strSubjectId);
                            object.put("Subject", strSubject);
                            object.put("class", strclass);
                            object.put("ClassId", strClassId);
                           
                            array.put(object);
               }while(rs.next());
               
                printSuccessStatus(array);
           }
           
          else {
                        printFaliureStatus(array);
                }
            
            return outObject.toString();
        }catch(Exception ee) {
            String exce = ee.toString();
            String exc = "{\"Status\":\"Exception\" ,  \"Message\":\""+exce+"\" ,"
                    + " \"Result\":[]}";
            return exc;    
        }finally{
            closeDBConnection();
        }
  } 
  
  public String getStaffclasswiseSubjectSyllabus(){
      try{
       String sql = " CALL sp_and_get_syllabus_by_class(?,?);";
            
             fetchStaffSubjectSyllabusData(otherData,sql);
            
            if(rs.first())
           
            {
               do
               {
                    JSONObject object = new JSONObject();
                            
                            String strSubject= rs.getString("Subject");
                            String strSyllabus = rs.getString("Syllabus");
                            String strclass = rs.getString("Class");
                             String strChapterName = rs.getString("ChapterName");
                            
                            object.put("Syllabus", strSyllabus);
                            object.put("Subject", strSubject);
                            object.put("class", strclass);
                            object.put("ChapterName", strChapterName);
                           
                            array.put(object);
               }while(rs.next());
               
                printSuccessStatus(array);
           }
           
          else {
                        printFaliureStatus(array);
                }
            
            return outObject.toString();
        }catch(Exception ee) {
            String exce = ee.toString();
            String exc = "{\"Status\":\"Exception\" ,  \"Message\":\""+exce+"\" ,"
                    + " \"Result\":[]}";
            return exc;    
        }finally{
            closeDBConnection();
        }
  }
  
    public String GetYear(){
       DBConnect con = new DBConnect();
       JSONArray jsonArray=new JSONArray();
          JSONObject outObject= new JSONObject();
       String strSp="call sp_get_academicyear_range(?);";
       try{
           CallableStatement callable=null;
              ResultSet rs=null;
         
          callable= con.getConnection().prepareCall(strSp);
            String strSchoolId= otherData.getString("SchoolId");
       
            callable.setString(1,strSchoolId);
         
           rs=callable.executeQuery();
             if(rs.first()){
                 
                  JSONObject object = new JSONObject();
                    
                     object.put("SchoolId","0");
                     object.put("AcademicYearId","0");
                     object.put("AcademicYearRange","Select Year");
                     
               jsonArray.put(object);
  
                 do{
                    JSONObject obj = new JSONObject();
                  
                    String strAcademicYearRange=rs.getString("AcademicYearRange");
                    String strAcademicYearId=rs.getString("AcademicYearId");
                     
                     //obj.put("SchoolId",strSchoolId);
                      obj.put("AcademicYearRange",strAcademicYearRange);
                     obj.put("AcademicYearId",strAcademicYearId);
                  jsonArray.put(obj);
                 }while (rs.next());
              
                 outObject.put("Status","Success");
                   outObject.put("Message","Data found");
                 outObject.put("Result", jsonArray);
                  
             }else {
                     outObject.put("Status","Failure");
                     outObject.put("Message","Data not found");
                    outObject.put("Result", jsonArray);
             }
               return outObject.toString();
       }catch(Exception e){
              String exc=e.toString();
            String errMsg="{\"status\":\"Execption\",\"Message\":" +exc+"}";
            return errMsg;
       }
   
   }
   public String GetFeesType(){
       DBConnect con = new DBConnect();
       JSONArray jsonArray=new JSONArray();
          JSONObject outObject= new JSONObject();
       String strSp="call sp_view_fees_type(?);";
       try{
           CallableStatement callable=null;
              ResultSet rs=null;
         
          callable= con.getConnection().prepareCall(strSp);
            String strSchoolId= otherData.getString("SchoolId");
       
            callable.setString(1,strSchoolId);
         
           rs=callable.executeQuery();
             if(rs.first()){
                 
                  JSONObject object = new JSONObject();
                    
                     object.put("SchoolId","0");
                     object.put("fees_type_Id","0");
                     object.put("Fees_Type","Select FeesType");
                     
               jsonArray.put(object);
  
                 do{
                    JSONObject obj = new JSONObject();
                  
                    String strfees_type_Id=rs.getString("fees_type_Id");
                    String strFees_Type=rs.getString("Fees_Type");
                     
                     //obj.put("SchoolId",strSchoolId);
                      obj.put("fees_type_Id",strfees_type_Id);
                     obj.put("Fees_Type",strFees_Type);
                  jsonArray.put(obj);
                 }while (rs.next());
              
                 outObject.put("Status","Success");
                   outObject.put("Message","Data found");
                 outObject.put("Result", jsonArray);
                  
             }else {
                     outObject.put("Status","Failure");
                     outObject.put("Message","Data not found");
                    outObject.put("Result", jsonArray);
             }
               return outObject.toString();
       }catch(Exception e){
              String exc=e.toString();
            String errMsg="{\"status\":\"Execption\",\"Message\":" +exc+"}";
            return errMsg;
       }
   
   }
   public String GetAdmissionFeesStudWise(){
       DBConnect con = new DBConnect();
       JSONArray jsonArray=new JSONArray();
          JSONObject outObject= new JSONObject();
      // String strSp="call sp_get_admission_feesreceipt_details_by_studentId_rvk(?);";
         try{
            String strSchoolId= otherData.getString("SchoolId"); 
            String strSp ="";
            if(strSchoolId.equalsIgnoreCase("4")){
               strSp ="call sp_get_admission_feesreceipt_details_by_studentId_rvk(?);";
            }else if(strSchoolId.equalsIgnoreCase("5")){
                 strSp ="call sp_get_admission_feesreceipt_details_by_studentId_rvk(?);";
            }
       
           CallableStatement callable=null;
              ResultSet rs=null;
         
         callable= con.getConnection().prepareCall(strSp);
           String strStudentId= otherData.getString("StudentId");
           
           callable.setString(1,strStudentId);
          
          rs=callable.executeQuery();
             if(rs.first()){
                 
                 do{
                    JSONObject obj = new JSONObject();
                    String strReceiptNumber=rs.getString("ReceiptNumber");
                   String strDate=rs.getString("Date");
                    String strClassSection=rs.getString("ClassSection");
                    String strRollNo=rs.getString("RollNo");
                    String strStudentName=rs.getString("StudentName");
                    String strTotal= rs.getString("Total");
                    String strTotal_amount=rs.getString("Total_amount");
                    String strBalance=rs.getString("Balance");
                    
                       obj.put("ReceiptNumber",strReceiptNumber);
                      obj.put("Date", strDate);
                      obj.put("ClassSection",strClassSection);
                      obj.put("RollNo",strRollNo);
                      obj.put("StudentName", strStudentName);
                      obj.put("Total",strTotal);
                      obj.put("Total_amount", strTotal_amount);
                      obj.put("Balance", strBalance);
                   
                      jsonArray.put(obj);
                      
                 }while (rs.next());
              
                  outObject.put("Status","Success");
                  outObject.put("Message","Data found");
                  outObject.put("Result", jsonArray);
                  
             }else {
                     outObject.put("Status","Failure");
                     outObject.put("Message","Data not found");
                    outObject.put("Result", jsonArray);
             }
               return outObject.toString();
       }catch(Exception e){
              String exc=e.toString();
            String errMsg="{\"status\":\"Execption\",\"Message\":" +exc+"}";
            return errMsg;
       }
   
   }
     public String GetAdmissionFeesClassIdWise(){
       DBConnect con = new DBConnect();
       JSONArray jsonArray=new JSONArray();
          JSONObject outObject= new JSONObject();
       String strSp="call sp_get_admission_feesreceipt_details_by_classId_rvk(?);";
       try{
           CallableStatement callable=null;
              ResultSet rs=null;
         
         callable= con.getConnection().prepareCall(strSp);
           String strClassId= otherData.getString("ClassId");
           
           callable.setString(1,strClassId);
          
          rs=callable.executeQuery();
             if(rs.first()){
                 
                 do{
                    JSONObject obj = new JSONObject();
                  String strRollNum = rs.getString("RollNo");
                  String strStudName= rs.getString("Studentname");
                    String strTotal_amount=rs.getString("Total_amount");
                    String strBalance=rs.getString("Balance");
                    
                    obj.put("RollNo",strRollNum);
                    obj.put("Studentname", strStudName);
                      obj.put("Total_amount", strTotal_amount);
                      obj.put("Balance", strBalance);
                   
                      jsonArray.put(obj);
                      
                 }while (rs.next());
              
                  outObject.put("Status","Success");
                  outObject.put("Message","Data found");
                  outObject.put("Result", jsonArray);
                  
             }else {
                     outObject.put("Status","Failure");
                     outObject.put("Message","Data not found");
                    outObject.put("Result", jsonArray);
             }
               return outObject.toString();
       }catch(Exception e){
              String exc=e.toString();
            String errMsg="{\"status\":\"Execption\",\"Message\":" +exc+"}";
            return errMsg;
       }
   
   }
   
}
