<?php 
    $connection = mysqli_connect("10.50.239.148", "ding", "Ding1234!", "DingDong");
    mysqli_query($connection,'SET NAMES utf8');

    $userID = $_POST["userID"];

    $sql = "SELECT userHeight,userWeight,userBodyfat,userMusclemass,userBMR,userFoodpurpose FROM USERS WHERE userID = '{$userID}'";
    $result = mysqli_query($connection, $sql);

    $row = mysqli_fetch_array($result);
   
    echo json_encode($row);

?>