/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

import daos.BookingDAO;
import daos.HotelDAO;
import daos.RatingDAO;
import dtos.Booking;
import dtos.Hotel;
import dtos.Rating;
import dtos.User;
import java.io.IOException;
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
public class RatingServlet extends HttpServlet {

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
        User user =(User) session.getAttribute("userdata");
        String url =URL.getLOGIN_PAGE();
        try {
            if (user!= null) {
                if (user.getUserRole() == Variable.getADMIN_ROLE()) {
                    url=URL.getINDEX_ADMIN_PAGE();
                }else{
                    url=URL.getUSER_BOOKING_DETAIL_SERVLET();
                }
            }
        } catch (Exception e) {
            log("Exception at RatingServlet :"+e.getMessage());
        }finally{
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
        String url = URL.getLOGIN_PAGE();
        try {
            if (user != null) {
            if (user.getUserRole() == Variable.getADMIN_ROLE()) {
                url=URL.getINDEX_ADMIN_PAGE();
            }
            else{
                String feedBack = request.getParameter("feedBack");
                int score =Integer.parseInt(request.getParameter("score"));
                String hotelID =request.getParameter("hotelID");
                HotelDAO hotelDAO = new HotelDAO();
                Hotel hotel = hotelDAO.getHotelByID(hotelID);
                String bookingID = request.getParameter("bookingID");
                BookingDAO bookingDAO = new BookingDAO();
                Booking booking = bookingDAO.getBookingByBookingIDAndUser(bookingID, user);
                RatingDAO ratingDAO = new RatingDAO();
                Rating rating = new Rating(hotel, user, feedBack.trim(), booking, score);
                ratingDAO.createRating(rating);
                booking.setBookingStatus(3);                
                float update_score = hotel.getHotelRatingScore() + score;
                int new_count =hotel.getHotelRatingCount()+1;
                hotel.setHotelRatingScore(update_score);
                hotel.setHotelRatingCount(new_count);
                hotelDAO.rateHotel(hotel);
                bookingDAO.changeStatusBooking(booking);
                url=URL.getUSER_BOOKING_DETAIL_SERVLET();
            }
        }
        } catch (Exception e) {
            log("Exception at RatingServlet :"+e.getMessage());
        }finally{
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
