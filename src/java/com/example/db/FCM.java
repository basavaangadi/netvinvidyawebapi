/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.db;

/**
 *
 * @author basava
 */
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.CallableStatement;
import java.sql.ResultSet;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author Lenovo
 */
public class FCM {

    DBConnect con = new DBConnect();
  JSONObject outObject=new JSONObject();
  JSONArray array= new JSONArray();
  ResultSet rs=null;
  CallableStatement callable=null;
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
    
    
  public void fetchParentUSerTokenDetails(String strMessage,String strTitle){
    
    try{
        
        do{
                JSONObject object=new JSONObject();
                /*
                String strSchoolId=rs.getString("SchoolId");
                String strSchoolName=rs.getString("SchoolName");
                String strClass=rs.getString("Class");
                String strRollNo=rs.getString("RollNo");
                String strStudentDetailsId=rs.getString("StudentDetailsId");
                String strStudentName=rs.getString("StudentName");*/
                String strParent_user_device_id=rs.getString("Parent_user_device_id");
                String strParent_user_device_token=rs.getString("Parent_user_device_token");
                
                // send the push notification to parents
                
                send_FCM_Notification(strParent_user_device_token, strMessage, strTitle);
                /*
                
                object.put("SchoolId",strSchoolId);
                object.put("SchoolName",strSchoolName);
                object.put("Class",strClass);
                object.put("RollNo",strRollNo);
                object.put("StudentDetailsId",strStudentDetailsId);
                object.put("StudentName",strStudentName);
                object.put("ParentUserDeviceId",strParent_user_device_id);
                object.put("ParentUserDeviceToken",strParent_user_device_token);
            
                array.put(object);
                
                */
        }while(rs.next());
    
    }catch(Exception e){
            e.printStackTrace();
        }
    
}
  
  
  
  public enum Status {
      SENT_SUCCESS, ERROR_SERVER, EXCEPTION
    }
    
    
    public class FCMResponse {
       Status status;
       String response;

        public Status getStatus() {
            return status;
        }

        public void setStatus(Status status) {
            this.status = status;
        }

        public String getResponse() {
            return response;
        }

        public void setResponse(String response) {
            this.response = response;
        }
    }
  
    static FCM fcm;
    
    public static FCM getInstance() {
        if(fcm == null) {
            fcm = new FCM();
        }
        return fcm;
    }
    
    final static private String FCM_URL = "https://fcm.googleapis.com/fcm/send";

    /**
     * Method to send push notification to Android FireBased Cloud messaging
     * Server.
     * @param strTokenId Generated and provided from Android Client Developer
     *
     * @param message which contains actual information.
     * @return 
     */
    public FCMResponse send_FCM_Notification(String strTokenId, String strMessage,String strTitle) {
            
        try {
            
            
            
            
            // Create URL instance.
            URL url = new URL(FCM_URL);
            // create connection.
            HttpURLConnection conn;
            conn = (HttpURLConnection) url.openConnection();
            conn.setUseCaches(false);
            conn.setDoInput(true);
            conn.setDoOutput(true);
            //set method as POST or GET
            conn.setRequestMethod("POST");
            //pass FCM server key
            conn.setRequestProperty("Authorization", "key=" + FCMConfig.LEGACY_SERVER_KEY);
            //Specify Message Format
            conn.setRequestProperty("Content-Type", "application/json");
            //Create JSON Object & pass value
            JSONObject infoJson = new JSONObject();
            infoJson.put("title", strTitle);
            infoJson.put("body", strMessage);
//            strTokenId="fRtbdzPpJkE:APA91bG4cd7FzJl2tWNEVvNhyPILQde6rqiurrUjIQFgvEX4KYj89GXKmKOvCIyU25sNj7o2i9wjBepwCnzM7R5OMSHkSNHaAXz62GWNViWWwb9nj1ie5l_XH8skpYNFzMfZD8hdIaW2";    
            JSONObject json = new JSONObject();
            json.put("to", strTokenId.trim());
            json.put("notification", infoJson);

            OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
            wr.write(json.toString());
            wr.flush();
            int status = 0;

            if (null != conn) {
                status = conn.getResponseCode();
            }
            
            if (status != 0) {
               
                if (status == 200) {
                    //SUCCESS message
                    
                    BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                   
                    FCMResponse fcmResp = new FCMResponse();
                    fcmResp.setResponse(reader.readLine());
                    fcmResp.setStatus(Status.SENT_SUCCESS);
                    return fcmResp;
                } else if (status == 401) {
                    //client side error
                    FCMResponse fcmResp = new FCMResponse();
                    fcmResp.setResponse("Notification Response : TokenId : " + strTokenId + " Error occurred :");
                    fcmResp.setStatus(Status.ERROR_SERVER);
                    
                    return fcmResp;
                } else if (status == 501) {
                    //server side error
                    FCMResponse fcmResp = new FCMResponse();
                    fcmResp.setResponse("Notification Response : [ errorCode=ServerError ] TokenId : " + strTokenId);
                    fcmResp.setStatus(Status.ERROR_SERVER);
               
                    return fcmResp;
                    
                } else if (status == 503) {
                    //server side error
             
                    FCMResponse fcmResp = new FCMResponse();
                    fcmResp.setResponse("Notification Response : FCM Service is Unavailable  TokenId : " + strTokenId);
                    fcmResp.setStatus(Status.ERROR_SERVER);
                     return fcmResp;
                }
            }

        } catch (MalformedURLException mlfexception) {
            // Prototcal Error
             FCMResponse fcmResp = new FCMResponse();
             fcmResp.setResponse("Error occurred while sending push Notification!.." + mlfexception.getMessage());
             fcmResp.setStatus(Status.EXCEPTION);
             return fcmResp;
        } catch (IOException mlfexception) {
            //URL problem
       
            FCMResponse fcmResp = new FCMResponse();
             fcmResp.setResponse("Error occurred while sending push Notification!.." + mlfexception.getMessage());
             fcmResp.setStatus(Status.EXCEPTION); 
              return fcmResp;
        } catch (JSONException jsonexception) {
            //Message format error
             FCMResponse fcmResp = new FCMResponse();
             fcmResp.setResponse("Message Format, Error occurred while sending push Notification!.." + jsonexception.getMessage());
             fcmResp.setStatus(Status.EXCEPTION); 
              return fcmResp;
           
        } catch (Exception exception) {
            //General Error or exception.
            FCMResponse fcmResp = new FCMResponse();
             fcmResp.setResponse("Error occurred while sending push Notification!.." + exception.getMessage());
             fcmResp.setStatus(Status.EXCEPTION); 
              return fcmResp;

        }
        return null;
    }

    public String sendClasswisePushNotification(int classId,String strMessage,String strTitle){
         
        try {
        String sql="call sp_get_parent_usertoken_details_classwise(?)";
           
                   callable= con.getConnection().prepareCall(sql);
                   //callable.setString(1, strUserId);
                   callable.setInt(1,classId );
                   
                  
                   rs= callable.executeQuery();
                 
                   if(rs.first()){
                       fetchParentUSerTokenDetails(strMessage,strTitle);
                     
                       if(array.length()!=0){
                           printSuccessStatus(array);
                       }else{
                           printFaliureStatus(array);
                       }
                    }else{
                       printFaliureStatus(array);
            }
               return null;
            
        } catch (Exception e) {
            String exce = e.toString();
            String exc = "{\"Status\":\"Exception\" ,  \"Message\":\""+exce+"\"}";
            return exc;
            
        }finally{
            closeDBConnection();
        }
    } 
 public String sendStudentPushNotificationSchoolWise(int schoolId,String strMessage,String strTitle){
     try{ 
     String sql="call sp_get_parent_usertoken_details_schoolwise(?)";
           
                   callable= con.getConnection().prepareCall(sql);
                   //callable.setString(1, strUserId);
                   callable.setInt(1,schoolId );
                   
                   rs=callable.executeQuery();
                 
                if(rs.first()){
                      fetchParentUSerTokenDetails(strMessage,strTitle);
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
}
