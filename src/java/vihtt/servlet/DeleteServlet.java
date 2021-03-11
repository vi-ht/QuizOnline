/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vihtt.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import vihtt.question.QuestionDAO;

/**
 *
 * @author Thanh Vi
 */
@WebServlet(name = "DeleteServlet", urlPatterns = {"/DeleteServlet"})
public class DeleteServlet extends HttpServlet {
         private final String ERROR_PAGE = "errorPage";
         private final String PAGE_NOT_FOUND = "pagenotfound";
         private static final Logger LOGGER = LogManager.getLogger(DeleteServlet.class);
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
                  String urlrewriting= ERROR_PAGE;        
                  String questionName = request.getParameter("txtQuestionName");
                  String subjectName = request.getParameter("txtSubjectName");
                  String status = request.getParameter("cbxStatus");
                  String ID = request.getParameter("txtQuestionID");
                  try {
                           if (ID == null) {
                                    urlrewriting = PAGE_NOT_FOUND;
                           } else {
                                    QuestionDAO dao = new QuestionDAO();
                                    boolean result = dao.deleteQuestion(ID);
                                    if (result) {
                                             urlrewriting = "searchRewriting" + "txtQuestionName=" + questionName + 
                                                     "&txtSubjectName=" + subjectName + "&cbxStatus=" + status;
                                    }
                           }
                  } catch (SQLException e) {
                           LOGGER.error("DeleteServlet_SQLException", e);
                  } catch (NamingException e) {
                           LOGGER.error("DeleteSServlet_NameException", e);
                  } finally {
                           response.sendRedirect(urlrewriting);
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
