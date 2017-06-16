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
import com.wso2telco.spprovisionapp.webapps.servlets.PropertyFileHandler;
import java.io.IOException;
import org.apache.axis2.AxisFault;
import org.apache.axis2.client.Options;
import org.apache.axis2.client.ServiceClient;
import org.apache.axis2.transport.http.HttpTransportProperties;
import org.wso2.carbon.identity.application.common.model.xsd.ApplicationBasicInfo;
import org.wso2.carbon.identity.application.common.model.xsd.ServiceProvider;
import org.wso2.carbon.identity.application.mgt.stub.IdentityApplicationManagementServiceIdentityApplicationManagementException;
import org.wso2.carbon.identity.application.mgt.stub.IdentityApplicationManagementServiceStub;
import java.rmi.RemoteException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ApplicationManagementClient {

    private IdentityApplicationManagementServiceStub stub = null;
    private ServiceClient client = null;
    private String applicationManagmentHostUrl, userName, password;
    private Properties popertiesFromPropertyFile;

    public ApplicationManagementClient(String environment) {

        String host;
        PropertyFileHandler propertyFileHandler = new PropertyFileHandler();

        try {
            popertiesFromPropertyFile = propertyFileHandler.popertiesFromPropertyFile();
            if (environment.equalsIgnoreCase("preprod")) {
                host = popertiesFromPropertyFile.getProperty("host_preprod_IS");
                userName = popertiesFromPropertyFile.getProperty("preprod_IS_Username");
                password = popertiesFromPropertyFile.getProperty("preprod_IS_password");

            } else {
                host = popertiesFromPropertyFile.getProperty("host_prod_IS");
                userName = popertiesFromPropertyFile.getProperty("prod_IS_Username");
                password = popertiesFromPropertyFile.getProperty("prod_IS_password");
            }

            applicationManagmentHostUrl = host + "/services/IdentityApplicationManagementService";

        } catch (IOException ex) {
            Logger.getLogger(ApiCallsInAM.class.getName()).log(Level.SEVERE, "Error occured in reading Property files" + ex.toString(), ex);
        }

        createAndAuthenticateStub();
    }

    private void createAndAuthenticateStub() {
        try {
            stub = new IdentityApplicationManagementServiceStub(null, applicationManagmentHostUrl);
            client = stub._getServiceClient();
        } catch (AxisFault axisFault) {
            axisFault.printStackTrace();
        }
    }

    public ServiceProvider getSpApplicationData(String applicationName) throws SpProvisionServiceException {

        ServiceProvider serviceProvider = null;
        authenticate(client);
        ApplicationBasicInfo[] applicationBasicInfo;

        applicationBasicInfo = getAllApplicationBasicInfo();
        if (applicationBasicInfo != null) {
            for (ApplicationBasicInfo appInfo : applicationBasicInfo) {
                if (appInfo.getApplicationName().equals(applicationName)) {
                    try {
                        serviceProvider = stub.getApplication(applicationName);
                    } catch (RemoteException e) {
                        Logger.getLogger(ApiCallsInAM.class.getName()).log(Level.SEVERE, "RemoteException occurred when getting Sp Application data for application :"
                                + applicationName + e.toString(), e);
                        throw new SpProvisionServiceException(e.getMessage());
                    } catch (IdentityApplicationManagementServiceIdentityApplicationManagementException e) {
                        Logger.getLogger(ApiCallsInAM.class.getName()).log(Level.SEVERE, "IdentityApplicationManagementServiceIdentityApplicationManagementException "
                                + "occurred when getting Sp Application data for application :"
                                + applicationName + e.toString(), e);
                        throw new SpProvisionServiceException(e.getMessage());
                    }
                    break;
                }
            }
        }
        return serviceProvider;
    }

    private ApplicationBasicInfo[] getAllApplicationBasicInfo() throws SpProvisionServiceException {

        ApplicationBasicInfo[] applicationBasicInfo = null;
        authenticate(client);
        try {
            applicationBasicInfo = stub.getAllApplicationBasicInfo();
        } catch (RemoteException e) {
            Logger.getLogger(ApiCallsInAM.class.getName()).log(Level.SEVERE, "RemoteException occurred when getting all Sp Application data" + e.toString(), e);
            throw new SpProvisionServiceException(e.getMessage());
        } catch (IdentityApplicationManagementServiceIdentityApplicationManagementException e) {
            Logger.getLogger(ApiCallsInAM.class.getName()).log(Level.SEVERE, "RemoteException occurred when getting all Sp Application data" + e.toString(), e);
            throw new SpProvisionServiceException(e.getMessage());
        }
        return applicationBasicInfo;
    }

    public void updateSpApplication(ServiceProvider serviceProvider) throws SpProvisionServiceException {

        authenticate(client);

        if (serviceProvider != null) {
            getSpApplicationData(serviceProvider.getApplicationName());
            try {
                stub.updateApplication(serviceProvider);
            } catch (RemoteException e) {
                Logger.getLogger(ApiCallsInAM.class.getName()).log(Level.SEVERE, "RemoteException occurred when updating Sp Application:" + serviceProvider.getApplicationName() + e.toString(), e);
                throw new SpProvisionServiceException(e.getMessage());
            } catch (IdentityApplicationManagementServiceIdentityApplicationManagementException e) {
                Logger.getLogger(ApiCallsInAM.class.getName()).log(Level.SEVERE, "RemoteException occurred when updating Sp Application:" + serviceProvider.getApplicationName() + e.toString(), e);
                throw new SpProvisionServiceException(e.getMessage());
            } catch (Exception e) {
                Logger.getLogger(ApiCallsInAM.class.getName()).log(Level.SEVERE, "RemoteException occurred when updating Sp Application:" + serviceProvider.getApplicationName() + e.toString(), e);
                throw new SpProvisionServiceException(e.getMessage());
            }
        } else {
            Logger.getLogger(ApiCallsInAM.class.getName()).log(Level.SEVERE, "Service provider details are null");
        }
    }

    public void authenticate(ServiceClient client) {
        Options option = client.getOptions();
        HttpTransportProperties.Authenticator auth = new HttpTransportProperties.Authenticator();
        auth.setUsername(userName);
        auth.setPassword(password);
        auth.setPreemptiveAuthentication(true);
        option.setProperty(org.apache.axis2.transport.http.HTTPConstants.AUTHENTICATE, auth);
        option.setManageSession(true);
    }
}
