	<?php
/**
 * Obtiene todas las metas de la base de datos
 */

require 'post_crud.php';

if ($_SERVER['REQUEST_METHOD'] == 'GET') {

    // Manejar petición GET
    $offers = OfferCrud::getAll();

    if ($offers) {

        $datos["estado"] = 1;
        $datos["offers"] = $offers;

        print json_encode($datos);
    } else {
        print json_encode(array(
            "estado" => 2,
            "mensaje" => "Ha ocurrido un error"
        ));
    }
}