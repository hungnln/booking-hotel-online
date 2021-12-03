/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

import daos.HotelDAO;
import daos.RoomTypeDAO;
import daos.TypeDAO;
import dtos.BookingCartItem;
import dtos.Hotel;
import dtos.RoomType;
import dtos.Type;
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
public class CartServlet extends HttpServlet {

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
        List<BookingCartItem> cart = (List<BookingCartItem>) session.getAttribute("cart");
        boolean isAdmin = false;
        String url = URL.getCART();
        try {
            if (user != null) {
                if (user.getUserRole() == 2) {
                    url = URL.getINDEX_ADMIN_PAGE();
                    isAdmin = true;
                }
            }
            if (!isAdmin) {
                if (cart == null) {
                    request.setAttribute("msg", "No Room in cart");
                }
            }
        } catch (Exception e) {
            log("Exception at CartServlet :"+e.getMessage());
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
                String hotelID = request.getParameter("hotelID");
                if (hotelID.trim().isEmpty() || checkin.isEmpty() || checkout.isEmpty()) {
                    request.setAttribute("msg", "URL error");
                } else {
                    long total = 0;
                    List<BookingCartItem> list = new ArrayList<>();
                    HotelDAO hotelDAO = new HotelDAO();
                    Hotel hotel = hotelDAO.getHotelByID(hotelID);
                    int index = Integer.parseInt(request.getParameter("index"));
                    for (int i = 0; i < index; i++) {
                        String roomID = request.getParameter("roomID_" + i);
                        int quantity = -1;
                        quantity = Integer.parseInt(request.getParameter("quantity_" + i));
                        if (roomID.trim().isEmpty() || quantity < 0) {
                            request.setAttribute("msg", "URL error");
                            break;
                        } else {
                            RoomTypeDAO roomTypeDAO = new RoomTypeDAO();
                            RoomType roomType = roomTypeDAO.getRoomByIDWithCheckInAdnCheckOut(roomID, checkin, checkout);
                            if (roomType == null) {
                                request.setAttribute("msg", "Can't find Room");
                                break;
                            } else {
                                if (quantity == 0) {
                                    continue;
                                }
                                if (quantity <= roomType.getRoomActiveQuantity()) {
                                    BookingCartItem bookingCartItem = new BookingCartItem(hotel, roomType, quantity);
                                    list.add(bookingCartItem);
                                    long subtotal = (roomType.getRoomPrice() * quantity * Variable.getDaysWithCheckInAndCheckOut(checkin, checkout));
                                    total += subtotal;
                                } else {
                                    valid = false;
                                    request.setAttribute("msg", "Out of stock");
                                    break;
                                }
                            }
                        }
                    }
                    if (valid) {
                        if (list.isEmpty()) {
                            request.setAttribute("msg", "No booking room in cart");
                        } else {
                            session.setAttribute("cart", list);
                            session.setAttribute("total", total);
                            session.setAttribute("hotel", hotel);
                            session.setAttribute("checkin", checkin);
                            session.setAttribute("checkout", checkout);
                        }
                    } else {
                        TypeDAO typeDAO = new TypeDAO();
                        List<Type> types = typeDAO.getAllTypes();
                        request.setAttribute("types", types);
                    }
                }
            }
        } catch (Exception e) {
            log("Exception at CartServlet :"+e.getMessage());
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

    public static int isExistHotel(String hotelID, List<BookingCartItem> cart) {
        for (int i = 0; i < cart.size(); i++) {
            if (cart.get(i).getHotel().getHotelID().equalsIgnoreCase(hotelID)) {
                return i;
            }
        }
        return -1;
    }

    public static int isExistRoom(String roomID, List<BookingCartItem> cart) {
        for (int i = 0; i < cart.size(); i++) {
            if (cart.get(i).getRoomType().getRoomID().equalsIgnoreCase(roomID)) {
                return i;
            }
        }
        return -1;
    }
}
