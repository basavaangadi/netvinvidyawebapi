/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.operations;
import com.example.db.DBConnect;
import java.sql.CallableStatement;
import java.sql.ResultSet;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 *
 * @author basava
 */
public class ClassSubjectOperation {
    
    
    JSONObject classSubjectData;
    DBConnect con = new DBConnect();
    CallableStatement callable=null;
    ResultSet rs=null;
// SchoolId, AcademicYearId, ClassId,SubjectId,StudentId,Class,Section,StaffId,RollNo,
    public ClassSubjectOperation(JSONObject classSubjectData) {
        this.classSubjectData = classSubjectData;
    }
    public void closeDBConnection()
    {
        try{
            callable.close();
            }catch(Exception e){
                e.printStackTrace();
            }
    }
    public String StudentNameWithRollNo()
    {
        JSONObject outObject = new JSONObject();
           try
           {
               JSONArray array = new JSONArray();
           // JSONObject classSubjectData = new JSONObject(classSubjectData);
            String strSchool = classSubjectData.getString("School");
          
            String strClass = classSubjectData.getString("Class");
            String strSection = classSubjectData.getString("Section");
            String strRollnumber = classSubjectData.getString("RollNumber");
            int introll=Integer.parseInt(strRollnumber);
            String sql= " CALL `sp_and_get_student_name_by_roll`(?, ?, ?,?)";
//           
            
           if(rs.first())
           {
               do
               {
                    JSONObject object = new JSONObject();
                    String rollNo = rs.getString("RollNo");
                    String strStudentname = rs.getString("studentname");
                    object.put("RollNo", rollNo);
                    object.put("studentname",strStudentname);
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
    public String SectionDetails()
    {
        JSONObject outObject = new JSONObject();
           try
           {
               JSONArray array = new JSONArray();
            //JSONObject classSubjectData = new JSONObject(classSubjectData);
            String strSchoolId = classSubjectData.getString("SchoolId");
            int SchoolId = Integer.parseInt(strSchoolId);
            String strClass = classSubjectData.getString("Class");
            String sql= " CALL `SectionDetails`(?, ?)";
//           
           if(rs.first())
           {
               do
               {
                    JSONObject object = new JSONObject();
                    String strSection = rs.getString("Section");
                    object.put("Section", strSection);
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
    
    public String Staff_ClassNames()
    {
        JSONObject outObject = new JSONObject();
            try
           {
               JSONArray array = new JSONArray();
            //JSONObject classSubjectData = new JSONObject(classSschoolubjectData);
            String strSchoolId = classSubjectData.getString("SchoolId");
            int schId = Integer.parseInt(strSchoolId);
            String strAcademicYearId = classSubjectData.getString("AcademicYearId");
            int acdmicId = Integer.parseInt(strAcademicYearId);
            String sql= " CALL `sp_and_get_class_by_school_n_academic`(?,?)";
            callable= con.getConnection().prepareCall(sql);
            callable.setInt(1, schId);
            callable.setInt(2, acdmicId);
            rs=callable.executeQuery();
           if(rs.first())
           {
               JSONObject object1=new JSONObject();
               object1.put("Class", "Select Class");
                     array.put(object1);
               do
               {
                    JSONObject object = new JSONObject();
                    String strClass = rs.getString("Class");
                    object.put("Class", strClass);
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
    
    /**
     * Web service operation
     {"StaffId":"3", "Class":"5th"}
     */
    @WebMethod(operationName = "Staff_Class_With_Section")
    public String Staff_Class_With_Section(@WebParam(name = "data") String data) {
        JSONObject outObject = new JSONObject();
           try
           {
               JSONArray array = new JSONArray();
            //JSONObject classSubjectData = new JSONObject(data);
            String strstfId = classSubjectData.getString("StaffId");
            int StaffId = Integer.parseInt(strstfId);
            String strClass = classSubjectData.getString("Class");
            String sql= " CALL `Staff_Class_With_Section`(?, ?)";
//           
           if(rs.first())
           {
               JSONObject object1= new JSONObject();
               object1.put("Section","Select Sections" );
               array.put(object1);
               do
               {
                    JSONObject object = new JSONObject();
                    String strCls = rs.getString("Section");
                    object.put("Section", strCls);
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
    public  String Staff_Class_With_Section()
    {
         JSONObject outObject = new JSONObject();
           try
           {
               JSONArray array = new JSONArray();
           // JSONObject classSubjectData = new JSONObject(classSubjectData);
           String strSchoolId = classSubjectData.getString("SchoolId");
            int schId = Integer.parseInt(strSchoolId);
            String strAcademicYearId = classSubjectData.getString("AcademicYearId");
            int acdmicId = Integer.parseInt(strAcademicYearId);
            String strClass = classSubjectData.getString("Class");
            String sql= " CALL `sp_and_get_section_by_class`(?,?,?)";
            callable =con.getConnection().prepareCall(sql);
            callable.setInt(1, schId);
            callable.setInt(2, acdmicId);
            callable.setString(3,strClass);
            rs=callable.executeQuery();
           if(rs.first())
           {
               JSONObject object1= new JSONObject();
               object1.put("Section", "Select Section");
               object1.put("ClassId", "0");
               array.put(object1);
               
               do
               {
                    JSONObject object = new JSONObject();
                    String strCls = rs.getString("Section");
                    String strClassId = rs.getString("ClassId");
                    object.put("Section", strCls);
                    object.put("ClassId", strClassId);
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
    public String getSubjectsWithClassAndSetion(){
        JSONObject outObject = new JSONObject();
           try
           {
               JSONArray array = new JSONArray();
           
            String strClass = classSubjectData.getString("Class");
               String strSection = classSubjectData.getString("Section");
               int schoolId=Integer.parseInt(classSubjectData.getString("SchoolId"));
               
            String sql= "CALL `sp_and_staff_get_subjects_with_class_section`(?,?,?)";
            callable=con.getConnection().prepareCall(sql);
            callable.setString(1, strClass);
            callable.setString(2, strSection);  
            callable.setInt(3, schoolId);
            rs=callable.executeQuery();
           if(rs.first())
           {
               do
               {
                    JSONObject object = new JSONObject();
                    String strsubject = rs.getString("subject");
                    String strSubjectId = rs.getString("SubjectId");
                    
                    object.put("Subject", strsubject);
                    object.put("SubjectId", strSubjectId);
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
    public  String getClasswiseSubjects()
    {
        JSONObject outObject = new JSONObject();
           try
           {
               JSONArray array = new JSONArray();
           // JSONObject classSubjectData = new JSONObject(classSubjectData);
//            String strSchoolId = classSubjectData.getString("SchoolId");
//            int schId = Integer.parseInt(strSchoolId);
//            String strAcademicYearId = classSubjectData.getString("AcademicYearId");
//               int acdmcId = Integer.parseInt(strAcademicYearId);
            String strClassId = classSubjectData.getString("ClassId");
               int clsId = Integer.parseInt(strClassId);
            String sql= " CALL `sp_and_staff_get_classwise_subject`(?)";
            callable=con.getConnection().prepareCall(sql);
//            callable.setInt(1, schId);
//            callable.setInt(2, acdmcId);
            callable.setInt(1, clsId);
            rs=callable.executeQuery();
           if(rs.first())
           {
               JSONObject object1 = new JSONObject();
                   
                    object1.put("Subject", "Select Subject");
                    object1.put("SubjectId", "0");
                     array.put(object1);
               
               do
               {
                    JSONObject object = new JSONObject();
                    String strsubject = rs.getString("subject");
                    String strSubjectId = rs.getString("SubjectId");
                    
                    object.put("Subject", strsubject);
                    object.put("SubjectId", strSubjectId);
                     array.put(object);
               }while(rs.next());
                outObject.put("Status", "Success");
                outObject.put("Message", "Data found..");
                outObject.put("Result", array);
           }
           
          else {
               JSONObject object1 = new JSONObject();
                   
                    object1.put("Subject", "Select Subject");
                    object1.put("SubjectId", "0");
                     array.put(object1);
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
     public  String getMarksEnteredSubjects()
    {
        JSONObject outObject = new JSONObject();
           try
           {
               JSONArray array = new JSONArray();
        
            String strClassId = classSubjectData.getString("ClassId");
               int clsId = Integer.parseInt(strClassId);
               String strExamId = classSubjectData.getString("ExamId");
               int exmId = Integer.parseInt(strExamId);
            String sql= " CALL `sp_get_marks_entered_subjects`(?,?)";
            callable=con.getConnection().prepareCall(sql);
//           
            callable.setInt(1, clsId);
            callable.setInt(2, exmId);
            rs=callable.executeQuery();
           if(rs.first())
           {
               JSONObject object1 = new JSONObject();
                   
                    object1.put("Subject", "Select Subject");
                    object1.put("SubjectId", "0");
                     array.put(object1);
               
               do
               {
                    JSONObject object = new JSONObject();
                    String strsubject = rs.getString("subject");
                    String strSubjectId = rs.getString("SubjectId");
                    
                    object.put("Subject", strsubject);
                    object.put("SubjectId", strSubjectId);
                     array.put(object);
               }while(rs.next());
                outObject.put("Status", "Success");
                outObject.put("Message", "Data found..");
                outObject.put("Result", array);
           }
           
          else {
               JSONObject object1 = new JSONObject();
                   
                    object1.put("Subject", "Select Subject");
                    object1.put("SubjectId", "0");
                     array.put(object1);
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
   public String getStaffwiseClassSubjects(){
       JSONObject outObject = new JSONObject();
           try
           {
               JSONArray array = new JSONArray();
           
            int stfId = Integer.parseInt(classSubjectData.getString("StaffId"));
              int schId = Integer.parseInt(classSubjectData.getString("SchoolId"));
              int acdmicId = Integer.parseInt(classSubjectData.getString("AcademicYearId"));
              int clsId = Integer.parseInt(classSubjectData.getString("ClassId"));
            String sql= " CALL `Sp_Dropdown_Subject_ByStaffId`(?,?,?,?)";
            callable=con.getConnection().prepareCall(sql);

            callable.setInt(1, stfId);
            callable.setInt(2, clsId);
            callable.setInt(3, schId);
            callable.setInt(4, acdmicId);
            rs=callable.executeQuery();
           if(rs.first())
           {
               do
               {
                    JSONObject object = new JSONObject();
                    String strsubject = rs.getString("subject");
                    String strSubjectId = rs.getString("SubjectId");
                    
                    object.put("Subject", strsubject);
                    object.put("SubjectId", strSubjectId);
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
    public String getClasswiseStudentsList(){
         JSONObject outObject = new JSONObject();
           try
           {
               int rollNotSet=0;
               JSONArray array = new JSONArray();
               String strClassId = classSubjectData.getString("ClassId");
               int clsId = Integer.parseInt(strClassId);
            String sql= " CALL sp_and_staff_get_classwise_studentList(?)";
            callable=con.getConnection().prepareCall(sql);
             callable.setInt(1, clsId);
            rs=callable.executeQuery();
           if(rs.first())
           {
               do
               {
                    JSONObject object = new JSONObject();
                    String strRollNo = rs.getString("RollNo");
                    String strstudentname = rs.getString("studentname");
                    String strStudentDetailsId = rs.getString("StudentDetailsId");
                    String strclass = rs.getString("class");
                    
                    if(strRollNo == null || strRollNo.isEmpty()){
                        rollNotSet=1;
                        break;
                        
                    }

                    
                    
                    object.put("RollNo", strRollNo);
                    object.put("studentname", strstudentname);
                     object.put("StudentDetailsId", strStudentDetailsId);
                    object.put("class", strclass);
                     array.put(object);
               }while(rs.next());
               if(rollNotSet==0){
                    outObject.put("Status", "Success");
                outObject.put("Message", "Data found..");
                outObject.put("Roll_Set", rollNotSet);
                outObject.put("Result", array);
               }else if(rollNotSet==1){
                outObject.put("Status", "Generate  Roll Number");
                outObject.put("Message", "Please generate the Roll Number before you refer this class.");
                outObject.put("Roll_Set", rollNotSet);
               
               }
               
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
    
    
   
    public String getClasslist(){
        
         JSONObject outObject = new JSONObject();
           try
           {
               JSONArray array = new JSONArray();
             
             int schId  = Integer.parseInt(classSubjectData.getString("SchoolId"));
             int acdmicId  = Integer.parseInt(classSubjectData.getString("AcademicYearId"));
            String sql= " CALL `sp_get_class_details_schoolwise`(?,?)";
            callable=con.getConnection().prepareCall(sql);
             
              callable.setInt(1, schId);
               callable.setInt(2, acdmicId);
            rs=callable.executeQuery();
           if(rs.first())
               
           {
               do
               {
                    JSONObject object = new JSONObject();
                    
                    String strClassId = rs.getString("ClassId");
                    String strAcademicYearId = rs.getString("AcademicYearId");
                    String strSchoolId = rs.getString("SchoolId");
                    String strClassSection = rs.getString("ClassSection");
              
                    object.put("ClassId", strClassId);
                    object.put("AcademicYearId", strAcademicYearId);
                    object.put("SchoolId", strSchoolId);
                    object.put("Class", strClassSection);
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
    
    
    
    
  public String getStaffwiseClassList(){
      JSONObject outObject = new JSONObject();
           try
           {
               JSONArray array = new JSONArray();
             int stfId  = Integer.parseInt(classSubjectData.getString("StaffId"));
             int schId  = Integer.parseInt(classSubjectData.getString("SchoolId"));
             int acdmicId  = Integer.parseInt(classSubjectData.getString("AcademicYearId"));
            String sql= " CALL `Sp_DropDown_ClassByStaffId`(?,?,?)";
            callable=con.getConnection().prepareCall(sql);
             callable.setInt(1, stfId);
              callable.setInt(2, schId);
               callable.setInt(3, acdmicId);
            rs=callable.executeQuery();
           if(rs.first())
           {
               do
               {
                    JSONObject object = new JSONObject();
                    
                    String strClass = rs.getString("Class");
                    
                    object.put("Class", strClass);
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
  public String getStaffwiseSectionList(){
       
         JSONObject outObject = new JSONObject();
           try
           {
               JSONArray array = new JSONArray();
           // JSONObject classSubjectData = new JSONObject(classSubjectData);
           int schId = Integer.parseInt(classSubjectData.getString("SchoolId"));
           int stfId  = Integer.parseInt(classSubjectData.getString("StaffId"));
            int acdmicId = Integer.parseInt(classSubjectData.getString("AcademicYearId"));
            String strClass = classSubjectData.getString("Class");
            
            String sql= " CALL `Sp_Dropdown_SectionByStaffId`(?,?,?,?)";
            
            callable =con.getConnection().prepareCall(sql);
            
            callable.setString(1,strClass);
            callable.setInt(2, stfId);
            callable.setInt(3, acdmicId);
            callable.setInt(4,schId);
            
            rs=callable.executeQuery();
           
            if(rs.first())
           {
               do
               {
                    JSONObject object = new JSONObject();
                    String strCls = rs.getString("Section");
                    String strClassId = rs.getString("ClassId");
                    object.put("Section", strCls);
                    object.put("ClassId", strClassId);
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
}
