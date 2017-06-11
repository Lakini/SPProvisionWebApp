/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.wso2telco.spprovisionapp.webapps.utils;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import com.ibatis.common.jdbc.ScriptRunner;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.Reader;

/**
 *
 * @author lakini
 */
public class DataBaseFunction {
    
    private static int appId;
    
    public static String activateApplication(Connection conn, String appName) throws SQLException {
        
        CallableStatement callablestatement = null;
        String message;
      
        try {
            String SQL = "{call populate_am_db (?,?)}";
            callablestatement = conn.prepareCall(SQL);
            callablestatement.setString(1, appName);
            callablestatement.registerOutParameter(2, java.sql.Types.INTEGER);
            callablestatement.execute();
            
            appId = callablestatement.getInt(2);
            System.out.println("creat appID:"+appId);
            message="Success";
            

        } catch (SQLException e) {
            message="Failure:"+e.toString();
            
        } finally {
            conn.close();
        }
        return message;

    }
    
    public static String updateApplication(Connection conn) throws SQLException {
        
        CallableStatement callablestatement = null;
        String message;
      
        try {
            String SQL = "{call populate_axiata_db (?)}";
            callablestatement = conn.prepareCall(SQL);
            System.out.println("AppId"+appId);
            callablestatement.setInt(1, appId);
            callablestatement.execute();
            message="Success";
            

        } catch (SQLException e) {
            message="Failure:"+e.toString();
            
        } finally {
            conn.close();
        }
        return message;

    }
    
    public static String updateSubscriptions(Connection conn) throws SQLException {
        
        CallableStatement callablestatement = null;
        String message;
      
        try {
            String SQL = "{call update_subscription (?)}";
            callablestatement = conn.prepareCall(SQL);
            System.out.println("AppId"+appId);
            callablestatement.setInt(1, appId);
            callablestatement.execute();
            message="Success";
            

        } catch (SQLException e) {
            message="Failure:"+e.toString();
            
        } finally {
            conn.close();
        }
        return message;

    }
    
    public static String populateSubscriptionValidator(Connection conn) throws SQLException {
        
        CallableStatement callablestatement = null;
        String message;
      
        try {
            String SQL = "{call populate_subscription_validator (?)}";
            callablestatement = conn.prepareCall(SQL);
            System.out.println("AppId"+appId);
            callablestatement.setInt(1, appId);
            callablestatement.execute();
            message="Success";
            

        } catch (SQLException e) {
            message="Failure:"+e.toString();
            
        } finally {
            conn.close();
        }
        return message;

    }
    
    
}
