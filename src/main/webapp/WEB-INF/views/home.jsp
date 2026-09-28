<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%> <!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
			<title>File Monitoring System - Home</title>
			<style> * { box-sizing: border-box; } body { margin: 0; font-family: Arial, Helvetica, sans-serif; background: #f4f6f9; color: #333; } /* ========================= TOP BAR ========================= */ .topbar { height: 60px; background: #1976d2; color: white; display: flex; align-items: center; justify-content: space-between; padding: 0 25px; } .logo { font-size: 20px; font-weight: bold; } .user-area { display: flex; align-items: center; gap: 20px; font-size: 14px; } .display-name { font-weight: normal; } .logout-button { text-decoration: none; color: white; background: rgba(0, 0, 0, 0.18); padding: 8px 15px; border-radius: 5px; } .logout-button:hover { background: rgba(0, 0, 0, 0.30); } /* ========================= MAIN LAYOUT ========================= */ .layout { display: flex; min-height: calc(100vh - 60px); } /* ========================= SIDEBAR ========================= */ .sidebar { width: 230px; background: #ffffff; border-right: 1px solid #ddd; padding-top: 20px; } .menu-title { padding: 10px 20px; font-size: 12px; color: #999; text-transform: uppercase; } .menu-item { display: block; padding: 13px 20px; color: #444; text-decoration: none; font-size: 14px; } .menu-item:hover { background: #f0f4f8; color: #1976d2; } .menu-item.active { background: #e3f2fd; color: #1976d2; font-weight: bold; border-right: 3px solid #1976d2; } /* ========================= CONTENT ========================= */ .content { flex: 1; padding: 30px; } .page-title { margin-bottom: 25px; } .page-title h1 { margin: 0; font-size: 26px; } .page-title p { margin-top: 7px; color: #777; } /* ========================= DASHBOARD CARDS ========================= */ .cards { display: grid; grid-template-columns: repeat(4, 1fr); gap: 20px; } .card { background: white; border-radius: 8px; padding: 22px; box-shadow: 0 2px 8px rgba(0, 0, 0, 0.06); } .card-title { color: #777; font-size: 13px; margin-bottom: 10px; } .card-value { font-size: 28px; font-weight: bold; } /* ========================= WELCOME PANEL ========================= */ .panel { margin-top: 25px; background: white; border-radius: 8px; padding: 25px; box-shadow: 0 2px 8px rgba(0, 0, 0, 0.06); } .panel h2 { margin-top: 0; font-size: 20px; } /* ========================= USER INFORMATION ========================= */ .user-info { margin-top: 25px; background: white; border-radius: 8px; padding: 25px; box-shadow: 0 2px 8px rgba(0, 0, 0, 0.06); } .user-info h2 { margin-top: 0; font-size: 20px; } .user-row { display: flex; padding: 10px 0; border-bottom: 1px solid #eee; } .user-label { width: 150px; color: #777; font-weight: bold; } .user-value { color: #333; } /* ========================= RESPONSIVE ========================= */ @media (max-width: 900px) { .cards { grid-template-columns: repeat(2, 1fr); } .sidebar { width: 180px; } } @media (max-width: 600px) { .sidebar { display: none; } .content { padding: 20px; } .cards { grid-template-columns: 1fr; } .user-area { gap: 8px; } } </style>
		</head>
		<body>
			<!-- ========================================================= TOP BAR ========================================================= -->
			<div class="topbar">
				<div class="logo"> File Monitoring System </div>
				<div class="user-area">
					<span class="display-name"> Welcome, ${sessionScope.user.displayName} </span>
					<a class="logout-button" href="${pageContext.request.contextPath}/logout"> Logout </a>
				</div>
			</div>
			<!-- ========================================================= MAIN LAYOUT ========================================================= -->
			<div class="layout">
				<!-- ===================================================== SIDEBAR ===================================================== -->
				<div class="sidebar">
					<div class="menu-title"> Main Menu </div>
					<a href="${pageContext.request.contextPath}/home" class="menu-item active"> 🏠 Dashboard </a>
					<a href="${pageContext.request.contextPath}/m3Missing" class="menu-item"> 📋 M3 Missing File </a>
					<a href="${pageContext.request.contextPath}/m3Error" class="menu-item"> 📋 M3 Error File </a>
<!-- 					<a href="#" class="menu-item"> 🤖 Robot </a> -->
<!-- 					<a href="#" class="menu-item"> 📊 Reports </a> -->
<!-- 					<div class="menu-title"> System </div> -->
<!-- 					<a href="#" class="menu-item"> ⚙ Settings </a> -->
				</div>
				<!-- ===================================================== MAIN CONTENT ===================================================== -->
				<div class="content">
					<!-- PAGE TITLE -->
					<div class="page-title">
						<h1> Dashboard </h1>
						<p> Welcome back, ${sessionScope.user.displayName}. </p>
					</div>
					<!-- ================================================= DASHBOARD CARDS ================================================= -->
					<div class="cards">
						<div class="card">
							<div class="card-title"> Total M3 Missing File</div>
							<div class="card-value">
						        ${totalM3MissingFile}
						    </div>
						</div>
						<div class="card">
							<div class="card-title"> Total M3 Error File </div>
							<div class="card-value">
						        ${totalM3ErrorFile}
						    </div>
						</div>
<!-- 						<div class="card"> -->
<!-- 							<div class="card-title"> Running Robots </div> -->
<!-- 							<div class="card-value"> 0 </div> -->
<!-- 						</div> -->
<!-- 						<div class="card"> -->
<!-- 							<div class="card-title"> Completed Tasks </div> -->
<!-- 							<div class="card-value"> 0 </div> -->
<!-- 						</div> -->
					</div>
					<!-- ================================================= WELCOME PANEL ================================================= -->
					<div class="panel">
						<h2> Welcome to File Monitoring System </h2>
						<p> You have successfully logged in. </p>
						<p> Use the menu on the left to access the system modules. </p>
					</div>
					<!-- ================================================= USER INFORMATION ================================================= -->
					<div class="user-info">
						<h2> Logged-in User </h2>
						<div class="user-row">
							<div class="user-label"> Display Name </div>
							<div class="user-value"> ${sessionScope.user.displayName} </div>
						</div>
						<div class="user-row">
							<div class="user-label"> Username </div>
							<div class="user-value"> ${sessionScope.user.username} </div>
						</div>
<!-- 						<div class="user-row"> -->
<!-- 							<div class="user-label"> Email </div> -->
<%-- 							<div class="user-value"> ${sessionScope.user.department} </div> --%>
<!-- 						</div> -->
					</div>
				</div>
			</div>
		</body>
	</html>