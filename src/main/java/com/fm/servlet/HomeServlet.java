package com.fm.servlet;

import java.io.IOException;

import com.fm.dao.FileMonitoringDao;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/home")
public class HomeServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    FileMonitoringDao dao = new FileMonitoringDao();
    @Override
    protected void doGet(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        // Prevent browser from caching the home page
        response.setHeader(
                "Cache-Control",
                "no-cache, no-store, must-revalidate"
        );
        response.setHeader(
                "Pragma",
                "no-cache"
        );
        response.setDateHeader(
                "Expires",
                0
        );

        HttpSession session = request.getSession(false);

        // No session or not logged in
        if (session == null
                || !Boolean.TRUE.equals(
                        session.getAttribute("loggedIn"))) {

            response.sendRedirect(
                    request.getContextPath() + "/"
            );

            return;
        }

        int totalM3MissingFile = 0;
        int totalM3ErrorFile = 0;

        // TODO: database query

        totalM3MissingFile =
                dao.getTotalM3MissingFile();
        
        request.setAttribute(
				"totalM3MissingFile",
                totalM3MissingFile
        );

        totalM3ErrorFile = dao.getTotalM3ErrorFile();
        request.setAttribute(
                "totalM3ErrorFile",
                totalM3ErrorFile
        );

        
        // Logged in
        request.getRequestDispatcher(
                "/WEB-INF/views/home.jsp"
        ).forward(request, response);
    }
}