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
package com.wso2telco.spprovisionapp.webapps.conn;

import com.wso2telco.spprovisionapp.webapps.servlets.PropertyFileHandler;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DataBaseAccessUtil {

    private static String hostNamePreProd;
    private static String hostNameProd;
    private static String apimgtDb;
    private static String apimgtDbuserName;
    private static String apimgtDbpassword;
    private static String axiataDb;
    private static String axiataDbuserName;
    private static String axiataDbpassword;
    private static String connectDb;
    private static String connectDbuserName;
    private static String connectDbpassword;

    public DataBaseAccessUtil() throws IOException {
        PropertyFileHandler propertyFIleHandler = new PropertyFileHandler();
        Properties properties = propertyFIleHandler.popertiesFromPropertyFile();
        hostNamePreProd = properties.getProperty("host_db_preprod");
        hostNameProd = properties.getProperty("host_db_prod");
        apimgtDb = properties.getProperty("am_db_name");
        apimgtDbuserName = properties.getProperty("am_db_user");
        apimgtDbpassword = properties.getProperty("am_db_password");
        axiataDb = properties.getProperty("axiata_db_name");
        axiataDbuserName = properties.getProperty("axiata_db_user");
        axiataDbpassword = properties.getProperty("axiata_db_password");
        connectDb = properties.getProperty("connect_db_name");
        connectDbuserName = properties.getProperty("connect_db_user");
        connectDbpassword = properties.getProperty("connect_db_password");
    }

    public static Connection getConnectionToApimgtDb(String environment)
            throws ClassNotFoundException, SQLException {
        return getMySQLConnection(environment, apimgtDb, apimgtDbuserName, apimgtDbpassword);
    }

    public static Connection getConnectionToAxiataDb(String environment)
            throws ClassNotFoundException, SQLException {
        return getMySQLConnection(environment, axiataDb, axiataDbuserName, axiataDbpassword);
    }

    public static Connection getConnectionToConnectDb(String environment)
            throws ClassNotFoundException, SQLException {
        System.out.println("axiataDb" + connectDb);
        System.out.println("axiataDbuserName" + connectDbuserName);
        System.out.println("axiataDbpassword" + connectDbpassword);
        return getMySQLConnection(environment, connectDb, connectDbuserName, connectDbpassword);
    }

    public static Connection getMySQLConnection(String environment, String dbName, String userName, String password
    ) throws SQLException, ClassNotFoundException {

        String hostName;

        if (environment.endsWith("preprod")) {
            hostName = hostNamePreProd;
        } else {
            hostName = hostNameProd;
        }

        Class.forName("com.mysql.jdbc.Driver");

        String connectionURL = "jdbc:mysql://" + hostName + "/" + dbName+"?autoReconnect=true&useSSL=false";

        Connection conn = DriverManager.getConnection(connectionURL, userName,
                password);
        return conn;
    }
}
