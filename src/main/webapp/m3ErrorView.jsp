<%@ page language="java"
    contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ taglib prefix="c"
    uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html>

<head>

    <meta charset="UTF-8">

    <title>M3 Error - View</title>

    <style>

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

        h3 {
            margin-top: 25px;
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

        .info-table {
            border-collapse: collapse;
            width: 100%;
        }

        .info-table td {
            padding: 8px 10px;
            border-bottom: 1px solid #ddd;
        }

        .label {
            font-weight: bold;
            width: 150px;
            background-color: #f8f9fa;
        }

        /* =========================================
           XML DISPLAY
           ========================================= */

        .xml-box {
            background-color: #1e1e1e;
            color: #f5f5f5;

            padding: 20px;

            border-radius: 6px;

            overflow: auto;

            font-family:
                Consolas,
                "Courier New",
                monospace;

            font-size: 13px;

            line-height: 1.6;

            max-height: 700px;

            white-space: pre;

            tab-size: 4;
        }

        .error-box {
            background-color: #f8d7da;
            color: #842029;

            border: 1px solid #f5c2c7;

            padding: 15px;

            border-radius: 5px;
        }

        .status {
            display: inline-block;

            padding: 4px 10px;

            border-radius: 4px;

            font-weight: bold;
        }

        .status-new {
            background-color: #fff3cd;
            color: #664d03;
        }

    </style>

</head>


<body>

<div class="container">


    <!-- ============================================== -->
    <!-- BACK -->
    <!-- ============================================== -->

    <a class="back-button"
       href="${pageContext.request.contextPath}/m3Error">

        ← Back to M3 Error

    </a>


    <!-- ============================================== -->
    <!-- TITLE -->
    <!-- ============================================== -->

    <h2>M3 Error - View</h2>


    <!-- ============================================== -->
    <!-- FILE INFORMATION -->
    <!-- ============================================== -->

    <table class="info-table">

        <tr>

            <td class="label">
                Filename
            </td>

            <td>
                <c:out value="${error.fileName}" />
            </td>

        </tr>


        <tr>

            <td class="label">
                File Path
            </td>

            <td>
                <c:out value="${error.filePath}" />
            </td>

        </tr>


        <tr>

            <td class="label">
                Detected Time
            </td>

            <td>
                <c:out value="${error.detectedTime}" />
            </td>

        </tr>


        <tr>

            <td class="label">
                Status
            </td>

            <td>

                <span class="status status-new">
                    <c:out value="${error.statusFlag}" />
                </span>

            </td>

        </tr>

    </table>


    <!-- ============================================== -->
    <!-- XML CONTENT -->
    <!-- ============================================== -->

    <h3>XML Content</h3>


    <c:choose>

        <c:when test="${not empty xmlContent}">

            <!--
                Hidden raw XML.
                c:out safely escapes the XML.
            -->

            <div id="xmlRaw" style="display:none;">
                <c:out value="${xmlContent}" />
            </div>


            <!--
                Formatted XML will be displayed here.
            -->

            <pre id="xmlFormatted"
                 class="xml-box"></pre>

        </c:when>


        <c:otherwise>

            <div class="error-box">

                Unable to load XML content.

            </div>

        </c:otherwise>

    </c:choose>


</div>


<script>

    // =====================================================
    // FORMAT XML
    // =====================================================

    function formatXml(xml) {

        var formatted = '';

        var reg = /(>)(<)(\/*)/g;

        xml = xml.replace(reg, '$1\r\n$2$3');

        var pad = 0;

        var lines = xml.split('\r\n');

        lines.forEach(function(line) {

            var indent = 0;

            if (line.match(/.+<\/\w[^>]*>$/)) {

                indent = 0;

            } else if (line.match(/^<\/\w/)) {

                if (pad !== 0) {
                    pad -= 1;
                }

            } else if (
                line.match(
                    /^<\w[^>]*[^\/]>.*$/
                )
            ) {

                indent = 1;

            } else {

                indent = 0;

            }


            var padding = '';

            for (
                var i = 0;
                i < pad;
                i++
            ) {

                padding += '    ';

            }


            formatted +=
                padding +
                line +
                '\r\n';


            pad += indent;

        });


        return formatted.trim();

    }


    // =====================================================
    // LOAD XML
    // =====================================================

    window.onload = function() {

        var raw =
            document.getElementById("xmlRaw");

        var formatted =
            document.getElementById("xmlFormatted");


        if (!raw || !formatted) {
            return;
        }


        var xml =
            raw.textContent ||
            raw.innerText;


        formatted.textContent =
            formatXml(xml);

    };

</script>


</body>

</html>