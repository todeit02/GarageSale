<?php
/**
 * Actualiza una meta especificada por su identificador
 */

require 'offer_crud.php';


if ($_SERVER['REQUEST_METHOD'] == 'POST') {


    $paymentMethod = $_POST["paymentMethod"];
    $hasContactedSeller = $_POST["hasContactedSeller"];
    $offer_id = $_POST["offer_id"];

   // $j = array('name' =>$name);
    //echo json_encode($j);

    // Actualizar meta
    $retorno = OfferCrud::updatePurchase(
        $paymentMethod,
        $hasContactedSeller,
        $offer_id);


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