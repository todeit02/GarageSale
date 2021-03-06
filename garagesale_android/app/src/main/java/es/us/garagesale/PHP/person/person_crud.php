<?php

require_once '../Database.php';

class PersonCrud
{
    function __construct()
    {
    }

    public static function getById($username)
    {
        // Consulta de la meta
        $consulta = "SELECT * FROM persons WHERE username = ?";

        try {
            // Preparar sentencia
            $comando = Database::getInstance()->getDb()->prepare($consulta);
            // Ejecutar sentencia preparada
            $comando->execute(array($username));
            // Capturar primera fila del resultado
            $row = $comando->fetch(PDO::FETCH_ASSOC);
            return $row;

        } catch (PDOException $e) {
            // Aquí puedes clasificar el error dependiendo de la excepción
            // para presentarlo en la respuesta Json
            return -1;
        }
    }

    public static function insertRanking(
                $seller_username,
                $buyer_username,
                $value,
                $offer_id
            )
    {
           // Sentencia INSERT
                       $comando = "INSERT INTO ranking ( " .
                           "seller_username," .
                           " buyer_username," .
                           " value," .
                           " offer_id)" .
                           " VALUES( ?,?,?,?)";

                       // Preparar la sentencia
                       $sentencia = Database::getInstance()->getDb()->prepare($comando);

                       return $sentencia->execute(
                           array(
                               $seller_username,
                               $buyer_username,
                               $value,
                               $offer_id
                           )
                       );

    }



    public static function getRankingById($seller_username, $buyer_username, $offer_id)
    {

           $consulta = "SELECT * FROM ranking WHERE seller_username = " . $seller_username. " AND buyer_username = " . $buyer_username. "AND offer_id = " . $offer_id;

       //    . " AND buyer_username = " . $buyer_username

            try {
                // Preparar sentencia
                $comando = Database::getInstance()->getDb()->prepare($consulta);
                // Ejecutar sentencia preparada
                $comando->execute(array($seller_username, $buyer_username, $offer_id));
                // Capturar primera fila del resultado
                 $row = $comando->fetch(PDO::FETCH_ASSOC);
                 return $row;

            } catch (PDOException $e) {
                // Aquí puedes clasificar el error dependiendo de la excepción
                // para presentarlo en la respuesta Json
                return false;
            }

    }

        public static function getUserRanking($seller_username)
        {
               $consulta = "SELECT AVG(value) as sum FROM ranking WHERE seller_username = " . $seller_username;

                try {
                    // Preparar sentencia
                    $comando = Database::getInstance()->getDb()->prepare($consulta);
                    // Ejecutar sentencia preparada
                    $comando->execute(array($seller_username));
                    // Capturar primera fila del resultado
                     $row = $comando->fetch(PDO::FETCH_ASSOC);
                     return $row;

                } catch (PDOException $e) {
                    // Aquí puedes clasificar el error dependiendo de la excepción
                    // para presentarlo en la respuesta Json
                    return false;
                }

        }


	public static function insert(
        $username,
        $password,
        $name,
        $email,
        $birthDate,
        $phone,
        $nationality,
        $card_id,
		$reputation
    )
    {
        // Sentencia INSERT
        $command = "INSERT INTO persons ( " .
            "username, " .
            "password, " .
            "name, " .
            "email, " .
            "birthDate, " .
            "phone, " .
            "nationality, " .
            "card_id, " .
            "reputation )" .
            " VALUES( ?,?,?,?,?,?,?,?,? )";

        // Preparar la sentencia
        $request = Database::getInstance()->getDb()->prepare($command);

        return $request->execute(
            array(
                $username,
				$password,
				$name,
				$email,
				$birthDate,
			    $phone,
				$nationality,
				$card_id,
				$reputation
            )
        );
    }
}

?>