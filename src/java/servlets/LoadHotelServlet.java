/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

import daos.HotelDAO;
import daos.RoomTypeDAO;
import daos.TypeDAO;
import dtos.Hotel;
import dtos.RoomType;
import dtos.Type;
import dtos.User;
import java.io.IOException;
import java.io.PrintWriter;
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
public class LoadHotelServlet extends HttpServlet {

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
            out.println("<title>Servlet LoadHotelServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet LoadHotelServlet at " + request.getContextPath() + "</h1>");
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
        String url = URL.getCHOOSE_ROOM_PAGE();
        HotelDAO hotelDAO = new HotelDAO();
        TypeDAO typeDAO = new TypeDAO();
        try {
            boolean isAdmin = false;
            HttpSession session = request.getSession();
            User user = (User) session.getAttribute("userdata");
            if (user != null) {
                if (user.getUserRole() == Variable.getADMIN_ROLE()) {
                    url = URL.getINDEX_ADMIN_PAGE();
                    isAdmin = true;
                }
            }
            if (!isAdmin) {
                List<RoomType> roomTypes = new ArrayList<>();
                RoomTypeDAO roomTypeDAO = new RoomTypeDAO();
                String hotelID, checkin, checkout;
                String change = request.getParameter("change");
                Hotel hotel;
                if (change != null) {
                    checkin = (String) session.getAttribute("checkin");
                    checkout = (String) session.getAttribute("checkout");
                    hotel = (Hotel) session.getAttribute("hotel");
                    hotelID = hotel.getHotelID();
                } else {
                    checkin = request.getParameter("checkin");
                    checkout = request.getParameter("checkout");
                    hotelID = request.getParameter("hotelID");
                    hotel = hotelDAO.getHotelByID(hotelID);
                }

                String typeID = request.getParameter("typeID");
                Type type = typeDAO.getTypeByID(typeID);
                if (hotel == null) {
                    request.setAttribute("msg", "Can't find Hotel");
                    url = URL.getINDEX_SERVLET();
                } else {
                    if (checkin.trim().isEmpty() & checkout.trim().isEmpty()) {
                        if (type == null) {
                            roomTypes = roomTypeDAO.getAllRoomByHotel(hotelID);
                        } else {
                            roomTypes = roomTypeDAO.getAllRoomByHotelByType(hotelID, typeID);
                        }
                    } else {
                        if (!Variable.compareDatewithCurrenDate(checkin)) {
                            request.setAttribute("msg", "Can't set checkin");                            
                        } else {
                            if (type == null) {
                                roomTypes = roomTypeDAO.getAllRoomByHotelWithCheckInAndCheckOut(hotelID, checkin, checkout);
                            } else {
                                roomTypes = roomTypeDAO.getAllRoomByTypeByHotelWithCheckInAndCheckOut(typeID, hotelID, checkin, checkout);
                            }
                        }

                    }
                    request.setAttribute("rooms", roomTypes);
                    request.setAttribute("hotel", hotel);
                    request.setAttribute("area", hotel.getArea());
                }
            }
            List<Type> types = typeDAO.getAllTypes();
            request.setAttribute("types", types);

        } catch (Exception e) {
            log("Exception at LoadHotelServlet :" + e.getMessage());
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
