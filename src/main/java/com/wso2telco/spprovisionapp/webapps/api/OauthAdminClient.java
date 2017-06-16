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
import org.wso2.carbon.identity.oauth.stub.OAuthAdminServiceException;
import org.wso2.carbon.identity.oauth.stub.OAuthAdminServiceStub;
import org.wso2.carbon.identity.oauth.stub.dto.OAuthConsumerAppDTO;

import java.rmi.RemoteException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

public class OauthAdminClient {

    private String adminServiceHostUrl, userName, password;
    private OAuthAdminServiceStub oAuthAdminServiceStub = null;
    private ServiceClient client = null;
    private Properties popertiesFromPropertyFile;

    public OauthAdminClient(String environment) {
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

            adminServiceHostUrl = host + "/services/OAuthAdminService";

        } catch (IOException ex) {
            Logger.getLogger(ApiCallsInAM.class.getName()).log(Level.SEVERE, "Error occured in reading Property files" + ex.toString(), ex);
        }

        createAndAuthenticateStub();
    }

    public void createAndAuthenticateStub() {
        try {
            oAuthAdminServiceStub = new OAuthAdminServiceStub(null, adminServiceHostUrl);
            oAuthAdminServiceStub._getServiceClient().getOptions().setTimeOutInMilliSeconds(2 * 1000 * 60);
            client = oAuthAdminServiceStub._getServiceClient();

        } catch (AxisFault e) {
            System.out.println(e.getMessage());
        }
    }

    /*
     * Get Service Provider Application Details using consumerKey
     */
    public OAuthConsumerAppDTO getOauthApplicationDataByConsumerKey(String consumerKey)
            throws SpProvisionServiceException {

        OAuthConsumerAppDTO apps = null;
        OAuthConsumerAppDTO[] allAppDetails;

        authenticate(client);

        allAppDetails = getAllOAuthApplicationData();
        if (allAppDetails != null) {
            for (OAuthConsumerAppDTO oAuthConsumerAppDTO : allAppDetails) {
                if (oAuthConsumerAppDTO.getOauthConsumerKey().equals(consumerKey)) {
                    apps = oAuthConsumerAppDTO;
                    break;
                }
            }
        }

        return apps;
    }

//    /*
//     * Register OAuthApplication Data
//     */
//    public void registerOauthApplicationData(AdminServiceDto adminServiceDto) throws SpProvisionServiceException {
//
//        transformAdminServiceDto = new TransformAdminServiceDto();
//        OAuthConsumerAppDTO app = transformAdminServiceDto.transformToOAuthConsumerAppDto(adminServiceDto);
//        authenticate(client);
//        try {
//            oAuthAdminServiceStub.registerOAuthApplicationData(app);
//        } catch (RemoteException e) {
//            throw new SpProvisionServiceException(e.getMessage());
//        } catch (OAuthAdminServiceException e) {
//            throw new SpProvisionServiceException(e.getMessage());
//        }
//    }
//    public void registerOauthApplicationData(OAuthConsumerAppDTO app, AdminServiceDto adminServiceDto)
//            throws SpProvisionServiceException {
//        authenticate(client);
//        try {
//            OAuthConsumerAppDTO appDto = transformAdminServiceDto.transformToOAuthConsumerAppDto(adminServiceDto);
//            oAuthAdminServiceStub.registerOAuthApplicationData(appDto);
//        } catch (RemoteException e) {
//            throw new SpProvisionServiceException(e.getMessage());
//        } catch (OAuthAdminServiceException e) {
//            throw new SpProvisionServiceException(e.getMessage());
//        }
//    }
//    public void registerOauthApplicationData(OAuthConsumerAppDTO app) throws SpProvisionServiceException {
//        authenticate(client);
//        try {
//            oAuthAdminServiceStub.registerOAuthApplicationData(app);
//        } catch (RemoteException e) {
//            throw new SpProvisionServiceException(e.getMessage());
//        } catch (OAuthAdminServiceException e) {
//            throw new SpProvisionServiceException(e.getMessage());
//        }
//    }
//    /*
//     * removeOAuthApplicationData method
//     */
//    public void removeOauthApplicationData(String consumerKey) throws SpProvisionServiceException {
//
//        authenticate(client);
//        try {
//            oAuthAdminServiceStub.removeOAuthApplicationData(consumerKey);
//        } catch (RemoteException e) {
//            throw new SpProvisionServiceException(e.getMessage());
//        } catch (OAuthAdminServiceException e) {
//            throw new SpProvisionServiceException(e.getMessage());
//        }
//    }
//    /*
//     * Update OAuthConfigurations
//     */
//    public void updateOauthApplicationData(AdminServiceDto adminServiceDto) throws SpProvisionServiceException {
//
//        OAuthConsumerAppDTO oAuthConsumerAppDTO = null;
//        TransformAdminServiceDto transformAdminServiceDto = new TransformAdminServiceDto();
//        oAuthConsumerAppDTO = transformAdminServiceDto.transformToOAuthConsumerAppDto(adminServiceDto);
//        authenticate(client);
//
//        try {
//            oAuthAdminServiceStub.updateConsumerApplication(oAuthConsumerAppDTO);
//        } catch (RemoteException e) {
//            throw new SpProvisionServiceException(e.getMessage());
//        } catch (OAuthAdminServiceException e) {
//            throw new SpProvisionServiceException(e.getMessage());
//        }
//    }

    /*
     * Get all Oauth application details
     */
    private OAuthConsumerAppDTO[] getAllOAuthApplicationData() throws SpProvisionServiceException {

        OAuthConsumerAppDTO[] allAppDetails;
        authenticate(client);

        try {
            allAppDetails = oAuthAdminServiceStub.getAllOAuthApplicationData();
        } catch (RemoteException e) {
            throw new SpProvisionServiceException(e.getMessage());
        } catch (OAuthAdminServiceException e) {
            throw new SpProvisionServiceException(e.getMessage());
        }
        return allAppDetails;
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
