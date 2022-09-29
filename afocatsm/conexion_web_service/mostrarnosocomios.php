<?php
    include 'conectarNuevoServicio.php';
    if ($_SERVER['REQUEST_METHOD']=='GET'){

        $sql= "SELECT*FROM nosocomios";
        
        $resultado= $mysql -> query($sql);
        if ($mysql -> affected_rows > 0){
            while($row = $resultado -> fetch_assoc()){
                $array[]= $row;

            }   
            echo json_encode($array);

        }else {
        echo "nanay";
        }
        $resultado->close();
        $mysql -> close();


    }
?>