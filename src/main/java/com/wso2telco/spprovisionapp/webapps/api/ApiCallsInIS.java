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

import com.wso2telco.spprovisionapp.webapps.exceptions.SpProvisionServiceException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.wso2.carbon.identity.application.common.model.xsd.*;
import org.wso2.carbon.identity.application.common.model.xsd.ServiceProvider;
import org.wso2.carbon.identity.application.common.model.xsd.InboundAuthenticationRequestConfig;

public class ApiCallsInIS {

    private static String environment;
    private final static ApplicationManagementClient applicationManagmenetClient = new ApplicationManagementClient(environment);
    private static ServiceProvider serviceProvider;

    public static String[] getClientSecret(String appName) {

        String[] clientAndSecret = new String[2];
        clientAndSecret = null;
        try {
            serviceProvider = applicationManagmenetClient.getSpApplicationData(appName);

            if (serviceProvider != null) {
                System.out.println("App not null");
                InboundProvisioningConfig inboundProvisioningConfig = serviceProvider.getInboundProvisioningConfig();
                System.out.println("inboundProvisioningConfig:" + inboundProvisioningConfig);
                InboundAuthenticationRequestConfig[] x = serviceProvider.getInboundAuthenticationConfig().getInboundAuthenticationRequestConfigs();
                System.out.println("x:" + x);
                System.out.println("length:" + x.length);
                for (int i = 0; i < x.length; i++) {
                    if (x[i].getInboundAuthType().equals("oauth2")) {
                        clientAndSecret[0] = x[i].getInboundAuthKey();
                        Property[] property = x[i].getProperties();
                        clientAndSecret[1] = property[0].getValue();
                        System.out.println("CK:" + clientAndSecret[0]);
                        System.out.println("CS:" + clientAndSecret[1]);
                        break;
                    }
                }
            }

        } catch (SpProvisionServiceException ex) {
            Logger.getLogger(ApiCallsInIS.class.getName()).log(Level.SEVERE, null, ex);
        }

        return clientAndSecret;
    }

    public static ServiceProvider getServiceProvider(String appName) {
        ServiceProvider serviceProvider = null;
        try {
            serviceProvider = applicationManagmenetClient.getSpApplicationData(appName);
        } catch (SpProvisionServiceException ex) {
            Logger.getLogger(ApiCallsInIS.class.getName()).log(Level.SEVERE, null, ex);
        }
        return serviceProvider;
    }

    public static String updateServiceProvider(String clientKey, String clientSecret, String appName) {
        String status = null;
        ServiceProvider serviceProvider = getServiceProvider(appName);
        InboundAuthenticationConfig[] inboundAuthenticationConfig = new InboundAuthenticationConfig[1];
        InboundAuthenticationRequestConfig[] inboundAuthenticationRequestConfig = new InboundAuthenticationRequestConfig[1];
        Property[] property = new Property[1];
        property[0].setValue(clientSecret);
        inboundAuthenticationRequestConfig[0].setInboundAuthKey(clientKey);
        inboundAuthenticationRequestConfig[0].setProperties(property);
        inboundAuthenticationConfig[0].setInboundAuthenticationRequestConfigs(inboundAuthenticationRequestConfig);

        if (serviceProvider != null) {
            try {
                serviceProvider.setInboundAuthenticationConfig(inboundAuthenticationConfig[0]);
                applicationManagmenetClient.updateSpApplication(serviceProvider);
                status = "Success";
            } catch (SpProvisionServiceException ex) {
                Logger.getLogger(ApiCallsInIS.class.getName()).log(Level.SEVERE, null, ex);
                status = "Failure:" + ex.toString();
            }
        }
        return status;
    }
}
