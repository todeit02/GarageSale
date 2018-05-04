<?php
/**
 * Insertar una nueva oferta en la base de datos
 */

require 'person_crud.php';

if ($_SERVER['REQUEST_METHOD'] == 'POST') {

    // Decodificando formato Json
    $body = json_decode(file_get_contents("php://input"), true);

    // Insertar oferta
    $retorno = person_crud::insert(
        $body['username'],
        $body['password'],
        $body['realName'],
        $body['email'],
        $body['birthDate']),
        $body['nationality']),
        $body['card_id']),
        $body['reputation']);

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
