<?php
/**
 * Obtiene todas los interesados de una oferta de la base de datos
 */

require 'offer_crud.php';

if ($_SERVER['REQUEST_METHOD'] == 'GET') {

 if (isset($_GET['offer_id'])) {

        // Obtener parámetro id
        $parametro = $_GET['offer_id'];

    // Manejar petición GET
    $interested = OfferCrud::getInterested($parametro);

    if ($interested) {

        $datos["estado"] = 1;
        $datos["interested"] = $interested;

        print json_encode($datos);
    } else {
        print json_encode(array(
            "estado" => 2,
            "mensaje" => "Ha ocurrido un error"
        ));
    }
 }

 else {
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