/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vihtt.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import javax.naming.NamingException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import vihtt.answer.AnswerDAO;
import vihtt.answer.AnswerDTO;
import vihtt.question.QuestionDAO;
import vihtt.question.QuestionDTO;
import vihtt.subject.SubjectDAO;
import vihtt.subject.SubjectDTO;


/**
 *
 * @author Thanh Vi
 */
@WebServlet(name = "CreateNewQuestionServlet", urlPatterns = {"/CreateNewQuestionServlet"})
public class CreateNewQuestionServlet extends HttpServlet {
         private final String VIEW_QUESTION_PAGE = "searchQuestionPage";
         private final String CREATE_NEW_QUESTION_PAGE="createNewQuestionPage";
         private final String ERROR_PAGE = "errorPage";
         private final String PAGE_NOT_FOUND = "pagenotfound";
         private static final Logger LOGGER = LogManager.getLogger(CreateNewQuestionServlet.class);
         /**
          * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
          * methods.
          *
          * @param request servlet request
          * @param response servlet response
          * @throws ServletException if a servlet-specific error occurs
          * @throws IOException if an I/O error occurs
          */
         protected void processRequest(HttpServletRequest request, HttpServletResponse response)
                 throws ServletException, IOException {
                  response.setContentType("text/html;charset=UTF-8");
                  PrintWriter out = response.getWriter();
                  String url = ERROR_PAGE;
                  int end = 0;
                  String questionID = request.getParameter("txtQuestionID");
                  String questionContent = request.getParameter("txtQuestionContent");
                  String subject = request.getParameter("cbxSubject");
                  String endSTR = request.getParameter("txtEND");
                  if(!endSTR.isEmpty()){
                           end= Integer.parseInt(endSTR);
                  }
                  String correctAnswer = request.getParameter("txtCorrectAnswer");
                  String button = request.getParameter("btnAction");
                  
                  LocalDate createDate = java.time.LocalDate.now();
                  try {
                           if (questionID == null) {
                                    url = PAGE_NOT_FOUND;
                           } else {
                                    SubjectDAO dao = new SubjectDAO();
                                    dao.getAllSubject();
                                    List<SubjectDTO> listSubject = dao.getSubjectList();
                                    if (listSubject != null) {
                                             request.setAttribute("LIST_SUBJECT", listSubject);
                                    }
                                             
                                             if(button.equals("Create more answers")){
                                                      url = CREATE_NEW_QUESTION_PAGE;
                                                      request.setAttribute("END", end+1);
                                             }else{

                                                      QuestionDAO questionDAO = new QuestionDAO();
                                                      QuestionDTO dto = new QuestionDTO(questionID, questionContent, subject, true, createDate);
                                                      boolean result = questionDAO.ceateNewQuestion(dto);
                                                      if (result) {
                                                               AnswerDAO answerDAO = new AnswerDAO();
                                                               AnswerDTO answerDTO = new AnswerDTO(questionID, correctAnswer,true, true, "0");
                                                               boolean resultAnswer = answerDAO.createNewAnswer(answerDTO);
                                                               if (resultAnswer) {
                                                                        resultAnswer = false;
                                                                        for (int i = 1; i <= Integer.parseInt(endSTR);i++) {
                                                                                 String parameterName = "txtAnswer"+i;
                                                                                 String answer_content = request.getParameter(parameterName);
                                                                                 if(!answer_content.isEmpty()){
                                                                                          answerDTO = new AnswerDTO(questionID, answer_content,false, true, i+"");
                                                                                          resultAnswer = answerDAO.createNewAnswer(answerDTO);
                                                                                          if(!resultAnswer){
                                                                                                   break;
                                                                                          }
                                                                                 }
                                                                        }
                                                                        if(resultAnswer){
                                                                                 request.setAttribute("CREATE_SUCCESS", true);
                                                                                 url = VIEW_QUESTION_PAGE;
                                                                        }    
                                                               }

                                                      }
                                             }
                                    
                           }
                  } catch (SQLException e) {
                           String errMess = e.getMessage();
                           LOGGER.error("CreateNewQuestionServlet_SQLException", e);
                           if (errMess.contains("duplicate")) {
                                    request.setAttribute("END", end);
                                    request.setAttribute("ERRORS", questionID + " is existed!");
                                    url = CREATE_NEW_QUESTION_PAGE;
                           }
                  } catch (NamingException e) {
                           LOGGER.error("CreateNewQuestionServlet_NamingException", e);
                  } finally {
                           RequestDispatcher rd = request.getRequestDispatcher(url);
                           rd.forward(request, response);
                           out.close();
                  }

         
         }

         // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
         /**
          * Handles the HTTP <code>GET</code> method.
          *
          * @param request servlet request
          * @param response servlet response
          * @throws ServletException if a servlet-specific error occurs
          * @throws IOException if an I/O error occurs
          */
         @Override
         protected void doGet(HttpServletRequest request, HttpServletResponse response)
                 throws ServletException, IOException {
                  processRequest(request, response);
         }

         /**
          * Handles the HTTP <code>POST</code> method.
          *
          * @param request servlet request
          * @param response servlet response
          * @throws ServletException if a servlet-specific error occurs
          * @throws IOException if an I/O error occurs
          */
         @Override
         protected void doPost(HttpServletRequest request, HttpServletResponse response)
                 throws ServletException, IOException {
                  processRequest(request, response);
         }

         /**
          * Returns a short description of the servlet.
          *
          * @return a String containing servlet description
          */
         @Override
         public String getServletInfo() {
                  return "Short description";
         }// </editor-fold>

}
