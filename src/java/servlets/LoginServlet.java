/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

import com.google.common.hash.Hashing;
import daos.UserDAO;
import dtos.User;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import methods.URL;
import methods.Validate;
import methods.Variable;

/**
 *
 * @author SE140018
 */
public class LoginServlet extends HttpServlet {

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
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet LoginServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet LoginServlet at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
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
        String url = URL.getLOGIN_PAGE();
        request.setCharacterEncoding("utf-8");
        response.setCharacterEncoding("utf-8");
        try {
            HttpSession session = request.getSession();
            User user = (User) session.getAttribute("userdata");
            if (user != null) {
                if (user.getUserRole() == Variable.getADMIN_ROLE()) {
                    url = URL.getINDEX_ADMIN_PAGE();
                    request.setAttribute("msg", "You have login");
                } else {
                    url = URL.getINDEX_SERVLET();
                    request.setAttribute("msg", "You have login");
                }
            }
        } catch (Exception e) {
            log("LoginServlet_Exception: " + e.getMessage());
        } finally {
            request.getRequestDispatcher(url).forward(request, response);
        }
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
        request.setCharacterEncoding("utf-8");
        response.setCharacterEncoding("utf-8");
        String url = URL.getLOGIN_PAGE();
        String userPasswordEncode = null;
        try {
            HttpSession session = request.getSession(false);
            String userID = request.getParameter("userID");
            boolean valid = true;
            if (!Validate.checkEmail(userID.trim())) {
                valid = false;
            }

            String userPassword = request.getParameter("userPassword");
            if (Validate.checkUserPasswordValidate(userPassword.trim())) {
                userPasswordEncode = Hashing.sha256()
                        .hashString(userPassword, StandardCharsets.UTF_8)
                        .toString();
            } else {
                valid = false;
            }
            if (valid) {
                UserDAO userDao = new UserDAO();
                User user = userDao.checkLogin(userID, userPasswordEncode);
                if (user != null) {
                    session.setAttribute("userdata", user);
                    url = URL.getINDEX_SERVLET();
                } else {
                    request.setAttribute("msgLoginFail", "Wrong ID or Password. Please try again.");
                }
            }

        } catch (Exception e) {
            log("LoginServlet_Exception: " + e.getMessage());
        } finally {
            request.getRequestDispatcher(url).forward(request, response);
        }
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
