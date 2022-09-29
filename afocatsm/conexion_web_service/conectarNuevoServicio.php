<?php

$mysql = new mysqli(

    /*$servidor = "tu ip de hosting";
    $basededatos = "tus datos";
    $usuario = "tus datos";
    $contrasena = "tus datos";  */
    "tu ip de hosting","tus datos" , "tus datos","tus datos"
    
);
if ($mysql->connect_error){
    die ("la conexion fallo".$mysql ->connect_error);
}

?>