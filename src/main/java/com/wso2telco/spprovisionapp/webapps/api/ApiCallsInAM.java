/*******************************************************************************
 * Copyright  (c) 2015-2017, WSO2.Telco Inc. (http://www.wso2telco.com) All Rights Reserved.
 *
 * WSO2.Telco Inc. licences this file to you under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ******************************************************************************/
package com.wso2telco.spprovisionapp.webapps.api;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.http.HttpResponse;
import org.apache.http.client.CookieStore;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.client.DefaultHttpClient;

public class ApiCallsInAM {

    private String amUserCreationAPI = "http://localhost:9763/store/site/blocks/user/sign-up/ajax/user-add.jag";
    private String amLoginAPI = "http://localhost:9763/store/site/blocks/user/login/ajax/login.jag";
    private String amAppCreationAPI = "http://localhost:9763/store/site/blocks/application/application-add/ajax/application-add.jag";
    private String addSubscriptionAPI = "http://localhost:9763/store/site/blocks/subscription/subscription-add/ajax/subscription-add.jag";
    private String password = "1qaz2wsx";
    private String appName ;
    List<Cookie> cookies;
    CookieStore cookieStore;
    
    //Create new User in AM
    public String createNewUserInAm(String appName, String devEmail) throws IOException {

       // appName = this.appName;
        String url = amUserCreationAPI+"?allFieldsValues=" + appName + "%7C" + appName + "%7C" + devEmail + "&username=" + appName + "&password=" + password + "&action=addUser";
        DefaultHttpClient httpClient = new DefaultHttpClient();
        String response = postToURL(url, httpClient);
        httpClient.getConnectionManager().shutdown();
        return response;
    }
    
    //Login the new User to API
    public String loginToAm(String appName) throws IOException {
        //chnage this if it start from the midele.User has to insert the app name as well.
        cookieStore = null;

        String url = amLoginAPI+"?username="+appName+"&password="+password+"&action=login";
        System.out.println();
        DefaultHttpClient httpClient = new DefaultHttpClient();
        String response = postToURL(url, httpClient);
        httpClient.getConnectionManager().shutdown();
        cookieStore = httpClient.getCookieStore();
        return response;
    }
    
        //Create application in AM
    public String createApplication(String appName,String description,String callback) throws IOException {
        
        String url = amAppCreationAPI +"?action=addApplication&application="+appName+"&tier=Unlimited&description="+description+"&callbackUrl="+callback+"";
        DefaultHttpClient httpClient = new DefaultHttpClient();
        httpClient.setCookieStore(cookieStore);
        String response = postToURL(url, httpClient);
        httpClient.getConnectionManager().shutdown();
        return response;
    }
    
    //add subscriptions to the newly created app
    public String addSubscritions(String appName,String apiName,String apiVersion,String apiProvider,String tier){
        
        String url = addSubscriptionAPI+"?action=addAPISubscription&name="+apiName+"&version="+apiVersion+"&provider="+apiProvider+"&tier="+tier+"&applicationName="+appName+"";
        DefaultHttpClient httpClient = new DefaultHttpClient();
        httpClient.setCookieStore(cookieStore);
        String response = postToURL(url, httpClient);
        httpClient.getConnectionManager().shutdown();
        return response;
    }
    
    private static String postToURL(String url, DefaultHttpClient httpClient) {
        try {
            HttpPost postRequest = new HttpPost(url);
           
            postRequest.addHeader("Content-Type", "application/json");
            HttpResponse response = httpClient.execute(postRequest);
            
            BufferedReader br = new BufferedReader(
                    new InputStreamReader((response.getEntity().getContent())));
            
            String output;
            StringBuffer totalOutput = new StringBuffer();
            String postResponse;
            
        
            while ((output = br.readLine()) != null) {
                totalOutput.append(output);
            }
            
            postResponse = "{\"httpCode\": "+response.getStatusLine().getStatusCode()+",\"output\": "+totalOutput.toString()+"}";
            return postResponse;
        } catch (IOException ex) {
            Logger.getLogger(ApiCallsInAM.class.getName()).log(Level.SEVERE, null, ex);
            String postResponse = "{\"exception\": "+ex.toString()+"\"}";
            return postResponse;
        }
    }
    
}
