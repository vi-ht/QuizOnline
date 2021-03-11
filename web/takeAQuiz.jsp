<%-- 
    Document   : takeaquiz
    Created on : Feb 14, 2021, 4:17:09 PM
    Author     : Thanh Vi
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
         <head>
                  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
                  <title>Quiz Page</title>
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
                                    <li><a href="subject" title="Home" ><i class="far fa-file-alt"></i></a></li>
                                    <li><a href="viewHistory" title="View History"><i class="fas fa-history"></i></a></li>
                                    <li><a href="logout" title="Logout"><i class="fas fa-sign-out-alt"></i></a></li>
                           </ul>
                  </div>
                  <div id="shopname">   
                           <h1 id="nameOfShop"><i class="far fa-file-alt" id="cookie"></i> Quiz Online <i class="far fa-file-alt" id="cookie"></i></h1>
                           <br>
                  </div>
                  <div id="listItemDetail">
                           <c:if test="${not empty requestScope.SUBJECT || not empty requestScope.QUESTION}">
                                    <form action="TakeAQuiz" name="my_form" id="myform">
                                             <c:set var="count" value="${requestScope.POS}"></c:set>
                                             <div id="item_image">
                                                      <p><b id="name_of_item">${requestScope.SUBJECT.subjectName}</b></p>
                                                      <p>Time: ${requestScope.SUBJECT.time} min</p>
                                                      <div id="time">
                                                               <span id="h" class="box">Giờ</span> :
                                                               <span id="m" class="box">Phút</span> :
                                                               <span id="s" class="box">Giây</span>
                                                      </div>
                                                      <p>Number Question: ${requestScope.SUBJECT.quantity}</p>
                                                      <c:forEach begin="1" step="1" end="${requestScope.SUBJECT.quantity}" var="counter">
                                                               <c:set var="link" value="#${counter}"></c:set>
                                                               <c:if test="${counter<count}">
                                                                        <a id="counter_pass" href="${link}">${counter}</a>
                                                               </c:if>
                                                               <c:if test="${counter>=count}">
                                                                        <a id="counter" href="${link}">${counter}</a>
                                                               </c:if>
                                                      </c:forEach>
                                                      <p><input type="submit" value="Submit quiz" name ="btnAction" id="view_detail" onclick="stop()"/></p>
                                             </div>       
                                             <c:set var="question" value="${requestScope.QUESTION}" ></c:set>
                                             <div id="item_detail">
                                                      <p class="ques" id="${count}"><b id="name_of_item">Question ${count}:</b></p>
                                                      <p class="ques">${question.question_content}</p>
                                                      <input type="hidden" name="txtQuestionID" value="${question.questionID}" />
                                                      <input type="hidden" name="txtPos" value="${requestScope.POS}" />
                                                      <input type="hidden" name="txtSubjectID" value="${requestScope.SUBJECT.subjectID}" />
                                                      <c:forEach var="answer" items="${question.listAnswer}">
                                                               <input type="radio" name="txtCorrectAns" value="${answer.answerID}" onchange="check_last()" />${answer.answer_content}<br>
                                                      </c:forEach>
                                                      <c:if test="${count!=requestScope.SUBJECT.quantity}">
                                                               <p><input type="submit" value="Next Question" name ="btnAction" id="button_detail"/></p>
                                                      </c:if> 
                                             </div> 
                                             <input type="hidden" id="h_val" name="txtHour" value="${requestScope.HOUR}"/> <br/>
                                             <input type="hidden" id="m_val" name="txtMin" value="${requestScope.MIN}"/> <br/>
                                             <input type="hidden" id="s_val" name="txtSecond" value="${requestScope.SEC}"/>
                                    </form>   
                           </div> 
                  </c:if> 
                  <div>
                           <c:if test="${empty requestScope.SUBJECT || empty requestScope.QUESTION}">
                                    <h2>No question to show!! </h2>
                           </c:if>  
                  </div>
                  <div id="foot">
                           <p><i class="fas fa-copyright" id="p"></i> 2021 <i class="far fa-file-alt" id="pCookie"></i> Quiz Online, by Thanh Vi.<br>
                                    Contact me via <a href="https://www.facebook.com/merry.kute.31/"><i class="fab fa-facebook" id="p" ></i></a> or <a href="#"><i class="fas fa-envelope" id="p"></i></a></p>
                  </div>
                  <script language="javascript">
                            function submitForm(){
                                     document.getElementById('myform').submit();
                           }
                           var h = null;
                           var m = null;
                           var s = null;
                           var timeout = null;
                           start();
                           document.getElementById("button_detail").disabled = true;
                           document.getElementById("button_detail").style.backgroundColor = "gray";
                           function start()
                           {
                                    if (h === null)
                                    {
                                             h = parseInt(document.getElementById('h_val').value);
                                             m = parseInt(document.getElementById('m_val').value);
                                             s = parseInt(document.getElementById('s_val').value);
                                    }
                                    if (s === -1) {
                                             m -= 1;
                                             s = 59;
                                    }
                                    if (m === -1) {
                                             h -= 1;
                                             m = 59;
                                    }
                                    if (h == -1) {
                                             clearTimeout(timeout);
                                             submitForm();
                                             return false;
                                    }
                                    document.getElementById('h').innerText = h.toString();
                                    document.getElementById('m').innerText = m.toString();
                                    document.getElementById('s').innerText = s.toString();
                                    document.forms["my_form"]["txtHour"].value = h.toString();
                                    document.forms["my_form"]["txtMin"].value = m.toString();
                                    document.forms["my_form"]["txtSecond"].value = s.toString();
                                    timeout = setTimeout(function () {
                                             s--;
                                             start();
                                    }, 1000);
                           }
                           function stop() {
                                    clearTimeout(timeout);
                                    submitForm();
                           }
                           function check_last() {
                                    document.getElementById("counter").style.border = "2px solid #F7819F";
                                    document.getElementById("counter").style.backgroundColor = "#F7819F";
                                    document.getElementById("button_detail").disabled = false;
                                    document.getElementById("button_detail").style.backgroundColor = "#F7819F";
                           }
                  </script>
         </body>
</html>
