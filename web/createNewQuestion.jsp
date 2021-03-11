<%-- 
    Document   : createNewProduct
    Created on : Jan 13, 2021, 2:52:12 PM
    Author     : Thanh Vi
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
         <head>
                  <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.7.0/css/all.css">
                  <link rel="stylesheet" href="CSS.css">
                  <title>Create New Question Page</title>
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
                                    <li><a href="searchQuestionPage" title="Search Page" ><i class="fas fa-search"></i></a></li>
                                    <li><a href="getInfoToCreate" title="Add New Question" style="border-bottom: 3px solid black; background: #ffffff;"><i class="far fa-plus-square"></i></a></li>
                                    <li><a href="logout" title="Logout"><i class="fas fa-sign-out-alt"></i></a></li>
                           </ul>
                  </div>
                  <div id="shopname">   
                           <h1 id="nameOfShop"><i class="far fa-file-alt" id="cookie"></i> Quiz Online <i class="far fa-file-alt" id="cookie"></i></h1>
                           <br>
                  </div>
                  <c:if test="${not empty requestScope.LIST_SUBJECT}">
                  <form action="createNewQuestion" method="GET"  id="create_form"  name="createForm">
                           <font id="create"><b>Create New Question</b></font><br><br>
                           <div class="display_in_create_form">
                                    <b>Question ID:</b>
                                    <c:if test="${ not empty requestScope.ERRORS}">
                                             <span id="error_create">${requestScope.ERRORS}</span><br>
                                    </c:if>
                                    <input type="text" name="txtQuestionID" placeholder="ID's max length is 10." value="${param.txtQuestionID}" required maxlength="10" required/>
                                    <b>Question Content :</b>
                                    <textarea name="txtQuestionContent" rows="1" cols="7" placeholder="Content's max length is 1000." required maxlength="1000" required>${param.txtQuestionContent}</textarea>
                                    <b>Subject:</b>
                                    <select name="cbxSubject" id="combobox" required>
                                             <c:if test="${empty requestScope.LIST_SUBJECT}">
                                                      <option selected value="">Choose one</option>
                                             </c:if>
                                             <c:if test="${ not empty requestScope.LIST_SUBJECT}">
                                                      <option value="">Choose one</option>
                                             </c:if>
                                             <c:forEach var="subject" items="${requestScope.LIST_SUBJECT}">
                                                      <c:if test="${subject.subjectID eq param.cbxSubject}">
                                                               <option selected value="${subject.subjectID}">${subject.subjectName}</option>
                                                      </c:if>
                                                      <c:if test="${subject.subjectID ne param.cbxSubject}">
                                                               <option value="${subject.subjectID}">${subject.subjectName}</option>
                                                      </c:if>
                                             </c:forEach>
                                    </select>
                                    <b>Correct Answer:</b>
                                    <textarea name="txtCorrectAnswer" rows="1" cols="5" placeholder="Correct Answer's max length is 300." id="txtCorrectAnswer"
                                              required maxlength="300" class="textarea_Ans" required onchange="check_dup('txtCorrectAnswer')">${param.txtCorrectAnswer}</textarea>
                                    <input type="submit" value="Create New Question" name="btnAction" id="create_button"/>
                           </div>
                           <div class="display_in_create_form">
                                    <input type="hidden" name="txtEND" value="${requestScope.END}" step="1" id="txtEnd"/>
                                    <c:forEach begin="1" end="${requestScope.END}" var="counter">
                                             <c:set var="parameter" value="txtAnswer${counter}"></c:set>
                                             <c:if test="${counter <=2 }">
                                                      <b>Answer ${counter}:</b>
                                                      <c:if test="${counter == 1 }">
                                                               <textarea name="${parameter}" rows="1" cols="5" placeholder="Answer's max length is 300." id="txtAnswer1"
                                                                         maxlength="300" class="textarea_Ans" required onchange="check_dup('txtAnswer1')">${param.txtAnswer1}</textarea>
                                                      </c:if>
                                                      <c:if test="${counter == 2 }">
                                                               <textarea name="${parameter}" rows="1" cols="5" placeholder="Answer's max length is 300."  id="txtAnswer2"
                                                                         maxlength="300" class="textarea_Ans" onchange="check_dup('txtAnswer2')">${param.txtAnswer2}</textarea>
                                                      </c:if>
                                             </c:if> 
                                             <c:if test="${counter >2 }">
                                                      <b>Answer ${counter}:</b>
                                                      <c:if test="${counter == 3 }">
                                                               <textarea name="${parameter}" rows="1" cols="5" placeholder="Answer's max length is 300." id="txtAnswer3"
                                                                         maxlength="300" class="textarea_Ans" onchange="check_dup('txtAnswer3')" >${param.txtAnswer3}</textarea>
                                                      </c:if>
                                                      <c:if test="${counter == 4 }">
                                                               <textarea name="${parameter}" rows="1" cols="5" placeholder="Answer's max length is 300." id="txtAnswer4"
                                                                         maxlength="300" class="textarea_Ans" onchange="check_dup('txtAnswer4')" >${param.txtAnswer4}</textarea>
                                                      </c:if>
                                                      <c:if test="${counter == 5 }">
                                                               <textarea name="${parameter}" rows="1" cols="5" placeholder="Answer's max length is 300." id="txtAnswer5"
                                                                         maxlength="300" class="textarea_Ans" onchange="check_dup('txtAnswer5')" >${param.txtAnswer5}</textarea>
                                                      </c:if>
                                                      <c:if test="${counter == 6 }">
                                                               <textarea name="${parameter}" rows="1" cols="5" placeholder="Answer's max length is 300." id="txtAnswer6"
                                                                         maxlength="300" class="textarea_Ans" onchange="check_dup('txtAnswer6')">${param.txtAnswer6}</textarea>
                                                      </c:if>
                                             </c:if> 
                                                      
                                    </c:forEach>
                                    <c:if test="${requestScope.END<6}">
                                             <input type="submit" value="Create more answers" name="btnAction" id="create_link"/>
                                    </c:if>     
                                             <p id="error"></p>
                                    
                          </div>   
                  </form> 
                  </c:if>
                  <c:if test="${empty requestScope.LIST_SUBJECT}">
                           <div id="invalid1">
                                    <h1>Add subject before add more question!!!! <i class="far fa-laugh-squint"></i></h1><br>
                           </div>
                  </c:if>
                  <div id="foot">
                           <p><i class="fas fa-copyright" id="p"></i> 2021 Hana Sh<i class="fas fa-cookie-bite" id="pCookie"></i>p, by Thanh Vi.<br>
                                    Contact me via <a href="https://www.facebook.com/merry.kute.31/"><i class="fab fa-facebook" id="p" ></i></a> or <a href="#"><i class="fas fa-envelope" id="p"></i></a></p>
                  </div>
                 <script>
                       document.getElementById("create_button").disabled = true;
                          document.getElementById("create_button").style.backgroundColor = "gray";
                           function check_dup(Ans) {
                               var test = true;
                                   var num = parseInt(document.getElementById("txtEnd").value);
                                   if((document.getElementById(Ans).value  === document.getElementById("txtCorrectAnswer").value )&& (Ans !== "txtCorrectAnswer")){
                                            test = false;
                                            document.getElementById("error").innerHTML = 'Questions cannot be duplicated!';
                                    }
                                   for(var i = 1; i<=num; i++){
                                            var name = "txtAnswer"+i;
                                      if((document.getElementById(Ans).value  === document.getElementById(name).value) && (Ans !== name)){
                                           test = false;
                                             document.getElementById("error").innerHTML = 'Questions cannot be duplicated!';
                                             break;
                                         }
                                  }
                                   for(var i = 1; i<=num; i++){
                                            var name = "txtAnswer"+i;
                                      if((document.getElementById(name).value  === document.getElementById("txtCorrectAnswer").value )&& (Ans !== "txtCorrectAnswer")){
                                           test = false;
                                             document.getElementById("error").innerHTML = 'Questions cannot be duplicated!';
                                             break;
                                         }
                                  }
                                  for(var i = 1; i<=num; i++){
                                           var nameIn = "txtAnswer"+i;
                                                    for(var j= 1; j<=num; j++){            
                                                       var nameOut = "txtAnswer"+j;
                                                if((document.getElementById(nameOut).value  === document.getElementById(nameIn).value) && (nameOut !== nameIn) 
                                                        &&(document.getElementById(nameOut).value!=="")){
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
         </body>
</html>
