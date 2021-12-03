/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

import daos.RoomTypeDAO;
import dtos.BookingCartItem;
import dtos.RoomType;
import dtos.User;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
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
public class ChangeDateBookingServlet extends HttpServlet {

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
            out.println("<title>Servlet ChangeDateBookingServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet ChangeDateBookingServlet at " + request.getContextPath() + "</h1>");
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
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("userdata");
        String url = URL.getCART();
        try {
            if (user != null) {
                if (user.getUserRole() == Variable.getADMIN_ROLE()) {
                    url = URL.getINDEX_ADMIN_PAGE();
                }
            }
        } catch (Exception e) {
            log("Exception at ChangeDateBookingServlet :" + e.getMessage());
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
        List<BookingCartItem> cart = (List<BookingCartItem>) session.getAttribute("cart");
        boolean isAdmin = false;
        boolean valid = true;
        String url = URL.getCART();
        try {
            if (user != null) {
                if (user.getUserRole() == 2) {
                    url = URL.getINDEX_ADMIN_PAGE();
                    isAdmin = true;
                }
            }
            if (!isAdmin) {

                String checkin = request.getParameter("checkin");
                String checkout = request.getParameter("checkout");
                if (!Variable.compareDatewithCurrenDate(checkin)) {
                    request.setAttribute("msg", "Can't set checkin");
                } else {
                    long total = 0;
                    for (BookingCartItem bookingCartItem : cart) {
                        String roomID = bookingCartItem.getRoomType().getRoomID();
                        RoomTypeDAO roomTypeDAO = new RoomTypeDAO();
                        RoomType roomType = roomTypeDAO.getRoomByIDWithCheckInAdnCheckOut(roomID, checkin, checkout);
                        if (bookingCartItem.getQuantity() <= roomType.getRoomActiveQuantity()) {
                            bookingCartItem.setRoomType(roomType);
                            long subtotal = (roomType.getRoomPrice() * bookingCartItem.getQuantity() * Variable.getDaysWithCheckInAndCheckOut(checkin, checkout));
                            total += subtotal;
                        } else {
                            request.setAttribute("msg", "Room is out of stock");
                            valid = false;
                            break;
                        }
                    }
                    if (valid) {
                        session.setAttribute("total", total);
                        session.setAttribute("cart", cart);
                        session.setAttribute("checkin", checkin);
                        session.setAttribute("checkout", checkout);
                    }
                }

            }
        } catch (Exception e) {
            log("Exception at ChangeDateBookingServlet :" + e.getMessage());
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
