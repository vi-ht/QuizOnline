/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vihtt.subject;

import java.io.Serializable;

/**
 *
 * @author Thanh Vi
 */
public class SubjectDTO implements Serializable{
         private String subjectID;
         private String subjectName;
         private int time;
         private int quantity;
         private boolean status;

         public SubjectDTO() {
         }

         public SubjectDTO(String subjectID, String subjectName, int time, int quantity, boolean status) {
                  this.subjectID = subjectID;
                  this.subjectName = subjectName;
                  this.time = time;
                  this.quantity = quantity;
                  this.status = status;
         }

         public String getSubjectID() {
                  return subjectID;
         }

         public void setSubjectID(String subjectID) {
                  this.subjectID = subjectID;
         }

         public String getSubjectName() {
                  return subjectName;
         }

         public void setSubjectName(String subjectName) {
                  this.subjectName = subjectName;
         }

         public int getTime() {
                  return time;
         }

         public void setTime(int time) {
                  this.time = time;
         }

         public int getQuantity() {
                  return quantity;
         }

         public void setQuantity(int quantity) {
                  this.quantity = quantity;
         }

         public boolean isStatus() {
                  return status;
         }

         public void setStatus(boolean status) {
                  this.status = status;
         }

         @Override
         public String toString() {
                  return  ""+time;
         }
         
}
