<%-- 
    Document   : login
    Created on : Jan 8, 2021, 5:00:53 PM
    Author     : Thanh Vi
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html>
         <head>
                  <title>Sign Up Page</title>
                  <meta charset="UTF-8">
                  <meta name="viewport" content="width=device-width, initial-scale=1.0">
                  <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.7.0/css/all.css">
                  <link rel="stylesheet" href="CSS.css">
         </head>
         <body>
                  <div id="navigation">   
                           <a id="welcome_icon"><i class="fas fa-smile"></i></a>
                           <ul>
                                    <li><a href="signUpPage" title="Sign Up" style="border-bottom: 3px solid black; background: #ffffff;" ><i class="fas fa-user-plus"></i></a></li>
                                    <li><a href="loginPage" title="Login" ><i class="fas fa-user-circle"></i></a></li>
                           </ul>
                  </div>
                  <div id="shopname">   
                           <h1 id="nameOfShop"><i class="far fa-file-alt" id="cookie"></i> Quiz Online <i class="far fa-file-alt" id="cookie"></i></h1>
                           <br>
                  </div>
                  <form action="create" method="POST" id="loginform" name="myform">
                           <font id="login"><b>Sign Up</b></font><br>
                           <p id="p1">Sign up here: </p>
                           <b>Email:</b> ${requestScope.CREATE_ERR}
                           <input type="email" name="txtEmail" value="" required maxlength="320"/>
                           <b>Password:</b>
                           <input type="password" name="txtPassword" value="" required maxlength="10" id="pass" onchange="CheckPassword()"/>
                           <b>Confirm Password:</b>
                           <input type="password" name="txtConfirmPassword" value="" required maxlength="10" onchange="ConfirmPassword()" id="conf_pass" />
                           <span id="icon_true" style="display:none;"><i class="fas fa-check"></i></span>
                           <span id="icon_false"><i class="fas fa-times"></i></span>
                           <b>Fullname:</b>
                           <input type="text"  value="" name="txtFullname" required maxlength="50" />
                           <b>Your Role:</b>
                           <input type="text"  value="Student" readonly="readonly"/>
                           <input type="submit" name="btnAction" value="Sign Up" id="signup_button" onclick="submitByJavascript()"/><br>
                  </form>   
                  <div id="foot">   
                           <p><i class="fas fa-copyright" id="p"></i> 2021 <i class="far fa-file-alt" id="pCookie"></i> Quiz Online, by Thanh Vi.<br>
                                    Contact me via <a href="https://www.facebook.com/merry.kute.31/"><i class="fab fa-facebook" id="p" ></i></a> or <a href="#"><i class="fas fa-envelope" id="p"></i></a></p>
                  </div>

         </body>
         <script>
                  document.getElementById("error").style.display = "none";
                  document.getElementById("icon_true").style.display = "none";
                  function CheckPassword() {
                           document.forms["myform"]["txtConfirmPassword"].value = "";
                           document.getElementById("icon_true").style.display = "none";
                           document.getElementById("icon_false").style.display = "inline-block";
                           document.getElementById("signup_button").style.backgroundColor = "gray";
                           document.getElementById("signup_button").disabled = true;
                  }
                  function ConfirmPassword() {
                           var pass = document.getElementById("pass").value;
                           var conf_pass = document.getElementById("conf_pass").value;
                           if (pass === conf_pass) {
                                    document.getElementById("icon_true").style.display = "inline-block";
                                    document.getElementById("icon_false").style.display = "none";
                                    document.getElementById("signup_button").style.backgroundColor = "#F7819F";
                                    document.getElementById("signup_button").disabled = false;
                           } else {
                                    document.getElementById("icon_true").style.display = "none";
                                    document.getElementById("icon_false").style.display = "inline-block";
                                    document.getElementById("signup_button").style.backgroundColor = "gray";
                                    document.getElementById("signup_button").disabled = true;
                           }
                  }
         </script>
</html>

