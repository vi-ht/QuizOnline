<%-- 
    Document   : result
    Created on : Feb 17, 2021, 5:24:41 PM
    Author     : Thanh Vi
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
         <head>
                  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
                  <title>Show Result Page</title>
                  <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.7.0/css/all.css">
                  <link rel="stylesheet" href="CSS.css">

         </head>     
         </body>
                  <c:if test="${empty sessionScope.NAME}">
                           <c:redirect url="loginPage"/>
                  </c:if>
                  <c:if test="${not empty sessionScope.NAME}">
                           <c:if test="${empty requestScope.SUBJECT}">
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
                                    <li><a href="subject" title="Home" ><i class="far fa-file-alt"></i></a></li>
                                    <li><a href="viewHistory" title="View History" ><i class="fas fa-history"></i></a></li>
                                    <li><a href="logout" title="Logout"><i class="fas fa-sign-out-alt"></i></a></li>
                           </ul>
                  </div>
                  <div id="shopname">   
                           <br><h1 id="nameOfShop"><em class="far fa-file-alt" id="cookie"></em> Quiz Online <em class="far fa-file-alt" id="cookie"></em></h1><br>
                  </div>
                  <div id="listItemDetail_re">
                           <c:if test="${not empty requestScope.SUBJECT}">
                                    <div id="item_image_re">
                                             <h1 id="re_name">RESULT</h1>
                                             <p><b id="name_of_item">${requestScope.SUBJECT.subjectName}</b></p>
                                             <p>Time: ${requestScope.SUBJECT.time} min</p>
                                             <p>Number Question: ${requestScope.SUBJECT.quantity}</p>
                                             <c:forEach items="${requestScope.SHOW_RESULT}" var="map">
                                                      <c:if test="${map.value}">
                                                               <a id="counter_green" >${map.key+1}</a>
                                                      </c:if>
                                                      <c:if test="${not map.value}">
                                                               <a id="counter_red" >${map.key+1}</a>
                                                      </c:if>
                                             </c:forEach>
                                             <p>Number Correct Answer: ${requestScope.NUMBER_CORRECT_ANSWER} / ${requestScope.SUBJECT.quantity}</p>
                                             <p>Mark: ${requestScope.MARK} / 10.0</p>       
                                             <a href="subject" id="home" >Take another quiz here.</a>
                                    </div>
                           </div>
                  </c:if> 
                  <div>
                           <c:if test="${empty requestScope.SUBJECT}">
                                    <h2>No result to show!! </h2>
                           </c:if>  
                  </div>
                   <div id="foot">
                           <p><em class="fas fa-copyright" id="p"></em> 2021 <em class="far fa-file-alt" id="pCookie"></em> Quiz Online, by Thanh Vi.<br>
                           Contact me via <a href="https://www.facebook.com/merry.kute.31/"><em class="fab fa-facebook" id="p" ></em></a> or <a href="#"><em class="fas fa-envelope" id="p"></em></a></p>
                  </div>
         </body>
</html>

