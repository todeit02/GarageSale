<?php
/**
 * Obtiene el detalle de un offer especificado por
 * su identificador "idCode"
 */

require 'post_crud.php';

if ($_SERVER['REQUEST_METHOD'] == 'GET') {

    if (isset($_GET['idCode'])) {

        // Obtener parámetro idMeta
        $parametro = $_GET['idCode'];

        // Tratar retorno
        $retorno = Meta::getById($parametro);


        if ($retorno) {

            $offer["estado"] = "1";
            $offer["offer"] = $retorno;
            // Enviar objeto json de la meta
            print json_encode($meta);
        } else {
            // Enviar respuesta de error general
            print json_encode(
                array(
                    'estado' => '2',
                    'mensaje' => 'No se obtuvo el registro'
                )
            );
        }

    } else {
        // Enviar respuesta de error
        print json_encode(
            array(
                'estado' => '3',
                'mensaje' => 'Se necesita un identificador'
            )
        );
    }
}