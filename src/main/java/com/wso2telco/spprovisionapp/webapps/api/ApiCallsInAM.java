/** *****************************************************************************
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
 ***************************************************************************** */
package com.wso2telco.spprovisionapp.webapps.api;

import com.wso2telco.spprovisionapp.webapps.servlets.PropertyFileHandler;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import java.security.KeyStore;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.http.client.CookieStore;
import org.apache.http.cookie.Cookie;
import org.apache.http.HttpResponse;
import org.apache.http.HttpVersion;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;
import org.apache.http.protocol.HTTP;

public class ApiCallsInAM {

//    private String amUserCreationAPI, amLoginAPI, amAppCreationAPI, addSubscriptionAPI, password, appName;
//    private List<Cookie> cookies;
//    private CookieStore cookieStore;
//    private Properties popertiesFromPropertyFile;
//   
//    public ApiCallsInAM(String environment){
//        String host;
//        PropertyFileHandler propertyFileHandler = new PropertyFileHandler();
//
//        try {
//            popertiesFromPropertyFile = propertyFileHandler.popertiesFromPropertyFile();
//            if (environment.equalsIgnoreCase("preprod")) {
//                host = popertiesFromPropertyFile.getProperty("host_preprod_AM");
//            } else {
//                host = popertiesFromPropertyFile.getProperty("host_prod_AM");
//            }
//
//            amUserCreationAPI = host + "/store/site/blocks/user/sign-up/ajax/user-add.jag";
//            amLoginAPI = host + "/store/site/blocks/user/login/ajax/login.jag";
//            amAppCreationAPI = host + "/store/site/blocks/application/application-add/ajax/application-add.jag";
//            addSubscriptionAPI = host + "/store/site/blocks/subscription/subscription-add/ajax/subscription-add.jag";
//            password = popertiesFromPropertyFile.getProperty("default_password");
//
//        } catch (IOException ex) {
//            Logger.getLogger(ApiCallsInAM.class.getName()).log(Level.SEVERE, "Error occured in reading Property files" + ex.toString(), ex);
//        }
//    }
//
//    //Create new User in AM
//    public String createNewUserInAm(String appName, String firstName, String lastName, String devEmail) throws IOException {
//
//        String url = amUserCreationAPI + "?allFieldsValues=" + firstName + "%7C" + lastName + "%7C" + devEmail + "&username=" + appName + "&password=" + password + "&action=addUser";
//        DefaultHttpClient httpClient = new DefaultHttpClient();
//        String response = postToURL(url, httpClient);
//        httpClient.getConnectionManager().shutdown();
//        return response;
//    }
//
//    //Login the new User to API
//    public String loginToAm(String appName) throws IOException {
//        //chnage this if it start from the midele.User has to insert the app name as well.
//        cookieStore = null;
//
//        String url = amLoginAPI + "?username=" + appName + "&password=" + password + "&action=login";
//        DefaultHttpClient httpClient = new DefaultHttpClient();
//        String response = postToURL(url, httpClient);
//        httpClient.getConnectionManager().shutdown();
//        cookieStore = httpClient.getCookieStore();
//        return response;
//    }
//
//    //Create application in AM
//    public String createApplication(String appName, String description, String callback, String tier) throws IOException {
//
//        String url = amAppCreationAPI + "?action=addApplication&application=" + appName + "&tier=" + tier + "&description=" + description + "&callbackUrl=" + callback + "";
//        DefaultHttpClient httpClient = new DefaultHttpClient();
//        httpClient.setCookieStore(cookieStore);
//        String response = postToURL(url, httpClient);
//        httpClient.getConnectionManager().shutdown();
//        return response;
//    }
//
//    //add subscriptions to the newly created app
//    public String addSubscritions(String appName, String apiName, String apiVersion, String apiProvider, String tier) {
//
//        String url = addSubscriptionAPI + "?action=addAPISubscription&name=" + apiName + "&version=" + apiVersion + "&provider=" + apiProvider + "&tier=" + tier + "&applicationName=" + appName + "";
//        DefaultHttpClient httpClient = new DefaultHttpClient();
//        httpClient.setCookieStore(cookieStore);
//        String response = postToURL(url, httpClient);
//        httpClient.getConnectionManager().shutdown();
//        return response;
//    }
//
//    private static String postToURL(String url, DefaultHttpClient httpClient) {
//        try {
//            HttpPost postRequest = new HttpPost(url);
//
//            postRequest.addHeader("Content-Type", "application/json");
//            HttpResponse response = httpClient.execute(postRequest);
//
//            BufferedReader br = new BufferedReader(
//                    new InputStreamReader((response.getEntity().getContent())));
//
//            String output;
//            StringBuffer totalOutput = new StringBuffer();
//            String postResponse;
//
//            while ((output = br.readLine()) != null) {
//                totalOutput.append(output);
//            }
//
//            postResponse = "{\"httpCode\": " + response.getStatusLine().getStatusCode() + ",\"output\": " + totalOutput.toString() + "}";
//            return postResponse;
//        } catch (IOException ex) {
//            Logger.getLogger(ApiCallsInAM.class.getName()).log(Level.SEVERE, "Error occured in reading responce from AM APIs:" + ex.getMessage(), ex);
//            String postResponse = "{\"exception\": " + ex.toString() + "\"}";
//            return postResponse;
//        }
//    }
    private String amUserCreationAPI, amLoginAPI, amAppCreationAPI, addSubscriptionAPI, password, appName;
    private List<Cookie> cookies;
    private CookieStore cookieStore;
    private Properties popertiesFromPropertyFile;

    public ApiCallsInAM(String environment) {
        String host;
        PropertyFileHandler propertyFileHandler = new PropertyFileHandler();

        try {
            popertiesFromPropertyFile = propertyFileHandler.popertiesFromPropertyFile();
            if (environment.equalsIgnoreCase("preprod")) {
                host = popertiesFromPropertyFile.getProperty("host_preprod_AM");
            } else {
                host = popertiesFromPropertyFile.getProperty("host_prod_AM");
            }

            amUserCreationAPI = host + "/store/site/blocks/user/sign-up/ajax/user-add.jag";
            amLoginAPI = host + "/store/site/blocks/user/login/ajax/login.jag";
            amAppCreationAPI = host + "/store/site/blocks/application/application-add/ajax/application-add.jag";
            addSubscriptionAPI = host + "/store/site/blocks/subscription/subscription-add/ajax/subscription-add.jag";
            password = popertiesFromPropertyFile.getProperty("default_password");

        } catch (IOException ex) {
            Logger.getLogger(ApiCallsInAM.class.getName()).log(Level.SEVERE, "Error occured in reading Property files" + ex.toString(), ex);
        }
    }

    //Create new User in AM
    public String createNewUserInAm(String appName, String firstName, String lastName, String devEmail) throws IOException {

        String url = amUserCreationAPI + "?allFieldsValues=" + firstName + "%7C" + lastName + "%7C" + devEmail + "&username=" + appName + "&password=" + password + "&action=addUser";
        HttpClient httpClient = getNewHttpClient();
        String response = postToURL(url, httpClient);
        httpClient.getConnectionManager().shutdown();
        return response;
    }

    //Login the new User to API
    public String loginToAm(String appName) throws IOException {
        //chnage this if it start from the midele.User has to insert the app name as well.
        cookieStore = null;

        String url = amLoginAPI + "?username=" + appName + "&password=" + password + "&action=login";
        DefaultHttpClient httpClient = getNewHttpClient();
        String response = postToURL(url, httpClient);
        httpClient.getConnectionManager().shutdown();
        cookieStore = httpClient.getCookieStore();
        return response;
    }

    //Create application in AM
    public String createApplication(String appName, String description, String callback, String tier) throws IOException {

        String url = amAppCreationAPI + "?action=addApplication&application=" + appName + "&tier=" + tier + "&description=" + description + "&callbackUrl=" + callback + "";
        DefaultHttpClient httpClient = getNewHttpClient();
        httpClient.setCookieStore(cookieStore);
        String response = postToURL(url, httpClient);
        httpClient.getConnectionManager().shutdown();
        return response;
    }

    //add subscriptions to the newly created app
    public String addSubscritions(String appName, String apiName, String apiVersion, String apiProvider, String tier) {

        String url = addSubscriptionAPI + "?action=addAPISubscription&name=" + apiName + "&version=" + apiVersion + "&provider=" + apiProvider + "&tier=" + tier + "&applicationName=" + appName + "";
        DefaultHttpClient httpClient = getNewHttpClient();
        httpClient.setCookieStore(cookieStore);
        String response = postToURL(url, httpClient);
        httpClient.getConnectionManager().shutdown();
        return response;
    }

    private static String postToURL(String url, HttpClient httpClient) {
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

            postResponse = "{\"httpCode\": " + response.getStatusLine().getStatusCode() + ",\"output\": " + totalOutput.toString() + "}";
            return postResponse;
        } catch (IOException ex) {
            Logger.getLogger(ApiCallsInAM.class.getName()).log(Level.SEVERE, "Error occured in reading responce from AM APIs:" + ex.getMessage(), ex);
            String postResponse = "{\"exception\": " + ex.toString() + "\"}";
            return postResponse;
        }
    }

    public DefaultHttpClient getNewHttpClient() {
        try {
            KeyStore trustStore = KeyStore.getInstance(KeyStore.getDefaultType());
            trustStore.load(null, null);

            SslSocketFactory sf = new SslSocketFactory(trustStore);
            sf.setHostnameVerifier(SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);

            HttpParams parameters = new BasicHttpParams();
            HttpProtocolParams.setVersion(parameters, HttpVersion.HTTP_1_1);
            HttpProtocolParams.setContentCharset(parameters, HTTP.UTF_8);

            SchemeRegistry registry = new SchemeRegistry();
            registry.register(new Scheme("http", PlainSocketFactory.getSocketFactory(), 80));
            registry.register(new Scheme("https", sf, 443));

            ClientConnectionManager ccm = new ThreadSafeClientConnManager(parameters,
                    registry);

            return new DefaultHttpClient(ccm, parameters);
        } catch (Exception exception) {

            return new DefaultHttpClient();
        }
    }
}
