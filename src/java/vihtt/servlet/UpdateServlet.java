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
import javax.servlet.annotation.MultipartConfig;
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
import vihtt.question.QuestionToShow;
import vihtt.subject.SubjectDAO;
import vihtt.subject.SubjectDTO;

/**
 *
 * @author Thanh Vi
 */
@WebServlet(name = "UpdateServlet", urlPatterns = {"/UpdateServlet"})
@MultipartConfig()
public class UpdateServlet extends HttpServlet {

         private final String ERROR_PAGE = "errorPage";
         private final String UPDATE_PAGE = "updatePage";
         private final String PAGE_NOT_FOUND = "pagenotfound";
         private static final Logger LOGGER = LogManager.getLogger(UpdateServlet.class);

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
                  boolean status = false;
                  String questionID = request.getParameter("txtQuestionID");
                  String questionName = request.getParameter("txtQuestionName");
                  String subjectName = request.getParameter("txtSubjectName");
                  String statusRewriting = request.getParameter("cbxStatus");
                  String questionContent = request.getParameter("txtQuestionContent");
                  String subject = request.getParameter("cbxSubject");
                  String correctAnswer = request.getParameter("txtCorrectAnswer");
                  String statusSTR = request.getParameter("cbxStatusUpdate");
                  LocalDate createDate = java.time.LocalDate.now();
                  try {
                           if (questionID == null) {
                                    url = PAGE_NOT_FOUND;
                           } else {
                                    if (statusSTR.equals("Active")) {
                                             status = true;
                                    }
                                    SubjectDAO dao = new SubjectDAO();
                                    dao.getAllSubject();
                                    List<SubjectDTO> listSubject = dao.getSubjectList();
                                    if (listSubject != null) {
                                             request.setAttribute("LIST_SUBJECT", listSubject);
                                    }
                                    QuestionDAO questionDAO = new QuestionDAO();
                                    QuestionDTO dto = new QuestionDTO(questionID, questionContent, subject, status, createDate);
                                    QuestionToShow dtoCheckDuplicated = questionDAO.getQuestionByQuestionID(questionID);
                                    boolean checkQuestion = true;
                                    boolean checkAnswer = true;

                                    if (!(questionContent.equals(dtoCheckDuplicated.getQuestion_content()) && subject.equals(dtoCheckDuplicated.getSubjectID())
                                            && status == dtoCheckDuplicated.isStatus())) {
                                             checkQuestion = false;
                                    }
                                    for (AnswerDTO a : dtoCheckDuplicated.getListAnswer()) {
                                             if (a.isCorrectAnswer()) {
                                                      if (!correctAnswer.equals(a.getAnswerContent())) {
                                                               checkAnswer = false;
                                                               break;
                                                      }
                                             } else {
                                                      String parameterName = "txtAnswer" + a.getAnswerID();
                                                      if (!request.getParameter(parameterName).equals(a.getAnswerContent())) {
                                                               checkAnswer = false;
                                                               break;

                                                      }
                                             }
                                    }
                                    if (checkAnswer && checkQuestion) {
                                             request.setAttribute("NO_MODIFY_UPDATE", true);
                                             request.setAttribute("QUESTION_DETAILS", dtoCheckDuplicated);
                                             url = UPDATE_PAGE;
                                             request.setAttribute("SIZE", request.getParameter("txtSize"));
                                    } else {
                                             if ((checkAnswer == false) && (checkQuestion == true)) {
                                                      AnswerDAO answerDAO = new AnswerDAO();
                                                      boolean result = true;
                                                      for (AnswerDTO a : dtoCheckDuplicated.getListAnswer()) {
                                                               AnswerDTO answer;
                                                               if (a.isCorrectAnswer()) {
                                                                        answer = new AnswerDTO(questionID, correctAnswer, a.isCorrectAnswer(), a.isStatus(), a.getAnswerID());
                                                               } else {
                                                                        String parameterName = "txtAnswer" + a.getAnswerID();
                                                                        answer = new AnswerDTO(questionID, request.getParameter(parameterName), a.isCorrectAnswer(), a.isStatus(), a.getAnswerID());
                                                               }
                                                               result = answerDAO.updateAnswer(answer);
                                                               if (!result) {
                                                                        break;
                                                               }
                                                      }
                                                      if (result) {
                                                               request.setAttribute("UPDATE_SUCCESS", true);
                                                               url = "searchRewriting" + "txtQuestionName=" + questionName + "&txtSubjectName=" + subjectName
                                                                       + "&cbxStatus=" + statusRewriting;
                                                      }
                                             } else if ((checkQuestion == false) && (checkAnswer == true)) {
                                                      boolean result = questionDAO.updateQuestion(dto);
                                                      if (result) {
                                                               request.setAttribute("UPDATE_SUCCESS", true);
                                                               url = "searchRewriting" + "txtQuestionName=" + questionName + "&txtSubjectName=" + subjectName
                                                                       + "&cbxStatus=" + statusRewriting;
                                                      }
                                             } else if ((checkQuestion == false) && (checkAnswer == false)) {
                                                      boolean resultUpdateQuestion = questionDAO.updateQuestion(dto);
                                                      AnswerDAO answerDAO = new AnswerDAO();
                                                      boolean resultUpdateAnswer = true;
                                                      for (AnswerDTO a : dtoCheckDuplicated.getListAnswer()) {
                                                               AnswerDTO answer;
                                                               if (a.isCorrectAnswer()) {
                                                                        answer = new AnswerDTO(questionID, correctAnswer, a.isCorrectAnswer(), a.isStatus(), a.getAnswerID());
                                                               } else {
                                                                        String parameterName = "txtAnswer" + a.getAnswerID();
                                                                        answer = new AnswerDTO(questionID, request.getParameter(parameterName), a.isCorrectAnswer(), a.isStatus(), a.getAnswerID());
                                                               }
                                                               resultUpdateAnswer = answerDAO.updateAnswer(answer);
                                                               if (!resultUpdateAnswer) {
                                                                        break;
                                                               }
                                                      }
                                                      if (resultUpdateAnswer && resultUpdateQuestion) {
                                                               request.setAttribute("UPDATE_SUCCESS", true);
                                                               url = "searchRewriting" + "txtQuestionName=" + questionName + "&txtSubjectName=" + subjectName
                                                                       + "&cbxStatus=" + statusRewriting;
                                                      }
                                             }

                                    }

                           }
                  } catch (SQLException e) {
                           LOGGER.error("UpdateServlet_SQL", e);
                           url = ERROR_PAGE;
                  } catch (NamingException e) {
                           LOGGER.error("UpdateServlet_Name", e);
                           url = ERROR_PAGE;
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
