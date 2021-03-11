/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vihtt.question;

import java.time.LocalDate;
import java.util.List;
import vihtt.answer.AnswerDTO;

/**
 *
 * @author Thanh Vi
 */
public class QuestionToShow {
         private List<AnswerDTO> listAnswer;
         private String questionID;
         private String question_content;
         private String subjectName;
         private String subjectID;
         private boolean status;
         private LocalDate createDate;

         public QuestionToShow() {
         }

         public QuestionToShow(List<AnswerDTO> listAnswer, String questionID, String question_content, String subjectName, String subjectID, boolean status, LocalDate createDate) {
                  this.listAnswer = listAnswer;
                  this.questionID = questionID;
                  this.question_content = question_content;
                  this.subjectName = subjectName;
                  this.subjectID = subjectID;
                  this.status = status;
                  this.createDate = createDate;
         }

         public String getSubjectID() {
                  return subjectID;
         }

         public void setSubjectID(String subjectID) {
                  this.subjectID = subjectID;
         }

         public List<AnswerDTO> getListAnswer() {
                  return listAnswer;
         }

         public void setListAnswer(List<AnswerDTO> listAnswer) {
                  this.listAnswer = listAnswer;
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


         public String getSubjectName() {
                  return subjectName;
         }

         public void setSubjectName(String subjectName) {
                  this.subjectName = subjectName;
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
