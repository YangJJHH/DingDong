<?php 
    $con = mysqli_connect("10.50.231.219", "root", "Root1234!", "DingDing", "3306");
    mysqli_query($con,'SET NAMES utf8');

    $userID = $_POST["userID"];
    $userPassword = $_POST["userPass"];
    $userName = $_POST["userName"];
    $userAge = $_POST["userAge"];
    $userHeight = $_POST["userHeight"];
    $userWeight = $_POST["userWeight"];

    $statement = mysqli_prepare($con, "INSERT INTO USERS VALUES (?,?,?,?,?,?)");
    mysqli_stmt_bind_param($statement, "sssiii", $userID, $userPass, $userName, $userAge, $userHeight, $userWeight);
    mysqli_stmt_execute($statement);


    $response = array();
    $response["success"] = true;
 
   
    echo json_encode($response);



?>