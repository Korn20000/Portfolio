<?php
include_once 'db_connect.php';
include_once 'functions.php';

sec_session_start();
if (isset($_POST['CPRNo']) && isset($_POST['level']) && $_POST['actionId'] == 'save_measurement') {
	$cpr = $_POST['CPRNo'];
	$level = $_POST['level'];
	echo json_encode(save_measurement_db($mysqli_patients, $cpr, $level));
}
else {
	echo 'Invalid Request';
}