<?php
include_once 'src/php/db_connect.php';
include_once 'src/php/functions.php';

sec_session_start();
?>
<!DOCTYPE html>
<html>
	<?php if (login_check($mysqli_doctors) == true) : ?>
    <head>
        <meta charset="UTF-8">
        <title>Redirecting</title>
        <link rel="stylesheet" href="src/styles/redirect.css" />
		<meta http-equiv="refresh" content="5;url=./main_dashboard.php" />
    </head>
    <body>
		<div class = "main">
			<div class = "wrapper">
				<p class="loggedAsText">Welcome, <?php echo htmlentities($_SESSION['username']); ?> !</p>
				<p class="redirectText">Redirecting to Main Dashboard in 5 seconds...</p>
				<img class="loadingAnim" src="src/styles/imgs/loading.gif" alt="Please Wait...">
			</div>
		</div>
	</body>
        <?php else : ?>
			<head>
				<meta charset="UTF-8">
				<title>Doctor Dashboard: Log In</title>
				<link rel="stylesheet" href="src/styles/login.css" />
				<script type="text/JavaScript" src="src/js/sha512.js"></script> 
				<script type="text/JavaScript" src="src/js/forms.js"></script> 
			</head>
			<body>
				<img class="headerImg" src="src/styles/imgs/Header.png" alt="Glucose Meter Dashboard">
				<div class="loginForm">
					<form action="src/php/process_login.php" method="post" name="login_form">                      
						<p class="labels">Email: </p><input type="text" name="email" />
						<p class="labels">Password: </p><input type="password" name="password" id="password"/>
						<?php
							$status = filter_input(INPUT_GET, 'status', $filter = FILTER_SANITIZE_STRING);
							if ($status == 'off') : ?>
								<p class="status">You have been logged off !</p>
						<?php elseif ($status == 'bad_pwd') : ?>
								<p class="status">Incorrect password !</p>
						<?php elseif ($status == 'no_email') : ?>
								<p class="status">User does not exist !</p>
						<?php elseif ($status == 'locked') : ?>
								<p class="status">Your account has been locked! Try again later or contact administrator.</p>
						<?php endif; ?>
								<input type="button" value="Login" onclick="formhash(this.form, this.form.password);" /> 
					</form>
				</div>
			</body>
    <?php endif; ?>
</html>