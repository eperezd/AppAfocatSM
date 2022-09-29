<?php
    include 'conectarNuevoServicio.php';
    if ($_SERVER['REQUEST_METHOD']=='POST'){

        $placa=$_POST['placa'];
        $telefono=$_POST['telefono'];
        $numeroSiniestroApp['numeroSiniestroApp'];

        $sql= "INSERT INTO sini (placa,telefono,numeroSiniestroApp) VALUES ('$placa','$telefono','$numeroSiniestroApp')";
        
        $result = $mysql->query($sql);

        if ($result=== true)
        {
            echo "datos guardados"; 
        }else {echo "error";}


    }
?>