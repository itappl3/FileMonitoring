<%@ page language="java"
    contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ taglib prefix="c"
    uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>

<html>

<head>

    <meta charset="UTF-8">

    <title>M3 Missing</title>

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
            width: 100px;
            text-align: center;
            white-space: nowrap;
        }

        .action form {
            margin: 0;
        }

        .retry-button {
            display: inline-block;
            padding: 5px 12px;
            background-color: #0d6efd;
            color: white;
            text-decoration: none;
            border: none;
            border-radius: 4px;
            font-size: 13px;
            cursor: pointer;
        }

        .retry-button:hover {
            background-color: #0b5ed7;
        }

        .no-record {
            text-align: center;
            padding: 20px;
            color: #777;
        }

    </style>

</head>


<body>

<div class="container">

    <a class="back-button"
       href="${pageContext.request.contextPath}/home">
        ← Back
    </a>


    <h2>M3 Missing</h2>


    <table>

        <thead>

            <tr>

                <th>WMS_OrderNo</th>

                <th>DATE</th>

                <th>ITEM_CODE</th>

                <th>QTY</th>

                <th>STORER_CODE</th>

                <th>Action</th>

            </tr>

        </thead>


        <tbody>

            <c:choose>

                <c:when test="${not empty m3Missing}">

                    <c:forEach
                        var="missing"
                        items="${m3Missing}">

                        <tr>

                            <td>
                                <c:out value="${missing.wmsOrderNo}" />
                            </td>

                            <td>
                                <c:out value="${missing.date}" />
                            </td>

                            <td>
                                <c:out value="${missing.itemCode}" />
                            </td>

                            <td>
                                <c:out value="${missing.qty}" />
                            </td>

                            <td>
                                <c:out value="${missing.storerCode}" />
                            </td>

                            <td class="action">

                                <form
                                    method="post"
                                    action="${pageContext.request.contextPath}/m3Missing"
                                    onsubmit="return retryM3Missing(this);">

                                    <input
                                        type="hidden"
                                        name="action"
                                        value="retry">

                                    <input
                                        type="hidden"
                                        name="id"
                                        value="${missing.id}">

                                    <input
                                        type="hidden"
                                        name="remark"
                                        class="remark-input">

                                    <button
                                        type="submit"
                                        class="retry-button">
                                        Retry
                                    </button>

                                </form>

                            </td>

                        </tr>

                    </c:forEach>

                </c:when>

                <c:otherwise>

                    <tr>

                        <td
                            colspan="6"
                            class="no-record">

                            No M3 Missing records found.

                        </td>

                    </tr>

                </c:otherwise>

            </c:choose>

        </tbody>

    </table>

</div>


<script>

function retryM3Missing(form) {

    var remark = prompt(
        "Enter remark (optional):",
        ""
    );


    // Cancel = do not submit
    if (remark === null) {

        return false;
    }


    // Put the entered remark into the hidden field
    var remarkInput =
        form.querySelector(".remark-input");


    remarkInput.value = remark.trim();


    // Submit the form
    return true;
}

</script>


</body>

</html>