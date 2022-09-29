<?php
if ($_SERVER['REQUEST_METHOD']== 'GET'){
    require_once('conectarNuevoServicio.php');
    $placa=$_GET['placa'];
    
    $query = "SELECT 	certificado.cNumeroCat, 
                vehiculo.cPlaca_V, 
                certificado.dFechaEmisionCat,
                certificado.dFechaVigenciaInicio,
                certificado.dFechaVigenciaTermino,
                persona.cPerApePaterno,
                persona.cPerApeMaterno, 
                persona.cPerNombre,
                certificado.cEstadoCat
                FROM ventas
                INNER JOIN certificado on ventas.cNumeroCat = certificado.cNumeroCat 
                INNER JOIN vehiculo ON certificado.nIdVehiculo = vehiculo.nIdvehiculo
                INNER JOIN persona ON certificado.cPerCodigo = persona.cPerCodigo                        
                WHERE vehiculo.cPlaca_V = '$placa'AND certificado.cEstadoCat = '1' ";


    $resultado= $mysql -> query($query);
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