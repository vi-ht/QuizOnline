/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vihtt.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
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
import vihtt.question.QuestionDAO;
import vihtt.question.QuestionToShow;
import vihtt.utils.Constant;

/**
 *
 * @author Thanh Vi
 */
@WebServlet(name = "SearchQuestionServlet", urlPatterns = {"/SearchQuestionServlet"})
public class SearchQuestionServlet extends HttpServlet {
private final String ERROR_PAGE = "errorPage";
         private final String VIEW_QUESTION_PAGE = "searchQuestionPage";
         private final String PAGE_NOT_FOUND = "pagenotfound";
         private static final Logger LOGGER = LogManager.getLogger(SearchQuestionServlet.class);
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
                  String url = VIEW_QUESTION_PAGE;
                  int pageNumber = 0;
                  String questionName = request.getParameter("txtQuestionName");
                  String subjectName = request.getParameter("txtSubjectName");
                  String status = request.getParameter("cbxStatus");
                  String pageNumberSTR = request.getParameter("txtPageNumber");
                  if (pageNumberSTR != null && !(pageNumberSTR.isEmpty())) {
                           pageNumber = Integer.parseInt(pageNumberSTR);
                  }
                  try {
                           if (questionName == null || subjectName == null || status == null) {
                                    url = PAGE_NOT_FOUND;
                           } else {
                                    QuestionDAO dao = new QuestionDAO();
                                    int sizeOfList = 0;
                                    if (status.equals("All")) {
                                             sizeOfList = dao.getSizeOfListQuestion(subjectName, questionName);
                                    } else if (!status.equals("All") && !status.equals("None")) {
                                             if (status.equals("Enable")) {
                                                      sizeOfList = dao.getSizeOfListQuestion(subjectName, questionName, true);
                                             } else if (status.equals("Disable")) {
                                                      sizeOfList = dao.getSizeOfListQuestion(subjectName, questionName, false);
                                             }
                                    }
                                    int totalPage = sizeOfList / Constant.PAGESIZE;
                                    int remainderList = sizeOfList % Constant.PAGESIZE;
                                    if (remainderList > 0) {
                                             totalPage += 1;
                                    }
                                    int offset = pageNumber * Constant.PAGESIZE;
                                    int fetch = 0;
                                    if (pageNumber < (totalPage - 1)) {
                                             fetch = Constant.PAGESIZE;
                                    }
                                    if (pageNumber == (totalPage - 1)) {
                                             if (remainderList == 0) {
                                                      fetch = Constant.PAGESIZE;
                                             } else {
                                                      fetch = remainderList;
                                             }
                                    }
                                    if (sizeOfList > 0) {
                                             if (status.equals("All")) {
                                                      dao.searchQuestion(subjectName, questionName, offset, fetch);
                                             } else if (!status.equals("All") && !status.equals("None")) {
                                                      if (status.equals("Enable")) {
                                                               dao.searchQuestion(subjectName, questionName, true, offset, fetch);
                                                      } else if (status.equals("Disable")) {
                                                               dao.searchQuestion(subjectName, questionName, false, offset, fetch);
                                                      }
                                             }
                                             List<QuestionToShow> list = dao.getQuestionListToShow();
                                             if (list != null) {
                                                      request.setAttribute("QUESTION", list);
                                                      request.setAttribute("PAGE_NUMBER", pageNumber);
                                                      request.setAttribute("TOTAL_PAGE", totalPage);
                                             }
                                    }
                                    request.setAttribute("SIZE", sizeOfList);
                                    switch (status) {
                                             case "Enable":
                                                      request.setAttribute("STATUS", "t");
                                                      break;
                                             case "Disable":
                                                      request.setAttribute("STATUS", "f");
                                                      break;
                                             case "None":
                                                      request.setAttribute("STATUS", "None");
                                                      break;
                                             case "All":
                                                      request.setAttribute("STATUS", "All");
                                                      break;
                                             default:
                                                      break;
                                    }
                           }
                  } catch (SQLException e) {
                           LOGGER.error("SearchQuestionServlet_SQLException", e);
                           url = ERROR_PAGE;
                  } catch (NamingException e) {
                           LOGGER.error("SearchQuestionServlet_NamingException", e);
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
