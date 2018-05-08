<?php
/**
 * Insertar una nueva oferta en la base de datos
 */

require 'person_crud.php';
require '../card/card_crud.php';

if ($_SERVER['REQUEST_METHOD'] == 'POST') {

    $body = json_decode(file_get_contents("php://input"), true);

	$isUsernameAlreadyTaken = (PersonCrud::getById($body['username']) != false);
	
	$succeeded = ($isUsernameAlreadyTaken == false) && CardCrud::insert(
        $body['cardNum'],
        $body['expDate'],
        $body['ccv'],
        $body['bank']
	);	
	
    $succeeded = $succeeded && PersonCrud::insert(
        $body['username'],
        $body['password'],
        $body['name'],
        $body['email'],
        $body['birthDate'],
        $body['nationality'],
        $body['card_id'],
        $body['reputation']
	);

    if ($succeeded) {
        // Código de éxito
        print json_encode(
            array(
                'estado' => '1',
                'mensaje' => 'Creación exitosa',
				'isUsernameAlreadyTaken' => $isUsernameAlreadyTaken)
        );
    } else {
        // Código de falla
        print json_encode(
            array(
                'estado' => '2',
                'mensaje' => 'Creación fallida',
				'isUsernameAlreadyTaken' => $isUsernameAlreadyTaken)
        );
    }
}
