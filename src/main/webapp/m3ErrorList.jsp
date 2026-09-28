<%@ page language="java"
    contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ taglib prefix="c"
    uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>

<html>

<head>

    <meta charset="UTF-8">

    <title>M3 Error</title>

    <style>

        /* =========================================
           LOADING OVERLAY
           ========================================= */

        #loadingOverlay {
            position: fixed;
            top: 0;
            left: 0;
            width: 100%;
            height: 100%;
            background-color: rgba(255, 255, 255, 0.95);

            display: flex;
            justify-content: center;
            align-items: center;

            z-index: 99999;
        }

        .loadingContent {
            text-align: center;
        }

        .loadingSpinner {
            width: 80px;
            height: 80px;

            border: 8px solid #e0e0e0;
            border-top: 8px solid #0d6efd;

            border-radius: 50%;

            animation: loadingSpin 0.9s linear infinite;

            margin: 0 auto;
        }

        .loadingText {
            margin-top: 20px;

            font-size: 24px;
            font-weight: bold;

            color: #333;
        }

        @keyframes loadingSpin {
            0% {
                transform: rotate(0deg);
            }

            100% {
                transform: rotate(360deg);
            }
        }


        /* =========================================
           NORMAL PAGE
           ========================================= */

        body {
            font-family: Arial, sans-serif;
            margin: 30px;
            background-color: #f5f6f8;
        }

        .container {
            background-color: white;
            padding: 25px;
            border-radius: 8px;
        }

        h2 {
            margin-top: 0;
            margin-bottom: 20px;
        }

        .back-button {
            display: inline-block;
            padding: 8px 15px;
            background-color: #6c757d;
            color: white;
            text-decoration: none;
            border-radius: 5px;
            margin-bottom: 20px;
        }

        .back-button:hover {
            background-color: #5a6268;
        }

        table {
            width: 100%;
            border-collapse: collapse;
            background-color: white;
        }

        th {
            background-color: #e9ecef;
            text-align: left;
            padding: 10px;
            border: 1px solid #ccc;
            font-weight: bold;
        }

        td {
            padding: 10px;
            border: 1px solid #ccc;
        }

        tr:hover {
            background-color: #f8f9fa;
        }

        .action {
            white-space: nowrap;
            width: 120px;
        }

        .action a {
            margin-right: 10px;
            color: #0d6efd;
            text-decoration: none;
        }

        .action a:hover {
            text-decoration: underline;
        }

        .no-record {
            text-align: center;
            padding: 20px;
            color: #777;
        }

    </style>

</head>


<body>


<!-- =========================================
     LOADING OVERLAY
     ========================================= -->

<div id="loadingOverlay">

    <div class="loadingContent">

        <div class="loadingSpinner"></div>

        <div class="loadingText">
            Loading...
        </div>

    </div>

</div>


<!-- =========================================
     MAIN CONTENT
     ========================================= -->

<div class="container">

    <a class="back-button"
       href="${pageContext.request.contextPath}/home">
        ← Back
    </a>


    <h2>M3 Error</h2>


    <table>

        <thead>

            <tr>

                <th>filename</th>

                <th>filepath</th>

                <th>detectedtime</th>

                <th>action</th>

            </tr>

        </thead>


        <tbody>

            <c:choose>

                <c:when test="${not empty m3Errors}">

                    <c:forEach
                        var="error"
                        items="${m3Errors}">

                        <tr>

                            <td>
                                <c:out value="${error.fileName}" />
                            </td>


                            <td>
                                <c:out value="${error.filePath}" />
                            </td>


                            <td>
                                <c:out value="${error.detectedTime}" />
                            </td>


                            <td class="action">

                                <a
                                    href="${pageContext.request.contextPath}/m3Error?action=view&id=${error.id}">
                                    view
                                </a>


                                <a
                                    href="${pageContext.request.contextPath}/m3Error?action=retry&id=${error.id}"
                                    onclick="return confirm('Retry this M3 error notification?');">
                                    retry
                                </a>


                                <a
                                    href="${pageContext.request.contextPath}/m3Error?action=ignore&id=${error.id}"
                                    onclick="return confirm('Ignore this M3 error notification?');">
                                    ignore
                                </a>

                            </td>

                        </tr>

                    </c:forEach>

                </c:when>


                <c:otherwise>

                    <tr>

                        <td
                            colspan="4"
                            class="no-record">

                            No M3 error files found.

                        </td>

                    </tr>

                </c:otherwise>

            </c:choose>

        </tbody>

    </table>

</div>


<!-- =========================================
     HIDE LOADING AFTER PAGE LOAD
     ========================================= -->

<script>

    window.addEventListener("load", function () {

        var loadingOverlay =
            document.getElementById("loadingOverlay");

        loadingOverlay.style.display = "none";

    });

</script>


</body>

</html>