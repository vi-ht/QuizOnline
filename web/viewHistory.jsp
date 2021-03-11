<%-- 
    Document   : viewUserHistory
    Created on : Jan 20, 2021, 8:18:32 PM
    Author     : Thanh Vi
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
         <head>
                  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
                  <title>User History Page</title>
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
                                    <li><a href="viewHistory" title="View History" style="border-bottom: 3px solid black; background: #ffffff;"><i class="fas fa-history"></i></a></li>
                                    <li><a href="logout" title="Logout"><i class="fas fa-sign-out-alt"></i></a></li>
                           </ul>
                  </div>
                  <div id="shopname">   
                           <h1 id="nameOfShop"><i class="far fa-file-alt" id="cookie"></i> Quiz Online <i class="far fa-file-alt" id="cookie"></i></h1>
                           <br>
                  </div>
                  <div id="categories_history">   
                           <a href="viewHistory" class="ctgr" style="border-bottom: 3px solid black;background: #F5A9BC;">View History</a>
                           <a href="searchHistoryPage" class="ctgr" >Search History</a>
                  </div>         
                  <div id="viewcart_form">
                           <c:if test="${not empty requestScope.SIZE}">
                                    <p style="float: left">Total row: ${requestScope.SIZE}</p>
                           </c:if>
                           <c:if test="${not empty requestScope.HISTORY}">
                                    <table border="1" id="tb">
                                             <thead>
                                                      <tr>
                                                               <th>No.</th>
                                                               <th>Date</th>
                                                               <th>Subject Name</th>
                                                               <th>Total Correct Question</th>
                                                               <th>Mark</th>
                                                      </tr>
                                             </thead>
                                             <tbody>
                                                      <c:forEach var="history" items="${requestScope.HISTORY}" varStatus="counter">
                                                               <tr>
                                                                        <td><p>${counter.count}</p></td>
                                                                        <td><p>${history.date}</p></td>
                                                                        <td><p>${history.subjectName}</p></td>
                                                                        <td><p>${history.total_correct_question} / ${history.total_question}</p></td>
                                                                        <td><p>${history.mark}</p></td>
                                                               </tr>
                                                      </c:forEach>    
                                             </tbody>
                                    </table>
                           </c:if>
                  </div>
                  <c:if test="${empty requestScope.HISTORY}">
                           <div id="invalid">
                                    <h1>No history to view!!!! <i class="far fa-laugh-squint"></i></h1><br>
                           </div>
                  </c:if>    
                  <div id="paging">
                           <c:set var="pageNumber" value="${requestScope.PAGE_NUMBER}"/>
                           <c:set var="totalPage" value="${requestScope.TOTAL_PAGE - 1}"/>
                           <c:if test="${(pageNumber lt totalPage) && (pageNumber gt 0)}">
                                    <c:url var="last" value="viewHistory">
                                             <c:param name="txtPageNumber" value="${pageNumber-1}"/> 
                                    </c:url>
                                    <a href="${last}" ><i class="fas fa-angle-left" id="paging" ></i></a><span style="font-size: 13px">${pageNumber+1} / ${totalPage+1}</span>
                                             <c:url var="next" value="viewHistory">
                                                      <c:param name="txtPageNumber" value="${pageNumber+1}"/>
                                             </c:url>
                                    <a href="${next}"><i class="fas fa-angle-right" id="paging"></i></a>
                                    </c:if>
                                    <c:if test="${(totalPage eq 0) || (totalPage lt 0)}">
                                    <i class="fas fa-angle-left" id="paging" style="color:gainsboro" ></i>
                                    <i class="fas fa-angle-right" id="paging" style="color:gainsboro"></i>
                           </c:if>
                           <c:if test="${(pageNumber eq totalPage) && (totalPage gt 0)}">
                                    <c:url var="last" value="viewHistory">
                                             <c:param name="txtPageNumber" value="${pageNumber-1}"/>
                                    </c:url>
                                    <a href="${last}" ><i class="fas fa-angle-left" id="paging" ></i></a><span style="font-size: 13px">${pageNumber+1} / ${totalPage+1}</span>
                                    <i class="fas fa-angle-right" id="paging" style="color:gainsboro"></i>
                           </c:if>
                           <c:if test="${(pageNumber eq 0) && (totalPage gt 0)}">
                                    <c:url var="next" value="viewHistory">
                                             <c:param name="txtPageNumber" value="${pageNumber+1}"/>
                                    </c:url>
                                    <i class="fas fa-angle-left" id="paging" style="color:gainsboro" ></i><span style="font-size: 13px">${pageNumber+1} / ${totalPage+1}</span>
                                    <a href="${next}"><i class="fas fa-angle-right" id="paging"></i></a>
                                    </c:if> 
                  </div>
                  <div id="foot">
                           <p><i class="fas fa-copyright" id="p"></i> 2021 <i class="far fa-file-alt" id="pCookie"></i> Quiz Online, by Thanh Vi.<br>
                                    Contact me via <a href="https://www.facebook.com/merry.kute.31/"><i class="fab fa-facebook" id="p" ></i></a> or <a href="#"><i class="fas fa-envelope" id="p"></i></a></p>
                  </div>
         </body>
</html>
