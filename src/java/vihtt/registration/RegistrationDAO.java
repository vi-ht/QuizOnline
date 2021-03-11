/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vihtt.registration;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.naming.NamingException;
import vihtt.role.RoleDAO;
import vihtt.utils.DBHelper;

/**
 *
 * @author Thanh Vi
 */
public class RegistrationDAO implements Serializable {

         public RegistrationDTO checkLogin(String e, String pw)
                 throws SQLException, NamingException {
                  Connection con = null;
                  PreparedStatement prm = null;
                  ResultSet rs = null;
                  try {
                           con = DBHelper.makeConnection();
                           if (con != null) {
                                    String sql = "Select email, password, name, roleID  "
                                            + "from tblRegistration "
                                            + "Where email  = ? And password = ? And status = ?";
                                    prm = con.prepareStatement(sql);
                                    prm.setString(1, e);
                                    prm.setString(2, pw);
                                    prm.setBoolean(3, true);
                                    rs = prm.executeQuery();
                                    if (rs.next()) {
                                             String email = rs.getString("email");
                                             String password = rs.getString("password");
                                             String fullname = rs.getString("name");
                                             String roleID = rs.getString("roleID");
                                             RoleDAO roleDao = new RoleDAO();
                                             String roleName = roleDao.getRoleName(roleID);
                                             if (roleName == null) {
                                                      return null;
                                             }
                                             RegistrationDTO dto = new RegistrationDTO(email, password, fullname, roleName, true);
                                             return dto;
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

         public boolean createAccount(String e, String pw, String n, boolean status, String role) throws SQLException, NamingException {
                  Connection con = null;
                  PreparedStatement prm = null;
                  try {
                           con = DBHelper.makeConnection();
                           if (con != null) {
                                    String sql = "Insert into tblRegistration(email, password, name, roleID, status) "
                                            + "values(?, ?, ?, ?, ?) ";
                                    prm = con.prepareStatement(sql);
                                    prm.setString(1, e);
                                    prm.setString(2, pw);
                                    prm.setString(3, n);
                                    prm.setString(4, role);
                                    prm.setBoolean(5, status);
                                    int row = prm.executeUpdate();
                                    if (row > 0) {
                                             return true;
                                    }
                           }
                  } finally {
                           if (prm != null) {
                                    prm.close();
                           }
                           if (con != null) {
                                    con.close();
                           }
                  }
                  return false;
         }

}
