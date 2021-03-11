/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vihtt.subject;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import javax.naming.NamingException;
import vihtt.utils.DBHelper;

/**
 *
 * @author Thanh Vi
 */
public class SubjectDAO implements Serializable {

         List<SubjectDTO> subjectList;

         public List<SubjectDTO> getSubjectList() {
                  return subjectList;
         }
         //---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

         public void getAllSubject(boolean Status) throws SQLException, NamingException {
                  Connection con = null;
                  PreparedStatement prm = null;
                  ResultSet rs = null;
                  try {
                           con = DBHelper.makeConnection();
                           if (con != null) {
                                    String sql = "Select subjectID, subjectName, time, quantity, status  "
                                            + "From tblSubject "
                                            + "Where status = ? "
                                            + "ORDER BY subjectName ASC ";
                                    prm = con.prepareStatement(sql);
                                    prm.setBoolean(1, Status);
                                    rs = prm.executeQuery();
                                    while (rs.next()) {
                                             String subjectID = rs.getString("subjectID");
                                             String subjectName = rs.getString("subjectName");
                                             int quantity = rs.getInt("quantity");
                                             int time = rs.getInt("time");
                                             boolean status = rs.getBoolean("status");
                                             if (quantity <= checkSizeOfListSubject(subjectID)) {
                                                      SubjectDTO dto = new SubjectDTO(subjectID, subjectName, time, quantity, status);
                                                      if (subjectList == null) {
                                                               subjectList = new ArrayList<>();
                                                      }
                                                      subjectList.add(dto);
                                             }

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
         }

         public void getAllSubject() throws SQLException, NamingException {
                  Connection con = null;
                  Statement prm = null;
                  ResultSet rs = null;
                  try {
                           con = DBHelper.makeConnection();
                           if (con != null) {
                                    String sql = "Select subjectID, subjectName, time, quantity, status  "
                                            + "From tblSubject ";
                                    prm = con.createStatement();
                                    rs = prm.executeQuery(sql);
                                    while (rs.next()) {
                                             String subjectID = rs.getString("subjectID");
                                             String subjectName = rs.getString("subjectName");
                                             int quantity = rs.getInt("quantity");
                                             int time = rs.getInt("time");
                                             boolean status = rs.getBoolean("status");
                                             SubjectDTO dto = new SubjectDTO(subjectID, subjectName, time, quantity, status);
                                             if (subjectList == null) {
                                                      subjectList = new ArrayList<>();
                                             }
                                             subjectList.add(dto);
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
         }

         public int getSizeOfListSubject(boolean status) throws NamingException, SQLException {
                  Connection con = null;
                  PreparedStatement prm = null;
                  ResultSet rs = null;
                  int total = 0;
                  try {
                           con = DBHelper.makeConnection();
                           if (con != null) {
                                    String sql = "Select subjectID, quantity "
                                            + "From tblSubject "
                                            + "Where status = ?";
                                    prm = con.prepareStatement(sql);
                                    prm.setBoolean(1, status);
                                    rs = prm.executeQuery();
                                    if (rs.next()) {
                                             int quantity = rs.getInt("quantity");
                                             String subjectID = rs.getString("subjectID");
                                             if (quantity <= checkSizeOfListSubject(subjectID)) {
                                                      total += 1;
                                             }
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
                  return total;
         }

         public int checkSizeOfListSubject(String id) throws NamingException, SQLException {
                  Connection con = null;
                  PreparedStatement prm = null;
                  ResultSet rs = null;
                  int total = 0;
                  try {
                           con = DBHelper.makeConnection();
                           if (con != null) {
                                    String sql = "Select COUNT(questionID) as Total "
                                            + "From tblQuestion "
                                            + "where subjectID = ? "
                                            + "group by subjectID";
                                    prm = con.prepareStatement(sql);
                                    prm.setString(1, id);
                                    rs = prm.executeQuery();
                                    if (rs.next()) {
                                             total = rs.getInt("Total");
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
                  return total;
         }

         //----------------------------------------------------------------------------------------------------------------------------------
         public SubjectDTO getAllSubjectbySubjectID(String subID) throws SQLException, NamingException {
                  Connection con = null;
                  PreparedStatement prm = null;
                  ResultSet rs = null;
                  SubjectDTO dto = new SubjectDTO();
                  try {
                           con = DBHelper.makeConnection();
                           if (con != null) {
                                    String sql = "Select subjectID, subjectName, time, quantity, status  "
                                            + "From tblSubject "
                                            + "Where subjectID = ? ";
                                    prm = con.prepareStatement(sql);
                                    prm.setString(1, subID);
                                    rs = prm.executeQuery();
                                    while (rs.next()) {
                                             String subjectID = rs.getString("subjectID");
                                             String subjectName = rs.getString("subjectName");
                                             int quantity = rs.getInt("quantity");
                                             int time = rs.getInt("time");
                                             boolean status = rs.getBoolean("status");
                                             dto = new SubjectDTO(subjectID, subjectName, time, quantity, status);
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
                  return dto;
         }
}
