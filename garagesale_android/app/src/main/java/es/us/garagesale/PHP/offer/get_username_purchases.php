<?php
/**
 * Obtiene todas las ofertas de la base de datos
 */

require 'offer_crud.php';

if ($_SERVER['REQUEST_METHOD'] == 'GET') {

    if (isset($_GET['buyer_username'])) {

            // Obtener parámetro id
            $parametro = $_GET['buyer_username'];

        // Manejar petición GET
        $purchases = OfferCrud::getUsernamePurchases($parametro);

        if ($purchases) {

            $datos["estado"] = 1;
            $datos["purchases"] = $purchases;

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