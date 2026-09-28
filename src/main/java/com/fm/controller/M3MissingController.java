package com.fm.controller;


import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

import com.fm.dao.FileMonitoringDao;
import com.fm.model.M3Missing;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/m3Missing")
public class M3MissingController extends HttpServlet {

    private static final long serialVersionUID = 1L;

    private FileMonitoringDao dao = new FileMonitoringDao();


    // =========================================================
    // GET
    // =========================================================

    @Override
    protected void doGet(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        try {

            String action = request.getParameter("action");
            String remark =
                    request.getParameter("remark");



            // =================================================
            // RETRY
            // =================================================

            if ("retry".equalsIgnoreCase(action)) {

                String idString =
                        request.getParameter("id");


                if (idString == null
                        || idString.trim().isEmpty()) {

                    response.sendError(
                            HttpServletResponse.SC_BAD_REQUEST,
                            "Missing record ID."
                    );

                    return;
                }


                int id;

                try {

                    id = Integer.parseInt(idString);
                    
                    String wms_orderno = dao.getWmsOrderNoById(id);
                    
                    
                    Path sourceFolder =
                            Paths.get("\\\\10.1.2.4\\c$\\T3_SuccessXML\\SentDORO");

                    Path destinationFolder =
                            Paths.get("\\\\10.1.2.4\\c$\\T3_M3_XML\\DORO");

                    String wmsOrderNo =
                            dao.getWmsOrderNoById(id);

                    try (DirectoryStream<Path> files =
                            Files.newDirectoryStream(
                                    sourceFolder,
                                    "*" + wmsOrderNo + "*.xml")) {

                        Path matchedFile = null;

                        for (Path file : files) {

                            if (matchedFile != null) {
                                throw new Exception(
                                    "Multiple XML files found for WMS Order No: "
                                    + wmsOrderNo
                                );
                            }

                            matchedFile = file;
                        }

                        if (matchedFile == null) {
                            throw new Exception(
                                "No XML file found for WMS Order No: "
                                + wmsOrderNo
                            );
                        }

                        Files.createDirectories(destinationFolder);

                        Path destination =
                                destinationFolder.resolve(
                                        matchedFile.getFileName()
                                );

                        Files.move(
                                matchedFile,
                                destination,
                                StandardCopyOption.REPLACE_EXISTING
                        );
                    }
                    
                    //update the status_fg to N and default the remark

                    if (null == remark) {
                    	remark = "retry from file monitoring system.";
                    }
                    dao.updateM3FileMissingStatus(id, "N", remark);

                } catch (NumberFormatException e) {

                    response.sendError(
                            HttpServletResponse.SC_BAD_REQUEST,
                            "Invalid record ID."
                    );

                    return;
                }


                // =============================================
                // Retry the record
                // =============================================

//                dao.retry(id);


                // =============================================
                // Return to listing
                // =============================================

                response.sendRedirect(
                        request.getContextPath()
                        + "/m3Missing?retry=success"
                );

                return;
            }


            // =================================================
            // LIST
            // =================================================

            List<M3Missing> m3Missing =
                    dao.getM3MissingFileList();


            request.setAttribute(
                    "m3Missing",
                    m3Missing
            );


            // =================================================
            // FORWARD TO JSP
            // =================================================

            request.getRequestDispatcher(
                    "/m3MissingList.jsp"
            ).forward(
                    request,
                    response
            );


        } catch (Exception e) {

            throw new ServletException(
                    "Unable to process M3 Missing.",
                    e
            );
        }
    }


    // =========================================================
    // POST
    // =========================================================

    @Override
    protected void doPost(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        doGet(request, response);
    }

}
