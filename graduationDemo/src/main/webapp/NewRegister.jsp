<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="en">

	<head>
	
		<!-- Basic -->
    	<meta charset="UTF-8" />

		<title>注册页面</title>

		<!-- Mobile Metas -->
	    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no" />
		
		<!-- Import google fonts -->
        <link href='http://fonts.useso.com/css?family=Titillium+Web' rel='stylesheet' type='text/css'>
        
		<!-- Favicon and touch icons -->
		<link rel="shortcut icon" href="assets/ico/favicon.ico" type="image/x-icon" />
		<link rel="apple-touch-icon" href="assets/ico/apple-touch-icon.png" />
		<link rel="apple-touch-icon" sizes="57x57" href="assets/ico/apple-touch-icon-57x57.png" />
		<link rel="apple-touch-icon" sizes="72x72" href="assets/ico/apple-touch-icon-72x72.png" />
		<link rel="apple-touch-icon" sizes="76x76" href="assets/ico/apple-touch-icon-76x76.png" />
		<link rel="apple-touch-icon" sizes="114x114" href="assets/ico/apple-touch-icon-114x114.png" />
		<link rel="apple-touch-icon" sizes="120x120" href="assets/ico/apple-touch-icon-120x120.png" />
		<link rel="apple-touch-icon" sizes="144x144" href="assets/ico/apple-touch-icon-144x144.png" />
		<link rel="apple-touch-icon" sizes="152x152" href="assets/ico/apple-touch-icon-152x152.png" />
		
	    <!-- start: CSS file-->
		
		<!-- Vendor CSS-->
		<link href="assets/vendor/bootstrap/css/bootstrap.min.css" rel="stylesheet" />
		<link href="assets/vendor/skycons/css/skycons.css" rel="stylesheet" />
		<link href="assets/vendor/font-awesome/css/font-awesome.min.css" rel="stylesheet" />
		
		<!-- Plugins CSS-->
		<link href="assets/plugins/bootkit/css/bootkit.css" rel="stylesheet" />	
		
		<!-- Theme CSS -->
		<link href="assets/css/jquery.mmenu.css" rel="stylesheet" />
		
		<!-- Page CSS -->		
		<link href="assets/css/style.css" rel="stylesheet" />
		<link href="assets/css/add-ons.min.css" rel="stylesheet" />
		
		<style>
			footer {
				display: none;
			}
		</style>
		
		<!-- end: CSS file-->	
	    
		
		<!-- Head Libs -->
		<script src="assets/plugins/modernizr/js/modernizr.js"></script>
		
		<!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
		<!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
		<!--[if lt IE 9]>
			<script src="https://oss.maxcdn.com/html5shiv/3.7.2/html5shiv.min.js"></script>
			<script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
		<![endif]-->		
		
	</head>

	<body>
		<!-- Start: Content -->
		<div class="container-fluid content">
			<div class="row">
				<!-- Main Page -->
				<div id="content" class="col-sm-12 full">
					<div class="row">
						<div class="register-box">
							<div class="panel">
								<div class="panel-body">										
									<div class="header bk-margin-bottom-20 text-center">										
										<img src="assets/img/logo.png" class="img-responsive" alt="" />
										<h4>New Account</h4>
									</div>									
									<form class="form-horizontal register" action="/admin/register" method="post">									
										<div class="bk-padding-left-20 bk-padding-right-20">
											<div class="form-group">
												<label>Name</label>
												<input name="name" type="text" class="form-control" placeholder="用户名 " id="name"/>
											</div>
											<div class="form-group">
												<label>phone</label>
												<input name="phone" type="phone" class="form-control" placeholder="手机号" id="phone"/>
											</div>											
											<div class="form-group">
												<div class="row">
													<div class="col-sm-6">
														<label>Password</label>
														<input name="password" type="password" class="form-control bk-margin-bottom-10" placeholder="Password" id="password"/>
													</div>
													<div class="col-sm-6">
														<label>Password Confirmation</label>
														<input name="passwordConfirm" type="password" class="form-control bk-margin-bottom-10" placeholder="Password Confirm" id="passwordConfirm"/>
													</div>
												</div>
											</div>
											<div class="row bk-margin-top-20 bk-margin-bottom-10">
												<div class="col-sm-8">
													<div class="checkbox-custom checkbox-default">
														<input id="AgreeTerms" name="agreeterms" type="checkbox"/>
														<label for="AgreeTerms">I agree with <a href="#">terms of use</a></label>
													</div>
												</div>
												<div class="col-sm-4 text-right">
													<button type="submit" class="btn btn-primary hidden-xs">Register</button>
													<button type="submit" class="btn btn-primary btn-block btn-lg visible-xs bk-margin-top-20">Register</button>
												</div>
											</div>											
											<div class="text-with-hr">
												<span>or use your another account</span>
											</div>											
											<div class="text-center bk-margin-top-10 bk-margin-bottom-30">
												<a class="btn btn-facebook bk-margin-bottom-15 bk-margin-5">Connect with <i class="fa fa-facebook"></i></a>
												<a class="btn btn-twitter bk-margin-bottom-15 bk-margin-5">Connect with <i class="fa fa-twitter"></i></a>
											</div>
											<p class="text-center">Already have an account? <a href="${pageContext.request.contextPath}/NewLogin.jsp">Log In!</a>										
										</div>	
									</form>									
								</div>
							</div>							
							<p class="text-center text-muted">Fire <i class="fa fa-coffee"></i> Collect from <a href="http://www.cssmoban.com/" title="网页模板" target="_blank">网页模板</a> - More Templates <a href="http://www.cssmoban.com/" target="_blank" title="模板之家">模板之家</a></p>	
						</div>
					</div>
				</div>	
				<!-- End Main Page -->	
			</div>
		</div><!--/container-->
		
		
		<!-- start: JavaScript-->
		
		<!-- Vendor JS-->				
		<script src="assets/vendor/js/jquery.min.js"></script>
		<script src="assets/vendor/js/jquery-2.1.1.min.js"></script>
		<script src="assets/vendor/js/jquery-migrate-1.2.1.min.js"></script>
		<script src="assets/vendor/bootstrap/js/bootstrap.min.js"></script>
		<script src="assets/vendor/skycons/js/skycons.js"></script>	
		
		<!-- Plugins JS-->
		<script src="assets/plugins/bootkit/js/bootkit.js"></script>
		
		<!-- Theme JS -->		
		<script src="assets/js/jquery.mmenu.min.js"></script>
		<script src="assets/js/core.min.js"></script>
		
		<!-- Pages JS -->
		<script src="assets/js/pages/page-register.js"></script>
		
		<!-- end: JavaScript-->
		
	</body>
	
</html>