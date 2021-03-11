/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vihtt.utils;
/**
 *
 * @author Thanh Vi
 */
import java.io.Serializable;
import java.sql.Connection;
import java.sql.SQLException;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class DBHelper implements Serializable{
    public static Connection makeConnection()
        throws SQLException, NamingException {
        Context context = new InitialContext();
        Context tomcatContex = (Context)context.lookup("java:comp/env");
        DataSource ds = (DataSource)tomcatContex.lookup("ThanhVi");
        Connection con = ds.getConnection();
        return con;
        }
}
