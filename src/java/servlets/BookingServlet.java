/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

import daos.BookingDAO;
import daos.BookingDetailDAO;
import daos.HotelDAO;
import daos.RoomLogDAO;
import dtos.Booking;
import dtos.BookingCartItem;
import dtos.Hotel;
import dtos.RoomLog;
import dtos.User;
import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import methods.Random;
import methods.URL;
import methods.Variable;
import utils.GmailAPI;
import utils.StripeAPI;

/**
 *
 * @author SE140018
 */
public class BookingServlet extends HttpServlet {

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
        List<BookingCartItem> cart = (List<BookingCartItem>) session.getAttribute("cart");
        boolean emptyCode = false;
        boolean valid = true;
        double discount = 0;
        String url = URL.getCART();
        try {
            if (user != null) {
                if (user.getUserRole() == Variable.getADMIN_ROLE()) {
                    url = URL.getINDEX_ADMIN_PAGE();
                } else {
                    String checkin = request.getParameter("checkin");
                    String checkout = request.getParameter("checkout");
                    String hotelID = request.getParameter("hotelID");
                    String discountCode = request.getParameter("discountCode");
                    if (discountCode.trim().isEmpty()) {
                        emptyCode = true;
                    } else {
                        discount = StripeAPI.checkPromotionCodeAndGetDiscount(user.getUserID(), discountCode);
                        if (discount < 0) {
                            request.setAttribute(url, url);
                            valid = false;
                        } else if (discount == 0) {
                            emptyCode = true;
                        }
                    }
                    if (valid) {
                        HotelDAO hotelDAO = new HotelDAO();
                        Hotel hotel = hotelDAO.getHotelByID(hotelID);
                        long total = (long) session.getAttribute("total");
                        if (!emptyCode) { 
                            long discountLong = (long) discount;
                            total = (long) total -  (total * discountLong/100);
                        }
                        String bookingID = Random.getRandomBookingID();
                        Booking booking = new Booking(bookingID, user, total, null, checkin, checkout, discountCode, 1, false, hotel);
                        BookingDAO bookingDAO = new BookingDAO();
                        BookingDetailDAO bookingDetailDAO = new BookingDetailDAO();
                        if (bookingDAO.createBooking(booking)) {
                            for (BookingCartItem bookingCartItem : cart) {
                                bookingDetailDAO.createBookingDetail(bookingID, bookingCartItem);
                                for (int i = 0; i < bookingCartItem.getQuantity(); i++) {
                                    RoomLog roomLog = new RoomLog(hotel, bookingCartItem.getRoomType(), checkin, checkout);
                                    RoomLogDAO roomLogDAO = new RoomLogDAO();
                                    roomLogDAO.createLog(roomLog);
                                }
                            }
                        }
                        GmailAPI gmailAPI = new GmailAPI();
                        gmailAPI.sendEmailWhenBookingSuccess(user.getUserID(), booking);
                        request.setAttribute("bookingID", bookingID);
                        request.setAttribute("msg", "Booking Success");
                        url = URL.getUSER_BOOKING_DETAIL_SERVLET();
                        session.removeAttribute("cart");
                    } else {
                        request.setAttribute("msg", "Can't use this code");
                    }
                }
            }else{
                url = URL.getLOGIN_PAGE();
            }
        } catch (Exception e) {
            log("Exception at BookingServlet :"+e.getMessage());
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
