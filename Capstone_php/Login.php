<?php
    $con = mysqli_connect("10.50.231.219", "ding", "Ding1234!", "DingDong");
  
    $userID = $_POST["userID"];
    $userPass = $_POST["userPass"];
    
    $statement = mysqli_prepare($con, "SELECT * FROM USERS WHERE userID = ? AND userPass = ?");
    mysqli_stmt_bind_param($statement, "ss", $userID, $userPass);
    mysqli_stmt_execute($statement);


    mysqli_stmt_store_result($statement);
    mysqli_stmt_bind_result($statement, $userID, $userPass, $userName, $userAge, $userSex, $userHeight, $userWeight);

    $response = array();
    $response["success"] = false;
 
    while(mysqli_stmt_fetch($statement)) {
        $response["success"] = true;
        $response["userID"] = $userID;
        $response["userPass"] = $userPass;
        $response["userName"] = $userName;
        $response["userAge"] = $userAge;
        $response["userSex"] = $userSex;    
        $response["userHeight"] = $userHeight;
        $response["userWeight"] = $userWeight;
    }

    echo json_encode($response);



?>