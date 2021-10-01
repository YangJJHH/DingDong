<?php 
    $connection = mysqli_connect("10.50.239.148", "ding", "Ding1234!", "DingDong");
    mysqli_query($connection,'SET NAMES utf8');

    $userID = $_POST["userID"];

    $statement = mysqli_prepare($connection, "SELECT userHeight, userWeight, userBodyfat, userMusclemass, userBMR, userFoodpurpose FROM USERS WHERE userID = ?");
    mysqli_stmt_bind_param($statement, "s", $userID);
    mysqli_stmt_execute($statement);

    mysqli_stmt_store_result($statement);
    mysqli_stmt_bind_result($statement, $userHeight, $userWeight, $userBodyfat, $userMusclemass, $userBMR, $userFoodpurpose);

    $row = array();

    while(mysqli_stmt_fetch($statement)) {
        $row["userHeight"] = $userHeight;
        $row["userWeight"] = $userWeight;
        $row["userBodyfat"] = $userBodyfat;
        $row["userMusclemass"] = $userMusclemass;
        $row["userBMR"] = $userBMR;
        $row["userFoodpurpose"] = $userFoodpurpose;
        
    }
   
    echo json_encode($row);

?>