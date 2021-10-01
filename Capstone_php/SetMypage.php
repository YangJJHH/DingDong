<?php 
    $connection = mysqli_connect("10.50.239.148", "ding", "Ding1234!", "DingDong");
    mysqli_query($connection,'SET NAMES utf8');

    $userID = $_POST['userID'];
    $userHeight = $_POST["userHeight"];
    $userWeight = $_POST["userWeight"];
    $userBodyfat = $_POST["userBodyfat"];
    $userMusclemass = $_POST["userMusclemass"];
    $userBMR = $_POST["userBMR"];
    $userFoodpurpose = $_POST["userFoodpurpose"];

    $statement = mysqli_prepare($connection, "UPDATE USERS SET userHeight = ?, userWeight = ?, userBodyfat = ?, userMusclemass = ?, userBMR = ?, userFoodpurpose = ? WHERE userID = ?");
    mysqli_stmt_bind_param($statement, "ddddiss", $userHeight, $userWeight, $userBodyfat, $userMusclemass, $userBMR, $userFoodpurpose, $userID);
    mysqli_stmt_execute($statement);

    $response = array();
    $response["success"] = true;

    while(mysqli_stmt_fetch($statement)) {
        $response["success"] = false;
    }
 
   
    echo json_encode($response);



?>