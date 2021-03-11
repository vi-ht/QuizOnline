/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vihtt.question;

import java.io.Serializable;
import java.time.LocalDate;

/**
 *
 * @author Thanh Vi
 */
public class QuestionDTO implements Serializable{
         private String questionID;
         private String question_content;
         private String subjectID;
         private boolean status;
         private LocalDate createDate;

         public QuestionDTO() {
         }

         public QuestionDTO(String questionID, String question_content, String subjectID, boolean status, LocalDate createDate) {
                  this.questionID = questionID;
                  this.question_content = question_content;
                  this.subjectID = subjectID;
                  this.status = status;
                  this.createDate = createDate;
         }
         
         public String getQuestionID() {
                  return questionID;
         }

         public void setQuestionID(String questionID) {
                  this.questionID = questionID;
         }

         public String getQuestion_content() {
                  return question_content;
         }

         public void setQuestion_content(String question_content) {
                  this.question_content = question_content;
         }

         public String getSubjectID() {
                  return subjectID;
         }

         public void setSubjectID(String subjectID) {
                  this.subjectID = subjectID;
         }

         public boolean isStatus() {
                  return status;
         }

         public void setStatus(boolean status) {
                  this.status = status;
         }

         public LocalDate getCreateDate() {
                  return createDate;
         }

         public void setCreateDate(LocalDate createDate) {
                  this.createDate = createDate;
         }

         
         
}
