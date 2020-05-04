<?php
$dbname= "cse442_542_2020_spring_teamk_db";
$username = "njceccar";
$password = "50234356";
$servername = "tethys.cse.buffalo.edu:3306";

// Create connection
$conn = new mysqli($servername, $username, $password, $dbname);
// Check connection

$response = array();
if ($conn->connect_error) {
	$response["success"] = 0;
    $response['data'] = "Connection failed";
    echo json_encode($response);
    die("Connection failed: " . $conn->connect_error);
}

// Prepare
$stmt  = $conn->prepare("INSERT INTO events (name, organizer, category, date_and_time, location, points, description) VALUES (?,?,?,?,?,?,?)");

// Bind parameters
$stmt->bind_param('sssssib', $name, $organizer, $category, $date_and_time, $location, $points, $desc);

// Define parameters
$name = "test event";
$organizer = "test organizer";
$category = "test category";
$date_and_time = "2020-04-15 23:11:00";
$location = "test location";
$points = 0;
$desc = "test descrpition";

// Execute, succeed, and close
$stmt->execute();
echo "success";
$stmt->close();
$conn->close();
?>
