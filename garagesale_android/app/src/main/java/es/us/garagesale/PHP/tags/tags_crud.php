<?php

/**
 * Representa el la estructura de las metas
 * almacenadas en la base de datos
 */
require_once '../Database.php';

class TagsCrud
{
    function __construct()
    {
    }
	
	
	public static function getByOfferId($offerId)
    {
        // Consulta de la meta
        $consulta = "SELECT * FROM tags WHERE offer_id = ?";

        try {
            // Preparar sentencia
            $comando = Database::getInstance()->getDb()->prepare($consulta);
            // Ejecutar sentencia preparada
            $comando->execute(array($offerId));
            // Capturar primera fila del resultado
            return $comando->fetchAll(PDO::FETCH_ASSOC);

        } catch (PDOException $e) {
            // Aquí puedes clasificar el error dependiendo de la excepción
            // para presentarlo en la respuesta Json
            return -1;
        }
    }
	
	
	public static function getByTag($tag)
    {
        // Consulta de la meta
        $consulta = "SELECT * FROM tags WHERE tag = ?";

        try {
            // Preparar sentencia
            $comando = Database::getInstance()->getDb()->prepare($consulta);
            // Ejecutar sentencia preparada
            $comando->execute(array($tag));
            // Capturar primera fila del resultado
            return $comando->fetchAll(PDO::FETCH_ASSOC);

        } catch (PDOException $e) {
            // Aquí puedes clasificar el error dependiendo de la excepción
            // para presentarlo en la respuesta Json
            return -1;
        }
    }
	
	
	public static function insert(
        $offerId,
        $tag
    )
    {
        // Sentencia INSERT
        $comando = "INSERT INTO tags (" .
            "offer_id, " .
            "tag)" .
            " VALUES(?,?)";

        // Preparar la sentencia
        $sentencia = Database::getInstance()->getDb()->prepare($comando);

        return $sentencia->execute(
            array(
                $offerId,
                $tag
            )
        );
    }
	
	
	public static function delete($offerId)
    {
        // Sentencia DELETE
        $comando = "DELETE FROM tags WHERE offer_id = ?";

        // Preparar la sentencia
        $sentencia = Database::getInstance()->getDb()->prepare($comando);

        return $sentencia->execute(array($offerId));
    }
}

?>