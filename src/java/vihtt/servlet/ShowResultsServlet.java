/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vihtt.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.Map;
import javax.naming.NamingException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import vihtt.result.ResultDAO;
import vihtt.result.ResultDTO;
import vihtt.result.ResultObject;
import vihtt.subject.SubjectDAO;
import vihtt.subject.SubjectDTO;

/**
 *
 * @author Thanh Vi
 */
@WebServlet(name = "ShowResultsServlet", urlPatterns = {"/ShowResultsServlet"})
public class ShowResultsServlet extends HttpServlet {
         private final String RESULT_PAGE = "resultPage";
         private final String ERROR_PAGE = "errorPage";
         private final String PAGE_NOT_FOUND = "pagenotfound";
         private static final Logger LOGGER = LogManager.getLogger(ShowResultsServlet.class);
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
                  String subjectID = request.getParameter("txtSubjectID");
                  HttpSession session = request.getSession();
                  String email = (String) session.getAttribute("EMAIL");
                  
                  try {
                           if (subjectID == null) {
                                    url = PAGE_NOT_FOUND;
                           } else {
                                    int numberCorrectAnswer = 0;
                                    float totalMark;
                                    float markFor1Answer;
                                    SubjectDAO subjectDAO = new SubjectDAO();
                                    SubjectDTO subjectDTO = subjectDAO.getAllSubjectbySubjectID(subjectID);
                                    int sizeOfList = subjectDTO.getQuantity();
                                    markFor1Answer = ((float) 10) / sizeOfList;
                                    ResultObject result = (ResultObject) session.getAttribute("RESULT");
                                    if (result != null) {
                                             if (result.resultSize() < sizeOfList) {
                                                      url = ERROR_PAGE;
                                             } else {
                                                      for (int i = 0; i < sizeOfList; i++) {
                                                               if (result.resultValue(i)) {
                                                                        numberCorrectAnswer += 1;
                                                               }
                                                      }
                                             }
                                             Map<Integer, Boolean> map = result.getResutls();
                                             totalMark = numberCorrectAnswer * markFor1Answer;
                                             ResultDTO dto = new ResultDTO(subjectID, email, sizeOfList, numberCorrectAnswer, totalMark, true, java.time.LocalDateTime.now());
                                             ResultDAO dao = new ResultDAO();
                                             if (dao.insertOrder(dto)) {
                                                      url = RESULT_PAGE;
                                                      request.setAttribute("MARK", totalMark);
                                                      request.setAttribute("NUMBER_CORRECT_ANSWER", numberCorrectAnswer);
                                                      request.setAttribute("SUBJECT", subjectDTO);
                                                      request.setAttribute("SHOW_RESULT", map);
                                                      session.removeAttribute("RESULT");
                                             }
                                    }
                          }
                  } catch (SQLException e) {
                           LOGGER.error("ShowResultsServlet_SQLException", e);
                  } catch (NamingException ex) {
                           LOGGER.error("ShowResultsServlet_NamingException", ex);
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
