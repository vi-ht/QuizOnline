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
import vihtt.subject.SubjectDAO;
import vihtt.subject.SubjectDTO;


/**
 *
 * @author Thanh Vi
 */
@WebServlet(name = "GetInfoToUpdateServlet", urlPatterns = {"/GetInfoToUpdateServlet"})
public class GetInfoToUpdateServlet extends HttpServlet {
private final String UPDATE_PAGE="updatePage";
private final String PAGE_NOT_FOUND = "pagenotfound";
private static final Logger LOGGER = LogManager.getLogger(GetInfoToUpdateServlet.class);
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
                  String url = UPDATE_PAGE;
                  PrintWriter out = response.getWriter();
                  String ID = request.getParameter("txtQuestionID");
                  try {
                           if (ID == null) {
                                    url = PAGE_NOT_FOUND;
                           }
                           SubjectDAO dao = new SubjectDAO();
                           dao.getAllSubject();
                           List<SubjectDTO> listSubject = dao.getSubjectList();
                           if (listSubject != null) {
                                    request.setAttribute("LIST_SUBJECT", listSubject);
                                    QuestionDAO questionDAO = new QuestionDAO();
                                    QuestionToShow questionToShow = questionDAO.getQuestionByQuestionID(ID);
                                    if (questionToShow != null) {
                                             request.setAttribute("QUESTION_DETAILS", questionToShow);
                                             request.setAttribute("SIZE", questionToShow.getListAnswer().size()-1);
                                    }
                           }
                           
                  } catch (SQLException e) {
                           LOGGER.error("GetInfoToUpdateServlet_SQLException", e);
                  } catch (NamingException ex) {
                           LOGGER.error("GetInfoToUpdateServlet_NamingException", ex);
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
