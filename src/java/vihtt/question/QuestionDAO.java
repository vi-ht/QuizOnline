/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vihtt.question;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import javax.naming.NamingException;
import vihtt.answer.AnswerDAO;
import vihtt.answer.AnswerDTO;
import vihtt.utils.DBHelper;

/**
 *
 * @author Thanh Vi
 */
public class QuestionDAO implements Serializable {

         List<QuestionDTO> questionList;

         public List<QuestionDTO> getQuestionList() {
                  return questionList;
         }
         List<QuestionToShow> questionListToShow;

         public List<QuestionToShow> getQuestionListToShow() {
                  return questionListToShow;
         }
         //---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

         public void getRandomQuestion(boolean Status, int quantity, String subID) throws SQLException, NamingException {
                  Connection con = null;
                  PreparedStatement prm = null;
                  ResultSet rs = null;
                  try {
                           con = DBHelper.makeConnection();
                           if (con != null) {
                                    String sql = "Select top (?) questionID, question_content, createDate, q.subjectID, s.subjectName, status  "
                                            + "From tblQuestion q,  (Select subjectID, subjectName "
                                            + "                                    From tblSubject)s  "
                                            + "Where status = ? AND q.subjectID = s.subjectID AND q.subjectID = ? "
                                            + "AND questionID IN (Select questionID " 
                                            +"                                   From  tblAnswers "
                                            + "                                  Group by questionID "
                                            + "                                  Having COUNT(answerID)  >= 2  ) "
                                            + "order by NEWID() ";
                                    prm = con.prepareStatement(sql);
                                    prm.setInt(1, quantity);
                                    prm.setBoolean(2, Status);
                                    prm.setString(3, subID);
                                    rs = prm.executeQuery();
                                    while (rs.next()) {
                                             String questionID = rs.getString("questionID");
                                             String question_content = rs.getString("question_content");
                                             LocalDate createDate = java.sql.Date.valueOf(rs.getString("createDate")).toLocalDate().plusDays(2);
                                             boolean status = rs.getBoolean("status");
                                             String subjectID = rs.getString("subjectID");
                                             String subjectName = rs.getString("subjectName");
                                             AnswerDAO answerDAO = new AnswerDAO();
                                             answerDAO.getAnswerByQuestionID(questionID);
                                             List<AnswerDTO> listAnswer = answerDAO.getAnswerList();
                                             QuestionToShow dto = new QuestionToShow(listAnswer, questionID, question_content, subjectName, subjectID, status, createDate);
                                             if (questionListToShow == null) {
                                                      questionListToShow = new ArrayList<>();
                                             }
                                             questionListToShow.add(dto);
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

      public QuestionToShow getQuestionByQuestionID(String ID) throws SQLException, NamingException {
                  Connection con = null;
                  PreparedStatement prm = null;
                  ResultSet rs = null;
                  QuestionToShow dto = new QuestionToShow();
                  try {
                           con = DBHelper.makeConnection();
                           if (con != null) {
                                    String sql = "Select questionID, question_content, createDate, q.subjectID, s.subjectName, status  "
                                            + "From tblQuestion q,  (Select subjectID, subjectName "
                                            + "                                    From tblSubject)s "
                                            + "Where questionID = ? AND q.subjectID = s.subjectID";
                                    prm = con.prepareStatement(sql);
                                    prm.setString(1, ID);
                                    rs = prm.executeQuery();
                                    while (rs.next()) {
                                             String questionID = rs.getString("questionID");
                                             String question_content = rs.getString("question_content");
                                             LocalDate createDate = java.sql.Date.valueOf(rs.getString("createDate")).toLocalDate().plusDays(2);
                                             boolean status = rs.getBoolean("status");
                                             String subjectID = rs.getString("subjectID");
                                             String subjectName = rs.getString("subjectName");
                                             AnswerDAO answerDAO = new AnswerDAO();
                                             answerDAO.getAnswerByQuestionID(questionID);
                                             List<AnswerDTO> listAnswer = answerDAO.getAnswerList();
                                             dto = new QuestionToShow(listAnswer, questionID, question_content, subjectName, subjectID, status, createDate);
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

         public void searchQuestion(String subjectName, String questionName, int offset, int fetch) throws SQLException, NamingException {
                  Connection con = null;
                  PreparedStatement prm = null;
                  ResultSet rs = null;
                  try {
                           con = DBHelper.makeConnection();
                           if (con != null) {
                                    String sql = "Select questionID, question_content, createDate, s.subjectName, status, q.subjectID  "
                                            + "From tblQuestion q, (Select subjectID, subjectName "
                                            + "                                    From tblSubject "
                                            + "                                  Where subjectName like ? )s "
                                            + "Where q.question_content like ? AND q.subjectID = s.subjectID "
                                            + "ORDER BY q.question_content DESC "
                                            + "OFFSET ? ROWS FETCH NEXT ? ROWS ONLY ";
                                    prm = con.prepareStatement(sql);
                                    prm.setString(1, "%" + subjectName + "%");
                                    prm.setString(2, "%" + questionName + "%");
                                    prm.setInt(3, offset);
                                    prm.setInt(4, fetch);
                                    rs = prm.executeQuery();
                                    while (rs.next()) {
                                             String questionID = rs.getString("questionID");
                                             String question_content = rs.getString("question_content");
                                             LocalDate createDate = java.sql.Date.valueOf(rs.getString("createDate")).toLocalDate().plusDays(2);
                                             boolean status = rs.getBoolean("status");
                                             subjectName = rs.getString("subjectName");
                                             String subjectID = rs.getString("subjectID");
                                             AnswerDAO answerDAO = new AnswerDAO();
                                             answerDAO.getAnswerByQuestionID(questionID);
                                             List<AnswerDTO> listAnswer = answerDAO.getAnswerList();
                                             QuestionToShow dto = new QuestionToShow(listAnswer, questionID, question_content, subjectName, subjectID, status, createDate);
                                             if (questionListToShow == null) {
                                                      questionListToShow = new ArrayList<>();
                                             }
                                             questionListToShow.add(dto);
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

         public int getSizeOfListQuestion(String subjectName, String questionName) throws NamingException, SQLException {
                  Connection con = null;
                  PreparedStatement prm = null;
                  ResultSet rs = null;
                  int total = 0;
                  try {
                           con = DBHelper.makeConnection();
                           if (con != null) {
                                    String sql = "Select COUNT(*) as Total "
                                            + "From tblQuestion q,(Select subjectID, subjectName "
                                            + "                                    From tblSubject "
                                            + "                                  Where subjectName like ?)s "
                                            + "Where q.question_content like ? AND q.subjectID = s.subjectID ";
                                    prm = con.prepareStatement(sql);
                                    prm.setString(1, "%" + subjectName + "%");
                                    prm.setString(2, "%" + questionName + "%");
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

         public void searchQuestion(String subjectName, String questionName, boolean status, int offset, int fetch) throws SQLException, NamingException {
                  Connection con = null;
                  PreparedStatement prm = null;
                  ResultSet rs = null;
                  try {
                           con = DBHelper.makeConnection();
                           if (con != null) {
                                    String sql = "Select  questionID, question_content, createDate, s.subjectName, status , q.subjectID  "
                                            + "From tblQuestion q, (Select subjectID, subjectName "
                                            + "                                    From tblSubject "
                                            + "                                  Where subjectName like ?)s "
                                            + "Where q.question_content like ? AND q.subjectID = s.subjectID AND status = ? "
                                            + "ORDER BY q.question_content DESC "
                                            + "OFFSET ? ROWS FETCH NEXT ? ROWS ONLY";
                                    prm = con.prepareStatement(sql);
                                    prm.setString(1, "%" + subjectName + "%");
                                    prm.setString(2, "%" + questionName + "%");
                                    prm.setBoolean(3, status);
                                    prm.setInt(4, offset);
                                    prm.setInt(5, fetch);
                                    rs = prm.executeQuery();
                                    while (rs.next()) {
                                             String questionID = rs.getString("questionID");
                                             String question_content = rs.getString("question_content");
                                             LocalDate createDate = java.sql.Date.valueOf(rs.getString("createDate")).toLocalDate().plusDays(2);
                                             subjectName = rs.getString("subjectName");
                                             String subjectID = rs.getString("subjectID");
                                             AnswerDAO answerDAO = new AnswerDAO();
                                             answerDAO.getAnswerByQuestionID(questionID);
                                             List<AnswerDTO> listAnswer = answerDAO.getAnswerList();
                                             QuestionToShow dto = new QuestionToShow(listAnswer, questionID, question_content, subjectName, subjectID, status, createDate);
                                             if (questionListToShow == null) {
                                                      questionListToShow = new ArrayList<>();
                                             }
                                             questionListToShow.add(dto);
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

         public int getSizeOfListQuestion(String subjectName, String questionName, boolean status) throws NamingException, SQLException {
                  Connection con = null;
                  PreparedStatement prm = null;
                  ResultSet rs = null;
                  int total = 0;
                  try {
                           con = DBHelper.makeConnection();
                           if (con != null) {
                                    String sql = "Select COUNT(*) as Total "
                                            + "From tblQuestion q,(Select subjectID, subjectName "
                                            + "                                    From tblSubject "
                                            + "                                  Where subjectName like ?)s "
                                            + "Where q.question_content like ? AND q.subjectID = s.subjectID AND status = ? ";
                                    prm = con.prepareStatement(sql);
                                    prm.setString(1, "%" + subjectName + "%");
                                    prm.setString(2, "%" + questionName + "%");
                                    prm.setBoolean(3, status);
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

         public boolean ceateNewQuestion(QuestionDTO dto)
                 throws SQLException, NamingException {
                  Connection con = null;
                  PreparedStatement prm = null;
                  try {
                           con = DBHelper.makeConnection();
                           if (con != null) {
                                    String sql = "Insert into tblQuestion(questionID, question_content, createDate, subjectID, status) "
                                            + "values(?, ?, ?, ?, ?) ";
                                    prm = con.prepareStatement(sql);
                                    prm.setString(1, dto.getQuestionID());
                                    prm.setString(2, dto.getQuestion_content());
                                    prm.setString(3, Date.valueOf(dto.getCreateDate()).toString());
                                    prm.setString(4, dto.getSubjectID());
                                    prm.setBoolean(5, dto.isStatus());
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

         public boolean deleteQuestion(String ID) throws SQLException, NamingException {
                  Connection con = null;
                  PreparedStatement prm = null;
                  try {
                           con = DBHelper.makeConnection();
                           if (con != null) {
                                    String sql = "Update tblQuestion "
                                            + "set status = ? "
                                            + "Where questionID  = ? ";
                                    prm = con.prepareStatement(sql);
                                    prm.setBoolean(1, false);
                                    prm.setString(2, ID);
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

         public boolean updateQuestion(QuestionDTO dto) throws SQLException, NamingException {
                  Connection con = null;
                  PreparedStatement prm = null;
                  try {
                           con = DBHelper.makeConnection();
                           if (con != null) {
                                    String sql = "Update tblQuestion "
                                            + "set  question_content = ?, createDate = ?, subjectID = ? , status = ? "
                                            + "Where questionID = ? ";
                                    prm = con.prepareStatement(sql);
                                    prm.setString(1, dto.getQuestion_content());
                                    prm.setString(2, Date.valueOf(dto.getCreateDate()).toString());
                                    prm.setString(3, dto.getSubjectID());
                                    prm.setBoolean(4, dto.isStatus());
                                    prm.setString(5, dto.getQuestionID());
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
