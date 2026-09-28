package com.fm.servlet;

import java.io.IOException;
import java.util.Hashtable;

import javax.naming.Context;
import javax.naming.NamingException;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;

import com.fm.model.LDAPUser;
import com.fm.util.LDAPUtil;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    /*
     * ============================================================
     * LOGIN
     * ============================================================
     */

    @Override
    protected void doPost(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        String username = request.getParameter("username");
        String password = request.getParameter("password");

        /*
         * Remove spaces around username.
         */
        if (username != null) {
            username = username.trim();
        }

        /*
         * Validate empty fields.
         */
        if (username == null || username.isEmpty()
                || password == null || password.isEmpty()) {

            request.setAttribute(
                    "error",
                    "Please enter username and password."
            );

            request.getRequestDispatcher(
                    "/WEB-INF/views/login.jsp"
            ).forward(request, response);

            return;
        }

        /*
         * Authenticate against LDAP.
         */
        LDAPUser user = LDAPUtil.authenticate(username, password);
        

        if (user != null) {

        	 /*
             * Create session.
             */
            HttpSession session = request.getSession(true);

            session.setAttribute("loggedIn", Boolean.TRUE);
            session.setAttribute("username", username);
            session.setAttribute("user", user);
            /*
             * Prevent session fixation.
             */
            request.changeSessionId();

            /*
             * Login successful.
             */
            response.sendRedirect(
                    request.getContextPath() + "/home"
            );

        } else {

            /*
             * Login failed.
             */
            request.setAttribute(
                    "error",
                    "Invalid username or password."
            );

            request.getRequestDispatcher(
                    "/WEB-INF/views/login.jsp"
            ).forward(request, response);
        }
    }

    

    /*
     * ============================================================
     * GET
     * ============================================================
     */

    @Override
    protected void doGet(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        response.sendRedirect(
                request.getContextPath() + "/"
        );
    }
}
