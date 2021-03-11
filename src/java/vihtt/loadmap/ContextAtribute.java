/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vihtt.loadmap;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.StringTokenizer;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

/**
 *
 * @author Thanh Vi
 */
public class ContextAtribute implements ServletContextListener{
         private static final Logger LOGGER = LogManager.getLogger(ContextAtribute.class);
         String filename = "IndexPage.txt";
         @Override
         public void contextInitialized(ServletContextEvent sce) {
                  try {
                           ServletName dto = new ServletName();
                           String path = sce.getServletContext().getRealPath("/");
                           FileReader f = new FileReader(path + "WEB-INF\\" + filename);
                           BufferedReader br = new BufferedReader(f);
                           String line;
                           while ((line = br.readLine()) != null) {
                                    line = line.trim();
                                    if (line.length() > 0) {
                                             StringTokenizer stk = new StringTokenizer(line, "=");
                                             String key = stk.nextToken();
                                             String value = stk.nextToken();
                                             dto.addDTO(key, value);
                                    }
                                    sce.getServletContext().setAttribute("SERVLETNAME", dto);
                           }
                  } catch (IOException e) {
                           LOGGER.error("ContextAtribute_IOException", e);
                  } catch (Exception ex) {
                           LOGGER.error("ContextAtribute_Exception", ex);
                  }
         }

         @Override
         public void contextDestroyed(ServletContextEvent sce) {
                  
         }

  
}
