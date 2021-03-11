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
import javax.servlet.http.HttpSession;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import vihtt.result.ResultDAO;
import vihtt.result.ResultHistory;
import vihtt.utils.Constant;

/**
 *
 * @author Thanh Vi
 */
@WebServlet(name = "ViewHistoryServlet", urlPatterns = {"/ViewHistoryServlet"})
public class ViewHistoryServlet extends HttpServlet {
         private final String ERROR_PAGE = "errorPage";
         private final String VIEW_HISTORY_PAGE = "viewHistoryPage";
         private final String PAGE_NOT_FOUND = "pagenotfound";
         private static final Logger LOGGER = LogManager.getLogger(ViewHistoryServlet.class);
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
                  String url = VIEW_HISTORY_PAGE;
                  int pageNumber = 0;
                  String pageNumberSTR = request.getParameter("txtPageNumber");
                  if (pageNumberSTR != null && !(pageNumberSTR.isEmpty())) {
                           pageNumber = Integer.parseInt(pageNumberSTR);
                  }
                  HttpSession session = request.getSession();
                  String email = (String) session.getAttribute("EMAIL");
                  try {
                           if (session.getAttribute("NAME") == null) {
                                    url = PAGE_NOT_FOUND;
                           } else {
                                    if ((boolean) session.getAttribute("ROLE_OF_USER")) {
                                             url = PAGE_NOT_FOUND;
                                    } else {
                                             ResultDAO dao = new ResultDAO();
                                             int sizeOfList = dao.getSizeOfListHistoryByUserEmail(email);
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
                                                      dao.getHistoryByUserEmail(email, offset, fetch);
                                                      List<ResultHistory> listHistory = dao.getListResult();
                                                      if (listHistory != null) {
                                                               request.setAttribute("HISTORY", listHistory);
                                                               request.setAttribute("PAGE_NUMBER", pageNumber);
                                                               request.setAttribute("TOTAL_PAGE", totalPage);
                                                      }
                                             }
                                             request.setAttribute("SIZE", sizeOfList);
                                    }
                           }
                  } catch (SQLException e) {
                           url = ERROR_PAGE;
                           LOGGER.error("ViewHistoryServlet_SQLException", e);
                  } catch (NamingException e) {
                           url = ERROR_PAGE;
                           LOGGER.error("ViewHistoryServlet_NamingException", e);
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
