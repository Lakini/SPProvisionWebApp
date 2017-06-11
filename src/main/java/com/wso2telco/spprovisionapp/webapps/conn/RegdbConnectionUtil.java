
package com.wso2telco.spprovisionapp.webapps.conn;

import java.sql.Connection;
import java.sql.SQLException;
/**
 *
 * @author lakini
 */
public class RegdbConnectionUtil {
     public static Connection getConnection()
             throws ClassNotFoundException, SQLException {
           
       return DataBaseAccessUtil.getConnectionToRegDb();

   }
    
   public static void closeQuietly(Connection conn) {
       try {
           conn.close();
       } catch (Exception e) {
       }
   }
 
   public static void rollbackQuietly(Connection conn) {
       try {
           conn.rollback();
       } catch (Exception e) {
       }
   }
    
    
}
