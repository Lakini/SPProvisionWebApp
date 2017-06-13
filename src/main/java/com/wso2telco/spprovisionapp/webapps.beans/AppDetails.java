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
package com.wso2telco.spprovisionapp.webapps;

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
