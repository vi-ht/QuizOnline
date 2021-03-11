/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vihtt.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import vihtt.registration.RegistrationDAO;
import vihtt.registration.RegistrationDTO;
import vihtt.utils.EncodeHelper;

/**
 *
 * @author Thanh Vi
 */
@WebServlet(name = "LoginServlet", urlPatterns = {"/LoginServlet"})
public class LoginServlet extends HttpServlet {
         private final String HOME_PAGE = "subject";
         private final String INVALID_PAGE = "invalidPage";
         private final String VIEW_QUESTION_PAGE = "searchQuestionPage";
         private final String PAGE_NOT_FOUND = "pagenotfound";
         private static final Logger LOGGER = LogManager.getLogger(LoginServlet.class);
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
                 throws ServletException, IOException{
                  response.setContentType("text/html;charset=UTF-8");
                  PrintWriter out = response.getWriter();
                  String url = INVALID_PAGE;
                  String email = request.getParameter("txtEmail");
                  String password = request.getParameter("txtPassword");
                  boolean role = false;
                  try {
                           if (email == null || password == null) {
                                    url = PAGE_NOT_FOUND;
                           } else {
                                    RegistrationDAO dao = new RegistrationDAO();
                                    RegistrationDTO result = dao.checkLogin(email, EncodeHelper.encode(password));
                                    if (result != null) {
                                             url = HOME_PAGE;
                                             HttpSession session = request.getSession();
                                             session.setAttribute("NAME", result.getFullname());
                                             session.setAttribute("EMAIL", result.getEmail());
                                             if (result.getRoleName().trim().equals("admin")) {
                                                      role = true;
                                                      url = VIEW_QUESTION_PAGE;
                                             }
                                             session.setAttribute("ROLE_OF_USER", role);
                                    }
                           }

                  } catch (SQLException e) {
                           LOGGER.error("LoginServlet_SQLException", e);
                  } catch (NamingException ex) {
                           LOGGER.error("LoginServlet_NamingException", ex);
                  } catch (NoSuchAlgorithmException ex) {
                           LOGGER.error("LoginServlet_NoSuchAlgorithmException", ex);
                  } finally {
                           response.sendRedirect(url);
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
