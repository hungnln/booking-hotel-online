/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

import daos.BookingDAO;
import daos.BookingDetailDAO;
import daos.RoomLogDAO;
import dtos.BookingDetail;
import dtos.Hotel;
import dtos.RoomLog;
import dtos.RoomType;
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
public class DeleteBookingServlet extends HttpServlet {

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
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("userdata");
        String url = URL.getLOGIN_PAGE();
        try {
            if (user != null) {
                if (user.getUserRole() == Variable.getADMIN_ROLE()) {
                    url = URL.getINDEX_ADMIN_PAGE();
                } else {
                    url = URL.getBOOKING_ALL();
                    request.setAttribute("msg", "Can't find Booking to delete");
                }
            } else {
                request.setAttribute("msg", "Login to use this function");
            }
        } catch (Exception e) {
            e.printStackTrace();
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
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("userdata");
        String url = URL.getINDEX_SERVLET();
        try {
            if (user != null) {
                if (user.getUserRole() == Variable.getADMIN_ROLE()) {
                    url = URL.getINDEX_ADMIN_PAGE();
                } else {
                    String bookingID = request.getParameter("bookingID");
                    BookingDAO bookingDAO = new BookingDAO();
                    BookingDetailDAO bookingDetailDAO = new BookingDetailDAO();
                    List<BookingDetail> list = bookingDetailDAO.getBookingDetailByBookingID(bookingID, user);
                    if (bookingDAO.deleteBooking(bookingID)) {
                        RoomLogDAO roomLogDAO = new RoomLogDAO();
                        for (BookingDetail bookingDetail : list) {
                            Hotel hotel = bookingDetail.getBooking().getHotel();
                            RoomType RoomType = bookingDetail.getRoomType();
                            String checkin = bookingDetail.getBooking().getCheckinDate();
                            String checkout = bookingDetail.getBooking().getCheckoutDate();
                            RoomLog roomLog = new RoomLog(hotel, RoomType, checkin, checkout);
                            roomLogDAO.deleteLog(roomLog);
                        }
                        request.setAttribute("msg", "Delete Booking Success");
                        url = URL.getBOOKING_ALL();
                    } else {
                        request.setAttribute("msg", "Can't delete Booking");
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
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
