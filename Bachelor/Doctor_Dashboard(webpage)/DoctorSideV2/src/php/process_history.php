<?php
include_once 'db_connect.php';
include_once 'functions.php';

sec_session_start();
if (isset($_POST['CPRNo']) && $_POST['actionId'] == 'disp_pat_history_json') {
	$cpr = $_POST['CPRNo'];
	echo json_encode(get_patients_history($mysqli_patients, $cpr));
}
else {
	echo 'Invalid Request';
}