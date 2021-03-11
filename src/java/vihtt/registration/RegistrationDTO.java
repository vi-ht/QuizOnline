/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vihtt.registration;

import java.io.Serializable;

/**
 *
 * @author Thanh Vi
 */
public class RegistrationDTO implements Serializable{
         private  String email;
         private  String password;
         private  String fullname;
         private  String  roleName;
         private boolean status;

         public RegistrationDTO() {
         }

         public RegistrationDTO(String email, String password, String fullname, String roleName, boolean status) {
                  this.email = email;
                  this.password = password;
                  this.fullname = fullname;
                  this.roleName = roleName;
                  this.status = status;
         }

         public String getEmail() {
                  return email;
         }

         public void setEmail(String email) {
                  this.email = email;
         }

         public String getPassword() {
                  return password;
         }

         public void setPassword(String password) {
                  this.password = password;
         }

         public String getFullname() {
                  return fullname;
         }

         public void setFullname(String fullname) {
                  this.fullname = fullname;
         }

         public String getRoleName() {
                  return roleName;
         }

         public void setRole(String roleName) {
                  this.roleName = roleName;
         }

         public boolean isStatus() {
                  return status;
         }

         public void setStatus(boolean status) {
                  this.status = status;
         }
         
         

         

         
}
