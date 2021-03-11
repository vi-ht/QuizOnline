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
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import vihtt.registration.RegistrationDAO;
import vihtt.utils.EncodeHelper;
/**
 *
 * @author Thanh Vi
 */
@WebServlet(name = "CreateNewAccountServlet", urlPatterns = {"/CreateNewAccountServlet"})
public class CreateNewAccountServlet extends HttpServlet {
         final String ERROR_PAGE = "errorPage";
         final String LOGIN_PAGE = "loginPage";
         final String CREATE_PAGE = "createPageJSP";
         private final String PAGE_NOT_FOUND = "pagenotfound";
         private static final Logger LOGGER = LogManager.getLogger(CreateNewAccountServlet.class);
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
                  String email= request.getParameter("txtEmail");
                  String password = request.getParameter("txtPassword");
                  String fullname= request.getParameter("txtFullname");
                  String url = ERROR_PAGE;
                  try {
                           if(email==null || password == null || fullname==null){
                                    url = PAGE_NOT_FOUND;
                           }else{
                                    RegistrationDAO dao = new RegistrationDAO();
                                    boolean result = dao.createAccount(email, EncodeHelper.encode(password), fullname, true, "s");
                                    if (result) {
                                             url = LOGIN_PAGE;
                                    }
                           }
                  } catch (SQLException e) {
                           String errMess = e.getMessage();
                           LOGGER.error("createNewAccountServlet_SQLException", e);
                           if (errMess.contains("duplicate")) {
                                    request.setAttribute("CREATE_ERR", email + " is existed!");
                                    url = CREATE_PAGE;
                           }
                  } catch (NamingException e) {
                           LOGGER.error("createnewAccountServlet_Naming", e);
                  } catch (NoSuchAlgorithmException ex) {
                           LOGGER.error("createnewAccountServlet_NoSuchAlgorithmException", ex);
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
