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

/**
 *
 * @author SE140018
 */
public class UserChangePasswordServlet extends HttpServlet {

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
            out.println("<title>Servlet UserChangePasswordServlet</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet UserChangePasswordServlet at " + request.getContextPath() + "</h1>");
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
       String url = URL.getINDEX_SERVLET();
        try {
            HttpSession session = request.getSession();
            {
                User user = (User) session.getAttribute("userdata");
                if (user != null) {                   
                        url=URL.getCHANGEPASSWORD_PAGE();                   
                }else{
                    request.setAttribute("msg", "Please login to use this funtion");
                }
            }
        } catch (Exception e) {
            log("ChangePassword_Exception at: " + e.getMessage());
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
        String url = URL.getCHANGEPASSWORD_PAGE();
        try {
            boolean valid = true;
            HttpSession session = request.getSession();
            User user = (User) session.getAttribute("userdata");
            if (user != null) {
                String userPassword = request.getParameter("userPassword");
                String userPasswordEncode = Hashing.sha256()
                .hashString(userPassword, StandardCharsets.UTF_8)
                .toString();
                if (userPasswordEncode.trim().equals(user.getUserPassword()) == false) {
                    valid = false;
                    request.setAttribute("msgPassword", "Password is not correct");
                }
                String userNewPassword = request.getParameter("userNewPassword");
                if (Validate.checkUserPasswordValidate(userNewPassword.trim()) == false) {
                    valid = false;
                    request.setAttribute("msgNewPassword", "Enter length from 8 to 30 with at least digit, lowercase and uppercase. ");
                }
                String userReNewPassword = request.getParameter("userReNewPassword");
                if (userReNewPassword.trim().equals(userNewPassword) == false || userReNewPassword.isEmpty()) {
                    valid = false;
                    request.setAttribute("msgReNewPassword", "Re-Password do not match.");
                }
                if (valid) {                   
                String userNewPasswordEncode = Hashing.sha256()
                .hashString(userNewPassword, StandardCharsets.UTF_8)
                .toString();
                    User updateUser = new User(user.getUserID(), userNewPasswordEncode, user.getUserName(), user.getUserAddress(),user.getUserPhone(),user.getUserCreateDate(),user.isUserStatus(), user.getUserRole());
                    UserDAO userDAO = new UserDAO();
                    if (userDAO.updateUser(updateUser)) {
                        session.setAttribute("userdata", updateUser);
                        request.setAttribute("msgUpdateSuccess", "Update Password success");
                    } else {
                        request.setAttribute("msgUpdateFail", "Update Password fail");
                    }
                }
            }
        } catch (Exception e) {
            log("ChangePasswordServlet_Exception: " + e.getMessage());
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
