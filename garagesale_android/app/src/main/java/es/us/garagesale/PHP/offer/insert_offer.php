<?php
/**
 * Insertar una nueva oferta en la base de datos
 */

require 'offer_crud.php';

if ($_SERVER['REQUEST_METHOD'] == 'POST') {

    // Decodificando formato Json
    $body = json_decode(file_get_contents("php://input"), true);

    // Insertar oferta
    $retorno = offer_crud::insert(
        $body['realName'],
        $body['desription'],
        $body['price'],
        $body['publishDate'],
        $body['sold']),
         $body['seller']),
          $body['id']);

    if ($retorno) {
        // Código de éxito
        print json_encode(
            array(
                'estado' => '1',
                'mensaje' => 'Creación exitosa')
        );
    } else {
        // Código de falla
        print json_encode(
            array(
                'estado' => '2',
                'mensaje' => 'Creación fallida')
        );
    }
}
