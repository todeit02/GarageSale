<?php


require_once ('person_crud.php');

if ($_SERVER['REQUEST_METHOD'] == 'GET') {

    if (isset($_GET['seller_username'])) {

        // Obtener parámetro id
        $parametro1 = $_GET['seller_username'];
        $parametro2 = $_GET['buyer_username'];


        // Tratar retorno
        $retorno = PersonCrud::getRankingById($parametro1, $parametro2);

        if ($retorno) {

            $datos["estado"] = "1";
            $datos["ranking"] = $retorno;
            // Enviar objeto json de la oferta
            print json_encode($datos);
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
?>