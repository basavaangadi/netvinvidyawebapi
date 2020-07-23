/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.db;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.bind.DatatypeConverter;

/**
 *
 * @author basava
 */
public class StringUtil {
    public static String  base64ToString(String baseStr){
       
       // byte[] decoded = Base64.getMimeDecoder().decode(baseStr);
       // String output = new String(decoded);
     //byte[] decoded= DatatypeConverter.parseBase64Binary("aGVsbG8gd29ybGQ=");
     try{
       byte[] decoded= DatatypeConverter.parseBase64Binary(baseStr);
       String output= new String(decoded,"UTF-8");
       
        return output;
     }catch(Exception e){
         e.printStackTrace();
         return e.toString();
     }
    }
}
