/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.db;

import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.net.URLConnection;
import java.net.URLEncoder;
import org.json.JSONArray;
import org.json.JSONObject;



public class SendSms{

public void sendMessage(String strPhoneNumberList,String textMessage)
       
{
    
//Your authentication key
String authkey = "222059ACFHhJkC5b2dfd12";
//Multiple mobiles numbers separated by comma
String mobiles = "";
//Sender ID,While using route4 sender id should be 6 characters long.
String senderId = "VinVid";
//Your message to send, Add URL encoding here.
//String message = "hi this is the message to check the characters \n of the message for msg 91 this is the test message please ignore if it had come for now, Actually we are doing sms test with character length more than 169 ";

String message = textMessage;
//define route
String route="4";
String unicode="1";
//Prepare Url
URLConnection myURLConnection=null;
URL myURL=null;
BufferedReader reader=null;

//encoding message
String encoded_message=URLEncoder.encode(message);

//Send SMS API
String mainUrl="http://api.msg91.com/api/sendhttp.php?";

//Prepare parameter string
StringBuilder sbPostData= new StringBuilder(mainUrl);
sbPostData.append("authkey="+authkey);
sbPostData.append("&mobiles="+strPhoneNumberList);
sbPostData.append("&message="+encoded_message);
sbPostData.append("&route="+route);
sbPostData.append("&sender="+senderId);
sbPostData.append("&unicode="+unicode);

//final string
mainUrl = sbPostData.toString();
try
{
//prepare connection
myURL = new URL(mainUrl);
myURLConnection = myURL.openConnection();
myURLConnection.connect();
reader= new BufferedReader(new InputStreamReader(myURLConnection.getInputStream()));
//reading response
String response;
while ((response = reader.readLine()) != null)
//print response
System.out.println(response);

//finally close connection
reader.close();
}
catch (IOException e)
{
e.printStackTrace();
}
}
}   

