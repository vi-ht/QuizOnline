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
                  <title>Login Page</title>
                  <meta charset="UTF-8">
                  <meta name="viewport" content="width=device-width, initial-scale=1.0">
                  <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.7.0/css/all.css">
                  <link rel="stylesheet" href="CSS.css">
         </head>
         <body>
                  <div id="navigation">   
                           <a id="welcome_icon"><i class="fas fa-smile"></i></a>
                           <ul>
                                    <li><a href="signUpPage" title="Sign Up"><i class="fas fa-user-plus"></i></a></li>
                                    <li><a href="loginPage" title="Login" style="border-bottom: 3px solid black; background: #ffffff;"><i class="fas fa-user-circle"></i></a></li>
                           </ul>
                  </div>
                  <div id="shopname">   
                           <br><h1 id="nameOfShop"><em class="far fa-file-alt" id="cookie"></em> Quiz Online <em class="far fa-file-alt" id="cookie"></em></h1><br>
                  </div>
                  <form action="login" method="POST" id="loginform">
                           <font id="login"><b>Login</b></font><br>
                           <p id="p1">If you have an account, login here: </p>
                           <b>Email:</b>
                           <input type="email" name="txtEmail" value="" required />
                           <b>Password:</b>
                           <input type="password" name="txtPassword" value="" required/>
                           <input type="submit" name="btnAction" value="Login" /><br>
                           <p id="p1">If you don't have an account, please sign up here : <a id="signup_icon" href="signUpPage"><i class="fas fa-user-plus"></i></a></p>
                  </form>   
                   <div id="foot">
                           <p><em class="fas fa-copyright" id="p"></em> 2021 <em class="far fa-file-alt" id="pCookie"></em> Quiz Online, by Thanh Vi.<br>
                           Contact me via <a href="https://www.facebook.com/merry.kute.31/"><em class="fab fa-facebook" id="p" ></em></a> or <a href="#"><em class="fas fa-envelope" id="p"></em></a></p>
                  </div>

         </body>
</html>

