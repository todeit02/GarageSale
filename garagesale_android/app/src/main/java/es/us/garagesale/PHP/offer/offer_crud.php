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
            $comando->execute(array($user));
            // Capturar primera fila del resultado
            $row = $comando->fetch(PDO::FETCH_ASSOC);
            return $row;

        } catch (PDOException $e) {
            // Aquí puedes clasificar el error dependiendo de la excepción
            // para presentarlo en la respuesta Json
            return -1;
        }
    }


    /**
     * Actualiza un registro de la bases de datos basado
     * en los nuevos valores relacionados con un identificador
     *
   * @param $name      nombre del nuevo registro
        * @param $description descripción del nuevo registro
         * @param $price   precio del nuevo registro
        * @param $publishDate    fecha de publicacion del nuevo registro
         * @param $sold    estado del nuevo registro
        * @param seller   vendedor del nuevo registro
         * @param $id    codigo  del nuevo registro
     */
    public static function update(
        $name,
        $description,
        $price,
        $publishDate,
        $sold,
        $seller,
        $id
    )
    {
        // Creando consulta UPDATE
        $consulta = "UPDATE offer" .
            " SET name=?, description=?, price=?, publishDate=?,sold=?,seller=?, id=? " .
            "WHERE id=?";

        // Preparar la sentencia
        $cmd = Database::getInstance()->getDb()->prepare($consulta);

        // Relacionar y ejecutar la sentencia
        $cmd->execute(array($name, $description, $price, $publishDate, $sold, $seller, $id));

        return $cmd;
    }


    /**
     * Insertar una nueva meta
     *
     * @param $name      nombre del nuevo registro
     * @param $description descripción del nuevo registro
      * @param $price   precio del nuevo registro
     * @param $publishDate    fecha de publicacion del nuevo registro
      * @param $sold    estado del nuevo registro
     * @param seller   vendedor del nuevo registro
      * @param $id    codigo  del nuevo registro
     * @return PDOStatement
     */
    public static function insert(
        $name,
        $description,
        $price,
        $publishDate,
         $sold,
         $seller,
        $id
    )
    {
        // Sentencia INSERT
        $comando = "INSERT INTO offer ( " .
            "name," .
            " description," .
            " price," .
            " publishDate," .
             " sold," .
            " seller)" .
            " VALUES( ?,?,?,?,?,?)";

        // Preparar la sentencia
        $sentencia = Database::getInstance()->getDb()->prepare($comando);

        return $sentencia->execute(
            array(
                $name,
                $description,
                $price,
                $publishDate,
                $sold,
                $seller,
                $id
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
        $comando = "DELETE FROM offer WHERE id=?";

        // Preparar la sentencia
        $sentencia = Database::getInstance()->getDb()->prepare($comando);

        return $sentencia->execute(array($id));
    }
}

?>