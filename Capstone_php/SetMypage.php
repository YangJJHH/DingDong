<?php 
    $connection = mysqli_connect("10.50.239.148", "ding", "Ding1234!", "DingDong");
    mysqli_query($connection,'SET NAMES utf8');

   
    $userHeight = $_POST["userHeight"];
    $userWeight = $_POST["userWeight"];
    $userBodyfat = $_POST["userBodyfat"];
    $userMusclemass = $_POST["userMusclemass"];
    $userBMR = $_POST["userBMR"];
    $userFoodpurpose = $_POST["userFoodpurpose"];

    $statement = mysqli_prepare($con, "UPDATE INTO USERS VALUES (?,?,?,?,?,?)");
    mysqli_stmt_bind_param($statement, "ddddis", $userHeight, $userWeight, $userBodyfat, $userMusclemass, $userBMR,$userFoodpurpose);
    mysqli_stmt_execute($statement);


    $response = array();
    $response["success"] = true;
 
   
    echo json_encode($response);



?>