/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

import com.google.common.hash.Hashing;
import daos.UserDAO;
import dtos.CustomerPojo;
import dtos.User;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import methods.URL;
import methods.Validate;
import methods.Variable;
import utils.GmailAPI;
import utils.StripeAPI;

/**
 *
 * @author SE140018
 */
public class SignUpServlet extends HttpServlet {

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
            out.println("<title>Servlet SignUpServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet SignUpServlet at " + request.getContextPath() + "</h1>");
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
        String url = URL.getSIGNUP_PAGE();
        try {
            HttpSession session = request.getSession();
            {
                User user = (User) session.getAttribute("userdata");
                if (user != null) {
                    url = URL.getINDEX();
                    request.setAttribute("msg", "You have login");
                    request.setAttribute("msgStatus", "true");
                }
            }
        } catch (Exception e) {
            log("SignupServlet_Exception at: " + e.getMessage());
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
        String url = URL.getSIGNUP_PAGE();
        boolean valid = true;

        try {
            HttpSession session = request.getSession(false);
            User user = (User) session.getAttribute("userdata");
            if (user != null) {
                if (user.getUserRole() == Variable.getADMIN_ROLE()) {
                    url = URL.getINDEX_ADMIN_PAGE();
                } else {
                    url = URL.getINDEX_SERVLET();
                }
            } else {
                String userPasswordEncode = null;
                String userID = request.getParameter("userID");
                if (Validate.checkEmail(userID.trim())) {
                    UserDAO userDAO = new UserDAO();
                    if (userDAO.checkExistEmail(userID)) {
                        valid = false;
                        request.setAttribute("msgID", "ID is exist.");
                    }
                } else {
                    valid = false;
                    request.setAttribute("msgID", "Enter length from 8 to 30 with alphanumeric characters, lowercase or uppercase. ");
                }
                String userPassword = request.getParameter("userPassword");
                if (!Validate.checkUserPasswordValidate(userPassword.trim())) {
                    valid = false;
                    request.setAttribute("msgPassword", "Enter length from 8 to 30 with at least digit, lowercase and uppercase. ");
                }
                String userRePassword = request.getParameter("userRePassword");
                if (userRePassword.trim().isEmpty()) {
                    valid = false;
                    request.setAttribute("msgRePassword", "Empty");
                } else {
                    if (!userRePassword.trim().equals(userPassword)) {
                        valid = false;
                        request.setAttribute("msgRePassword", "Do not match password");
                    }
                }

                String userName = request.getParameter("userName");
                if (!Validate.checkUserNameValidate(userName)) {
                    valid = false;
                    request.setAttribute("msgName", "Enter length from 1 to 30 with first letter is uppercase. ");
                }

                String userPhone = request.getParameter("userPhone");
                if (!Validate.checkUserPhoneValidate(userPhone.trim())) {
                    valid = false;
                    request.setAttribute("msgPhoneNumber", "Enter with digit and first digit is 0. ");
                }
                String userAddress = request.getParameter("userAddress");
                if (!Validate.checkUserAddressValidate(userAddress.trim())) {
                    valid = false;
                    request.setAttribute("msgAddress", "Enter length 90");
                }
                int userRole = Variable.getUSER_ROLE();
                if (valid) {
                    userPasswordEncode = Hashing.sha256()
                            .hashString(userPassword, StandardCharsets.UTF_8)
                            .toString();
                    Date createDate = new Date();
                    DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                    String createDateString = dateFormat.format(createDate);
                    User newuser = new User(userID, userPasswordEncode, userName, userAddress, userPhone, createDateString, true, userRole);
                    UserDAO userDAO = new UserDAO();
                    if (userDAO.createUser(newuser)) {
                        url = URL.getLOGIN_PAGE();
                        request.setAttribute("msgNotification", "SignUp Success");
                        CustomerPojo customerPojo = new CustomerPojo(userID, userName, userPhone);
                        StripeAPI.createCustomerPojo(customerPojo);
                        String code = StripeAPI.createPromotionCode(userID);
                        GmailAPI gmailAPI = new GmailAPI();
                        StripeAPI.createPromotionCode(userID);
                        gmailAPI.sendEmailWhenCreatUserSuccess(userID, code);
                    } else {
                        request.setAttribute("msgNotification", "SignUp Fail");
                    }
                }
            }
        } catch (Exception e) {
            log("SignUpServlet_Exception: " + e.getMessage());
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
