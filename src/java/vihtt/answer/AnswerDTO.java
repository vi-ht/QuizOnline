/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vihtt.answer;

import java.io.Serializable;

/**
 *
 * @author Thanh Vi
 */
public class AnswerDTO implements Serializable{
         private String questionID;
         private String answerContent;
         private boolean correctAnswer;
         private boolean status;
         private String answerID;

         public AnswerDTO() {
         }

         public AnswerDTO(String questionID, String answerContent, boolean correctAnswer, boolean status, String answerID) {
                  this.questionID = questionID;
                  this.answerContent = answerCXontent;
                  this.correctAnswer = correctAnswer;
                  this.status = status;
                  this.answerID = answerID;
         }

         public String getQuestionID() {
                  return questionID;
         }

         public void setQuestionID(String questionID) {
                  this.questionID = questionID;
         }

         public String getAnswerContent() {
                  return answerContent;
         }

         public void setAnswer_content(String answer_content) {
                  this.answerContent = answerContent;
         }

         public boolean isCorrectAnswer() {
                  return correctAnswer;
         }

         public void setCorrectAnswer(boolean correctAnswer) {
                  this.correctAnswer = correctAnswer;
         }

         public boolean isStatus() {
                  return status;
         }

         public void setStatus(boolean status) {
                  this.status = status;
         }

         public String getAnswerID() {
                  return answerID;
         }

         public void setAnswerID(String answerID) {
                  this.answerID = answerID;
         }

        
         
}
