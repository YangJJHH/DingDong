<?php
    $con = mysqli_connect("10.50.231.219", "root", "Root1234!", "DingDing", "3306");
  
    $userID = $_POST["userID"];
    $userPassword = $_POST["userPass"];
    
    $statement = mysqli_prepare($con, "SELECT * FROM USERS WHERE userID = ? AND userPass = ?");
    mysqli_stmt_bind_param($statement, "ss", $userID, $userPass);
    mysqli_stmt_execute($statement);


    mysqli_stmt_store_result($statement);
    mysqli_stmt_bind_result($statement, $userID, $userPass, $userName, $userAge, $userHeight, $userWeight);

    $response = array();
    $response["success"] = false;
 
    while(mysqli_stmt_fetch($statement)) {
        $response["success"] = true;
        $response["userID"] = $userID;
        $response["userPas"] = $userPass;
        $response["userName"] = $userName;
        $response["userAge"] = $userAge;    
        $response["userHeight"] = $userHeight;
        $response["userWeight"] = $userWeight;
    }

    echo json_encode($response);



?>