<?php
/**
 * Upload a photo into folder for offer id.
 */

if ($_SERVER['REQUEST_METHOD'] == 'POST')
{
	$succeeded = false;
	
    $body = json_decode(file_get_contents("php://input"), true);
	$offerId = $body['offerId'];
	$image = $body['image'];
	
	$offerPhotosPath = "../photo_storage/" . $offerId . '/';
	
	if(!file_exists($offerPhotosPath))
	{
		mkdir($offerPhotosPath, 0755, true);
	}
	
	$existentPhotos = glob($offerPhotosPath . "*");
	if ($existentPhotos) $photoName = count($existentPhotos);
	else $photoName = 0;
	
	$photoNameWithExtension = $photoName . ".jpg";
	$photoPath = $offerPhotosPath . $photoNameWithExtension;
	
	$photoFile = fopen($photoPath, "w");
	if($photoFile)
	{
		fwrite($photoFile, $body['image']);
		fclose($photoFile);
		$succeeded = true;
	}
	
	if ($succeeded) {
        print json_encode(
            array(
                'estado' => '1',
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
