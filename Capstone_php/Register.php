<?php 
    $con = mysqli_connect("10.50.231.219", "ding", "Ding1234!", "DingDong");
    mysqli_query($con,'SET NAMES utf8');

    $userID = $_POST["userID"];
    $userPass = $_POST["userPass"];
    $userName = $_POST["userName"];
    $userAge = $_POST["userAge"];
    $userSex = $_POST["userSex"];
    $userHeight = $_POST["userHeight"];
    $userWeight = $_POST["userWeight"];

    $statement = mysqli_prepare($con, "INSERT INTO USERS VALUES (?,?,?,?,?,?,?)");
    mysqli_stmt_bind_param($statement, "sssisdd", $userID, $userPass, $userName, $userAge, $userSex, $userHeight, $userWeight);
    mysqli_stmt_execute($statement);


    $response = array();
    $response["success"] = true;
 
   
    echo json_encode($response);



?>