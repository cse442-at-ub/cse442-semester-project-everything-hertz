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

$sql = "SELECT name, date_and_time AS date, location AS loc, description AS `desc` FROM events HAVING date_and_time > '" . date("Y-m-d h:m:s") . "' ORDER BY date_and_time";
$result = $conn->query($sql);

$data = array();
$count = 0;
if ($result->num_rows > 0) {
    // output data of each row
    $response["success"] = 1;
    while($row = $result->fetch_assoc()) {
        
        $data[$count] = $row;
        $count = $count + 1;
    }
    $response["data"] = $data;
    echo json_encode($response);
} else {
	$response["success"] = 0;
    $response['data'] = "No results";
    echo json_encode($response);
}
$conn->close();
?>
