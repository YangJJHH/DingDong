<?php
    $connection = mysqli_connect("10.50.239.148", "ding", "Ding1234!", "DingDong");
  
    $userID = $_POST["userID"];
    $userPass = $_POST["userPass"];
    
    $sql = "SELECT * FROM USERS WHERE userID = '{$userID}'";
    $result = mysqli_query($connection, $sql);

    $row = mysqli_fetch_array($result);
    $hashedPass = $row['userPass'];

    if( password_verify($userPass, $hashedPass) ) {
        $statement = mysqli_prepare($connection, "SELECT * FROM USERS WHERE userID = ? AND userPass = ?");
        mysqli_stmt_bind_param($statement, "ss", $userID, $userPass);
        mysqli_stmt_execute($statement);


        mysqli_stmt_store_result($statement);
        mysqli_stmt_bind_result($statement, $userID);

        $response = array();
        $response["success"] = false;

        while(mysqli_stmt_fetch($statement)) {
            $response["success"] = true;
            $response["userID"] = $userID;
        }

        echo json_encode($response);
    } else {
        $response = array();
        $response["success"] = false;

        echo json_encode($response);
    }

?>