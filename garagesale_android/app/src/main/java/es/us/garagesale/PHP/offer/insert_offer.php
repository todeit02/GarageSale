<?php
/**
 * Insert new offer into the database
 */

require_once 'offer_crud.php';

if ($_SERVER['REQUEST_METHOD'] == 'POST') {

    $body = json_decode(file_get_contents("php://input"), true);

    $succeeded = OfferCrud::insert(
        $body['name'],
        $body['description'],
        $body['price'],
        $body['seller_username'],
        $body['state'],
        $body['activePeriod']);
		
		$offer = OfferCrud::getByAttributes($body['name'], $body['seller_username']);
		if($offer != NULL) $offerId = $offer['id'];
		else $succeeded = false;

    if ($succeeded) {
        print json_encode(
            array(
                'estado' => '1',
				'offerId' => $offerId,
                'mensaje' => 'Creación exitosa')
        );
    } else {
        print json_encode(
            array(
                'estado' => '2',
                'mensaje' => 'Creación fallida')
        );
    }
}
