<?php
/**
 * Actualiza una meta especificada por su identificador
 */

require 'offer_crud.php';

if ($_SERVER['REQUEST_METHOD'] == 'POST') {

    // Decodificando formato Json
    $body = json_decode(file_get_contents("php://input"));

    // Actualizar meta
    $retorno = OfferCrud::update(
        $body['name'],
        $body['description'],
        $body['price'],
        $body['sold'],
        $body['seller_username'],
        $body['id'],
        $body['state'],
        $body['activePeriod']);

    if ($retorno) {
        // Código de éxito
        print json_encode(
            array(
                'estado' => '1',
                'mensaje' => 'Actualización exitosa')
        );
    } else {
        // Código de falla
        print json_encode(
            array(
                'estado' => '2',
                'mensaje' => 'Actualización fallida')
        );
    }
}

?>