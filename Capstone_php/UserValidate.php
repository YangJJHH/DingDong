<?php
    $connection = mysqli_connect("10.50.239.148", "ding", "Ding1234!", "DingDong");
  
    $userID = $_POST["userID"];

    
    $statement = mysqli_prepare($connection, "SELECT userID FROM USERS WHERE userID = ?");
    mysqli_stmt_bind_param($statement, "s", $userID);
    mysqli_stmt_execute($statement);

    mysqli_stmt_store_result($statement);
    mysqli_stmt_bind_result($statement, $userID);

    $response = array();
    $response["success"] = true;

    while(mysqli_stmt_fetch($statement)) {
        $response["success"] = false;
    }

    echo json_encode($response);
    
?>