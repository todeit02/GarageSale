<?php
/**
 * Obtiene todas las ofertas de la base de datos
 */

require_once 'offer_crud.php';
require_once '../tags/tags_crud.php';

if ($_SERVER['REQUEST_METHOD'] == 'GET') {

    // Manejar petición GET
    $offers = OfferCrud::getAll();
	
	if($offers)
	{
		for($i = 0; $i < count($offers); $i++)
		{
			$tagsAddingOffer =& $offers[$i];
			$addingTagRows = TagsCrud::getByOfferId($tagsAddingOffer['id']);
			if($addingTagRows)
			{
				$addingTags = array_column($addingTagRows, "tag");
				$tagsAddingOffer['tags'] = $addingTags;
			}			
		}
	}

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

?>