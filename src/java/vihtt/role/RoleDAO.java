/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vihtt.role;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.naming.NamingException;
import vihtt.utils.DBHelper;

/**
 *
 * @author Thanh Vi
 */
public class RoleDAO implements Serializable {

         public String getRoleName(String roleID)
                 throws SQLException, NamingException {
                  Connection con = null;
                  PreparedStatement prm = null;
                  ResultSet rs = null;
                  try {
                           con = DBHelper.makeConnection();
                           if (con != null) {
                                    String sql = "Select roleName  "
                                            + "from tblRole "
                                            + "Where roleID = ?";
                                    prm = con.prepareStatement(sql);
                                    prm.setString(1, roleID);
                                    rs = prm.executeQuery();
                                    if (rs.next()) {
                                             String roleName = rs.getString("roleName");
                                             return roleName;
                                    }
                           }
                  } finally {
                           if (rs != null) {
                                    rs.close();
                           }
                           if (prm != null) {
                                    prm.close();
                           }
                           if (con != null) {
                                    con.close();
                           }
                  }
                  return null;
         }
}
