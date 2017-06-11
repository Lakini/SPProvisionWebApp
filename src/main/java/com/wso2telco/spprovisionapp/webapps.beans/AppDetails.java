package com.wso2telco.spprovisionapp.webapps;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author lakini
 */
public class AppDetails {
    
    private String appName;
    private String appShortName;
    private String appShortDescription;
    private String requestedClientKey;
    private String requestedClientSecret;
    private String redirectUrl;
    private String developerName;
    private String developerEmail;

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public String getAppShortName() {
        return appShortName;
    }

    public void setAppShortName(String appShortName) {
        this.appShortName = appShortName;
    }

    public String getAppShortDescription() {
        return appShortDescription;
    }

    public void setAppShortDescription(String appShortDescription) {
        this.appShortDescription = appShortDescription;
    }

    public String getRequestedClientKey() {
        return requestedClientKey;
    }

    public void setRequestedClientKey(String requestedClientKey) {
        this.requestedClientKey = requestedClientKey;
    }

    public String getRequestedClientSecret() {
        return requestedClientSecret;
    }

    public void setRequestedClientSecret(String requestedClientSecret) {
        this.requestedClientSecret = requestedClientSecret;
    }

    public String getRedirectUrl() {
        return redirectUrl;
    }

    public void setRedirectUrl(String redirectUrl) {
        this.redirectUrl = redirectUrl;
    }

    public String getDeveloperName() {
        return developerName;
    }

    public void setDeveloperName(String developerName) {
        this.developerName = developerName;
    }

    public String getDeveloperEmail() {
        return developerEmail;
    }

    public void setDeveloperEmail(String developerEmail) {
        this.developerEmail = developerEmail;
    }
    
}
