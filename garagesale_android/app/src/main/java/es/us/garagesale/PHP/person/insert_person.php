<?php

require_once 'person_crud.php';
require_once '../card/card_crud.php';

if ($_SERVER['REQUEST_METHOD'] == 'POST') {

    $body = json_decode(file_get_contents("php://input"), true);
	
	error_log(print_r($body, TRUE));

	$isUsernameAlreadyTaken = (PersonCrud::getById($body['username']) != false);
	
	$succeeded = ($isUsernameAlreadyTaken == false) && CardCrud::insert(
        $body['cardNum'],
        $body['expDate'],
        $body['ccv']
	);	
	
	if($succeeded)
	{
		$card = CardCrud::getByCardNum($body['cardNum']);
		if($card != NULL) $card_id = $card['id'];
		else $succeeded = false;
	}
	
    $succeeded = $succeeded && PersonCrud::insert(
        $body['username'],
        $body['password'],
        $body['realName'],
        $body['email'],
        $body['birthDate'],
        $body['nationality'],
        $card_id,
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
