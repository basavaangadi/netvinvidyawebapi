/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.operations;

import com.example.db.DBConnect;
import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 *
 * @author basava
 */
public class ServiceWithJsonArray {
    
    JSONObject jsonData;
     String studentId,schoolId,staffId,academicYearIdId;
     int studId,stfId,acdmicId;
     DBConnect con = new DBConnect();
     JSONObject outObject=new JSONObject();
     JSONArray array= new JSONArray();
      ResultSet rs=null;      
      CallableStatement callable=null;
      int resId,effRows;
   public ServiceWithJsonArray(JSONObject jsonData){
    this.jsonData=jsonData;
}  
   
   public void closeDBConnection()
    {
        try{
            callable.close();
            }catch(Exception e){
                e.printStackTrace();
            }
    }
   public String homeworkFeedback_Insert_withJsonArray()
   {
       
           try
           {
              
             String sql =  " CALL `Sp_Insert_HomeWorkFeedback`(?,?,?,?,?,?,?,?,?);";
             JSONArray hwfebckArray=jsonData.getJSONArray("studentArray");
             for(int i=0;i<hwfebckArray.length();i++){
              
                 JSONObject hmfebckObject=hwfebckArray.getJSONObject(i);
             String homeworkId=  hmfebckObject.getString("HomeworkId");
              int hmwrkId = Integer.parseInt(homeworkId);
            String enteredBy = hmfebckObject.getString("EnteredBy");
        
            int entrBy = Integer.parseInt(enteredBy);
            String studentId = hmfebckObject.getString("StudentId");
            int studId = Integer.parseInt(studentId);
            String status = hmfebckObject.getString("Status");
            int sts = Integer.parseInt(status);
            String hmwrkDate=hmfebckObject.getString("Date");
            int hmwrkfdbckId=0;
            Date hmwrkDateSQL = null;
             java.sql.Date sqlhmwrkDate = null;
             if(!(hmwrkDate.equals("")))
             {
                 Date homewrokDate = new SimpleDateFormat("dd-MM-yyyy").parse(hmwrkDate);
               
                SimpleDateFormat dateFormatToServer = new SimpleDateFormat("yyyy-MM-dd");
                String strhmwrkDateInFormated = dateFormatToServer.format(homewrokDate);
                
                hmwrkDateSQL = dateFormatToServer.parse(strhmwrkDateInFormated);
               
                //SQL Date For Stored Procuder Init
                sqlhmwrkDate = new java.sql.Date(hmwrkDateSQL.getTime());
             }
            
            
            
            callable=con.getConnection().prepareCall(sql);
           
            callable.setInt(1,0);
            callable.setInt(2, hmwrkId);
            callable.setDate(3, sqlhmwrkDate);
            callable.setInt(4, entrBy);
            callable.setInt(5, studId);
            callable.setInt(6, sts);
            callable.setInt(7, entrBy);
            callable.setDate(8, sqlhmwrkDate);
//            callable.setInt(9,0);
            
            effRows=callable.executeUpdate();
                resId=callable.getInt("ResultId");
               
                if(resId==-1){
                 outObject.put("Status", "fail");
                 outObject.put("Message", " data already exist");
          
                 break;
                }
             }
             if(resId>0)
            {
                 outObject.put("Status", "Success");
                 outObject.put("Message", "inserted sucessfully");
                 outObject.put("effected rows",effRows);
            }else if(resId==-1){
                 outObject.put("Status", "fail");
                 outObject.put("Message", " data already exist ");
            }else {
            outObject.put("Status", "fail");
                 outObject.put("Message", " something went wrong");
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
    
    
}
