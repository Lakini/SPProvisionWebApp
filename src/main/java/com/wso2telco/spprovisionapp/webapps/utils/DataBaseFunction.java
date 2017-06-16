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
package com.wso2telco.spprovisionapp.webapps.utils;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import com.wso2telco.spprovisionapp.webapps.conn.ApimgtConnectionUtil;
import com.wso2telco.spprovisionapp.webapps.conn.AxiatadbConnectionUtil;
import com.wso2telco.spprovisionapp.webapps.conn.ConnectdbConnectionUtil;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DataBaseFunction {

    private static int appId;

    public static String activateApplication(Connection conn, String appName) throws SQLException {

        CallableStatement callablestatement = null;
        String message;

        try {
            String SQL = "{call populate_am_database_procedure (?,?)}";
            callablestatement = conn.prepareCall(SQL);
            callablestatement.setString(1, appName);
            callablestatement.registerOutParameter(2, java.sql.Types.INTEGER);
            callablestatement.execute();

            appId = callablestatement.getInt(2);
            System.out.println("create appID:" + appId);
            message = "Success";

        } catch (SQLException e) {
            message = "Failure:" + e.toString();

        } finally {
            conn.close();
        }
        return message;
    }

    public static String updateApplication(Connection conn) throws SQLException {

        CallableStatement callablestatement = null;
        String message;

        try {
            String SQL = "{call populate_axiata_database_procedure (?)}";
            callablestatement = conn.prepareCall(SQL);
            System.out.println("AppId" + appId);
            callablestatement.setInt(1, appId);
            callablestatement.execute();
            message = "Success";

        } catch (SQLException e) {
            message = "Failure:" + e.toString();

        } finally {
            conn.close();
        }
        return message;
    }

    public static String updateSubscriptions(Connection conn) throws SQLException {

        CallableStatement callablestatement = null;
        String message;

        try {
            String SQL = "{call update_subscription_procedure (?)}";
            callablestatement = conn.prepareCall(SQL);
            System.out.println("AppId" + appId);
            callablestatement.setInt(1, appId);
            callablestatement.execute();
            message = "Success";

        } catch (SQLException e) {
            message = "Failure:" + e.toString();

        } finally {
            conn.close();
        }
        return message;
    }

    public static String populateSubscriptionValidator(Connection conn) throws SQLException {

        CallableStatement callablestatement = null;
        String message;

        try {
            String SQL = "{call populate_subscription_validator_procedure (?)}";
            callablestatement = conn.prepareCall(SQL);
            System.out.println("AppId" + appId);
            callablestatement.setInt(1, appId);
            callablestatement.execute();
            message = "Success";

        } catch (SQLException e) {
            message = "Failure:" + e.toString();

        } finally {
            conn.close();
        }
        return message;
    }

    public static String scopeCofiguration(Connection conn, String consumerKey) throws SQLException {

        CallableStatement callablestatement = null;
        String message;

        try {
            String SQL = "{call populate_sp_config_procedure(?)}";
            callablestatement = conn.prepareCall(SQL);
            callablestatement.setString(1, consumerKey);
            callablestatement.execute();
            message = "Success";

        } catch (SQLException e) {
            message = "Failure:" + e.toString();

        } finally {
            conn.close();
        }
        return message;
    }

    public static String updateClientAndSecretKeys(String environment, String consumerKeyOld, String consumerKeyNew, String secretKeyOld, String secretKeyNew) throws SQLException {

        String message;
        String resultsetValue;

        resultsetValue = check_for_consumer_key_availability(environment, consumerKeyOld);

        if (resultsetValue.equals(consumerKeyOld)) {
            if (update_SP_INBOUND_AUTH_table(environment, consumerKeyOld, consumerKeyNew, secretKeyOld, secretKeyNew) > 0) {
                if (update_AM_APPLICATION_KEY_MAPPING_table(environment, consumerKeyOld, consumerKeyNew) > 0) {
                    if (update_AM_APP_KEY_DOMAIN_MAPPING_table(environment, consumerKeyOld) > 0) {
                        if (update_IDN_OAUTH2_ACCESS_TOKEN_table(environment, consumerKeyOld) > 0) {
                            if (update_IDN_OAUTH2_AUTHORIZATION_CODE_table(environment, consumerKeyOld) > 0) {
                                if (update_IDN_OAUTH_CONSUMER_APPS_table(environment, consumerKeyOld, consumerKeyNew, secretKeyNew) > 0) {
                                    if (update_tempkey_in_AM_APP_KEY_DOMAIN_MAPPING_table(environment, consumerKeyNew) > 0) {
                                        if (update_tempkey_in_IDN_OAUTH2_ACCESS_TOKEN_table(environment, consumerKeyNew) > 0) {
                                            if (update_tempkey_in_IDN_OAUTH2_AUTHORIZATION_CODE_table(environment, consumerKeyNew) > 0) {
                                                if (update_sp_token_table(environment, consumerKeyOld, consumerKeyNew) > 0) {
                                                    if (update_sp_configuration_table(environment, consumerKeyOld, consumerKeyNew) > 0) {
                                                        message = "Success";
                                                    } else {
                                                        message = "Failure in updating Sp Configuration Table";
                                                    }
                                                } else {
                                                    message = "Failure in updating Sp Token Table";
                                                }
                                            } else {
                                                message = "Failure in updating IDN_OAUTH2_AUTHORIZATION_CODE_table";
                                            }
                                        } else {
                                            message = "Failure in updating IDN_OAUTH2_ACCESS_TOKEN_table";
                                        }
                                    } else {
                                        message = "Failure in updating AM_APP_KEY_DOMAIN_MAPPING_table";
                                    }
                                } else {
                                    message = "Failure in updating IDN_OAUTH_CONSUMER_APPS_table";
                                }

                            } else {
                                message = "Failure in updating IDN_OAUTH2_AUTHORIZATION_CODE_table";
                            }
                        } else {
                            message = "Failure in updating IDN_OAUTH2_ACCESS_TOKEN_table";
                        }
                    } else {
                        message = "Failure in updating AM_APP_KEY_DOMAIN_MAPPING_table";
                    }
                } else {
                    message = "Failure in updating AM_APPLICATION_KEY_MAPPING_table";
                }
            } else {
                message = "Failure in updating SP_INBOUND_AUTH_table";
            }
        } else {
            message = "Failure :Consumer key is not available";
        }

        return message;
    }

    private static String check_for_consumer_key_availability(String environment, String consumerKey) throws SQLException {

        Connection conn = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        String outputValue = null;
        try {
            conn = ApimgtConnectionUtil.getConnection(environment);
            String sqlQuery = "select * from IDN_OAUTH_CONSUMER_APPS where IDN_OAUTH_CONSUMER_APPS.CONSUMER_KEY=?;";

            statement = conn.prepareStatement(sqlQuery);
            statement.setString(1, consumerKey);
            resultSet = statement.executeQuery();

            if (resultSet.wasNull()) {
                outputValue = null;
            } else {
                if (resultSet.next()) {
                    System.out.println("resultset next");
                    outputValue = resultSet.getString("CONSUMER_KEY");
                }
            }

        } catch (SQLException ex) {
            Logger.getLogger(DataBaseFunction.class.getName()).log(Level.SEVERE, "SQL Exception occured when check for consumer key availability:" + ex.getMessage(), ex);

        } catch (ClassNotFoundException ex) {
            Logger.getLogger(DataBaseFunction.class.getName()).log(Level.SEVERE, "Class Not Found Error occured when check for consumer key availability:" + ex.getMessage(), ex);
        } finally {

            if (conn != null && resultSet != null && statement != null) {
                conn.close();
                resultSet.close();
                statement.close();
            }
        }

        return outputValue;
    }

    private static int update_SP_INBOUND_AUTH_table(String environment, String consumerKeyOld, String consumerKeyNew, String secretKeyOld, String secretKeyNew) throws SQLException {

        Connection conn = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        String sqlQuery, tempValue = null;
        int status = 0;
        try {
            conn = ApimgtConnectionUtil.getConnection(environment);
            conn.setAutoCommit(false);
            sqlQuery = "select * from SP_INBOUND_AUTH where SP_INBOUND_AUTH.INBOUND_AUTH_KEY=? and SP_INBOUND_AUTH.PROP_VALUE=?";
            statement = conn.prepareStatement(sqlQuery);
            statement.setString(1, consumerKeyOld);
            statement.setString(2, secretKeyOld);
            resultSet = statement.executeQuery();

            while (resultSet.next()) {
                tempValue = resultSet.getString("INBOUND_AUTH_KEY");
            }

            if (tempValue.equals(consumerKeyOld)) {

                sqlQuery = "update SP_INBOUND_AUTH set SP_INBOUND_AUTH.INBOUND_AUTH_KEY=?, SP_INBOUND_AUTH.PROP_VALUE=? where SP_INBOUND_AUTH.INBOUND_AUTH_KEY=? and SP_INBOUND_AUTH.PROP_VALUE=?";
                statement = conn.prepareStatement(sqlQuery);
                statement.setString(1, consumerKeyNew);
                statement.setString(2, secretKeyNew);
                statement.setString(3, consumerKeyOld);
                statement.setString(4, secretKeyOld);
                status = statement.executeUpdate();
                conn.commit();

            }

        } catch (SQLException ex) {
            Logger.getLogger(DataBaseFunction.class.getName()).log(Level.SEVERE, null, ex);
            if (conn != null) {
                conn.rollback();
            }

        } catch (ClassNotFoundException ex) {
            Logger.getLogger(DataBaseFunction.class.getName()).log(Level.SEVERE, null, ex);
            if (conn != null) {
                conn.rollback();
            }
        } finally {

            if (conn != null && resultSet != null && statement != null) {
                conn.close();
                resultSet.close();
                statement.close();
            }
        }
        return status;
    }

    private static int update_AM_APPLICATION_KEY_MAPPING_table(String environment, String consumerKeyOld, String consumerKeyNew) throws SQLException {

        Connection conn = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        String sqlQuery;
        int status = 0;
        try {
            conn = ApimgtConnectionUtil.getConnection(environment);
            conn.setAutoCommit(false);
            sqlQuery = "select * from AM_APPLICATION_KEY_MAPPING where AM_APPLICATION_KEY_MAPPING.CONSUMER_KEY=?";
            statement = conn.prepareStatement(sqlQuery);
            statement.setString(1, consumerKeyOld);
            resultSet = statement.executeQuery();

            if (resultSet.next()) {

                sqlQuery = "update AM_APPLICATION_KEY_MAPPING set AM_APPLICATION_KEY_MAPPING.CONSUMER_KEY=? where AM_APPLICATION_KEY_MAPPING.CONSUMER_KEY=?";
                statement = conn.prepareStatement(sqlQuery);
                statement.setString(1, consumerKeyNew);
                statement.setString(2, consumerKeyOld);
                status = statement.executeUpdate();
                conn.commit();
            }

        } catch (SQLException ex) {
            Logger.getLogger(DataBaseFunction.class.getName()).log(Level.SEVERE, null, ex);
            if (conn != null) {
                conn.rollback();
            }

        } catch (ClassNotFoundException ex) {
            Logger.getLogger(DataBaseFunction.class.getName()).log(Level.SEVERE, null, ex);
            if (conn != null) {
                conn.rollback();
            }
        } finally {

            if (conn != null && resultSet != null && statement != null) {
                conn.close();
                resultSet.close();
                statement.close();
            }
        }
        return status;
    }

    private static int update_AM_APP_KEY_DOMAIN_MAPPING_table(String environment, String consumerKeyOld) throws SQLException {

        Connection conn = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        String sqlQuery;
        int status = 0;
        try {
            conn = ApimgtConnectionUtil.getConnection(environment);
            conn.setAutoCommit(false);
            sqlQuery = "select * from AM_APP_KEY_DOMAIN_MAPPING where AM_APP_KEY_DOMAIN_MAPPING.CONSUMER_KEY=?";
            statement = conn.prepareStatement(sqlQuery);
            statement.setString(1, consumerKeyOld);
            resultSet = statement.executeQuery();

            if (resultSet.next()) {

                sqlQuery = "update AM_APP_KEY_DOMAIN_MAPPING set AM_APP_KEY_DOMAIN_MAPPING.CONSUMER_KEY='tempConsumerKey' where AM_APP_KEY_DOMAIN_MAPPING.CONSUMER_KEY=?";
                statement = conn.prepareStatement(sqlQuery);
                statement.setString(1, consumerKeyOld);
                status = statement.executeUpdate();
                conn.commit();
            }

        } catch (SQLException ex) {
            Logger.getLogger(DataBaseFunction.class.getName()).log(Level.SEVERE, null, ex);
            if (conn != null) {
                conn.rollback();
            }

        } catch (ClassNotFoundException ex) {
            Logger.getLogger(DataBaseFunction.class.getName()).log(Level.SEVERE, null, ex);
            if (conn != null) {
                conn.rollback();
            }
        } finally {

            if (conn != null && resultSet != null && statement != null) {
                conn.close();
                resultSet.close();
                statement.close();
            }
        }
        return status;
    }

    private static int update_IDN_OAUTH2_ACCESS_TOKEN_table(String environment, String consumerKeyOld) throws SQLException {

        Connection conn = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        String sqlQuery;
        int status = 0;
        try {
            conn = ApimgtConnectionUtil.getConnection(environment);
            conn.setAutoCommit(false);
            sqlQuery = "select * from IDN_OAUTH2_ACCESS_TOKEN where IDN_OAUTH2_ACCESS_TOKEN.CONSUMER_KEY=?";
            statement = conn.prepareStatement(sqlQuery);
            statement.setString(1, consumerKeyOld);
            resultSet = statement.executeQuery();

            if (resultSet.next()) {

                sqlQuery = "update IDN_OAUTH2_ACCESS_TOKEN set IDN_OAUTH2_ACCESS_TOKEN.CONSUMER_KEY='tempConsumerKey' where IDN_OAUTH2_ACCESS_TOKEN.CONSUMER_KEY=?";
                statement = conn.prepareStatement(sqlQuery);
                statement.setString(1, consumerKeyOld);
                status = statement.executeUpdate();
                conn.commit();
            }

        } catch (SQLException ex) {
            Logger.getLogger(DataBaseFunction.class.getName()).log(Level.SEVERE, null, ex);
            if (conn != null) {
                conn.rollback();
            }

        } catch (ClassNotFoundException ex) {
            Logger.getLogger(DataBaseFunction.class.getName()).log(Level.SEVERE, null, ex);
            if (conn != null) {
                conn.rollback();
            }
        } finally {

            if (conn != null && resultSet != null && statement != null) {
                conn.close();
                resultSet.close();
                statement.close();
            }
        }

        return status;
    }

    private static int update_IDN_OAUTH2_AUTHORIZATION_CODE_table(String environment, String consumerKeyOld) throws SQLException {

        Connection conn = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        String sqlQuery;
        int status = 0;
        try {
            conn = ApimgtConnectionUtil.getConnection(environment);
            conn.setAutoCommit(false);
            sqlQuery = "select * from IDN_OAUTH2_AUTHORIZATION_CODE where IDN_OAUTH2_AUTHORIZATION_CODE.CONSUMER_KEY=?";
            statement = conn.prepareStatement(sqlQuery);
            statement.setString(1, consumerKeyOld);
            resultSet = statement.executeQuery();

            if (resultSet.next()) {

                sqlQuery = "update IDN_OAUTH2_AUTHORIZATION_CODE set IDN_OAUTH2_AUTHORIZATION_CODE.CONSUMER_KEY='tempConsumerKey' where IDN_OAUTH2_AUTHORIZATION_CODE.CONSUMER_KEY=?";
                statement = conn.prepareStatement(sqlQuery);
                statement.setString(1, consumerKeyOld);
                status = statement.executeUpdate();
                conn.commit();
            }

        } catch (SQLException ex) {
            Logger.getLogger(DataBaseFunction.class.getName()).log(Level.SEVERE, null, ex);
            if (conn != null) {
                conn.rollback();
            }

        } catch (ClassNotFoundException ex) {
            Logger.getLogger(DataBaseFunction.class.getName()).log(Level.SEVERE, null, ex);
            if (conn != null) {
                conn.rollback();
            }
        } finally {

            if (conn != null && resultSet != null && statement != null) {
                conn.close();
                resultSet.close();
                statement.close();
            }
        }
        return status;
    }

    private static int update_IDN_OAUTH_CONSUMER_APPS_table(String environment, String consumerKeyOld, String consumerKeyNew, String consumerSecretNew) throws SQLException {

        Connection conn = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        String sqlQuery;
        int status = 0;
        try {
            conn = ApimgtConnectionUtil.getConnection(environment);
            conn.setAutoCommit(false);
            sqlQuery = "select * from IDN_OAUTH_CONSUMER_APPS where IDN_OAUTH_CONSUMER_APPS.CONSUMER_KEY=?";
            statement = conn.prepareStatement(sqlQuery);
            statement.setString(1, consumerKeyOld);
            resultSet = statement.executeQuery();

            if (resultSet.next()) {

                sqlQuery = "update IDN_OAUTH_CONSUMER_APPS set IDN_OAUTH_CONSUMER_APPS.CONSUMER_KEY=? , IDN_OAUTH_CONSUMER_APPS.CONSUMER_SECRET=? where IDN_OAUTH_CONSUMER_APPS.CONSUMER_KEY=?";
                statement = conn.prepareStatement(sqlQuery);
                statement.setString(1, consumerKeyNew);
                statement.setString(2, consumerSecretNew);
                statement.setString(3, consumerKeyOld);
                status = statement.executeUpdate();
                conn.commit();
            }

        } catch (SQLException ex) {
            Logger.getLogger(DataBaseFunction.class.getName()).log(Level.SEVERE, null, ex);
            if (conn != null) {
                conn.rollback();
            }

        } catch (ClassNotFoundException ex) {
            Logger.getLogger(DataBaseFunction.class.getName()).log(Level.SEVERE, null, ex);
            if (conn != null) {
                conn.rollback();
            }
        } finally {

            if (conn != null && resultSet != null && statement != null) {
                conn.close();
                resultSet.close();
                statement.close();
            }
        }
        return status;
    }

    private static int update_tempkey_in_AM_APP_KEY_DOMAIN_MAPPING_table(String environment, String consumerKeyNew) throws SQLException {

        Connection conn = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        String sqlQuery;
        int status = 0;
        try {
            conn = ApimgtConnectionUtil.getConnection(environment);
            conn.setAutoCommit(false);
            sqlQuery = "select * from AM_APP_KEY_DOMAIN_MAPPING where AM_APP_KEY_DOMAIN_MAPPING.CONSUMER_KEY='tempConsumerKey'";
            statement = conn.prepareStatement(sqlQuery);
            resultSet = statement.executeQuery();

            if (resultSet.next()) {

                sqlQuery = "update AM_APP_KEY_DOMAIN_MAPPING set AM_APP_KEY_DOMAIN_MAPPING.CONSUMER_KEY=? where AM_APP_KEY_DOMAIN_MAPPING.CONSUMER_KEY='tempConsumerKey'";
                statement = conn.prepareStatement(sqlQuery);
                statement.setString(1, consumerKeyNew);
                status = statement.executeUpdate();
                conn.commit();
            }

        } catch (SQLException ex) {
            Logger.getLogger(DataBaseFunction.class.getName()).log(Level.SEVERE, null, ex);
            if (conn != null) {
                conn.rollback();
            }

        } catch (ClassNotFoundException ex) {
            Logger.getLogger(DataBaseFunction.class.getName()).log(Level.SEVERE, null, ex);
            if (conn != null) {
                conn.rollback();
            }
        } finally {

            if (conn != null && resultSet != null && statement != null) {
                conn.close();
                resultSet.close();
                statement.close();
            }
        }
        return status;
    }

    private static int update_tempkey_in_IDN_OAUTH2_ACCESS_TOKEN_table(String environment, String consumerKeyNew) throws SQLException {

        Connection conn = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        String sqlQuery;
        int status = 0;
        try {
            conn = ApimgtConnectionUtil.getConnection(environment);
            conn.setAutoCommit(false);
            sqlQuery = "select * from IDN_OAUTH2_ACCESS_TOKEN where IDN_OAUTH2_ACCESS_TOKEN.CONSUMER_KEY='tempConsumerKey'";
            statement = conn.prepareStatement(sqlQuery);
            resultSet = statement.executeQuery();

            if (resultSet.next()) {

                sqlQuery = "update IDN_OAUTH2_ACCESS_TOKEN set IDN_OAUTH2_ACCESS_TOKEN.CONSUMER_KEY=? where IDN_OAUTH2_ACCESS_TOKEN.CONSUMER_KEY='tempConsumerKey'";
                statement = conn.prepareStatement(sqlQuery);
                statement.setString(1, consumerKeyNew);
                status = statement.executeUpdate();
                conn.commit();
            }

        } catch (SQLException ex) {
            Logger.getLogger(DataBaseFunction.class.getName()).log(Level.SEVERE, null, ex);
            if (conn != null) {
                conn.rollback();
            }

        } catch (ClassNotFoundException ex) {
            Logger.getLogger(DataBaseFunction.class.getName()).log(Level.SEVERE, null, ex);
            if (conn != null) {
                conn.rollback();
            }
        } finally {

            if (conn != null && resultSet != null && statement != null) {
                conn.close();
                resultSet.close();
                statement.close();
            }
        }
        return status;
    }

    private static int update_tempkey_in_IDN_OAUTH2_AUTHORIZATION_CODE_table(String environment, String consumerKeyNew) throws SQLException {

        Connection conn = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        String sqlQuery;
        int status = 0;
        try {
            conn = ApimgtConnectionUtil.getConnection(environment);
            conn.setAutoCommit(false);
            sqlQuery = "select * from IDN_OAUTH2_AUTHORIZATION_CODE where IDN_OAUTH2_AUTHORIZATION_CODE.CONSUMER_KEY='tempConsumerKey'";

            statement = conn.prepareStatement(sqlQuery);
            resultSet = statement.executeQuery();

            if (resultSet.next()) {

                sqlQuery = "update IDN_OAUTH2_AUTHORIZATION_CODE set IDN_OAUTH2_AUTHORIZATION_CODE.CONSUMER_KEY=? where IDN_OAUTH2_AUTHORIZATION_CODE.CONSUMER_KEY='tempConsumerKey'";
                statement = conn.prepareStatement(sqlQuery);
                statement.setString(1, consumerKeyNew);
                status = statement.executeUpdate();
                conn.commit();
            }

        } catch (SQLException ex) {
            Logger.getLogger(DataBaseFunction.class.getName()).log(Level.SEVERE, null, ex);
            if (conn != null) {
                conn.rollback();
            }

        } catch (ClassNotFoundException ex) {
            Logger.getLogger(DataBaseFunction.class.getName()).log(Level.SEVERE, null, ex);
            if (conn != null) {
                conn.rollback();
            }
        } finally {

            if (conn != null && resultSet != null && statement != null) {
                conn.close();
                resultSet.close();
                statement.close();
            }
        }
        return status;
    }

    private static int update_sp_token_table(String environment, String consumerKeyOld, String consumerKeyNew) throws SQLException {

        Connection conn = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        String sqlQuery;
        int status = 0;
        try {
            conn = AxiatadbConnectionUtil.getConnection(environment);
            conn.setAutoCommit(false);
            sqlQuery = "select * from sp_token where sp_token.consumer_key=?";
            statement = conn.prepareStatement(sqlQuery);
            statement.setString(1, consumerKeyOld);
            resultSet = statement.executeQuery();

            if (resultSet.next()) {

                sqlQuery = "update sp_token set sp_token.consumer_key=? where sp_token.consumer_key=?";
                statement = conn.prepareStatement(sqlQuery);
                statement.setString(1, consumerKeyNew);
                statement.setString(2, consumerKeyOld);
                status = statement.executeUpdate();
                conn.commit();
            }

        } catch (SQLException ex) {
            Logger.getLogger(DataBaseFunction.class.getName()).log(Level.SEVERE, null, ex);
            if (conn != null) {
                conn.rollback();
            }

        } catch (ClassNotFoundException ex) {
            Logger.getLogger(DataBaseFunction.class.getName()).log(Level.SEVERE, null, ex);
            if (conn != null) {
                conn.rollback();
            }
        } finally {

            if (conn != null && resultSet != null && statement != null) {
                conn.close();
                resultSet.close();
                statement.close();
            }
        }

        return status;
    }

    private static int update_sp_configuration_table(String environment, String consumerKeyOld, String consumerKeyNew) throws SQLException {

        Connection conn = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        String sqlQuery;
        int status = 0;
        try {
            conn = ConnectdbConnectionUtil.getConnection(environment);
            conn.setAutoCommit(false);
            sqlQuery = "SELECT * from sp_configuration where client_id = ?";
            statement = conn.prepareStatement(sqlQuery);
            statement.setString(1, consumerKeyOld);
            resultSet = statement.executeQuery();

            if (resultSet.next()) {

                sqlQuery = "update sp_configuration set sp_configuration.client_id=? where sp_configuration.client_id=?";
                statement = conn.prepareStatement(sqlQuery);
                statement.setString(1, consumerKeyNew);
                statement.setString(2, consumerKeyOld);
                status = statement.executeUpdate();
                conn.commit();
            }

        } catch (SQLException ex) {
            Logger.getLogger(DataBaseFunction.class.getName()).log(Level.SEVERE, null, ex);
            if (conn != null) {
                conn.rollback();
            }

        } catch (ClassNotFoundException ex) {
            Logger.getLogger(DataBaseFunction.class.getName()).log(Level.SEVERE, null, ex);
            if (conn != null) {
                conn.rollback();
            }
        } finally {

            if (conn != null && resultSet != null && statement != null) {
                conn.close();
                resultSet.close();
                statement.close();
            }
        }
        return status;
    }
}
