<? php
$db_name= "cse442_542_2020_spring_teamk_db";
$mysql_username = "ksemenov";
$mysql_password = "50219589";
$server_name = "tethys.cse.buffalo.edu";
$connect = mysqli_connect($server_name,$mysql_username,$mysql_password);
if($conn){
	echo "connection success";
}else{
	echo "connection_failed";
}
?>