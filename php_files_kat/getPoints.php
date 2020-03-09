<?php
$dbname= "cse442_542_2020_spring_teamk_db";
$username = "ksemenov";
$password = "50219589";
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

$sql = "SELECT SUM(points) AS total_points FROM events WHERE event_number IN (SELECT event_number FROM attendance WHERE ubit='user1')";
$result = $conn->query($sql);

if ($result->num_rows > 0) {
    // output data of each row
    while($row = $result->fetch_assoc()) {
        $response["success"] = 1;
        $response['data'] = $row;
        echo json_encode($response);
    }
} else {
	$response["success"] = 0;
    $response['data'] = "No results";
    echo json_encode($response);
}
$conn->close();
?>