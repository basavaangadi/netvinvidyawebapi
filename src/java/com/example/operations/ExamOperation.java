/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
 
package com.example.operations;

import com.example.db.DBConnect;
import com.example.db.FCM;
import com.example.db.StringUtil;
import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.json.JSONArray;
import org.json.JSONObject;
import java.text.DecimalFormat;
import java.math.RoundingMode;

/**
 *
 * @author basava
 */
public class ExamOperation {
    
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

    
    JSONObject examData;
    JSONArray array= new JSONArray();
    JSONObject outObject=new JSONObject();
    String strStudentId,strExam;
    int studId,resId=0,exmId,failFlag=0,absCount=0,schSubCount=0;
    String strTotalGrade="",strUserToken="",strTotalResultStatus="";;
    
    float total=0.0f,maxTotal=0.0f;
    Double dblTotal=0.0,dblMaxTotal=0.0;
    ResultSet rs;
    int effRow=0;
     Date scdulSetDateSQL = null;
     java.sql.Date sqlSdulSetDate = null;
    
    Date scdulOnDateSQL = null;
    java.sql.Date sqlScdulOnDate = null;
    FCM fcm= new FCM();
    DBConnect con=new  DBConnect();
    CallableStatement callable=null;
    // common data int variables 
    int subId,setBy,clsid,exmid;
    
        public void fetchStudentWithExamData(JSONObject examData,String sql) {
        try{
        
         studId= Integer.parseInt(examData.getString("StudentId"));
       
         exmId=Integer.parseInt(examData.getString("ExamId"));
        
        callable = con.getConnection().prepareCall(sql);
        callable.setInt(1,studId);
        callable.setInt(2,exmId);
        
       rs= callable.executeQuery();
        
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
     public void fetchStudentData(JSONObject examData,String sql){
         try{
        studId= Integer.parseInt(examData.getString("StudentId"));

          callable= con.getConnection().prepareCall(sql);
         callable.setInt(1,studId);
         rs= callable.executeQuery();
         }catch(Exception e){
             e.printStackTrace();
         }
     }
     public void fetchStaffExamData(JSONObject examData,String sql){
         try{
        String strClassId=examData.getString("SchoolId");
        
        int clsId= Integer.parseInt(strClassId);
        
        callable= con.getConnection().prepareCall(sql);
        callable.setInt(1,clsId);
      
        rs= callable.executeQuery();
        
         }catch(Exception e){
             e.printStackTrace();
         }
             
     }
     
     public void fetchSchoolIdForExam(JSONObject examData,String sql){
         try{
        String strClassId=examData.getString("SchoolId");
        
        int schId= Integer.parseInt(strClassId);
        
        callable= con.getConnection().prepareCall(sql);
        callable.setInt(1,schId);
      
        rs= callable.executeQuery();
        
         }catch(Exception e){
             e.printStackTrace();
         }
             
     }
    public void  fetchStaffExamResultData(JSONObject examData,String sql){
         try{
       int classid= Integer.parseInt(examData.getString("ClassId"));
        
        callable= con.getConnection().prepareCall(sql);
        callable.setInt(1,classid);
      
        rs= callable.executeQuery();
        
         }catch(Exception e){
             e.printStackTrace();
         }
    }
    public void  fetchClassIdForExam(JSONObject examData,String sql){
         try{
       int classid= Integer.parseInt(examData.getString("ClassId"));
        
        callable= con.getConnection().prepareCall(sql);
        callable.setInt(1,classid);
      
        rs= callable.executeQuery();
        
         }catch(Exception e){
             e.printStackTrace();
         }
    }
    
    public void fetchStaffIdAndAcedemicYearIdForExam(JSONObject examData,String sql){
         try{
         int staffId=Integer.parseInt(examData.getString("StaffId"));
        
        int acdmicId=Integer.parseInt(examData.getString("AcademicYearId"));
       
        
        callable = con.getConnection().prepareCall(sql);
        callable.setInt(1,staffId);
      
        callable.setInt(2,acdmicId);
        
        rs = callable.executeQuery();
        } catch(Exception e){
         e.printStackTrace();
        }
     }
    public void fetchSchoolIdAndAcedemicYearIdForExam(JSONObject examData,String sql){
         try{
         int schId=Integer.parseInt(examData.getString("SchoolId"));
        
        int acdmicId=Integer.parseInt(examData.getString("AcademicYearId"));
       
        
        callable = con.getConnection().prepareCall(sql);
        callable.setInt(1,schId);
      
        callable.setInt(2,acdmicId);
        
        rs = callable.executeQuery();
        } catch(Exception e){
         e.printStackTrace();
        }
     }
    
     public void fetchStaffwiseExamData(JSONObject examData,String sql){
         try{
         int staffId=Integer.parseInt(examData.getString("StaffId"));
        int examId=Integer.parseInt(examData.getString("ExamId"));
        int acdmicId=Integer.parseInt(examData.getString("AcademicYearId"));
       
        
        callable = con.getConnection().prepareCall(sql);
        callable.setInt(1,staffId);
        callable.setInt(2,examId);
        callable.setInt(3,acdmicId);
        
        rs = callable.executeQuery();
        } catch(Exception e){
         e.printStackTrace();
        }
     }
      public void fetchStaffExamDatByExam(JSONObject examData,String sql){
         try{
        int clsId= Integer.parseInt(examData.getString("ClassId"));
        int exmId= Integer.parseInt(examData.getString("ExamId"));
        
        callable= con.getConnection().prepareCall(sql);
        callable.setInt(1,clsId);

        callable.setInt(2,exmId);
        rs= callable.executeQuery();
        
         }catch(Exception e){
             e.printStackTrace();
         }
             
     }
     
// SchoolId, AcademicYearId, SetBy, ClassId, SyllabusSetDate, ExamId,SubjectId, 
// StudentId, MarksObtained, Grade, Date, Syllabus
    
    public ExamOperation(JSONObject examData) {
        this.examData = examData;    
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
    
    public String verifyDataInsertion(){
        try{
         if(resId>0)
            {
                 outObject.put("Status", "Success");
                 outObject.put("Message", "inserted sucessfully");
            }else if(resId==-1){
                 outObject.put("Status", "fail");
                 outObject.put("Message", " record already inserted ");
            } else {
                outObject.put("Status", "fail");
                 outObject.put("Message", " record was not inserted");
            }
         return outObject.toString();
        }catch(Exception e){
            String exce = e.toString();
            String exc = "{\"Status\":\"Exception\" ,  \"Message\":\""+exce+"\"}";
            return exc; 
        }
    }
    
    public void fetchCommonExamData(){
        try{
             String strSubjectId = examData.getString("SubjectId");
           subId = Integer.parseInt(strSubjectId);
             String strSetBy = examData.getString("SetBy");
             setBy = Integer.parseInt(strSetBy);
              String classId = examData.getString("ClassId");
            clsid = Integer.parseInt(classId);
             String scdulSetDate = examData.getString("SetDate");
          
           
           if(!(scdulSetDate.equals("")))
             {
                 Date noticeDateFrom = new SimpleDateFormat("dd-MM-yyyy HH:mm").parse(scdulSetDate);
               
                SimpleDateFormat dateFormatToServer = new SimpleDateFormat("yyyy-MM-dd HH:mm");
                String strnoteSentDateInFormated = dateFormatToServer.format(noticeDateFrom);
                
                scdulSetDateSQL = dateFormatToServer.parse(strnoteSentDateInFormated);
               
                //SQL Date For Stored Procuder Init
                sqlSdulSetDate = new java.sql.Date(scdulSetDateSQL.getTime());
             }
            
            String scdulOnDate = examData.getString("ExamOnDate"); 
           //  java.util.Date ntcDate= formatterentrdt.parse(noteOnDate);
                  
           
           
            if(!(scdulOnDate.equals("")))
             {
                 Date noticeDateTo = new SimpleDateFormat("dd-MM-yyyy HH:mm").parse(scdulOnDate);
               
                SimpleDateFormat dateFormatToServer = new SimpleDateFormat("yyyy-MM-dd HH:mm");
                String strNtcToDateInFormated = dateFormatToServer.format(noticeDateTo);
                
                scdulOnDateSQL = dateFormatToServer.parse(strNtcToDateInFormated);
               
                //SQL Date For Stored Procuder Init
                sqlScdulOnDate = new java.sql.Date(scdulOnDateSQL.getTime());
             }
           
            String strExamId = examData.getString("ExamId");
              exmid = Integer.parseInt(strExamId);
             
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    public String ExamSyllabusSet(){
        try {
            JSONObject outObject = new JSONObject();
            String schoolId = examData.getString("SchoolId");
            int sclId = Integer.parseInt(schoolId);
            String setBy = examData.getString("SetBy");
             int stby = Integer.parseInt(setBy);
             String classId = examData.getString("ClassId");
            int clsid = Integer.parseInt(classId);
             String ExamId = examData.getString("ExamId");
            int exmid = Integer.parseInt(ExamId);
            String subjectId = examData.getString("SubjectId");
             int subid = Integer.parseInt(subjectId);
            String syllabus = examData.getString("Syllabus");
            
             String syllabusSetDate = examData.getString("SyllabusSetDate"); 
            SimpleDateFormat formatterentrdt = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            java.util.Date setDate= formatterentrdt.parse(syllabusSetDate);
            String academicYearId = examData.getString("AcademicYearId");
             int acmdid = Integer.parseInt(academicYearId);
             String sql = "CALL `ExamSyllabusSet`(?, ?, ?, ?, ?, ?, ?, ?)";
            
            callable.setInt(1,sclId);
            callable.setInt(2,stby);
            callable.setInt(3, clsid);
            callable.setInt(4, exmid);
            callable.setInt(5, subid); 
            callable.setString(6, syllabus);
             callable.setDate(7,new java.sql.Date(setDate.getTime()));
             callable.setInt(8, acmdid);
                        
            int effRow=callable.executeUpdate();
            if(effRow>0)
            {
                 outObject.put("Status", "Success");
                 outObject.put("Message", "Inserted sucessfully");
            }else{
                 outObject.put("Status", "fail");
                 outObject.put("Message", " not inserted ");
            }
            return outObject.toString();
        } catch (Exception e) {
            
         String exce = e.toString();
            String exc = "{\"Status\":\"Exception\" ,  \"Message\":\""+exce+"\"}";
            return exc; 
        }
        finally{
            closeDBConnection();
        }
  
    }
     public void fetchExamResultDataLater(){
        try{
            do
               {
                    JSONObject object = new JSONObject();
                    String strRollNo= rs.getString("RollNo");
                    String strExam= rs.getString("Exam");
                    String strClass= rs.getString("Class");
                     String strSubjectId = rs.getString("SubjectId");
                    String strClassSection=rs.getString("ClassSection");
                    String strScholastics=rs.getString("Scholastics");
                    String strSubject = rs.getString("Subject");
                    String strMarksObtained = rs.getString("Obtained");
                    String strStudentname = rs.getString("StudentName");
                    String strMaxMarks=rs.getString("MaxMarks");
                    String strMinMarks=rs.getString("MinMarks");
                    if(!strScholastics.equalsIgnoreCase("0")){
                    total+=Float.parseFloat(strMarksObtained);
                    maxTotal+=Float.parseFloat(strMaxMarks);
                    int marks=Integer.parseInt(strMarksObtained);
                    int minMarks=Integer.parseInt(strMinMarks);
                    if(marks<minMarks)
                        {
                        failFlag=1;
                         }
                     }else{
                        if(strMarksObtained.equalsIgnoreCase("-23232")){
                            strMarksObtained="A+";
                        }else if(strMarksObtained.equalsIgnoreCase("-33333")){
                            strMarksObtained="A";
                        }else if(strMarksObtained.equalsIgnoreCase("-45454")){
                            strMarksObtained="B+";
                        }else if(strMarksObtained.equalsIgnoreCase("-55555")){
                            strMarksObtained="B";
                        }else if(strMarksObtained.equalsIgnoreCase("-67676")){
                            strMarksObtained="C+";
                        }else if(strMarksObtained.equalsIgnoreCase("-66666")){
                            strMarksObtained="C";
                        }
                    }    
                    object.put("Exam", strExam);
                    object.put("Subject", strSubject);
                    object.put("MarksObtained", strMarksObtained);
                    object.put("studentname", strStudentname);
                    object.put("MaxMarks", strMaxMarks);
                    object.put("MinMarks", strMinMarks);
                    object.put("class", strClassSection);
                    object.put("RollNo", strRollNo);
                    object.put("SubjectId", strSubjectId);        
                            array.put(object);
               }while(rs.next());
    }catch(Exception e){
        e.printStackTrace();
    }
    }
     
     public void fetchmarksWithoutTotal(){
            try{
            do
               {
                    JSONObject object = new JSONObject();
                    String strRollNo= rs.getString("RollNo");
                    String strExam= rs.getString("Exam");
                    String strClass= rs.getString("Class");
                    String strSubjectId = rs.getString("SubjectId");
                    String strClassSection=rs.getString("ClassSection");
                    String strScholastics=rs.getString("Scholastics");
                    String strSubject = rs.getString("Subject");
                    String strMarksObtained = rs.getString("Obtained");
                    String strStudentname = rs.getString("StudentName");
                    String strMaxMarks=rs.getString("MaxMarks");
                    String strMinMarks=rs.getString("MinMarks");
                    String strDate=rs.getString("Date");
                    
                    object.put("Exam", strExam);
                    object.put("Subject", strSubject);
                    object.put("MarksObtained", strMarksObtained);
                    object.put("studentname", strStudentname);
                    object.put("MaxMarks", strMaxMarks);
                    object.put("MinMarks", strMinMarks);
                    object.put("class", strClassSection);
                    object.put("RollNo", strRollNo);
                    object.put("SubjectId", strSubjectId);
                    object.put("Date", strDate);
                    
                    array.put(object);
                    
                    
                    /*if(!strScholastics.equalsIgnoreCase("0")){
                    
                        total+=Float.parseFloat(strMarksObtained);
                    maxTotal+=Float.parseFloat(strMaxMarks);
                    int marks=Integer.parseInt(strMarksObtained);
                    
                    int minMarks=Integer.parseInt(strMinMarks);
                    if(marks<minMarks)
                        {
                        failFlag=1;
                         }
                     }else{
                        if(strMarksObtained.equalsIgnoreCase("-23232")){
                            strMarksObtained="A+";
                        }else if(strMarksObtained.equalsIgnoreCase("-33333")){
                            strMarksObtained="A";
                        }else if(strMarksObtained.equalsIgnoreCase("-45454")){
                            strMarksObtained="B+";
                        }else if(strMarksObtained.equalsIgnoreCase("-55555")){
                            strMarksObtained="B";
                        }else if(strMarksObtained.equalsIgnoreCase("-67676")){
                            strMarksObtained="C+";
                        }else if(strMarksObtained.equalsIgnoreCase("-66666")){
                            strMarksObtained="C";
                        }
                    }*/    
                    
               }while(rs.next());
    }catch(Exception e){
        e.printStackTrace();
    }
     }
     public void fetchExamMarksWithGradeGdgb(){
        try{ 
            String strClass="";
            do
               {
                    
                   JSONObject object = new JSONObject();
                   String strRollNo=rs.getString("RollNo");
                   String strStudentName = rs.getString("StudentName"); 
                    strClass= rs.getString("Class");
                   String strSubjectId = rs.getString("SubjectId");
                   String strClassSection= rs.getString("ClassSection");
                   int scholastics=Integer.parseInt(rs.getString("Scholastics"));
                   String strSubject = rs.getString("Subject");
                   String strExamId=rs.getString("ExamId");
                    String strMaxMarks=rs.getString("MaxMarks");
                    String strMinMarks=rs.getString("MinMarks"); 
                    String strMarksObtained = rs.getString("Obtained");
                      String strGrade="" ;
                      
                      //= rs.getString("Grade");
                    String strExam= rs.getString("Exam");
                   
                   
                     if(strMarksObtained==null||strMarksObtained.isEmpty()){
                       strGrade="-";    
                       }else{
                       
                         
                         
                         if(strClass.equalsIgnoreCase("LKG")||strClass.equalsIgnoreCase("UKG")){
                        if(strMarksObtained.equalsIgnoreCase("-22222")){
                            strGrade="A1";
                        }else if(strMarksObtained.equalsIgnoreCase("-33333")){
                            strGrade="A";
                        }else if(strMarksObtained.equalsIgnoreCase("-23232")){
                            strGrade="A+";
                        }else if(strMarksObtained.equalsIgnoreCase("-44444")){
                            strGrade="B1";
                        }else if(strMarksObtained.equalsIgnoreCase("-55555")){
                            strGrade="B";
                        }else if(strMarksObtained.equalsIgnoreCase("-45454")){
                            strGrade="B+";
                        }else if(strMarksObtained.equalsIgnoreCase("-66666")){
                            strGrade="C";
                        }else if(strMarksObtained.equalsIgnoreCase("-67676")){
                            strGrade="C+";
                        }
                        else if(strMarksObtained.equalsIgnoreCase("-9999")){
                            strGrade="AB";
                        }
                        
                    }else if(scholastics==0||strMarksObtained.equalsIgnoreCase("AB")){
                        if(strMarksObtained.equalsIgnoreCase("-22222")){
                            strGrade="A1";
                        }else if(strMarksObtained.equalsIgnoreCase("-33333")){
                            strGrade="A";
                        }else if(strMarksObtained.equalsIgnoreCase("-23232")){
                            strGrade="A+";
                        }else if(strMarksObtained.equalsIgnoreCase("-44444")){
                            strGrade="B1";
                        }else if(strMarksObtained.equalsIgnoreCase("-55555")){
                            strGrade="B";
                        }else if(strMarksObtained.equalsIgnoreCase("-45454")){
                            strGrade="B+";
                        }else if(strMarksObtained.equalsIgnoreCase("-66666")){
                            strGrade="C";
                        }else if(strMarksObtained.equalsIgnoreCase("-67676")){
                            strGrade="C+";
                        }
                        else if(strMarksObtained.equalsIgnoreCase("-9999")){
                            strGrade="AB";
                        }
                    }else {
                        if(strExamId.equalsIgnoreCase("31")||strExamId.equalsIgnoreCase("32")||strExamId.equalsIgnoreCase("103")){
                                
                              //if(strClass.equalsIgnoreCase("6")||strClass.equalsIgnoreCase("7")||strClass.equalsIgnoreCase("8")||strClass.equalsIgnoreCase("9")||strClass.equalsIgnoreCase("10")){
        
                            /*total+=Float.parseFloat(strMarksObtained);
                            maxTotal+=Float.parseFloat(strMaxMarks);*/ 
                             DecimalFormat df = new DecimalFormat("#.##");
                                                df.setRoundingMode(RoundingMode.UP);
                                                //fltObtainedMarks=Float.parseFloat(df.format(fltObtainedMarks));
                                                //fltObtainedMarks=Double.parseDouble(df.format(strMarksObtained));
                                        double dbtot=Double.parseDouble(strMarksObtained);              
                                        dbtot = (double) Math.round(dbtot * 100) / 100;
                                        
                                        double dbMaxtot=Double.parseDouble(strMaxMarks);              
                                        dbMaxtot = (double) Math.round(dbMaxtot * 100) / 100;
                                        dbtot=Double.parseDouble(df.format(dbtot));
                                        dblTotal+=dbtot;       
                                        //dblTotal+=Double.parseDouble(strMarksObtained);
                                        //dblMaxTotal+=Double.parseDouble(strMaxMarks);
                                        dbMaxtot=Double.parseDouble(df.format(dbMaxtot));
                                        dblMaxTotal+=dbMaxtot;
                            //}        
                            }
                            /*if(strExam.equalsIgnoreCase("Midterm Result")||strExam.equalsIgnoreCase("Annual Result")){
                                
                                if(strClass.equalsIgnoreCase("6")||strClass.equalsIgnoreCase("7")||strClass.equalsIgnoreCase("8")||strClass.equalsIgnoreCase("9")||strClass.equalsIgnoreCase("10")){
        
                            total+=Float.parseFloat(strMarksObtained);
                            maxTotal+=Float.parseFloat(strMaxMarks);     
                            }        
                            }*/
                        
                        
            Double fltMaxMarks=Double.parseDouble(strMaxMarks);
            //Float fltObtainedMarks=Float.parseFloat(strMarksObtained);
            Double fltObtainedMarks=Double.parseDouble(strMarksObtained);
            if(!strMarksObtained.equalsIgnoreCase("AB")){
                 
                 
                 if(strClass.length()<2){
                            int cls=Integer.parseInt(strClass);
                            if(cls>5){
                    
                        if(fltMaxMarks==10.0){
                                                DecimalFormat df = new DecimalFormat("#.##");
                                                df.setRoundingMode(RoundingMode.UP);
                                                //fltObtainedMarks=Float.parseFloat(df.format(fltObtainedMarks));
                                                fltObtainedMarks=Double.parseDouble(df.format(fltObtainedMarks));
                                                if(fltObtainedMarks>=9.1&&fltObtainedMarks<10.1){
                                                            strGrade="A1";
                                                  }else if(fltObtainedMarks>=8.1&&fltObtainedMarks<9.1){
                                                            strGrade="A2";
                                                  }else if(fltObtainedMarks>=7.1&&fltObtainedMarks<8.1){
                                                            strGrade="B1";
                                                 }else if(fltObtainedMarks>=6.1&&fltObtainedMarks<7.1){
                                                            strGrade="B2";
                                                 }else if(fltObtainedMarks>=5.1&&fltObtainedMarks<6.1){
                                                            strGrade="C1";
                                                 }else if(fltObtainedMarks>=4.1&&fltObtainedMarks<5.1){
                                                            strGrade="C2";
                                                 }else if(fltObtainedMarks>=3.3&&fltObtainedMarks<4.1){
                                                            strGrade="D";
                                                 }else if(fltObtainedMarks>=0.0&&fltObtainedMarks<3.3){
                                                            strGrade="E";
                                             }

                        }else if(fltMaxMarks==30.0){
                                                        DecimalFormat df = new DecimalFormat("#.##");
                                                        df.setRoundingMode(RoundingMode.UP);
                                                        //fltObtainedMarks=Float.parseFloat(df.format(fltObtainedMarks));
                                                        fltObtainedMarks=Double.parseDouble(df.format(fltObtainedMarks));
                                                   if(fltObtainedMarks>=27.3&&fltObtainedMarks<=30.0){
                                                            strGrade="A1";
                                                    }else if(fltObtainedMarks>=24.3&&fltObtainedMarks<27.3){
                                                            strGrade="A2";
                                                    }else if(fltObtainedMarks>=21.3&&fltObtainedMarks<24.3){
                                                            strGrade="B1";
                                                    }else if(fltObtainedMarks>=18.5&&fltObtainedMarks<21.3){
                                                            strGrade="B2";
                                                    }else if(fltObtainedMarks>=15.3&&fltObtainedMarks<=18.2){
                                                            strGrade="C1";
                                                    }else if(fltObtainedMarks>=12.3&&fltObtainedMarks<15.3){
                                                            strGrade="C2";
                                                    }else if(fltObtainedMarks>=9.9&&fltObtainedMarks<12.3){
                                                            strGrade="D";
                                                    }else if(fltObtainedMarks>=0.0&&fltObtainedMarks<9.9){
                                                            strGrade="E";
                                                    }
                    }else if(fltMaxMarks==5.0){
                                                DecimalFormat df = new DecimalFormat("#.##");
                                                df.setRoundingMode(RoundingMode.UP);
                                                //fltObtainedMarks=Float.parseFloat(df.format(fltObtainedMarks));
                                                fltObtainedMarks=Double.parseDouble(df.format(fltObtainedMarks));
                                                 //String strObtMarks=String.format("%2.2f",fltObtainedMarks);
                                                if(fltObtainedMarks>=4.6&&fltObtainedMarks<=5.0){
                                                            strGrade="A1";
                                                    }else if(fltObtainedMarks>=4.10&&fltObtainedMarks<4.6){
                                                            strGrade="A2";
                                                    }else if(fltObtainedMarks>=3.60&&fltObtainedMarks<4.1){
                                                            strGrade="B1";
                                                    }else if(fltObtainedMarks>=3.10&&fltObtainedMarks<3.6){
                                                            strGrade="B2";
                                                    }else if(fltObtainedMarks>=2.60&&fltObtainedMarks<3.1){
                                                            strGrade="C1";
                                                    }else if(fltObtainedMarks>=2.10&&fltObtainedMarks<2.5){
                                                            strGrade="C2";
                                                    }else if(fltObtainedMarks>=1.60&&fltObtainedMarks<2.1){
                                                            strGrade="D";
                                                    }else if(fltObtainedMarks>=0.0&&fltObtainedMarks<1.6){
                                                            strGrade="E";
                                                    }
                    }else if(fltMaxMarks==80.0){
                                                    DecimalFormat df = new DecimalFormat("#.##");
                                                    df.setRoundingMode(RoundingMode.UP);
                                                //fltObtainedMarks=Float.parseFloat(df.format(fltObtainedMarks));
                                                    fltObtainedMarks=Double.parseDouble(df.format(fltObtainedMarks));
                                                  if(fltObtainedMarks>=72.8&&fltObtainedMarks<80.1){
                                                       strGrade="A1";
                                                    }else if(fltObtainedMarks>=64.8&&fltObtainedMarks<72.8){
                                                       strGrade="A2";
                                                    }else if(fltObtainedMarks>=56.8&&fltObtainedMarks<64.8){
                                                       strGrade="B1";
                                                    }else if(fltObtainedMarks>=48.8&&fltObtainedMarks<56.8){
                                                       strGrade="B2";
                                                    }else if(fltObtainedMarks>=40.8&&fltObtainedMarks<48.8){
                                                       strGrade="C1";
                                                    }else if(fltObtainedMarks>=32.8&&fltObtainedMarks<40.8){
                                                       strGrade="C2";
                                                    }else if(fltObtainedMarks>=26.4&&fltObtainedMarks<32.8){
                                                       strGrade="D";
                                                    }else if(fltObtainedMarks>=0.0&&fltObtainedMarks<=26.4){
                                                       strGrade="E";
                                                    }
                    }else if(fltMaxMarks==50.0){
                                                    DecimalFormat df = new DecimalFormat("#.##");
                                                df.setRoundingMode(RoundingMode.UP);
                                                //fltObtainedMarks=Float.parseFloat(df.format(fltObtainedMarks));
                                                fltObtainedMarks=Double.parseDouble(df.format(fltObtainedMarks));
                                                    if(fltObtainedMarks>=45.5&&fltObtainedMarks<50.1){
                                                           strGrade="A1";
                                                    }else if(fltObtainedMarks>=40.5&&fltObtainedMarks<45.5){
                                                           strGrade="A2";
                                                    }else if(fltObtainedMarks>=35.5&&fltObtainedMarks<40.5){
                                                           strGrade="B1";
                                                    }else if(fltObtainedMarks>=30.5&&fltObtainedMarks<35.5){
                                                           strGrade="B2";
                                                    }else if(fltObtainedMarks>=25.5&&fltObtainedMarks<30.5){
                                                           strGrade="C1";
                                                    }else if(fltObtainedMarks>=20.5&&fltObtainedMarks<25.5){
                                                           strGrade="C2";
                                                    }else if(fltObtainedMarks>=16.5&&fltObtainedMarks<20.5){
                                                           strGrade="D";
                                                    }else if(fltObtainedMarks>=0.0&&fltObtainedMarks<16.5){
                                                           strGrade="E";
                                                    }
                    }else if(fltMaxMarks==100.0){
                                                    DecimalFormat df = new DecimalFormat("#.##");
                                                df.setRoundingMode(RoundingMode.UP);
                                                //fltObtainedMarks=Float.parseFloat(df.format(fltObtainedMarks));
                                                fltObtainedMarks=Double.parseDouble(df.format(fltObtainedMarks));
                                                    if(fltObtainedMarks>=91.00&&fltObtainedMarks<=100.0){
                                                            strGrade="A1";
                                                    }else if(fltObtainedMarks>=81.00&&fltObtainedMarks<91.00){
                                                            strGrade="A2";
                                                    }else if(fltObtainedMarks>=71.0&&fltObtainedMarks<81.0){
                                                            strGrade="B1";
                                                    }else if(fltObtainedMarks>=61.0&&fltObtainedMarks<71){
                                                            strGrade="B2";
                                                    }else if(fltObtainedMarks>=51.1&&fltObtainedMarks<61.0){
                                                            strGrade="C1";
                                                    }else if(fltObtainedMarks>=41.0&&fltObtainedMarks<51.0){
                                                            strGrade="C2";
                                                    }else if(fltObtainedMarks>=33.00&&fltObtainedMarks<41.0){
                                                            strGrade="D";
                                                    }else if(fltObtainedMarks>=0.00&&fltObtainedMarks<33){
                                                            strGrade="E";
                                                    }
                    }else if(fltMaxMarks==70.0){
                                                    DecimalFormat df = new DecimalFormat("#.##");
                                                df.setRoundingMode(RoundingMode.UP);
                                                //fltObtainedMarks=Float.parseFloat(df.format(fltObtainedMarks));
                                                fltObtainedMarks=Double.parseDouble(df.format(fltObtainedMarks));
                                                    if(fltObtainedMarks>=62.40&&fltObtainedMarks<=70.0){
                                                            strGrade="A1";
                                                    }else if(fltObtainedMarks>=51.90&&fltObtainedMarks<62.40){
                                                            strGrade="A2";
                                                    }else if(fltObtainedMarks>=38.6&&fltObtainedMarks<51.90){
                                                            strGrade="B1";
                                                    }else if(fltObtainedMarks>=60.1&&fltObtainedMarks<70.1){
                                                            strGrade="B2";
                                                    }else if(fltObtainedMarks>=23.90&&fltObtainedMarks<38.60){
                                                            strGrade="C1";
                                                    }else if(fltObtainedMarks>=40.1&&fltObtainedMarks<50.1){
                                                            strGrade="C2";
                                                    }else if(fltObtainedMarks>=32.1&&fltObtainedMarks<40.1){
                                                            strGrade="D";
                                                    }else if(fltObtainedMarks>=21.0&&fltObtainedMarks<32.1){
                                                            strGrade="E";
                                                    }
                    } 
            }else{
                       if(fltMaxMarks==10.0){
                                                DecimalFormat df = new DecimalFormat("#.##");
                                                df.setRoundingMode(RoundingMode.UP);
                                                //fltObtainedMarks=Float.parseFloat(df.format(fltObtainedMarks));
                                                fltObtainedMarks=Double.parseDouble(df.format(fltObtainedMarks));
                                                if(fltObtainedMarks>=9.0&&fltObtainedMarks<=10.0){
                                                            strGrade="A+";
                                                  }else if(fltObtainedMarks>=7.5&&fltObtainedMarks<9.0){
                                                            strGrade="A";
                                                  }else if(fltObtainedMarks>=5.6&&fltObtainedMarks<7.5){
                                                            strGrade="B";
                                                 }else if(fltObtainedMarks>=3.5&&fltObtainedMarks<5.6){
                                                            strGrade="C";
                                                 }else if(fltObtainedMarks>=0.0&&fltObtainedMarks<3.5){
                                                            strGrade="D";
                                                 }
                                             

                        }else if(fltMaxMarks==30.0){
                                                    DecimalFormat df = new DecimalFormat("#.##");
                                                df.setRoundingMode(RoundingMode.UP);
                                                //fltObtainedMarks=Float.parseFloat(df.format(fltObtainedMarks));
                                                fltObtainedMarks=Double.parseDouble(df.format(fltObtainedMarks));
                                                   if(fltObtainedMarks>=26.5&&fltObtainedMarks<=30.0){
                                                            strGrade="A+";
                                                    }else if(fltObtainedMarks>=22.5&&fltObtainedMarks<26.5){
                                                            strGrade="A";
                                                    }else if(fltObtainedMarks>=16.5&&fltObtainedMarks<22.5){
                                                            strGrade="B";
                                                    }else if(fltObtainedMarks>=10.5&&fltObtainedMarks<16.5){
                                                            strGrade="C";
                                                    }else if(fltObtainedMarks>=0&&fltObtainedMarks<10.5){
                                                            strGrade="D";
                                                    }
                    }else if(fltMaxMarks==70.0){
                                                    DecimalFormat df = new DecimalFormat("#.##");
                                                df.setRoundingMode(RoundingMode.UP);
                                                //fltObtainedMarks=Float.parseFloat(df.format(fltObtainedMarks));
                                                fltObtainedMarks=Double.parseDouble(df.format(fltObtainedMarks));
                                                   if(fltObtainedMarks>=62.40&&fltObtainedMarks<=70.0){
                                                            strGrade="A+";
                                                    }else if(fltObtainedMarks>=51.90&&fltObtainedMarks<62.40){
                                                            strGrade="A";
                                                    }else if(fltObtainedMarks>=38.60&&fltObtainedMarks<51.90){
                                                            strGrade="B";
                                                    }else if(fltObtainedMarks>=23.90&&fltObtainedMarks<38.60){
                                                            strGrade="C";
                                                    }else if(fltObtainedMarks>=0&&fltObtainedMarks<23.90){
                                                            strGrade="D";
                                                    }
                    }else if(fltMaxMarks==5.0){
                                                DecimalFormat df = new DecimalFormat("#.##");
                                                df.setRoundingMode(RoundingMode.UP);
                                                //fltObtainedMarks=Float.parseFloat(df.format(fltObtainedMarks));
                                                fltObtainedMarks=Double.parseDouble(df.format(fltObtainedMarks));
                                                  if(fltObtainedMarks==5.0){
                                                            strGrade="A+";
                                                  }else if(fltObtainedMarks==4.0){
                                                            strGrade="A";
                                                  }else if(fltObtainedMarks==3.0){
                                                            strGrade="B";
                                                  }else if(fltObtainedMarks==2.0){
                                                            strGrade="C";
                                                  }else if(fltObtainedMarks==1.0){
                                                            strGrade="D";
                                                  }
                    }else if(fltMaxMarks==50.0){
                                                    DecimalFormat df = new DecimalFormat("#.##");
                                                df.setRoundingMode(RoundingMode.UP);
                                                //fltObtainedMarks=Float.parseFloat(df.format(fltObtainedMarks));
                                                fltObtainedMarks=Double.parseDouble(df.format(fltObtainedMarks));
                                                    if(fltObtainedMarks>=44.5&&fltObtainedMarks<=50.0){
                                                           strGrade="A+";
                                                    }else if(fltObtainedMarks>=37.5&&fltObtainedMarks<44.5){
                                                           strGrade="A";
                                                    }else if(fltObtainedMarks>=27.5&&fltObtainedMarks<37.5){
                                                           strGrade="B";
                                                    }else if(fltObtainedMarks>=17.5&&fltObtainedMarks<27.5){
                                                           strGrade="C";
                                                    }else if(fltObtainedMarks>=0&&fltObtainedMarks<17.5){
                                                           strGrade="D";
                                                    }
                    }else if(fltMaxMarks==100.0){
                                                    DecimalFormat df = new DecimalFormat("#.##");
                                                df.setRoundingMode(RoundingMode.UP);
                                                //fltObtainedMarks=Float.parseFloat(df.format(fltObtainedMarks));
                                                fltObtainedMarks=Double.parseDouble(df.format(fltObtainedMarks));
                                                    if(fltObtainedMarks>=90.0&&fltObtainedMarks<=100.0){
                                                            strGrade="A+";
                                                    }else if(fltObtainedMarks>=75.0&&fltObtainedMarks<90){
                                                            strGrade="A";
                                                    }else if(fltObtainedMarks>=56.0&&fltObtainedMarks<75){
                                                            strGrade="B";
                                                    }else if(fltObtainedMarks>=35.0&&fltObtainedMarks<56){
                                                            strGrade="C";
                                                    }else if(fltObtainedMarks>=0&&fltObtainedMarks<35){
                                                            strGrade="D";
                                                    }
                    }else if(fltMaxMarks==20.0){
                                                DecimalFormat df = new DecimalFormat("#.##");
                                                df.setRoundingMode(RoundingMode.UP);
                                                //fltObtainedMarks=Float.parseFloat(df.format(fltObtainedMarks));
                                                fltObtainedMarks=Double.parseDouble(df.format(fltObtainedMarks));
                                                if(fltObtainedMarks>=18.0&&fltObtainedMarks<=20.0){
                                                            strGrade="A+";
                                                  }else if(fltObtainedMarks>=15.0&&fltObtainedMarks<18.0){
                                                            strGrade="A";
                                                  }else if(fltObtainedMarks>=11.2&&fltObtainedMarks<15.0){
                                                            strGrade="B";
                                                 }else if(fltObtainedMarks>=7.0&&fltObtainedMarks<11.2){
                                                            strGrade="C";
                                                 }else if(fltObtainedMarks>=0.0&&fltObtainedMarks<7.0){
                                                            strGrade="D";
                                                 }
                                             

                        }
                                
               }           
                            
           }else{
                
                     
                     
                }
           }
              
        }
    }
              if(!(strMarksObtained==null||strMarksObtained.isEmpty())){
                  
              
            object.put("Exam", strExam);
            object.put("RollNo",strRollNo);
            object.put("Subject", strSubject);
            object.put("MarksObtained", strGrade);
            object.put("Grade", strGrade);
            object.put("studentname", strStudentName);
            object.put("MaxMarks", strMaxMarks);
            object.put("MinMarks", strMinMarks);
            object.put("class", strClassSection);
            object.put("marks", strMarksObtained);                 

            array.put(object);
              }
    }while(rs.next());
    if(dblTotal!=0.0f){
                        
               //float percentage=(total/maxTotal)*100;
               double percentage = ( dblTotal/dblMaxTotal)*100;
               DecimalFormat df = new DecimalFormat("#.##");
                                                df.setRoundingMode(RoundingMode.UP);
                                                //fltObtainedMarks=Float.parseFloat(df.format(fltObtainedMarks));
                                                percentage=Double.parseDouble(df.format(percentage));
               String strPercentage=String.valueOf(percentage);
               //String strPercentage=String.format("%2.2f",percentage);
               int inClass=0;
               if(!(strClass.equalsIgnoreCase("LKG")||strClass.equalsIgnoreCase("UKG"))){
                   inClass=Integer.parseInt(strClass);
               }
               if(inClass<6){
                   
                   if(percentage>=90.00&&percentage<=100.00){
                   strTotalGrade="A+";
               }else if(percentage>=75.00&&percentage<90.00){
                   strTotalGrade="A";
               }else if(percentage>=56.00&&percentage<75.00){
                   strTotalGrade="B";
               }else if(percentage>=35.00&&percentage<56.00){
                   strTotalGrade="C";
               }else if(percentage>=00.00&&percentage<35.00){
                   strTotalGrade="D";
               }
               
               }else{
                
                    if(percentage>=91.00&&percentage<=100.00){
                        strTotalGrade="A1";
                    }else if(percentage>=81.00&&percentage<91.00){
                         strTotalGrade="A2";
                    }else if(percentage>=71.00&&percentage<81.00){
                        strTotalGrade="B1";
                    }else if(percentage>=61.00&&percentage<71.00){
                        strTotalGrade="B2";
                    }else if(percentage>=51.00&&percentage<61.00){
                        strTotalGrade="C1";
                    }else if(percentage>=41.00&&percentage<51.00){
                        strTotalGrade="C2";
                    }else if(percentage>=33.00&&percentage<41.00){
                        strTotalGrade="D";
                    }else if(percentage>=0.00&&percentage<33.00){
                        strTotalGrade="E";
                    }
               }
               /*if(percentage>=91.00&&percentage<=100.00){
                   strTotalGrade="A1";
               }else if(percentage>=81.00&&percentage<91.00){
                   strTotalGrade="A2";
               }else if(percentage>=71.00&&percentage<81.00){
                   strTotalGrade="B1";
               }else if(percentage>=61.00&&percentage<71.00){
                   strTotalGrade="B2";
               }else if(percentage>=51.00&&percentage<61.00){
                   strTotalGrade="C1";
               }else if(percentage>=41.00&&percentage<51.00){
                   strTotalGrade="C2";
               }else if(percentage>=33.00&&percentage<41.00){
                   strTotalGrade="D";
               }else if(percentage>=0.00&&percentage<33.00){
                   strTotalGrade="E";
               }*/
            }else{
                   strTotalGrade="-9999";
           }
   }catch(Exception e){
        e.printStackTrace();
  } 
}
     
public void fetchExamMarksWithGradeMNJT(int examid){
        try{ 
            int mnjtFailCount=0;
            do
               {
                    
                    JSONObject object = new JSONObject();
                    String strRollNo=rs.getString("RollNo");
                    String strStudentName = rs.getString("StudentName"); 
                    String strClass= rs.getString("Class");
                    String strSubjectId = rs.getString("SubjectId");
                    String strClassSection= rs.getString("ClassSection");
                    int scholastics=Integer.parseInt(rs.getString("Scholastics"));
                    String strSubject = rs.getString("Subject");
                    String strMaxMarks=rs.getString("MaxMarks");
                    String strMinMarks=rs.getString("MinMarks"); 
                    String strMarksObtained = rs.getString("Obtained");
                    String strGrade="" ;
                    String strExam= rs.getString("Exam");
                   
                   
                    if(scholastics==0||strMarksObtained.equalsIgnoreCase("AB")){
                        if(strMarksObtained.equalsIgnoreCase("-22222")){
                            strGrade="A1";
                        }else if(strMarksObtained.equalsIgnoreCase("-33333")){
                            strGrade="A";
                        }else if(strMarksObtained.equalsIgnoreCase("-23232")){
                            strGrade="A+";
                        }else if(strMarksObtained.equalsIgnoreCase("-44444")){
                            strGrade="B1";
                        }else if(strMarksObtained.equalsIgnoreCase("-55555")){
                            strGrade="B";
                        }else if(strMarksObtained.equalsIgnoreCase("-45454")){
                            strGrade="B+";
                        }else if(strMarksObtained.equalsIgnoreCase("-66666")){
                            strGrade="C";
                        }else if(strMarksObtained.equalsIgnoreCase("-67676")){
                            strGrade="C+";
                        }
                        else if(strMarksObtained.equalsIgnoreCase("-9999")){
                            strGrade="AB";
                        }
                    }else {
                            if(strExam.equalsIgnoreCase("Midterm Result")||strExam.equalsIgnoreCase("Annual Result")){
                                int cls=Integer.parseInt(strClass);
                                //if(strClass.equalsIgnoreCase("6")||strClass.equalsIgnoreCase("7")||strClass.equalsIgnoreCase("8")||strClass.equalsIgnoreCase("9")||strClass.equalsIgnoreCase("10")){
                                  if(cls>5){
                            total+=Float.parseFloat(strMarksObtained);
                            maxTotal+=Float.parseFloat(strMaxMarks);     
                            }        
                            }
                        
                        
            Float fltMaxMarks=Float.parseFloat(strMaxMarks);
            Float fltObtainedMarks=Float.parseFloat(strMarksObtained);
             if(!strMarksObtained.equalsIgnoreCase("AB")){
                 
                 
                 if(strClass.length()<2){
                            int cls=Integer.parseInt(strClass);
                            if(cls<9){
                    
                        if(fltMaxMarks==10.0){
                                                if(fltObtainedMarks>=9.1&&fltObtainedMarks<10.1){
                                                            strGrade="A1";
                                                  }else if(fltObtainedMarks>=8.1&&fltObtainedMarks<9.1){
                                                            strGrade="A2";
                                                  }else if(fltObtainedMarks>=7.1&&fltObtainedMarks<8.1){
                                                            strGrade="B1";
                                                 }else if(fltObtainedMarks>=6.1&&fltObtainedMarks<7.1){
                                                            strGrade="B2";
                                                 }else if(fltObtainedMarks>=5.1&&fltObtainedMarks<6.1){
                                                            strGrade="C1";
                                                 }else if(fltObtainedMarks>=4.1&&fltObtainedMarks<5.1){
                                                            strGrade="C2";
                                                 }else if(fltObtainedMarks>=3.3&&fltObtainedMarks<4.1){
                                                            strGrade="D";
                                                 }else if(fltObtainedMarks>=0.0&&fltObtainedMarks<3.3){
                                                            strGrade="E";
                                             }

                        }else if(fltMaxMarks==30.0){
                                                   if(fltObtainedMarks>=27.3&&fltObtainedMarks<=30.0){
                                                            strGrade="A1";
                                                    }else if(fltObtainedMarks>=24.3&&fltObtainedMarks<27.3){
                                                            strGrade="A2";
                                                    }else if(fltObtainedMarks>=21.3&&fltObtainedMarks<24.3){
                                                            strGrade="B1";
                                                    }else if(fltObtainedMarks>=18.5&&fltObtainedMarks<21.3){
                                                            strGrade="B2";
                                                    }else if(fltObtainedMarks>=15.3&&fltObtainedMarks<=18.2){
                                                            strGrade="C1";
                                                    }else if(fltObtainedMarks>=12.3&&fltObtainedMarks<15.3){
                                                            strGrade="C2";
                                                    }else if(fltObtainedMarks>=9.9&&fltObtainedMarks<12.3){
                                                            strGrade="D";
                                                    }else if(fltObtainedMarks>=0.0&&fltObtainedMarks<9.9){
                                                            strGrade="E";
                                                    }
                    }else if(fltMaxMarks==5.0){
                                                  if(fltObtainedMarks>=4.6&&fltObtainedMarks<=5.0){
                                                            strGrade="A1";
                                                    }else if(fltObtainedMarks>=4.1&&fltObtainedMarks<4.6){
                                                            strGrade="A2";
                                                    }else if(fltObtainedMarks>=3.6&&fltObtainedMarks<4.1){
                                                            strGrade="B1";
                                                    }else if(fltObtainedMarks>=3.1&&fltObtainedMarks<3.6){
                                                            strGrade="B2";
                                                    }else if(fltObtainedMarks>=2.6&&fltObtainedMarks<3.1){
                                                            strGrade="C1";
                                                    }else if(fltObtainedMarks>=2.1&&fltObtainedMarks<2.5){
                                                            strGrade="C2";
                                                    }else if(fltObtainedMarks>=1.6&&fltObtainedMarks<2.1){
                                                            strGrade="D";
                                                    }else if(fltObtainedMarks>=0.0&&fltObtainedMarks<1.6){
                                                            strGrade="E";
                                                    }
                    }else if(fltMaxMarks==80.0){
                                                  if(fltObtainedMarks>=72.8&&fltObtainedMarks<80.1){
                                                       strGrade="A1";
                                                    }else if(fltObtainedMarks>=64.8&&fltObtainedMarks<72.8){
                                                       strGrade="A2";
                                                    }else if(fltObtainedMarks>=56.8&&fltObtainedMarks<64.8){
                                                       strGrade="B1";
                                                    }else if(fltObtainedMarks>=48.8&&fltObtainedMarks<56.8){
                                                       strGrade="B2";
                                                    }else if(fltObtainedMarks>=40.8&&fltObtainedMarks<48.8){
                                                       strGrade="C1";
                                                    }else if(fltObtainedMarks>=32.8&&fltObtainedMarks<40.8){
                                                       strGrade="C2";
                                                    }else if(fltObtainedMarks>=26.4&&fltObtainedMarks<32.8){
                                                       strGrade="D";
                                                    }else if(fltObtainedMarks>=0.0&&fltObtainedMarks<=26.4){
                                                       strGrade="E";
                                                    }
                    }else if(fltMaxMarks==50.0){
                                                    if(fltObtainedMarks>=45.5&&fltObtainedMarks<50.1){
                                                           strGrade="A1";
                                                    }else if(fltObtainedMarks>=40.5&&fltObtainedMarks<45.5){
                                                           strGrade="A2";
                                                    }else if(fltObtainedMarks>=35.5&&fltObtainedMarks<40.5){
                                                           strGrade="B1";
                                                    }else if(fltObtainedMarks>=30.5&&fltObtainedMarks<35.5){
                                                           strGrade="B2";
                                                    }else if(fltObtainedMarks>=25.5&&fltObtainedMarks<30.5){
                                                           strGrade="C1";
                                                    }else if(fltObtainedMarks>=20.5&&fltObtainedMarks<25.5){
                                                           strGrade="C2";
                                                    }else if(fltObtainedMarks>=15.5&&fltObtainedMarks<20.5){
                                                           strGrade="D";
                                                    }else if(fltObtainedMarks>=0.0&&fltObtainedMarks<16.5){
                                                           strGrade="E";
                                                    }
                    }else if(fltMaxMarks==100.0){
                                                    if(fltObtainedMarks>=90.1&&fltObtainedMarks<=100.0){
                                                            strGrade="A1";
                                                    }else if(fltObtainedMarks>=80.1&&fltObtainedMarks<90.1){
                                                            strGrade="A2";
                                                    }else if(fltObtainedMarks>=70.1&&fltObtainedMarks<80.1){
                                                            strGrade="B1";
                                                    }else if(fltObtainedMarks>=60.1&&fltObtainedMarks<70.1){
                                                            strGrade="B2";
                                                    }else if(fltObtainedMarks>=50.1&&fltObtainedMarks<60.1){
                                                            strGrade="C1";
                                                    }else if(fltObtainedMarks>=40.1&&fltObtainedMarks<50.1){
                                                            strGrade="C2";
                                                    }else if(fltObtainedMarks>=32.1&&fltObtainedMarks<40.1){
                                                            strGrade="D";
                                                    }else if(fltObtainedMarks>=21.0&&fltObtainedMarks<32.1){
                                                            strGrade="E";
                                                            
                                                    }
                    }    
            }else{
                       if(fltMaxMarks==10.0){
                                                if(fltObtainedMarks>=9&&fltObtainedMarks<=10.0){
                                                            strGrade="A+";
                                                  }else if(fltObtainedMarks>=7.5&&fltObtainedMarks<9){
                                                            strGrade="A";
                                                  }else if(fltObtainedMarks>=5.6&&fltObtainedMarks<7.5){
                                                            strGrade="B+";
                                                 }else if(fltObtainedMarks>=3.5&&fltObtainedMarks<5.6){
                                                            strGrade="C";
                                                 }else if(fltObtainedMarks>=0.0&&fltObtainedMarks<3.5){
                                                            strGrade="D";
                                                 }
                                             

                        }else if(fltMaxMarks==30.0){
                                                   if(fltObtainedMarks>=27&&fltObtainedMarks<=30.0){
                                                            strGrade="A+";
                                                    }else if(fltObtainedMarks>=22.5&&fltObtainedMarks<27){
                                                            strGrade="A";
                                                    }else if(fltObtainedMarks>=16.8&&fltObtainedMarks<22.5){
                                                            strGrade="B";
                                                    }else if(fltObtainedMarks>=10.5&&fltObtainedMarks<=16.7){
                                                            strGrade="C";
                                                    }else if(fltObtainedMarks>=0&&fltObtainedMarks<10.5){
                                                            strGrade="D";
                                                    }
                    }else if(fltMaxMarks==5.0){
                                                  if(fltObtainedMarks==5.0){
                                                            strGrade="A+";
                                                  }else if(fltObtainedMarks==4.0){
                                                            strGrade="A";
                                                  }else if(fltObtainedMarks==3.0){
                                                            strGrade="B";
                                                  }else if(fltObtainedMarks==2.0){
                                                            strGrade="C";
                                                  }else if(fltObtainedMarks==1.0){
                                                            strGrade="D";
                                                  }
                    }else if(fltMaxMarks==50.0){
                                                    if(fltObtainedMarks>=45.0&&fltObtainedMarks<=50.0){
                                                           strGrade="A+";
                                                    }else if(fltObtainedMarks>=37.5&&fltObtainedMarks<44.9){
                                                           strGrade="A";
                                                    }else if(fltObtainedMarks>=28.0&&fltObtainedMarks<37.4){
                                                           strGrade="B";
                                                    }else if(fltObtainedMarks>=17.5&&fltObtainedMarks<28){
                                                           strGrade="C";
                                                    }else if(fltObtainedMarks>=0&&fltObtainedMarks<17.5){
                                                           strGrade="D";
                                                    }
                    }else if(fltMaxMarks==100.0){
                                                    if(fltObtainedMarks>=90.0&&fltObtainedMarks<=100.0){
                                                            strGrade="A+";
                                                    }else if(fltObtainedMarks>=75.0&&fltObtainedMarks<89.9){
                                                            strGrade="A";
                                                    }else if(fltObtainedMarks>=56.0&&fltObtainedMarks<74.9){
                                                            strGrade="B";
                                                    }else if(fltObtainedMarks>=35.0&&fltObtainedMarks<55.9){
                                                            strGrade="C";
                                                    }else if(fltObtainedMarks>=0&&fltObtainedMarks<34.9){
                                                            strGrade="D";
                                                    }
                    }
                                
               }           
                            
           }else{
                
                     
                     
                }
           }
              
        }
               
            object.put("Exam", strExam);
            object.put("RollNo",strRollNo);
            object.put("Subject", strSubject);
            object.put("MarksObtained", strGrade);
            object.put("Grade", strGrade);
            object.put("studentname", strStudentName);
            object.put("MaxMarks", strMaxMarks);
            object.put("MinMarks", strMinMarks);
            object.put("class", strClassSection);
            array.put(object);
    }while(rs.next());
    if((examid==93||examid==94)){
        
    
        if(total!=0.0f){
                        
               float percentage=(total/maxTotal)*100;
               
               String strPercentage=String.format("%2.2f",percentage);
               if(percentage>=91.00&&percentage<=100.00){
                   strTotalGrade="A1";
               }else if(percentage>=81.00&&percentage<91.00){
                   strTotalGrade="A2";
               }else if(percentage>=71.00&&percentage<81.00){
                   strTotalGrade="B1";
               }else if(percentage>=61.00&&percentage<71.00){
                   strTotalGrade="B2";
               }else if(percentage>=51.00&&percentage<61.00){
                   strTotalGrade="C1";
               }else if(percentage>=41.00&&percentage<51.00){
                   strTotalGrade="C2";
               }else if(percentage>=33.00&&percentage<41.00){
                   strTotalGrade="D";
               }else if(percentage>=0.00&&percentage<33.00){
                   strTotalGrade="E";
               }
            }else if(examid==104){
               
               float percentage=(total/maxTotal)*100;
               /*84.50 >= to <=100 Distinction
59.50 >= to < 84.50 First Class
49.50 >= to < 59.50 Second Class
35.00 >= to <49.50 Pass
Below 35.00 :-Fail*/
               String strPercentage=String.format("%2.2f",percentage);
               if(percentage>=91.00&&percentage<=100.00){
                   strTotalGrade="A1";
                   
               }else if(percentage>=81.00&&percentage<91.00){
                   strTotalGrade="A2";
                   
               }else if(percentage>=71.00&&percentage<81.00){
                   strTotalGrade="B1";
               }else if(percentage>=61.00&&percentage<71.00){
                   strTotalGrade="B2";
               }else if(percentage>=51.00&&percentage<61.00){
                   strTotalGrade="C1";
               }else if(percentage>=41.00&&percentage<51.00){
                   strTotalGrade="C2";
               }else if(percentage>=33.00&&percentage<41.00){
                   strTotalGrade="D";
               }else if(percentage>=0.00&&percentage<33.00){
                   strTotalGrade="E";
               }
                /*84.50 >= to <=100 Distinction
59.50 >= to < 84.50 First Class
49.50 >= to < 59.50 Second Class
35.00 >= to <49.50 Pass
Below 35.00 :-Fail*/
                if(percentage>=84.50&&percentage<=100.00){
                   strTotalResultStatus="Distinction";
                   
               }else if(percentage>=59.50&&percentage<84.50){
                   strTotalResultStatus="First class";
               }else if(percentage>=49.50&&percentage<59.50){
                   strTotalResultStatus="Second class";
               }else if(percentage>=35.00&&percentage<49.50){
                   strTotalResultStatus="Pass";
               }
               
            }else{
                   strTotalGrade="-9999";
           }
        
           
        
    }else {
        strTotalGrade="-9999";
    }
            
   }catch(Exception e){
        e.printStackTrace();
  } 
}     
     
     
     
     
     
     
     public void fetchExamMarksWithGradeHBBN(){
        try{
            String strClass="";
            do
               {
                    
                   JSONObject object = new JSONObject();
                   String strRollNo=rs.getString("RollNo");
                   String strStudentName = rs.getString("StudentName"); 
                   strClass= rs.getString("Class");
                   String strSubjectId = rs.getString("SubjectId");
                   String strClassSection= rs.getString("ClassSection");
                   int scholastics=Integer.parseInt(rs.getString("Scholastics"));
                   String strSubject = rs.getString("Subject");
                   
                    String strMaxMarks=rs.getString("MaxMarks");
                    String strMinMarks=rs.getString("MinMarks"); 
                    String strMarksObtained = rs.getString("Obtained");
                      String strGrade="" ;
                      
                      //= rs.getString("Grade");
                    String strExam= rs.getString("Exam");
                       
                    if(strMarksObtained.equalsIgnoreCase("AB")||strMarksObtained.equalsIgnoreCase("-9999")||strMarksObtained==null||strMarksObtained.isEmpty()){
                       if(strMarksObtained==null||strMarksObtained.isEmpty()){
                       strGrade="-";    
                       }else {
                        strGrade="AB";    
                        absCount++;
                       }
                       
                        
                        total+=0;
                       maxTotal+=Float.parseFloat(strMaxMarks);
                    }else if(scholastics!=0){
                        schSubCount++;
                        total+=Float.parseFloat(strMarksObtained);
                      maxTotal+=Float.parseFloat(strMaxMarks);
                                            
                      Float fltMaxMarks=Float.parseFloat(strMaxMarks);
            Float fltObtainedMarks=Float.parseFloat(strMarksObtained);
             //if(!strMarksObtained.equalsIgnoreCase("AB")){
                 
                 
                 if(strClass.length()<2){
                            int cls=Integer.parseInt(strClass);
                 //scholastic ==1 and class lesser then 10th
                            if(cls<10){
                    
                        if(fltMaxMarks==10.0){
                                                if(fltObtainedMarks>=8.01&&fltObtainedMarks<=10.00){
                                                            strGrade="A+";
                                                  }else if(fltObtainedMarks>=6.01&&fltObtainedMarks<8.01){
                                                            strGrade="A";
                                                  }else if(fltObtainedMarks>=4.01&&fltObtainedMarks<6.01){
                                                            strGrade="B+";
                                                 }else if(fltObtainedMarks>=2.01&&fltObtainedMarks<4.01){
                                                            strGrade="B";
                                                 }else if(fltObtainedMarks>=0.00&&fltObtainedMarks<2.01){
                                                            strGrade="C";
                                                 }

                        }else if(fltMaxMarks==30.0){
                                                   if(fltObtainedMarks>26.00&&fltObtainedMarks<=30.0){
                                                            strGrade="A+";
                                                    }else if(fltObtainedMarks>20.00&&fltObtainedMarks<26.01){
                                                            strGrade="A";
                                                    }else if(fltObtainedMarks>14.00&&fltObtainedMarks<20.01){
                                                            strGrade="B+";
                                                    }else if(fltObtainedMarks>8.01&&fltObtainedMarks<14.01){
                                                            strGrade="B";
                                                    }else if(fltObtainedMarks>=0.00&&fltObtainedMarks<8.01){
                                                            strGrade="C";
                                                    }
                    }else if(fltMaxMarks==5.0){
                                                  if(fltObtainedMarks>4.00&&fltObtainedMarks<=5.00){
                                                            strGrade="A+";
                                                    }else if(fltObtainedMarks>3.00&&fltObtainedMarks<=4.00){
                                                            strGrade="A";
                                                    }else if(fltObtainedMarks>2.00&&fltObtainedMarks<=3.00){
                                                            strGrade="B+";
                                                    }else if(fltObtainedMarks>1.00&&fltObtainedMarks<=2.00){
                                                            strGrade="B";
                                                    }else if(fltObtainedMarks>=0.00&&fltObtainedMarks<=1.00){
                                                            strGrade="C";
                                                    }
                    }/*else if(fltMaxMarks==80.0){
                                                  if(fltObtainedMarks>=72.8&&fltObtainedMarks<80.1){
                                                       strGrade="A1";
                                                    }else if(fltObtainedMarks>=64.8&&fltObtainedMarks<72.8){
                                                       strGrade="A2";
                                                    }else if(fltObtainedMarks>=56.8&&fltObtainedMarks<64.8){
                                                       strGrade="B1";
                                                    }else if(fltObtainedMarks>=48.8&&fltObtainedMarks<56.8){
                                                       strGrade="B2";
                                                    }else if(fltObtainedMarks>=40.8&&fltObtainedMarks<48.8){
                                                       strGrade="C";
                                                    }
                    }*/else if(fltMaxMarks==50.0){
                                                    if(fltObtainedMarks>=44.01&&fltObtainedMarks<50.1){
                                                           strGrade="A+";
                                                    }else if(fltObtainedMarks>=34.01&&fltObtainedMarks<44.01){
                                                           strGrade="A";
                                                    }else if(fltObtainedMarks>=24.01&&fltObtainedMarks<34.01){
                                                           strGrade="B+";
                                                    }else if(fltObtainedMarks>=14.01&&fltObtainedMarks<24.01){
                                                           strGrade="B";
                                                    }else if(fltObtainedMarks>=0.0&&fltObtainedMarks<14.01){
                                                           strGrade="C";
                                                    }
                    }else if(fltMaxMarks==100.0){
                                                    if(fltObtainedMarks>=90.00&&fltObtainedMarks<=100.01){
                                                            strGrade="A+";
                                                    }else if(fltObtainedMarks>=70.00&&fltObtainedMarks<90){
                                                            strGrade="A";
                                                    }else if(fltObtainedMarks>=50.00&&fltObtainedMarks<70){
                                                            strGrade="B+";
                                                    }else if(fltObtainedMarks>=30.00&&fltObtainedMarks<50.00){
                                                            strGrade="B";
                                                    }else if(fltObtainedMarks>=0.0&&fltObtainedMarks<30.00){
                                                            strGrade="C";
                                                    }
                    }else if(fltMaxMarks==20.0){
                                                    if(fltObtainedMarks>16.00&&fltObtainedMarks<=20.0){
                                                            strGrade="A+";
                                                    }else if(fltObtainedMarks>12.01&&fltObtainedMarks<16.01){
                                                            strGrade="A";
                                                    }else if(fltObtainedMarks>8.01&&fltObtainedMarks<12.01){
                                                            strGrade="B+";
                                                    }else if(fltObtainedMarks>4.01&&fltObtainedMarks<8.01){
                                                            strGrade="B";
                                                    }else if(fltObtainedMarks>=0.00&&fltObtainedMarks<04.01){
                                                            strGrade="C";
                                                    }
                    }else if(fltMaxMarks==40.0){
                                                    if(fltObtainedMarks>=35.01&&fltObtainedMarks<=40.01){
                                                            strGrade="A1";
                                                    }else if(fltObtainedMarks>=27.01&&fltObtainedMarks<=35.01){
                                                            strGrade="A";
                                                    }else if(fltObtainedMarks>=19.01&&fltObtainedMarks<=27.01){
                                                            strGrade="B+";
                                                    }else if(fltObtainedMarks>=11.01&&fltObtainedMarks<=19.01){
                                                            strGrade="B";
                                                    }else if(fltObtainedMarks>=00.00&&fltObtainedMarks<=11.01){
                                                            strGrade="C";
                                                    }
                    }else if(fltMaxMarks==15.0){
                                                    if(fltObtainedMarks>=13.1&&fltObtainedMarks<=15.1){
                                                            strGrade="A+";
                                                    }else if(fltObtainedMarks>=10.1&&fltObtainedMarks<13.1){
                                                            strGrade="A";
                                                    }else if(fltObtainedMarks>=7.01&&fltObtainedMarks<=10.1){
                                                            strGrade="B+";
                                                    }else if(fltObtainedMarks>=4.01&&fltObtainedMarks<7.01){
                                                            strGrade="B";
                                                    }else if(fltObtainedMarks>=0.00&&fltObtainedMarks<=4.01){
                                                            strGrade="C";
                                                    }
                    }else if(fltMaxMarks==60.0){
                                                    if(fltObtainedMarks>=53.01&&fltObtainedMarks<=60.0){
                                                            strGrade="A+";
                                                    }else if(fltObtainedMarks>=41.01&&fltObtainedMarks<=53.01){
                                                            strGrade="A";
                                                    }else if(fltObtainedMarks>=29.01&&fltObtainedMarks<=41.01){
                                                            strGrade="B+";
                                                    }else if(fltObtainedMarks>=17.01&&fltObtainedMarks<=29.01){
                                                            strGrade="B";
                                                    }else if(fltObtainedMarks>=00.00&&fltObtainedMarks<=17.01){
                                                            strGrade="C";
                                                    }
                    }/*else if(fltMaxMarks==25.0){
                                                    if(fltObtainedMarks>=21.0&&fltObtainedMarks<=25.0){
                                                            strGrade="A1";
                                                    }else if(fltObtainedMarks>=16.0&&fltObtainedMarks<21.0){
                                                            strGrade="A2";
                                                    }else if(fltObtainedMarks>=11.0&&fltObtainedMarks<16.0){
                                                            strGrade="B1";
                                                    }else if(fltObtainedMarks>=7.0&&fltObtainedMarks<11.0){
                                                            strGrade="B2";
                                                    }else if(fltObtainedMarks>=30.5&&fltObtainedMarks<7){
                                                            strGrade="C";
                                                    }
        
                    }*/    
                        //scholastic 1 and class 10th
            }else if (cls==10){
                       if(fltMaxMarks==10.0){
                                                if(fltObtainedMarks>=9&&fltObtainedMarks<=10.0){
                                                            strGrade="A+";
                                                  }else if(fltObtainedMarks>=8.00&&fltObtainedMarks<9.00){
                                                            strGrade="A";
                                                  }else if(fltObtainedMarks>=7.00&&fltObtainedMarks<8.00){
                                                            strGrade="B+";
                                                  }else if(fltObtainedMarks>=6.00&&fltObtainedMarks<7.00){
                                                            strGrade="B";
                                                 }else if(fltObtainedMarks>=5.00&&fltObtainedMarks<6.00){
                                                            strGrade="C+";
                                                 }else if(fltObtainedMarks>=3.5&&fltObtainedMarks<5.00){
                                                            strGrade="C";
                                                 }else if(fltObtainedMarks>=0.0&&fltObtainedMarks<3.5){
                                                            strGrade="NC";
                                                 }
                                             

                        }else if(fltMaxMarks==30.0){
                                                   if(fltObtainedMarks>=26.00&&fltObtainedMarks<=30.0){
                                                            strGrade="A+";
                                                  }else if(fltObtainedMarks>=23.00&&fltObtainedMarks<26.00){
                                                            strGrade="A";
                                                  }else if(fltObtainedMarks>=20.00&&fltObtainedMarks<23.00){
                                                            strGrade="B+";
                                                  }else if(fltObtainedMarks>=17.00&&fltObtainedMarks<20.00){
                                                            strGrade="B";
                                                 }else if(fltObtainedMarks>=14.00&&fltObtainedMarks<17.00){
                                                            strGrade="C+";
                                                 }else if(fltObtainedMarks>=11.00&&fltObtainedMarks<14.00){
                                                            strGrade="C";
                                                 }else if(fltObtainedMarks>=0.0&&fltObtainedMarks<11.00){
                                                            strGrade="NC";
                                                 }
                    }else if(fltMaxMarks==5.0){
                                                  if(fltObtainedMarks>=4.50&&fltObtainedMarks<=5.0){
                                                            strGrade="A+";
                                                  }else if(fltObtainedMarks>=4.00&&fltObtainedMarks<4.50){
                                                            strGrade="A";
                                                  }else if(fltObtainedMarks>=3.50&&fltObtainedMarks<4.00){
                                                            strGrade="B+";
                                                  }else if(fltObtainedMarks>=3.00&&fltObtainedMarks<3.50){
                                                            strGrade="B";
                                                 }else if(fltObtainedMarks>=2.50&&fltObtainedMarks<3.00){
                                                            strGrade="C+";
                                                 }else if(fltObtainedMarks>1.5&&fltObtainedMarks<2.50){
                                                            strGrade="C";
                                                 }else if(fltObtainedMarks>=0.0&&fltObtainedMarks<1.5){
                                                            strGrade="NC";
                                                 }
                    }else if(fltMaxMarks==50.0){
                                                    if(fltObtainedMarks>=44.00&&fltObtainedMarks<=50.0){
                                                            strGrade="A+";
                                                  }else if(fltObtainedMarks>=39.00&&fltObtainedMarks<44.00){
                                                            strGrade="A";
                                                  }else if(fltObtainedMarks>=34.00&&fltObtainedMarks<39.00){
                                                            strGrade="B+";
                                                  }else if(fltObtainedMarks>=29.00&&fltObtainedMarks<34.00){
                                                            strGrade="B";
                                                 }else if(fltObtainedMarks>=24.00&&fltObtainedMarks<29.00){
                                                            strGrade="C+";
                                                 }else if(fltObtainedMarks>=18.00&&fltObtainedMarks<24.00){
                                                            strGrade="C";
                                                 }else if(fltObtainedMarks>=0.0&&fltObtainedMarks<18.00){
                                                            strGrade="NC";
                                                 }
                    }else if(fltMaxMarks==100.0){
                                                    if(fltObtainedMarks>=90&&fltObtainedMarks<=100.0){
                                                            strGrade="A+";
                                                  }else if(fltObtainedMarks>=80.00&&fltObtainedMarks<90.00){
                                                            strGrade="A";
                                                  }else if(fltObtainedMarks>=70.00&&fltObtainedMarks<80.00){
                                                            strGrade="B+";
                                                  }else if(fltObtainedMarks>=60.00&&fltObtainedMarks<70.00){
                                                            strGrade="B";
                                                 }else if(fltObtainedMarks>=50.00&&fltObtainedMarks<60.00){
                                                            strGrade="C+";
                                                 }else if(fltObtainedMarks>=35.00&&fltObtainedMarks<50.00){
                                                            strGrade="C";
                                                 }else if(fltObtainedMarks>=0.0&&fltObtainedMarks<35.0){
                                                            strGrade="NC";
                                                 }
                    }else if(fltMaxMarks==125.0){
                                                    if(fltObtainedMarks>=112&&fltObtainedMarks<=125.0){
                                                            strGrade="A+";
                                                  }else if(fltObtainedMarks>=99.00&&fltObtainedMarks<112.00){
                                                            strGrade="A";
                                                  }else if(fltObtainedMarks>=87.00&&fltObtainedMarks<99.00){
                                                            strGrade="B+";
                                                  }else if(fltObtainedMarks>=74.00&&fltObtainedMarks<87.00){
                                                            strGrade="B";
                                                 }else if(fltObtainedMarks>=62.00&&fltObtainedMarks<74.00){
                                                            strGrade="C+";
                                                 }else if(fltObtainedMarks>=44.00&&fltObtainedMarks<62.00){
                                                            strGrade="C";
                                                 }else if(fltObtainedMarks>=0.0&&fltObtainedMarks<44.0){
                                                            strGrade="NC";
                                                 }
                    }
                                
               }           
                            
           }
                      
                    }else if(scholastics==0) {
                        
                        
                     
                                            
                      Float fltMaxMarks=Float.parseFloat(strMaxMarks);
            Float fltObtainedMarks=Float.parseFloat(strMarksObtained);
             //if(!strMarksObtained.equalsIgnoreCase("AB")){
                 
                 
                 if(strClass.length()<=2){
                            int cls=Integer.parseInt(strClass);
                            if(cls<10){
                        // scholastics is Zero and class class less then 10th        
                              if(fltMaxMarks==10.0){
                                                if(fltObtainedMarks>=7&&fltObtainedMarks<=10.0){
                                                            strGrade="A";
                                                  }else if(fltObtainedMarks>=3&&fltObtainedMarks<7){
                                                            strGrade="B";
                                                  }else if(fltObtainedMarks>=0.00&&fltObtainedMarks<3){
                                                            strGrade="C";
                                                 }                   

                        }else if(fltMaxMarks==30.0){
                                                   if(fltObtainedMarks>=21.0&&fltObtainedMarks<=30.0){
                                                           strGrade="A";
                                                    }else if(fltObtainedMarks>=10&&fltObtainedMarks<21){
                                                           strGrade="B";
                                                    }else if(fltObtainedMarks>=0&&fltObtainedMarks<10){
                                                           strGrade="C";
                                                    }
                    }else if(fltMaxMarks==15.0){
                                                  if(fltObtainedMarks>=10.0&&fltObtainedMarks<=15.0){
                                                           strGrade="A";
                                                    }else if(fltObtainedMarks>=4&&fltObtainedMarks<10.00){
                                                           strGrade="B";
                                                    }else if(fltObtainedMarks>=0&&fltObtainedMarks<4){
                                                           strGrade="C";
                                                    }
                    }else if(fltMaxMarks==50.0){
                                                    if(fltObtainedMarks>=34.01&&fltObtainedMarks<=50.0){
                                                           strGrade="A";
                                                    }else if(fltObtainedMarks>=15&&fltObtainedMarks<34.01){
                                                           strGrade="B";
                                                    }else if(fltObtainedMarks>=0&&fltObtainedMarks<15){
                                                           strGrade="C";
                                                    }
                    }else if(fltMaxMarks==100.0){
                                                    if(fltObtainedMarks>=70.0&&fltObtainedMarks<=100.0){
                                                           strGrade="A";
                                                    }else if(fltObtainedMarks>=30&&fltObtainedMarks<70){
                                                           strGrade="B";
                                                    }else if(fltObtainedMarks>=0&&fltObtainedMarks<30){
                                                           strGrade="C";
                                                    }
                    }
                                
                }else if(cls==10){
                    // scholastics is 0 and class is 10th
                        if(fltMaxMarks==10.0){
                                                if(fltObtainedMarks>=8.00&&fltObtainedMarks<=10.0){
                                                           strGrade="A";
                                                    }else if(fltObtainedMarks>=5&&fltObtainedMarks<7.90){
                                                           strGrade="B";
                                                    }else if(fltObtainedMarks>=3.5&&fltObtainedMarks<4.90){
                                                           strGrade="C";
                                                    }else if(fltObtainedMarks>=0&&fltObtainedMarks<3.5){
                                                           strGrade="NC";
                                                    }

                        }if(fltMaxMarks==20.0){
                                                if(fltObtainedMarks>=16.0&&fltObtainedMarks<=20.0){
                                                           strGrade="A";
                                                    }else if(fltObtainedMarks>=10&&fltObtainedMarks<16){
                                                           strGrade="B";
                                                    }else if(fltObtainedMarks>=7&&fltObtainedMarks<10){
                                                           strGrade="C";
                                                    }else if(fltObtainedMarks>=0&&fltObtainedMarks<7){
                                                           strGrade="NC";
                                                    }

                        }if(fltMaxMarks==40.0){
                                                if(fltObtainedMarks>=32.0&&fltObtainedMarks<=40.0){
                                                           strGrade="A";
                                                    }else if(fltObtainedMarks>=20&&fltObtainedMarks<32){
                                                           strGrade="B";
                                                    }else if(fltObtainedMarks>=14&&fltObtainedMarks<20){
                                                           strGrade="C";
                                                    }else if(fltObtainedMarks>=0.00&&fltObtainedMarks<14){
                                                           strGrade="NC";
                                                    }

                        }if(fltMaxMarks==50.0){
                                                if(fltObtainedMarks>=40.0&&fltObtainedMarks<=50.0){
                                                           strGrade="A";
                                                    }else if(fltObtainedMarks>=25&&fltObtainedMarks<40){
                                                           strGrade="B";
                                                    }else if(fltObtainedMarks>=18&&fltObtainedMarks<24){
                                                           strGrade="C";
                                                    }else if(fltObtainedMarks>=0.00&&fltObtainedMarks<18){
                                                           strGrade="NC";
                                                    }

                        }/*else if(fltMaxMarks==30.0){
                                                   if(fltObtainedMarks>26.00&&fltObtainedMarks<=30.0){
                                                            strGrade="A+";
                                                    }else if(fltObtainedMarks>20.00&&fltObtainedMarks<26.01){
                                                            strGrade="A";
                                                    }else if(fltObtainedMarks>14.00&&fltObtainedMarks<20.01){
                                                            strGrade="B+";
                                                    }else if(fltObtainedMarks>8.01&&fltObtainedMarks<14.01){
                                                            strGrade="B";
                                                    }else if(fltObtainedMarks>=0.00&&fltObtainedMarks<8.01){
                                                            strGrade="C";
                                                    }
                    }else if(fltMaxMarks==5.0){
                                                  if(fltObtainedMarks>4.00&&fltObtainedMarks<=5.00){
                                                            strGrade="A+";
                                                    }else if(fltObtainedMarks>3.00&&fltObtainedMarks<=4.00){
                                                            strGrade="A";
                                                    }else if(fltObtainedMarks>2.00&&fltObtainedMarks<=3.00){
                                                            strGrade="B+";
                                                    }else if(fltObtainedMarks>1.00&&fltObtainedMarks<=2.00){
                                                            strGrade="B";
                                                    }else if(fltObtainedMarks>=0.00&&fltObtainedMarks<=1.00){
                                                            strGrade="C";
                                                    }
                    }*/else if(fltMaxMarks==80.0){
                                                  if(fltObtainedMarks>=64.00&&fltObtainedMarks<80.1){
                                                       strGrade="A";
                                                    }else if(fltObtainedMarks>=40.00&&fltObtainedMarks<64){
                                                       strGrade="B";
                                                    }else if(fltObtainedMarks>=28.00&&fltObtainedMarks<40){
                                                       strGrade="C";
                                                    }else if(fltObtainedMarks>=0.00&&fltObtainedMarks<28.00){
                                                       strGrade="NC";
                                                    }
                    }else if(fltMaxMarks==100.0){
                                                    if(fltObtainedMarks>=80.00&&fltObtainedMarks<=100.01){
                                                            strGrade="A";
                                                    }else if(fltObtainedMarks>=50&&fltObtainedMarks<80.00){
                                                            strGrade="B";
                                                    }else if(fltObtainedMarks>=35&&fltObtainedMarks<50){
                                                            strGrade="C";
                                                    }else if(fltObtainedMarks>=00.00&&fltObtainedMarks<35){
                                                            strGrade="NC";
                                                    }
                    }else if(fltMaxMarks==20.0){
                                                    if(fltObtainedMarks>=16.00&&fltObtainedMarks<=20.0){
                                                            strGrade="A";
                                                    }else if(fltObtainedMarks>=10.00&&fltObtainedMarks<16.00){
                                                            strGrade="B";
                                                    }else if(fltObtainedMarks>=7.00&&fltObtainedMarks<10.00){
                                                            strGrade="C";
                                                    }else if(fltObtainedMarks>=0.00&&fltObtainedMarks<7.00){
                                                            strGrade="NC";
                                                    }
                    }else if(fltMaxMarks==40.0){
                                                    if(fltObtainedMarks>=32.00&&fltObtainedMarks<=40.0){
                                                            strGrade="A";
                                                    }else if(fltObtainedMarks>=20.00&&fltObtainedMarks<32.00){
                                                            strGrade="B";
                                                    }else if(fltObtainedMarks>=14.00&&fltObtainedMarks<20.00){
                                                            strGrade="C";
                                                    }else if(fltObtainedMarks>=0.00&&fltObtainedMarks<14.00){
                                                            strGrade="NC";
                                                    }
                    }/*else if(fltMaxMarks==15.0){
                                                    if(fltObtainedMarks>=13.1&&fltObtainedMarks<=15.1){
                                                            strGrade="A+";
                                                    }else if(fltObtainedMarks>=10.1&&fltObtainedMarks<13.1){
                                                            strGrade="A";
                                                    }else if(fltObtainedMarks>=7.01&&fltObtainedMarks<=10.1){
                                                            strGrade="B+";
                                                    }else if(fltObtainedMarks>=4.01&&fltObtainedMarks<7.01){
                                                            strGrade="B";
                                                    }else if(fltObtainedMarks>=0.00&&fltObtainedMarks<=4.01){
                                                            strGrade="C";
                                                    }
                    }else if(fltMaxMarks==60.0){
                                                    if(fltObtainedMarks>=53.01&&fltObtainedMarks<=60.0){
                                                            strGrade="A+";
                                                    }else if(fltObtainedMarks>=41.01&&fltObtainedMarks<=53.01){
                                                            strGrade="A";
                                                    }else if(fltObtainedMarks>=29.01&&fltObtainedMarks<=41.01){
                                                            strGrade="B+";
                                                    }else if(fltObtainedMarks>=17.01&&fltObtainedMarks<=29.01){
                                                            strGrade="B";
                                                    }else if(fltObtainedMarks>=00.00&&fltObtainedMarks<=17.01){
                                                            strGrade="C";
                                                    }
                    }else if(fltMaxMarks==25.0){
                                                    if(fltObtainedMarks>=21.0&&fltObtainedMarks<=25.0){
                                                            strGrade="A1";
                                                    }else if(fltObtainedMarks>=16.0&&fltObtainedMarks<21.0){
                                                            strGrade="A2";
                                                    }else if(fltObtainedMarks>=11.0&&fltObtainedMarks<16.0){
                                                            strGrade="B1";
                                                    }else if(fltObtainedMarks>=7.0&&fltObtainedMarks<11.0){
                                                            strGrade="B2";
                                                    }else if(fltObtainedMarks>=30.5&&fltObtainedMarks<7){
                                                            strGrade="C";
                                                    }
        
                    }*/    
            }else{
                       
                                
               }           
                            
           }
                        
                        
                        
                        
        }else {
                  strGrade="-"; 
               }
               
         //  }
            object.put("Exam", strExam);
            object.put("RollNo",strRollNo);
            object.put("Subject", strSubject);
            object.put("MarksObtained", strGrade);
            object.put("Grade", strGrade);
            object.put("studentname", strStudentName);
            object.put("MaxMarks", strMaxMarks);
            object.put("MinMarks", strMinMarks);
            object.put("class", strClassSection);
            array.put(object);
    }while(rs.next());
    if(total!=0.0f){
                        
               float percentage=(total/maxTotal)*100;
               String strPercentage=String.format("%2.2f",percentage);
               if(strClass.length()<=2){
                   int cls=Integer.parseInt(strClass);
                   if(cls<10){
                                if(percentage>=90.00&&percentage<=100.01){
                                         strTotalGrade="A+";
                                }else if(percentage>=70.00&&percentage<90){
                                        strTotalGrade="A";
                                }else if(percentage>=50.00&&percentage<70){
                                        strTotalGrade="B+";
                                }else if(percentage>=30.00&&percentage<50.00){
                                        strTotalGrade="B";
                                }else if(percentage>=0.0&&percentage<30.00){
                                        strTotalGrade="C";
                               }
                   }else if(cls==10){
                       
                                        if(percentage>=90&&percentage<=100.0){
                                                strTotalGrade="A+";
                                          }else if(percentage>=80.00&&percentage<90.00){
                                                strTotalGrade="A";
                                          }else if(percentage>=70.00&&percentage<80.00){
                                                strTotalGrade="B+";
                                          }else if(percentage>=60.00&&percentage<70.00){
                                                strTotalGrade="B";
                                          }else if(percentage>=50.00&&percentage<60.00){
                                                strTotalGrade="C+";
                                          }else if(percentage>=35.00&&percentage<50.00){
                                                strTotalGrade="C";
                                          }else if(percentage>=0.0&&percentage<35.0){
                                                strTotalGrade="NC";
                                          }
                       
                   }
               
                   
               }else{
                   
               }
               
               
               
               
    }else{
        strTotalGrade="AB";
    }
               
 
   }catch(Exception e){
        e.printStackTrace();
  } 
}
     
     
     
     
     
     public void fetchExamResultDataBKBI(){
         
         
          try{
              float total=0.0f,maxTotal=0.0f;
            do
               {
                    JSONObject object = new JSONObject();
                    String strRollNo= rs.getString("RollNo");
                    String strExam= rs.getString("Exam");
                    String strExamId= rs.getString("ExamId");
                    String strClass= rs.getString("Class");
                    String strSubjectId = rs.getString("SubjectId");
                    String strClassSection=rs.getString("ClassSection");
                    String strScholastics=rs.getString("Scholastics");
                    String strSubject = rs.getString("Subject");
                    String strMarksObtained = rs.getString("Obtained");
                    String strStudentname = rs.getString("StudentName");
                    String strMaxMarks=rs.getString("MaxMarks");
                    String strMinMarks=rs.getString("MinMarks");
                    String strDate=rs.getString("Date");
                    float fltMaxMarks=Integer.parseInt(strMaxMarks);
                    if(!(strExamId.equalsIgnoreCase("79")&& strExamId.equalsIgnoreCase("1"))&& strScholastics.equalsIgnoreCase("0")&&strMarksObtained.equalsIgnoreCase("-9999")){
                        float fltObtMarks=Float.parseFloat(strMarksObtained);
                        maxTotal+=fltMaxMarks;
                        total+=fltObtMarks; 
                        
                     }else if(strScholastics.equalsIgnoreCase("0")){
                       
                         if(strMarksObtained.equalsIgnoreCase("-22222")){
                            strMarksObtained="A1";
                        }else if(strMarksObtained.equalsIgnoreCase("-33333")){
                            strMarksObtained="A";
                        }else if(strMarksObtained.equalsIgnoreCase("-44444")){
                            strMarksObtained="B1";
                        }else if(strMarksObtained.equalsIgnoreCase("-55555")){
                            strMarksObtained="B";
                        }else if(strMarksObtained.equalsIgnoreCase("-66666")){
                            strMarksObtained="C";
                        }
                       
                    }else if(strMarksObtained.equalsIgnoreCase("-9999")){
                         strMarksObtained="AB";
                         if(strScholastics.equalsIgnoreCase("1")){
                             maxTotal+=fltMaxMarks;
                         }
                         
                    }else{
                    total=-9999;
                    maxTotal=-9999;
               }
                    object.put("Exam", strExam);
                    object.put("ExamId", strExamId);
                    object.put("Subject", strSubject);
                    object.put("MarksObtained", strMarksObtained);
                    object.put("studentname", strStudentname);
                    object.put("MaxMarks", strMaxMarks);
                    object.put("MinMarks", strMinMarks);
                    object.put("class", strClassSection);
                    object.put("RollNo", strRollNo);
                    object.put("SubjectId", strSubjectId);
                    object.put("Date", strDate);
                   
                    array.put(object);
                     
                    
               }while(rs.next());
           float percentage;
            if(total==-9999||maxTotal==-9999){
               percentage=(total/maxTotal)*100;
           }else {
                percentage=-9999;
            }
            
           outObject.put("Total", total);
           outObject.put("maxTotal", maxTotal);
           outObject.put("TotalGrade","-9999");
           outObject.put("Percentage",percentage);
    }catch(Exception e){
        e.printStackTrace();
    }
         
         
         
     }
        public void fetchExamResultData(){
        try{
            do
               {
                    JSONObject object = new JSONObject();
                    String strExam= rs.getString("Exam");
                    String strClass= rs.getString("class");
                    String strSubject = rs.getString("Subject");
                    String strMarksObtained = rs.getString("Obtained");
                    String strStudentname = rs.getString("studentname");
                    String strMaxMarks=rs.getString("MaxMarks");
                    String strMinMarks=rs.getString("MinMarks");
                    String strRoll=rs.getString("RollNo");
//                 
             
                    object.put("Exam", strExam);
                    object.put("Subject", strSubject);
                    object.put("MarksObtained", strMarksObtained);
                    object.put("studentname", strStudentname);
                    object.put("MaxMarks", strMaxMarks);
                    object.put("MinMarks", strMinMarks);
                    object.put("class", strClass);
                    object.put("RollNo", strRoll);        
                array.put(object);
               }while(rs.next());
    
        }catch(Exception e){
      
        e.printStackTrace();
    
    }
    }
  public String examResultDisplay_Studentwise(){
      
     try
           {
               
            String sql = " CALL sp_and_get_marks_default(?)";
           
            fetchStudentData(examData,sql);
            
            if(rs.first())
           {
              
               fetchExamResultDataLater();
               String strTotalGrade="",strPercentage="";
               
               
               if(failFlag!=1){
               
                   float percentage=(total/maxTotal)*100;
               strPercentage=String.format("%2.2f",percentage);
                   if(percentage>=89.50&&percentage<=100.00){
                       strTotalGrade="A+";
                   }else if(percentage>=69.50&&percentage<89.50){
                       strTotalGrade="A";
                   }else if(percentage>=49.50&&percentage<69.50){
                       strTotalGrade="B+";
                   }else if(percentage>=34.50&&percentage<49.50){
                       strTotalGrade="B";
                   }else if(percentage>=00.00&&percentage<34.50){
                       strTotalGrade="Fail";
                   }
               }
               else{
                   strTotalGrade="Fail";
                   strPercentage="NA";
               }
               outObject.put("Status", "Success");
                outObject.put("Message", "Data found..");
                outObject.put("Total",total);
                outObject.put("maxTotal",maxTotal);
                outObject.put("TotalGrade",strTotalGrade);
                outObject.put("Percentage",strPercentage);
                outObject.put("Result", array);
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
        } 
      finally{
            closeDBConnection();
        }
  }
  
  
  
  
  
  
  public String examResultDisplay_Studentwise_GDGB(){
     try
           {
               
            String sql = " CALL sp_and_get_marks_default(?)";
           
            fetchStudentData(examData,sql);
            
            if(rs.first()){
              
               fetchExamMarksWithGradeGdgb();
               
               
               outObject.put("Status", "Success");
                outObject.put("Message", "Data found..");
                outObject.put("TotalGrade",strTotalGrade);
                outObject.put("Total","-9999");
                outObject.put("maxTotal","-9999");
                outObject.put("Percentage","-9999");
                outObject.put("Result", array);
         }else {
                printFaliureStatus(array);
                
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
  
  
  public String examResultDisplay_Studentwise_HBBN(){
     try
           {
               
               
               
               
            String sql = " CALL sp_and_get_marks_default(?)";
           
            fetchStudentData(examData,sql);
            
            if(rs.first()){
              
               fetchExamMarksWithGradeHBBN();
               
               
               outObject.put("Status", "Success");
                outObject.put("Message", "Data found..");
                outObject.put("TotalGrade",strTotalGrade);
                outObject.put("Total","-9999");
                outObject.put("maxTotal","-9999");
                outObject.put("Percentage","-9999");
                outObject.put("Result", array);
         }else {
                printFaliureStatus(array);
                
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
  public String examResultDisplay_Studentwise_BKBI(){
     try
           {
               
            String sql = " CALL sp_and_get_marks_default_bkbi(?)";
           
            fetchStudentData(examData,sql);
            
            if(rs.first()){
              
               fetchExamResultDataBKBI();
               
               
               outObject.put("Status", "Success");
                outObject.put("Message", "Data found..");
                /*outObject.put("TotalGrade",strTotalGrade);
                outObject.put("Total","-9999");
                outObject.put("maxTotal","-9999");
                outObject.put("Percentage","-9999");*/
                outObject.put("Result", array);
         }else {
                printFaliureStatus(array);
                
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
  
  
  public String getStaffResultDisplay(){
      try
           {
            String sql = " CALL sp_and_staff_get_exam_result(?)";
           
            fetchStaffExamData(examData,sql);
            
            if(rs.first())
           {
              
               fetchExamResultData();
               
               float percentage=(total/maxTotal)*100;
               String strPercentage=String.format("%2.2f",percentage);
               
               outObject.put("Status", "Success");
                outObject.put("Message", "Data found..");
                outObject.put("Total",total);
                outObject.put("maxTotal",maxTotal);
                outObject.put("Percentage",strPercentage);
                outObject.put("Result", array);
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
        } 
      finally{
            closeDBConnection();
        }
  }
  
  public String getStaffResultDisplayExamwise(){
        try
           {
                          
            //String sql = "CALL `Sp_StudentMarksList_ClassWise`(?,?)";
                String sql = "CALL Sp_Get_GDGB_StudentMarksList_Classwise(?,?)";
                 
           
            fetchStaffExamDatByExam(examData,sql);
           ResultSetMetaData rsmd = rs.getMetaData();
           int columnCount = rsmd.getColumnCount();
          
           if(rs.first())
           {
               do{
                   JSONObject object= new JSONObject();
                   for(int i=1;i<=columnCount;i++){
                       
                       String data=rsmd.getColumnName(i);
                       
                       String value=rs.getString(i);
                       if(value.equalsIgnoreCase("-9999")){
                           value="AB";
                       }
                       object.put(data,value);
                   }
                   array.put(object);
               }while(rs.next());
               
               outObject.put("Status", "Success");
                outObject.put("Message", "Data found..");
//                outObject.put("Total",total);
//                outObject.put("maxTotal",maxTotal);
//                outObject.put("Percentage",strPercentage);
                outObject.put("Result", array);
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
        } 
      finally{
            closeDBConnection();
        }
  }
  
  
  public String getStaffExamResultTabularGDGB(){
      try
           {
                          
           // String sql = "CALL `Sp_Get_GDGB_StudentMarksList_Classwise`(?,?)";
           String sql="";
            String exmID=examData.getString("ExamId");
           if(exmID.equalsIgnoreCase("31")){
             sql="CALL Sp_Get_GDGB_StudentMarkslist_Midterm_Result(?,?)";
           }else if(exmID.equalsIgnoreCase("32")){
                sql=" Call Sp_Get_GDGB_StudentMarkslist_Annual_Result(?,?)";
           }else if(exmID.equalsIgnoreCase("103")){
               sql=" call Sp_Get_GDGB_StudentMarkslist_Final_Result(?,?)";
           } 
           else{
               sql = "CALL Sp_Get_GDGB_StudentMarksList_Classwise(?,?)";
           }
            
            
           fetchStaffExamDatByExam(examData,sql); 
           ResultSetMetaData rsmd = rs.getMetaData();
           int columnCount = rsmd.getColumnCount();
           
           
           
           //for (int i = 1; i <= rsmd.getColumnCount(); i++){ String name = rsmd.getColumnName(i);}

           if(rs.first())
           {
               do{
                   JSONObject object= new JSONObject();
                   for(int i=1;i<=columnCount;i++){
                       
                       String data=rsmd.getColumnName(i);
                       
                      
                       String value=rs.getString(i);
                      if(value != null && !value.isEmpty()){
                               object.put(data,value); 
                             }else{
                           object.put(data,"-");
                      }
                       /*
                       if(value.equalsIgnoreCase("-9999")){
                           value="AB";
                       } */
                      
                   }
                   array.put(object);
               }while(rs.next());
              // fetchExamResultData();
               
//               
               outObject.put("Status", "Success");
               outObject.put("Message", "Data found..");
//               outObject.put("Total",total);
//               outObject.put("maxTotal",maxTotal);
//               outObject.put("Percentage",strPercentage);
               outObject.put("Result", array);
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
        }
        finally{
            closeDBConnection();
        }
  }
  
  
  
  
  public String getStaffExamResultTabularMNJT(){
      try
           {
                          
           // String sql = "CALL `Sp_Get_GDGB_StudentMarksList_Classwise`(?,?)";
           String sql="";
            String exmID=examData.getString("ExamId");
           if(exmID.equalsIgnoreCase("93")){
             sql="CALL Sp_Get_MNJT_StudentMarkslist_Midterm_Result(?,?)";
           }else if(exmID.equalsIgnoreCase("94")){
                sql=" Call Sp_Get_MNJT_StudentMarkslist_Annual_Result(?,?)";
           }else if(exmID.equalsIgnoreCase("104")){
               sql=" call Sp_Get_MNJT_StudentMarkslist_Final_Result(?,?)";
           } 
           else{
               sql = "CALL Sp_Get_MNJT_StudentMarksList_Classwise(?,?)";
           }
            
            
           fetchStaffExamDatByExam(examData,sql); 
           ResultSetMetaData rsmd = rs.getMetaData();
           int columnCount = rsmd.getColumnCount();
           
           
           
           //for (int i = 1; i <= rsmd.getColumnCount(); i++){ String name = rsmd.getColumnName(i);}

           if(rs.first())
           {
               do{
                   JSONObject object= new JSONObject();
                   for(int i=1;i<=columnCount;i++){
                       
                       String data=rsmd.getColumnName(i);
                       
                      
                       String value=rs.getString(i);
                      if(value != null && !value.isEmpty()){
                               object.put(data,value); 
                             }else{
                           object.put(data,"-");
                      }
                       /*
                       if(value.equalsIgnoreCase("-9999")){
                           value="AB";
                       } */
                      
                   }
                   array.put(object);
               }while(rs.next());
              // fetchExamResultData();
               
//               
               outObject.put("Status", "Success");
               outObject.put("Message", "Data found..");
//               outObject.put("Total",total);
//               outObject.put("maxTotal",maxTotal);
//               outObject.put("Percentage",strPercentage);
               outObject.put("Result", array);
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
        }
        finally{
            closeDBConnection();
        }
  }
  
    public String getStaffExamResultTabularHBBN(){
      try
           {
                          
           // String sql = "CALL `Sp_Get_GDGB_StudentMarksList_Classwise`(?,?)";
           String sql="";
            String exmID=examData.getString("ExamId");
           if(exmID.equalsIgnoreCase("77")){
             sql="CALL Sp_Get_HBBN_StudentMarkslist_Midterm_Result(?,?)";
           }else if(exmID.equalsIgnoreCase("80")){
                sql=" Call Sp_Get_HBBN_StudentMarkslist_SecondSem_Result(?,?)";
           }else if(exmID.equalsIgnoreCase("102")){
                sql=" Call Sp_Get_HBBN_StudentMarkslist_Annual_Result(?,?)";
           }else{
               sql = "CALL Sp_Get_HBBN_StudentMarksList_Classwise(?,?)";
           }
            
            
           fetchStaffExamDatByExam(examData,sql); 
           ResultSetMetaData rsmd = rs.getMetaData();
           int columnCount = rsmd.getColumnCount();
           
           
           
           //for (int i = 1; i <= rsmd.getColumnCount(); i++){ String name = rsmd.getColumnName(i);}

           if(rs.first())
           {
               do{
                   JSONObject object= new JSONObject();
                   for(int i=1;i<=columnCount;i++){
                       
                       String data=rsmd.getColumnName(i);
                       
                      
                       String value=rs.getString(i);
                      if(value != null && !value.isEmpty()){
                               object.put(data,value); 
                             }else{
                           object.put(data,"-");
                      }
                       /*
                       if(value.equalsIgnoreCase("-9999")){
                           value="AB";
                       } */
                      
                   }
                   array.put(object);
               }while(rs.next());
              // fetchExamResultData();
               
//               
               outObject.put("Status", "Success");
               outObject.put("Message", "Data found..");
//               outObject.put("Total",total);
//               outObject.put("maxTotal",maxTotal);
//               outObject.put("Percentage",strPercentage);
               outObject.put("Result", array);
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
        }
        finally{
            closeDBConnection();
        }
  }
   
  
  
  
  
  public String getStaffExamResultGDGB(){
      try
           {
                          
                            
            String sql = "CALL `sp_and_get_classwise_student_marks`(?,?)";
           
            fetchStaffExamDatByExam(examData,sql);

            if(rs.first())
           {
              
               do{
                   JSONObject object =new JSONObject();
                   String strRollNo=rs.getString("RollNo");
                  
                   String strStudentName=rs.getString("StudentName");
                   String strClass=rs.getString("Class");
                   String strExam=rs.getString("Exam");
                   String strSubject=rs.getString("Subject");
                   String strGrade=rs.getString("Grade");
                    
                   object.put("RollNo",strRollNo);
                   object.put("StudentName",strStudentName);
                   object.put("Class",strClass);
                   object.put("Exam",strExam);
                   object.put("Subject",strSubject);
                   object.put("Grade",strGrade);
                   
                   array.put(object);
               
               }while(rs.next());
              // fetchExamResultData();
              /* JSONArray outputArray= new JSONArray();
               JSONObject tempObj = null;
             for(int i=0;i<array.length();i++){
                 JSONObject obj = array.getJSONObject(i);
                 int length = array.length();
                 if(i < length) {
                     tempObj = array.getJSONObject(i+1);
                     System.out.println("tempObject: i==0 "+tempObj.toString() +" obj: "+obj);
                 } else {
                     tempObj = obj;
                     System.out.println("tempObject: "+tempObj.toString());
                 }
                 
                 
             }*/
              
//               
               outObject.put("Status", "Success");
               outObject.put("Message", "Data found..");
               outObject.put("Result", array);
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
        }
        finally{
            closeDBConnection();
        }
  }
  
  public String getStaffExamResultRVK(){
      try
           {
                int maxMarksTotal=0;
             String sql2="call Sp_Get_MaxMinMarksByClsExamId(?,?)";             
              int clsId= Integer.parseInt(examData.getString("ClassId"));
        int exmId= Integer.parseInt(examData.getString("ExamId"));
        
        callable= con.getConnection().prepareCall(sql2);
        callable.setInt(1,clsId);
        JSONArray subArray=new JSONArray();
        callable.setInt(2,exmId);
         ResultSet rs1= callable.executeQuery();
         if(rs1.first())  
           {
             
                do
               {
                    JSONObject object = new JSONObject();
                    String strScholastics= rs1.getString("Scholastics");
                    String strMaxMarks= rs1.getString("MaxMarks");
                     String strMinMarks= rs1.getString("MinMarks");
                    String strSubject = rs1.getString("Subject");
                    String strSubject_Prifix = rs1.getString("Subject_Prifix");
                      int scholastics=Integer.parseInt(strScholastics);
                    
                        if(scholastics==1){
                            int MaxMarks=Integer.parseInt(strMaxMarks);
                        maxMarksTotal+=MaxMarks;
                    }
             
                    object.put("Scholastics", strScholastics);
                    object.put("MaxMarks", strMaxMarks);
                    object.put("Subject", strSubject);
                    
                    object.put("MinMarks", strMinMarks);   
                    object.put("Subject_Prifix", strSubject_Prifix);   
                subArray.put(object);
               }while(rs1.next());     
           }
         String sql = "CALL `Sp_Get_RVK_StudentMarksList_Classwise`(?,?)";
           
               
               
            fetchStaffExamDatByExam(examData,sql);
            
           ResultSetMetaData rsmd = rs.getMetaData();
           int columnCount = rsmd.getColumnCount();
           //Float total=0.0f;
           
           
           //for (int i = 1; i <= rsmd.getColumnCount(); i++){ String name = rsmd.getColumnName(i);}

           if(rs.first())
           {
               do{
                   float total=0.0f;
                   int failFlag=0;
                   JSONObject object= new JSONObject();
                   for(int i=1;i<=columnCount;i++){
                       
                       String data=rsmd.getColumnName(i);
                       
                      
                       String value=rs.getString(i);
                       //if(!data.equalsIgnoreCase("StudentDetailsId")&&!data.equalsIgnoreCase("RollNo")&&!data.equalsIgnoreCase("Name")){
                       if(!data.equalsIgnoreCase("StudentDetailsId")&&!data.equalsIgnoreCase("RollNo")&&!data.equalsIgnoreCase("Name")&&!data.equalsIgnoreCase("Student_History_Id")){
                       Float fltmarks=0.0f;
                        if(value!=null&&!value.isEmpty()&&!value.equalsIgnoreCase("-")){
                            
                            fltmarks=Float.parseFloat(value);
                            if(!(fltmarks<0.0)){
                          
                                for(int subPos=0;subPos<subArray.length();subPos++){
                              JSONObject subObject=subArray.getJSONObject(subPos);
                            String subScholastics=subObject.getString("Scholastics");
                              String sub= subObject.getString("Subject_Prifix");
                             Float fltMinMarks=Float.parseFloat(subObject.getString("MinMarks"));
                              if(sub.equalsIgnoreCase(data)&&subScholastics.equalsIgnoreCase("1")){
                                total+=fltmarks;   
                                 
                                if(fltmarks<fltMinMarks){
                                    failFlag=1;
                                }
                                break;
                             }else{
                            if(fltmarks==-23232){
                               value="A+";
                           }else if (fltmarks==-33333){
                               value="A";
                           }else if (fltmarks==-45454){
                               value="B+";
                           }else if (fltmarks==-55555){
                               value="B";
                           }else if(fltmarks==-44444){
                               value="B1";
                           }
                                 else if (fltmarks==-67676){
                               value="C+";
                           }else if (fltmarks==-66666){
                               value="C";
                           }else if(fltmarks==-9999){
                               value="AB";
                           }        
                        }
                           }
                           //total+=fltmarks;
                       }else{
                         
                            if(fltmarks==-23232){
                               value="A+";
                           }else if (fltmarks==-33333){
                               value="A";
                           }else if (fltmarks==-45454){
                               value="B+";
                           }else if(fltmarks==-44444){
                               value="B1";
                           }
                           else if (fltmarks==-55555){
                               value="B";
                           }else if (fltmarks==-67676){
                               value="C+";
                           }else if (fltmarks==-66666){
                               value="C";
                           }else if(fltmarks==-9999){
                               value="AB";
                           }        
                           
                           
                           
                       }
                            
                        }else{
                            value="-";
                        }
                       
                       
                               }
                       /*
                       
                       if(value.equalsIgnoreCase("-9999")){
                           value="AB";
                       } */
                       object.put(data,value);
                       
                   }
                   
                   /*for(int j=0;j<subArray.length();j++){
                       JSONObject subObject=subArray.getJSONObject(j);
                       String strSolastic=subObject.getString("Scholastics");
                       int schlastic=Integer.parseInt(strSolastic);
                       if(schlastic==1){
                       String strSub= subObject.getString("Subject_Prifix");
                       String strMarks=object.getString(strSub);
                      if(strMarks != null && !strMarks.isEmpty()){
                      
                          
                             Float fltMrks=Float.parseFloat(strMarks);
                       String strMinMrks=subObject.getString("MinMarks");
                       Float fltMinMrks=Float.parseFloat(strMinMrks);;
                       if(fltMrks<fltMinMrks){
                           failFlag=1;
                       }
//   object.put(data,value); 
                             }else{
                           //object.put(data,"-");
                      } 
                      
                   }
                   }*/    
                  if(failFlag!=1){
                      Float percentage=(total/maxMarksTotal)*100;
                   
                   String formattedTotal = String.format("%2.2f", total);
                  // String formattedMaxMarksTotal = String.format("%.02f", maxMarksTotal);
                   String formattedPercentage = String.format("%2.2f", percentage);
                   if(percentage>=89.50&&percentage<=100.00){
                       strTotalGrade="A+";
                   }else if(percentage>=69.50&&percentage<89.50){
                       strTotalGrade="A";
                   }else if(percentage>=49.50&&percentage<69.50){
                       strTotalGrade="B+";
                   }else if(percentage>=34.50&&percentage<49.50){
                       strTotalGrade="B";
                   }else if(percentage>=00.00&&percentage<34.50){
                       strTotalGrade="Fail";
                   }
                   
                   
                   
                   
                   object.put("Total",formattedTotal);
                   object.put("MaxTotal",maxMarksTotal);
                   object.put("Percentage",formattedPercentage);
                   object.put("TotalGrade", strTotalGrade);
                  }else{
                   
                   String formattedTotal = String.format("%2.2f", total);
                  // String formattedMaxMarksTotal = String.format("%.02f", maxMarksTotal);
                  // String formattedPercentage = String.format("%.02f", finPercentage);
                   object.put("Total",formattedTotal);
                   object.put("MaxTotal",maxMarksTotal);
                   object.put("Percentage","NA");
                    object.put("TotalGrade", "Fail");
                   
                  }
                   array.put(object);
                    
               }while(rs.next());
              // fetchExamResultData();
               
//               
               outObject.put("Status", "Success");
               outObject.put("Message", "Data found..");
               outObject.put("Total",total);
               outObject.put("maxTotal",maxMarksTotal);
//               outObject.put("Percentage",strPercentage);
               outObject.put("Result", array);
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
        }
        finally{
            closeDBConnection();
        }
  }
   public String getStaffExamResultGDGBDemo(){
     
      try
           {
                          
                            
            String sql = "CALL `sp_and_get_classwise_student_marks`(?,?)";
           
            fetchStaffExamDatByExam(examData,sql);

            if(rs.first())
           {
              String curStud,PrevStud;
               int studPos=0;
              do{
                   JSONObject object =new JSONObject();
                   String strRollNo=rs.getString("RollNo");
                  JSONObject inObject;
                   String strFirstName=rs.getString("FirstName");
                  
                   
                   String strClass=rs.getString("class");
                   String strExam=rs.getString("Exam");
                   String strSubject=rs.getString("Subject");
                   String strObtainedMarks=rs.getString("ObtainedMarks");
                    
                  object.put("RollNo",strRollNo);
                   object.put("FirstName",strFirstName); 
                   object.put("Class",strClass);
                   object.put("Exam",strExam);
                   object.put("Subject",strSubject);
                   object.put("ObtainedMarks",strObtainedMarks);
                   
                   studPos++;
                   
                   array.put(object);
               
               }while(rs.next());
              // fetchExamResultData();
             /*  JSONArray outputArray= new JSONArray();
               JSONObject tempObj = null;
             for(int i=0;i<array.length();i++){
                 JSONObject obj = array.getJSONObject(i);
                 int length = array.length();
                 if(i < length) {
                     tempObj = array.getJSONObject(i+1);
                     System.out.println("tempObject: i==0 "+tempObj.toString() +" obj: "+obj);
                 } else {
                     tempObj = obj;
                     System.out.println("tempObject: "+tempObj.toString());
                 }
                 
                 
             }*/
//               
               outObject.put("Status", "Success");
               outObject.put("Message", "Data found..");
//               outObject.put("Total",total);
//               outObject.put("maxTotal",maxTotal);
//               outObject.put("Percentage",strPercentage);
               outObject.put("Result", array);
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
        }
        finally{
            closeDBConnection();
        }  
       
       
       
   }
   
   
   
   
   
   
  public String getStaffExamwiseSubjectList(){
      try
           {
            String sql = " CALL sp_and_get_exmwise_subjectList(?,?)";
           
            fetchStaffExamDatByExam(examData,sql);
            
            if(rs.first())  
           {
              
                do
               {
                    JSONObject object = new JSONObject();
                    String strExam= rs.getString("Exam");
                    String strClass= rs.getString("class");
                    String strSubject = rs.getString("Subject");
                    
//                 
             
                    object.put("Exam", strExam);
                    object.put("Subject", strSubject);
                    
                    object.put("class", strClass);
                       
                array.put(object);
               }while(rs.next());
               
               outObject.put("Status", "Success");
                outObject.put("Message", "Data found..");
                outObject.put("Result", array);
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
        } 
      finally{
            closeDBConnection();
        }
  }
  
  public String getSubjectwiseResultRVK(){
      Timestamp tmpScduleSetDate=null;
      try{
         String sql = "  CALL `sp_and_get_classwise_result_by_subject`(?,?,?,?)";
           
            int clsId= Integer.parseInt(examData.getString("ClassId"));
            int exmId= Integer.parseInt(examData.getString("ExamId"));
            int subId= Integer.parseInt(examData.getString("SubjectId"));
            String strSchduleDate=examData.getString("ExamSchduleDate");
             if(!(strSchduleDate.equals("")))
             {
                 //Date schduleSetDate = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").parse(strSchduleDate);
               Date schduleSetDate = new SimpleDateFormat("yyyy-MM-dd").parse(strSchduleDate);
                tmpScduleSetDate=new Timestamp(schduleSetDate.getTime());
             }
             callable= con.getConnection().prepareCall(sql);
        callable.setInt(1,exmId);
        callable.setInt(2,clsId);
        callable.setInt(3,subId);
        callable.setTimestamp(4,tmpScduleSetDate);
        rs=callable.executeQuery();
        if(rs.next()){
            do{
            JSONObject object = new JSONObject();
                    
                    String strRollNo= rs.getString("RollNo");
                    String strStudentname= rs.getString("Studentname");
                    String strClass = rs.getString("Class");
                    String strSubject = rs.getString("Subject");
                    String strMaxMarks= rs.getString("MaxMarks");
                    String strMinMarks= rs.getString("MinMarks");
                    String strMarksObtained= rs.getString("ObtainedMarks");
                   String strScholastics=rs.getString("Scholastics");
                    String strGrade=rs.getString("Grade");
                   String strStatus="Pass";
                   if(strScholastics.equalsIgnoreCase("1")){
                        float marks=Float.parseFloat(strMarksObtained);
                        float minMarks=Float.parseFloat(strMinMarks);
                        if(marks<minMarks){
                            strStatus="Fail";
                        }
                    }else{
                       strStatus="-9999";
                       if(strMarksObtained.equalsIgnoreCase("-23232")){
                           strMarksObtained="A+";
                           
                       }else if(strMarksObtained.equalsIgnoreCase("-33333")){
                           strMarksObtained="A";
                           
                       }else if(strMarksObtained.equalsIgnoreCase("-45454")){
                           strMarksObtained="B+";
                           
                       }else if(strMarksObtained.equalsIgnoreCase("-44444")){
                           strMarksObtained="B";
                           
                       }else if(strMarksObtained.equalsIgnoreCase("-67676")){
                           strMarksObtained="C+";
                           
                       }else if(strMarksObtained.equalsIgnoreCase("-66666")){
                           strMarksObtained="C";
                       }
                   }
                    object.put("Exam", strExam);
                    object.put("RollNo",strRollNo);
                    object.put("Subject", strSubject);
                    object.put("Scholastics", strScholastics);
                    object.put("MarksObtained", strMarksObtained);
                    object.put("Studentname", strStudentname);
                    object.put("MaxMarks", strMaxMarks);
                    object.put("MinMarks", strMinMarks);
                    object.put("class", strClass); 
                    object.put("Status",strGrade);
                    array.put(object);
                }while(rs.next());
                
            printSuccessStatus(array);
           
        }else {
                printFaliureStatus(array);
                
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
  
  public String getSubjectwiseResultGDGB(){
      Timestamp tmpScduleSetDate=null;
              
      try
           {
            String sql = "  CALL `sp_and_get_classwise_result_by_subject`(?,?,?,?)";
           
            int clsId= Integer.parseInt(examData.getString("ClassId"));
            int exmId= Integer.parseInt(examData.getString("ExamId"));
            int subId= Integer.parseInt(examData.getString("SubjectId"));
            String strSchduleDate=examData.getString("ExamSchduleDate");
             if(!(strSchduleDate.equals("")))
             {
                 //Date schduleSetDate = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").parse(strSchduleDate);
               Date schduleSetDate = new SimpleDateFormat("yyyy-MM-dd").parse(strSchduleDate);
                tmpScduleSetDate=new Timestamp(schduleSetDate.getTime());
             }
             callable= con.getConnection().prepareCall(sql);
        callable.setInt(1,exmId);
        callable.setInt(2,clsId);
        callable.setInt(3,subId);
        callable.setTimestamp(4,tmpScduleSetDate);
        rs=callable.executeQuery();
            if(rs.first())  
           {
              
                do{
                    JSONObject object = new JSONObject();
                    
                    String strRollNo= rs.getString("RollNo");
                    String strStudentname= rs.getString("Studentname");
                    String strClass = rs.getString("Class");
                    String strSubject = rs.getString("Subject");
                    String strMaxMarks= rs.getString("MaxMarks");
                    String strMinMarks= rs.getString("MinMarks");
                    String strMarksObtained= rs.getString("ObtainedMarks");
                    String strGrade=rs.getString("Grade");
                    String strScholastics=rs.getString("Scholastics");
                  /*  
                    int scholastics=Integer.parseInt(rs.getString("Scholastics"));
                   
                  // String strGrade="";
                    if(strMarksObtained.equalsIgnoreCase("AB")){
                        strGrade="Absent";
                    }
                    else {
                    int min=Integer.parseInt(strMinMarks);
                    float obt=Float.parseFloat(strMarksObtained);
                     
                    if(obt<min){
                        strGrade="Fail";
                    }
                    else {
                        strGrade="Pass";
                    }
                    }
                    
                    
                    
                    
                    if(scholastics==0){
                        
                        if(strMarksObtained.equalsIgnoreCase("-22222")){
                            strGrade="A1";
                        }else if(strMarksObtained.equalsIgnoreCase("-33333")){
                            strGrade="A";
                        }else if(strMarksObtained.equalsIgnoreCase("-23232")){
                            strGrade="A+";
                        } else if(strMarksObtained.equalsIgnoreCase("-44444")){
                            strGrade="B1";
                            
                        } else if(strMarksObtained.equalsIgnoreCase("-45454")){
                            strGrade="B+";
                        }else if(strMarksObtained.equalsIgnoreCase("-55555")){
                            strGrade="B";
                        }else if(strMarksObtained.equalsIgnoreCase("-67676")){
                            strGrade="C+";
                        }else if(strMarksObtained.equalsIgnoreCase("-66666")){
                            strGrade="C";
                        }else if(strMarksObtained.equalsIgnoreCase("AB")){
                            strGrade="AB";
                        }
                    }else { 
                            Float fltMaxMarks=Float.parseFloat(strMaxMarks);
                            Float fltObtainedMarks=Float.parseFloat(strMarksObtained);
                            if(!strMarksObtained.equalsIgnoreCase("AB")){
                                if(fltMaxMarks==10.0){
                                    if(fltObtainedMarks>=9.5&&fltObtainedMarks<=10.0){
                                        strGrade="A1";
                                    }else if(fltObtainedMarks>=8.5&&fltObtainedMarks<=9.4){
                                        strGrade="A2";
                                    }else if(fltObtainedMarks>=7.5&&fltObtainedMarks<=8.4){
                                        strGrade="B1";
                                    }else if(fltObtainedMarks>=6.5&&fltObtainedMarks<=7.4){
                                        strGrade="B2";
                                    }else if(fltObtainedMarks>=5.5&&fltObtainedMarks<=6.4){
                                        strGrade="C1";
                                    }else if(fltObtainedMarks>=4.5&&fltObtainedMarks<=5.4){
                                        strGrade="C2";
                                    }else if(fltObtainedMarks>=3.5&&fltObtainedMarks<=4.4){
                                        strGrade="D";
                                    }else if(fltObtainedMarks>=2.5&&fltObtainedMarks<=3.4){
                                        strGrade="E1";
                                    }else if(fltObtainedMarks>=0.0&&fltObtainedMarks<=2.4){
                                        strGrade="E2";
                                        }

                        }else if(fltMaxMarks==30.0){
                                if(fltObtainedMarks>=27.5&&fltObtainedMarks<=30.0){
                                        strGrade="A1";
                                }else if(fltObtainedMarks>=24.5&&fltObtainedMarks<=27.4){
                                        strGrade="A2";
                                }else if(fltObtainedMarks>=21.5&&fltObtainedMarks<=24.4){
                                        strGrade="B1";
                                }else if(fltObtainedMarks>=18.5&&fltObtainedMarks<=21.4){
                                        strGrade="B2";
                                }else if(fltObtainedMarks>=15.5&&fltObtainedMarks<=18.4){
                                        strGrade="C1";
                                }else if(fltObtainedMarks>=12.5&&fltObtainedMarks<=15.4){
                                        strGrade="C2";
                                }else if(fltObtainedMarks>=9.5&&fltObtainedMarks<=12.4){
                                        strGrade="D";
                                }else if(fltObtainedMarks>=6.5&&fltObtainedMarks<=9.4){
                                        strGrade="E1";
                                }else if(fltObtainedMarks>=0.0&&fltObtainedMarks<=6.4){
                                        strGrade="E2";
                                    }
                        }else if(fltMaxMarks==5.0){
                                if(fltObtainedMarks==5.0){
                                        strGrade="A";
                                }else if(fltObtainedMarks==4.0){
                                        strGrade="B";
                                }else if(fltObtainedMarks==3.0){
                                        strGrade="C";
                                }else if(fltObtainedMarks==2.0){
                                        strGrade="D";
                                }else if(fltObtainedMarks==1.0){
                                        strGrade="E";
                            }
                        }else if(fltMaxMarks==80.0){
                                if(fltObtainedMarks>72.0&&fltObtainedMarks<=80.0){
                                        strGrade="A1";
                                }else if(fltObtainedMarks>64.0&&fltObtainedMarks<=72.0){
                                        strGrade="A2";
                                }else if(fltObtainedMarks>56.0&&fltObtainedMarks<=64.0){
                                        strGrade="B1";
                                }else if(fltObtainedMarks>48.0&&fltObtainedMarks<=56.0){
                                        strGrade="B2";
                                }else if(fltObtainedMarks>40.0&&fltObtainedMarks<=48.0){
                                        strGrade="C1";
                                }else if(fltObtainedMarks>32.0&&fltObtainedMarks<=40.0){
                                        strGrade="C2";
                                }else if(fltObtainedMarks>24.0&&fltObtainedMarks<=32.0){
                                        strGrade="D";
                                }else if(fltObtainedMarks>=0.0&&fltObtainedMarks<=24.0){
                                        strGrade="E";
                                }
                        
                        }else if(fltMaxMarks==50.0){
                                if(fltObtainedMarks>=45.5&&fltObtainedMarks<=50.0){
                                        strGrade="A1";
                                }else if(fltObtainedMarks>=40.5&&fltObtainedMarks<=45.4){
                                        strGrade="A2";
                                }else if(fltObtainedMarks>=35.5&&fltObtainedMarks<=40.4){
                                        strGrade="B1";
                                }else if(fltObtainedMarks>=30.5&&fltObtainedMarks<=35.4){
                                        strGrade="B2";
                                }else if(fltObtainedMarks>=25.5&&fltObtainedMarks<=30.4){
                                        strGrade="C1";
                                }else if(fltObtainedMarks>=20.5&&fltObtainedMarks<=25.4){
                                        strGrade="C2";
                                }else if(fltObtainedMarks>=15.5&&fltObtainedMarks<=20.4){
                                        strGrade="D";
                                }else if(fltObtainedMarks>=10.5&&fltObtainedMarks<=15.4){
                                        strGrade="E1";
                                }else if(fltObtainedMarks>=0.0&&fltObtainedMarks<=10.4){
                                        strGrade="E2";
                                }
                        
                        }else if(fltMaxMarks==100.0){
                                if(fltObtainedMarks>=91.0&&fltObtainedMarks<=100.0){
                                       strGrade="A1";
                                }else if(fltObtainedMarks>=81.0&&fltObtainedMarks<91.0){
                                       strGrade="A2";
                                }else if(fltObtainedMarks>=71.0&&fltObtainedMarks<81.0){
                                       strGrade="B1";
                                }else if(fltObtainedMarks>=61.0&&fltObtainedMarks<71.0){
                                       strGrade="B2";
                                }else if(fltObtainedMarks>=51.0&&fltObtainedMarks<61.0){
                                       strGrade="C1";
                                }else if(fltObtainedMarks>=41.0&&fltObtainedMarks<50.0){
                                       strGrade="C2";
                                }else if(fltObtainedMarks>=33.0&&fltObtainedMarks<41.0){
                                       strGrade="D";
                                }else if(fltObtainedMarks>=21.0&&fltObtainedMarks<33.0){
                                       strGrade="E1";
                                }else if(fltObtainedMarks>=0.0&&fltObtainedMarks<21){
                                       strGrade="E2";
                                }
                        }else if(fltMaxMarks==20.0){
                                if(fltObtainedMarks>=45.5&&fltObtainedMarks<=50.0){
                                        strGrade="A1";
                                }else if(fltObtainedMarks>=40.5&&fltObtainedMarks<=45.4){
                                        strGrade="A2";
                                }else if(fltObtainedMarks>=35.5&&fltObtainedMarks<=40.4){
                                        strGrade="B1";
                                }else if(fltObtainedMarks>=30.5&&fltObtainedMarks<=35.4){
                                        strGrade="B2";
                                }else if(fltObtainedMarks>=25.5&&fltObtainedMarks<=30.4){
                                        strGrade="C1";
                                }else if(fltObtainedMarks>=20.5&&fltObtainedMarks<=25.4){
                                        strGrade="C2";
                                }else if(fltObtainedMarks>=15.5&&fltObtainedMarks<=20.4){
                                        strGrade="D";
                                }else if(fltObtainedMarks>=10.5&&fltObtainedMarks<=15.4){
                                        strGrade="E1";
                                }else if(fltObtainedMarks>=0.0&&fltObtainedMarks<=10.4){
                                        strGrade="E2";
                                }
                        
                        }else if(fltMaxMarks==40.0){
                                if(fltObtainedMarks>=45.5&&fltObtainedMarks<=50.0){
                                    strGrade="A1";
                                }else if(fltObtainedMarks>=40.5&&fltObtainedMarks<=45.4){
                                    strGrade="A2";
                                }else if(fltObtainedMarks>=35.5&&fltObtainedMarks<=40.4){
                                    strGrade="B1";
                                }else if(fltObtainedMarks>=30.5&&fltObtainedMarks<=35.4){
                                    strGrade="B2";
                                }else if(fltObtainedMarks>=25.5&&fltObtainedMarks<=30.4){
                                    strGrade="C1";
                                }else if(fltObtainedMarks>=20.5&&fltObtainedMarks<=25.4){
                                    strGrade="C2";
                                }else if(fltObtainedMarks>=15.5&&fltObtainedMarks<=20.4){
                                    strGrade="D";
                                }else if(fltObtainedMarks>=10.5&&fltObtainedMarks<=15.4){
                                    strGrade="E1";
                                }else if(fltObtainedMarks>=0.0&&fltObtainedMarks<=10.4){
                                    strGrade="E2";
                                }
                        }else if(fltMaxMarks==60.0){
                                if(fltObtainedMarks>=54.5&&fltObtainedMarks<=60.0){
                                    strGrade="A1";
                                }else if(fltObtainedMarks>=48.5&&fltObtainedMarks<=54.4){
                                    strGrade="A2";
                                }else if(fltObtainedMarks>=42.5&&fltObtainedMarks<=48.4){
                                    strGrade="B1";
                                }else if(fltObtainedMarks>=36.5&&fltObtainedMarks<=42.4){
                                    strGrade="B2";
                                }else if(fltObtainedMarks>=30.5&&fltObtainedMarks<=36.4){
                                    strGrade="C1";
                                }else if(fltObtainedMarks>=24.5&&fltObtainedMarks<=30.4){
                                    strGrade="C2";
                                }else if(fltObtainedMarks>=18.5&&fltObtainedMarks<=24.4){
                                    strGrade="D";
                                }else if(fltObtainedMarks>=12.5&&fltObtainedMarks<=18.4){
                                    strGrade="E1";
                                }else if(fltObtainedMarks>=0.0&&fltObtainedMarks<=12.4){
                                    strGrade="E2";
                                }
                        }else if(fltMaxMarks==25.0){
                                if(fltObtainedMarks>=21.0&&fltObtainedMarks<=25.0){
                                    strGrade="A1";
                                }else if(fltObtainedMarks>=16.0&&fltObtainedMarks<21.0){
                                    strGrade="A2";
                                }else if(fltObtainedMarks>=11.0&&fltObtainedMarks<16.0){
                                    strGrade="B1";
                                }else if(fltObtainedMarks>=7.0&&fltObtainedMarks<11.0){
                                    strGrade="B2";
                                }else if(fltObtainedMarks>=30.5&&fltObtainedMarks<7){
                                    strGrade="C";
                                }
        
                        }    
                    }       
                }
                    
               */
            object.put("Exam", strExam);
            object.put("RollNo",strRollNo);
            object.put("Subject", strSubject);
            object.put("Grade", strGrade);
            object.put("MarksObtained", strMarksObtained);
            object.put("Studentname", strStudentname);
            object.put("MaxMarks", strMaxMarks);
            object.put("MinMarks", strMinMarks);
            object.put("class", strClass);
            object.put("Scholastics", strScholastics);
        
            array.put(object);
    }while(rs.next());
        
               printSuccessStatus(array);
           }else {
                printFaliureStatus(array);
                
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
  
  
  public String examResultDisplayExamwise_GDGB(){
      try
           {
                          
            String sql = "  CALL sp_and_get_marks_by_exam(?,?);";
           
            fetchStudentWithExamData(examData,sql);
           
           if(rs.first())
           {
               fetchExamMarksWithGradeGdgb();
              
               outObject.put("Status", "Success");
                outObject.put("Message", "Data found..");
                outObject.put("TotalGrade",strTotalGrade);
                outObject.put("Total","-9999");
                outObject.put("maxTotal","-9999");
                outObject.put("Percentage","-9999");
                outObject.put("Result", array);
         }else {
                       printFaliureStatus(array);
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
  
  public String examResultDisplayExamwise_MNJT(){
      try
           {
                          
            String sql = "CALL Sp_Get_SA_Markslist_by_Studentwise_GDGB(?,?);";
           
            fetchStudentWithExamData(examData,sql);
            int exmId=Integer.parseInt(examData.getString("ExamId"));
           if(rs.first())
           {
               fetchExamMarksWithGradeMNJT(exmId);
              
               outObject.put("Status", "Success");
                outObject.put("Message", "Data found..");
                outObject.put("TotalGrade",strTotalGrade);
                outObject.put("Total","-9999");
                outObject.put("maxTotal","-9999");
                outObject.put("Percentage","-9999");
                outObject.put("Result", array);
         }else {
                       printFaliureStatus(array);
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
  
  
  public String examResultDisplayExamwise_HBBN(){
      try
           {
             
               String sql="";
            String exmID=examData.getString("ExamId");
           if(exmID.equalsIgnoreCase("77")||exmID.equalsIgnoreCase("80")||exmID.equalsIgnoreCase("102")){
             sql="CALL Sp_Get_SA_Markslist_by_Studentwise_GDGB(?,?)";
           
           }else{
               sql = "CALL sp_and_get_marks_by_exam(?,?)";
           }
               
            //String sql = "  CALL sp_and_get_marks_by_exam(?,?);";
           
            fetchStudentWithExamData(examData,sql);
           
           if(rs.first())
           {
               fetchExamMarksWithGradeHBBN();
              
               outObject.put("Status", "Success");
                outObject.put("Message", "Data found..");
                outObject.put("TotalGrade",strTotalGrade);
                outObject.put("Total","-9999");
                outObject.put("maxTotal","-9999");
                outObject.put("Percentage","-9999");
                outObject.put("Result", array);
         }else {
                       printFaliureStatus(array);
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
  
  
  
  public String examResultDisplayExamwise_BKBI(){
      try
           {
                          
            String sql = "  CALL sp_and_get_marks_by_exam_bkbi(?,?);";
           
            fetchStudentWithExamData(examData,sql);
           
           if(rs.first())
           {
               fetchExamResultDataBKBI();
              
               outObject.put("Status", "Success");
                outObject.put("Message", "Data found..");
               /* outObject.put("TotalGrade",strTotalGrade);
                outObject.put("Total","-9999");
                outObject.put("maxTotal","-9999");
                outObject.put("Percentage","-9999");*/
                outObject.put("Result", array);
         }else {
                       printFaliureStatus(array);
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
  
  
   public String getExamMarksByExam_Jv(){
      try
           {
                          
            String sql = "  CALL sp_and_get_marks_by_exam_jv(?,?);";
           
            fetchStudentWithExamData(examData,sql);
           
           if(rs.first())
           {
               fetchmarksWithoutTotal();
              
               outObject.put("Status", "Success");
                outObject.put("Message", "Data found..");
               
                outObject.put("Result", array);
         }else {
                       printFaliureStatus(array);
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
  
   
   

  
  public String examResultDisplayTermExam_GDGB(){
      try
           {
                          
            String sql = "  CALL Sp_Get_SA_Markslist_by_Studentwise_GDGB(?,?);";
           
            fetchStudentWithExamData(examData,sql);
           
           if(rs.first())
           {
               fetchExamMarksWithGradeGdgb();
              /*
               float percentage=(total/maxTotal)*100;
               String strPercentage=String.format("%2.2f",percentage);
               String strTotalGrade="";
               if(){
               if(percentage>=91.00&&percentage<=100.00){
                   strTotalGrade="A1";
               }else if(percentage>=81.00&&percentage<91.00){
                   strTotalGrade="A2";
               }else if(percentage>=71.00&&percentage<81.00){
                   strTotalGrade="B1";
               }else if(percentage>=61.00&&percentage<71.00){
                   strTotalGrade="B2";
               }else if(percentage>=51.00&&percentage<61.00){
                   strTotalGrade="C1";
               }else if(percentage>=41.00&&percentage<51.00){
                   strTotalGrade="C2";
               }else if(percentage>=33.00&&percentage<41.00){
                   strTotalGrade="D";
               }else if(percentage>=21.00&&percentage<33.00){
                   strTotalGrade="E1";
               }else if(percentage>=00.00&&percentage<21.00){
                   strTotalGrade="E2";
               } 
               }else{
                   strTotalGrade="-9999";
               } */
               outObject.put("Status", "Success");
                outObject.put("Message", "Data found..");
                outObject.put("TotalGrade",strTotalGrade);
                outObject.put("Total","-9999");
                outObject.put("maxTotal","-9999");
                outObject.put("Percentage","-9999");
                outObject.put("Result", array);
         }else {
                       printFaliureStatus(array);
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
  public String insertExamResult(){
       try {
             int rowEffected=0;
             JSONArray array= examData.getJSONArray("MarksArray");
             for (int i=0; i < array.length(); i++) {
                 String sql = "CALL `Sp_Insert_TblMarksObtained`(?,?,?,?,?,?,?,?,?,?)";
                 JSONObject object = array.getJSONObject(i);
                 int examResultId = Integer.parseInt(object.getString("ExamResultId"));
                 int addedBy = Integer.parseInt(object.getString("AddedBy"));
                 int studId = Integer.parseInt(object.getString("StudentId"));
                 Float fltObtainedMarks = Float.parseFloat(object.getString("ObtainedMarks"));
                 String strMarks=object.getString("ObtainedMarks");
                 int hmwrkfdbckId=0;
                 String setDate=object.getString("SetDate");
                 Date hmwrkDateSQL = null;
             java.sql.Date sqlhmwrkDate = null;
             Timestamp tmpResultSet=null;
             if(!(setDate.equals("")))
             {
                 Date noticeDateFrom = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").parse(setDate);
               tmpResultSet=new Timestamp(noticeDateFrom.getTime());

             }
             /*if(!(hmwrkDate.equals("")))
             {
                 Date homewrokDate = new SimpleDateFormat("dd-MM-yyyy").parse(hmwrkDate);
               
                SimpleDateFormat dateFormatToServer = new SimpleDateFormat("yyyy-MM-dd");
                String strhmwrkDateInFormated = dateFormatToServer.format(homewrokDate);
                
                hmwrkDateSQL = dateFormatToServer.parse(strhmwrkDateInFormated);
               
                //SQL Date For Stored Procuder Init
                sqlhmwrkDate = new java.sql.Date(hmwrkDateSQL.getTime());
             } */
             callable=con.getConnection().prepareCall(sql);
           
            callable.setInt(1,0);
            callable.setInt(2, examResultId);
            callable.setInt(3, studId);
            callable.setFloat(4, fltObtainedMarks);
            callable.setInt(5, addedBy);
            callable.setTimestamp(6,tmpResultSet );
            callable.setInt(7,addedBy);
            callable.setTimestamp(8, tmpResultSet);
            
            
            effRow=callable.executeUpdate();
                resId=callable.getInt(9);
               strUserToken=callable.getString(10);
                if(resId==-1){
                 
                    break;
                }
                 else if (resId>0){
                   
                     rowEffected++;
                     
                         
                     
                     String strMessage="Your child has scored  "+strMarks;
                     //fcm.send_FCM_Notification(strUserToken,strMessage,"Homework feedack");
                }
                else {
                       
                       break;
                }
             }
     if(resId>0)
            {
                 outObject.put("Status", "Success");
                 outObject.put("Message", "Marks added sucessfully");
                 outObject.put("effected rows",rowEffected);
           }else if(resId==-1){
                 outObject.put("Status", "Fail");
                 outObject.put("Message", "Marks has been already added");
            }else {
            outObject.put("Status", "Fail");
                 outObject.put("Message", "Something went wrong");
            }
            
          return outObject.toString();
        }catch(Exception ee) {
            String exce = ee.toString();
            String exc = "{\"Status\":\"Exception\" ,  \"Message\":\""+exce+"\"}";
            return exc;    
        }finally{
            closeDBConnection();
        }
  }
  public String insertExamResultMaxMin(){
    try
           {
            String sql=" CALL `Sp_Insert_tblexamresult`(?,?,?,?,?,?,?,?,?,?)";
             Timestamp tmpStmpExmSetDate=null,tmpStmpExmOnDate=null;
             
            String strExamScheduleId = examData.getString("ExamScheduleId");
           int schdulId = Integer.parseInt(strExamScheduleId);
             String strSetBy = examData.getString("SetBy");
             setBy = Integer.parseInt(strSetBy);
              String strMaxMarks = examData.getString("MaxMarks");
            int maxMarks = Integer.parseInt(strMaxMarks);
             float  fltMinMarks = Float.parseFloat(examData.getString("MinMarks"));
           //  = Integer.parseInt(strMinMarks);
             String scdulSetDate = examData.getString("SetDate");
          
           
           if(!(scdulSetDate.equals("")))
             {
                 Date noticeDateFrom = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").parse(scdulSetDate);
               tmpStmpExmSetDate=new Timestamp(noticeDateFrom.getTime());

             }
            /*
            String strScdulOnDate = examData.getString("ExamOnDate"); 
           //  java.util.Date ntcDate= formatterentrdt.parse(noteOnDate);
                  
           Date noteSentDateSQL = null,examOnDateSql=null;
             java.sql.Date sqlNoteSentDate = null,sqlExamOnDate=null;
           
            if(!(strScdulOnDate.equals("")))
             {
                 Date examDate = new SimpleDateFormat("dd-MM-yyyy").parse(strScdulOnDate);
               tmpStmpExmOnDate=new Timestamp(examDate.getTime()); 
                SimpleDateFormat dateFormatToServer = new SimpleDateFormat("dd-MM-yyyy");
                String strExamOnDateInFormated = dateFormatToServer.format(examDate);
                
                examOnDateSql = dateFormatToServer.parse(strExamOnDateInFormated);
//              SQL Date For Stored Procuder Init
                sqlExamOnDate = new java.sql.Date(examOnDateSql.getTime());



               
             }
            
           
            String strExamId = examData.getString("ExamId");
            String strExamStartTime= examData.getString("ExamStartTime");
            String strExamEndTime= examData.getString("ExamEndTime");
              exmid = Integer.parseInt(strExamId);*/
            callable= con.getConnection().prepareCall(sql);
            callable.setInt(1,0);
            callable.setInt(2,schdulId);
            callable.setInt(3,maxMarks);
            callable.setFloat(4,fltMinMarks);
             callable.setInt(5,setBy);
            callable.setTimestamp(6,tmpStmpExmSetDate);
             
            
            callable.setInt(7, setBy); 
            
             callable.setTimestamp(8,tmpStmpExmSetDate);
             callable.setInt(9,1);
             
                      
            effRow=callable.executeUpdate();
            resId=callable.getInt(10);
            
             if(resId>0)
            {
                 
                outObject.put("Status", "Success");
                 outObject.put("resultId", resId);
                 outObject.put("Message", "Max Min marks  set sucessfully");
                //String strMessage=sqlExamOnDate.toString();
               // strMessage=strMessage+" /n "+ strExamStartTime+" to "+strExamEndTime;
                // fcm.sendClasswisePushNotification(clsid, strMessage, "Exam is set on");
             }else if(resId==-1){
                 outObject.put("Status", "Fail");
                 outObject.put("resultId", resId);
                 outObject.put("Message", "Max Min marks has been already set");
            } else if(resId==-11){
                 outObject.put("Status", "Fail");
                 outObject.put("resultId", resId);
                 outObject.put("Message", "Max Min marks has been already set please contact service provider to re-enable it!!");
            } 
            
            else {
                outObject.put("Status", "Fail");
                outObject.put("resultId", resId);
                 outObject.put("Message", "Coludn't set the Max Min marks because of some error ");
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
  public String examResultDisplayExamwise()
  {
  
           try
           {
                          
            String sql = "  CALL sp_and_get_marks_by_exam(?,?);";
           
            fetchStudentWithExamData(examData,sql);
           
          if(rs.first())
           {
              
               fetchExamResultDataLater();
               String strTotalGrade="",strPercentage="";
               
               
               if(failFlag!=1){
               
                   float percentage=(total/maxTotal)*100;
               strPercentage=String.format("%2.2f",percentage);
                   if(percentage>=89.50&&percentage<=100.00){
                       strTotalGrade="A+";
                   }else if(percentage>=69.50&&percentage<89.50){
                       strTotalGrade="A";
                   }else if(percentage>=49.50&&percentage<69.50){
                       strTotalGrade="B+";
                   }else if(percentage>=34.50&&percentage<49.50){
                       strTotalGrade="B";
                   }else if(percentage>=49.50&&percentage<69.50){
                       strTotalGrade="Fail";
                   }
               }
               else{
                   strTotalGrade="Fail";
                   strPercentage="NA";
               }
               outObject.put("Status", "Success");
                outObject.put("Message", "Data found..");
                outObject.put("Total",total);
                outObject.put("maxTotal",maxTotal);
                outObject.put("TotalGrade",strTotalGrade);
                outObject.put("Percentage",strPercentage);
                outObject.put("Result", array);
           }else {
                       printFaliureStatus(array);
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
  
  public String examResultDisplayExamwiseWithoutColumn(){
        try
           {
                          
            String sql = "CALL `Sp_StudentMarksList_ClassWise`(?,?)";
           
            fetchStaffExamDatByExam(examData,sql);
           ResultSetMetaData rsmd = rs.getMetaData();
           int columnCount = rsmd.getColumnCount();
           
           
           
           //for (int i = 1; i <= rsmd.getColumnCount(); i++){ String name = rsmd.getColumnName(i);}

           if(rs.first())
           {
               do{
                   JSONObject object= new JSONObject();
                   for(int i=1;i<=columnCount;i++){
                       
                       String data=rsmd.getColumnName(i);
                       
                       String value=rs.getString(i);
                       if(value.equalsIgnoreCase("-9999")){
                           value="AB";
                       }
                       object.put(data,value);
                   }
                   array.put(object);
               }while(rs.next());
              // fetchExamResultData();
               
//               float percentage=(total/maxTotal)*100;
//               String strPercentage=String.format("%2.2f",percentage);
               outObject.put("Status", "Success");
               outObject.put("Message", "Data found..");
//               outObject.put("Total",total);
//               outObject.put("maxTotal",maxTotal);
//               outObject.put("Percentage",strPercentage);
               outObject.put("Result", array);
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
        }
        finally{
            closeDBConnection();
        }
  }
  public String ExamResultDisplay_admin_studentwise()
  {
      
      return null;
  }
  public  String examResultDisplay()
  {
      JSONObject outObject = new JSONObject();
                try
                {
                    JSONArray array = new JSONArray();
              //   JSONObject examData = new JSONObject(examData);
                 String schoolid = examData.getString("SchoolId");
                 int sclId = Integer.parseInt(schoolid);
                 String clss = examData.getString("Class");
                 String section = examData.getString("Section");
                 String sql = "  CALL `ExamResult_Classwise`(?, ?, ?)";
                
                 callable.setInt(1,sclId);
                 callable.setString(2, clss);
                 callable.setString(3, section);
                 ResultSet rs = callable.executeQuery();
                if(rs.first())
                {
                    do
                    {
                         JSONObject object = new JSONObject();
                                 String strExam = rs.getString("Exam");
                                 String strClass = rs.getString("Class");
                                 String strSection = rs.getString("Section");
                                 String strRollNo = rs.getString("RollNo");
                                 String strStudentName = rs.getString("StudentName");
                                 String strSubject = rs.getString("Subject");
                                 String strMarksObtained = rs.getString("MarksObtained");
                                 String strMaxMarks = rs.getString("MaxMarks");
                                 object.put("Exam", strExam);
                                 object.put("Class", strClass);
                                  object.put("RollNo", strRollNo);
                                 object.put("Section", strSection);
                                 object.put("StudentName", strStudentName);
                                 object.put("Subject", strSubject);
                                 object.put("MarksObtained", strMarksObtained);
                                 object.put("MaxMarks", strMaxMarks);
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
             }
         finally{
            closeDBConnection();
        }       
  }
  public  String ExamResult_insert()
  {
      JSONObject outObject = new JSONObject();
           try
           {
               JSONArray array = new JSONArray();
          //  JSONObject examData = new JSONObject(examData.toString());
            String schoolId = examData.getString("SchoolId");
            int sclId = Integer.parseInt(schoolId);
            String academicYearId = examData.getString("AcademicYearId");
             int acdmcyr = Integer.parseInt(academicYearId);
            String classId = examData.getString("ClassId");
             int clsid = Integer.parseInt(classId);
            String subjectId = examData.getString("SubjectId");
            int sbjid = Integer.parseInt(classId);
            String addedBy = examData.getString("AddedBy");
            int adby = Integer.parseInt(addedBy);
            String enteredDate = examData.getString("EnteredDate"); 
            SimpleDateFormat formatterentrdt = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            java.util.Date entrdtdate= formatterentrdt.parse(enteredDate);
            String examId = examData.getString("ExamId");
             int exmid = Integer.parseInt(examId);
             String studentId = examData.getString("StudentId");
             int studid = Integer.parseInt(studentId);
             String marksObtained = examData.getString("MarksObtained");
             int mxmobtnd = Integer.parseInt(marksObtained);
             String Grade = examData.getString("Grade");
             String sql = "CALL `ExamResult_insert`(?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            
            callable.setInt(1,sclId);
            callable.setInt(2,acdmcyr);
            callable.setInt(3, clsid);
            callable.setInt(4, sbjid);
            callable.setInt(5, adby);
            callable.setDate(6,new java.sql.Date(entrdtdate.getTime()));
            callable.setInt(7, exmid);
            callable.setInt(8, studid);
            callable.setInt(9, mxmobtnd);
            callable.setString(10, Grade);
                        
            int effRow=callable.executeUpdate();
            if(effRow>0)
            {
                 outObject.put("Status", "Success");
                 outObject.put("Message", "inserted sucessfully");
            }else{
                 outObject.put("Status", "fail");
                 outObject.put("Message", " not inserted ");
            }
            
          return outObject.toString();
        }catch(Exception ee) {
            String exce = ee.toString();
            String exc = "{\"Status\":\"Exception\" ,  \"Message\":\""+exce+"\"}";
            return exc;    
        }
      finally{
            closeDBConnection();
        }
  }
  public void fetchExamScheduleDataLater()
  {
      try{
          do
               {
                    JSONObject object = new JSONObject();
      String strStudentName = rs.getString("studentname");
      String strClass = rs.getString("class");
      String strDate = rs.getString("Date");
      String strExam= rs.getString("Exam");
       String strExamTime = rs.getString("ExamTime");
      String strSubject = rs.getString("Subject");
      String strSubjectId=rs.getString("SubjectId");
      object.put("StudentName", strStudentName);
      object.put("Class", strClass);
      object.put("Date", strDate);
      object.put("Exam", strExam);
      object.put("ExamTime", strExamTime);
      object.put("Subject", strSubject);
      object.put("SubjectId", strSubjectId);
      array.put(object);
      }while(rs.next());
      }catch(Exception e){
          e.printStackTrace();
      }
  }
  
  public void fetchExamSchoolAcademicClassId(JSONObject examData,String sql){
            try{
            int exmId= Integer.parseInt(examData.getString("ExamId"));
            int schId= Integer.parseInt(examData.getString("SchoolId"));
             int acdId= Integer.parseInt(examData.getString("AcademicYearId"));//AcademicYearId
             int classId= Integer.parseInt(examData.getString("ClassId"));
            callable= con.getConnection().prepareCall(sql);
            callable.setInt(1,classId);
            callable.setInt(2,exmId);
            callable.setInt(3,schId);
             callable.setInt(4,acdId);
            rs= callable.executeQuery();
            }catch(Exception e){
                e.printStackTrace();
            }
   }
  
  public void fetchExamScheduleData()
  {
      try{
          do
               {
                    JSONObject object = new JSONObject();
     // String strStudentName = rs.getString("studentname");
      String strClass = rs.getString("Class");
      String strDate = rs.getString("Date");
      String strExam= rs.getString("Exam");
      String strClassId = rs.getString("ClassId");
      String strSubject = rs.getString("Subject");
      String strExamTime= rs.getString("ExamTime");
      String strStartTime=rs.getString("StartTime");
      String strEndTime=rs.getString("EndTime");
          String strExamScheduleId = rs.getString("ExamScheduleId");
      String strExamId=rs.getString("ExamId");
      String strSubjectId=rs.getString("SubjectId");
      String strIsSyllabusSet=rs.getString("IsSyllabusSet");
      String strHas_Syllabus=rs.getString("Has_Syllabus");
      
      //object.put("StudentName", strStudentName);
      object.put("Class", strClass);
      object.put("Date", strDate);
      object.put("Exam", strExam);
      object.put("Subject", strSubject);
      object.put("ExamTime", strExamTime);
      object.put("ExamScheduleId", strExamScheduleId);
      object.put("EndTime", strEndTime);
      object.put("StartTime", strStartTime);
       object.put("ClassId", strClassId);
      object.put("ExamId", strExamId);
      object.put("SubjectId", strSubjectId);
      object.put("IsSyllabusSet",strIsSyllabusSet);
      object.put("Has_Syllabus",strHas_Syllabus);
      array.put(object);
      }while(rs.next());
      }catch(Exception e){
          e.printStackTrace();
      }
  }
  public String ExamScheduleDisplay_ClassWise()
  {
      JSONObject outObject = new JSONObject();
                try
                {
                    JSONArray array = new JSONArray();
                 //JSONObject examData = new JSONObject(examData);
                 String schoolid = examData.getString("SchoolId");
                 int sclId = Integer.parseInt(schoolid);
                 String clss = examData.getString("Class");
                 String section = examData.getString("Section");
                 String sql = "  CALL `ExamScheduleDisplay_ClassWise`(?, ?, ?)";
            
                 callable.setInt(1,sclId);
                 callable.setString(2, clss);
                 callable.setString(3, section);
                 ResultSet rs = callable.executeQuery();
                if(rs.first())
                {
                    do
               {
                    
                    fetchExamScheduleData();        
                    
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
             }
      finally{
            closeDBConnection();
        }
  }
  
   public String ExamScheduleDisplay_Staffwise()
   {
     
           try
           {
               JSONArray array = new JSONArray();
            //JSONObject examData = new JSONObject(examData);
            String staffid = examData.getString("StaffId");
            int stfId = Integer.parseInt(staffid);
            String sql = " CALL `ExamScheduleDisplay_Staffwise`(?)";
           
            callable.setInt(1,stfId);
            ResultSet rs = callable.executeQuery();
           if(rs.first())
           {
               fetchExamScheduleData();
                
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
        }
           finally{
            closeDBConnection();
        }
   }
   public String examScheduleDisplay_Studentwise()
   {
        try
           {
               
            String sql = "CALL sp_and_get_exam_schedule(?)"; 
            
            fetchStudentData(examData,sql);
            
           if(rs.first())
           {
               
              fetchExamScheduleDataLater();   
             
               if(array.length()!=0){
               printSuccessStatus(array);
               }else{
                   printFaliureStatus(array);
               }
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
        }
        finally{
            closeDBConnection();
        }
   }
   public String examScheduleDisplayByExamid()
   {
      try
           {
            String sql="CALL sp_and_get_exam_schedule_by_exam(?, ?)";
            DBConnect con = new DBConnect();
            
            fetchStudentWithExamData(examData,sql);
            
           if(rs.first())
           {
               fetchExamScheduleDataLater();
                
              if(array.length()!=0){
               printSuccessStatus(array);
               }else{
                   printFaliureStatus(array);
               }
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
        }
      finally{
            closeDBConnection();
        }
   }
   
   
   public String examScheduleDisplayByExamid_jv()
   {
      try
           {
            String sql="CALL sp_and_get_exam_schedule_by_exam_jv(?, ?)";
            DBConnect con = new DBConnect();
            
            fetchStudentWithExamData(examData,sql);
            
           if(rs.first())
           {
               fetchExamScheduleDataLater();
                
              if(array.length()!=0){
               printSuccessStatus(array);
               }else{
                   printFaliureStatus(array);
               }
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
        }
      finally{
            closeDBConnection();
        }
   }
   
   
   public String checkIsExamScheduleEntered(){
       
       try
           {
            String sql=" call sp_get_not_entered_examshedule_list(?,?,?)";
//               fetchExamSchoolAcademicId(examData, sql);
            if(rs.first())
           {
               do
               {
                    JSONObject object = new JSONObject();
     // String strStudentName = rs.getString("studentname");
     
      String strClass = rs.getString("ClassSection");
      String strClassId = rs.getString("ClassId");
      String strExam= rs.getString("Exam");
      String strSubject = rs.getString("Subject");
      String strSubjectId = rs.getString("SubjectId");
      
      object.put("Class", strClass);
      object.put("ClassId", strClassId);
      object.put("Exam", strExam);
      object.put("Subject", strSubject);
      object.put("SubjectId", strSubjectId);
      array.put(object);
      }while(rs.next());
                
              if(array.length()!=0){
               printSuccessStatus(array);
               }else{
                   printFaliureStatus(array);
               }
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
        }
      finally{
            closeDBConnection();
        }
       
   }
   
   
    public String  checkIsExamDetailsEntered(){
     
     
     try
           {
            String sql= "call sp_get_not_entered_examsection_details(?,?,?,?)";
               fetchExamSchoolAcademicClassId(examData, sql);
            if(rs.first())
           {
               do
               {
                    JSONObject object = new JSONObject();
     // String strStudentName = rs.getString("studentname");
      
        String strClass = rs.getString("ClassSection");
        String strClassId = rs.getString("ClassId");
        String strExam = rs.getString("Exam");
        String strExamId = rs.getString("ExamId");
        String strDate=rs.getString("Date");
        String strSubject = rs.getString("Subject");
        String strSubjectId = rs.getString("SubjectId");
        String strExamTime = rs.getString("ExamTime");
        String strScheduleSet = rs.getString("ScheduleSet");
        String strSyllabusSet = rs.getString("SyllabusSet");
        String strResultSet = rs.getString("ResultSet");
        String strExamScheduleId=rs.getString("ExamScheduleId");
        String strExamSyllabusId=rs.getString("ExamSyllabusId");
        String strExamResultId=rs.getString("ExamResultId");
        String strSyllabus=rs.getString("Syllabus");
        String strScholastics=rs.getString("Scholastics");
        String strIs_Work_Book_Exam=rs.getString("Is_Work_Book_Exam");
        if(strDate == null || strDate.isEmpty()){
            strDate="9999";
        }
         if(strExamTime == null || strExamTime.isEmpty()){
            strExamTime="9999";
        }   
        object.put("Class", strClass);
        object.put("ClassId", strClassId);
        object.put("Exam", strExam);
        object.put("ExamId", strExamId);
        object.put("Date", strDate);
        object.put("Subject", strSubject);
        object.put("SubjectId", strSubjectId);
        object.put("ExamTime", strExamTime);
        object.put("ScheduleSet", strScheduleSet);
        object.put("SyllabusSet", strSyllabusSet);
        object.put("ResultSet", strResultSet);
        object.put("Syllabus", strSyllabus);
        object.put("Scholastics", strScholastics);
        object.put("IsWorkBookExam", strIs_Work_Book_Exam);
     if(strExamScheduleId == null && strExamScheduleId.isEmpty()){
         object.put("ExamScheduleId", "0");
     }else{
         object.put("ExamScheduleId", strExamScheduleId);
     }
      
      if(strExamScheduleId == null && strExamScheduleId.isEmpty()){
         object.put("ExamScheduleId", "0");
     }else{
         object.put("ExamScheduleId", strExamScheduleId);
     }
      
       if(strExamSyllabusId == null && strExamSyllabusId.isEmpty()){
         object.put("ExamSyllabusId", "0");
     }else{
        object.put("ExamSyllabusId", strExamSyllabusId);
     }
     
        if(strExamResultId == null && strExamResultId.isEmpty()){
        object.put("ExamResultId", "0");
     }else{
        object.put("ExamResultId", strExamResultId);
     }
       
     
      
      
      array.put(object);
      }while(rs.next());
                
              if(array.length()!=0){
               printSuccessStatus(array);
               }else{
                   printFaliureStatus(array);
               }
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
        }
      finally{
            closeDBConnection();
        }
     
 }
   
   
   
   
   public String checkIsExamSyllabusEntered(){
       try
           {
            String sql=" call sp_get_not_entered_examsyllabus_list(?,?,?)";
             //  fetchExamSchoolAcademicId(examData, sql);
            if(rs.first())
           {
               do
               {
                    JSONObject object = new JSONObject();
     // String strStudentName = rs.getString("studentname");
      
      String strClass = rs.getString("ClassSection");
      String strClassId = rs.getString("ClassId");
      String strExam= rs.getString("Exam");
      String strExamId= rs.getString("ExamId");
      String strSubject = rs.getString("Subject");
       String strSubjectId = rs.getString("SubjectId");
       String strStartTime = rs.getString("StartTime");
       String strEndTime = rs.getString("EndTime");
      String strExamScheduleId=rs.getString("ExamScheduleId");
      
      object.put("Class", strClass);
      object.put("ClassId", strClassId);
      object.put("Exam", strExam);
      object.put("ExamId", strExamId);
      object.put("Subject", strSubject);
      object.put("SubjectId", strSubjectId);
      object.put("StartTime", strStartTime);
      object.put("EndTime", strEndTime);
      object.put("ExamScheduleId", strExamScheduleId);
      
      array.put(object);
      }while(rs.next());
                
              if(array.length()!=0){
               printSuccessStatus(array);
               }else{
                   printFaliureStatus(array);
               }
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
        }
      finally{
            closeDBConnection();
        }
   }
   
   
   public String getLeftOutStudentsForMarksEntry(){
       JSONObject outObject = new JSONObject();
           try
           {
               int rollNotSet=0;
               JSONArray array = new JSONArray();
               int clsId = Integer.parseInt(examData.getString("ClassId"));
               int exmId = Integer.parseInt(examData.getString("ExamResultId"));
            String sql= " CALL sp_get_left_out_students_for_marks_entry(?,?)";
            callable=con.getConnection().prepareCall(sql);
             callable.setInt(1, clsId);
             callable.setInt(2, exmId);
            rs=callable.executeQuery();
           if(rs.first())
           {
               do
               {
                    JSONObject object = new JSONObject();
                    String strRollNo = rs.getString("RollNo");
                    String strstudentname = rs.getString("StudentName");
                    String strStudentDetailsId = rs.getString("StudentDetailsId");
                    String strclass = rs.getString("Class");
                    String strMaxMarks = rs.getString("MaxMarks");
                    String strMinMarks = rs.getString("MinMarks");
                    String strExamId = rs.getString("ExamId");
                    
                    if(strRollNo == null || strRollNo.isEmpty()){
                        rollNotSet=1;
                        break;
                        
                    }

                    
                    
                    object.put("RollNo", strRollNo);
                    object.put("studentname", strstudentname);
                     object.put("StudentDetailsId", strStudentDetailsId);
                    object.put("class", strclass);
                    object.put("MaxMarks", strMaxMarks);
                     object.put("MinMarks", strMinMarks);
                    object.put("ExamId", strExamId);
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
                        outObject.put("Message", "Couldnt find the students please try agian..");
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
   
   public String checkIsExamMarksEntered(){
       try
           {
            String sql=" call sp_get_not_entered_exammarks_list(?,?,?)";
//               fetchExamSchoolAcademicId(examData, sql);
            if(rs.first())
           {
               do
               {
                    JSONObject object = new JSONObject();
     // String strStudentName = rs.getString("studentname");
      
      String strClass = rs.getString("ClassSection");
      String strClassId = rs.getString("ClassId");
     
      
      String strSubject = rs.getString("Subject");
       String strSubjectId = rs.getString("SubjectId");
       String strStartTime = rs.getString("StartTime");
       String strEndTime = rs.getString("EndTime");
      String strExamScheduleId=rs.getString("ExamScheduleId");
      
      object.put("Class", strClass);
      object.put("ClassId", strClassId);
      object.put("Exam", strExam);
      //object.put("ExamId", strExamId);
      object.put("Subject", strSubject);
      object.put("SubjectId", strSubjectId);
      object.put("StartTime", strStartTime);
      object.put("EndTime", strEndTime);
      object.put("ExamScheduleId", strExamScheduleId);
      
      array.put(object);
      }while(rs.next());
                
              if(array.length()!=0){
               printSuccessStatus(array);
               }else{
                   printFaliureStatus(array);
               }
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
        }
      finally{
            closeDBConnection();
        }
   }
   
   
   
   public  String getStaffexamSchdule(){
       try
           {
            String sql="CALL Sp_Get_ExamScheduleStaffwise(?,?,?)";
           
            
          fetchStaffwiseExamData(examData,sql);
            
           if(rs.first())
           {
               fetchExamScheduleData();
                
              if(array.length()!=0){
               printSuccessStatus(array);
               }else{
                   printFaliureStatus(array);
               }
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
        }
      finally{
            closeDBConnection();
        }
   }
   
   public String getStaffexamSchduleClasswise(){
        try
           {
            String sql="CALL Sp_Get_ExamScheduleDetailsClasswise(?,?)";
           
            
            fetchStaffExamDatByExam(examData,sql);
            
           if(rs.first())
           {
               fetchExamScheduleData();
                
              if(array.length()!=0){
               printSuccessStatus(array);
               }else{
                   printFaliureStatus(array);
               }
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
        }
      finally{
            closeDBConnection();
        }
       
   }
   public String getStaffexamSchduleDetailsById(){
       try
           {
            String sql=" CALL Sp_Get_ExamScheduleDetailsById(?)";
           
            int exmScdulId= Integer.parseInt(examData.getString("ExamSceduleId"));
        
        callable= con.getConnection().prepareCall(sql);
        callable.setInt(1,exmScdulId);
        rs= callable.executeQuery();

            
            
           if(rs.first())
           {
               do
               {
                    JSONObject object = new JSONObject();
     // String strStudentName = rs.getString("studentname");
      String strClass = rs.getString("ClassSection");
      String strDate = rs.getString("Date");
      String strExam= rs.getString("Exam");
      String strSubject = rs.getString("Subject");
      String strExamTime= rs.getString("ExamTime");
      String strExamScheduleId = rs.getString("ExamScheduleId");
      
      //object.put("StudentName", strStudentName);
      object.put("Class", strClass);
      object.put("Date", strDate);
      object.put("Exam", strExam);
      object.put("Subject", strSubject);
      object.put("ExamTime", strExamTime);
      object.put("ExamScheduleId", strExamScheduleId);
      array.put(object);
      }while(rs.next());
                
              if(array.length()!=0){
               printSuccessStatus(array);
               }else{
                   printFaliureStatus(array);
               }
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
        }
      finally{
            closeDBConnection();
        }
   }
   
   public String editExamSchdule(){
       try
           {
            String sql=" CALL `Sp_Insert_tblexamschedule`(?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
             Timestamp tmpStmpExmSetDate=null,tmpStmpExmOnDate=null;
             int examScdulId=Integer.parseInt(examData.getString("ExamScheduleId"));
            String strSubjectId = examData.getString("SubjectId");
           subId = Integer.parseInt(strSubjectId);
                String strSetBy = examData.getString("SetBy");
             setBy = Integer.parseInt(strSetBy);
              String classId = examData.getString("ClassId");
            clsid = Integer.parseInt(classId);
             
            String scdulSetDate = examData.getString("SetDate");
          
           
           if(!(scdulSetDate.equals("")))
             {
                 Date noticeDateFrom = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").parse(scdulSetDate);
               tmpStmpExmSetDate=new Timestamp(noticeDateFrom.getTime());;
//                SimpleDateFormat dateFormatToServer = new SimpleDateFormat("yyyy-MM-dd HH:mm");
//                String strnoteSentDateInFormated = dateFormatToServer.format(noticeDateFrom);
//                
//                scdulSetDateSQL = dateFormatToServer.parse(strnoteSentDateInFormated);
//               
//                //SQL Date For Stored Procuder Init
//                sqlSdulSetDate = new java.sql.Date(scdulSetDateSQL.getTime());
             }
            
            
            String scdulOnDate = examData.getString("ExamOnDate"); 
           //  java.util.Date ntcDate= formatterentrdt.parse(noteOnDate);
                  
           
           
            if(!(scdulOnDate.equals("")))
             {
                 Date noticeDateTo = new SimpleDateFormat("dd-MM-yyyy").parse(scdulOnDate);
               tmpStmpExmOnDate=new Timestamp(noticeDateTo.getTime());
              SimpleDateFormat dateFormatToServer = new SimpleDateFormat("yyyy-MM-dd HH:mm");
                String strNtcToDateInFormated = dateFormatToServer.format(noticeDateTo);
//                
                scdulOnDateSQL = dateFormatToServer.parse(strNtcToDateInFormated);
//               
//                //SQL Date For Stored Procuder Init
                sqlScdulOnDate = new java.sql.Date(scdulOnDateSQL.getTime());
             }
           
            String strExamId = examData.getString("ExamId");
            String strExamStartTime= examData.getString("ExamStartTime");
            String strExamEndTime= examData.getString("ExamEndTime");
              exmid = Integer.parseInt(strExamId);
            callable= con.getConnection().prepareCall(sql);
            callable.setInt(1,examScdulId);
            callable.setInt(2,setBy);
            callable.setTimestamp(3,null);
             callable.setInt(4,exmid);
              callable.setInt(5,clsid);
               callable.setInt(6, subId);
            callable.setDate(7,sqlScdulOnDate);
            
            callable.setString(8,strExamStartTime);
            callable.setString(9,strExamEndTime);
            
            callable.setInt(10, setBy); 
            
             callable.setTimestamp(11,tmpStmpExmSetDate);
             callable.setInt(12,1); 
                      
            effRow=callable.executeUpdate();
            resId=callable.getInt(13);
            int resExmSdulId=callable.getInt(14);
             if(resId==-2)
            {
                 outObject.put("Status", "Success");
                 outObject.put("resultId", resId);
                 outObject.put("Message", "Exam schedule Updated sucessfully");
                 String strMessage=sqlScdulOnDate.toString();
                 strMessage=strMessage+" \n "+" from "+strExamStartTime+" to "+strExamEndTime;
                 fcm.sendClasswisePushNotification(clsid, strMessage, "Exam TimeTable Update");
            
            }
             else if(resId==-1)
            {
                 outObject.put("Status", "Fail");
                 outObject.put("resultId", resId);
                 outObject.put("Message", "No changes in schedule to update");
            }else if(resId==-333){
                 outObject.put("Status", "Fail");
                 outObject.put("resultId", resId);
                 outObject.put("Message", "Schedule for this exam and subject has been already set so please edit the same.");
            }else if(resId==-11){
                outObject.put("Status", "Fail");
                 outObject.put("resultId", resId);
                 outObject.put("Message", "This exam schedule has been already added Contact service provider to Re-enable it.");
            }
            else {
                outObject.put("Status", "Fail");
                outObject.put("resultId", resId);
                 outObject.put("Message", "Coludn't update the exam schedule because of some error ");
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
   public String deleteExamSchedule(){
       try
           {

             /*String sql=" CALL `Sp_Delete_ExamScheduleById`(?)";
             Timestamp tmpStmpExmSetDate=null,tmpStmpExmOnDate=null;
             int examScdulId=Integer.parseInt(examData.getString("ExamScheduleId"));
            
            callable= con.getConnection().prepareCall(sql);
            callable.setInt(1,examScdulId);
            
                      
            effRow=callable.executeUpdate();
            
            
             if(resId==0)
            {
                 outObject.put("Status", "Success");
                 outObject.put("resultId", resId);
                 outObject.put("Message", "Exam schedule deleted sucessfully");
            }
            
            else {
                outObject.put("Status", "Fail");
                outObject.put("resultId", resId);
                 outObject.put("Message", "Coludn't delete the exam schedule because of some error ");
            }
            
            */
                outObject.put("Status", "Fail");
                outObject.put("resultId", 11);
                outObject.put("Message", " This feature is no more, please update the app");
                 
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
   
   public String setStaffexamSchdule(){
       try
           {
            String sql=" CALL `Sp_Insert_tblexamschedule`(?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
             Timestamp tmpStmpExmSetDate=null,tmpStmpExmOnDate=null;
             
            String strSubjectId = examData.getString("SubjectId");
           subId = Integer.parseInt(strSubjectId);
             String strSetBy = examData.getString("SetBy");
             setBy = Integer.parseInt(strSetBy);
              String classId = examData.getString("ClassId");
            clsid = Integer.parseInt(classId);
             String scdulSetDate = examData.getString("SetDate");
          
           
           if(!(scdulSetDate.equals("")))
             {
                 Date noticeDateFrom = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").parse(scdulSetDate);
               tmpStmpExmSetDate=new Timestamp(noticeDateFrom.getTime());;
//                SimpleDateFormat dateFormatToServer = new SimpleDateFormat("yyyy-MM-dd HH:mm");
//                String strnoteSentDateInFormated = dateFormatToServer.format(noticeDateFrom);
//                
//                scdulSetDateSQL = dateFormatToServer.parse(strnoteSentDateInFormated);
//               
//                //SQL Date For Stored Procuder Init
//                sqlSdulSetDate = new java.sql.Date(scdulSetDateSQL.getTime());
             }
            
            String strScdulOnDate = examData.getString("ExamOnDate"); 
           //  java.util.Date ntcDate= formatterentrdt.parse(noteOnDate);
                  
           Date noteSentDateSQL = null,examOnDateSql=null;
             java.sql.Date sqlNoteSentDate = null,sqlExamOnDate=null;
           
            if(!(strScdulOnDate.equals("")))
             {
                 Date examDate = new SimpleDateFormat("dd-MM-yyyy").parse(strScdulOnDate);
               tmpStmpExmOnDate=new Timestamp(examDate.getTime()); 
                SimpleDateFormat dateFormatToServer = new SimpleDateFormat("dd-MM-yyyy");
                String strExamOnDateInFormated = dateFormatToServer.format(examDate);
                
                examOnDateSql = dateFormatToServer.parse(strExamOnDateInFormated);
//              SQL Date For Stored Procuder Init
                sqlExamOnDate = new java.sql.Date(examOnDateSql.getTime());



               
             }
            
           
            String strExamId = examData.getString("ExamId");
            String strExamStartTime= examData.getString("ExamStartTime");
            String strExamEndTime= examData.getString("ExamEndTime");
              exmid = Integer.parseInt(strExamId);
            callable= con.getConnection().prepareCall(sql);
            callable.setInt(1,0);
            callable.setInt(2,setBy);
            callable.setTimestamp(3,tmpStmpExmSetDate);
             callable.setInt(4,exmid);
              callable.setInt(5,clsid);
               callable.setInt(6, subId);
            callable.setDate(7,sqlExamOnDate);
            
            callable.setString(8,strExamStartTime);
            callable.setString(9,strExamEndTime);
            
            callable.setInt(10, setBy); 
            
             callable.setTimestamp(11,tmpStmpExmSetDate);
             callable.setInt(12,1); 
                      
            effRow=callable.executeUpdate();
            resId=callable.getInt(13);
            int resExmSdulId=callable.getInt(14);
             if(resId>0)
            {
                 
                outObject.put("Status", "Success");
                 outObject.put("resultId", resId);
                 outObject.put("Message", "Exam schedule set sucessfully");
                String strMessage=sqlExamOnDate.toString();
                strMessage=strMessage+" /n "+ strExamStartTime+" to "+strExamEndTime;
                 fcm.sendClasswisePushNotification(clsid, strMessage, "Exam is set on");
             }else if(resId==-1){
                 outObject.put("Status", "Fail");
                 outObject.put("resultId", resId);
                 outObject.put("Message", "Exam schedule has been already set");
            }else if(resId==-11){
                 outObject.put("Status", "Fail");
                 outObject.put("resultId", resId);
                 outObject.put("Message", "Exam schedule has been already set please contact service provider to re-enable it!!");
            } 
            
            else {
                outObject.put("Status", "Fail");
                outObject.put("resultId", resId);
                 outObject.put("Message", "Coludn't set the exam schedule because of some error ");
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
   public String insertStaffexamSchdule()
   {
       JSONObject outObject = new JSONObject();
           try
           {
               JSONArray array = new JSONArray();
           // JSONObject examData = new JSONObject(examData);
            String schoolId = examData.getString("SubjectId");
            int subId = Integer.parseInt(schoolId);
          
            String setBy = examData.getString("SetBy");
             int setby = Integer.parseInt(setBy);
             String scheduleSetDate = examData.getString("SetDate"); 
            SimpleDateFormat formatterentrdt = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            java.util.Date setDate= formatterentrdt.parse(scheduleSetDate);
            String ExamId = examData.getString("ExamId");
            int exmid = Integer.parseInt(ExamId);
            String classId = examData.getString("ClassId");
            int clsid = Integer.parseInt(classId);
            
             String subjectId = examData.getString("SubjectId");
             int subid = Integer.parseInt(subjectId);
              String date = examData.getString("ExamOnDate"); 
            SimpleDateFormat formatterexmdt = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            java.util.Date exmDate= formatterentrdt.parse(date);
            
            String sql = "  CALL `Sp_Insert_tblexamschedule`(?,?,?,?,?,?,?,?,?,?)";
           callable=con.getConnection().prepareCall(sql);
            callable.setInt(1,0);
            callable.setInt(2, setby);
            callable.setDate(3,new java.sql.Date(setDate.getTime()));
            callable.setInt(4, exmid);
             callable.setInt(5, clsid);
             callable.setInt(6, subId);
            callable.setDate(7,new java.sql.Date(exmDate.getTime()));
             callable.setInt(8, setby);
             callable.setDate(9,new java.sql.Date(setDate.getTime()));
                        
            int effRow=callable.executeUpdate();
            resId=callable.getInt(10);
            if(resId>0)
            {
                 outObject.put("Status", "Success");
                 outObject.put("Message", "inserted sucessfully");
            }else if(resId==-1){
                 outObject.put("Status", "fail");
                 outObject.put("Message", " record already inserted ");
            } else {
                  outObject.put("Status", "fail");
                 outObject.put("Message", " record was not inserted ");
            }
            
          return outObject.toString();
        }catch(Exception ee) {
            String exce = ee.toString();
            String exc = "{\"Status\":\"Exception\" ,  \"Message\":\""+exce+"\"}";
            return exc;    
        }
           finally{
            closeDBConnection();
        }
   }
   public void fetchGrades(){
       
       
       try{
           JSONObject obj=new JSONObject();
            obj.put("Grade", "Grade");
            obj.put("GradingValue", "");
            array.put(obj);
           do
               {
                    JSONObject object = new JSONObject();
           
                    String strGrade = rs.getString("grade");
                    String strGradingValue= rs.getString("grading_value");
                    
                    object.put("Grade", strGrade);
                    object.put("GradingValue", strGradingValue);
                   
                    array.put(object);
               }while(rs.next());
           
       }catch(Exception e){
           e.printStackTrace();
       }
       finally{
            closeDBConnection();
        }
       
       
   }
   public void fetchExamsyllabusDataLater()
   {
       try{
           do
               {
                    JSONObject object = new JSONObject();
           
                    String strExam = rs.getString("Exam");
                    String strClass= rs.getString("class");
                    String strDate= rs.getString("ExamDate");
                    String strExamTime= rs.getString("ExamTime");
                    String strStudentName = rs.getString("studentname");
                    String strSubject = rs.getString("Subject");
                    String strSyllabus = rs.getString("Syllabus");


                    object.put("Exam", strExam);
                    object.put("Class", strClass);
                    object.put("ExamDate", strDate);
                     object.put("ExamTime", strExamTime);
                    object.put("Studentname", strStudentName);
                    object.put("Subject", strSubject);
                    object.put("Syllabus", strSyllabus);
                    array.put(object);
               }while(rs.next());
           
       }catch(Exception e){
           e.printStackTrace();
       }
       finally{
            closeDBConnection();
        }
   }
   public void fetchExamsyllabusData()
   {
       try{
           do
               {
                    JSONObject object = new JSONObject();
                    String strExam = rs.getString("Exam");
           String strClass= rs.getString("ClassSection");
           String strDate= rs.getString("Date");
           String strExamTime = rs.getString("ExamTime");
           String strExamSyllabusId=rs.getString("ExamSyllabusId");
           String strExamScheduleId= rs.getString("ExamScheduleId");
           String strSubject = rs.getString("Subject");
           String strSyllabus = rs.getString("Syllabus");
           String strScholastics = rs.getString("Scholastics");
           String strIsMarksSet = rs.getString("IsMarksSet");
           String strExamResultId = rs.getString("ExamResultId");
            String strClassId = rs.getString("ClassId");
            
           object.put("Exam", strExam);
           object.put("Class", strClass);
           object.put("Date", strDate);
           object.put("ExamTime", strExamTime);
           object.put("ExamScheduleId", strExamScheduleId);
           object.put("Subject", strSubject);
           object.put("ExamSyllabusId",strExamSyllabusId);
           object.put("Syllabus", strSyllabus);
           object.put("Scholastics", strScholastics);
           object.put("IsMarksSet", strIsMarksSet);
           object.put("ExamResultId", strExamResultId);
           object.put("ClassId", strClassId);
           
           array.put(object);
               }while(rs.next());
           
       }catch(Exception e){
           e.printStackTrace();
       }
       finally{
            closeDBConnection();
        }
   }
   public  String ExamSyllabusDisplay_Staffwise()
   {
       JSONObject outObject = new JSONObject();
           try
           {
               JSONArray array = new JSONArray();
         //   JSONObject examData = new JSONObject(examData);
            String staffid = examData.getString("StaffId");
            int stfId = Integer.parseInt(staffid);
            String sql = " CALL `ExamSyllabusDisplay_Staffwise`(?)";
           
            callable.setInt(1,stfId);
            ResultSet rs = callable.executeQuery();
           if(rs.first())
           {
               do
               {
                    JSONObject object = new JSONObject();
                            String strExam = rs.getString("Exam");
                            String strStaff_name= rs.getString("Staff_name");
                            String strClass = rs.getString("Class");
                            String strSection = rs.getString("Section");
                            String strSubject= rs.getString("Subject");
                            String strSyllabus= rs.getString("Syllabus");
                            object.put("Exam", strExam);
                            object.put("Staff_name", strStaff_name);
                            object.put("Class", strClass);
                            object.put("Section", strSection);
                            object.put("Subject", strSubject);
                            object.put("Syllabus", strSyllabus);
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
        }
           finally{
            closeDBConnection();
        }
   }
   public String examSyllabus()
   {
      
        try
           {
            
            String sql = "CALL sp_and_get_exam_syllabus(?)";
            
            fetchStudentData(examData,sql);
            
           if(rs.first())
           {
               fetchExamsyllabusDataLater();
            
               if(array.length()!=0){
               printSuccessStatus(array);
               }else{
                   printFaliureStatus(array);
               }
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
        }
       finally{
            closeDBConnection();
        }
   }
   public  String examSyllabusDisplayByExam()
   {
       
          try
           {
            String sql = "CALL sp_and_get_exam_syllabus_by_exam(?,?)";
            fetchStudentWithExamData(examData,sql);
            
           if(rs.first())
           {
               fetchExamsyllabusDataLater();
             
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
   
   
   public  String getGradesBySchoolId()
   {
       
          try
           {
            String sql = "CALL sp_get_grading_nonscholastic(?,?)";
            fetchSchoolIdAndAcedemicYearIdForExam(examData,sql);
            
           if(rs.first())
           {
               fetchGrades();
             
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
   
   public  String examSyllabusDisplayByExam_jv()
   {
       
          try
           {
            String sql = "CALL sp_and_get_exam_syllabus_by_exam_jv(?,?)";
            fetchStudentWithExamData(examData,sql);
            
           if(rs.first())
           {
               fetchExamsyllabusDataLater();
             
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
   
   
   
   
   public String getStaffExamSyllabus(){
       try
           {
            String sql = "CALL Sp_Get_ExamSyllabusDetailsStaffwise(?,?,?)";
            fetchStaffwiseExamData(examData,sql);
            
           if(rs.first())
           {
            fetchExamsyllabusData();
           if(array.length()!=0){
               printSuccessStatus(array);
               }else{
                   printFaliureStatus(array);
               }
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
   public String getStaffExamSyllabusClasswise(){
       try
           {
            String sql = "CALL Sp_Get_ExamSyllabusDetailsByClasswise(?,?)";
            fetchStaffExamDatByExam(examData,sql);
            
           if(rs.first())
           {
            fetchExamsyllabusData();
           if(array.length()!=0){
               printSuccessStatus(array);
               }else{
                   printFaliureStatus(array);
               }
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
  public String getSubjectByExamId(){
      try
           {
            String sql = "CALL sp_and_get_subjects_by_examId(?,?)";
            fetchStaffExamDatByExam(examData,sql);
            
           if(rs.first())
           {
               do
               {
               JSONObject object= new JSONObject();
            String strSubjectId=rs.getString("SubjectId");
            String strSubject=rs.getString("Subject");
            object.put("SubjectId", strSubjectId);
            object.put("Subject", strSubject);
            array.put(object);
               }while(rs.next());
                int size =0;
                
                if (rs != null) 
                    {
                        rs.beforeFirst();
                        rs.last();
                        size = rs.getRow();
                    }
               if(array.length()==size){
               printSuccessStatus(array);
               }else{
                   printFaliureStatus(array);
               }
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
   
   public String setStaffExamSyllabus(){
       try{
       String sql="CALL `Sp_Insert_ExamSyllabus`(?,?,?,?,?,?,?,?,?);";

            setBy = Integer.parseInt(examData.getString("SetBy"));
             

             
            int exmScdulId=Integer.parseInt(examData.getString("ExamSceduleId"));
             String scdulSetDate = examData.getString("SetDate");
          
           
           if(!(scdulSetDate.equals("")))
             {
                 Date noticeDateFrom = new SimpleDateFormat("dd-MM-yyyy HH:mm").parse(scdulSetDate);
               
                SimpleDateFormat dateFormatToServer = new SimpleDateFormat("yyyy-MM-dd HH:mm");
                String strnoteSentDateInFormated = dateFormatToServer.format(noticeDateFrom);
                
                scdulSetDateSQL = dateFormatToServer.parse(strnoteSentDateInFormated);
               
                //SQL Date For Stored Procuder Init
                sqlSdulSetDate = new java.sql.Date(scdulSetDateSQL.getTime());
             }
            
       String strSyllabus=examData.getString("Syllabus");
       
       String base64Syllabus=StringUtil.base64ToString(strSyllabus);
       callable=con.getConnection().prepareCall(sql);
       
       callable.setInt(1,0);
       callable.setInt(2,setBy);
       
       callable.setDate(3, sqlSdulSetDate);
       callable.setInt(4, exmScdulId);
       
       callable.setString(5, base64Syllabus);
       callable.setInt(6, setBy);
       
       callable.setDate(7, sqlSdulSetDate);
       callable.setInt(8, 1);
       
       effRow=callable.executeUpdate();
       
       resId=callable.getInt(9);
       
       
       if(resId>0)
            {
                 outObject.put("Status", "Success");
                 outObject.put("Message", "Syllabus added sucessfully");
                 fcm.sendClasswisePushNotification(clsid, base64Syllabus, "Exam Syllabus");
            }else if(resId==-1){
                 outObject.put("Status", "Fail");
                 outObject.put("Message", "Syllabus has been already added");
            } else {
                outObject.put("Status", "Fail");
                 outObject.put("Message", "Syllabus couldn't be added");
            }
            
            
          return outObject.toString();
       }catch(Exception e){
            String exce = e.toString();
            String exc = "{\"Status\":\"Exception\" ,  \"Message\":\""+exce+"\"}";
            return exc; 
       } finally{
            closeDBConnection();
        }
        
   }
   public String editExamSyllabus(){
       try{
       String sql="CALL `Sp_Insert_ExamSyllabus`(?,?,?,?,?,?,?,?,?);";

            setBy = Integer.parseInt(examData.getString("SetBy"));
             int examSyllabusId=Integer.parseInt(examData.getString("ExamSyllabusId"));

             Date syllabusSetDate=null;
            int exmScdulId=Integer.parseInt(examData.getString("ExamSceduleId"));
             String syllabusEditDate = examData.getString("EditDate");
          Timestamp tmpSyllabusEditedDate=null;
           java.sql.Date  syllabusSetDateSql=null;
           if(!(syllabusEditDate.equals("")))
             {
                 Date SyllabusDateFrom = new SimpleDateFormat("dd-MM-yyyy HH:mm").parse(syllabusEditDate);
               
                SimpleDateFormat dateFormatToServer = new SimpleDateFormat("yyyy-MM-dd HH:mm");
                String strSyllabusDateInFormated = dateFormatToServer.format(SyllabusDateFrom);
                tmpSyllabusEditedDate=new Timestamp(SyllabusDateFrom.getTime());      
                syllabusSetDate = dateFormatToServer.parse(strSyllabusDateInFormated);
               
                //SQL Date For Stored Procuder Init
                syllabusSetDateSql = new java.sql.Date(syllabusSetDate.getTime());
             }
            /*
            String scdulOnDate = examData.getString("ExamOnDate"); 
           //  java.util.Date ntcDate= formatterentrdt.parse(noteOnDate);
                  
           
           
            if(!(scdulOnDate.equals("")))
             {
                 Date noticeDateTo = new SimpleDateFormat("dd-MM-yyyy HH:mm").parse(scdulOnDate);
               
                SimpleDateFormat dateFormatToServer = new SimpleDateFormat("yyyy-MM-dd HH:mm");
                String strNtcToDateInFormated = dateFormatToServer.format(noticeDateTo);
                
                scdulOnDateSQL = dateFormatToServer.parse(strNtcToDateInFormated);
               
                //SQL Date For Stored Procuder Init
                sqlScdulOnDate = new java.sql.Date(scdulOnDateSQL.getTime());
             }
           
            */
       String strSyllabus=examData.getString("Syllabus");
       String base64Syllabus=StringUtil.base64ToString(strSyllabus);
       callable=con.getConnection().prepareCall(sql);
       
       callable.setInt(1,examSyllabusId);
       callable.setInt(2,0);
       
       callable.setDate(3, null);
       callable.setInt(4, exmScdulId);
       
       callable.setString(5, base64Syllabus);
       callable.setInt(6, setBy);
       
       callable.setTimestamp(7, tmpSyllabusEditedDate);
       callable.setInt(8, 1);
       
       effRow=callable.executeUpdate();
       
       resId=callable.getInt(9);
       
       
       if(resId==-2)
            {
                 outObject.put("Status", "Success");
                 outObject.put("Message", "Syllabus Edited sucessfully");
                  fcm.sendClasswisePushNotification(clsid, base64Syllabus, "Exam Syllabus update");
                 
            }else if(resId==-1){
                 outObject.put("Status", "Fail");
                 outObject.put("Message", "No changes in the syllabus to edit");
            } else {
                outObject.put("Status", "Fail");
                 outObject.put("Message", "Syllabus couldn't be Edited");
            }
            
            
          return outObject.toString();
       }catch(Exception e){
            String exce = e.toString();
            try{
                outObject.put("Status", "Error");
                 outObject.put("Message",exce);
            }catch(Exception ee){
                
            }
            
            String exc = "{\"Status\":\"Exception\" ,  \"Message\":\""+exce+"\"}";
            return exc; 
       } finally{
            closeDBConnection();
        } 
   }
   public String deleteExamSyllabus(){
      try{
      /* String sql="CALL Sp_Delete_ExamSyllabusById(?);";
       int examSyllabusId=Integer.parseInt(examData.getString("ExamSyllabusId"));
      callable=con.getConnection().prepareCall(sql);
       callable.setInt(1, examSyllabusId);
        effRow=callable.executeUpdate();
       
       
       
       
       if(effRow==1)
            {
                 outObject.put("Status", "Success");
                 outObject.put("Message", "Syllabus Deleted sucessfully");
            }else {
                outObject.put("Status", "Fail");
                 outObject.put("Message", "Syllabus couldn't be deleted");
            }
            
         */
                outObject.put("Status", "Fail");
                outObject.put("Message", " This feature is no more, please update the app");
          return outObject.toString();
       }catch(Exception e){
            String exce = e.toString();
            try{
                outObject.put("Status", "Error");
                 outObject.put("Message",exce);
            }catch(Exception ee){
                
            }
            String exc = "{\"Status\":\"Exception\" ,  \"Message\":\""+exce+"\"}";
            return exc; 
       } finally{
            closeDBConnection();
        }
       
   }
   public void fetchExamNameForResult(){
       try{
           
           JSONObject object1 = new JSONObject();
                    
                    object1.put("Exam", "Select Exam");
                    object1.put("ExamId", "0");
                    object1.put("IsWorkBookExam","0");
                     array.put(object1);
           
           do
               {
                    JSONObject object = new JSONObject();
                    String strExam = rs.getString("Exam");
                    String strExamId= rs.getString("ExamId");
                    String strIsWorkBookExam=rs.getString("Is_Work_Book_Exam");
                    //Is_Work_Book_Exam
                    object.put("Exam", strExam);
                    object.put("ExamId", strExamId);
                    object.put("IsWorkBookExam", strIsWorkBookExam);
                     array.put(object);
               }while(rs.next());
       }catch(Exception e){
           e.printStackTrace();
       }
   }
   public void fetchExamName(){
      
       try{
           
           JSONObject object1 = new JSONObject();
                    
                    object1.put("Exam", "Select Exam");
                    object1.put("ExamId", "0");
                     array.put(object1);
           
           do
               {
                    JSONObject object = new JSONObject();
                    String strExam = rs.getString("Exam");
                   String strExamId= rs.getString("ExamId");
                    object.put("Exam", strExam);
                    object.put("ExamId", strExamId);
                     array.put(object);
               }while(rs.next());
       }catch(Exception e){
           e.printStackTrace();
       }
   }
   public String getStudentResultExamName(){
       try
           {
               
           // String sql= "CALL `sp_and_get_exam_name`(?)";
            String sql= "CALL `sp_and_student_get_result_exam_name`(?)";
            fetchStudentData(examData,sql);
            
           if(rs.first())
           {
               do
               {
                    JSONObject object = new JSONObject();
                    String strExam = rs.getString("Exam");
                    String strExamId=rs.getString("ExamId");    
                    object.put("Exam", strExam);
                    object.put("ExamId", strExamId);
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
   public String getExamName()
   {
       
           try
           {
               
           // String sql= "CALL `sp_and_get_exam_name`(?)";
            String sql= "CALL `sp_exam_list_for_view_result`(?)";
            fetchStudentData(examData,sql);
            
           if(rs.first())
           {
               do
               {
                    JSONObject object = new JSONObject();
                    String strExam = rs.getString("Exam");
                    String strExamId=rs.getString("ExamId");    
                    object.put("Exam", strExam);
                    object.put("ExamId", strExamId);
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
   public  String getStaffExamName(){
       try
           {
               
            String sql= "CALL `Sp_Dropdown_ExamIdMaster`(?)";
            
            fetchStaffExamData(examData,sql);
            
           if(rs.first())
           {
               
               fetchExamName();
                    if(array.length()!=0){
                        printSuccessStatus(array);
                    }else {
                        JSONObject object1 = new JSONObject();
                    
                    object1.put("Exam", "Select Exam");
                    object1.put("ExamId", "0");
                     array.put(object1);
                        printFaliureStatus(array);
                    }
           }
           
          else {
               JSONObject object1 = new JSONObject();
                    
                    object1.put("Exam", "Select Exam");
                    object1.put("ExamId", "0");
                     array.put(object1);
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
   public  String getExamNameForSchedule(){
       try
           {
               
            String sql= "CALL `Sp_Dropdown_Main_ExamSchedule_Exams`(?)";
            
            fetchSchoolIdForExam(examData,sql);
            
           if(rs.first())
           {
               
               fetchExamName();
                    if(array.length()!=0){
                        printSuccessStatus(array);
                    }else {
                        JSONObject object1 = new JSONObject();
                    
                    object1.put("Exam", "Select Exam");
                    object1.put("ExamId", "0");
                     array.put(object1);
                        printFaliureStatus(array);
                    }
           }
           
          else {
               JSONObject object1 = new JSONObject();
                    
                    object1.put("Exam", "Select Exam");
                    object1.put("ExamId", "0");
                     array.put(object1);
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
   
   
   public  String getEnteredExamNameForScheduleOfClass(){
       try
           {
               
            String sql= "CALL `Sp_Dropdown_Entered_ExamSchedule_Exams`(?)";
            
            fetchClassIdForExam(examData,sql);
            
           if(rs.first())
           {
               
               fetchExamName();
                    if(array.length()!=0){
                        printSuccessStatus(array);
                    }else {
                        JSONObject object1 = new JSONObject();
                    
                    object1.put("Exam", "Select Exam");
                    object1.put("ExamId", "0");
                     array.put(object1);
                        printFaliureStatus(array);
                    }
           }
           
          else {
               JSONObject object1 = new JSONObject();
                    
                    object1.put("Exam", "Select Exam");
                    object1.put("ExamId", "0");
                     array.put(object1);
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
   /*public String getEnteredExamNameForMarksOfClass(){
       try
           {
               
            String sql= "CALL `Sp_Dropdown_ExamNames_For_Marks_Of_Class`(?)";
            
            fetchClassIdForExam(examData,sql);
            
           if(rs.first())
           {
               
               //fetchExamName();
               fetchExamNameForResult();
                    if(array.length()!=0){
                        printSuccessStatus(array);
                    }else {
                        JSONObject object1 = new JSONObject();
                    
                    object1.put("Exam", "Select Exam");
                    object1.put("ExamId", "0");
                     array.put(object1);
                        printFaliureStatus(array);
                    }
           }
           
          else {
               JSONObject object1 = new JSONObject();
                    
                    object1.put("Exam", "Select Exam");
                    object1.put("ExamId", "0");
                     array.put(object1);
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
   }*/
   public String getEnteredExamNameForMarksOfClass(){
       try
           {
               
            String sql= "CALL `Sp_Dropdown_ExamNames_For_Marks_Of_Class`(?)";
            
            fetchClassIdForExam(examData,sql);
            
           if(rs.first())
           {
               
               //fetchExamName();
               fetchExamNameForResult();
                    if(array.length()!=0){
                        printSuccessStatus(array);
                    }else {
                        JSONObject object1 = new JSONObject();
                    
                    object1.put("Exam", "Select Exam");
                    object1.put("ExamId", "0");
                     array.put(object1);
                        printFaliureStatus(array);
                    }
           }
           
          else {
               JSONObject object1 = new JSONObject();
                    
                    object1.put("Exam", "Select Exam");
                    object1.put("ExamId", "0");
                     array.put(object1);
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
   public String getEnteredExamNameForMarksOfSubject(){
       try
           {
               
            String sql= "CALL sp_get_entered_exam_names_for_marks_of_subject(?)";
            
            fetchClassIdForExam(examData,sql);
            
           if(rs.first())
           {
               
               //fetchExamName();
               fetchExamNameForResult();
                    if(array.length()!=0){
                        printSuccessStatus(array);
                    }else {
                        JSONObject object1 = new JSONObject();
                    
                    object1.put("Exam", "Select Exam");
                    object1.put("ExamId", "0");
                     array.put(object1);
                        printFaliureStatus(array);
                    }
           }
           
          else {
               JSONObject object1 = new JSONObject();
                    
                    object1.put("Exam", "Select Exam");
                    object1.put("ExamId", "0");
                     array.put(object1);
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
   
   
    public  String getEnteredExamNameForSyllabusOfClass(){
       try
           {
               
            String sql= "CALL `Sp_Dropdown_Entered_ExamSyllabus_Exams`(?)";
            
            fetchClassIdForExam(examData,sql);
            
           if(rs.first())
           {
               
               fetchExamName();
                    if(array.length()!=0){
                        printSuccessStatus(array);
                    }else {
                        JSONObject object1 = new JSONObject();
                    
                    object1.put("Exam", "Select Exam");
                    object1.put("ExamId", "0");
                     array.put(object1);
                        printFaliureStatus(array);
                    }
           }
           
          else {
               JSONObject object1 = new JSONObject();
                    
                    object1.put("Exam", "Select Exam");
                    object1.put("ExamId", "0");
                     array.put(object1);
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
    
    public  String getEnteredExamNameForMarksOfStaff(){
       try
           {
               
            String sql= "CALL `Sp_Dropdown_ExamNames_For_Marks_Of_Staff`(?,?)";
            
               fetchStaffIdAndAcedemicYearIdForExam(examData,sql);
            
           if(rs.first())
           {
               
               fetchExamName();
                    if(array.length()!=0){
                        printSuccessStatus(array);
                    }else {
                        JSONObject object1 = new JSONObject();
                    
                    object1.put("Exam", "Select Exam");
                    object1.put("ExamId", "0");
                     array.put(object1);
                        printFaliureStatus(array);
                    }
           }
           
          else {
               JSONObject object1 = new JSONObject();
                    
                    object1.put("Exam", "Select Exam");
                    object1.put("ExamId", "0");
                     array.put(object1);
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
    
    
    
    
    
    public  String getEnteredExamNameForScheduleOfStaff(){
       try
           {
               
            String sql= "CALL `Sp_Dropdown_Entered_ExamSchedule_Exams_Staffwise`(?,?)";
            
               fetchStaffIdAndAcedemicYearIdForExam(examData,sql);
            
           if(rs.first())
           {
               
               fetchExamName();
                    if(array.length()!=0){
                        printSuccessStatus(array);
                    }else {
                        JSONObject object1 = new JSONObject();
                    
                    object1.put("Exam", "Select Exam");
                    object1.put("ExamId", "0");
                     array.put(object1);
                        printFaliureStatus(array);
                    }
           }
           
          else {
               JSONObject object1 = new JSONObject();
                    
                    object1.put("Exam", "Select Exam");
                    object1.put("ExamId", "0");
                     array.put(object1);
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
   
    public  String getEnteredExamNameForSyllabusOfStaff(){
       try
           {
               
            String sql= "CALL `Sp_Dropdown_Entered_ExamSyllabus_Exams_Staffwise`(?,?)";
            
            fetchStaffIdAndAcedemicYearIdForExam(examData,sql);
            
           if(rs.first())
           {
               
               fetchExamName();
                    if(array.length()!=0){
                        printSuccessStatus(array);
                    }else {
                        JSONObject object1 = new JSONObject();
                    
                    object1.put("Exam", "Select Exam");
                    object1.put("ExamId", "0");
                     array.put(object1);
                        printFaliureStatus(array);
                    }
           }
           
          else {
               JSONObject object1 = new JSONObject();
                    
                    object1.put("Exam", "Select Exam");
                    object1.put("ExamId", "0");
                     array.put(object1);
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
    
    
   
   
   
   
   public String getSyllabusExamName(){
        try
           {
               
            String sql= "CALL `Sp_Dropdown_ExamIdMaster`(?)";
            
            fetchStaffExamData(examData,sql);
            
           if(rs.first())
           {
               
               fetchExamName();
                    if(array.length()!=0){
                        printSuccessStatus(array);
                    }else {
                        JSONObject object1 = new JSONObject();
                    
                    object1.put("Exam", "Select Exam");
                    object1.put("ExamId", "0");
                     array.put(object1);
                        printFaliureStatus(array);
                    }
           }
           
          else {
               JSONObject object1 = new JSONObject();
                    
                    object1.put("Exam", "Select Exam");
                    object1.put("ExamId", "0");
                     array.put(object1);
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
   public String getResultExamName(){
        try
           {
               
            String sql= "CALL `sp_and_staff_get_result_exam_name`(?)";
            
               fetchStaffExamResultData(examData, sql);
            
           if(rs.first())
           {
               
               fetchExamName();
                    if(array.length()!=0){
                        printSuccessStatus(array);
                    }else {
                        JSONObject object1 = new JSONObject();
                    
                    object1.put("Exam", "Select Exam");
                    object1.put("ExamId", "0");
                     array.put(object1);
                        printFaliureStatus(array);
                    }
           }
           
          else {JSONObject object1 = new JSONObject();
                    
                    object1.put("Exam", "Select Exam");
                    object1.put("ExamId", "0");
                     array.put(object1);
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
   public String getScheduleExamName(){
        try
           {
               
            String sql= "CALL `sp_and_get_schedule_exam_name`(?)";
            
            fetchStaffExamData(examData,sql);
            
           if(rs.first())
           {
               
               fetchExamName();
                    if(array.length()!=0){
                        printSuccessStatus(array);
                    }else {
                        JSONObject object1 = new JSONObject();
                    
                    object1.put("Exam", "Select Exam");
                    object1.put("ExamId", "0");
                     array.put(object1);
                        printFaliureStatus(array);
                    }
           }
           
          else {
               JSONObject object1 = new JSONObject();
                    
                    object1.put("Exam", "Select Exam");
                    object1.put("ExamId", "0");
                     array.put(object1);
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
   public String getresultExamSchedule(){
       try
           {
               
            String sql= "CALL `Sp_Get_ExamSchedule_In_tblExamResult`(?,?,?)";
            
             int clsId= Integer.parseInt(examData.getString("ClassId"));
            
            int subId= Integer.parseInt(examData.getString("SubjectId"));
            int exmId= Integer.parseInt(examData.getString("ExamId"));
           callable= con.getConnection().prepareCall(sql);
        callable.setInt(1,clsId);
        callable.setInt(2,subId);
        callable.setInt(3, exmId);
            rs=callable.executeQuery();
           if(rs.first())
           {
               JSONObject object1 = new JSONObject();
                    
                    object1.put("Scheduledate", "Select the Date");
                    object1.put("ExamId", "0");
                    array.put(object1);
                    
               do
               {
                    JSONObject object = new JSONObject();
                    String strScheduledate = rs.getString("Scheduledate");
                   String strExamId= rs.getString("ExamId");
                    object.put("Scheduledate", strScheduledate);
                    object.put("ExamId", strExamId);
                     array.put(object);
               }while(rs.next());
               
                  outObject.put("Status", "Success");
                  outObject.put("Message", "Data found..");
                  outObject.put("Result", array);
           }
           
          else {
               JSONObject object1 = new JSONObject();
                    
                    object1.put("Scheduledate", "Select the Date");
                    object1.put("ExamId", "0");
                    array.put(object1);
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

}

