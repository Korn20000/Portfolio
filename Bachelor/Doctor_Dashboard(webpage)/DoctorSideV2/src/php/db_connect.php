<?php
include_once 'psl-config.php';

$mysqli_doctors = new mysqli(HOST, USER_DOC, PASSWORD, DATABASE_DOC);
$mysqli_patients = new mysqli(HOST, USER_PAT, PASSWORD, DATABASE_PAT);