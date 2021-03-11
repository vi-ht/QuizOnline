<%-- 
    Document   : home
    Created on : Feb 13, 2021, 9:11:14 PM
    Author     : Thanh Vi
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
         <head>
                  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
                  <title>Home Page</title>
                  <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.7.0/css/all.css">
                  <link rel="stylesheet" href="CSS.css">
         </head>
         <body>
                  <c:if test="${empty sessionScope.NAME}">
                           <c:redirect url="loginPage"/>
                  </c:if>
                  <c:if test="${not empty sessionScope.NAME}">
                           <c:if test="${sessionScope.ROLE_OF_USER}">
                                    <c:redirect url="pagenotfound"/>
                           </c:if>
                  </c:if>
                  <div id="navigation">   
                           <a id="welcome_icon"><i class="fas fa-smile"></i></a>
                                    <c:if test="${not empty sessionScope.NAME}">
                                             <c:if test="${sessionScope.ROLE_OF_USER}">
                                             <span id="welcome" style="border-bottom: 3px solid black; "><b>Welcome, ${sessionScope.NAME} (Admin)</b></span>
                                    </c:if>
                                    <c:if test="${not sessionScope.ROLE_OF_USER}">
                                             <span id="welcome" style="border-bottom: 3px solid black; "><b>Welcome, ${sessionScope.NAME}</b></span>
                                    </c:if>
                           </c:if>
                           <ul>
                                    <li><a href="subject" title="Home" style="border-bottom: 3px solid black; background: #ffffff;"><i class="far fa-file-alt"></i></a></li>
                                    <li><a href="viewHistory" title="View History"><i class="fas fa-history"></i></a></li>
                                    <li><a href="LogoutServlet" title="Logout"><i class="fas fa-sign-out-alt"></i></a></li>
                           </ul>
                  </div>
                  <div id="shopname">   
                           <h1 id="nameOfShop"><i class="far fa-file-alt" id="cookie"></i> Quiz Online <i class="far fa-file-alt" id="cookie"></i></h1>
                           <br>
                  </div>
                  <div id="categories">   
                           <p style="border-bottom: 3px solid black;color:black;font-size:16px;padding-bottom:7px;"><b>All Subject </b><i class="fas fa-book"></i></p>
                  </div>
                  <div id="listItem">
                           <c:if test="${not empty requestScope.SUBJECT}">
                                    <c:forEach var="subject" items="${requestScope.SUBJECT}">
                                             <form action="TakeAQuiz">
                                                      <div id="item">
                                                               <p><b id="name_of_item">${subject.subjectName}</b></p>
                                                               <p>Time: ${subject.time}</p>
                                                               <p>Number Question: ${subject.quantity}</p>
                                                               <input type="hidden" name="txtSubjectID" value="${subject.subjectID}" />
                                                               <p><input type="submit" value="Take A Quiz" name ="btnAction" id="view_detail"/></p>
                                                      </div> 
                                             </form>
                                    </c:forEach>
                           </c:if>   
                  </div>
                  <div>
                           <c:if test="${empty requestScope.SUBJECT}">
                                    <div id="invalid">
                                             <h1>No Subject to view!!!! <i class="far fa-laugh-squint"></i></h1><br>
                                    </div>
                           </c:if>  

                  </div>
                  <div id="foot">
                           <p><i class="fas fa-copyright" id="p"></i> 2021 <i class="far fa-file-alt" id="pCookie"></i> Quiz Online, by Thanh Vi.<br>
                                    Contact me via <a href="https://www.facebook.com/merry.kute.31/"><i class="fab fa-facebook" id="p" ></i></a> or <a href="#"><i class="fas fa-envelope" id="p"></i></a></p>
                  </div>
         </body>
</html>
