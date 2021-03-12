/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vihtt.answer;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.naming.NamingException;
import vihtt.utils.DBHelper;

/**
 *
 * @author Thanh Vi
 */
public class AnswerDAO implements Serializable{
         List<AnswerDTO> answerList;

         public List<AnswerDTO> getAnswerList() {
                  return answerList;
         }
         
         public boolean createNewAnswer(AnswerDTO dto)
                 throws SQLException, NamingException {
                  Connection con = null;
                  PreparedStatement prm = null;
                  try {
                           con = DBHelper.makeConnection();
                           if (con != null) {
                                    String sql = "Insert into tblAnswers(questionID, answerID, answer_content, correctAnswer, status) "
                                            + "values(?, ?, ?, ?, ?) ";
                                    prm = con.prepareStatement(sql);
                                    prm.setString(1, dto.getQuestionID());
                                    prm.setString(2, dto.getAnswerID());
                                    prm.setString(3, dto.getAnswerContent());
                                    prm.setBoolean(4, dto.isCorrectAnswer());
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

        public void getAnswerByQuestionID(String id) throws SQLException, NamingException {
                  Connection con = null;
                  PreparedStatement prm = null;
                  ResultSet rs = null;
                  try {
                           con = DBHelper.makeConnection();
                           if (con != null) {
                                    String sql = "Select questionID, answerID, answer_content, correctAnswer, status  "
                                            + "From tblAnswers "
                                            + "Where questionID = ?";
                                    prm = con.prepareStatement(sql);
                                    prm.setString(1, id);
                                    rs = prm.executeQuery();
                                    while (rs.next()) {
                                             String questionID = rs.getString("questionID").trim();
                                             String answerContent = rs.getString("answer_content").trim();
                                             String answerID = rs.getString("answerID").trim();
                                             boolean correctAnswer = rs.getBoolean("correctAnswer");
                                             boolean status = rs.getBoolean("status");
                                             AnswerDTO dto = new AnswerDTO(questionID, answerContent, correctAnswer, status, answerID);
                                             if(answerList==null){
                                                      answerList = new ArrayList<>();
                                             }
                                             answerList.add(dto);
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
        
        public AnswerDTO getAnswerByQuestionIDAndAnswerID(String quesID, String ansID) throws SQLException, NamingException {
                  Connection con = null;
                  PreparedStatement prm = null;
                  ResultSet rs = null;
                  AnswerDTO dto = new AnswerDTO();
                  try {
                           con = DBHelper.makeConnection();
                           if (con != null) {
                                    String sql = "Select questionID, answerID, answer_content, correctAnswer, status  "
                                            + "From tblAnswers "
                                            + "Where questionID = ? AND answerID = ?";
                                    prm = con.prepareStatement(sql);
                                    prm.setString(1, quesID);
                                    prm.setString(2, ansID);
                                    rs = prm.executeQuery();
                                    while (rs.next()) {
                                             String questionID = rs.getString("questionID");
                                             String answerContent = rs.getString("answer_content");
                                             String answerID = rs.getString("answerID");
                                             boolean correctAnswer = rs.getBoolean("correctAnswer");
                                             boolean status = rs.getBoolean("status");
                                             dto = new AnswerDTO(questionID, answerContent, correctAnswer, status, answerID);
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
         
               
         public boolean updateAnswer(AnswerDTO dto) throws SQLException, NamingException {
                  Connection con = null;
                  PreparedStatement prm = null;
                  try {
                           con = DBHelper.makeConnection();
                           if (con != null) {
                                    String sql = "Update tblAnswers "
                                            + "set  answer_content = ?, correctAnswer = ?, status = ? "
                                            + "Where questionID = ? AND answerID = ?";
                                    prm = con.prepareStatement(sql);
                                    prm.setString(1, dto.getAnswerContent());
                                    prm.setBoolean(2, dto.isCorrectAnswer());
                                    prm.setBoolean(3, dto.isStatus());
                                    prm.setString(4, dto.getQuestionID());
                                    prm.setString(5, dto.getAnswerID());
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
