/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vihtt.result;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 *
 * @author Thanh Vi
 */
public class ResultDTO implements Serializable{
         private String subjectID;
         private String email;
         private int total_question;
         private int total_correct_question;
         private float mark;
         private boolean status;
         private LocalDateTime date;

         public ResultDTO() {
         }

         public ResultDTO(String subjectID, String email, int total_question, int total_correct_question, float mark, boolean status, LocalDateTime date) {
                  this.subjectID = subjectID;
                  this.email = email;
                  this.total_question = total_question;
                  this.total_correct_question = total_correct_question;
                  this.mark = mark;
                  this.status = status;
                  this.date = date;
         }

         public String getSubjectID() {
                  return subjectID;
         }

         public void setSubjectID(String subjectID) {
                  this.subjectID = subjectID;
         }

         public String getEmail() {
                  return email;
         }

         public void setEmail(String email) {
                  this.email = email;
         }

         public int getTotal_question() {
                  return total_question;
         }

         public void setTotal_question(int total_question) {
                  this.total_question = total_question;
         }

         public int getTotal_correct_question() {
                  return total_correct_question;
         }

         public void setTotal_correct_question(int total_correct_question) {
                  this.total_correct_question = total_correct_question;
         }

         public float getMark() {
                  return mark;
         }

         public void setMark(float mark) {
                  this.mark = mark;
         }

         public boolean isStatus() {
                  return status;
         }

         public void setStatus(boolean status) {
                  this.status = status;
         }

         public LocalDateTime getDate() {
                  return date;
         }

         public void setDate(LocalDateTime date) {
                  this.date = date;
         }
         
}
