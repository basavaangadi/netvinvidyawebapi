/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.operations;

import com.example.db.DBConnect;
import java.sql.CallableStatement;
import java.sql.ResultSet;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
/**
 *
 * @author basava
 */
public class UserOPeration {
  
  JSONObject userData;
  String studentId,schoolId;
  int studId;
  DBConnect con = new DBConnect();
  JSONObject outObject=new JSONObject();
  JSONArray array= new JSONArray();
  ResultSet rs=null;
  CallableStatement callable=null;
      
      
      public UserOPeration(JSONObject userData) {
        this.userData = userData;
    }
      
      public void closeDBConnection()
    {
        try{
            callable.close();
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
      
      public  void printFaliureStatus(JSONArray array){
      try{
          outObject.put("Status", "Failure");
          outObject.put("Message", "Data not found..");
          outObject.put("Result", array);
      }catch(Exception e ){
          e.printStackTrace();
      }
  }
      public void printUpdateStatus(JSONArray array){
       try{
          outObject.put("Status", "fail");
          outObject.put("Message", "please update the app");
          outObject.put("Result", array);
      }catch(Exception e ){
          e.printStackTrace();
      }   
      }
      
      
      
    public void fetchUserIdPassword(JSONObject userData,String sql)
    {
        try{
           String strPhonenumber = userData.getString("Phonenumber");
           String strPassword = userData.getString("Password");
           
           callable= con.getConnection().prepareCall(sql);
           callable.setString(1, strPhonenumber);
           callable.setString(2,strPassword);
           rs= callable.executeQuery();
        }catch(Exception e){
            e.printStackTrace();
        }
    }    
    
    
    
    public void fetchSatffIdPassword(JSONObject userData,String sql)
    {
        try{
           String strStaffId = userData.getString("StaffId");
           String strPassword = userData.getString("Password");
           
           callable= con.getConnection().prepareCall(sql);
           callable.setString(1, strStaffId);
           callable.setString(2,strPassword);
           rs= callable.executeQuery();
        }catch(Exception e){
            e.printStackTrace();
        }
    }   
    public String addUser() {
       
        try {
           // String strUserId = userData.getString("UserId");
            String strPassword = userData.getString("password");
            String strEmail = userData.getString("email");
            String strMobileNumber = userData.getString("mobileNumber");
            String strName = userData.getString("name");
          int isPhonePresent= checkPhoneNumberIsInDatabase(strMobileNumber);
          if(isPhonePresent==0){
              outObject.put("Status", "Sorry !!!");
                         outObject.put("Message", "We dont have Student for this phone number");
                         outObject.put("ResultId", -9999);
                         outObject.put("Result", array);
          }  else if (isPhonePresent<0){
              outObject.put("Status", "Error");
                         outObject.put("Message", " couldn't verify the Phone number in  Database");
                         outObject.put("ResultId", -3);
                         outObject.put("Result", array);
          }else{
          String sql="CALL sp_user_register(?,?,?,?,?)";
           
                   callable= con.getConnection().prepareCall(sql);
                   //callable.setString(1, strUserId);
                   callable.setString(1, strPassword);
                   callable.setString(2, strEmail);
                   callable.setString(3, strMobileNumber);
                   callable.setString(4, strName);
                   callable.executeQuery();
                  //int resultId= Integer.parseInt(callable.getString("resultid_out_param"));
                   int resultId= callable.getInt(5);
            
            if(resultId>0){
                 //String strUsername = rs.getString("userID");
                 outObject.put("Status", "unsucessfull");
                 outObject.put("ResultId", -1);
               outObject.put("Message", " Sorry !! mobile number has been already added");
            }
           //String strQuery= "INSERT INTO tbl_user_details (userID, password, email, mobile_number) VALUES ('"+strUserId+"', '"+strPassword+"', '"+strEmail+"', '"+strMobileNumber+"')";
          /*String sql="CALL sp_user_register(?, ?, ?, ?)";
           
                   callable= con.getConnection().prepareCall(sql);
                   callable.setString(1, strUserId);
                   callable.setString(2, strPassword);
                   callable.setString(3, strEmail);
                   callable.setString(4, strMobileNumber);
                   int acKno= callable.executeUpdate();*/
            else if(resultId==0) {
               outObject.put("Status", "Success");
               outObject.put("ResultId", 1);
               outObject.put("Message", "User added successfully");
           }else{
               outObject.put("Status", "Faliure ");
               outObject.put("Message", "User could not be added please try again");
               outObject.put("ResultId", -3);
           }
           
            
           
       }   
          return outObject.toString();
        } catch (Exception e) {
            String exce = e.toString();
            String exc = "{\"Status\":\"Exception\" ,  \"Message\":\""+exce+"\"}";
            return exc;
            
        }finally{
            closeDBConnection();
        }
        
    
}
    public String authUser()
    {
        
      try {
              String sql=" CALL `sp_and_new_auth_user`(?,?)";
         
              fetchUserIdPassword(userData, sql);
              JSONObject object = new JSONObject();
              if(rs.next()) 
              {
                
              //  String strUserId= rs.getString("userID");
                String strEmail=rs.getString("email");
                String strName=rs.getString("name");
                String strMobile=rs.getString("mobile_number");
                String strStudents=rs.getString("students");
              int stud=Integer.parseInt(strStudents);
                
                if(stud>0){
                //object.put("userID", strUserId);
                object.put("email", strEmail);
                object.put("name", strName);
                object.put("mobile_number", strMobile);
                object.put("students", strStudents);
                object.put("Status", "Success");
                object.put("Message", "User verified  successfully");    
                }
                else{
               outObject.put("Status", "Failure");
               outObject.put("Message", "invalid userid or password");
               //array.put(object);
              outObject.put("result", array);
           }
                
                
                array.put(object);
                
                outObject.put("result", array);
           }else{
               outObject.put("Status", "Failure");
               outObject.put("Message", "invalid userid or password");
               //array.put(object);
              outObject.put("result", array);
           }
               return outObject.toString();
        } catch (Exception e) {
            String exce = e.toString();
            String exc = "{\"Status\":\"Exception\" ,  \"Message\":\""+exce+"\"}";
            return exc;    
        }finally{
            closeDBConnection();
        }

    }
    public String authStaff(){
        try {
              String sql=" CALL `sp_and_authenticate_user`(?,?)";
            
         
              fetchSatffIdPassword(userData, sql);
              JSONObject object = new JSONObject();
              if(rs.next()) 
              {
                
                String strStaffId= rs.getString("StaffId");
                String strEmail=rs.getString("EmailId");
                String strName=rs.getString("Name");
                String strPhoneNo=rs.getString("PhoneNo");
                 String strRoleId=rs.getString("RoleId");
                String strDesignation=rs.getString("Designation");
                String strSchoolId=rs.getString("SchoolId");
                 String strSchool=rs.getString("School");
                String strStaffDetailsId=rs.getString("StaffDetailsId");
                String strAcademicYearId=rs.getString("AcademicYearId");
                int role=Integer.parseInt(strRoleId);
                if(role>0){
                object.put("StaffId", strStaffId);
                
                object.put("email", strEmail);
                object.put("name", strName);
                object.put("PhoneNo", strPhoneNo);
                object.put("RoleId", strRoleId);
                object.put("SchoolId", strSchoolId);
                object.put("School", strSchool);
                object.put("Designation", strDesignation);
                object.put("StaffDetailsId", strStaffDetailsId);
                object.put("AcademicYearId", strAcademicYearId);
                object.put("Status", "Success");
                object.put("Message", "User verified  successfully");
                }
                else {
                     outObject.put("Status", "Failure");
               outObject.put("Message", "invalid userid or password");
               //array.put(object);
              outObject.put("result", array);
                }
                
                array.put(object);
                
                outObject.put("result", array);
           }else{
               outObject.put("Status", "Failure");
               outObject.put("Message", "invalid userid or password");
               //array.put(object);
              outObject.put("result", array);
           }
           return outObject.toString();
        } catch (Exception e) {
            String exce = e.toString();
            String exc = "{\"Status\":\"Exception\" ,  \"Message\":\""+exce+"\"}";
            return exc;    
        }finally{
            closeDBConnection();
        } 
    }
    public String addUserChild()
    {
        try {
            String strUserId = userData.getString("UserId");
            String strSchoolName=userData.getString("schoolName");
            String strClass = userData.getString("class");
            String strSection = userData.getString("section");
            String strRollNo= userData.getString("rollNo");
            int rollNo=Integer.parseInt(strRollNo);
       
            String sql="CALL `sp_add_new_student`(?, ?, ?, ?, ?)";
        
             callable=con.getConnection().prepareCall(sql);
             callable.setString(1, strUserId);
             callable.setString(2, strSchoolName);
             callable.setString(3, strClass);
             callable.setString(4, strSection);
             callable.setInt(5, rollNo);
            
             rs=callable.executeQuery();
        
            int effRow=callable.executeUpdate();
            
            if(effRow > 0) {
               outObject.put("Status", "Success");
               outObject.put("Message", "User Child added successfully");
           }else{
               outObject.put("Status", "failure");
               outObject.put("Message", "User could not be added");
           }
        return outObject.toString();
        }catch(Exception e){
        String exce = e.toString();
            String exc = "{\"Status\":\"Exception\" ,  \"Message\":\""+exce+"\"}";
            return exc; 
    }finally{
            closeDBConnection();
        }
   
    }
    
    public String getPassword() {
   
          try
           {
            JSONObject outObject= new JSONObject();
            JSONArray array = new JSONArray();
            String strPhoneNumber = userData.getString("PhoneNumber");
            String sql="CALL sp_and_get_password(?);";
            
            callable=con.getConnection().prepareCall(sql);
            callable.setString(1, strPhoneNumber);
           
            rs=callable.executeQuery();
           
            if(rs.first())
            {
               do
               {
                    JSONObject object = new JSONObject();
                            String strPassword = rs.getString("password");
                            object.put("password", strPassword);
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
    public String changePassword()
    {
    try {
            String strPhoneNumber = userData.getString("PhoneNumber");
            String strPassword = userData.getString("Password");
           String sql= " CALL sp_and_change_password(?,?)";
          
           callable= con.getConnection().prepareCall(sql);
           callable.setString(1, strPhoneNumber);
           callable.setString(2, strPassword);
          
           int effRow=callable.executeUpdate();
;           // "{userID":"basu","password":"basu123}}"
           if(effRow>0) {
               outObject.put("Status", "Success");
               outObject.put("Message", "password changed  successfully");
           }else{
               outObject.put("Status", "fail");
               outObject.put("Message", "invalid userid or password");
           }
           return outObject.toString();
        } catch (Exception e) {
            String exce = e.toString();
            String exc = "{\"Status\":\"Exception\" ,  \"Message\":\""+exce+"\"}";
            return exc;    
        }finally{
            closeDBConnection();
        }
        
        
        
    }
    
    public String checkIsStaffActive()
    {
    try {
            int staffId = Integer.parseInt(userData.getString("StaffId"));
             int app = Integer.parseInt(userData.getString("App"));
           String sql= " CALL sp_check_is_staff_active(?,?)";
          
           callable= con.getConnection().prepareCall(sql);
           callable.setInt(1, staffId);
          
          
           rs=callable.executeQuery();

           int resId =callable.getInt(2);
           
          
           
            
           
           
                   
           
// "{userID":"basu","password":"basu123}}"
           if(resId>0) {
               outObject.put("Status", "Success");
               outObject.put("Message", "Staff Active");
                outObject.put("ResultId", resId);
                String sql2="call sp_get_max_version_code(?)";
                CallableStatement callable2= con.getConnection().prepareCall(sql2);
                   //callable.setString(1, strUserId);
                         callable2.setInt(1,app);
                        
                         ResultSet rs2=callable2.executeQuery();
                if(rs2.first()){
                        
                    
                         JSONObject object=new JSONObject();
                         
                         int versionCode=rs2.getInt("version_code");
                        
                         outObject.put("VersionCode", versionCode);
                 }
                 else{
                        
                         outObject.put("VersionCode", -3);
                         
                      }
                
           }else{
               outObject.put("Status", "fail");
                outObject.put("Message", "Staff  InActive");
                outObject.put("ResultId", resId);
           }
           return outObject.toString();
        } catch (Exception e) {
            String exce = e.toString();
            String exc = "{\"Status\":\"Exception\" ,  \"Message\":\""+exce+"\"}";
            return exc;    
        }finally{
            closeDBConnection();
        }
        
        
        
    }
    
    
    
    
    public String changeStaffPassword(){
        try {
              String strStaffId = userData.getString("StaffId");
                String strCurPassword = userData.getString("CurPassword");
              String strNewPassword = userData.getString("NewPassword");
              String sql= " CALL sp_and_change_staff_password(?,?,?)";
          
              callable= con.getConnection().prepareCall(sql);
              callable.setString(1, strStaffId);
              callable.setString(2, strCurPassword);
              callable.setString(3, strNewPassword);
            //fetchSatffIdPassword(userData, sql);
           int effRow=callable.executeUpdate();
;           // "{userID":"basu","password":"basu123}}"
           if(effRow>0) {
               outObject.put("Status", "Success");
               outObject.put("Messsage", "Password changed  successfully");
           }else{
               outObject.put("Status", "fail");
               outObject.put("Messsage", "invalid userid or password");
           }
           return outObject.toString();
        } catch (Exception e) {
            String exce = e.toString();
            String exc = "{\"Status\":\"Exception\" ,  \"Message\":\""+exce+"\"}";
            return exc;    
        }finally{
            closeDBConnection();
        }
    }
    
    public String changeStaffPhonenumber(){
        try {
              String strStaffId = userData.getString("StaffId");
                String strPassword = userData.getString("Password");
              String strPhoneNumber = userData.getString("PhoneNumber");
              String sql= " CALL sp_and_staff_change_phonenumber(?,?,?)";
          
              callable= con.getConnection().prepareCall(sql);
              callable.setString(1, strStaffId);
              callable.setString(2, strPassword);
              callable.setString(3, strPhoneNumber);
            //fetchSatffIdPassword(userData, sql);
           int effRow=callable.executeUpdate();
;           // "{userID":"basu","password":"basu123}}"
           if(effRow>0) {
               outObject.put("Status", "Success");
               outObject.put("Messsage", "phone number changed  successfully");
           }else{
               outObject.put("Status", "fail");
               outObject.put("Messsage", "invalid userid or password");
           }
           return outObject.toString();
        } catch (Exception e) {
            String exce = e.toString();
            String exc = "{\"Status\":\"Exception\" ,  \"Message\":\""+exce+"\"}";
            return exc;    
        }finally{
            closeDBConnection();
        }
    }
    
    public String staffForgotPassword(){
        try {
              String strStaffId = userData.getString("StaffId");
              int stfId=Integer.parseInt(strStaffId);

              String sql= " CALL sp_and_staff_forgot_password(?)";
          
              callable= con.getConnection().prepareCall(sql);
              callable.setInt(1, stfId);
             
            //fetchSatffIdPassword(userData, sql);
            rs=callable.executeQuery();
          // "{userID":"basu","password":"basu123}}"
           if(rs.first()) {
               do{
               JSONObject object= new JSONObject();
                String strEmail=rs.getString("EmailId");
                object.put("email", strEmail);
                array.put(object);
               }while (rs.next()); 
                  
                outObject.put("Status", "Success");
               outObject.put("Result", array);
           }else{
               outObject.put("Status", "fail");
               outObject.put("Result", array);
           }
           return outObject.toString();
        } catch (Exception e) {
            String exce = e.toString();
            String exc = "{\"Status\":\"Exception\" ,  \"Message\":\""+exce+"\"}";
            return exc;    
        }finally{
            closeDBConnection();
        }
    }
    
    public String getUserId() {
        
       
        try {
            String strUserId = userData.getString("UserId");
           
           String sql= " CALL sp_and_get_userid(?)";
          
           callable= con.getConnection().prepareCall(sql);
           callable.setString(1, strUserId);
           JSONArray array = new JSONArray();
            rs= callable.executeQuery();
          // int effRow=callable.executeUpdate();
;           // "{userID":"basu","password":"basu123}}"
           if(rs.first()) {
                do
               {
                    JSONObject object = new JSONObject();
                            String strUserID = rs.getString("userID");
                            object.put("userID", strUserID);
                           array.put(object);
               }while(rs.next());
               outObject.put("Status", "fail");
               outObject.put("Message", "User is added is already");
               outObject.put("Result", array);
                      
           }else{
               outObject.put("Status", "sucess");
               outObject.put("Message", "user is not added already");
               outObject.put("Result", array);
           }
           return outObject.toString();
        } catch (Exception e) {
            String exce = e.toString();
            String exc = "{\"Status\":\"Exception\" ,  \"Message\":\""+exce+"\"}";
            return exc;
        
    }finally{
            closeDBConnection();
        }
    
    }
   public  String getSchoolName(){
       
        try {
            //String strUserId = userData.getString("UserId");
           
           String sql= " CALL sp_and_get_school_name()";
          
           callable= con.getConnection().prepareCall(sql);
          // callable.setString(1, strUserId);
           JSONArray array = new JSONArray();
            rs= callable.executeQuery();
          // int effRow=callable.executeUpdate();
;           // "{userID":"basu","password":"basu123}}"
           if(rs.first()) {
                do
               {
                    JSONObject object = new JSONObject();
                            String strSchool = rs.getString("School");
                            object.put("School", strSchool);
                           array.put(object);
               }while(rs.next());
               outObject.put("Status", "sucess");
               outObject.put("Message", "Data found");
               outObject.put("Result", array);
                      
           }else{
               outObject.put("Status", "fail");
               outObject.put("Message", "data not founfd ");
               outObject.put("Result", array);
           }
           return outObject.toString();
        } catch (Exception e) {
            String exce = e.toString();
            String exc = "{\"Status\":\"Exception\" ,  \"Message\":\""+exce+"\"}";
            return exc;
        
    }finally{
            closeDBConnection();
        }
   }
   public String getSectionByClass(){
       
        try {
            String strSchoolName = userData.getString("schoolName");
            String strClass=userData.getString("class");
           
           String sql= " CALL sp_and_get_section_by_class(?,?)";
            callable= con.getConnection().prepareCall(sql);
           callable.setString(1, strSchoolName);
            callable.setString(2, strClass);
           JSONArray array = new JSONArray();
           rs= callable.executeQuery();
          // int effRow=callable.executeUpdate();
;           // "{userID":"basu","password":"basu123}}"
           if(rs.first()) {
                do
               {
                    JSONObject object = new JSONObject();
                            String strSection = rs.getString("Section");
                            object.put("Section", strSection);
                           array.put(object);
               }while(rs.next());
               outObject.put("Status", "sucess");
               outObject.put("Message", "Data found");
               outObject.put("Result", array);
                      
           }else{
               outObject.put("Status", "fail");
               outObject.put("Message", "data not founfd ");
               outObject.put("Result", array);
           }
           return outObject.toString();
        } catch (Exception e) {
            String exce = e.toString();
            String exc = "{\"Status\":\"Exception\" ,  \"Message\":\""+exce+"\"}";
            return exc;
        
    }finally{
            closeDBConnection();
        }
   }
   public  String getClassBySchool(){
       
        try {
            String strSchoolName = userData.getString("schoolName");
            
           
           String sql= " CALL sp_and_get_class_by_school(?)";
          
           callable= con.getConnection().prepareCall(sql);
           callable.setString(1, strSchoolName);
         
           JSONArray array = new JSONArray();
           rs= callable.executeQuery();
          // int effRow=callable.executeUpdate();
;           // "{userID":"basu","password":"basu123}}"
           if(rs.first()) {
                do
               {
                    JSONObject object = new JSONObject();
                            String strClass = rs.getString("Class");
                            object.put("Class", strClass);
                           array.put(object);
               }while(rs.next());
               outObject.put("Status", "sucess");
               outObject.put("Message", "Data found");
               outObject.put("Result", array);
                      
           }else{
               outObject.put("Status", "fail");
               outObject.put("Message", "data not founfd ");
               outObject.put("Result", array);
           }
           return outObject.toString();
        } catch (Exception e) {
            String exce = e.toString();
            String exc = "{\"Status\":\"Exception\" ,  \"Message\":\""+exce+"\"}";
            return exc;
        
    }finally{
            closeDBConnection();
        }
   }
   public String getRollNoByClass()
   {
      
        try {
            String strSchoolName = userData.getString("schoolName");
            String strClass = userData.getString("class");
           String strSection = userData.getString("section");
           String sql= " CALL 	sp_and_get_rollno_by_class(?,?,?)";
           
           callable= con.getConnection().prepareCall(sql);
           callable.setString(1, strSchoolName);
           callable.setString(2, strClass);
           callable.setString(3, strSection);
           JSONArray array = new JSONArray();
            rs= callable.executeQuery();
          // int effRow=callable.executeUpdate();
;           // "{userID":"basu","password":"basu123}}"
           if(rs.first()) {
                do
               {
                    JSONObject object = new JSONObject();
                            String strStudent_name = rs.getString("student_name");
                            String strRollNo=rs.getString("RollNo");
                                    
                            object.put("student_name", strStudent_name);
                            object.put("RollNo",strRollNo);
                            array.put(object);
               }while(rs.next());
               outObject.put("Status", "sucess");
               outObject.put("Message", "Data found");
               outObject.put("Result", array);
                      
           }else{
               outObject.put("Status", "fail");
               outObject.put("Message", "data not founfd ");
               outObject.put("Result", array);
           }
           return outObject.toString();
        } catch (Exception e) {
            String exce = e.toString();
            String exc = "{\"Status\":\"Exception\" ,  \"Message\":\""+exce+"\"}";
            return exc;
        
    } finally{
            closeDBConnection();
        }
   }
   public String getStudentByRoll(){
       
        try {
            String strSchoolName = userData.getString("schoolName");
            String strClass = userData.getString("class");
           String strSection = userData.getString("section");
           String strRollNo=userData.getString("rollNo");
           int rollNo=Integer.parseInt(strRollNo);
           String sql= " CALL sp_and_get_student_name_by_roll(?,?,?,?)";
           
           callable= con.getConnection().prepareCall(sql);
           callable.setString(1, strSchoolName);
           callable.setString(2, strClass);
           callable.setString(3, strSection);
           callable.setInt(4, rollNo);
           JSONArray array = new JSONArray();
            rs= callable.executeQuery();
          // int effRow=callable.executeUpdate();
;           // "{userID":"basu","password":"basu123}}"
           if(rs.first()) {
                do
               {
                    JSONObject object = new JSONObject();
                            String strStudentname = rs.getString("studentname");
                            String strRoll = rs.getString("RollNo");
                            
                            object.put("Studentname", strStudentname);
                            object.put("RollNo", strRoll);
                            
                           array.put(object);
               }while(rs.next());
               outObject.put("Status", "sucess");
               outObject.put("Message", "Data found");
               outObject.put("Result", array);
                      
           }else{
               outObject.put("Status", "fail");
               outObject.put("Message", "data not founfd ");
               outObject.put("Result", array);
           }
           return outObject.toString();
        } catch (Exception e) {
            String exce = e.toString();
            String exc = "{\"Status\":\"Exception\" ,  \"Message\":\""+exce+"\"}";
            return exc;
    } finally{
            closeDBConnection();
        }
   }
    
   public String getStudentListByParent()
    {
        
        try{
       
            String strPhoneNumber=userData.getString("phoneNumber");
            int acdId=Integer.parseInt(userData.getString("AcademicYearId"));
            String sql = " CALL sp_and_get_student_list_by_parent(?,?);";
            callable = con.getConnection().prepareCall(sql);
            
            callable.setString(1,strPhoneNumber);
            callable.setInt(2,acdId);
             rs = callable.executeQuery();
            
            if(rs.first())
           
            {
               do
               {
                    JSONObject object = new JSONObject();
                          
                           String strStudentId= rs.getString("studentId");
                            String strstudentname = rs.getString("studentname");
                            String strclass = rs.getString("class");
                            String strClassId = rs.getString("ClassId");
                            String strRollNo= rs.getString("RollNo");
                            String strSchool = rs.getString("School");
                            String strSchoolId= rs.getString("SchoolId");
                            String strAcademicYearId= rs.getString("AcademicYearId");
                            
                            object.put("studentname", strstudentname);
                            object.put("RollNo", strRollNo);
                            object.put("studentId", strStudentId);
                            object.put("class", strclass);
                            object.put("School", strSchool);
                            object.put("SchoolId", strSchoolId);
                            object.put("ClassId", strClassId);
                            object.put("AcademicYearId", strAcademicYearId);
                           
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
   public String submitStaffUserToken(){
    
       try {
           // String strUserId = userData.getString("UserId");
            int staffId = Integer.parseInt(userData.getString("StaffId"));
            String strUserToken = userData.getString("UserToken");
            String strDeviceId = userData.getString("DeviceId");
            
           String sql="call sp_update_staff_user_token(?,?,?,?)";
           
                   callable= con.getConnection().prepareCall(sql);
                   //callable.setString(1, strUserId);
                   callable.setInt(1, staffId);
                   callable.setString(2, strDeviceId);
                   callable.setString(3, strUserToken);
                   callable.executeUpdate();
                  //int resultId= Integer.parseInt(callable.getString("resultid_out_param"));
                   int resultId= callable.getInt(4);
            
            if(resultId==-2){
                 //String strUsername = rs.getString("userID");
                 outObject.put("Status", "Success");
                 outObject.put("resultId",resultId);
               outObject.put("Message", "User token updated sucessfully");
            }else{
                outObject.put("Status", "Fail");
                 outObject.put("resultId",resultId);
               outObject.put("Message", "User token was not updated sucessfully");
            }
               return outObject.toString();
            
        } catch (Exception e) {
            String exce = e.toString();
            String exc = "{\"Status\":\"Exception\" ,  \"Message\":\""+exce+"\"}";
            return exc;
            
        }finally{
            closeDBConnection();
        }
       
   }
public String submitParentUserToken(){
    
       try {
           // String strUserId = userData.getString("UserId");
            String strPhoneNumber = userData.getString("PhoneNumber");
            String strUserToken = userData.getString("UserToken");
            String strDeviceId = userData.getString("DeviceId");
            
           String sql="call sp_update_student_user_token(?,?,?,?)";
           
                   callable= con.getConnection().prepareCall(sql);
                   //callable.setString(1, strUserId);
                   
                   callable.setString(1, strUserToken);
                    callable.setString(2, strDeviceId);
                    callable.setString(3,strPhoneNumber );
                   callable.executeUpdate();
                  //int resultId= Integer.parseInt(callable.getString("resultid_out_param"));
                   int resultId= callable.getInt(4);
            
            if(resultId==-2){
                 //String strUsername = rs.getString("userID");
                 outObject.put("Status", "Success");
                 outObject.put("resultId",resultId);
               outObject.put("Message", "User token updated sucessfully");
            }else{
                outObject.put("Status", "Fail");
                 outObject.put("resultId",resultId);
               outObject.put("Message", "User token was not updated sucessfully");
            }
               return outObject.toString();
            
        } catch (Exception e) {
            String exce = e.toString();
            String exc = "{\"Status\":\"Exception\" ,  \"Message\":\""+exce+"\"}";
            return exc;
            
        }finally{
            closeDBConnection();
        }
       
   }
public void getStudentUserTokenAndDeviceId(){
    try{
        
        if(rs.first()){
        do{
            /*String strUserToken=rs.getString("user_device_token");
            String strUserDeviceId=rs.getString("user_device_id");
            String strUserDeviceId=rs.getString("user_device_id");
            String strUserDeviceId=rs.getString("user_device_id"); */
        }while(rs.next());
    }
        
        
    }catch(Exception e){
        
    }
    
}
public String getParentUserTokenSchoolwise(){
    try {
           // String strUserId = userData.getString("UserId");
            int schoolId = Integer.parseInt(userData.getString("SchoolId"));
            
            
           String sql="call sp_get_parent_usertoken_details_schoolwise(?)";
           
                   callable= con.getConnection().prepareCall(sql);
                   //callable.setString(1, strUserId);
                   callable.setInt(1,schoolId );
                   
                   rs=callable.executeQuery();
                 
                if(rs.first()){
                      fetchParentUSerTokenDetails();
                      if(array.length()!=0){
                          printSuccessStatus(array);
                  }else{
                          printFaliureStatus(array);
                      }
            
            }else{
                printFaliureStatus(array);
            }
               return outObject.toString();    
           } catch (Exception e) {
            String exce = e.toString();
            String exc = "{\"Status\":\"Exception\" ,  \"Message\":\""+exce+"\"}";
            return exc;
            
        }finally{
            closeDBConnection();
        }
}
   
public String getParentUserTokenClasswise(){
    try {
           // String strUserId = userData.getString("UserId");
            int classId =   Integer.parseInt(userData.getString("ClassId"));
           
            
           String sql="call sp_get_student_usertoken_details_classwise(?)";
           
                   callable= con.getConnection().prepareCall(sql);
                   //callable.setString(1, strUserId);
                   callable.setInt(1,classId );
                   
                  
                   rs= callable.executeQuery();
                 
                   if(rs.first()){
                       fetchParentUSerTokenDetails();
                       if(array.length()!=0){
                           printSuccessStatus(array);
                       }else{
                           printFaliureStatus(array);
                       }
                    }else{
                       printFaliureStatus(array);
            }
               return outObject.toString();
            
        } catch (Exception e) {
            String exce = e.toString();
            String exc = "{\"Status\":\"Exception\" ,  \"Message\":\""+exce+"\"}";
            return exc;
            
        }finally{
            closeDBConnection();
        }
}
public String getParentUserTokenStudentWise(){
    try {
           int studentId =   Integer.parseInt(userData.getString("StudentId"));
           
            
           String sql="call sp_get_parent_usertoken_details_studentwise(?)";
           
                   callable= con.getConnection().prepareCall(sql);
                   //callable.setString(1, strUserId);
                   callable.setInt(1,studentId );
                   
                  
                   rs= callable.executeQuery();
                 
                   if(rs.first()){
                       fetchParentUSerTokenDetails();
                       if(array.length()!=0){
                           printSuccessStatus(array);
                       }else{
                           printFaliureStatus(array);
                       }
                    }else{
                       printFaliureStatus(array);
            }
               return outObject.toString();
            
        } catch (Exception e) {
            String exce = e.toString();
            String exc = "{\"Status\":\"Exception\" ,  \"Message\":\""+exce+"\"}";
            return exc;
            
        }finally{
            closeDBConnection();
        }
}

public void fetchParentUSerTokenDetails(){
    
    try{
        
        do{
                JSONObject object=new JSONObject();
                
                String strSchoolId=rs.getString("SchoolId");
                String strSchoolName=rs.getString("SchoolName");
                String strClass=rs.getString("Class");
                String strRollNo=rs.getString("RollNo");
                String strStudentDetailsId=rs.getString("StudentDetailsId");
                String strStudentName=rs.getString("StudentName");
                String strParent_user_device_id=rs.getString("Parent_user_device_id");
                String strParent_user_device_token=rs.getString("Parent_user_device_token");
        
                object.put("SchoolId",strSchoolId);
                object.put("SchoolName",strSchoolName);
                object.put("Class",strClass);
                object.put("RollNo",strRollNo);
                object.put("StudentDetailsId",strStudentDetailsId);
                object.put("StudentName",strStudentName);
                object.put("ParentUserDeviceId",strParent_user_device_id);
                object.put("ParentUserDeviceToken",strParent_user_device_token);
            
                array.put(object);
    
        }while(rs.next());
    
    }catch(Exception e){
            e.printStackTrace();
        }
    
}
public void fetchStaffUserTokenDetails(){
    try{
        
        do{
                JSONObject object=new JSONObject();
                String strSchoolId=rs.getString("SchoolId");
                String strSchoolName=rs.getString("SchoolName");
                String strStaffDetailsId=rs.getString("StaffDetailsId");
                String strStaffId=rs.getString("StaffId");
                String strStaffName=rs.getString("StaffName");
                String strStaff_DeviceId=rs.getString("Staff_DeviceId");
                String strStaff_UserToken=rs.getString("Staff_UserToken");
        
                object.put("SchoolId",strSchoolId);
                object.put("SchoolName",strSchoolName);
                object.put("StaffDetailsId",strStaffDetailsId);
                object.put("StaffId",strStaffId);
                object.put("StaffName",strStaffName);
                object.put("StaffDeviceId",strStaff_DeviceId);
                object.put("StaffUserToken",strStaff_UserToken);
            
                array.put(object);
    
        }while(rs.next());
    
    }catch(Exception e){
            e.printStackTrace();
        }
}
public String getStaffUSertokenSchoolWise(){
    try {
           // String strUserId = userData.getString("UserId");
            int schoolId = Integer.parseInt(userData.getString("SchoolId"));
            
            
           String sql="call sp_get_staff_usertoken_details_schoolwise(?)";
           
                   callable= con.getConnection().prepareCall(sql);
                   //callable.setString(1, strUserId);
                   callable.setInt(1,schoolId);
                  
                  
                  rs= callable.executeQuery();
                  //int resultId= Integer.parseInt(callable.getString("resultid_out_param"));
                  if(rs.first()){
                      fetchStaffUserTokenDetails();
                      if(array.length()!=0){
                          printSuccessStatus(array);
                  }else{
                          printFaliureStatus(array);
                      }
            
            }else{
                printFaliureStatus(array);
            }
               return outObject.toString();
            
        } catch (Exception e) {
            String exce = e.toString();
            String exc = "{\"Status\":\"Exception\" ,  \"Message\":\""+exce+"\"}";
            return exc;
            
        }finally{
            closeDBConnection();
        }
}

public String getUserIdPasswordByStudentId(){
    
    try {
           // String strUserId = userData.getString("UserId");
            int studId = Integer.parseInt(userData.getString("StudentId"));
           
            
           String sql="call sp_get_student_credential_by_studentid(?)";
           
                   callable= con.getConnection().prepareCall(sql);
                   //callable.setString(1, strUserId);
                   callable.setInt(1,studId );
                   rs=callable.executeQuery();
                 if(rs.first()){
                      /*do{
                            JSONObject object=new JSONObject();
                            String strUserDetailsId=rs.getString("user_details_id");
                            String strStudentHistoryId=rs.getString("Student_History_Id");
                            String strStudentDetailsId=rs.getString("StudentDetailsId");
                            String strClassSection=rs.getString("ClassSection");
                            String strStudentName=rs.getString("StudentName");
                            String strMobileNumber=rs.getString("mobile_number");
                            String strPassword=rs.getString("password");
                            String strRollNo=rs.getString("RollNo");
        
                            object.put("UserDetailsId",strUserDetailsId);
                            object.put("StudentHistoryId",strStudentHistoryId);
                            object.put("StudentDetailsId",strStudentDetailsId);
                            object.put("ClassSection",strClassSection);
                            object.put("StudentName",strStudentName);
                            object.put("MobileNumber",strMobileNumber);
                            object.put("Password",strPassword);
                            object.put("RollNo",strRollNo);
            
                array.put(object);
    
        }while(rs.next()); */
                      fetchStudentCredentials();
                      if(array.length()!=0){
                          printSuccessStatus(array);
                  }else{
                          printFaliureStatus(array);
                      }
            
            }else{
                printFaliureStatus(array);
            }
               return outObject.toString();
            
        } catch (Exception e) {
            String exce = e.toString();
            String exc = "{\"Status\":\"Exception\" ,  \"Message\":\""+exce+"\"}";
            return exc;
            
        }finally{
            closeDBConnection();
        }
    
    
}

public String updateStudentCredentials(){
    try {
           // String strUserId = userData.getString("UserId");
            String strPassword = userData.getString("Password");
            
            String strPhoneNumber = userData.getString("PhoneNumber");
            int studId = Integer.parseInt(userData.getString("StudentId"));
         
          String sql="CALL sp_update_mobile_number_and_pswrd_studentwise(?,?,?,?)";
           
                   callable= con.getConnection().prepareCall(sql);
                   //callable.setString(1, strUserId);
                   callable.setString(1, strPhoneNumber);
                  /* if(strPassword.equalsIgnoreCase("-9999")){
                       callable.setString(2, null);
                   }else{*/
                   callable.setString(2, strPassword);
                   
                   callable.setInt(3,studId );
                   callable.executeQuery();
                  //int resultId= Integer.parseInt(callable.getString("resultid_out_param"));
                   int resultId= callable.getInt(4);
            
            if(resultId==-2){
                 //String strUsername = rs.getString("userID");
                
                  outObject.put("Status", "Success");
               outObject.put("ResultId", resultId);
               outObject.put("Message", "Credentials Updated successfully");
                
            }
           //String strQuery= "INSERT INTO tbl_user_details (userID, password, email, mobile_number) VALUES ('"+strUserId+"', '"+strPassword+"', '"+strEmail+"', '"+strMobileNumber+"')";
          /*String sql="CALL sp_user_register(?, ?, ?, ?)";
           
                   callable= con.getConnection().prepareCall(sql);
                   callable.setString(1, strUserId);
                   callable.setString(2, strPassword);
                   callable.setString(3, strEmail);
                   callable.setString(4, strMobileNumber);
                   int acKno= callable.executeUpdate();*/
            else if(resultId==-1) {
               
                 outObject.put("Status", "unsucessfull");
                 outObject.put("ResultId", resultId);
               outObject.put("Message", " Sorry !! no Changes in Credentials");
           }else{
               outObject.put("Status", "Faliure ");
               outObject.put("Message", "User could not be Updated please try again");
               outObject.put("ResultId", resultId);
           }
           
            
           
          
          return outObject.toString();
        } catch (Exception e) {
            String exce = e.toString();
            String exc = "{\"Status\":\"Exception\" ,  \"Message\":\""+exce+"\"}";
            return exc;
            
        }finally{
            closeDBConnection();
        }
}

public void fetchStudentCredentials(){
    try{
               do{
                    JSONObject object = new JSONObject();
                          
                           
                            String strstudentname = rs.getString("StudentName");
                            String strclass = rs.getString("Class");
                           
                            String strRollNo= rs.getString("RollNo");
                            String strPhone = rs.getString("PhoneNumber");
                            String strPassword= rs.getString("Password");
                            String strStudentId=rs.getString("StudentId");
                            
                            object.put("StudentName", strstudentname);
                            object.put("RollNo", strRollNo);
                            object.put("Class", strclass);
                            object.put("PhoneNumber", strPhone);
                            object.put("Password", strPassword);
                           
                            object.put("StudentId", strStudentId);
                           
                            array.put(object);
               }while(rs.next());
    }catch(Exception e){
        String exce = e.toString();
            String exc = "{\"Status\":\"Exception\" ,  \"Message\":\""+exce+"\" ,"
                    + " \"Result\":[]}";
             
    }
}
public String getUserIdPasswordByPhoneNumber(){
try{
       if(userData.length()==2){
           printUpdateStatus(array);
       }else{
            String strPhoneNumber=userData.getString("phoneNumber");
            int acdId=Integer.parseInt(userData.getString("AcademicYearId"));
             int schId=Integer.parseInt(userData.getString("SchoolId"));
            String sql = " CALL sp_get_student_credential_by_phonenumber(?,?,?);";
            callable = con.getConnection().prepareCall(sql);
            
            callable.setString(1,strPhoneNumber);
            callable.setInt(2,schId);
            callable.setInt(3,acdId);
             rs = callable.executeQuery();
            
            if(rs.first())
           
            {
               
               fetchStudentCredentials();
               if(array.length()!=0){
                          printSuccessStatus(array);
                  }else{
                          printFaliureStatus(array);
                      }
           }
           
          else {
                        printFaliureStatus(array);
                }
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
public String getSttaffUserTokenStaffwise(){
    try {
           // String strUserId = userData.getString("UserId");
            int staffId = Integer.parseInt(userData.getString("StaffId"));
           
            
           String sql="call sp_get_staff_usertoken_details_staffwise(?)";
           
                   callable= con.getConnection().prepareCall(sql);
                   //callable.setString(1, strUserId);
                   callable.setInt(1,staffId );
                   rs=callable.executeQuery();
                 if(rs.first()){
                      fetchStaffUserTokenDetails();
                      if(array.length()!=0){
                          printSuccessStatus(array);
                  }else{
                          printFaliureStatus(array);
                      }
            
            }else{
                printFaliureStatus(array);
            }
               return outObject.toString();
            
        } catch (Exception e) {
            String exce = e.toString();
            String exc = "{\"Status\":\"Exception\" ,  \"Message\":\""+exce+"\"}";
            return exc;
            
        }finally{
            closeDBConnection();
        }
}
public int checkPhoneNumberIsInDatabase(String strPhoneNumber ){
    try {
           // String strUserId = userData.getString("UserId");
            //String strPhoneNumber = userData.getString("PhoneNumber");
           
            
           String sql="call sp_check_parent_phone_number(?,?)";
           
                   callable= con.getConnection().prepareCall(sql);
                   //callable.setString(1, strUserId);
                   callable.setString(1,strPhoneNumber );
                   rs=callable.executeQuery();
                 int resId= callable.getInt(2);
                 
                 
                 if(resId>0){
                         outObject.put("Status", "Success");
                         outObject.put("Message", "Phone number is in Database");
                         outObject.put("ResultId", resId);
                         outObject.put("Result", array);
                         
                  }else{
                         outObject.put("Status", "Sorry");
                         outObject.put("Message", "Phone number is not in Database");
                         outObject.put("ResultId", resId);
                         outObject.put("Result", array);
                      }
                 
               return resId;
            
        } catch (Exception e) {
            String exce = e.toString();
            String exc = "{\"Status\":\"Exception\" ,  \"Message\":\""+exce+"\"}";
            return -3;
            
        }finally{
            closeDBConnection();
        } 
}

public String checkRecentAppVersion(){
    
    try {
           // String strUserId = userData.getString("UserId");
            int app = Integer.parseInt(userData.getString("App"));
           
            
           String sql="call sp_get_max_version_code(?)";
           
                   callable= con.getConnection().prepareCall(sql);
                   //callable.setString(1, strUserId);
                   callable.setInt(1,app);
                   rs=callable.executeQuery();
                 
                 if(rs.first()){
                     
                         JSONObject object=new JSONObject();
                         int versionCode=rs.getInt("version_code");
                         outObject.put("Status", "Success");
                         outObject.put("Message", "Verion Number");
                         outObject.put("VersionCode", versionCode);
                        
                    
                     
                 }
                 else{
                         outObject.put("Status", "Sorry");
                         outObject.put("Message", "we couldnt find the version");
                         outObject.put("VersionCode", -3);
                         
                      }
                 
                  
                 
                   /*if(rs.first()){
                       do{
                JSONObject object=new JSONObject();
                String strSchoolId=rs.getString("SchoolId");
                String strSchoolName=rs.getString("SchoolName");
                String strStaffDetailsId=rs.getString("StaffDetailsId");
                String strStaffId=rs.getString("StaffId");
                String strStaffName=rs.getString("StaffName");
                String strStaff_DeviceId=rs.getString("Staff_DeviceId");
                String strStaff_UserToken=rs.getString("Staff_UserToken");
        
                object.put("SchoolId",strSchoolId);
                object.put("SchoolName",strSchoolName);
                object.put("StaffDetailsId",strStaffDetailsId);
                object.put("StaffId",strStaffId);
                object.put("StaffName",strStaffName);
                object.put("StaffDeviceId",strStaff_DeviceId);
                object.put("StaffUserToken",strStaff_UserToken);
            
                array.put(object);
    
        }while(rs.next());*/
                      
            
            
               
            return outObject.toString();
        } catch (Exception e) {
            String exce = e.toString();
            String exc = "{\"Status\":\"Exception\" ,  \"Message\":\""+exce+"\"}";
            return exc;
            
        }finally{
            closeDBConnection();
        }
    
    
}

}
