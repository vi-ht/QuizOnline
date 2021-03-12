<%-- 
    Document   : searchUserHistory
    Created on : Jan 22, 2021, 2:02:44 PM
    Author     : Thanh Vi
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
         <head>
                  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
                  <title>Search Question Page</title>
                  <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.7.0/css/all.css">
                  <link rel="stylesheet" href="CSS.css">
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
                  <c:if test="${not empty requestScope.CREATE_SUCCESS}">
                           <script>
                                    window.alert("Create successfully!!!")
                           </script>
                  </c:if>
                  <c:if test="${not empty requestScope.UPDATE_SUCCESS}">
                           <script>
                                    window.alert("Update successfully!!!")
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
                                    <li><a href="searchQuestionPage" title="Search Page" style="border-bottom: 3px solid black; background: #ffffff;"><i class="fas fa-search"></i></a></li>
                                    <li><a href="getInfoToCreate" title="Add New Question"><i class="far fa-plus-square"></i></a></li>
                                    <li><a href="logout" title="Logout"><i class="fas fa-sign-out-alt"></i></a></li>
                           </ul>
                  </div>
                  <div id="shopname">   
                           <br><h1 id="nameOfShop"><em class="far fa-file-alt" id="cookie"></em> Quiz Online <em class="far fa-file-alt" id="cookie"></em></h1><br>
                  </div>
                  <form action="searchQuestion" id="search_history_form">
                           <font id="login"><b>Search Question</b></font><br><br>
                           <b>Question Name:</b> <input type="text" name="txtQuestionName" value="${param.txtQuestionName}" id="search" />
                           <b>Subject Name: </b> <input type="text" name="txtSubjectName" value="${param.txtSubjectName}" id="search" />
                           <b>Status: </b>
                           <c:set var="T" value="t"/>
                           <c:set var="F" value="f"/>
                           <c:set var="All" value="All"/>
                           <c:set var="None" value="None"/>
                           <select name="cbxStatus" id="display_combobox">
                                    <c:if test="${empty requestScope.STATUS}">

                                             <option selected>None</option>
                                             <option >All</option>
                                             <option >Enable</option>
                                             <option>Disable</option>
                                    </c:if>
                                    <c:if test="${not empty requestScope.STATUS}">
                                             <c:if test="${(requestScope.STATUS eq T) || (param.cbxStatus eq T)}">
                                                      <option >All</option>
                                                      <option >None</option>
                                                      <option selected>Enable</option>
                                                      <option >Disable</option>
                                             </c:if>
                                             <c:if test="${(requestScope.STATUS eq F) || (param.cbxStatus eq F)}">
                                                      <option >All</option>
                                                      <option >None</option>
                                                      <option>Enable</option>
                                                      <option selected>Disable</option>
                                             </c:if>
                                             <c:if test="${(requestScope.STATUS eq None) || (param.cbxStatus eq None)}">
                                                      <option >All</option>
                                                      <option selected>None</option>
                                                      <option >Enable</option>
                                                      <option >Disable</option>
                                             </c:if>
                                             <c:if test="${(requestScope.STATUS eq All)|| (param.cbxStatus eq All)}">
                                                      <option selected>All</option>
                                                      <option >None</option>
                                                      <option >Enable</option>
                                                      <option >Disable</option>
                                             </c:if>
                                    </c:if>
                           </select>
                           <input type="submit" name="btnAction" value="Search" id ="Search_button"  /><br>
                           <c:if test="${not empty requestScope.SIZE}">
                                    <p style="float: left">Total row: ${requestScope.SIZE}</p>
                           </c:if>         
                  </form>
                  <div id="viewcart_form">
                           <c:if test="${not empty requestScope.QUESTION}">
                                    <table  id="tb" style="overflow-x:auto;">
                                             <thead>
                                                      <tr>
                                                               <th>No.</th>
                                                               <th>Create Date</th>
                                                               <th>Question Content</th>
                                                               <th>Subject Name</th>
                                                               <th>Answer</th>
                                                               <th>Correct Answer</th>
                                                               <th>Status</th>
                                                               <th>Options</th>
                                                      </tr>
                                             </thead>
                                             <tbody>
                                                      <c:forEach var="question" items="${requestScope.QUESTION}" varStatus="counter">
                                                      <form action="forward"  name="viewcartForm">
                                                               <tr>
                                                                        <td><p>${counter.count}</p>
                                                                        <input type="hidden" name="txtQuestionID" value="${question.questionID}" /></td>
                                                                        <input type="hidden" name="txtQuestionName" value="${param.txtQuestionName}" /></td>
                                                                        <input type="hidden" name="txtSubjectName" value="${param.txtSubjectName}" /></td>
                                                                        <input type="hidden" name="cbxStatus" value="${param.cbxStatus}" /></td>
                                                                        <td><p>${question.createDate}</p></td>
                                                                        <td><p>${question.question_content}</p></td>
                                                                        <td><p>${question.subjectName}</p></td>
                                                                        <td>
                                                                                 <c:forEach var="answer" items="${question.listAnswer}" varStatus="counter">
                                                                                          <p>Answer ${counter.count} - ${answer.answer_content}</p>
                                                                                 </c:forEach>
                                                                        </td>
                                                                        <td>
                                                                                 <c:forEach var="answer" items="${question.listAnswer}" varStatus="counter">
                                                                                          <c:if test="${answer.correctAnswer}">
                                                                                                   <p>${answer.answer_content}</p>
                                                                                          </c:if>
                                                                                 </c:forEach>
                                                                        <td><p><c:if test="${question.status}">
                                                                                                   Enable
                                                                                          </c:if> 
                                                                                          <c:if test="${not question.status}">
                                                                                                   Disable
                                                                                          </c:if>
                                                                                 </p></td>
                                                                        <td><p><input type="submit" name="btnAction" value="Update" id="submit" />
                                                                                          <c:if test="${question.status}">
                                                                                                   <input type="submit" name="btnAction" value="Delete" id="submit"/>
                                                                                          </c:if> 
                                                                                          <c:if test="${not question.status}">
                                                                                                   <input type="submit" name="btnAction" value="Delete" id="submit" disabled="" style="background-color: gray;"/>
                                                                                          </c:if>
                                                                                 </p></td>
                                                               </tr>
                                                      </form>
                                             </c:forEach>    
                                             </tbody>
                                    </table>
                           </c:if>
                  </div>
                  <c:if test="${empty requestScope.UPDATE_SUCCESS && empty requestScope.CREATE_SUCCESS}">
                           <c:if test="${not empty param.txtQuestionName || not empty param.txtSubjectName || not empty param.cbxStatus }">
                                    <c:if test="${empty requestScope.QUESTION}">
                                             <div id="invalid1">
                                                      <h1>No question matches!!!! <i class="far fa-laugh-squint"></i></h1><br>
                                             </div>
                                    </c:if>
                           </c:if>    
                  </c:if>    
                  <div id="paging">
                           <c:set var="pageNumber" value="${requestScope.PAGE_NUMBER}"/>
                           <c:set var="totalPage" value="${requestScope.TOTAL_PAGE - 1}"/>
                           <c:if test="${(pageNumber lt totalPage) && (pageNumber gt 0)}">
                                    <c:url var="last" value="searchQuestion">
                                             <c:param name="txtQuestionName" value="${param.txtQuestionName}"/>  
                                             <c:param name="txtSubjectName" value="${param.txtSubjectName}"/> 
                                             <c:param name="cbxStatus" value="${param.cbxStatus}"/> 
                                             <c:param name="txtPageNumber" value="${pageNumber-1}"/> 
                                    </c:url>
                                    <a href="${last}" ><i class="fas fa-angle-left" id="paging"></i></a><span style="font-size: 13px">${pageNumber+1} / ${totalPage+1}</span>
                                             <c:url var="next" value="searchQuestion">
                                                      <c:param name="txtQuestionName" value="${param.txtQuestionName}"/>  
                                                      <c:param name="txtSubjectName" value="${param.txtSubjectName}"/> 
                                                      <c:param name="cbxStatus" value="${param.cbxStatus}"/> 
                                                      <c:param name="txtPageNumber" value="${pageNumber+1}"/>
                                             </c:url>
                                    <a href="${next}"><i class="fas fa-angle-right" id="paging"></i></a>
                                    </c:if>
                                    <c:if test="${(totalPage eq 0) || (totalPage lt 0)}">
                                    <i class="fas fa-angle-left" id="paging" style="color:gainsboro" ></i>
                                    <i class="fas fa-angle-right" id="paging" style="color:gainsboro"></i>
                           </c:if>
                           <c:if test="${(pageNumber eq totalPage) && (totalPage gt 0)}">
                                    <c:url var="last" value="searchQuestion">
                                             <c:param name="txtQuestionName" value="${param.txtQuestionName}"/>  
                                             <c:param name="txtSubjectName" value="${param.txtSubjectName}"/> 
                                             <c:param name="cbxStatus" value="${param.cbxStatus}"/> 
                                             <c:param name="txtPageNumber" value="${pageNumber-1}"/>
                                    </c:url>
                                    <a href="${last}" ><i class="fas fa-angle-left" id="paging" ></i></a><span style="font-size: 13px">${pageNumber+1} / ${totalPage+1}</span>
                                    <i class="fas fa-angle-right" id="paging" style="color:gainsboro"></i>
                           </c:if>
                           <c:if test="${(pageNumber eq 0) && (totalPage gt 0)}">
                                    <c:url var="next" value="searchQuestion">
                                             <c:param name="txtQuestionName" value="${param.txtQuestionName}"/>  
                                             <c:param name="txtSubjectName" value="${param.txtSubjectName}"/> 
                                             <c:param name="cbxStatus" value="${param.cbxStatus}"/> 
                                             <c:param name="txtPageNumber" value="${pageNumber+1}"/>
                                    </c:url>
                                    <i class="fas fa-angle-left" id="paging" style="color:gainsboro"></i><span style="font-size: 13px">${pageNumber+1} / ${totalPage+1}</span>
                                    <a href="${next}"><i class="fas fa-angle-right" id="paging"></i></a>
                                    </c:if> 
                  </div>
                   <div id="foot">
                           <p><em class="fas fa-copyright" id="p"></em> 2021 <em class="far fa-file-alt" id="pCookie"></em> Quiz Online, by Thanh Vi.<br>
                           Contact me via <a href="https://www.facebook.com/merry.kute.31/"><em class="fab fa-facebook" id="p" ></em></a> or <a href="#"><em class="fas fa-envelope" id="p"></em></a></p>
                  </div>
         </body>
</html>
