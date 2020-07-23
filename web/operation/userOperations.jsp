<%-- 
Document   : useroperations
Created on : 23 Jul, 2017, 12:15:30 AM
Author     : basava
--%>

<%@page import="com.example.operations.UserOPeration"%>
<%@page import="java.io.BufferedReader"%>
<%@page import="org.json.JSONObject"%>
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
        String opName = object.getString("OperationName");
        JSONObject userData = object.getJSONObject("userData");
        UserOPeration oPeration = new UserOPeration(userData);

        // {"OperationName":"addUser","userData":{"UserId":"anabdcj","password":"12345","email":"abc@kbc.com","mobileNumber":"12345678990"}}
        if (opName.equals("addUser")) {

            out.println(oPeration.addUser());
        } //{"OperationName":"addUserChild","userData":{"UserId":"anirudh","schoolName":"Bhartiya vidyapeeth","class":"5th","section":"A","rollNo":"1"}}
        else if (opName.equals("addUserChild")) {
            //{"":"","":{"":""}}
            out.println(oPeration.addUserChild());
        } //  {"OperationName":"authUser","userData":{"Phonenumber":"7676036259","Password":"qwerty"}}
        else if (opName.equals("authUser")) {
            //{"":"","":{"":""}}
            out.println(oPeration.authUser());
        } //{"OperationName":"authStaff","userData":{"StaffId":"stf2","Password":"admin"}}
        else if (opName.equals("authStaff")) {
            //{"":"","":{"":""}}
            out.println(oPeration.authStaff());
        } //  {"OperationName":"changePassword","userData":{"UserId":"basu","Password":"basu123"}}
        else if (opName.equals("changePassword")) {
            out.println(oPeration.changePassword());
        } // {"OperationName":"changeStaffPassword","userData":{"StaffId":"stf2","CurPassword":"purva","NewPassword":"admin"}}
        else if (opName.equals("changeStaffPassword")) {
            out.println(oPeration.changeStaffPassword());
        } //{"OperationName":"checkIsStaffActive","userData":{"StaffId":"11"}}
        else if (opName.equals("checkIsStaffActive")) {
            out.println(oPeration.checkIsStaffActive());
        } //{"OperationName":"getPassword","userData":{"UserId":"basu"}}
        else if (opName.equals("getPassword")) {
            out.println(oPeration.getPassword());
        } //{"OperationName":"changeStaffPhonenumber","userData":{"StaffId":"stf2","Password":"admin","PhoneNumber":"7676036259"}}
        else if (opName.equals("changeStaffPhonenumber")) {
            out.println(oPeration.changeStaffPhonenumber());
        } //{"OperationName":"staffForgotPassword","userData":{"StaffId":"2"}}
        else if (opName.equals("staffForgotPassword")) {
            out.println(oPeration.staffForgotPassword());
        } //{"OperationName":"getUserId","userData":{"UserId":"basu"}}
        else if (opName.equals("getUserId")) {
            out.println(oPeration.getUserId());
        } // {"OperationName":"getSchoolName","userData":{}}
        else if (opName.equals("getSchoolName")) {
            out.println(oPeration.getSchoolName());
        } //{"OperationName":"getSectionByClass","userData":{"schoolName":"Holy cross convent","class":"1"}}
        else if (opName.equals("getSectionByClass")) {
            out.println(oPeration.getSectionByClass());
        } //{"OperationName":"getClassBySchool","userData":{"schoolName":"Holy cross convent"}}
        else if (opName.equals("getClassBySchool")) {
            out.println(oPeration.getClassBySchool());
        } //{"OperationName":"getRollNoByClass","userData":{"schoolName":"Holy cross convent","class":"1","section":"A"}}
        else if (opName.equals("getRollNoByClass")) {
            out.println(oPeration.getRollNoByClass());
        } //{"OperationName":"getStudentByRoll","userData":{"schoolName":"Holy cross convent","class":"1","section":"A","rollNo":"1"}}
        else if (opName.equals("getStudentByRoll")) {
            out.println(oPeration.getStudentByRoll());
        } //{"OperationName":"getStudentListByParent","userData":{"phoneNumber":"8805020212"}}
        else if (opName.equals("getStudentListByParent")) {
            out.println(oPeration.getStudentListByParent());
        } //{"OperationName":"submitStaffUserToken","userData":{"StaffId":"5","UserToken":"15263357","DeviceId":"257635763"}}
        else if (opName.equals("submitStaffUserToken")) {
            out.println(oPeration.submitStaffUserToken());
        } //{"OperationName":"submitParentUserToken","userData":{"PhoneNumber":"8805020212"}}
        else if (opName.equals("submitParentUserToken")) {
            out.println(oPeration.submitParentUserToken());
        } //{"OperationName":"getParentUserTokenSchoolwise","userData":{"SchoolId":"4"}}
        else if (opName.equals("getParentUserTokenSchoolwise")) {
            out.println(oPeration.getParentUserTokenSchoolwise());
        } //{"OperationName":"getParentUserTokenClasswise","userData":{"ClassId":"6"}}
        else if (opName.equals("getParentUserTokenClasswise")) {
            out.println(oPeration.getParentUserTokenClasswise());
        } //{"OperationName":"getParentUserTokenStudentWise","userData":{"StudentId":"6"}}
        else if (opName.equals("getParentUserTokenStudentWise")) {
            out.println(oPeration.getParentUserTokenStudentWise());
        } //{"OperationName":"getStaffUSertokenSchoolWise","userData":{"SchoolId":"4"}}
        else if (opName.equals("getStaffUSertokenSchoolWise")) {
            out.println(oPeration.getStaffUSertokenSchoolWise());
        } //{"OperationName":"getSttaffUserTokenStaffwise","userData":{"StaffId":"5"}}
        else if (opName.equals("getSttaffUserTokenStaffwise")) {
            out.println(oPeration.getSttaffUserTokenStaffwise());
        }
        //{"OperationName":"getUserIdPasswordByStudentId","userData":{"StudentId":"9460"}}
        else if (opName.equals("getUserIdPasswordByStudentId")) {
            out.println(oPeration.getUserIdPasswordByStudentId());
        }
        
        //{"OperationName":"getUserIdPasswordByStudenId","userData":{"StudentId":"9460"}}
        else if (opName.equals("updateStudentCredentials")) {
            out.println(oPeration.updateStudentCredentials());
        }
        
        //{"OperationName":"getUserIdPasswordByPhoneNumber","userData":{"phoneNumber":"7676036259","AcademicYearId":"4"}}
        else if (opName.equals("getUserIdPasswordByPhoneNumber")) {
            out.println(oPeration.getUserIdPasswordByPhoneNumber());
        }
        //{"OperationName":"checkPhoneNumberIsInDatabase","userData":{"PhoneNumber":"7676036259"}}
        else if (opName.equals("checkPhoneNumberIsInDatabase")) {
        //    out.println(oPeration.checkPhoneNumberIsInDatabase());
        }
         //{"OperationName":"checkRecentAppVersion","userData":{"App":"7676036259"}}
        else if (opName.equals("checkRecentAppVersion")) {
          out.println(oPeration.checkRecentAppVersion());
        }
        
    } catch (Exception e) {
        out.println(e.toString());
    }

%>

