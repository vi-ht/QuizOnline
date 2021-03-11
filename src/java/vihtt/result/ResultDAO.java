/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vihtt.result;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import javax.naming.NamingException;
import vihtt.utils.DBHelper;

/**
 *
 * @author Thanh Vi
 */
public class ResultDAO implements Serializable {

         List<ResultHistory> listResult;

         public List<ResultHistory> getListResult() {
                  return listResult;
         }

         public boolean insertOrder(ResultDTO dto) throws SQLException, NamingException {
                  Connection con = null;
                  PreparedStatement prm = null;
                  try {
                           con = DBHelper.makeConnection();
                           if (con != null) {
                                    String sql = "Insert tblResult (subjectID, email, total_question, total_correct_question, mark, date, status) "
                                            + "Values(?,?,?,?,?,?,?)";
                                    prm = con.prepareStatement(sql);
                                    prm.setString(1, dto.getSubjectID());
                                    prm.setString(2, dto.getEmail());
                                    prm.setInt(3, dto.getTotal_question());
                                    prm.setInt(4, dto.getTotal_correct_question());
                                    prm.setFloat(5, dto.getMark());
                                    prm.setString(6, Timestamp.valueOf(dto.getDate()).toString());
                                    prm.setBoolean(7, dto.isStatus());
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

         public void getHistoryByUserEmail(String Email, int offset, int fetch) throws SQLException, NamingException {
                  Connection con = null;
                  PreparedStatement prm = null;
                  ResultSet rs = null;
                  try {
                           con = DBHelper.makeConnection();
                           if (con != null) {
                                    String sql = "select s.subjectName, r.email, r.total_question, r.total_correct_question, r.mark, r.date, r.status "
                                            + "FROM tblSubject s, (Select subjectID, email, total_question, total_correct_question, mark, date, status  "
                                            + "                                 From tblResult  "
                                            + "                                 Where email = ? AND status =?)r "
                                            + "where s.subjectID=r.subjectID "
                                            + "ORDER BY r.date DESC "
                                            + "OFFSET ? ROWS FETCH NEXT ? ROWS ONLY";
                                    prm = con.prepareStatement(sql);
                                    prm.setString(1, Email);
                                    prm.setBoolean(2, true);
                                    prm.setInt(3, offset);
                                    prm.setInt(4, fetch);
                                    rs = prm.executeQuery();
                                    while (rs.next()) {
                                             String subjectName = rs.getString("subjectName");
                                             String email = rs.getString("email");
                                             int total_question = rs.getInt("total_question");
                                             int total_correct_question = rs.getInt("total_correct_question");
                                             float mark = rs.getFloat("mark");
                                             boolean status = rs.getBoolean("status");
                                             String date = rs.getString("date");
                                             ResultHistory history = new ResultHistory(subjectName, email, total_question, total_correct_question, mark, status, date);
                                             if (listResult == null) {
                                                      listResult = new ArrayList<>();
                                             }
                                             listResult.add(history);
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

         public int getSizeOfListHistoryByUserEmail(String Email) throws NamingException, SQLException {
                  Connection con = null;
                  PreparedStatement prm = null;
                  ResultSet rs = null;
                  int total = 0;
                  try {
                           con = DBHelper.makeConnection();
                           if (con != null) {
                                    String sql = "Select COUNT(*) as Total "
                                            + "FROM tblSubject s, (Select subjectID, email, total_question, total_correct_question, mark, date, status  "
                                            + "                                 From tblResult  "
                                            + "                                 Where email = ? AND status =?)r "
                                            + "where s.subjectID=r.subjectID ";
                                    prm = con.prepareStatement(sql);
                                    prm.setString(1, Email);
                                    prm.setBoolean(2, true);
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

         public void getHistoryBySubjectName(String Email, String SubjectName, int offset, int fetch) throws SQLException, NamingException {
                  Connection con = null;
                  PreparedStatement prm = null;
                  ResultSet rs = null;
                  try {
                           con = DBHelper.makeConnection();
                           if (con != null) {
                                    String sql = "select s.subjectName, r.email, r.total_question, r.total_correct_question, r.mark, r.date, r.status "
                                            + "FROM tblSubject s, (Select subjectID, email, total_question, total_correct_question, mark, date, status  "
                                            + "                                 From tblResult  "
                                            + "                                 Where email = ? AND status =?)r "
                                            + "where s.subjectID=r.subjectID AND s.subjectName like ? "
                                            + "ORDER BY r.date DESC "
                                            + "OFFSET ? ROWS FETCH NEXT ? ROWS ONLY";
                                    prm = con.prepareStatement(sql);
                                    prm.setString(1, Email);
                                    prm.setBoolean(2, true);
                                    prm.setString(3, "%" + SubjectName + "%");
                                    prm.setInt(4, offset);
                                    prm.setInt(5, fetch);
                                    rs = prm.executeQuery();
                                    while (rs.next()) {
                                             String subjectName = rs.getString("subjectName");
                                             String email = rs.getString("email");
                                             int total_question = rs.getInt("total_question");
                                             int total_correct_question = rs.getInt("total_correct_question");
                                             float mark = rs.getFloat("mark");
                                             boolean status = rs.getBoolean("status");
                                             String date = rs.getString("date");
                                             ResultHistory history = new ResultHistory(subjectName, email, total_question, total_correct_question, mark, status, date);
                                             if (listResult == null) {
                                                      listResult = new ArrayList<>();
                                             }
                                             listResult.add(history);
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

         public int getSizeOfListHistoryBySubjectName(String Email, String SubjectName) throws NamingException, SQLException {
                  Connection con = null;
                  PreparedStatement prm = null;
                  ResultSet rs = null;
                  int total = 0;
                  try {
                           con = DBHelper.makeConnection();
                           if (con != null) {
                                    String sql = "Select COUNT(*) as Total "
                                            + "FROM tblSubject s, (Select subjectID, email, total_question, total_correct_question, mark, date, status  "
                                            + "                                 From tblResult  "
                                            + "                                 Where email = ? AND status =?)r "
                                            + "where s.subjectID=r.subjectID AND s.subjectName like ? ";
                                    prm = con.prepareStatement(sql);
                                    prm.setString(1, Email);
                                    prm.setBoolean(2, true);
                                    prm.setString(3, "%" + SubjectName + "%");
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

         public void searchHistoryByDate(String Email, LocalDateTime minDateTime, LocalDateTime maxDateTime, int offset, int fetch) throws SQLException, NamingException {
                  Connection con = null;
                  PreparedStatement prm = null;
                  ResultSet rs = null;
                  try {
                           con = DBHelper.makeConnection();
                           if (con != null) {
                                    String sql = "select s.subjectName, r.email, r.total_question, r.total_correct_question, r.mark, r.date, r.status "
                                            + "FROM tblSubject s, (Select subjectID, email, total_question, total_correct_question, mark, date, status  "
                                            + "                                 From tblResult  "
                                            + "                                 Where email = ? AND status =? AND date <= ?  AND date >= ?)r "
                                            + "where s.subjectID=r.subjectID "
                                            + "ORDER BY r.date DESC "
                                            + "OFFSET ? ROWS FETCH NEXT ? ROWS ONLY";
                                    prm = con.prepareStatement(sql);
                                    prm.setString(1, Email);
                                    prm.setBoolean(2, true);
                                    prm.setString(3, Timestamp.valueOf(maxDateTime).toString());
                                    prm.setString(4, Timestamp.valueOf(minDateTime).toString());
                                    prm.setInt(5, offset);
                                    prm.setInt(6, fetch);
                                    rs = prm.executeQuery();
                                    while (rs.next()) {
                                             String subjectName = rs.getString("subjectName");
                                             String email = rs.getString("email");
                                             int total_question = rs.getInt("total_question");
                                             int total_correct_question = rs.getInt("total_correct_question");
                                             float mark = rs.getFloat("mark");
                                             boolean status = rs.getBoolean("status");
                                             String date = rs.getString("date");
                                             ResultHistory history = new ResultHistory(subjectName, email, total_question, total_correct_question, mark, status, date);
                                             if (listResult == null) {
                                                      listResult = new ArrayList<>();
                                             }
                                             listResult.add(history);
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

         public int getSizeOfListHistoryByDate(String Email, LocalDateTime minDateTime, LocalDateTime maxDateTime) throws NamingException, SQLException {
                  Connection con = null;
                  PreparedStatement prm = null;
                  ResultSet rs = null;
                  int total = 0;
                  try {
                           con = DBHelper.makeConnection();
                           if (con != null) {
                                    String sql = "Select COUNT(*) as Total "
                                            + "FROM tblSubject s, (Select subjectID,  email, total_question, total_correct_question, mark, date, status   "
                                            + "                                 From tblResult  "
                                            + "                                 Where email = ? AND status =? AND date <= ?  AND date >= ?)r "
                                            + "where s.subjectID=r.subjectID ";
                                    prm = con.prepareStatement(sql);
                                    prm.setString(1, Email);
                                    prm.setBoolean(2, true);
                                    prm.setString(3, Timestamp.valueOf(maxDateTime).toString());
                                    prm.setString(4, Timestamp.valueOf(minDateTime).toString());
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

         public void searchHistoryByDate(String Email, String SubjectName, LocalDateTime minDateTime, LocalDateTime maxDateTime, int offset, int fetch) throws SQLException, NamingException {
                  Connection con = null;
                  PreparedStatement prm = null;
                  ResultSet rs = null;
                  try {
                           con = DBHelper.makeConnection();
                           if (con != null) {
                                    String sql = "select s.subjectName, r.email, r.total_question, r.total_correct_question, r.mark, r.date, r.status "
                                            + "FROM tblSubject s, (Select subjectID, email, total_question, total_correct_question, mark, date, status  "
                                            + "                                 From tblResult  "
                                            + "                                 Where email = ? AND status =? AND date <= ?  AND date >= ?)r "
                                            + "where s.subjectID=r.subjectID AND s.subjectName like ? "
                                            + "ORDER BY r.date DESC "
                                            + "OFFSET ? ROWS FETCH NEXT ? ROWS ONLY";
                                    prm = con.prepareStatement(sql);
                                    prm.setString(1, Email);
                                    prm.setBoolean(2, true);
                                    prm.setString(3, Timestamp.valueOf(maxDateTime).toString());
                                    prm.setString(4, Timestamp.valueOf(minDateTime).toString());
                                    prm.setString(5, "%" + SubjectName + "%");
                                    prm.setInt(6, offset);
                                    prm.setInt(7, fetch);
                                    rs = prm.executeQuery();
                                    while (rs.next()) {
                                             String subjectName = rs.getString("subjectName");
                                             String email = rs.getString("email");
                                             int total_question = rs.getInt("total_question");
                                             int total_correct_question = rs.getInt("total_correct_question");
                                             float mark = rs.getFloat("mark");
                                             boolean status = rs.getBoolean("status");
                                             String date = rs.getString("date");
                                             ResultHistory history = new ResultHistory(subjectName, email, total_question, total_correct_question, mark, status, date);
                                             if (listResult == null) {
                                                      listResult = new ArrayList<>();
                                             }
                                             listResult.add(history);
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

         public int getSizeOfListHistoryByDate(String Email, String SubjectName, LocalDateTime minDateTime, LocalDateTime maxDateTime) throws NamingException, SQLException {
                  Connection con = null;
                  PreparedStatement prm = null;
                  ResultSet rs = null;
                  int total = 0;
                  try {
                           con = DBHelper.makeConnection();
                           if (con != null) {
                                    String sql = "Select COUNT(*) as Total "
                                            + "FROM tblSubject s, (Select subjectID, email, total_question, total_correct_question, mark, date, status   "
                                            + "                                 From tblResult  "
                                            + "                                 Where email = ? AND status =? AND date <= ?  AND date >= ?)r "
                                            + "where s.subjectID=r.subjectID AND s.subjectName like ? ";
                                    prm = con.prepareStatement(sql);
                                    prm.setString(1, Email);
                                    prm.setBoolean(2, true);
                                    prm.setString(3, Timestamp.valueOf(maxDateTime).toString());
                                    prm.setString(4, Timestamp.valueOf(minDateTime).toString());
                                    prm.setString(5, "%" + SubjectName + "%");
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
}
