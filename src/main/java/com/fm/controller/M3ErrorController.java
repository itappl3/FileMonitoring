package com.fm.controller;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

import com.fm.dao.FileMonitoringDao;
import com.fm.model.M3Error;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/m3Error")
public class M3ErrorController extends HttpServlet {

    private static final long serialVersionUID = 1L;

    private FileMonitoringDao dao = new FileMonitoringDao();


    @Override
    protected void doGet(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        try {

            String action = request.getParameter("action");
            String idString = request.getParameter("id");


            // =================================================
            // VIEW
            // =================================================

            if ("view".equalsIgnoreCase(action)
                    && idString != null) {

                int id = Integer.parseInt(idString);

                M3Error error = dao.getM3ErrorById(id);

                if (error == null) {

                    response.sendError(
                        HttpServletResponse.SC_NOT_FOUND,
                        "M3 error record not found."
                    );

                    return;
                }


                String filePath = error.getFilePath();

                Path path = Paths.get(filePath);


                if (!Files.exists(path)) {

                    String alternativePath = "\\\\10.1.2.4\\C$\\T3_ErrorXML\\M3ErrorDORO\\check\\" + error.getFileName();

                    path = Paths.get(alternativePath);
                    
                    if (!Files.exists(path)) {
                    	response.sendError(
                                HttpServletResponse.SC_NOT_FOUND,
                                "XML file not found: " + filePath
                            );

                            return;
                    }
                    
                }


                String xml = Files.readString(
                    path,
                    StandardCharsets.UTF_8
                );


                request.setAttribute("error", error);
                request.setAttribute("xmlContent", xml);


                request.getRequestDispatcher(
                    "/m3ErrorView.jsp"
                ).forward(request, response);

                return;
            }
            
	          if ("ignore".equalsIgnoreCase(action)
	        		  && idString != null) {
	
			      int id = Integer.parseInt(idString);
			
			      M3Error error = dao.getM3ErrorById(id);
			
			      if (error == null) {
			
			          response.sendError(
			              HttpServletResponse.SC_NOT_FOUND,
			              "M3 error record not found."
			          );
			
			          return;
			      }
			
			
			      /*
			       * For now, retry means making the record
			       * available for processing again.
			       *
			       * We will connect this to the actual M3
			       * processing logic later.
			       */
			
			      dao.updateM3ErrorStatus(id, "N");
			
			
			      response.sendRedirect(
			          request.getContextPath()
			          + "/m3Error?retry=success"
			      );
			
			      return;
			  }

	       // =================================================
	       // RETRY
	       // =================================================

	       if ("retry".equalsIgnoreCase(action)) {

	           if (idString == null || idString.trim().isEmpty()) {

	               response.sendError(
	                   HttpServletResponse.SC_BAD_REQUEST,
	                   "Missing record ID."
	               );

	               return;
	           }

	           try {

	               int id = Integer.parseInt(idString);

	               M3Error error = dao.getM3ErrorById(id);

	               if (error == null) {

	                   response.sendError(
	                       HttpServletResponse.SC_NOT_FOUND,
	                       "M3 error record not found."
	                   );

	                   return;
	               }

	               Path sourceFolder =
	                   Paths.get("\\\\10.1.2.4\\c$\\T3_SuccessXML\\SentDORO");

	               Path destinationFolder =
	                   Paths.get("\\\\10.1.2.4\\c$\\T3_M3_XML\\DORO");
	               
	               String fileName = error.getFileName();

	               String key = fileName.substring(
	                   fileName.indexOf("_") + 1,
	                   fileName.indexOf("_", fileName.indexOf("_") + 1)
	               );

	               
	               System.out.println("Source folder: " + sourceFolder);
	               System.out.println("Looking for: " + key);
	               System.out.println("Folder exists: " + Files.exists(sourceFolder));
	               System.out.println("Is directory: " + Files.isDirectory(sourceFolder));

	               
	               try (DirectoryStream<Path> files =
                           Files.newDirectoryStream(
                                   sourceFolder,
                                   "*" + key + "*.xml")) {

	                   Path matchedFile = null;

	                   for (Path file : files) {

	                       if (matchedFile != null) {

	                           throw new Exception(
	                               "Multiple XML files found for file name: "
	                               + key
	                           );
	                       }

	                       matchedFile = file;
	                   }


	                   if (matchedFile == null) {

	                       throw new Exception(
	                           "No XML file found for file name: "
	                           + key
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

	               // Optional: update status after successful retry
	               // dao.updateM3ErrorStatus(id, "N");

	               response.sendRedirect(
	                   request.getContextPath()
	                   + "/m3Error?retry=success"
	               );
	               
	               dao.updateM3ErrorStatus(id, "N");

	               return;

	           } catch (NumberFormatException e) {

	               response.sendError(
	                   HttpServletResponse.SC_BAD_REQUEST,
	                   "Invalid record ID."
	               );

	               return;
	           }
	       }   // <-- THIS closes the retry IF


            // =================================================
            // LIST
            // =================================================

            List<M3Error> m3Errors =
                dao.getM3Errors();

            request.setAttribute(
                "m3Errors",
                m3Errors
            );


            request.getRequestDispatcher(
                "/m3ErrorList.jsp"
            ).forward(request, response);


        }
        catch (Exception e) {

            throw new ServletException(
                "Unable to process M3 Error.",
                e
            );
        }
    }
}