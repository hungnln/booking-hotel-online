/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

import daos.UserDAO;
import dtos.User;
import java.io.IOException;
import java.io.PrintWriter;
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
public class UserUpdateServlet extends HttpServlet {

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
            out.println("<title>Servlet UserUpdateServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet UserUpdateServlet at " + request.getContextPath() + "</h1>");
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
        try {
            HttpSession session = request.getSession(false);
            {
                User user = (User) session.getAttribute("userdata");
                if (user != null) {
                    url = URL.getPROFILE_PAGE();
                } else {
                    request.setAttribute("msgLoginFail", "Please login and try again");
                }
            }
        } catch (Exception e) {
            log("ChangePassWord_Exception at: " + e.getMessage());
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
        String url = URL.getPROFILE_PAGE();
        try {
            HttpSession session = request.getSession();
            User user = (User) session.getAttribute("userdata");
            boolean valid = true;

            if (user != null) {
                String userName = request.getParameter("userName");
                if (!Validate.checkUserNameValidate(userName.trim())) {
                    valid = false;
                    request.setAttribute("msgName", "Enter length from 1 to 30 with first letter is uppercase. ");
                }
                String userPhoneNumber = request.getParameter("userPhone");
                if (!Validate.checkUserPhoneValidate(userPhoneNumber)) {
                    valid = false;
                    request.setAttribute("msgPhone", "Enter with digit and first digit is 0. ");
                }
                String userAddress = request.getParameter("userAddress");
                if (userAddress.trim().isEmpty() || userAddress.trim().length() > 90) {
                    valid = false;
                    request.setAttribute("msgAddress", "Enter length 90");
                }
                if (valid) {
                    User userUpdate = new User(user.getUserID(), user.getUserPassword(), userName, userAddress, userPhoneNumber, user.getUserCreateDate(), true, user.getUserRole());
                    UserDAO userDAO = new UserDAO();
                    if (userDAO.updateUser(userUpdate)) {
                        session.setAttribute("userdata", userUpdate);
                        request.setAttribute("msgUpdate", "User Update Success");
                    } else {
                        request.setAttribute("msgUpdate", "User Update Fail");
                    }
                }
            } else {
                url = URL.getLOGIN_PAGE();
                request.setAttribute("msgLogin", "Please login");
            }
        } catch (Exception e) {
            log("UpdateUserServlet_Exception: " + e.getMessage());

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
