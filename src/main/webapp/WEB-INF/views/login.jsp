<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>File Monitoring System - Login</title>

    <style>
        * {
            box-sizing: border-box;
        }

        body {
            margin: 0;
            font-family: Arial, Helvetica, sans-serif;
            background: #f4f6f9;
            height: 100vh;
            display: flex;
            align-items: center;
            justify-content: center;
        }

        .login-container {
            width: 400px;
            background: #ffffff;
            border-radius: 10px;
            box-shadow: 0 8px 30px rgba(0, 0, 0, 0.12);
            overflow: hidden;
        }

        .login-header {
            background: #1976d2;
            color: white;
            padding: 30px;
            text-align: center;
        }

        .login-header h1 {
            margin: 0;
            font-size: 26px;
        }

        .login-header p {
            margin: 8px 0 0;
            opacity: 0.9;
            font-size: 14px;
        }

        .login-body {
            padding: 35px;
        }

        .form-group {
            margin-bottom: 20px;
        }

        .form-group label {
            display: block;
            margin-bottom: 7px;
            font-size: 14px;
            font-weight: bold;
            color: #333;
        }

        .form-group input {
            width: 100%;
            height: 45px;
            padding: 0 12px;
            border: 1px solid #d0d5da;
            border-radius: 5px;
            font-size: 15px;
            outline: none;
        }

        .form-group input:focus {
            border-color: #1976d2;
            box-shadow: 0 0 0 2px rgba(25, 118, 210, 0.12);
        }

        .login-button {
            width: 100%;
            height: 45px;
            border: none;
            border-radius: 5px;
            background: #1976d2;
            color: white;
            font-size: 15px;
            font-weight: bold;
            cursor: pointer;
        }

        .login-button:hover {
            background: #1565c0;
        }

        .error-message {
            background: #ffebee;
            color: #c62828;
            padding: 10px;
            margin-bottom: 20px;
            border-radius: 5px;
            font-size: 14px;
            text-align: center;
        }

        .login-footer {
            text-align: center;
            padding: 15px;
            color: #888;
            font-size: 12px;
            border-top: 1px solid #eee;
        }
    </style>
</head>

<body>

<div class="login-container">

    <div class="login-header">
        <h1>File Monitoring System</h1>
    </div>

    <div class="login-body">

        <% if (request.getAttribute("error") != null) { %>
            <div class="error-message">
                ${error}
            </div>
        <% } %>

        <form method="post"
              action="${pageContext.request.contextPath}/login">

            <div class="form-group">
                <label for="username">Username</label>

                <input type="text"
                       id="username"
                       name="username"
                       placeholder="Enter your username"
                       autocomplete="username"
                       required
                       autofocus>
            </div>

            <div class="form-group">
                <label for="password">Password</label>

                <input type="password"
                       id="password"
                       name="password"
                       placeholder="Enter your password"
                       autocomplete="current-password"
                       required>
            </div>

            <button type="submit" class="login-button">
                Login
            </button>

        </form>

    </div>

    <div class="login-footer">
        ACK System &copy; 2026
    </div>

</div>

</body>
</html>