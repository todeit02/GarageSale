<?php

/**
 * Representa el la estructura de las metas
 * almacenadas en la base de datos
 */
require '../Database.php';

class PersonCrud
{
    function __construct()
    {
    }

    /**
     * Obtiene los campos de un offer con un identificador
     * determinado
     *
     * @param $id Identificador del offer
     * @return mixed
     */
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
	
	public static function insert(
        $username,
        $password,
        $name,
        $email,
        $birthDate,
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
            "nationality, " .
            "card_id, " .
            "reputation )" .
            " VALUES( ?,?,?,?,?,?,?,? )";

        // Preparar la sentencia
        $request = Database::getInstance()->getDb()->prepare($command);

        return $request->execute(
            array(
                $username,
				$password,
				$name,
				$email,
				$birthDate,
				$nationality,
				$card_id,
				$reputation
            )
        );
    }
}

?>