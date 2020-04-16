<?php
$dbname= "cse442_542_2020_spring_teamk_db";
$username = "njceccar";
$password = "50234356";
$servername = "tethys.cse.buffalo.edu:3306";
$ubit = $_POST["user_name"];
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

$stmt  = $conn->prepare("SELECT user_type FROM user_info WHERE ubit = ?");
if ($stmt->bind_param('s', $ubit) &&
    $stmt->execute() &&
    $stmt -> store_result() &&
    $stmt -> bind_result($result)) {

    while ($stmt -> fetch()) {
        // $response["success"] = 1;
        // $response['data'] = $result;
        echo json_encode($result);
    }
} else {
	// $response["success"] = 0;
    $response['data'] = "No results";
    echo json_encode($response);
}
$stmt->close();
$conn->close();
?>
