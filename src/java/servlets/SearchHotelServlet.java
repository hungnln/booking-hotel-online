/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

import daos.AreaDAO;
import daos.HotelDAO;
import dtos.Area;
import dtos.Hotel;
import dtos.User;
import java.io.IOException;
import java.io.PrintWriter;
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
public class SearchHotelServlet extends HttpServlet {

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
            out.println("<title>Servlet SearchHotelServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet SearchHotelServlet at " + request.getContextPath() + "</h1>");
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
        String url = URL.getCHOOSE_HOTEL_PAGE();
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("userdata");
        boolean isAdmin = false;
        try {
            if (user != null) {
                if (user.getUserRole() == Variable.getADMIN_ROLE()) {
                    isAdmin = true;
                    url = URL.getINDEX_ADMIN_PAGE();
                }
            }
            if (!isAdmin) {
                String areaID = request.getParameter("areaID");
                String checkin = request.getParameter("checkin");
                String checkout = request.getParameter("checkout");
                String hotelName = request.getParameter("hotelName");
                if (areaID != null && checkin != null && checkout != null) {
                    if (areaID.isEmpty() || checkin.isEmpty() || checkout.isEmpty()) {
                        if (areaID.isEmpty()) {
                            request.setAttribute("msg", "Enter Area");
                            url = URL.getINDEX_SERVLET();
                        } else {
                            //get hotel by area which are availabel
                            if (hotelName == null) {
                                HotelDAO hotelDAO = new HotelDAO();
                                List<Hotel> hotels = hotelDAO.getAllHotelsByArea(areaID);
                                request.setAttribute("hotels", hotels);
                            } else {
                                if (hotelName.trim().isEmpty()) {
                                    HotelDAO hotelDAO = new HotelDAO();
                                    List<Hotel> hotels = hotelDAO.getAllHotelsByArea(areaID);
                                    request.setAttribute("hotels", hotels);
                                } else {
                                    HotelDAO hotelDAO = new HotelDAO();
                                    List<Hotel> hotels = hotelDAO.getAllHotelsByAreaByHotelName(areaID, hotelName);
                                    request.setAttribute("hotels", hotels);
                                }
                            }
                            request.setAttribute("msg", "Search done");
                        }
                    } else {
                        if (!Variable.compareDatewithCurrenDate(checkin)) {
                            request.setAttribute("msg", "Can't set checkin");
                        } else {
                            //get hotel by area and checkin check out which are available               
                            if (hotelName == null) {
                                HotelDAO hotelDAO = new HotelDAO();
                                List<Hotel> hotels = hotelDAO.getAllHotelAvailableByArea(areaID, checkin, checkout);
                                request.setAttribute("hotels", hotels);
                            } else {
                                if (hotelName.trim().isEmpty()) {
                                    HotelDAO hotelDAO = new HotelDAO();
                                    List<Hotel> hotels = hotelDAO.getAllHotelAvailableByArea(areaID, checkin, checkout);
                                    request.setAttribute("hotels", hotels);
                                } else {
                                    HotelDAO hotelDAO = new HotelDAO();
                                    List<Hotel> hotels = hotelDAO.getAllHotelAvailableByAreaByName(areaID, hotelName, checkin, checkout);
                                    request.setAttribute("hotels", hotels);
                                }
                            }
                            request.setAttribute("msg", "Search done");
                        }
                    }

                }
                AreaDAO areaDAO = new AreaDAO();
                List<Area> areas = areaDAO.getAllAreas();
                request.setAttribute("areas", areas);
            }

        } catch (Exception e) {
            log("Exception at SearchHotelServlet :" + e.getMessage());
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
