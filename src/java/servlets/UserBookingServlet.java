/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

import daos.BookingDAO;
import dtos.Booking;
import dtos.User;
import java.io.IOException;
import java.util.ArrayList;
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
public class UserBookingServlet extends HttpServlet {

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
        String url = URL.getBOOKING_ALL();
        try {
            if (user != null) {
                if (user.getUserRole() == Variable.getADMIN_ROLE()) {
                    url = URL.getINDEX_ADMIN_PAGE();
                } else {
                    List<Booking> list = new ArrayList<>();
                    String hotelName = request.getParameter("searchHotelName");
                    String firstDate = request.getParameter("firstCreateDate");
                    String lastDate = request.getParameter("lastCreateDate");
                    BookingDAO bookingDAO = new BookingDAO();
                    if (hotelName != null && firstDate != null && lastDate != null) {
                        if (hotelName.trim().isEmpty() && firstDate.trim().isEmpty() && lastDate.trim().isEmpty()) {
                            list = bookingDAO.getAllBookingByUser(user);
                        } else {
                            if (hotelName.isEmpty()) {
                                list = bookingDAO.getAllBookingByUserByRangeCreateDate(user, firstDate, lastDate);
                            } else if (firstDate.trim().isEmpty() && lastDate.trim().isEmpty()) {
                                list = bookingDAO.getAllBookingByUserByHotelName(user, hotelName);
                            } else {
                                list = bookingDAO.getAllBookingByUserByHotelNameByRangeCreateDate(user, hotelName, firstDate, lastDate);
                            }
                        }
                        request.setAttribute("msg", "Search done");
                    } else {
                        list = bookingDAO.getAllBookingByUser(user);
                    }                   
                    request.setAttribute("bookings", list);
                }
            }else{
                url = URL.getLOGIN_PAGE();
            }
        } catch (Exception e) {
            log("Exception at UserBookingServlet :"+e.getMessage());
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
