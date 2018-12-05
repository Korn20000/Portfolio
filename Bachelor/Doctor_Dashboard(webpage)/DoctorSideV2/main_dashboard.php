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
        <title>Main Dashboard</title>
        <link rel="stylesheet" type="text/css" href="src/styles/main.css" />
		<link rel="stylesheet" type="text/css" href="src/styles/datatables.min.css"/>
		<link rel="stylesheet" type="text/css" href="src/styles/buttons.dataTables.min.css"/>
		<script type="text/javascript" src="src/js/adapter.js"></script>
		<script type="text/javascript" src="src/js/model.js"></script>
		<script type="text/javascript" src="src/js/modelmanager.js"></script>
		<script type="text/javascript" src="src/js/view.js"></script>
		<script type="text/javascript" src="src/js/controller.js"></script>
		<script type="text/javascript" src="src/js/jquery.js"></script>
		<script type="text/javascript" src="src/js/babel.js"></script>
		<script type="text/javascript" src="src/js/datatables.min.js"></script>
		<script type="text/javascript" src="src/js/dataTables.buttons.min.js"></script>
		<script type="text/javascript" src="src/js/buttons.colVis.min.js"></script>
		<script type="text/javascript" src="src/js/buttons.print.min.js"></script>
		<script type="text/javascript" src="src/js/buttons.html5.min.js"></script>
		<script type="text/javascript" src="src/js/jszip.min.js"></script>
		<script type="text/javascript" src="src/js/buttons.html5.min.js"></script>
		<script type="text/javascript" src="src/js/pdfmake.min.js"></script>
		<script type="text/javascript" src="src/js/vfs_fonts.js"></script>
		<script type="text/javascript" src="src/js/Chart.min.js"></script>
    </head>
    <body>
		<div class = "main_panel">
			<a class="button" id="outButton" href="src/php/logout.php">Logout</a>
			<a class="button" id="reloadButton" href="#" onclick="window.location.reload();">Reload</a>
			<div id="loadingList">
				<p class="loadinText">Loading patients, please wait...</p>
				<img class="loadingAnim" src="src/styles/imgs/loading.gif" alt="LOADING !">
			</div>
		</div>
		<div id = "main_table">
			<table id="patientsTable" class="display" width="100%"></table>
		</div>
	</body>
	<script>
    var adapter = adapter();
    var view = view();
	var model = model();
	var modelManager = modelmanager(model, adapter);
    var controller = controller(view, modelManager);
		window.onload = controller.getPatientsFromModelManager();
	</script>
    <?php else : ?>
    <head>
        <meta charset="UTF-8">
        <title>Redirecting</title>
        <link rel="stylesheet" href="src/styles/redirect.css" />
		<meta http-equiv="refresh" content="5;url=./index.php" />
    </head>
    <body>
		<div class = "main">
			<div class = "wrapper">
				<p class="loggedAsText">Access denied !<br>Please log in first !</p>
				<p class="redirectText">Redirecting to Log in page in 5 seconds...</p>
				<img class="loadingAnim" src="src/styles/imgs/loading.gif" alt="Please Wait...">
			</div>
		</div>
	</body>
    <?php endif; ?>
</html>