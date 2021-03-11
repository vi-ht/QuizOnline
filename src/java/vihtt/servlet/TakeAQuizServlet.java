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
import vihtt.answer.AnswerDTO;
import vihtt.question.QuestionDAO;
import vihtt.result.ResultObject;
import vihtt.subject.SubjectDAO;
import vihtt.subject.SubjectDTO;
import vihtt.question.QuestionToShow;

/**
 *
 * @author Thanh Vi
 */
@SuppressWarnings("unchecked") 
@WebServlet(name = "TakeAQuizServlet", urlPatterns = {"/TakeAQuizServlet"})
public class TakeAQuizServlet extends HttpServlet {
private final String QUIZ_PAGE = "quizPage";
private final String PAGE_NOT_FOUND = "pagenotfound";
private final String RESULT_PAGE = "showResult";
private final String ERROR_PAGE = "errorPage";
         private static final Logger LOGGER = LogManager.getLogger(TakeAQuizServlet.class);
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
                  String url = QUIZ_PAGE;
                  int pos = 1;
                  int min = 0;
                  int sec = 0;
                  int hour  = 0;
                  int sizeOfListQuestion = 0; 
                  HttpSession session = request.getSession();
                  String subjectID = request.getParameter("txtSubjectID");   
                  String posSTR = request.getParameter("txtPos");
                  String hourSRT = request.getParameter("txtHour");
                  String minSRT = request.getParameter("txtMin");
                  String secSRT = request.getParameter("txtSecond");
                  String button = request.getParameter("btnAction");
                  if(button==null){
                           button="";
                  }
                  if(button.equals("Take A Quiz")){
                           ResultObject result = (ResultObject) session.getAttribute("RESULT");
                           if (result != null) {
                                    session.removeAttribute("RESULT");
                           }
                           session.removeAttribute("LIST_QUESTION");
                  }
                  if(posSTR != null && !(posSTR.isEmpty())){
                           pos = Integer.parseInt(posSTR)+1;
                  }
                  try {
                           if(subjectID==null){
                                    url=PAGE_NOT_FOUND;
                           }else{
                                    List<QuestionToShow> listQuestion = (List<QuestionToShow>)session.getAttribute("LIST_QUESTION");
                                    SubjectDAO subjectDAO = new SubjectDAO();
                                    SubjectDTO subjectDTO = subjectDAO.getAllSubjectbySubjectID(subjectID);
                                    int sizeOfList=subjectDTO.getQuantity();
                                    int totalTime = subjectDTO.getTime();
                                    String time = totalTime+"";
                                    if((hourSRT != null && !(hourSRT.isEmpty())) && (minSRT != null && !(minSRT.isEmpty())) && (secSRT != null && !(secSRT.isEmpty()))){
                                             hour = Integer.parseInt(hourSRT);
                                             min = Integer.parseInt(minSRT);
                                             sec = Integer.parseInt(secSRT);
                                    }else{
                                             if(time.length()>2 ){
                                                      hour = Integer.parseInt(time.substring(0,1));
                                                      min =  Integer.parseInt(time.substring(2));
                                             }else if(time.length()<=2){
                                                      min =  Integer.parseInt(time.substring(0));
                                             }
                                    }
                                    if(listQuestion==null){
                                             QuestionDAO questionDAO = new QuestionDAO();
                                             questionDAO.getRandomQuestion(true, subjectDTO.getQuantity(), subjectID);
                                             listQuestion = questionDAO.getQuestionListToShow();
                                             session.setAttribute("LIST_QUESTION", listQuestion);
                                    }
                                    if(listQuestion!=null){
                                                      sizeOfListQuestion = listQuestion.size();
                                    }
                                    if(sizeOfListQuestion<sizeOfList){
                                           url=PAGE_NOT_FOUND;
                                    }else{
                                             if(pos>1){
                                                               String correctAns = request.getParameter("txtCorrectAns");
                                                               ResultObject result = (ResultObject) session.getAttribute("RESULT");
                                                               if (result == null) {
                                                                        result = new ResultObject();
                                                                        result.addResult(sizeOfList, false);
                                                               }
                                                               List<AnswerDTO> listAnswer = listQuestion.get(pos-2).getListAnswer();
                                                               for (AnswerDTO answerDTO : listAnswer) {
                                                                        if(answerDTO.getAnswerID().equals(correctAns)){
                                                                                 if(answerDTO.isCorrectAnswer()){
                                                                                          result.updateResult(pos-2, true);
                                                                                 }else{
                                                                                          result.updateResult(pos-2, false);
                                                                                 }
                                                                                 break;
                                                                        }
                                                               }
                                                               session.setAttribute("RESULT", result);
                                                      }
                                             if(button.equals("Submit quiz") || button.equals("")){
                                                       session.removeAttribute("LIST_QUESTION");
                                                      url=RESULT_PAGE;
                                             }else{
                                                      QuestionToShow question = listQuestion.get(pos-1);                                                      
                                                      if (question != null) {
                                                               request.setAttribute("QUESTION", question);
                                                               request.setAttribute("SUBJECT", subjectDTO);
                                                               request.setAttribute("POS",pos);
                                                               request.setAttribute("HOUR",hour);
                                                               request.setAttribute("MIN",min);
                                                               request.setAttribute("SEC",sec);
                                                      }
                                             }    
                                    }
                           }
                           
                  }catch(SQLException e)  {
                           LOGGER.error("TakeAQuizServlet_SQLException", e);
                           url=ERROR_PAGE;
                  }catch(NamingException ex)  {
                           LOGGER.error("TakeAQuizServlet_NamingException", ex);
                           url=ERROR_PAGE;
                  }catch(NumberFormatException ex)  {
                           LOGGER.error("TakeAQuizServlet_NumberFormatException", ex);
                           url=ERROR_PAGE;
                  }
                  finally{
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
