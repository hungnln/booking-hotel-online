/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

import daos.BookingDAO;
import daos.BookingDetailDAO;
import daos.RatingDAO;
import dtos.Booking;
import dtos.BookingDetail;
import dtos.Rating;
import dtos.User;
import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import methods.URL;
import methods.Variable;

/**
 *
 * @author SE140018
 */
public class UserBookingDetailServlet extends HttpServlet {

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
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("userdata");
        String url = URL.getBOOKING_DETAIL();
        try {
            if (user != null) {
                if (user.getUserRole() == Variable.getADMIN_ROLE()) {
                    url = URL.getINDEX_ADMIN_PAGE();
                } else {
                    String bookingID = request.getParameter("bookingID");
                    if (bookingID == null) {
                        bookingID = (String) request.getAttribute("bookingID");
                    }
                    BookingDetailDAO bookingDetailDAO = new BookingDetailDAO();
                    BookingDAO bookingDAO = new BookingDAO();
                    List<BookingDetail> list = bookingDetailDAO.getBookingDetailByBookingID(bookingID, user);
                    Booking booking = bookingDAO.getBookingByBookingIDAndUser(bookingID, user);
                    request.setAttribute("bookingdetails", list);
                    request.setAttribute("booking", booking);
                    if (booking.getBookingStatus() == 3) {
                        RatingDAO ratingDAO = new RatingDAO();
                        Rating rating = ratingDAO.getRatingByBookingID(booking);
                        request.setAttribute("rating", rating);
                    }
                    
                }
            } else {
                url = URL.getLOGIN_PAGE();
                request.setAttribute("msg", "Login to use this function");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            request.getRequestDispatcher(url).forward(request, response);
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
