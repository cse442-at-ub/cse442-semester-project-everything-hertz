<? php
require "connection.php";
$ubit = $_GET['ubit'];
$result = mysqli_query($con,"SELECT SUM(points) AS total_points FROM events WHERE event_number IN 
							(SELECT event_number FROM attendance WHERE ubit='$ubit')");
$row = mysqli_fetch_array($result);
$data = $row[0];

if($data){
    echo $data;
}
?>