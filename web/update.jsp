<%-- 
    Document   : update
    Created on : Jan 15, 2021, 10:45:38 AM
    Author     : Thanh Vi
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
         <head>
                  <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.7.0/css/all.css">
                  <link rel="stylesheet" href="CSS.css">
                  <title>Update Page</title>
         </head>
         <body>
                  <c:if test="${empty sessionScope.NAME}">
                           <c:redirect url="loginPage"/>
                  </c:if>
                  <c:if test="${not empty sessionScope.NAME}">
                           <c:if test="${not sessionScope.ROLE_OF_USER}">
                                    <c:redirect url="pagenotfound"/>
                           </c:if>
                  </c:if>
                  <c:if test="${not empty requestScope.NO_MODIFY_UPDATE}">
                           <script>
                                    window.alert("No modify to update!!!")
                           </script>
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
                                    <li><a href="searchQuestionPage" title="Search Page"><i class="fas fa-search"></i></a></li>
                                    <li><a href="getInfoToCreate" title="Add New Question"><i class="far fa-plus-square"></i></a></li>
                                    <li><a href="logout" title="Logout"><i class="fas fa-sign-out-alt"></i></a></li>
                           </ul>
                  </div>
                  <div id="shopname">   
                           <br><h1 id="nameOfShop"><em class="far fa-file-alt" id="cookie"></em> Quiz Online <em class="far fa-file-alt" id="cookie"></em></h1><br>
                  </div>
                  <c:set var="error" value="${ requestScope.ERRORS}" />
                  <c:if test="${not empty requestScope.QUESTION_DETAILS}">
                           <c:set var="question" value="${requestScope.QUESTION_DETAILS}"/>
                           <form action="update" method="POST"  id="create_form"  name="createForm" >
                                    <font id="create"><b>Update</b></font><br><br>
                                    <div class="display_in_create_form">
                                             <input type="hidden" name="txtSize" value="${requestScope.SIZE}" step="1" id="txtSIZE"/>
                                             <input type="hidden" name="txtQuestionName" value="${param.txtQuestionName}" /></td>
                                             <input type="hidden" name="txtSubjectName" value="${param.txtSubjectName}" /></td>
                                             <input type="hidden" name="cbxStatus" value="${param.cbxStatus}" /></td>
                                             <input type="hidden" name="txtQuestionID" placeholder="ID's max length is 10." value="${question.questionID}" required maxlength="10"/>
                                             <b>Question Content :</b>
                                             <textarea name="txtQuestionContent" rows="1" cols="7" placeholder="Content's max length is 1000." required maxlength="1000">${question.question_content}</textarea>
                                             <b>Subject:</b>
                                             <select name="cbxSubject" id="combobox" required>
                                                      <c:forEach var="subject" items="${requestScope.LIST_SUBJECT}">
                                                               <c:if test="${subject.subjectID eq question.subjectID}">
                                                                        <option selected value="${subject.subjectID}">${subject.subjectName}</option>
                                                               </c:if>
                                                               <c:if test="${subject.subjectID ne question.subjectID}">
                                                                        <option value="${subject.subjectID}">${subject.subjectName}</option>
                                                               </c:if>
                                                      </c:forEach>
                                             </select>

                                             <b>Question Status:</b>
                                             <select name="cbxStatusUpdate" id="combobox" required>
                                                      <c:if test="${question.status}">
                                                               <option selected>Active</option>
                                                               <option>Inactive</option>
                                                      </c:if>
                                                      <c:if test="${not question.status}">
                                                               <option>Active</option>
                                                               <option selected>Inactive</option>
                                                      </c:if>
                                             </select>

                                             <input type="submit" value="Update" name="btnAction" id="create_button"/>
                                    </div>
                                    <div class="display_in_create_form">
                                             <b>Correct Answer:</b>
                                             <c:forEach var="answer" items="${question.listAnswer}" >
                                                      <c:if test="${answer.correctAnswer}">
                                                               <textarea name="txtCorrectAnswer" rows="1" cols="5" placeholder="Correct Answer's max length is 300." maxlength="300" 
                                                                         id="txtCorrectAnswer" class="textarea_Ans" required onchange="check_dup('txtCorrectAnswer')">${answer.answer_content}</textarea>
                                                      </c:if>
                                             </c:forEach>
                                             <c:forEach var="answer" items="${question.listAnswer}" varStatus="counter">  

                                                      <c:if test="${not answer.correctAnswer}">
                                                               
                                                               <c:set var="parameter" value="txtAnswer${answer.answerID}"></c:set>
                                                               <c:set var="1" value="1"></c:set><c:set var="2" value="2"></c:set>
                                                               <c:set var="3" value="3"></c:set><c:set var="4" value="4"></c:set>
                                                               <c:set var="5" value="5"></c:set><c:set var="6" value="6"></c:set>
                                                               <c:if test="${answer.answerID eq 1}">
                                                                        <b>Answer ${answer.answerID}:</b>
                                                                        <textarea name="${parameter}" rows="1" cols="5" placeholder="Answer's max length is 300." id="txtAnswer1"
                                                                                  maxlength="300" class="textarea_Ans" required onchange="check_dup('txtAnswer1')">${answer.answer_content}</textarea>
                                                               </c:if>
                                                               <c:if test="${answer.answerID eq 2}">
                                                                        <b>Answer ${answer.answerID}:</b>
                                                                        <textarea name="${parameter}" rows="1" cols="5" placeholder="Answer's max length is 300." id="txtAnswer2" 
                                                                                  maxlength="300" class="textarea_Ans" required onchange="check_dup('txtAnswer2')">${answer.answer_content}</textarea>
                                                               </c:if>
                                                               <c:if test="${answer.answerID eq 3}">
                                                                        <b>Answer ${answer.answerID}:</b>
                                                                        <textarea name="${parameter}" rows="1" cols="5" placeholder="Answer's max length is 300." id="txtAnswer3" 
                                                                                  maxlength="300" class="textarea_Ans" required onchange="check_dup('txtAnswer3')">${answer.answer_content}</textarea>
                                                               </c:if>
                                                               <c:if test="${answer.answerID eq 4}">
                                                                        <b>Answer ${answer.answerID}:</b>
                                                                        <textarea name="${parameter}" rows="1" cols="5" placeholder="Answer's max length is 300." id="txtAnswer4" 
                                                                                  maxlength="300" class="textarea_Ans" required onchange="check_dup('txtAnswer4')">${answer.answer_content}</textarea>
                                                               </c:if>
                                                               <c:if test="${answer.answerID eq 5}">
                                                                        <b>Answer ${answer.answerID}:</b>
                                                                        <textarea name="${parameter}" rows="1" cols="5" placeholder="Answer's max length is 300." id="txtAnswer5"
                                                                                  maxlength="300" class="textarea_Ans" required onchange="check_dup('txtAnswer5')">${answer.answer_content}</textarea>
                                                               </c:if>
                                                               <c:if test="${answer.answerID eq 6}">
                                                                        <b>Answer ${answer.answerID}:</b>
                                                                        <textarea name="${parameter}" rows="1" cols="5" placeholder="Answer's max length is 300." id="txtAnswer6" 
                                                                                  maxlength="300" class="textarea_Ans" required onchange="check_dup('txtAnswer6')">${answer.answer_content}</textarea>
                                                               </c:if>
                                                      </c:if>

                                             </c:forEach>
                                             <p id="error"></p>
                                    </div> 
                           </form>
                  </c:if>
                   <div id="foot">
                           <p><em class="fas fa-copyright" id="p"></em> 2021 <em class="far fa-file-alt" id="pCookie"></em> Quiz Online, by Thanh Vi.<br>
                           Contact me via <a href="https://www.facebook.com/merry.kute.31/"><em class="fab fa-facebook" id="p" ></em></a> or <a href="#"><em class="fas fa-envelope" id="p"></em></a></p>
                  </div>
                  <script>

                           function check_dup(Ans) {
                                    var test = true;
                                    var num = parseInt(document.getElementById("txtSIZE").value);
                                    if ((document.getElementById(Ans).value === document.getElementById("txtCorrectAnswer").value) && (Ans !== "txtCorrectAnswer")) {
                                             test = false;
                                             document.getElementById("error").innerHTML = 'Questions cannot be duplicated!';
                                    }
                                    for (var i = 1; i <= num; i++) {
                                             var name = "txtAnswer" + i;
                                             if ((document.getElementById(Ans).value === document.getElementById(name).value) && (Ans !== name)) {
                                                      test = false;
                                                      document.getElementById("error").innerHTML = 'Questions cannot be duplicated!';
                                                      break;
                                             }
                                    }
                                    for (var i = 1; i <= num; i++) {
                                             var name = "txtAnswer" + i;
                                             if ((document.getElementById(name).value === document.getElementById("txtCorrectAnswer").value) && (Ans !== "txtCorrectAnswer")) {
                                                      test = false;
                                                      document.getElementById("error").innerHTML = 'Questions cannot be duplicated!';
                                                      break;
                                             }
                                    }
                                    for (var i = 1; i <= num; i++) {
                                             var nameIn = "txtAnswer" + i;
                                             for (var j = 1; j <= num; j++) {
                                                      var nameOut = "txtAnswer" + j;
                                                      if ((document.getElementById(nameOut).value === document.getElementById(nameIn).value) && (nameOut !== nameIn)
                                                              && (document.getElementById(nameOut).value !== "")) {
                                                               test = false;
                                                               document.getElementById("error").innerHTML = 'Questions cannot be duplicated!';
                                                               break;
                                                      }
                                             }
                                    }
                                    if (test) {
                                             document.getElementById("error").innerHTML = '';
                                             document.getElementById("create_button").style.backgroundColor = "#F7819F";
                                             document.getElementById("create_button").disabled = false;
                                    } else {
                                             document.getElementById("create_button").style.backgroundColor = "gray";
                                             document.getElementById("create_button").disabled = true;
                                    }

                           }
                  </script>
</html>
