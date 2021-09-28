<?php 
    $connection = mysqli_connect("10.50.239.148", "ding", "Ding1234!", "DingDong");
    mysqli_query($connection,'SET NAMES utf8');

    $userID = $_POST["userID"];
    $userPass = password_hash($_POST["userPass"], PASSWORD_DEFAULT);
    $userName = $_POST["userName"];
    $userAge = $_POST["userAge"];
    $userSex = $_POST["userSex"];
    $userHeight = $_POST["userHeight"];
    $userWeight = $_POST["userWeight"];
    $userBodyfat = 0.0;
    $userMusclemass = 0.0;
    $userBMR = 0;
    $userFoodpurpose = 'n';

    $statement = mysqli_prepare($con, "INSERT INTO USERS VALUES (?,?,?,?,?,?,?,?,?,?,?)");
    mysqli_stmt_bind_param($statement, "sssisddddic", $userID, $userPass, $userName, $userAge, $userSex, $userHeight, $userWeight, $userBodyfat, $userMusclemass, $userBMR, $userFoodpurpose);
    mysqli_stmt_execute($statement);


    $response = array();
    $response["success"] = true;
 
   
    echo json_encode($response);



?>