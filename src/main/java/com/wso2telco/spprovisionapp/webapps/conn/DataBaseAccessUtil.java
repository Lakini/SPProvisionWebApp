package com.wso2telco.spprovisionapp.webapps.conn;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author lakini
 */
public class DataBaseAccessUtil {
    
    //can create seperate bean for this.
    private static String hostName = "localhost";
    private static String port ="3306";
    private static String userName="root";
    private static String password ="root";
    private static String apimgtDb = "dev2_apimgt";
    private static String axiataDb="dev2_axiatadb";
    private static String connectDb="dev2_connectdb";
    private static String regDb="dev2_regdb ";
    //private String statDb="dev2_statdb";
    private static String statDb="aush_statdb";
    private static String userDb="dev2_userdb";
    
    public static Connection getConnectionToApimgtDb()
        throws ClassNotFoundException, SQLException {
    return getMySQLConnection(apimgtDb);
     }
    
    public static Connection getConnectionToAxiataDb()
        throws ClassNotFoundException, SQLException {
    return getMySQLConnection(axiataDb);
     }
     
    public static Connection getConnectionToConnectDb()
        throws ClassNotFoundException, SQLException {
    return getMySQLConnection(connectDb);
     }
      
    public static Connection getConnectionToRegDb()
        throws ClassNotFoundException, SQLException {
    return getMySQLConnection(regDb);
     }
       
    public static Connection getConnectionToStatDb()
        throws ClassNotFoundException, SQLException {
    return getMySQLConnection(statDb);
     }
    
    public static Connection getConnectionToUserDb()
        throws ClassNotFoundException, SQLException {
    return getMySQLConnection(userDb);
     }
 
public static Connection getMySQLConnection(String dbName
        ) throws SQLException,ClassNotFoundException {
    
    Class.forName("com.mysql.jdbc.Driver");
   
    String connectionURL = "jdbc:mysql://" + hostName + ":"+ port +"/" + dbName;
 
    Connection conn = DriverManager.getConnection(connectionURL, userName,
            password);
    return conn;
}
   
}
