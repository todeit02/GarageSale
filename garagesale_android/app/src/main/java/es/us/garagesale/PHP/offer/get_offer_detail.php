<?php
/**
 * Obtiene el detalle de una oferta especificado por
 * su identificador "id"
 */

require_once ('offer_crud.php');

if ($_SERVER['REQUEST_METHOD'] == 'GET') {

    if (isset($_GET['id'])) {

        // Obtener parámetro id
        $parametro = $_GET['id'];


        // Tratar retorno
        $retorno = getById($parametro);


        if ($retorno) {

            $offer["estado"] = "1";
            $offer["offer"] = $retorno;
            // Enviar objeto json de la oferta
            print json_encode($offer);
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