<?php
/**
 * Insert new offer into the database
 */

require_once 'offer_crud.php';
require_once '../tags/tags_crud.php';

if ($_SERVER['REQUEST_METHOD'] == 'POST') {

	error_log(file_get_contents("php://input"));
    $body = json_decode(file_get_contents("php://input"), true);

    $succeeded = OfferCrud::insert(
        $body['name'],
        $body['description'],
        $body['price'],
        $body['seller_username'],
        $body['state'],
        $body['activePeriod'],
        $body['latitude'],
        $body['longitude']);
		
	$offer = OfferCrud::getByAttributes($body['name'], $body['seller_username']);
	if($offer != NULL) $offerId = $offer['id'];
	else $succeeded = false;
	
	foreach($body['tags'] as $insertingTag)
	{
		error_log("Inserting " . $insertingTag);
		$succeeded = $succeeded && TagsCrud::insert($offerId, $insertingTag);
	}
	error_log("Inserting tags complete. Success: " . $succeeded);

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
