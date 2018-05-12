<?php

/**
 * Representa el la estructura de las metas
 * almacenadas en la base de datos
 */
require '../Database.php';

class OfferCrud
{
    function __construct()
    {
    }

    /**
     * Retorna en la fila especificada de la tabla 'offer'
     *
     * @param $id Identificador del registro
     * @return array Datos del registro
     */
    public static function getAll()
    {
        $consulta = "SELECT * FROM offers";
        try {
            // Preparar sentencia
            $comando = Database::getInstance()->getDb()->prepare($consulta);
            // Ejecutar sentencia preparada
            $comando->execute();

            return $comando->fetchAll(PDO::FETCH_ASSOC);

        } catch (PDOException $e) {
            return false;
        }
    }

    public static function getInterested($offer_id)
    {
        // Consulta de la meta
        $consulta = "SELECT * FROM interests WHERE offer_id = ?";

        try {
            // Preparar sentencia
            $comando = Database::getInstance()->getDb()->prepare($consulta);
            // Ejecutar sentencia preparada
            $comando->execute(array($offer_id));

        return $comando->fetchAll(PDO::FETCH_ASSOC);

        } catch (PDOException $e) {
            return false;
        }
    }

    public static function getUsernameOffers($seller_username)
    {
            // Consulta de la meta
            $consulta = "SELECT * FROM offers WHERE seller_username = ?";

            try {
                // Preparar sentencia
                $comando = Database::getInstance()->getDb()->prepare($consulta);
                // Ejecutar sentencia preparada
                $comando->execute(array($seller_username));

            return $comando->fetchAll(PDO::FETCH_ASSOC);

            } catch (PDOException $e) {
                return false;
            }
    }


    /**
     * Obtiene los campos de un offer con un identificador
     * determinado
     *
     * @param $id Identificador del offer
     * @return mixed
     */
    public static function getById($id)
    {
        // Consulta de la meta
        $consulta = "SELECT * FROM offers WHERE id = ?";

        try {
            // Preparar sentencia
            $comando = Database::getInstance()->getDb()->prepare($consulta);
            // Ejecutar sentencia preparada
            $comando->execute(array($id));
            // Capturar primera fila del resultado
            $row = $comando->fetch(PDO::FETCH_ASSOC);
            return $row;

        } catch (PDOException $e) {
            // Aquí puedes clasificar el error dependiendo de la excepción
            // para presentarlo en la respuesta Json
            return -1;
        }
    }
	
	
	public static function getByAttributes($name, $seller_username)
    {
        // Consulta de la meta
        $consulta = "SELECT * FROM offers WHERE name = ? AND seller_username = ?";

        try {
            // Preparar sentencia
            $comando = Database::getInstance()->getDb()->prepare($consulta);
            // Ejecutar sentencia preparada
            $comando->execute(array($name, $seller_username));
            // Capturar primera fila del resultado
            $row = $comando->fetch(PDO::FETCH_ASSOC);
            return $row;

        } catch (PDOException $e) {
            // Aquí puedes clasificar el error dependiendo de la excepción
            // para presentarlo en la respuesta Json
            return NULL;
        }
    }


    /**
     * Actualiza un registro de la bases de datos basado
     * en los nuevos valores relacionados con un identificador
     *
   * @param $name      nombre del nuevo registro
        * @param $description descripción del nuevo registro
         * @param $price   precio del nuevo registro
        * @param $startTime    fecha de publicacion del nuevo registro
         * @param $sold    estado del nuevo registro
        * @param seller_username   vendedor del nuevo registro
         * @param $id    codigo  del nuevo registro
     */

   public static function update(
          $sold,
          $id
     )
     {
         // Creando consulta UPDATE
         $consulta = "UPDATE offers" .
             " SET sold=? " .
             "WHERE id=?";

         // Preparar la sentencia
         $cmd = Database::getInstance()->getDb()->prepare($consulta);

         // Relacionar y ejecutar la sentencia
         $cmd->execute(array($sold, $id));

         return $cmd;
     }

	/**
	* insert new offer
	*
	* @return boolean success
	*/
    public static function insert(
        $name,
        $description,
        $price,
        $seller_username,
		$state,
		$activePeriod
    )
    {
        // Sentencia INSERT
        $comando = "INSERT INTO offers ( " .
            "name," .
            " description," .
            " price," .
            " seller_username," .
            " state," .
            " activePeriod)" .
            " VALUES(?,?,?,?,?,?)";

        // Preparar la sentencia
        $sentencia = Database::getInstance()->getDb()->prepare($comando);

        return $sentencia->execute(
            array(
                $name,
                $description,
                $price,
                $seller_username,
				$state,
				$activePeriod
            )
        );

    }

    /**
     * Eliminar el registro con el identificador especificado
     *
     * @param $idCode identificador del offer
     * @return bool Respuesta de la eliminación
     */
    public static function delete($id)
      {
          // Sentencia DELETE
          $comando = "DELETE FROM offers WHERE id=?";

          // Preparar la sentencia
          $sentencia = Database::getInstance()->getDb()->prepare($comando);

          return $sentencia->execute(array($id));
      }

     /**
         * Insertar una nueva meta
         *
         * @param $titulo      titulo del nuevo registro
         * @param $descripcion descripción del nuevo registro
         * @param $fechaLim    fecha limite del nuevo registro
         * @param $categoria   categoria del nuevo registro
         * @param $prioridad   prioridad del nuevo registro
         * @return PDOStatement
         */
        public static function insertOfferInterested(
            $username,
            $offer_id,
            $price
        )
        {
            // Sentencia INSERT
            $comando = "INSERT INTO interests ( " .
                "username," .
                " offer_id," .
                " price)" .
                " VALUES( ?,?,?)";

            // Preparar la sentencia
            $sentencia = Database::getInstance()->getDb()->prepare($comando);

            return $sentencia->execute(
                array(
                    $username,
                    $offer_id,
                    $price
                )
            );

        }
}

?>